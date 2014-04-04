package com.klspta.base.workflow.foundations;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.jbpm.api.task.Task;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.console.ManagerFactory;
import com.klspta.console.user.User;

public class NodeOperation extends AbstractBaseBean {

	/**
	 * <br>
	 * Description:移交任务REST服务 <br>
	 * Author:赵伟 <br>
	 * Date:2013-3-27
	 */
	public void transferTask() {
		String nextNodeName = "";
		String op = "";
		String wfInsTaskId = "";
		String nextFullName = "";
		String assignee = "";
		String wfInsId = "";
		try {
			nextNodeName = URLDecoder.decode(request.getParameter("nextNodeName"), "utf-8");
			op = request.getParameter("op");
			assignee = request.getParameter("assignee");
			wfInsTaskId = request.getParameter("wfInsTaskId");
			wfInsId = request.getParameter("wfInsId");
			nextFullName = URLDecoder.decode(request.getParameter("nextFullName"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		if (nextNodeName != null) {
			if (op != null && op.equals("donext")) {
				// 移交完成任务
				IWorkflowInsOp workflowInsOp = WorkflowInsOp.getInstance();
				workflowInsOp.doNext(wfInsTaskId, nextNodeName);

				// 获取移交后下一节点的节点ID,首先查出task对象，根据是否有下一节点判断流程是否结束。
				Task nextWfInsTask = JBPMServices.getInstance().getTaskService().createTaskQuery().executionId(wfInsId)
						.uniqueResult();
				String nextWfInsTaskId = "";
				if (nextWfInsTask != null) {
					nextWfInsTaskId = nextWfInsTask.getId();
				}

				// 取走/占有该任务
				if (!"all".equals(nextFullName) && !"".equals(nextFullName) && assignee.equals("false")
						&& nextWfInsTask != null) {
					workflowInsOp.iDoIt(nextWfInsTaskId, nextFullName);
				}
			}
			try {
				response.getWriter().write("true");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				response.getWriter().write("false");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * <br>
	 * Description:回退任务REST服务 <br>
	 * Author:赵伟 <br>
	 * Date:2013-3-27
	 */
	public void backTask() {
		try {
			request.setCharacterEncoding("utf-8");
			String wfInsId = request.getParameter("wfInsId");
			String wfId = JBPMServices.getInstance().getExecutionService().findExecutionById(wfInsId)
					.getProcessDefinitionId();
			String wfInsTaskId = JBPMServices.getInstance().getTaskService().createTaskQuery().executionId(wfInsId)
					.uniqueResult().getId();
			String activityName = JBPMServices.getInstance().getTaskService().createTaskQuery().executionId(wfInsId)
					.uniqueResult().getActivityName();
			boolean b = false;

			// 当回退出现错误，捕捉到错误返回错误。
			try {
				b = WorkflowInsOp.getInstance().back(wfId, wfInsId, wfInsTaskId, activityName);
			} catch (Exception e) {
				response("error");
				e.printStackTrace();
				return;
			}
			if (b) {
				response.getWriter().write("success");
			} else {
				response.getWriter().write("false");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <br>
	 * Description:删除流程 <br>
	 * Author:赵伟 <br>
	 * Date:2013-6-17
	 */
	public void delTask() {
		try {
			String wfInsID = request.getParameter("wfInsID");
			String id = wfInsID.substring(wfInsID.indexOf('.') + 1);
			String sql1 = "delete JBPM4_VARIABLE t where t.execution_=?";
			String sql2 = "delete JBPM4_HIST_VAR t where t.procinstid_=?";
			update(sql1, WORKFLOW, new String[] { id });
			update(sql2, WORKFLOW, new String[] { wfInsID });
			response.getWriter().write("true");
		} catch (Exception e) {
			try {
				response.getWriter().write("false");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * <br>
	 * Description:根据角色获取人员 <br>
	 * Author:赵伟 <br>
	 * Date:2012-9-24
	 * 
	 * @throws Exception
	 */
	public void getUsersByRoleId() throws Exception {
		String roleid = request.getParameter("roleId");
		String wfInsId = request.getParameter("wfInsId");
		String result = "";
		List<User> list = null;
		if (roleid != null) {
			list = ManagerFactory.getUserManager().getUserWithRoleID(roleid);
			for (int i = 0; i < list.size(); i++) {
				if (i == 0) {
					result += list.get(i).getFullName();
					continue;
				}
				result += "," + list.get(i).getFullName();
			}
		} else if (roleid == null && wfInsId != null) {
			result = JBPMServices.getInstance().getExecutionService().getVariable(wfInsId, "owner").toString();
		}
		response(result);
	}

	/**
	 * <br>
	 * Description:根据节点获取名称 <br>
	 * Author:赵伟 <br>
	 * Date:2012-9-24
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public void getRoleByActivityName() throws UnsupportedEncodingException {
		String activityName = URLDecoder.decode(request.getParameter("activityName"), "utf-8");
		String wfId = request.getParameter("wfId");
		IWorkflowOp workflowOp = WorkflowOp.getInstance();
		List<Map<String, Object>> Roles = workflowOp.getAllRoles(activityName, wfId);
		try {
			response(UtilFactory.getJSONUtil().objectToJSON(Roles));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
