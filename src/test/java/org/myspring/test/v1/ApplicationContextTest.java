package org.myspring.test.v1;

import org.junit.Test;
import org.myspring.context.support.FileSystemXmlApplicationContext;
import org.myspring.service.v1.PetStoreService;
import org.myspring.context.ApplicationContext;
import org.myspring.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertNotNull;

/**
 * @author yujiangtao
 * @date 2018/6/16 18:04
 */
public class ApplicationContextTest {

    @Test
    public void testGetBean() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v1.xml");
        PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
        assertNotNull(petStore);
    }

    @Test
    public void testGetBeanFromFileSystem() {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("C:\\study\\git-workspace\\myspring\\src\\test\\resources\\petstore-v1.xml");
        PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
        assertNotNull(petStore);
    }
}
