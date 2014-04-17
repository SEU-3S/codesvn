<%@ page language="java"  pageEncoding="utf-8"%>
<%@page import="com.klspta.base.util.UtilFactory"%>
<%@page import="com.klspta.console.user.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.klspta.base.workflow.foundations.IWorkflowOp"%>
<%@page import="com.klspta.base.workflow.foundations.WorkflowOp"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	String zfjcType = request.getParameter("zfjcType");
	String yw_guid = request.getParameter("yw_guid");
	String yw_guidHead = yw_guid.substring(0,2);
	String wfInsTaskId = request.getParameter("wfInsTaskId");
	String wfInsId = request.getParameter("wfInsId");
	String wfId = request.getParameter("wfId");
	String zfjcName = new String(request.getParameter("zfjcName").getBytes("ISO-8859-1"), "utf-8");
    IWorkflowOp workflow = WorkflowOp.getInstance();
	String activityName = workflow.getActivityNameByWfInsID(wfInsId);
	String returnPath=request.getParameter("returnPath");
	returnPath = basePath + returnPath;
	String edit=request.getParameter("edit");
	String lyType = request.getParameter("lyType"); 
	String buttonHidden = request.getParameter("buttonHidden"); 
	String isHaveSave = request.getParameter("isHaveSave");
	String isFirst = request.getParameter("isFirst");
	String type = request.getParameter("type");
	 String fixed = (String)request.getParameter("fixed");
	String sb = "zfjcType=" + zfjcType + "&yw_guid=" + yw_guid
			+ "&activityName=" + activityName + "&wfInsId=" + wfInsId
			+ "&wfInsTaskId=" + wfInsTaskId + "&wfId=" + wfId
			+ "&zfjcName=" + zfjcName+"&returnPath="+returnPath+"&edit="+edit+"&lyType="+lyType+"&buttonHidden="+buttonHidden
			+ "&isHaveSave=" +isHaveSave+"&isFirst="+isFirst+"&type="+type+"&fixed="+fixed;
	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	String fullName=((User) principal).getFullName();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>待办任务处理</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<%@ include file="/base/include/ext.jspf"%>
		<%@ include file="/base/include/restRequest.jspf" %>
		<script>
	function deleteTask() {
		lower.Ext.MessageBox.confirm('注意', '终止后不可恢复，是否继续?', function(btn) {
			if (btn == 'yes') {
				upper.stopTask(btn);
			}
		})
	}
	
	//启动立案查处工作流
	function la(){
	  var zfjctype=<%=zfjcType%>;
	  	if(zfjctype=='8'){//信访
	  	  var parameter="zfjcType=7&yw_guid=<%=yw_guid%>&flag=1&lyType='XF'&returnPath=<%=returnPath%>";
			    var result=window.showModalDialog("<%=basePath%>web/jinan/xfaj/xzxfaj/xfajLa.jsp?wfInsId=<%=wfInsId%>&yw_guid=<%=yw_guid%>&wfInsTaskId=<%=wfInsTaskId%>&wfId=<%=wfId%>&activityName=<%=URLEncoder.encode(activityName, "UTF-8")%>",window,"dialogWidth=400px;dialogHeight=330px;status=no;scroll=no"); 
	            if(result[0]=="1")//选择的是“法规办”
	            {
		         location.href="<%=returnPath%>";
		     	}
		     	if(result[0]=="3")//选择的是"立案查处"
		     	{  
		     	 var path=result[1];
		     	 location.href="<%=basePath%>"+path+"&returnPath=<%=returnPath%>";
		     	}
	  }else{
	  	  Ext.Msg.confirm("请确认","确定要立案吗？", function(button,text){ 
                if(button=="yes"){//这个是通过yes or no 来确定的点击值 
    				 putClientCommond("startWorkflow","startWorkflow");
	                 putRestParameter("zfjcType","7");
	                 putRestParameter("yw_guid","<%=yw_guid%>");
	                 putRestParameter("userName","<%=fullName%>");
	                 var path=restRequest();
	                 document.location.href="<%=basePath%>"+path.urlPath+"&returnPath=<%=returnPath%>";
                } 
            });  
	  }
	  
	}
	///////【回退】
	function rollBack() {
	   //信访 回退时要求写回退原因
	   var guidHead='<%=yw_guidHead%>';
	 if(guidHead=='XF'){
	   var result=window.showModalDialog("<%=basePath%>web/jinan/xfaj/dbxf/htyy.jsp?wfInsId=<%=wfInsId%>&yw_guid=<%=yw_guid%>&wfInsTaskId=<%=wfInsTaskId%>&wfId=<%=wfId%>&activityName=<%=URLEncoder.encode(activityName, "UTF-8")%>",window,"dialogWidth=340px;dialogHeight=240px;status=no;scroll=no"); 
	   if(result!=null){
	       if(result[0]=='1'){//success
	       location.href="<%=returnPath%>";
	     }
	     else if(result[0]=='2'){//false
	     }
	     else if(result[0]=='3'){//error
	     }
	   }
	    
	}
	//不是信访时
	else{
	   lower.Ext.MessageBox.confirm('注意', '您确定要回退到给上一办理人吗？', function(btn) {
			if (btn == 'yes') {
				upper.rollBack(btn);
			}
		})
	} 
}

	function show(showResult) {
		lower.Ext.MessageBox.alert('提示', showResult, showResult);
	}
</script>
	</head>
	<frameset id="main" rows="30,*" frameborder="no" border="0"
		framespacing="0">
		<frame id="upper" name="upper" scrolling="NO" noresize
			src="<%=basePath%>model/workflow/dispense.jsp?<%=sb.toString()%>" />
		<frame id="lower" name="lower" scrolling="NO" noresize
			src="<%=basePath%>model/resourcetree/resourceTree.jsp?<%=sb.toString()%>" />
	</frameset>
	<body>
</body>

</html>
