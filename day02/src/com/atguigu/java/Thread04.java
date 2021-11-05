package com.atguigu.java;

/**
 * 使用同步方法解决继承Thread类的方式中的线程安全问题
 *
 *
 *
 * @author shkstart
 * @create 2021-04-06 11:29
 */
class Window3 extends Thread{
    private static int ticket = 100;

    public void run() {
        while (true) {
            show();

        }
    }
    public static synchronized void show(){//同步监视器：Window3.class
    //    public synchronized void show(){//同步监视器：this(w1,w2,w3).此种解决方式是错误的，没有解决安全问题
        if (ticket > 0) {

            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + ":卖票，票号为：" + ticket);
            ticket--;
        }

    }
}
public class Thread04 {
    public static void main(String[] args) {
        Window3 w1 = new Window3();
        Window3 w2 = new Window3();
        Window3 w3 = new Window3();

        w1.setName("窗口一");
        w2.setName("窗口二");
        w3.setName("窗口三");

        w1.start();
        w2.start();
        w3.start();
    }
}
