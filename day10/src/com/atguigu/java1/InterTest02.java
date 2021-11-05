package com.atguigu.java1;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 实现TCP的网络编程
 *
 * 使用TCP协议前，须先建立TCP连接，形成传输数据通道
 * 传输前，采用“三次握手”方式，点对点通信，是可靠的----断开传输需要四次挥手
 * TCP协议进行通信的两个应用进程：客户端、服务端。
 * 在连接中可进行大数据量的传输
 * 传输完毕，需释放已建立的连接，效率低
 *
 * 问：传输层的TCP协议和UDP协议的主要区别是？
 * TCP:可靠的数据传输(三次握手)；进行大数据量的传输；效率低。
 * UDP:不可靠；以数据报形式发送，数据报限定为64K；效率高。
 *
 * 例子1：客户端发送消息给服务端，服务端将数据显示在控制台上(先启动服务器在启动客户端)
 * @author shkstart
 * @create 2021-05-12 14:09
 */
public class InterTest02 {
    //注：先开启服务端，再开启客户端才有用，否则客户端创建Socket对象时就会因为匹配不到服务器而报异常。
    //相当于创建Socket对象就是连接到服务器，而服务器也就能通过accept()获取客户端的socket。两个共有的socket
    //就可以获取输入输出流互相传递数据。

    //客户端
    @Test
    public void client(){
        Socket socket = null;
        OutputStream os = null;
        try {
            //1.创建Socket对象，指明服务器端的IP和端口号
            InetAddress inet = InetAddress.getByName("127.0.0.1");
            //注：①也可以不创建InetAddress对象，构造器(InetAddress / String host , int port)
            //port代表端口号，host放String的IP和域名。而直接放IP地址(InetAddress对象)，相当于直接将host的地址取出来了。
            //②可以直接Socket socket = new Socket(InetAddress.getByName("IP"),端口号);
            socket = new Socket(inet,8899);
            //2.获取一个输出流，用于输出数据----write()直接输出到服务器端，需要服务器接收
            os = socket.getOutputStream();
            //3.写出数据的操作
            os.write("嘿嘿！".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.资源的关闭
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    //服务端
    @Test
    public void server(){
        ServerSocket ss = null;
        Socket socket = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;//里面是一个byte[]数组,可以将数据读入到数组中
        try {
            //1.创建服务器端的ServerSocket,指明自己的端口号(IP不需要指明，就是本地IP---自己的主机)
            ss = new ServerSocket(8899);
            //2.调用accept()表示接收来自于客户端的socket
            socket = ss.accept();
            //3.获取输入流
            is = socket.getInputStream();

            //不建议这样写，可能会有乱码
//        byte[] buffer = new byte[1024];
//        int len;
//        while((len = is.read(buffer)) != -1){
//            String str = new String(buffer,0,len);
//            System.out.println(str);
//        }
            //4.读取输入流中的数据
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[5];
            int len;
            while((len = is.read(buffer)) != -1){
                baos.write(buffer,0,len);//可以将数据全部存到数组在一次性读出来，不像前面的循环一次读出一次而可能出现乱码
            }

            System.out.println(baos.toString());

            System.out.println("收到了来自："+socket.getInetAddress().getHostName()+"的数据。");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(baos != null){
                //5.关闭资源
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(ss != null){
                try {
                    ss.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
