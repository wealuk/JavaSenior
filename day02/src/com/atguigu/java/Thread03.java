package com.atguigu.java;

/**
 * 使用同步方法解决实现Runnable接口的线程安全问题
 *
 * 关于同步方法的总结：
 * 1.同步方法仍然涉及到同步监视器，只是不需要我们显式的声明
 * 2.非静态的同步方法，同步监视器是：this(用于实现Runnable这种共用同一个对象的)
 *  静态的同步方法，同步监视器是：当前类本身(用于继承Thread类这种有多个对象的，但是同一个类的)
 *  //notes：继承Thread也可以用到this的锁，比如Thread08，应该具体问题具体分析
 *
 * @author shkstart
 * @create 2021-04-06 11:09
 */
class Window2 implements Runnable{
    private int ticket = 100;

    @Override
    public void run() {
        while(true){
            show();

            }

    }
    public synchronized void show(){
        if (ticket > 0) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + ":卖票，票号为：" + ticket);
            ticket--;
        }
    }
}
public class Thread03 {
    public static void main(String[] args) {
        Window2 w1 = new Window2();

        Thread t1 = new Thread(w1);
        Thread t2 = new Thread(w1);
        Thread t3 = new Thread(w1);

        t1.setName("窗口一");
        t2.setName("窗口二");
        t3.setName("窗口三");

        t1.start();
        t2.start();
        t3.start();
    }
}
