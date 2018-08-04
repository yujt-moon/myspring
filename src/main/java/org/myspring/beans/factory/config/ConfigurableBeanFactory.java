package org.myspring.beans.factory.config;

import org.myspring.beans.factory.BeanFactory;

/**
 * @author yujiangtao
 * @date 2018/8/2 20:20
 */
public interface ConfigurableBeanFactory extends BeanFactory {
    void setBeanClassLoader(ClassLoader beanClassLoader);

    ClassLoader getBeanClassLoader();
}
