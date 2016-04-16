
package com.train.jdk18.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LambdaTest {
	
	public static void main(String[] args) {
		//����д��
		List<String> names = Arrays.asList("peter", "anna", "mike",
		                "xenia");
		Collections.sort(names, new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				return b.compareTo(a);
			}
		});
		
		//lambda д��
		Collections.sort(names, (String a,String b)->{
			return b.compareTo(a);
		});
		
		Collections.sort(names, (String a,String b)->b.compareTo(a));
		
		Collections.sort(names, (a,b)->b.compareTo(a));
	}
	
}
