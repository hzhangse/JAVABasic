package com.train.encrypt;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Test {

	public Base64Test() {
		// TODO Auto-generated constructor stub
	}

	private static String Base64EncoderTest(String s) {
		BASE64Encoder encoder = new BASE64Encoder();
		
		String encoded = encoder.encode(s.getBytes());
		System.out.println(s+" encoded -> " + encoded);
        return encoded;
		
	}

	private static void Base64DecoderTest(String s) {
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] bs;
		try {
			bs = decoder.decodeBuffer(s);
			System.out.println(s+" decoded -> " + new String(bs));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		String encoded = Base64EncoderTest("Xue");
		Base64DecoderTest(encoded);
	}

}
