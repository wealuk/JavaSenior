package com.atguigu.java;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 一、说明：Java中的对象，正常情况下，只能进行比较：== 或 != 。不能使用 > 或 < 的
 *          但是在开发场景中，我们需要对多个对象进行排序，言外之意，就需要比较对象的大小。
 *          如何实现？使用两个接口中的任何一个：Comparable或Comparator
 *
 *     Comparable接口和Comparator接口的使用的对比：
 *      Comparable接口的方式一旦确定，保证 Comparable接口实现类的对象在任何位置都可以比较大小
 *      Comparator接口 属于临时性的比较
 *
 *      notes：1.Comparable接口的形式需要实现类，实现类只要调用sort()，就可以比较大小从而排序。但是
 *      比较大小的方式一旦确定就不能修改了；而Comparator接口可以直接使用在sort()中，无需创建一个实现类
 *      作为一个形参，可以直接使用new Comparator(){};的形式创建一个一次性的比较大小的方式，比较大小的方式多变但只能用一次。
 *     2.相比于 Comparable接口，Comparator接口可以临时修改比较大小的方式且不需要创建一个非匿名实现类，但是只能使用一次。
 *        而Comparable接口有了一个实现类后，就可以一直比较大小，虽然比较大小的方式不能改变了(是从小到大，
 *        就不能变成从大到小。要变成从大到小(不修改compareTo的情况下)就可以临时在sort()中加一个Comparator匿名实现类)
 *     3.当然Comparator接口也可以声明非匿名实现类，与匿名效果一样的。
 *
 * 二、Comparable接口的使用：
 *      具体内容见下面
 *
 *  notes：如果对于自定义类在重写的compareTo()中，要比较属性。其中基本数据类型可以用包装类的compare,String类型
 *         则可以直接用重写后的compareTo()方法
 *
 * 三、Comparator接口的使用：定制排序
 *      具体内容见下面line79
 *
 *
 * System类代表系统，系统级的很多属性和控制方法都放置在该类的内部。 该类位于java.lang包。
 * 由于该类的构造器是private的，所以无法创建该类的对象，也就是无法实 例化该类。其内部的
 * 成员变量和成员方法都是static的，所以也可以很方便 的进行调用。
 *1.native long currentTimeMillis()：
 * 2.void exit(int status)：
 * 3.void gc()：
 *
 * BigInteger可以表示不可变的任意精度的整数
 * 但在商业计算中， 要求数字精度比较高，故用到java.math.BigDecimal类。
 *
 *
 * @author shkstart
 * @create 2021-04-19 13:04
 */
public class StringTest06 {
    /*
    Comparable接口的使用举例：自然排序
    1.像String、包装类等实现了Comparable接口，重写了compareTo(obj)方法，给出了比较两个大小的方式
    2.像String、包装类重写compareTo()方法以后，进行了从小到大的排序
    3.重写compareTo(obj)的规则：
        如果当前对象this大于形参对象obj，则返回正整数，
        如果当前对象this小于形参对象obj，则返回负整数，
        如果当前对象this等于形参对象obj，则返回零。
    4.对于自定义类来说，如果需要排序，我们可以让自定义类实现Comparable接口，重写compareTo(obj)方法，
      在compareTo(obj)方法中指明如何排序

     */
    @Test
    public void test1(){
        String[] arr = new String[]{"AA" , "BB" , "JJ" , "GG" , "DD"};

        Arrays.sort(arr);//sort()会执行compareTo()方法

        System.out.println(Arrays.toString(arr));//[AA, BB, DD, GG, JJ]。发现已经从小到大排序了，说明String对象
    }                                            //是可以比较的

    @Test
    public void test2(){
        Goods[] arr = new Goods[5];
        arr[0] = new Goods("lenovo" , 34);
        arr[1] = new Goods("dell" , 42);
        arr[2] = new Goods("xiaomi" , 12);
        arr[3] = new Goods("huawei" , 64);
        arr[4] = new Goods("microsoft" , 42);

        Arrays.sort(arr);

//如果没有实现Comparable接口：java.lang.ClassCastException: com.atguigu.java.Goods cannot be cast
// to java.lang.Comparable。无法转化为Comparable进行比较。故需要实现接口，并且重写compareTo()方法
        System.out.println(Arrays.toString(arr));
//[Goods{name='lenovo', price=34.0}, Goods{name='dell', price=42.0}, Goods{name='xiaomi', price=12.0}, Goods{name='huawei', price=64.0}]
        //重写compareTo、toString后，结果已经按价格从小到大排序对象了

    }


    /*
    Comparator接口的使用：定制排序
    1.背景：当元素的类型没有实现java.lang.Comparable接口而又不方便修改代码， 或者
    实现了java.lang.Comparable接口的排序规则不适合当前的操作，那 么可以考虑
    使用 Comparator 的对象来排序，
    2.重写compare(Object o1,Object o2)方法，比较o1和o2的大小：
    如果方法返 回正整数，则表示o1大于o2；
    如果返回0，表示相等；
    返回负整数，表示 o1小于o2。

     */
    @Test
    public void test3(){
        String[] arr = new String[]{"AA" , "BB" , "JJ" , "GG" , "DD"};
        Arrays.sort(arr , new Comparator(){//使用Comparator的匿名实现类，一次性让sort()变成从大到小排序

            //按照字符串从大到小的顺序排列
            @Override
            public int compare(Object o1, Object o2) {
                if(o1 instanceof String && o2 instanceof String){
                    String s1 = (String) o1;
                    String s2 = (String) o2;
                    return -s1.compareTo(s2);//加-号就是从大到小排序
                }
                throw new RuntimeException("输入的数据类型不一致");
            }
        });
        System.out.println(Arrays.toString(arr));//[JJ, GG, DD, BB, AA]
    }
    @Test
    public void test4(){
        Goods[] arr = new Goods[5];
        arr[0] = new Goods("lenovo" , 34);
        arr[1] = new Goods("dell" , 42);
        arr[2] = new Goods("xiaomi" , 12);
        arr[3] = new Goods("huawei" , 64);
        arr[4] = new Goods("microsoft" , 42);

        Arrays.sort(arr, new Comparator() {
            //指明商品比较大小的方式：按照产品名称大小从低到高排序。再按照价格从高到低排序
            @Override
            public int compare(Object o1, Object o2) {
                if(o1 instanceof Goods && o2 instanceof Goods){
                    Goods g1 = (Goods) o1;
                    Goods g2 = (Goods) o2;
                    if(g1.getName().equals(g2.getName())){
                        return -Double.compare(g1.getPrice() , g2.getPrice());
                    }else{
                        return g1.getName().compareTo(g2.getName());
                    }
                }
                throw new RuntimeException("输入的数据类型不一致");
            }
        });

        System.out.println(Arrays.toString(arr));
    }


}
//商品类
class Goods implements Comparable{
    private String name;
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Goods(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Goods() {
    }

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    //指明商品比较大小的方式：按照价格大小从低到高排序。如果相等，再按照商品名称排序（要从高到低排，可以在返回值前加个-号）
    @Override
    public int compareTo(Object o) {
        System.out.println("********");
        if(o instanceof Goods){
            Goods goods = (Goods)o;
            //方式一：
            if(this.price > goods.price){
                return 1;
            }else if(this.price > goods.price){
                return -1;
            }else{
//                return 0;         从高到低排名字,让返回值相反
                return -this.name.compareTo(goods.name);//String类型已经重写了compareTo(),故可以直接调用来比较
            }
            //方式二：
//            return Double.compare(this.price , goods.price);
        }
        throw new RuntimeException("传入的数据类型不一致！");
    }
}