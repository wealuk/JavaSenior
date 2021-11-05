package com.atguigu.java;

import org.junit.Test;

/**
 * 关于StringBuffer和StringBuilder的使用
 *
 * 面试题：String、StringBuffer、StringBuilder三者的异同
 * String:不可变的字符序列，底层使用char[]存储。
 * StringBuffer:可变的字符序列：线程安全，效率低。底层使用char[]存储.
 * StringBuilder:可变的字符序列：jdk5.0新增的，线程不安全，效率高。底层使用char[]存储。
 * notes：下面两个是可变的，是因为他们的char[] value没有用final修饰，故可变。
 *
 * 源码解析：
 * String str = new String();//char[] value = new char[0]
 * String str1 = new String("abc");//char[] value = new char[]{'a','b','c'};
 *
 * StringBuffer sb1 = new StringBuffer();//char[] value = new char[16]//底层创建了一个长度为16的数组
 * System.out.println(sb1.length());//此时返回的0.返回的是实例化元素的个数，不是数组的长度(16)
 * sb1.append('a');//value[0] = 'a';
 * sb1.append('b');//value[1] = 'b';
 *
 * StringBuffer sb2 = new StringBuffer('abc');//char[] value = new char["abc".length + 16];
 *
 * 问题1：System.out.println(sb2.length());//3，重写了方法，返回的不是数组长度而是里面实例化的元素的个数(count)
 * 问题2：扩容问题：如果要添加的数据底层数组容不下了，那就需要扩容底层的数组。
 *        默认情况下，扩容为原来容量的2倍+2，同时将原有数组中的元素复制到新的数组中(将原有的value.length的二进制向左移动一个，转换回来十进制就是翻了2倍，然后再加2)
 *
 *        指导意义:开发中建议大家使用：StringBuffer(int capacity)或StringBuilder(int capacity)
 *
 * StringBuffer的常用方法(StringBuilder是一样的)：
 * StringBuffer append(xxx)：提供了很多的append()方法，用于进行字符串拼接
 * StringBuffer delete(int start,int end)：删除指定位置的内容
 * StringBuffer replace(int start, int end, String str)：把[start,end)位置替换为str
 * StringBuffer insert(int offset, xxx)：在指定位置插入xxx
 * StringBuffer reverse() ：把当前字符序列逆转
 * public int indexOf(String str)：返回字符串首次出现的位置
 * public String substring(int start,int end):返回一个从start开始到end索引结束的左闭右开区间的子字符集(注意：这个不是在原数组上修改的，而是返回一个子字符集)
 * public int length()：返回的初始化过的元素个数(count，记住该方法在StringBuffer已经重写过了，不再返回数组的length)
 * public char charAt(int n )：获取索引为n的字符
 * public void setCharAt(int n ,char ch)：修改索引为n的一个字符
 *
 *         总结：
 *         增：append(xxx)
 *         删：delete(int start,int end)
 *         改：replace(int start, int end, String str)、etCharAt(int n ,char ch)
 *         查：charAt(int n )
 *         插：insert(int offset, xxx)
 *         长度：length()
 *         遍历：for() + charAt() / toString()
 *
 * 三者的效率对比见@test3
 *
 * @author shkstart
 * @create 2021-04-12 12:31
 */
public class StringTest07 {
    @Test
    public void test1(){
        StringBuffer sb1 = new StringBuffer("abc");
        sb1.setCharAt(0,'m');
        System.out.println(sb1);//mbc，可变的

        StringBuffer sb2 = new StringBuffer();
        System.out.println(sb2.length());//0(不是16)
    }

    @Test
    public void test2(){
        StringBuffer s1 = new StringBuffer("abc");
        s1.append(1);
        s1.append("1");
        System.out.println(s1);//abc11
        //如果要删除c1，那么调用的delete方法的坐标为(2,4),依然是左闭右开，删掉的是索引为2，3的元素。
        // *******notes：记住，凡是涉及到start到end的范围，都是左闭右开。[int start , int end)
//        s1.delete(2,4);
//        s1.replace(2,4,"hello");//abhello1.也是第三个和第四个元素被替换掉。[2,4)取得2，3
//        s1.insert(2,false);//abfalsec11
//        s1.reverse();//11cba，反转s1的元素,直接在s1的value数组上改的。
        //notes：前面的替代，删除，添加，反转，插入都是直接在原数组上修改的。而后面的这个subString并不是在原数组上修改的
        String s2 = s1.substring(2, 4);
        System.out.println(s1);//abc11，没有变，subString并不是修改了原数组
        System.out.println(s1.length());
        System.out.println(s2);//c1
    }


    /*
    对比String、StringBuffer、StringBuilder三者的效率
    从高到低排列：StringBuilder>StringBuffer>String
     */
    @Test
    public void test3(){
        long startTime = 0L;  long endTime = 0L;  String text = "";
        StringBuffer buffer = new StringBuffer("");
        StringBuilder builder = new StringBuilder("");
//开始对比
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {
            buffer.append(String.valueOf(i));
        }
        endTime = System.currentTimeMillis();
        System.out.println("StringBuffer的执行时间："  + (endTime - startTime));
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {
            builder.append(String.valueOf(i));
        }
        endTime = System.currentTimeMillis();
        System.out.println("StringBuilder的执行时间：" + (endTime - startTime));
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 20000; i++) {
            text = text + i;
        }
        endTime = System.currentTimeMillis();  System.out.println("String的执行时间：" + (endTime - startTime));

    }
}
