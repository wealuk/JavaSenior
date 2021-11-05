package com.atguigu.java;

import org.junit.Test;

import java.io.*;

/**
 * 对象流的使用
 * 1.ObjectInputStream 和 ObjectOutputStream----都是字节流
 * 2.作用：用于存储和读取基本数据类型数据或对象的处理流。它的强大之处就是可以把Java中的对象写入到数据源中，
 *  也能把对象从数据源中还原回来。
 *
 * 3.要想一个java对象是可序列化的，需要满足相应的要求。见下面的class Person
 *
 * 4.对象的序列化机制：
 * 对象序列化机制允许把内存中的Java对象转换成平台无关的二进制流，从而允许把这种二进制流持久地保存在磁盘上，
 * 或通过网络将这种二进制流传 输到另一个网络节点。(序列化)//当其它程序获取了这种二进制流，就可以恢复成原来的Java对象(反序列化)
 *
 * @author shkstart
 * @create 2021-05-10 10:54
 */
public class IOTest01 {

    /*
    序列化过程：将内存中的java对象保存到磁盘中或通过网络传输出去
    使用ObjectOutputStream实现
    注：保存到磁盘不是用来打开看的，而是为了持久化数据。用的时候再读取
     */
    @Test
    public void test1(){
        ObjectOutputStream oos = null;
        try {
            //1.
            oos = new ObjectOutputStream(new FileOutputStream("object.dat"));
            //2.
            oos.writeObject(new String("我爱上海"));
            oos.flush();//刷新操作

            oos.writeObject(new Person("Tom",23));
            oos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(oos != null){
                //3.
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /*
    反序列化：将磁盘文件中的对象还原为内存中的一个java对象
    使用ObjectInputStream实现
     */
    @Test
    public void test2(){
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("object.dat"));

            Object obj = ois.readObject();
            String str = (String)obj;

            Person p = (Person) ois.readObject();

            System.out.println(str);
            System.out.println(p);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

/*
Person需要满足如下的要求，方可序列化
1.需要实现接口：Serializable  ---- 该接口中没写任何内容，起标识作用。表示凡是实现该接口的类都是可序列化的
2.当前类提供一个全局常量(序列版本号)：serialVersionUID
3.除了当前Person类需要实现Serializable接口之外，还必须保证其内部所有的属性也必须是
可序列化。(默认情况下，基本数据类型可序列化。而String已经满足了这些条件)---常指作为该类属性的类也必须满足可序列化的三个条件

notes:凡是实现Serializable接口的类都有一个表示序列化版本标识符的静态常量(serialVersionUID)。
    原因：1.serialVersionUID用来表明类的不同版本间的兼容性。简言之，其目的是以序列化对象 进行版本控制，有关各版本反序列化时是否兼容。
        2.如果类没有显示定义这个静态常量，它的值是Java运行时环境根据类的内部细节自动生成的。若类的实例变量做了
        修改，serialVersionUID 可能发生变化。故建议显式声明。
     原因总结：其实就是类没有显式声明的话，其值根据类的内部细节自动生成的。如果将该类的对象序列化到磁盘中后，对该类的
            内部结构进行了修改或添加，那么其serialVersionUID值就改变了。当反序列化时，系统根据原来的值匹配不到这个类了
            (因为该类修改后值变了)，那么就不能反序列化回来了。
            所以显式的声明serialVersionUID的值，即使修改了类，值也不变，反序列化就不会出现问题。

补充：ObjectOutputStream和ObjectInputStream不能序列化static和transient修饰的成员变量(这些成员变量本
        身是可序列化的满足第三点要求，只是加上这两个关键字后不能序列化了(其他属性仍然正常序列化)，也就是说如果
        我们有些属性不想序列化保存和读取，就加上这些关键字，打印对象的这些属性结果就是默认值，而别的正常序列化保存和读取)
notes：属性不是可序列化的与不能序列化是两个概念。像String、基本数据类型是可序列化的，但加上static和transient修饰它们
就不能序列化了。也就是传输的过程传不了，而别的正常的仍然序列化成功，正因为这些对象的属性数据传输不了，打印读取的对象时，
这些属性就为默认值。
 */
class Person implements Serializable{

    public static final long serialVersionUID = 457898744L;

    private String name;//如果加上static或transient。那么对象name的数据就序列化不了，也就是无法保存和读取。
                        //打印读取的对象后，我们就会发现，每个保存的对象的name都是null，因为对象的name无法序列化，用的默认值。
    private int age;//同理，如果这个加上static或transient，那么每个对象的age都是默认的0。

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

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }
}
