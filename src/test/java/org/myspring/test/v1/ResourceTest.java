package org.myspring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.myspring.core.io.ClassPathResource;
import org.myspring.core.io.FileSystemResource;
import org.myspring.core.io.Resource;

import java.io.InputStream;

/**
 * @author yujiangtao
 * @date 2018/6/16 18:24
 */
public class ResourceTest {

    @Test
    public void testClassPathResource() throws Exception {
        Resource r = new ClassPathResource("petstore-v1.xml");

        InputStream is = null;
        try {
            is = r.getInputStream();
            // 注意：这个测试其实并不充分！！
            Assert.assertNotNull(is);
            String description = r.getDescription();
            Assert.assertNotNull(description);
        } finally {
            if(is != null) {
                is.close();
            }
        }
    }

    @Test
    public void testFileSystemResource() throws Exception {
        Resource r = new FileSystemResource("src/test/resources/petstore-v1.xml");

        InputStream is = null;

        try {
            is = r.getInputStream();
            // 注意：这个测试其实并不充分！！
            Assert.assertNotNull(is);
            String description = r.getDescription();
            Assert.assertNotNull(description);
        } finally {
            if(is != null) {
                is.close();
            }
        }

    }
}
