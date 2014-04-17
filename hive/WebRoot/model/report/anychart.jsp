<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String id = request.getParameter("id");
	String condition = request.getParameter("condition");
	String fieldValue = request.getParameter("fieldValue");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>图表展示页</title>
		<script src="<%=basePath%>/base/thirdres/anyChart/binaries/js/AnyChart.js">
</script>
		<%@ include file="/base/include/ext.jspf"%>
		<%@ include file="/base/include/restRequest.jspf"%>
		<style type="text/css">
html,body,#chartContainer {
	width: 100%;
	height: 100%;
	padding: 0;
	margin: 0;
}
</style>
	</head>

	<body>
		<!--执行数据加载 add by 赵伟 2012-8-7-->

		<script type="text/javascript" language="javascript">
		
<!--获取资源 add by 赵伟 2012-8-8-->	
putClientCommond("anyChart", "getChartResource");
putRestParameter("id", "<%=id%>");
var res = eval(restRequest());

<!--获取数据 add by 赵伟 2012-8-8-->
putClientCommond("anyChart", "getChartData");
putRestParameter("sql", escape(escape(res[0].SQL)));
putRestParameter("fieldValue", escape(escape("<%=fieldValue%>")));
putRestParameter("condition", escape(escape("<%=condition%>")));
var result = eval(restRequest());

var chart = new AnyChart(
		'<%=basePath%>/base/thirdres/anyChart/binaries/swf/AnyChart.swf');
chart.setXMLFile('<%=basePath%>'+res[0].XMLPATH);
chart.width = '100%';
chart.height = '100%';

setTimeout(function() {
	for ( var i = 0; i < result.length; i++) {
		chart.view_addPoint("" + result[i].VIEWID, "" + result[i].SERIESID,
				"<point id='" + result[i].POINTNAME + "' name='"
						+ result[i].POINTNAME + "' y='" + result[i].POINTVALUE
						+ "' />");
	}
	chart.view_refresh(result[0].VIEWID);
}, 500);
chart.write();
</script>
	</body>
</html>
