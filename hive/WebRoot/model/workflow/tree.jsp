<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="com.klspta.base.workflow.foundations.IWorkflowOp"%>
<%@page import="com.klspta.base.workflow.foundations.WorkflowOp"%>
<%@page import="com.klspta.model.resourcetree.TreeOperation"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
    String roleId=request.getParameter("roleId");
    String nodeName=URLDecoder.decode(request.getParameter("nodeName"), "utf-8");
    String wfID=request.getParameter("wfId");
    IWorkflowOp wfOp=WorkflowOp.getInstance();
    String zfjcType=wfOp.getZfjcType(wfID);
    String treeName=TreeOperation.getInstance().getTreeNameByYwType(zfjcType);
    HashMap map=new HashMap();
    map.put("zfjcType",zfjcType);
    String tree=TreeOperation.getInstance().getTree(map).getContent();
    String[] nodes=TreeOperation.getInstance().getWFTree(wfID,nodeName);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>content</title>
<link href="<%=basePath %>/console/css/style.css" rel="stylesheet" type="text/css" />
		<%@ include file="/base/include/ext.jspf" %>
	    <%@ include file="/base/include/restRequest.jspf" %>
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
	   		el:'mapTree', 
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
	        loader: new Ext.tree.TreeLoader({
        	   baseAttrs: { uiProvider: Ext.ux.TreeCheckNodeUI }
            }),
	        root: new Ext.tree.AsyncTreeNode({
	            expanded: false,
	            children: [<%=tree%>]
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
	     tree.on('click', function(n){
		    	var formName = tree.getNodeById(n.attributes.id).text;
		    	alert(formName)
		    	var urlName;
		    	var flag = true;
		    	switch(formName){
		    		case "立案呈批表": 
		    			formName = "lacpb";
		    			urlName = "ajcc/lacpb";
		    			break;
		    		case "结案呈批表": 
		    			formName = "jacpb";
		    			urlName = "ajcc/jacpb";
		    			break;
		    		case "法律文书呈批表": 
		    			formName = "flwscpb";
		    			urlName = "ajcc/sg_flwscpb";
		    			break;
		    		case "来信登记表":
		    			formName = "xfgl";
		    			urlName ="xfgl/xfgl";
		    			break;
		    		default:
		    			flag = false;
		    	}
		    	 var zfjcType = "<%=zfjcType%>";
		    	 var activityName = "<%=nodeName%>"
		    	if(flag){
		    		var url = encodeURI(encodeURI("<%=basePath%>supervisory/"+urlName+".jsp?permission=yes&zfjcType=<%=zfjcType%>&activityName=<%=nodeName%>&formName="+formName));
		    		window.showModalDialog(url,"","dialogWidth=1000px;dialogHeight=800px");
		        	//window.open(url);
		           //alert("treeName=<%=zfjcType%>&wfID="+tree.getNodeById(n.attributes.id).text+"&nodeName=<%=nodeName%>&treeIdList="+treeIdList);
		   		}	     
	     });
 	     tree.render();
   //先展开用于初始化ext的checked选项
 tree.getRootNode().expand(true); 
 	if('<%=nodes %>'!="null"){
	     var nodevalue="";//定义一个全局变量，保存节点的id或值{
		 var rootNode=tree.getRootNode();//获取根节点
		 //nodevalue+=rootNode.id;//获取跟节点的值
		  findchildnode(rootNode); //开始递归
		  nodevalue=nodevalue.substr(0, nodevalue.length - 1);
		 //alert(nodevalue);
			  
		  //获取所有的子节点   
		  function findchildnode(node){
			  var childnodes = node.childNodes;
			  var nd;
			  for(var i=0;i<childnodes.length;i++){ //从节点中取出子节点依次遍历
				  nd = childnodes[i];
				  //nodevalue += nd.id + ",";
				  var flag=0; 
				  <%
				     if(nodes!=null){
		  		       for(int j=0;j<nodes.length;j++){
				  %>
					     if('<%=nodes[j]%>'==nd.id){
					       flag=1;
					     }	  
				  <%
				       }
				     }
				  %>
				   if(flag==1){
		              tree.getNodeById(nd.id).ui.check(nd.id);	    
		           }
				  if(nd.hasChildNodes()){ //判断子节点下是否存在子节点
				    findchildnode(nd); //如果存在子节点 递归
				  }   
		      }
	      }
	   }  


//再合并
 tree.getRootNode().collapse(true); 
  		var treeIdList="";
  		
        var form = new Ext.form.FormPanel({
        renderTo: 'formPanel',
        title   : '<%=nodeName%>-菜单授权',
        autoHeight: true,
        width   : 200,
       
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
                				if(n>0){
                					treeIdList+=",";
                				}
                				treeIdList+=nodes[i].id;
                				n++;
                		}
                		
                		var path = "<%=basePath%>";
		   		    	var actionName="treeAC";
		   		    	var actionMethod="saveWorkflowTree";
		   		    	var nodeName=encodeURI(encodeURI('<%=nodeName%>'));
		   		    	var parameter="treeName=<%=treeName%>&wfID=<%=wfID%>&nodeName="+nodeName+"&treeIdList="+treeIdList;		   		        
		   		        var result = ajaxRequest(path,actionName,actionMethod,parameter);
					if(result=="{success:true}"){
						Ext.Msg.alert('提示','资源树权限保存成功。');
					}else{
						Ext.Msg.alert('提示','资源树权限保存失败，请稍后重试或联系管理员。');
					}
						treeIdList="";
				}
          	}
        ]
    });
    
});


</script>
	<body bgcolor="#FFFFFF" >
		<div id="formPanel" style="width:300px;height:500px;OVERFLOW-y:auto;  ">
		<div id="mapTree" />
		</div>
	</body>
</html>