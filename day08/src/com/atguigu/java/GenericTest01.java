package com.atguigu.java;

import org.junit.Test;

import java.util.*;

/**
 * 泛型的使用
 * 1.jdk 5.0新增的特性
 *
 * 2.在集合中使用泛型：
 * 总结：
 *  ①集合接口或集合类在jdk5.0时都修改为带泛型的结构
 *  ②在实例化集合类时，可以指明具体的泛型类型
 *  ③指明完以后，在集合类或接口中凡是定义类或接口时，内部结构(比如：方法、构造器、属性)使用到类的泛型的位置，都指定为实例化的泛型类型，
 *      比如：add(E e) --->实例化以后：add(Integer e)
 *  ④注意点：泛型的类型必须是类，不能是基本数据类型，需要用到基本数据类型的位置，拿包装类替换。
 *  ⑤如果实例化时，没有指明泛型的类型。默认类型为java.lang.Object类型。
 *
 * notes:1. jdk7新特性：类型推断---------后面的<>可以省略内容---比如：ArrayList<Integer> list = new ArrayList<>();
 *      2.两个比较器也带有泛型。指明泛型类型后，compareTo方法就不需要考虑比较的数据是否是同一个类的了。
 *          比如：class User implements Comparable<User>{//<>的类型就是实现类
 *              public int compareTo(User o){
 *                  return this.name.compareTo(o.name);//省略了很多的判断，方便了很多。用泛型就直接阻止了类型不同的情况。
 *              }
 *          }   comparator同理
 *
 * 3.如何自定义泛型结构：泛型类、泛型接口；泛型方法。见Test02
 *
 *
 * @author shkstart
 * @create 2021-04-30 12:59
 */
public class GenericTest01 {

    //在集合中使用泛型之前的情况
    @Test
    public void test1(){
        ArrayList list = new ArrayList();
        //需求：存放学生的成绩
        list.add(78);
        list.add(78);
        list.add(78);
        list.add(78);
        //问题一：类型不安全
        list.add("Tom");

        for(Object score : list){
            //问题二：强转时，可能出现ClassCastException
            int stuScore = (int)score;

            System.out.println(stuScore);
        }
    }

    //在集合中使用泛型的情况：
    @Test
    public void test2(){
//        ArrayList<Integer> list = new ArrayList<Integer>();//<>不能放基本数据类型
        //jdk7新特性：类型推断---------后面的<>可以省略内容
        ArrayList<Integer> list = new ArrayList<>();
        list.add(78);
        list.add(78);
        list.add(78);
        list.add(78);
        //编译时，就会进行类型检查，保证数据的安全
//        list.add("Tom");无法通过编译

        //方式一：
//        for(Integer score : list){
//            //避免了强转操作
//            int stuScore = score;
//
//            System.out.println(stuScore);
//        }

        //方式二：
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()){
            int stuScore = iterator.next();
            System.out.println(stuScore);
        }
    }

    //在集合中使用泛型的情况：以HashMap为例
    @Test
    public void test3(){
        Map<String,Integer> map = new HashMap<String,Integer>();

        map.put("Tom",87);
        map.put("Jerry",87);
        map.put("Tom",87);

//        map.put(123,"abc");
        //泛型的嵌套
        Set<Map.Entry<String, Integer>> entry = map.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator = entry.iterator();

        while(iterator.hasNext()){
            Map.Entry<String, Integer> e = iterator.next();
            String key = e.getKey();
            Integer value = e.getValue();
            System.out.println(key + "," + value);
        }
    }
}
