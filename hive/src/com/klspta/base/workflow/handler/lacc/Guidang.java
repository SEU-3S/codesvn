package com.klspta.base.workflow.handler.lacc;

import java.util.HashMap;
import com.klspta.base.AbstractBaseBean;
import com.klspta.base.workflow.foundations.IWorkflowOp;
import com.klspta.base.workflow.foundations.WorkflowOp;
import com.klspta.base.workflow.handler.api.IWfHandler;

public class Guidang extends AbstractBaseBean implements IWfHandler{

    @Override
    public void afterDoNext(HashMap<String, Object> map) {
        String wfInsID = (String)map.get("wfInsID");
        IWorkflowOp workflowOp=WorkflowOp.getInstance();
        String yw_guid = workflowOp.getYW_guidByWfInsID(wfInsID);
        String sql="update lacpb t set t.xmzt='1' where t.yw_guid=?";
        update(sql,YW,new Object[]{yw_guid});        
    }

    @Override
    public void afterRollBack(HashMap<String, Object> map) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void preDoNext(HashMap<String, Object> map) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void preRollBack(HashMap<String, Object> map) {
        // TODO Auto-generated method stub
        
    }

}
