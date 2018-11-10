package com.ten.summer.context;

import com.ten.summer.beans.BeanDefinition;
import com.ten.summer.beans.factory.AbstractBeanFactory;
import com.ten.summer.beans.factory.AutowireCapableBeanFactory;
import com.ten.summer.beans.io.ResourceLoader;
import com.ten.summer.beans.xml.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * Load ApplicationContext.XML File, Through "configLocation" path
 * <p>
 * 通过传入的类路径加载ApplicationContext.XML文件，将配置信息导入容器
 *
 * @author wshten
 * @date 2018/11/10
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
    /**
     * ApplicationContext.XML Path
     * <p>
     * 配置文件类路径
     */
    private String configLocation;

    /**
     * Application should invoke this method, to afferent the config path
     * <p>
     * 用户需要调用此方法，将类路径传入构造函数
     */
    public ClassPathXmlApplicationContext(String configLocation) throws Exception {
        this(configLocation, new AutowireCapableBeanFactory());
    }

    /**
     * Use Default BeanFactory or Customize BeanFactory to Super()
     * <p>
     * 传入BeanFactory到父类构造器，使用默认或自定义的Abstract实现类
     *
     * @param configLocation 配置文件类路径
     * @param beanFactory    Bean装配工厂，传入Abstract实现类
     */
    public ClassPathXmlApplicationContext(String configLocation, AbstractBeanFactory beanFactory) throws Exception {
        super(beanFactory);
        this.configLocation = configLocation;
        // 使用beanFactory读取配置文件信息
        refresh();
    }

    /**
     * Load Bean Definitions From XML File
     * <p>
     * 从类路径中读取XML配置文件信息
     *
     * @param beanFactory 通过BeanFactory加载配置文件的Bean
     */
    @Override
    protected void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws Exception {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
        xmlBeanDefinitionReader.loadBeanDefinitions(configLocation);
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : xmlBeanDefinitionReader.getRegistry().entrySet()) {
            beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
        }
    }

}
