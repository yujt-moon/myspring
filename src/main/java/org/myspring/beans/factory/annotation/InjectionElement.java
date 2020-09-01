package org.myspring.beans.factory.annotation;

import org.myspring.beans.factory.config.AutowiredCapableBeanFactory;

import java.lang.reflect.Member;

/**
 * @author yujiangtao
 * @date 2020/8/31 下午8:02
 */
public abstract class InjectionElement {

    protected Member member;

    protected AutowiredCapableBeanFactory factory;

    public InjectionElement(Member member, AutowiredCapableBeanFactory factory) {
        this.member = member;
        this.factory = factory;
    }

    public abstract void inject(Object target);
}
