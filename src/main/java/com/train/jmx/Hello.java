package com.train.jmx;

/**
 * 2. 被管理的类 被管理的类需要实现相应的MBean接口，通过MBean接口中的方法来被管理。
 * 
 * @author ryan
 *
 */
public class Hello implements HelloMBean {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void printHello() {
		System.out.println("Hello World, " + name);
	}

	public void printHello(String whoName) {
		System.out.println("Hello , " + whoName);
	}
}