package org.myspring.beans;

/**
 * @author yujiangtao
 * @date 2018/8/2 20:00
 */
public abstract class BeansException extends RuntimeException {

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
