package com.klspta.base.workflow.foundations;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.IdentityService;
import org.jbpm.api.ManagementService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;

import com.klspta.base.AbstractBaseBean;


public class JBPMServices extends AbstractBaseBean{
    
    private static JBPMServices instance;
    private static TaskService taskService = null;
    private static ExecutionService executionService = null;
    private static HistoryService historyService = null;
    private static IdentityService identityService = null;
    private static ManagementService managementService = null;
    private static RepositoryService repositoryService = null;
    private JBPMServices(){
        ProcessEngine processEngine = getProcessEngine();
        taskService = processEngine.getTaskService();
        executionService = processEngine.getExecutionService();
        historyService = processEngine.getHistoryService();
        identityService = processEngine.getIdentityService();
        managementService = processEngine.getManagementService();
        repositoryService = processEngine.getRepositoryService();
    }
    
    public static JBPMServices getInstance(){
        if(instance == null){
            instance = new JBPMServices();
        }
        return instance;
    }
    
    public TaskService getTaskService() {
        return taskService;
    }

    public ExecutionService getExecutionService() {
        return executionService;
    }

    public HistoryService getHistoryService() {
        return historyService;
    }

    public IdentityService getIdentityService() {
        return identityService;
    }

    public ManagementService getManagementService() {
        return managementService;
    }

    public RepositoryService getRepositoryService() {
        return repositoryService;
    }
}
