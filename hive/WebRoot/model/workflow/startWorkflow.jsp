<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="com.klspta.console.user.User"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//获取当前登录用户
Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//String fullName = ((User) principal).getFullName();
String userId = ((User)principal).getUserID();
String zfjcType=request.getParameter("zfjcType");
String beanId=request.getParameter("beanId");
String returnPath = request.getParameter("returnPath");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>案件立案</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

    <%@ include file="/base/include/restRequest.jspf" %>
    <%@ include file="/base/include/ext.jspf" %>
	<script type="text/javascript">
	//初始化
	function firstInit()
	{
	  putClientCommond("<%=beanId%>","initWorkflow");
      putRestParameter("zfjcType","<%=zfjcType%>");
      putRestParameter("userId", "<%=userId%>");
      putRestParameter("returnPath","<%=returnPath%>");
      var path=restRequest();
      document.location.href="<%=basePath%>"+path+"&edit=true";
	}  
	</script>
  </head>
  
  <body onload="firstInit();">
    
  </body>
</html>
