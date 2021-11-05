package com.atguigu.java;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 面试题
 * @author shkstart
 * @create 2021-04-25 11:45
 */
public class CollectionTest06 {
    /*
    区分List中remove(int index)和remove(Object obj)
     */
    @Test
    public void testListRemove() {
        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        updateList(list);
        System.out.println(list);//
    }

    private static void updateList(List list) {
//        list.remove(2);//[1,2]。2代表的int index索引，指第三个元素，而不是内容2
//                          这里的2可能是索引，也可能是内容。由于2是内容的话，需要装箱，需要一系列操作，故这里直接表示的索引。
        list.remove(new Integer(2));//[1,3]//要表示内容的话，需要显式声明为Integer
    }
}
