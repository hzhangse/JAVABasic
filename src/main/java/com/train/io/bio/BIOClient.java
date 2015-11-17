package com.train.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BIOClient {

	public static void main(String[] args) {
		BIOClient c = new BIOClient();

		// 种20个线程发起Socket客户端连接请求
		for (int i = 0; i < 20; i++) {
			new Thread(c.new Worker()).start();
		}
	}

	private class Worker implements Runnable {

		@Override
		public void run() {
			Socket socket = null;
			BufferedReader reader = null;
			PrintWriter writer = null;

			try {
				// 创建一个Socket并连接到指定的目标服务器
				socket = new Socket("localhost", 8383);

				reader = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream());

				writer.println("who");
				writer.println("what");
				writer.println("where");
				writer.println("OVER");// OVER作为操作完成暗号
				writer.flush();

				String answer = reader.readLine(); // 没有内容会阻塞
				while (!answer.equals("OVER")) {
					System.out.println(Thread.currentThread().getId()
							+ "---Message from server:" + answer);
					answer = reader.readLine();
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (writer != null) {
						writer.close();
					}

					if (reader != null) {
						reader.close();
					}

					if (socket != null) {
						socket.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}