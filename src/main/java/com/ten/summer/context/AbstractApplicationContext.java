package com.ten.summer.context;

import com.ten.summer.beans.BeanPostProcessor;
import com.ten.summer.beans.factory.AbstractBeanFactory;

import java.util.List;

/**
 * Abstract ApplicationContext Loader, Commission Children Specific Class to Loader Multiple Forms File
 * <p>
 * ApplicationContext配置文件的抽象加载父类，通过<b>模板设计模式</b>将具体类型的加载方式委托给子类实现类
 *
 * @author wshten
 * @date 2018/11/10
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
    /**
     * Specific BeanFactory
     * <p>
     * 子类设置具体的BeanFactory
     */
    protected AbstractBeanFactory beanFactory;

    public AbstractApplicationContext(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Load Bean Definitions By Children Specific Method
     * <p>
     * 读取配置文件: 将具体操作委托给子类实现
     */
    public void refresh() throws Exception {
        loadBeanDefinitions(beanFactory);
        registerBeanPostProcessors(beanFactory);
        onRefresh();
    }

    /**
     * Template design pattern
     * <p>
     * 模板设计模式
     */
    protected abstract void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws Exception;

    /**
     * TODO
     */
    protected void registerBeanPostProcessors(AbstractBeanFactory beanFactory) throws Exception {
        List beanPostProcessors = beanFactory.getBeansForType(BeanPostProcessor.class);
        for (Object beanPostProcessor : beanPostProcessors) {
            beanFactory.addBeanPostProcessor((BeanPostProcessor) beanPostProcessor);
        }
    }

    /**
     * TODO
     */
    protected void onRefresh() throws Exception {
        beanFactory.preInstantiateSingletons();
    }

    /**
     * TODO
     */
    @Override
    public Object getBean(String name) throws Exception {
        return beanFactory.getBean(name);
    }
}
