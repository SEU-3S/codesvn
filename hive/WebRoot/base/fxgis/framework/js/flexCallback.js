var tempValue;
function showVideo(s) {
	window.showModalDialog("../../videoMonitor/pop.jsp?carname=" + s, window,
			"dialogWidth=352px;dialogHeight=288px;status=no;scroll=no");
}
// flex加载完成后，会调用此方法，重置图层是否展现等，返回null则无需重置。
function getInitMapLayerVisiable() {
	putClientCommond("mapconfig", "getInitMapService");
	var result = restRequest();
	return Ext.encode(result);
	// return
	// "[{\"servicename\":\"jz_yw\",\"visiableids\":[0]},{\"servicename\":\"jz_xz\",\"visiableids\":[]},{\"servicename\":\"jz_jsydgzq\",\"visiableids\":[]},{\"servicename\":\"jz_tdytq\",\"visiableids\":[]},{\"servicename\":\"jz_yx\",\"visiableids\":[0]}]";
}
// 画点 回调方法
function drawPointCallback(s) {
	parent.drawPolygonCallback(s);
	// tempValue=eval('('+s+')');
	// var point = tempValue.x + ','+ tempValue.y;
	// window.open("/domain/model/fxgiscomponents/djfx/pointfx.jsp?point="+point,"","height=300,
	// width=350, top=200,left=300,location=no,scrollbars=yes");
}

// 画面 回调方法
function drawPolygonCallback(s) {
	parent.parent.drawPolygonCallback(s);
	/*
	 * tempValue=eval('(' + s + ')'); var zb = ''+tempValue.rings+''; var zbs =
	 * zb.split(','); var points = ''; for(var i=0;i<zbs.length;i+=2){ points +=
	 * zbs[i]+','+zbs[i+1]+';'; }
	 * window.open("/domain/model/fxgiscomponents/djfx/polygonfx.jsp?points="+points,"","height=800,
	 * width=700, top=200,left=300,location=no,scrollbars=yes");
	 */
}

// 属性查询 回调方法
function identifyCallback(s) {
	if (!operation) {
		return;
	}
	tempValue = eval('(' + s + ')');
	showWindow(tempValue);

}
var win;
function showWindow(mes) {
	var tabs = new Ext.TabPanel({
				id : 'pan',
				autoTabs : true,
				activeTab : 0,
				height : 400,
				enableTabScroll : true,
				deferredRender : false,
				border : false,
				scrollDuration : 0.35,
				scrollIncrement : 100,
				animScroll : true,
				defaults : {
					autoScroll : true
				}
			});
	for (var i = 0; i < mes.length; i++) {
		var attributes = mes[i].attributes;
		var attritable = '<table border="1" cellpadding="0" cellspacing="0" width="430"  style="text-align:center; vertical-align:middle;font-family: 宋体, Arial; font-size: 15px;border-collapse:collapse;border:1px #000 solid;" >';
		for (var attr in attributes) {
			if ("Null" == attributes[attr]) {
				attributes[attr] = "";
			}
			attritable += '<tr><td>' + attr + '</td><td>' + attributes[attr]
					+ '</td></tr>';
		}
		attritable += '</table>';
		tabs.add({
					title : mes[i].layername,
					html : attritable
				}).show();
	}
	if (!win) {
		win = new Ext.Window({
					renderTo : Ext.getBody(),
					layout : 'fit',
					title : '属性叠加分析',
					width : 450,
					height : 400,
					plain : true,
					closeAction : 'hide',
					items : tabs,
					buttons : [{
								text : '关闭',
								handler : function() {
									win.hide();
								}
							}]
				});
	} else {
		win.items.removeAt(0);
		win.items.add("pan", tabs);
		win.doLayout();
	}

	win.show();
}

// 长度量算 回调方法
function measureLengthsCallback(s) {
}
// 面积量算 回调方法
function measureAreasCallback(s) {
}
// 图斑查询 回调方法
function findExecuteCallback(s) {
	parent.frames["east"].findExecuteCallback(s);
}

var overlaywin;
function overlayCallback(s) {
	if (s == "error") {
		Ext.MessageBox.alert("提示", "分析结果为空，没有压盖到任何有效图斑！");
	}
	var result = eval(s);
	var overlay = new Ext.TabPanel({
				id : 'overlay',
				autoTabs : true,
				activeTab : 0,
				height : 400,
				enableTabScroll : true,
				deferredRender : false,
				border : false,
				scrollDuration : 0.35,
				scrollIncrement : 100,
				animScroll : true,
				defaults : {
					autoScroll : true
				}
			});
	var tempValue;
	for (var i = 0; i < result.length; i++) {
		var value = result[i];
		var title;
		if (value.value.features.length == 0) {
			continue;
		}
		var attritable = '<table border="1" cellpadding="0" cellspacing="0" width="580"  style="text-align:center; vertical-align:middle;font-family: 宋体, Arial; font-size: 14px;border-collapse:collapse;border:1px #000 solid;" >';
		attritable += '<tr><td bgcolor="#A2CD5A">图斑总面积</td><td colspan="2">'
				+ (value.F_AREA * 0.0015).toFixed(2) + '(亩)</td></tr>';
		if (value.paramName == "OUT_DLTB") {
			title = "现状";
			value = result[tempValue];
			attritable += '<tr bgcolor="#A2CD5A"><td>地类代码</td><td>地类名称</td><td>地类面积</td></tr>';
			for (var j = 0; j < value.value.features.length; j++) {
				var attributes = value.value.features[j].attributes;
				var dl;
				var dm;
				if (attributes.GNFQLXDM == '010') {
					dl = "水田";
					dm = "011";
				} else if (attributes.GNFQLXDM == '020') {
					dl = "旱地";
					dm = "013";
				} else if (attributes.GNFQLXDM == '030') {
					dl = "城市";
					dm = "070";
				} else if (attributes.GNFQLXDM == '040') {
					dl = "村庄";
					dm = "071";
				} else if (attributes.GNFQLXDM == '050') {
					dl = "独立工矿用地区";
					dm = '050';
				} else if (attributes.GNFQLXDM == '060') {
					dl = "果园";
					dm = "021";
				} else if (attributes.GNFQLXDM == '070') {
					dl = "林地";
					dm = "030";
				} else if (attributes.GNFQLXDM == '080') {
					dl = "其他园地";
					dm = "023";
				} else if (attributes.GNFQLXDM == '090') {
					dl = "林业用地区";
					dm = '090';
				} else if (attributes.GNFQLXDM == '100') {
					dl = "牧业用地区";
					dm = '100';
				} else {
					dl = "裸地";
					dm = "127";
				}
				attritable += '<tr><td>' + dm + '</td><td>' + dl + '</td><td>'
						+ (value.value.features[j].F_AREA * 0.0015).toFixed(2)
						+ '(亩)</td></tr>';
			}
		} else if (value.paramName == "OUT_TDYTQ") {
			title = "规划";
			tempValue = i;
			attritable += '<tr bgcolor="#A2CD5A"><td>分区代码</td><td>土地用途</td><td>分区面积</td></tr>';
			for (var j = 0; j < value.value.features.length; j++) {
				var attributes = value.value.features[j].attributes;
				var dl;
				if (attributes.GNFQLXDM == '010') {
					dl = "基本农田保护区";
				} else if (attributes.GNFQLXDM == '020') {
					dl = "一般农用地";
				} else if (attributes.GNFQLXDM == '030') {
					dl = "城镇建设用地区";
				} else if (attributes.GNFQLXDM == '040') {
					dl = "村镇建设用地区";
				} else if (attributes.GNFQLXDM == '050') {
					dl = "独立工矿用地区";
				} else if (attributes.GNFQLXDM == '060') {
					dl = "风景旅游用地区";
				} else if (attributes.GNFQLXDM == '070') {
					dl = "生态环境安全控制区";
				} else if (attributes.GNFQLXDM == '080') {
					dl = "自然与文化遗产保护区";
				} else if (attributes.GNFQLXDM == '090') {
					dl = "林业用地区";
				} else if (attributes.GNFQLXDM == '100') {
					dl = "牧业用地区";
				} else {
					dl = "其他用地区";
				}
				attritable += '<tr><td>' + attributes.GNFQLXDM + '</td><td>'
						+ dl + '</td><td>'
						+ (value.value.features[j].F_AREA * 0.0015).toFixed(2)
						+ '(亩)</td></tr>';
			}
		} else if (value.paramName == "OUT_SP") {
			title = "审批";
			attritable += '<tr bgcolor="#A2CD5A"><td>审批项目名称</td><td>批准文号</td><td>批准时间</td><td>审批面积</td></tr>';
			for (var j = 0; j < value.value.features.length; j++) {
				var attributes = value.value.features[j].attributes;
				var sj = attributes.ZZYSJ;
				if (sj == null) {
					sj = "";
				} else {
					sj = new Date(sj).format("Y年m月d日");
				}
				attritable += '<tr><td>' + attributes.BJPCWH + '</td><td>'
						+ attributes.ZZYWH + '</td><td>' + sj + '</td><td>'
						+ (value.value.features[j].F_AREA * 0.0015).toFixed(2)
						+ '(亩)</td></tr>';
			}
		} else if (value.paramName == "OUT_TDGY") {
			title = "供地";
			attritable += '<tr bgcolor="#A2CD5A"><td>供地项目名称</td><td>供地文号</td><td>供地面积</td></tr>';
			for (var j = 0; j < value.value.features.length; j++) {
				var attributes = value.value.features[j].attributes;
				attritable += '<tr><td>' + attributes.XMMC + '</td><td>'
						+ attributes.GDPZWH + '</td><td>'
						+ (value.value.features[j].F_AREA * 0.0015).toFixed(2)
						+ '(亩)</td></tr>';
			}
		}
		attritable += '</table>';
		overlay.add({
					title : title,
					html : attritable
				}).show();
	}
	if (!overlaywin) {
		overlaywin = new Ext.Window({
					renderTo : Ext.getBody(),
					layout : 'fit',
					title : '叠加分析',
					width : 620,
					height : 400,
					plain : true,
					closeAction : 'hide',
					items : overlay,
					buttons : [{
								text : '关闭',
								handler : function() {
									overlaywin.hide();
								}
							}]
				});
	} else {
		overlaywin.items.removeAt(0);
		overlaywin.items.add("overlay", overlay);
		overlaywin.doLayout();
	}
	overlaywin.show();
}
