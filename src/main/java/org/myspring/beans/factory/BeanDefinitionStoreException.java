package org.myspring.beans.factory;

import org.myspring.beans.BeansException;

/**
 * @author yujiangtao
 * @date 2018/8/2 20:09
 */
public class BeanDefinitionStoreException extends BeansException {
    public BeanDefinitionStoreException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
