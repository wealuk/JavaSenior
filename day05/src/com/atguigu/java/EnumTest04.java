package com.atguigu.java;

import java.util.ArrayList;

/**
 * 注解(Annotation)的使用
 *
 * 1.理解注解 (Annotation) ：
 *   ①jdk 5.0新增的功能
 *
 *   ②Annotation 其实就是代码里的特殊标记, 这些标记可以在编译, 类加 载, 运行时被读取, 并执行相应的处理。
 *   通过使用 Annotation, 程序员 可以在不改变原有逻辑的情况下, 在源文件中嵌入一些补充信息。
 *
 *   ③在JavaSE中，注解的使用目的比较简单，例如标记过时的功能，忽略警告等。在JavaEE/Android中注解
 *   占据了更重要的角色，例如 用来配置应用程序的任何切面，代替JavaEE旧版中所遗留的繁冗 代码和XML配置等。
 *
 * 2.Annotation的使用示例
 * 示例一：生成文档相关的注解
 * 示例二：在编译时进行格式检查(JDK内置的三个基本注解)
 * @Override: 限定重写父类方法, 该注解只能用于方法
 * @Deprecated: 用于表示所修饰的元素(类, 方法等)已过时。通常是因为所修饰的结构危险或存在更好的选择
 * @SuppressWarnings: 抑制编译器警告-----比如声明的东西没有使用就可以用这个来抑制警告(Eclipse比较明显)
 * 示例三：跟踪代码依赖性，实现替代配置文件功能
 *
 * 3.如何自定义注解：参照@SuppressWarnings定义
 *      ①注解声明为：@interface
 *      ②内部定义成员，通常使用value表示
 *      ③可以指定成员的默认值，使用defualt表示
 *      ④如果自定义注解没有成员，表明是一个标识作用(overried)
 *
 *    如果注解有成员，在使用注解时，需要指明成员的值。(当然如果defualt赋了默认的值，可以不显式的指明了，
 *    若是觉得值不行，也可以指明。)
 *    自定义注解必须配上注解的信息处理流程(反射)才有意义
 *    自定义注解通常都会指明两个元注解：Retention、Target
 *
 * 4.jdk 提供的4种元注解
 *  元注解：对现有的注解进行解释说明的注解
 * Retention：指定所修飾的 Annotation 的生命周期：SOURCE\CLASS(默认行为)\RUNTIME---这三者是RetentionPolicy枚举类中的三个对象
 *            只有声明为RUNTIME生命周期的注解，才能通过反射获取。（SOURCE在编译前就无了，CLASS可以过编译但过不了运行）
 *          注:没有使用@Retention的，默认使用的是@Retention(RetentionPolicy.CLASS)
 * Target:用于指定被修饰的 Annotation 能用于修饰哪些程序元素
 * ********出现的频率较低********
 * Documented：表示所修饰的注解在被javadoc解析时，保留下来
 * Inherited：被它修饰的 Annotation 将具有继承性。(Annotation修饰一个类，该类的子类也会有Annotation修饰一个类)
 *
 * 5.通过反射获取注解信息---到反射内容时系统讲解
 * 前提：要求此注解的元注解Retention中声明的生命周期状态为：RUNTIME
 *
 * 6.jdk 8中注解的新特性：可重复注解、类型注解
 *
 * 6.1可重复注解：①在EnumTest05上声明@Repeatable，成员值为EnumTest05s.class
 *               ②EnumTest05在Target和Retention等元注解 要跟EnumTest05s相同
 *
 * 6.2 类型注解：
 * ElementType.TYPE_PARAMETER 表示该注解能写在类型变量的声明语句中（如：泛型声明）。
 * ElementType.TYPE_USE 表示该注解能写在使用类型的任何语句中
 *
 * @author shkstart
 * @create 2021-04-21 12:54
 */
public class EnumTest04 {
    public static void main(String[] args) {
        @SuppressWarnings("unused")
        int n = 10;//深色的

        int m = 10;//没有抑制的话就是暗色的

        @SuppressWarnings({"unused" , "rawtypes"})//没用以及没有泛型
        ArrayList list = new ArrayList();
    }
}


//jdk 8.0之前的写法
//@EnumTest05s(@EnumTest05(value = "hello") , @EnumTest05(value = "hi"))////如果用了defualt就不需要指明value了。这里value=可以省略
@EnumTest05(value = "hello")//首先要满足6.1的条件才能这么使用
@EnumTest05(value = "hi")
class Person{
    @EnumTest05("hello")
    public Person(){

    }

    @EnumTest05("hello")
    public void walk(){

    }
}

class Generic<@EnumTest05 T>{//与Target中的TYPE_PARAMETER有关
    public void show() throws @EnumTest05 RuntimeException{//下面的注解则与Target中的TYPE_USE有关
        ArrayList<@EnumTest05 String> list = new ArrayList<>();
        int num = (@EnumTest05 int) 10L;
    }
}
