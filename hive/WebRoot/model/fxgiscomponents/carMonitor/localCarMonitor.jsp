<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
var restUrl = "";

if(restUrl == null || restUrl == ""){
	var query = window.location.href;
	var ss = query.split("/");
	restUrl = "http://" + ss[2] + "/gisland/service/rest/";
}
</script>
        <%@ include file="/base/include/ext.jspf" %>
<script type="text/javascript" src="<%=basePath%>gisapp/pages/framework/frameworkHttpRequest.js"></script>
<script type="text/javascript" src="localCarMonitor.js"></script>
<style type="text/css">
html,body {
	margin: 0;
	padding: 0;
	margin-right: 1;
	height: 100%;
	background-color: #FFFFFF;
	font: normal 12px verdana;
}

.los {
	width: 100%;
	height: 30px;
	line-height:30px;
	background-image: url('images/left/left_bg.PNG');
}
</style>
<SCRIPT LANGUAGE=javascript FOR=MSComm1 EVENT=OnComm>     
// MSComm1控件每遇到 OnComm 事件就调用 MSComm1_OnComm()函数    
MSComm1_OnComm()    
</SCRIPT>   

</head>

<body >
<div width:100%;height:100%;overflow:auto;">
<table width=80>
<tr>
<td><input  id="start" name="start" type="button" onclick='OpenPort()' value="开始跟踪" style="color:#00509F"/></td>
<!--td><input  id="autoCenter" name="autoCenter" type="button" onclick='change()' value="暂停跟踪" style="color:#00509F" disabled="disabled" /></td-->
<td><input  id="end" name="end" type="button" onclick='closePort()' value="停止跟踪" style="color:#00509F" disabled="disabled" /></td>
<td><input id='wxgs' name='wxgs' value='获取中' disabled="disabled" size=10></td>
</tr>
</table>
</div>
<OBJECT CLASSID="clsid:648A5600-2C6E-101B-82B6-000000000014" id=MSComm1 codebase="MSCOMM32.OCX" type="application/x-oleobject"    
style="LEFT: 54px; TOP: 14px" >    
<PARAM NAME="CommPort" VALUE="5">    
<PARAM NAME="DTREnable" VALUE="1">    
<PARAM NAME="Handshaking" VALUE="0">    
<PARAM NAME="InBufferSize" VALUE="1024">    
<PARAM NAME="InputLen" VALUE="0">    
<PARAM NAME="NullDiscard" VALUE="0">    
<PARAM NAME="OutBufferSize" VALUE="512">    
<PARAM NAME="ParityReplace" VALUE="?">    
<PARAM NAME="RThreshold" VALUE="1">    
<PARAM NAME="RTSEnable" VALUE="1">    
<PARAM NAME="SThreshold" VALUE="2">    
<PARAM NAME="EOFEnable" VALUE="0">    
<PARAM NAME="InputMode" VALUE="0">   

<PARAM NAME="DataBits" VALUE="8">    
<PARAM NAME="StopBits" VALUE="1">    
<PARAM NAME="BaudRate" VALUE="4800">    
<PARAM NAME="Settings" VALUE="4800,N,8,1">
</OBJECT>
<!--font color='#00509F' size=2><br>周围卫片图斑信息:</font-->
		<!--div id="status_grid"></div-->
<!--input  id="buffer" name="buffer" type="button" onclick='buffer()' value="手动分析" style="color:#00509F"/-->
</body>
</html>
<script>
var width=document.body.clientWidth-10;
</script>
