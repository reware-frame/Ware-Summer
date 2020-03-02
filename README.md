# Summer

> Artifical Tiny Spring Framework. Spring IOC/AOP學習筆記

## Github

[Ware-Summer](https://github.com/kevinten10/Ware-Summer)

--------------------------------------------------------

## context包

### **类层次设计**

使用`模板设计模式`，将具体实现委托到子类实现

### **对象设计**

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

### **编程风格**

`组合子编程`风格，context的方法流程类似`模板模式的组合`，对细粒度的对象方法进行组合

```java
public void refresh() throws Exception {
    loadBeanDefinitions(beanFactory);
    registerBeanPostProcessors(beanFactory);
    onRefresh();
}
```

--------------------------------------------------------

## beans

### **方法风格**

直链路式的调用链，方法单一职责，向下委托

```java
public void fun1(){
    fun2();
}

protected void fun2(){
    func3();
}

private void fun3(){
    func4();
}

private void fun4(){
    throw ...;
}44
```

### **类结构风格**

> 树形类结构

以单一职责，单一资源进行module的建模。

使用接口->抽象的module族，通过组合使用。

--------------------------------------------------------

## aop

### **类结构**

> 扁平类结构

接口单一职责，实现类实现多个接口。

属性提供接口提供要使用的对象，功能接口消费对象。

### **对象结构**

工厂创建bean时，将this工厂本身注册到bean，bean的其他方法可使用工厂