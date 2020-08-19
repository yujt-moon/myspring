package org.myspring.beans;

import java.util.List;

/**
 * bean定义类
 * @author yujiangtao
 * @date 2018/8/2 11:20
 */
public interface BeanDefinition {

    /**
     * 单例
     */
    public static final String SCOPE_SINGLETON = "singleton";

    /**
     * 原型
     */
    public static final String SCOPE_PROTOTYPE = "prototype";

    /**
     * 默认的为空，表示单例
     */
    public static final String SCOPE_DEFAULT = "";

    /**
     * 获取bean定义的id
     * @return
     */
    String getId();

    /**
     * 获取bean的类名
     * @return
     */
    String getBeanClassName();

    /**
     * 是否单例
     * @return
     */
    boolean isSingleton();

    /**
     * 是否原型
     * @return
     */
    boolean isPrototype();

    /**
     * 获取beanDefinition的范围（单例、原型）
     * @return
     */
    String getScope();

    /**
     * 存放 <property name="accountDao" ref="accountDao"/> 标签信息
     * @return
     */
    List<PropertyValue> getPropertyValues();
}
