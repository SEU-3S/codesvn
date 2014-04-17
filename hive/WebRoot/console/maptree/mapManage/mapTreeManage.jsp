<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
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
	/*
	var path = "<%=basePath%>";
	var actionName = "mapAuthorOperation";
	var actionMethod = "getAllExtTree";
	var parameter = "";
	var myData = ajaxRequest(path, actionName, actionMethod, parameter);
	var mapTree = eval(myData);
	*/
	putClientCommond("mapAuthorOperation","getAllExtTree");
    var myData=restRequest();
    var mapTree = eval(myData);

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
	            children: mapTree
	        }),
	         listeners: {//单击右侧进行修改
	         'click': function(node, e){
	        // alert(node.attributes.id+"----"+node.attributes.parentId);   
	                var nodeid=node.attributes.id;
	                var parentMapTreeId;
	                //if(node.leaf){
	               	parentMapTreeId=node.attributes.parentId;
	                //}else{
	                //	parentMapTreeId='0';
	                //}
	                parent.mapInfo.location.href="<%=basePath%>/console/maptree/mapManage/mapTreeInfo.jsp?treeId="+nodeid+"&parentTreeId="+parentMapTreeId;   	  				  
	             	parent.parent.Ext.getCmp('west').collapse(); 
	             }
	         }
	    });
	    
	        //增加右键事件 
	   tree.on('contextmenu',showRighrClickMenu,RighrClickMenu);
	   var parentMapTreeId;
	   var selectMapTreeId;   //要删除的mapTreeId
	   var RighrClickMenu=new Ext.menu.Menu({
	   items:[{
		   		   text:"添加子文件夹",
		   		   pressed:true,
		   		   handler:function(tree){
	                	var mapTreeId=newGuid();	   		   
		   		   		/*
		   		   		var path = "<%=basePath%>";
		   		    	var actionName="mapAuthorOperation";
		   		    	var actionMethod="addMapTreeNode";
		   		    	var parameter="treeId="+mapTreeId+"&leafFlag=0&parentTreeId="+selectMapTreeId;
		   		        var result = ajaxRequest(path,actionName,actionMethod,parameter);
		   		        */
		   		        putClientCommond("mapAuthorOperation","addMapTreeNode");
		   		        putRestParameter("treeId",mapTreeId);
		   		        putRestParameter("leafFlag", "0");
		   		        putRestParameter("parentTreeId", selectMapTreeId);
    					var result=restRequest();
		   		        
		   		        //页面刷新
		   		   		document.location.reload();
						parent.mapInfo.location.href="<%=basePath%>/console/maptree/mapManage/mapTreeInfo.jsp?treeId="+mapTreeId+"&parentTreeId="+selectMapTreeId;
		   		   		parent.parent.Ext.getCmp('west').collapse();
		   		   }
	   		   },{
		   		   text:"添加子图层",
		   		   pressed:true,
		   		   handler:function(tree){
		   		   		var mapTreeId=newGuid();
		   		   		/*
		   		   		var path = "<%=basePath%>";
		   		    	var actionName="mapAuthorOperation";
		   		    	var actionMethod="addMapTreeNode";
		   		    	var parameter="treeId="+mapTreeId+"&leafFlag=1&parentTreeId="+parentMapTreeId;
		   		        var result = ajaxRequest(path,actionName,actionMethod,parameter);
		   		        */
		   		       	putClientCommond("mapAuthorOperation","addMapTreeNode");
		   		        putRestParameter("treeId",mapTreeId);
		   		        putRestParameter("leafFlag", "1");
		   		        putRestParameter("parentTreeId", parentMapTreeId);
    					var result=restRequest();
		   		        
		   		        
		   		        //页面刷新
		   		   		document.location.reload();
						parent.mapInfo.location.href="<%=basePath%>/console/mapManage/mapTreeInfo.jsp?treeId="+mapTreeId+"&parentTreeId="+parentMapTreeId;
		   		   		parent.parent.Ext.getCmp('west').collapse();
		   		   }
	   		   },
	   		   {
		   		   text:"删除",
		   		    pressed:true,
		   		    handler:function(tree){
		   		    	/*
		   		    	var path = "<%=basePath%>";
		   		    	var actionName="mapAuthorOperation";
		   		    	var actionMethod="deleteMapTreeNode";
		   		    	var parameter="treeId="+selectMapTreeId+"&leafFlag=0&parentTreeId="+parentMapTreeId;
		   		        var result = ajaxRequest(path,actionName,actionMethod,parameter);
		   		    	*/
		   		   		putClientCommond("mapAuthorOperation","deleteMapTreeNode");
		   		        putRestParameter("treeId",selectMapTreeId);
		   		        putRestParameter("leafFlag", "0");
		   		        putRestParameter("parentTreeId", parentMapTreeId);
    					var result=restRequest();
		   		    	
		   		    	//页面刷新
		   		   		document.location.reload()
		   		   }
	   		   }
	   		   ]
	   });
	   
	    var RighrClickMenuSecond=new Ext.menu.Menu({
	   items:[{
		   		   text:"添加子图层",
		   		   pressed:true,
		   		   handler:function(tree){
		   		   		var mapTreeId=newGuid();
		   		   		/*
		   		   		var path = "<%=basePath%>";
		   		    	var actionName="mapAuthorOperation";
		   		    	var actionMethod="addMapTreeNode";
		   		    	var parameter="treeId="+mapTreeId+"&leafFlag=1&parentTreeId="+parentMapTreeId;
		   		        var result = ajaxRequest(path,actionName,actionMethod,parameter);
		   		        */
		   		       	putClientCommond("mapAuthorOperation","addMapTreeNode");
		   		        putRestParameter("treeId", mapTreeId);
		   		        putRestParameter("leafFlag", "1");
		   		        putRestParameter("parentTreeId", parentMapTreeId);
    					var result=restRequest();
		   		        
		   		        
		   		        //页面刷新
		   		   		document.location.reload();
						parent.mapInfo.location.href="<%=basePath%>/console/maptree/mapManage/mapTreeInfo.jsp?treeId="+mapTreeId+"&parentTreeId="+parentMapTreeId;
		   		   		parent.parent.Ext.getCmp('west').collapse();
		   		   }
	   		   },
	   		   {
		   		   text:"删除",
		   		    pressed:true,
		   		    handler:function(tree){
		   		    	/*
		   		    	var path = "<%=basePath%>";
		   		    	var actionName="mapAuthorOperation";
		   		    	var actionMethod="deleteMapTreeNode";
		   		    	var parameter="treeId="+selectMapTreeId+"&leafFlag=0&parentTreeId="+parentMapTreeId;
		   		        var result = ajaxRequest(path,actionName,actionMethod,parameter);
		   		    	*/
		   		    	putClientCommond("mapAuthorOperation","deleteMapTreeNode");
		   		    	putRestParameter("treeId", selectMapTreeId);
		   		    	putRestParameter("leafFlag", "0");
		   		    	putRestParameter("parentTreeId", parentMapTreeId);
		   		    	var result = restRequest();
		   		    	
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
	   		    	    var path= "<%=basePath%>";
		   		    	var actionName="mapAuthorOperation";
		   		    	var actionMethod="deleteMapTreeNode";
		   		    	var parameter="treeId="+selectMapTreeId+"&leafFlag=1&parentTreeId="+parentMapTreeId;
		   		        var result = ajaxRequest(path,actionName,actionMethod,parameter);
		   		    	*/
		   		    	putClientCommond("mapAuthorOperation", "deleteMapTreeNode");
		   		    	putRestParameter("treeId", selectMapTreeId);
		   		    	putRestParameter("leafFlag", "1");
		   		    	putRestParameter("parentTreeId", parentMapTreeId);
		   		    	var result = restRequest();
		   		    	
		   		    	//页面刷新
		   		   		document.location.reload()
	   		   }
	   		   }]
	   	});
	   	
	   	function showRighrClickMenu(node,e){
   			e.preventDefault();
   			node.select();
   			selectMapTreeId=node.id;
   			if(node.leaf){
	   			 parentMapTreeId=node.parentNode.id;
	   			 leaf_RighrClickMenu.showAt(e.getPoint());
   			}else{
   			     parentMapTreeId=node.id;
   			     if(node.parentNode.parentNode!=null&&node.parentNode.parentNode.parentNode==null){
  					 RighrClickMenuSecond.showAt(e.getPoint());
   			     }else{
	   				 RighrClickMenu.showAt(e.getPoint());
	   			 }
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
                text   : '新增图层文件夹',
                handler: function() {
                		var mapTreeId=newGuid();
                		/*
		   		   		var path = "<%=basePath%>";
		   		    	var actionName="mapAuthorOperation";
		   		    	var actionMethod="addMapTreeNode";
		   		    	var parameter="treeId="+mapTreeId+"&leafFlag=0&parentTreeId=0";
		   		        var result = ajaxRequest(path,actionName,actionMethod,parameter);
		   		        */
		   		       	putClientCommond("mapAuthorOperation", "addMapTreeNode");
		   		    	putRestParameter("treeId", mapTreeId);
		   		    	putRestParameter("leafFlag", "0");
		   		    	putRestParameter("parentTreeId", "0");
		   		    	var result = restRequest();
		   		        
		   		        //页面刷新
		   		   		document.location.reload();
						parent.mapInfo.location.href="<%=basePath%>/console/maptree/mapManage/mapTreeInfo.jsp?treeId="+mapTreeId+"&parentTreeId=0";
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
	</body>
</html>

