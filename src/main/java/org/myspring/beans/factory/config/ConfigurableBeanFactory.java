package org.myspring.beans.factory.config;

import org.myspring.beans.factory.BeanFactory;

import java.util.List;

/**
 * 可配置的beanFactory
 * @author yujiangtao
 * @date 2018/8/2 20:20
 */
public interface ConfigurableBeanFactory extends AutowiredCapableBeanFactory {

    /**
     * 设置bean的类加载器
     * @param beanClassLoader
     */
    void setBeanClassLoader(ClassLoader beanClassLoader);

    /**
     * 获取bean的类加载器
     * @return
     */
    ClassLoader getBeanClassLoader();

    void addBeanPostProcessor(BeanPostProcessor postProcessor);

    List<BeanPostProcessor> getBeanPostProcessors();
}
