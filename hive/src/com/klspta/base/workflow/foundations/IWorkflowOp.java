package com.klspta.base.workflow.foundations;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.model.Activity;

import com.klspta.base.workflow.bean.DoNextBean;
import com.klspta.base.workflow.bean.NodeDefineInfoBean;
import com.klspta.base.workflow.bean.WorkItemBean;


/**
 * 
 * <br>Title:工作流实例操作接口
 * <br>Description:提供工作流实例才做接口
 * <br>Author:王瑛博
 * <br>Date:2011-6-28
 */
public interface IWorkflowOp {
    
    /**
     * 
     * <br>Description:发布工作流
     * <br>Author:王瑛博
     * <br>Date:2011-6-28
     * @param path 工作流文件所在服务器位置
     * @return
     */
    String deploy(String path) throws Exception;
    
    /**
     * 
     * <br>Description:获得工作流定义列表
     * <br>Author:王瑛博
     * <br>Date:2011-6-28
     * @param pdList 工作流定义列表
     * @return
     */
    List<ProcessDefinition> getWFList();
    /**
     * <br>Description:根据工作流定义ID 获得工作流节点定义信息列表
     * <br>Author:郭润沛
     * <br>Date:2011-11-4
     * @param wfID 工作流定义ID
     * @return
     */
    List<NodeDefineInfoBean> getNodeDefineInfoList(String wfID);
    /**
     * 
     * <br>Description:删除工作流
     * <br>Author:王瑛博
     * <br>Date:2011-6-28
     * @param wfID 工作流模板ID
     * @param parameters 参数
     * @return 返回工作流实例ID
     */
    void delete(String wfID);
    
    /**
     * 
     * <br>Description:启动工作流
     * <br>Author:王瑛博
     * <br>Date:2011-6-28
     * @param wfID 工作流模板ID
     * @param parameters 参数
     * @return 返回工作流实例ID
     */
    String start(String wfID, WorkItemBean wiBean);
    
    /**
     * 
     * <br>Description:启动工作流
     * <br>Author:王瑛博
     * <br>Date:2011-6-29
     * @param wfID
     * @param receiveid
     * @return
     */
    String start(String wfID, String creater, String receiveid);
    
    /**
     * 
     * <br>Description:启动工作流
     * <br>Author:王瑛博
     * <br>Date:2011-6-28
     * @param wfID 工作流模板ID
     * @param parameters 参数
     * @return 返回工作流实例ID
     */
    String start(String wfID, Map<String, Object> parameters);
    
    /**
     * 
     * <br>Description:获取下一节点相关信息流
     * <br>Author:王瑛博
     * <br>Date:2011-7-1
     * @param wfInsTaskId 节点id
     * @return
     */
    DoNextBean getNextInfo(String wfInsTaskId);
    /**
     * <br>Description:获取下一节点相关信息流
     * <br>Author:赵伟
     * <br>Date:2012-9-26
     * @param wfId 工作流定义ID
     * @param wfInsTaskId 工作流实例节点ID
     * @return
     */
    DoNextBean getNextInfo(String wfId,String wfInsId,String wfInsTaskId);

    
    
    
    
    /**
     * <br>Description:获取下一节点相关信息流（因工作流中没有实现decision，只能手写固定）
     * <br>Author:黎春行
     * <br>Date:2012-11-14
     * @param wfId
     * @param wfInsId
     * @param wfInsTaskId
     * @return
     */
    DoNextBean getNextInfo(String wfId,String wfInsId,String wfInsTaskId, String yw_guid);
    
    /**
     * 
     * <br>Description:获取工作流图形
     * <br>Author:郭润沛
     * <br>Date:2011-11-8
     * @param deploymentId 节点id
     * @param resourceName :图片名称，如：XFGL.png
     * @return
     */
    InputStream getResourceAsStream(String deploymentId, String resourceName);
    
    /**
     * 
     * <br>Description:根据WFID获取ZFJCTYPE
     * <br>Author:王峰
     * <br>Date:2011-11-8
     * @param wfID
     * @return
     */
    public String getZfjcType(String wfId);

    /**
     * 
     * <br>Description:根据nodename,wfid获取组织机构
     * <br>Author:王峰
     * <br>Date:2011-11-8
     * @param wfID,nodename
     * @return
     */
	List<Map<String, Object>> getAllRoles(String nodeName, String wfId);
	
	/**
	 * 
	 * <br>Description:根据nodenane， wfid， username获取组织机构
	 * <br>Author:黎春行
	 * <br>Date:2013-1-24
	 * @param nodeName
	 * @param wfId
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> getAllRoles(String nodeName, String wfId, String userId);

	/**
	 * <br>Description:根据流程实例ID查询流程到了哪个节点
	 * <br>Author:赵伟
	 * <br>Date:2013-4-2
	 * @param WfInsID
	 * @return
	 */
	String getActivityNameByWfInsID(String WfInsID);
	
	/**
	 * <br>Description:根据流程实例ID查询启动流程的业务ID
	 * <br>Author:赵伟
	 * <br>Date:2013-4-2
	 * @param WfInsID
	 * @return
	 */
	String getYW_guidByWfInsID(String WfInsID);
	
	/**
	 * <br>Description:根据流程实例ID以及KEY获取变量
	 * <br>Author:赵伟
	 * <br>Date:2013-4-2
	 * @param WfInsID
	 * @param key
	 * @return
	 */
	Object getVariableByWfInsID(String WfInsID,String key);
	
	/**
	 * <br>Description:根据流程定义ID获取流程描述
	 * <br>Author:赵伟
	 * <br>Date:2013-4-2
	 * @param wfID
	 * @return
	 */
	String getWfDescriptionByWfID(String wfID);
	
	/**
	 * <br>Description:实例ID获取流程定义ID
	 * <br>Author:赵伟
	 * <br>Date:2013-4-8
	 * @param wfInsId
	 * @return
	 */
	String getWfIdByWfInsID(String wfInsId);
	/**
	 * <br>Description:根据定义ID，当前节点获取上一个节点
	 * <br>Author:赵伟
	 * <br>Date:2013-4-8
	 * @param wfId
	 * @param activityName
	 * @return
	 */
	Activity getPreActivity(String wfInsID,String activityName);
	
	/**
	 * <br>Description:根据实例ID获取当前任务ID
	 * <br>Author:赵伟
	 * <br>Date:2013-6-18
	 * @param wfInsID
	 * @return
	 */
	String getWfInsTaskIdByWfInsID(String wfInsID);
	
	/**
	 * <br>Description:根据流程定义ID获取流程图片
	 * <br>Author:赵伟
	 * <br>Date:2013-6-19
	 * @param wfID
	 * @return
	 */
	InputStream getImageByWfID(String wfID);
}
