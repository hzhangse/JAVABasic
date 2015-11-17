package com.train.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOServer {

	private ByteBuffer readBuffer;
	private Selector selector;

	public static void main(String[] args) {
		NIOServer xiaona = new NIOServer();
		xiaona.init();
		xiaona.listen();
	}

	private void init() {
		readBuffer = ByteBuffer.allocate(1024);
		ServerSocketChannel servSocketChannel;

		try {
			servSocketChannel = ServerSocketChannel.open();
			servSocketChannel.configureBlocking(false);
			// �󶨶˿�
			servSocketChannel.socket().bind(new InetSocketAddress(8383));

			selector = Selector.open();
			servSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void listen() {
		while (true) {
			try {
				selector.select();
				Iterator ite = selector.selectedKeys().iterator();

				while (ite.hasNext()) {
					SelectionKey key = (SelectionKey) ite.next();
					ite.remove();// ȷ�����ظ�����

					handleKey(key);
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}
	
	
	 private void handleKey(SelectionKey key)
	            throws IOException, ClosedChannelException {
	        SocketChannel channel = null;
	         
	        try{
	            if(key.isAcceptable()){
	                ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
	                channel = serverChannel.accept();//������������
	                channel.configureBlocking(false);
	                channel.register(selector, SelectionKey.OP_READ);
	            }
	            else if(key.isReadable()){
	                channel = (SocketChannel) key.channel();
	                readBuffer.clear();
	                /*���ͻ���channel�رպ󣬻᲻���յ�read�¼�����û����Ϣ����read��������-1
	                 * ������ʱ��������Ҳ��Ҫ�ر�channel������������Ч�Ĵ���*/              
	                int count = channel.read(readBuffer);
	                 
	                if(count > 0){
	                    //һ����Ҫ����flip�����������ȡ��������
	                    readBuffer.flip();
	                    /*ʹ��CharBuffer���ȡ����ȷ������
	                    String question = new String(readBuffer.array());  
	                    ���ܻ������Ϊǰ��readBuffer.clear();��δ������������
	                    ֻ�����û�������position, limit, mark��
	                    ��readBuffer.array()�᷵�����������������ݡ�
	                    decode����ֻȡreadBuffer��position��limit���ݡ�
	                    ���磬��һ�ζ�ȡ������������"where", clear��positionΪ0��limitΪ 1024��
	                    �ٴζ�ȡ��bye"����������positionΪ3��limit���䣬
	                    flip��positionΪ0��limitΪ3��ǰ�����ַ��������ˣ���"re"�����ڻ������У�
	                    ���� new String(readBuffer.array()) ���� "byere",
	                    ��decode(readBuffer)����"bye"��            
	                    */
	                    CharBuffer charBuffer = CharsetHelper.decode(readBuffer); 
	                    String question = charBuffer.toString(); 
	                    String answer = getAnswer(question);
	                    channel.write(CharsetHelper.encode(CharBuffer.wrap(answer)));
	                }
	                else{
	                    //����ر�channel����Ϊ�ͻ����Ѿ��ر�channel�����쳣��
	                    channel.close();               
	                }                      
	            }
	        }
	        catch(Throwable t){
	            t.printStackTrace();
	            if(channel != null){
	                channel.close();
	            }
	        }      
	    }

	private String getAnswer(String question) {
		String answer = null;

		switch (question) {
		case "who":
			answer = "����С��\n";
			break;
		case "what":
			answer = "������������Ƶ�\n";
			break;
		case "where":
			answer = "��������̫��\n";
			break;
		case "hi":
			answer = "hello\n";
			break;
		case "bye":
			answer = "88\n";
			break;
		default:
			answer = "������ who�� ����what�� ����where";
		}

		return answer;
	}
}