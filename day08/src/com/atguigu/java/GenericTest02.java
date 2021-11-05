package com.atguigu.java;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 *如何自定义泛型结构：泛型类、泛型接口；泛型方法
 *
 * 一.关于自定义泛型类、泛型接口：
 *
 * 1.泛型类可能有多个参数，此时应将多个参数一起放在尖括号内。比如：<E1,E2,E3>
 * 2.泛型类的构造器如下：public GenericClass(){}。 而下面是错误的：public GenericClass<E>(){}
 * 3.实例化后，操作原来泛型位置的结构必须与指定的泛型类型一致。
 * 4.泛型不同的引用不能相互赋值。-----比如：ArrayList<Integer> list1 = null;ArrayList<String> list2 = null;list1 = list2是错误的
 *      >尽管在编译时ArrayList<String>和ArrayList<Integer>是两种类型，但是，在运行时只有一个ArrayList被加载到JVM中。
 * 5.泛型如果不指定，将被擦除，泛型对应的类型均按照Object处理，但不等价 于Object。经验：泛型要使用一路都用。要不用，一路都不要用。
 * 6.如果泛型结构是一个接口或抽象类，则不可创建泛型类的对象。
 * 7.jdk1.7，泛型的简化操作：ArrayList<Fruit> flist = new ArrayList<>();----jdk7新特性：类型推断
 * 8.泛型的指定中不能使用基本数据类型，可以使用包装类替换。
 * 9.在静态方法 中不能使用类的泛型。----泛型是在创建类的对象时指明类型后使用，而静态方法是在对象之前，所以无法在静态中用泛型
 * 10.异常类不能是泛型的----notes:try-catch中catch的()不能放泛型
 * 11.不能使用new E[]。但是可以：E[] elements = (E[])new Object[capacity];
 * 参考：ArrayList源码中声明：Object[] elementData，而非泛型参数类型数组。
 * 12.父类有泛型，子类可以选择保留泛型也可以选择指定泛型类型：
 * 子类不保留父类的泛型：按需实现
     * 没有类型 擦除:class Son1 extends Father {// 等价于class Son extends Father<Object,Object>{}---全是object
     * 具体类型:class Son2 extends Father<Integer, String> {}---son2无需指明泛型类型，用的父类的泛型类型
 * 子类保留父类的泛型：泛型子类：
     * 全部保留：class Son3<T1, T2> extends Father<T1, T2> {}---Son3使用子类自己定义的泛型
     * 部分保留：class Son4<T2> extends Father<Integer, T2> {}----第一个泛型使用父类的Integer，第二个使用自己定义的泛型
 * 结论：子类必须是“富二代”，子类除了指定或保留父类的泛型，还可以增加自己的泛型
 *
 * 二、泛型方法：
 *  1.方法，也可以被泛型化，不管此时定义在其中的类是不是泛型类。在泛型方法中可以定义泛型参数，此时，参数的类型就是传入数据的类型。
 *  泛型方法的格式：[访问权限] <泛型> 返回类型	方法名([泛型标识 参数名称])	抛出的异常
 *
 *  2.泛型方法：在方法中出现了泛型的结构，泛型参数与类的泛型参数没有任何关系。
 *  换句话说，泛型方法所属的类是不是泛型类都没有关系
 *  泛型方法，可以声明为静态的。原因：泛型参数是在调用方法时确定的。并非在实例化类时确定。
 *
 *  notes：泛型接口、泛型类根据创建对象时指明泛型来确定泛型类型；而泛型方法可以根据给进去的实参确定泛型类型，
 *  也可以根据返回值确定(没有泛型的形参时)。
 * 
 *
 * @author shkstart
 * @create 2021-04-30 17:23
 */
public class GenericTest02 {

    @Test
    public void test1(){
        //如果定义了泛型类，实例化没有指明类的泛型，则认为此泛型类型为Object类型
        //要求：如果大家定义了类是带泛型的，建议在实例化时要指明类的泛型。
        Order order = new Order();
        order.setOrderT(123);
        order.setOrderT("abc");

        //建议：实例化时指明类的泛型
        Order<String> order1 = new Order<>("orderAA" , 1001 , "AA");

        order1.setOrderT("BB");
    }

    @Test
    public void test2(){
        SubOrder sub1 = new SubOrder();
        //由于子类在继承带泛型的父类时，指明了(父类)泛型类型，则实例化子类对象时，不再需要指明泛型。
        sub1.setOrderT(1122);//只能放父类指明的泛型类型--Integer

        //子类继承带泛型的父类，但父类没有指明泛型类型时：需要子类自己创建对象时指明泛型类型。
        SubOrder1<String> sub2 = new SubOrder1<>();
        sub2.setOrderT("order2...");
    }

    @Test
    public void test3(){
        ArrayList<String> list1 = null;
        ArrayList<Integer> list2 = null;
        //泛型不同的引用不能相互赋值。
//        list1 = list2;
    }

    //测试泛型方法
    @Test
    public void test4(){
        Order<String> order = new Order<>();
        Integer[] arr = new Integer[]{1,2,3};
        //泛型方法在调用时，指明泛型参数的类型。----这里实参的类型就是形参中泛型的类型----如果没有泛型形参，那么就根据返回值的类型来定义泛型类型
        List<Integer> list = order.copyFromArrayToList(arr);
    }
}

//自定义泛型类
class Order<T>{
    String orderName;
    int orderId;

    //类的内部结构就可以使用类的泛型

    T orderT;

    public Order(){
        //编译不通过
//        T[] arr = new T[10];
        //编译通过
        T[] arr = (T[]) new Object[10];
    }

    public Order(String orderName , int orderId , T orderT){
        this.orderName = orderName;
        this.orderId = orderId;
        this.orderT = orderT;
    }

    //如下的三个方法都不是泛型方法
    public T getOrderT(){
        return orderT;
    }
    public void setOrderT(T orderT){
        this.orderT = orderT;
    }

    @Override
    public String toString() {
        return "genericTest02{" +
                "orderName='" + orderName + '\'' +
                ", orderId=" + orderId +
                ", orderT=" + orderT +
                '}';
    }

    //泛型方法：在方法中出现了泛型的结构，泛型参数与类的泛型参数没有任何关系。
    //换句话说，泛型方法所属的类是不是泛型类都没有关系
    //泛型方法，可以声明为静态的。原因：泛型参数是在调用方法时确定的。并非在实例化类时确定。---该对象与本类无关，故可以静态时调用
    public static <E> List<E> copyFromArrayToList(E[] arr){//泛型的类型由实参赋给形参时的实参所决定的或者没有泛型形参时根据返回值所决定

        ArrayList<E> list = new ArrayList<>();

        for(E e : arr){
            list.add(e);
        }
        return list;

    }


    //在静态方法中不能使用类的泛型。----泛型是在创建类的对象时指明类型后使用，而静态方法是在对象之前，所以无法在静态中用泛型
//    public static void show(T orderT){
//        System.out.println(orderT);
//    }

    public void show(){
        //编译不通过
//        try{
//
//        }catch(T t){
//
//        }
    }
}

class SubOrder extends Order<Integer>{//此时SubOrder是普通类，不是泛型类
    //用于测试继承带泛型并且指明了泛型类型的类的子类


    public static <E> List<E> copyFromArrayToList(E[] arr){

        ArrayList<E> list = new ArrayList<>();

        for(E e : arr){
            list.add(e);
        }
        return list;

    }
}
class SubOrder1<T> extends Order<T>{//SubOrder1<T>仍然是泛型类
    //用于测试继承带泛型但没有指明泛型类型的类的子类
}

//异常类不能是泛型的
//class MyException<T> extends Exception{
//
//}
