package org.myspring.stereotype;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

    /**
     * The value my indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case if an autodetected component.
     * @return the suggested component name, if any
     */
    String value() default "";
}
