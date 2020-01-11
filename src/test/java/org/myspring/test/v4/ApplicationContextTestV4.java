package org.myspring.test.v4;

import org.junit.Test;
import org.myspring.context.ApplicationContext;
import org.myspring.context.support.ClassPathXmlApplicationContext;
import org.myspring.service.v4.PetStoreService;

import static org.junit.Assert.assertNotNull;

/**
 * @author yujiangtao
 * @date 2018/6/22 20:35
 */
public class ApplicationContextTestV4 {

    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v4.xml");
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");

        assertNotNull(petStore.getAccountDao());
        assertNotNull(petStore.getItemDao());
    }
}
