package com.train.encrypt.duicheng;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class EncrypDES {
	
	//KeyGenerator �ṩ�Գ���Կ�������Ĺ��ܣ�֧�ָ����㷨
	private KeyGenerator keygen;
	//SecretKey ���𱣴�Գ���Կ
	private SecretKey deskey;
	//Cipher������ɼ��ܻ���ܹ���
	private Cipher c;
	//���ֽ����鸺�𱣴���ܵĽ��
	private byte[] cipherByte;
	
	public EncrypDES() throws NoSuchAlgorithmException, NoSuchPaddingException{
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		//ʵ����֧��DES�㷨����Կ������(�㷨���������谴�涨�������׳��쳣)
		keygen = KeyGenerator.getInstance("DES");
		//������Կ
		deskey = keygen.generateKey();
		//����Cipher����,ָ����֧�ֵ�DES�㷨
		c = Cipher.getInstance("DES");
	}
	
	/**
	 * ���ַ�������
	 * 
	 * @param str
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] Encrytor(String str) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		// ������Կ����Cipher������г�ʼ����ENCRYPT_MODE��ʾ����ģʽ
		c.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] src = str.getBytes();
		// ���ܣ���������cipherByte
		cipherByte = c.doFinal(src);
		return cipherByte;
	}

	/**
	 * ���ַ�������
	 * 
	 * @param buff
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public byte[] Decryptor(byte[] buff) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		// ������Կ����Cipher������г�ʼ����DECRYPT_MODE��ʾ����ģʽ
		c.init(Cipher.DECRYPT_MODE, deskey);
		cipherByte = c.doFinal(buff);
		return cipherByte;
	}

	/**
	 * @param args
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 */
	public static void main(String[] args) throws Exception {
		EncrypDES de1 = new EncrypDES();
		String msg ="��XX-��Ц����ȫ��";
		byte[] encontent = de1.Encrytor(msg);
		byte[] decontent = de1.Decryptor(encontent);
		System.out.println("������:" + msg);
		System.out.println("���ܺ�:" + new String(encontent));
		System.out.println("���ܺ�:" + new String(decontent));
	}

}
