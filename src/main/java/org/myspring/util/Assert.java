package org.myspring.util;

/**
 * 断言类（abstract无法被实例化，不会产生new Assert().notNull()的调用）
 * @author yujiangtao
 * @date 2018/6/16 18:44
 */
public abstract class Assert {

    /**
     * 判断对象是否为空
     * @param object
     * @param message 对象为空时，抛出异常时所带的异常信息
     */
    public static void notNull(Object object, String message) {
        if(object == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
