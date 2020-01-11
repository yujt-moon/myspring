package org.myspring.context.annotation;

import org.myspring.beans.factory.annotation.AnnotationBeanDefinition;
import org.myspring.beans.factory.support.GenericBeanDefinition;
import org.myspring.core.type.AnnotationMetadata;

/**
 * @author yujiangtao
 * @date 2020/1/7 19:25
 */
public class ScannedGenericBeanDefinition extends GenericBeanDefinition
                                            implements AnnotationBeanDefinition {

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
