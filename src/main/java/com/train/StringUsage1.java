package com.train;


public class StringUsage1 {
	public static void main(String[] args) {
		String a = "abc";
		// String a = new String("abc");
		String b = "abc";
		final String c = "a";
		String d = c + "bc";
		String e = "a" + "bc";
		// String b = new String("abc");
		if (a == b) {
			System.out.println("a == b");
		}
		if (a.equals(b) == true) {
			System.out.println("a equals b");
		}
		if (d == a) {
			System.out.println("a == d");
		}
		if (e == a) {
			System.out.println("a == e");
		}
	}
}
