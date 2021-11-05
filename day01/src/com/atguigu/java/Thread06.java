package com.atguigu.java;

/**
 * 例子：创建三个窗口卖票，总票数为100张。使用实现Runnable的方式
 *存在线程的安全问题，待解决
 *
 *
 * 比较创建线程的两种方式。
 * 开发中：优先选择实现Runnable接口的方式
 * 原因：1.实现的方式没有类的单继承性的局限性
 *      2.实现的方式更适合来处理多个线程有共享数据的情况
 *
 *  联系：Thread也是实现了Runnable
 *  相同点：两种方式都需要重写run(),将线程要执行的逻辑说明在run()
 *          目前两种方式，想要启动线程，都是调用的Thread类中的start().
 *
 * @author shkstart
 * @create 2021-04-03 23:51
 */
class Window1 implements Runnable{
    private int ticket = 100;//这里无需加static仍是卖的100张不是300(也就是没问题)，三个线程是Thread对象
                            //所执行的内容是同一个对象(地址值相同)
    @Override
    public void run() {
        while(true){
            if(ticket > 0){
                System.out.println(Thread.currentThread().getName() + ":卖票，票号为：" + ticket);
                ticket--;
            }else{
                break;
            }
        }

    }
}
public class Thread06 {
    public static void main(String[] args) {
        Window1 w1 = new Window1();

        Thread t1 = new Thread(w1);//w1就相当于三个线程的共享数据
        Thread t2 = new Thread(w1);
        Thread t3 = new Thread(w1);//执行的内容相同故可以将w1赋给三个线程。如果内容不同，那么就要创建不同
                                    //的实现类对象再赋给一个线程
        t1.setName("窗口一");
        t2.setName("窗口二");
        t3.setName("窗口三");

        t1.start();
        t2.start();
        t3.start();
    }
}
