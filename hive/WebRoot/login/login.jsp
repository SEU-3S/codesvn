<%@ page language="java"  pageEncoding="utf-8"%>
<%@page import="com.klspta.model.projectinfo.ProjectInfo;"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String type = request.getParameter("type");
    ProjectInfo project=ProjectInfo.getInstance();
    String name = project.getProjectName();
    String loginname1=project.getProjectLoginName1();
    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>
    <%=loginname1%>
    </title>
    <style type="text/css">
    body {
	    background-image: url(images/<%=name%>/login_bk.jpg);
    }
    font.style1 {
    font-family: "微软雅黑";
    color: #FFFFFF;
    font-size:40px;
    font-weight: bolder;
  }
  font.style2 {
    color: #BAF7FF;
    font-size:24px;
    font-weight: bolder;
   }
    </style>
</head>

<body onload="runing()">

<script>
function runing(){
document.location.href='<%=basePath%>/login/project/<%=name%>/login.jsp?type=<%=type%>';
}
</script>
</body>
</html>
