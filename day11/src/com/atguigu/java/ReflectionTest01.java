package com.atguigu.java;

import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Reflection（反射）是被视为动态语言的关键，反射机制允许程序在执行期 借助于Reflection API取得任何类的内部信息
 * ，并能直接操作任意对象的内部属性及方法。---要获取注解需要注解的元注解@Retention是RUNTIME，也就是要能加载到内存中
 *
 * java.lang.Class:反射的源头
 * java.lang.reflect.Method    //reflect作为lang的子包
 * java.lang.reflect.Field
 * java.lang.reflect.Constructor
 *
 * 总结：创建类的对象的方式？
 * 方式一： new + 构造器
 * 方式二： 要创建Xxx类的对象，可以考虑：Xxx、Xxxs、XxxFactory、XxxBuilder类中查看是否有静态方法的存在。
 * 可以调用其静态方法，创建Xxx对象。---常见于构造器私有化的情况下
 * 方式三：通过反射
 *
 * @author shkstart
 * @create 2021-05-13 10:56
 */
public class ReflectionTest01 {

    //反射之前，对于Person的操作
    @Test
    public void test1(){

        //1.创建Person类的对象
        Person p1 = new Person("Tom",12);

        //2.通过对象，调用其内部的属性、方法
        p1.age = 10;
        System.out.println(p1);

        p1.show();

        //在Person类外部,不可以通过Person类的对象调用其内部私有结构。
        //比如：name、showNation()以及私有的构造器
    }

    //反射之后，对于Person的操作
    @Test
    public void test2() throws Exception {
        Class clazz = Person.class;
        //1.通过反射，创建Person类的对象
        Constructor cons = clazz.getConstructor(String.class, int.class);
        Object obj = cons.newInstance("Tom", 12);//本质是Person对象(拿到的是Person的构造器)，故可以向下转型
        Person p = (Person)obj;
        System.out.println(p.toString());

        //2.通过反射，调用对象指定的属性、方法
        Field age = clazz.getDeclaredField("age");
        age.set(p,10);
        System.out.println(p.toString());

        //调用方法
        Method show = clazz.getDeclaredMethod("show");
        show.invoke(p);

        //通过反射，可以调用Person类的私有结构。比如：私有化构造器、方法、属性
        //调用私有的构造器
        Constructor cons1 = clazz.getDeclaredConstructor(String.class);
        cons1.setAccessible(true);
        Person p1 = (Person)cons1.newInstance("Jerry");
        System.out.println(p1);

        //调用私有的属性
        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);
        name.set(p1,"HanMM");
        System.out.println(p1);

        //调用私有的方法
        Method showNation = clazz.getDeclaredMethod("showNation", String.class);
        showNation.setAccessible(true);
        String nation = (String)showNation.invoke(p1,"中国");//相当于String nation = p1.showNation("中国")
        System.out.println(nation);
    }

    //疑问1：通过直接new的方式或反射的方式都可以调用公共的结构，开发中到底用哪个？
    //建议：直接new的方式。
    //什么时候会使用反射的方式。反射的特征：动态性。
    //疑问2：反射机制与面向对象中的封装性是不是矛盾的？如何看待这两个技术。
    //不矛盾。封装性是建议你去调用哪个。而反射是能不能调用。

    /*
    关于java.lang.Class类的理解
    1.类的加载过程：
        程序经过javac.exe命令以后，会生成一个或多个字节码文件(.class结尾)。
        接着我们使用java.exe命令对某个字节码文件进行解释运行。相当于将某个字节码文件
        加载到内存中，此过程就称为类的加载。加载到内存中的类，我们就称为运行时类，此
        运行时类，就作为Class的一个实例。---一个加载的类(运行时类)在JVM 中只会有一个Class实例

    2.换句话说，Class的实例就对应着一个运行时类。(运行时类也就是Class的实例对象，所以调用静态方法的类.xxx也可看为对象.xxx)---Class的实例不一定是运行时类，也可以是基本数据类型等其他
    3.加载到内存中的运行时类，会缓存一定的时间。在此时间之内，我们可以通过不同的方式来获取此运行时类。
    notes：1.运行时类在内存中只有一个,并且会缓存一定时间，而我们可以通过下面的四种不同方法拿到这相同的一个运行时类
           2.Class的实例除了是运行时类，还可以是别的。具体见test4。
     */
    //获取Class的实例的方式(前三种方式需要掌握)
    @Test
    public void test3() throws ClassNotFoundException {
        //方式一：调用运行时类的属性:.class      注：之所以不能直接Person，是因为将其看作一种类型而不是类。加上.class就代表运行时类了。
        Class clazz1 = Person.class;
        System.out.println(clazz1);
        //方式二：通过运行时类的对象，调用getClass()
        Person p1 = new Person();
        Class clazz2 = p1.getClass();
        System.out.println(clazz2);
        //方式三：调用Class的静态方法：forName(String classPath)
        // notes：方式三最常用，方式一必须写出已有的类，没有就编译错误，不能体现反射运行时的动态性。方式二都拿到对象了，
        //    而通过反射能做到第一件事的就是获取运行时类的对象，故没必要反射了。
        Class clazz3 = Class.forName("com.atguigu.java.Person");
//        clazz3 = Class.forName("java.lang.String");
        System.out.println(clazz3);

        System.out.println(clazz1 == clazz2);//true
        System.out.println(clazz1 == clazz3);//true。都一样，通过不同方式获取的同一个运行时类(当然也只有一个)。

        //方式四：使用类的加载器：ClassLoader（了解）
        ClassLoader classLoader = ReflectionTest01.class.getClassLoader();
        Class clazz4 = classLoader.loadClass("com.atguigu.java.Person");
        System.out.println(clazz4);

        System.out.println(clazz1 == clazz4);//true

    }

    //万事万物皆对象？对象.xxx，File，URL，反射(类本身也作为对象)，前端，数据库操作

    //Class实例可以是哪些结构的说明：
    @Test
    public void test4(){
        Class c1 = Object.class;//类
        Class c2 = Comparable.class;//接口
        Class c3 = String[].class;//数组
        Class c4 = int[][].class;//二维数组
        Class c5 = ElementType.class;//元注解
        Class c6 = Override.class;//注解
        Class c7 = int.class;//基本数据类型
        Class c8 = void.class;//void
        Class c9 = Class.class;//Class本身

        int[] a = new int[10];
        int[] b = new int[100];
        Class c10 = a.getClass();
        Class c11 = b.getClass();
        // 只要数组的元素类型与维度一样，就是同一个Class
        System.out.println(c10 == c11);//true,都是一维数组，且元素都是int类型
    }
}



class Person{
    private String name;
    public int age;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    private Person(String name) {
        this.name = name;
    }

    public void show(){
        System.out.println("我是一个中国人");
    }
    private String showNation(String nation){
        System.out.println("我的国籍是：" + nation);
        return nation;
    }
}
