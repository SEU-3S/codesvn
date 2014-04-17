<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
request.setCharacterEncoding("utf-8"); 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String vaultPath = basePath + "base/thirdres/dhtmlx//dhtmlxvault//codebase";

String yw_guid=request.getParameter("yw_guid");
String parent_file_id=request.getParameter("parent_file_id");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>upload</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css"
			href="<%=vaultPath%>/dhtmlxvault.css">
		<script src="<%=vaultPath%>/dhtmlxvault.js"></script>
    <style>
	body {font-size:12px}
	.{font-family:arial;font-size:12px}
	h1 {cursor:hand;font-size:16px;margin-left:10px;line-height:10px}
	.hdr{
		background-color:lightgrey;
		margin-bottom:10px;
		padding-left:10px;
	}
    </style>
  </head>
<script>
var vault;
function onLoad(){
	vault=new dhtmlXVaultObject();
            vault.setImagePath("<%=vaultPath%>/imgs/");
            var handlerPath="<%=basePath%>model/accessory/dzfj/need_jsp/"; 
            vault.setServerHandlers(handlerPath+"UploadHandler.jsp?parent_file_id=<%=parent_file_id%>&yw_guid=<%=yw_guid%>", handlerPath+"GetInfoHandler.jsp", handlerPath+"GetIdHandler.jsp");
            vault.setFilesLimit(20);//限制每次最多上传20个文件，若去掉限制改为0即可
            vault.strings = {
				remove: "移除",
				done: "完成",
				error: "出错",
				btnAdd: "添加",
				btnUpload: "上传",
				btnClean: "清空"
			};
			vault.onUploadComplete = function(files) {
	     		parent.document.location.reload();
            };
            vault.create("vaultDiv");
}
</script>
  <body onload="onLoad()">
<div id="vaultDiv"></div>
  </body>
</html>
