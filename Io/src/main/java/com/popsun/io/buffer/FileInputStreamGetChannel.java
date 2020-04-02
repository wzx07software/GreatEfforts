package com.popsun.io.buffer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author 吴志祥
 * @create 2020-03-25 17:11
 */
public class FileInputStreamGetChannel {
    static String filePath = "CreateAndWriteAndReadPath.txt";

    public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            FileChannel channel = fileInputStream.getChannel();
            ByteBuffer readBuffer = ByteBuffer.allocateDirect((int) channel.size());
            int readed = channel.read(readBuffer);
            readBuffer.flip();
            System.out.println(" fileInputStream get Channel read from " + readed + " bytes:" + new String(readBytesFrromBuffer(readBuffer)));
            //这里关闭的时候会先判断文件通道的parent 属性是否为空，如果不为空则会调用parent属性的关闭，这里的parent 就是FileInputStream对象
            channel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
        }
    }

    private static byte[] readBytesFrromBuffer(ByteBuffer byteBuffer) {
        byte[] result = new byte[byteBuffer.limit()];
        byteBuffer.get(result);
        return result;
    }
}
