package com.atguigu.java;

/**
 * 演示线程的死锁问题
 *
 * 1.死锁的理解：不同的线程分别占用对方需要的同步资源不放弃，都在等待对方放弃自己所
 * 需要的同步资源，就形成了线程的死锁
 *
 * 2.说明：
 * ①、出现死锁后，不会出现异常，不会出现提示，只是所有的线程都处于阻塞状态，无法继续
 * ②、我们使用同步时，要避免出现死锁
 *
 * 解决方法：
 * 专门的算法、原则
 * 尽量减少同步资源的定义
 * 尽量避免嵌套同步
 *
 * @author shkstart
 * @create 2021-04-06 15:44
 */
public class Thread06 {

    public static void main(String[] args) {

        StringBuffer s1 = new StringBuffer();
        StringBuffer s2 = new StringBuffer();

        //Thread的匿名子类的匿名线程
        new Thread(){
            @Override
            public void run() {
                synchronized (s1){

                    s1.append("a");
                    s2.append("1");
                //原因：第一个线程进入锁s1以后就会阻塞0.1秒，这时如果第二个线程同时
                    //进入了锁s2。而第一个线程需要s2的锁，第二个需要s1的锁
                    //两个线程都不解锁就形成了死锁
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (s2){

                        s1.append("b");
                        s2.append("2");

                        System.out.println(s1);
                        System.out.println(s2);
                    }

                }

            }
        }.start();

        //Runnable的匿名实现类的匿名线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (s2){
                    s1.append("c");
                    s2.append("3");

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (s1){
                        s1.append("d");
                        s2.append("4");
                        System.out.println(s1);
                        System.out.println(s2);
                    }
                }
            }
        }).start();

    }

}
