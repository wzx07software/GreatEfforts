package com.popsun.io.buffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.IntStream;

/**
 * @author 吴志祥
 * @create 2020-03-26 17:53
 */
public class FileChannelWriteTest {
    static String testFilePath = "testFileChannelReadTest.txt";

    public static void main(String[] args) {
        Path path = Paths.get(testFilePath);
        //使用FileChannel #open 方法 创建 FileChannel 比较推荐这种
        try (FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            ByteBuffer write1 = ByteBuffer.allocate(8);
            write1.put("hello".getBytes(Charset.forName("UTF-8")));
            //缓冲区切换读写模式 详见第一节内容
            write1.flip();
            //写入
            int writed1 = fileChannel.write(write1);
            System.out.println("write  ByteBuffer " + writed1 + " bytes");

            fileChannel.position(5);
            ByteBuffer[] byteBuffers = new ByteBuffer[9999];
            IntStream.rangeClosed(0, 9998).forEach(i -> {
                byteBuffers[i] = ByteBuffer.allocate(1024);
                byteBuffers[i].put(("hjjhjhklehjkjfkdasjfhthejwrhjdkfshajfhjhkjkhjhkkjkjsdhafejhjjhhjhdsafhhhgghdfaeahjhdhajfejkhgfgdsafeudfaseuhuaf蝴蝶结发而后已发货的发货撒后付款和护肤的空间阿范德萨发合计好" + i+"\n").getBytes());
                //切换读写模式 一定别忘了
                byteBuffers[i].flip();
            });

            //聚合写入 将多个缓冲写入到一个通道里
            long writed2 = fileChannel.write(byteBuffers, 0, byteBuffers.length);
            System.out.println(" write ByteBuffers " + writed2 + " bytes");
        } catch (IOException e) {
        }
    }
}
