package com.klspta.base.workflow.handler.OA;

import java.util.HashMap;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.workflow.handler.api.IWfCommonHandler;


public class OA extends AbstractBaseBean implements IWfCommonHandler{

	@Override
	public void doCommonInsMethed(HashMap<String, Object> map) {
		String _processInstanceId = (String)map.get("_processInstanceId");
        String activityName=(String)map.get("_activityName");
        String sql;
        if(activityName!=null&&activityName.length()>0){
            sql="update newsw_db t set t.activityname=? where t.wfinsid=?";
            update(sql,YW, new Object[] {activityName,_processInstanceId});
        }else{
            sql="update newsw_db t set t.xmzt=1";
            update(sql,YW);
        }
		
	}

}
