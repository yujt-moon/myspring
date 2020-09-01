package org.myspring.beans.factory.annotation;

import java.util.LinkedList;
import java.util.List;

/**
 * @author yujiangtao
 * @date 2020/8/31 下午8:04
 */
public class InjectionMetadata {

    private final Class<?> targetClass;

    private List<InjectionElement> injectionElements;

    public InjectionMetadata(Class<?> targetClass, List<InjectionElement> injectionElements) {
        this.targetClass = targetClass;
        this.injectionElements = injectionElements;
    }

    public List<InjectionElement> getInjectionElements() {
        return injectionElements;
    }

    public void inject(Object target) {
        if(injectionElements == null || injectionElements.isEmpty()) {
            return;
        }
        for (InjectionElement element : injectionElements) {
            element.inject(target);
        }
    }
}
