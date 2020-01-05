package org.myspring.test.v3;

import org.junit.Test;
import org.myspring.context.ApplicationContext;
import org.myspring.context.support.ClassPathXmlApplicationContext;
import org.myspring.dao.v3.AccountDao;
import org.myspring.dao.v3.ItemDao;
import org.myspring.service.v3.PetStoreService;

import static org.junit.Assert.*;

/**
 * @author yujiangtao
 * @date 2018/6/22 20:35
 */
public class ApplicationContextTestV3 {

    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v3.xml");
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");

        assertNotNull(petStore.getAccountDao());
        assertNotNull(petStore.getItemDao());

        assertTrue(petStore.getAccountDao() instanceof AccountDao);
        assertTrue(petStore.getItemDao() instanceof ItemDao);
        assertEquals(1, petStore.getVersion());
    }
}
