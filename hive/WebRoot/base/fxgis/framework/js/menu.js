// 放大
function zoomIn() {
	frames["lower"].swfobject.getObjectById("FxGIS").zoomIn();
}

// 缩小
function zoomOut() {
	frames["lower"].swfobject.getObjectById("FxGIS").zoomOut();
}
// 框选放大
function zoomInNav() {
	frames["lower"].swfobject.getObjectById("FxGIS").zoomInNav();
}
// 框选缩小
function zoomOutNav() {
	frames["lower"].swfobject.getObjectById("FxGIS").zoomOutNav();
}
// 漫游
function pan() {
	frames["lower"].swfobject.getObjectById("FxGIS").panmap();
}
// 全图
function zoomToFullExtent() {
	frames["lower"].swfobject.getObjectById("FxGIS").zoomToFullExtent();
}
// 前一视图
function zoomToPrevExtent() {
	frames["lower"].swfobject.getObjectById("FxGIS").zoomToPrevExtent();
}
// 后一视图
function zoomToNextExtent() {
	frames["lower"].swfobject.getObjectById("FxGIS").zoomToNextExtent();
}
// 清除
function clear() {
	frames["lower"].swfobject.getObjectById("FxGIS").clear();
	operation = true;
}

function findFeature(name, layer, key, keyname) {
	frames["lower"].swfobject.getObjectById("FxGIS").findFeature(name, layer,
			key, keyname);
}

// 属性查询
function identify() {
	frames["lower"].swfobject.getObjectById("FxGIS").panmap();
	frames["lower"].swfobject.getObjectById("FxGIS").identify();
	operation = true;
	// parent.document.getElementById("east").src="/domain/model/fxgiscomponents/infoQuery/sxcxList.jsp";
}

// 压盖分析
function overlay() {
	frames["lower"].swfobject.getObjectById("FxGIS").panmap();
	frames["lower"].swfobject.getObjectById("FxGIS").overLay();
}
// 选中高亮显示
function annotation() {
	frames["lower"].swfobject.getObjectById("FxGIS").panmap();
	frames["lower"].swfobject.getObjectById("FxGIS").identify();
	operation = false;
	// parent.document.getElementById("east").src="/domain/model/fxgiscomponents/infoQuery/sxcxList.jsp";
}

// 压盖分析
function glandAnalyse() {
	parent.Ext.getCmp('east-panel').expand();
	parent.Ext.getCmp('east-panel').setTitle('压盖分析');
	parent.document.getElementById("east").src = "/domain/model/fxgiscomponents/djfx/ygfx.jsp?yw_guid="
			+ yw_guid;
}

// 直线长度量算
function measureLengths() {
	frames["lower"].swfobject.getObjectById("FxGIS").measureLengths();
}

// 多窗口对比
function morewindows() {
	frames["lower"].swfobject.getObjectById("FxGIS").morewindows_clickHandler();
}

// 地图打印
function mapPrint() {
	frames["lower"].swfobject.getObjectById("FxGIS").mapPrint();
	// var w=document.body.clientWidth;
	// var h=document.body.clientHeight;
	// window.open(basePath+"model/fxgiscomponents/mapPrint/print.jsp","print",'width='+w+',height='+(h+120)+',top=0,left=0,toolbar=no,menubar=no,scrollbars=no,
	// resizable=no,location=no, status=no');
}

// 图斑查询
function findFeature(serviceid, layerid, where, fields) {
	frames["lower"].swfobject.getObjectById("FxGIS").findFeature(serviceid,
			layerid, where, fields);
}

// 面积量算
function measureAreas() {
	frames["lower"].swfobject.getObjectById("FxGIS").clear();
	frames["lower"].swfobject.getObjectById("FxGIS").measureAreas();
}

// flex定位
function setCenterAt(xpoint, ypoint) {
	frames["lower"].swfobject.getObjectById("FxGIS")
			.setCenterAt(xpoint, ypoint);
}

// 坐标导入
function doLocation(rings) {
	frames["lower"].swfobject.getObjectById("FxGIS").doLocation(rings);
}

/* 定位 */
function doLocation() {
	parent.Ext.getCmp('east-panel').expand();
	parent.Ext.getCmp('east-panel').setTitle('定位');
	parent.document.getElementById("east").src = "/domain/model/fxgiscomponents/location/location.jsp";
}

/* 图斑导入 */
function importTB() {
	parent.Ext.getCmp('east-panel').expand();
	parent.Ext.getCmp('east-panel').setTitle('图斑导入');
	parent.document.getElementById("east").src = "/domain/model/fxgiscomponents/importShapefile/importShapefile.jsp";
}

/* 坐标导入 */
function importZB() {
	parent.Ext.getCmp('east-panel').expand();
	parent.Ext.getCmp('east-panel').setTitle('坐标导入');
	parent.document.getElementById("east").src = "/domain/model/fxgiscomponents/importzb/importZbFile.jsp";
}

/* 地图图例 */
function legend() {
	parent.Ext.getCmp('east-panel').expand();
	parent.Ext.getCmp('east-panel').setTitle('地图图例');
	parent.document.getElementById("east").src = "/domain/model/fxgiscomponents/legend/legendTab.jsp";
}

/* shp导入 */
function shpimport() {
	parent.Ext.getCmp('east-panel').expand();
	parent.Ext.getCmp('east-panel').setTitle('图斑导入');
	parent.document.getElementById("east").src = "/domain/model/fxgiscomponents/importShapefile/importShapefile.jsp";
}

/* 卷帘 */
function doShutter() {
	parent.Ext.getCmp('east-panel').expand();
	parent.Ext.getCmp('east-panel').setTitle('图层透视工具');
	parent.document.getElementById("east").src = "/domain/model/fxgiscomponents/swipe/swipe.jsp";
	// parent.document.getElementById("east").src="/domain/model/fxgiscomponents/location/location.jsp";
}

/* 地图图例 */
function legend() {
	parent.Ext.getCmp('east-panel').expand();
	parent.Ext.getCmp('east-panel').setTitle('地图图例');
	parent.document.getElementById("east").src = "/domain/model/fxgiscomponents/legend/legendTab.jsp";
}

/* 叠加分析 */
function fun() {
	parent.Ext.getCmp('east-panel').expand();
	parent.Ext.getCmp('east-panel').setTitle('叠加分析');
	parent.document.getElementById("east").src = "/domain/model/fxgiscomponents/djfx/djfx.jsp";
}
/* 轨迹回放 */
function playback() {
	parent.Ext.getCmp('east-panel').expand();
	parent.Ext.getCmp('east-panel').setTitle('轨迹回放');
	parent.document.getElementById("east").src = "/domain/model/fxgiscomponents/playback/playBack.jsp";
}
/* 图斑查询 */
function tbQuery() {
	frames["lower"].swfobject.getObjectById("FxGIS").panmap();
	parent.Ext.getCmp('east-panel').expand();
	parent.Ext.getCmp('east-panel').setTitle('图斑查询');
	parent.document.getElementById("east").src = "/domain/model/fxgiscomponents/infoQuery/infoQuery.jsp";
}
/* 实时跟踪 */
function doMonitor() {
	parent.Ext.getCmp('west-panel').collapse();
	parent.Ext.getCmp('east-panel').expand();
	parent.Ext.getCmp('east-panel').setTitle('实时跟踪');
	parent.document.getElementById("east").src = "/domain/model/fxgiscomponents/carMonitor/index.jsp";
}

/* 地图全屏 */
function fullScreen() {
	Ext.getCmp('full_screen').setVisible(false);
	Ext.getCmp('quit_full_screen').setVisible(true);
	parent.parent.parent.index.rows = "0,0,*"
	parent.parent.content.cols = "0,0,*";
}

/* 退出全屏 */
function quitFullScreen() {
	Ext.getCmp('full_screen').setVisible(true);
	Ext.getCmp('quit_full_screen').setVisible(false);
	parent.parent.parent.index.rows = "106,32,*"
	parent.parent.content.cols = "0,9,*";
}

/* 标注分析 */
function drawPointAnalysis() {
	frames["lower"].swfobject.getObjectById("FxGIS").drawPoint("");
	openPointAnalysis(1, 1);
}

/* 标注分析的回调 */
function openPointAnalysis(x, y) {
	parent.Ext.getCmp('east-panel').expand();
	parent.Ext.getCmp('east-panel').setTitle('标注查看');
	parent.document.getElementById("east").src = "/domain/model/fxgiscomponents/analysisPoint/analysisPoint.jsp?x='"
			+ x + "'&y='" + y + "'";
}

/* 点标记 */
function drawPoint() {
	frames["lower"].swfobject.getObjectById("FxGIS").drawPoint("");
}

/* 面标记 */
function drawPolygon() {
	frames["lower"].swfobject.getObjectById("FxGIS").drawPolygon();
}

/* 导出图片 */
function exportMap() {
	frames["lower"].swfobject.getObjectById("FxGIS").exportMap();
}

// 按ESC退出全屏
function enterAndEsc() {
	var esc = window.event.keyCode;
	if (esc == 27) // 判断是不是按的Esc键,27表示Esc键的keyCode.按下退出全屏.
	{
		parent.parent.parent.index.rows = "106,32,*"
		parent.parent.content.cols = "0,9,*";
	}
}
document.onkeydown = enterAndEsc;
// 切换电子地图和遥感地图
var ISImage = false;
function changeBaseMap() {
	if (!ISImage) {

		ISImage = true;
		// 切换到电子地图
		frames["lower"].swfobject.getObjectById("FxGIS").setLayerVisiableById(
				'VECTOR', false);
		frames["lower"].swfobject.getObjectById("FxGIS").setLayerVisiableById(
				'V_ANNO', false);
		frames["lower"].swfobject.getObjectById("FxGIS").setLayerVisiableById(
				'IMAGE', true);
		frames["lower"].swfobject.getObjectById("FxGIS").setLayerVisiableById(
				'I_ANNO', true);
	} else {

		ISImage = false;
		// 切换到遥感影像地图
		frames["lower"].swfobject.getObjectById("FxGIS").setLayerVisiableById(
				'VECTOR', true);
		frames["lower"].swfobject.getObjectById("FxGIS").setLayerVisiableById(
				'V_ANNO', true);
		frames["lower"].swfobject.getObjectById("FxGIS").setLayerVisiableById(
				'IMAGE', false);
		frames["lower"].swfobject.getObjectById("FxGIS").setLayerVisiableById(
				'I_ANNO', false);

	}
}