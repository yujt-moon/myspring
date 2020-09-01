package org.myspring.beans.factory.config;

import org.myspring.beans.BeansException;

/**
 * @author yujiangtao
 * @date 2020/9/1 上午11:16
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    boolean afterInstantiation(Object bean, String beanName) throws BeansException;

    void postProcessorPropertyValues(Object bean, String beanName) throws BeansException;
}
