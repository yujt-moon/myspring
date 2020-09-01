package org.myspring.beans.factory.annotation;

import org.myspring.beans.BeanDefinition;
import org.myspring.core.type.AnnotationMetadata;

/**
 * @author yujiangtao
 * @date 2020/8/27 下午8:55
 */
public interface AnnotatedBeanDefinition extends BeanDefinition {

    AnnotationMetadata getMetadata();
}
