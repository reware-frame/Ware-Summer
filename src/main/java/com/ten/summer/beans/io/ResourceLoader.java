package com.ten.summer.beans.io;

import java.net.URL;

/**
 * Load a Resource Object
 *
 * @author wshten
 * @date 2018/11/10
 */
public class ResourceLoader {
    /**
     * Get a Resource Object, It can get a InputStream From File
     * Use Class.ClassLoader to load resource
     *
     * @param location Resource File Location
     */
    public Resource getResource(String location) {
        URL resource = this.getClass().getClassLoader().getResource(location);
        return new UrlResource(resource);
    }
}
