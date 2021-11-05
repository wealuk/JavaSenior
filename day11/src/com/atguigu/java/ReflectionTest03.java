package com.atguigu.java;

import org.junit.Test;

import java.util.Random;

/**
 * 通过反射创建对应的运行时类的对象
 *
 * 核心是Class实例的newInstance()方法
 *
 * @author shkstart
 * @create 2021-05-17 0:27
 */
public class ReflectionTest03 {
    @Test
    public void test1() throws IllegalAccessException, InstantiationException {
        Class<Person> clazz = Person.class;
        /*
        newInstance():调用此方法，创建对应的运行时类对象，内部调用了运行时类的空参的构造器。 --- 本质上还是方法里调用了构造器来造的对象

        notes:只要造对象，就得用到构造器。只是有的表现为new + 构造器的形式，有的表现为调用方法的形式(方法里还是调用了构造器)，
        所以不能说调用方法能得到对象就不需要构造器了，其实方法里本质还是调用了构造器。

        InstantiationException：调用newInstance()而运行时类没有空参构造器时会报该异常
        IllegalAccessException：调newInstance()而运行时类的空参构造器为private，权限不够就会报

        要想此方法正常的创建运行时类的对象，要求：
        1.运行时类必须提供空参的构造器
        2.空参的构造器的访问权限得够。通常，设置为public。


        在javabe中要求提供一个public的空参构造器。原因：
        1.便于通过反射，创建运行时类的对象
        2.便于子类继承此运行时类，默认调用super(),保证父类有此构造器。
         */
        Person p = clazz.newInstance();//没加泛型的话，默认返回的Object
        System.out.println(p);
    }

    //体会反射的动态性:不像new的形式，在编译时就知道是创建谁的对象。而反射可以只在运行时才能知道创建的谁的对象，体现了动态性。
    @Test
    public void test2(){

        int num = new Random().nextInt(3);//0,1,2中的随机一个
        String classPath = "";
        switch (num){
            case 0:
                classPath = "java.util.Date";
                break;
            case 1:
                classPath = "java.lang.Object";
                break;
            case 2:
                classPath = "com.atguigu.java.Person";
                break;
        }
        try {
            Object obj = getInstance(classPath);
            System.out.println(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    创建一个指定类的对象。
    classPath：指定类的全类名
     */
    public Object getInstance(String classPath) throws Exception{
        Class clazz = Class.forName(classPath);
        return clazz.newInstance();
    }

}
