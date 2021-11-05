package com.atguigu.java;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

/**
 * jdk 5.0新增foreach循环，用于遍历集合、数组
 * for(集合元素的类型 局部变量 : 集合对象)
 * for(数组元素的类型 局部变量 : 数组对象)
 *
 * Java 5.0 提供了 foreach 循环迭代访问 Collection和数组。
 * 遍历操作不需获取Collection或数组的长度，无需使用索引访问元素。
 * 遍历集合的底层调用Iterator完成操作。
 * foreach还可以用来遍历数组
 *
 * notes:foreach相当于就是将集合对象\数组的元素依次赋给前面的局部变量，然后打印局部变量，其实就相当于打印
 * 了集合对象\数组的内容
 *
 * 补充：jdk 8后直接提供了forEach的方法，更方便了---但需要新特性，到后面再补充
 *
 * @author shkstart
 * @create 2021-04-23 11:05
 */
public class CollectionTest04 {
    @Test
    public void test1(){
        Collection coll = new ArrayList();
        coll.add(123);
        coll.add(456);
        coll.add(new Person("Jerry" , 20));
        coll.add(new String("Tom"));
        coll.add(false);

        //for(集合元素的类型 局部变量 : 集合对象)
        //内部仍然调用了迭代器。(notes：每循环一次，将coll的一个元素赋给obj，然后再打印obj。)
        for(Object obj : coll){
            System.out.println(obj);
        }
    }

    @Test
    public void test2(){
        int[] arr = new int[]{1,2,3,4,5};
        //for(数组元素的类型 局部变量 : 数组对象)
        for(int i : arr){
            System.out.println(i);
        }
    }

    //练习题
    @Test
    public void test3(){
        String[] arr = new String[]{"MM","MM","MM"};

        //方式一：普通for赋值
//        for(int i = 0;i < arr.length;i++){
//            arr[i] = "GG";
//        }数组三个元素都变成了GG

        //方式二：foreach循环
        for(String s : arr){
            s = "GG";
        }//三个元素没变还是MM。相当于将arr的元素赋给了新的变量，由于String的不可变性，改变的是新的变量，原数组元素是不变的。

        for(int i = 0;i < arr.length;i++){
            System.out.println(arr[i]);
        }
    }

    @Test
    public void test4(){
        //jdk 8的foreach方法,更加方便了
        Collection c = new ArrayList();
        c.add(123);
        c.add(456);
        c.add(789);
        c.add(111);

        c.forEach(System.out::println);
    }
}
