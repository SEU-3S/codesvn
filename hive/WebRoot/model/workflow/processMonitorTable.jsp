<%@ page language="java"  pageEncoding="utf-8"%>
<%@page import="com.klspta.base.workflow.foundations.ProcessMonitor"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			
	String wfInsId=request.getParameter("wfInsId");	
	String wfId=request.getParameter("wfId");
	ProcessMonitor.isFlush=true;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'processMonitorTable.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<%@ include file="/base/include/restRequest.jspf" %>
		<%@ include file="/base/include/ext.jspf"%>
		<style type="text/css">
html,body {
	font: normal 12px verdana;
	margin: 0;
	padding: 0;
	border: 0 none;
	height: 100%;
	width: 100%;
}
</style>
		<script type="text/javascript">
		var myData;
Ext.onReady(function(){
	putClientCommond("processMonitor","getProcessMonitorList");
	putRestParameter("wfInsId","<%=wfInsId%>");
	putRestParameter("wfId","<%=wfId%>");
	myData = restRequest();
    var store = new Ext.data.JsonStore({
    proxy: new Ext.ux.data.PagingMemoryProxy(myData),
		remoteSort:true,
        fields: [
           {name: 'ACTIVITY_NAME_'},
           {name: 'ASSIGNEE_'},
           {name: 'START_'},
           {name: 'END_'}
        ]
    });
    
    store.load({params:{start:0, limit:5}});
    var width=document.body.clientWidth;
    var grid = new Ext.grid.GridPanel({
        store: store,
        columns: [
           {header: '办理节点', dataIndex:'ACTIVITY_NAME_',sortable: true, width:100},
           {header: '办理人', dataIndex:'ASSIGNEE_',sortable: true, width:90},
           {header: '开始时间', dataIndex:'START_',sortable: true, width:150},
           {header: '结束时间', dataIndex:'END_',sortable: true, width:150}
        ],
        stripeRows: true,
        height: 165,
        width:500,
        // config options for stateful behavior
        stateful: true,
        stateId: 'grid',
        bbar: new Ext.PagingToolbar({
        pageSize: 5,
        store: store,
        displayInfo: true,
            displayMsg: '共{2}条，当前为：{0} - {1}条',
            emptyMsg: "无记录",
        plugins: new Ext.ux.ProgressBarPager()
        })
    });  
    grid.render('mygrid_container'); 
    
    grid.on('mouseover', function(e){
    	var num = grid.getBottomToolbar().cursor;
		var index = grid.getView().findRowIndex(e.getTarget());
		if(index != false || index==0){
			index = num + index;
			var zuobiao=myData[index];
		    x=zuobiao.X;
			y=zuobiao.Y;
			height=zuobiao.HEIGHT;
			width=zuobiao.WIDTH;
			parent.document.getElementById('kuang').style.display="block";
		    parent.document.getElementById('kuang').style.left=x;
		    parent.document.getElementById('kuang').style.top=y+203;
		    parent.document.getElementById('kuang').style.border="4px solid blue";
		}
    });
    grid.on('mouseout', function(e){
        parent.document.getElementById('kuang').style.display="none";
    });
}
);

</script>
	</head>
	<body bgcolor="#FFFFFF" topmargin="0" leftmargin="0">
		<div id="mygrid_container" style="width: 100%; height: 100%;"></div>
	</body>
</html>
