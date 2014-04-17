<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.klspta.model.mapconfig.MapConfig"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
	//String data = new MapConfig().getMapServices();
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
var store;
var grid;
var myData;
var datas;
var mydata;
var height;
/*
function identifyCallback(s){
	    myData =s;
	    //alert(s)
	    myData=myData.substring(1,myData.length-1);
	    datas=myData.split(',');
	    //alert(datas)
	    mydata='[';
	    for(var i=0;i<datas.length;i++){
	    	mydata+='['+datas[i]+'],'
	    }
	    mydata=mydata.substring(0,mydata.length-1);
	    mydata+=']';
	    mydata=mydata.replace(new RegExp(':', 'g'), ',');
	    mydata=mydata.replace(new RegExp('\"', 'g'), '\'');
	    //alert(mydata)
	    parent.Ext.getCmp('east-panel').expand();
	    parent.Ext.getCmp('east-panel').setTitle('属性查询结果');
	   // mydata= [['XMMC','扬州铁路货运货场配套工程'],['Shape','Polygon'],['SHAPE.LEN','1189.632214'],['ZQBM','321000'],['SHAPE.AREA','92960.22537'],['YW_GUID','3210001018'],['DKMC','扬州铁路货站货场配套工程(二)'],['ZQMC','扬州市'],['PZWH','扬发改许发[2010]421号'],['PZRQ','Null'],['OBJECTID','1303'],['XBH','(扬)地呈字[2010]第18号'],['DKID','{B19D3794-4377-43CB-A885-BDE6F8175DDF}']]
	   myData=eval(mydata);
	   store.loadData(myData);
}
*/
Ext.onReady(function(){
   
    store = new Ext.data.ArrayStore({

        fields: [
           {name: '属性名'},
           {name: '属性值'}
        ]
    });  
   
     height=document.body.clientHeight;
     grid = new Ext.grid.GridPanel({
        store: store, 
        autoHeight:true,
        width:300,
        buttonAlign:'left',
        columns: [
            {header: '属性名', width: 120},
            {header: '属性值', width: 175}
        ]
            
    });  
    grid.render('status_grid');
})

function analyse(){
	
}
</script>
	</head>
	<body bgcolor="#FFFFFF">
		<div id="status_grid" style="width:100%; "></div>
	</body>
</html>
