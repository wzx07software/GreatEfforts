package com.popsun.io.buffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author 吴志祥
 * @create 2020-03-25 16:54
 */
public class FileChannelOpenStudy {
    public static final String JAVA_NIO = "java NIO";
    static String createAndWriteAndReadPath = "CreateAndWriteAndReadPath.txt";



    public static void main(String[] args) {
        try {
            FileChannel fileChannel = FileChannel.open(Paths.get("HelloWorld"),StandardOpenOption.READ,StandardOpenOption.WRITE);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1000);
            System.out.println(fileChannel.read(byteBuffer));
            byteBuffer.flip();
            System.out.println("从文件中读取到的内容如下：" + new String(readBytesFrromBuffer(byteBuffer)));
            byteBuffer = ByteBuffer.allocateDirect(1000);
            byteBuffer.put("这个是吴志祥的测试数据，我也不清楚能不能成功，先随便谢谢".getBytes(Charset.forName("UTF-8")));
            byteBuffer.flip();
            fileChannel.write(byteBuffer);




            //create and write
            FileChannel createAndWriteChannel = FileChannel.open(Paths.get(createAndWriteAndReadPath), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            ByteBuffer writeBuffer = ByteBuffer.allocate(6);
            writeBuffer.put("hello,".getBytes(Charset.forName("UTF-8")));
            writeBuffer.flip();
            int writed1 = createAndWriteChannel.write(writeBuffer);
            System.out.println("CreateAndWriteAndReadPath.txt has been writen in " + writed1 + " bytes");

            FileChannel readChannel = FileChannel.open(Paths.get(createAndWriteAndReadPath), StandardOpenOption.READ);
            ByteBuffer readBuffer = ByteBuffer.allocate(writed1);
            int readed1 = readChannel.read(readBuffer);
            readBuffer.flip();
            System.out.println("ReadChannel read " + readed1 + " bytes:" + new String(readBytesFrromBuffer(readBuffer)));

            FileChannel appendChannel = FileChannel.open(Paths.get(createAndWriteAndReadPath), StandardOpenOption.APPEND);
            ByteBuffer appendBuffer = ByteBuffer.allocate(JAVA_NIO.length());
            appendBuffer.put(JAVA_NIO.getBytes("UTF-8"));
            appendBuffer.flip();
            int writed2 = appendChannel.write(appendBuffer);
            System.out.println("appendChannel writed in " + writed2 + " bytes");

            ByteBuffer readBuffer1 = ByteBuffer.allocate(writed2);
            int readed2 = readChannel.read(readBuffer1, readed1);
            readBuffer1.flip();
            System.out.println("readChannel readed " + readed2 + " bytes:" +
                    new String(readBytesFrromBuffer(readBuffer1)));

            appendChannel.close();
            readChannel.close();
            createAndWriteChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] readBytesFrromBuffer(ByteBuffer byteBuffer) {
        byte[] result = new byte[byteBuffer.limit()];
        byteBuffer.get(result);
        return result;
    }
}
