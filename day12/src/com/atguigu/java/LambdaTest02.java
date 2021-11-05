package com.atguigu.java;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Consumer;

/**
 * Lambda表达式的使用
 *
 * 1.举例： (o1,o2) -> Integer.compare(o1,o2);
 * 2.格式：
 *       -> :lambda操作符 或 箭头操作符
 *       ->左边：lambda形参列表（其实就是接口中的抽象方法的形参列表）
 *       ->右边：lambda体（其实就是重写的抽象方法的方法体）
 *
 * 3.Lambda表达式的使用：（分为六种情况介绍）
 *
 *      总结：
 *          ->左边：Lambda形参列表的参数类型可以省略(类型推断)；如果Lambda形参列表只有一个参数，其一对()也可以省略。
 *          ->右边：Lambda体应该使用一对{}包裹；如果Lambda体只有一条执行语句(可能是return语句)，可以省略一对{}和return关键字(如果省略了{}，那么return也必须省略)
 *
 * 4.Lambda表达式的本质：作为函数式接口的实例(也可以说是函数式接口的匿名实现类的对象)
 *
 * 5.如果一个接口中，只声明了一个抽象方法，则此接口就称为函数式接口。我们可以在一个接口上使用 @FunctionalInterface 注解，这样做可以检查它是否是一个函数式接口。
 *
 * notes：①Lambda表达式必须依赖于函数式接口使用，因为Lambda表达式省略了重写的方法名，故该接口只能有一个抽象方法，
 *      也就是该接口必须是函数式接口，其匿名实现类对象才可以用Lambda代替。--函数式接口的匿名实现类都可以用Lambda代替。
 *        ②总而言之，Lambda表达式就是函数式接口的实例。比直接创建接口的匿名实现类的对象要省略很多
 *      步骤，new 接口(){重写方法(参数列表){方法体}};只需要留参数列表和{方法体}就行了，其他都省略,接口类型 变量名 = (参数列表) -> {方法体}。
 *
 * 6.所以对应函数式接口以前用匿名实现类表示的现在都可以用Lambda表达式来写。
 *
 * @author shkstart
 * @create 2021-05-22 13:32
 */

//自定义函数式接口
@FunctionalInterface    //用FunctionalInterface注解函数式接口，确保该接口在编译时只能有一个抽象方法。当然如果没有该注解，而只有一个抽象方法也是函数式接口
interface MyInterface{
    void method();

    //void method2();
}

public class LambdaTest02 {
    //语法格式一：无参，无返回值
    @Test
    public void test1(){
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("我爱北京天安门");
            }
        };

        r1.run();

        System.out.println("************");

        Runnable r2 = () -> {
            System.out.println("我爱北京故宫");
        };

        r2.run();
    }
    //语法格式二：Lambda 需要一个参数，但是没有返回值。
    @Test
    public void test2(){

        Consumer<String> con = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        con.accept("谎言和誓言的区别是什么？");

        System.out.println("************");

        Consumer<String> con1 = (String s) -> {
            System.out.println(s);
        };
        con1.accept("一个是听的人当真了，一个是说的人当真了");
    }

    //语法格式三：数据类型可以省略，因为可由编译器推断得出，称为“类型推断”
    //注：显式的定义了泛型的话，那么参数类型就是泛型的类型。即使没有显式定义，默认的参数类型也就是Object。所以根本
    // 不需要再声明参数类型了，无论怎样编译器都会推断出参数的类型
    @Test
    public void test3(){

        Consumer<String> con1 = (String s) -> { //因为泛型是String，故可以通过类型推断得出形参类型也是String，故可以不写
            System.out.println(s);
        };
        con1.accept("一个是听的人当真了，一个是说的人当真了");

        System.out.println("************");

        Consumer<String> con2 = (s) -> {
            System.out.println(s);
        };
        con2.accept("一个是听的人当真了，一个是说的人当真了");
    }
    //其他与类型相关的操作
    @Test
    public void test4(){
        ArrayList<String> list = new ArrayList<>();//类型推断，第二个<>不需要写String了

        int[] arr = {1,2,3}; //省略了new int[],同样可以类型推断故可以省略
    }

    //语法格式四：Lambda 若只需要一个参数时，参数的小括号可以省略
    @Test
    public void test5(){
        Consumer<String> con1 = (s) -> {
            System.out.println(s);
        };
        con1.accept("一个是听的人当真了，一个是说的人当真了");

        System.out.println("************");

        Consumer<String> con2 = s -> {
            System.out.println(s);
        };
        con2.accept("一个是听的人当真了，一个是说的人当真了");
    }

    //语法格式五：Lambda 需要两个或以上的参数，多条执行语句，并且可以有返回值
    @Test
    public void test6(){

        Comparator<Integer> com1 = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                System.out.println(o1);
                System.out.println(o2);
                return o1.compareTo(o2);
            }
        };
        System.out.println(com1.compare(12,23));

        System.out.println("*********");

        Comparator<Integer> com2 = (o1,o2) -> { //因为有泛型，故参数类型可以省略。但是参数个数有两个而不是一个，所以()不可以省略
            System.out.println(o1);
            System.out.println(o2);
            return o1.compareTo(o2);
        };
        System.out.println(com2.compare(12,5));
    }

    //语法格式六：当 Lambda 体只有一条语句时，return 与大括号若有，都可以省略
    @Test
    public void test7(){

        Comparator<Integer> com1 = (o1,o2) -> {
            return o1.compareTo(o2);
        };
        System.out.println(com1.compare(12,5));

        System.out.println("*******");

        Comparator<Integer> com2 = (o1,o2) -> o1.compareTo(o2);

        System.out.println(com2.compare(12,5));
    }

    @Test
    public void test8(){
        Consumer<String> con1 = s -> {
            System.out.println(s);
        };
        con1.accept("一个是听的人当真了，一个是说的人当真了");

        System.out.println("********");

        Consumer<String> con2 = s -> System.out.println(s);

        con2.accept("一个是听的人当真了，一个是说的人当真了");
    }
}
