package com.atguigu.java;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 调用运行时类中指定的结构：属性、方法、构造器
 *
 * 注：Xxx为Field、Method、Constructor
 * 归纳：1.通过运行时类clazz.getDeclaredXxx()可以获取对应的java.lang.reflection.Field(/Method/Constructor)
 *      notes:获取的属性(Field)、方法(Method)、构造器(Constructor)都是在lang包的子包下java.lang.reflection
 *   其中获取属性时，里面放属性的变量名；方法，放方法的变量名和参数列表；构造器，放参数列表
 *      2.保证当前属性(/方法/构造器)是可访问的----防止低于private的结构无法使用，出现非法访问的异常
 *      3.用属性，属性的对象有set()、get(),其中set()参数1为运行时类的对象，参数2为修改的值;
 *        用方法，方法的对象有invoke()，参数1为运行时类的对象，参数2为实参(需要赋给形参的值，可以有多个)。
 *        用构造器，通过获取的构造器的对象通过newInstance()创建对象。
 *   补充：如果属性、方法是静态的。那么这些方法或属性的调用者默认就是运行时类自己。所以这些静态方法或静态属性的修改获取
 *         不再需要指明调用者是哪个谁，就是运行时类自己。故参数1不再需要放东西。但是编译时必须赋一个，所以我们一般
 *         赋给其null或者运行时类。(无论赋什么都没有任何作用，因为调用者就是运行时类自己，比如赋了别的运行时类也可以，
 *         不对其构成影响，调用者还是这个运行时类自己)。
 *         比如：Class clazz = Person.class;  static name; 得到name的属性后，name.set(User,"Tom")与
 *         name.set(null,"Tom")与name.set(Person,"Tom")其实都是一样的，默认都是Person运行时类作为调用者。静态方法同理
 *
 * @author shkstart
 * @create 2021-05-19 9:54
 */
public class ReflectionTest07 {
    /*
    不需要掌握
     */
    @Test
    public void test1() throws Exception {
        Class clazz = Person1.class;

        //创建运行时类的对象
        Person1 p = (Person1)clazz.newInstance();

        //获取指定的属性:要求运行时类中属性声明为public
        //通常不采用此方法
        Field id = clazz.getField("id");//不是public的无法获取Field，而下面的test所介
                            // 绍的方法则可以拿到Field，但是不保证访问权限的话，无法改变和获取属性值。

        /*
        设置当前属性的值

        set():参数1：指明设置哪个对象的属性   参数2：将此属性值设置为多少
         */

        id.set(p,1001);

        /*
        获取当前属性的值
        get():参数1：获取哪个对象的当前属性值
         */
        int pId = (int)id.get(p);
        System.out.println(pId);
    }

    /*
    如何操作运行时类中的指定的属性---需要掌握
     */
    @Test
    public void test2() throws Exception{
        Class clazz = Person1.class;

        //创建运行时类的对象
        Person1 p = (Person1)clazz.newInstance();

        //1. getDeclaredField(String fieldName):获取运行时类中指定变量名的属性
        //notes:该方法可以拿到低于public修饰的属性，而上面那个不行。但是拿到了低于public修饰的属性，还不能对其
        //属性值进行修改和获取，还需要保证当前属性是可访问的
        Field name = clazz.getDeclaredField("name");

        //2.保证当前属性是可访问的
        //notes:没有的话就不能对权限低于public的属性值进行设置和获取，否则会报IllegalAccessException非法访问的异常
        //没有这个设置，就只能对public的属性值进行设置和获取，就与上面的方法没区别了
        name.setAccessible(true);
        //3.获取、设置指定对象的此属性值
        name.set(p,name);
        System.out.println(name.get(p));

        //notes：调用静态属性跟下面的静态方法相同。假设name为static的属性。因为属性name本身是由Person1.class
        //获取的，调用属性name的方法(set、get)的调用者就是该运行时类呗,所以其实不需要参数1指明调用者是哪个了。但是
        //编译又需要赋一个该参数。所以像set()、get()里面可以放null或者放个Person1.class都行(参数放什么都一样，
        // 默认都是Person1运行时类调用的，即使放String也可以，因为默认的是运行时类作为调用者)。
        //比如：name.set(null,name);   name.get(null);
    }

    /*
    如何操作运行时类中的指定的方法---需要掌握
     */
    @Test
    public void test3() throws Exception{
        Class clazz = Person1.class;

        //创建运行时类的对象
        Person1 p = (Person1)clazz.newInstance();

        //1.获取指定的某个方法
        //getDeclaredMethod():参数1：指明获取的方法的名称  参数2：指明获取的方法的形参列表
        Method show = clazz.getDeclaredMethod("show", String.class);

        //2.保证当前方法是可访问的
        show.setAccessible(true);

        //3.调用方法的invoke():参数1：方法的调用者  参数2：给方法形参赋值的实参
        //invoke()的返回值即为对应类中调用的方法的返回值。
        Object returnValue = show.invoke(p,"CHN");//String nation = p.show("CHN");
        System.out.println(returnValue);

        System.out.println("***********如何调用静态方法************");

        //private static void showDesc()

        Method showDesc = clazz.getDeclaredMethod("showDesc");
        showDesc.setAccessible(true);
        //如果调用的运行时类中的方法没有返回值(void)，则此invoke()返回null
        Object invoke = showDesc.invoke(Person1.class);
        //也可以这样调用静态方法。因为showDesc本身就是Person1.class获取的,而静态方法在对象之前就已存在。
        //故invoke()由showDesc()方法调用，而showDesc()就是由Person1.class调用，已经不需要指明参数1调用者
        // 所以其实里面不需要放东西，但是又需要放一个参数，那么就放一个null或运行时类也行。
        // (参数放什么都一样，默认都是Person1运行时类调用的，即使放String也可以，因为默认的是运行时类作为调用者)。
//        Object invoke = showDesc.invoke(null);
        System.out.println(invoke);//null
    }

    /*
    如何调用运行时类中的指定的构造器---不常用。----常用的是Class实例的newInstance()
     */
    @Test
    public void test4() throws Exception{
        Class clazz = Person1.class;

        //private Person(String name)

        //1.获取指定的构造器
        //getDeclaredConstructor():参数：指明构造器的参数列表
        Constructor declaredConstructor = clazz.getDeclaredConstructor(String.class);

        //2.保证此构造器是可访问的
        declaredConstructor.setAccessible(true);

        //3.调用此构造器创建运行时类的对象
        Person1 p = (Person1)declaredConstructor.newInstance("Tom");
        System.out.println(p);
    }
}
