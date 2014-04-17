<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>location</title>
		<%@ include file="/base/include/ext.jspf" %>
		<script type="text/javascript" src="<%=basePath%>/model/fxgiscomponents/location/location.js"></script>
		
		<style type="text/css">
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
	<body bgcolor="#FFFFFF">
		<div id="form-ct" style="width: 100%; height: 18%;"></div>
		<div id="form-ct2" style="width: 100%; height: 80%;"></div>
	</body>
</html>
