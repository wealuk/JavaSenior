package com.atguigu.java;

import org.junit.Test;

import java.util.*;

/**
 * 1.泛型在继承方面的体现
 * ①虽然类A是类B的父类，但是G<A>和G<B>二者不具备子父类关系，二者是并列关系。比如List<Object>和List<String>两者是并列的不是子父类，不能将后者赋给前者
 * ②补充：类A是类B的父类，A<G>是B<G>的父类，比如常见的List<String> list = new ArrayList<String>();就是子类赋给父类的形式
 *
 * 2.通配符的使用
 * 通配符：?
 * 类A是类B的父类，G<A>和G<B>是没有关系的，二者共同的父类是：G<?>
 * notes:如果没有共同父类的话，彼此之前是并列的，那么像遍历操作就需要多个重载的方法遍历各自的；有了通配符代表共同的父类，
 * 就可以利用多态(子类赋给父类)的方式，创建一个父类的遍历操作就行了，子类要用只需要利用多态将子类赋给父类就行了。
 *
 * 通配符注意点：添加(写入)：对于List<?>就不能向其内部添加数据。（null除外）
 *      获取(读取):允许读取数据，读取的数据类型为Object。
 *
 * 3.有限制条件的通配符的使用
 *           ? extends A:
 *                     G<? extends A>可以作为G<A>和G<B>的父类，其中B是A的子类
 *           ? super A:
 *                     G<? super A>可以作为G<A>和G<B>的父类，其中B是A的父类
 *   注意：一样可以读取，但写入的话 ? extends A没有明确最小子类，容易出现父类添加到子类中的情况所以不行；? super A
 *        A作为范围内的最小类，那么A及A的子类都可以作为内容添加到父类中。-----见test中的笔记
 *
 * @author shkstart
 * @create 2021-05-01 10:57
 */
public class GenericTest03 {

    /*
    1.泛型在继承方面的体现

    ①虽然类A是类B的父类，但是G<A>和G<B>二者不具备子父类关系，二者是并列关系。比如List<Object>和List<String>两者是并列的不是子父类，不能将后者赋给前者

    ②补充：类A是类B的父类，A<G>是B<G>的父类，比如常见的List<String> list = new ArrayList<String>();就是子类赋给父类的形式
     */
    @Test
    public void test1(){

        Object obj = null;
        String str = null;
        obj = str;//多态，子类赋给父类

        Object[] arr1 = null;
        String[] arr2 = null;
        arr1 = arr2;//子类数组也可以赋给多态。每一个Object元素都可以放子类String的元素
        //编译不通过
//        Date date = new Date();
//        str = date;不具有子父类关系
        List<Object> list1 = null;
        List<String> list2 = new ArrayList<>();
        //此时的list1和list2的类型不具有子父类关系(即使Object是String的父类)
        //编译不通过
//        list1 = list2;
        /*
        反证法：
        假设list1 = list2;
            list1.add(123);导致混入非String的数据。出错
         */

        show(list1);
//        show(list2);两者是并列关系，只能添加List<Object>这种类型的
        show1(list2);

    }

    public void show1(List<String> list){

    }

    public void show(List<Object> list){

    }

    @Test
    public void test2(){
        AbstractList<String> list1 = null;
        List<String> list2 = null;
        ArrayList<String> list3 = null;
        list1 = list3;
        list2 = list3;

        List<String> list4 = new ArrayList<String>();//也是子父类关系，将ArrayList<String>赋给List<String>
    }

    /*
    2.通配符的使用
    通配符：?

    类A是类B的父类，G<A>和G<B>是没有关系的，二者共同的父类是：G<?>
     */

    @Test
    public void test3(){
        List<Object> list1 = null;
        List<String> list2 = null;

        List<?> list = null;//作为两个结构的通用父类

        list = list1;
        list = list2;

        print(list1);
        print(list2);

        List<String> list3 = new ArrayList<>();
        list3.add("AA");
        list3.add("AA");
        list3.add("AA");
        list = list3;
        //添加(写入)：对于List<?>就不能向其内部添加数据。
        //除了添加null之外----添加的一定是对象(?)，是对象就一定都有null，不会有数据冲突
//        list.add("DD");
//        list.add("?");
        list.add(null);

        //获取(读取):允许读取数据，读取的数据类型为Object。---读取的？也就是不知道什么类型，但都可以用object来接收
        Object o = list.get(0);
        System.out.println(o);
    }
    //比如打印的话，如果没有通配符，那么就必须写很多重载方法去遍历不同的。此时有共同父类List<?>就可以根据多态写一个就行了，然后子类赋给父类
    public void print(List<?> list){
        Iterator<?> iterator = list.iterator();
        while (iterator.hasNext()){
            Object obj = iterator.next();//? obj = iterator.next()是错误的写法
            System.out.println(obj);
        }
    }

    /*
    3.有限制条件的通配符的使用
          ? extends A:
                    G<? extends A>可以作为G<A>和G<B>的父类，其中B是A的子类
          ? super A:
                    G<? super A>可以作为G<A>和G<B>的父类，其中B是A的父类
     */
    @Test
    public void test4(){
        List<? extends Person> list1 = null;
        List<? super Person> list2 = null;

        List<Student> list3 = null;
        List<Person> list4 = null;
        List<Object> list5 = null;

        list1 = list3;
        list1 = list4;
//        list1 = list5;//泛型类型只能是Person及其子类

//        list2 = list3;//泛型类型只能是Person及其父类
        list2 = list4;
        list2 = list5;

        //读取数据：
        list1 = list3;
        Person p = list1.get(0);//数据最大到Person，故可以用Person接收
        //编译不通过
//        Student s = list1.get(0);-最大到Person类

        list2 = list4;
        Object obj = list2.get(0);//最大到Object
        //编译不通过
//        Person obj = list2.get(0);-最大到Object


        //写入数据：
        //编译不通过
//        list1.add(new Student());--list1实例化时必是其子类，如果其还是Student类的子类，那么就是父类放到子类，不符合子类的内容

        //编译通过
        list2.add(new Person());//最小类是Person类，而这个实例化必是Person类及其父类，而Person类作为子类添加到父类是可以的
        list2.add(new Student());//Person作为最小子类添加到父类完全可以，同样Person的子类更是可以添加。就像Object中可以添加任何子类一样

    }

}

class Person{

}
class Student extends Person{

}
