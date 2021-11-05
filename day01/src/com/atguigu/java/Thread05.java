package com.atguigu.java;

/**
 * 创建多线程的方式二：实现Runnable接口
 * 1.创建一个实现了Runnable接口的类
 * 2.实现类去实现Runnable中的抽象方法：run()
 * 3.创建实现类的对象
 * 4.将此对象作为参数传递到Thread类的构造器中，创建Thread类的对象
 * 5.通过Thread类的对象调用start()
 *
 * notes：Thread源码中有Runnable target的属性，而该方式中创建的也是Thread的对象，故开启的
 * 也是Thread的线程。而Thread的start方法调用的run方法中表示如果target不是null，那么就调用
 * target的run方法。我们创建了Runnable实现类并且将其作为实参赋给了Thread的初始化target
 * 的构造器。由于是将实现类的地址值给了target，并且target也不是null了，所以就会调用target
 * 也就是实现类的run方法。
 * 总结：创建多个线程是创建多个Thread对象(不是实现类)，而线程所执行的内容是创建Thread(线程)
 * 对象时所赋给构造器的Runnable实现类中的run方法。比如在卖票问题中，我们只创建了一个实现类的
 * 对象，但创建了三个Thread对象，也就是三个线程。三个线程所执行的run方法是同一个实现类对象的
 * 也就是说是同一个地址值，所以属性为他们所共有，ticket无需加static，因为三个线程所执行的是
 * 同一个对象，改变的也是同一个属性(地址值相同)。而在继承Thread的方式中，是创建三个子类的对象，
 * 不加static则每个子类都有自己的一套ticket，只有加了static才行
 *
 * 实现Runnable接口的匿名方式：
 *   new Thread(new Runnable(){}).start()  new Thread().start()是启动线程，
 *   new Runnable(){}作为实参赋给构造器的形参，该东西就是实现类
 *
 * @author shkstart
 * @create 2021-04-03 22:36
 */
//1.创建一个实现了Runnable接口的类
class MThread implements  Runnable{
    @Override
    public void run() {
        //2.实现类去实现Runnable中的抽象方法：run()
        for (int i = 0; i < 100; i++) {
            if(i % 2 == 0){
                System.out.println(Thread.currentThread().getName() + i);
                //由于不是继承的Thread类，所以不能直接使用(this.)getName，必须写Thread.currentThread().getName()
            }
        }
    }
}

public class Thread05 {
    public static void main(String[] args) {
        //3.创建实现类的对象
        MThread mThread = new MThread();
        //4.将此对象作为参数传递到Thread类的构造器中，创建Thread类的对象
        Thread t1 = new Thread(mThread);
            //notes：*实参赋给了Thread的属性Runnable target,而Thread中的start方法中表示如果
            //属性target不是null，那么就调用target的run()。由于形参和实参共用的同一个
            //地址值，调用的target的run方法就是mThread的run方法，也就是实现类中的run
        t1.setName("线程一");
        //5.通过Thread类的对象调用start():①启动线程 ②调用当前线程的run() -->调用了Runnable类型的target的run()
        t1.start();

        //再启动一个线程，遍历100以内的偶数
        Thread t2 = new Thread(mThread);
        t2.setName("线程二");
        t2.start();

    }
}
