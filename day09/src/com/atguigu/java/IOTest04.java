package com.atguigu.java;

import org.junit.Test;

import java.io.*;

/**
 * 处理流之一：缓冲流的使用
 *
 * 1.缓冲流：
 * BufferedInputStream
 * BufferedOutputStream
 * BufferedReader
 * BufferedWriter
 *
 * 2.作用：提高流的读取、写入的速度。
 *      提高读写速度的原因：内部提供了一个缓冲区。默认情况下是8kb(private static int DEFAULT_BUFFER_SIZE = 8192;)
 *
 *  notes：BufferedInputStream内部提供了一个缓冲区，可以存储1024*8=8192个字节，存满时就会调用flush()清空
 *  缓冲区并输出里面的字节；而BufferedOutputStream中的write()方法调用了flush()，一旦调用flush()就会
 *  直接清空缓冲区并且输出字节(不需要等到存满再释放)，所以不需要再显式调用fiush()来释放缓冲区的字符了。
 *
 *  3.处理流，就是‘套接’在已有的流的基础上
 *
 *
 *
 * @author shkstart
 * @create 2021-05-08 14:08
 */
public class IOTest04 {
    /*
    实现非文本文件的复制
     */
    @Test
    public void test1(){
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            //1.造文件
            File srcFile = new File("20210212135721_1.jpg");
            File destFile = new File("20210212135721_13.jpg");

            //2.造流
            //2.1造节点流
            FileInputStream fis = new FileInputStream(srcFile);//存在缓冲区，能存8192个字节，存满时释放。当然，可以通过flush()直接释放
            FileOutputStream fos = new FileOutputStream(destFile);
            //2.2造缓冲流
            bis = new BufferedInputStream(fis);
            bos = new BufferedOutputStream(fos);

            //3.复制的细节：读取、写入
            byte[] buffer = new byte[10];
            int len;
            while((len = bis.read(buffer)) != -1){
                bos.write(buffer,0,len);

//                bos.flush();//刷新缓冲区。write()方法中有flush()方法来刷新缓冲区，不需要再显式的声明。

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.资源关闭
            //要求：先关闭外层的流，再关闭内层的流
            if(bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //说明：关闭外层流的同时，内层流也会自动的进行关闭。关于内层流的关闭，我们可以省略。
//        fos.close();
//        fis.close();
        }
    }

    //实现文件复制的方法
    public void copyFileWithBuffered(String srcPath,String destPath){
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            //1.造文件
            File srcFile = new File(srcPath);
            File destFile = new File(destPath);

            //2.造流
            //2.1造节点流
            FileInputStream fis = new FileInputStream(srcFile);
            FileOutputStream fos = new FileOutputStream(destFile);
            //2.2造缓冲流
            bis = new BufferedInputStream(fis);
            bos = new BufferedOutputStream(fos);

            //3.复制的细节：读取、写入
            byte[] buffer = new byte[1024];
            int len;
            while((len = bis.read(buffer)) != -1){
                bos.write(buffer,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.资源关闭
            //要求：先关闭外层的流，再关闭内层的流
            if(bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //说明：关闭外层流的同时，内层流也会自动的进行关闭。关于内层流的关闭，我们可以省略。
//        fos.close();
//        fis.close();
        }
    }
    @Test
    public void test2(){
        long start = System.currentTimeMillis();

        String srcPath = "C:\\Users\\幼稚特恶霸\\Videos\\Red Dead Redemption 2\\Red Dead Redemption 2 2021.02.20 - 11.44.16.02.mp4";
        String destpath = "C:\\Users\\幼稚特恶霸\\Videos\\Red Dead Redemption 2\\亚瑟2.mp4";

        copyFileWithBuffered(srcPath,destpath);

        long end = System.currentTimeMillis();

        System.out.println("复制花费的时间：" + (end - start));//现在：949  原来：2687
    }

    /*
    使用BufferedReader和BufferedWriter实现文本文件的复制
     */
    @Test
    public void test3(){
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            //创建文件和相应的流
            //写熟练后就不需要一个个File、文件流去声明了，直接在构造器中用匿名对象就可以实现。
            br = new BufferedReader(new FileReader(new File("dbcp.txt")));
            bw = new BufferedWriter(new FileWriter(new File("dbcp1.txt")));

            //读写操作
            //方式一：使用char[]数组
//            char[] cbu = new char[1024];
//            int len;
//            while((len = br.read(cbuf)) != -1){
//                bw.write(buffer,0,len);
//                //bw.flush();
//            }

            //方式二：使用String
            //readLine()一次读取一行，以String形式返回，直到文件末尾返回null(读取的内容没有换行符，故write后会打印在一行)
            String data;
            while((data = br.readLine()) != null){
//                bw.write(data);//data中不包含换行符---内容会全部输出在一行
                //方法一：
                bw.write(data + "\n");
                //方法二：
                bw.write(data);
                bw.newLine();//提供换行的操作
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if(bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
