﻿<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@ page import="com.klspta.model.resourcetree.TreeOperation"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.klspta.base.workflow.foundations.WorkflowOp"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	String zfjcType = request.getParameter("zfjcType");
	String yw_guid = request.getParameter("yw_guid");
	HashMap<String, String> map = new HashMap<String, String>();
	map.put("zfjcType", zfjcType);
	map.put("yw_guid", yw_guid);
	String tree = TreeOperation.getInstance().getTree(map).getContent();

	String activity_Name = request.getParameter("activityName");
	String activityName = "";
	String wfInsId = "";
	String wfId = "";
	String treename = "";
	String WFTreeName = "";
	String[] nodes = null;
	if (activity_Name != null) {
		activityName = new String(activity_Name.getBytes("ISO-8859-1"), "utf-8");
		wfInsId = request.getParameter("wfInsId");
		wfId = WorkflowOp.getInstance().getWfIdByWfInsID(wfInsId);
		zfjcType = (String) map.get("zfjcType");
		treename = TreeOperation.getInstance().getTreeNameByYwType(zfjcType);
		WFTreeName = TreeOperation.getInstance().getWFTreeName(wfId, activityName);
		if (wfId != null && activityName != null) {
			nodes = TreeOperation.getInstance().getWFTree(wfId, activityName);
		}
	}

	StringBuffer sb = new StringBuffer();
	Map maps = request.getParameterMap();
	Iterator its = maps.entrySet().iterator();
	while (its.hasNext()) {
		Entry entry = (Entry) (its.next());
		String key = entry.getKey().toString().trim();
		String value = new String(request.getParameter(key).trim().getBytes("ISO-8859-1"), "utf-8");
		int i = value.indexOf("\"");
		if (i != -1) {
			int a = value.indexOf("\"", i + 1);
			value = value.substring(i + 1, a);
		}
		sb.append(key + "=" + value);
		if (its.hasNext()) {
			sb.append("&");
		}
	}
	String edit = request.getParameter("edit");
	if(edit == null){
		edit = "true";
	}
	sb.append("&edit=" + edit);
	String parameter = sb.toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<meta http-equiv="X-UA-Compatible" content="IE=8">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>详细信息</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<%@ include file="/base/include/ext.jspf"%>
		<style>
html,body {
	font: normal 12px verdana;
	margin: 0;
	padding: 0;
	border: 0 none;
	overflow: hidden;
	height: 100%;
}
</style>

		<script type="text/javascript">
     var border;
     var tree;
     Ext.onReady(function(){
	 //定义树
	 tree = new Ext.tree.TreePanel({
	        region: 'west',
	        id:'west_tree',
	        title: '列表',
	        collapsible: true,
	        useArrows: true,
	        autoScroll: true,
	        animate: false,
	        enableDD: true,
	        autoHeight: false,
	        width: 200,
	        border: false,
	        margins: '2 2 0 2',
	        containerScroll: true,
	        rootVisible: false,
	        loader: new Ext.tree.TreeLoader(),
	        root: new Ext.tree.AsyncTreeNode({
	            expanded: false,
	            children: [<%=tree%>]
	        })	        	        
	    });
	    
	//定义布局形式	    
	border =new Ext.Viewport({
		layout:"border", 
		items:[tree,
				{
				 region:'center',
	             contentEl: 'center',	            
	             collapsible: false,        
	             margins:'2 2 0 0'
	            }]
		});
	
	//添加树的单击打开页面事件	 
	tree.on('click', function(n){
		var str = n.attributes.src;	
    	if(n.attributes.src!=null){
    		if(str.substr(str.length-4,str.length)!='.jsp'){
       			frames['center'].location='<%=basePath%>'+n.attributes.src+'&<%=parameter%>&flag=1&docNodeName='+n.text;    
       		}else{
       			frames['center'].location='<%=basePath%>'+n.attributes.src+'?<%=parameter%>';	
       		}
     	}
    });
    //对树进行渲染  
	tree.render();
	//页面加载后树节点打开
	tree.getRootNode().expand(true);
	
	var rootNode=tree.getRootNode();//获取根节点
    <%if(treename.equals(WFTreeName)&&nodes!=null){%>
         var shownnode="";
         var shownnodeId;
		  //nodevalue+=rootNode.id;//获取跟节点的值
		 findchildnode(rootNode); //开始递归
		 shownnode=shownnode.substr(0, shownnode.length - 1);
			  
		 //获取所有的子节点   
		 function findchildnode(node){
			  var childnodes = node.childNodes;
			  var nd;
			  for(var i=0;i<childnodes.length;i++){ //从节点中取出子节点依次遍历
				  nd = childnodes[i];
				  shownnode += nd.id + ",";
				  if(tree.getNodeById(nd.id).attributes.type=="dynamic"){
				   continue;
				  }
				  if(nd.hasChildNodes()){ //判断子节点下是否存在子节点
				    findchildnode(nd); //如果存在子节点 递归
				  }   
		      }
	     }
	     shownnodeId=shownnode.split(",");
	     for(var i=0;i<shownnodeId.length;i++){
	     var flag=0;
		  <%
		    for(int j=0;j<nodes.length;j++){
		  %>
		     if('<%=nodes[j]%>'==shownnodeId[i]){
		       flag=1;
		     }	  
		  <%
		   }
		  %>
		     if(flag==0){
		      if(tree.getNodeById(shownnodeId[i])!=null){
		        tree.getNodeById(shownnodeId[i]).remove();
		      }
		     }
	    }
	<%}%>
         /*让center显示资源树的第一个节点*/	 
	     var firstNode;
	     getFirstNode(rootNode);
	     function getFirstNode(node){
			  var childnodes = node.childNodes;
			  var nd=childnodes[0];
			  firstNode=nd.id;
			  if(nd.hasChildNodes()){ //判断子节点下是否存在子节点
				 getFirstNode(nd); //如果存在子节点 递归
			  } 
	     }
	     var st=tree.getNodeById(firstNode).attributes.src;
	     if(st.substr(st.length-4,st.length)=='.jsp'){
	       frames['center'].location='<%=basePath%>'+st+'?<%=parameter%>';
	     }else{
	       frames['center'].location='<%=basePath%>'+st+'&<%=parameter%>';
		 } 
	
        
   if('<%=wfId%>'=='null'||'<%=activityName%>'=='null'){
   		if(tree.getNodeById('importZB')!=null){
			 tree.getNodeById('importZB').remove();	 	 
		 }
		 if(tree.getNodeById('workflow')!=null){
			 tree.getNodeById('workflow').remove();	 	 
		 }
    } 
    
});
function showWindow(showUrl,width,height){
if(width==0 || height==0){
width=document.body.clientWidth;
height=document.body.clientHeight;
}
            win = new Ext.Window({
                layout:'fit',
                width:width,
                height:height,
                closable : true,
                closeAction:'close',
                shadow : true, 
                html: "<iframe id='process'  style='height:100%;width=100%;' src='"+showUrl+"' ></iframe>"
            });
        win.show(this);
}

function addTreeNode(docName, file_id){
	var newNode = new Ext.tree.TreeNode({text:docName,src:"common/pages/weboffice/webOffice_read.jsp?file_type=doc&file_id=" + file_id});
	tree.getSelectionModel().getSelectedNode().appendChild(newNode);
}
</script>

	</head>
	<body style="background-color: white">
		<iframe id="center" name="center" style="width: 100%; height: 100%;"
			src=""></iframe>
	</body>
</html>
