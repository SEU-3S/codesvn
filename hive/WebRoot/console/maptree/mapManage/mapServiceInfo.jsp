<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.klspta.console.maptree.MapServiceBean"%>
<%@page import="com.klspta.console.maptree.MapServiceManager"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String id = request.getParameter("serverid");
MapServiceBean mapServiceBean = MapServiceManager.getInstance().getMapServiceById(id);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>地图服务管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%@ include file="/base/include/ext.jspf" %>
	<script type="text/javascript">
	var id = "<%=id%>";
	Ext.onReady(function(){
		Ext.QuickTips.init();
		
		var form = new Ext.form.FormPanel({
			renderTo:'mapserviceInfo',
			autoHeight:true,
			frame:true,
			bodyStyle:'padding:5px 0px 0',
			width: 300,
			url:"<%=basePath%>service/rest/mapAuthorOperation/updateMapService?beforeid=" + id,
			defaults: {
            anchor: '0'
        	},
        	items : [
	        	{
	                xtype: 'textfield',
	                id      : 'id',
	                value:'<%=mapServiceBean.getId()%>',
	                fieldLabel: '序号'
	            },
	            {
	                xtype     : 'textfield',
	           		id		: 'alias',
	                value:'<%=mapServiceBean.getAlias()%>',
	                fieldLabel: '名称'            
	            },
	             {
	                xtype: 'textfield',
	                id      : 'url',
	                value:'<%=mapServiceBean.getUrl()%>',
	                fieldLabel: 'URL'
	            },
	            {
	                xtype: 'textfield',
	                id      : 'type',
	                value:'<%=mapServiceBean.getType()%>',
	                fieldLabel: '类型'
	            },
	            {
	                xtype: 'textfield',
	                id      : 'opacity',
	                value:'<%=mapServiceBean.getOpacity()%>',
	                fieldLabel: '透明度'
	            },
	            {
	                xtype: 'textfield',
	                id      : 'defaulton',
	                value:'<%=mapServiceBean.getDefaulton()%>',
	                fieldLabel: 'DEFAULTON'
	            },
	            {
	                xtype: 'textfield',
	                id      : 'format',
	                value:'<%=mapServiceBean.getFormat()%>',
	                fieldLabel: '格式'
	            },           
	            {
	                xtype: 'textfield',
	                id      : 'flag',
	                value:'<%=mapServiceBean.getFlag()%>',
	                fieldLabel: '启用'
	            }, 
	            {
	                xtype: 'textfield',
	                id      : 'Ranking',
	                value:'<%=mapServiceBean.getRanking()%>',
	                fieldLabel: '顺序标识'
	            }
        	],
	       buttons: [
	            {
	                text   : '保存',
	                handler: function() {
							form.form.submit({ 
								waitMsg: '正在保存,请稍候... ', 		
								success:function(){
									Ext.Msg.alert('提示','保存成功。');
									parent.mapService.location.reload();
								}, 
								failure:function(){ 
									Ext.Msg.alert('提示','保存失败，请稍后重试或联系管理员。');
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
  
  <body bgcolor="#FFFFFF">
	  <div id="mapserviceInfo" />
	</body>
</html>
