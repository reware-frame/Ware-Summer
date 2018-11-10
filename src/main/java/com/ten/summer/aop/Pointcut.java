package com.ten.summer.aop;

/**
 * @author yihua.huang@dianping.com
 */
public interface Pointcut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();

}
