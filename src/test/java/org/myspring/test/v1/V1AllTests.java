package org.myspring.test.v1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author yujiangtao
 * @date 2018/8/3 15:35
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationContextTest.class,
        BeanFactoryTest.class,
        ResourceTest.class
})
public class V1AllTests {
}
