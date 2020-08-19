package org.myspring.beans;

/**
 * @author yujiangtao
 * @date 2020/8/19 下午2:41
 */
public class TypeMismatchException extends BeansException {

    private transient Object value;

    private Class<?> requiredType;

    public TypeMismatchException(Object value, Class<?> requiredType) {
        super("Failed to convert value :" + value + "to type " + requiredType);
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
