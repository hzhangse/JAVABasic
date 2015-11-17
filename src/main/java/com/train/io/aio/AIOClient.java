package com.train.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

public class AIOClient implements Runnable {

	private AsynchronousSocketChannel channel;
	private Helper helper;
	private CountDownLatch latch;
	private final Queue<ByteBuffer> queue = new LinkedList<ByteBuffer>();
	private boolean writing = false;

	public AIOClient(AsynchronousChannelGroup channelGroup, CountDownLatch latch)
			throws IOException, InterruptedException {
		this.latch = latch;
		helper = new Helper();
		initChannel(channelGroup);
	}

	private void initChannel(AsynchronousChannelGroup channelGroup)
			throws IOException {
		// ��Ĭ��channel group�´���һ��socket channel
		channel = AsynchronousSocketChannel.open(channelGroup);
		// ����Socketѡ��
		channel.setOption(StandardSocketOptions.TCP_NODELAY, true);
		channel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
		channel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
	}

	public static void main(String[] args) throws IOException,
			InterruptedException {
		int sleepTime = Integer.parseInt(args[0]);
		Helper.sleep(sleepTime);

		AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup
				.withFixedThreadPool(
						Runtime.getRuntime().availableProcessors(),
						Executors.defaultThreadFactory());
		// ֻ����һ���̣߳��ڶ����߳�connect���ס����ʱ����ԭ��
		final int THREAD_NUM = 1;
		CountDownLatch latch = new CountDownLatch(THREAD_NUM);

		// ���������߳�ģ�����ͻ��ˣ�ģ��ʧ�ܣ���Ч
		// ֻ��ͨ��������ͬʱ���ж��������ģ�����ͻ���
		for (int i = 0; i < THREAD_NUM; i++) {
			AIOClient c = new AIOClient(channelGroup, latch);
			Thread t = new Thread(c);
			System.out.println(t.getName() + "---start");
			t.start();
			// �����̵߳ȴ����̴߳������˳�, ������첽������Ч
			// t.join();
		}

		latch.await();

		if (channelGroup != null) {
			channelGroup.shutdown();
		}
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + "---run");

		// ���ӷ�����
		channel.connect(new InetSocketAddress("localhost", 8383), null,
				new CompletionHandler<Void, Void>() {
					final ByteBuffer readBuffer = ByteBuffer
							.allocateDirect(1024);

					@Override
					public void completed(Void result, Void attachment) {
						// ���ӳɹ���, �첽����OS�������дһ����Ϣ
						try {
							// channel.write(CharsetHelper.encode(CharBuffer.wrap(helper.getWord())));
							writeStringMessage(helper.getWord());
						} catch (CharacterCodingException e) {
							e.printStackTrace();
						}

						// helper.sleep();//�ȴ�д�첽�������
						readBuffer.clear();
						// �첽����OS��ȡ���������͵���Ϣ
						channel.read(readBuffer, null,
								new CompletionHandler<Integer, Object>() {

									@Override
									public void completed(Integer result,
											Object attachment) {
										try {
											// �첽��ȡ��ɺ���
											if (result > 0) {
												readBuffer.flip();
												CharBuffer charBuffer = CharsetHelper
														.decode(readBuffer);
												String answer = charBuffer
														.toString();
												System.out.println(Thread
														.currentThread()
														.getName()
														+ "---" + answer);
												readBuffer.clear();

												String word = helper.getWord();
												if (word != null) {
													// �첽д
													// channel.write(CharsetHelper.encode(CharBuffer.wrap(word)));
													writeStringMessage(word);
													// helper.sleep();//�ȴ��첽����
													channel.read(readBuffer,
															null, this);
												} else {
													// ���뷢��Ϣ�ˣ������ر�channel
													shutdown();
												}
											} else {
												// �Է��Ѿ��ر�channel���Լ������رգ������ѭ��
												shutdown();
											}
										} catch (Exception e) {
											e.printStackTrace();
										}
									}

									/**
									 * ��ȡʧ�ܴ���
									 * 
									 * @param exc
									 * @param attachment
									 */
									@Override
									public void failed(Throwable exc,
											Object attachment) {
										System.out
												.println("client read failed: "
														+ exc);
										try {
											shutdown();
										} catch (IOException e) {
											e.printStackTrace();
										}
									}

								});
					}

					/**
					 * ����ʧ�ܴ���
					 * 
					 * @param exc
					 * @param attachment
					 */
					@Override
					public void failed(Throwable exc, Void attachment) {
						System.out.println("client connect to server failed: "
								+ exc);

						try {
							shutdown();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
	}

	private void shutdown() throws IOException {
		if (channel != null) {
			channel.close();
		}

		latch.countDown();
	}

	/**
	 * Enqueues a write of the buffer to the channel. The call is asynchronous
	 * so the buffer is not safe to modify after passing the buffer here.
	 *
	 * @param buffer
	 *            the buffer to send to the channel
	 */
	private void writeMessage(final ByteBuffer buffer) {
		boolean threadShouldWrite = false;

		synchronized (queue) {
			queue.add(buffer);
			// Currently no thread writing, make this thread dispatch a write
			if (!writing) {
				writing = true;
				threadShouldWrite = true;
			}
		}

		if (threadShouldWrite) {
			writeFromQueue();
		}
	}

	private void writeFromQueue() {
		ByteBuffer buffer;

		synchronized (queue) {
			buffer = queue.poll();
			if (buffer == null) {
				writing = false;
			}
		}

		// No new data in buffer to write
		if (writing) {
			writeBuffer(buffer);
		}
	}

	private void writeBuffer(ByteBuffer buffer) {
		channel.write(buffer, buffer,
				new CompletionHandler<Integer, ByteBuffer>() {
					@Override
					public void completed(Integer result, ByteBuffer buffer) {
						if (buffer.hasRemaining()) {
							channel.write(buffer, buffer, this);
						} else {
							// Go back and check if there is new data to write
							writeFromQueue();
						}
					}

					@Override
					public void failed(Throwable exc, ByteBuffer attachment) {
					}
				});
	}

	/**
	 * Sends a message
	 * 
	 * @param string
	 *            the message
	 * @throws CharacterCodingException
	 */
	public void writeStringMessage(String msg) throws CharacterCodingException {
		writeMessage(Charset.forName("UTF-8").newEncoder()
				.encode(CharBuffer.wrap(msg)));
	}
}