package org.myspring.core.type.classreading;

import org.myspring.core.io.Resource;
import org.myspring.core.type.AnnotationMetadata;
import org.myspring.core.type.ClassMetadata;

/**
 * Simple facade for accessing class metadata,
 * as read by ASM {@link org.springframework.asm.ClassReader}.
 * @author yujiangtao
 * @date 2020/1/6 18:44
 */
public interface MetadataReader {

    /**
     * Return the resource refenence for the class file.
     * @return
     */
    Resource getResource();

    /**
     * Read basic class metadata for the underlying class.
     * @return
     */
    ClassMetadata getClassMetadata();

    /**
     * Read full annotation metadata for the underlying class,
     * including metadata for annotated methods.
     * @return
     */
    AnnotationMetadata getAnnotationMetadata();
}
