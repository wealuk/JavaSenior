package com.atguigu.team.service;

import com.atguigu.team.domain.Architect;
import com.atguigu.team.domain.Designer;
import com.atguigu.team.domain.Employee;
import com.atguigu.team.domain.Equipment;
import com.atguigu.team.domain.NoteBook;
import com.atguigu.team.domain.PC;
import com.atguigu.team.domain.Printer;
import com.atguigu.team.domain.Programmer;

import static com.atguigu.team.service.Data.*;//可以直接调用静态结构了

import org.junit.Test;

/**
 * 
* @Description 负责将Data中的数据封装到Employee[]数组中，同时提供相关操作Employee[]的方法。
* @author wealuk Email:2546867299@qq.com
* @version
* @date 2021年3月30日下午1:21:11
*
 */
public class NameListService {

	private Employee[] employees;
	
	/**
	 * 给employees及数值元素进行初始化
	 */
	public NameListService(){
//		1.根据项目提供的Data类构建相应大小的employees数组
//		2.再根据Data类中的数据构建不同的对象，包括Employee、Programmer、Designer和Architect对象，以及相关联的Equipment子类的对象
//		3.将对象存于数组中
		employees = new Employee[EMPLOYEES.length];//Data.EMPLOYEES.length导入line5后Data.
									//可以省略。后面所有的静态方法、属性可以直接用而不需要类.了
		for(int i = 0;i < employees.length;i++){
			//获取员工的类型
			int type = Integer.parseInt(EMPLOYEES[i][0]);
			
			//获取Employee的四个基本信息
			int id = Integer.parseInt(EMPLOYEES[i][1]);
			String name = EMPLOYEES[i][2];
			int age = Integer.parseInt(EMPLOYEES[i][3]);
			double salary = Double.parseDouble(EMPLOYEES[i][4]);
			
			Equipment equipment;//放里面的话不同的子类都要有Equipment，就要声明新的Equipment
								//类型，所以就要起不同的变量名，过于麻烦，所以声明在外面。
			double bonus;
			int stock;
			switch(type){			
			case EMPLOYEE:
				employees[i] = new Employee(id,name,age,salary);
				break;
			case PROGRAMMER:
				equipment = creatEquipment(i);//获取其设备
				employees[i] = new Programmer(id, name, age, salary, equipment);
				break;
			case DESIGNER:
				equipment = creatEquipment(i);
				bonus = Double.parseDouble(EMPLOYEES[i][5]);
				employees[i] = new Designer(id, name, age, salary, equipment, bonus);
				break;
			case ARCHITECT:
				equipment = creatEquipment(i);
				bonus = Double.parseDouble(EMPLOYEES[i][5]);
				stock = Integer.parseInt(EMPLOYEES[i][6]);
				employees[i] = new Architect(id, name, age, salary, equipment, bonus, stock);
				break;
			}
		}

	}
	/**
	 * 
	 * @Description 获取指定index上的员工的设备
	 * @author wealuk
	 * @date 2021年3月30日下午2:19:37
	 * @param i
	 * @return
	 */
	private Equipment creatEquipment(int index) {
		int key = Integer.parseInt(EQUIPMENTS[index][0]);
		
		String modelOrName = EQUIPMENTS[index][1];
		switch(key){
		case PC://21
			String display = EQUIPMENTS[index][2];
			return new PC(modelOrName, display);
		case NOTEBOOK:
			double price = Double.parseDouble(EQUIPMENTS[index][2]);
			return new NoteBook(modelOrName, price);
		case PRINTER:
			String type = EQUIPMENTS[index][2];
			return new Printer(modelOrName, type);
		}
		return null;
	}
	/**
	 * 
	 * @Description 获取当前所有员工
	 * @author wealuk
	 * @date 2021年3月30日下午6:15:58
	 * @return
	 */
	public Employee[] getAllEmployees(){
		return employees;
	}
	/**
	 * 
	 * @Description 获取指定ID的员工对象
	 * @author wealuk
	 * @date 2021年3月30日下午6:16:40
	 * @param id
	 * @return
	 * @throws TeamException 
	 */
	public Employee getEmployee(int id) throws TeamException{
		for(int i = 0;i < employees.length;i++){
			if(employees[i].getId() == id){
				return employees[i];
			}
		}
		throw new TeamException("找不到指定员工");
	}
	@Test
	public void testGetAllEmployee(){
		NameListService service = new NameListService();
		Employee[] employees = service.getAllEmployees();
		for(int i = 0;i < employees.length;i++){
			System.out.println(employees[i]);
		}
	}
	@Test
	public void testGetEmployee(){
		NameListService service = new NameListService();
		int id = 1;
		id = 10;
		try{
			Employee employee = service.getEmployee(1);
			System.out.println(employee);
		}catch(TeamException e){
			System.out.println(e.getMessage());
		}
	}
}
