package org.myspring.test.v3;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author yujiangtao
 * @date 2020/8/20 下午4:47
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ConstructorResolverTest.class,
        BeanDefinitionTestV3.class,
        ApplicationContextTestV3.class
})
public class V3AllTests {

}
