package com.train.encrypt;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class DesEncrypt extends BaseCrypt {

	// //////////////////////////////////////////////////////////////////////////
	/**
	 * �����ܳ�
	 * 
	 * @param algorithm
	 *            �����㷨,���� DES,DESede,Blowfish
	 * @return SecretKey ���ܣ��Գƣ���Կ
	 */
	public SecretKey createSecretKey(String algorithm) {
		// ����KeyGenerator����
		KeyGenerator keygen;
		// ���� ��Կ����
		SecretKey deskey = null;
		try {
			// ��������ָ���㷨��������Կ�� KeyGenerator ����
			keygen = KeyGenerator.getInstance(algorithm);
			// ����һ����Կ
			deskey = keygen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// �����ܳ�
		return deskey;
	}

	/**
	 * �����ܳ׽���DES����
	 * 
	 * @param key
	 *            �ܳ�
	 * @param info
	 *            Ҫ���ܵ���Ϣ
	 * @return String ���ܺ����Ϣ
	 */
	public String encryptToDES(SecretKey key, String info) {
		// ���� �����㷨,���� DES,DESede,Blowfish
		String Algorithm = "DES";
		// ��������������� (RNG),(���Բ�д)
		SecureRandom sr = new SecureRandom();
		// ����Ҫ���ɵ�����
		byte[] cipherByte = null;
		try {
			// �õ�����/������
			Cipher c1 = Cipher.getInstance(Algorithm);
			// ��ָ������Կ��ģʽ��ʼ��Cipher����
			// ����:(ENCRYPT_MODE, DECRYPT_MODE, WRAP_MODE,UNWRAP_MODE)
			c1.init(Cipher.ENCRYPT_MODE, key, sr);
			// ��Ҫ���ܵ����ݽ��б��봦��,
			cipherByte = c1.doFinal(info.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// �������ĵ�ʮ��������ʽ
		return byte2hex(cipherByte);
	}

	/**
	 * �����ܳ׽���DES����
	 * 
	 * @param key
	 *            �ܳ�
	 * @param sInfo
	 *            Ҫ���ܵ�����
	 * @return String ���ؽ��ܺ���Ϣ
	 */
	public String decryptByDES(SecretKey key, String sInfo) {
		// ���� �����㷨,
		String Algorithm = "DES";
		// ��������������� (RNG)
		SecureRandom sr = new SecureRandom();
		byte[] cipherByte = null;
		try {
			// �õ�����/������
			Cipher c1 = Cipher.getInstance(Algorithm);
			// ��ָ������Կ��ģʽ��ʼ��Cipher����
			c1.init(Cipher.DECRYPT_MODE, key, sr);
			// ��Ҫ���ܵ����ݽ��б��봦��
			cipherByte = c1.doFinal(hex2byte(sInfo));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return byte2hex(cipherByte);
		return new String(cipherByte);
	}

	public DesEncrypt() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		DesEncrypt encypt = new DesEncrypt();

		// ����һ��DES�㷨���ܳ�
		SecretKey key = encypt.createSecretKey("DES");
		// ���ܳ׼�����Ϣ"Hello world!"
		String str1 = encypt.encryptToDES(key, "Hello");
		System.out.println("ʹ��des������ϢHelloΪ:" + str1);
		// ʹ������ܳ׽���
		String str2 = encypt.decryptByDES(key, str1);
		System.out.println("���ܺ�Ϊ��" + str2);
	}
}
