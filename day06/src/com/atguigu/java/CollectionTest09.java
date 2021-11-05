package com.atguigu.java;

import org.junit.Test;

import java.util.HashSet;
import java.util.Objects;

/**
 * 两道经典面试题：
 *
 * @author shkstart
 * @create 2021-04-28 12:35
 */
public class CollectionTest09 {

    @Test
    public void test1(){
        HashSet set = new HashSet();
        Person1 p1 = new Person1(1001,"AA");
        Person1 p2 = new Person1(1002,"BB");

        set.add(p1);
        set.add(p2);

        p1.age = "CC";
        set.remove(p1);
        System.out.println(set);//[Person1{name=1002, age='BB'}, Person1{name=1001, age='CC'}]
        //p1并没有删除。原因：remove方法根据p1的哈希值去寻找数组的索引位置，而p1(已经在数组中了)是根据属性“AA”找到的
        //索引，后面将p1的“AA”改为“CC”，其p1的哈希值改变了但是在数组的索引没变。remove根据新的哈希值匹到的索引自然与
        //原本的位置不同，自然找不到p1更不可能删除了。

        set.add(new Person1(1001,"CC"));
        System.out.println(set);//[Person1{name=1002, age='BB'}, Person1{name=1001, age='CC'}, Person1{name=1001, age='CC'}]
        //发现已经有了“CC”，但仍然添加成功了。原因跟上面一样，新的哈希值找到的索引不同于原来按“AA”找到的索引，两个相同内容的
        // 对象不在一个数组位置，故新的可以添加。

        set.add(new Person1(1001,"AA"));
        System.out.println(set);//[Person1{name=1002, age='BB'}, Person1{name=1001, age='CC'}, Person1{name=1001, age='CC'}, Person1{name=1001, age='AA
        //也添加成功了。与原来的p1哈希值相同，故其数组位置与p1相同，但p1已经修改成“CC”，所以equals不为true，也能添加成功。

        //notes：核心就是Set集合中修改元素的属性，改变了哈希值，但改变其在数组的位置。像remove、add这些方法用的新的
        //哈希值去匹对，其索引就与原来的不同，对原来的对象不构成影响。

    }
}

class Person1{
    int name;
    String age;

    public Person1() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person1 person1 = (Person1) o;
        return name == person1.name &&
                Objects.equals(age, person1.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "Person1{" +
                "name=" + name +
                ", age='" + age + '\'' +
                '}';
    }

    public Person1(int name, String age) {
        this.name = name;
        this.age = age;
    }
}
