package com.train;

public class ClassLoader1 {
	public static void main(String[] args) {
		ClassLoader loader = ClassLoader1.class.getClassLoader(); 
        while (loader != null) { 
            System.out.println(loader.toString()); 
            loader = loader.getParent(); 
        } 
	}
}
