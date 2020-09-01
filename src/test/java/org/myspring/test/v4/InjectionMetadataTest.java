package org.myspring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.myspring.beans.factory.annotation.AutowiredFieldElement;
import org.myspring.beans.factory.annotation.InjectionElement;
import org.myspring.beans.factory.annotation.InjectionMetadata;
import org.myspring.beans.factory.support.DefaultBeanFactory;
import org.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.myspring.core.io.ClassPathResource;
import org.myspring.core.io.Resource;
import org.myspring.dao.v4.AccountDao;
import org.myspring.dao.v4.ItemDao;
import org.myspring.service.v4.PetStoreService;

import java.lang.reflect.Field;
import java.util.LinkedList;

/**
 * @author yujiangtao
 * @date 2020/8/31 下午7:57
 */
public class InjectionMetadataTest {

    @Test
    public void testInjection() throws Exception{

        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v4.xml");
        reader.loadBeanDefinitions(resource);

        Class<?> clz = PetStoreService.class;
        LinkedList<InjectionElement> elements = new LinkedList<InjectionElement>();

        {
            Field f = PetStoreService.class.getDeclaredField("accountDao");
            InjectionElement injectionElem = new AutowiredFieldElement(f,true,factory);
            elements.add(injectionElem);
        }
        {
            Field f = PetStoreService.class.getDeclaredField("itemDao");
            InjectionElement injectionElem = new AutowiredFieldElement(f,true,factory);
            elements.add(injectionElem);
        }

        InjectionMetadata metadata = new InjectionMetadata(clz,elements);

        PetStoreService petStore = new PetStoreService();

        metadata.inject(petStore);

        Assert.assertTrue(petStore.getAccountDao() instanceof AccountDao);

        Assert.assertTrue(petStore.getItemDao() instanceof ItemDao);

    }
}
