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
		var myData ;
Ext.onReady(function() {
		putClientCommond("mapconfig","getMapProperties");
        putRestParameter("type","");
	    myData = restRequest();
	var dtServiceData = new Ext.data.JsonStore({
		data: myData,
		fields: ["TEXT","VALUE"]
	});
	
    var locationForm = new Ext.FormPanel({
        labelWidth: 70, // label settings here cascade unless overridden
        labelAlign:"center",
        frame:true,
        bodyStyle:'padding:0px 0px 0',
        width: 300,
        defaults: {width: 135},
      //  defaultType: 'textfield',

    submit:function(){
       window.open("<%=basePath%>base/fxgis/fx/PlayBack.html");
    },
        items : [{
								xtype : 'datetimefield',
								id : 'starttime',
								anchor : '100%',
								format : 'Y-m-d H:i:s',
								allowBlank : false,
								blankText : '开始时间不能为空',
								editable : false,
								emptyText : "请选择",
								fieldLabel : '开始时间'
							}, {
								xtype : 'datetimefield',
								id : 'endtime',
								anchor : '100%',
								format : 'Y-m-d H:i:s',
								allowBlank : false,
								blankText : '结束时间不能为空',
								editable : false,
								emptyText : "请选择",
								fieldLabel : '结束时间'
							}, {
								xtype : 'radiogroup',
								fieldLabel : '播放速度',
								items : [{
											boxLabel : '快',
											checked : false,
											inputValue : 1,
											id : 'fast',
											name : 'speed'
										}, {
											boxLabel : '慢',
											checked : true,
											inputValue : 2,
											id : 'slow',
											name : 'speed'
										}]
							}],
        buttons: [{
            text: '播放',handler: function(){
                locationForm.form.submit();
            }
        }
        ]
    });
    locationForm.render('form-ct');
    
});

function viewDetail(){
  var rings="{\"rings\":[[[40449368,3620962],[40449375,3620791],[40449552,3620788],[40449538,3620899],[40449368,3620962]]],\"spatialReference\":{\"wkid\":2364}}";
  parent.center.frames["lower"].swfobject.getObjectById("FxGIS").doLocation(rings);
}

</script>
	</head>
	<body bgcolor="#FFFFFF">
		<div id="form-ct" style="width: 100%; height: 18%;"></div>
		<div id="status_grid" style="width: 100%; height: 80%;"></div>
	</body>
</html>
