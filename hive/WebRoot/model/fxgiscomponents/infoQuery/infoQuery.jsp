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
		var dkid;
		var dkmc;
		var width;
		var statusTab;
        var data;
        var selectRowIndex;
        var locationForm;
        var serviceid ;
        var layerid;
        var where;
        var queryfieldsinfo;
        var queryfields;
        var rr='';
        var temp;
        var queryfieldsLength=0;//查询列总个数
        var queryResult="";//查询结果
        var currentQueryField=0;//当前查询列的次序
Ext.onReady(function() {
		putClientCommond("mapconfig","getMapProperties");
        putRestParameter("type","");
	    myData = restRequest();
	    var dtServiceData = new Ext.data.JsonStore({
		data: myData,
		fields: ["TEXT","VALUE"]
	});
	  width=document.body.clientWidth;
     locationForm = new Ext.FormPanel({
        labelWidth: 70, // label settings here cascade unless overridden
        labelAlign:"center",
        frame:true,
        bodyStyle:'padding:0px 0px 0',
        width: width,
        defaults: {width: 135},
      //  defaultType: 'textfield',
        items: [{
                fieldLabel: '地图服务',
                xtype: 'combo',
                id: 'dtService',
                store: dtServiceData,
                mode:"local", 
                displayField:'TEXT',
	            valueField: 'VALUE',
	            //hiddenName: "URL",
	            emptyText:'请选择图层...',
                accelerate: true,
                triggerAction:'all',
                editable:false 
            },{
                fieldLabel: '查询关键字',
                id:'keywork',
                width: 200,
                xtype: 'textfield',
                accelerate: true
            }
        ],
        buttons: [{
            text: '查询',handler: chaxun
        }
        ]
    });
   // grid.render('status_grid');
    locationForm.render('form-ct');
});
//查询
function chaxun(){
parent.center.frames["lower"].swfobject.getObjectById("FxGIS").clear();
	//获取选择的地图服务、输入的关键字
      var dtService = locationForm.get('dtService').getValue();
      var arr=dtService.split("@");
      var keywork = locationForm.get('keywork').getValue();
      keywork=keywork.replace(/[ ]/g,""); 
      if(dtService == ""){
      	Ext.MessageBox.alert('提示', '请选择地图服务!');
      }else{
      //图形查询
	  serviceid = arr[0];
      layerid = arr[1];
      var field = arr[2];
      queryfieldsinfo= arr[3].split(",");//查询结果展示的中文列名
      queryfields=field.split(",");
      where = keywork;
      var fields =field;
      
      parent.center.frames["lower"].swfobject.getObjectById("FxGIS").findFeature(serviceid,layerid,where,fields);//图行查询
 }
}
function findExecuteCallback(s){
      rr=s;
      panel();
}
function panel(){

    if(statusTab!=null){
           statusTab.destroy(); 
        }
        var scrHeight= document.body.offsetHeight;
        var height=document.body.clientHeight;
        
        statusTab = new Ext.Panel({
            renderTo:"status_grid",
            width:width,
            height:height-100, 
            layout:"accordion",
            layoutConfig: {animate: true }, 
             items:[{
             collapsible : true,
               html: "<iframe id='cxjg' style='height:"+(scrHeight-95)+"PX; width:350px;' src='queryData.jsp'/>"
            }
            ]
        });
}

</script>
	</head>
	<body bgcolor="#FFFFFF">
		<div id="form-ct" style="width: 100%; height: 18%;"></div>
		<div id="status_grid" style="width: 100%; height: 30%;"></div>
		<!--  <iframe id="lower" name="lower"  style="width: 100%;height:100%; overflow: auto;" src=""></iframe>
		-->
	</body>
</html>
