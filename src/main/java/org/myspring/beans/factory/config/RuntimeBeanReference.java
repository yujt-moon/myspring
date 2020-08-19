package org.myspring.beans.factory.config;

/**
 * @author yujiangtao
 * @date 2020/8/18 下午8:09
 */
public class RuntimeBeanReference {

    private final String beanName;

    public RuntimeBeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
