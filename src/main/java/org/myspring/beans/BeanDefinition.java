package org.myspring.beans;

/**
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

    public static final String SCOPE_DEFAULT = "";

    String getId();

    String getBeanClassName();

    boolean isSingleton();

    boolean isPrototype();

    String getScope();
}
