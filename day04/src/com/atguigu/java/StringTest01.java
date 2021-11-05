package com.atguigu.java;

import org.junit.Test;

/**
 * Debug调试与eclipse差不多
 *
 * @author shkstart
 * @create 2021-04-12 21:45
 */
public class StringTest01 {

    @Test
    public void test(){
        String str = null;
        StringBuffer sb = new StringBuffer();
        sb.append(str);//append源码中，如果str == null ,那么就会调用appendNull的方法，将null转换为"null"添加到char[]中
        System.out.println(sb.length());//4，"null"
        System.out.println(sb);//"null"(记住是字符串的"null",不是null(空))

        StringBuffer sb1 = new StringBuffer(str);//抛异常NullPointerException(源码中str.length()为null)
        System.out.println(sb1);//

    }
}
