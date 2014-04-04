package com.klspta.console.user;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.klspta.console.ManagerFactory;
import com.klspta.console.role.Role;

/**
 * 
 * <br>
 * Title:用户类 <br>
 * Description: <br>
 * Author:陈强峰 <br>
 * Date:2012-2-7
 */
public class User implements UserDetails,org.jbpm.api.identity.User {

    private static final long serialVersionUID = 1L;
    User(String userID, String userName, String password, String fullName, boolean enabled,  boolean accountNonexpired, 
            boolean credentialNonExpired, boolean accountNonLocked, Collection<GrantedAuthority> authorities, String xzqh,
            BigDecimal sort, String email, String officephone, String mobilephone){
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.accountNonexpired = accountNonexpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialNonExpired = credentialNonExpired;
        this.authorities = authorities;
        this.userID = userID;
        this.fullName = fullName;
        this.xzqh = xzqh;
        this.sort = sort;
        this.email = email;
        this.officephone = officephone;
        this.mobilephone = mobilephone;
    }
    
    User(Map<String, Object> map,Collection<GrantedAuthority> auths){
        this.userName = (String)map.get("username");
        this.password = (String)map.get("password");
        this.enabled = ((String)map.get("enabled")).equals("1") ? true : false;
        this.accountNonexpired = true;
        this.accountNonLocked = true;
        this.credentialNonExpired = true;
        this.authorities = auths;
        this.userID = (String)map.get("id");
        this.fullName = (String)map.get("fullname");
        this.xzqh = (String)map.get("xzqh");
        this.sort = (BigDecimal)map.get("sort");
        this.email = (String)map.get("emailaddress");
        this.officephone = (String)map.get("officephone");
        this.mobilephone = (String)map.get("mobilephone");
    }

    String userID;
    String userName;
    String password;
    String fullName;
    boolean enabled;
    boolean accountNonexpired;
    boolean credentialNonExpired;
    boolean accountNonLocked;
    Collection<GrantedAuthority> authorities;
    String xzqh;
    BigDecimal sort;
    String email;
    String officephone;
    String mobilephone;
    public User(){
    	this.fullName="";
    	this.userName="";
    	this.password="";
    	this.email="";
    	this.mobilephone="";
    	this.officephone="";
    	this.enabled=true;
        this.accountNonexpired = true;
        this.accountNonLocked = true;
        this.credentialNonExpired = true;
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        GrantedAuthorityImpl auth = new GrantedAuthorityImpl("ROLE_USER");
        auths.add(auth);
        this.authorities = auths;
    	
    }
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonexpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getXzqh() {
        return xzqh;
    }

    public void setXzqh(String xzqh) {
        this.xzqh = xzqh;
    }

    public BigDecimal getSort() {
        return sort;
    }

    public void setSort(BigDecimal sort) {
        this.sort = sort;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOfficephone() {
        return officephone;
    }

    public void setOfficephone(String officephone) {
        this.officephone = officephone;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPassword(String password) {
         this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Role> getRoleList(){
        try {
            return ManagerFactory.getRoleManager().getRoleWithUserID(this.userID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<String> getRoleIdListByRoleList(){
        try{
            List<Role> list = getRoleList();
            if(list != null){
                List<String> returnList = new ArrayList<String>();
                Iterator<Role> iter = list.iterator();
                while (iter.hasNext()) {
                    Role role = iter.next();
                    if(role != null){
                        returnList.add(role.getRoleid());
                    }
                }
                return returnList;
            }else{
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }


        
    }
    
    public static String getDeleteSQL(){
        return "delete from core_users t where t.id = ?";
    }
    
    public static String getInsertSQL(){
        return "insert into core_users t (t.ID, USERNAME, PASSWORD, FULLNAME, EMAILADDRESS, ENABLED, SORT, OFFICEPHONE, MOBILEPHONE, XZQH) values(?,?,?,?,?,?,?,?,?,?)";
    }
    public Object[] getInsertArgs(){
        return new Object[] {this.userID, this.userName,  this.password, this.fullName, this.email, this.enabled==true?1:0, this.sort, this.officephone, this.mobilephone, this.xzqh};
    }
    
    public static String getUpdateSQL(){
        return "update core_users t set t.USERNAME = ?, t.PASSWORD = ?, t.FULLNAME = ?, t.EMAILADDRESS = ?, t.ENABLED = ?, t.SORT = ?, t.OFFICEPHONE = ?, t.MOBILEPHONE = ?, t.XZQH = ? where t.id = ?";
    }
    
    public Object[] getUpdateArgs(){
        return new Object[] {this.userName,  this.password, this.fullName, this.email, this.enabled, this.sort, this.officephone, this.mobilephone, this.xzqh, this.userID};
    }

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getBusinessEmail() {
		return null;
	}

	@Override
	public String getFamilyName() {
		return null;
	}

	@Override
	public String getGivenName() {
		return null;
	}

	@Override
	public String getId() {
		return userID;
	}

}
