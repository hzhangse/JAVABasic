package com.train;



public class TryCatchDemo {
	public static void main(String[] args) {
		int[] ints = new int[] { 1, 2, 3, 4 };
		System.out.println("before");
		try {
			System.out.println(ints[4]);
			System.out.println("after");// �����쳣�Ժ󣬺���Ĵ��벻�ܱ�ִ��
		} catch (IndexOutOfBoundsException e) {
			System.out.println("array out of range");
		}
		System.out.println("at last");
	}

}
