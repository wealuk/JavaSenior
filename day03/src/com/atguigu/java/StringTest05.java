package com.atguigu.java;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * 涉及到String类与其他结构之间的转换
 *
 * 复习：
 * String与基本数据类型、包装类之间的转换。
 *      String-->基本数据类型、包装类：调用包装类的静态方法：parseXxx(str)
 *      基本数据类型、包装类-->String:调用String重载的valueOf(xxx)
 *
 * String与char[]之间的转换
 *      String-->char[]:调用String的toCharArray()
 *      char[]-->String:调用String的构造器
 *
 * String与byte[]之间的转换
 * 编码：String-->byte[]:调用String的getByte()
 * 解码：byte[]-->String:调用String的构造器
 *
 * 编码：字符串-->字节(看得懂-->看不懂的二进制数据)
 * 解码：编码的逆过程，字节-->字符串(看不懂的二进制数据-->看得懂)
 *
 * 说明：解码时，要求解码使用的字符集必须与编码时使用的字符集一致，否则会出现乱码。
 *
 *
 * String-->StringBuffer、StringBuilder:调用StringBuffer、StringBuilder的构造器
 * StringBuffer、StringBuilder-->String:①调用String的构造器  ②StringBuffer、StringBuilder的toString()
 *
 * @author shkstart
 * @create 2021-04-11 18:26
 */
public class StringTest05 {
    @Test
    public void test(){
        String s1 = "123";
//        int num = (int)s1;错误的
        int num = Integer.parseInt(s1);//123
        String s2 = String.valueOf(num);//"123"
        String s3 = num + "";//有变量参与。此时s3指向堆，堆结构再指向常量池

        System.out.println(s1 == s3);//false.s1指向常量池,s3指向堆
    }

    @Test
    public void test1(){
        String str1 = "abc123";//题目：a21cb3,将第二个到倒数第二个反转下。--将str1先转为char[]，反转后再转回String。

        char[] charArray = str1.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            System.out.print(charArray[i]);
        }

        char[] arr = new char[]{'h','e','l','l','o'};
        String s2 = new String(arr);
        System.out.println(s2);
    }

    @Test
    public void test2() throws UnsupportedEncodingException {
        String s1 = "abc123中国";
        byte[] bytes = s1.getBytes();//使用默认的字符集，进行编码(当前用的UTF-8)
        System.out.println(Arrays.toString(bytes));//

        byte[] gbks = s1.getBytes("gbk");//使用gbk字符集进行编码。
        System.out.println(Arrays.toString(gbks));

        System.out.println("*************");
        String str2 = new String(bytes);//使用默认的字符集，进行解码
        System.out.println(str2);

        String s3 = new String(gbks);
        System.out.println(s3);//出现乱码。原因：编码集和解码集不一致！

        //要想gbks解码后不乱吗
        String s4 = new String(gbks,"gbk");
        System.out.println(s4);//没有出现乱码。原因：编码集和解码集一致！
    }
}
