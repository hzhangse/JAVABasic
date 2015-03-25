package com.train.vm;
/**
 * 操作系统分配给每个进程的内存是有限制的，譬如32位Windows限制为2G，Java堆和方法区的大小JVM有参数可以限制最大值，那剩余的内存为2G（
 * 操作系统限制）-Xmx（最大堆）-MaxPermSize（最大方法区），程序计数器消耗内存很小，可以忽略掉，那虚拟机进程本身耗费的内存不计算的话，
 * 剩下的内存就供每一个线程的VM栈和本地方法栈瓜分了，那自然每个线程中VM栈分配内存越多，就越容易把剩下的内存耗尽。 创建线程导致OOM异常 VM
 * Args：-Xss2M （这时候不妨设大些）
 */
public class JavaVMStackOOM {

	private void dontStop() {
		int a=0;
		a++;
		while (true) {
			
		}
	}

	public void stackLeakByThread() {
		int i = 0;
		while (i++<10000) {
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					dontStop();
				}
			});
			thread.start();
			System.out.println("finish start thread:" + i);
		}
		System.out.println("finish all thread");
	}

	public static void main(String[] args) throws Throwable {
		JavaVMStackOOM oom = new JavaVMStackOOM();
		oom.stackLeakByThread();
	}
}