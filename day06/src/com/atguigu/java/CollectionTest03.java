package com.atguigu.java;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * 集合元素的遍历操作：使用迭代器Iterator接口(用来遍历Collection，不包括Map)
 *  1.    内部的方法：hasNext() 和 next()
 *   hasNext():判断是否还有下一个元素
 *   next():①指针下移 ②将下移以后集合位置上的元素返回
 *   2.集合对象每次调用iterator()方法都得到一个全新的迭代器对象，默认游标都在集合 的第一个元素之前。
 *   3.内部定义了remove()，可以在遍历的时候，删除集合中的元素。此方法不同于集合直接调用remove()
 *
 * 注意点：
 * ①Iterator对象称为迭代器(设计模式的一种)，主要用于遍历 Collection 集合中的元素
 * ②Collection接口继承了java.lang.Iterable接口，该接口有一个iterator()方法，
 * 那么所有实现了Collection接口的集合类都有一个iterator()方法，用以返回一个实现了 Iterator接口的对象
 * ③Iterator 仅用于遍历集合，Iterator 本身并不提供承装对象的能力。如果需要创建Iterator 对象，则必须有一个被迭代的集合。
 * ④集合对象每次调用iterator()方法都得到一个全新的迭代器对象，默认游标都在集合 的第一个元素之前。
 * ⑤在调用it.next()方法之前必须要调用it.hasNext()进行检测。若不调用，且下一条记录无效，直接调用it.next()会抛出NoSuchElementException异常。
 * ⑥如果还未调用next()或在上一次调用 next  方法之后已经调用了 remove  方法，再调用remove都会报IllegalStateException。
 *
 *
 *notes：Iterator对象不是容器，只是有指针指向集合。然后通过hasNext、next一同遍历集合中的元素.开始，指针指向第一个元素之前，调用next就会
 * 向下移一个。所以一个迭代器Iterator对象遍历完一次以后，就不能再用来遍历一遍集合，因为它的指针已经到了集合末尾。如果要想再遍历一边集合，需要重新
 * 创建一个迭代器Iterator对象。
 *
 *
 * @author shkstart
 * @create 2021-04-23 9:25
 */
public class CollectionTest03 {
    @Test
    public void test1(){
        Collection coll = new ArrayList();
        coll.add(123);
        coll.add(456);
        coll.add(new Person("Jerry" , 20));
        coll.add(new String("Tom"));
        coll.add(false);

        Iterator iterator = coll.iterator();
        //方式一：
//        System.out.println(iterator.next());
//        System.out.println(iterator.next());
//        System.out.println(iterator.next());
//        System.out.println(iterator.next());
//        System.out.println(iterator.next());
//        //报异常：NoSuchElementException
//        System.out.println(iterator.next());

        //方式二：不推荐
//        for (int i = 0; i < coll.size(); i++) {
//            System.out.println(iterator.next());
//        }

        //方式三：推荐
        while(iterator.hasNext()){//hasNext():判断是否还有下一个元素
            System.out.println(iterator.next());//next():①指针下移 ②将下移以后集合位置上的元素返回
        }
    }

    @Test
    public void test2(){
        Collection coll = new ArrayList();
        coll.add(123);
        coll.add(456);
        coll.add(new Person("Jerry" , 20));
        coll.add(new String("Tom"));
        coll.add(false);

        //错误的方式一：会报NoSuchElementException，并且跳着输出元素
        Iterator iterator = coll.iterator();
        while(iterator.next() != null){
            System.out.println(iterator.next());
        }

        //错误的方式二：
        //集合对象每次调用iterator()方法都得到一个全新的迭代器对象，默认游标都在集合 的第一个元素之前。
        while(coll.iterator().hasNext()){
            System.out.println(coll.iterator().next());
        }//会一直输出第一个元素123
    }

    //测试Iterator中的remove()
    //如果还未调用next()或在上一次调用 next方法之后已经调用了 remove  方法，
    //再调用remove都会报IllegalStateException。
    @Test
    public void test3(){
        Collection coll = new ArrayList();
        coll.add(123);
        coll.add(456);
        coll.add(new Person("Jerry" , 20));
        coll.add(new String("Tom"));
        coll.add(false);

        //删除集合中的“Tom"
        Iterator iterator = coll.iterator();
        while (iterator.hasNext()){
//            iterator.remove();IllegalStateException没有调用next前，指针在第一个元素之前
            Object obj = iterator.next();
            if("Tom".equals(obj)){
                iterator.remove();
//                iterator.remove();//IllegalStateException删除了不能继续删
            }
        }

        //再次遍历集合：不能再使用原来的迭代器(iterator)了，因为其指针已经到达集合末尾。需要重新创建一个迭代器Iterator对象
        Iterator iterator1 = coll.iterator();
        while(iterator1.hasNext()){
            System.out.println(iterator1.next());
        }
    }

}
