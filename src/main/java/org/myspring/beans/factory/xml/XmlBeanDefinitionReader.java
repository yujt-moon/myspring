package org.myspring.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.myspring.beans.BeanDefinition;
import org.myspring.beans.factory.BeanDefinitionStoreException;
import org.myspring.beans.factory.support.BeanDefinitionRegistry;
import org.myspring.beans.factory.support.GenericBeanDefinition;
import org.myspring.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * @author yujiangtao
 * @date 2018/8/2 11:16
 */
public class XmlBeanDefinitionReader {

    public static final String ELEMENT_BEAN = "bean";

    public static final String ATTRIBUTE_ID = "id";

    public static final String ATTRIBUTE_CLASS = "class";

    private BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void loadBeanDefinitions(Resource resource) {
        InputStream is = null;
        SAXReader reader = new SAXReader();
        Document doc = null;
        try {
            is = resource.getInputStream();
            doc = reader.read(is);

            Element rootElement = doc.getRootElement();
            Iterator it = rootElement.elementIterator();
            while(it.hasNext()) {
                Element ele = (Element) it.next();
                // 处理bean标签
                if(ELEMENT_BEAN.equals(ele.getName())) {
                    // 获取bean id
                    String id = ele.attributeValue(ATTRIBUTE_ID);
                    // 获取bean class
                    String className = ele.attributeValue(ATTRIBUTE_CLASS);
                    BeanDefinition bd = new GenericBeanDefinition(id, className);
                    registry.registerBeanDefinition(id, bd);
                }
            }
        } catch (Exception e) {
            throw new BeanDefinitionStoreException("IOException parsing XML document from " +
                    resource.getDescription(), e);
        } finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
