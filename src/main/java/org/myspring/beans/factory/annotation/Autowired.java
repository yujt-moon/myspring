package org.myspring.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @author yujiangtao
 * @date 2020/8/26 下午2:41
 */
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

    boolean required() default true;
}
