package com.klspta.base.workflow.handler.api;

import java.util.HashMap;

/**
 * 
 * <br>Title:工作流节点助手类
 * <br>Description:在节点做【移交】【回退】等操作的前后需进行一些业务操作的情况，需实现此接口
 * <br>Author:赵伟
 * <br>Date:2013-3-27
 */
public interface IWfHandler {

    //当前节点移交执行前方法
    /**
     * <br>Description:方法功能描述
     * <br>Author:赵伟
     * <br>Date:2013-4-8
     * @param map里面放的参数
     * key:activityName  value:节点名称
     * key:wfInsID       value:实例ID
     */
    void preDoNext(HashMap<String, Object> map);
    
    //当前节点移交执行后方法
    void afterDoNext(HashMap<String, Object> map);

    //当前节点回退执行前方法
    void preRollBack(HashMap<String, Object> map);
    
    //当前节点回退执行后方法
    void afterRollBack(HashMap<String, Object> map);
    
}
