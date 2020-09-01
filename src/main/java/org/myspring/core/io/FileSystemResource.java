package org.myspring.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件系统的资源
 * @author yujiangtao
 * @date 2018/8/2 10:54
 */
public class FileSystemResource implements Resource {
    /**
     * 文件的路径
     */
    private String path;

    /**
     * 文件对象
     */
    private File file;

    public FileSystemResource(String path) {
        this.path = path;
        this.file = new File(path);
    }

    public FileSystemResource(File file) {
        this.path = file.getPath();
        this.file = file;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }

    @Override
    public String getDescription() {
        return this.path;
    }
}
