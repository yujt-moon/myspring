package org.myspring.beans.factory.support;

import org.myspring.beans.BeanDefinition;
import org.myspring.beans.factory.BeanCreationException;
import org.myspring.beans.factory.config.ConfigurableBeanFactory;
import org.myspring.util.ClassUtils;

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

    @Override
    public Object getBean(String beanId) {
        BeanDefinition bd = beanDefinitionMap.get(beanId);
        String scope = bd.getScope();
        // 如果当前的bean的范围为单例
        if("singleton".equals(scope) || "".equals(scope)) {
            Object singletonObject = singletonObjectMap.get(beanId);
            if(singletonObject == null) {
                singletonObject = resovleSingletonObject(bd);
                singletonObjectMap.put(beanId, singletonObject);
            }
            return singletonObject;
        }
        // 当前的bean的范围为原型
        else if("prototype".equals(scope)) {
            return resovleSingletonObject(bd);
        } else {
            // 如果范围都不符合，抛出异常
            throw new RuntimeException("Bean's scope is not correct!");
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

    private Object resovleSingletonObject(BeanDefinition bd) {
        Class<?> clazz = null;
        String beanClassName = bd.getBeanClassName();
        try {
            // TODO classLoader问题
            clazz = this.getBeanClassLoader().loadClass(beanClassName);
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean " + beanClassName + " failed", e);
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
}
