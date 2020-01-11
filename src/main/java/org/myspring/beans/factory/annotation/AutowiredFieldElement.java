package org.myspring.beans.factory.annotation;

import org.myspring.beans.factory.BeanCreationException;
import org.myspring.beans.factory.config.AutowireCapableBeanFactory;
import org.myspring.beans.factory.config.DependencyDescriptor;
import org.myspring.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author yujiangtao
 * @date 2020/1/9 19:33
 */
public class AutowiredFieldElement extends InjectionElement {

    boolean required;

    public AutowiredFieldElement(Field f, boolean required,
                                 AutowireCapableBeanFactory factory) {
        super(f, factory);
        this.required = required;
    }

    public Field getField() {
        return (Field) this.member;
    }

    @Override
    public void inject(Object target) {
        Field field = this.getField();
        try {
            DependencyDescriptor descriptor = new DependencyDescriptor(field, this.required);
            Object value = factory.resolveDependency(descriptor);
            if(value != null) {
                ReflectionUtils.makeAccessible(field);
                field.set(target, value);
            }
        } catch (Throwable ex) {
            throw new BeanCreationException("Could not autowire field: " +
                                                field, ex);
        }
    }
}
