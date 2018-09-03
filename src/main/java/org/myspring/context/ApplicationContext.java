package org.myspring.context;

/**
 * 应用的上下文
 * @author yujiangtao
 * @date 2018/8/3 15:11
 */
public interface ApplicationContext {

    /**
     * 获取bean对象
     * @param beanId
     * @return
     */
    Object getBean(String beanId);
}
