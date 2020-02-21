# Summer

> Artifical Spring Framework

## context 

#### 类层次设计

使用`模板设计模式`，将具体实现委托到子类实现

#### 对象设计

使用`对象委托机制`，在内部维护委托对象，外部通过构造函数传入委托对象

方法可拆分成对`委托对象`的步骤建模，通过对象属性维护`过程变量`

```java
public ClassPathXmlApplicationContext(String configLocation, AbstractBeanFactory beanFactory) throws Exception {
    super(beanFactory);
    this.configLocation = configLocation;
    // 使用beanFactory读取配置文件信息
    refresh();
}
```

> 别于service+被操作数据的`过程式建模`

#### 编程风格

`组合子编程`风格，context的方法流程类似`模板模式的组合`，对细粒度的对象方法进行组合

```java
public void refresh() throws Exception {
    loadBeanDefinitions(beanFactory);
    registerBeanPostProcessors(beanFactory);
    onRefresh();
}
```

### beans