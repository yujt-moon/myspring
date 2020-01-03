package org.myspring.test.v2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author yujiangtao
 * @date 2020/1/3 14:55
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CustomBooleanEditorTest.class,
        CustomNumberTest.class,
        TypeConverterTest.class,
        BeanDefinitionValueResolverTest.class,
        BeanDefinitionTestV2.class,
        ApplicationContextTestV2.class
})
public class V2AllTests {
}
