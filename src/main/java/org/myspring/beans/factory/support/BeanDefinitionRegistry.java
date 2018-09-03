package org.myspring.beans.factory.support;

import org.myspring.beans.BeanDefinition;

/** bean的注册接口
 * @author yujiangtao
 * @date 2018/8/2 11:19
 */
public interface BeanDefinitionRegistry {

    /**
     * 获取beanDefinition
     * @param beanId
     * @return
     */
    BeanDefinition getBeanDefinition(String beanId);

    /**
     * 注册beanDefinition
     * @param beanId
     * @param bd
     */
    void registerBeanDefinition(String beanId, BeanDefinition bd);
}
