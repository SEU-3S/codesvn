<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String point = request.getParameter("point");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>点标注分析</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
 	<%@ include file="/base/include/restRequest.jspf" %>

  </head>
  <style type="text/css">
  	table {
		border-collapse:collapse;
		border:none;
	}
	table td{
		border:solid #000 1px;
	}
  </style>
  <script type="text/javascript">
  	function initData(){
		putClientCommond("proanalyse","showInfo");
		putRestParameter("point","<%=point%>");
		var result = restRequest(); 
		document.getElementById('info').innerHTML = result; 		
  	}
  </script>
  <body onload="initData()" style="margin:0 0 0 0;">
		<div align="center" style="font-weight: bold; font-size: 12pt; font-family: 宋体;margin-top:20px;">
			叠加分析结果表
		</div>
		<center>
    		<div  id="info" style="width:100%;height:80%;margin-top:20px;text-align:left;margin-left:30px;"></div>
    	</center>
  </body>
</html>
