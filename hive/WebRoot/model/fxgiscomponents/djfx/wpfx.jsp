<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.klspta.model.CBDReport.CBDReportManager"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
CBDReportManager cbd= new CBDReportManager();
String points = request.getParameter("points");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>卫片叠加分析</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
	table{
	    font-size: 14px;
	    background-color: #A8CEFF;
	    border-color:#000000;
	    /**
	    border-left:1dp #000000 solid;
	    border-top:1dp #000000 solid;
	    **/
	    color:#000000;
	    border-collapse: collapse;
	  }
	  tr{
	    border-width: 0px;
	    text-align:center;
	  }
	  td{
	    text-align:center;
	    border-color:#000000;
	    /**
	    border-bottom:1dp #000000 solid;
	    border-right:1dp #000000 solid;
	    **/
	  }
	  .title{
	    font-weight:bold;
	    font-size: 15px;
	    text-align:center;
	    line-height: 50px;
		margin-top: 3px;
	  }
	  .trtotal{
	  	text-align:center;
	    font-weight:bold;
	    line-height: 30px;
	   }
	  .trsingle{
	    background-color: #D1E5FB;
	    line-height: 20px;
	    text-align:center;
	   }
	</style>
  </head>
  <body>
  <center>
  	<div align="center" style="font-weight: bold; font-size: 18pt; font-family: 宋体">
					叠加分析结果表
			</div>
			<br>
  			<%=cbd.getReport("DJFX",new Object[]{points,"0"}) %>
	</center>
  </body>
</html>
