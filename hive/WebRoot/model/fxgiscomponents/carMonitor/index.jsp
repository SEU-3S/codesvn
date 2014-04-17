﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>执法监管系统</title>

</head>
  	<frameset id="index" name="index" rows="25,*" frameborder="no" border="0"  framespacing="0" >
		<frame id="monitor" name="flash" scrolling="NO" noresize
			src="<%=basePath%>model/giscomponents/carMonitor/localCarMonitor.jsp" />
		<frame id="list" name="menu" scrolling="NO" noresize
			src="<%=basePath%>model/dc/pages/taskTab.jsp" />
	</frameset>
</html>
