package org.myspring.beans.factory.support;

import org.myspring.beans.factory.config.SingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yujiangtao
 * @date 2018/8/2 19:27
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

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
