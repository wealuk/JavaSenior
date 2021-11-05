package com.atguigu.java;

import org.junit.Test;

/**
 *
 * String的使用:
 *
 * String:字符串，使用一对""引用来表示
 * 1.String声明为final的，不可被继承
 * 2.String实现了Serializable接口：表示字符串是支持序列化的。(与后面的IO有关)
 *         实现了Comparable接口：表示String可以比较大小
 * 3.String内部定义了final char[] value用于存储字符串数据(内容(value)确定后就不能重新赋值了，哪怕是元素也不能改变。要改变的话只能重新开辟内存空间，是不可能在原有的基础上改动的)
 *      notes：非final的成员变量在堆里，final类型的成员变量存放在方法区的常量池中。所以value是在常量池中。
 * 4.String：代表不可变的字符序列，简称：不可变性(原因是3,就是要改变内容，不可能在原有的value上修改，除非重新开辟空间)
 *      体现：1.当对字符串重新赋值时，需要重新指定内存区域赋值，不能使用原有的value进行赋值
 *            2.当对现有的字符串进行连接操作时，也需要重新指定内存区域赋值，不能使用原有的value进行赋值
 *            3.当调用String的replac()方法修改指定字符或字符串时，也需要重新指定内存区域赋值，不能使用原有的value进行赋值
 * 5.通过字面量的方式(区别于new)给一个字符串赋值，此时的字符串值声明在字符串常量池中
 * 6.字符串常量池是不会存储相同内容的字符串的。
 *
 *
 *
 *
 * @author shkstart
 * @create 2021-04-10 9:32
 */
public class StringTest01 {

    @Test
    public void test(){
        String s1 = "abc";//字面量的定义方式
        String s2 = "abc";
//        s1 = "hello";//s1改变是开辟了一个新的内存空间，s2依然是abc。体现字符串的不可变性

        System.out.println(s1 == s2);//比较s1与s2的地址值(true)

        System.out.println(s1);
        System.out.println(s2);

        System.out.println("************");

        String s3 = "abc";
        s3 += "def";
        System.out.println(s3);//abcdef
        System.out.println(s2);//abc,还是abc说明s3是开辟的新的内存空间，而不是在原有的"abc"上修改的

        System.out.println("**************");

        String s4 = "abc";
        String s5 = s4.replace('a', 'm');
        System.out.println(s4);//abc。发现将a改成m后s4还是abc，说明改变后的字符串是开辟的另外一个内存空间。
        System.out.println(s5);//mbc

    }

}
