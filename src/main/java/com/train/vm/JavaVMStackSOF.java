package com.train.vm;
/**
 * * VM Args：-Xss128k 
 * .使用-Xss参数削减栈内存容量。结果：抛出SOF异常时的堆栈深度相应缩小。
 */
public class JavaVMStackSOF {

	private int stackLength = 1;

	public void stackLeak() {
		stackLength++;
		System.out.println("stackLength:"+stackLength);
		stackLeak();
		
	}

	public static void main(String[] args) throws Throwable {
		JavaVMStackSOF oom = new JavaVMStackSOF();
		try {
			oom.stackLeak();
		} catch (Throwable e) {
			System.out.println("stack length:" + oom.stackLength);
			throw e;
		}
	}
}