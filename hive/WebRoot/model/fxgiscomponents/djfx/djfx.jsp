<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.klspta.base.gis.GisConfigTools"%>
<%@page import="com.klspta.base.gis.Extent"%>
<%
	String path = request.getContextPath();
	String basePath = request.getServerName() + ":" + request.getServerPort() + path + "/";
	String gisapiPath = basePath + "thirdres/arcgis_js_api/library/2.5/arcgis_compact";
	basePath = request.getScheme() + "://" + basePath; 
	Extent extent=GisConfigTools.getInstance().getExtent();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<script>
var gisapiPath = "<%=gisapiPath%>";
</script>
	<head>
		<title>analysis</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
        <%@ include file="/base/include/ext.jspf" %>
        <script src="<%=basePath%>base/thirdres/ext/examples/ux/fileuploadfield/FileUploadField.js" type="text/javascript"></script>
         <script src="<%=basePath%>model/fxgiscomponents/djfx/djfxtop.js" type="text/javascript"></script>
         <script type="text/javascript" src="<%=basePath%>base/fxgis/framework/js/menu.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>base/thirdres/ext/examples/ux/fileuploadfield/css/fileuploadfield.css"/>
		<style type="text/css">
    html,body {
	    font: normal 12px verdana;
	    margin: 0;
	    padding: 0;
	    border: 0 none;
	    overflow: hidden;
	    height: 100%;
    }

    .upload-icon { background: url('<%=basePath%>thirdres/ext/examples/shared/icons/fam/image_add.png') no-repeat 0 0 !important;}
</style>
	</head>
	<body bgcolor="#FFFFFF">
		<div id="form-ct" style="width:100%; "></div>
		<iframe id="lower" name="lower"  style="width:110%;height:100%; overflow: auto;" src="statusTab.jsp" ></iframe>
	</body>
</html>
