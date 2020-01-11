package org.myspring.test.v1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.myspring.beans.factory.support.DefaultBeanFactory;
import org.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.myspring.core.io.ClassPathResource;
import org.myspring.service.v1.CatStoreService;
import org.myspring.service.v1.DogStoreService;
import org.myspring.service.v1.PetStoreService;

/**
 * bean的scope的单元测试（后添加，用来测试边界和异常情况）
 * @author yujiangtao
 * @date 2018/8/22 20:43
 */
public class BeanScopeTest {

    DefaultBeanFactory factory = null;

    @Before
    public void setUp() {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v1.xml"));
    }

    /**
     * 测试bean没有填写scope属性
     */
    @Test
    public void testBeanWithoutScope() {
        PetStoreService petStore1 = (PetStoreService) factory.getBean("petStore");
        PetStoreService petStore2 = (PetStoreService) factory.getBean("petStore");

        Assert.assertEquals("", factory.getBeanDefinition("petStore").getScope());
        Assert.assertEquals(petStore1, petStore2);
    }

    /**
     * 测试bean的scope为singleton
     */
    @Test
    public void testBeanSingleton() {
        CatStoreService catStore1 = (CatStoreService) factory.getBean("catStore");
        CatStoreService catStore2 = (CatStoreService) factory.getBean("catStore");

        Assert.assertEquals("singleton", factory.getBeanDefinition("catStore").getScope());
        Assert.assertEquals(catStore1, catStore2);
    }

    /**
     * 测试bean的scope为prototype
     */
    @Test
    public void testBeanPrototype() {
        DogStoreService dogStore1 = (DogStoreService) factory.getBean("dogStore");
        DogStoreService dogStore2 = (DogStoreService) factory.getBean("dogStore");

        Assert.assertEquals("prototype", factory.getBeanDefinition("dogStore").getScope());
        Assert.assertNotEquals(dogStore1, dogStore2);
    }
}
