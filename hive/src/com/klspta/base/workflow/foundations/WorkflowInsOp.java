package com.klspta.base.workflow.foundations;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

import org.jbpm.api.JbpmException;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.history.HistoryTask;
import org.jbpm.api.history.HistoryTaskQuery;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;

public class WorkflowInsOp implements IWorkflowInsOp {

	private static WorkflowInsOp instance = null;

	private static final String HANDLER_HOME = "com.klspta.base.workflow.handler.";
	
	private static HanyuPinyinOutputFormat spellFormat = new HanyuPinyinOutputFormat();

	private WorkflowInsOp() {
	}

	public static IWorkflowInsOp getInstance() {
		if (instance == null) {
			instance = new WorkflowInsOp();
			spellFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
			spellFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			spellFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		}
		return instance;
	}

	@Override
	public List<Task> getAllTaskListByUserName(String userName) {
		// 先取出个人的
		List<Task> taskList = JBPMServices.getInstance().getTaskService().findPersonalTasks(userName);
		List<Task> task = null;
		task = JBPMServices.getInstance().getTaskService().findGroupTasks(userName);
		taskList.addAll(task);
		return taskList;
	}

	@Override
	public List<Task> getPersonalTaskListByUserName(String userName) {
		List<Task> taskList = JBPMServices.getInstance().getTaskService().findPersonalTasks(userName);
		return taskList;
	}

	@Override
	public List<Task> getGroupTaskListByUserName(String userName) {
		List<Task> taskList = null;
		taskList = JBPMServices.getInstance().getTaskService().findGroupTasks(userName);
		return taskList;
	}

	@Override
	public List<ProcessInstance> getProcessInstanceList() {
		List<ProcessInstance> piList = JBPMServices.getInstance().getExecutionService().createProcessInstanceQuery()
				.list();
		return piList;
	}

	@Override
	public Object getParameter(String wfInsTaskID, String parKey) {
		Object par = JBPMServices.getInstance().getTaskService().getVariable(wfInsTaskID, parKey);
		return par;
	}

	@Override
	public void iDoIt(String wfInsTaskID, String userName) {
		JBPMServices.getInstance().getTaskService().takeTask(wfInsTaskID, userName);
	}

	@Override
	public void deleteWfInsTask(String taskID) {
		JBPMServices.getInstance().getTaskService().deleteTask(taskID);
	}

	@Override
	public void deleteWfIns(String wfInsID) {
		JBPMServices.getInstance().getExecutionService().deleteProcessInstance(wfInsID);
	}

	@Override
	public void doNext(String taskID) {
		doNext(taskID, null);
	}

	@Override
	public void doNext(String taskID, String nextNodeName) {
		_doNext(taskID, nextNodeName);
	}

	public void _doNext(String taskID, String nextNodeName) {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
	        parameters.put("taskID", taskID);
	        parameters.put("activityName", nextNodeName);
	        
	        String wfInsId=JBPMServices.getInstance().getTaskService().getTask(taskID).getExecutionId();
	        parameters.put("wfInsID", wfInsId);
	        String wfId=JBPMServices.getInstance().getExecutionService().findExecutionById(wfInsId).getProcessDefinitionId();
	        String key=JBPMServices.getInstance().getRepositoryService().createProcessDefinitionQuery().processDefinitionId(wfId).uniqueResult().getName();
	        String className = PinyinHelper.toHanyuPinyinString(nextNodeName, spellFormat ,"");
            className = className.replaceFirst(className.substring(0, 1), className.substring(0, 1).toUpperCase()) ;
	        
			invokeMe(HANDLER_HOME+key+"."+PinyinHelper.toHanyuPinyinString(className, spellFormat, ""), new Object[]{parameters}, "preDoNext");
			if (nextNodeName != null) {
				JBPMServices.getInstance().getTaskService().completeTask(taskID, nextNodeName);
			} else {
				JBPMServices.getInstance().getTaskService().completeTask(taskID);
			}
			invokeMe(HANDLER_HOME+key+"."+PinyinHelper.toHanyuPinyinString(className, spellFormat, ""), new Object[]{parameters}, "afterDoNext");
		} catch (JbpmException e) {
			e.printStackTrace();
			System.out.println("出错啦！没有连接线的名字叫： " + nextNodeName);
		}
	}

	public Object invokeMe(String classPath, Object[] args, String methodName) {
		Object returnValue=null;
		try {
			Class<?> c = Class.forName(classPath);
			Constructor<?> ctor = c.getConstructor();
			Object object = ctor.newInstance();
			Class<?>[] argsClass = new Class[args.length];
			for (int i = 0, j = args.length; i < j; i++) {
				argsClass[i] = args[i].getClass();
			}
			Method method = null;
			method = object.getClass().getMethod(methodName, argsClass);
			returnValue=method.invoke(object, args);
		} catch (ClassNotFoundException e) {
			System.out.println("未找到  " + classPath + ".java   实现类。");
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
			System.out.println("类   " + classPath + " 需要实现 ：IWfHandler接口。");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	@Override
	public boolean back(String wfId, String wfInsId, String wfInsTaskId, String activityName) throws Exception {
		return jumpToPreTask(wfId, wfInsId, wfInsTaskId, activityName);
	}

	/**
	 * <br>
	 * Description:工作流回退 <br>
	 * Author:赵伟 <br>
	 * Date:2012-9-26
	 * 
	 * @param wfId
	 * @param wfInsId
	 * @param wfInsTaskId
	 * @param sourceName
	 * @return
	 * @throws Exception
	 */
	private boolean jumpToPreTask(String wfId, String wfInsId, String wfInsTaskId, String activityName)
			throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("wfId", wfId);
        parameters.put("wfInsID", wfInsId);
        parameters.put("wfInsTaskId", wfInsTaskId);
        parameters.put("activityName", activityName);
        
        String className = PinyinHelper.toHanyuPinyinString(activityName, spellFormat ,"");
        className = className.replaceFirst(className.substring(0, 1), className.substring(0, 1).toUpperCase()) ;
        String key=JBPMServices.getInstance().getRepositoryService().createProcessDefinitionQuery().processDefinitionId(wfId).uniqueResult().getName();
        invokeMe(HANDLER_HOME+key+"."+PinyinHelper.toHanyuPinyinString(className, spellFormat, ""), new Object[]{parameters}, "preRollBack");
		
		boolean b = true;
		// 得到流程定义
		ProcessDefinitionImpl pd = (ProcessDefinitionImpl) JBPMServices.getInstance().getRepositoryService()
				.createProcessDefinitionQuery().processDefinitionId(wfId).uniqueResult();
		// 获取当前任务的活动节点
		ActivityImpl currentActivity = pd.findActivity(activityName);
		// 获取该活动节点的IncomingTransition
		TransitionImpl incomingTransition = (TransitionImpl) currentActivity.getIncomingTransitions().get(0);
		// 获取IncomingTransition的源活动节点
		ActivityImpl sourceActivity = incomingTransition.getSource();
		if (sourceActivity.getType().equals("start")) {
			b = false;
			return b;
		}
		TransitionImpl backTransition = currentActivity.createOutgoingTransition();

		// 当前活动节点为新的回退Transition的源，而原来的"源"活动节点变成了目标活动节点
		backTransition.setSource(currentActivity);
		backTransition.setDestination(sourceActivity);
		String incomingTransitionName = incomingTransition.getName();
		backTransition.setName(incomingTransitionName);

		//执行动态创建的transmission进行回退。
		doNext(wfInsTaskId, incomingTransitionName);

		try {
			// 获取回退后新创建的节点Id
			String wfInsTaskID = JBPMServices.getInstance().getTaskService().createTaskQuery().executionId(wfInsId)
					.uniqueResult().getId();
			HistoryTask preTask = JBPMServices.getInstance().getHistoryService().createHistoryTaskQuery().executionId(
					wfInsId).outcome(activityName).orderDesc(HistoryTaskQuery.PROPERTY_CREATETIME).list().get(1);
			String preAssignee = preTask.getAssignee();
			// 重新让前面的工作人员将任务拾起来
			iDoIt(wfInsTaskID, preAssignee);
			invokeMe(HANDLER_HOME+key+"."+PinyinHelper.toHanyuPinyinString(className, spellFormat, ""), new Object[]{parameters}, "afterRollBack");
		} catch (Exception e) {
			return b;
		}
		return b;
	}
	public static void main(String[] args) {
		ProcessDefinition p= JBPMServices.getInstance().getRepositoryService()
		.createProcessDefinitionQuery().processDefinitionId("OA-1").uniqueResult();
		String str=p.getDescription();
		System.out.println(str);
	}
}
