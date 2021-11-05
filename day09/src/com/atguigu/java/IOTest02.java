package com.atguigu.java;

import org.junit.Test;

import java.io.*;

/**
 * 一、流的分类:
 *  1.操作数据单位：字节流(8 bit--byte)，字符流(16 bit--char)---文本类的用字符流，其他的基本用字节流
 *  2.数据流的流向：输入流，输出流
 *  3.流的角色：节点流，处理流--节点流直接操作文件，处理流就是对节点流或别的处理流的包装
 *
 * 二、流的体系结构
 * 抽象基类             节点流(或文件流)                                   缓冲流(处理流的一种)
 * InputStream          FileInputStream (read(byte[] buffer))           BufferedInputStream(read(byte[] buffer))
 * OutputStream         FileOutputStream (write(byte[] buffer,0,len))   BufferedOutputStream(write(byte[] buffer,0,len)) / flush()
 * Reader               FileReader(read(char[] cbuf))                   BufferedReader(read(char[] cbuf) / readLine())
 * Writer               FileWriter(write(char[] cbuf,0,len))            BufferedWriter(write(char[] cbuf,0,len)) / flush()
 *                   notes:1.读取后要在控制台输出，简洁的方式：String str = new String(cbuf,0,len); sout(str)
 *                          2.字符流的write()可以直接放String。而字节流不行，只能放int和byte[]，所以放String，需要调用String的getBytes()
 *
 * 首先介绍字符流--FileReader、FileWriter核心内容见下面的各个test
 * notes:1.字符流总结的书写规范：
 *          ① 创建File类的对象，指明读入和写出的文件：File file = new File("hello.txt");
 *          ②创建输入流和输出流的对象：FileReader(或FileWriter) f = new FileReader(或FileWriter)(file);
 *          ③读入的操作：char[] cubf = new char[5];----读入的字符就放到这个数组中，数组的长度就代表每次循环能读入的字符的个数
 *                            int len;//记录每次读入到cbuf数组中的字符的个数
 *                            while((len = fr.read(cubf)) != -1){
 *                                      String str = new String(cubf,0,len)-----只需要取出读入到数组中的字符的个数，而不需要全部取出
 *                                      System.out.print(str);
 *                              }
 *             写出：f.write("内容");
 *            ④关闭流资源：f.close();
 *            ⑤上面写好后，再进行异常处理，将①②③用try-catch-finally包在一起，然后将④资源关闭放到finally中并且加个非null的if判断就行了
 *     注：写熟练后，可以直接将创建文件和创建流合并成一步，即在构造器中用匿名对象的方式：比如
 *          BufferedReader br = new BufferedReader(new FileReader(new File("路径")));
 *          同时节点流的构造器中又可以省略文件对象直接写路径也行，FileReader fr = new FileReader("路径");
 *
 *        2.读入时，必须要保证文件存在；写出时，文件可以不存在。同时读入时，我们一般使用read(char[] cubf),要注意是将
 *          文件的字符读到数组中了，而在遍历或写出读入的文件字符时，则需要记住不能遍历char[]中的所有元素或是写出
 *          所有元素，而应该是每次数组中添加几个元素就遍历几个元素或者写出元素(write(cbuf,0,len)、String str = new String(cbuf,0,len))
 *
 * 字节流与字符流的区别和使用情景。
 * 字节流：read(byte[] buffer) / read()    非文本文件
 * 字符流：read(char[] cbuf) / read()       文本文件
 *
 * @author shkstart
 * @create 2021-05-06 16:41
 */
public class IOTest02 {
    public static void main(String[] args) {
        File file = new File("hello.txt");//相较于当前工程----故找不到hello.txt
        System.out.println(file.getAbsolutePath());//D:\workspace_idea1\JavaSenior\hello.txt

        File file1 = new File("day09\\hello.txt");//此时才能找到txt
        System.out.println(file1.getAbsolutePath());
    }

    /*
    将day09下的hello.txt文件内容读入程序中，并输出到控制台

    说明点：
    1.read()的理解:返回读入的一个字符。如果达到文件末尾，返回-1。
    2.异常的处理：为了保证流资源一定可以执行关闭操作。需要使用try-catch-final处理
            notes:1.共有三处异常需要处理(见下方)，用throws的话出现一个异常程序就停止了，使得流关闭操作无法执行，从而内存溢出
                  2.流(fr)需要在try外面的final中使用，故需要声明在try-catch外面(实例化还是在try中)，如果文件找不到，流就不会
                  实例化，那么执行关闭操作时就会出现空指针异常。所以需要在执行fr.close();之前进行 fr != null的判断。
    3.读入的文件一定要存在，否则就会报FileNotFoundException。

     */
    @Test
    public void test1() {
        FileReader fr = null;
        try {
            //1.实例化File类的对象，指明要操作的文件
            File file = new File("hello.txt");//相较于当前Module
            //2.提供具体的流
            fr = new FileReader(file);  //①文件找不到出异常

            //3.数据的读入
            //read():返回读入的一个字符。如果达到文件末尾，返回-1。
            //方式一：
//        int data = fr.read();         //②读取出现阻塞异常
//        while(data != -1){  //先读一次，判断文件是否内容为空
//            System.out.print((char)data);
//            data = fr.read();
//        }

            //方式二：语法上针对于方式一的修改
            int data;
            while((data = fr.read()) != -1){        //②读取出现阻塞异常
                System.out.println((char)data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.流的关闭操作
//            try {
//                if(fr != null)
//                    fr.close();       //③关闭时出现异常
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            //或
            if(fr != null){
                try {
                    fr.close();     //③关闭时出现异常
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //对read()操作升级：使用read的重载方法read(char[] cubf)
            //notes:1.read()返回的int数据代表字符，末尾了则为-1；而read(char[] cubf)返回的int数据代表添加到cubf中字符的个数，末尾则为-1
                //  2.read(char[] cubf)其实调用了重载的read(char cbuf[], int off, int len)，每次读入到数组中[off,len)的字符，
                //  比如下面的cubf的长度是5，如果使用read(cubf,0,3)也就是只有0，1，2位置能读入字符，也就是每次读入3个，虽然别的位置能装但我不装。
                //  而read(char[] cubf)是return read(cbuf, 0, cbuf.length); 其实就是数组的每个位置都装满，等于直接装数组。
                //  所以重载的read(char cbuf[], int off, int len)很少用到
    @Test
    public void test2() {
        FileReader fr = null;
        try {
            //1.File类的实例化
            File file = new File("hello.txt");

            //2.FileReader流的实例化
            fr = new FileReader(file);

            //3.读入的操作---------利用了char[]来读取，提高了效率，但读取时存在隐患，按数组长度来确定每次循环读取的个数，但后面个数不够填满数组时，需要处理好(见下面)。
            //read(char[] cbuf):返回每次读入cubf数组中的字符的个数(不是字符，而是添加进数组的个数)。如果达到文件末尾，返回-1.
            char[] cubf = new char[5];//---文件内容放到了(读入)数组char[]里面。通过数组来获取内容
            int len;//记录每次读入到cbuf数组中的字符的个数
            while((len = fr.read(cubf)) != -1){
                //方式一：
                //错误的写法
//                for (int i = 0; i < cubf.length; i++) {--数组中全部遍历
//                    System.out.print(cubf[i]);
//                }----结果是helloworld123ld  多打印了ld，因为每次添加5个，最后只有三个(123),其实123是对前面数组中world中的“wor”
                        //的覆盖，就留下了123ld。
                //正确的写法
//                for(int i = 0;i < len;i++){ //添加进数组几个就打印几个，int len代表添加进cubf中字符的个数
//                    System.out.println(cubf[i]);
//                }
                //方式二：
                //错误的写法，对应着方式一的错误的写法
//                String str = new String(cubf);--也是每次循环把数组中的全部取出来了，也就是helloworld123ld
//                System.out.println(str);
                //正确的写法：
                String str = new String(cubf , 0 , len);//----放多少个取多少个转换
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fr != null){
                //4.资源的关闭
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
    从内存中写出数据到硬盘的文件里。

    说明：
    1.输出操作，对应的File可以不存在，并不会报异常。
    2.      File对应的硬盘中的文件如果不存在，在输出的过程中，会自动创建此文件。
            File对应的硬盘中的文件如果存在：
                    如果流使用的构造器是：FileWriter(file,false) / FileWriter(file):对原有文件的覆盖。
                    如果流使用的构造器是：FileWriter(file,true):不会对原有文件覆盖，而是在原有文件基础上追加内容

     */
    @Test
    public void test3() {
        FileWriter fw = null;
        try {
            //1.提供File类的对象，指明写出到的文件
            File file = new File("hello1.txt");

            //2.提供FileWriter的对象，用于数据的写出
            fw = new FileWriter(file);

            //3.写出操作
            fw.write("i have a dream!\n");
            fw.write("we need to have a dream!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.流资源的关闭
            if(fw != null){
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //同时利用输入输出流，可以对输入的文件再输出，即可完成一次文件的复制
    @Test
    public void test4() {
        FileReader fr = null;
        FileWriter fw = null;
        try {
            //1.创建File类的对象，指明读入和写出的文件
            File srcFile = new File("hello.txt");
            File destFile = new File("hello2.txt");

            //不能使用字符流来处理图片等字节数据
//            File srcFile = new File("20210212135721_1.jpg");
//            File destFile = new File("20210212135721_11.jpg");

            //2.创建输入流和输出流的对象
            fr = new FileReader(srcFile);
            fw = new FileWriter(destFile);

            //3.数据的读入和写出操作
            char[] cbuf = new char[5];
            int len;//记录每次读入到cbuf数组中的字符的个数
            while((len = fr.read(cbuf)) != -1){
                //每次写出len个字符
                fw.write(cbuf,0,len);//fw.write(cbuf)就会出现把数组的所有元素都写出，出现的问题跟上面一样。故我们只需要循环一次添加
            }                               //进数组几个字符，就写出几个字符，而不是全部写出。真好write提供了这个重载方法。
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.关闭流资源
            //方式一：
//            try {
//                if(fw != null){
//                    fw.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }finally {
//                try {
//                    if(fr != null){
//                        fr.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }

            //方式二：
            try {
                if(fw != null){
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }//异常处理掉了，下面的程序依然可以运行

            try {
                if(fr != null){
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
