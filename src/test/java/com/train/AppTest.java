package com.train;

import java.util.Collection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		assertTrue(true);
	}

	public static void testAdd(Collection<?> c) {

		c.add(null); // 编译通过

		// c.add(“test”); //编译错误
		for (Object o : c) {
			System.out.println(o);
		}
	}

	public static void testAdd1() {
		Collection<? extends BaseClass> c;
		c.add(null); // 编译通过

		c.add(new BaseClass()); // 编译错误
		for (BaseClass o : c) {
			System.out.println(o);
		}
		Collection<? super BaseClass> c1;
		c1.add(new BaseClass());
		for (BaseClass o : c1) {
			System.out.println(o);
		}
	}

	public static <T> void fromArrayToCollection(T[] a, Collection<T> c) {

		for (T o : a) {
			c.add(o); // 合法。注意与Collection<?>的区别
		}
	}

	public static long sum1(Collection<? extends Number> numbers) {
		long sum = 0;
		for (Number n : numbers) {
			sum += n.longValue();
		}
		return sum;
	}

	// 我们也可以将其以Generic方法实现：

	public static <T extends Number> long sum2(Collection<T> numbers) {

		long sum = 0;

		for (Number n : numbers) {

			sum += n.longValue();

		}

		return sum;
	}
}

class BaseClass {
	public static <T> void method2(T t) {
	};// 编译错误，静态方法不能使用T

	public <T > void method3(T t) {
	};// 编译错误，静态方法不能使用T
}