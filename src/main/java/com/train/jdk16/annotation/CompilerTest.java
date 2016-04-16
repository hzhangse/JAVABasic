
package com.train.jdk16.annotation;

import java.util.HashSet;
import java.util.Set;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class CompilerTest {
	
	public static void main(String[] args) {
		JavaCompiler compiler = ToolProvider
		                .getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler
		                .getStandardFileManager(
		                                null, null,
		                                null);
		Iterable<? extends JavaFileObject> sourcefiles = fileManager
		                .getJavaFileObjects("/home/ryan/workspace/JAVABasic/src/main/java/com/train/jdk16/annotation/Testing.java");
		Set<String> options = new HashSet<String>();
		options.add("-XprintRounds");
		//options.add("-classpath /home/ryan/workspace/JAVABasic/target/classes/");
		
		//options.add("-processor com.train.jdk16.annotation.MyAnnotationProcessor ");
		
		
		Set<String> classes = new HashSet<String>();
		classes.add("/home/ryan/workspace/JAVABasic/target/classes");
		//classes.add("com.train.jdk16.annotation.MyAnnotationProcessor");
		compiler.getTask(null, fileManager, null,
		                options, null, sourcefiles)
		                .call();
		
	}
	
}
