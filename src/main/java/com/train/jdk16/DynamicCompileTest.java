
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
 * ʹ�� StandardJavaFileManager ���� Java Դ���� �ڵ�һ�����������۵��� java
 * �������������׵ķ��������ַ����ܷǳ��õع���,����ȷ�� �ܸ���Ч�صõ���������Ҫ����Ϣ,���׼�����롢�����Ϣ������ Java SE6 ����ѵķ�����ʹ
 * �� StandardJavaFileManager �ࡣ������ܷǳ��õؿ������롢���,������ͨ�� DiagnosticListener �õ������Ϣ,��
 * DiagnosticCollector ����� listener ��ʵ�֡� ʹ�� StandardJavaFileManager ��Ҫ���������Ƚ���һ��
 * DiagnosticCollector ʵ����ͨ�� JavaCompiler �� getStandardFileManager()�����õ�һ��
 * StandardFileManager �������ͨ�� CompilationTask �е� call ��������Դ���� ÿ����ľ��巽���������Բ鿴
 * jase6 API �ĵ��������к���ϸ�Ľ���
 * 
 * @author ryan
 *
 */
public class DynamicCompileTest {
	
	/**
	 * ʹ�� StandardJavaFileManager ���� Java Դ���� �ڵ�һ�����������۵��� java
	 * �������������׵ķ��������ַ����ܷǳ��õع���,����ȷ�� �ܸ���Ч�صõ���������Ҫ����Ϣ,���׼�����롢�����Ϣ������ Java SE6
	 * ����ѵķ�����ʹ �� StandardJavaFileManager �ࡣ������ܷǳ��õؿ������롢���,������ͨ��
	 * DiagnosticListener �õ������Ϣ,�� DiagnosticCollector ����� listener ��ʵ�֡� ʹ��
	 * StandardJavaFileManager ��Ҫ���������Ƚ���һ�� DiagnosticCollector ʵ����ͨ��
	 * JavaCompiler �� getStandardFileManager()�����õ�һ�� StandardFileManager �������ͨ��
	 * CompilationTask �е� call ��������Դ���� ÿ����ľ��巽���������Բ鿴 jase6 API �ĵ��������к���ϸ�Ľ���
	 * 
	 * @throws Exception
	 */
	@Test
	public void compileFromFile() throws Exception {
		// 1.������Ҫ��̬����Ĵ����ַ���
		String nr = "\r\n"; // �س�
		String source = "package temp.com; "
		                + nr
		                + " public class  Hello{"
		                + nr
		                + " public static void main (String[] args){"
		                + nr
		                + " System.out.println(\"HelloWorld! 1\");"
		                + nr + " }" + nr
		                + " }";
		// 2.������̬����Ĵ���д���ļ��� 1.������ʱĿ¼ 2.д����ʱ�ļ�Ŀ¼
		File dir = new File(
		                System.getProperty("user.dir")
		                                + "/temp"); // ��ʱĿ¼
		// ��� \temp ������ �ʹ���
		if (!dir.exists()) {
			dir.mkdir();
		}
		FileWriter writer = new FileWriter(
		                new File(dir,
		                                "Hello.java"));
		writer.write(source);
		writer.flush();
		writer.close();
		
		// 3.ȡ�õ�ǰϵͳ�ı�����
		JavaCompiler javaCompiler = ToolProvider
		                .getSystemJavaCompiler();
		// 4.��ȡһ���ļ�������
		StandardJavaFileManager javaFileManager = javaCompiler
		                .getStandardFileManager(
		                                null, null,
		                                null);
		// 5.�ļ������������ļ���������
		Iterable it = javaFileManager
		                .getJavaFileObjects(new File(
		                                dir,
		                                "Hello.java"));
		// 6.������������
		CompilationTask task = javaCompiler
		                .getTask(null,
		                                javaFileManager,
		                                null,
		                                Arrays.asList("-d",
		                                                "./temp"),
		                                null,
		                                it);
		// 7.ִ�б���
		task.call();
		javaFileManager.close();
		
		// 8.���г���
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
	 * JavaCompiler �����ܱ���Ӳ���ϵ� Java �ļ�,���һ��ܱ����ڴ��е� Java ����,Ȼ��ʹ �� reflection
	 * ���������ǡ������ܱ�дһ����,ͨ������������� Java ԭʼ�롣 һ����������� ��, ������������������� Java ����,Ȼ���������С�
	 */
	@Test
	public void compileFromMemory() throws Exception{
		/*
		 * �����ڴ��е�java����
		 * */
		// 1.������д���ڴ���
		StringWriter writer = new StringWriter(); // �ڴ��ַ��������
		PrintWriter out = new PrintWriter(writer);
		out.println("package com.dongtai.hello;");
		out.println("public class Hello{");
		out.println("public static void main(String[] args){");
		out.println("System.out.println(\"HelloWorld! 2\");");
		out.println("}");
		out.println("}");
		out.flush();
		out.close();
		
		// 2.��ʼ����
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
//			System.out.println("����ʧ��");
//		} else {
//			System.out.println("����ɹ�");
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