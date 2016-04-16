
package com.train.reference;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.h2.util.SoftHashMap;
import org.junit.Test;

import com.sun.beans.WeakCache;

/**
 *  -Xmx5m -Xms5m   -XX:MetaspaceSize=5m  -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDetails  -Xloggc:gcDetail.log
 * @author ryan
 *
 */
public class weakContainerTest {
	
	//@Test
	public void testWeakCache() {
		WeakCache<String, Car> cache = new WeakCache<String, Car>();
		int i = 0;
		while (true) {
			Car car = new Car(22000, "silver");
			cache.put(String.valueOf(i), car);
			i++;
			System.out.println("put car:" + car);
		
		}
	}
	
	@Test
	public void testWeakMap() {
		Map<String, Car> cache = new WeakHashMap<String, Car>();
		this.printDetail(cache);
	}
	
	//@Test
	public void testSoftMap() {
		Map<String, Car> cache = new SoftHashMap<String, Car>();
		this.printDetail(cache);
	}
	
	// @Test
	public void testMap() {
		Map<String, Car> cache = new HashMap<String, Car>();
		this.printDetail(cache);
	}
	
	private void printDetail(Map cache) {
		int i = 0;
		while (true) {
			Car car = new Car(22000, "silver");
			
			cache.put(String.valueOf(i), car);
			i++;
			//System.out.println("put car:" + car);
			System.out.println("Map size :" + cache.size());
		}
	}
	
}
