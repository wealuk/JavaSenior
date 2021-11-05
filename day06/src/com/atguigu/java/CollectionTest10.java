package com.atguigu.java;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 题二：在List内去除重复数字值，要求尽量简单
 * @author shkstart
 * @create 2021-04-28 12:58
 */
public class CollectionTest10 {
    public static List duplicateList(List list) {

//        notes：就是将list的元素addAll到set中，然后将set赋给list的构造器就行了

        HashSet set = new HashSet();
        set.addAll(list);
        return new ArrayList(set);
    }

        public static void main (String[]args){
            List list = new ArrayList();
            list.add(new Integer(1));
            list.add(new Integer(2));
            list.add(new Integer(2));
            list.add(new Integer(4));
            list.add(new Integer(4));
            List list2 = duplicateList(list);
            for (Object integer : list2) {
                System.out.println(integer);
            }
        }
    }

