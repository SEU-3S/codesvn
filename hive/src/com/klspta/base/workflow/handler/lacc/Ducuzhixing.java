package com.klspta.base.workflow.handler.lacc;

import java.util.HashMap;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.workflow.foundations.IWorkflowOp;
import com.klspta.base.workflow.foundations.WorkflowOp;
import com.klspta.base.workflow.handler.api.IWfHandler;

public class Ducuzhixing extends AbstractBaseBean implements IWfHandler  {


	@Override
	public void afterDoNext(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterRollBack(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preDoNext(HashMap<String, Object> map) {
		String  wfInsID = (String)map.get("wfInsID");
		IWorkflowOp workflowOp=WorkflowOp.getInstance();
		String yw_guid = workflowOp.getYW_guidByWfInsID(wfInsID);
		String wfId=workflowOp.getWfIdByWfInsID(wfInsID);
		String name=workflowOp.getPreActivity(wfId, "督促执行").getName();
		if("市局分管局长签批确认".equals(name)){
			String newyw_guid = yw_guid + "v02";
			String sql = "insert into flwscpb(yw_guid) values ('"+ newyw_guid +"')";
			update(sql, YW);
		}
	}

	@Override
	public void preRollBack(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		
	}


}
