package com.klspta.console.user;

import java.util.List;


import org.jbpm.pvm.internal.identity.spi.IdentitySession;
import org.jbpm.api.identity.Group;
import org.jbpm.api.identity.User;
import com.klspta.console.ManagerFactory;

public class UserSession implements IdentitySession{

    @Override
    public String createGroup(String arg0, String arg1, String arg2) {
        return null;
    }

    @Override
    public void createMembership(String arg0, String arg1, String arg2) {
        
    }

    @Override
    public String createUser(String arg0, String arg1, String arg2, String arg3) {
        return null;
    }

    @Override
    public void deleteGroup(String arg0) {
        
    }

    @Override
    public void deleteMembership(String arg0, String arg1, String arg2) {
        
    }

    @Override
    public void deleteUser(String arg0) {
        
    }

    @Override
    public Group findGroupById(String arg0) {
        return null;
    }

    @Override
    public List<Group> findGroupsByUser(String arg0) {
        try {
			//UserBean ub = UserUtil.getInstance().getUserBeanByUserName(arg0);
			com.klspta.console.user.User user=ManagerFactory.getUserManager().getUserWithName(arg0);
			//return RoleUtil.getInstance().getRoleBeanListByUserId(ub.getId());
			return ManagerFactory.getRoleManager().getRoleBeanListByUserId(user.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

    @Override
    public List<Group> findGroupsByUserAndGroupType(String arg0, String arg1) {
        return null;
    }

    @Override
    public User findUserById(String arg0) {
        return null;
    }

    @Override
    public List<User> findUsers() {
        return null;
    }

    @Override
    public List<User> findUsersByGroup(String arg0) {
        return null;
    }

    @Override
    public List<User> findUsersById(String... arg0) {
        return null;
    }

}
