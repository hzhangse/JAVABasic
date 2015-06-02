package com.train.classloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/***
 * �Զ����������������
 */
public class ClassLoadTest {

	public static Class<?> loadClass(String name){
		CustomClassLoader loader = new CustomClassLoader(Thread
				.currentThread().getContextClassLoader(), name);
		Class<?> clazz = loader.loadClass();
		return clazz;
	}
	public static void main(String[] args) {
		/** Ҫ�����ȼ��ص����� **/
		String printName = Printer.class.getName();
		String callPrint = CallPrinter.class.getName();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		IPrinter printer = null;
		while (true) {
			System.out.println("���������ַ������ȼ��أ�ֱ���ûس����˳�����");
			try {
			String line = reader.readLine();
				if (line != null && line.length() > 0) {
					CustomClassLoader loader = new CustomClassLoader(Thread
							.currentThread().getContextClassLoader(), printName);
					Class<?> clazz = loader.loadClass();
					/**
					 * ���Ӽ��������ص���ӵ�б������������ص���Ŀɼ��� Printer�����Զ�������������صģ�
					 * �����ĸ���IPrinter����ϵͳ����������صģ� ���IPrinter����Printer���пɼ��ԣ�
					 * ���ת�ͳɹ�����������Ϊ���������ͬ����ClassCastException�쳣
					 */
					printer = (IPrinter) clazz.newInstance();
					/** �����Ƿ��ȼ��سɹ��� **/
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
