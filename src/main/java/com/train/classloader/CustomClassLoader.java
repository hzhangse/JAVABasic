package com.train.classloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 自定义类加载器
 */
public class CustomClassLoader extends ClassLoader {
	/** 类名 **/
	private String name;

	/** 通过构造方法设置父类加载器和要热加载的类名 **/
	public CustomClassLoader(ClassLoader parent, String name) {
		super(parent);
		if (name == null || name.length() <= 0)
			throw new NullPointerException();

		this.name = name;
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		Class<?> clazz = null;
		/** 如果是我们想要热加载的类则调用我们重写的findClass方法来加载 **/
		if (this.name.equals(name) && !"java".equals(name)) {
			/** 先看看要热加载的类之前是否已经加载过了，因为一个类加载器只能加载一个类一次，加载多次会报异常 **/
			clazz = findLoadedClass(name);
			/** clazz==null说明之前没有加载过 **/
			if (clazz == null)
				clazz = findClass(name);

			/**
			 * 类的生命周期包括：加载、验证、准备、解析、初始化、使用、卸载。其中验证、准备、解析统称为连接 如果要连接类
			 */
			if (resolve)
				resolveClass(clazz);// 如果类已连接过，resolveClass方法会直接返回
			return clazz;
		}
		return super.loadClass(name, resolve);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		String fileName = c2f(name);
		byte[] bytes = f2b(fileName);
		return defineClass(name, bytes, 0, bytes.length);
	}

	/**
	 * 类名转为文件名
	 * 
	 * @param name
	 * @return
	 */
	private String c2f(String name) {
		/** 编译后的class文件存放的目录 **/
		String baseDir = "D:\\workspace\\train\\JavaBasic\\target\\classes\\";
		name = name.replace(".", File.separator);
		name = baseDir + name + ".class";
		return name;
	}

	/**
	 * 读取文件byte数组
	 * 
	 * @param fileName
	 * @return
	 */
	private byte[] f2b(String fileName) {
		RandomAccessFile file = null;
		FileChannel channel = null;
		byte[] bytes = null;
		try {
			/** 随机存取文件对象，只读取模式 **/
			file = new RandomAccessFile(fileName, "r");
			/** NIO文件通道 **/
			channel = file.getChannel();
			/** NIO字节缓冲 **/
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			int size = (int) channel.size();
			bytes = new byte[size];
			int index = 0;
			/** 从NIO文件通道读取数据 **/
			while (channel.read(buffer) > 0) {
				/** 字节缓冲从写模式转为读取模式 **/
				buffer.flip();
				while (buffer.hasRemaining()) {
					bytes[index] = buffer.get();
					++index;
				}
				/** 字节缓冲的readerIndex、writerIndex置零 **/
				buffer.clear();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (channel != null) {
				try {
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bytes;
	}

	/**
	 * 热加载类
	 * 
	 * @return
	 */
	public Class<?> loadClass() {
		try {
			return loadClass(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
