package com.atguigu.java;

/**
 * 使用enum关键字定义枚举类
 * 说明:定义的枚举类默认继承于java.lang.Enum类
 *
 * 总结(notes)：1.将class关键字改成enum，此时需要先声明对象。在声明对象时，就可以(是必须省略)
 * 省略public static final 类  和 = new 类(都有这个结构就省略不用写呗),直接
 * 对象名 + 构造器()就行了(如果没有属性，那么构造器都不需要了)。不过记住对象之间用“,”隔开，之后一个才用“;”。
 *          2.此时的类虽然没有显式的写继承于哪个类，但要记住并不是继承的Object，而是Enum。
 *  而Enum类中有重写的toString方法，故类也继承了该方法，无需重写toString，也会直接打印对象名。
 *  当然，如果不想要打印对象名而要别的，也可以在类中重写toString方法
 *
 * @author shkstart
 * @create 2021-04-20 22:47
 */
public class EnumTest02 {
    public static void main(String[] args) {
        Season1 summer = Season1.SUMMER;
        //toString():
        System.out.println(summer.toString());//SUMMER，此时没有重写toString,但打印的不是地址值，而是对象名

        System.out.println(Season1.class.getSuperclass());//java.lang.Enum

        System.out.println("**********");
        //values():返回枚举类型的对象数组。该方法可以很方便地遍历所有的枚举值。
        Season1[] values = Season1.values();
        for (int i = 0; i < values.length; i++) {
            System.out.println(values[i]);
            values[i].show();
        }

        System.out.println("*********");
        Thread.State[] values1 = Thread.State.values();//State(线程状态)也是一个枚举类
        for (int i = 0; i < values1.length; i++) {
            System.out.println(values1[i]);
        }

        //valueOf(String objName):返回枚举类中对象名是objName的对象
        Season1 winter = Season1.valueOf("WINTER");
        System.out.println(winter);//WINTER
        //如果没有objName的枚举类对象，则抛异常：IllegalArgumentException
//        Season1 winter1 = Season1.valueOf("WINTER1");

        winter.show();

    }
}

interface Info{
    void show();
}

//使用enum关键字定义枚举类
enum Season1 implements Info{
    //1.提供当前枚举类的多个对象,多个对象之间用“,”隔开，末尾对象“;”结束
    SPRING("春天" , "春日花开"){//如果没有属性，连构造器也可以省略

        @Override
        public void show() {
            System.out.println("1");
        }
    },
    SUMMER("夏天" , "夏日炎炎"){
        @Override
        public void show() {
            System.out.println("2");
        }
    },
    AUTUMN("秋天" , "秋高气爽"){
        @Override
        public void show() {
            System.out.println("3");
        }
    },
    WINTER("冬天" , "冰天雪地"){
        @Override
        public void show() {
            System.out.println("4");
        }
    };

    //2.声明Season对象的属性:private final修饰(对象是常量，故其属性也应该是常量)。
    private final String seasonName;//显式、代码块赋值会使每个对象的属性一样
    private final String seasonDesc;//只有构造器赋值才能使不同对象属性不一样

    //3.私有化类的构造器，并给对象属性赋值(对象是确定的，故不能让外界创建对象)
    private Season1(String seasonName , String seasonDesc){
        this.seasonName = seasonName;
        this.seasonDesc = seasonDesc;
    }


    //4.其他诉求1：获取枚举类对象的属性
    public String getSeasonName() {
        return seasonName;
    }

    public String getSeasonDesc() {
        return seasonDesc;
    }

//    //4.其他诉求2：提供toString()
//    @Override
//    public String toString() {
//        return "Season{" +
//                "seasonName='" + seasonName + '\'' +
//                ", seasonDesc='" + seasonDesc + '\'' +
//                '}';
//    }


    //情况一：这样每个枚举类中的对象show方法都是一样的。如果要实现内容不一致就需要考虑情况二了
//    @Override
//    public void show() {
//        System.out.println("这是一个季节");
//    }
}
