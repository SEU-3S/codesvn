package com.klspta.model.personInfo;

import java.io.IOException;

import com.klspta.base.AbstractBaseBean;
import com.klspta.console.ManagerFactory;
import com.klspta.console.user.User;

/**
 * <br>Title:TODO PersonInfo
 * <br>Description:TODO 修改个人信息
 * <br>Author:姚建林
 * <br>Date:2012-5-30
 */
public class PersonInfo extends AbstractBaseBean {

	/**
	 * <br>Description:TODO 实现基本信息修改
	 * <br>Author:姚建林
	 * <br>Date:2012-5-31
	 */
	public void changeBaseInfo(){
		//得到传进来的参数
		String userName = request.getParameter("userName");//登陆账号
		String emailAddress = request.getParameter("emailAddress");//电子邮件地址
		String officePhone = request.getParameter("officePhone");//办公电话
		String mobilePhone = request.getParameter("mobilePhone");//移动电话
		
		//声明User对象
		User user = null;
		try {
			user = ManagerFactory.getUserManager().getUserWithName(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//修改基本信息
		user.setEmail(emailAddress);
		user.setOfficephone(officePhone);
		user.setMobilephone(mobilePhone);
		
		//将修改之后的user保存
		try {
			ManagerFactory.getUserManager().updateUser(user);
			response.getWriter().write("{success:true,msg:true}");//成功的反馈信息
		} catch (Exception e) {
			try {
				response.getWriter().write("{failure:true,msg:true}");//失败的反馈信息
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	/**
	 * <br>Description:TODO 实现密码修改
	 * <br>Author:姚建林
	 * <br>Date:2012-5-31
	 */
	public void changePwdInfo(){
		//得到传进的参数
		String userName = request.getParameter("userName");//登陆账号
		String oldpass = request.getParameter("oldpass");//旧密码
		String newpass = request.getParameter("newpass");//新密码
		//声明User对象
		User user = null;
		try {
			user = ManagerFactory.getUserManager().getUserWithName(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//进行逻辑判断
		if(user.getPassword().equals(oldpass)){ 
			user.setPassword(newpass);
			try {
				ManagerFactory.getUserManager().updateUser(user);
				response.getWriter().write("{success:true,msg:true}");//成功的反馈信息
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}   	    	
         }else if(!user.getPassword().equals("oldpass")){  
        	 try {
        		 response.getWriter().write("{failure:true,msg:true}");//失败的反馈信息
			} catch (IOException e) {
				e.printStackTrace();
			} 
        }else{
        	try {
        		response.getWriter().write("{failure:true,msg:true}");//失败的反馈信息
			} catch (IOException e) {
				e.printStackTrace();
			} 
        }
	}
}
