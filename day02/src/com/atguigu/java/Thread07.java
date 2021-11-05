package com.atguigu.java;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 解决线程安全问题的方式三：Lock锁 --- JDK5.0新增
 * Lock是一个接口，而ReentrantLock是Lock的一个实现类，一般创建ReentrantLock的对象lock
 * 以此调用lock.lock(),lock.unlock()
 *
 *
 * 1.面试题：synchronized 与 lock的异同？
 *      相同：二者都可以解决线程安全问题
 *      不同：synchronized机制在执行完相应的同步代码以后，自动的释放同步监视器
 *            lock需要手动的启动同步(lock()),同时结束同步也需要手动的实现(unlock())
 *
 * 2.优先使用顺序：
 * Lock  同步代码块（已经进入了方法体，分配了相应资源）  同步方法
 * （在方法体之外）
 *
 * 面试题：如何解决线程安全问题？有几种方式：同步代码块、同步方法、lock
 * @author shkstart
 * @create 2021-04-06 18:19
 */
class Window4 implements Runnable{
    private int ticket = 100;
//    private ReentrantLock lock = new ReentrantLock(true);//true则是公平的，先到先得
            //但是拿完就要让给别的线程，不会让一个线程一直抢。false则可以一个线程一直抢到

    private ReentrantLock lock = new ReentrantLock();
    //注：如果使用的继承Thread的方式，那么lock就需要加上static，依然要保证多个线程的lock是共享唯一的

    @Override
    public void run() {
        while(true){
            try{
                //2.调用锁定方法：lock()
                lock.lock();

                if(ticket > 0){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + ":售票，票号为:" + ticket);
                    ticket--;
                }else{
                    break;
                }
            }finally {
                //3.调用解锁方法：unlock()
                lock.unlock();
            }

        }

    }
}
public class Thread07 {
    public static void main(String[] args) {
        Window4 w1 = new Window4();

        Thread t1 = new Thread(w1);
        Thread t2 = new Thread(w1);
        Thread t3 = new Thread(w1);

        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");

        t1.start();
        t2.start();
        t3.start();
    }
}
