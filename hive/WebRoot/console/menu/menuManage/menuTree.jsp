<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.klspta.console.ManagerFactory"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
    String tree =ManagerFactory.getMenuManager().getExtMenuTreeByMenuBeanList();
    //parentId:'d9f531c33ec2578914b4c99f5aaa5a5d'"
//    String tree="[{text:'首页(0)',icon:'',leaf:0,id:'d9f531c33ec2578914b4c99f5aaa5a5d'"
//    +",children:[{text:'登记(线索受理区)(0)',icon:'',leaf:0,id:'08b62ba780e5dcad8334e7eca61572b7'"
 //  +",children:[{text:'测试',icon:'',leaf:0,id:'d9f531c33ec2578914b4c99f5aaa5a55'"
//    +",children:[{text:'登记(线索受理区)(0)',icon:'',leaf:0,id:'d9f531c33ec2578914b4c99f5aaa5a52'"
  
//   +"}]"
//+"}]"
 //   +"}]"
 //   +"}]";

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
 Ext.onReady(function() {
        
	    var tree = new Ext.tree.TreePanel({
	        useArrows:true,
	        autoScroll:true,
	        animate:true,
	        enableDD:true,
	        margins: '2 2 0 2',
	        border: false,
	        containerScroll: true,
	        rootVisible: false,
	        frame: true,
	        loader: new Ext.tree.TreeLoader(),
	        root: new Ext.tree.AsyncTreeNode({
	            expanded: false,
	            children: <%=tree%>
	        }),
	         listeners: {//单击右侧进行修改
	         'click': function(node, e){
	                var nodeid=node.attributes.id;
	                var parentMenuTreeId;
	                if(node.leaf){
	                parentMapTreeId=node.attributes.parentId;
	                }else{
	                parentMapTreeId='0';
	                }
	                parent.menuInfo.location.href="<%=basePath%>/console/menu/menuManage/menuTreeInfo.jsp?treeId="+nodeid+"&parentTreeId="+parentMenuTreeId;
	             	parent.parent.Ext.getCmp('west').collapse();
	             }
	         }
	    });
	    
	        //增加右键事件
	   tree.on('contextmenu',showRighrClickMenu,RighrClickMenu);
	   var parentMenuTreeId;
	   var selectMenuTreeId;   //要删除的menuTreeId
	   var RighrClickMenu=new Ext.menu.Menu({
	   items:[{
		   		   text:"添加子图层",
		   		   pressed:true,
		   		   handler:function(tree){
		   		   		var menuTreeId=newGuid();
		   		   		//var path = "<%=basePath%>";
		   		   		//var parentTreeId=node.attributes.parentId;
		   			  	//var actionName="";
		   		    	//var actionMethod="addMenuTreeNode";
		   		    	//var parameter="treeId="+menuTreeId+"&menuType=2&parentId="+parentMenuTreeId;
		   		        //var result = ajaxRequest(path,actionName,actionMethod,parameter);
		   		        //页面刷新
		   		   		document.location.reload();
						parent.menuInfo.location.href="<%=basePath%>/console/menu/menuManage/menuTreeInfo.jsp?treeId="+menuTreeId+"&parentTreeId="+selectMenuTreeId;
		   		   		parent.parent.Ext.getCmp('west').collapse();
		   		   }
	   		   },
	   		   {
		   		   text:"删除",
		   		    pressed:true,
		   		    handler:function(tree){
		   		    	/*
		   		    	var path = "<%=basePath%>";
		   		    	var beanName="menuAction";
		   		    	var actionMethod="deleteMenu";
		   		    	var parameter="treeId="+selectMenuTreeId+"&menuType=1&parentId="+parentMenuTreeId;
		   		        var result = ajaxRequest(path,beanName,actionMethod,parameter);
		   		    	*/
		   		    	putClientCommond("menuAction","deleteMenu");
    					putRestParameter("treeId", selectMenuTreeId);
		   		        putRestParameter("menuType", "1");
		   		        putRestParameter("parentId", parentMenuTreeId);
    					var result = restRequest();
		   		    	
		   		    	//页面刷新
		   		   		document.location.reload()
		   		   }
	   		   }
	   		   ]
	   });
	   
	      var leaf_RighrClickMenu=new Ext.menu.Menu({
	   	  items:[{
	   		     text:"添加子图层",
		   		   pressed:true,
		   		   handler:function(tree){
		   		   		var menuTreeId=newGuid();
		   		   		//var parentTreeId=node.attributes.parentId;
		   		   		//var path = "<%=basePath%>";
		   		    	//var actionName="treeAC";
		   		    	//var actionMethod="addMenuTreeNode";
		   		    	//var parameter="treeId="+menuTreeId+"&menuType=2&parentId="+parentMenuTreeId;
		   		        //var result = ajaxRequest(path,actionName,actionMethod,parameter);
		   		        //页面刷新
		   		   		document.location.reload();
						parent.menuInfo.location.href="<%=basePath%>/console/menu/menuManage/menuTreeInfo.jsp?treeId="+menuTreeId+"&parentTreeId="+selectMenuTreeId;
		   		   		parent.parent.Ext.getCmp('west').collapse();
		   		   }
	   		   },
	   		   {
		   		   text:"删除",
		   		    pressed:true,
		   		    handler:function(tree){
		   		    	/*
		   		    	var path = "<%=basePath%>";
		   		    	var beanName="menuAction";
		   		    	var actionMethod="deleteMenu";
		   		    	var parameter="treeId="+selectMenuTreeId+"&menuType=1&parentId="+parentMenuTreeId;
		   		        var result = ajaxRequest(path,beanName,actionMethod,parameter);
		   		    	*/
		   		    	putClientCommond("menuAction","deleteMenu");
    					putRestParameter("treeId", selectMenuTreeId);
		   		        putRestParameter("menuType", "1");
		   		        putRestParameter("parentId", parentMenuTreeId);
    					var result = restRequest();
		   		    	
		   		    	//页面刷新
		   		   		document.location.reload()
		   		   }
	   		   }
	   		   ]
	   	});
	   	
	   	function showRighrClickMenu(node,e){
   			e.preventDefault();
   			node.select();
   			selectMenuTreeId=node.id;
   			if(node.leaf){
	   			 parentMenuTreeId=node.parentNode.id;
	   			 leaf_RighrClickMenu.showAt(e.getPoint());
   			}else{
	   			 parentMenuTreeId=node.id;
	   			 RighrClickMenu.showAt(e.getPoint());
   			}
   		}
 
     //表单FormPanel
        var form = new Ext.form.FormPanel({
        renderTo: 'mapTree',
        title   : '图层树管理',
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
                text   : '新增一级菜单',
                handler: function() {
                		var menuTreeId=newGuid();
		   		   		//var path = "<%=basePath%>";
		   		    	//var actionName="treeAC";
		   		    	//var actionMethod="addMenuTreeNode";
		   		    	//var parameter="treeId="+menuTreeId+"&menuType=1&parentId=0";
		   		        //var result = ajaxRequest(path,actionName,actionMethod,parameter);
		   		        //页面刷新
		   		   		document.location.reload();
						parent.menuInfo.location.href="<%=basePath%>/console/menu/menuManage/menuTreeInfo.jsp?treeId="+menuTreeId+"&parentTreeId=0";
		   		   		parent.parent.Ext.getCmp('west').collapse();
				}
          	}
        ]
    });
    
    function newGuid(){ 
    var guid = ""; 
    for (var i = 1; i <= 32; i++){ 
        var n = Math.floor(Math.random()*16.0).toString(16); 
        guid += n; 
    } 
    return guid; 
} 
    
});


</script>
	<body bgcolor="#FFFFFF" >
		<div id="mapTree"  style="width:500px;height:500px;OVERFLOW-y:auto;  "/>
	</div></body>
</html>

