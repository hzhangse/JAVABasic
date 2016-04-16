
package com.train.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import org.junit.Test;

/**
 *-Xmx5m -Xms5m   -XX:MetaspaceSize=5m  -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDetails  -Xloggc:gcDetail.log
 * @author ryan
 *
 */

public class weakreferenceTest {
	//@Test
	public void testsoftrefer() {
		
		Car car = new Car(22000, "silver");
		ReferenceQueue<Car> rq = new ReferenceQueue<Car>();
		SoftReference<Car> softCar = new SoftReference<Car>(car, rq);
		int i = 0;
		
		while (true) {
			// System.out.println("here is the strong reference 'car' "+car);
			// 当引用对象被标识为可回收时 返回true, 即当user对象标识为可回收时，返回true
			if (!softCar.isEnqueued()) {
				i++;
				System.out.println("Object is alive for " + i
				                + " loops - " + softCar.get());
			} else {
				System.out.println("Object has been collected."
				                + rq.poll());
				break;
			}
			
		}
	}
	
	
	
	//@Test
	public void testWeakrefer() {
		
		Car car = new Car(22000, "silver");
		ReferenceQueue<Car> rq = new ReferenceQueue<Car>();
		WeakReference<Car> weakCar = new WeakReference<Car>(car, rq);
		int i = 0;
		
		while (true) {
			// System.out.println("here is the strong reference 'car' "+car);
			// 当引用对象被标识为可回收时 返回true, 即当user对象标识为可回收时，返回true
			if (!weakCar.isEnqueued()) {
				i++;
				System.out.println("Object is alive for " + i
				                + " loops - " + weakCar.get());
			} else {
				System.out.println("Object has been collected."
				                + rq.poll());
				break;
			}
			// if (weakCar.get() != null) {
			// i++;
			// System.out.println("Object is alive for " + i
			// + " loops - " + weakCar);
			// } else {
			// System.out.println("Object has been collected.");
			// break;
			// }
			// if (i==1000){
			// System.gc();
			// }
		}
	}
	
	@Test
	public void testPhantomReference(){
		
		Car car = new Car(22000, "silver");
		ReferenceQueue<Car> rq = new ReferenceQueue<Car>();
		PhantomReference<Car> softCar = new PhantomReference<Car>(car, rq);
		int i = 0;
		
		while (true) {
			// System.out.println("here is the strong reference 'car' "+car);
			// 当引用对象被标识为可回收时 返回true, 即当user对象标识为可回收时，返回true
			if (!softCar.isEnqueued()) {
				i++;
				System.out.println("Object is alive for " + i
				                + " loops - " + softCar.get());
			} else {
				System.out.println("Object has been collected."
				                + rq.poll());
				break;
			}
			
		}
	}
}
