package com.atguigu.java;

/**
 * 银行有一个账户。
 * 有两个储户分别向同一个账户存3000元，每次存1000，存3次。每次存完打 印账户余额。
 *
 * 分析：
 * 1.是否是多线程问题？是，两个储户线程
 * 2.是否有共享数据？有，账户
 * 3.是否有线程安全问题？有
 * 4.需要考虑如何解决线程安全问题？同步机制：有三种方式
 *
 *
 * @author shkstart
 * @create 2021-04-06 20:35
 */
class Account{
    private double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    //存钱
    public synchronized void deposit(double amt){
        //**前面讲了遇到继承Thread时，慎用this，但就像该题一样特殊情况可以用
        // this的同步监视器。原因在于前面虽然创建了多个线程的对象，但每个线程
        //所执行的run方法都是独立的，内容与别的线程无关故，只能使用类的锁
        // 来确保是共享的锁。但在这里，run中所执行的方法与Account有关，而
        //Account只有一个对象且被几个线程所共享，操作的都是同一个对象，故可以
        //在这个共享的对象的方法用this的锁

        //总结：能否使用this，就看当前方法的对象是否是多个线程所共享且唯一的。这里的方法属于Account，Account对象
        //是多个线程所共享的，是操作的同一个对象。所以该方法用this作为锁是正确的，而不要认为继承的方法就一定要用类
        if(amt > 0){
            balance += amt;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "存钱成功，余额为：" + balance);
        }
    }
}

class Customer extends Thread{
    private Account acct;

    public Customer(Account acct) {
        this.acct = acct;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            acct.deposit(1000);
        }

    }
}

public class Thread08 {
    public static void main(String[] args) {
        Account acct = new Account(0);
        Customer c1 = new Customer(acct);
        Customer c2 = new Customer(acct);

        c1.setName("甲");
        c2.setName("乙");

        c1.start();
        c2.start();
    }
}
