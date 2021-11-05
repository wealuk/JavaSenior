package com.atguigu.java1;

import org.junit.Test;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 一、构造器引用
 *      和方法引用类似，函数式接口的抽象方法的形参列表和构造器的形参列表一致。
 *      抽象方法的返回值类型即为构造器所属的类的类型
 *      notes：一句话：抽象方法返回的是一个对象，而抽象方法的形参列表与对象的构造器形参列表一致就可以
 *          使用  对象的类::new  的形式来代替Lambda表达式 (抽象方法的参数) -> new 对象(参数);
 *
 * 二、数组引用
 *      大家可以把数组看做是一个特殊的类，则写法与构造器引用一致
 *
 * Created by shkstart
 */
public class ConstructorRefTest {
	//构造器引用
    //Supplier中的T get()
    //Employee的空参构造器：Employee()
    @Test
    public void test1(){

        Supplier<Employee> sup = new Supplier<Employee>() {
            @Override
            public Employee get() {
                return new Employee();
            }
        };
        System.out.println(sup.get());

        System.out.println("********");

        Supplier<Employee> sup1 = () -> new Employee();

        System.out.println("**********");

        Supplier<Employee> sup2 = Employee::new;
	}

	//Function中的R apply(T t)
    @Test
    public void test2(){
        Function<Integer,Employee> func1 = id -> new Employee(id);
        Employee employee = func1.apply(1001);
        System.out.println(employee);

        System.out.println("********");

        Function<Integer,Employee> func2 = Employee::new;
        Employee employee1 = func2.apply(1002);
        System.out.println(employee1);
    }

	//BiFunction中的R apply(T t,U u)
    @Test
    public void test3(){
        BiFunction<Integer,String,Employee> func1 = (id,name) -> new Employee(id,name);
        System.out.println(func1.apply(1001,"Tom"));

        System.out.println("**********");

        BiFunction<Integer,String,Employee> func2 = Employee::new;
        System.out.println(func2.apply(1001,"Tom"));
	}

	//数组引用
    //Function中的R apply(T t)
    @Test
    public void test4(){
        Function<Integer,String[]> fun1 = length -> new String[length];
        String[] arr1 = fun1.apply(5);
        System.out.println(Arrays.toString(arr1));

        System.out.println("*********");

        Function<Integer,String[]> fun2 = String[]::new;
        String[] arr2 = fun2.apply(10);
        System.out.println(Arrays.toString(arr2));
    }
}
