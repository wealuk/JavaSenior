package com.atguigu.team.domain;

public class Printer implements Equipment{

	private String name;//机器名字
	private String type;//型号
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Printer(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}
	public Printer() {
		super();
	}
	@Override
	public String getDescription() {
		return name + "(" + type + ")";
	}
	
	
}
