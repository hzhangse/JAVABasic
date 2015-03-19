package com.train;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

	/* ��ʶ���� */
	private int flag = 0;
	/* ��������С */
	private int BLOCK = 4096;
	/* �������ݻ����� */
	private ByteBuffer sendbuffer = ByteBuffer.allocate(BLOCK);
	/* �������ݻ����� */
	private ByteBuffer receivebuffer = ByteBuffer.allocate(BLOCK);
	private Selector selector;

	public NIOServer(int port) throws IOException {
		// �򿪷������׽���ͨ��
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		// ����������Ϊ������
		serverSocketChannel.configureBlocking(false);
		// �������ͨ�������ķ������׽���
		ServerSocket serverSocket = serverSocketChannel.socket();
		// ���з���İ�
		serverSocket.bind(new InetSocketAddress(port));
		// ͨ��open()�����ҵ�Selector
		selector = Selector.open();
		// ע�ᵽselector���ȴ�����
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("Server Start----8888:");
	}

	// ����
	private void listen() throws IOException {
		while (true) {
			// ѡ��һ�����������Ӧ��ͨ���Ѿ���
			selector.select();
			// ���ش�ѡ��������ѡ�������
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = selectionKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();
				iterator.remove();
				handleKey(selectionKey);
			}
		}
	}

	// ��������
	private void handleKey(SelectionKey selectionKey) throws IOException {
		// ��������
		ServerSocketChannel server = null;
		SocketChannel client = null;
		String receiveText;
		String sendText;
		int count = 0;
		// ���Դ˼���ͨ���Ƿ���׼���ý����µ��׽������ӡ�
		if (selectionKey.isAcceptable()) {
			// ����Ϊ֮�����˼���ͨ����
			server = (ServerSocketChannel) selectionKey.channel();
			// ���ܵ���ͨ���׽��ֵ����ӡ�
			// �˷������ص��׽���ͨ��������У�����������ģʽ��
			client = server.accept();
			// ����Ϊ������
			client.configureBlocking(false);
			// ע�ᵽselector���ȴ�����
			client.register(selector, SelectionKey.OP_READ);
		} else if (selectionKey.isReadable()) {
			// ����Ϊ֮�����˼���ͨ����
			client = (SocketChannel) selectionKey.channel();
			// ������������Ա��´ζ�ȡ
			receivebuffer.clear();
			// ��ȡ�����������������ݵ���������
			count = client.read(receivebuffer);
			if (count > 0) {
				receiveText = new String(receivebuffer.array(), 0, count);
				System.out.println("�������˽��ܿͻ�������--:" + receiveText);
				client.register(selector, SelectionKey.OP_WRITE);
			}
		} else if (selectionKey.isWritable()) {
			// ������������Ա��´�д��
			sendbuffer.clear();
			// ����Ϊ֮�����˼���ͨ����
			client = (SocketChannel) selectionKey.channel();
			sendText = "message from server--" + flag++;
			// �򻺳�������������
			sendbuffer.put(sendText.getBytes());
			// ������������־��λ,��Ϊ������put�����ݱ�־���ı�Ҫ����ж�ȡ���ݷ��������,��Ҫ��λ
			sendbuffer.flip();
			// �����ͨ��
			client.write(sendbuffer);
			System.out.println("����������ͻ��˷�������--��" + sendText);
			client.register(selector, SelectionKey.OP_READ);
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int port = 8888;
		NIOServer server = new NIOServer(port);
		server.listen();
	}

}
