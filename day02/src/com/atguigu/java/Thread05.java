package com.atguigu.java;

/**
 * 使用同步机制将单例模式中的懒汉式改写为线程安全的
 *
 *
 *
 * @author shkstart
 * @create 2021-04-06 12:15
 */
public class Thread05 {

}
class Bank{
    private Bank(){}

    private static Bank instance = null;

    public static Bank getInstance(){
        //方式一：效率较差(有一个线程完成操作后，instance不为null了，但后面还是必须一个一个经过锁)
//        synchronized (Bank.class) {当然可以不用同步代码块，直接用同步方法也一样的，锁也是Bank类
//            if(instance == null){
//
//                instance = new Bank();
//            }
//        }
//        return instance;
        //方式二：效率更高(就前面几个并列的线程要麻烦点多经过一个判断，但有一个完成完成后，后面线程就
        // 不需要经过锁了，可以直接返回instance)
        if(instance == null){

            synchronized (Bank.class) {
                if(instance == null){

                    instance = new Bank();
                }
            }

        }
        return instance;
    }
}
