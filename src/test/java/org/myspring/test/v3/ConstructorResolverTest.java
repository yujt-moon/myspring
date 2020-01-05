package org.myspring.test.v3;

import org.junit.Assert;
import org.junit.Test;
import org.myspring.beans.BeanDefinition;
import org.myspring.beans.factory.support.ConstructorResolver;
import org.myspring.beans.factory.support.DefaultBeanFactory;
import org.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.myspring.core.io.ClassPathResource;
import org.myspring.core.io.Resource;
import org.myspring.service.v3.PetStoreService;

/**
 * @author yujiangtao
 * @date 2018/7/3 15:07
 */
public class ConstructorResolverTest {

    @Test
    public void testAutowireConstructor() {
        // 文件从哪里来呢？
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v3.xml");
        reader.loadBeanDefinitions(resource);

        BeanDefinition bd = factory.getBeanDefinition("petStore");

        ConstructorResolver resolver = new ConstructorResolver(factory);
        PetStoreService petStore = (PetStoreService) resolver.autowireConstructor(bd);

        // 验证参数version 正确地通过此构造函数初始化
        // PetStoreService(AccountDao accountDao, ItemDao itemDao, int version);
        Assert.assertEquals(1, petStore.getVersion());

        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());
    }
}
