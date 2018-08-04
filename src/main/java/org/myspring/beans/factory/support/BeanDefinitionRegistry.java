package org.myspring.beans.factory.support;

import org.myspring.beans.BeanDefinition;

/**
 * @author yujiangtao
 * @date 2018/8/2 11:19
 */
public interface BeanDefinitionRegistry {
    BeanDefinition getBeanDefinition(String beanId);
    void registerBeanDefinition(String beanId, BeanDefinition bd);
}
