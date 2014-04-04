package com.klspta.base.workflow.foundations;

import java.util.List;

import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;


/**
 * 
 * <br>Title:工作流模板操作接口
 * <br>Description:提供工作流模板操作的接口
 * <br>Author:王瑛博
 * <br>Date:2011-6-28
 */
public interface IWorkflowInsOp {
    
    /**
     * 
     * <br>Description:根据人员名称获取个人待办项
     * <br>Author:王瑛博
     * <br>Date:2011-6-28
     * @return
     */
    List<Task> getPersonalTaskListByUserName(String userName);
    
    /**
     * 
     * <br>Description:根据人员名称获取组待办项
     * <br>Author:王瑛博
     * <br>Date:2011-6-28
     * @return
     */
    List<Task> getGroupTaskListByUserName(String userName);
    
    /**
     * 
     * <br>Description:根据人员名称获取所有待办项
     * <br>Author:王瑛博
     * <br>Date:2011-6-28
     * @return
     */
    List<Task> getAllTaskListByUserName(String userName);
    
    /**
     * 
     * <br>Description:根据角色名称获取待办项
     * <br>Author:王瑛博
     * <br>Date:2011-6-28
     * @return
     */
    List<ProcessInstance> getProcessInstanceList();
    
    /**
     * 
     * <br>Description:获取指定的节点的指定参数
     * <br>Author:王瑛博
     * <br>Date:2011-6-28
     * @param wfInsTaskID 节点ID
     * @param parKey 参数关键字
     * @return
     */
    Object getParameter(String wfInsTaskID, String parKey);
    
    /**
     * 
     * <br>Description:签收任务
     * <br>Author:王瑛博
     * <br>Date:2011-6-29
     * @param wfInsTaskID 流程实例的task ID
     * @param userName 签收人名称
     */
    void iDoIt(String wfInsTaskID, String userName);
    
    /**
     * 
     * <br>Description:删除节点
     * <br>Author:王瑛博
     * <br>Date:2011-6-28
     * @param wfInsID 工作流实例ID
     * @param parameters 下一节点线的名称
     * @return
     */
    void deleteWfInsTask(String wfInsTaskID);
    /**
     * 
     * <br>Description:删除流程实例
     * <br>Author:王瑛博
     * <br>Date:2011-6-28
     * @param wfInsID 工作流实例ID
     * @param parameters 下一节点线的名称
     * @return
     */
    void deleteWfIns(String wfInsID);
    /**
     * <br>Description:工作流流转
     * <br>Author:赵伟
     * <br>Date:2012-9-24
     * @param wfInsTaskID 工作流实例节点ID
     * @param nextNodeName 工作流下一节点ID
     */
    void doNext(String wfInsTaskID, String nextNodeName);
    
    /**
     * 
     * <br>Description:工作流流转
     * <br>Author:王瑛博
     * <br>Date:2011-6-28
     * @param wfInsID 工作流实例ID
     * @return
     */
    void doNext(String wfInsTaskID);
    

    /**
     * <br>Description:工作流回退
     * <br>Author:赵伟
     * <br>Date:2012-9-26
     * @param wfId 流程定义Id
     * @param wfInsId 流程实例Id
     * @param wfInsTaskId 流程实例任务Id
     * @param activityName 流程节点Id
     * @return
     * @throws Exception
     */
    boolean back(String wfId, String wfInsId, String wfInsTaskId, String activityName) throws Exception;

}
