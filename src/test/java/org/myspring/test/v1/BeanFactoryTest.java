package org.myspring.test.v1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.myspring.beans.BeanDefinition;
import org.myspring.beans.factory.BeanCreationException;
import org.myspring.beans.factory.BeanDefinitionStoreException;
import org.myspring.beans.factory.support.DefaultBeanFactory;
import org.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.myspring.core.io.ClassPathResource;
import org.myspring.service.v1.PetStoreService;

import static org.junit.Assert.*;

/**
 * @author yujiangtao
 * @date 2018/6/11 11:26
 */
public class BeanFactoryTest {

    DefaultBeanFactory factory = null;
    XmlBeanDefinitionReader reader = null;

    @Before
    public void setUp() {
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
    }

    @Test
    public void testGetBean() {
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));

        BeanDefinition bd = factory.getBeanDefinition("petStore");

        assertTrue(bd.isSingleton());

        assertFalse(bd.isPrototype());

        assertEquals(BeanDefinition.SCOPE_DEFAULT, bd.getScope());

        assertEquals("org.myspring.service.v1.PetStoreService", bd.getBeanClassName());

        PetStoreService petStore = (PetStoreService)factory.getBean("petStore");

        assertNotNull(petStore);

        PetStoreService petStore2 = (PetStoreService)factory.getBean("petStore");

        assertTrue(petStore.equals(petStore2));
    }

    @Test
    public void testInvalidBean() {
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));
        try {
            factory.getBean("invalidBean");
        } catch (BeanCreationException e) {
            return;
        }
        Assert.fail("except BeanCreationException");
    }

    @Test
    public void testInvalidXML() {
        try {
            reader.loadBeanDefinitions(new ClassPathResource("xxx.xml"));
        } catch (BeanDefinitionStoreException e) {
            return;
        }
        Assert.fail("except BeanDefinitionStoreException");
    }
}
