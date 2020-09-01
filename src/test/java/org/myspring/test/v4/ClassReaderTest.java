package org.myspring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.myspring.core.annotation.AnnotationAttributes;
import org.myspring.core.io.ClassPathResource;
import org.myspring.core.type.classreading.AnnotationMetadataReadingVisitor;
import org.myspring.core.type.classreading.ClassMetadataReadingVisitor;
import org.objectweb.asm.ClassReader;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author yujiangtao
 * @date 2020/8/26 下午10:25
 */
public class ClassReaderTest {

    @Test
    public void testGetClassMetaData() throws IOException {
        ClassPathResource resource = new ClassPathResource("org/myspring/service/v4/PetStoreService.class");
        ClassReader reader = new ClassReader(resource.getInputStream());

        ClassMetadataReadingVisitor visitor = new ClassMetadataReadingVisitor();

        reader.accept(visitor, ClassReader.SKIP_DEBUG);

        Assert.assertFalse(visitor.isAbstract());
        Assert.assertFalse(visitor.isInterface());
        Assert.assertFalse(visitor.isFinal());
        Assert.assertEquals("org.myspring.service.v4.PetStoreService", visitor.getClassName());
        Assert.assertEquals("java.lang.Object", visitor.getSuperClassName());
        Assert.assertEquals(0, visitor.getInterfaceNames().length);
    }

    @Test
    public void testGetAnnonation() throws Exception{
        ClassPathResource resource = new ClassPathResource("org/myspring/service/v4/PetStoreService.class");
        ClassReader reader = new ClassReader(resource.getInputStream());

        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();

        reader.accept(visitor, ClassReader.SKIP_DEBUG);

        String annotation = "org.myspring.stereotype.Component";
        Assert.assertTrue(visitor.hasAnnotation(annotation));

        AnnotationAttributes attributes = visitor.getAnnotationAttributes(annotation);

        Assert.assertEquals("petStore", attributes.get("value"));

    }
}
