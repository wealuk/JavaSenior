package com.atguigu.java;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * 打印流：PrintStream和PrintWriter---都是输出流
 *
 * notes：1.实现将基本数据类型的数据转化为字符串输出---也就是输出的都是字符串(使用了valueOf转换成了字符串)，就像
 * System.in搭配readLine()使用，得到的都是String类型，要利用其变成其他基本数据类型再使用各种方法转换。
 * 2.System.out返回的是PrintStream的实例
 *
 * 1.提供了一系列重载的print()和println()
 * 2.练习---一般打印流与System.setOut()以及System.out.print()一起使用---用于保存打印的数据，具体见下方总结和notes
 *
 * 总结：System.out是PrintStream,其print()默认打印在控制台。而我们一般搭配System.setOut()使用。
 * 比如PrintStream ps = new PrintStream(new FileOutputStream("路径"),true); System.setOut(ps);
 * 这样的话修改了out属性，改成了ps的打印流，这样调用System.out.print()就会打印在指定路径下的文件，而不是在控制台。
 * noptes:如果不想System.out.print()打印在控制台而是指定文件中保存下来，就调用System.setOut(打印流对象)，打印流对象就跟上面的一样。
 *
 * @author shkstart
 * @create 2021-05-09 13:48
 */
public class IOTest10 {
    @Test
    public void test1(){
        PrintStream ps = null;
        try {
            FileOutputStream fos = new FileOutputStream(new File("D:\\IO\\text.txt"));
            // 创建打印输出流,设置为自动刷新模式(写入换行符或字节 '\n' 时都会刷新输出缓冲区)
            ps = new PrintStream(fos, true);
            if (ps != null) {// 把标准输出流(控制台输出)改成文件
                System.setOut(ps);
            }


            for (int i = 0; i <= 255; i++) { // 输出ASCII字符
                System.out.print((char) i);
                if (i % 50 == 0) { // 每50个数据一行
                    System.out.println(); // 换行
                }   //只有这部分代码，没有前面的setOut的操作，那么默认就输出在控制台。而上面修改成ps的打印流，
            }       //就会打印在文件中，而不是默认的控制台。
            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }
}
