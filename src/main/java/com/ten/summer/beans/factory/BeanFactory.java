package com.ten.summer.beans.factory;

/**
 * Bean IOC Container
 * <p>
 * Bean的IOC容器
 *
 * @author wshten
 * @date 2018/11/10
 */
public interface BeanFactory {
    /**
     * Get Bean Object From BeanFactory By BeanName, Default Singleton Pattern
     * <p>
     * 根据Bean的名称从IOC容器中获取Bean实例，默认使用单例模式
     */
    Object getBean(String name) throws Exception;
}
