<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
	String yw_guid=request.getParameter("yw_guid");
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
		var yw_guid='<%=yw_guid%>';
		var myData ;
		var width;
		var height;
        var locationForm;
        var serviceid ;
        var layerid;
        var store;
		var grid;
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
        defaults: {width: 180},
      //  defaultType: 'textfield',
        items: [{
                fieldLabel: '压盖图层',
                xtype: 'combo',
                id: 'dtService',
                store: dtServiceData,
                mode:"local", 
                displayField:'TEXT',
	            valueField: 'VALUE',
	            //hiddenName: "URL",
	            forceSelection: false, 
	            selectOnFocus:false,
	            emptyText:'请选择压盖图层...',
                //accelerate: true,
                triggerAction:'all',
                editable:false 
            }
        ],
        buttons: [{
            text: '分析',handler: analyse
        }
        ]
    });
    locationForm.render('form-ct');
});
//分析
function analyse(){
	  
      var dtService = locationForm.get('dtService').getValue();
      var arr=dtService.split("@");
      if(dtService == ""){
      	Ext.MessageBox.alert('提示', '请选择地图服务!');
      }else{
		
	  	serviceid = arr[0];
	 	//alert(serviceid)
      	layerid = arr[1];
	  	//alert(layerid)
		
	  	putClientCommond("gisoanalysis","getOverlayAnalysis");
        putRestParameter("yw_guid",yw_guid);
        putRestParameter("layername",serviceid);
       // putRestParameter("layername",serviceid);
	    myData = restRequest();
        if(myData){ 
        	showResult(myData); 
        }else{
            alert('无分析结果！');
        }
     }
}

function showResult(myData){

	    store = new Ext.data.JsonStore({
				    proxy: new Ext.ux.data.PagingMemoryProxy(myData),
					remoteSort:true,
			        fields: [
			           {name: 'SXM'},
			           {name: 'SXZ'},
			        ]
			    });
   	    store.load({params:{start:0, limit:10}});
 		width = document.body.clientWidth - 5;
			    var height = document.body.clientHeight * 0.955; 
			    grid = new Ext.grid.GridPanel({
			        store: store,
			        columns: [
			            new Ext.grid.RowNumberer(),
			        	{header: '属性名',dataIndex:'SXM',width: width*0.3, sortable: true},
			            {header: '属性值(㎡)',dataIndex:'SXZ', width: width*0.65, sortable: true}
			        ],
			        stripeRows: true,
			        height: height,
			        stateful: true,
			        buttonAlign:'center',
			        bbar: new Ext.PagingToolbar({
				        pageSize: 10,
				        store: store,
				        displayInfo: true,
				        displayMsg: '共{2}条，当前为：{0} - {1}条',
				        emptyMsg: "无记录",
				        plugins: new Ext.ux.ProgressBarPager()
			        })
			    });
    grid.render('status_grid');
}

</script>
	</head>
	<body bgcolor="#FFFFFF">
		<div id="form-ct" style="width: 100%; height: 18%;"></div>
		<div id="status_grid" style="width: 100%; height: 30%;"></div>
	</body>
</html>
