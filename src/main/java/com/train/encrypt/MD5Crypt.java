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
			// 得到一个md5的消息摘要
			MessageDigest alga = MessageDigest.getInstance("MD5");
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
		MD5Crypt md5 = new MD5Crypt();
		// 执行MD5加密"Hello world!"
		System.out.println("Hello经过MD5: " + md5.encryptToMD5("Hello"));

	}

}
