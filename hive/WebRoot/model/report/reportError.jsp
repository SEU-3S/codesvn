<%@ page contentType="text/html; charset=utf-8" language="java"%>
<html>
	<head>
		<title>报表模块异常处理</title>
		<style>
<!--
H1 {
	font-family: Tahoma, Arial, sans-serif;
	color: white;
	background-color: #525D76;
	font-size: 25px;
}

H2 {
	font-family: Tahoma, Arial, sans-serif;
	color: white;
	background-color: #525D76;
	font-size: 16px;
}

H3 {
	font-family: Tahoma, Arial, sans-serif;
	color: white;
	background-color: #525D76;
	font-size: 14px;
}

BODY {
	font-family: Tahoma, Arial, sans-serif;
	color: black;
	background-color: white;
}

B {
	font-family: Tahoma, Arial, sans-serif;
	color: white;
	background-color: #525D76;
	font-size: 20px;
}

u {
	font-family: Tahoma, Arial, sans-serif;
	background: white;
	color: black;
	font-size: 20px;
}

A {
	color: black;
}

A.name {
	color: black;
}

HR {
	color: #525D76;
}
-->
</style>
<script type="text/javascript" language="javascript">
function showOrCloseDiv(divId)
	{
		var div=document.getElementById(divId);
		div.style.display=(div.style.display=="block"||div.style.display==""? "none":"block");
	}
</script>
	</head>
	<body>
		<h1>
			报表模块错误信息显示
		</h1>
		<HR size="1" noshade="noshade">
		<b>错误信息</b>
		<u><%=session.getAttribute("errormessage")%></u><br/><br/>
		<div id="handler" onclick="showOrCloseDiv('detail')" style="cursor:hand">
			<b>点击查看详细描述</b>
		</div>
		<div id="detail" class="detail" style="display: none">
			<u><%=session.getAttribute("errordetail")+"\n"%></u>
		</div>
		<HR size="1" noshade="noshade">
		<h3>
		</h3>
	</body>
</html>
<%
	session.removeAttribute("errormessage");
	session.removeAttribute("errordetail");
%>
