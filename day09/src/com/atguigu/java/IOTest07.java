package com.atguigu.java;

import org.junit.Test;

import java.io.*;

/**
 * 处理流之二：转换流的使用
 * 1.转换流：属于字符流----读入写出都用到char[] cbuf
 *      InputStreamReader:将一个字节的输入流转换为字符的输入流---字节到字符属于解码过程，构造器中可以声明解码的字符集
 *      OutputStreamWriter:将一个字符的输出流转换为字节的输出流---字符到字节属于编码过程，构造器可以声明编码的字符集
 *
 * 2.作用：提供字节流与字符流之间的转换
 *
 * 3.解码：字节、字节数组--->字符数组、字符串
 *   编码：字符数组、字符串--->字节、字节数组
 *   说明：编码决定了解码的方式
 *
 * 4.字符集(了解)
 * ASCII：美国标准信息交换码。
 *      用一个字节的7位可以表示。
 * ISO8859-1：拉丁码表。欧洲码表
 *      用一个字节的8位表示。
 * GB2312：中国的中文编码表。最多两个字节编码所有字符
 * GBK：中国的中文编码表升级，融合了更多的中文文字符号。最多两个字节编码
 * Unicode：国际标准码，融合了目前人类使用的所有字符。为每个字符分配唯一的 字符码。所有的文字都用两个字节来表示。
 * UTF-8：变长的编码方式，可用1-4个字节来表示一个字符。
 * notes:中文在gbk占两个字节，而在UTF-8之所以占三个字节，是因为三个字节中一个字节大小表示三个字节为一个整体是标识
 * 作用。另外两个字节仍然代表对应的中文。比如：1110xxxx 10xxxxxx 10xxxxxx 这些xx合起来就是中文的二进制，明确的
 * 数字代表三个字符为一个整体来识别。
 *
 * @author shkstart
 * @create 2021-05-08 23:51
 */
public class IOTest07 {

    /*
    此时处理异常的话，仍然应该使用try-catch-finally
    InputStreamReader的使用，实现字节的输入流到字符的输出流的转换
     */
    @Test
    public void test1() throws IOException {
        FileInputStream fis = new FileInputStream("dbcp.txt");
//        InputStreamReader isr = new InputStreamReader(fis);//使用系统默认的字符集
        //参数2指明了字符集,具体使用哪个字符集，取决于文件dbcp.txt保存时使用的字符集
        InputStreamReader isr = new InputStreamReader(fis,"UTF-8");

        char[] cbuf = new char[20];
        int len;
        while((len = isr.read(cbuf)) != -1){
            String str = new String(cbuf,0,len);
            System.out.print(str);//如果使用的gbk解码(字节流转换为字符流的过程)就会乱码
        }

        isr.close();
    }

    /*
    此时处理异常的话，仍然应该使用try-catch-finally

    综合使用InputStreamReader和OutputStreamWriter
     */
    @Test
    public void test2() throws IOException {
        //1.造文件、造流
        File file1 = new File("dbcp.txt");
        File file2 = new File("dbcp_gbk.txt");

        FileInputStream fis = new FileInputStream(file1);
        FileOutputStream fos = new FileOutputStream(file2);

        InputStreamReader isr = new InputStreamReader(fis,"utf-8");
        OutputStreamWriter osw = new OutputStreamWriter(fos , "gbk");//以gbk的形式存储(编码)

        //2.读写过程
        char[] cbuf = new char[20];
        int len;
        while((len = isr.read(cbuf)) != -1){
            osw.write(cbuf,0,len);
        }

        //3.关闭资源
        osw.close();
        isr.close();
    }
}
