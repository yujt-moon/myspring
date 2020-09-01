package org.myspring.test.v4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author yujiangtao
 * @date 2020/8/27 下午9:42
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        PackageResourceLoaderTest.class,
        ClassReaderTest.class,
        MetadataReaderTest.class,
        ClassPathBeanDefinitionScannerTest.class,
        XmlBeanDefinitionReaderTest.class,
        DependencyDescriptorTest.class,
        InjectionMetadataTest.class,
        ApplicationContextTestV4.class
})
public class V4AllTests {
}
