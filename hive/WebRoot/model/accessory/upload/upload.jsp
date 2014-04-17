<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String yw_guid = request.getParameter("yw_guid");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>附件上传</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
    <%@ include file="/base/include/restRequest.jspf" %>
    <%@ include file="/base/include/ext.jspf" %>
  <script type="text/javascript">
	function change(){
		document.getElementById("submit").disabled = false;  
	}
	function init(){
		document.getElementById("hidden_frame").src = "<%=basePath %>model/accessory/upload/UploadHandler.jsp?yw_guid=<%=yw_guid %>";        
	} 
  </script>
<body onload="init();" >
	<form action="<%=basePath %>model/accessory/upload/UploadHandler.jsp?yw_guid=<%=yw_guid %>" id="form1" name="form1" encType="multipart/form-data"  method="post" target="hidden_frame" >
    	<input type="file" id="file" name="file" style="width:500" onchange="change();" />
    	<input id="submit" type="submit" name="submit" size="50" value="上传" disabled="disabled"  /> 
   		<iframe name='hidden_frame' id="hidden_frame" style="width: 595;height:550" frameborder="no" border="0" marginwidth="0" marginheight="0" ></iframe> 
    </form> 
</body>
</html>