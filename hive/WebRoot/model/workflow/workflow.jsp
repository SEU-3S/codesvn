
<%@page import="org.jbpm.api.task.Task"%>
<%@page import="org.jbpm.api.ProcessInstance"%>
<%@page import="org.jbpm.api.ProcessDefinition"%>
<%@page import="java.util.List"%>
<%@ page language="java"  pageEncoding="utf-8"%>
<%@page import="com.klspta.base.workflow.foundations.IWorkflowOp"%>
<%@page import="com.klspta.base.workflow.foundations.WorkflowOp"%>
<%@page import="com.klspta.base.workflow.foundations.IWorkflowInsOp"%>
<%@page import="com.klspta.base.workflow.foundations.WorkflowInsOp"%>
<%@page import="com.klspta.base.workflow.bean.WorkItemBean"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
    + request.getServerPort() + path + "/";
    


    IWorkflowOp work = WorkflowOp.getInstance();
    List<ProcessDefinition> pdList = work.getWFList();
    IWorkflowInsOp workIns = WorkflowInsOp.getInstance();
    List<ProcessInstance> piList = workIns.getProcessInstanceList();
    List<Task> taskList = workIns.getAllTaskListByUserName("wangf");
    List<Task> personalTaskList = workIns.getPersonalTaskListByUserName("wangf");
    List<Task> groupTaskList = workIns.getGroupTaskListByUserName("wangf");
    
    
    
    
    String op = request.getParameter("op");
    String id = request.getParameter("id");
    
    
    if(id != null){
        work.getNextInfo(id);
    }
    
    if(op!= null && op.equals("true")){
        work.deploy("C://aaa.zip");///com/klspta/workflow/resources/workflow.zip
    }else if(op != null && op.equals("start")){
        work.start(id, new WorkItemBean("wangf", "21023304040", "这个是用中文的名字哦", "编号￥%……&*","执法监察","普通案卷查处流程"));
    }else if(op != null && op.equals("remove")){
        work.delete(id);
    }else if(op != null && op.equals("donext")){
       // workIns.doNext(id, "wangf", "王峰");
    }else if(op != null && op.equals("idoit")){
        workIns.iDoIt(id, "wangf");
    }else if(op != null && op.equals("his")){
        //workIns.getInsHistory(id);
    }
    else if(op != null && op.equals("del")){
        workIns.deleteWfIns(id);
    }

    

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>index</title>
  </head>
  <body> 

    <table border="1" width="100%">
      <caption><a href="workflow.jsp">流程定义</a></caption>
      <thead>
        <tr>
          <td>id</td>
          <td>name</td>
          <td>version</td>
          <td>&nbsp;</td>
        </tr>
      </thead>
      <tbody>
<%
	for (ProcessDefinition pd : pdList) {
%>
	    <tr>
	      <td><%=pd.getId() %></td>
	      <td><%=pd.getName() %></td>
	      <td><%=pd.getVersion() %></td>
	      <td><%=pd.getDescription() %></td>
	      <td>
	        <a href="workflow.jsp?op=remove&id=<%=pd.getDeploymentId() %>">remove</a>
	        &nbsp;|&nbsp;
	        <a href="workflow.jsp?op=start&id=<%=pd.getId() %>">start</a>
	      </td>
	    </tr>
<%
	}
%>
	  </tbody>
	</table> 

    <table border="1" width="100%">
      <caption>流程实例</caption>
      <thead>
        <tr>
          <td>id</td>
          <td>activity</td>
          <td>state</td>
          <td>&nbsp;</td>
        </tr>
      </thead>
      <tbody>
<%
	for (ProcessInstance pi : piList) {
%>
	    <tr>
	      <td><%=pi.getId() %></td>
	      <td><%=pi.findActiveActivityNames() %></td>
	      <td><%=pi.getState() %></td>
	      <td><a href="workflow.jsp?op=del&id=<%=pi.getId() %>">del</a></td>
	    </tr>
<%
	}
%>
	  </tbody>
	</table> 

    <table border="1" width="100%">
      <caption>所有待办任务</caption>
      <thead>
        <tr>
          <td>id</td>
          <td>节点名称</td>
          <td>任务名称</td>
          <td>任务编号</td>
          <td>接件编号</td>
          <td>大类</td>
          <td>小类</td>
          <td>&nbsp;</td>
        </tr>
      </thead>
      <tbody>
<%
	for (Task task : taskList) {
%>
	    <tr>
	      <td><%=task.getId() %></td>
	      <td><%=task.getName() %></td>
	      <td><%=workIns.getParameter(task.getId(), "name") %></td>
	      <td><%=workIns.getParameter(task.getId(), "no") %></td>
	      <td><%=workIns.getParameter(task.getId(), "receiveid") %></td>
	      <td><%=workIns.getParameter(task.getId(), "typeOne") %></td>
	      <td><%=workIns.getParameter(task.getId(), "typeTwo") %></td>
	      <td><a href="workflow.jsp?op=donext&id=<%=task.getId() %>">转发</a></td>
	    </tr>
<%
	}
%>
	  </tbody>
	</table> 
	
	
	   <table border="1" width="100%">
      <caption>个人待办任务</caption>
      <thead>
        <tr>
          <td>id</td>
          <td>节点名称</td>
          <td>任务名称</td>
          <td>任务编号</td>
          <td>接件编号</td>
          <td>大类</td>
          <td>小类</td>
          <td>&nbsp;</td>
        </tr>
      </thead>
      <tbody>
<%
	for (Task task : personalTaskList) {
%>
	    <tr>
	      <td><%=task.getId() %></td>
	      <td><%=task.getName() %></td>
	      <td><%=workIns.getParameter(task.getId(), "name") %></td>
	      <td><%=workIns.getParameter(task.getId(), "no") %></td>
	      <td><%=workIns.getParameter(task.getId(), "receiveid") %></td>
	      <td><%=workIns.getParameter(task.getId(), "typeOne") %></td>
	      <td><%=workIns.getParameter(task.getId(), "typeTwo") %></td>
	      <td><a href="workflow.jsp?op=donext&id=<%=task.getId() %>">转发</a></td>
	    </tr>
<%
	}
%>
	  </tbody>
	</table> 
	
	
	   <table border="1" width="100%">
      <caption>组内待办任务</caption>
      <thead>
        <tr>
          <td>id</td>
          <td>节点名称</td>
          <td>任务名称</td>
          <td>任务编号</td>
          <td>接件编号</td>
          <td>大类</td>
          <td>小类</td>
          <td>&nbsp;</td>
        </tr>
      </thead>
      <tbody>
<%
	for (Task task : groupTaskList) {
%>
	    <tr>
	      <td><%=task.getId() %></td>
	      <td><%=task.getName() %></td>
	      <td><%=workIns.getParameter(task.getId(), "name") %></td>
	      <td><%=workIns.getParameter(task.getId(), "no") %></td>
	      <td><%=workIns.getParameter(task.getId(), "receiveid") %></td>
	      <td><%=workIns.getParameter(task.getId(), "typeOne") %></td>
	      <td><%=workIns.getParameter(task.getId(), "typeTwo") %></td>
	      <td><a href="workflow.jsp?op=idoit&id=<%=task.getId() %>">签收</a></td>
	    </tr>
<%
	}
%>
	  </tbody>
	</table> 
	
	
  </body>
</html>