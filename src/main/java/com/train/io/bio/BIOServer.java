package com.train.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;

public class BIOServer {
	public static void main(String[] args) {
		BIOServer xiaona = new BIOServer();

		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			// �������ÿͻ�����������еĳ��ȣ�����5�����г��ȳ���5��ܾ���������
			// serverSocket =
			// ServerSocketFactory.getDefault().createServerSocket(8383, 5);
			serverSocket = ServerSocketFactory.getDefault().createServerSocket(
					8383);

			while (true) {
				try {
					// ����ֱ���������Ӻ󷵻�һ����Socket����
					socket = serverSocket.accept();// ����
					// newһ���̴߳�����������
					new Thread(xiaona.new Worker(socket)).start();
					;
				} catch (Throwable e) { // ��ֹ�����쳣����������
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static String getAnswer(String question) {
		String answer = null;

		switch (question) {
		case "who":
			answer = "����С��";
			break;
		case "what":
			answer = "������������Ƶ�";
			break;
		case "where":
			answer = "��������̫��";
			break;
		default:
			answer = "������ who�� ����what�� ����where";
		}

		return answer;
	}

	private class Worker implements Runnable {
		private Socket socket;

		public Worker(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			BufferedReader reader = null;
			PrintWriter writer = null;

			try {
				reader = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));

				writer = new PrintWriter(socket.getOutputStream());

				String question = reader.readLine();// û�����ݻ�����

				while (!question.equals("OVER")) {
					String answer = getAnswer(question);
					writer.println(answer);
					question = reader.readLine();
				}

				writer.println("OVER");// OVER��Ϊ������ɰ���
				writer.flush();

				if (writer != null) {
					writer.close();
				}

				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
