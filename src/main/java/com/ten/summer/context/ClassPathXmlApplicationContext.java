package com.ten.summer.context;

import com.ten.summer.beans.BeanDefinition;
import com.ten.summer.beans.factory.AbstractBeanFactory;
import com.ten.summer.beans.factory.AutowireCapableBeanFactory;
import com.ten.summer.beans.io.ResourceLoader;
import com.ten.summer.beans.xml.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * @author yihua.huang@dianping.com
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

	private String configLocation;

	public ClassPathXmlApplicationContext(String configLocation) throws Exception {
		this(configLocation, new AutowireCapableBeanFactory());
	}

	public ClassPathXmlApplicationContext(String configLocation, AbstractBeanFactory beanFactory) throws Exception {
		super(beanFactory);
		this.configLocation = configLocation;
		refresh();
	}

	@Override
	protected void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws Exception {
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
		xmlBeanDefinitionReader.loadBeanDefinitions(configLocation);
		for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : xmlBeanDefinitionReader.getRegistry().entrySet()) {
			beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
		}
	}

}
