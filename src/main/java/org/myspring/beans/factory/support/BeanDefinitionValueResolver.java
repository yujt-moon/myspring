package org.myspring.beans.factory.support;

import org.myspring.beans.factory.config.RuntimeBeanReference;
import org.myspring.beans.factory.config.TypedStringValue;

/**
 * @author yujiangtao
 * @date 2020/8/18 下午9:06
 */
public class BeanDefinitionValueResolver {

    private final DefaultBeanFactory beanFactory;

    public BeanDefinitionValueResolver(DefaultBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 解析 <property> 中的 ref 和 value
     * @param value
     * @return
     */
    public Object resolveValueIfNecessary(Object value) {
        if(value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            String refName = ref.getBeanName();
            Object bean = this.beanFactory.getBean(refName);
            return bean;
        } else if(value instanceof TypedStringValue) {
            TypedStringValue stringValue = (TypedStringValue) value;
            return stringValue.getValue();
        } else {
            // TODO
            throw new RuntimeException("the value " + value + " has not implemented");
        }
    }
}
