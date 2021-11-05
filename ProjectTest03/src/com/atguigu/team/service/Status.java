package com.atguigu.team.service;
/**
 * 
* @Description 表示员工的状态
* @author wealuk Email:2546867299@qq.com
* @version
* @date 2021年3月30日下午12:38:16
*
 */
//public class Status {
////类似于单例模式，不让外部创建对象，只在内部创建一个对象提供给外部使用。
////在这里则是给该类提供了三个对象，然后不让外部有别的对象创建而只能使用这三个。与后面的枚举有关
//	private final String NAME;
//	private Status(String name){
//		this.NAME = name;
//	}
//
//	public static final Status FREE = new Status("FREE");
//	public static final Status BUSY  = new Status("BUSY");
//	public static final Status VACATION = new Status("VACATION");
//
//	public String getName() {
//		return NAME;
//	}
//	@Override
//		public String toString() {
//			return NAME;
//		}
//}

public enum Status{
	FREE,BUSY,VOCATION;
	//这样就好了，用枚举极其简单。无需声明属性就无需构造器，同时父类Enum有重写的toString方法，直接可以输出对象的名称
}
