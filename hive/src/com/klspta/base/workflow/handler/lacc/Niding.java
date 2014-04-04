package com.klspta.base.workflow.handler.lacc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.klspta.base.AbstractBaseBean;
import com.klspta.base.workflow.foundations.IWorkflowOp;
import com.klspta.base.workflow.foundations.WorkflowOp;
import com.klspta.base.workflow.handler.api.IWfHandler;


public class Niding extends AbstractBaseBean implements IWfHandler {

    @Override
    public void afterDoNext(HashMap<String, Object> map) {
        String wfInsID = (String)map.get("wfInsID");
        IWorkflowOp workflowOp=WorkflowOp.getInstance();
        String yw_guid = workflowOp.getYW_guidByWfInsID(wfInsID);
        String activityName = (String)map.get("activityName");
        String sql = "select * from JBPM4_HIST_ACTINST t where t.transition_=? order by t.end_ desc";
        List<Map<String, Object>> result = query(sql, WORKFLOW, new String[] { activityName });
        if(result != null && result.size()>0){
            String name = result.get(0).get("activity_name_").toString();
            if("承办人处理".equals(name)){
                sql = "insert into flwscpb(yw_guid) values ('"+ yw_guid +"v02')";
                update(sql, YW);            
            }
        }
        
    }

    @Override
    public void afterRollBack(HashMap<String, Object> map) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void preDoNext(HashMap<String, Object> map) {

    }

    @Override
    public void preRollBack(HashMap<String, Object> map) {
        // TODO Auto-generated method stub
        
    }



}
