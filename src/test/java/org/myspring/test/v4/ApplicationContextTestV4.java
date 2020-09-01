package org.myspring.test.v4;

import org.junit.Test;
import org.myspring.context.ApplicationContext;
import org.myspring.context.support.ClassPathXmlApplicationContext;
import org.myspring.service.v4.PetStoreService;

import static org.junit.Assert.*;

/**
 * @author yujiangtao
 * @date 2020/8/18 下午7:50
 */
public class ApplicationContextTestV4 {

    @Test
    public void testGetBean() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v4.xml");
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");

        assertNotNull(petStore.getAccountDao());
        assertNotNull(petStore.getItemDao());
    }
}
