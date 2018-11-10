package com.ten.summer.beans.factory;

import com.ten.summer.BeanReference;
import com.ten.summer.aop.BeanFactoryAware;
import com.ten.summer.beans.BeanDefinition;
import com.ten.summer.beans.PropertyValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * This BeanFactory can auto wire the referance bean, it check {@literal <ref>} label to load beans
 * <p>
 * 可自动装配引用Bean的Factory，扫描{@literal <ref>}标签，通过PropertyValue属性实例化引用的Bean
 *
 * @author wshten
 * @date 2018/11/10
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    /**
     * Autowire Reference Bean, Scilicet Lable Content {@literal <ref></ref>}, From PropertyValue
     * <p>
     * 自动装配所引用的Bean，即{@literal <ref></ref>}标签的内容，从PropertyValue属性中获取引用信息
     *
     * @param bean Bean Object
     * @param mbd  Bean Definition -> To get PropertyValues
     */
    @Override
    protected void applyPropertyValues(Object bean, BeanDefinition mbd) throws Exception {
        // 若装配的Bean为Factory，
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
        for (PropertyValue propertyValue : mbd.getPropertyValues().getPropertyValues()) {
            Object value = propertyValue.getValue();
            // PropertyValue is BeanReference
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                // 加载引用Bean的实例
                value = getBean(beanReference.getName());
            }

            // FIXME IMPORTANT -> Dynamic Get Declare Method To Setter Property
            // 将加载好的引用Bean，获取以声明的Method方法(setValue)，设置到Bean的属性中
            try {
                Method declaredMethod = bean.getClass().getDeclaredMethod(
                        // setter method, such as "value = object"
                        "set"
                                // Upper Case Name : setO
                                + propertyValue.getName().substring(0, 1).toUpperCase()
                                // Get Other Name : setObject
                                + propertyValue.getName().substring(1),
                        // get declared method from this class
                        value.getClass());

                // open safety check -> improve performance by 20 times
                declaredMethod.setAccessible(true);

                // 动态调用Setter方法，将引用Bean设置到Bean属性中
                declaredMethod.invoke(bean, value);

            }
            // 若动态Setter方法失败，则获取Field域变量，直接设置值
            catch (NoSuchMethodException e) {
                Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
                declaredField.setAccessible(true);
                declaredField.set(bean, value);
            }
        }
    }
}
