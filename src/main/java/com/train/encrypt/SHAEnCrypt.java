package com.train.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAEnCrypt extends BaseCrypt {

	public SHAEnCrypt() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 进行SHA加密
	 * 
	 * @param info
	 *            要加密的信息
	 * @return String 加密后的字符串
	 */
	public String encryptToSHA(String info) {
		byte[] digesta = null;
		try {
			// 得到一个SHA-1的消息摘要
			MessageDigest alga = MessageDigest.getInstance("SHA-1");
			// 添加要进行计算摘要的信息
			alga.update(info.getBytes());
			// 得到该摘要
			digesta = alga.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 将摘要转为字符串
		String rs = byte2hex(digesta);
		return rs;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SHAEnCrypt crypt = new SHAEnCrypt();
		System.out.println("Hello 经过SHA: " +crypt.encryptToSHA("hello world"));
	}

}
