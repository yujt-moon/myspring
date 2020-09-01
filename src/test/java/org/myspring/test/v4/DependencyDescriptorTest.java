package org.myspring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.myspring.beans.factory.config.DependencyDescriptor;
import org.myspring.beans.factory.support.DefaultBeanFactory;
import org.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.myspring.core.io.ClassPathResource;
import org.myspring.core.io.Resource;
import org.myspring.dao.v4.AccountDao;
import org.myspring.service.v4.PetStoreService;

import java.lang.reflect.Field;

/**
 * @author yujiangtao
 * @date 2020/8/31 下午5:17
 */
public class DependencyDescriptorTest {

    @Test
    public void testResolveDependency() throws Exception{

        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v4.xml");
        reader.loadBeanDefinitions(resource);

        Field f = PetStoreService.class.getDeclaredField("accountDao");
        DependencyDescriptor  descriptor = new DependencyDescriptor(f,true);
        Object o = factory.resolveDependency(descriptor);
        Assert.assertTrue(o instanceof AccountDao);
    }
}
