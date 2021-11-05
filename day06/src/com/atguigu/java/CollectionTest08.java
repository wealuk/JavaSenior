package com.atguigu.java;

import org.junit.Test;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * TreeSet的使用
 *
 * 总结：List需要重写equals()、Set中的HashSet和LinkedHashSet需要重写equals和hashCode、Set中的
 * TreeSet不需要重写equals和hashCode,但需要重写compareTo或compare，判断标准不再是equals而是compare
 *
 * TreeSet底层使用红黑树
 *
 *
 * 复习点：Stack(栈)作为Vector的子类，用数组存储，同时存入的数据先进来的后删除
 * @author shkstart
 * @create 2021-04-25 23:49
 */
public class CollectionTest08 {
    /*
    1.向TreeSet中添加的数据，要求是相同类的对象
    2.两种排序方式：自然排序(实现Comparable接口)和定制排序。-----没有实现任何一个，那么无法添加元素
    notes:必须要实现两个中的一个，且必须在里面指明排序的规律。

    3.自然排序中，比较两个对象是否相同的标准为：compareTo()返回0.不再是equals()，判断标准更加严格了
    4.定制排序中，比较两个对象是否相同的标准为：compare()返回0.不再是equals()

    注意下两个notes

     */
    @Test
    public void test1(){
        TreeSet set = new TreeSet();

        //失败：不能添加不同类的对象
//        set.add(123);
//        set.add(456);
//        set.add("AA");
//        set.add(new User("Tom" , 12));

        //举例一：
        set.add(34);
        set.add(-34);
        set.add(43);
        set.add(11);
        set.add(8);//从小到大排列

        //举例二：
        set.add(new User("Tom" , 12));
        set.add(new User("Jerry" , 32));
        set.add(new User("Jim" , 2));
        set.add(new User("Mike" , 65));
        set.add(new User("Jack" , 33));
        set.add(new User("Jack" , 56));
        //notes：自然排序中，比较两个对象是否相同的标准为：compareTo()返回0.不再是equals()。正因为这样，如果重写的
        // compareTo中只比较了名字，而没有比较年龄，那么第五个和第六个返回0会被视为同一个对象，则第六个无法添加成功。
        //当然，可以像07中的那样，把所有的属性都有一个比较，这样就不会有内容不同而加不进来。当然具体问题具体分析

        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }

    }

    @Test
    public void test2(){
        Comparator com = new Comparator() {
            //按年龄从小到大排列
            @Override
            public int compare(Object o1, Object o2) {
                if(o1 instanceof User && o2 instanceof User){
                    User u1 = (User)o1;
                    User u2 = (User)o2;
                    return  Integer.compare(u1.getAge() , u2.getAge());
                }else{
                    throw new RuntimeException(("输入的数据类型不匹配！"));
                }
            }
        };


        TreeSet set = new TreeSet(com);//构造器没有放com就是自然排序，放了com就是定制排序
        set.add(new User("Tom" , 12));
        set.add(new User("Jerry" , 32));
        set.add(new User("Jim" , 2));
        set.add(new User("Mike" , 65));
        set.add(new User("Jack" , 33));
        set.add(new User("Marry" , 33));
        set.add(new User("Jack" , 56));
        //notes：同样只比较了年龄，那么年龄相同的只会添加先来的那个，后面的哪怕名字不同也没用，因为判断的依据是是否返回0

        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

}
