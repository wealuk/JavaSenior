package com.atguigu.java;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * 注解有成员时，在调用注解时必须给成员(value)赋值。当然如果有默认的值(defualt)，就可以不用再指明了
 * @author shkstart
 * @create 2021-04-21 13:38
 */
@Inherited
@Repeatable(EnumTest05s.class)
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE,TYPE_PARAMETER,TYPE_USE})//如果去掉TYPE就不能注解类、接口、枚举类了。其他同理
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumTest05 {

//    String value();//看起来是一种方法，其实是一个属性。如果用这个的话就没有默认值，后面调用注解时就需要对其指明成员的值

    String value() default "hello";//这个的话用注解就不需要再指明了
}

@Inherited//这个元注解一个有另一个也要有
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
@interface EnumTest05s{
    EnumTest05[] value();
}
