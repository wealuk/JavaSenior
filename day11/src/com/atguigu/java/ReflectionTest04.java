package com.atguigu.java;

import org.junit.Test;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static java.lang.annotation.ElementType.*;

/**test4、5了解下就行
 * 获取当前运行时类的属性结构
 *
 * @author shkstart
 * @create 2021-05-18 16:12
 */
public class ReflectionTest04 {

    /*
    获取当前运行时类的属性结构
     */
    @Test
    public void test1(){
        Class clazz = Person1.class;

        //获取属性结构
        //getFields():获取当前运行时类及其父类中声明为public访问权限的属性
        Field[] fields = clazz.getFields();
        for(Field f : fields){
            System.out.println(f);
        }
        System.out.println();

        //getDeclaredFields():获取当前运行时类中声明的所有属性。（不包含父类中声明的属性）
        Field[] declaredFields = clazz.getDeclaredFields();//declared:声明的
        for(Field f : declaredFields){
            System.out.println(f);
        }
    }

    //抽取出属性的结构：
    //权限修饰符、数据类型、变量名(而变量值只有static静态的才能获取，非静态因为没有对象故无法获取)
    @Test
    public void test2(){
        Class clazz = Person1.class;
        Field[] declaredFields = clazz.getDeclaredFields();
        for(Field f : declaredFields){
            //1.权限修饰符
            int modifier = f.getModifiers();//调用Modifier的toString方法可以
            System.out.print(Modifier.toString(modifier) + "\t");

            //2.数据类型
            Class type = f.getType();
            System.out.print(type.getName()+ "\t");

            //3.变量名
            String name = f.getName();
            System.out.println(name);

            System.out.println();
        }
    }
}


@MyAnnotation(value="hi")
class Person1 extends Creature<String> implements Comparable<String> , MyInterface{

    private String name;
    int age;
    public int id;

    public Person1(){

    }
    @MyAnnotation
    private Person1(String name){
        this.name = name;
    }
    Person1(String name , int age){
        this.name = name;
        this.age = age;
    }

    @MyAnnotation
    private String show(String nation){
        System.out.println("我的国籍是：" + nation);
        return nation;
    }
    public String display(String interest,int age) throws NullPointerException,Exception{
        return interest + age;
    }

    @Override
    public void info() {
        System.out.println("我是一个人");
    }

    @Override
    public int compareTo(String o) {
        return 0;
    }

    @Override
    public String toString() {
        return "Person1{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                '}';
    }

    private static void showDesc(){
        System.out.println("我是一个可爱的人");
    }
}

class Creature<T> implements Serializable {
    private char gender;
    public double weight;

    private void breath(){
        System.out.println("生物呼吸");
    }
    public void eat(){
        System.out.println("生物吃东西");
    }
}

interface MyInterface{
    void info();
}

@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME) //只有RUNTIME才可以加载到内存，故才可以使用反射获取
@interface MyAnnotation{
    String value() default "hello";
}