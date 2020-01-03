package org.myspring.test.v2;

import org.junit.Test;
import org.myspring.dao.v2.AccountDao;
import org.myspring.dao.v2.ItemDao;
import org.myspring.service.v2.PetStoreService;
import org.myspring.context.ApplicationContext;
import org.myspring.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * @author yujiangtao
 * @date 2018/6/22 20:35
 */
public class ApplicationContextTestV2 {

    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v2.xml");
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");

        assertNotNull(petStore.getAccountDao());
        assertNotNull(petStore.getItemDao());

        assertTrue(petStore.getAccountDao() instanceof AccountDao);
        assertTrue(petStore.getItemDao() instanceof ItemDao);

        assertEquals("liuxin", petStore.getOwner());
        assertEquals(2, petStore.getVersion());
    }
}
