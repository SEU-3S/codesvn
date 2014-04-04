package com.klspta.console.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jbpm.api.identity.Group;
import org.springframework.stereotype.Component;
import com.klspta.base.AbstractBaseBean;



@Component
public class RoleManager extends AbstractBaseBean{
    
    private HashMap<String, Role> rolemap = new HashMap<String, Role>();
    private HashMap<String, List<Role>> usermap = new HashMap<String, List<Role>>();
    private  List<Group> groupList = null;
    
    private RoleManager(){
    	//修改了下sql，有个字段写错了
        String sql = "select t.roleid, t.rolename,t.leaf_flag, t.flag, t.parentroleid, t.SORT,t.xzqh from core_roles t";
        List<Map<String, Object>> list = query(sql, CORE);
        Map<String, Object> map = null;
        for(int i = 0; i < list.size(); i++){
            map = list.get(i);
            Role role = new Role(map);
            rolemap.put(role.getRoleid(),role);
        }
    }
    
    private static RoleManager instance;
    
    public static RoleManager getInstance(String key) throws Exception{
        if(key.equals("NEW WITH MANAGER FACTORY!")){
            if(instance == null){
                instance = new RoleManager();
            }
            return instance;
        }else{
            throw new Exception("请从ManagerFacoory获取工具.");
        }
    }
    
    public Role getRoleWithId(String id) throws Exception{
        return rolemap.get(id);
    }
    
    public boolean addRole(Role role){
        update(Role.getInsertSQL(), CORE, role.getInsertArgs());
        rolemap.put(role.getRoleid(), role);
        return true;
    }
    public boolean updateRole(Role role){
        update(Role.getUpdateSQL(), CORE, role.getUpdateArgs());
        rolemap.remove(role.getRoleid());
        rolemap.put(role.getRoleid(), role);
        return true;
    }
    public boolean deleteRole(Role role){
        return deleteRole(role.getRoleid());
    }
    public boolean deleteRole(String roleid){
        update(Role.getDeleteSQL(), CORE, new Object[]{roleid});
        rolemap.remove(roleid);
        flashUserMap();
        return true;
    }
    
    public List<Role> getRoleWithUserID(String roleid){
        List<Role> rs = usermap.get(roleid);
        //if(rs == null){
            rs = new ArrayList<Role>();
            String sql = "select ROLE_ID from map_user_role t where t.user_id = ?";
            List<Map<String, Object>> ls = query(sql, CORE, new Object[] {roleid});
            Map<String, Object> map;
            for(int i = 0; i < ls.size(); i++){
                map = ls.get(i);
                rs.add(rolemap.get((String)map.get("ROLE_ID")));//改为ROLE_ID
            }
            usermap.put(roleid, rs);
        //}
        return rs;
    }
   
    public List<Group> getRoleBeanListByUserId(String userId) {
        	groupList = new ArrayList<Group>();
            String sql = "select * from core_roles t where t.roleid in (select m.role_id  from map_user_role m where m.user_id=?) and  t.flag='1' order by leaf_flag,sort";
            List<Map<String, Object>> ls = query(sql, CORE, new Object[] {userId});
            Map<String, Object> map;
            for(int i = 0; i < ls.size(); i++){
                map = ls.get(i);
                Role role=new Role();
                role.setFlag((String)map.get("flag"));
                role.setRoleid((String)map.get("roleid"));
                role.setLeafflag((String)map.get("leaf_flag"));
                role.setParentroleid((String)map.get("parentroleid"));
                role.setSort(map.get("sort").toString());
                role.setRolename((String)map.get("rolename"));
                role.setXzqh((String)map.get("xzqh"));
                groupList.add((Group)role);//改为ROLE_ID
            }         
        return groupList;
    }
   
    /**
     * 
     * <br>Description:获取role列表的json
     * <br>Author:任宝龙
     * <br>Date:2012-6-14
     * @return
     */
    public String getRoleListExtJson()
    {
    	Map<String,List<Role>> map=getRoleMapTreeMap();
    	List<Role> list=map.get("0");
    	return addRoleElementJson(list, map);
    }
    /**
     * 
     * <br>Description:拼接角色Json
     * <br>Author:任宝龙
     * <br>Date:2012-6-14
     * @param list
     * @param map
     * @return
     */
    private String addRoleElementJson(List<Role> list,Map<String,List<Role>> map)
    {
    	StringBuffer extJsonCode = new StringBuffer("[");
		List<Role> childList=null;
		sortList(list);
		for (Role role : list) {

			if (extJsonCode.length() > 1)
				extJsonCode.append(",");
			extJsonCode.append("\n{text:'");// text
			extJsonCode.append(role .getRolename());
			extJsonCode.append("(");
			extJsonCode.append(role .getSort());

			extJsonCode.append(")',leaf:"); // leaf
			
			childList =map.get(role .getRoleid());

			if ("0".equals(role .getParentroleid()))
				extJsonCode.append("0");
			else
				extJsonCode.append(childList == null ? "1,parentId:'"+ role.getParentroleid() + "'" : "0");
			extJsonCode.append(",id:'");// id
			extJsonCode.append(role.getRoleid());
			extJsonCode.append("'");

			if (childList != null) {
				extJsonCode.append(",children:");// children
				extJsonCode.append(addRoleElementJson(childList,map));
			}
			extJsonCode.append("}");
		}
		extJsonCode.append("]");
		return extJsonCode.toString();
	}
    public void delete(String roleId)
    {
    	Role role=rolemap.get(roleId);
    	delete(role);
    }
      
	public void delete(Role role) {

		StringBuffer sqlBuffer = new StringBuffer(
				"delete core_roles t where  t.roleid in (");
		List<Role> list = getChildRoleList(role);
		Object[] args = new Object[list.size()];
		Role r;
		for (int i = 0; i < list.size(); i++) {
			r = list.get(i);
			sqlBuffer.append("?,");
			args[i] = r.getRoleid();
		}
		String sql = sqlBuffer.substring(0, sqlBuffer.length() - 1) + ")";

		update(sql, CORE, args);
		
		flashUserMap();
		for(Role rol:list)
			rolemap.remove(rol.getRoleid());

		
	}
	private List<Role> getChildRoleList(Role role) {
		List<Role>  menuList = new ArrayList<Role >();
		menuList.addAll(rolemap.values());
		Map<String, List<Role>> map = getRoleMapTreeMap();
		List<Role> list = new ArrayList<Role>();
		list.add(role);
		if (map.containsKey(role.getRoleid()))
			addChildRoles(list, map,role);
		return list;
	}

	private void addChildRoles(List<Role> list,
			Map<String, List<Role>> menuMap, Role role) {
		List<Role> childList = menuMap.get(role.getRoleid());
		list.addAll(childList);
		for (Role r: childList) {
			if (menuMap.containsKey(r.getRoleid()))
				addChildRoles(list, menuMap,r);
		}
	}
    /**
     * 
     * <br>Description:构建角色表树形结构
     * <br>Author:任宝龙
     * <br>Date:2012-6-14
     * @return
     */
    public Map<String,List<Role>> getRoleMapTreeMap()
    {
    	Map<String,List<Role>> map=new HashMap<String, List<Role>>();
    	String key="";
    	
    	for(Role role:rolemap.values())
    	{
    		key=role.getParentroleid();
    		if(!map.containsKey(key))
    			map.put(key, new ArrayList<Role>());
    		map.get(key).add(role);
    	}
    	return map;
    }
   /**
    * 
    * <br>Description:根据sort列表排序
    * <br>Author:任宝龙
    * <br>Date:2012-6-14
    * @param list
    */
   
    private void sortList(List<Role> list)
    {
    	Role roleA;
    	Role roleB;
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = i + 1; j < list.size(); j++) {
				roleA = list.get(i);
				roleB = list.get(j);
				
				if (Integer.parseInt(roleA.getSort()) >Integer.parseInt(roleB.getSort())) {
					list.set(i,roleB);
					list.set(j,roleA);
				}
			}
		}
    }
    /**
     * 
     * <br>Description:刷新
     * <br>Author:任宝龙
     * <br>Date:2012-6-14
     */
    public void flashUserMap()
    {
    	usermap.clear();
    }
    
    public List<Role> getRoleBeanList(){
    	List<Role> list = new ArrayList<Role>();
    	String sql = "select * from core_roles t where  t.flag='1' order by leaf_flag,sort";
    	List<Map<String, Object>> resultList = query(sql, CORE);
    	for(Map<String, Object> resultMap : resultList){
    		Role role = new Role();
    		role.setFlag(String.valueOf(resultMap.get("flag")));
    		role.setLeafflag(String.valueOf(resultMap.get("leafflag")));
    		role.setParentroleid(String.valueOf("parentroleid"));
    		role.setRoleid(String.valueOf(resultMap.get("roleid")));
    		role.setRolename(String.valueOf(resultMap.get("rolename")));
    		role.setSort(String.valueOf(resultMap.get("sort")));
    		role.setXzqh(String.valueOf(resultMap.get("xzqh")));
    		list.add(role);
    	}
    	return list;
    }
    
    public String getParantRoleName(String roleid){
        String sql1 = "select t.parentroleid from core_roles t where t.roleid=?";
        List<Map<String,Object>> list1 = query(sql1,CORE,new Object[]{roleid});
        String parentroleid = (String)(list1.get(0)).get("parentroleid");
        String sql2 = "select t.rolename from core_roles t where t.parentroleid='0' and t.roleid=?";
        List<Map<String,Object>> list2 = query(sql2,CORE,new Object[]{parentroleid});      
        return (String)(list2.get(0)).get("rolename");
    }
    
}
