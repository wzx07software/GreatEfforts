package com.popsun.io.buffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.IntStream;

/**
 * @author 吴志祥
 * @create 2020-03-26 17:32
 */
public class FileChannelReadTest {
    //该文件内容自己脑补
    static String testFilePath = "CreateAndWriteAndReadPath.txt";

    public static void main(String[] args) {
        Path path = Paths.get(testFilePath);
        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ)) {
            //read
            ByteBuffer byteBuffer = ByteBuffer.allocate(4);
            int readed = fileChannel.read(byteBuffer, 0);
            System.out.println("read ByteBuffer " + readed + " bytes，內容：" + new String(byteBuffer.array()));
            ByteBuffer[] byteBuffersRead = new ByteBuffer[4];
            IntStream.rangeClosed(0, 3).forEach(i -> byteBuffersRead[i] = ByteBuffer.allocateDirect(8));
            System.out.println("alfter read fileChannel position:" + fileChannel.position());
            //很关键 要不然又从头开始读，如下面的结果A
            fileChannel.position(readed);
            //scatter read 分散读取 从channnel 中分散地读取到buffer数组中
            long readed2 = fileChannel.read(byteBuffersRead, 0, byteBuffersRead.length);
            IntStream.rangeClosed(0, 3).forEach(i -> {
                byteBuffersRead[i].flip();
            });
            System.out.println(String.format("read ByteBuffers %d bytes,content:%s%s%s%s", readed2,
                    new String(readBytesFrromBuffer(byteBuffersRead[0])),
                    new String(readBytesFrromBuffer(byteBuffersRead[1])),
                    new String(readBytesFrromBuffer(byteBuffersRead[2])),
                    new String(readBytesFrromBuffer(byteBuffersRead[3]))));

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
