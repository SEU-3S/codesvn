<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.klspta.model.mapconfig.TBQuery"%>
<%@ taglib uri="/WEB-INF/taglib/queryLabel.tld" prefix="common"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
    String type = request.getParameter("type");
    String tbbh = request.getParameter("tbbh");   
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>PDA列表</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	    <%@ include file="/base/include/ext.jspf" %>
	    <%@ include file="/base/include/restRequest.jspf" %>
	</head>
	<script>
    var myData;
    Ext.onReady(function(){
    putClientCommond("tBQuery","getWpjctbSPdata");
    putRestParameter("tbbh", '<%=tbbh%>');
	var myData= restRequest();
    var store = new Ext.data.JsonStore({
    proxy: new Ext.ux.data.PagingMemoryProxy(myData),
        fields: [
           {name: 'TBBH'},
           {name: 'YGMJ'},
           {name: 'YGBL'},
           {name: 'YEAR'},
           {name: 'XMMC'},
           {name: 'PZWH'},
           {name: 'PZSJ'}
        ]
    });  
       var height=document.body.clientHeight;
       var width=document.body.clientWidth;
       var grid = new Ext.grid.GridPanel({
        store: store, 
        height:height,
        width:width,
        columns: [
            {header: '编号',    width: width*0.2},
            {header: '压盖面积', width: width*0.2},
            {header: '压盖比率', width: width*0.2},
            {header: '卫片年度', width: width*0.2},
            {header: '项目名称', width: width*0.280},
            {header: '批准文号', width: width*0.2},
            {header: '批准时间', width: width*0.4}
        ],
         listeners:{
		       rowclick:function(grid,row){	
		       }
         }      
    });  
    store.load(); 
    grid.render('status_grid');
 })
	</script>
	<body>
		<div id="status_grid"></div>
	</body>
</html>