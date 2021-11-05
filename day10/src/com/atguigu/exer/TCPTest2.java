package com.atguigu.exer;

import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 客户端给服务端发送文本，服务端会将文本转成大写在返回给客户端。
 *
 * @author shkstart
 * @create 2021-05-14 14:09
 */
public class TCPTest2 {
    @Test
    public void client() throws IOException {
        Socket socket = new Socket("127.0.0.1",2001);
        OutputStream os = socket.getOutputStream();
        FileInputStream fis = new FileInputStream("hello.txt");

        byte[] buffer = new byte[5];
        int len;
        while((len = fis.read(buffer)) != -1){
            os.write(buffer,0,len);
        }

        socket.shutdownOutput();

        InputStream is = socket.getInputStream();
//        ByteOutputStream baos = new ByteOutputStream();读取还是建议用这个来存储数据再打印
        byte[] buffer1 = new byte[5];
        int len1;
        while((len = is.read(buffer1)) != -1){
            String str = new  String(buffer1,0,len);
            System.out.print(str);
//            baos.write(buffer1);
        }

        socket.close();
        os.close();
        fis.close();
    }
    @Test
    public void server() throws IOException {
        ServerSocket ss = new ServerSocket(2001);
        Socket socket = ss.accept();
        InputStream is = socket.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] buffer = new byte[5];
        int len;
        while((len = is.read(buffer)) != -1){
            baos.write(buffer,0,len);
        }

        byte[] data = baos.toString().toUpperCase().getBytes();
        OutputStream os = socket.getOutputStream();
        os.write(data);

        ss.close();
        socket.close();
        is.close();
        baos.close();
    }
}
