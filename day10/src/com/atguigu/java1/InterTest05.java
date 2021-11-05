package com.atguigu.java1;

import org.junit.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * UDP协议的网络编程
 *
 * 将数据、源、目的封装成数据包，不需要建立连接
 * 每个数据报的大小限制在64K内
 * 发送不管对方是否准备好，接收方收到也不确认，故是不可靠的
 * 可以广播发送
 * 发送数据结束时无需释放资源，开销小，速度快
 *
 * UDP数据报通过数据报套接字 DatagramSocket 发送和接收，系统不保证
 * UDP数据报一定能够安全送到目的地，也不能确定什么时候可以抵达。
 * @author shkstart
 * @create 2021-05-12 20:21
 */
public class InterTest05 {
    //注：TCP需要先开启服务端再开启客户端；而UDP可以直接先开发送端，而接收端是否收到就与发送端无关了。当然，
//    如果要实现接收端接收到信息，需要先开接收端，在开发送端，这样才能接收到。

    //发送端
    @Test
    public void sender() throws IOException {
        //1.创建DatagramSocket的对象，用于后面发送数据报
        DatagramSocket socket = new DatagramSocket();

        String str = "我是UDP方式发送的导弹";
        byte[] data = str.getBytes();
        InetAddress inet = InetAddress.getLocalHost();
        //2.创建数据报(存储了要发送的数据)，前三个参数代表发送的数据，后两个代表接收端的Socket
        DatagramPacket packet = new DatagramPacket(data,0,data.length,inet,9090);

        //3.通过socket发送数据报
        socket.send(packet);
        //4.关闭资源
        socket.close();
    }

    //接收端
    @Test
    public void reciever() throws IOException {
        //1.创建DatagramSocket对象，需要指定端口号(IP为本地地址)
        DatagramSocket socket = new DatagramSocket(9090);

        byte[] buffer = new byte[100];
        //2.创建数据报，用于存储接收到的数据报
        DatagramPacket packet = new DatagramPacket(buffer,0,buffer.length);
        //3.通过socket接收数据报，存储到创建的packet中
        socket.receive(packet);
        //4.调用packet的getData()方法可以得到存储数据的byte[]，赋给String就可以实现打印了。
        System.out.println(new String(packet.getData(),0,packet.getLength()));
        //5.关闭资源
        socket.close();
    }
}
