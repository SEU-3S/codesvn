<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.klspta.base.util.UtilFactory"%>
<%@ taglib uri="/WEB-INF/taglib/queryLabel.tld" prefix="common"%>
<%
	String path = request.getContextPath();
	String basePath = request.getServerName() + ":" + request.getServerPort() + path + "/";
	String gisapiPath = basePath + "thirdres/arcgis_js_api/library/2.5/arcgis_compact";
	basePath = request.getScheme() + "://" + basePath; 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<script>
var gisapiPath = "<%=gisapiPath%>";
</script>
	<head>
		<title>analysis</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
        <%@ include file="/base/include/ext.jspf" %>
        <%@ include file="/base/include/restRequest.jspf" %>
        <script src="<%=basePath%>base/thirdres/ext/examples/ux/fileuploadfield/FileUploadField.js" type="text/javascript"></script>
        <script src="<%=basePath%>model/fxgiscomponents/importShapefile/importShapefile.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>model/fxgiscomponents/infoQuery/ringsToJson.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>base/thirdres/ext/examples/ux/fileuploadfield/css/fileuploadfield.css"/>
		<style type="text/css">
    html,body {
	    font: normal 12px verdana;
	    margin: 0;
	    padding: 0;
	    border: 0 none;
	    overflow: hidden;
	    height: 100%;
    }

    .upload-icon { background: url('<%=basePath%>thirdres/ext/examples/shared/icons/fam/image_add.png') no-repeat 0 0 !important;}
</style>
<script>
var store;
var myData;
Ext.onReady(function(){
 myData=eval(myData);
 store = new Ext.data.ArrayStore({
    proxy: new Ext.ux.data.PagingMemoryProxy(myData),
        fields: [
           {name: '编号'},
           {name: '面积'},
           {name: '周长'},
           {name:'geo'}
        ]
    });  
       var grid = new Ext.grid.GridPanel({
        store: store, 
        id:'gridID',
        height:430,
        width:300,
        columns: [
            {header: '编号', width: 60},
            {header: '面积', width: 120},
            {header: '周长', width: 120},
            {header:'loc',hidden:true,dataIndex:'geo'}
        ],
        listeners:{
		        rowclick:function(grid,row){
		        var rowIndex = grid.store.indexOf(grid.getSelectionModel().getSelected());
   				var rings = grid.getStore().getAt(rowIndex).get('geo');
   				var jsonstring=toJsonString(rings);
  				//图斑定位
  				parent.center.frames["lower"].swfobject.getObjectById("FxGIS").doLocation(jsonstring);
		       }
         },
        bbar: new Ext.PagingToolbar({
        pageSize: 15,
        store: store,
        displayInfo: true,
            displayMsg: '共{2}条，当前为：{0} - {1}条',
            emptyMsg: "无记录",
        plugins: new Ext.ux.ProgressBarPager()
        })    
    });  
    grid.render('status_grid');
 });
 
  putClientCommond("mapconfig","getMapExtent");
  var res = restRequest();
  var _$WKID = res[0].WKID;
  var basePath = "<%=basePath%>";
 </script>
	</head>
	<body bgcolor="#FFFFFF">
		<div id="form-ct" style="width:100%; "></div>
		<div id="status_grid"></div>
	</body>
</html>
