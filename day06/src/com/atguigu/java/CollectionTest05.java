package com.atguigu.java;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 1.List接口框架
 * |---Collection接口：单列集合，用来存储一个一个的对象
 *           |---List接口：存储有序的、可重复的数据。 --->"动态"数组,替换原来的数组
 *               |--ArrayList：作为List接口的主要实现类；线程不安全，效率高；底层使用Object[] elementData存储
 *               |--LinkedList：对于频繁的插入、删除操作，使用此类效率要比ArrayList高；底层使用双向链表存储
 *               |--Vector：作为List接口的古老实现类；线程安全，效率低；底层使用Object[] elementData存储
 *
 *           notes:ArrayList底层是Object数组，故插入到中间或删除都需要把其往后移在删除。LinkedList则只要改变指针
 * 存储的元素的的要求：所在的类要重写equals方法
 *
 * 2.ArrayList的源码分析：
 *      2.1jdk 7情况下
 *              ArrayList list = new ArrayList();//底层创建了长度是10的Object[]数组elementData
 *              list.add(123);//elementData[0] = new Integer(123);
 *              ...
 *              list.add(11);//如果此次的添加导致底层elementData数组容量不够，则扩容。
 *              默认情况下，扩容为原来的容量的1.5倍，同时需要将原有的数组中的数据复制到新的数组中
 *
 *              结论：建议开发中使用带参的构造器：ArrayList list = new ArrayList(int capacity)//指定容量，尽量不扩容
 *
 *      2.2jdk  8中ArrayList的变化
 *          ArrayList list = new ArrayList();//底层Object[] elementData初始化为{},并没有创建长度为10的数组
 *          list.add(123);//第一次调用add()时，底层才创建长度为10的数组，并将数据123添加到elementData[0]
 *          ...
 *          后续的添加和扩容操作与jdk 7无异。
 *      2.3 小结；jdk 7中的ArrayList的对象的创建类似于单例的饿汉式，而jdk8中的ArrayList的对象
 *          的创建类似于单例的懒汉式，延迟了数组的创建，节省内存。
 *
 *  3.LinkedList的源码分析：
 *         LinkedList list = new LinkedList();内部声明了Node类型的first和last属性，默认值为null
 *         list.add(123);//将123封装到Node中，创建了Node对象
 *
 *         其中，Node定义为：(体现了LinkedList双向链表的说法)
 *         private static class Node<E> {
         *         E item;
         *         Node<E> next;
         *         Node<E> prev;
         *
         *         Node(Node<E> prev, E element, Node<E> next) {
         *             this.item = element;
         *             this.next = next;
         *             this.prev = prev;
 *         }
 *     }
 *
 *  4.Vector的源码分析：jdk7和jdk8中通过Vector()构造器创建对象时，底层都创建了长度为10的数组
 *      在扩容方面，默认扩容为原来的数组的长度的2倍
 *
 *
 * 面试题：ArrayList、LinkedList、Vector三者的异同？
 * 同：三个类都是实现类List接口，存储数据的特点相同：存储有序的、可重复的数据
 * 不同：见上(第一部分和第二三四的源码分析)
 *
 * 5.List的方法：见下面
 * 总结：常用方法
 *          增：add(Object obj)
 *          删：remove(int index) / remove(Obejct obj)
 *          改：set(int index , Object ele)
 *          查：get(int index)
 *          插：add(int index, Object ele)
 *          长度：size()
 *          遍历：①Iterator迭代器方式
 *               ②foreach循环
 *               ③普通的循环
 *
 * @author shkstart
 * @create 2021-04-23 12:37
 */
public class CollectionTest05 {
    /*
    List中的方法：
void add(int index, Object ele):在index位置插入ele元素
boolean addAll(int index, Collection eles):从index位置开始将eles中的所有元素添加进来
Object get(int index):获取指定index位置的元素
int indexOf(Object obj):返回obj在集合中首次出现的位置
int lastIndexOf(Object obj):返回obj在当前集合中末次出现的位置
Object remove(int index):移除指定index位置的元素，并返回此元素---与remove(Object o)构成重载
Object set(int index, Object ele):设置指定index位置的元素为ele
List subList(int fromIndex, int toIndex):返回从fromIndex到toIndex位置的子集合

总结：常用方法----必须掌握好
           增：add(Object obj)
           删：remove(int index) / remove(Obejct obj)
           改：set(int index , Object ele)
           查：get(int index)
           插：add(int index, Object ele)
           长度：size()
           遍历：①Iterator迭代器方式
                ②foreach循环
                ③普通的循环
     */

    @Test
    public void test1(){
        ArrayList list = new ArrayList();
        list.add(123);
        list.add(456);
        list.add("AA");
        list.add(new Person("Tom" , 12));
        list.add(456);

        System.out.println(list);

        //void add(int index, Object ele):在index位置插入ele元素
        list.add(1 , "BB");
        System.out.println(list);

        //boolean addAll(int index, Collection eles):从index位置开始将eles中的所有元素添加进来
        List list1 = Arrays.asList(1, 2, 3);
        list.addAll(list1);
        //如果是list.add(list1),那么size就是7，把集合当成一个整体添加了，而不是把元素添加
        System.out.println(list.size());//6+3=9

        //Object get(int index):获取指定index位置的元素
        System.out.println(list.get(0));

        //int indexOf(Object obj):返回obj在集合中首次出现的位置。如果不存在，返回-1.
        int index = list.indexOf(4567);
        System.out.println(index);

        //lastIndexOf(Object obj):返回obj在当前集合中末次出现的位置。如果不存在，返回-1.
        int i = list.lastIndexOf(456);
        System.out.println(i);

        //Object remove(int index):移除指定index位置的元素，并返回此元素--与remove(Object o)构成重载
        Object obj = list.remove(0);
        System.out.println(obj);
        System.out.println(list);

        //Object set(int index, Object ele):设置指定index位置的元素为ele
        list.set(1,"CC");
        System.out.println(list);

        //List subList(int fromIndex, int toIndex):返回从fromIndex到toIndex位置的左闭右开的子集合
        List list2 = list.subList(2, 4);
        System.out.println(list2);
        System.out.println(list);//并没有改变，所以subList不对原有的集合构成改变，只是返回值是想要的
//        notes：subString、subList都是左闭右开的，并且都不对原来的数据构成改变。
    }

    @Test
    public void test2(){
        ArrayList list = new ArrayList();
        list.add(123);
        list.add(456);
        list.add("AA");

        //方式一：Iterator迭代器方式
        Iterator iterator = list.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
        System.out.println("*******");

        //方式二：增强for循环
        for(Object obj : list){
            System.out.println(obj);
        }

        //方式三：普通for循环
        for(int i = 0;i < list.size();i++){
            System.out.println(list.get(i));
        }
    }

}
