package org.myspring.core.type;

import org.myspring.core.annotation.AnnotationAttributes;

import java.util.Set;

/**
 * @author yujiangtao
 * @date 2020/1/6 19:25
 */
public interface AnnotationMetadata extends ClassMetadata {

    Set<String> getAnnotationTypes();

    boolean hasAnnotation(String annotationType);

    AnnotationAttributes getAnnotationAttributes(String annotationType);
}
