package com.atguigu.java;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * File类的使用
 *
 * 1.File类的一个对象，代表一个文件或一个文件目录(俗称：文件夹)
 * 2.File类声明在java.io包下
 * 3.File类中涉及到关于文件或文件目录的创建、删除、重命名、修改时间、文件大小等方法，
 *      并未涉及到写入或读取文件内容的操作。如果需要读取或写入文件内容，必须使用IO流来完成。
 * 4.后续File类的对象常会作为参数传递到流的构造器中，指明读取或写入的”终点“。
 *
 * @author shkstart
 * @create 2021-05-06 10:33
 */
public class IOTest01 {
    /*
    1.如何创建File类的实例
         File(String filePath)
         File(String parentPath,String childPath)
         File(File parentFile,String childPath)

    2.
    相对路径：相较于某个路径下，指明的路径。
    绝对路径：包含盘符在内的文件或文件目录的路径。

    说明：
        IDEA中：
               如果大家开发使用JUnit中的单元测试方法测试，相对路径即为当前Module下
               如果大家使用main()测试，相对路径即为当前的Project下。
        Eclipse中：
                不管使用单元测试方法还是使用main()测试，相对路径都是当前的Project下

    3.路径分隔符
        windows:\\
        unix:/

     */
    @Test
    public void test1(){
        //构造器1
        File file1 = new File("hello.txt");//相对路径，相对于当前module。---也就是D:\workspace_idea1\JavaSenior\day09中的文件
        File file2 = new File("D:\\workspace_idea1\\JavaSenior\\day09\\he.txt");//绝对路径，会自动补一个\，因为\有转义的意思，故需要补一个\代表路径分隔符

        System.out.println(file1);
        System.out.println(file2);

        //构造器2
        File file3 = new File("D:\\workspace_idea1","JavaSenior");
        System.out.println(file3);

        //构造器3：
        File file4 = new File(file3 , "hi.txt");
        System.out.println(file4);
    }
    /*
public String getAbsolutePath()：获取绝对路径
public String getPath() ：获取路径
public String getName() ：获取名称
public String getParent()：获取上层文件目录路径。若无，返回null
public long length() ：获取文件长度（即：字节数）。不能获取目录的长度。
public long lastModified() ：获取最后一次的修改时间，毫秒值
如下的两个方法适用于文件目录：
public String[] list() ：获取指定目录下的所有文件或者文件目录的名称数组
public File[] listFiles() ：获取指定目录下的所有文件或者文件目录的File数组

public boolean renameTo(File dest):把文件重命名为指定的文件路径
     */
    @Test
    public void test2(){
        File file1 = new File("hello.txt");
        File file2 = new File("d:\\io\\hi.txt");

        System.out.println(file1.getAbsolutePath());
        System.out.println(file1.getPath());
        System.out.println(file1.getName());
        System.out.println(file1.getParent());
        System.out.println(file1.length());
        System.out.println(file1.lastModified());

        System.out.println();

        //不存在的话，都是默认值
        System.out.println(file2.getAbsolutePath());
        System.out.println(file2.getPath());
        System.out.println(file2.getName());
        System.out.println(file2.getParent());
        System.out.println(file2.length());
        System.out.println(new Date(file2.lastModified()));
    }
    @Test
    public void test3(){
        File file = new File("D:\\workspace_idea1\\JavaSenior");//如果该路径不存在，那么就会报NullPointerException
        String[] list = file.list();
        for(String s : list){
            System.out.println(s);
        }

        System.out.println();

        File[] files = file.listFiles();
        for(File f : files){
            System.out.println(f);
        }
    }
    /*
    public boolean renameTo(File dest):把文件重命名为指定的文件路径
        比如：file1.renameTo(file2)为例：
            要想保证返回true(重命名成功)，需要file1在硬盘中是存在的，且file2不能在硬盘中存在。
            notes：如果file2存在，那么在硬盘中就相当于file1覆盖掉file2，就不是重命名了所以不行，就会返回false重命名失败。
     */
    @Test
    public void test4(){
        File file1 = new File("hello.txt");
        File file2 = new File("D:\\io\\hi.txt");

        boolean renameTo = file1.renameTo(file2);
//        boolean renameTo1 = file2.renameTo(file1);//弄回去
        System.out.println(renameTo);
    }

    /*
public boolean isDirectory()：判断是否是文件目录
public boolean isFile() ：判断是否是文件
public boolean exists() ：判断是否存在
public boolean canRead() ：判断是否可读
public boolean canWrite() ：判断是否可写
public boolean isHidden() ：判断是否隐藏
     */
    @Test
    public void test5(){
        File file1 = new File("hello.txt");
//        file1 = new File("hello1.txt");//不存在则都为false

        System.out.println(file1.isDirectory());
        System.out.println(file1.isFile());
        System.out.println(file1.exists());
        System.out.println(file1.canRead());
        System.out.println(file1.canWrite());
        System.out.println(file1.isHidden());

        File file2 = new File("d:\\io");
//        file2 = new File("d:\\io");//不存在都为false
        System.out.println(file2.isDirectory());
        System.out.println(file2.isFile());
        System.out.println(file2.exists());
        System.out.println(file2.canRead());
        System.out.println(file2.canWrite());
        System.out.println(file2.isHidden());
    }
    /*
    创建硬盘中对应的文件或文件目录
public boolean createNewFile() ：创建文件。若文件存在，则不创建，返回false
public boolean mkdir() ：创建文件目录。如果此文件目录存在，就不创建了。如果此文件目录的上层目录不存在，也不创建。
public boolean mkdirs() ：创建文件目录。如果此文件目录存在，就不创建了。如果上层文件目录不存在，一并创建

    删除磁盘中的文件或文件目录
public boolean delete()：删除文件或者文件夹---要想删除成功，io4文件目录下不能有子目录或文件
    删除注意事项：
    Java中的删除不走回收站。
     */
    @Test
    public void test6() throws IOException {
        //文件的创建
        File file1 = new File("hi.txt");
        if(!file1.exists()){
            file1.createNewFile();
            System.out.println("创建成功");
        }else{//文件存在---文件的删除
            file1.delete();
            System.out.println("删除成功");
        }
    }
    @Test
    public void test7(){
        //文件目录的创建
        File file1 = new File("d:\\io\\io1\\io3");

        boolean mkdir = file1.mkdir();
        if(mkdir){
            System.out.println("创建成功1");
        }

        File file2 = new File("d:\\io\\io1\\io4");

        boolean mkdir1 = file2.mkdirs();
        if(mkdir1){
            System.out.println("创建成功2");//只有2创建成功，1没有上层路径io1不创建
        }
        //要想删除成功，io4文件目录下不能有子目录或文件
        File file3 = new File("D:\\io");
        System.out.println(file3.delete());//false,有子目录
    }

    //练习:创建一个与a.txt文件同目录下的另外一个文件b.txt
    File file1 = new File("d:\\a\\a.txt");
    File file2 = new File(file1.getParent() , "b.txt");
}