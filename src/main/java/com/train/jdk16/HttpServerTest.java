
package com.train.jdk16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.httpclient.HttpStatus;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HttpServerTest {
	static AtomicInteger	count	= new AtomicInteger(
	                                              0);
	
	public static void httpServerService()
	                throws IOException {
		ExecutorService pool = Executors
		                .newCachedThreadPool();
		HttpServer server = HttpServer
		                .create(new InetSocketAddress(
		                                44444), 5);// ���10����10����������
		server.createContext("/register",
		                new MyRequestHandle());
		// ����ͨ������һ���߳������߳�������ִ�еĹ���
		server.setExecutor(pool);
		server.start();
		System.out.println("server started:");
	}
	
	static class MyRequestHandle implements HttpHandler {
		@Override
		public void handle(HttpExchange exc)
		                throws IOException {
			System.err.println("�߳�����"
			                + Thread.currentThread()
			                                .getName());
			InputStream in = exc
			                .getRequestBody();// ����������������
			BufferedReader reader = new BufferedReader(
			                new InputStreamReader(
			                                in));// ���������
			String requestMessage = reader
			                .readLine();
			
			String value = URLDecoder
			                .decode(
			                                requestMessage
			                                                + ":"
			                                                + count.getAndIncrement(),
			                                "UTF-8");
			
			try {
				 String responseString = "<font color='#ff0000'>Hello! This a HttpServer!</font>"; 
			            //������Ӧͷ  
			            exc.sendResponseHeaders(HttpStatus.SC_OK, responseString.length());    
			            OutputStream os = exc.getResponseBody();    
			            os.write(responseString.getBytes());    
			            os.close();  
//				exc.getResponseHeaders().add("Content-Disposition", "attachment; filename="); 
//				
//				exc.getResponseBody()
//				                .write(value.getBytes());
			            exc.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			System.out.println(value);
			
			// exc.close();
		}
	}
	
	public static void main(String[] args) {
		try {
			HttpServerTest.httpServerService();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}