package com.ten.summer.aop;

import com.ten.summer.beans.factory.BeanFactory;

/**
 * @author yihua.huang@dianping.com
 */
public interface BeanFactoryAware {

    void setBeanFactory(BeanFactory beanFactory) throws Exception;
}
