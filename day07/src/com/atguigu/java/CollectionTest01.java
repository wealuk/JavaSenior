package com.atguigu.java;

import org.junit.Test;

import java.util.*;

/**
 * 一、Map的实现类的结构：
 * |---Map:双列数据，存储key-value对的数据  -----类似于高中的函数：y = f(x)
 *      |---HashMap:作为Map的主要实现类：线程不安全的，效率高;存储null的key和value
 *            |---LinkedHashMap:保证在遍历map元素时，可以按照添加的顺序实现遍历。
 *                          原因：在原有的HashMap底层结构基础上，添加了一对指针，指向前一个和后一个元素。----Entry[]数组上多了一对指针(jdk7)，而jdk8中HashMap用的Node[],LinkedHashMap仍用Entry[]
 *                          对于频繁的遍历操作，此类执行效率高于HashMap。
 *      |---TreeMap:保证按照添加的key-value对进行排序，实现排序遍历。此时考虑key的自然排序或定制排序。
 *                  底层使用红黑树
 *      |---Hashtable:作为古老的实现类：线程安全的，效率低；不能存储null的key和value
 *            |--Properties:常用来处理配置文件。key和value都是String类型
 *
 *      HashMap的底层：数组+链表（jdk7及之前）
 *                    数组+链表+红黑树（jdk8）
 *
 *
 *  面试题：
 *     1.HashMap的底层实现原理？
 *     2.HashMap 和 Hashtable的异同？
 *     3.CurrentHashMap(分段同步锁) 与 Hashtable的异同？ （暂时不讲）
 *
 *
 * 二、Map结构的理解：
 *      Map中的key：无序的、不可重复的，使用Set存储所有的key(HashMap就用HashSet存，LinkedHashMap就用LinkedHashSet存)
 *                              --->key所在的类要重写equals()和hashCode() (以HashMap为例----TreeMap就与两个排序有关了)
 *      Map中的value：无序的、可重复的，使用Collection存储所有的value  ---->value所在的类要重写equals()-----因为containsValue()方法需要
 *      一个键值对：key-value构成了一个Entry对象。(key、value作为Entry对象的两个属性)
 *      Map中的entry：无序的、不可重复的，使用Set存储所有的entry
 *
 * 三、HahsMap的底层实现原理？以jdk7为例说明：
 *      HashMap map = new HashMap();
 *      在实例化以后，底层创建了长度是16的一维数组Entry[] table。
 *      ...可能已经执行多次put...
 *      map.put(key1 , value1);
 *      首先，调用key1所在类的hashCode()计算key1哈希值，此哈希值经过某种算法计算以后，得到在Entry数组中的存放位置。
 *      如果此位置上的数据为空，此时的key1-value1添加成功。---情况1
 *      如果此位置上的数据不为空，(意味着此位置上存在一个或多个数据(以链表形式存在))，比较key1和已经存在的一个或多个数据
 *      的哈希值:
 *              如果key1的哈希值与已经存在的数据的哈希值都不相同，此时key1-value1添加成功。---情况2
 *              如果key1的哈希值和已经存在的某一个数据(key2-value2)的哈希值相同，继续比较：调用key1所在类的equals(key2)方法，比较：
 *                          如果equals()返回false：此时key1-value1添加成功。---情况3
 *                          如果equals()返回true：使用value1替换value2
 *
 *         补充：关于情况2和情况3：此时key1-value1和原来的数据以链表的方式存储。
 *
 *         在不断的添加过程中，会涉及到扩容问题，当超出临界值(且要存放的位置非空)时，扩容。默认的扩容方式：扩容为原来容量的2倍，并将原有的数据复制过来。
 *
 *    jdk8 相较于jdk7在底层实现方面的不同：
 *          1.new HashMap():底层没有创建一个长度为16的数组
 *          2.jdk 8底层的数组是：Node[],而非Entry[]
 *          3.首次调用put()方法时，底层创建长度为16的数组
 *          4.jdk底层结构只有：数组+链表，jdk8中底层结构：数组+链表+红黑树。
 *             当数组的某一个索引位置上的元素以链表形式存在的数据个数 > 8且当前数组的长度 > 64时，
 *             此时此索引位置上的所有数据改为使用红黑树存储。
 *
 *           DEFAULT_INITIAL_CAPACITY : HashMap的默认容量，16
 *           DEFAULT_LOAD_FACTOR：HashMap的默认加载因子：0.75
 *           threshold：扩容的临界值，=容量*填充因子：16*0.75 =>12
 *           TREEIFY_THRESHOLD：Bucket中链表长度大于该默认值，转化为红黑树:8
 *           MIN_TREEIFY_CAPACITY：桶中的Node被树化时最小的hash表容量:64
 *
 *  四、LinkedHashMap的底层实现原理（了解）
 *        源码中：
 *              static class Entry<K,V> extends HashMap.Node<K,V> {
                      Entry<K,V> before, after;//能够记录添加的元素的先后顺序
                      Entry(int hash, K key, V value, Node<K,V> next) {
                          super(hash, key, value, next);
                      }
                  }
 *
 * 五、Map中定义的方法：
 * 添加、删除、修改操作：
 * Object put(Object key,Object value)：将指定key-value添加到(或修改)当前map对象中
 * void putAll(Map m):将m中的所有key-value对存放到当前map中
 * Object remove(Object key)：移除指定key的key-value对，并返回value
 * void clear()：清空当前map中的所有数据
 * 元素查询的操作：
 * Object get(Object key)：获取指定key对应的value
 * boolean containsKey(Object key)：是否包含指定的key
 * boolean containsValue(Object value)：是否包含指定的value
 * int size()：返回map中key-value对的个数
 * boolean isEmpty()：判断当前map是否为空
 * boolean equals(Object obj)：判断当前map和参数对象obj是否相等
 * 元视图操作的方法：
 * Set keySet()：返回所有key构成的Set集合
 * Collection values()：返回所有value构成的Collection集合
 * Set entrySet()：返回所有key-value对构成的Set集合
 *
 * 总结:常用的方法：
 *      增：put(Object key,Object value)
 *      删：remove(Object key)
 *      改：put(Object key,Object value)
 *      查：get(Object key)
 *      长度：size()
 *      遍历：keySet() / values() / entrySet()
 *
 *
 * @author shkstart
 * @create 2021-04-27 14:42
 */
public class CollectionTest01 {

    @Test
    public void test1(){
        Map map = new HashMap();
//        map = new Hashtable();
        map.put(null,123);
    }

    @Test
    public void test2(){
        Map map = new HashMap();
        map = new LinkedHashMap();
        map.put(123,5);
        map.put(234,6);
        map.put(345,7);
        System.out.println(map);
    }
    /*
    添加、删除、修改操作：
      Object put(Object key,Object value)：将指定key-value添加到(或修改)当前map对象中
      void putAll(Map m):将m中的所有key-value对存放到当前map中
      Object remove(Object key)：移除指定key的key-value对，并返回value
      void clear()：清空当前map中的所有数据
     */
    @Test
    public void test3(){
        Map map = new HashMap();
        //添加
        map.put("AA",123);
        map.put(45,123);
        map.put("BB",123);
        //修改
        map.put("AA",87);

        System.out.println(map);//{AA=87, BB=123, 45=123}

        Map map1 = new HashMap();
        map1.put("CC",123);
        map1.put("DD",123);

        map.putAll(map1);
        System.out.println(map);

        //remove(Object key)
        Object value = map.remove("CC");//如果key不存在，那么返回值为null
        System.out.println(value);//返回的为key对应的value值
        System.out.println(map);

        //clear()
        map.clear();//与map = null操作不同,依然是实例化的只是没有元素
        System.out.println(map.size());//0，没有空指针异常
        System.out.println(map);//{}
    }
    /*
    元素查询的操作：
 * Object get(Object key)：获取指定key对应的value
 * boolean containsKey(Object key)：是否包含指定的key
 * boolean containsValue(Object value)：是否包含指定的value
 * int size()：返回map中key-value对的个数
 * boolean isEmpty()：判断当前map是否为空
 * boolean equals(Object obj)：判断当前map和参数对象obj是否相等
     */
    @Test
    public void test4(){
        Map map = new HashMap();
        map.put("AA",123);
        map.put(45,123);
        map.put("BB",56);

        //Object get(Object key)
        System.out.println(map.get(455));//null
        //containsKey(Object key)
        boolean isExist = map.containsKey("BB");//调用了"BB"String的hashCode和equals方法
        System.out.println(isExist);//true

        //containsValue(Object value)
        isExist = map.containsValue(123);//找到第一个后就不往后找了
        System.out.println(isExist);

        System.out.println(map.size());//3

        map.clear();
        System.out.println(map.isEmpty());//是否为空，看size是否为0就行了
    }
    /*
    元视图操作的方法：----主要用于遍历操作，因为Map没有Collection中的遍历操作。正好key、value、Entry[]都是用Collection存储，
                        所以可以用这些方法转为Collection再使用三种方法来遍历key、value、Entry[]。注意：内容是Entry[]的对象
                        可以强转为Map.Entry(Map中的静态内部接口)，从而调用里面的getKey()、getValue()获取单独key和value
          notes：需要掌握如何遍历key、value、键值对。也就是取出这些数据，后面需要用到。
  Set keySet()：返回所有key构成的Set集合
  Collection values()：返回所有value构成的Collection集合
  Set entrySet()：返回所有key-value对构成的Set集合
     */
    @Test
    public void test5(){
        Map map = new HashMap();
        map.put("AA",123);
        map.put(45,123);
        map.put("BB",56);

        //遍历所有的key集：keySet()
        Set set = map.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        System.out.println();
        //遍历所有的value集：values()
        Collection values = map.values();
        for(Object obj : values){
            System.out.println(obj);
        }

        //遍历所有的key-value:------需要知道怎么分离出key和value
        // 方式一：entrySet()
        Set entrySet = map.entrySet();
        Iterator iterator1 = entrySet.iterator();
        while (iterator1.hasNext()){
            Object obj = iterator1.next();
            System.out.println(obj);
            //entrySet集合中的元素都是entry
            Map.Entry entry = (Map.Entry)obj;  //notes：Entry是Map中的内部静态接口,有分别获取出key和value的方法
            System.out.println(entry.getKey() + "--->" + entry.getValue());
        }
        //方式二：获取key后，调用get(key)方法获取value
        Set keySet = map.keySet();
        Iterator iterator2 = keySet.iterator();
        while (iterator2.hasNext()){
            Object key = iterator2.next();
            Object value = map.get(key);
            System.out.println(key + "===" + value);
        }
    }
}
