package com.atguigu.java;

import org.junit.Test;

/**
 * String的实例化方式：(给String赋值本质就是给final char[] value赋值)
 * 方式一：通过字面量定义的方式
 * 方式二：通过new + 构造器的方式
 *
 *面试题：String s = new String("abc");方式创建对象，在内存中创建了几个对象？
 *      两个：一个是堆空间中new的结构，另一个是char[]对应的常量池中的数据:"abc"
 *
 * 结论：
 *      1.常量与常量的拼接结果在常量池。且常量池中不会存在相同内容的常量。
 *      2.只要其中有一个是变量(s1,s2.....这些)，结果就在堆中(相当于在堆中new了个结构，该结构再指向常量池)
 *      3.如果拼接的结果调用intern()方法，返回值就在常量池中(无论直接在常量池或是连接堆再指向常量池其方法的返回值都是直接在常量池)
 *  notes：如果一个String s1用final修饰，那么s1 + "JavaEE"符合第一条，是两个常量的拼接，是指向常量池的。见test3
 *
 * @author shkstart
 * @create 2021-04-10 10:56
 */
public class StringTest02 {

    @Test
    public void test1(){
        //通过字面量定义的方式：此时的s1和s2的数据JavaEE声明在方法区中的字符串常量池中(s1,s2直接指向常量池)
        String s1 = "JavaEE";
        String s2 = "JavaEE";
        //通过new + 构造器的方式：此时的s3和s4保存的地址值，是数据在堆空间中开辟空间以后对应的地址值
        //(s3,s4指向堆空间的结构，而堆空间的结构再指向常量池中的value)
        String s3 = new String("JavaEE");
        String s4 = new String("JavaEE");

        System.out.println(s1 == s2);//true。指向同一个常量池
        System.out.println(s1 == s3);//false.s3的堆空间结构所指向的常量池跟s1是相同的，但s3指向堆空间是不可能跟s1相同
        System.out.println(s1 == s4);//false。同上
        System.out.println(s3 == s4);//false。new的两个不同地址值(堆中)，但他们的堆空间结构指向的也是同一个常量池
        //notes: == 比较的地址值，故后面三个为false。如果使用的equals那么就是比较的内容，则四个都为true
        //      重写的String的equals是比较的常量池，也就是内容

        Person p1 = new Person("Ton" , 18);
        Person p2 = new Person("Ton" , 18);
        System.out.println(p1.name.equals(p2.name));//比较的内容，true。(内容相同)
        System.out.println(p1.name == p2.name);//比较的地址值，true。指向的同一个常量池(常量池不会存储相同内容的字符串的)

        p1.name = "Jerry";
        System.out.println(p2.name);//Tom

    }
        @Test
    public void test2(){
        String s1 = "JavaEE";
        String s2 = "hadop";

        String s3 = "JavaEEhadop";
        String s4 = "JavaEE" + "hadop";
        String s5 = s1 + "hadop";
        String s6 = "JavaEE" + s2;
        String s7 = s1 + s2;
            System.out.println();
            System.out.println(s3 == s4);//true
            System.out.println(s3 == s5);//flase。s3是直接指向常量池，而s5则相当于new了个堆空间结构，堆空间结构再指向常量池
            System.out.println(s3 == s6);//flase。同上，s3与s6(s5)一个指向常量池，一个指向堆。绝对不同，但是s6指向的堆
            System.out.println(s3 == s7);//flase。空间结构所指向的常量池与s3相同
            System.out.println(s5 == s6);//flase。5，6，7都是new了一个结构，堆中地址值不同。
            System.out.println(s5 == s7);//flase
            System.out.println(s6 == s7);//flase

            String s8 = s5.intern();//返回值的得到的s8使用的常量池中已经存在的"JavaEEhadop"(将s5变成常量值中的返回值)
            System.out.println(s3 == s8);//true

        }
        //****面试题：**********************************************************
    @Test
    public void test3(){
        String s1 = "JavaEEhadoop";
        String s2 = "JavaEE";
        String s3 = s2 + "hadoop";
        System.out.println(s1 == s3);//false.一个指向常量池，一个指向堆

        final String s4 = "JavaEE";//s4是一个常量
        String s5 = s4 + "hadoop";//在这里s4不是变量，因为用final修饰是一个常量。所以符合第一条常量与常量的相加，而不是第二条
        System.out.println(s1 == s5);//true
    }
}

class Person{
    String name;
    int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
