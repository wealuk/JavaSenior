package com.atguigu.exer;

import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端读取图片并发送给客户端，客户端保存图片到本地
 *
 * @author shkstart
 * @create 2021-05-14 13:28
 */
public class TCPTest1 {
    @Test
    public void client(){
        Socket socket = null;
        InputStream is = null;
        BufferedOutputStream bos = null;
        try {
            socket = new Socket(InetAddress.getLocalHost(),2021);
            is = socket.getInputStream();
            bos = new BufferedOutputStream(new FileOutputStream("荒野大镖客.jpg"));

            byte[] buffer = new byte[1024];
            int len;
            while((len = is.read(buffer)) != -1){
                bos.write(buffer,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socket != null){
                try {
                    socket.close();
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
            if(bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void server(){
        ServerSocket ss = null;
        Socket socket = null;
        OutputStream os = null;
        BufferedInputStream bis = null;
        try {
            ss = new ServerSocket(2021);
            socket = ss.accept();//同样是先开启服务器
            os = socket.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream("20210212135721_1.jpg"));

            byte[] buffer = new byte[1024];
            int len;
            while((len = bis.read(buffer)) != -1){
                os.write(buffer,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
