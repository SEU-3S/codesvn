<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/taglib/queryLabel.tld" prefix="common"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
    String selectRowIndex = request.getParameter("selectRowIndex");
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
	var datas;
    var mydata;
    Ext.onReady(function(){
     var d=eval(parent.data);
     attrs=d[<%=selectRowIndex%>].feature.attributes;
	//myData = [["图斑编号","420282-0745"],["图斑面积","134.6"],["行政代码","370783001"],["图斑类型","未批先建"],["中心点坐标X","40385728.9"],["中心点坐标Y","4126772.9"],["前时相","20101118"],["后时相","20111201"],["年度","2011"]];
	//myData = [{mc:"图斑编号",sx:"420282-0745"},{mc:"图斑面积",sx:"134.6"}];
	  mydata=new Array();
	for (var key in attrs) {
	var array=new Array() 
	  array.push(key);
	  array.push(attrs[key]);
	     mydata.push(array);
	}
    var store = new Ext.data.ArrayStore({
    proxy: new Ext.ux.data.PagingMemoryProxy(mydata),
        fields: [
           {name: '名称'},
           {name: '属性'}
        ]
    });  
    var height=document.body.clientHeight;
       var grid = new Ext.grid.GridPanel({
        store: store, 
        height:height,
        width:350,
        columns: [
            {header: '名称', width: 170},
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