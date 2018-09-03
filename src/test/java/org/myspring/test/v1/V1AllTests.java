package org.myspring.test.v1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * v1版本的所有单元测试
 * @author yujiangtao
 * @date 2018/8/3 15:35
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationContextTest.class,
        BeanFactoryTest.class,
        BeanScopeTest.class,
        ResourceTest.class
})
public class V1AllTests {
}
