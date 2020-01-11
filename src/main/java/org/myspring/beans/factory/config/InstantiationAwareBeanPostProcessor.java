package org.myspring.beans.factory.config;

import org.myspring.beans.BeansException;

/**
 * @author yujiangtao
 * @date 2020/1/10 16:26
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    Object beforeInstantiation(Class<?> beanClass, String beanName);

    boolean afterInstantiation(Object bean, String beanName) throws BeansException;

    void postProcessPropertyValues(Object bean, String beanName) throws BeansException;
}
