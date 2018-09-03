package org.myspring.beans.factory;

/**
 * beanFactory接口
 * @author yujiangtao
 * @date 2018/8/2 11:11
 */
public interface BeanFactory {

    /**
     * 根据beanId获取bean的实例
     * @param beanId
     * @return
     */
    Object getBean(String beanId);
}
