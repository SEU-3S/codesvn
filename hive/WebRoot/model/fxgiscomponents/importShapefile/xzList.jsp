<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/taglib/queryLabel.tld" prefix="common"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
    String type = request.getParameter("type");
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
	</head>
	<script>
    Ext.onReady(function(){
	var myData = [["土地坐落","李家湾村"],["农用地面积","65.3"],["农用地其中耕地面积","60.7"],["建设用地面积","45.2"],["未利用地面积","25.9"]];
    var store = new Ext.data.ArrayStore({
    proxy: new Ext.ux.data.PagingMemoryProxy(myData),
        fields: [
           {name: '名称'},
           {name: '属性'}
        ]
    });  
       var grid = new Ext.grid.GridPanel({
        store: store, 
        height:350,
        width:340,
        columns: [
            {header: '名称', width: 150},
            {header: '属性', width: 180}
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