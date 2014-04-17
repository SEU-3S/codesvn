<%@ page language="java"  pageEncoding="utf-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.klspta.base.workflow.foundations.IWorkflowOp"%>
<%@page import="com.klspta.base.workflow.foundations.WorkflowOp"%>
<%@page import="com.klspta.base.workflow.bean.DoNextBean"%>
<%@page import="com.klspta.base.workflow.bean.NodeDefineInfoBean"%>
<%@page import="com.klspta.base.util.UtilFactory"%>
<%@page import="com.klspta.base.workflow.foundations.ProcessMonitor"%>
<%@page import="com.klspta.base.workflow.foundations.JBPMServices"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"+ request.getServerPort() + path + "/";   
    
    //必需参数/////////////////////////////////////
    String wfInsId =request.getParameter("wfInsId");
	IWorkflowOp workflowOp = WorkflowOp.getInstance();
	String wfInsTaskId = workflowOp.getWfInsTaskIdByWfInsID(wfInsId);
	String wfId = workflowOp.getWfIdByWfInsID(wfInsId);
	String activityName = workflowOp.getActivityNameByWfInsID(wfInsId);
        
    
	DoNextBean ac=workflowOp.getNextInfo(wfId,wfInsId,wfInsTaskId);
		
    List<NodeDefineInfoBean> list=new ArrayList<NodeDefineInfoBean>();
    if(wfId!=null){  
      list=workflowOp.getNodeDefineInfoList(wfId);
    }
    List<Map<String,Object>> list_ = new ProcessMonitor().getHistoryTaskInfo(wfInsId);
    Map<String,Object> map_1 = list_.get(0);
    Map<String,Object> map_2 = list_.get(1);		
    		
    String state=JBPMServices.getInstance().getHistoryService().createHistoryProcessInstanceQuery().processInstanceId(wfInsId).uniqueResult().getState();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		
		<title>流程监控</title>
		
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<%@ include file="/base/include/ext.jspf" %>
		<%@ include file="/base/include/restRequest.jspf" %>
		<style type="text/css">
				html,body {
			FONT-SIZE: 12px;
			color:#CC3300 ; 
			font-weight: bold;
			margin: 0;
			padding: 0;
			border: 0 none;
			height: 100%;
		}
			td{
				border:0.5px #000 solid;
			}
	    </style>
	<script type="text/javascript">
	//将获取到的数据存在缓存当中，防止频繁访问数据库
	var infoArray=new Array();
	var tempString="";
	
	//显示效果控制 add by zhaow 2012-11-1
	function showTip(id,status,activityName){
	   if(status=="over"){
	     document.getElementById(id).style.filter="alpha(opacity=100)";
	     if(infoArray.length==0){
	        getTipData(activityName);
	     }
	     iterateData(activityName);
	     if(tempString==""){
	       getTipData(activityName);
	       iterateData(activityName);
	     }
	     var _tst = document.getElementById(id);
         _tst.innerHTML ="<center><font size='4' color='black'><<"+activityName+">>节点信息</font></center>"+tempString;
         tempString="";
	   }else if(status=="out"){
	     document.getElementById(id).style.filter="alpha(opacity=0)";
	   }
	}
	//迭代数据 
	function iterateData(activityName){
	   for(var i=0;i<infoArray.length;i++){
	      if(infoArray[i].ACTIVITY_NAME_==activityName){
	        tempString+="<font color='BLACK' size='3'>办理人:"+infoArray[i].ASSIGNEE_
	           +"  开始时间："+infoArray[i].START_+"  结束时间："+infoArray[i].END_+"<br/></font>";
	      }
	   }
	}
	//获取数据
	function getTipData(activityName){
	  putClientCommond("processMonitor","getImageTip");
	  putRestParameter("activityName",escape(escape(activityName)));
	  putRestParameter("wfInsId",'<%=wfInsId%>');
	  myData = restRequest();
	  for(var i=0;i<myData.length;i++){
	    infoArray.push(myData[i]);
	  }
	}
	</script>
	</head>
	<body>	
	<div style="leftmargin:0px,topmargin:0px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<iframe src="model/workflow/processMonitorTable.jsp?wfInsId=<%=wfInsId %>&wfId=<%=wfId %>" width=70% height=170></iframe>
	</div>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
			<table border="0" cellpadding="0" cellspacing="0" >
				<tr>
				<%if(!state.equals("ended")){ %>
					<td valign="top" >
	 					<img src="<%=basePath%>model/workflow/flowChart.jsp?wfInsTaskId=<%=wfInsTaskId%>&wfId=<%=wfId %>" />
	 					<div id="kuang" style="position:absolute;display:none;border:1px solid red;left:<%=ac.getX()%>px;top:<%=ac.getY()+203%>px;width:<%=ac.getWidth()%>px;height:<%=ac.getHeight()%>px;"></div>
	 					<div style="position:absolute;border:4px solid red;left:<%=ac.getX()+2%>px;top:<%=ac.getY()+205%>px;width:<%=ac.getWidth()-4%>px;height:<%=ac.getHeight()-4%>px;"></div>
	 				</td>
	 			<%}else{ %>	
					<td valign="top" >
	 					<img src="<%=basePath%>model/workflow/flowChart.jsp?wfInsTaskId=<%=wfInsTaskId%>&wfId=<%=wfId %>" />
	 					<div id="kuang" style="position:absolute;display:none;border:1px solid red;left:<%=ac.getX()%>px;top:<%=ac.getY()+203%>px;width:<%=ac.getWidth()%>px;height:<%=ac.getHeight()%>px;"></div>
	 				</td>
	 			<%} %>
				</tr>
			</table>
            <table border="0" cellpadding="0" cellspacing="0" width="90%" height="30px">
               <tr>
                  <td width="16%"><div align="center">图&nbsp;&nbsp;&nbsp;&nbsp;例</div></td>
                  <td width="7%">
                  <img src="<%=basePath%>model/workflow/images/yellow.jpg" width="60" height="30"/>
                  </td>
                  <td width="14%"><div align="left">&nbsp;当前节点</div></td>
                  <td width="7%">
                  <img src="<%=basePath%>model/workflow/images/green.jpg" width="60" height="30"/>
                  </td>
                  <td width="14%"><div align="left">&nbsp;已完成</div></td>
                  <td width="7%">
                  <img src="<%=basePath%>model/workflow/images/blue.jpg" width="60" height="30"/>
                  </td>
                  <td width="14%"><div align="left">&nbsp;已回退</div></td>
               </tr>
           </table>
       <% 
        NodeDefineInfoBean nd=null;
        String nodeName=null;  
        for(int i=0;i<list.size();i++){
        nd=list.get(i);
        nodeName=nd.getNodeName();
        if(!(nodeName.toLowerCase().indexOf("start")>=0 || nodeName.toLowerCase().indexOf("end")>=0||"办结".equals(activityName))){
	       if(activityName.equals(nodeName)){    
       %>
       <%--下面有三个不同情况的涂色，每个涂色里面有三个DIV，第一个是涂色的DIV，后面两个DIV是鼠标放在放在图片上显示的一个提示信息的DIV，这两个DIV和其他两个涂色里面的提示信息的DIV是完全相同的--%>
       <%--当前节点涂色 add by zhaow 2012-11-1--%>
<div id= '<%=nodeName %>' style="position:absolute;border:0px solid red;left:<%=nd.getX()+8.8%>px;top:<%=nd.getY()+211.8%>px;width:<%=nd.getWidth()-16%>px;height:<%=nd.getHeight()-16%>px;background:#FFFF00;filter: alpha(opacity=55);"></div>
<div onmouseover="showTip('<%=nodeName %>info','over','<%=nodeName %>')" onmouseout="showTip('<%=nodeName %>info','out','<%=nodeName %>')" 
     style="left:<%=nd.getX()+8.8%>px;top:<%=nd.getY()+211.8%>px;width:<%=nd.getWidth()-16%>px;height:<%=nd.getHeight()-16%>px;background: #080808;border-color: red;border-style: solid;border-width: 0px 0px 0px 0px;border-radius: 100%;position: absolute;filter: alpha(opacity=0);"></div>
<div id= '<%=nodeName %>info' style="left:<%=nd.getX()-50%>px;top:<%=nd.getY()+100%>px;width:<%=nd.getWidth()+480%>px;background: #FFFFFF;border-color:#1C86EE;border-style: solid;border-width: 4px 4px 4px 4px;border-radius: 100%;position: absolute;filter: alpha(opacity=0);"></div> 
       <%--回退节点涂色--%>
         <%}else if(map_1.get(nodeName)!=null&&map_2.get(nodeName)==null){%>
<div id= '<%=nodeName %>' style="position:absolute;border:0px solid red;left:<%=nd.getX()+8.8%>px;top:<%=nd.getY()+211.8%>px;width:<%=nd.getWidth()-16%>px;height:<%=nd.getHeight()-16%>px;background:#0000ff;filter: alpha(opacity=55);"></div>  
<div onmouseover="showTip('<%=nodeName %>info','over','<%=nodeName %>')" onmouseout="showTip('<%=nodeName %>info','out','<%=nodeName %>')" 
     style="left:<%=nd.getX()+8.8%>px;top:<%=nd.getY()+211.8%>px;width:<%=nd.getWidth()-16%>px;height:<%=nd.getHeight()-16%>px;background: #080808;border-color: red;border-style: solid;border-width: 0px 0px 0px 0px;border-radius: 100%;position: absolute;filter: alpha(opacity=0);"></div>
<div id= '<%=nodeName %>info' style="left:<%=nd.getX()-50%>px;top:<%=nd.getY()+100%>px;width:<%=nd.getWidth()+480%>px;background: #FFFFFF;border-color:#1C86EE;border-style: solid;border-width: 4px 4px 4px 4px;border-radius: 100%;position: absolute;filter: alpha(opacity=0);"></div> 
        <%--走过节点涂色--%> 
         <%}else if(map_2.get(nodeName)!=null){%>
<div id= '<%=nodeName %>' style="position:absolute;border:0px solid red;left:<%=nd.getX()+8.8%>px;top:<%=nd.getY()+211.8%>px;width:<%=nd.getWidth()-16%>px;height:<%=nd.getHeight()-16%>px;background:#00ff00;filter: alpha(opacity=55);"></div>  
<div onmouseover="showTip('<%=nodeName %>info','over','<%=nodeName %>')" onmouseout="showTip('<%=nodeName %>info','out','<%=nodeName %>')" 
     style="left:<%=nd.getX()+8.8%>px;top:<%=nd.getY()+211.8%>px;width:<%=nd.getWidth()-16%>px;height:<%=nd.getHeight()-16%>px;background: #080808;border-color: red;border-style: solid;border-width: 0px 0px 0px 0px;border-radius: 100%;position: absolute;filter: alpha(opacity=0);"></div>
<div id= '<%=nodeName %>info' style="left:<%=nd.getX()-50%>px;top:<%=nd.getY()+100%>px;width:<%=nd.getWidth()+480%>px;background: #FFFFFF;border-color:#1C86EE;border-style: solid;border-width: 4px 4px 4px 4px;border-radius: 100%;position: absolute;filter: alpha(opacity=0);"></div>         
        
        <%}}}
       %>          
	</body>
</html>