package org.myspring.core.type.classreading;

import org.myspring.core.annotation.AnnotationAttributes;
import org.myspring.core.type.AnnotationMetadata;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Type;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author yujiangtao
 * @date 2020/8/27 上午11:00
 */
public class AnnotationMetadataReadingVisitor extends ClassMetadataReadingVisitor implements AnnotationMetadata {

    private final Set<String> annotationSet = new LinkedHashSet<>(4);

    private final Map<String, AnnotationAttributes> attributeMap = new LinkedHashMap<>(4);

    public AnnotationMetadataReadingVisitor() {
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        String className = Type.getType(descriptor).getClassName();
        this.annotationSet.add(className);
        return new AnnotationAttributesReadingVisitor(className, this.attributeMap);
    }

    @Override
    public Set<String> getAnnotationTypes() {
        return this.annotationSet;
    }

    @Override
    public boolean hasAnnotation(String annotationType) {
        return this.annotationSet.contains(annotationType);
    }

    @Override
    public AnnotationAttributes getAnnotationAttributes(String annotationType) {
        return this.attributeMap.get(annotationType);
    }
}
