/**
 * 
 */

package com.train.jdk18.interfaceImpl;

/**
 * @author ryan
 *
 */
public interface Formula {
	double calculate(int a);
	
	default double sqrt(int a) {
		return Math.sqrt(a);
	}
}
