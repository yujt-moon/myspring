package org.myspring.beans.factory;

import org.myspring.beans.BeansException;

/**
 * bean的创建异常
 * @author yujiangtao
 * @date 2018/8/2 20:03
 */
public class BeanCreationException extends BeansException {

    /**
     * bean的class<bean class="com.xxx.xxx"></bean>
     */
    private String beanName;

    public BeanCreationException(String msg) {
        super(msg);
    }

    public BeanCreationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BeanCreationException(String beanName, String msg) {
        super("Error creating bean with name '" + beanName + "': " + msg);
        this.beanName = beanName;
    }

    public BeanCreationException(String beanName, String msg, Throwable cause) {
        this(beanName, msg);
        initCause(cause);
    }

    public String getBeanName() {
        return this.beanName;
    }
}
