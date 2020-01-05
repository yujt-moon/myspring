package org.myspring.beans.factory.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.myspring.beans.BeanDefinition;
import org.myspring.beans.ConstructorArgument;
import org.myspring.beans.PropertyValue;
import org.myspring.beans.factory.BeanDefinitionStoreException;
import org.myspring.beans.factory.config.RuntimeBeanReference;
import org.myspring.beans.factory.config.TypedStringValue;
import org.myspring.beans.factory.support.BeanDefinitionRegistry;
import org.myspring.beans.factory.support.GenericBeanDefinition;
import org.myspring.core.io.Resource;
import org.myspring.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * 通过读取xml，产生bean的定义
 * @author yujiangtao
 * @date 2018/8/2 11:16
 */
public class XmlBeanDefinitionReader {

    /**
     * bean标签
     */
    public static final String BEAN_ELEMENT = "bean";

    /**
     * id属性
     */
    public static final String ID_ATTRIBUTE = "id";

    /**
     * class属性
     */
    public static final String CLASS_ATTRIBUTE = "class";

    /**
     * scope属性
     */
    public static final String SCOPE_ATTRIBUTE = "scope";

    /**
     * property标签
     */
    public static final String PROPERTY_ELEMENT = "property";

    /**
     * ref 属性
     */
    public static final String REF_ATTRIBUTE = "ref";

    /**
     * value 属性
     */
    public static final String VALUE_ATTRIBUTE = "value";

    /**
     * name 属性
     */
    public static final String NAME_ATTRIBUTE = "name";

    /**
     * constructor-arg 标签
     */
    public static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";

    /**
     * type 属性
     */
    public static final String TYPE_ATTRIBUTE = "type";

    private BeanDefinitionRegistry registry;

    protected final Log logger = LogFactory.getLog(this.getClass());

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
                if(BEAN_ELEMENT.equals(ele.getName())) {
                    // 获取bean id
                    String id = ele.attributeValue(ID_ATTRIBUTE);
                    // 获取bean class
                    String className = ele.attributeValue(CLASS_ATTRIBUTE);
                    BeanDefinition bd = new GenericBeanDefinition(id, className);
                    if(ele.attribute(SCOPE_ATTRIBUTE) != null) {
                        bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));
                    }
                    parsePropertyElement(ele, bd);
                    parseConstructorArgElements(ele, bd);
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

    private void parseConstructorArgElements(Element beanEle, BeanDefinition bd) {
        Iterator iter = beanEle.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
        while(iter.hasNext()) {
            Element ele = (Element) iter.next();
            parseConstructorArgElement(ele, bd);
        }
    }

    private void parseConstructorArgElement(Element ele, BeanDefinition bd) {
        String typeAttr = ele.attributeValue(TYPE_ATTRIBUTE);
        String nameAttr = ele.attributeValue(NAME_ATTRIBUTE);
        Object value = parsePropertyValue(ele, bd, null);
        ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(value);
        if(StringUtils.hasLength(typeAttr)) {
            valueHolder.setType(typeAttr);
        }
        if(StringUtils.hasLength(nameAttr)) {
            valueHolder.setName(nameAttr);
        }
        bd.getConstructorArgument().addArgumentValue(valueHolder);
    }

    private void parsePropertyElement(Element beanElem, BeanDefinition bd) {
        Iterator properties = beanElem.elementIterator(PROPERTY_ELEMENT);
        while(properties.hasNext()) {
            Element property = (Element) properties.next();
            String name = property.attributeValue(NAME_ATTRIBUTE);
            if(!StringUtils.hasLength(name)) {
                logger.fatal("Tag 'property' must have a 'name' attribute");
                return;
            }

            Object val = parsePropertyValue(property, bd, name);
            PropertyValue pv = new PropertyValue(name, val);

            bd.getPropertyValues().add(pv);
        }
    }

    private Object parsePropertyValue(Element ele, BeanDefinition bd, String propertyName) {
        String elementName = (propertyName != null) ?
                                                "<property> element for property '" + propertyName + "'" :
                                                "<constructor-arg> element";
        boolean hasRefAttribute = ele.attribute(REF_ATTRIBUTE) != null;
        boolean hasValueAttribute = ele.attribute(VALUE_ATTRIBUTE) != null;

        if(hasRefAttribute) {
            String refName = ele.attributeValue(REF_ATTRIBUTE);
            if(!StringUtils.hasText(refName)) {
                logger.error(elementName + " contains empty 'ref' attribute");
            }
            RuntimeBeanReference ref = new RuntimeBeanReference(refName);
            return ref;
        } else if(hasValueAttribute) {
            TypedStringValue valueHolder = new TypedStringValue(ele.attributeValue(VALUE_ATTRIBUTE));
            return valueHolder;
        } else {
            throw new RuntimeException(elementName + " must specify a ref or value");
        }
    }
}
