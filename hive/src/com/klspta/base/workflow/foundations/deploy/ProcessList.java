package com.klspta.base.workflow.foundations.deploy;

import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import org.jbpm.api.ProcessDefinition;
import com.klspta.base.workflow.foundations.IWorkflowOp;
import com.klspta.base.workflow.foundations.WorkflowOp;
/**
 * 
 * <br>
 * Title:获取工作流信息 <br>
 * Description: <br>
 * Author:王峰 <br>
 * Date:2011-6-30
 */
public class ProcessList {

	@SuppressWarnings("unchecked")
	public String getProcessList() {
		IWorkflowOp work = WorkflowOp.getInstance();
		List<ProcessDefinition> pdList = work.getWFList();
		List allRows = new ArrayList();
		int i=0;
		for (ProcessDefinition pd : pdList) {
			List oneRow = new ArrayList();
			oneRow.add(pd.getId());
			oneRow.add(pd.getName());
			oneRow.add(pd.getVersion());
			oneRow.add(pd.getDescription());
			oneRow.add(pd.getDeploymentId());
			oneRow.add(i);
			oneRow.add(i++);
			oneRow.add(pd.getImageResourceName());
			allRows.add(oneRow);
		}
		return JSONArray.fromObject(allRows).toString();	
	}
}
