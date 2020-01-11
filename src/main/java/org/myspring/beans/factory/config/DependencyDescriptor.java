package org.myspring.beans.factory.config;

import org.myspring.util.Assert;

import java.lang.reflect.Field;

/**
 * @author yujiangtao
 * @date 2020/1/9 11:15
 */
public class DependencyDescriptor {

    private Field field;

    private boolean required;

    public DependencyDescriptor(Field field, boolean required) {
        Assert.notNull(field, "Field must not be null");
        this.field = field;
        this.required = required;
    }

    public Class<?> getDependencyType() {
        if(this.field != null) {
            return field.getType();
        }
        throw new RuntimeException("only support field dependency");
    }

    public boolean isRequired() {
        return this.required;
    }
}
