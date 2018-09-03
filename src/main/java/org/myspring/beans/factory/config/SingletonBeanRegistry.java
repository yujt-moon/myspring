package org.myspring.beans.factory.config;

/**
 * 单例的bean注册接口
 * @author yujiangtao
 * @date 2018/8/2 19:24
 */
public interface SingletonBeanRegistry {

    /**
     * 注册单例
     * @param beanName 就是beanId
     * @param singletonObject bean的实例
     */
    void registerSingleton(String beanName, Object singletonObject);

    /**
     * 获取bean的单例
     * @param beanName
     * @return
     */
    Object getSingleton(String beanName);
}
