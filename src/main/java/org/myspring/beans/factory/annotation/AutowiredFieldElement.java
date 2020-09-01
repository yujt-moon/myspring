package org.myspring.beans.factory.annotation;

import org.myspring.beans.factory.BeanCreationException;
import org.myspring.beans.factory.config.AutowiredCapableBeanFactory;
import org.myspring.beans.factory.config.DependencyDescriptor;
import org.myspring.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author yujiangtao
 * @date 2020/8/31 下午8:38
 */
public class AutowiredFieldElement extends InjectionElement {

    private boolean required;

    public AutowiredFieldElement(Field f, boolean required, AutowiredCapableBeanFactory factory) {
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
            DependencyDescriptor desc = new DependencyDescriptor(field, this.required);
            Object value = factory.resolveDependency(desc);
            if(value != null) {
                ReflectionUtils.makeAccessible(field);
                field.set(target, value);
            }
        } catch (Throwable e) {
            throw new BeanCreationException("Could not autowire field: " + field, e);
        }
    }
}
