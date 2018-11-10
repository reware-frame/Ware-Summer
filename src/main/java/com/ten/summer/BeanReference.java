package com.ten.summer;

/**
 * This Specify the reference relationship which is {@literal <ref>}
 * <p>
 * Bean之间的引用关系，即{@literal <ref>}标签的内容
 *
 * @author wshten
 * @date 2018/11/10
 */
public class BeanReference {
    
    private String name;

    private Object bean;

    public BeanReference(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
