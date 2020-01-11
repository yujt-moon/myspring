package org.myspring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.myspring.beans.factory.annotation.AutowiredAnnotationProcessor;
import org.myspring.beans.factory.annotation.AutowiredFieldElement;
import org.myspring.beans.factory.annotation.InjectionElement;
import org.myspring.beans.factory.annotation.InjectionMetadata;
import org.myspring.beans.factory.config.DependencyDescriptor;
import org.myspring.beans.factory.support.DefaultBeanFactory;
import org.myspring.dao.v4.AccountDao;
import org.myspring.dao.v4.ItemDao;
import org.myspring.service.v4.PetStoreService;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author yujiangtao
 * @date 2018/7/31 19:30
 */
public class AutowiredAnnotationProcessorTest {

    AccountDao accountDao = new AccountDao();

    ItemDao itemDao = new ItemDao();

    DefaultBeanFactory beanFactory = new DefaultBeanFactory(){
        @Override
        public Object resolveDependency(DependencyDescriptor descriptor) {
            if(descriptor.getDependencyType().equals(AccountDao.class)) {
                return accountDao;
            }
            if(descriptor.getDependencyType().equals(ItemDao.class)) {
                return itemDao;
            }
            throw new RuntimeException("can't support types except AccountDao and ItemDao");
        }
    };

    @Test
    public void testGetInjectionMetadata() {
        AutowiredAnnotationProcessor processor = new AutowiredAnnotationProcessor();
        processor.setBeanFactory(beanFactory);
        InjectionMetadata injectionMetadata = processor.buildAutowiringMetadata(PetStoreService.class);
        List<InjectionElement> elements = injectionMetadata.getInjectionElements();
        Assert.assertEquals(2, elements.size());

        assertFieldExists(elements, "accountDao");
        assertFieldExists(elements, "itemDao");
    }

    private void assertFieldExists(List<InjectionElement> elements, String fieldName) {
        for(InjectionElement ele : elements) {
            AutowiredFieldElement fieldEle = (AutowiredFieldElement) ele;
            Field field = fieldEle.getField();
            if(field.getName().equals(fieldName)) {
                return;
            }
        }
        Assert.fail(fieldName + "does not exist!");
    }
}
