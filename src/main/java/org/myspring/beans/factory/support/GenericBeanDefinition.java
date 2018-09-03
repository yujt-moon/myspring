package org.myspring.beans.factory.support;

import org.myspring.beans.BeanDefinition;

/**
 * 普通的beanDefinition
 * @author yujiangtao
 * @date 2018/8/2 14:02
 */
public class GenericBeanDefinition implements BeanDefinition {

    /**
     * id对应于xml中的<bean id="xxxx"></bean>
     */
    private String id;

    /**
     * beanClassName对应于xml中的<bean class="com.xxx.xxx"></bean>
     */
    private String beanClassName;

    /**
     * scope对应于xml中的<bean scope="singleton|prototype"></>
     */
    private String scope;

    public GenericBeanDefinition(String id, String beanClassName) {
        this(id, beanClassName, "");
    }

    public GenericBeanDefinition(String id, String beanClassName, String scope) {
        this.id = id;
        this.beanClassName = beanClassName;
        this.scope = scope;
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
