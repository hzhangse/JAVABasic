package com.train.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Crypt extends BaseCrypt {

	public MD5Crypt() {
		// TODO Auto-generated constructor stub
	}

	public String encryptToMD5(String info) {
		byte[] digesta = null;
		try {
			// �õ�һ��md5����ϢժҪ
			MessageDigest alga = MessageDigest.getInstance("MD5");
			// ���Ҫ���м���ժҪ����Ϣ
			alga.update(info.getBytes());
			// �õ���ժҪ
			digesta = alga.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// ��ժҪתΪ�ַ���
		String rs = byte2hex(digesta);
		return rs;
	}

	public static void main(String[] args) {
		MD5Crypt md5 = new MD5Crypt();
		// ִ��MD5����"Hello world!"
		System.out.println("Hello����MD5: " + md5.encryptToMD5("Hello"));

	}

}
