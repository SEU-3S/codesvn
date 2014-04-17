<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="java.net.URLDecoder"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
    request.setCharacterEncoding("utf-8");//根据你页面的设置
    String roleId=request.getParameter("roleId");
    String nodeName=URLDecoder.decode(request.getParameter("nodeName"), "utf-8");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>outlookBar</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<%@ include file="/base/include/restRequest.jspf"%>
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
	/*
var path = "<%=basePath%>";
var actionName = "mapAuthorOperation";
var actionMethod = "getExtTreeByRoleid";
var parameter = "roleid=<%=roleId%>";
var myData = ajaxRequest(path, actionName, actionMethod, parameter);
var mapTree = eval(myData);
*/
	  putClientCommond("mapAuthorOperation","getExtTreeByRoleid");
      putRestParameter("roleid","<%=roleId%>");
      var myData=restRequest();
      var mapTree = eval(myData);
 Ext.onReady(function() {
        
	    var tree = new Ext.tree.TreePanel({
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
	            expanded: true,
	            children: mapTree
	        }),
             listeners:{'checkchange':function(node,checked){
		                   node.expand();
		                   node.attributes.checked = checked;
		                   if(checked){
			                  
		                   }
 							node.eachChild(function(child) {
		                       child.ui.toggleCheck(checked);
		                       child.attributes.checked = checked;
		                       child.fireEvent('checkchange', child, checked);
		                   });
		                 
             } }
	    });
 
  		var treeIdList="";
  		
        var form = new Ext.form.FormPanel({
        renderTo: 'mapTree',
        title   : '<%=nodeName%>-图层授权',
        autoHeight: true,
        width   : 500,
       
        bodyStyle: 'padding: 5px',
        defaults: {
            anchor: '0'
        },
        items   : [
        		tree
   				],
        buttons: [
            {
                text   : '保存',
                handler: function() {
                		var nodes=tree.getChecked();
                		var n=0;
                		for(i=0;i<nodes.length;i++){
                			/*if(nodes[i].hasChildNodes()){
                				//父节点过滤掉
                			}else{  */
                				if(n>0){
                					treeIdList+=",";
                				}
                				treeIdList+=nodes[i].id;
                				n++;
                			//}
                		}
                		form.form.submit({ 
							waitMsg: '正在保存,请稍候... ', 
							url:"<%=basePath%>service/rest/mapAuthorOperation/addMapAuthor?roleId=<%=roleId%>&treeIdList="+treeIdList,
							success:function(){ 
								Ext.Msg.alert('提示','保存成功。');
								treeIdList="";
							}, 
							failure:function(){ 
							//重新赋值
							Ext.Msg.alert('提示','保存失败，请稍后重试或联系管理员。');
							treeIdList="";
							} 
						});
				}
          	}
        ]
    });
    
});


</script>
	<body bgcolor="#FFFFFF" >
		<div id="mapTree"  style="width:300px;height:500px;OVERFLOW-y:auto;  "/>
	</body>
</html>

