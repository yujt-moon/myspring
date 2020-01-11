package org.myspring.beans.factory.config;

import org.myspring.beans.factory.BeanFactory;

/**
 * @author yujiangtao
 * @date 2020/1/9 11:33
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    Object resolveDependency(DependencyDescriptor descriptor);
}
