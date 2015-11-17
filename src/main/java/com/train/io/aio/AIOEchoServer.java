package com.train.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class AIOEchoServer {
    private AsynchronousServerSocketChannel server;

    public static void main(String[] args) throws IOException {
        AIOEchoServer aioServer = new AIOEchoServer();
        aioServer.init("localhost", 6025);
    }

    private void init(String host, int port) throws IOException {
        //ChannelGroup������������Դ
        AsynchronousChannelGroup group = AsynchronousChannelGroup.withCachedThreadPool(Executors.newCachedThreadPool(), Runtime.getRuntime().availableProcessors());
        server = AsynchronousServerSocketChannel.open(group);
        //ͨ��setOption����Socket
        server.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        server.setOption(StandardSocketOptions.SO_RCVBUF, 16 * 1024);
        //�󶨵�ָ�����������˿�
        server.bind(new InetSocketAddress(host, port));
        System.out.println("Listening on " + host + ":" + port);
        //���provider
        System.out.println("Channel Provider : " + server.provider());
        //�ȴ����ӣ���ע��CompletionHandler�����ں���ɺ�Ĳ�����
        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            final ByteBuffer buffer = ByteBuffer.allocate(1024);

            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                System.out.println("waiting....");
                buffer.clear();
                try {
                    //��socket�е����ݶ�ȡ��buffer��
                    result.read(buffer).get();
                    buffer.flip();
                    System.out.println("Echo " + new String(buffer.array()).trim() + " to " + result);
                   
                    //���յ���ֱ�ӷ��ظ��ͻ���
                    result.write(buffer);
                    buffer.flip();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        //�رմ������socket�������µ���accept�ȴ��µ�����
                        result.close();
                        server.accept(null, this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.print("Server failed...." + exc.getCause());
            }
        });

        //��ΪAIO�����������ý��̣���˱��������������������ܱ��ֽ��̴�
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}