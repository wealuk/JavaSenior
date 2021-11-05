package com.atguigu.java;

/**
 * 一道面试题
 *
 * @author shkstart
 * @create 2021-04-11 13:45
 */
public class StringTest03 {
    String str = new String("good");
    char[] ch = { 't', 'e', 's', 't' };

    public void change(String str, char ch[]) {
        str = "test ok";//原因：两个都是引用数据类型。实参，形参共享一个地址值。char[]数组没有不可变性，故改变了
        ch[0] = 'b';//形参的内容其实改变的也是实参的内容。而String有不可变性，只要对其内容进行修改(包括重新赋值)
                    //，那么就会重新开辟一个新的地址值。故这里形参重新赋值了，就开辟了新的地址值，不过形参变了，实参
                    //可没有变依然是good的地址值(不可变性是因为value是final的不能改变，具体见前面的笔记)
//        notes：可以说，实参赋给形参的形式对String类型的修改是无效的(字面上的修改，如上。直接赋地址值修改不算)
    }
    public static void main(String[] args) {
        StringTest03 ex = new StringTest03();
        ex.change(ex.str, ex.ch);
        System.out.print(ex.str);//good
        System.out.println(ex.ch);//best
    }

}
