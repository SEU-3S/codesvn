package com.klspta.base.workflow.foundations;

import com.klspta.base.AbstractBaseBean;


/**
 * <br>Title:工作流工具类
 * <br>Description:
 * <br>Author:郭润沛
 * <br>Date:2011-7-1
 */
public class WorkflowUtil extends AbstractBaseBean {
    private static WorkflowUtil workItemIns = null;

    private WorkflowUtil() {

    }

    public static WorkflowUtil getInstance() {
        if (workItemIns == null) {
            workItemIns = new WorkflowUtil();
        }
        return workItemIns;
    }

  
   
}
