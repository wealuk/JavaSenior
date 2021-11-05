package com.atguigu.java;

/**
 * 多线程的创建，方式一：继承于Thread类
 * 1.创建一个继承于Thread类的子类
 * 2.重写Thread类的run() ---> 将此线程执行的操作声明在run()
 * 3.创建Thread类的子类的对象
 * 4.通过此对象调用start()
 * notes：①第三步创建对象就是创建线程，start则是开启线程。故Thread.currentThread()
 * 所返回的线程就是第三步创建的对象
 * ②所以在继承Thread的子类中this.与Thread.currentThread().是指的同一个东西(该线程的)
 * 并且在类中this.常省略，里面的方法类似于Thread.currentThread().方法，指线程的方法
 *
 *<p>
 * 例子:遍历100以内的所有的偶数
 *
 * @author shkstart
 * @create 2021-04-03 10:02
 */

//1.创建一个继承于Thread类的子类
class MyThread extends Thread{
    //2.重写Thread类的run()
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if(i % 2 == 0){
                System.out.println(Thread.currentThread().getName() + i);
            }//Thread.currentThread().getName()获取当前线程的名字
        }
    }
}


public class Thread01 {
    public static void main(String[] args) {
        //3.创建Thread类的子类的对象
        MyThread t1 = new MyThread();
        //4.通过此对象调用start():①启动当前线程 ②调用当前线程的run()
        t1.start();
        //notes：执行了start方法时，才会开辟新的线程，而新的线程与main线程是并行的。
        //创建对象，以及对象.方法都是属于main线程的，开始start方法就是另一个线程了
        //且开辟线程后就会执行run方法，run里面就是该线程需要执行的操作

        //notes：如果不是t1.start(),而是直接t1.run()就不是多线程了。而是在main中简单的创建对象
        //然后调用对象的方法，没有开辟新的线程(一直是在main线程中)
        //问题一：我们不能通过直接调用run()的方式启动线程
//        t1.run();

        //问题二：再启动一个线程，遍历100以内的偶数。不可以还让已经start()的线程去执行。
        //会报IllegalThreadStateException
//        t1.start();
        //所以我们需要重新创建一个线程的对象
        MyThread t2 = new MyThread();
        t2.start();
        //notes：我们需要几个线程就需要创建几个继承于Thread类的对象。而要启动线程就必须使用start()
        //当然如果这几个线程是执行相同的操作，就是创建同一个类的对象；如果执行的操作不同，那么
        //就需要创建不同的类有不同的run方法，从而创建不同的对象。


        //如下操作仍然是在main线程中执行
        for (int i = 0; i < 100; i++) {
            if(i % 2 == 0){
                System.out.println(i + "**********" + Thread.currentThread().getName());
            }
        }
    }
}
