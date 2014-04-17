<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>analysis</title>
        <%@ include file="../componetsbase.jspf" %>
        <%@ include file="/common/include/ext.jspf" %>
		<script src="<%=basePath%>ext/examples/ux/fileuploadfield/FileUploadField.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>ext/examples/ux/fileuploadfield/css/fileuploadfield.css"/>
		<style type="text/css">
    html,body {
	    font: normal 12px verdana;
	    margin: 0;
	    padding: 0;
	    border: 0 none;
	    overflow: hidden;
	    height: 100%;
    }

    .upload-icon { background: url('<%=basePath%>ext/examples/shared/icons/fam/image_add.png') no-repeat 0 0 !important;}
</style>
	</head>
	<body bgcolor="#FFFFFF">
		<div id="form-ct" style="width:100%; "></div>
	</body>
</html>
