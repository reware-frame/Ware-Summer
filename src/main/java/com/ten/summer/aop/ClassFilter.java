package com.ten.summer.aop;

/**
 * @author yihua.huang@dianping.com
 */
public interface ClassFilter {

    boolean matches(Class targetClass);
}
