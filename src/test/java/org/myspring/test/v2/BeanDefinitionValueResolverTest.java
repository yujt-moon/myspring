package org.myspring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.myspring.beans.factory.config.TypedStringValue;
import org.myspring.beans.factory.config.RuntimeBeanReference;
import org.myspring.beans.factory.support.BeanDefinitionValueResolver;
import org.myspring.beans.factory.support.DefaultBeanFactory;
import org.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.myspring.core.io.ClassPathResource;
import org.myspring.dao.v2.AccountDao;

/**
 * 测试处理<property></property>的value和ref的值
 * @author yujiangtao
 * @date 2018/6/23 14:09
 */
public class BeanDefinitionValueResolverTest {

    @Test
    public void testResolveRuntimeBeanReference() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));

        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);

        RuntimeBeanReference reference = new RuntimeBeanReference("accountDao");
        Object value = resolver.resolveValueIfNecessary(reference);

        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof AccountDao);
    }

    @Test
    public void testResolveTypedStringValue() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));

        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(factory);

        TypedStringValue stringValue = new TypedStringValue("test");
        Object value = resolver.resolveValueIfNecessary(stringValue);

        Assert.assertNotNull(value);
        Assert.assertEquals("test", value);
    }
}
