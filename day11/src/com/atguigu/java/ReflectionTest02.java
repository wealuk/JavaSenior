package com.atguigu.java;

import org.junit.Test;

import java.io.InputStream;
import java.util.Properties;

/**
 * 了解类的加载器
 *
 * 其实类的加载过程分为多个阶段：
 * 类的加载过程：1.类的加载：将类的class文件读入内存，并为之创建一 个java.lang.Class对象。此过程由类加载器完成
 *      2.类的链接：将类的二进制数据合并到JRE中。(正式为类变量（static）分配内存并设置类变量默认初始值的阶段，这些内存 都将在方法区中进行分配。)
 *      3.类的初始化：JVM负责对类 进行初始化(执行类构造器<clinit>(),类构造器<clinit>()方法是由编译期自动收集类中
 *              所有类变量的赋值动作和静态代码块中的语句合并产生的。)
 *  总结：加载类(加载)->加载类中的静态结构，此时静态属性都是默认值(链接)->对静态属性进行显式赋值或者静态代码块赋值(初始化)
 *
 * 类加载器作用是用来把类(class)装载进内存的。JVM 规范定义了如下类型的类的加载器：
 *      引导类加载器：是JVM自带的类 加载器，负责Java平台核心库，用来装载核心类 库。该加载器无法直接获取---核心类库
 *      扩展类加载器：负责jre/lib/ext目录下的jar包或 –  D java.ext.dirs 指定目录下的jar包装入工作库---jar包
 *      系统类加载器：负责java –classpath 或 –D  java.class.path所指的目录下的类与jar包装入工作 ，是最常用的加载器---自定义类
 *
 * @author shkstart
 * @create 2021-05-16 23:19
 */
public class ReflectionTest02 {

    @Test
    public void test1(){
        //对于自定义类，使用系统类加载器进行加载。
        ClassLoader classLoader1 = ReflectionTest02.class.getClassLoader();
        System.out.println(classLoader1);
        //调用系统类加载器的getParent()：获取扩展类的加载器
        ClassLoader classLoader2 = classLoader1.getParent();
        System.out.println(classLoader2);
        //调用扩展类加载器的getParent()：无法获取引导类加载器
        //引导类加载器主要负责加载java的核心类库，无法加载自定义类的。
        ClassLoader classLoader3 = classLoader2.getParent();
        System.out.println(classLoader3);//null

        ClassLoader classLoader4 = String.class.getClassLoader();
        System.out.println(classLoader4);//null,无法拿到引导类加载器
    }
    /*
    properties：用来读取配置文件。
     */
    @Test
    public void test2() throws Exception {
        Properties pros = new Properties();
        //此时的文件默认在当前的module下
        //读取配置文件的方式一：
//        FileInputStream fis = new FileInputStream("jdbc.properties");
//        pros.load(fis);

        //读取配置文件的方式二：使用ClassLoader
        //配置文件默认识别为：当前module下的src下
        ClassLoader classLoader = ReflectionTest02.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream("jdbc1.properties");
        pros.load(is);

        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        System.out.println(user + password);
    }
}
