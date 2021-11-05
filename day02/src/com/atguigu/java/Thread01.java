package com.atguigu.java;

/**
 * 使用同步代码块解决实现Runnable接口的方式的线程安全问题
 * 例子：创建三个窗口卖票，总票数为100张。使用实现Runnable接口的方式
 *
 * 1.问题：卖票的过程中，出现了重票、错票 --->出现了线程的安全问题 notes：只要是多个线程操作一个共享数据，那么就有线程安全问题
 * 2.问题出现的原因：当某个线程操作车票的过程中，尚未操作完成时，其他的线程参与进来，也操作了车票
 * 3.如何解决：当一个线程a在操作ticket的时候，其他线程不能参与进来。直到线程a操作完ticket时，其他
 *          线程才可以开始操作ticket。这种情况即使线程a出现阻塞，也不能被改变
 * 4.在Java中，我们通过同步机制，来解决线程的安全问题。
 *
 *      方式一：同步代码块
 *          synchronized(同步监视器){
 *              //需要被同步的代码块
 *          }
 *       说明：1.操作共享数据的代码，即为需要被同步的代码-->不能包含代码多了，也不能包含代码少了
 *       notes：包少了，那么没包的部分阻塞了，别的线程又同时来到这里，那么又会出现重票、错票的情况。
 *              包多了，那么就会显得效率及其低下，甚至出问题，比如下面把while保住，那么就会直到
 *              一个窗口把票卖完才会让别的线程进入while，但此时票已经被一个窗口卖完啦
 *            2.共享数据：多个线程共同操作的变量。比如ticket就是共享数据(没有共享数据就没有线程安全问题)
 *            3.同步监视器，俗称：锁。任何一个类的对象，都可以充当锁
 *                要求：多个线程必须要共用同一把锁
 *
 *              补充：在实现Runnable接口创建多线程的方式中，我们可以考虑使用this充当同步监视器
 *              在继承Thread类创建多线程的方式中，慎用this充当同步监视器，考虑使用当前类充当同步监视器。
 *              notes：实现Runnable接口的锁也可以用当前类，所以在不清楚的情况下用类来表示同步监视器是决定没问题的
 *                     并且继承Thread类的也可以用到this的锁，如Thread08，具体问题具体分析
 *
 *
 *       方式二：同步方法。
 *          如果操作共享数据的代码完整的声明在一个方法中，我们不妨将此方法声明同步的
 *          关于同步方法的总结：
 *                   1.同步方法仍然涉及到同步监视器，只是不需要我们显式的声明
 *                   2.非静态的同步方法，同步监视器是：this(用于实现Runnable这种共用同一个对象的)
 *                     静态的同步方法，同步监视器是：当前类本身(用于继承Thread类这种有多个对象的，但是同一个类的)
 *            注意：如果一个类中有多个方法都操作共享数据，那么每个方法加上synchronized就行了。这样每次只会
 *                  有一个线程进入一个方法。之后就锁上了。(此时同步监视器是同一个this对象)具体见Thread10
 *                  (这里告诉我们操作共享数据不一定只在一个方法中)
 *
 *
 * 5.同步的方式，解决了线程的安全问题。---好处
 * 操作同步代码时，只能有一个线程参与，其他线程等待。相当于是一个单线程的过程，效率低。---局限性
 *
 * @author shkstart
 * @create 2021-04-03 23:51
 */
class Window1 implements Runnable{
    private int ticket = 100;
    Object obj = new Object();

    @Override
    public void run() {
//        Object obj = new Object();不能把锁放在这里，这样的话每个线程创建了自己的锁(obj是不同的)
        while(true){            //也就不会像属性那样，是唯一且共享的数据。
//           方式二： synchronized(obj) {//这里更不能new 对象了，这样循环一个就创建一个锁，更离谱了
            synchronized(this){//方式一：this指w1，所以是同一个唯一对象。就无需再去new一个对象
                            //把this换成Window1.class也可以
                if (ticket > 0) {

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + ":卖票，票号为：" + ticket);
                    ticket--;
                } else {
                    break;
                }
            }
        }

    }
}

public class Thread01 {
    public static void main(String[] args) {
        Window1 w1 = new Window1();

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
