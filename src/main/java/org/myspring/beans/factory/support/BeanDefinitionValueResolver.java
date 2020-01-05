package org.myspring.beans.factory.support;

import org.myspring.beans.factory.config.RuntimeBeanReference;
import org.myspring.beans.factory.config.TypedStringValue;

/**
 * @author yujiangtao
 * @date 2020/1/3 11:08
 */
public class BeanDefinitionValueResolver {

    private final AbstractBeanFactory beanFactory;

    public BeanDefinitionValueResolver(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object resolveValueIfNecessary(Object value) {
        if(value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            String refBeanName = ref.getBeanName();
            Object bean = this.beanFactory.getBean(refBeanName);
            return bean;
        } else if(value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        } else {
            throw new RuntimeException("The value " + value + " has not implemented");
        }
    }
}
