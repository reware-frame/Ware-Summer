package com.ten.summer.beans.factory;


import com.ten.summer.beans.BeanDefinition;
import com.ten.summer.beans.BeanPostProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Abstract BeanFactory For IOC Container
 * <p>
 * 默认IOC容器
 *
 * @author wshten
 * @date 2018/11/10
 */
public abstract class AbstractBeanFactory implements BeanFactory {
    /**
     * Bean Definition Map : Use ConcurrentHashMap
     * <p>
     * Bean容器，保存Bean数据到Map中
     *
     * @param key Bean Name
     * @param val BeanDefinition
     */
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    /**
     * Bean Names Container, Which is Registered
     * <p>
     * 已注册的Bean的集合
     */
    private final List<String> beanDefinitionNames = new ArrayList<>();
    /**
     * Bean Customize Proceesor Collections
     * <p>
     * Bean的自定义处理器集合
     */
    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    /**
     * Get Bean From IOC Container, If Bean is defined But not Init, Try to Create object By Proxy
     * <p>
     * 从IOC容器中(Map)获取BeanDefinition实例，若Bean定义但还未实例化对象，那么使用<b>动态代理模式</b>创建实例
     *
     * @throws IllegalArgumentException Bean在IOC容器中未定义
     * @throws Exception                Bean已定义但实例化失败
     */
    @Override
    public Object getBean(String name) throws Exception {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if (beanDefinition == null) {
            throw new IllegalArgumentException("No bean named " + name + " is defined");
        }
        Object bean = beanDefinition.getBean();
        // 若Bean还未实例化
        if (bean == null) {
            // 动态代理 -> 创建Bean实例
            bean = doCreateBean(beanDefinition);
            // Bean初始化操作 -> 调用PostProcessor进行操作
            bean = initializeBean(bean, name);
            // 将Bean注册到IOC容器中
            beanDefinition.setBean(bean);
        }
        return bean;
    }

    /**
     * Customize BeanPostProcessor
     * <p>
     * Bean初始化操作 -> 调用PostProcessor进行操作
     */
    protected Object initializeBean(Object bean, String name) throws Exception {
        // @Before
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessBeforeInitialization(bean, name);
        }

        // TODO :call initialize method

        // @After
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessAfterInitialization(bean, name);
        }
        return bean;
    }

    /**
     * Dynamic Proxy Design Pattern
     * <p>
     * 动态代理加载Bean实例
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition) throws Exception {
        return beanDefinition.getBeanClass().newInstance();
    }

    /**
     * Register Bean to IOC Container
     * <p>
     * 将Bean信息注册到IOC容器中，在加载配置文件时调用
     */
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) throws Exception {
        beanDefinitionMap.put(name, beanDefinition);
        beanDefinitionNames.add(name);
    }

    /**
     * Prepared Load Bean Singletons Object
     * <p>
     * 将Bean从配置文件读入后，预先将Bean的单实例加载完毕
     */
    public void preInstantiateSingletons() throws Exception {
        for (String beanName : this.beanDefinitionNames) {
            // 若未实例化，则进行实例化
            getBean(beanName);
        }
    }

    /**
     * 创建Bean实例
     */
    protected Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
        Object bean = createBeanInstance(beanDefinition);
        beanDefinition.setBean(bean);
        applyPropertyValues(bean, beanDefinition);
        return bean;
    }

    /**
     * TODO
     */
    protected abstract void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception;

    /**
     * Register Customize Bean Post Processor
     * <p>
     * 注册自定义的Bean处理器
     */
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) throws Exception {
        this.beanPostProcessors.add(beanPostProcessor);
    }

    /**
     * To Get All the Beans which is Assign From The Type
     * <p>
     * 获取指定类型的所有Beans，包括超类，接口等关系
     */
    public List getBeansForType(Class type) throws Exception {
        List beans = new ArrayList<Object>();
        for (String beanDefinitionName : beanDefinitionNames) {
            // 判断是否类型相同/超类/接口
            if (type.isAssignableFrom(beanDefinitionMap.get(beanDefinitionName).getBeanClass())) {
                beans.add(getBean(beanDefinitionName));
            }
        }
        return beans;
    }

}
