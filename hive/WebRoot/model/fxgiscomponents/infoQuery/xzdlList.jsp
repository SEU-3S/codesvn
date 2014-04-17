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
	var myData = [["0189","有林地","温泉村农民集体","84.5亩"],["0028","沟渠","温泉村农民集体","50.1亩"]];
    var store = new Ext.data.ArrayStore({
    proxy: new Ext.ux.data.PagingMemoryProxy(myData),
        fields: [
           {name: '图斑编号'},
           {name: '地类名称'},
           {name: '权属单位名称'},
           {name: '压盖面积'}
        ]
    });  
       var grid = new Ext.grid.GridPanel({
        store: store, 
        height:300,
        width:340,
        columns: [
            {header: '图斑编号', width: 65},
            {header: '地类名称', width: 65},
            {header: '权属单位名称', width: 100},
            {header: '压盖面积', width: 70}
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
	    <div style="font-size:12px">总面积：134.6亩<br>农用地：88.2亩（耕地：70.6亩）<br>建设用地：32.0亩<br>未利用地14.4亩</div>
		<div id="status_grid"></div>
	</body>
</html>