package org.myspring.beans.factory.support;

import org.myspring.beans.factory.config.SingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的单例bean的注册器
 * @author yujiangtao
 * @date 2018/8/2 19:27
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 持有单例bean实例的map
     */
    protected Map<String, Object> singletonObjectMap = new ConcurrentHashMap<String, Object>();

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        this.singletonObjectMap.put(beanName, singletonObject);
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletonObjectMap.get(beanName);
    }
}
