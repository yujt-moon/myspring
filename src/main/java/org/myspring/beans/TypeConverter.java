package org.myspring.beans;

/**
 * @author yujiangtao
 * @date 2020/1/2 20:51
 */
public interface TypeConverter {

    <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;
}
