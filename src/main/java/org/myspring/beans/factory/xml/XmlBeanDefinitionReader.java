package org.myspring.beans.factory.xml;

import org.apache.log4j.Logger;
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
import org.myspring.context.annotation.ClassPathBeanDefinitionScanner;
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
     * bean 标签
     */
    public static final String BEAN_ELEMENT = "bean";

    /**
     * id 属性
     */
    public static final String ATTRIBUTE_ID = "id";

    /**
     * class 属性
     */
    public static final String ATTRIBUTE_CLASS = "class";

    /**
     * scope 属性
     */
    public static final String ATTRIBUTE_SCOPE = "scope";

    /**
     * property 标签
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

    public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";

    public static final String CONTEXT_NAMESPACE_URI = "http://www.springframework.org/schema/context";

    private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";

    private BeanDefinitionRegistry registry;

    protected final Logger logger = Logger.getLogger(getClass());

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    /**
     * 加载 <bean></bean> 的信息
     * @param resource
     */
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
                String namespaceURI = ele.getNamespaceURI();
                if(this.isDefaultNamespace(namespaceURI)) {
                    parseDefaultElement(ele); // 普通的 bean
                } else if(this.isContextNamespace(namespaceURI)) {
                    parseComponentElement(ele); // 例如 <context:component-scan>
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

    private void parseDefaultElement(Element ele) {
        // 处理bean标签
        if(BEAN_ELEMENT.equals(ele.getName())) {
            // 获取bean id
            String id = ele.attributeValue(ATTRIBUTE_ID);
            // 获取bean class
            String className = ele.attributeValue(ATTRIBUTE_CLASS);
            // 获取bean scope
            String scope = ele.attributeValue(ATTRIBUTE_SCOPE);
            BeanDefinition bd = null;
            if(StringUtils.hasLength(scope)) {
                bd = new GenericBeanDefinition(id, className, scope);
            } else {
                bd = new GenericBeanDefinition(id, className);
            }
            parseConstructorArgElements(ele, bd);
            parsePropertyElement(ele, bd);
            registry.registerBeanDefinition(id, bd);
        }
    }

    private void parseComponentElement(Element ele) {
        String basePackages = ele.attributeValue(BASE_PACKAGE_ATTRIBUTE);
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        scanner.doScan(basePackages);
    }

    private boolean isDefaultNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || BEANS_NAMESPACE_URI.equals(namespaceUri));
    }

    private boolean isContextNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || CONTEXT_NAMESPACE_URI.equals(namespaceUri));
    }

    /**
     * 解析构造参数的信息
     *     <bean id="petStore" class="org.myspring.service.v3.PetStoreService">
     *         <constructor-arg ref="accountDao" />
     *         <constructor-arg ref="itemDao" />
     *         <constructor-arg value="1" />
     *     </bean>
     * @param ele
     * @param bd
     */
    private void parseConstructorArgElements(Element ele, BeanDefinition bd) {
        Iterator iterator = ele.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
        while(iterator.hasNext()) {
            Element next = (Element) iterator.next();
            parseConstructorArgElement(next, bd);
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


    /**
     * 解析 <property name="accountDao" ref="accountDao"/>
     * @param beanElem
     * @param bd
     */
    private void parsePropertyElement(Element beanElem, BeanDefinition bd) {
        Iterator iterator = beanElem.elementIterator(PROPERTY_ELEMENT);
        while (iterator.hasNext()) {
            Element propElem = (Element) iterator.next();
            String propertyName = propElem.attributeValue(NAME_ATTRIBUTE);
            if(!StringUtils.hasLength(propertyName)) {
                logger.fatal("Tag 'property' must have a 'name' attribute");
                return;
            }

            Object val = parsePropertyValue(propElem, bd, propertyName);
            PropertyValue pv = new PropertyValue(propertyName, val);
            bd.getPropertyValues().add(pv);
        }
    }

    /**
     * 解析属性值
     * @param elem
     * @param bd
     * @param propertyName
     * @return
     */
    private Object parsePropertyValue(Element elem, BeanDefinition bd, String propertyName) {
        String elementName = (propertyName != null) ? "<property> element for property '" + propertyName + "'" :
                "<constructor-arg> element";

        boolean hasRefAttribute = (elem.attribute(REF_ATTRIBUTE) != null);
        boolean hasValueAttribute = (elem.attribute(VALUE_ATTRIBUTE) != null);

        if(hasRefAttribute) {
            String refName = elem.attributeValue(REF_ATTRIBUTE);
            if(!StringUtils.hasLength(refName)) {
                logger.error(elementName + " contains empty 'ref' attribute");
            }
            RuntimeBeanReference ref = new RuntimeBeanReference(refName);
            return ref;
        } else if(hasValueAttribute) {
            TypedStringValue valueHolder = new TypedStringValue(elem.attributeValue(VALUE_ATTRIBUTE));
            return valueHolder;
        } else {
            throw new RuntimeException(elementName + " must specify a ref or value");
        }
    }
}
