package com.atguigu.java;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 练习：图片的加密
 *
 * 提示：将字节数据进行修改：可以将其^5,解密就只需要将加密的文件再^5。（a^b^b = a）
 *
 * @author shkstart
 * @create 2021-05-08 21:16
 */
public class IOTest05 {
    //图片的加密
    @Test
    public void test1(){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            //注：节点流提供了另外的构造器，可以直接写String类型的文件的路径，不需要创建一个File对象。
            //其实调用的还是File文件，其实就是将路径自动包装成了File文件
            fis = new FileInputStream("20210212135721_1.jpg");
            fos = new FileOutputStream("20210212135721_1secret.jpg");

            byte[] buffer = new byte[20];
            int len;
            while((len = fis.read(buffer)) != -1){
                //字节数据进行修改
                //错误的---前面的foreach讲过了，将byte的值赋给了新的变量b，改变的是b，原本的buffer数组没有改变
    //            for(byte b : buffer){
    //                b = (byte)(b ^ 5);
    //            }
                //notes：^代表异或是一种运算符，不是所谓的几次方(Math.pow(a,b))。而a^b^b=a。

                //正确的---将数组的元素取出来进行修改。而不是赋给新的变量
                for(int i = 0;i < len;i++){
                    buffer[i] = (byte)(buffer[i] ^ 5);
                }


                fos.write(buffer,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    //图片的解密
    // 就是a^b^b = a,前面的字节^5了，再^5一次就又回到了原字节a。所以将输入流的文件改成加密得到的文件就行了，
    // 其他的与上面加密过程一样
    @Test
    public void test2(){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            //注：节点流提供了另外的构造器，可以直接写String类型的文件的路径，不需要创建一个File对象。
            //其实调用的还是File文件，其实就是将路径自动包装成了File文件
            fis = new FileInputStream("20210212135721_1secret.jpg");
            fos = new FileOutputStream("20210212135721_14.jpg");

            byte[] buffer = new byte[20];
            int len;
            while((len = fis.read(buffer)) != -1){
                //字节数据进行修改
                //错误的---前面的foreach讲过了，将byte的值赋给了新的变量b，改变的是b，原本的buffer数组没有改变
                //            for(byte b : buffer){
                //                b = (byte)(b ^ 5);
                //            }
                //正确的---将数组的元素取出来进行修改。而不是赋给新的变量
                for(int i = 0;i < len;i++){
                    buffer[i] = (byte)(buffer[i] ^ 5);
                }


                fos.write(buffer,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
