﻿﻿<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.klspta.base.gis.GisConfigTools"%>
<%@page import="com.klspta.base.gis.Extent"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="com.klspta.console.user.User"%>
<%
	String path = request.getContextPath();
	String basePath = request.getServerName() + ":" + request.getServerPort() + path + "/";
	String gisapiPath = basePath + "base/thirdres/arcgis_js_api/library/2.5/arcgis_compact";
	basePath = request.getScheme() + "://" + basePath; 
	String mapServices = GisConfigTools.getInstance().getMapServices();
	Extent extent=GisConfigTools.getInstance().getExtent();
	String geometryServiceUrl=GisConfigTools.getInstance().getGeometryService();
	String operation=request.getParameter("operation");//访问当前页面的操作
	String[] ops = operation.split(",");
	String value = request.getParameter("yw_guid");
	boolean isop = false;
	String[] where = new String[ops.length];
	String[] url = new String[ops.length];
	String[] layerid = new String[ops.length];
	String[] expand = new String[ops.length];
	String[] title = new String[ops.length];
	String[] height = new String[ops.length];
	String[] width = new String[ops.length];
	Map<String, Object> op = GisConfigTools.getInstance().getOpParameters(ops[0]);
	for(int i = 0; i < ops.length; i++){
		op = GisConfigTools.getInstance().getOpParameters(ops[i]);
		if(op != null){
			isop = true;
			where[i] = (String)op.get("PARAMETERS");
			layerid[i] = (String)op.get("LAYERID");
			expand[i] = (String)op.get("EXPANDLEVEL");
			url[i] = (String)op.get("OPURL");
			title[i] = (String)op.get("title");
			height[i] = (String)op.get("height");
			width[i] = (String)op.get("width");
			if(where[i] != null && (!where[i].equals(""))){
				where[i] = where[i].replaceAll("value", value);
			}
		}
	}
	
	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	String userId = null;
	String fullName = "";
	if (principal instanceof User) {
	   userId = ((User) principal).getUserID();
	   fullName = ((User) principal).getFullName();
	} else {
	   fullName = principal.toString();
	}
%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Simple Map</title>
    <script type="text/javascript">
        var gisapiPath = "<%=gisapiPath%>";
    </script>
    <link rel="stylesheet" type="text/css" href="http://<%=gisapiPath %>/js/dojo/dijit/themes/tundra/tundra.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>gisapp/css/gisView.css"/>
    <script type="text/javascript" src="http://<%=gisapiPath %>/index.jsp"></script>
    <script type="text/javascript" src="framework/frameworkConfig.js"></script>
    <script type="text/javascript" src="framework/frameworkConfig4SG.js"></script>
    <script type="text/javascript" src="framework/frameworkApplication.js"></script>
    <script type="text/javascript" src="framework/frameworkTaskManager.js"></script>
    <script type="text/javascript" src="framework/frameworkInterface.js"></script>
    <script type="text/javascript" src="framework/frameworkInterfaceimpl.js"></script>
    <script type="text/javascript" src="framework/frameworkHttpRequest.js"></script>
    <%@ include file="/base/include/restRequest.jspf" %>
    <%@ include file="/base/include/ext.jspf" %>
    
    
    <script type="text/javascript">
    var basePath="<%=basePath%>";
    var _$queryMapServiceIndex,_$queryLayerIndex,_$layerID,_$queryEvt;
	var _$layers = Array();
	mapServices = <%=mapServices%>;
	var urlid = 0;
	var mywmsurl = Array();
	_$WKID = <%=extent.getWkid()%>;
	startExtent = new esri.geometry.Extent(<%=extent.getMin_X()%>, <%=extent.getMin_Y()%>, <%=extent.getMax_X()%>, <%=extent.getMax_Y()%>, new esri.SpatialReference({wkid: <%=extent.getWkid()%>}));
	geometryServiceUrl='<%=geometryServiceUrl%>';	  
    dojo.require("esri.map");
    dojo.require("esri.toolbars.navigation");
    dojo.require("esri.dijit.Scalebar");
    dojo.require("esri.dijit.OverviewMap");
    dojo.require("esri.tasks.query");
    dojo.require("esri.toolbars.draw");
	dojo.require("esri.tasks.geometry");
	dojo.require("esri.tasks.query");
	dojo.require("esri.layers.wms");
	dojo.require("esri.layers.tiled");
	dojo.require("esri.dijit.InfoWindow");	  
	dojo.require("dijit.layout.ContentPane");
	dojo.require("esri.layers.graphics");
    dojo.addOnLoad(init);
    //set the skin of the menu (0 or 1, with 1 rendering a default Windows menu like skin)
    var menuskin=1;
    //set this variable to 1 if you wish the URLs of the highlighted menu to be displayed in the status bar
    var display_url=0;
    function showmenuie5(){
        //Find out how close the mouse is to the corner of the window
        var rightedge=document.body.clientWidth-event.clientX;
        var bottomedge=document.body.clientHeight-event.clientY;
        //if the horizontal distance isn't enough to accomodate the width of the context menu
        if (rightedge<ie5menu.offsetWidth){
            ie5menu.style.left=document.body.scrollLeft+event.clientX-ie5menu.offsetWidth
        }else{
            //move the horizontal position of the menu to the left by it's width
            //position the horizontal position of the menu where the mouse was clicked
	        ie5menu.style.left=document.body.scrollLeft+event.clientX;
        }
        //same concept with the vertical position
        if (bottomedge<ie5menu.offsetHeight){
            ie5menu.style.top=document.body.scrollTop+event.clientY-ie5menu.offsetHeight;
        }else{
            ie5menu.style.top=document.body.scrollTop+event.clientY;
        }
        ie5menu.style.visibility="visible";
        return false;
    }
 
    function hidemenuie5(){
        ie5menu.style.visibility="hidden";
    }
 
    function highlightie5(){
        if (event.srcElement.className=="menuitems"){
            event.srcElement.style.backgroundColor="#8E8E8E";
            event.srcElement.style.color="white";
            if (display_url==1){
            	window.status=event.srcElement.url;
            }
        }
    }
 
    function lowlightie5(){
        if (event.srcElement.className=="menuitems"){
            event.srcElement.style.backgroundColor="";
            event.srcElement.style.color="black";
            window.status='';
        }
    }
 
    function jumptoie5(){
        if (event.srcElement.className=="menuitems"){
            if (event.srcElement.getAttribute("target")!=null){
            	window.open(event.srcElement.url,event.srcElement.getAttribute("target"));
            }else{
            	window.location=event.srcElement.url;
            }
        }
    }
    
    function init(){
        map = new esri.Map("mapDiv",{extent: startExtent});
        //dojo.connect(map, "onExtentChange", function(extent){
        //    var s = "";
        //    alert("XMin: "+ extent.xmin + " "+"YMin: " + extent.ymin + " "+"XMax: " + extent.xmax + " "+"YMax: " + extent.ymax);
        // });
        var opid = <%=ops[0]%>;
        var height = <%=height[0]%>;
        var width = <%=width[0]%>;
        application = new frameworkTaskManager();
        application.setupMapServices(map);
    	application.setupSymbol();
    	application.setupStatus(map);
    	//application.setupScalebar(map);
    	application.setupToolbar(map);
    	application.setupOverview(map);
        geometryService = new esri.tasks.GeometryService(geometryServiceUrl);
        <%if(isop){%>
            if(opid < 100){
            	queryAndLocation("<%=url[0]%>",<%=layerid[0]%>,"<%=where[0]%>",<%=expand[0]%>,true);
            }else{
            
            	parent.Ext.getCmp('east-panel').expand();
            	parent.Ext.getCmp('east-panel').setTitle('<%=title[0]%>');
	parent.document.getElementById("east").src="<%=basePath%>gisapp/pages/<%=url[0]%>";
}

        <%}%>
    }
 
</script>
</head>
<body  class="tundra">
<div id="ie5menu" class="skin1" onMouseover="highlightie5()" onMouseout="lowlightie5()" onClick="jumptoie5()" style="position: absolute; left: 0px; top: 15px; width: 75px; right: 0;">
    <div class="menuitems" url="javascript:zoomIn();">&nbsp;放大</div>
    <div class="menuitems" url="javascript:zoomOut();">&nbsp;缩小</div>
    <div class="menuitems" url="javascript:pan();">&nbsp;漫游</div>
    <div class="menuitems" url="javascript:zoomToFullExtent();">&nbsp;全图</div>
    <div class="menuitems" url="javascript:zoomToPrevExtent();">前一视图</div>
    <div class="menuitems" url="javascript:zoomToNextExtent();">后一视图</div>
    <div class="menuitems" url="javascript:clear();">清除</div>
    <div class="menuitems" url="javascript:DrawFreeHandPolygon();">面积量算</div>
    <div class="menuitems" url="javascript:drawFreeHandPolyline();">长度量算</div>
    <div class="menuitems" url="javascript:mapAnalyse();">属性查询</div>
    <!--<div class="menuitems" url="javascript:projectQuery();">项目查询</div>-->
    <div class="menuitems" url="javascript:drawLocationPoint();">点标注</div>
    <div class="menuitems" url="javascript:openDoubleWindow();">双窗口</div>
    <!--<div class="menuitems" url="javascript:exportShape();">shp导出</div>-->
    <!--<div class="menuitems" url="javascript:openSwipe();">卷帘</div>-->
    <div class="menuitems" url="javascript:printMapHandler();">打印</div>
</div>
  	<div id="toolbar" style="width:100%; height:30;"></div>
    <div id="mapDiv" dojotype="dijit.layout.ContentPane" style="width:100%; height:100%; border:0px solid #000; position: absolute;z-index:200 "></div>
    <div id="result-win" class="x-hidden"></div>
    <div id="result-tabs"></div>
    <div id="properties" title="图斑属性" style="overflow: scroll;"></div>
    <div id='xz'  title="现状叠加分析" style="overflow: scroll; "></div>
    <div id='gh'   title="规划叠加分析" style="overflow: scroll;"></div>
	<div id="win" class="x-hidden">
	    <div id="form" style="margin-left:10px; margin-top:5px"></div>
	</div>
	<div id="MileageStatisticsWin" class="x-hidden">
			<div id="MileageStatisticsForm" style="margin-left:10px; margin-top:5px"></div>
	</div>
 	<div id="graphwin" class="x-hidden">
		<div id="updateForm" style="margin-left:10px; margin-top:5px"></div>
	</div>
	<div id="wfsWin" class="x-hidden"></div>	
	    <div id="kx"></div>	
	<!-- shape文件导出 -->
	<div id="exportShape" style="margin-left:10px; margin-top:5px"></div>	
</body>
<script type="text/javascript">
if (document.all && window.print){
    document.oncontextmenu = showmenuie5;
    document.body.onclick = hidemenuie5;
}
var basePath="<%=basePath%>";
var username="<%=fullName%>";
function openDoubleWindow(){
    var scrWidth=screen.availWidth;
    var scrHeight=screen.availHeight;
    newWin=window.open('/model/giscomponents/doubleWindow/doubleWindow.jsp');
    newWin.moveTo(0,0);
    newWin.resizeTo(scrWidth,scrHeight);  
}
/* 卷帘*/
function openSwipe() {
	var scrWidth=screen.availWidth;
	var scrHeight=screen.availHeight;
	newWin=window.open('/base/gis/pages/fexUI/SwipeSpotlight.html');
	newWin.moveTo(0,0);
	newWin.resizeTo(scrWidth,scrHeight);
}
</script>
</html>


