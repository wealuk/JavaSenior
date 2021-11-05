package com.atguigu.java;

import org.junit.Test;

import java.io.*;

/**
 * RandomAccessFile的使用
 * 1.RandomAccessFile直接继承于java.lang.Object类，实现了DataInput和DataOutput接口。---不像其他的流都是继承于四大抽象基类
 * 2.RandomAccessFile既可以作为一个输入流，又可以作为一个输出流。---一个对象只能单独作为输入流或者输出流。不可能即是输入又是输出
 *
 * 构造器为("路径"/文件 ,String mode)。----构造器中无需放节点流。直接放文件(File)或文件路径(String)
 * 创建 RandomAccessFile 类实例需要指定一个 mode 参数，该参数指定RandomAccessFile 的访问模式：
 * r: 以只读方式打开
 * rw：打开以便读取和写入
 * rwd:打开以便读取和写入；同步文件内容的更新
 * rws:打开以便读取和写入；同步文件内容和元数据的更新
 * 我们一般使用r和rw。由于不存在单独的写出w操作，所以我们可以将mode为r的对象作为输入流，为rw的对象作为输出流。
 *
 * 3.如果RandomAccessFile作为输出流时，写出到的文件不存在，则在执行过程中自动创建。
 *  如果写出到的文件存在，则会对原有文件内容进行覆盖。(默认情况下，从头覆盖。)
 *
 *  4.可以通过相关操作，实现RandomAccessFile插入数据的效果
 *
 *  RandomAccessFile 对象包含一个记录指针，用以标示当前读写处的位置。
 *  void seek(long pos)：将文件记录指针定位到 pos 位置
 *
 *  有指针的好处：我们可以用RandomAccessFile这个类，来实现一个多线程断点下载的功能，
 * 用过下载工具的朋友们都知道，下载前都会建立两个临时文件，一个是与 被下载文件大小相同的空文件，
 * 另一个是记录文件指针的位置文件，每次 暂停的时候，都会保存上一次的指针，然后断点下载的时候，
 * 会继续从上 一次的地方下载，从而实现断点下载或上传的功能
 *
 * @author shkstart
 * @create 2021-05-11 11:04
 */
public class IOTest02 {

    @Test
    public void test1() throws FileNotFoundException {
        RandomAccessFile raf1 = null;
        RandomAccessFile raf2 = null;
        try {
            //1.造文件、造流。----构造器中无需放节点流。直接放文件(File)或文件路径(String)
            raf1 = new RandomAccessFile("20210212135721_1.jpg","r");
            raf2 = new RandomAccessFile(new File("20210212135721_12.jpg"),"rw");

            //2.读写操作1
            byte[] buffer = new byte[1024];
            int len;
            while((len = raf1.read(buffer)) != -1){
                raf2.write(buffer,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //3.关闭资源
            if(raf2 != null){
                try {
                    raf2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if(raf1 != null){
                try {
                    raf1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Test
    public void test2() throws IOException {
        RandomAccessFile raf1 = new RandomAccessFile("hello.txt","rw");

        raf1.seek(3);//将指针调到角标为3的位置---后面再write()那么就是从该位置开始覆盖
        raf1.write("xyz".getBytes());//字节流放String，必须将其转化为byte[]数组才能放(字符流才能直接放)

        raf1.close();
    }

    /*
    实现RanmdomAccessFile实现数据的插入效果
     */
    @Test
    public void test3() throws IOException {
        //比如：将hello.txt文件从第四个元素插入xyz
        RandomAccessFile raf1 = new RandomAccessFile("hello.txt","rw");

        raf1.seek(3);//将指针调到角标为3的位置

        //保存指针3后面的所有数据到StringBuilder中
        StringBuilder builder = new StringBuilder((int) new File("hello.txt").length());
        byte[] buffer = new byte[20];
        int len;
        while((len = raf1.read(buffer)) != -1){
            builder.append(new String(buffer,0,len));
        }
        //退回指针，写入"xyz"
        raf1.seek(3);
        raf1.write("xyz".getBytes());

        //将StringBuilder的数据写入到文件
        raf1.write(builder.toString().getBytes());

        raf1.close();
        //思考，将StringBuilder替换为ByteArrayOutputStream
    }
    //将StringBuilder替换为ByteArrayOutputStream
    /*
    1.ByteArrayOutputStream内部有一个数组，write()就是把字节全部存到数组中，然后可以一次性取出来。
    2.如果我们在内存中读取数据时，如果将buffer直接转为String读出，那么可能出现乱码，而用ByteArrayOutputStream
    是将所有的数据先读到内部的数组再一次取出就不存在乱码了。
    3.ByteArrayOutputStream baos = new ByteArrayOutputStream();相当于一个数组，不需要参数，write()添加到数组中。
     */
    @Test
    public void test4() throws IOException {
        //将StringBuilder替换为ByteArrayOutputStream，也就是换了一种临时存储方式
        //1.创建RandomAccessFile对象，用于读和写
        RandomAccessFile rasf = new RandomAccessFile("hello.txt","rw");
        //2.将指针调到第三个元素
        rasf.seek(3);
        //3.创建ByteArrayOutputStream用于临时存储第三个元素后面的所有数据
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //4.将后面的所有数据读到baos中。
        byte[] buffer = new byte[20];
        int len;
        while((len = rasf.read(buffer)) != -1){
            baos.write(buffer,0,len);
        }
        //5.将指针调回到3
        rasf.seek(3);
        //6.依次写出"xyz"和baos----注意，baos是个流，我们需要先调用toString()转化为字符串，再getBytes()转为byte[]
        rasf.write("xyz".getBytes());
        rasf.write(baos.toString().getBytes());

        //7.关闭资源
        baos.close();
        rasf.close();

        //notes：这里RandomAccessFile rasf = new RandomAccessFile("hello.txt","rw");即读取了也写出来。
        //虽然RandomAccessFile具有输入、输出功能。但如果在读取时作为输入通道，就不能再写出作为输出通道。
        //也就是说，rasf可以即输出又输入，但是，二者不能同时进行。一次只能作为一种输入或者输出通道。

    }
}
