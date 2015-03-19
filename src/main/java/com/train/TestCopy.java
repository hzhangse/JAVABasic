package com.train;


import java.util.ArrayList;

class Student
{
	public int id;
}

public class TestCopy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Student s = new Student();
		s.id = 1;
		ArrayList a1 = new ArrayList();
		ArrayList a2 = new ArrayList();
		a1.add(s);
		a2.add(s);
		((Student)(a1.get(0))).id = 2;
		System.out.println(((Student)(a2.get(0))).id);

	}

}
