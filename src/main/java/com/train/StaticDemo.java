package com.train;


class StaticClass {
	static int a = 42;
	static int b = 99;
	// int c= 0;

	static void callme() {
		// c++;
		System.out.println("a = " + a);
	}
}

public class StaticDemo {
	public static void main(String[] args) {
		StaticClass.callme();
		System.out.println("b = " + StaticClass.b);
	}
}
