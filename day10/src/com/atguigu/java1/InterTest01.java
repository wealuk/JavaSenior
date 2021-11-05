package com.atguigu.java1;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 一、网络编程中有两个主要的问题：
 *  1.如何准确地定位网络上一台或多台主机；定位主机上的特定的应用
 *  2.找到主机后如何可靠高效地进行数据传输
 *
 * 二、网络编程中的两个要素
 *  1.对应问题一：IP和端口号
 *  2.对应问题二：提供网络通信协议：TCP/IP参考模型(应用层、传输层、网络层、物理+数据链路层)
 *
 * 三、通信要素一：IP和端口号
 * 1.IP：唯一的标识 Internet 上的计算机（通信实体）
 * 2.在Java中使用InetAddress类代表IP
 * 3.IP的分类： IPv4 和 IPv6 ; 万维网 和 局域网
 * 4.域名：  www.baidu.com   www.mi.com   www.jd.com  www.vip.com
 * Internet上的主机有两种方式表示地址：域名(hostName)：www.atguigu.com；IP 地址(hostAddress)：202.108.35.210
 * 域名容易记忆，当在连接网络时输入一个主机的域名后，域名服务器(DNS)负责将域名转化成IP地址，这样才能和主机建立连接。 -------域名解析
 * 5.本地回路地址：127.0.0.1  对应着：localhost
 *
 * 6.如何实例化InetAddress:两个方法：getByName(String host) 、 getLocalHost()---String host可以是是域名也可以是IP地址；getLocalHost()是获取本地IP地址
 *      两个常用方法：getHostName() 获取域名/ getHostAddress()获取IP地址
 *   注：InetAddress不能new对象，因为构造器私有化了
 *
 * 7.   端口号：正在计算机上运行的进程（程序）
 *  要求：不同的进程有不同的端口号
 *  范围：被规定为一个 16 位的整数 0~65535。
 *  端口分类：
 * 公认端口：0~1023。被预先定义的服务通信占用（如：HTTP占用端口80，FTP占用端口21，Telnet占用端口23）
 * 注册端口：1024~49151。分配给用户进程或应用程序。（如：Tomcat占用端口8080，MySQL占用端口3306，Oracle占用端口1521等）。
 * 动态/私有端口：49152~65535。
 *
 * 8.端口号与IP地址的组合得出一个网络套接字：Socket。
 *
 *
 * @author shkstart
 * @create 2021-05-12 10:50
 */
public class InterTest01 {

    public static void main(String[] args) {
        //File file = new File("hello.txt");
        try {
            InetAddress inet1 = InetAddress.getByName("192.168.10.14");//192.168.开头的就是私有址址，范围即为192.168.0.0--192.168.255.255

            System.out.println(inet1);

            InetAddress inet2 = InetAddress.getByName("www.baidu.com");
            System.out.println(inet2);//www.baidu.com/110.242.68.3

            InetAddress inet3 = InetAddress.getByName("127.0.0.1");//本地IP
            System.out.println(inet3);

            //获取本地IP
            InetAddress inet4 = InetAddress.getLocalHost();//获取本地IP对象
            System.out.println(inet4);

            //getHostName()
            System.out.println(inet2.getHostName());
            //getHostAddress()
            System.out.println(inet2.getHostAddress());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }
}
