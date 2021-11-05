package com.atguigu.java;

import org.junit.Test;

import java.io.*;

/**
 * 数据流
 * 1.DataInputStream和DataOutputStream
 * 2.作用：用于读取或写出基本数据类型的变量或字符串
 *
 * notes:其实就是将这些变量或字符串持久化到内存中保存，要用的时候再读取就行了。注意保存到的文件不是用来打开查看的，
 * 会出现乱码，而是用来读取到内存使用的。
 *
 * 练习：将内存中的字符串、基本数据类型的变量写到文件中。
 *
 * @author shkstart
 * @create 2021-05-09 18:41
 */
public class IOTest11 {
    //处理异常仍然应该有try-catch-finally
    @Test
    public void test1() throws IOException {
        //1.
        DataOutputStream dos = new DataOutputStream(new FileOutputStream("data.txt"));
        //2.
        dos.writeUTF("老阳");
        dos.flush();//刷新操作，将内存中的数据写入文件
        dos.writeInt(23);
        dos.flush();
        dos.writeBoolean(true);
        dos.flush();
        //3.
        dos.close();
    }

    /*
    将文件中存储的基本数据类型变量和字符串读取到内存中，保存在变量中

    注意点：读取不同类型的数据的顺序要与当初写入文件时，保存的数据的顺序一致！
     */
    @Test
    public void test2() throws IOException {
        //1.
        DataInputStream dis = new DataInputStream(new FileInputStream("data.txt"));
        //2.
        String name = dis.readUTF();//先写出的String类型，读取的时候必须先读String类型
        int age = dis.readInt();
        boolean isMale = dis.readBoolean();

        System.out.println("name" + name);
        System.out.println("age" + age);
        System.out.println("isMale" + isMale);

        //3.
        dis.close();
    }
}
