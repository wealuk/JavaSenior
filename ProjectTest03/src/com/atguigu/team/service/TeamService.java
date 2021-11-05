package com.atguigu.team.service;

import com.atguigu.team.domain.Architect;
import com.atguigu.team.domain.Designer;
import com.atguigu.team.domain.Employee;
import com.atguigu.team.domain.Programmer;

/**
 * 
* @Description 关于开发团队成员的管理：添加、删除等。
* @author wealuk Email:2546867299@qq.com
* @version
* @date 2021年3月31日下午12:22:06
*
 */
public class TeamService {

	private static int counter = 1;//给memberId赋值使用
	private final int MAX_MEMBER = 5;//现在开发团队的人数
	private Programmer[] team = new Programmer[MAX_MEMBER];//保存开发团队成员
	private int total;//记录开发团队中实际的人数
	
	public TeamService(){
		super();
	}
	/**
	 * 
	 * @Description 获取开发团队中的所有成员
	 * @author wealuk
	 * @date 2021年3月31日下午12:32:24
	 * @return
	 */
	public Programmer[] getTeam(){
		Programmer[] team = new Programmer[total];
		for(int i = 0;i < team.length;i++){
			team[i] = this.team[i];
		}
		return team;
	}
	/**
	 * 
	 * @Description 关于开发团队成员的管理：添加、删除等
	 * @author wealuk
	 * @date 2021年3月31日下午12:49:11
	 * @param e
	 */
	public void addMember(Employee e) throws TeamException{
//		成员已满，无法添加
		if(total >= MAX_MEMBER){
			throw new TeamException("成员已满，无法添加");
		}
//		该成员不是开发人员，无法添加
		if(!(e instanceof Programmer)){
			throw new TeamException("该成员不是开发人员，无法添加");
		}
//		该员工已在本开发团队中
		if(isExist(e)){
			throw new TeamException("该员工已在本开发团队中");
		}
//		该员工已是某团队成员 
//		该员工正在休假，无法添加
		Programmer p = (Programmer)e;//一定不会出现ClassCastException(前面的if已经过滤了)
//		if("BUSY".equals(p.getStatus().getName())){
//			throw new TeamException("该员工已是某团队员工");
//		}else if("VACATION".equals(p.getStatus().getName())){
//			throw new TeamException("该员工正在休假，无法添加");
//		}
		switch (p.getStatus()){//byte\short\char\int\String\枚举类对象
			case BUSY://直接用对象，而不是case Status.BUSY
				throw new TeamException("该员工已是某团队成员");
			case VOCATION:
				throw new TeamException("该员工正在休假，无法添加。");
		}



//		团队中至多只能有一名架构师
//		团队中至多只能有两名设计师
//		团队中至多只能有三名程序员
		
		//获取team已有成员中架构师，设计师，程序员的人数
		int numOfArch = 0,numOfDes = 0,numOfPro = 0;
		for(int i = 0;i < total;i++){
			if(team[i] instanceof Architect){
				numOfArch++;
			}else if(team[i] instanceof Designer){
				numOfDes++;
			}else if(team[i] instanceof Programmer){
				numOfPro++;
			}
		}
		
		if(p instanceof Architect){
			if(numOfArch >= 1){
				throw new TeamException("团队中至多只有一名架构师");
			}
		}else if(p instanceof Designer){
			if(numOfDes >= 2){
				throw new TeamException("团队中至多有两名设计师");
			}
		}else if(p instanceof Programmer){
			if(numOfPro >= 3){
				throw new TeamException("团队中至多有三名程序员");
			}
		}
		//易犯错的点
//		if(p instanceof Architect && numOfArch >= 1){
//				throw new TeamException("团队中至多只有一名架构师");
//			}else if(p instanceof Designer && numOfDes >= 2){
//				throw new TeamException("团队中至多有两名设计师");
//			}else if(p instanceof Programmer && numOfPro >= 3){
//				throw new TeamException("团队中至多有三名程序员");
//			}这样写的话，如果第一个if没问题，那么还会到第二个if进行判断
		//也就是如果已经有了两个设计师，再添加一个架构师理论上是可以成功的。但是用上面的代码
		//在第一个if上过了，但符合第二个if会抛出设计师的异常。应该像正确的那样，先区分类型，
		//再在类型里面进行个数的判断。
		
		
		//将p（或e）添加到现有的team中
		team[total++] = p;
		//p的属性赋值
		p.setStatus(Status.BUSY);
		p.setMemberId(counter++);

	}
	private boolean isExist(Employee e) {
		for(int i = 0;i < total;i++){
			if(team[i].getId() == e.getId()){
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * @Description 从团队在删除成员
	 * @author wealuk
	 * @date 2021年3月31日下午6:26:23
	 * @param memberId
	 * @throws TeamException 
	 */
	public void removeMember(int memberId) throws TeamException{
		int i = 0;
		for(;i < total;i++){
			if(team[i].getMemberId() == memberId){
				team[i].setStatus(Status.FREE);
				break;
			}
		}
		//未找到memberId的情况
		if(i == total){
			throw new TeamException("找不到指定memberId的员工，删除失败");
		}
		
		
		for(int j = i + 1;j < total;j++){
			team[j - 1] = team[j];
		}
		
		//写法一：
//		team[total - 1] = null;
//		total--;
		//写法二：
		team[--total] = null;
	}
}
