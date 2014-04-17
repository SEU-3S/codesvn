<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.klspta.base.workflow.foundations.IWorkflowOp"%>
<%@page import="com.klspta.base.workflow.foundations.WorkflowOp"%>
<%@page import="com.klspta.model.resourcetree.TreeOperation"%>
<%@page import="com.klspta.model.projectinfo.ProjectInfo"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
    String roleId=request.getParameter("roleId");
    String nodeName=new String(request.getParameter("nodeName").getBytes("ISO-8859-1"), "utf-8");
    String wfID=request.getParameter("wfId");
    IWorkflowOp wfOp=WorkflowOp.getInstance();
    String zfjcType=wfOp.getZfjcType(wfID);
    String treeName=TreeOperation.getInstance().getTreeNameByYwType(zfjcType);
    HashMap map=new HashMap();
    map.put("zfjcType",zfjcType);
    String tree=TreeOperation.getInstance().getTree(map).getContent();
    String[] nodes=TreeOperation.getInstance().getWFTree(wfID,nodeName);
    ProjectInfo project=ProjectInfo.getInstance();
    String name = project.getFlag();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>content</title>
<link href="<%=basePath %>/common/pages/homepage/css/style.css" rel="stylesheet" type="text/css" />
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
		    	//alert(formName)
		    	var urlName;
		    	var flag = true;
		    	switch(formName){
		    		case "案源登记表-":
		    			formName="aydjb";
		    			urlName="web/jinan/aydj/aydjb";
		    			break;
		    		case "立案呈批表-": 
		    			formName = "lacpb";
		    			urlName = "web/default/ajgl/lacpb";
		    			break;
		    		case "撤案呈批表-":
		    			formName = "cacpb"
		    			urlName = "web/jinan/cacpb/cacpb";
		    			break;
		    		case "案件调查处理审批表-": 
		    			formName = "ajsccpb";
		    			urlName = "web/jinan/wfajcc/ajsccpb";
		    			break;
		    		case "移送信息表-": 
		    			formName = "ysxxb";
		    			urlName = "web/jinan/aydj/ysxxb";
		    			break;	
		    		case "法律文书呈批表-":
		    			formName = "sg_flwscpb";
		    			urlName ="web/default/lacc/sg_flwscpb";
		    			break;
		    		case "违法案件结案呈批表-":
		    			formName = "jacpb";
		    			urlName ="web/jinan/jacp/wfajjacpb";
		    			break;
		    		case "信访案件登记表-":
		    			formName = "xfajdjb";
		    			urlName ="web/jinan/xfaj/xzxfaj/xfajdjb";
		    			break;
		    		case "处罚决定主要事项-":
		    			formName = "cfjdzysx";
		    			urlName ="web/jinan/cfjdzysx/cfjdzysx";
		    			break;
		    		case "处罚决定落实情况-":
		    			formName = "cfjdlsqk";
		    			urlName ="web/jinan/cfjdlsqk/cfjdlsqk";
		    			break;
		    		case "矿产违法案件调查处理审批表-":
		    			formName = "kcajdcclpb";
		    			urlName ="web/jinan/wfajcc/kcajdcclpb";
		    			break;
	    			case "绩效考核表":
		    			formName = "jxkh";
		    			urlName ="web/oa/jx/jxkh";
		    			break;
	    			case "绩效信息表":
		    			formName = "jxgzt";
		    			urlName ="web/oa/jx/jxgzt";
		    			break;
	    			case "报销单":
		    			formName = "bxd";
		    			urlName ="web/oa/cwgl/bx/bxd";
		    			break;
	    			case "出差纪要":
		    			formName = "ccrz";
		    			urlName ="web/oa/cwgl/bx/ccrz";
		    			break;
		    		case "费用报销审批单":
		    			formName = "fybxd";
		    			urlName = "web/oa/cwgl/bx/fybxd";
		    			break;	
					case "借款申请表":
		    			formName = "jkd";
		    			urlName="web/oa/cwgl/jksq/jkd";
		    			break;
		    		case "实际借款信息表":
		    			formName = "sjjkd";
		    			urlName="web/oa/cwgl/jksq/sjjkd";
		    			break;
		    		case "请假单":
		    			formName = "leave";
		    			urlName="web/oa/executive/leave/leave";
		    			break;
		    		case "采购申请单":
		    			formName = "purchase";
		    			urlName="web/oa/executive/purchase/purchase";
		    			break;
		    		case "立案呈批表":
		    			formName = "lacpb";
		    			urlName="web/<%=name%>/lacc/lacp/lacpb";
		    			break;
		    		case "违法案件处理决定呈批表":
		    			formName = "cljdcpb";
		    			urlName="web/<%=name%>/lacc/cljdcp/cljdcpb";
		    			break;
		    		case "处罚决定主要事项":
		    			formName = "cfjdzysx";
		    			urlName="web/<%=name%>/lacc/cfjd/cfjdzysx";
		    			break;
		    		case "处罚决定落实情况":
		    			formName = "cfjdlsqk";
		    			urlName="web/<%=name%>/lacc/cfjd/cfjdlsqk";
		    			break;
		    		case "法律文书呈批表":
		    			formName = "flwscpb";
		    			urlName="web/<%=name%>/lacc/flws/flwscpb";
		    			break;
		    		case "违法案件结案呈批表":
		    			formName = "jacpb";
		    			urlName="web/<%=name%>/lacc/jacp/jacpb";
		    			break;
		    		case "违法线索反馈信息":
		    			formName = "wfxsfkxx";
		    			urlName="web/<%=name%>/xfaj/wfxsfkxx";
		    			break;
		    		case "案件基本信息登记表":
		    			formName = "ajjbxxdjb";
		    			urlName="web/<%=name%>/lacc/lacp/ajjbxxdjb";
		    			break;		    			
		    		default:
		    			flag = false;
		    	}
		    	 var zfjcType = "<%=zfjcType%>";
		    	 var activityName = "<%=nodeName%>"
		    	 //alert(flag)
		    	if(flag){
		    		
		    		var url = encodeURI(encodeURI("<%=basePath%>"+urlName+".jsp?permission=yes&zfjcType=<%=zfjcType%>&activityName=<%=nodeName%>&formName="+formName));
		    		//alert(url);
		    		window.showModalDialog(url,"","dialogWidth=1000px;dialogHeight=800px");
		        	//window.open(url);
		           //alert("treeName=<%=zfjcType%>&wfID="+tree.getNodeById(n.attributes.id).text+"&nodeName=<%=nodeName%>&treeIdList="+treeIdList);
		   		}else{
		   			Ext.Msg.alert('提示','这个表单无须授权。');
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
		   		    	var actionName="formrequest";
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
		<div id="formPanel" style="width:300px;height:500px;OVERFLOW-y:auto;">
		<div id="mapTree" />
		</div>
		<div style="position: absolute;top:320px;font-size:14px;">说明：点击左侧工作流节点，<br>右侧弹出违法案件查处资源树，<br>
		选中树节点前面的复选框<br>可以对资源树进行授权操作；<br>
		点击相应的树节点，<br>弹出对应的表单，<br>可以对表单进行授权操作。
		</div>
	</body>
</html>