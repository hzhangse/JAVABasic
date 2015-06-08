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
		 * �����ܳ��飬�������ף�˽�׷��뵽ָ���ļ���
		 * 
		 * Ĭ�Ϸ���mykeys.bat�ļ���
		 */
		public void createPairKey() {
			try {
				// �����ض����㷨һ����Կ��������
				KeyPairGenerator keygen = KeyPairGenerator.getInstance("DSA");
				// ��������������� (RNG)
				SecureRandom random = new SecureRandom();
				// �������ô�������������
				random.setSeed(1000);
				// ʹ�ø��������Դ����Ĭ�ϵĲ������ϣ���ʼ��ȷ����Կ��С����Կ��������
				keygen.initialize(512, random);// keygen.initialize(512);
				// ������Կ��
				KeyPair keys = keygen.generateKeyPair();
				// �õ�����
				PublicKey pubkey = keys.getPublic();
				// �õ�˽��
				PrivateKey prikey = keys.getPrivate();
				// ������˽��д�뵽�ļ�����
				doObjToFile("mykeys.bat", new Object[] { prikey, pubkey });
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}

		
	/**
	 * ����˽�׶���Ϣ����ǩ�� ��ǩ�������Ϣ���뵽ָ�����ļ���
	 * 
	 * @param info
	 *            Ҫǩ������Ϣ
	 * @param signfile
	 *            ������ļ�
	 */
	public void signToInfo(String info, String signfile) {
		// ���ļ����ж�ȡ˽��
		PrivateKey myprikey = (PrivateKey) getObjFromFile("mykeys.bat", 1);
		// ���ļ��ж�ȡ����
		PublicKey mypubkey = (PublicKey) getObjFromFile("mykeys.bat", 2);
		try {
			// Signature ������������ɺ���֤����ǩ��
			Signature signet = Signature.getInstance("DSA");
			// ��ʼ��ǩ��ǩ����˽Կ
			signet.initSign(myprikey);
			// ����Ҫ���ֽ�ǩ������֤������
			signet.update(info.getBytes());
			// ǩ�����֤���и����ֽڵ�ǩ��������ǩ��
			byte[] signed = signet.sign();
			// ������ǩ��,����,��Ϣ�����ļ���
			doObjToFile(signfile, new Object[] { signed, mypubkey, info });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ����ǩ���ļ� ���ݹ��ף�ǩ������Ϣ��֤��Ϣ�ĺϷ���
	 * 
	 * @return true ��֤�ɹ� false ��֤ʧ��
	 */
	public boolean validateSign(String signfile) {
		// ��ȡ����
		PublicKey mypubkey = (PublicKey) getObjFromFile(signfile, 2);
		// ��ȡǩ��
		byte[] signed = (byte[]) getObjFromFile(signfile, 1);
		// ��ȡ��Ϣ
		String info = (String) getObjFromFile(signfile, 3);
		try {
			// ��ʼһ��Signature����,���ù�Կ��ǩ��������֤
			Signature signetcheck = Signature.getInstance("DSA");
			// ��ʼ����֤ǩ���Ĺ�Կ
			signetcheck.initVerify(mypubkey);
			// ʹ��ָ���� byte �������Ҫǩ������֤������
			signetcheck.update(info.getBytes());
			System.out.println(info);
			// ��֤�����ǩ��
			return signetcheck.verify(signed);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) {
		DSAEncrypt crypt = new DSAEncrypt();
		// �������׺�˽��
		crypt.createPairKey();
		// ��Hello world!ʹ��˽�׽���ǩ��
		crypt.signToInfo("Hello", "mysign.bat");
		// ���ù��׶�ǩ��������֤��
		if (crypt.validateSign("mysign.bat")) {
			System.out.println("Success!");
		} else {
			System.out.println("Fail!");
		}

	}

}
