package com.atguigu.java;

/**
 * 线程通信的例子：使用两个线程打印 1-100。线程1, 线程2 交替打印(多个线程的交互)
 *
 * 涉及到的三个方法：
 * wait():一旦执行此方法，当前线程就进入阻塞状态，并释放同步监视器。
 * notify():一旦执行此方法，就会唤醒被wait的一个线程。如果有多个线程被wait，就唤醒优先级高的那个。
 * notifyAll():旦执行此方法，就会唤醒所有被wait的一个线程。
 *
 * 说明：
 * 1.wait()、notify()、notifyAll()三个方法必须使用在同步代码块或同步方法中
 * 2.wait()、notify()、notifyAll()三个方法的调用者必须是同步代码块或同步方法中的同步监视器(所以三个方法必须声明在步代码块或同步方法中)
 *      否则，会出现IllegalMonitorStateException异常
 * 3.wait()、notify()、notifyAll()三个方法是定义在java.lang.Object类中(所有对象都可以作为监视器，故都有这三个方法，都是继承于Object)
 *
 * notes：1、2也就是说同步监视器是this，那么这些方法就可以通过(this.)notify来调用；如果同步监视器是obj，
 * 那么这些方法就必须用obj.notify方式来调用。如果同步监视器是obj，而是以(this).notify调用的话，就
 * 会出现IllegalMonitorStateException异常。也是因为方法的调用者是同步监视器，所以这些方法必须使用
 * 在同步代码块或同步方法中
 *
 *
 * 面试题：sleep()和wait()的异同？
 * 1.相同点：一旦执行方法，都可以使得当前的线程进入阻塞状态
 * 2.不同点：①两个方法声明的位置不同：Thread类中声明sleep()，Object类中声明wait()
 *          ②调用的要求不同：sleep()可以在任何需要的场景下调用，wait()必须使用在同步代码块或同步方法中
 *          ③关于是否是否同步监视器：如果两个方法都使用在同步代码块或同步方法中，sleep()不会释放锁，wait()会释放锁。
 *
 * @author shkstart
 * @create 2021-04-07 17:32
 */
class Number implements Runnable{
    private int number = 1;
    private Object obj = new Object();

    @Override
    public void run() {

        while(true){
            //同步监视器是否能用this就看该类的对象是否是作为共享数据所出现的。Number的对象是唯一且共享的，故可以使用this
            synchronized (this) {
//              synchronized (obj){//如果obj作为同步监视器，那么后面的三个方法就需要以obj.方法的形式调用
                notify();//notes：省略了this，调用者是同步监视器(同步监视器是this时就使用this.notify,如果是obj就需要使用obj.notify)
                            //如果同步监视器是obj，使用的是(this.)notify，那么就会出错。正是因为这些方法是同步监视器调用的
                            //故这三个方法必须在同步代码块或同步方法中使用

                if(number <= 100){

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + ":" + number);
                    number++;

                    try {
                        //使得调用如下wait()方法的线程进入阻塞状态 (此时会释放锁)
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    break;
                }
            }

        }
    }
}

public class Thread09 {

    public static void main(String[] args) {
        Number number = new Number();

        Thread t1 = new Thread(number);
        Thread t2 = new Thread(number);

        t1.setName("线程1");
        t2.setName("线程2");

        t1.start();
        t2.start();
    }
}
