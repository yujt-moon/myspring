package org.myspring.core.type.classreading;

import org.myspring.core.annotation.AnnotationAttributes;
import org.myspring.core.type.AnnotationMetadata;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Map;
import java.util.Set;

/**
 * @author yujiangtao
 * @date 2020/8/27 下午4:00
 */
public class AnnotationAttributesReadingVisitor extends AnnotationVisitor {

    private final String annotationType;

    private final Map<String, AnnotationAttributes> attributeMap;

    private AnnotationAttributes attributes = new AnnotationAttributes();

    public AnnotationAttributesReadingVisitor(String annotationType, Map<String, AnnotationAttributes> attributeMap) {
        super(Opcodes.ASM4);
        this.annotationType = annotationType;
        this.attributeMap = attributeMap;
    }

    @Override
    public void visit(String attributeName, Object attributeValue) {
        this.attributes.put(attributeName, attributeValue);
    }

    @Override
    public void visitEnd() {
        this.attributeMap.put(this.annotationType, this.attributes);
    }
}
