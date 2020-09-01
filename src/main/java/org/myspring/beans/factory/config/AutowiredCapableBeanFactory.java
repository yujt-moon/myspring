package org.myspring.beans.factory.config;

import org.myspring.beans.factory.BeanFactory;

/**
 * @author yujiangtao
 * @date 2020/8/31 下午5:27
 */
public interface AutowiredCapableBeanFactory extends BeanFactory {

    Object resolveDependency(DependencyDescriptor descriptor);
}
