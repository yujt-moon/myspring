package org.myspring.context.annotation;

import org.myspring.beans.BeanDefinition;
import org.myspring.beans.factory.annotation.AnnotationBeanDefinition;
import org.myspring.beans.factory.support.BeanDefinitionRegistry;
import org.myspring.beans.factory.support.BeanNameGenerator;
import org.myspring.core.annotation.AnnotationAttributes;
import org.myspring.core.type.AnnotationMetadata;
import org.myspring.util.ClassUtils;
import org.myspring.util.StringUtils;

import java.beans.Introspector;
import java.util.Set;

/**
 * @author yujiangtao
 * @date 2020/1/7 19:43
 */
public class AnnotationBeanNameGenerator implements BeanNameGenerator {

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        if(definition instanceof AnnotationBeanDefinition) {
            String beanName = determineBeanNameFromAnnotation((AnnotationBeanDefinition) definition);
            if(StringUtils.hasText(beanName)) {
                // Explicit bean name found.
                return beanName;
            }
        }
        return buildDefaultBeanName(definition, registry);
    }

    protected String determineBeanNameFromAnnotation(AnnotationBeanDefinition annotatedDef) {
        AnnotationMetadata amd = annotatedDef.getMetadata();
        Set<String> types = amd.getAnnotationTypes();
        String beanName = null;
        for(String type : types) {
            AnnotationAttributes attributes = amd.getAnnotationAttributes(type);
            if(attributes.get("value") != null) {
                Object value = attributes.get("value");
                if(value instanceof String) {
                    String strVal = (String) value;
                    if(StringUtils.hasLength(strVal)) {
                        beanName = strVal;
                    }
                }
            }
        }
        return beanName;
    }

    protected String buildDefaultBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        return buildDefaultBeanName(definition);
    }

    protected String buildDefaultBeanName(BeanDefinition definition) {
        String shortClassName = ClassUtils.getShortName(definition.getBeanClassName());
        return Introspector.decapitalize(shortClassName);
    }
}
