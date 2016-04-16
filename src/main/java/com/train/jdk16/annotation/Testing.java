
package com.train.jdk16.annotation;

import javax.annotation.PostConstruct;

public class Testing {
	@ToBeTested(group = "A")
	public void m1() {
	}
	
	@ToBeTested(group = "B", owner = "QQ")
	public void m2() {
	}
	
	@PostConstruct
	// Common Annotation里面的一个Annotation
	public void m3() {
	}
}