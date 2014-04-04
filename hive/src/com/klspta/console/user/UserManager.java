package com.klspta.console.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import com.klspta.base.AbstractBaseBean;
import com.klspta.console.ManagerFactory;

@Component
public class UserManager extends AbstractBaseBean {

	private HashMap<String, User> usermap = new HashMap<String, User>();
	private HashMap<String, User> usermapById = new HashMap<String, User>();
	private HashMap<String, User> usermapByFullName = new HashMap<String, User>();
	private HashMap<String, List<User>> rolemap = new HashMap<String, List<User>>();

	private UserManager() {
		flush();
	}

	public void flush() {
		usermap = new HashMap<String, User>();
		usermapById = new HashMap<String, User>();
		usermapByFullName = new HashMap<String, User>();
		rolemap.clear();
		String sql = "select t.id,t.username,t.password,t.fullname,t.enabled,t.xzqh,t.sort,t.emailaddress,t.officephone,t.mobilephone from core_users t";
		List<Map<String, Object>> list = query(sql, CORE);
		ArrayList<Map<String, Object>> arrayList = new ArrayList<Map<String, Object>>();
		for (int j = 0; j < list.size(); j++) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("id", list.get(j).get("id"));
			hashMap.put("username", list.get(j).get("username"));
			String password = list.get(j).get("password").toString();
			hashMap.put("password", password);
			hashMap.put("fullname", list.get(j).get("fullname"));
			hashMap.put("enabled", list.get(j).get("enabled"));
			hashMap.put("xzqh", list.get(j).get("xzqh"));
			hashMap.put("sort", list.get(j).get("sort"));
			hashMap.put("emailaddress", list.get(j).get("emailaddress"));
			hashMap.put("officephone", list.get(j).get("officephone"));
			hashMap.put("mobilephone", list.get(j).get("mobilephone"));

			arrayList.add(hashMap);

		}

		Map<String, Object> map = null;
		for (int i = 0; i < arrayList.size(); i++) {
			map = arrayList.get(i);
			List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
			GrantedAuthorityImpl auth = new GrantedAuthorityImpl("ROLE_USER");
			auths.add(auth);
			User user = new User(map, auths);
			usermap.put((String) map.get("username"), user);
			usermapById.put(user.getUserID(), user);
			usermapByFullName.put(user.getFullName(), user);
		}
	}

	private static UserManager instance;

	public static UserManager getInstance() {
		if (instance == null) {
			instance = new UserManager();
			return instance;

		} else {
			return instance;
		}
	}

	public User getUserWithName(String name) throws Exception {
		return usermap.get(name);
	}

	public User getUserWithId(String userId) throws Exception {
		return usermapById.get(userId);
	}

	public User getUserWithFullName(String fullName) {
		return usermapByFullName.get(fullName);
	}

	public User getUserWithUserNameAndPassword(String userName, String passWord)
			throws Exception {
		User user = getUserWithName(userName);
		if (user == null)
			return null;
		else if (passWord.equals(user.getPassword())) {
			return user;
		}

		return null;
	}

	public boolean addUser(User user) {
		update(User.getInsertSQL(), CORE, user.getInsertArgs());

		// 刷新内存
		usermap.put(user.getUsername(), user);
		usermapById.put(user.getUserID(), user);
		usermapByFullName.put(user.getFullName(), user);
		return true;
	}

	public boolean updateUser(User user) {
		update(User.getUpdateSQL(), CORE, user.getUpdateArgs());

		// 刷新内存
		usermap.put(user.getUsername(), user);
		usermapById.put(user.getUserID(), user);
		usermapByFullName.put(user.getFullName(), user);
		return true;
	}

	public boolean deleteUser(User user) {
		return deleteUser(user.getUserID());
	}

	public boolean deleteUser(String userid) {
		update(User.getDeleteSQL(), CORE, new Object[] { userid });
		flush();
		return true;
	}

	/**
	 * 
	 * <br>
	 * Description:添加用户角色 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-14
	 * 
	 * @param roleId
	 * @param userNames
	 * @return
	 * @throws Exception
	 */
	public boolean addUserRoleMap(String roleId, List<String> userNames)
			throws Exception {
		// 删除
		String sql = "delete from map_user_role where role_id=?";
		update(sql, CORE, new Object[] { roleId });
		if(userNames.size()>0 && !"".equals(userNames.get(0))){
    		// 添加
    		StringBuffer sqlBuf = new StringBuffer(
    				"insert into map_user_role(role_id,user_id)(select ?,t.id from core_users t where t.id in (");
    		Object[] args = new Object[userNames.size() + 1];
    		args[0] = roleId;
    		for (int i = 0; i < userNames.size(); i++) {
    			sqlBuf.append("?,");
    			args[i + 1] = usermap.get(userNames.get(i)).getUserID();
    		}
    		String sql2 = sqlBuf.substring(0, sqlBuf.length() - 1) + "))";
    		update(sql2, CORE, args);
		}
		// 刷新内存
		rolemap.remove(roleId);
		ManagerFactory.getRoleManager().flashUserMap();
		return true;
	}

	public List<User> getUserWithRoleID(String roleid) {
		List<User> rs = rolemap.get(roleid);
		if (rs == null) {
			rs = new ArrayList<User>();
			String sql = "select USER_ID from map_user_role t where t.role_id = ?";
			List<Map<String, Object>> ls = query(sql, CORE,
					new Object[] { roleid });
			Map<String, Object> map;

			for (int i = 0; i < ls.size(); i++) {
				map = ls.get(i);
				if (usermapById.get((String) map.get("USER_ID")) != null)
					rs.add(usermapById.get((String) map.get("USER_ID")));
			}
			rolemap.put(roleid, rs);
		}
		return rs;
	}

	/**
	 * 
	 * <br>
	 * Description:获取所有用户信息的Json <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-14
	 * 
	 * @return
	 */
	public String getExtAllUserInfoJson() {
		List<List<String>> rows = new ArrayList<List<String>>();
		List<String> row;
		int index = 0;
		List<User> list = new ArrayList<User>();
		list.addAll(usermap.values());
		sortList(list);
		for (User user : list) {
			row = new ArrayList<String>();

			if (user != null) {
				row.add(user.getSort() + "");
				row.add(user.getFullName());
				row.add(user.getUsername());
				row.add(user.getPassword());
				row.add(user.getEmail());
				row.add(user.getOfficephone());
				row.add(user.getMobilephone());
				row.add(user.getXzqh());
				row.add(user.getUserID());
				row.add(user.getUserID());
				rows.add(row);
			}
			index++;
		}
		return JSONArray.fromObject(rows).toString();
	}

	/**
	 * 
	 * <br>
	 * Description:根据角色id获取用户列表的json <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-14
	 * 
	 * @param roleId
	 * @return
	 */
	public String getUserNameJsonByRoleId(String roleId) {
		List<User> list = getUserWithRoleID(roleId);
		if (list == null || list.size() == 0)
			return "";
		sortList(list);
		StringBuffer userNameJson = new StringBuffer();
		for (User user : list) {
			userNameJson.append(user.getFullName());
			userNameJson.append("(");
			userNameJson.append(user.getUsername());
			userNameJson.append("),");
		}
		return userNameJson.substring(0, userNameJson.length() - 1);
	}

	/**
	 * 
	 * <br>
	 * Description:根据角色id获取用户列表信息 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-14
	 * 
	 * @param roleId
	 * @return
	 */
	public String getSelectUserJsonByRoleId(String roleId) {
		List<User> list = getUserWithRoleID(roleId);
		return getUserJsonByList(list);
	}

	/**
	 * 
	 * <br>
	 * Description:获取非roleId的用户列表的json <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-14
	 * 
	 * @param roleId
	 * @return
	 */
	public String getUserInfoArrayJsonByRoleId(String roleId) {
		List<User> allUserList = new ArrayList<User>();
		allUserList.addAll(usermap.values());
		List<User> list = getUserWithRoleID(roleId);
		allUserList.removeAll(list);

		return getUserJsonByList(allUserList);
	}

	/**
	 * 
	 * <br>
	 * Description:根据用户列表拼接json <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-14
	 * 
	 * @param list
	 * @return
	 */
	public String getUserJsonByList(List<User> list) {
		if (list == null || list.size() == 0)
			return "[['','']]";

		sortList(list);
		StringBuffer userInfoJson = new StringBuffer("[");
		for (User user : list) {
			if (userInfoJson.length() > 1)
				userInfoJson.append(",");
			userInfoJson.append("['");
			userInfoJson.append(user.getFullName());
			userInfoJson.append("(");
			userInfoJson.append(user.getUsername());
			userInfoJson.append(")','");
			userInfoJson.append(user.getFullName());
			userInfoJson.append("(");
			userInfoJson.append(user.getUsername());
			userInfoJson.append(")']");
		}
		userInfoJson.append("]");
		return userInfoJson.toString();
	}

	/**
	 * 
	 * <br>
	 * Description:根据username用户名排序 <br>
	 * Author:姚建林 <br>
	 * Date:2013-8-2
	 * 
	 * @param list
	 */
	private void sortList(List<User> list) {
		User userA;
		User userB;
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = i + 1; j < list.size(); j++) {
				userA = list.get(i);
				userB = list.get(j);
				if (userA.getUsername().charAt(0) > userB.getUsername().charAt(0)){
					list.set(i, userB);
					list.set(j, userA);
				}
			}
		}
	}

	public void clearRoleMap() {
		rolemap.clear();
	}

	public List<Object> getAllUser(String roleId) {
		String sql1 = "select user_id from map_user_role where role_id='"
				+ roleId + "'";
		List<Map<String, Object>> userIdList = query(sql1, CORE);
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < userIdList.size(); i++) {
			Map<String, Object> userMap = (Map<String, Object>) userIdList
					.get(i);
			String sql2 = "select username,fullname from CORE_USERS where id='"
					+ (String) userMap.get("user_id") + "'";
			List<Map<String, Object>> userNameList = query(sql2, CORE);
			Map<String, Object> map = (Map<String, Object>) userNameList.get(0);
			list.add(map);
		}
		return list;
	}

	public List<User> getAllUser() {
		List<User> list = new ArrayList<User>();
		list.addAll(usermapById.values());
		return list;
	}

}
