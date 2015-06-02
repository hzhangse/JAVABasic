package com.train.classloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/***
 * 自定义类加载器测试类
 */
public class ClassLoadTest {

	public static Class<?> loadClass(String name){
		CustomClassLoader loader = new CustomClassLoader(Thread
				.currentThread().getContextClassLoader(), name);
		Class<?> clazz = loader.loadClass();
		return clazz;
	}
	public static void main(String[] args) {
		/** 要进行热加载的类名 **/
		String printName = Printer.class.getName();
		String callPrint = CallPrinter.class.getName();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		IPrinter printer = null;
		while (true) {
			System.out.println("输入任意字符进行热加载，直接敲回车键退出程序");
			try {
			String line = reader.readLine();
				if (line != null && line.length() > 0) {
					CustomClassLoader loader = new CustomClassLoader(Thread
							.currentThread().getContextClassLoader(), printName);
					Class<?> clazz = loader.loadClass();
					/**
					 * 被子加载器加载的类拥有被父加载器加载的类的可见性 Printer是由自定义类加载器加载的，
					 * 而它的父类IPrinter是由系统类加载器加载的， 因此IPrinter对于Printer具有可见性，
					 * 因此转型成功，并不会因为类加载器不同导致ClassCastException异常
					 */
					printer = (IPrinter) clazz.newInstance();
					/** 看看是否热加载成功了 **/
					printer.print();
					
					Class<?> icls = loadClass(ICallPrinter.class.getName());
					Class<?> acls = loadClass(AbstractCallPrinter.class.getName());
					Class<?> cls = loadClass(CallPrinter.class.getName());
					
					AbstractCallPrinter call =(AbstractCallPrinter) cls.newInstance();
					call.setPrinter(printer);
					
					
					
					System.out.println("same classload:"+AbstractCallPrinter.class.isAssignableFrom(CallPrinter.class));
					System.out.println("child-parent classload:"+AbstractCallPrinter.class.isAssignableFrom(cls));
					System.out.println("parent-child classload:"+acls.isAssignableFrom(CallPrinter.class));
					System.out.println("diff classload:"+acls.isAssignableFrom(cls));
					System.out.println("diff classload:"+icls.isAssignableFrom(cls));
				} else {
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
