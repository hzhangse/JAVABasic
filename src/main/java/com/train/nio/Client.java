package com.train.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class Client implements Runnable {
	// ���м�����,������г���10��,�����server�Ƿ��ж�����.
	private static int idleCounter = 0;
	private Selector selector;
	private SocketChannel socketChannel;
	private ByteBuffer temp = ByteBuffer.allocate(1024);

	public static void main(String[] args) throws IOException {
		Client client = new Client();
		for (int i = 0; i < 100; i++) {
			new Thread(client).start();
		}
		// client.sendFirstMsg();
	}

	public Client() throws IOException {
		// ͬ����,ע������.
		this.selector = Selector.open();

		// ����Զ��server
		socketChannel = SocketChannel.open();
		// ������ٵĽ���������,����true.���û�н���,�򷵻�false,�������Ӻ����Connect�¼�.
		Boolean isConnected = socketChannel.connect(new InetSocketAddress(
				"localhost", 7878));
		socketChannel.configureBlocking(false);
		SelectionKey key = socketChannel.register(selector,
				SelectionKey.OP_READ);

		if (isConnected) {
			this.sendFirstMsg();
		} else {
			// ������ӻ��ڳ�����,��ע��connect�¼��ļ���. connect�ɹ��Ժ�����connect�¼�.
			key.interestOps(SelectionKey.OP_CONNECT);
		}
	}

	public void sendFirstMsg() throws IOException {
		String msg = "Hello NIO.";
		socketChannel.write(ByteBuffer.wrap(msg.getBytes(Charset
				.forName("UTF-8"))));
	}

	@Override
	public void run() {
		while (true) {
			try {
				// ����,�ȴ��¼�����,����1�볬ʱ. numΪ�����¼�������.
				int num = this.selector.select(1000);
				if (num == 0) {
					idleCounter++;
					if (idleCounter > 10) {
						// ���server�Ͽ�������,������Ϣ��ʧ��.
						try {
							this.sendFirstMsg();
						} catch (ClosedChannelException e) {
							e.printStackTrace();
							this.socketChannel.close();
							return;
						}
					}
					continue;
				} else {
					idleCounter = 0;
				}
				Set<SelectionKey> keys = this.selector.selectedKeys();
				Iterator<SelectionKey> it = keys.iterator();
				while (it.hasNext()) {
					SelectionKey key = it.next();
					it.remove();
					if (key.isConnectable()) {
						// socket connected
						SocketChannel sc = (SocketChannel) key.channel();
						if (sc.isConnectionPending()) {
							sc.finishConnect();
						}
						// send first message;
						this.sendFirstMsg();
					}
					if (key.isReadable()) {
						// msg received.
						SocketChannel sc = (SocketChannel) key.channel();
						this.temp = ByteBuffer.allocate(1024);
						int count = sc.read(temp);
						if (count < 0) {
							sc.close();
							continue;
						}
						// �л�buffer����״̬,�ڲ�ָ���λ.
						temp.flip();
						String msg = Charset.forName("UTF-8").decode(temp)
								.toString();
						System.out.println("Client received [" + msg
								+ "] from server address:"
								+ sc.socket().getRemoteSocketAddress());

						Thread.sleep(1000);
						// echo back.
						sc.write(ByteBuffer.wrap(msg.getBytes(Charset
								.forName("UTF-8"))));

						// ���buffer
						temp.clear();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}