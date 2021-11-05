package com.atguigu.java;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 获取构造器结构
 *
 * 这三节反射获取类的属性、方法、构造器的总结：getXxxs():获取当前运行时类和父类中声明为public的结构(构造器不包括父类)
 *                                  getDeclaredXxxs():获取当前运行时类中声明的所有该结构
 * @author shkstart
 * @create 2021-05-18 20:30
 */
public class ReflectionTest06 {
    @Test
    public void test1(){
        Class clazz = Person1.class;
        //getConstructors():获取当前运行时类中声明为public的构造器---并没有父类的构造器
        Constructor[] constructors = clazz.getConstructors();
        for(Constructor c : constructors){
            System.out.println(c);
        }

        System.out.println();

        //getDeclaredConstructors():获取当前运行时类中声明的所有的构造器
        Constructor[] declaredConstructors = clazz.getDeclaredConstructors();
        for(Constructor c : declaredConstructors){
            System.out.println(c);
        }
    }

    /*
    创建运行时类的父类
     */
    @Test
    public void test2(){
        Class clazz = Person1.class;

        Class superclass = clazz.getSuperclass();
        System.out.println(superclass);
    }
    /*
    创建运行时类的带泛型的父类
     */
    @Test
    public void test3(){
        Class clazz = Person1.class;

        Type genericSuperclass = clazz.getGenericSuperclass();
        System.out.println(genericSuperclass);

        ParameterizedType paramType = (ParameterizedType) genericSuperclass;
        //获取其父类的泛型
        Type[] actualTypeArguments = paramType.getActualTypeArguments();
//        System.out.println(actualTypeArguments[0].getTypeName());
        System.out.println(((Class)actualTypeArguments[0]).getName());
    }

    /*
    获取运行时类实现的接口---用于动态代理
     */
    @Test
    public void test4(){
        Class clazz = Person1.class;
        Class[] interfaces = clazz.getInterfaces();//只有运行时类实现的接口，不包括其父类实现的接口
        for(Class c : interfaces){
            System.out.println(c);
        }

        System.out.println();

        //获取运行时类父类实现的接口
        Class[] interfaces1 = clazz.getSuperclass().getInterfaces();
        for(Class c : interfaces1){
            System.out.println(c);
        }
    }

    /*
    获取运行时类所在的包
     */
    @Test
    public void test5(){
        Class clazz = Person1.class;
        Package aPackage = clazz.getPackage();
        System.out.println(aPackage);
    }

    /*
    获取运行时类声明的注解---多用于框架
     */
    @Test
    public void test6(){
        Class clazz = Person1.class;

        Annotation[] annotations = clazz.getAnnotations();
        for(Annotation a : annotations){
            System.out.println(a);
        }
    }

}
