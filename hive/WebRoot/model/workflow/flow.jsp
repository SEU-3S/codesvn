<%@ page language="java"  pageEncoding="utf-8"%>
<%@page import="java.io.InputStream"%>
<%@page import="com.klspta.base.workflow.foundations.IWorkflowOp"%>
<%@page import="com.klspta.base.workflow.foundations.WorkflowOp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
    + request.getServerPort() + path + "/";
    String resourceName=request.getParameter("resourceName");
    String wfID=request.getParameter("wfID");
    if(wfID!=null && resourceName!=null){  
    IWorkflowOp workflowOp=WorkflowOp.getInstance();
    InputStream inputStream = workflowOp.getResourceAsStream(wfID,resourceName);
	byte[] b = new byte[1024];
	int len = -1;
	while ((len = inputStream.read(b, 0, 1024)) != -1) {
	response.getOutputStream().write(b, 0, len);
	}	
	out.clear();//清空缓存内容
	out = pageContext.pushBody(); //返回一个新的BodyContent
	inputStream.close();
	}
%>
