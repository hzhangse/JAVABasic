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

		// 输入xml文件
		BufferedReader in = new BufferedReader(new FileReader(xmlFile));
		String s;
		StringBuilder xmlSb = new StringBuilder();
		// xml文件转换成String
		while ((s = in.readLine()) != null)
			xmlSb.append(s + "\n");
		in.close();
		String xmlString = xmlSb.toString();
		// TODO Auto-generated method stub
		// 无特殊字符的
		// int i =
		// checkCharacterData("<?xml version=\"1.0\" encoding=\"gbk\"?><CC>卡号</CC>");
		// 有特殊字符的
		// int i =
		// checkCharacterData("<?xml version=\"1.0\" encoding=\"gbk\"?><CC>\u001E卡号</CC>");

		int errorChar = checkCharacterData(xmlString);
		System.out
				.println("This XML　file contain " + errorChar + " errorChar.");
	}

	// 判断字符串中是否有非法字符
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
			
			// 先判断是否在代理范围（surrogate blocks）
			// 增补字符编码为两个代码单元，
			// 第一个单元来自于高代理（high surrogate）范围（0xD800 至 0xDBFF），
			// 第二个单元来自于低代理（low surrogate）范围（0xDC00 至 0xDFFF）。
			if (result >= 0xD800 && result <= 0xDBFF) {
				// 解码代理对（surrogate pair）
				int high = c;
				try {
					int low = text.charAt(i + 1);

					if (low < 0xDC00 || low > 0xDFFF) {
						char ch = (char) low;
					}
					// unicode说明定义的算法 计算出增补字符范围0x10000 至 0x10FFFF
					// 即若result是增补字符集，应该在0x10000到0x10FFFF之间，isXMLCharacter中有判断
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
		// 根据xml规范中的Character Range检测xml不支持的字符
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
