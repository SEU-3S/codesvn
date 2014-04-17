<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.klspta.base.workflow.foundations.IWorkflowOp"%>
<%@page import="com.klspta.base.workflow.foundations.WorkflowOp"%>
<%@page import="com.klspta.base.workflow.bean.DoNextBean"%>
<%@page import="com.klspta.console.ManagerFactory"%>
<%@page import="com.klspta.base.workflow.foundations.JBPMServices"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="com.klspta.console.user.User"%>
<%@page import="org.jbpm.pvm.internal.model.ProcessDefinitionImpl"%>
<%@page import="org.jbpm.api.model.Activity"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

    ///////////////////必需参数///////////////////////
	String wfInsId = request.getParameter("wfInsId");//流程实例ID
	String yw_guid=request.getParameter("yw_guid");//业务ID
	
	String wfInsTaskId = JBPMServices.getInstance().getTaskService().createTaskQuery().executionId(wfInsId).uniqueResult().getId();
	String wfId = JBPMServices.getInstance().getExecutionService().findExecutionById(wfInsId).getProcessDefinitionId();
	String activityName = JBPMServices.getInstance().getTaskService().createTaskQuery().executionId(wfInsId).uniqueResult().getActivityName();

	IWorkflowOp workflowOp = WorkflowOp.getInstance();
	DoNextBean ac = workflowOp.getNextInfo(wfId,wfInsId,wfInsTaskId, yw_guid);
	Set<String> set = ac.getNextNames();
	Iterator<String> it = set.iterator();
	String currentNode = null;
	String currentRole = null;
	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	String fullName=((User) principal).getFullName();
	
	//判断下一个节点是否到最后一个节点
	ProcessDefinitionImpl pd = (ProcessDefinitionImpl) JBPMServices.getInstance().getRepositoryService()
		.createProcessDefinitionQuery().processDefinitionId(wfId).uniqueResult();
	Activity activity=pd.findActivity(activityName).getDefaultOutgoingTransition().getDestination();
	boolean isEnd=false;
	if(activity.getType().equals("end")){
	   isEnd=true;
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>任务移交窗口</title>
    <%@ include file="/base/include/restRequest.jspf" %>
    <%@ include file="/base/include/ext.jspf" %>
    		<style type="text/css">
body {
	background-image: url(images/main_bk.gif);
	margin: 0px;
	padding: 0px;
}

.btn {
	background: url(images/btn_bk.gif);
	CURSOR: hand;
	FONT-SIZE: 12px;
	color: white;
	BORDER-RIGHT: #002D96 0px solid;
	BORDER-TOP: #002D96 0px solid;
	BORDER-LEFT: #002D96 0px solid;
	BORDER-BOTTOM: #002D96 0px solid
}
</style>
		<script>
   var assignee="false";
   function transfer(){
        var fullName=document.getElementById("users");
        if(fullName!=null){
          var fullName=encodeURI(encodeURI(fullName.value));
        }  
        var temp = document.getElementsByName("nextName");
        var nextName=null;
        for(var i=0;i<temp.length;i++){
          if(temp[i].checked) nextName = temp[i].value;
        }
        nextName=encodeURI(encodeURI(nextName)); 
        var name=encodeURI(encodeURI('<%=fullName%>'));  
        var parameter="op=donext&assignee="+assignee+"&wfInsTaskId=<%=wfInsTaskId%>&nextNodeName="+nextName
                       +"&nextFullName="+fullName+"&wfInsId=<%=wfInsId%>&yw_guid=<%=yw_guid%>&fullName="+name;
        var result = ajaxRequest("<%=basePath%>","workflowNodeOperation","transferTask",parameter);
        if(result=="true"){
              alert("已移交！");
              var MyArgs = new Array("1","2");
              window.returnValue = MyArgs;  
              window.close();
        }else{      
              alert("移交异常！");
        }
   }
    
   function changeRole(){
       var role=document.getElementById("roles");
       var user=document.getElementById("users");
       //reset old option
       if(role!=null){
         role.options.length = 0;
       }
       //get roles 
       var roles=getRoles();
       if(roles!="null"&&roles!=null){
       	/*
         var roleArray=roles.split(",");
         for(var i=0;i<roleArray.length/2;i++){
            role.options.add(new Option(roleArray[i*2+1],roleArray[i*2])); 
         }
         var users=getUsers(roleArray[0]);
         var userArray=users.split(",");
         user.options.length = 0; 
         //user.options.add(new Option("所有人","all"));
         for(var i=0;i<userArray.length;i++){
            user.options.add(new Option(userArray[i],userArray[i])); 
         }
         */
         var users;
         user.options.length = 0;
         var userArray = new Array();
         for(var i = 0; i < roles.length; i++){
         	var rolename = roles[i].ROLENAME;
         	var roleid=roles[i].roleid;
         	role.options.add(new Option(rolename, roleid));
         	users = getUsers(roles[i].roleid);
         	userArray = users.split(",");
         	for(var j = 0; j < userArray.length; j++){
         		user.options.add(new Option(userArray[j], userArray[j]));
         	}
         }
         document.getElementById('roleDiv').style.display="block";
       }else{
		 document.getElementById('roleDiv').style.display="none";
         users=getUsers(null);
         user.options.length = 0;
         user.options.add(new Option(users,users));
         assignee="true";
       }
   }
  
   function changeUser(){
        var role=document.getElementById("roles").value;
        var user=document.getElementById("users");
        var users=getUsers(role);
        var userArray=users.split(",");
        user.options.length = 0; 
        /*
        for(var i=0;i<userArray.length/2;i++){
          user.options.add(new Option(userArray[i*2+1],userArray[i*2])); 
        }
        */
        for(var i = 0; i < userArray.length; i++){
        	user.options.add(new Option(userArray[i], userArray[i]));
        }
   }
   
   function getRoles(){ 
        var temp = document.getElementsByName("nextName");
        var nextName;
        for(var i=0;i<temp.length;i++){
             if(temp[i].checked) nextName = temp[i].value;
        }
        nextName=encodeURI(encodeURI(nextName));
        var parameter="wfId=<%=wfId%>&activityName="+nextName;
        var result = ajaxRequest("<%=basePath%>","workflowNodeOperation","getRoleByActivityName",parameter);
        result = eval(result);
        return result;
   }
   
    function getUsers(roleid){
        var parameter;
        if(roleid!=null){
          parameter="roleId="+roleid;
        }else{
          parameter="wfInsId=<%=wfInsId%>";
        }
        var result = ajaxRequest("<%=basePath%>","workflowNodeOperation","getUsersByRoleId",parameter);
        return result;
   }
      
   function closeWin(){
       window.close();
   }
   
   function bindCheck(){
        document.getElementsByName("nextName")[0].checked=true;
   }
</script>
	</head>

	<body onload="bindCheck()">
		<div
			style="width: 300px; height: 150px; margin: auto; margin-top: 50px">
			<form>
				<font color="#804000"><b>下一办理环节：</b> </font>
				<br />
				<br />
				<%
					int count = 0;
					while (it.hasNext()) {
						String str = (String) it.next();
						if (!str.endsWith(activityName)) {
							count++;
							if (count == 1) {
								currentNode = str;
							}
				%>
				&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
				<input type="radio" name="nextName" onclick="changeRole()"
					value='<%=str%>' /><%=str%><br />
				<br />
				<%
					}
					}
				%>


				<%
					if (!isEnd) {
						List<Map<String, Object>> Roles = workflowOp.getAllRoles(currentNode, wfId);
						if (Roles != null) {
				%>
				<div id="roleDiv">
				<font color="#804000"><b>移交机构：</b> </font>
				<select id="roles" onchange="changeUser()" style="width: 200px">
					<%
						for (int i = 0; i < Roles.size(); i++) {
									Map<String, Object> roleMap = (Map<String, Object>) Roles.get(i);
									if (i == 0) {
										currentRole = (String) roleMap.get("roleid");
									}
					%>
					<option value='<%=(String) roleMap.get("roleid")%>'><%=(String) roleMap.get("ROLENAME")%></option>
					<%
						}
					%>
				</select>
				</div>
				<br />
				<br />
				<font color="#804000"><b>移&nbsp&nbsp交&nbsp&nbsp给：</b> </font>
				<select id="users" style="width: 200px">
					<%
						List<Object> initUsers = ManagerFactory.getUserManager().getAllUser(currentRole);
								for (int i = 0; i < initUsers.size(); i++) {
									Map<String, Object> userMap = (Map<String, Object>) initUsers.get(i);
					%>
					<option value='<%=(String) userMap.get("fullname")%>'><%=(String) userMap.get("fullname")%></option>
					<%
						}
					%>
				</select>
				<%
					} else {
							String owner = JBPMServices.getInstance().getExecutionService().getVariable(wfInsId, "owner")
									.toString();
				%>
				<div id="roleDiv"></div>
				<br />
				<br />
				<font color="#804000"><b>移&nbsp&nbsp交&nbsp&nbsp给：</b> </font>
				<select id="users" style="width: 200px">
					<option value='<%=owner%>'><%=owner%></option>
				</select>
				<script type="text/javascript">
				assignee="true";
	           </script>
				<%
					}
					}
				%>



				<br />
				<br />
				<%
					if (isEnd) {
				%>
				&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
				<input type="button" class='btn' onclick="transfer()" value="结束"
					style="width: 61px; height: 22px" />
				&nbsp;&nbsp;&nbsp;
				<%
					} else {
				%>
				&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
				<input type="button" class='btn' onclick="transfer()" value="移 交"
					style="width: 61px; height: 22px" />
				&nbsp;&nbsp;&nbsp;
				<%
					}
				%>
				<input type="button" class='btn' value="关 闭" onclick="closeWin()"
					style="width: 61px; height: 22px" />
			</form>
		</div>
	</body>
</html>