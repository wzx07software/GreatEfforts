package com.popsun.redis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author 吴志祥
 * @create 2020-03-06 16:26
 */
public class OriginOpMainTest {
    public static void main(String[] args) throws IOException {
        String host = "192.168.1.143";
        int port = 6379;
        String password = "yitong";
        Socket socket = new Socket(host, port);
        setSocket(socket);
        // 建立连接后获得IO出流
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();
        //看过很多是将斜杠转义的写法，是不对的，因为他和r或者n连起来当做命令，而不是单纯的字符串
        //redis在处理命令时也做了判断，直接发送redis cli命令也可以解析，但必须以\r\n或者\n结尾
        //String message = "*3\\r\\n$3\\r\\nSET\\r\\n$6\\r\\nsocket\\r\\n$10\\r\\nsocketTest\\r\\n";
        getResult(outputStream, inputStream, "auth yitong\r\n");//先认证服务器
        //*<参数数量>\r\n$<参数1的字节数量>\r\n<参数1的数据>\r\n...$<参数N的字节数量>\r\n<参数N的数据>\r\n
        getResult(outputStream, inputStream, "*3\r\n$3\r\nSET\r\n$6\r\nsocket\r\n$10\r\nsocketTest\r\n");//等价于set socket socketTest，向redis中设置一个键值对
        getResult(outputStream, inputStream, "*2\r\n$3\r\nGET\r\n$6\r\nsocket\r\n");
        getResult(outputStream, inputStream, "set socket socketTest1\r\n");
        getResult(outputStream, inputStream, "exists socket\r\n");
        getResult(outputStream, inputStream, "get socket\r\n");
        getResult(outputStream, inputStream, "keys *\r\n");
        inputStream.close();
        outputStream.close();
        socket.close();
    }


    private static void getResult(OutputStream outputStream, InputStream inputStream, String cmd) throws IOException {
        System.out.println("\r\nsend command : " + cmd.replaceAll("\\r\\n", " "));
        outputStream.write(cmd.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        //此写法是为了测试，不适合返回数据太多的情况
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            byte[] bytes = new byte[8192];
            int length = inputStream.read(bytes);
            stringBuilder.append(getChars(bytes, length));
            if (length < 8192) {
                break;
            }
        }
        String result = stringBuilder.toString();
        while (result.length() > 0) {
            int index = result.indexOf("\r\n");
            //取第一个命令
            String currentStr = result.substring(0, index);
            //剩余命令，注意：\r\n占两个长度
            result = result.substring(index + 2);
            if (currentStr.startsWith("+")) {
                System.err.println("+ status reply message：" + currentStr.substring(1));
            } else if (currentStr.startsWith("-")) {
                System.err.println("- error reply message：" + currentStr.substring(1));
            } else if (currentStr.startsWith(":")) {
                System.err.println(": integer reply message：" + currentStr.substring(1));
            } else if (currentStr.startsWith("$")) {
                System.err.println("$ bulk reply message：" + currentStr.substring(1));
            } else if (currentStr.startsWith("*")) {
                System.err.println("* multi bulk reply message：" + currentStr.substring(1));
            } else {
                System.err.println("other message : " + currentStr);
            }
        }
    }

    private static char[] getChars(byte[] bytes, int length) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes, 0, length);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        return cb.array();
    }

    private static void setSocket(Socket socket) throws SocketException {
        socket.setKeepAlive(true);//在链接建立后，如果在net.ipv4.tcp_keepalive_time配置的时间内没有任何数据交互，则发送一个探测包，保持链接存活。
        socket.setSoTimeout(1000);// 可以通过这个选项来设置读取数据超时。当输入流的read方法被阻塞时，如果设置timeout（timeout的单位是毫秒），那么系统在等待了timeout毫秒后会抛出一个InterruptedIOException例外。
        // 在抛出例外后，输入流并未关闭，你可以继续通过read方法读取数据。
        // 如果将timeout设为0，就意味着read将会无限等待下去，直到服务端程序关闭这个Socket.这也是timeout的默认值
        socket.setOOBInline(true);//如果这个Socket选项打开，可以通过Socket类的sendUrgentData方法向服务器发送一个单字节的数据。
        // 这个单字节数据并不经过输出缓冲区，而是立即发出。
        // 虽然在客户端并不是使用OutputStream向服务器发送数据，但在服务端程序中这个单字节的数据是和其它的普通数据混在一起的。
        // 因此，在服务端程序中并不知道由客户端发过来的数据是由OutputStream还是由sendUrgentData发过来的
        // 注意，sendUrgentData仅能发送单字节，也就是发送的数据仅能小于256.
        socket.setReceiveBufferSize(8192);//在默认情况下，输入流的接收缓冲区是8096个字节（8K）。
        // 这个值是Java所建议的输入缓冲区的大小。如果这个默认值不能满足要求，可以用setReceiveBufferSize方法来重新设置缓冲区的大小。
        // 但最好不要将输入缓冲区设得太小，否则会导致传输数据过于频繁，从而降低网络传输的效率。
        socket.setSendBufferSize(8192);//在默认情况下，输出流的发送缓冲区是8096个字节（8K）。
        // 这个值是Java所建议的输出缓冲区的大小。如果这个默认值不能满足要求，可以用setSendBufferSize方法来重新设置缓冲区的大小。
        // 但最好不要将输出缓冲区设得太小，否则会导致传输数据过于频繁，从而降低网络传输的效率。
        socket.setTcpNoDelay(true);//在默认情况下，客户端向服务器发送数据时，会根据数据包的大小决定是否立即发送。
        // 当数据包中的数据很少时，如只有1个字节，而数据包的头却有几十个字节（IP头+TCP头）时，系统会在发送之前先将较小的包合并到较大的包后，一起将数据发送出去。
        // 在发送下一个数据包时，系统会等待服务器对前一个数据包的响应，当收到服务器的响应后，再发送下一个数据包，这就是所谓的Nagle算法；在默认情况下，Nagle算法是开启的。
        //这种算法虽然可以有效地改善网络传输的效率，但对于网络速度比较慢，而且对实现性的要求比较高的情况下（如游戏、Telnet等），使用这种方式传输数据会使得客户端有明显的停顿现象。
        // 因此，最好的解决方案就是需要Nagle算法时就使用它，不需要时就关闭它。
        // 而使用setTcpToDelay正好可以满足这个需求。当使用setTcpNoDelay（true）将Nagle算法关闭后，客户端每发送一次数据，无论数据包的大小都会将这些数据发送出去。
        socket.setReuseAddress(true);// 如果端口已经被占用，但TCP状态位于 TIME_WAIT ，可以重用端口。
        // 如果端口已经被占用，而TCP状态位于其他状态，重用端口时依旧得到一个错误信息， 抛出“Address already in use： JVM_Bind”。
        // 如果你的服务程序停止后想立即重启，不等60秒，而新套接字依旧 使用同一端口，此时 SO_REUSEADDR 选项非常有用。
        socket.setSoLinger(true, 100);//这个Socket选项可以影响close方法的行为。
        // 在默认情况下，当调用close方法后，将立即返回；如果这时仍然有未被送出的数据包，那么这些数据包将被丢弃。
        // 如果将linger参数设为一个正整数n时（n的值最大是65，535），在调用close方法后，将最多被阻塞n秒。
        // 在这n秒内，系统将尽量将未送出的数据包发送出去；如果超过了n秒，如果还有未发送的数据包，这些数据包将全部被丢弃，close方法会立即返回。
        // 如果将linger设为0，和关闭SO_LINGER选项的作用是一样的。
        // 如果底层的Socket实现不支持SO_LINGER都会抛出SocketException例外。
        // 当给linger参数传递负数值时，setSoLinger还会抛出一个IllegalArgumentException例外。
        // 可以通过getSoLinger方法得到延迟关闭的时间，如果返回-1，则表明SO_LINGER是关闭的。
        //socket.setPerformancePreferences(); //jdk未实现方法
        //socket.setTrafficClass();//基本没什么实际作用，发送的包在 ip 头中设置流量类别 (traffic class) 或服务类型八位组 (type-of-service octet)。由于底层网络实现可能忽略此值，应用程序应该将其视为一种提示。

    }
}
