package com.atguigu.java;

/**
 * 练习：创建两个分线程，其中一个线程遍历1~100以内的偶数，另一个线程遍历1~100以内的奇数
 *
 *notes:1.两个线程执行的操作不同时，需要创建两个不同的类继承Thread(如果相同一个类就行了)。
 * 每开一个线程就需要一个对象(这个对象就是线程)。
 * 2.同时，如果一个线程只使用一次的话，就可以使用Thread的匿名子类的方式，省略
 * 类的创建、对象的创建直接用new Thread(){};的方式代表Tread的匿名子类的匿名对象，
 * 而{}里面则对应的要重写run()方法。new Thread(){}.start();则是调用start的方法。
 *
 *
 * @author shkstart
 * @create 2021-04-03 12:12
 */
public class Thread02 {
    public static void main(String[] args) {
//        MyThread1 m1 = new MyThread1();
//        MyThread2 m2 = new MyThread2();
//
//        m1.start();
//        m2.start();

        //启动匿名线程
//        new Thread().start();

        //创建Thread类的匿名子类的方式：new Thread(){}.start();启动匿名子类的线程
        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    if(i % 2 == 0){
                        System.out.println(i + "**********" + Thread.currentThread().getName());
                    }
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    if(i % 2 != 0){
                        System.out.println(i + "**********" + Thread.currentThread().getName());
                    }
                }
            }
        }.start();

        //创建Runnable的匿名实现类的方式(将new Runnable(){}直接放到new Thread().start()的构造器中 )
       // new Thread().start();第一步启动匿名线程
        //第二部创建Runnable的匿名实现类赋给构造器即可
        new Thread(new Runnable() {
            @Override
            public void run() {
                //放内容
            }
        }).start();


    }
}

class  MyThread1 extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if(i % 2 == 0){
                System.out.println(i + "**********" + Thread.currentThread().getName());
            }
        }
    }
}
class MyThread2 extends  Thread{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if(i % 2 != 0){
                System.out.println(i + "**********" + Thread.currentThread().getName());
            }
        }
    }
}
