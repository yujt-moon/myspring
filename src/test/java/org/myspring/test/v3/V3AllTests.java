package org.myspring.test.v3;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author yujiangtao
 * @date 2020/1/3 16:53
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        BeanDefinitionTestV3.class,
        ConstructorResolverTest.class,
        ApplicationContextTestV3.class
})
public class V3AllTests {
}
