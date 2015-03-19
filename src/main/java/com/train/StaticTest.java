package com.train;


public class StaticTest {
	private static String name = "javaJohn";
	private String id = "X001";

	static class Person {
		private String address = "swjtu,chenDu,China";
		public String mail = "josserchai@yahoo.com";// �ڲ��๫�г�Ա

		public void display() {
			// System.out.println(id);//����ֱ�ӷ����ⲿ��ķǾ�̬��Ա
			System.out.println(name);// ֻ��ֱ�ӷ����ⲿ��ľ�̬��Ա
			System.out.println("Inner" + address); // ���ʱ��ڲ����Ա
		}
	}

	public void printInfo() {
		Person person = new Person();
		person.display();
		// System.out.println(mail);//���ɷ���
		// System.out.println(address);//���ɷ���
		System.out.println(person.address);// ���Է����ڲ����˽�г�Ա
		System.out.println(person.mail);// ���Է����ڲ���Ĺ��г�Ա
	}

	public static void main(String[] args) {
		StaticTest staticTest = new StaticTest();
		staticTest.printInfo();
	}
}
