package org.myspring.context.annotation;

import org.myspring.beans.factory.annotation.AnnotatedBeanDefinition;
import org.myspring.beans.factory.support.GenericBeanDefinition;
import org.myspring.core.type.AnnotationMetadata;

/**
 * @author yujiangtao
 * @date 2020/8/27 下午8:53
 */
public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition {

    private final AnnotationMetadata metadata;

    public ScannedGenericBeanDefinition(AnnotationMetadata metadata) {
        super();
        this.metadata = metadata;
        setBeanClassName(this.metadata.getClassName());
    }

    @Override
    public AnnotationMetadata getMetadata() {
        return this.metadata;
    }
}
