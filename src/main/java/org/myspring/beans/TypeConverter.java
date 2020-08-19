package org.myspring.beans;

/**
 * @author yujiangtao
 * @date 2020/8/19 下午2:40
 */
public interface TypeConverter {

    <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;
}
