package com.atguigu.java;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * jdk 8.0之前的日期时间的API测试
 * 1.System类中的currentTimeMillis();
 * 2.java.util.Date和子类java.sql.Date
 * 3.SimpleDateFormat
 * 4.Calendar
 *
 * SimpleDateFormat的使用：SimpleDateFormat对日期Date类(util)的格式化和解析
 * 1.两个操作：
 *  1.1格式化：日期-->字符串
 *  1.2解析：格式化的逆过程：字符串-->日期
 *
 * 2.SimpleDateFormat的实例化
 *
 *
 * 后面有两个练习题需要看一下
 *
 * Calendar见StringTest04
 *
 * @author shkstart
 * @create 2021-04-14 15:12
 */
public class StringTest02 {
    @Test
    public void test() throws ParseException {
        //实例化SimpleDateFormat：使用默认的构造器
        SimpleDateFormat sdf = new SimpleDateFormat();

        //格式化：日期-->字符串
        Date date = new Date();
        System.out.println(date);//Wed Apr 14 15:49:24 GMT+08:00 2021

        String format = sdf.format(date);
        System.out.println(format);//21-4-14 下午3:49

        //解析：格式化的逆过程：字符串-->日期
        String str = "21-7-14 下午3:49";//要确保是这种格式，像"2020-2-3"都是无法解析的(默认构造器)
        Date parse = sdf.parse(str);
        System.out.println(parse);

        //*******按照指定的方式格式化和解析：调用带参的构造器*********(主要使用这种形式)
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //格式化
        String format1 = sdf1.format(date);
        System.out.println(format1);//2021-04-14 04:01:15
        //解析:要求字符串必须符合SimpleDateFormat识别的格式(通过构造器参数体现。没有就是默认的情况)
        //否则，抛异常
        String str1 = "2021-04-14 04:01:15";//这时解析只识别构造器中形参的这种形式。
        Date parse1 = sdf1.parse(str1);
        System.out.println(parse1);//Wed Apr 14 04:01:15 GMT+08:00 2021
    }

    /*
    练习1：字符串"2020-09-08"转换为java.sql.Date
     */
    @Test
    public void test2() throws ParseException {
        String birth = "2020-09-08";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(birth);

        java.sql.Date birthDate = new java.sql.Date(date.getTime());
        System.out.println(birthDate);
    }

    /*
    练习2："三天打鱼两天晒网"  1990-01-01开始 ，然后xxxx-xx-xx这天打鱼还是晒网？

    举例：2020-09-08？总天数

    总天数 % 5 = 1，2，3：打鱼
    总天数 % 5 = 4，0：晒完

    总天数的计算？
    方式一：(date2.getTime() - date1.getTime()) / (1000*60*60*24) + 1
        notes：我的思路是从控制台获取年月日的字符串，然后拼接成xxxx-xx-xx的形式，然后创建SimpleDateFormat时自定义形参
        将其解析成date，然后就可以调用date.getTime()了。
    方式二：1990-01-01 -->2019-12-31 + 2020-01-01 -->2020-09-08
    前面的整年计算分出闰年就行；后面的一年的第多少天就可以转化为Calendar对象然后调用get(Calnedar.DAY_OF_YEAR)
                    (转换的具体步骤：字符串解析成Date，再转为sql.Date,通过calendar.setTime转为日历类，调用get方法)
     */
}
