package com.atguigu.java;

import org.junit.Test;

import java.util.*;

/**
 * Collection接口声明的方法的测试
 *
 * 结论：
 * 向Collection接口的实现类的对象添加数据obj时，要求obj所在类要重写equals().
 *
 * @author shkstart
 * @create 2021-04-22 7:21
 */
public class CollectionTest02 {
    @Test
    public void test1(){
        Collection coll = new ArrayList();
        coll.add(123);
        coll.add(456);
        coll.add(new String("Tom"));
        coll.add(false);//Double,包装类
        Person p = new Person("Jerry" , 18);
        coll.add(p);
        coll.add(new Person("Jerry" , 20));
        //1.contains(Object obj):判断当前集合中是否包含obj
        //我们在判断时会调用obj对象所在类的equals()。----notes：由于List是有序的，故obj调用equals方法会先与第一个元素(123)比，
                                                     //然后依次往后比，直到equals匹配到相同的或找完整个集合。
        boolean contains = coll.contains(123);
        System.out.println(contains);//true
        System.out.println(coll.contains(new String("Tom")));//true,可见对于String比较的是内容而不是地址值，故是调用了重写的equals方法
        System.out.println(coll.contains(p));//true
        System.out.println(coll.contains(new Person("Jerry" , 20)));//false(没有重写equals方法，故比较的还是对象的地址值)-->true(重写equals后比较的就是内容)
        //notes：①.由于在Person中重写的equals方法加了一行输出语句，我们可以看出，coll中有6个元素，而new Person("Jerry" , 20)
        //      在第6个元素，所有要equals6次，故输出语句就会有6个。(如果添加时往前放，equals就少，输出语句少)
        //       ②.如果Person p = new Person("Jerry" , 18);中age改为20，coll.contains(new Person("Jerry" , 20))只会
        //          打印5次输出语句，也就是说与p相同----重写equals后比较的内容

        //2.containsAll(Collection coll1):判断形参coll1中的所有元素是否都存在于当前集合中
        Collection coll1 = Arrays.asList(123,456);//返回一个ArrayList
        System.out.println(coll.containsAll(coll1));//true
    }

    @Test
    public void test2(){
        //3.remove(Object obj):从当前集合中移除obj元素-----有返回值boolean判断是否删除成功
        Collection coll = new ArrayList();
        coll.add(123);
        coll.add(456);
        coll.add(new Person("Jerry" , 20));
        coll.add(new String("Tom"));
        coll.add(false);

        //notes：remove()中obj也要用到equals来匹对集合中相同内容的元素，从而进行删除操作。所以remove()方法必须
        //保证obj的类要重写equals方法
        coll.remove(1234);
        System.out.println(coll);

        coll.remove(new Person("Jerry" , 20));
        System.out.println(coll);

        //4.removeAll(Collection coll1):差集：从当前集合中移除coll1中所有的元素。
        Collection coll1 = Arrays.asList(123,456);
        coll.removeAll(coll1);
        System.out.println(coll);
    }

    @Test
    public void test3(){
        Collection coll = new ArrayList();
        coll.add(123);
        coll.add(456);
        coll.add(new Person("Jerry" , 20));
        coll.add(new String("Tom"));
        coll.add(false);

        //5.retainAll(Collection coll1):交集：获取当前集合和coll1集合的交集，并返回给当前集合(直接在原集合上修改的)
//        Collection coll1 = Arrays.asList(123, 456, 789);
//        coll.retainAll(coll1);//将coll改为coll和coll1的交集的集合
//        System.out.println(coll);//[123, 456]。是在原集合上修改的

        //6.equals(Object obj):要返回集合需要当前集合和形参集合的元素都相同(具体还要分有序和无序)
        Collection coll2 = new ArrayList();
        coll2.add(123);
        coll2.add(456);
        coll2.add(new Person("Jerry" , 20));
        coll2.add(new String("Tom"));
        coll2.add(false);

        System.out.println(coll.equals(coll2));//true
        //注：由于是List的实现类，故是有序的。也就是每一个元素要一一对应相等，才返回true。哪怕元素456只提到前一个元素位置，那么返回的也是false
        //    无序的话，就不需要考虑元素的先后顺序了。
    }

    @Test
    public void test5(){
        Collection coll = new ArrayList();
        coll.add(123);
        coll.add(456);
        coll.add(new Person("Jerry" , 20));
        coll.add(new String("Tom"));
        coll.add(false);

        //7.hashCode():返回当前对象的哈希值
        System.out.println(coll.hashCode());

        //8.集合-->数组：toArray()
        Object[] arr = coll.toArray();
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }

        //扩展：数组-->集合：调用Arrays类的静态方法asList()
        List<String> list = Arrays.asList(new String[]{"AA", "BB", "CC"});
        System.out.println(list);//[AA, BB, CC]

        List list1 = Arrays.asList(new int[]{123, 456});
        System.out.println(list1.size());//1,将数组当成一个元素了---------注意点，这种会被当成一个元素。如果是(){123,456}则是两个元素
        System.out.println(list1);//[[I@5e8c92f4]

        List list2 = Arrays.asList(new Integer[]{123, 456});
        System.out.println(list2.size());//2，将数组中的123，456作为元素

        //9.iterator():返回Iterator接口的实例，用于遍历集合元素。放在Test03中测试
    }
}

class Person{
    private String name;
    private int age;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

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

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("Person equals....");
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(name, person.name);
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(name, age);
//    }
}
