package com.atguigu.java1;

import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 实现TCP的网络编程
 * 例题2：客户端发送文件给服务端，服务端将文件保存在本地。
 *
 * @author shkstart
 * @create 2021-05-12 18:13
 */
public class InterTest03 {

    /*
    这里涉及到的异常，应该使用try-catch-finally处理

    记得先开启服务器再开启客户端。否则客户端创建Socket对象就会报错
     */
    @Test
    public void client() throws IOException {
        //1.创建Socket对象，指明服务器端的IP和端口号-----如果服务器未开启(找不到)，这一步就会报异常。
        Socket socket = new Socket(InetAddress.getByName("127.0.0.1"),9090);
        //2.获取一个输出流，用于输出数据给服务端
        OutputStream os = socket.getOutputStream();
        //3.创建一个输入流，用于从磁盘中读取要发送的文件到内存中。
        FileInputStream fis = new FileInputStream(new File("20210212135721_1.jpg"));
        //4.读取并发送数据
        byte[] buffer = new byte[1024];
        int len;
        while((len = fis.read(buffer)) != -1){
            os.write(buffer,0,len);
        }
        //5.关闭资源
        fis.close();
        os.close();
        socket.close();

    }

    @Test
    public void server() throws IOException {
        //1.创建服务器端的ServerSocket,指明自己的端口号
        ServerSocket ss = new ServerSocket(9090);
        //2.调用accept()表示接收来自于客户端的socket
        Socket socket = ss.accept();
        //3.获取输入流，读取客户端发生的数据
        InputStream is = socket.getInputStream();
        //4.创建输出流，用于保存发送过来的数据
        FileOutputStream fos = new FileOutputStream(new File("20210212135721_13.jpg"));
        //5.读取和写出的过程
        byte[] buffer = new byte[1024];
        int len;
        while((len = is.read(buffer)) != -1){
            fos.write(buffer,0,len);
        }
        //6.关闭资源
        fos.close();
        is.close();
        socket.close();
        ss.close();

    }
}
class Client{
    public static void main(String[] args) {
        Socket socket = null;
        OutputStream os = null;
        try {
            socket = new Socket(InetAddress.getLocalHost(),1001);
            os = socket.getOutputStream();
            os.write("差不多得了！".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(os != null){
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
class Server{
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(1001);
        Socket socket = ss.accept();
        InputStream is = socket.getInputStream();
        byte[] buffer = new byte[1024];
        int len;
        while((len = is.read(buffer)) != -1){
            System.out.println(new String(buffer));
        }
        is.close();
        socket.close();
        ss.close();
    }
}