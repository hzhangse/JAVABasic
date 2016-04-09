
package com.train.jdk16;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.h2.util.SourceCompiler;
import org.junit.Test;

/**
 * 使用 StandardJavaFileManager 编译 Java 源程序 在第一部分我们讨论调用 java
 * 编译器的最容易的方法。这种方法能非常好地工作,但他确不 能更有效地得到我们所需要的信息,如标准的输入、输出信息。而在 Java SE6 中最佳的方法是使
 * 用 StandardJavaFileManager 类。这个类能非常好地控制输入、输出,并且能通过 DiagnosticListener 得到诊断信息,而
 * DiagnosticCollector 类就是 listener 的实现。 使用 StandardJavaFileManager 需要两步。首先建立一个
 * DiagnosticCollector 实例及通过 JavaCompiler 的 getStandardFileManager()方法得到一个
 * StandardFileManager 对象。最后通过 CompilationTask 中的 call 方法编译源程序 每个类的具体方法参数可以查看
 * jase6 API 文档。上面有很详细的解释
 * 
 * @author ryan
 *
 */
public class DynamicCompileTest {
	
	/**
	 * 使用 StandardJavaFileManager 编译 Java 源程序 在第一部分我们讨论调用 java
	 * 编译器的最容易的方法。这种方法能非常好地工作,但他确不 能更有效地得到我们所需要的信息,如标准的输入、输出信息。而在 Java SE6
	 * 中最佳的方法是使 用 StandardJavaFileManager 类。这个类能非常好地控制输入、输出,并且能通过
	 * DiagnosticListener 得到诊断信息,而 DiagnosticCollector 类就是 listener 的实现。 使用
	 * StandardJavaFileManager 需要两步。首先建立一个 DiagnosticCollector 实例及通过
	 * JavaCompiler 的 getStandardFileManager()方法得到一个 StandardFileManager 对象。最后通过
	 * CompilationTask 中的 call 方法编译源程序 每个类的具体方法参数可以查看 jase6 API 文档。上面有很详细的解释
	 * 
	 * @throws Exception
	 */
	@Test
	public void compileFromFile() throws Exception {
		// 1.创建需要动态编译的代码字符串
		String nr = "\r\n"; // 回车
		String source = "package temp.com; "
		                + nr
		                + " public class  Hello{"
		                + nr
		                + " public static void main (String[] args){"
		                + nr
		                + " System.out.println(\"HelloWorld! 1\");"
		                + nr + " }" + nr
		                + " }";
		// 2.将欲动态编译的代码写入文件中 1.创建临时目录 2.写入临时文件目录
		File dir = new File(
		                System.getProperty("user.dir")
		                                + "/temp"); // 临时目录
		// 如果 \temp 不存在 就创建
		if (!dir.exists()) {
			dir.mkdir();
		}
		FileWriter writer = new FileWriter(
		                new File(dir,
		                                "Hello.java"));
		writer.write(source);
		writer.flush();
		writer.close();
		
		// 3.取得当前系统的编译器
		JavaCompiler javaCompiler = ToolProvider
		                .getSystemJavaCompiler();
		// 4.获取一个文件管理器
		StandardJavaFileManager javaFileManager = javaCompiler
		                .getStandardFileManager(
		                                null, null,
		                                null);
		// 5.文件管理器根与文件连接起来
		Iterable it = javaFileManager
		                .getJavaFileObjects(new File(
		                                dir,
		                                "Hello.java"));
		// 6.创建编译任务
		CompilationTask task = javaCompiler
		                .getTask(null,
		                                javaFileManager,
		                                null,
		                                Arrays.asList("-d",
		                                                "./temp"),
		                                null,
		                                it);
		// 7.执行编译
		task.call();
		javaFileManager.close();
		
		// 8.运行程序
		Runtime run = Runtime.getRuntime();
		Process process = run
		                .exec("java -cp ./temp temp/com/Hello");
		InputStream in = process.getInputStream();
		BufferedReader reader = new BufferedReader(
		                new InputStreamReader(in));
		String info = "";
		while ((info = reader.readLine()) != null) {
			System.out.println(info);
			
		}
	}
	
	/*
	 * JavaCompiler 不仅能编译硬盘上的 Java 文件,而且还能编译内存中的 Java 代码,然后使 用 reflection
	 * 来运行他们。我们能编写一个类,通过这个类能输入 Java 原始码。 一但建立这个对 象, 你能向其中输入任意的 Java 代码,然后编译和运行。
	 */
	@Test
	public void compileFromMemory() throws Exception{
		/*
		 * 编译内存中的java代码
		 * */
		// 1.将代码写入内存中
		StringWriter writer = new StringWriter(); // 内存字符串输出流
		PrintWriter out = new PrintWriter(writer);
		out.println("package com.dongtai.hello;");
		out.println("public class Hello{");
		out.println("public static void main(String[] args){");
		out.println("System.out.println(\"HelloWorld! 2\");");
		out.println("}");
		out.println("}");
		out.flush();
		out.close();
		
		// 2.开始编译
		JavaCompiler javaCompiler = ToolProvider
		                .getSystemJavaCompiler();
		SourceCompiler compile = new SourceCompiler();
		compile.setSource("com.dongtai.hello.Hello",writer.toString());
		Class classl = compile.getClass("com.dongtai.hello.Hello");
		
//		JavaFileObject fileObject = new StringJavaFileObject(
//		                "Hello", writer.toString());
//		CompilationTask task = javaCompiler
//		                .getTask(null,
//		                                null,
//		                                null,
//		                                Arrays.asList("-d",
//		                                                "./bin"),
//		                                null,
//		                                Arrays.asList(fileObject));
//		boolean success = task.call();
//		if (!success) {
//			System.out.println("编译失败");
//		} else {
//			System.out.println("编译成功");
//		}
//		URL[] urls = new URL[] { new URL("file:/"
//		                + "./bin/") };
//		URLClassLoader classLoader = new URLClassLoader(
//		                urls);
//		Class classl = classLoader
//		                .loadClass("com.dongtai.hello.Hello");
		Method method = classl.getDeclaredMethod(
		                "main", String[].class);
		String[] argsl = { null };
		method.invoke(classl.newInstance(), argsl);
		
	}
}