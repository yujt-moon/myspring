package org.myspring.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.myspring.test.v1.V1AllTests;
import org.myspring.test.v2.V2AllTest;

/**
 * 所有测试用例的总测试
 * @author yujiangtao
 * @date 2018/8/22 20:03
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        V1AllTests.class,
        V2AllTest.class
})
public class AllTests {
}
