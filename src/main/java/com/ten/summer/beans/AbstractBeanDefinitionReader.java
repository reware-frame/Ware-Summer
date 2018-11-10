package com.ten.summer.beans;

import com.ten.summer.beans.io.ResourceLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract ApplicationContext Configuration File Reader
 * <p>
 * 从配置中读取BeanDefinition
 *
 * @author wshten
 * @date 2018/11/10
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private Map<String, BeanDefinition> registry;

    private ResourceLoader resourceLoader;

    protected AbstractBeanDefinitionReader(ResourceLoader resourceLoader) {
        this.registry = new HashMap<>();
        this.resourceLoader = resourceLoader;
    }

    public Map<String, BeanDefinition> getRegistry() {
        return registry;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
