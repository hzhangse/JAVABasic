package com.train;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientBean {
	// ����UDPͨѶ��DatagramSocket����
	private DatagramSocket ds;
	// ������װͨѶ�ַ���
	private byte buffer[];
	// �ͻ��˵Ķ˿ں�
	private int clientport;
	// �������˵Ķ˿ں�
	private int serverport;
	// ͨѶ����
	private String content;
	// ����ͨѶ��ַ
	private InetAddress ia;

	// �����Ǹ����Ե�Get��Set���ͷ���
	public byte[] getBuffer() {
		return buffer;
	}

	public void setBuffer(byte[] buffer) {
		this.buffer = buffer;
	}

	public int getClientport() {
		return clientport;
	}

	public void setClientport(int clientport) {
		this.clientport = clientport;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public DatagramSocket getDs() {
		return ds;
	}

	public void setDs(DatagramSocket ds) {
		this.ds = ds;
	}

	public InetAddress getIa() {
		return ia;
	}

	public void setIa(InetAddress ia) {
		this.ia = ia;
	}

	public int getServerport() {
		return serverport;
	}

	public void setServerport(int serverport) {
		this.serverport = serverport;
	}

	public ClientBean() throws SocketException, UnknownHostException {
		buffer = new byte[1024];
		clientport = 1985;
		serverport = 1986;
		content = "";
		ds = new DatagramSocket(clientport);
		ia = InetAddress.getByName("localhost");
	}

	public void sendToServer() throws IOException {
		buffer = content.getBytes();
		ds.send(new DatagramPacket(buffer, content.length(), ia, serverport));
	}

}
