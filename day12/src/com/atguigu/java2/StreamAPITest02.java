package com.atguigu.java2;

import com.atguigu.java1.Employee;
import com.atguigu.java1.EmployeeData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 测试Stream的中间操作
 *
 * @author shkstart
 * @create 2021-05-24 12:56
 */
public class StreamAPITest02 {

    //1-筛选与切片
    @Test
    public void test1(){
        List<Employee> list = EmployeeData.getEmployees();
//        filter(Predicate p)---接收Lambda ,从流中排除某些元素
        Stream<Employee> stream = list.stream();
        //练习：查询员工表中薪资大于7000的员工的信息
        stream.filter(e -> e.getSalary() > 7000).forEach(System.out::println);

        System.out.println();

//        limit(long maxSize)---截断流，使其元素不超过给定数量
        list.stream().limit(3).forEach(System.out::println);//前面的stream已关闭，需要重新创建Stream的实例

        System.out.println();

//        skip(long n)---跳过元素，返回一个扔掉了前n个元素的流。若流中元素不足n个，则返回一个空流。与limit(n) 互补
        list.stream().skip(3).forEach(System.out::println);//与limit(3)互补，limit是前3个，skip是3个以后所有

        System.out.println();

//        distinct()---筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
        list.add(new Employee(1010,"刘强东",40,8000));
        list.add(new Employee(1010,"刘强东",40,8000));
        list.add(new Employee(1010,"刘强东",40,8000));
        list.add(new Employee(1010,"刘强东",40,8000));

//        System.out.println(list);

        list.stream().distinct().forEach(System.out::println);
    }

    //映射
    @Test
    public void test2(){
//        map(Function f) --- 接收一个函数作为参数，将元素转化为其他形式或提取信息，该函数会被应用到每个元素上，并将其映射成一个新的元素。
        List<String> list = Arrays.asList("aa", "bb", "cc", "dd");
        list.stream().map(str -> str.toUpperCase()).forEach(System.out::println);

//        练习1：获取员工姓名长度大于3的员工的姓名。
        List<Employee> employees = EmployeeData.getEmployees();
        Stream<String> namesStream = employees.stream().map(Employee::getName);
        namesStream.filter(name -> name.length() > 3).forEach(System.out::println);

        System.out.println();
        //练习2
        Stream<Stream<Character>> streamStream = list.stream().map(StreamAPITest02::fromStringToStream);
        streamStream.forEach(s -> {
            s.forEach(System.out::println);
        });

//        flatMap(Function f) --- 接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。
        //map()和flatMap(Function f)的区别就相当于list集合中的add()、addAll(),add只能添加集合整体，而addAll
        // 可以将集合拆开再添加到集合。而map对应add，flagMap对应addAll
        Stream<Character> characterStream = list.stream().flatMap(StreamAPITest02::fromStringToStream);
        characterStream.forEach(System.out::println);//相比于练习2方便很多，直接自动将Stream中的Stream<Character>打开了
    }

    //将字符串中的多个字符构成的集合转化为对应的Stream实例---用于练习2和flatMap(Function f)的验证，比较map和flagMap的区别
    public static Stream<Character> fromStringToStream(String str){
        ArrayList<Character> list = new ArrayList<>();
        for(Character c : str.toCharArray()){
            list.add(c);
        }
        return list.stream();
    }

    //3-排序
    @Test
    public void test3(){
        //sorted()--自然排序
        List<Integer> list = Arrays.asList(12, 43, 65, 78, -8, 74);
        list.stream().sorted().forEach(System.out::println);
        //抛异常，原因：Employee没有实现Comparable接口
//        List<Employee> employees = EmployeeData.getEmployees();
//        employees.stream().sorted().forEach(System.out::println);


        //sorted(Comparator com)--定制排序
        List<Employee> employees = EmployeeData.getEmployees();
        employees.stream().sorted((e1,e2) -> Integer.compare(e1.getAge(),e2.getAge())).forEach(System.out::println);
    }
}
