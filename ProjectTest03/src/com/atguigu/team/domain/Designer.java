package com.atguigu.team.domain;

public class Designer extends Programmer{

	private double bonus;//奖金

	public Designer() {
		super();
	}

	public Designer(int id, String name, int age, double salary, Equipment equipment, double bonus) {
		super(id, name, age, salary, equipment);
		this.bonus = bonus;
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}
	//5	雷军	28	10000.0	设计师	FREE	5000.0		激光(佳能 2900)
	@Override
	public String toString() {
		return getDetails() + "\t设计师\t" + getStatus() + "\t" + bonus + "\t\t" + getEquipment().getDescription();
	}
	//2/5	雷军	28	10000.0	设计师	5000.0
	public String getDetailsForTeam(){
		//getMemberId() + "/" + getId() + "\t" + getName() + "\t" + getAge() + "\t" + getSalary()反复用到，故可以
		//封装到一个方法里面，使代码看起来更简洁
		return getMemberId() + "/" + getId() + "\t" + getName() + "\t" + getAge() + "\t" + getSalary() + "\t设计师\t" + getBonus();
	}
	
}
