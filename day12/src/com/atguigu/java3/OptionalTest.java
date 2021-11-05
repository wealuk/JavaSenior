package com.atguigu.java3;

import org.junit.Test;

import java.util.Optional;

/**
 * Optional类：为了在程序中避免出现空指针异常而创建的。---对对象进行ofNullable包装，然后用orElse给个
 *                                                  备胎防止出现null空指针。必定返回一个非空的新对象
 * 常用的方法：ofNullable(T t)
 *            orElse(T t)
 *
 * @author shkstart
 * @create 2021-05-25 11:54
 */
public class OptionalTest {
    /*
Optional.of(T t) : 创建一个 Optional 实例，t必须非空；
Optional.empty() : 创建一个空的 Optional 实例
Optional.ofNullable(T t)：t可以为null
     */
    @Test
    public void test1(){
        Girl girl = new Girl();
        girl = null;
        //of(T t)：保证t是非空的
        Optional<Girl> optionalGirl = Optional.of(girl);
    }

    @Test
    public void test2(){
        Girl girl = new Girl();
        girl = null;
        //ofNullable(T t)：t可以为null
        Optional<Girl> optionalGirl = Optional.ofNullable(girl);
        System.out.println(optionalGirl);

        //orElse(T t1):如果当前的Optional内部封装的t是非空的，则返回内部的t。
        //如果内部的t是空的，则返回orElse()方法中的参数t1. --- 可以规避空指针的问题
        Girl girl1 = optionalGirl.orElse(new Girl("太阳"));
        System.out.println(girl1);
    }

    public String getGirlName(Boy boy){
        return boy.getGirl().getName();
    }
    @Test
    public void test3(){
        Boy boy = new Boy();
        String girlName = getGirlName(boy);
        System.out.println(girlName);//存在空指针异常
    }

    //优化以后的getGirlName():
    public String getGirlName1(Boy boy){
        if(boy != null){
            Girl girl = boy.getGirl();
            if(girl != null){
                return girl.getName();
            }
        }
        return null;
    }
    @Test
    public void test4(){
        Boy boy = new Boy();
        String girlName = getGirlName1(boy);
        System.out.println(girlName);
    }

    //使用Optional类的getGirlName()：
    public String getGirlName2(Boy boy){
        Optional<Boy> boyOptional = Optional.ofNullable(boy);
        //此时的boy1一定非空
        Boy boy1 = boyOptional.orElse(new Boy(new Girl("傻姑凉")));

        Girl girl = boy1.getGirl();

        Optional<Girl> girl1 = Optional.ofNullable(girl);
        //girl2一定非空
        Girl girl2 = girl1.orElse(new Girl("这"));

        return girl2.getName();
    }
    @Test
    public void test5(){
        Boy boy = null;//傻姑凉
//        boy = new Boy();//这
//        boy = new Boy(new Girl("蔡徐坤"));蔡徐坤，没有空的正常情况
        String girlName = getGirlName2(boy);
        System.out.println(girlName);
    }
}



class Girl{
    private String name;

    public Girl() {
    }

    public Girl(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Girl{" +
                "name='" + name + '\'' +
                '}';
    }
}

class Boy{
    private Girl girl;

    @Override
    public String toString() {
        return "Boy{" +
                "girl=" + girl +
                '}';
    }

    public Boy() {
    }

    public Boy(Girl girl) {
        this.girl = girl;
    }

    public Girl getGirl() {
        return girl;
    }

    public void setGirl(Girl girl) {
        this.girl = girl;
    }
}
