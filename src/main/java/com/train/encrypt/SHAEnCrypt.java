package com.train.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAEnCrypt extends BaseCrypt {

	public SHAEnCrypt() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * ����SHA����
	 * 
	 * @param info
	 *            Ҫ���ܵ���Ϣ
	 * @return String ���ܺ���ַ���
	 */
	public String encryptToSHA(String info) {
		byte[] digesta = null;
		try {
			// �õ�һ��SHA-1����ϢժҪ
			MessageDigest alga = MessageDigest.getInstance("SHA-1");
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
		// TODO Auto-generated method stub
		SHAEnCrypt crypt = new SHAEnCrypt();
		System.out.println("Hello ����SHA: " +crypt.encryptToSHA("hello world"));
	}

}
