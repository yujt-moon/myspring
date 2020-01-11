package org.myspring.core.io.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myspring.core.io.FileSystemResource;
import org.myspring.core.io.Resource;
import org.myspring.util.Assert;
import org.myspring.util.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author yujiangtao
 * @date 2020/1/5 16:50
 */
public class PackageResourceLoader {

    private static final Log logger = LogFactory.getLog(PackageResourceLoader.class);

    private final ClassLoader classLoader;

    public PackageResourceLoader() {
        this.classLoader = ClassUtils.getDefaultClassLoader();
    }

    public PackageResourceLoader(ClassLoader classLoader) {
        Assert.notNull(classLoader, "ResourceLoader must not be null");
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    /**
     * 获取指定包下面的资源信息
     * @param basePackage
     * @return
     * @throws IOException
     */
    public Resource[] getResources(String basePackage) throws IOException {
        Assert.notNull(basePackage, "basePackage must not be null");
        String location = ClassUtils.convertClassNameToResourcePath(basePackage);
        ClassLoader cl = getClassLoader();
        URL url = cl.getResource(location);
        if(url == null) {
            throw new IOException("该文件路径[" + basePackage + "]不存在");
        }
        File rootDir = new File(url.getFile());

        Set<File> matchingFiles = retrieveMatchingFiles(rootDir);
        Resource[] result = new Resource[matchingFiles.size()];
        int i = 0;
        for(File file : matchingFiles) {
            result[i++] = new FileSystemResource(file);
        }
        return result;
    }

    protected Set<File> retrieveMatchingFiles(File rootDir) throws IOException {
        if(!rootDir.exists()) {
            // Silently skip non-existing directories.
            if(logger.isDebugEnabled()) {
                logger.debug("Skipping [" + rootDir.getAbsolutePath() +
                        "] because it does not donote a directory");
            }
            return Collections.emptySet();
        }
        if(!rootDir.isDirectory()) {
            // Complain louder if it exists but is no directory
            if(logger.isWarnEnabled()) {
                logger.warn("Skipping [" + rootDir.getAbsolutePath() +
                        "] because it does not a directory");
            }
            return Collections.emptySet();
        }
        if(!rootDir.canRead()) {
            if(logger.isWarnEnabled()) {
                logger.warn("can not search for matching files underneath directory [" +
                        rootDir.getAbsolutePath() + "] because the application is not allowed to read the directory");
                return Collections.emptySet();
            }
        }
        Set<File> result = new LinkedHashSet<>(8);
        doRetrieveMatchingFiles(rootDir, result);
        return result;
    }

    /**
     * 递归的获取目录下的文件
     * @param dir
     * @param result
     * @throws IOException
     */
    protected void doRetrieveMatchingFiles(File dir, Set<File> result) throws IOException {
        File[] dirContents = dir.listFiles();
        if(dirContents == null) {
            if(logger.isWarnEnabled()) {
                logger.warn("Could not retrieve contents of directory [" +
                        dir.getAbsolutePath() + "]");
            }
            return;
        }
        for(File content : dirContents) {
            if(content.isDirectory()) {
                if(logger.isDebugEnabled()) {
                    logger.debug("Skipping sudirectory [" + content.getAbsolutePath() +
                            "because the application is not allowed to read the directory");
                } else {
                    doRetrieveMatchingFiles(content, result);
                }
            } else {
                result.add(content);
            }
        }
    }
}
