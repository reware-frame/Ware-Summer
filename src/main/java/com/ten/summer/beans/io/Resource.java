package com.ten.summer.beans.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * It can get a InputStream From File(XML)
 *
 * @author wshten
 * @date 2018/11/10
 */
public interface Resource {
    /**
     * Get InputStream From XML-File
     */
    InputStream getInputStream() throws IOException;
}
