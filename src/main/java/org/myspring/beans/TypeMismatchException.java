package org.myspring.beans;

/**
 * @author yujiangtao
 * @date 2020/1/2 20:54
 */
public class TypeMismatchException extends RuntimeException {
    private transient Object value;

    private Class<?> requiredType;

    public TypeMismatchException(Object value, Class<?> requiredType) {
        super("Failed to convert value: " + value + " to type " + requiredType);
        this.value = value;
        this.requiredType = requiredType;
    }

    public Object getValue() {
        return value;
    }

    public Class<?> getRequiredType() {
        return requiredType;
    }
}
