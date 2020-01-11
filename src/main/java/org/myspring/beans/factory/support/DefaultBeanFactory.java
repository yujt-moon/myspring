package org.myspring.beans.factory.support;

import org.myspring.beans.BeanDefinition;
import org.myspring.beans.PropertyValue;
import org.myspring.beans.SimpleTypeConverter;
import org.myspring.beans.TypeConverter;
import org.myspring.beans.factory.BeanCreationException;
import org.myspring.beans.factory.config.BeanPostProcessor;
import org.myspring.beans.factory.config.DependencyDescriptor;
import org.myspring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.myspring.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的beanFactory
 * @author yujiangtao
 * @date 2018/8/2 11:08
 */
public class DefaultBeanFactory extends AbstractBeanFactory
                                    implements BeanDefinitionRegistry {

    // 存储bean的定义
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    private ClassLoader beanClassLoader;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor postProcessor) {
        this.beanPostProcessors.add(postProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    @Override
    public Object getBean(String beanId) {
        BeanDefinition bd = beanDefinitionMap.get(beanId);
        if(bd == null) {
            return null;
        }

        if(bd.isSingleton()) {
            Object bean = this.getSingleton(beanId);
            if(bean == null) {
                bean = createBean(bd);
                this.registerSingleton(beanId, bean);
            }
            return bean;
        }
        return createBean(bd);
    }

    @Override
    protected Object createBean(BeanDefinition bd) {
        // 创建实例
        Object bean = instantiateBean(bd);
        // 设置属性
        populateBean(bd, bean);
        return bean;
    }

    protected void populateBean(BeanDefinition bd, Object bean) {

        // 注解注入（component/autowired）
        for(BeanPostProcessor processor : this.beanPostProcessors) {
            if(processor instanceof InstantiationAwareBeanPostProcessor) {
                ((InstantiationAwareBeanPostProcessor) processor).postProcessPropertyValues(bean, bd.getId());
            }
        }

        // 获取<bean>中的<property>标签的属性
        List<PropertyValue> pvs = bd.getPropertyValues();
        if(pvs == null || pvs.isEmpty()) {
            return;
        }

        // 将标签<property>中的value和ref的值转换为对应的实际的值
        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
        TypeConverter converter = new SimpleTypeConverter();

        try {
            // 使用javabean提供的方法，未使用反射（底层实现？）
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();

            for (PropertyValue pv : pvs) {
                String propertyName = pv.getName();
                Object orginalvalue = pv.getValue();
                Object resolvedValue = valueResolver.resolveValueIfNecessary(orginalvalue);

                for (PropertyDescriptor pd : pds) {
                    if(pd.getName().equals(propertyName)) {
                        Object convertedValue = converter.convertIfNecessary(resolvedValue, pd.getPropertyType());
                        pd.getWriteMethod().invoke(bean, convertedValue);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" +
                                                bd.getBeanClassName() + "]");
        }
    }

    /**
     * 实例化含有默认构造器的对象
     * @param bd
     * @return
     */
    private Object instantiateBean(BeanDefinition bd) {
        // 有参构造器
        if(bd.hasConstructorArgumentValues()) {
            ConstructorResolver resolver = new ConstructorResolver(this);
            return resolver.autowireConstructor(bd);
        }
        // 无参构造器
        else {
            ClassLoader cl = this.getBeanClassLoader();
            String beanClassName = bd.getBeanClassName();
            try {
                Class<?> clz = cl.loadClass(beanClassName);
                return clz.newInstance();
            } catch (Exception e) {
                throw new BeanCreationException("create bean for " + beanClassName + "failed", e);
            }
        }
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }

    @Override
    public void registerBeanDefinition(String beanId, BeanDefinition bd) {
        this.beanDefinitionMap.put(beanId, bd);
    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader == null ? ClassUtils.getDefaultClassLoader() : this.beanClassLoader;
    }

    @Override
    public Object resolveDependency(DependencyDescriptor descriptor) {
        Class<?> typeToMatch = descriptor.getDependencyType();
        for(BeanDefinition bd : this.beanDefinitionMap.values()) {
            // 确保BeanDefinition有Class对象
            resolveBeanClass(bd);
            Class<?> beanClass = bd.getBeanClass();
            if(typeToMatch.isAssignableFrom(beanClass)) {
                return this.getBean(bd.getId());
            }
        }
        return null;
    }

    private void resolveBeanClass(BeanDefinition bd) {
        if(bd.hasBeanClass()) {
            return;
        } else {
            try {
                bd.resolveBeanClass(this.getBeanClassLoader());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("can't load class: " +
                                            bd.getBeanClassName());
            }
        }
    }
}
