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
Ext.onReady(function() {
		var historyform = new Ext.form.FormPanel({
					applyTo : 'backform',
					id : 'myForm',
					baseCls : 'x-plain',
					autoHeight : true,
					frame : true,
					labelWidth : 60,
					buttonAlign : 'center',
					bodyStyle : 'padding:0px 0px 0',
					width : 360,
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
					buttons : [{
								text : '播放',
								id : 'play',
								handler : function(){
									var url = basePath + "playManageAC.do?method=playback&gpsid=" + gpsid;
	if (historyform.getForm().isValid()) {
		if (Ext.getCmp('starttime').getValue() < Ext.getCmp('endtime').getValue()) {
			historywin.hide();
			historyform.form.submit({
						url : url,
						waitMsg : '正在加载数据,请稍候... ',
						success : function(form, action) {
							var msg = Ext.decode(action.result.msg);
							transfer(msg, Ext.getCmp('fast').getValue());
						},
						failure : function() {
							Ext.Msg.alert('提示', '请稍后重试或联系管理员。');
						}
					});
		} else {
			Ext.Msg.alert('提示', '开始时间必须小于结束时间！');
		}
	}
								}
							}, {
								text : '重置',
								handler : function() {
									historyform.getForm().reset();
								}
							}]
				});
     locationForm.render('form-ct');
});


</script>
	</head>
	<body bgcolor="#FFFFFF">
		<div id="backform" style="width: 100%; height: 18%;"></div>
	</body>
</html>
