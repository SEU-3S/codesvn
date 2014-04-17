<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.klspta.console.maptree.MapTreeBean"%>
<%@page import="com.klspta.console.maptree.MapTreeManager"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
	String parentTreeId=request.getParameter("parentTreeId");
	System.out.print(parentTreeId);
	String treeId=request.getParameter("treeId");
    MapTreeBean mapTreeBean = MapTreeManager.getInstance().getMapTreeBeanByTreeid(treeId);
%> 
 
 	

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Insert title here</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<%@ include file="/base/include/ext.jspf" %>
		<%@ include file="/base/include/restRequest.jspf"%>
		<script type="text/javascript" src="<%=basePath %>console/maptree/js/mapTreeInfo.js"></script>		
<script>
	/*
	var path = "<%=basePath%>";
	var actionName = "mapAuthorOperation";
	var actionMethod = "getMapServiceIds";
	var parameter = "";
	var myData = ajaxRequest(path, actionName, actionMethod, parameter);
	var mapServiceIds = eval(myData);
	*/
 	putClientCommond("mapAuthorOperation","getMapServiceIds");
    var myData=restRequest();
    var mapServiceIds = eval(myData);

	/*
	actionMethod = "getAliasByServerid";
	parameter = "serverid=<%=mapTreeBean.getServerId()%>";
	myData = ajaxRequest(path, actionName, actionMethod, parameter);
	var alias = myData;
	*/
	putClientCommond("mapAuthorOperation", "getAliasByServerid");
	putRestParameter("serverid","<%=mapTreeBean.getServerId()%>");
	myData = restRequest();
	var alias = myData;

	/*
	actionMethod = "getLayerIds";
	parameter = "map_serverId=<%=mapTreeBean.getServerId()%>";
	myData = ajaxRequest(path, actionName, actionMethod, parameter);
	var json = myData;
	*/
	putClientCommond("mapAuthorOperation", "getLayerIds");
	putRestParameter("map_serverId", "<%=mapTreeBean.getServerId()%>");
	myData = restRequest();
	var json = myData;

var layer =0 ;  
 Ext.onReady(function() {
 Ext.QuickTips.init();
 Ext.lib.Ajax.defaultPostHeader += '; charset=utf-8' 
 
      var layerstore=new Ext.data.JsonStore({
          data:[],
          fields:["id","name"]
      });
 
 	var layerName = change(json,'<%=mapTreeBean.getLayerId()%>');   
	var layerId = layerIds(json);
    mapService = stringToArray(mapServiceIds); //地图服务，采用json格式存储的数组    

    var form = new Ext.form.FormPanel({ 
        renderTo: 'mapTreeInfo',
        autoHeight: true,
        title   : '<%=mapTreeBean.getTreeName()%>信息',
        frame:true,
        bodyStyle:'padding:5px 0px 0',
        width: 500,
        url:"<%=basePath%>service/rest/mapAuthorOperation/saveMapTreeNode?treeId=<%=treeId%>",
        defaults: {
            anchor: '0'
        },
        items   : [
   			{   xtype: 'textfield',
                id      : 'treeName',
                value:'<%=mapTreeBean.getTreeName()%>',
                fieldLabel: '图层名称'
            },
            {   xtype: 'numberfield',
                id      : 'sort',
                value:'<%=mapTreeBean.getSort() %>',
                fieldLabel: '排序号'
            },
            {
            	xtype: 'fieldset',
                title: '详细',
                collapsible: true,
                items: [
                	{   xtype     : 'combo', 
		           		id:'serverId',
		           		editable: false,                
		                fieldLabel: '地图服务', 
		                displayField:   'text', 
		                valueField:     'value', 
		                displayField:   'text',  
	                    forceSelection: true,//强制选择    
	                    triggerAction: 'all',//点击下拉按钮全部显示     
	                    mode:'local',   
	                    store: new Ext.data.ArrayStore({   
	                        fields : ['value', 'text'],  
	                        //根据图层书管理id动态取得子项地图服务ids    
	                      	data:mapService 	                             
	                    }), 
	                    listeners:{
	                    	'select': function(comboBox){
	                    	//动态加载id对应的name      	                    	
							Ext.Ajax.request({
							//url:'<%=basePath%>mapManage.do' 
							url:'<%=basePath%>/service/rest/mapAuthorOperation/getLayerIds'
							//url:"http:127.0.0.1:8080/reduce/service/service/rest/mapAuthorOperation/getLayerIds"
							,method:'post'
							,params:{ 
									method:'getLayerIds',     
									map_serverId:comboBox.getValue()   
								}
									,success:function(response){
												//根据选中的地图ID，动态加载地图服中对应的图层号 
												myData = Ext.util.JSON.decode(response.responseText); 
												layerstore.loadData(myData.layers);    															
									}
									,failure:function(response){ 
												Ext.Msg.alert('提示',response.responseText);   
									}
								});	
	                    	},
	                    	'afterrender': function(comboBox) {         
							   Ext.getCmp('serverId').setValue('<%=mapTreeBean.getServerId() %>');         
							   Ext.getCmp('serverId').setRawValue(alias);             
							}   
	                    }	                    
	                    
                	},
                	{
		               	xtype: 'combo',  
		                id   : 'layerId', 
		              	store: layerstore, 
		                editable:       false,
		                fieldLabel: '服务中图层号',   
		                displayField:   'name',     
		                valueField:     'id',     		                		                 
	                    forceSelection: true,//强制选择    
	                    triggerAction: 'all',//点击下拉按钮全部显示     
	                    mode:'local',  
	                    listeners:{  
	                    	 'afterrender': function(comboBox) {
	                    	   //加载数据  
	                    	   layerstore.loadData(layerId);         
							   Ext.getCmp('layerId').setValue('<%=mapTreeBean.getLayerId() %>');    
							   Ext.getCmp('layerId').setRawValue(layerName);           
							} 
	                     } 
            		},
            		{
		                xtype: 'combo',
		                id      : 'type',
		                editable:       false,
		                value:'<%=mapTreeBean.getType()%>',
		                emptyText:"请选择...",
		                fieldLabel: '图层类型',
		                displayField:   'text',
		                valueField:     'value',
	                    forceSelection: true,//强制选择  
	                    triggerAction: 'all',//点击下拉按钮全部显示  
	                    mode:'local',  
	                    store: new Ext.data.ArrayStore({  
	                        fields : ['text', 'value'],
	                        data:[['请选择...',''],['切片','tiled'],['矢量','dynamic']]  
	                    }),
	                    listeners:{
	                    	'select': function(){
	                    		if(Ext.getCmp('type').getValue()=="dynamic"){
	                    			Ext.getCmp('featureType').enable();
	                    		}else{
	                    		    Ext.getCmp('featureType').disable();
	                    		}
	                    	}
	                    }
		            },
		            {
		                xtype: 'combo',
		                id      : 'featureType',
		                disabled:true,
		                editable:       false,
		                value:'<%=mapTreeBean.getFeatureType() %>',
		                emptyText:"请选择...",
		                fieldLabel: '矢量类型',
		                displayField:   'text',
		                valueField:     'value',
		                valueField:     'value',
	                    forceSelection: true,//强制选择  
	                    triggerAction: 'all',//点击下拉按钮全部显示  
	                    mode:'local',  
	                    store: new Ext.data.ArrayStore({  
	                        fields : ['text', 'value'],
	                        data:[['请选择...',''],['点','point'],['线','line'],['面','polygon']]  
	                    })
		            },
		             {
		                xtype: 'combo',
		                id      : 'kind',
		                editable:       false,
		                value:'<%=mapTreeBean.getKind() %>',
		                emptyText:"请选择...",
		                fieldLabel: '数据分类',
		                displayField:   'text',
		                valueField:     'value',
		                valueField:     'value',
	                    forceSelection: true,//强制选择  
	                    triggerAction: 'all',//点击下拉按钮全部显示  
	                    mode:'local',  
	                    store: new Ext.data.ArrayStore({  
	                        fields : ['text', 'value'],
	                        data:[['请选择...',''],['现状图','0'],['规划图','1']]  
	                    })
		            },
		            new Ext.form.NumberField({   
		            	id:'opacity',
		            	 value:'<%=mapTreeBean.getOpacity() %>',
		                fieldLabel:'透明度(0-1)',   
		                allowDecimals:false,               //不允许输入小数   
		                nanText:'请输入有效整数',           //无效数字提示   
		                allowNegative:false,                //不允许输入负数   
		                maxValue:1 ,                           //允许输入最大数值  
						minValue :0                           //允许输入最小值  
            		}),
            		 {
		                xtype: 'combo',
		                id      : 'checked',
		                editable:       false,
		                value:'<%=mapTreeBean.getKind() %>',
		                emptyText:"请选择...",
		                fieldLabel: '是否被选中',
		                displayField:   'text',
		                valueField:     'value',
		                valueField:     'value',
	                    forceSelection: true,//强制选择  
	                    triggerAction: 'all',//点击下拉按钮全部显示  
	                    mode:'local',  
	                    store: new Ext.data.ArrayStore({  
	                        fields : ['text', 'value'],
	                        data:[['请选择...',''],['否','0'],['是','1']]  
	                    })
		            }
                ]
                
            }
            
            
            
        ],
        buttons: [
            {
                text   : '保存',
                handler: function() {
					form.form.submit({
						waitMsg: '正在保存,请稍候... ', 
						params: form.getForm().getFieldValues(),
						success:function(){ 
						Ext.Msg.alert('提示','保存成功。');
							parent.mapTree.location.reload()
						}, 
						failure:function(){ 
							Ext.Msg.alert('提示','保存失败，请确定图层名称后重试或联系管理员。');
						} 
					});
                }
            },
            
            {
                text   : '刷新',
                handler: function() {
                   document.location.reload()
                }
            }
        ]
    });
     
});

	</script>
	</head>
	<body bgcolor="#FFFFFF"">
	  <div id="mapTreeInfo" />
	</body>
</html>