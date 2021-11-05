package com.atguigu.java;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * jdk 8.0中日期时间API的测试
 *
 * Date、Calendar缺点：
 * 可变性：像日期和时间这样的类应该是不可变的。
 * 偏移性：Date中的年份是从1900开始的，而月份都从0开始。 格式化：格式化只对Date有用，Calendar则不行。
 * 此外，它们也不是线程安全的；不能处理闰秒等。
 * 因此，引入了新的API
 *
 * LocalDate、LocalTime、LocalDateTime的使用
 *      说明：1.LocalDateTime相较于LocalDate、LocalTime，使用频率要高
 *           2.类似于Calendar
 *   实例化：now()、of()两种静态方法
 *   调用属性：getXxx()、withXxx()、plusXxx()、minusXxx()
 *
 * @author shkstart
 * @create 2021-04-15 22:29
 */
public class StringTest04 {
    @Test
    public void test(){
        //偏移量
        Date date1 = new Date(2020 - 1900 , 9 - 1 , 8);//表示2020-9-8。需要减去偏移量
        System.out.println(date1);//Tue Sep 08 00:00:00 GMT+08:00 2020
        // 没减去偏移值：Fri Oct 08 00:00:00 GMT+08:00 3920
    }

    /*
    LocalDate、LocalTime、LocalDateTime的使用
     */
    @Test
    public void test1(){
        //now():获取当前的日期、时间、日期+时间
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTime = LocalDateTime.now();

        System.out.println(localDate);
        System.out.println(localTime);
        System.out.println(localDateTime);

        //of():设置指定的年、月、日、时、分、秒。没有偏移量
        LocalDateTime localDateTime1 = LocalDateTime.of(2020, 10, 13, 23, 43);
        System.out.println(localDateTime1);

        //getXxx()//获取相关属性
        System.out.println(localDateTime1.getDayOfMonth());//13
        System.out.println(localDateTime1.getDayOfWeek());//TUESDAY
        System.out.println(localDateTime1.getMonth());//OCTOBER
        System.out.println(localDateTime1.getMonthValue());//10
        System.out.println(localDateTime1.getMinute());//43

        //体现不可变性
        //withXxx():设置相关的属性
        LocalDateTime localDateTime2 = localDateTime1.withDayOfMonth(22);
        System.out.println(localDateTime1);//2020-10-13T23:43---原来的并没有变化
        System.out.println(localDateTime2);//2020-10-22T23:43

        LocalDateTime localDateTime3 = localDateTime1.withHour(4);
        System.out.println(localDateTime1);//2020-10-13T23:43---原来的没有变化
        System.out.println(localDateTime3);//2020-10-13T04:43

        //plusXxx():增加
        LocalDateTime localDateTime4 = localDateTime1.plusMonths(3);
        System.out.println(localDateTime1);//2020-10-13T23:43---不可变性
        System.out.println(localDateTime4);//2021-01-13T23:43

        //minus():减少
        LocalDateTime localDateTime5 = localDateTime1.minusDays(6);
        System.out.println(localDateTime1);//2020-10-13T23:43
        System.out.println(localDateTime5);//2020-10-07T23:43
    }
}
