package com.train.io.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * To change this template use File | Settings | File Templates.
 */
public class AFCDemo {
    static Thread current;

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        if (args == null || args.length == 0) {
            System.out.println("Please input file path");
            return;
        }
        Path filePath = Paths.get(args[0]);
        AsynchronousFileChannel afc = AsynchronousFileChannel.open(filePath);
        ByteBuffer byteBuffer = ByteBuffer.allocate(16 * 1024);
//ʹ��FutureDemoʱ����ע�͵�completionHandlerDemo����֮��Ȼ
        futureDemo(afc, byteBuffer);
        completionHandlerDemo(afc, byteBuffer);
    }

    private static void completionHandlerDemo(AsynchronousFileChannel afc, ByteBuffer byteBuffer) throws IOException {
        current = Thread.currentThread();
        afc.read(byteBuffer, 0, null, new CompletionHandler<Integer, Object>() {
            @Override
            public void completed(Integer result, Object attachment) {
                System.out.println("Bytes Read = " + result);
                current.interrupt();
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println(exc.getCause());
                current.interrupt();
            }
        });
        System.out.println("Waiting for completion...");
        try {
            current.join();
        } catch (InterruptedException e) {
        }
        System.out.println("End");
        afc.close();
    }

    private static void futureDemo(AsynchronousFileChannel afc, ByteBuffer byteBuffer) throws InterruptedException, ExecutionException, IOException {
        Future<Integer> result = afc.read(byteBuffer, 0);
        while (!result.isDone()) {
            System.out.println("Waiting file channel finished....");
            Thread.sleep(1);
        }
        System.out.println("Finished? = " + result.isDone());
        System.out.println("byteBuffer = " + result.get());
        System.out.println(byteBuffer);
        afc.close();
    }
}
