package com.train;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UDPClient extends Thread {
	public static String content;
	public static ClientBean client;

	public void run() {
		try {
			client.setContent(content);
			client.sendToServer();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}// end of run

	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		client = new ClientBean();
		System.out.println("�ͻ�������...");
		while (true) {
			// �����û�����
			content = br.readLine();
			// �����end���,�˳�ѭ��
			if (content == null || content.equalsIgnoreCase("end")
					|| content.equalsIgnoreCase("")) {
				break;
			}
			// �������̣߳�������Ϣ
			new Thread(new UDPClient()).start();
		}
	}
}
