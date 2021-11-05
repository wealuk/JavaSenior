package com.atguigu.java;

/**
 * 一、枚举类的使用
 * 1.枚举类的理解：类的对象只有有限个，确定的。我们称此类为枚举类。
 * 2.当需要定义一组常量时，强烈建议使用枚举类。
 * 3.如果枚举类中只有一个对象，则可以作为单例模式的实现方式。
 *
 * 二、如何定义枚举类
 * 方式一：jdk 5.0之前，自定义枚举类--见下面
 * 方式二：jdk 5.0，可以使用enum关键字定义枚举类--见Test02
 *
 * 三、Enum类中的常用方法： --见Test02
 *      values()方法：返回枚举类型的对象数组。该方法可以很方便地遍历所有的枚举值。
 *      valueOf(String str)：可以把一个字符串转为对应的枚举类对象。要求字符 串必须是枚举类对象的“名字”。
 *      如不是，会有运行时异常：  IllegalArgumentException。
 *      toString()：返回当前枚举类对象常量的名称
 *
 * 四、使用enum关键字定义的枚举类实现接口的情况 --见Test02
 *      情况一：实现接口，在enum类中实现抽象方法（每一个枚举类中的对象调用的重写的抽象方法内容一致）----在类中重写
 *      情况二：让枚举类的对象分别实现接口中的抽象方法(每个枚举类的对象都有自己独特的重写方法)----在每个对象的构造器后面加{},里面重写
 *
 * @author shkstart
 * @create 2021-04-20 21:06
 */
public class EnumTest01 {
    public static void main(String[] args) {
        Season spring = Season.SPRING;//将SPRING对象赋给一个Season
        System.out.println(spring);//Season{seasonName='春天', seasonDesc='春日花开'}
    }                           //如果没有重写toString方法那么就是地址值了
}
//自定义枚举类
class Season{
    //1.声明Season对象的属性:private final修饰(对象是常量，故其属性也应该是常量)。
    private final String seasonName;//显式、代码块赋值会使每个对象的属性一样
    private final String seasonDesc;//只有构造器赋值才能使不同对象属性不一样

    //2.私有化类的构造器，并给对象属性赋值(对象是确定的，故不能让外界创建对象)
    private Season(String seasonName , String seasonDesc){
        this.seasonName = seasonName;
        this.seasonDesc = seasonDesc;
    }

    //3.提供当前枚举类的多个对象：public static final的
    public static final Season SPRING = new Season("春天" , "春日花开");
    public static final Season SUMMER = new Season("夏天" , "夏日炎炎");
    public static final Season AUTUMN = new Season("秋天" , "秋高气爽");
    public static final Season WINTER = new Season("冬天" , "冰天雪地");

    //4.其他诉求1：获取枚举类对象的属性
    public String getSeasonName() {
        return seasonName;
    }

    public String getSeasonDesc() {
        return seasonDesc;
    }

    //4.其他诉求2：提供toString()
    @Override
    public String toString() {
        return "Season{" +
                "seasonName='" + seasonName + '\'' +
                ", seasonDesc='" + seasonDesc + '\'' +
                '}';
    }
}
