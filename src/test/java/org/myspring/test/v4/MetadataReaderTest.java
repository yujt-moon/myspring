package org.myspring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.myspring.core.annotation.AnnotationAttributes;
import org.myspring.core.io.ClassPathResource;
import org.myspring.core.type.AnnotationMetadata;
import org.myspring.core.type.classreading.MetadataReader;
import org.myspring.core.type.classreading.SimpleMetadataReader;
import org.myspring.stereotype.Component;

import java.io.IOException;

/**
 * @author yujiangtao
 * @date 2018/7/19 22:22
 */
public class MetadataReaderTest {

    @Test
    public void testGetMetadata() throws IOException {
        ClassPathResource resource = new ClassPathResource("org/myspring/service/v4/PetStoreService.class");

        MetadataReader reader = new SimpleMetadataReader(resource);
        // 注意：不需要单独适应ClassMetadata
        // ClassMetadata clzMetadata = reader.getClassMetadata();
        AnnotationMetadata amd = reader.getAnnotationMetadata();

        String annotation = Component.class.getName();

        Assert.assertTrue(amd.hasAnnotation(annotation));
        AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
        Assert.assertEquals("petStore", attributes.get("value"));

        // 注：下面对class metadata的测试并不充分
        Assert.assertFalse(amd.isAbstract());
        Assert.assertFalse(amd.isFinal());
        Assert.assertEquals("org.myspring.service.v4.PetStoreService", amd.getClassName());
    }
}
