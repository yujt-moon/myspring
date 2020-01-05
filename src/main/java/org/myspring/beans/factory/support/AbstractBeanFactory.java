package org.myspring.beans.factory.support;

import org.myspring.beans.BeanDefinition;
import org.myspring.beans.factory.BeanCreationException;
import org.myspring.beans.factory.config.ConfigurableBeanFactory;

/**
 * @author yujiangtao
 * @date 2020/1/3 17:07
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry
                                                implements ConfigurableBeanFactory {
    protected abstract Object createBean(BeanDefinition bd) throws BeanCreationException;
}
