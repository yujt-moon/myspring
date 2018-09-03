package org.myspring.core.io;

import org.myspring.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 通过classpath读取的数据源
 * @author yujiangtao
 * @date 2018/8/2 10:43
 */
public class ClassPathResource implements Resource {

    /**
     * classpath下的路径
     */
    private String path;

    /**
     * 类加载器
     */
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
