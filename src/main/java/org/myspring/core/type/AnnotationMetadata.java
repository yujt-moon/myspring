package org.myspring.core.type;

import org.myspring.core.annotation.AnnotationAttributes;

import java.util.Set;

/**
 * @author yujiangtao
 * @date 2020/8/27 下午5:01
 */
public interface AnnotationMetadata extends ClassMetadata {

    Set<String> getAnnotationTypes();

    boolean hasAnnotation(String annotationType);

    AnnotationAttributes getAnnotationAttributes(String annotationType);
}
