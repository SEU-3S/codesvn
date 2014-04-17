<%@ page language="java"  pageEncoding="utf-8"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.klspta.base.workflow.bean.NodeDefineInfoBean"%>
<%@page import="com.klspta.base.workflow.foundations.IWorkflowOp"%>
<%@page import="com.klspta.base.workflow.foundations.WorkflowOp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
    + request.getServerPort() + path + "/";
    String resourceName=request.getParameter("resourceName");
    String wfID=request.getParameter("wfID");
    List<NodeDefineInfoBean> list=new ArrayList();
    if(wfID!=null){  
    IWorkflowOp workflowOp=WorkflowOp.getInstance();
    list=workflowOp.getNodeDefineInfoList(wfID);
    }
    String treeName="ajcc_wfajcc";
    IWorkflowOp wfOp=WorkflowOp.getInstance();
    wfOp.getZfjcType(wfID);
    String initNodeName=list.get(1).getNodeName();
%>

<html   xmlns= "http://www.w3.org/1999/xhtml "> 
<script>
 function initNode(){
    var initNodeName="<%=initNodeName%>";
    showTree("受理立案");
  }
</script>
<HEAD>
<META content="text/html; charset=utf-8" http-equiv=Content-Type></HEAD>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="initNode()">
<img src="<%=basePath%>model/workflow/flow.jsp?resourceName=<%=resourceName%>&wfID=<%=wfID%>" />
<% 
NodeDefineInfoBean nd=null;
String nodeName=null;
for(int i=0;i<list.size();i++){
nd=list.get(i);
nodeName=nd.getNodeName();
if(!(nodeName.toLowerCase().indexOf("start")>=0 || nodeName.toLowerCase().indexOf("end")>=0)){
%>

<div onclick="showTree('<%=nodeName %>')" 
id= '<%=nodeName %>'
style="position:absolute;
border:0px solid red;
left:<%=nd.getX()+8.8%>px;
top:<%=nd.getY()+8.8%>px;
width:<%=nd.getWidth()-16%>px;
height:<%=nd.getHeight()-16%>px;
background:#F75000;
filter: alpha(opacity=55);"
>
</div>


<% }}%>
</body>
</html>
<script>
function showTree(nodeName){
var divObjs=document.getElementsByTagName('div');
for(var i=0;i<divObjs.length;i++){
divObjs[i].style.background='#F75000';
}
document.getElementById(nodeName).style.background='#9AFF02';
parent.tree.location.href="<%=basePath%>model/workflow/authorityTree.jsp?nodeName="+nodeName+"&wfId=<%=wfID%>";
}
</script>