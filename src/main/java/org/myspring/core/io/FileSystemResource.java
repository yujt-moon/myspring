package org.myspring.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author yujiangtao
 * @date 2018/8/2 10:54
 */
public class FileSystemResource implements Resource {
    private String path;

    private File file;

    public FileSystemResource(String path) {
        this.path = path;
        this.file = new File(path);
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
