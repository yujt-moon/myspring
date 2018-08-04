package org.myspring.beans.factory.support;

import org.myspring.beans.BeanDefinition;

/**
 * @author yujiangtao
 * @date 2018/8/2 14:02
 */
public class GenericBeanDefinition implements BeanDefinition {

    private String id;

    private String beanClassName;

    // TODO 暂不实现
    private String scope = "";

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getBeanClassName() {
        return this.beanClassName;
    }

    @Override
    public boolean isSingleton() {
        return SCOPE_DEFAULT.equals(scope) || SCOPE_SINGLETON.equals(scope);
    }

    @Override
    public boolean isPrototype() {
        return SCOPE_PROTOTYPE.equals(scope);
    }

    @Override
    public String getScope() {
        return this.scope;
    }
}
