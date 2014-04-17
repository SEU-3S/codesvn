<%@ page language="java"  pageEncoding="utf-8"%>
<%@page import="com.klspta.model.projectinfo.ProjectInfo;"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String type = request.getParameter("type");
    ProjectInfo project=ProjectInfo.getInstance();
  //  String name = project.getProjectName();
    String loginname1=project.getProjectLoginName1();
    String loginname2=project.getProjectLoginName2();
    if(type != null && "logout".equals(type)){
        type = "已登出！";
    }else if(type != null && "error".equals(type)){
    	type="用户名或密码错误！";
    }else{
        type = "&nbsp;";
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>
    <%=loginname1%>
    </title>
    <link href="../../css/style.css" rel="stylesheet" type="text/css" />
    <script src="<%=basePath%>/login/js/cookies.js" type="text/javascript"></script>
    <style type="text/css">
    body {
	    background-image: url(../../images/login_bk.jpg);
    }
    font.style1 {
    font-family: "黑体";
    color: #FFFFFF;
    font-size:30px;
    font-weight: bolder;
  }
  font.style2 {
    color: #BAF7FF;
    font-size:18px;
    font-weight: bolder;
   }
    </style>
</head>

<body >
<form id='loginForm' method="post" action='<%=basePath %>j_spring_security_check'>
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="595" valign="top" background="../../images/login.jpg">
    <table width="1000" border="0" cellspacing="0" cellpadding="0">
      <tr>
      <td  colspan="2"><div style="margin-top: 6%;margin-left: 25%"><font class="style1"><%=loginname1 %></font></div></td>
      </tr>
      <tr>
      <td colspan="2" ><div style="margin-top: 1%;margin-left: 26%" ><font class="style2"><%=loginname2 %></font></div></td>
      </tr>
      <tr>
        <td width="707" height="158">&nbsp;</td>
        <td width="293">&nbsp;</td>
      </tr>
     
      <tr>
        <td height="16">&nbsp;</td>
        <td style="padding-left:55px;padding-top:1px;"><label>
          <input name="j_username" type="text" class="input" id="j_username" />
        </label></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="16">&nbsp;</td>
        <td style="padding-left:55px;padding-top:2px;"><input name="j_password" type="password" class="input" id="j_password" /></td>
      </tr>
      <tr>
        <td height="53">&nbsp;</td>
        <td><table width="" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td style="width:150px;text-align: left"><span style="margin-left:-1px;color:red;font-size:12px;"><%=type%></span></td>
            <td width="51"><a href='javascript:login();' style="margin-left:0px;" ><img src="../../images/dl_btnA.gif" name="Image1" width="51" height="22" border="0" id="Image1"/></a></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</form>
<script>

function login(){
var loginName=document.getElementById('j_username').value;
setCookie('loginName',loginName,30);
document.getElementById('loginForm').submit();
}

var logName=getCookie('loginName');
if(logName){
if(logName=="undefined"){
    logName="";
    document.getElementById('j_username').focus(false,100);
}else{
    document.getElementById('j_username').value=logName;
    document.getElementById('j_password').focus(false,100);
}
}else{
document.getElementById('j_username').focus(false,100);
} 

function loginin() 
{ 
if(event.keyCode==13) 
{ 
login();
} 
}
document.onkeydown=loginin;
</script>
</body>
</html>
