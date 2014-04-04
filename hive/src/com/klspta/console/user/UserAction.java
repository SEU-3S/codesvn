package com.klspta.console.user;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ctc.wstx.io.UTFAttrValueWriter;
import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.console.ManagerFactory;
import com.klspta.console.role.Role;

public class UserAction extends AbstractBaseBean {
	/**
	 * 
	 * <br>
	 * Description: 添加修改用户 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-14
	 */
	public void saveUser() {
		try {
			String userId = request.getParameter("userId");
			User oldUser = ManagerFactory.getUserManager()
					.getUserWithId(userId);
			if (oldUser == null) {
				User user = new User();
				user.setUserID(userId);
				setUserBean(request, user);
				// 判断用户名是否重复
				oldUser = ManagerFactory.getUserManager().getUserWithName(
						user.getUsername());
				if (oldUser != null) {
					response.getWriter().write("{failure:true,msg:true}");
					return;
				}
				if (ManagerFactory.getUserManager().addUser(user))
					response.getWriter().write("{success:true,msg:true}");
			} else {
				setUserBean(request, oldUser);
				if (ManagerFactory.getUserManager().updateUser(oldUser))
					response.getWriter().write("{success:true,msg:true}");
			}
		} catch (Exception e) {
			responseException(this, "saveUser", "200002", e);
			try {
				response.getWriter().write("{failure:true,msg:true}");
			} catch (IOException e1) {
			}
		}
	}

	/**
	 * 
	 * <br>
	 * Description:添加用户角色 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-14
	 */
	public void saveRoleUser() {
		String roleId = request.getParameter("roleId");
		String parentRoleId = request.getParameter("parentRoleId");
		try {
			Role role = ManagerFactory.getRoleManager().getRoleWithId(roleId);
			if (role == null) {
				role = new Role();
				role.setRoleid(roleId);
				role.setParentroleid(parentRoleId);
				role.setRolename(request.getParameter("roleName"));
				role.setSort(request.getParameter("sort"));
				role.setFlag("1");
				role.setLeafflag("1");
				ManagerFactory.getRoleManager().addRole(role);
			} else {
				role.setRolename(request.getParameter("roleName"));
				role.setSort(request.getParameter("sort"));
				ManagerFactory.getRoleManager().updateRole(role);
			}
			String personName = request.getParameter("personName");
			List<String> userNames = new ArrayList<String>();
			
			if ("".equals(personName)) {
				//response.getWriter().write("{success:true,msg:true}");
				//return;
			    userNames.add("");
			}else{
    			String[] users = personName.split(",");    		   
    			for (int i = 0; i < users.length; i++) {
    				if ("".equals(users[i]))
    					continue;
    				userNames.add(users[i].substring(users[i].indexOf('(') + 1,
    						users[i].indexOf(')')));
    			}
			}
			ManagerFactory.getUserManager().addUserRoleMap(role.getRoleid(),
					userNames);
			response.getWriter().write("{success:true,msg:true}");
		} catch (Exception e) {
			responseException(this, "saveUser", "200002", e);
			e.printStackTrace();
			try {
				response.getWriter().write("{failure:true,msg:true}");
			} catch (IOException e1) {
			}
		}
	}

	public void deleteRole() {
		String roleId = request.getParameter("selectRoleId");
		try {
			ManagerFactory.getRoleManager().delete(roleId);
			ManagerFactory.getUserManager().clearRoleMap();
			response.getWriter().write("{success:true,msg:true}");
		} catch (Exception e) {
			responseException(this, "saveUser", "200002", e);
			try {
				response.getWriter().write("{failure:true,msg:true}");
			} catch (IOException e1) {
			}
		}
	}

	/**
	 * 
	 * <br>
	 * Description:删除用户 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-14
	 */
	public void deleteUser() {
		String userId = request.getParameter("userId");
		try {
			if (ManagerFactory.getUserManager().deleteUser(userId))
				response.getWriter().write("{success:true,msg:true}");
			else
				response.getWriter().write("{failure:true,msg:true}");

		} catch (Exception e) {
			responseException(this, "saveUser", "200002", e);
			try {
				response.getWriter().write("{failure:true,msg:true}");
			} catch (IOException e1) {

			}
		}
	}

	public void login() {
		String userName = request.getParameter("userName");
		String passWord = request.getParameter("passWord");
		if (userName == null || "".equals(userName)) {
			response("error");
			return;
		}
		if (passWord == null) {
			response("error");
			return;
		}
		try {
			User user = ManagerFactory.getUserManager()
					.getUserWithUserNameAndPassword(userName, passWord);
			if (user != null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("userId", user.getUserID());
				map.put("userName", user.getUsername());
				map.put("fullName", user.getFullName());
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				list.add(map);
				response(list);
				return;
			}

		} catch (Exception e) {
			responseException(this, "login", "200003", e);
		}
		response("error");
	}

	/**
	 * 
	 * <br>
	 * Description:设置用户属性 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-14
	 * 
	 * @param request
	 * @param user
	 */
	private void setUserBean(HttpServletRequest request, User user) {
		user.setFullName(request.getParameter("fullName"));
		user.setUserName(request.getParameter("userName"));
		user.setPassword(request.getParameter("password"));
		user.setEmail(request.getParameter("emailAddress"));
		user.setOfficephone(request.getParameter("officePhone"));
		user.setMobilephone(request.getParameter("mobilePhone"));
		user.setXzqh(request.getParameter("xzqh"));
		String sort = request.getParameter("sort");
		if (!"".equals(sort) && sort != null) {
			user.setSort(new BigDecimal(Integer.parseInt(sort)));
		} else
			user.setSort(new BigDecimal(0));
	}
	
	/**
	 * 
	 * <br>Title: 用于角色添加人员
	 * <br>Description: 快速查询
	 * <br>Author: 姚建林
	 * <br>Date: 2013-8-2
	 */
	public void getUserInfoArrayJsonByRoleId(){
		//从页面中传来的参数
		String keyWord = request.getParameter("keyWord");
		String roleId = request.getParameter("roleId");
		//当关键字为空时，将所有人员查出
		if(keyWord == null || "".equals(keyWord)){
			response(ManagerFactory.getUserManager().getUserInfoArrayJsonByRoleId(roleId));
		}else{
			User tempUser = null;
			//得到所有人员
			List<User> allUserList = ManagerFactory.getUserManager().getAllUser();
			List<User> allUserListTemp = new ArrayList<User>();
			//用关键字过滤
			for (int i = 0; i < allUserList.size(); i++) {
				tempUser = (User)allUserList.get(i);
				if(keyWord.equals(tempUser.getUsername().charAt(0)+"")){
					allUserListTemp.add(tempUser);
				}
			}
			//根据roleId过滤
			List<User> list = ManagerFactory.getUserManager().getUserWithRoleID(roleId);
			allUserListTemp.removeAll(list);
			String returnString = ManagerFactory.getUserManager().getUserJsonByList(allUserListTemp);
			response(returnString);
		}
	}
	/***
	 * 快速查找
	 */
	public void getuser(){
		String keyWord = request.getParameter("keyWord");
		String sql = "select t.id,t.username,t.password,t.fullname,t.enabled,t.xzqh,t.sort,t.emailaddress,t.officephone,t.mobilephone from core_users t";
		if(keyWord!=null&&!"".equals(keyWord)){
			keyWord=UtilFactory.getStrUtil().unescape(keyWord);
			sql+=" where (t.username||t.fullname||t.xzqh like '%"+keyWord+"%')";
		}
		List<Map<String, Object>> query = query(sql,CORE);
		response(query);
		
	}
}
