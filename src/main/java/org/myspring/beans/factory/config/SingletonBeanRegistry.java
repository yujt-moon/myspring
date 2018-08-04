package org.myspring.beans.factory.config;

/**
 * @author yujiangtao
 * @date 2018/8/2 19:24
 */
public interface SingletonBeanRegistry {

    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);
}
