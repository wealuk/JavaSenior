package com.atguigu.java;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;

/**
 * instance的使用
 * 类似于java.util.Date类
 *
 *
 * DateTimeFormatter的使用:格式化或解析日期、时间
 * 类似于SimpleDateFormat
 *
 * @author shkstart
 * @create 2021-04-17 12:49
 */
public class StringTest05 {
    @Test
    public void test1(){
        /*
        instance的使用
         */
        //now():获取本初子午线对应的标准时间
        Instant instant = Instant.now();
        System.out.println(instant);//2021-04-17T05:09:07.169Z ----这是本初子午线的时间，而我们是东8区，
                                    // 故要比我们的时间少8个小时

        //添加时间的偏移量
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofHours(8));
        System.out.println(offsetDateTime);//2021-04-17T13:10:55.724+08:00----这是加上东8的结果

        //toEpochMilli():获取自1970年1月1日0时0分0秒（UTC）开始的毫秒数。--->Date类的getTime()
        long milli = instant.toEpochMilli();
        System.out.println(milli );//1618636554181

        //ofEpochMilli():通过给定的毫秒数，获取Instanct实例 --->Date(long millis)
        Instant instant1 = Instant.ofEpochMilli(1618636554181L);
        System.out.println(instant1);//2021-04-17T05:15:54.181Z
    }

    /*
    DateTimeFormatter的使用
     */
    @Test
    public void test2(){
        //方式一：预定义的标准格式。如：ISO_LOCAL_DATE_TIME;ISO_LOCAL_DATE;ISO_LOCAL_TIME
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        //格式化:日期-->字符串
        LocalDateTime localDateTime = LocalDateTime.now();
        String str1 = formatter.format(localDateTime);
        System.out.println(localDateTime);//2021-04-17T14:25:33.377
        System.out.println(str1);//2021-04-17T14:25:33.377
        //解析：字符串-->日期
        TemporalAccessor parse = formatter.parse("2021-04-17T14:25:33.377");
        System.out.println(parse);//{},ISO resolved to 2021-04-17T14:25:33.377


        //方式二：
        //本地化相关的格式。如：ofLocalizedDateTime()
        //FormatStyle.LONG / FormatStyle.MEDIUM / FormatStyle.SHORT :适用于LocalDateTime
        DateTimeFormatter formatter1 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);//常量不同，出来的格式不同
        //格式化
        String str2 = formatter1.format(localDateTime);
        System.out.println(str2);//2021年4月17日 下午02时45分10秒
        //解析
        TemporalAccessor parse1 = formatter1.parse("2021年4月17日 下午02时45分10秒");//放指定的格式
        System.out.println(parse1);

        //本地化相关的格式。如：ofLocalizedDate()
        //FormatStyle.FULL // FormatStyle.LONG / FormatStyle.MEDIUM / FormatStyle.SHORT:适用于LocalDate
        DateTimeFormatter formatter2 = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        String str3 = formatter2.format(LocalDate.now());
        System.out.println(str3);//2021年4月19日 星期一


        //重点：方式三：自定义的格式。如：ofPattern(“yyyy-MM-dd hh:mm:ss”)
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        //格式化
        String str4 = formatter3.format(LocalDateTime.now());
        System.out.println(str4);//2021-04-19 12:51:20
        //解析
        TemporalAccessor parse2 = formatter3.parse("2021-04-19 12:51:20");
        System.out.println(parse2);//{SecondOfMinute=20, NanoOfSecond=0, HourOfAmPm=0, MicroOfSecond=0, MinuteOfHour=51, MilliOfSecond=0},ISO resolved to 2021-04-19

    }
}
