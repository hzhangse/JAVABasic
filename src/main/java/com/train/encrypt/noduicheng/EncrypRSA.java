package com.train.encrypt.noduicheng;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class EncrypRSA {
	
	/**
	 * ����
	 * @param publicKey
	 * @param srcBytes
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	protected byte[] encrypt(RSAPublicKey publicKey,byte[] srcBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		if(publicKey!=null){
			//Cipher������ɼ��ܻ���ܹ���������RSA
			Cipher cipher = Cipher.getInstance("RSA");
			//���ݹ�Կ����Cipher������г�ʼ��
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] resultBytes = cipher.doFinal(srcBytes);
			return resultBytes;
		}
		return null;
	}
	
	/**
	 * ���� 
	 * @param privateKey
	 * @param srcBytes
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	protected byte[] decrypt(RSAPrivateKey privateKey,byte[] srcBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		if(privateKey!=null){
			//Cipher������ɼ��ܻ���ܹ���������RSA
			Cipher cipher = Cipher.getInstance("RSA");
			//���ݹ�Կ����Cipher������г�ʼ��
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] resultBytes = cipher.doFinal(srcBytes);
			return resultBytes;
		}
		return null;
	}

	/**
	 * @param args
	 * @throws NoSuchAlgorithmException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeyException 
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		EncrypRSA rsa = new EncrypRSA();
		String msg = "��XX-��Ʒ����";
		//KeyPairGenerator���������ɹ�Կ��˽Կ�ԣ�����RSA�㷨���ɶ���
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		//��ʼ����Կ������������Կ��СΪ1024λ
		keyPairGen.initialize(1024);
		//����һ����Կ�ԣ�������keyPair��
		KeyPair keyPair = keyPairGen.generateKeyPair();
		//�õ�˽Կ
		RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();				
		//�õ���Կ
		RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
		
		//�ù�Կ����
		byte[] srcBytes = msg.getBytes();
		byte[] resultBytes = rsa.encrypt(publicKey, srcBytes);
		
		//��˽Կ����
		byte[] decBytes = rsa.decrypt(privateKey, resultBytes);
		
		System.out.println("������:" + msg);
		System.out.println("���ܺ���:" + new String(resultBytes));
		System.out.println("���ܺ���:" + new String(decBytes));
	}

}
