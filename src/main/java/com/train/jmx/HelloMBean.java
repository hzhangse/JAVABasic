package com.train.jmx;

/**
 * Server端程序范例： 1. MBean接口 这是一个应用程序将要向外暴露的接口，在该接口中需要定义要公布的所有函数。
 * 其中，如果存在一对对应的get方法和set方法，那么就默认有一个属性，且熟悉的名字就是get方法名字中get后面的部分。
 * 包含在MBean中方法都将是可以被管理的。MBean起名是有规范的，就是原类名后加上MBean字样。
 * 
 * @author ryan
 *
 */
public interface HelloMBean {
	public String getName();

	public void setName(String name);

	public void printHello();

	public void printHello(String whoName);
}
