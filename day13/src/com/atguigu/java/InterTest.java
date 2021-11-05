package com.atguigu.java;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author shkstart
 * @create 2021-05-25 13:03
 */
public class InterTest {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = null;
        Socket socket = null;
        OutputStream os = null;
        BufferedInputStream bis = null;
        try {
            ss = new ServerSocket(1001);
            socket = ss.accept();

            os = socket.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream("C:\\Users\\幼稚特恶霸\\Videos\\SEKIRO  SHADOWS DIE TWICE\\SEKIRO  SHADOWS DIE TWICE 2021.04.02 - 21.26.43.02.mp4"));
            byte[] buffer = new byte[1024];
            int len;
            while((len = bis.read(buffer)) != -1){
                os.write(buffer,0,len);
            }
            System.out.println("传输成功！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ss.close();
            socket.close();
            os.close();
            bis.close();
        }

    }
}
class Client{
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getByName("192.168.31.218"),1001);
        OutputStream os = socket.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("C:\\Program Files (x86)\\Steam\\userdata\\1112949692\\760\\remote\\1174180\\screenshots\\20210210153115_1.jpg"));
        byte[] buffer = new byte[1024];
        int len;
        while((len = bis.read(buffer)) != -1){
            os.write(buffer,0,len);
        }
        System.out.println("发送成功");
        socket.close();
        os.close();
        bis.close();
    }
}
class a{
    public static void main(String[] args) throws UnknownHostException {
        System.out.println(InetAddress.getLocalHost());
    }
}
