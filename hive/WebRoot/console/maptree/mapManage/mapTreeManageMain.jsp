<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>执法监察系统</title>

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
	  	<frameset id="mapTreeMain" name="mapTreeMain" cols="500,*" frameborder="no" border="0" framespacing="0" >
		<frame id="mapTree" name="mapTree" scrolling="NO" noresize
			src="console/maptree/mapManage/mapTreeManage.jsp" />
		<frame id="mapInfo" name="mapInfo" scrolling="NO" noresize
			 />
	</frameset>
</html>
