package com.atguigu.team.service;
/**
 * 
* @Description 自定义异常类
* @author wealuk Email:2546867299@qq.com
* @version
* @date 2021年3月30日下午6:24:48
*
 */
public class TeamException extends Exception{

	static final long serialVersionUID = -33875169124229948L;

	public TeamException(){
		super();
	}
	public TeamException(String msg){
		super(msg);
	}
}
