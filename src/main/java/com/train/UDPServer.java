package com.train;


import java.io.IOException;

public class UDPServer {
	public static void main(String args[]) throws IOException {
		System.out.println("������������...");
		// ��ʼ��ServerBean����
		ServerBean server = new ServerBean();
		// ������������
		server.listenClient();
	}
}
