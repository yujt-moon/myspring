package org.myspring.beans.factory.config;

import org.myspring.beans.BeansException;

/**
 * @author yujiangtao
 * @date 2020/1/10 16:21
 */
public interface BeanPostProcessor {

    Object beforeInitialization(Object bean, String beanName) throws BeansException;

    Object afterInitialization(Object bean, String beanName) throws BeansException;
}
