package com.klspta.console.role;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.jbpm.api.identity.Group;

import com.klspta.console.ManagerFactory;
import com.klspta.console.user.User;

public class Role implements Group{
	
    private String roleid = "";
    private String rolename = "";
    private String leafflag = "";
    private String flag = "";
    private String parentroleid = "";
    private String sort = "";
    private String xzqh="";
    public Role(){}
    public Role(Map<String, Object> map){
        this.roleid = (String)map.get("roleid");
        this.rolename = (String)map.get("rolename");
        this.leafflag = (String)map.get("leaf_flag");
        this.flag = (String)map.get("flag");
        this.parentroleid = (String)map.get("parentroleid");
        this.sort = ((BigDecimal)map.get("sort")).toString();//map.get("sort") 是 BigDecimal
        this.xzqh = (String)map.get("xzqh");
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getLeafflag() {
        return leafflag;
    }

    public void setLeafflag(String leafflag) {
        this.leafflag = leafflag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getParentroleid() {
        return parentroleid;
    }

    public void setParentroleid(String parentroleid) {
        this.parentroleid = parentroleid;
    }
     
    public String getXzqh() {
		return xzqh;
	}
	public void setXzqh(String xzqh) {
		this.xzqh = xzqh;
	}
	public List<User> getUserList(){
            return ManagerFactory.getUserManager().getUserWithRoleID(this.roleid);
    }
    public static String getDeleteSQL(){
        return "delete from core_roles t where t.id = ?";
    }
    public static String getInsertSQL(){
        return "insert into core_roles (ROLEID, ROLENAME, LEAF_FLAG, FLAG, PARENTROLEID, SORT,XZQH) values(?,?,?,?,?,?,?)";
    }
    public Object[] getInsertArgs(){
        return new Object[] {this.roleid, this.rolename, this.leafflag,this.flag, this.parentroleid, this.sort,this.xzqh};
    }  
    public static String getUpdateSQL(){
        return "update core_roles t set t.rolename = ?, t.leaf_flag = ?, t.flag = ?, t.parentroleid = ?, t.sort = ?,t.xzqh=? where t.roleid = ?";
    }   
    public Object[] getUpdateArgs(){
        return new Object[] {this.rolename, this.leafflag, this.flag, this.parentroleid, this.sort, this.xzqh,this.roleid};
    }
	@Override
	public String getId() {
		return this.roleid;
	}
	@Override
	public String getName() {
		return this.rolename;
	}
	@Override
	public String getType() {
		return null;
	}
    
}
