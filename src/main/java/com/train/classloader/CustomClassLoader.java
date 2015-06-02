package com.train.classloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * �Զ����������
 */
public class CustomClassLoader extends ClassLoader {
	/** ���� **/
	private String name;

	/** ͨ�����췽�����ø����������Ҫ�ȼ��ص����� **/
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
		/** �����������Ҫ�ȼ��ص��������������д��findClass���������� **/
		if (this.name.equals(name) && !"java".equals(name)) {
			/** �ȿ���Ҫ�ȼ��ص���֮ǰ�Ƿ��Ѿ����ع��ˣ���Ϊһ���������ֻ�ܼ���һ����һ�Σ����ض�λᱨ�쳣 **/
			clazz = findLoadedClass(name);
			/** clazz==null˵��֮ǰû�м��ع� **/
			if (clazz == null)
				clazz = findClass(name);

			/**
			 * ����������ڰ��������ء���֤��׼������������ʼ����ʹ�á�ж�ء�������֤��׼��������ͳ��Ϊ���� ���Ҫ������
			 */
			if (resolve)
				resolveClass(clazz);// ����������ӹ���resolveClass������ֱ�ӷ���
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
	 * ����תΪ�ļ���
	 * 
	 * @param name
	 * @return
	 */
	private String c2f(String name) {
		/** ������class�ļ���ŵ�Ŀ¼ **/
		String baseDir = "D:\\workspace\\train\\JavaBasic\\target\\classes\\";
		name = name.replace(".", File.separator);
		name = baseDir + name + ".class";
		return name;
	}

	/**
	 * ��ȡ�ļ�byte����
	 * 
	 * @param fileName
	 * @return
	 */
	private byte[] f2b(String fileName) {
		RandomAccessFile file = null;
		FileChannel channel = null;
		byte[] bytes = null;
		try {
			/** �����ȡ�ļ�����ֻ��ȡģʽ **/
			file = new RandomAccessFile(fileName, "r");
			/** NIO�ļ�ͨ�� **/
			channel = file.getChannel();
			/** NIO�ֽڻ��� **/
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			int size = (int) channel.size();
			bytes = new byte[size];
			int index = 0;
			/** ��NIO�ļ�ͨ����ȡ���� **/
			while (channel.read(buffer) > 0) {
				/** �ֽڻ����дģʽתΪ��ȡģʽ **/
				buffer.flip();
				while (buffer.hasRemaining()) {
					bytes[index] = buffer.get();
					++index;
				}
				/** �ֽڻ����readerIndex��writerIndex���� **/
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
	 * �ȼ�����
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
