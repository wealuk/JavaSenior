package com.atguigu.java;

/**
 * 线程通信的应用：经典例题：生产者/消费者问题
 *
 * 生产者(Productor)将产品交给店员(Clerk)，而消费者(Customer)从店员处 取走产品，店员一次
 * 只能持有固定数量的产品(比如:20），如果生产者试图 生产更多的产品，店员会叫生产者停一下
 * ，如果店中有空位放产品了再通 知生产者继续生产；如果店中没有产品了，店员会告诉消费者等一
 * 下，如 果店中有产品了再通知消费者来取走产品。
 *
 * 分析：
 * 1.是否是多线程问题？是，生产者线程，消费者线程
 * 2.是否有共享数据？是，店员(或产品)
 * 3.如何解决线程的安全问题？同步机制，有三种方法
 * 4.是否涉及线程的通信？是
 *
 * notes：1.该题的核心就是有两种类型的线程--一种是消费一种是生产。而每一种又可以创建多个线程。
 * 而线程运行的核心就是同一对象Clerk。两种线程具体运行的操作(方法)都放在了同一个对象中，而这两个
 * 操作(方法)都是对共享数据productCount的操作，故该两个方法是并列的关系，需要将两个方法用同一个
 * 同步监视器(锁)就行了，这样一次只能有一个线程改变共享数据，也就是一次只能有一个线程进入一个方法。
 * 2.该题的思路与Thread08的思路类似，在Thread08中我们只需要一种类型的线程，这里需要两者类型的线程
 * (判断需要几种线程(类)，只需要知道有几种不同的操作共享数据的方法)。同时线程中处理共享数据的方法我们
 * 将其封装在一个新的类中，共享数据也在该类中。故线程只提供运行，核心操作(方法)单独放到一个类中，然后
 * 创建该类的一个对象，将其赋给每个线程的构造器。
 *
 * @author shkstart
 * @create 2021-04-07 19:57
 */
class Clerk{

    private int productCount = 0;
    //生产产品
    public synchronized void produceProduct() {
        if(productCount < 20){
            productCount++;//如果没有同步机制，就可能在该线程中productCount加了一下后还没输出就阻塞了
                            //恰巧另一个线程消费了一个，所以后面输出的是已经存在的产品编号
            System.out.println(Thread.currentThread().getName() + "：开始生产第" + productCount + "个产品");
            notify();

        }else{
            try {
                wait();//用于激活消费的线程
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
//**notes:两个方法的同步监视器都是同一个Clerk对象，故两个方法的锁是同一个，也就是执行到一个方法后就领了这把锁
//      而别的加了这把锁的方法就无法再次进入了  。也就是一个线程进入了一个方法后，其他的线程就无法进入其中任何
//      一个方法。总结：多个同步方法用的是同一个锁，被锁上的这些方法一次只能有一个线程进入一个方法，因为一个线程
//       进入方法后就把所以有该锁的结构锁上了，别的无法再调用。(只提供了一个Clerk的的对象)
    //消费产品
    public synchronized void consumeProduct() {
        if(productCount > 0){
            System.out.println(Thread.currentThread().getName() + ":开始消费第" + productCount + "个产品");
            productCount--;
            notify();//用于激活生产的线程
        }else{
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Producer extends Thread{//生产者
    private Clerk clerk;

    public Producer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        System.out.println(getName() + ":开始生产产品....");

        while(true){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.produceProduct();
        }
    }
}

class Consumer extends Thread{//消费者
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        System.out.println(getName() + ":开始消费产品....");

        while(true){
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.consumeProduct();
        }
    }
}

public class Thread10 {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        Producer p1 = new Producer(clerk);
        p1.setName("生产者1");

        Consumer c1 = new Consumer(clerk);
        c1.setName("消费者1");
        Consumer c2 = new Consumer(clerk);
        c2.setName("消费者2");

        p1.start();
        c1.start();
        c2.start();
    }
}
