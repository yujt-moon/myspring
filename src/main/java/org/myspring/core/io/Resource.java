package org.myspring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 代表资源的接口
 * @author yujiangtao
 * @date 2018/8/2 10:40
 */
public interface Resource {

    /**
     * 获取输入流（哪一种设计模式：模板方法？）
     * @return
     * @throws IOException
     */
    InputStream getInputStream() throws IOException;

    /**
     * 获取资源的描述
     * @return
     */
    String getDescription();
}
