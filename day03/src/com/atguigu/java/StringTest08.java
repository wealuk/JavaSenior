package com.atguigu.java;


import org.junit.Test;

import java.util.Date;

/**
 * JDK 8 之前日期和时间的API测试：
 * 1.System类中的currentTimeMillis()：返回当前时间与1970年1月1日0时0分0秒之间以毫秒为单位的时间差。该毫秒数也称为时间戳
 * 2.java.util.Date类
 *          |----java.sql.Date类
 *    ①两个构造器的使用
 *    ②两个方法的使用
 *
 * @author shkstart
 * @create 2021-04-12 20:05
 */
public class StringTest08 {
    //1.System类中的currentTimeMillis()
    @Test
    public void test1(){
        long time = System.currentTimeMillis();
        //返回当前时间与1970年1月1日0时0分0秒之间以毫秒为单位的时间差。
        //该毫秒数也称为时间戳
        System.out.println(time);
    }

    /*
    java.util.Date类
           |----java.sql.Date类(util.Date的子类)

     1.两个构造器的使用
        >构造器一：Date():创建一个对应当前时间的Date对象
        >构造器二：Date(long 毫秒数)创建指定毫秒数的Date对象
     2.两个方法的使用
        >toString():显示当前的年、月、日、时、分、秒
        >getTime():获取当前Date对象对应的毫秒数(时间戳)。

     3.java.sql.Date对应着数据库中的日期类型的变量
        >如何实例化
        >如何将java.util.Date对象转换为java.sql.Date对象(sql转util利用多态，直接赋值)
     */
    @Test
    public void test2(){
    //构造器一：Date():创建一个对应当前时间的Date对象
        Date date1 = new Date();
        System.out.println(date1.toString());//Mon Apr 12 20:22:04 GMT+08:00 2021

        System.out.println(date1.getTime());//1618230249932

        //构造器二：创建指定毫秒数的Date对象
        Date date2 = new Date(1618230249932L);
        System.out.println(date2.toString());//Mon Apr 12 20:24:09 GMT+08:00 2021

        //创建java.sql.Date对象
        java.sql.Date date3 = new java.sql.Date(32222222245L);//没有空参构造器，必须初始化构造器内容
        System.out.println(date3);//1971-01-09，直接打印年月日,不同于util.Date

        //如何将java.util.Date对象转换为java.sql.Date对象
        //情况一：
//        Date date4 = new java.sql.Date(24588888888887L);
//        java.sql.Date date5 = (java.sql.Date)date4;//利用多态的方式直接进行转换
        //情况二：主要是这种
        Date date6 = new Date();//也就是将util.Date调用getTime转换为毫秒数，然后再将其赋给java.sql.Date的构造器就行了
        java.sql.Date date7 = new java.sql.Date(date6.getTime());
    }
}
