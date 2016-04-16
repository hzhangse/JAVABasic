
package com.train.jdk16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.naming.CommunicationException;

import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

public class TestSSL {
	protected final int	timeOut	   = 120 * 1000;
	
	protected String	httpSchema	= "http";
	
	protected String	index	   = "://localhost:44444/register";
	
	protected int executeWithStatus(HttpClient client,
	                HttpMethod method)
	                throws CommunicationException {
		int status = 0;
		try {
			client.getHttpConnectionManager()
			                .getParams()
			                .setConnectionTimeout(
			                                timeOut);
			status = client.executeMethod(method);
			System.out.println(status);
		} catch (HttpException e) {
			throw new CommunicationException(
			                e.getMessage());
		} catch (IOException e) {
			throw new CommunicationException(
			                e.getMessage());
		}
		
		return status;
	}
	
	protected static String getResponse(
	                HttpMethod method) {
		StringBuilder response = new StringBuilder();
		InputStream response_is;
		try {
			response_is = method
			                .getResponseBodyAsStream();
			
			InputStreamReader reader = new InputStreamReader(
			                response_is);
			BufferedReader reader_buffered = new BufferedReader(
			                reader);
			String line = reader_buffered
			                .readLine();
			
			while (line != null) {
				System.out.println(line);
				response.append(line);
				line = reader_buffered
				                .readLine();
			}
			
			reader_buffered.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.toString();
	}
	
	protected String executeWithResponse(
	                HttpClient client, HttpMethod method)
	                throws CommunicationException {
		int status = 0;
		String response = "";
		try {
			client.getHttpConnectionManager()
			                .getParams()
			                .setConnectionTimeout(
			                                timeOut);
			status = client.executeMethod(method);
			
			response = getResponse(method);
		} catch (HttpException e) {
			throw new CommunicationException(
			                e.getMessage());
		} catch (IOException e) {
			throw new CommunicationException(
			                e.getMessage());
		}
		
		return response;
	}
	
	//@Test
	public void testConnect() {
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		HttpClient client = new HttpClient(
		                connectionManager);
		
		PostMethod authpost = new PostMethod(
		                httpSchema + index);
		
		NameValuePair[] data = new NameValuePair[2];
		data[0] = new NameValuePair("j_username",
		                "username");
		data[1] = new NameValuePair("j_password",
		                "password");
		
		authpost.setRequestBody(data);
		try {
			executeWithStatus(client, authpost);
		} catch (Exception e) {
			e.printStackTrace();
			authpost.releaseConnection();
			
		}
		
		// GetMethod getMethod = new GetMethod(httpSchema+index);
		//
		// try {
		// executeWithResponse(client, getMethod);
		// } catch (Exception e) {
		// e.printStackTrace();
		// getMethod.releaseConnection();
		//
		// }
	}
	
	@Test
	public void MultiRequestsTest() {
		// 构造一个Runner
		TestRunnable runner = new TestRunnable() {
			@Override
			public void runTest()
			                throws Throwable {
				// 测试内容
				testConnect();
			}
		};
		int runnerCount = 200;
		// Rnner数组，想当于并发多少个。
		TestRunnable[] trs = new TestRunnable[runnerCount];
		for (int i = 0; i < runnerCount; i++) {
			trs[i] = runner;
		}
		// 用于执行多线程测试用例的Runner，将前面定义的单个Runner组成的数组传入
		MultiThreadedTestRunner mttr = new MultiThreadedTestRunner(
		                trs);
		try {
			// 开发并发执行数组里定义的内容
			mttr.runTestRunnables();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
