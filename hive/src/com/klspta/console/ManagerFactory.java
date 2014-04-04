package com.klspta.console;

import com.klspta.console.role.RoleManager;
import com.klspta.console.menu.MenuManager;
import com.klspta.console.user.UserManager;
import com.klspta.model.report.ReportManage;

public class ManagerFactory {
    private ManagerFactory(){}
    
    private static ManagerFactory instance;
    
    public ManagerFactory getInstance(){
        if(instance == null){
            instance = new ManagerFactory();
        }
        return instance;
    }
    
    public static UserManager getUserManager(){
        return UserManager.getInstance();
    }
    
    public static RoleManager getRoleManager() throws Exception {
        return RoleManager.getInstance("NEW WITH MANAGER FACTORY!");
    }
    public static MenuManager getMenuManager() throws Exception {
    	return MenuManager.getInstance("NEW WITH MANAGER FACTORY!");
    }
    public static ReportManage getReportManage() throws Exception{
    	return ReportManage.getInstance("NEW WITH MANAGER FACTORY!");
    }
    
}
