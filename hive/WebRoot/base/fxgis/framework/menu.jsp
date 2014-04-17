<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.klspta.web.xuzhouNW.xfjb.manager.XfAction"%>
<%@page import="com.klspta.web.xuzhouNW.dtxc.PADDataManager"%>
<%@page import="java.util.Map.Entry"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//basePath = "http://127.0.0.1:8080/domain/";
//用来标识是不是信访中的地图标注
String dtbzflag=request.getParameter("dtbzflag");
//用来标识是不是外业巡查成果展现
String flag=request.getParameter("flag");
String yw_guid=request.getParameter("yw_guid");
String year = request.getParameter("year");
String filter = request.getParameter("filter");
String pra = "";
if(yw_guid==null||"null".equals(yw_guid)){
	pra="dolocation=true&p={\"rings\":[[[38688372,4431495],[38688350,4431440],[38688372,4431390],[38688402,4431431],[38688430,4431431],[38688414,4431494],[38688372,4431495]]],\"spatialReference\":{\"wkid\":2362}}&i=false";
}else{
	if(dtbzflag != null && "true".equals(dtbzflag)){
		XfAction xfAction = new XfAction();
		if("null".equals(xfAction.getBiaozhu(yw_guid))){
			
		}else{
			pra = "dolocation=true&p="+xfAction.getBiaozhu(yw_guid)+"&i=false";
		}
	}else{
		PADDataManager pDataList=new PADDataManager();
		pra="dolocation=true&p="+pDataList.getCjzb(yw_guid)+"&i=false";
	}
}

String url=basePath+"base/fxgis/fx/FxGIS.html?debug=true&i=false";
if(filter!=null){
	url += "&filter="+filter;
}
if(year != null && !"null".equals(year)){
	pra = "initFunction=[{\"name\":\"findFeature\",\"parameters\":\"jz_yw,0,"+yw_guid+",OBJECTID\"}]&i=false";
}

if(flag!=null&&!flag.equals("null")||dtbzflag!=null&&!dtbzflag.equals("null")){
  url=basePath+"base/fxgis/fx/FxGIS.html?"+pra;
}

String preParameters=request.getQueryString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <meta http-equiv="X-UA-Compatible" content="IE=7" >
    <base href="<%=basePath%>">
    <title>中上</title>
	<%@ include file="/base/include/ext.jspf" %>
	<%@ include file="/base/include/restRequest.jspf" %>
  </head>
      <script type="text/javascript" src="<%=basePath%>base/fxgis/framework/js/menu.js"></script>
      <script type="text/javascript" src="<%=basePath%>base/fxgis/framework/js/flexCallback.js"></script>
      <script src="<%=basePath%>/base/fxgis/framework/js/toJson.js"></script>
<script type="text/javascript">
var yw_guid='<%=yw_guid%>';
var basePath='<%=basePath%>';
var operation = true;
 var view;
Ext.onReady(function(){
 view=new Ext.Viewport({
		layout : "border",
		items : [{
					region : "north",
					contentEl : 'toolbar',
					height:40,
					margins : '0 0 -12 0',
					tbar : [{
								xtype : 'tbbutton',
								text : ' 放大',
								cls : 'x-btn-text-icon',
								icon : '<%=basePath%>base/fxgis/framework/images/zoom-in.png',
								tooltip : '放大',
								handler : zoomIn
							}, {
								xtype : 'tbbutton',
								text : '缩小',
								cls : 'x-btn-text-icon',
								icon : '<%=basePath%>base/fxgis/framework/images/zoom-out.png',
								tooltip : '缩小',
								handler :zoomOut
							},{
								xtype : 'tbbutton',
								text : '  漫游',
								cls : 'x-btn-text-icon',
								icon : '<%=basePath%>base/fxgis/framework/images/hand.png',
								tooltip : '漫游',
								handler : pan
							}, {
								xtype : 'tbbutton',
								text : '  全图',
								cls : 'x-btn-text-icon',
								icon : '<%=basePath%>base/fxgis/framework/images/Full_Extent.png',
								tooltip : '全图',
								handler : zoomToFullExtent
							}, {
								xtype : 'tbbutton',
								text : '前图',
								cls : 'x-btn-text-icon',
								icon : '<%=basePath%>base/fxgis/framework/images/Zoom_Back.png',
								tooltip : '前图',
								handler : zoomToPrevExtent
							}, {
								xtype : 'tbbutton',
								text : '后图',
								cls : 'x-btn-text-icon',
								icon : '<%=basePath%>base/fxgis/framework/images/Zoom_Forward.png',
								tooltip : '后图',
								handler : zoomToNextExtent
							}, {
								xtype : 'splitbutton',
								text : '量算',
								handler : function(){
									this.showMenu();
									frames["lower"].swfobject.getObjectById("FxGIS").panmap();
								},
								icon : '<%=basePath%>base/fxgis/framework/images/rule.png',
								menu : [{
											text : '直线量算',
											cls : 'x-btn-text-icon',
											icon : '<%=basePath%>base/fxgis/framework/images/line.png',
											tooltip : '直线量算',
											handler : measureLengths
										},{
											text : '面积量算',
											cls : 'x-btn-text-icon',
											icon : '<%=basePath%>base/fxgis/framework/images/showVertices.png',
											tooltip : '面积量算',
											handler : measureAreas
										}]
							},{
								xtype : 'tbbutton',
								text : '清除',
								cls : 'x-btn-text-icon',
								icon : '<%=basePath%>base/fxgis/framework/images/Clear.png',
								tooltip : '清除',
								handler : clear
							},{		
							    text : '属性查询',
								cls : 'x-btn-text-icon',
								icon : '<%=basePath%>base/fxgis/framework/images/isearch.png',
								tooltip : '属性查询',
								handler : identify
								},{		
							    text : '地块标记',
								cls : 'x-btn-text-icon',
								icon : '<%=basePath%>base/fxgis/framework/images/isearch.png',
								tooltip : '地块标记',
								handler : annotation
								},{
							     text : '图斑查询',
								cls : 'x-btn-text-icon',
								icon : '<%=basePath%>base/fxgis/framework/images/ygfx.png',
								tooltip : '图斑查询',
								handler:tbQuery							
							},{
								xtype : 'splitbutton',
								text : '工具箱',							
								handler : function(){
									this.showMenu();
									frames["lower"].swfobject.getObjectById("FxGIS").panmap();
								},
								icon : '<%=basePath%>base/fxgis/framework/images/box.png',
								menu : [{
											text : 'shape导入',
											cls : 'x-btn-text-icon',
											icon : '<%=basePath%>base/fxgis/framework/images/importshp.png',
											tooltip : 'shape导入',
											handler : shpimport
										},{
											text : '坐标定位',
											cls : 'x-btn-text-icon',
											icon : '<%=basePath%>base/fxgis/framework/images/location.png',
											tooltip : '坐标定位',
											handler : doLocation
										},{
											text : '地图图例',
											cls : 'x-btn-text-icon',
											icon : '<%=basePath%>base/fxgis/framework/images/legend.png',
											tooltip : '地图图例',
											handler : legend
										},{
											text : '图层透视',
											cls : 'x-btn-text-icon',
											icon : '<%=basePath%>base/fxgis/framework/images/torch.png',
											tooltip : '图层透视',
											handler : doShutter
										},{
											text : '多窗口对比',
											cls : 'x-btn-text-icon',
											icon : '<%=basePath%>base/fxgis/framework/images/multi-window.png',
											tooltip : '多窗口对比',
											handler : morewindows
										},{
											text : '打印',
											cls : 'x-btn-text-icon',
											icon : '<%=basePath%>base/fxgis/framework/images/printer.png',
											tooltip : '打印',
											handler : mapPrint
										},{
									    	text : '点标记',
											id : 'drawPoint',
											cls : 'x-btn-text-icon',
											icon : '<%=basePath%>base/fxgis/framework/images/point1.png',
											tooltip : '点标记',
											handler : drawPoint
										},{
									    	text : '面标记',
											id : 'drawPolygon',
											cls : 'x-btn-text-icon',
											icon : '<%=basePath%>base/fxgis/framework/images/showVertices.png',
											tooltip : '面标记',
											handler : drawPolygon
										},{
									    	text : '导出图片',
											id : 'exportMap',
											cls : 'x-btn-text-icon',
											icon : '<%=basePath%>base/fxgis/framework/images/showVertices.png',
											tooltip : '导出图片',
											handler : exportMap
										}]
							           },
							           /*
							           {
									    	text : '切换',
											id:'image',
											cls : 'x-btn-text-icon',
											icon : '<%=basePath%>base/fxgis/framework/images/layers.png',
											tooltip : '切换',
											handler : changeBaseMap
										},
										*/{
									    	text : '全屏',
											id:'full_screen',
											cls : 'x-btn-text-icon',
											icon : '<%=basePath%>base/fxgis/framework/images/computer_16x16.png',
											tooltip : '全屏',
											handler : fullScreen
										},{
									    	text : '退出全屏',
											id : 'quit_full_screen',
											hidden : true,
											cls : 'x-btn-text-icon',
											icon : '<%=basePath%>base/fxgis/framework/images/nofullscreen.png',
											tooltip : '退出全屏',
											handler : quitFullScreen
										}]
				}, {
					region : "center",
					margins : '0 0 0 0',
					contentEl : 'mapDiv'
				}]
	});
	if(!parent.parent.content){
		Ext.getCmp('full_screen').setVisible(false);
		//Ext.getCmp('yganalyse').setVisible(true);
	}	
});

</script>
  <body>
  <div id="toolbar" style="width:100%;height:40"></div>
  <div id="mapDiv">
  <iframe id="lower" name="lower"  style="width: 100%;height:100%; overflow: auto;" src=<%=url%>&<%=preParameters %>></iframe>
</div>
    <div id="result-win" class="x-hidden">    </div>
    <div id="result-tabs"></div>
    <div id='properties'   title="图斑属性" style="overflow: scroll;"></div>
    <div id='xz'  title="现状叠加分析" style="overflow: scroll; "></div>
    <div id='gh'   title="规划叠加分析" style="overflow: scroll;"></div>
  </body>
</html>
