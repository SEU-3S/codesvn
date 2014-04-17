<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.klspta.base.util.UtilFactory"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
	String myData2 = request.getParameter("myData");
	String myData =  new String (myData2.getBytes("iso8859-1"),"utf-8");
	StringBuffer suff = new StringBuffer();
	suff.append("[");
	suff.append(myData);
	suff.append("]");
	myData= suff.toString();
	System.out.println(myData);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>location</title>
		<%@ include file="/base/include/ext.jspf" %>
		<script type="text/javascript" src="<%=basePath%>/model/giscomponents/location/location1.js"></script>
		<%@ include file="/base/include/restRequest.jspf" %>
<style type="text/css">
html,body {
	font: normal 12px verdana;
	margin: 0;
	padding: 0;
	border: 0 none;
	overflow: hidden;
	height: 100%;
}
</style>
<script type="text/javascript">
	var myData;
    Ext.onReady(function(){
    myData = <%=myData%>;
	//myData=[["扬州是2010年度第5批次建设用地","0"],["扬州是2011年度第5批次建设用地","1"]];
    var store = new Ext.data.JsonStore({
    proxy: new Ext.ux.data.PagingMemoryProxy(myData),
        fields: [
           {name: 'XM_MC'},
           {name: 'ZD_GUID'},
           {name: 'GD_GUID'},
           {name: 'SHAPE'},
           {name: 'SHAPE.AREA'},
           {name: 'SHAPE.LEN'}
        ]
    });  
       var grid = new Ext.grid.GridPanel({
        store: store, 
        height:300,
        //width:350,
        columns: [
            {header: '名称',dataIndex:'XM_MC',width: 200},
            {header: '编号',dataIndex:'ZD_GUID',width: 200},
            {header: 'SHAPE',dataIndex:'SHAPE',width: 200},
            {header: 'SHAPE.AREA',dataIndex:'SHAPE.AREA',width: 150},
            {header: 'SHAPE.LEN',dataIndex:'SHAPE.LEN',width: 200}
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
	    <div style="font-size:12px"></div>
		<div id="status_grid"></div>
	</body>
</html>
