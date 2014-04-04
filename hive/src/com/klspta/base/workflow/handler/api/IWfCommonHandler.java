package com.klspta.base.workflow.handler.api;

import java.util.HashMap;

/**
 * 
 * <br>Title: 工作流全局助手类接口
 * <br>Description:只有类名称与工作流名称一样的类才可以继承自该接口
 * <br>Author:王瑛博
 * <br>Date:2011-7-5
 */
public abstract interface IWfCommonHandler {
    
     //执行工作流全局方法
    /**
     * <br>Description:方法功能描述
     * <br>Author:赵伟
     * <br>Date:2013-4-8
     * @param map里面放的参数
     * key:activityName  value:节点名称
     * key:wfInsID       value:实例ID
     */
    public abstract void doCommonInsMethed(HashMap<String, Object> map);
    
}
