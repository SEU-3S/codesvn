<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.klspta.model.projectinfo.ProjectInfo"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String name = ProjectInfo.getInstance().getProjectName();
String loginname1 = ProjectInfo.getInstance()
		.getProjectLoginName1();
String loginname2 = ProjectInfo.getInstance()
		.getProjectLoginName2();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<meta http-equiv="X-UA-Compatible" content="IE=8">
<html>
  <head>
    <title><%=loginname1 %></title>
<script type="text/javascript">
function exitFullScreen() 
{ 
   var esc=window.event.keyCode; 
   if(esc==27) //判断是不是按的Esc键,27表示Esc键的keyCode. 
   {   
     var url=document.location.href;  
     var scrWidth=screen.availWidth;
     var scrHeight=screen.availHeight;   
     newWin=window.open(url,'','fullscreen=0,directories=1,location=1,menubar=1,resizable=1,scrollbars=1,status=1,titlebar=1,toolbar=1'); 
     newWin.moveTo(0,0);
     newWin.resizeTo(scrWidth,scrHeight);                               
     window.opener=null; 
     window.open('','_self');          
     window.close();                          
    }
} 
document.onkeydown = exitFullScreen; 
</script>
  </head>
  	<frameset id="main" name="main" rows="106,*" frameborder="no" border="0" framespacing="0" >
		<frame id="upper" name="upper" scrolling="NO" noresize src="upper.jsp" />
		<frame id="lower" name="lower" scrolling="NO" noresize src="consoleMain.jsp" />
	</frameset>
</html>
