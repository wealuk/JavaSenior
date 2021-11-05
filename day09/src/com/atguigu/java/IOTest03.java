package com.atguigu.java;

import org.junit.Test;

import java.io.*;

/**
 * 测试FileInputStream和FileOutputStream的使用
 *
 * notes:1.跟字符流FileWriter一样，FileOutputStream也提供了构造器(File,boolean append),为true时，
 * 是在原文本上添加，false就是覆盖文件
 *  2.FileReader、FileWriter这些专门用来处理字符的，故write()中可以直接放String。而字节流不行，如果要
 *  放String类型的，那么需要调用String的getBytes()方法将其变为byte[]数组。
 *
 * 结论：
 *  1.对于文本文件(.txt、.java、.c、.cpp)，使用字符流处理
 *  2.对于非文本文件(.jpg、.mm3、.mp4、.avi、.doc、.ppt.......),使用字节流处理
 *  notes：*非文本文件绝对不能用字符流处理。但文本文件如果只进行复制而不在内存中读是可以使用字节流的。*
 *      之所以用字节流处理文本会出现乱码，是因为在内存中读可能会将一个中文所占的三个字节分开来读。而我们不读的话，直接
 *      写出去，那么又重新拼凑起来就不存在乱码的问题了，所以复制文本是可以用字节流的。
 *
 * @author shkstart
 * @create 2021-05-07 23:21
 */
public class IOTest03 {

    //使用字节流FileInputStream处理文本文件，可能出现乱码。---如果不读直接写出(复制的操作)，那么就没有乱码
    // notes：比如下面，每次循环读入到数组中的字节只有5个，字母和数组都是一个字节故可以完美读入，而中文占三个字节。如果
    //  byte[5]已经读取了3个字节，那么再读取一个中文时，就会一分为二，将前两个字节放到数组里面解析出来，后一个字节就并
    //  入到下次循环再放到数组第一个再解析出来，所有就有乱码的出现
    @Test
    public void test1() throws FileNotFoundException {
        FileInputStream fis = null;
        try {
            //1.造文件
            File file = new File("hello.txt");

            //2.造流
            fis = new FileInputStream(file);

            //3.读数据
            byte[] buffer = new byte[5];
            int len;//记录每次读取的字节的个数
            while ((len = fis.read(buffer)) != -1) {

                String str = new String(buffer, 0, len);
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                //4.关闭资源
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    /*
    实现对图片的复制操作
     */
    @Test
    public void test2() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            //
            File srcFile = new File("20210212135721_1.jpg");
            File destFile = new File("20210212135721_12.jpg");

            //
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);

            //复制的过程
            byte[] buffer = new byte[5];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //指定路径下文件的复制----封装成一个方法
    public void copyFile(String srcPath, String destPath) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            //
            File srcFile = new File(srcPath);
            File destFile = new File(destPath);

            //
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);

            //复制的过程
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //测试封装的方法
    @Test
    public void test3(){
        long start = System.currentTimeMillis();

        String srcPath = "C:\\Users\\幼稚特恶霸\\Videos\\Red Dead Redemption 2\\Red Dead Redemption 2 2021.02.20 - 11.44.16.02.mp4";
        String destpath = "C:\\Users\\幼稚特恶霸\\Videos\\Red Dead Redemption 2\\亚瑟.mp4";

//        String srcPath = "hello.txt";
//        String destpath = "hello3.txt";//可以用来复制文本文件，如果在内存读取可能有乱码，而不读直接写出去就没有乱码问题了
        copyFile(srcPath,destpath);

        long end = System.currentTimeMillis();

        System.out.println("复制花费的时间：" + (end - start));//2687
    }
}
