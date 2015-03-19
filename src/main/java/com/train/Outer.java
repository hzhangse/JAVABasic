package com.train;


public class Outer {
	int outer_x = 100;

	class Inner {
		public int y = 10;
		private int z = 9;
		int m = 5;

		public void display() {
			System.out.println("display outer_x:" + outer_x);
		}

		private void display2() {
			System.out.println("display outer_x:" + outer_x);
		}
	}

	void test() {
		Inner inner = new Inner();
		inner.display();
		inner.display2();
		// System.out.println("Inner y:" + y);//���ܷ����ڲ��ڱ���
		System.out.println("Inner y:" + inner.y);// ���Է���
		System.out.println("Inner z:" + inner.z);// ���Է���
		System.out.println("Inner m:" + inner.m);// ���Է���
		InnerTwo innerTwo = new InnerTwo();
		innerTwo.show();
	}

	class InnerTwo {
		Inner innerx = new Inner();

		public void show() {
			// System.out.println(y);//���ɷ���Innter��y��Ա
			// System.out.println(Inner.y);//����ֱ�ӷ���Inner���κγ�Ա�ͷ���
			innerx.display();// ���Է���
			innerx.display2();// ���Է���
			System.out.println(innerx.y);// ���Է���
			System.out.println(innerx.z);// ���Է���
			System.out.println(innerx.m);// ���Է���
		}
	}

	public static void main(String args[]) {
		Outer outer = new Outer();
		outer.test();
	}
}
