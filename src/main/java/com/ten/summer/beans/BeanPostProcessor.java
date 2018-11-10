package com.ten.summer.beans;

/**
 * Customize Bean Post Processor, This will invoked in AbstractBeanFactory's Loading Process
 * <p>
 * 自定义Bean处理器，将会在BeanFactory加载Bean的初始化过程中调用
 *
 * @author wshten
 * @date 2018/11/10
 */
public interface BeanPostProcessor {
    /**
     * Before init
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception;

    /**
     * After init
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws Exception;

}