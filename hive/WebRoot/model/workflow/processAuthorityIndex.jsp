<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
             String deploymentId=request.getParameter("deploymentId");
    String resourceName=request.getParameter("resourceName");
    String wfID=request.getParameter("wfID");
 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>流程模板授权</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<style>
html,body {
	font: normal 12px verdana;
	margin: 0;
	padding: 0;
	border: 0 none;
	overflow: hidden;
	height: 100%;
}

</style>
	</head>
	  	<frameset id="processIndex" name="processIndex" cols="75%,*" frameborder="no" border="0" framespacing="0" >
		<frame id="wf" name="wf"  noresize
			src="<%=basePath%>model/workflow/authority.jsp?wfID=<%=wfID%>&resourceName=<%=resourceName%>" />
		<frame id="tree" name="tree" scrolling="NO" noresize
			 src="<%=basePath%>model/workflow/tree.jsp?nodeName=承办&wfId=<%=wfID%>" />
	</frameset>
</html>
