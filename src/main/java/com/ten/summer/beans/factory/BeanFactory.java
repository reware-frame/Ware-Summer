package com.ten.summer.beans.factory;

/**
 * bean的容器
 * @author yihua.huang@dianping.com
 */
public interface BeanFactory {

    Object getBean(String name) throws Exception;

}
