package com.atguigu.team.domain;

import com.atguigu.team.service.Status;

public class Programmer extends Employee{

	private int memberId;//开发团队中的id
	private Status status = Status.FREE;
	private Equipment equipment;
	public Programmer() {
		super();
	}
	public Programmer(int id, String name, int age, double salary, Equipment equipment) {
		super(id, name, age, salary);
		this.equipment = equipment;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Equipment getEquipment() {
		return equipment;
	}
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	//3	李彦宏	23	7000.0	程序员	FREE			戴尔(NEC17寸)
	@Override
	public String toString() {
		return getDetails() + "\t程序员\t" + status + "\t\t\t" + equipment.getDescription();
	}
	//1/3	李彦宏	23	7000.0	程序员
	public String getDetailsForTeam(){
		return memberId + "/" + getId() + "\t" + getName() + "\t" + getAge() + "\t" + getSalary() + "\t程序员";
	}
}
