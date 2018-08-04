package org.myspring.context.support;

import org.myspring.core.io.FileSystemResource;
import org.myspring.core.io.Resource;

/**
 * @author yujiangtao
 * @date 2018/8/3 15:30
 */
public class FileSystemXmlApplicationContext extends AbstractApplicationContext {

    public FileSystemXmlApplicationContext(String path) {
        super(path);
    }

    @Override
    protected Resource getResourceByPath(String path) {
        return new FileSystemResource(path);
    }
}
