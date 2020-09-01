package org.myspring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.myspring.core.io.Resource;
import org.myspring.core.io.support.PackageResourceLoader;

import java.io.IOException;

/**
 * @author yujiangtao
 * @date 2020/8/26 下午3:21
 */
public class PackageResourceLoaderTest {

    @Test
    public void testGetResources() throws IOException {
        PackageResourceLoader loader = new PackageResourceLoader();
        Resource[] resources = loader.getResources("org.myspring.dao.v4");
        Assert.assertEquals(2, resources.length);
    }
}
