package com.atguigu.java;

/**
 *
 * 使用同步代码块解决继承Thread类的方式的线程安全问题
 *
 * 例子：创建三个窗口卖票，总票数为100张。使用继承Thread类的方式
 *
 *说明：在继承Thread类创建多线程的方式中，慎用this充当同步监视器，考虑使用当前类充当同步监视器。
 *
 * @author shkstart
 * @create 2021-04-03 18:46
 */
class Window extends Thread{
    private static  int ticket = 100;
    private static Object obj = new Object();//创建三个不同的对象，也就是三个obj是独立的、不同的，
                                    //故不加static就是独立的三个锁，只有加static就是共享的一把锁
    public void run() {
        while (true) {
            //正确的
//            synchronized (obj) {
            //错误的方式：this代表w1,w2,w3三个对象
//            synchronized (this){

            synchronized (Window.class){//简洁的方法：类也是对象。Class clazz = Window.class
                if (ticket > 0) {       //其中Window.class只会加载一次

                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(getName() + ":卖票，票号为：" + ticket);
                    ticket--;
                } else {
                    break;
                }

            }
        }
    }
}

public class Thread02 {
    public static void main(String[] args) {
        Window w1 = new Window();
        Window w2 = new Window();
        Window w3 = new Window();

        w1.setName("窗口一");
        w2.setName("窗口二");
        w3.setName("窗口三");

        w1.start();
        w2.start();
        w3.start();
    }
}
