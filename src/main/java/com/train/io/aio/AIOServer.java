package com.train.io.aio;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
//import java.nio.channels.WritePendingException;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
 
public class AIOServer {
    private  AsynchronousServerSocketChannel server = null;
    //д���У���Ϊ��ǰһ���첽д���û�û���֮ǰ�������첽д����WritePendingException
    //������Ҫһ��д����������Ҫд������ݣ�����AIO�ȽϿӵĵط�
    private final Queue<ByteBuffer> queue = new LinkedList<ByteBuffer>();
    private boolean writing = false;
     
    public static void main(String[] args) throws IOException{
    	AIOServer xiaona = new AIOServer();
        xiaona.listen();
    }
 
    public AIOServer() throws IOException{
        //�����߳���ΪCPU����
        AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withFixedThreadPool(
        		Runtime.getRuntime().availableProcessors(),
        		Executors.defaultThreadFactory());
        server = AsynchronousServerSocketChannel.open(channelGroup);
        //���ö˿�
        server.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        //�󶨶˿ڲ���������������г���
        server.bind(new InetSocketAddress(8383), 80);    
    }
 
    public void listen() {
        System.out.println(Thread.currentThread().getName() + ": run in listen method" );
        //��ʼ���ܵ�һ����������
        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>(){                       
            @Override
            public void completed(AsynchronousSocketChannel channel,
                    Object attachment) {
                System.out.println(Thread.currentThread().getName() + ": run in accept completed method" );
                 
                //�Ȱ��Ŵ�����һ�����������첽���������ã����Բ��õ��Ĺ�ס��
                //���ﴫ��this�Ǹ����ף�С�Ķ��߳�
                server.accept(null, this);
                //�������Ӷ�д
                handle(channel);
            }
 
            private void handle(final AsynchronousSocketChannel channel) {
                System.out.println(Thread.currentThread().getName() + ": run in handle method" );
                //ÿ��AsynchronousSocketChannel������һ��������
                final ByteBuffer readBuffer = ByteBuffer.allocateDirect(1024);
                readBuffer.clear();
                channel.read(readBuffer, null, new CompletionHandler<Integer, Object>(){
 
                    @Override
                    public void completed(Integer count, Object attachment) {
                        System.out.println(Thread.currentThread().getName() + ": run in read completed method" );  
                         
                        if(count > 0){
                            try{
                                readBuffer.flip();
                                //CharBuffer charBuffer = CharsetHelper.decode(readBuffer); 
                                CharBuffer charBuffer = Charset.forName("UTF-8").newDecoder().decode(readBuffer);
                                String question = charBuffer.toString(); 
                                String answer = Helper.getAnswer(question);
                                /*//д��Ҳ���첽���ã�Ҳ����ʹ�ô���CompletionHandler����ķ�ʽ������д����
                                //channel.write(CharsetHelper.encode(CharBuffer.wrap(answer)));                            
                                try{
                                    channel.write(Charset.forName("UTF-8").newEncoder().encode(CharBuffer.wrap(answer)));
                                }
                                //Unchecked exception thrown when an attempt is made to write to an asynchronous socket channel and a previous write has not completed.
                                //��������ϵͳҲ���ɿ�
                                catch(WritePendingException wpe){
                                    //��Ϣһ�������ԣ����ʧ�ܾͲ�����
                                    Helper.sleep(1);
                                    channel.write(Charset.forName("UTF-8").newEncoder().encode(CharBuffer.wrap(answer)));
                                }*/
                                writeStringMessage(channel, answer);
                                 
                                readBuffer.clear();
                            }
                            catch(IOException e){
                                e.printStackTrace();
                            }
                        }
                        else{
                            try {
                                //����ͻ��˹ر�socket����ô������Ҳ��Ҫ�رգ������˷�CPU
                                channel.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                         
                        //�첽����OS�����¸���ȡ����
                        //���ﴫ��this�Ǹ����ף�С�Ķ��߳�
                        channel.read(readBuffer, null, this);
                    }
 
                    /**
                     * ��������ʧ�ܴ���
                     * @param exc
                     * @param attachment
                     */
                    @Override
                    public void failed(Throwable exc, Object attachment) {
                        System.out.println("server read failed: " + exc);         
                        if(channel != null){
                            try {
                                channel.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                     
                });                            
            }
 
            /**
             * ��������������ʧ�ܴ���
             * @param exc
             * @param attachment
             */
            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("server accept failed: " + exc);
            }
             
        });
    }
     
    /**
     * Enqueues a write of the buffer to the channel.
     * The call is asynchronous so the buffer is not safe to modify after
     * passing the buffer here.
     *
     * @param buffer the buffer to send to the channel
     */
    private void writeMessage(final AsynchronousSocketChannel channel, final ByteBuffer buffer) {
        boolean threadShouldWrite = false;
 
        synchronized(queue) {
            queue.add(buffer);
            // Currently no thread writing, make this thread dispatch a write
            if (!writing) {
                writing = true;
                threadShouldWrite = true;
            }
        }
 
        if (threadShouldWrite) {
            writeFromQueue(channel);
        }
    }
 
    private void writeFromQueue(final AsynchronousSocketChannel channel) {
        ByteBuffer buffer;
 
        synchronized (queue) {
            buffer = queue.poll();
            if (buffer == null) {
                writing = false;
            }
        }
 
        // No new data in buffer to write
        if (writing) {
            writeBuffer(channel, buffer);
        }
    }
 
    private void writeBuffer(final AsynchronousSocketChannel channel, ByteBuffer buffer) {
        channel.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer buffer) {
                if (buffer.hasRemaining()) {
                    channel.write(buffer, buffer, this);
                } else {
                    // Go back and check if there is new data to write
                    writeFromQueue(channel);
                }
            }
 
            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("server write failed: " + exc);
            }
        });
    }
 
    /**
     * Sends a message
     * @param string the message
     * @throws CharacterCodingException 
     */
    private void writeStringMessage(final AsynchronousSocketChannel channel, String msg) throws CharacterCodingException {
        writeMessage(channel, Charset.forName("UTF-8").newEncoder().encode(CharBuffer.wrap(msg)));
    }
}