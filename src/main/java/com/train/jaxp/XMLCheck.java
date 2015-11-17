package com.train.jaxp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class XMLCheck {
	public static void main(String[] args) throws IOException {
		String file ="D:\\workspace\\train\\JavaBasic\\src\\main\\java\\com\\train\\jaxp\\resources\\bookStore1.xml";
//		if (args.length == 0) {
//			System.out.print("Usage: XMLCheck filename");
//			return;
//		}

		File xmlFile = new File(file);
		if (!xmlFile.exists()) {
			System.out.print("File not exist");
			return;
		}

		// ����xml�ļ�
		BufferedReader in = new BufferedReader(new FileReader(xmlFile));
		String s;
		StringBuilder xmlSb = new StringBuilder();
		// xml�ļ�ת����String
		while ((s = in.readLine()) != null)
			xmlSb.append(s + "\n");
		in.close();
		String xmlString = xmlSb.toString();
		// TODO Auto-generated method stub
		// �������ַ���
		// int i =
		// checkCharacterData("<?xml version=\"1.0\" encoding=\"gbk\"?><CC>����</CC>");
		// �������ַ���
		// int i =
		// checkCharacterData("<?xml version=\"1.0\" encoding=\"gbk\"?><CC>\u001E����</CC>");

		int errorChar = checkCharacterData(xmlString);
		System.out
				.println("This XML��file contain " + errorChar + " errorChar.");
	}

	// �ж��ַ������Ƿ��зǷ��ַ�
	public static int checkCharacterData(String text) {
		int errorChar = 0;
		if (text == null) {
			return errorChar;
		}
		char[] data = text.toCharArray();
		for (int i = 0, len = data.length; i < len; i++) {
			char c = data[i];
			if (c=='&'){
				System.out.println(c);
			}
			int result = c;
			
			// ���ж��Ƿ��ڴ���Χ��surrogate blocks��
			// �����ַ�����Ϊ�������뵥Ԫ��
			// ��һ����Ԫ�����ڸߴ���high surrogate����Χ��0xD800 �� 0xDBFF����
			// �ڶ�����Ԫ�����ڵʹ���low surrogate����Χ��0xDC00 �� 0xDFFF����
			if (result >= 0xD800 && result <= 0xDBFF) {
				// �������ԣ�surrogate pair��
				int high = c;
				try {
					int low = text.charAt(i + 1);

					if (low < 0xDC00 || low > 0xDFFF) {
						char ch = (char) low;
					}
					// unicode˵��������㷨 ����������ַ���Χ0x10000 �� 0x10FFFF
					// ����result�������ַ�����Ӧ����0x10000��0x10FFFF֮�䣬isXMLCharacter�����ж�
					result = (high - 0xD800) * 0x400 + (low - 0xDC00) + 0x10000;
					i++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (!isXMLCharacter(result)) {
				errorChar++;
			}
		}
		return errorChar;
	}

	private static boolean isXMLCharacter(int c) {
		// ����xml�淶�е�Character Range���xml��֧�ֵ��ַ�
		if (c <= 0xD7FF) {
			if (c >= 0x20)
				return true;
			else {
				if (c == '\n')
					return true;
				if (c == '\r')
					return true;
				if (c == '\t')
					return true;
				return false;
			}
		}
		if (c < 0xE000)
			return false;
		if (c <= 0xFFFD)
			return true;
		if (c < 0x10000)
			return false;
		if (c <= 0x10FFFF)
			return true;
		return false;
	}
}
