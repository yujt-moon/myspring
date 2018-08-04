package org.myspring.context;

/**
 * @author yujiangtao
 * @date 2018/8/3 15:11
 */
public interface ApplicationContext {
    Object getBean(String beanId);
}
