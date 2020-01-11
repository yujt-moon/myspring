package org.myspring.context.support;

import org.myspring.beans.factory.annotation.AutowiredAnnotationProcessor;
import org.myspring.beans.factory.config.ConfigurableBeanFactory;
import org.myspring.beans.factory.support.DefaultBeanFactory;
import org.myspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.myspring.context.ApplicationContext;
import org.myspring.core.io.Resource;

/**
 * 抽象的应用上下文
 * @author yujiangtao
 * @date 2018/8/3 15:13
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    private DefaultBeanFactory factory = null;

    private ClassLoader beanClassLoader;

    public AbstractApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = this.getResourceByPath(configFile);
        reader.loadBeanDefinitions(resource);
        this.setBeanClassLoader(factory.getBeanClassLoader());
        this.registerBeanPostProcessor(factory);
    }

    public Object getBean(String beanId) {
        return this.factory.getBean(beanId);
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    protected abstract Resource getResourceByPath(String path);

    protected void registerBeanPostProcessor(ConfigurableBeanFactory beanFactory) {
        {
            AutowiredAnnotationProcessor postProcessor = new AutowiredAnnotationProcessor();
            postProcessor.setBeanFactory(beanFactory);
            beanFactory.addBeanPostProcessor(postProcessor);
        }
    }
}
