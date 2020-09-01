package org.myspring.stereotype;

import java.lang.annotation.*;

/**
 * @author yujiangtao
 * @date 2020/8/26 下午2:35
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {

    String value() default "";
}
