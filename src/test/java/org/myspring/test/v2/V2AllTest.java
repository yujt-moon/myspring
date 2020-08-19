package org.myspring.test.v2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.myspring.beans.TypeConverter;

/**
 * @author yujiangtao
 * @date 2020/8/19 下午3:31
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        BeanDefinitionTestV2.class,
        BeanDefinitionValueResolverTest.class,
        CustomNumberEditorTest.class,
        CustomBooleanEditorTest.class,
        TypeConverterTest.class,
        ApplicationContextTestV2.class
})
public class V2AllTest {
}
