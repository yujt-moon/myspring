package org.myspring.beans.factory;

/**
 * @author yujiangtao
 * @date 2018/8/2 11:11
 */
public interface BeanFactory {
    Object getBean(String beanId);
}
