<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@page import="com.klspta.console.ManagerFactory"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
    String tree=ManagerFactory.getRoleManager().getRoleListExtJson();
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
		<%@ include file="/base/include/restRequest.jspf"%>
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
                if(node.leaf){
                parentRoleId=node.attributes.parentId;
                }else{
                parentRoleId='0';
                }
                parent.info.location.href="<%=basePath%>/console/role/roleInfo.jsp?roleId="+nodeid+"&parentRoleId="+parentRoleId;
             }
            
         
         }
     
    });
 tree.render();
    tree.getRootNode().expand(true);

   
    //增加右键事件
   tree.on('contextmenu',showRighrClickMenu,RighrClickMenu);
   var parentRoleId;
   var selectRoleId;   //要删除的roleId
   var RighrClickMenu=new Ext.menu.Menu({
   items:[{
	   		   text:"添加根结构",
	   		   pressed:true,
	   		   handler:function(tree){
	   		   		var roleId=newGuid();
	   		        //页面刷新
	   		   		document.location.reload();
					parent.info.location.href="<%=basePath%>/console/role/roleInfo.jsp?roleId="+roleId+"&parentRoleId=0";
	   		   }
   		   },
   		   {
	   		   text:"添加子结构",
	   		   pressed:true,
	   		   handler:function(tree){
   		   			var roleId=newGuid();
	   		   		document.location.reload();
					parent.info.location.href="<%=basePath%>/console/role/roleInfo.jsp?roleId="+roleId+"&parentRoleId="+parentRoleId;
	   		   }
   		   },
   		   {
	   		   text:"删除",
	   		    pressed:true,
	   		    handler:function(tree){
	   		    	/*
	   		    	var path = "<%=basePath%>";
	   		    	var actionName="userAction";
	   		    	var actionMethod="deleteRole";
	   		    	var parameter="selectRoleId="+selectRoleId;
	   		        var result = ajaxRequest(path,actionName,actionMethod,parameter);
	   		    	*/
	   		        putClientCommond("userAction","deleteRole");
	   		        putRestParameter("selectRoleId",selectRoleId);
	   		       // putRestParameter("parentTreeId", selectMapTreeId);
   					var result=restRequest();
	   		    	
	   		    	//页面刷新
	   		   		document.location.reload()
	   		   }
   		   }
   		   ]
   });
   
      var leaf_RighrClickMenu=new Ext.menu.Menu({
   	  items:[{
   		   text:"删除",
   		    pressed:true,
   		    handler:function(tree){
   		    /*
   		    	var path = "<%=basePath%>";
   		    	var actionName="userAction";
   		    	var actionMethod="deleteRole";
   		    	var parameter="selectRoleId="+selectRoleId;
   		        var result = ajaxRequest(path,actionName,actionMethod,parameter);
   		    */
		    	putClientCommond("userAction","deleteRole");
   		        putRestParameter("selectRoleId",selectRoleId);
				var result=restRequest();
   		    	//页面刷新
   		   		document.location.reload()
   		   }
   		   }]
   	});
   
   
       
   		function showRighrClickMenu(node,e)
   		{
   			e.preventDefault();
   			node.select();
   			selectRoleId=node.id;
   			if(node.leaf){
	   			 parentRoleId=node.parentNode.id;
	   			 leaf_RighrClickMenu.showAt(e.getPoint());
   			}else{
	   			 parentRoleId=node.id;
	   			 RighrClickMenu.showAt(e.getPoint());
   			}
   		}
   		
   		
   
   
   
   
  
   
   
});

function newGuid(){ 
    var guid = ""; 
    for (var i = 1; i <= 32; i++){ 
        var n = Math.floor(Math.random()*16.0).toString(16); 
        guid += n; 
    } 
    return guid; 
} 

</script>
	<body bgcolor="#FFFFFF" >
		<div id="jgTree"  style="margin-Left:-1px;margin-5Top:-5px"/>
	</body>
</html>

