package com.train.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class EchoServer {
    public static SelectorLoop connectionBell;
    public static SelectorLoop readBell;
    public boolean isReadBellRunning=false;
 
    public static void main(String[] args) throws IOException {
        new EchoServer().startServer();
    }
     
    // ����������
    public void startServer() throws IOException {
        // ׼����һ������.�������ӽ�����ʱ����.
        connectionBell = new SelectorLoop();
         
        // ׼����һ����װ,����read�¼�������ʱ����.
        readBell = new SelectorLoop();
         
        // ����һ��server channel������
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // ����������ģʽ
        ssc.configureBlocking(false);
         
        ServerSocket socket = ssc.socket();
        socket.bind(new InetSocketAddress("localhost",7878));
         
        // �����ӹ涨��Ҫ����������¼�,�������ֻ�����������¼�.
        ssc.register(connectionBell.getSelector(), SelectionKey.OP_ACCEPT);
        new Thread(connectionBell).start();
    }
     
    // Selector��ѯ�߳���
    public class SelectorLoop implements Runnable {
        private Selector selector;
        private ByteBuffer temp = ByteBuffer.allocate(1024);
         
        public SelectorLoop() throws IOException {
            this.selector = Selector.open();
        }
         
        public Selector getSelector() {
            return this.selector;
        }
 
        @Override
        public void run() {
            while(true) {
                try {
                    // ����,ֻ�е�����һ��ע����¼�������ʱ��Ż����.
                    this.selector.select();
                     
                    Set<SelectionKey> selectKeys = this.selector.selectedKeys();
                    Iterator<SelectionKey> it = selectKeys.iterator();
                    while (it.hasNext()) {
                        SelectionKey key = it.next();
                        it.remove();
                        // �����¼�. �����ö��߳�������.
                        this.dispatch(key);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
         
        public void dispatch(SelectionKey key) throws IOException, InterruptedException {
            if (key.isAcceptable()) {
                // ����һ��connection accept�¼�, ��������¼���ע����serversocketchannel�ϵ�.
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                // ����һ������.
                SocketChannel sc = ssc.accept();
                 
                // ���µ����ӵ�channelע��read�¼�. ʹ��readBell����.
                sc.configureBlocking(false);
                sc.register(readBell.getSelector(), SelectionKey.OP_READ);
                 
                // �����ȡ�̻߳�û������,�Ǿ�����һ����ȡ�߳�.
                synchronized(EchoServer.this) {
                    if (!EchoServer.this.isReadBellRunning) {
                        EchoServer.this.isReadBellRunning = true;
                        new Thread(readBell).start();
                    }
                }
                 
            } else if (key.isReadable()) {
                // ����һ��read�¼�,��������¼���ע����socketchannel�ϵ�.
                SocketChannel sc = (SocketChannel) key.channel();
                // д���ݵ�buffer
                int count = sc.read(temp);
                if (count < 0) {
                    // �ͻ����Ѿ��Ͽ�����.
                    key.cancel();
                    sc.close();
                    return;
                }
                // �л�buffer����״̬,�ڲ�ָ���λ.
                temp.flip();
                String msg = Charset.forName("UTF-8").decode(temp).toString();
                System.out.println("Server received ["+msg+"] from client address:" + sc.socket().getRemoteSocketAddress());
                 
                Thread.sleep(1000);
                // echo back.
                sc.write(ByteBuffer.wrap(msg.getBytes(Charset.forName("UTF-8"))));
                 
                // ���buffer
                temp.clear();
            }
        }
         
    }
 
}