<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>地图服务管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="/base/include/restRequest.jspf"%>
	<%@ include file="/base/include/ext.jspf" %>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  <script type="text/javascript">
  // 获取所有地图数据
  /*
  	var path = "<%=basePath%>";
	var actionName = "mapAuthorOperation";
	var actionMethod = "getAllMapService";
	var parameter = "";
	var myData = ajaxRequest(path, actionName, actionMethod, parameter);
	var mapService = eval(myData);
	*/
	putClientCommond("mapAuthorOperation","getAllMapService");
	var myData=restRequest();
    var mapService = eval(myData);	
	Ext.onReady(function(){
		var store = new Ext.data.ArrayStore({
			proxy: new Ext.ux.data.PagingMemoryProxy(mapService),
			remoteSort:true,
			fields:[
				{name: '序号'},
           		{name: '名称'},
           		{name: 'URL'},
           		{name: '类型'},
           		{name: '透明度'},
           		{name: 'DEFAULTON'},
           		{name: '格式'},
           		{name: '是否启用'},
           		{name: '顺序'},
           		{name: '修改'},
           		{name: '删除'},
			]
		});
	
  
	  	store.load({params:{start:0, limit:15}});
	  	
	  	var grid = new Ext.grid.GridPanel({
	  		store: store,
	  		columns:[
				{header: '序号', width:80},
	           	{header: '名称', width:80},
	           	{header: 'URL', width:195},
	           	{header: '类型', width:50},
	           	{header: '透明度', width:50},
	           	{header: 'DEFAULTON', hidden:true, width:30},
	           	{header: '格式', width:50},
	           	{header: '是否启用', width:60},
	           	{header: '顺序', width:40, sortable: true},
	           	{header: '修改', width: 40, sortable: false, renderer: modify}, 
            	{header: '删除', width: 50, sortable: false, renderer: del}   
			],
			stripeRows: true,
			height: 480,
			title:'系统地图服务列表',
			stateful:true,
			stateId: 'grid',
			bbar: new Ext.PagingToolbar({
				pageSize:15,
				store: store,
				displayInfo:true,
					displayMsg: '共{2}条，当前为：{0} - {1}条',
	            	emptyMsg: "无记录",
	            plugins:new Ext.ux.ProgressBarPager()
			}),
				buttons:[{
					text:'新增',handler:createMap
				}]
	  	});
	  	grid.render('mygrid_container');
	})
	
	//处理删除操作
	function del(id){
		//alert(id);
		return "<span style='cursor:pointer;' onclick='deleteMap(\""+id+"\")'><img src='base/gis/images/delete.png' alt='删除'/></span>";
	}
	//处理查看操作
	function modify(id){
		//alert(id);
		return "<span style='cursor:pointer;' onclick='modifyMap(\""+id+"\")'><img src='base/gis/images/conf.png' alt='修改'/></span>";
	}
	
	function createMap(){
		parent.mapinfo.location.href="mapServiceInfo.jsp?serverid=" + newGuid();
		parent.parent.Ext.getCmp('west').collapse();
		document.location.reload();
	}
	
	function modifyMap(id){
		parent.mapinfo.location.href="mapServiceInfo.jsp?serverid=" + id;
		parent.parent.Ext.getCmp('west').collapse();
		document.location.reload();
	}
	
	 function newGuid(){ 
	    var guid = ""; 
	    for (var i = 1; i <= 32; i++){ 
	        var n = Math.floor(Math.random()*16.0).toString(16); 
	        guid += n; 
	    } 
	    return guid; 
	}
	
	
  
  	function deleteMap(id){
  		var mapId = id;
  		Ext.MessageBox.confirm('注意', '删除后不能恢复，您确定吗？',function(btn){
	  		if(btn=='yes'){
	  			/*
					var path = "<%=basePath%>";
			    	var actionName = "mapAuthorOperation";
			    	var actionMethod = "deleteMapServiceById";
			    	var parameter="id="+id;
					var result = ajaxRequest(path,actionName,actionMethod,parameter);
				*/
				putClientCommond("mapAuthorOperation","deleteMapServiceById");
				putRestParameter("id",id);
				var result=restRequest();
				document.location.reload();
			}else{
				return false;
			}
  		});
  	}
  </script>
 </head>
  	<body  bgcolor="#FFFFFF" topmargin="0" leftmargin="0">
		<div id="mygrid_container" style="width: 700; height: 100%;"></div>
	</body>
</html>
