package org.myspring.core.io;

import org.myspring.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author yujiangtao
 * @date 2018/8/2 10:43
 */
public class ClassPathResource implements Resource {

    private String path;

    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, (ClassLoader) null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        this.path = path;
        this.classLoader = classLoader == null ? ClassUtils.getDefaultClassLoader() : classLoader;
    }

    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        InputStream is = this.classLoader.getResourceAsStream(path);
        if(is == null) {
            throw new FileNotFoundException(this.path + "can't be opened!");
        }
        return is;
    }

    @Override
    public String getDescription() {
        return this.path;
    }
}
