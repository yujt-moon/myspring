package org.myspring.beans.factory.support;

import org.myspring.beans.BeanDefinition;

/**
 * @author yujiangtao
 * @date 2020/1/7 19:41
 */
public interface BeanNameGenerator {
    String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry);
}
