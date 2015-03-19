package com.train;


class C {
	static A a;
}

class A {
	B b;

	public A(B b) {
		this.b = b;
	}

	@Override
	public void finalize() {
		System.out.println("A finalize");
		C.a = this;
	}
}

class B {
	String name;
	int age;

	public B(String name, int age) {
		this.name = name;
		this.age = age;
	}

	@Override
	public void finalize() {
		System.out.println("B finalize");
	}

	@Override
	public String toString() {
		return name + " is " + age;
	}
}

public class TestFinalize {
	public static void main(String[] args) {
		A a = new A(new B("allen", 20));
		a = null;

		System.gc();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		System.out.println(C.a.b);
	}
}
