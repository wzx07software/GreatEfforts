package com.popsun.io.buffer;

import java.nio.ByteBuffer;

/**
 * @author 吴志祥
 * @create 2020-03-25 15:49
 */
public class ByteBufferDemo {

    public static void main(String[] args) {
        ByteBuffer demoByteBuffer = ByteBuffer.allocate(8);
        printBufferProperties("Before write to demoByteBuffer", demoByteBuffer);
        //put to buffer 5 bytes utf-8 编码
        demoByteBuffer.put("hello".getBytes());
        printBufferProperties("After  write to demoByteBuffer", demoByteBuffer);
        //invoke flip
        demoByteBuffer.flip();
        printBufferProperties("After  flip     demoByteBuffer", demoByteBuffer);
        byte[] temp = new byte[demoByteBuffer.limit()];
        int index = 0;
        while (demoByteBuffer.hasRemaining()) {
            temp[index] = demoByteBuffer.get();
            index++;
        }
        printBufferProperties("After read from demoByteBuffer", demoByteBuffer);
        demoByteBuffer.flip();
        printBufferProperties("After  flip     demoByteBuffer", demoByteBuffer);
        demoByteBuffer.clear();
        printBufferProperties("After  clear    demoByteBuffer", demoByteBuffer);
        System.out.println(new String(temp));


        ByteBuffer demoDirectByteBuffer = ByteBuffer.allocateDirect(8);//A
        printBufferProperties("Before write to demoDirectByteBuffer", demoDirectByteBuffer);
        //put to buffer 5 bytes utf-8 编码
        demoDirectByteBuffer.put("hello".getBytes());
        printBufferProperties("After  write to demoDirectByteBuffer", demoDirectByteBuffer);
        //invoke flip
        demoDirectByteBuffer.flip();
        printBufferProperties("After  flip     demoDirectByteBuffer", demoDirectByteBuffer);
        temp = new byte[demoDirectByteBuffer.limit()];
        index = 0;
        while (demoDirectByteBuffer.hasRemaining()) {
            temp[index] = demoDirectByteBuffer.get();
            index++;
        }
        printBufferProperties("After read from demoDirectByteBuffer", demoDirectByteBuffer);

        System.out.println(new String(temp));

    }

    private static void printBufferProperties(String des, ByteBuffer target) {
        System.out.println(String.format("%s -- position:%d,limit:%d,capacity:%s", des, target.position(), target.limit(), target.capacity()));
    }
}
