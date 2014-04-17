<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>location</title>
		<%@ include file="/base/include/ext.jspf" %>
		<!--  <script type="text/javascript" src="<%=basePath%>/model/giscomponents/shutter/location.js"></script> -->
		<%@ include file="/base/include/restRequest.jspf" %>
		<!-- <script type="text/javascript" src="<%=basePath %>console/mapManage/mapTreeInfo.js"></script> -->
		<script type="text/javascript" src="<%=basePath%>base/fxgis/framework/js/json2.js"></script>
		
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
		var path = "<%=basePath%>";
		var actionName = "mapconfig";
		var actionMethod = "getMapServices";
		var parameter = "";
		var mapService = ajaxRequest(path, actionName, actionMethod, parameter);
		var mapService = eval(mapService);		
		var  mapService = stringToArray(mapService);
		mapService = JSON.parse(mapService);			
		function stringToArray(mapServiceid){
			var str = '[';
			for(var i = 0; i < mapServiceid.length; i++){
				if(i==mapServiceid.length-1){
					str+='{"VALUE":"'+mapServiceid[i].ID+'","TEXT":"'+ mapServiceid[i].ALIAS+'"}]'; 
					break;
				}
		   		str+='{"VALUE":"'+ mapServiceid[i].ID+'","TEXT":"'+ mapServiceid[i].ALIAS+'"},'; 
			}
			return str;
		}
	﻿﻿﻿﻿﻿Ext.onReady(function(){
		var locationForm = new Ext.FormPanel({
        labelWidth:60, // label settings here cascade unless overridden
        labelAlign:"right",
        frame:true,
        bodyStyle:'padding:0px 0px 0',
        width: 330,
        buttonAlign:'center',
        defaults: {width: 150},
        defaultType: 'textfield',
        submit:function(){
        	var value = locationForm.get('serverId').getValue();
        	var arr=value.split("@");
        	value=arr[0];
        	parent.center.frames["lower"].swfobject.getObjectById("FxGIS").panmap();
        	parent.center.frames["lower"].swfobject.getObjectById("FxGIS").swipe_clickHandler(value);
    	},
		reset:function(){
			parent.center.frames["lower"].swfobject.getObjectById("FxGIS").panmap();
      	},
      	shoudian:function(){
      		var value = locationForm.get('serverId').getValue();
      		var arr=value.split("@");
        	value=arr[0];
        	parent.center.frames["lower"].swfobject.getObjectById("FxGIS").panmap();
        	parent.center.frames["lower"].swfobject.getObjectById("FxGIS").spotlight_clickHandler(value, "50");
      	},
    	items: [{
                xtype     : 'combo', 
		       id:'serverId',
		       editable: false,  
		       fieldLabel: '地图图层',
		       valueField:     'VALUE', 
		       displayField:   'TEXT',  
		       emptyText:'请选择图层',
	           forceSelection: true,//强制选择    
	           triggerAction: 'all',//点击下拉按钮全部显示     
	           mode:'local',   
	           store: new Ext.data.JsonStore({   
	                 fields : ['TEXT', 'VALUE'],  
	                 data:mapService 	                             
	           })
	           }
        ],
         buttons: [{
            text: '卷帘',
            handler: function(){
                locationForm.form.submit();
            }
        },{
            text: '手电筒',
            handler: function(){
                locationForm.form.shoudian();
            }
        },{
            text: '取消',
            type:'reset',
            handler: function(){
                locationForm.form.reset();
            }
        }]
    	});
	 	locationForm.render('form-ct');
	})
	</script>
	</head>
	<body bgcolor="#FFFFFF">
		<div id="form-ct" style="width: 100%; height: 100%;"></div>
	</body>
</html>
