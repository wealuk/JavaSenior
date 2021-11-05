package com.atguigu.java;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理的举例
 *
 * 流程总结：1.创建接口和被代理类(这两者与静态代理区别不大，主要区别就是代理类的问题：1.如何动态创建对应代理类。2.如何在代理类调用方法a时，能调用被代理类的方法a)
 *      2.在创建代理类对象的类ProxyFactory中，提供获取代理类对象的静态方法，比如public static Object
 *      getProxyInstance(Object obj) {}//obj:被代理类的对象
 *
 *      3.创建InvocationHandler的实现类，重写public Object invoke(Object proxy, Method method, Object[] args)
 *      throws Throwable方法，并在该类中定义属性Object obj，使用被代理类的对象进行赋值(提供赋值方法或者在构造器中提供赋值方法
 *      ，必须确保在调用到invoke方法时，有被代理类的对象可以用)
 *
 *      注：①.InvocationHandler的实现类的对象handler用于赋给第4步Proxy.newProxyInstance()的第三个参数，作用是代理类对象调用
 *      方法a的时候，就会默认跳转到handler的invoke()方法。所以将被代理类要执行的方法a的功能就声明在invoke()中也就解决掉了
 *      第二个问题。代理类调用方法a其实是调用的handler的方法invoke(),而在invoke()中再定义被代理类的方法a。所以整个步骤就是调用的被代理类的方法a。
 *         ②关于invoke()中的参数讲解以及如何在invoke()中定义被代理类的方法a。method是反射拿到的代理类的方法a，而args则是
 *         代理类调用方法a时给的实参。因为拿到了方法a method，所以我们可以通过method(被代理类对象,方法a需要的实参),在invoke()中
 *         就能调用代理类的方法a。而这样，我们就需要被代理类的对象，也就是为什么我们需要在InvocationHandler的实现类中定义
 *         一个属性Object obj，然后提供对其赋值的方法(用被代理类进行赋值)。
 *
 *      4.回到第2步，在getProxyInstance方法中，调用Proxy类的静态方法newProxyInstance(被代理类对象obj的类加载器,obj的接口,InvocationHandler的实现类的对象)，
 *      比如：Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), handler);
 *      因为需要handler对象，所以需要在return返回newProxyInstance()创建的代理类对象之前需要创建InvocationHandler的实现类
 *      的对象handler，并且给handler的属性obj赋值，用this.obj = obj;(第一个obj为属性，第二个为被代理类对象)
 *
 *      注：①newProxyInstance()的前两个参数是被代理类的类加载器和实现的接口，这两个接口其实就解决了问题，创建了对应的代理类。
 *      (因为要创建与被代理类对应的代理类对象，我们需要知道其接口并实现，然后需要拿到类的加载器进行代理类对象的创建)
 *         ②第三个参数handler用于解决问题2，代理类对象调用方法时实则是调的handler中invoke()方法，invoke中利用获取的
 *         method和参数，再创建被代理类的对象，就可以调用被代理类的方法(method.invoke(obj,args))。具体见上一个注解
 *
 *      5.到这一步基本了解原理了，原理在两个注解中
 *
 *      6.再回到第三步，完成对问题2的解决。我们代理类调用方法a,那么在invoke()方法中，就拿到了代理类的方法a，而这也是
 *      被代理类在invoke()中要调用的方法a。所以我们通过method.invoke(obj,args)即可调用被代理类的方法a，然后return就行了
 *      ，其在obj为用被代理类的属性，而args是拿到的代理类调用的方法a中的参数(args为数组)，这里拿来放到被代理类的方法a中作参数。
 *      如此便实现了调用代理类的方法a而调用的其实是被代理类的方法a。
 *      核心就是被代理的方法a放到InvocationHandler的实现类的invoke方法中，调用代理类的方法其实就是调用invoke()方法，所以
 *      我们可以在invoke()中加一些准备和首位操作。
 *
 * @author shkstart
 * @create 2021-05-20 15:49
 */

interface Human{
    String getBelief();

    void eat(String food);
}

//被代理类
class SuperMan implements Human{

    @Override
    public String getBelief() {
        return "i believe i can fly!";
    }

    @Override
    public void eat(String food) {
        System.out.println("我喜欢吃" + food);
    }
}

/*
要想实现动态代理，需要解决的问题？
问题1：如何根据加载到内存中的被代理类，动态的创建一个代理类及其对象。
问题2：当通过代理类的对象调用方法a时，如何动态的去调用被代理类中的同名方法a。
 */
class ProxyFactory{
    //调用此方法，返回一个代理类的对象。解决问题1.
    public static Object getProxyInstance(Object obj) {//obj:被代理类的对象
        MyInvocationHandler handler = new MyInvocationHandler();

        handler.bind(obj);

        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), handler);
        //handler(InvocationHandler实现类的对象)用于解决问题2。
    }
}

class MyInvocationHandler implements InvocationHandler{

    private Object obj;//需要使用被代理类的对象进行赋值

    public void bind(Object obj){
        this.obj = obj;
    }

    //当我们通过代理类的对象，调用方法a时，就会自动的调用如下的方法：invoke()
    //将被代理类要执行的方法a的功能就声明在invoke()中
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //method:即为代理类对象调用的方法，此方法也就作为了被代理类对象要调用的方法。
        //obj：被代理类的对象
        Object returnValue = method.invoke(obj,args);
        //上述方法的返回值就作为当前类中的invoke()的返回值
        return returnValue;
    }
}

public class ReflectionTest09 {
    public static void main(String[] args) {
        SuperMan superMan = new SuperMan();
        //proxyInstance:代理类的对象
        Human proxyInstance = (Human) ProxyFactory.getProxyInstance(superMan);
        //当通过代理类对象调用方法时，会自动的调用被代理类中同名的方法
        System.out.println(proxyInstance.getBelief());
        proxyInstance.eat("四川麻辣烫");

        System.out.println("**********");

        //发现只需要被代理类及其接口，就可以动态的创建其代理类
        NikeClothFactory nikeClothFactory = new NikeClothFactory();

        ClothFactory proxyClothFactory = (ClothFactory) ProxyFactory.getProxyInstance(nikeClothFactory);

        proxyClothFactory.produceCloth();
    }

}
