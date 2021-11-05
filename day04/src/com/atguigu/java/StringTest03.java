package com.atguigu.java;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Calendar日历类(抽象类)的使用：
 *
 * 1.实例化：
 * 方式一：创建其子类(GregorianCalendar)的对象
 * 方式二：调用其静态方法getInstance()----Calendar calendar = Calendar.getInstance()方法返回的是
 *                                      GregorianCalendar对象，运用了多态。
 * notes：两种方式其实一样，都是相当于创建了GregorianCalendar的对象。由于第二种方便故多用第二种
 *
 * 2.常用方法：
 * get():多用于获取Calendar中的常量
 * set()：设置这些常量
 * add()：将常量的值增加
 * public final Date getTime()：日历类--->Date，返回一个Date对象
 * public final void setTime()：Date --> 日历类 ， calendar.setTime()将Date对象转换到calendar对象，没有返回值
 *  notes：setTime方法可以将前面三天打鱼两天晒网问题优化，判断输入的年份是该年的多少天可以将。将字符串解析成Date，
 *  再将Date转换为java.sqil.Date,将该sql.Date作为实参赋给setTime,然后就可以调用calendar的get(),获取DAY_OF_YEAR
 *
 *  注意：
 *  获取月份时：一月是0，二月是1，以此类推，12月是11
 * 获取星期时：周日是1，周二是2 ， 。。。。周六是7
 *
 *
 * @author shkstart
 * @create 2021-04-14 19:32
 */
public class StringTest03 {
    @Test
    public void test(){
        //1.实例化
        //方式一：创建其子类(GregorianCalendar)的对象
        //方式二：调用其静态方法getInstance()----两种方式最终是一样的，都是创建GregorianCalendar的对象。该方式比较好用
        Calendar calendar = Calendar.getInstance();//Calendar是不能实例化的，最终得到的还是GregorianCalendar对象
        System.out.println(calendar.getClass());//java.util.GregorianCalendar。说明创建的还是其子类
                                    // GregorianCalendar的对象

        //2.常用方法
        //get()
        int days = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println(days);
        System.out.println(calendar.get(Calendar.DAY_OF_YEAR));

        //set()
        //calendar可变性
        calendar.set(Calendar.DAY_OF_MONTH,22);//calendar是可变的，故是在原基础上改的(不像String)
        days = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(days);//22

        //add()
        calendar.add(Calendar.DAY_OF_MONTH,3);
        days = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(days);//25
        //注：calendar没有减法的方法，故add中加一个负数就作为减法
        calendar.add(Calendar.DAY_OF_MONTH , -3);
        days = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(days);//22

        //getTime():日历类--->Date
        Date date = calendar.getTime();//calendar默认的当前时间
        System.out.println(date);//Thu Apr 22 21:46:51 GMT+08:00 2021

        //setTime():Date --> 日历类
        Date date1 = new Date();
        calendar.setTime(date1);//calendar就是date1转换成日历类后的结果
        days = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(days);//15
    }
}
