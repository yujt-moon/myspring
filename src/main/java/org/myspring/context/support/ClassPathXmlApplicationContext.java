package org.myspring.context.support;

import org.myspring.context.ApplicationContext;
import org.myspring.core.io.ClassPathResource;
import org.myspring.core.io.Resource;

/**
 * @author yujiangtao
 * @date 2018/8/3 15:11
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
    public ClassPathXmlApplicationContext(String configFile) {
        super(configFile);
    }

    @Override
    protected Resource getResourceByPath(String path) {
        return new ClassPathResource(path);
    }
}
