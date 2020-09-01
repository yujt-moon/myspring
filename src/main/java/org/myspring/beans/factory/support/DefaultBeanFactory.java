package org.myspring.beans.factory.support;

import org.apache.commons.beanutils.BeanUtils;
import org.myspring.beans.BeanDefinition;
import org.myspring.beans.PropertyValue;
import org.myspring.beans.SimpleTypeConverter;
import org.myspring.beans.TypeConverter;
import org.myspring.beans.factory.BeanCreationException;
import org.myspring.beans.factory.config.BeanPostProcessor;
import org.myspring.beans.factory.config.ConfigurableBeanFactory;
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
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
                                implements ConfigurableBeanFactory, BeanDefinitionRegistry {

    // 存储bean的定义
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

    private ClassLoader beanClassLoader;

    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    @Override
    public Object getBean(String beanId) {
        // 查询 bean 的定义
        BeanDefinition bd = this.getBeanDefinition(beanId);
        if(bd == null) {
            return null;
        }

        // 单例只创建一个
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

    private Object createBean(BeanDefinition bd) {
        // 创建实例
        Object bean = instantiateBean(bd);
        // 设置属性
        populateBean(bd, bean);
        return bean;
    }

    private void populateBean(BeanDefinition bd, Object bean) {
        for (BeanPostProcessor processor : this.getBeanPostProcessors()) {
            if(processor instanceof InstantiationAwareBeanPostProcessor) {
                ((InstantiationAwareBeanPostProcessor) processor).postProcessorPropertyValues(bean, bd.getId());
            }
        }

        List<PropertyValue> pvs = bd.getPropertyValues();
        if(pvs == null || pvs.isEmpty()) {
            return;
        }

        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
        TypeConverter converter = new SimpleTypeConverter();

        try {
            for (PropertyValue pv : pvs) {
                String propertyName = pv.getName();
                // RuntimeBeanReference or TypedStringValue
                Object originalValue = pv.getValue();
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor pd : pds) {
                    if(pd.getName().equals(propertyName)) {
                        resolvedValue = converter.convertIfNecessary(resolvedValue, pd.getPropertyType());
                        pd.getWriteMethod().invoke(bean, resolvedValue);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" +
                    bd.getBeanClassName() + "]", e);
        }
    }

    private void populateBeanUseCommonBeanUtils(BeanDefinition bd, Object bean) {
        List<PropertyValue> pvs = bd.getPropertyValues();
        if(pvs == null || pvs.isEmpty()) {
            return;
        }

        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);

        try {
            for (PropertyValue pv : pvs) {
                String propertyName = pv.getName();
                // RuntimeBeanReference or TypedStringValue
                Object originalValue = pv.getValue();
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);
                BeanUtils.setProperty(bean, propertyName, resolvedValue);
            }
        } catch (Exception e) {
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" +
                    bd.getBeanClassName() + "]", e);
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

    private Object instantiateBean(BeanDefinition bd) {
        Class<?> clazz = null;
        String beanClassName = bd.getBeanClassName();
        if(bd.hasConstructArgumentValues()) {
            ConstructorResolver constructorResolver = new ConstructorResolver(this);
            return constructorResolver.autowireConstructor(bd);
        } else {
            try {
                clazz = this.getBeanClassLoader().loadClass(beanClassName);
                return clazz.newInstance();
            } catch (Exception e) {
                throw new BeanCreationException("create bean '" + beanClassName + "' failed", e);
            }
        }
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
    public void addBeanPostProcessor(BeanPostProcessor postProcessor) {
        this.beanPostProcessors.add(postProcessor);
    }

    @Override
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    @Override
    public Object resolveDependency(DependencyDescriptor descriptor) {
        Class<?> typeToMatch = descriptor.getDependencyType();
        for (BeanDefinition bd : this.beanDefinitionMap.values()) {
            // 确保 BeanDefinition 有 Class 对象
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
                throw new RuntimeException("can't load class: " + bd.getBeanClassName());
            }
        }
    }
}
