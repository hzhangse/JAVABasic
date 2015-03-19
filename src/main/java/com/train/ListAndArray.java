package com.train;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ListAndArray {
	private static final int count = 60000;
	private static final String base = "hello world";
	static ArrayList strArr = new ArrayList();
	static LinkedList list = new LinkedList();

	public static void listAddTest() {
		long begin, end;
		begin = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			list.add(base);
		}
		end = System.currentTimeMillis();
		System.out.println((end - begin)
				+ " millis has elapsed when list add String. ");
	}

	public static void listVisitTest() {
		long begin, end;
		String val;
		begin = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			val = list.get(i).toString();
		}
		end = System.currentTimeMillis();
		System.out.println((end - begin)
				+ " millis has elapsed when list visit items. ");
	}

	public static void ArrayAddTest() {
		long begin, end;
		begin = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			strArr.add(base);
		}
		end = System.currentTimeMillis();
		System.out.println((end - begin)
				+ " millis has elapsed when array add String. ");
	}

	public static void ArrayVisitTest() {
		long begin, end;
		String val;
		begin = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			val = strArr.get(i).toString();
		}
		end = System.currentTimeMillis();
		System.out.println((end - begin)
				+ " millis has elapsed when list visit items. ");
	}

	public static void main(String[] args) {
		listAddTest();
		listVisitTest();
		ArrayAddTest();
		ArrayVisitTest();
	}

}
