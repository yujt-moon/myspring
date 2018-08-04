package org.myspring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author yujiangtao
 * @date 2018/8/2 10:40
 */
public interface Resource {
    InputStream getInputStream() throws IOException;
    String getDescription();
}
