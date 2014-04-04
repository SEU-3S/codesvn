package com.klspta.base.workflow.foundations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipInputStream;

import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.JbpmException;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.history.HistoryTaskQuery;
import org.jbpm.api.model.Activity;
import org.jbpm.api.model.ActivityCoordinates;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.base.workflow.bean.DoNextBean;
import com.klspta.base.workflow.bean.NodeDefineInfoBean;
import com.klspta.base.workflow.bean.WorkItemBean;
import com.klspta.console.ManagerFactory;

public class WorkflowOp extends AbstractBaseBean implements IWorkflowOp {

	private static WorkflowOp instance = null;

	private WorkflowOp() {
	}

	public static IWorkflowOp getInstance() {
		if (instance == null) {
			instance = new WorkflowOp();
		}
		return instance;
	}

	/**
	 * <br>
	 * Description:解析流程定义XML，将相关信息存入数据库 <br>
	 * Author:郭润沛 <br>
	 * Date:2011-11-4
	 * 
	 * @param path:zip文件路径
	 * @param wfID：流程定义ID
	 */
	private String parseDeploy(String path, String deploymentId) {
		List<ProcessDefinition> pdList = this.getWFList();
		String wfID = null;
		String tempPath = "d://temp//";
		for (int i = 0; i < pdList.size(); i++) {
			if (deploymentId.equals(pdList.get(i).getDeploymentId())) {
				wfID = pdList.get(i).getId();
				break;
			}
		}
		File file = new File(tempPath + deploymentId);
		file.mkdirs();
		UtilFactory.getZIPUtil().unZip(path, tempPath + deploymentId);
		File[] fileList = new File(tempPath + deploymentId).listFiles();
		for (int i = 0; i < fileList.length; i++) {
			String xmlFilePath = fileList[i].getAbsolutePath().toLowerCase();
			if (xmlFilePath.endsWith(".xml")) {
				try {
					SAXBuilder builder = new SAXBuilder();
					Document doc = builder.build(new File(xmlFilePath));
					Element foo = doc.getRootElement();
					List<?> firstChild = foo.getChildren();
					update("delete from EXT_NODEDEFINE_INFO where wfid=?", WORKFLOW, new Object[] { wfID });
					for (int j = 0; j < firstChild.size(); j++) {
						String name = ((Element) firstChild.get(j)).getAttributeValue("name");
						String assignee = ((Element) firstChild.get(j)).getAttributeValue("assignee");
						String groups = ((Element) firstChild.get(j)).getAttributeValue("candidate-groups");
						if (((Element) firstChild.get(j)).getAttributeValue("g") == null) {
							continue;
						}
						String[] g = ((Element) firstChild.get(j)).getAttributeValue("g").split(",");
						update(
								"insert into EXT_NODEDEFINE_INFO(WFID,NODENAME,X,Y,HEIGHT,WIDTH,groups, assignee) values(?,?,?,?,?,?,?,?)",
								WORKFLOW, new Object[] { wfID, name, g[0], g[1], g[3], g[2], groups, assignee });
					}
				} catch (JDOMException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;// 跳出循环
			}
		}
		return wfID;
	}

	@Override
	public String deploy(String path) throws Exception {
		if (!path.endsWith(".zip")) {
			Exception e = new Exception("请发布zip格式工作流文件！");
			throw e;
		}
		ZipInputStream zis = new ZipInputStream(new FileInputStream(path));
		try {
			String deploymentId = JBPMServices.getInstance().getRepositoryService().createDeployment()
					.addResourcesFromZipInputStream(zis).deploy();
			return parseDeploy(path, deploymentId);

		} catch (JbpmException je) {
			je.printStackTrace();
			System.out
					.println("==============================================================================================================");
			System.out
					.println("============================流程发布失败，请尝试按照以下说明操作：===============================================");
			System.out
					.println("==============================================================================================================");
			System.out
					.println("==========将tomcat的lib下的包el-api.jar 替换成jbpm4的lib下的juel-api.jar、juel-engine.jar、juel-impl.jar========");
			System.out
					.println("==============================================================================================================");
			System.out
					.println("==============================================================================================================");
			System.out
					.println("==============================================================================================================");
			return null;
		}
	}

	@Override
	public List<ProcessDefinition> getWFList() {
		List<ProcessDefinition> pdList = JBPMServices.getInstance().getRepositoryService()
				.createProcessDefinitionQuery().list();
		return pdList;
	}

	@Override
	public void delete(String wfID) {
		JBPMServices.getInstance().getRepositoryService().deleteDeploymentCascade(wfID);
	}

	@Override
	public String start(String wfID, WorkItemBean wiBean) {
		return start(wfID, wiBean.changeToMap());
	}

	@Override
	public String start(String wfID, String creater, String receiveid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("owner", creater);
		map.put("receiveid", receiveid);
		return start(wfID, map);
	}

	@Override
	public String start(String wfID, Map<String, Object> parameters) {
		wfID = getWorkflowName(wfID);
		ProcessInstance wfIns = JBPMServices.getInstance().getExecutionService().startProcessInstanceById(wfID,
				parameters);
		JBPMServices.getInstance().getExecutionService().createVariables(wfIns.getId(), parameters, true);
		return wfIns.getId();
	}

	@Override
	public String getZfjcType(String wfId) {
		String sql = "select child_id from public_code where child_name='" + wfId + "'";
		List list = query(sql, YW);
		Map map = (Map) list.get(0);
		String zfjcType = (String) map.get("child_id");
		return zfjcType;
	}

	@Override
	public InputStream getResourceAsStream(String wfId, String resourceName) {
		List<ProcessDefinition> pdList = this.getWFList();
		String deploymentId = null;
		for (int i = 0; i < pdList.size(); i++) {
			if (wfId.equals(pdList.get(i).getId())) {
				deploymentId = pdList.get(i).getDeploymentId();
				break;
			}
		}
		return JBPMServices.getInstance().getRepositoryService().getResourceAsStream(deploymentId, resourceName);
	}

	@Override
	public DoNextBean getNextInfo(String wfInsTaskId) {
		DoNextBean ib = null;
		try {
			TaskService taskService = JBPMServices.getInstance().getTaskService();
			Task task = taskService.getTask(wfInsTaskId);
			Set<String> ss = taskService.getOutcomes(wfInsTaskId);

			String execuinId = task.getExecutionId();
			ExecutionService executionService = JBPMServices.getInstance().getExecutionService();
			Execution execution = executionService.findExecutionById(execuinId);
			ProcessInstance processInstance = (ProcessInstance) execution.getProcessInstance();
			String wfInsId = processInstance.getId();

			String processDefinitionId = processInstance.getProcessDefinitionId();

			RepositoryService repositoryService = JBPMServices.getInstance().getRepositoryService();
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(
					processDefinitionId).uniqueResult();
			String name = processDefinition.getName();
			InputStream inputStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), name
					+ ".png");
			Set<String> activityNames = processInstance.findActiveActivityNames();
			ActivityCoordinates ac = repositoryService.getActivityCoordinates(processInstance.getProcessDefinitionId(),
					activityNames.iterator().next());
			ib = new DoNextBean(inputStream, ac.getX(), ac.getY(), ac.getWidth(), ac.getHeight(), ss);
		} catch (Exception e) {
			ib = new DoNextBean(false);
		}
		return ib;
	}

	@Override
	public DoNextBean getNextInfo(String wfId, String wfInsId, String wfInsTaskId) {
		DoNextBean bean = null;
		try {
			TaskService taskService = JBPMServices.getInstance().getTaskService();
			Set<String> ss = taskService.getOutcomes(wfInsTaskId);

			RepositoryService repositoryService = JBPMServices.getInstance().getRepositoryService();
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(
					wfId).uniqueResult();
			String name = processDefinition.getName();
			InputStream inputStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), name
					+ ".png");

			ExecutionService es = JBPMServices.getInstance().getExecutionService();
			ProcessInstance processInstance = es.findProcessInstanceById(wfInsId);
			Set<String> activityNames = processInstance.findActiveActivityNames();
			ActivityCoordinates ac = repositoryService.getActivityCoordinates(processInstance.getProcessDefinitionId(),
					activityNames.iterator().next());
			bean = new DoNextBean(inputStream, ac.getX(), ac.getY(), ac.getWidth(), ac.getHeight(), ss);
		} catch (Exception e) {
			bean = new DoNextBean(false);
		}
		return bean;
	}

	@Override
	public DoNextBean getNextInfo(String wfId, String wfInsId, String wfInsTaskId, String yw_guid) {
		DoNextBean bean = null;
		Set<String> ss = new HashSet<String>();
		try {
			TaskService taskService = JBPMServices.getInstance().getTaskService();
			ss = taskService.getOutcomes(wfInsTaskId);
			if (ss.contains("副支队长审查")) {
				ss.clear();
				String sql = "select t.JBLX from XFAJDJB t where t.YW_GUID = ?";
				List<Map<String, Object>> nextInfo = query(sql, YW, new Object[] { yw_guid });
				if ("土地".equals(nextInfo.get(0).get("JBLX"))) {
					ss.add("副支队长审查");
				} else {
					ss.add("书记审查");
				}
			}
			RepositoryService repositoryService = JBPMServices.getInstance().getRepositoryService();
			ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(
					wfId).uniqueResult();
			String name = processDefinition.getName();
			InputStream inputStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), name
					+ ".png");

			ExecutionService es = JBPMServices.getInstance().getExecutionService();
			ProcessInstance processInstance = es.findProcessInstanceById(wfInsId);
			Set<String> activityNames = processInstance.findActiveActivityNames();
			ActivityCoordinates ac = repositoryService.getActivityCoordinates(processInstance.getProcessDefinitionId(),
					activityNames.iterator().next());
			bean = new DoNextBean(inputStream, ac.getX(), ac.getY(), ac.getWidth(), ac.getHeight(), ss);
		} catch (Exception e) {
			bean = new DoNextBean(false);
		}
		return bean;
	}

	@Override
	public List<NodeDefineInfoBean> getNodeDefineInfoList(String wfID) {
		List rows = query(
				"select wfid,nodename,to_char(x) x,to_char(y) y,to_char(width) width ,to_char(height) height from ext_nodedefine_info  where wfID=?",
				WORKFLOW, new Object[] { wfID });
		List nodeList = new ArrayList();

		for (int i = 0; i < rows.size(); i++) {
			Map map = (Map) rows.get(i);
			NodeDefineInfoBean node = new NodeDefineInfoBean();
			node.setWfID(wfID);
			node.setNodeName((String) map.get("nodeName"));
			node.setHeight((Integer.parseInt((String) map.get("height"))));
			node.setWidth((Integer.parseInt((String) map.get("width"))));
			node.setX((Integer.parseInt((String) map.get("x"))));
			node.setY((Integer.parseInt((String) map.get("y"))));
			nodeList.add(node);
		}
		return nodeList;
	}

	/**
	 * <br>
	 * Description:增加，当用户组为空时，返回移交人 <br>
	 * Author:赵伟 <br>
	 * Date:2012-9-21
	 * 
	 * @see com.klspta.model.workflow.foundations.IWorkflowOp#getAllRoles(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> getAllRoles(String nodeName, String wfId) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		nodeName = nodeName.trim();
		String sql = "select groups,assignee from ext_nodedefine_info where nodename='" + nodeName + "' and wfid='"
				+ wfId + "'";
		List<Map<String, Object>> list = query(sql, WORKFLOW);
		Map<String, Object> map = list.get(0);
		String roleString = (String) map.get("groups");
		if (roleString == null) {
			String assignee = (String) map.get("assignee");
			if (assignee != null) {
				return null;
			}
		}
		String[] roles = roleString.split(",");
		for (int i = 0; i < roles.length; i++) {
			String sql1 = "select rolename from core_roles where roleid='" + roles[i] + "'";
			List<Map<String, Object>> list1 = query(sql1, CORE);
			Map<String, Object> map1 = list1.get(0);
			map1.put("roleid", roles[i]);
			result.add(map1);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getAllRoles(String nodeName, String wfId, String userId) {
		if (nodeName.equals("部门主管审批")) {
			List groupList = new ArrayList();
			try {
				String roleId = ManagerFactory.getRoleManager().getRoleBeanListByUserId(userId).get(0).getId();
				String sql = "select * from core_roles t where t.roleid = (select t2.leaderid from core_roles t2 where t2.roleid=?)";
				List<Map<String, Object>> ls = query(sql, CORE, new Object[] { roleId });
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < ls.size(); i++) {
					map.put("roleid", ls.get(i).get("roleid"));
					map.put("ROLENAME", ls.get(i).get("rolename"));
					groupList.add(map);
				}
				return groupList;
			} catch (Exception e) {
				// e.printStackTrace();
				System.out.println("当前人员没有设定主管，请在core下core_roles表中设定当前人员的主管role");
			}
		}
		IWorkflowOp workflowOp = WorkflowOp.getInstance();
		List<Map<String, Object>> Roles = workflowOp.getAllRoles(nodeName, wfId);
		return Roles;
	}

	@Override
	public String getActivityNameByWfInsID(String WfInsID) {
		Task t = JBPMServices.getInstance().getTaskService().createTaskQuery().executionId(WfInsID)
		.uniqueResult();
		if(t!=null){
			return t.getActivityName();
		}
		return JBPMServices.getInstance().getHistoryService().createHistoryTaskQuery().executionId(WfInsID).orderDesc(
				HistoryTaskQuery.PROPERTY_ENDTIME).list().get(0).getOutcome();
	}

	@Override
	public Object getVariableByWfInsID(String WfInsID, String key) {
		Object value = JBPMServices.getInstance().getExecutionService().getVariable(WfInsID, key);
		return value;
	}

	@Override
	public String getWfDescriptionByWfID(String wfID) {
		return JBPMServices.getInstance().getRepositoryService().createProcessDefinitionQuery().processDefinitionId(
				wfID).uniqueResult().getDescription();
	}

	@Override
	public String getYW_guidByWfInsID(String WfInsID) {
		return getVariableByWfInsID(WfInsID, "receiveid").toString();
	}

	@Override
	public Activity getPreActivity(String wfInsId, String activityName) {
		String sql = "select * from JBPM4_HIST_ACTINST t where t.transition_=? order by t.end_ desc";
		List<Map<String, Object>> result = query(sql, WORKFLOW, new String[] { activityName });
		String name = result.get(0).get("activity_name_").toString();
		String wfId = getWfIdByWfInsID(wfInsId);
		ProcessDefinitionImpl pd = (ProcessDefinitionImpl) JBPMServices.getInstance().getRepositoryService()
				.createProcessDefinitionQuery().processDefinitionId(wfId).uniqueResult();
		ActivityImpl currentActivity = pd.findActivity(name);
		/*
		 * ProcessDefinitionImpl pd = (ProcessDefinitionImpl)
		 * JBPMServices.getInstance().getRepositoryService()
		 * .createProcessDefinitionQuery().processDefinitionId(wfId).uniqueResult();
		 * ActivityImpl currentActivity = pd.findActivity(activityName);
		 * TransitionImpl incomingTransition = (TransitionImpl)
		 * currentActivity.getIncomingTransitions().get(0); ActivityImpl
		 * sourceActivity = incomingTransition.getSource();
		 */
		return currentActivity;
	}

	@Override
	public String getWfIdByWfInsID(String wfInsId) {
		String wfId = JBPMServices.getInstance().getHistoryService().createHistoryProcessInstanceQuery()
				.processInstanceId(wfInsId).uniqueResult().getProcessDefinitionId();
		return wfId;
	}

	private String getWorkflowName(String zfjcType) {
		String sql1 = "select t.child_name from public_code t where t.id='WORKFLOW' and t.child_id=?";
		List<Map<String, Object>> wfIdList = query(sql1, YW, new Object[] { zfjcType });
		Map<String, Object> map1 = (Map<String, Object>) wfIdList.get(0);
		String wfId = (String) map1.get("child_name");
		return wfId;
	}

	@Override
	public String getWfInsTaskIdByWfInsID(String wfInsID) {
		Task t = JBPMServices.getInstance().getTaskService().createTaskQuery().executionId(wfInsID).uniqueResult();
		if (t != null) {
			return t.getId();
		}
		return null;
	}

	@Override
	public InputStream getImageByWfID(String wfID) {
		RepositoryService repositoryService = JBPMServices.getInstance().getRepositoryService();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(
				wfID).uniqueResult();
		String name = processDefinition.getName();
		InputStream inputStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), name
				+ ".png");
		return inputStream;
	}
}
