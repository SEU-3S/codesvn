package com.klspta.model.projectinfo;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.klspta.base.AbstractBaseBean;
/***
 * 
 * <br>Title:配置类
 * <br>Description:系统相关信息动态配置
 * <br>Author:朱波海
 * <br>Date:2013-7-9
 */
public class ProjectInfo extends AbstractBaseBean{
    
    public static  String PROJECT_NAME = "";
    public static  String PROJECT_LOGINNAME1 = "";
    public static  String PROJECT_LOGINNAME2 = "";
    public static  String FLAG = "";
    private static ProjectInfo instance=new ProjectInfo();
    private ProjectInfo(){
        init();
    }
    public static ProjectInfo getInstance()
    {
    	return instance;
    }
    
    public void init(){
        String sql = "select t.*, t.rowid from core_projectname t where t.use = 'yes'";
        List<Map<String, Object>> list = query(sql, CORE);
        PROJECT_NAME = list.get(0).get("flag").toString();
        PROJECT_LOGINNAME1 = list.get(0).get("loginname1").toString();
        PROJECT_LOGINNAME2 = list.get(0).get("loginname2").toString();
        FLAG = list.get(0).get("flag").toString();
    }
public String getProjectName(){
	return PROJECT_NAME;
}
public String getProjectLoginName1(){
	return PROJECT_LOGINNAME1;
}
public String getProjectLoginName2(){
	return PROJECT_LOGINNAME2;
}

public String getFlag(){
    return FLAG;
}
public void save(){
    String ChName = request.getParameter("ChName");
    String EnName = request.getParameter("EnName");
    String sqlString="update core_projectname set loginname1=?,loginname2=? where use = 'yes' ";
    int i = update(sqlString, CORE, new Object[]{ChName,EnName});
    if(i>0){
        init();
        try {
            response.getWriter().write("{success:true}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

}
