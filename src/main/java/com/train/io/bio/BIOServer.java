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
			// 可以设置客户连接请求队列的长度，比如5，队列长度超过5后拒绝连接请求
			// serverSocket =
			// ServerSocketFactory.getDefault().createServerSocket(8383, 5);
			serverSocket = ServerSocketFactory.getDefault().createServerSocket(
					8383);

			while (true) {
				try {
					// 监听直到接受连接后返回一个新Socket对象
					socket = serverSocket.accept();// 阻塞
					// new一个线程处理连接请求
					new Thread(xiaona.new Worker(socket)).start();
					;
				} catch (Throwable e) { // 防止发生异常搞死服务器
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
			answer = "我是小娜";
			break;
		case "what":
			answer = "我是来帮你解闷的";
			break;
		case "where":
			answer = "我来自外太空";
			break;
		default:
			answer = "请输入 who， 或者what， 或者where";
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

				String question = reader.readLine();// 没有内容会阻塞

				while (!question.equals("OVER")) {
					String answer = getAnswer(question);
					writer.println(answer);
					question = reader.readLine();
				}

				writer.println("OVER");// OVER作为操作完成暗号
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
