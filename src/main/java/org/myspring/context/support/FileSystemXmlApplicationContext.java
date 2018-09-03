package org.myspring.context.support;

import org.myspring.core.io.FileSystemResource;
import org.myspring.core.io.Resource;

/**
 * 通过文件系统中的配置文件获取应用上下文
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
