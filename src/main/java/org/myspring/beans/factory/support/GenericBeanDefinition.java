package org.myspring.beans.factory.support;

import org.myspring.beans.BeanDefinition;
import org.myspring.beans.ConstructorArgument;
import org.myspring.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 存放 <property name="accountDao" ref="accountDao"/> 标签信息
     */
    private List<PropertyValue> propertyValues = new ArrayList<>();

    /**
     * 存放多个 <constructor-arg ref="itemDao" />
     *         <constructor-arg value="1" /> 标签
     */
    private ConstructorArgument constructorArgument = new ConstructorArgument();

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

    @Override
    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    @Override
    public ConstructorArgument getConstructorArgument() {
        return this.constructorArgument;
    }

    @Override
    public boolean hasConstructArgumentValues() {
        return !this.constructorArgument.isEmpty();
    }
}
