package com.klspta.base.rest;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;

public class ProjectInfo extends AbstractBaseBean{
    
    public static String PROJECT_NAME = "";
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
    }

}
