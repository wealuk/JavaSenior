package com.atguigu.java;

import org.junit.Test;

import java.util.*;

/**
 * Set接口的框架：
 *
 * |---Collection接口：单列集合，用来存储一个一个的对象(有许多子接口，然后才是子接口的实现类)
 *            |---Set接口：存储无序的、不可重复的数据。 --->高中将的“集合”。
 *                |---HashSet：作为Set接口的主要实现类；线程不安全的；可以存储null值----底层数组长度为16
 *                    |---LinkedHashSet：作为HashSet的子类：遍历其内部数据时，可以按照添加的顺序遍历
 *                                      在添加数据的同时，每个数据还维护了两个引用，记录此数据前一个数据和后一个数据(按顺序的原因)
 *                                      对于频繁的遍历操作，LinkedHashSet效率高于HashSet(记录了谁先来谁后来，故遍历时可以按添加顺序遍历)
 *                |---TreeSet：可以按照添加对象的指定属性，进行排序。----必须使用自然排序和定制排序中的一个，且指明如何排序---底层使用红黑树
 *  存储的元素的要求：对于HashSet、LinkedHashSet，需要重写equals和hashCode；TreeSet需要重写compareTo或compare
 *
 *
 *  1.Set接口中没有额外定义新的方法，使用的都是Collection中声明过的方法

 *  2.要求：向Set(HashSet、LinkedHashSet)中添加的数据，其所在的类一定要重写hashCode()和equals()
 *    要求：重写的hashCode()和equals()尽可能保持一致性：相等的对象必须具有相等的数列码
 *          重写两个方法的小技巧:对象中用作equals()方法比较的Field，都应该用来计算hashCode
 *
 *      notes1：哈希值不同可能得到相同的索引位置，又HashSet底层：数组+链表的结构。数组每一个位置，可能由一个链表也就是
 *          有多个元素(对象)存在。我们需要通过hashCode来根据对象的属性得到相应的哈希值，确保相同的对象得到的哈希值相同，
 *          从而可以分到同一个数组位置。然后又需要equals方法来区分出哈希值不同但索引相同的元素以及同一个类的对象，属性
 *          不同但总的哈希值相同而内容不同的元素。故一定要重写hashCode()和equals()，且重写的hashCode()和equals()
 *          尽可能保持一致性,也就是两种运用到的属性要相同。
 *
 * HashSet、LinkedHashSet见下面。TreeSet见08
 *
 * 本节内容较多，且内容散乱，故下面总结一波
 * notes2：1.Set主要理解无序、不可重复两个点：无序是指按每个元素根据所调用的HashCode()方法得到的哈希值来判断在数组
 * 的哪个位置，而不是根据数组的索引依次排序或是随机排序。而LinkedHashSet作为HashSet的子类，同样遵守这个原则，只不过
 * 之所以能按找添加到集合中的顺序来遍历，就是因为在添加数据的同时，每个数据还维护了两个引用，记录此数据前一个数据和
 * 后一个数据。故我们可以知道添加的顺序了，但是总而言之，添加元素的过程是无序的。
 *      2.不可重复广泛的说是不能有两个相同内容的对象。不可重复具体来说，就需要分三步判断两个内容是否相等，同时其判断核心
 *      仍然是equals方法。首先根据hashCode方法获取对象的哈希值，然后通过某种算法计算出在HashSet底层数组中的
 *      存放位置(即为：索引位置)，具体见line56。其要求在第三点
 *      3.要实现不可重复的判断，必须重写equals和hashCode方法，且两者尽可能保持一致性,也就是两种运用到的属性要相同。原因见notes1
 *      4.理解Set添加元素的过程自然就懂了，纠纷点见line81
 *
 *
 * @author shkstart
 * @create 2021-04-25 12:37
 */
public class CollectionTest07 {
    /*
    一、Set：存储无序的、不可重复的数据
     以HashSet为例说明
      1.无序性：不等于随机性。存储的数据在底层数组中并非按照数组索引的顺序添加。而是根据数据的哈希值决定的。

      2.不可重复性：保证添加的元素按照equals()判断时，不能返回true。即：相同的元素只能添加一个。

    二、添加元素的过程，以HashSet为例：
            我们向HashSet中添加元素a，首先调用元素a所在类的hashCode()方法,计算元素a的哈希值，
            此哈希值接着通过某种算法计算出在HashSet底层数组中的存放位置(即为：索引位置)，判断
            数组此位置上是否已经有元素：
                    如果此位置上没有其他元素，则元素a添加成功。--->情况1
                    如果此位置上有其他元素b(或以链表形式存在的多个元素)，则比较元素a与元素b的hash值：
                            如果hash值不相同，则元素a添加成功。--->情况2
                            如果hash值相同，进而需要调用元素a所在类的equals()方法：
                                    equals()返回true，元素a添加失败
                                    equals()返回false，则元素a添加成功。--->情况3

                   对于添加成功的情况2和情况3而言：元素a与已经存在指定索引位置上数据  以链表的方式存储。
                   jdk 7：元素a放到数组中，指向原来的元素。
                   jdk 8：原来的元素在数组中，指向元素a。
                   总结：七上八下

                   HashSet底层：数组+链表的结构（前提：jdk 7）
     */
    @Test
    public void test1(){
        Set set = new HashSet();
        set.add(456);
        set.add(123);
        set.add("AA");
        set.add("CC");
        set.add(new User("Tom" , 123));
        set.add(new User("Tom" , 123));//没有重写hashCode方法时，调用的父类Object类的，哈希值是随机的
                //，故无论有没有重写equals方法，哈希值不同分到的索引位置大概率不同。(如果偶然相同，重写了equals方法的就
                //不会添加，没有重写比较的就是地址值就会直接添加)，基本直接添加用不到equals方法了(不同的哈希值得到相同索引概率低)。
                //而如果重写了hashCode，没有重写equals，那么根据属性得到的哈希值相同，那么就可以分到数组的同一个索引。
                //这是如果没有重写equals就会比较地址值，两个User就不一样，不是比较的内容。
                //总结：set中添加的元素，必须满足重写了equals和hashCode.并且equals和hashCode满足相等的对象必须
                //具有相等的数列码，也就是equals比较的属性，必须用到hashCode上面。通过相同的属性可以得到相同的哈希值，
                //从而分到相同的数组位置，然后通过equals方法比较该索引处别的元素，直到找到相同的就不添加，否则添加。
        set.add(129);

        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    //LinkedHashSet的使用
    //LinkedHashSet作为HashSet的子类，在添加数据的同时，每个数据还维护了两个引用，记录
    //此数据前一个数据和后一个数据。
    //优点：对于频繁的遍历操作，LinkedHashSet效率高于HashSet(记录了谁先来谁后来，故遍历时可以按添加顺序遍历)
    @Test
    public void test2(){
        Set set = new LinkedHashSet();
        set.add(456);
        set.add(123);
        set.add("AA");
        set.add("CC");
        set.add(new User("Tom" , 123));
        set.add(new User("Tom" , 123));
        set.add(129);

        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());//按添加的顺序遍历的
        }
    }
}


class User implements Comparable{
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("User equals...");
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age &&
                Objects.equals(name, user.name);
    }

    //hashCode()没有重写的话，是调用的Object类中的hashCode()。
    // 父类Object的hashCode是取得随机值，而重写的hashCode得到的哈希值与属性有关
    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    //按照姓名从小到大排列，年龄从小到大(用于08TreeSet比较对象属性的测试，如果没有重写compareTo那么就无法添加，因为TreeSet要用来排序的)
    @Override
    public int compareTo(Object o) {
        if(o instanceof User){
            User user = (User)o;
//            return this.name.compareTo(user.name);
            int compare = this.name.compareTo(user.name);
            if(compare != 0){
                return compare;
            }else{
                return Integer.compare(this.age,user.age);
            }
        }else{
            throw new RuntimeException("输入的类型不匹配！");
        }
    }
}
