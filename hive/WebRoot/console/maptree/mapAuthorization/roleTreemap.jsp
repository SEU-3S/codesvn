<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.klspta.console.ManagerFactory"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
    String tree =ManagerFactory.getRoleManager().getRoleListExtJson();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>zzjgTree</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<%@ include file="/base/include/ext.jspf" %>

		<style>
body {
	font-family: helvetica, tahoma, verdana, sans-serif;
	padding: 0px;
scrollbar-3dlight-color:#D4D0C8; 
  scrollbar-highlight-color:#fff; 
  scrollbar-face-color:#E4E4E4; 
  scrollbar-arrow-color:#666; 
  scrollbar-shadow-color:#808080; 
  scrollbar-darkshadow-color:#D7DCE0; 
  scrollbar-base-color:#D7DCE0; 
  scrollbar-track-color:#;

}
</STYLE>
	</head>
	<script>
	var tree;
Ext.onReady(function(){
    tree = new Ext.tree.TreePanel({
        el:'jgTree',
        useArrows:true,
        autoScroll:true,
        animate:true,
        enableDD:true,
        margins: '2 2 0 2',
        autoScroll: true,
        border: false,
        containerScroll: true,
        rootVisible: false,
        frame: true,
        loader: new Ext.tree.TreeLoader(),
        root: new Ext.tree.AsyncTreeNode({
            expanded: false,
            children: <%=tree%>
        }),
         listeners: {
         'click': function(node, e){
                var nodeid=node.attributes.id;
                var parentRoleId;
                var nodeName=node.attributes.text;
                if(node.leaf){
                parentRoleId=node.attributes.parentId;
                }else{
                parentRoleId='0';
                }
                var url="<%=basePath%>/console/maptree/mapAuthorization/mapAuthorTree.jsp?roleId="+nodeid+"&nodeName="+nodeName;
                url=encodeURI(encodeURI(url));
                parent.info.location.href=url;
             }
         }
     
    });
 tree.render();
    tree.getRootNode().expand(true);

   
});


</script>
	<body bgcolor="#FFFFFF" >
		<div id="jgTree"  style="margin-Left:-1px;margin-5Top:-5px"/>
	</div></body>
</html>

