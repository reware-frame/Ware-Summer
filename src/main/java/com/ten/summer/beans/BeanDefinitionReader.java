package com.ten.summer.beans;

/**
 * ApplicationContext Configuration File Reader 
 *
 * 配置文件读取类顶层接口
 *
 * @author wshten
 * @date 2018/11/10
 */
public interface BeanDefinitionReader {

    void loadBeanDefinitions(String location) throws Exception;
}
