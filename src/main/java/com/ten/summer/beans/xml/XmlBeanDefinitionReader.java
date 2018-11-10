package com.ten.summer.beans.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.ten.summer.BeanReference;
import com.ten.summer.beans.AbstractBeanDefinitionReader;
import com.ten.summer.beans.BeanDefinition;
import com.ten.summer.beans.PropertyValue;
import com.ten.summer.beans.io.ResourceLoader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

/**
 * @author yihua.huang@dianping.com
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(ResourceLoader resourceLoader) {
        super(resourceLoader);
    }

    /**
     * Extend From Top Interface, Loading Configuration File
     * <p>
     * 读取配置文件的具体实现类
     */
    @Override
    public void loadBeanDefinitions(String location) throws Exception {
        InputStream inputStream = getResourceLoader().getResource(location).getInputStream();
        doLoadBeanDefinitions(inputStream);
    }

    /**
     * Parse XML File, Use "Document" library
     * <p>
     * 通过Document包解析XML文件
     */
    protected void doLoadBeanDefinitions(InputStream inputStream) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        Document doc = docBuilder.parse(inputStream);
        // 解析bean
        registerBeanDefinitions(doc);
        inputStream.close();
    }

    /**
     * 解析{@literal <beans>}标签内容
     */
    public void registerBeanDefinitions(Document doc) {
        Element root = doc.getDocumentElement();

        parseBeanDefinitions(root);
    }

    /**
     * 解析{@literal <beans>}标签下的{@literal <bean>}属性内容
     */
    protected void parseBeanDefinitions(Element root) {
        NodeList nl = root.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                processBeanDefinition(ele);
            }
        }
    }

    /**
     * 解析{@literal <bean>}标签下的{@literal <id> <class>}内容，存储到BeanDefinition中
     */
    protected void processBeanDefinition(Element ele) {
        String name = ele.getAttribute("id");
        String className = ele.getAttribute("class");
        BeanDefinition beanDefinition = new BeanDefinition();
        processProperty(ele, beanDefinition);
        beanDefinition.setBeanClassName(className);
        getRegistry().put(name, beanDefinition);
    }

    /**
     * 解析{@literal <bean>}标签下的{@literal <property>}等内容，存储到PropertyValues中
     */
    private void processProperty(Element ele, BeanDefinition beanDefinition) {
        // <property>
        NodeList propertyNode = ele.getElementsByTagName("property");
        for (int i = 0; i < propertyNode.getLength(); i++) {
            Node node = propertyNode.item(i);
            if (node instanceof Element) {
                Element propertyEle = (Element) node;
                String name = propertyEle.getAttribute("name");
                String value = propertyEle.getAttribute("value");
                // <name> <value> => 解析属性
                if (value != null && value.length() > 0) {
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
                }
                // <name> <ref> => 解析引用
                else {
                    String ref = propertyEle.getAttribute("ref");
                    if (ref == null || ref.length() == 0) {
                        throw new IllegalArgumentException("Configuration problem: <property> element for property '"
                                + name + "' must specify a ref or value");
                    }
                    BeanReference beanReference = new BeanReference(ref);
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
                }
            }
        }
    }
}
