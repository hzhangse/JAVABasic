package com.train.encrypt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;

public class DSAEncrypt extends BaseCrypt {

	public DSAEncrypt() {
		// TODO Auto-generated constructor stub
	}

	// /////////////////////////////////////////////////////////////////////////////
		/**
		 * 创建密匙组，并将公匙，私匙放入到指定文件中
		 * 
		 * 默认放入mykeys.bat文件中
		 */
		public void createPairKey() {
			try {
				// 根据特定的算法一个密钥对生成器
				KeyPairGenerator keygen = KeyPairGenerator.getInstance("DSA");
				// 加密随机数生成器 (RNG)
				SecureRandom random = new SecureRandom();
				// 重新设置此随机对象的种子
				random.setSeed(1000);
				// 使用给定的随机源（和默认的参数集合）初始化确定密钥大小的密钥对生成器
				keygen.initialize(512, random);// keygen.initialize(512);
				// 生成密钥组
				KeyPair keys = keygen.generateKeyPair();
				// 得到公匙
				PublicKey pubkey = keys.getPublic();
				// 得到私匙
				PrivateKey prikey = keys.getPrivate();
				// 将公匙私匙写入到文件当中
				doObjToFile("mykeys.bat", new Object[] { prikey, pubkey });
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}

		
	/**
	 * 利用私匙对信息进行签名 把签名后的信息放入到指定的文件中
	 * 
	 * @param info
	 *            要签名的信息
	 * @param signfile
	 *            存入的文件
	 */
	public void signToInfo(String info, String signfile) {
		// 从文件当中读取私匙
		PrivateKey myprikey = (PrivateKey) getObjFromFile("mykeys.bat", 1);
		// 从文件中读取公匙
		PublicKey mypubkey = (PublicKey) getObjFromFile("mykeys.bat", 2);
		try {
			// Signature 对象可用来生成和验证数字签名
			Signature signet = Signature.getInstance("DSA");
			// 初始化签署签名的私钥
			signet.initSign(myprikey);
			// 更新要由字节签名或验证的数据
			signet.update(info.getBytes());
			// 签署或验证所有更新字节的签名，返回签名
			byte[] signed = signet.sign();
			// 将数字签名,公匙,信息放入文件中
			doObjToFile(signfile, new Object[] { signed, mypubkey, info });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取数字签名文件 根据公匙，签名，信息验证信息的合法性
	 * 
	 * @return true 验证成功 false 验证失败
	 */
	public boolean validateSign(String signfile) {
		// 读取公匙
		PublicKey mypubkey = (PublicKey) getObjFromFile(signfile, 2);
		// 读取签名
		byte[] signed = (byte[]) getObjFromFile(signfile, 1);
		// 读取信息
		String info = (String) getObjFromFile(signfile, 3);
		try {
			// 初始一个Signature对象,并用公钥和签名进行验证
			Signature signetcheck = Signature.getInstance("DSA");
			// 初始化验证签名的公钥
			signetcheck.initVerify(mypubkey);
			// 使用指定的 byte 数组更新要签名或验证的数据
			signetcheck.update(info.getBytes());
			System.out.println(info);
			// 验证传入的签名
			return signetcheck.verify(signed);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) {
		DSAEncrypt crypt = new DSAEncrypt();
		// 创建公匙和私匙
		crypt.createPairKey();
		// 对Hello world!使用私匙进行签名
		crypt.signToInfo("Hello", "mysign.bat");
		// 利用公匙对签名进行验证。
		if (crypt.validateSign("mysign.bat")) {
			System.out.println("Success!");
		} else {
			System.out.println("Fail!");
		}

	}

}
