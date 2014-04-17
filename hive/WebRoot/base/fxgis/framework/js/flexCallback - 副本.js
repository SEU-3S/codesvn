var tempValue;
function showVideo(s){
    window.showModalDialog("../../videoMonitor/pop.jsp?carname="+s,window,"dialogWidth=352px;dialogHeight=288px;status=no;scroll=no");
}
//flex加载完成后，会调用此方法，重置图层是否展现等，返回null则无需重置。
function getInitMapLayerVisiable(){ 
    putClientCommond("mapconfig", "getInitMapService");
    var result = restRequest();
    var jsonData = new Dictionary();
    var flag;
    for(var i=0;i<result.length;i++){
    	if(result[i].FLAG=='true'){
    		flag = true;
    	}else{
    		flag = false;
    	}
    	jsonData.put(result[i].SERVERID,result[i].LAYERID,result[i].TYPE,flag);
    }
    return jsonData.toStr();
    //return "[{\"servicename\":\"jz_yw\",\"visiableids\":[0]},{\"servicename\":\"jz_xz\",\"visiableids\":[]},{\"servicename\":\"jz_jsydgzq\",\"visiableids\":[]},{\"servicename\":\"jz_tdytq\",\"visiableids\":[]},{\"servicename\":\"jz_yx\",\"visiableids\":[0]}]";
}
//画点 回调方法
function drawPointCallback(s){
	parent.drawPolygonCallback(s);
	//tempValue=eval('('+s+')');
	//var point = tempValue.x + ','+ tempValue.y;
	//window.open("/domain/model/fxgiscomponents/djfx/pointfx.jsp?point="+point,"","height=300, width=350, top=200,left=300,location=no,scrollbars=yes");
}

//画面 回调方法
function drawPolygonCallback(s){
	parent.parent.drawPolygonCallback(s);
	/*
	tempValue=eval('(' + s + ')');					
	var zb = ''+tempValue.rings+'';
	var zbs = zb.split(',');
	var points = ''; 
	for(var i=0;i<zbs.length;i+=2){
		points += zbs[i]+','+zbs[i+1]+';';
	}
	window.open("/domain/model/fxgiscomponents/djfx/polygonfx.jsp?points="+points,"","height=800, width=700, top=200,left=300,location=no,scrollbars=yes");	
	*/
}


//属性查询 回调方法
function identifyCallback(s){
	tempValue = eval('(' + s + ')');
	var attributes = tempValue.attributes;
	var attritable = '<table border="1" cellpadding="0" cellspacing="0" width="330"  style="text-align:center; vertical-align:middle;font-family: 宋体, Arial; font-size: 12px;border-collapse:collapse;border:1px #000 solid;" >';
	for(var attr in attributes){
		if("Null" == attributes[attr]){
			attributes[attr] = "";
		}
		attritable+='<tr><td>'+attr+'</td><td>'+attributes[attr]+'</td></tr>';
	}
	attritable+='</table>';
	document.getElementById('properties').innerHTML = attritable;
	var rings = ''+tempValue.geometry.rings+'';
	var zbs = rings.split(',');
	var points = ''; 
	for(var i=0;i<zbs.length;i+=2){
		points += zbs[i]+','+zbs[i+1]+';';
	}
	putClientCommond("proanalyse", "fenxi");
	putRestParameter("points", points);
	putRestParameter("flag", "0");
	var result = restRequest();
	if(result.indexOf('@')!=-1){
			document.getElementById('xz').innerHTML = (result.split('@'))[0];
			document.getElementById('gh').innerHTML = (result.split('@'))[1];
			showWindow('right');
	}else{
		showWindow('fault');
	}
		
} 
var win;
function showWindow(mes) {
	var tabs = new Ext.TabPanel({
		        id:'pan',
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
				},
				items : [{
							contentEl : 'properties',
							title : '图斑属性',
							id : 'properties_tab',
							closable : false,
							autoScroll : true,
							autoDestroy : true
						}, {
							contentEl : 'xz',
							title : '现状分析',
							id : 'xz_tab',
							closable : false,
							autoScroll : true,
							autoDestroy : true
						}, {
							contentEl : 'gh',
							title : '规划分析',
							id : 'gh_tab',
							closable : false,
							autoScroll : true,
							autoDestroy : true
						}]
			});
	if(mes=='fault'){
		tabs.remove('xz_tab');
		tabs.remove('gh_tab');
	}	
	if (!win) {
		win = new Ext.Window({
					layout : 'fit',
					width : 350,
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
	}else{	
	 win.items.removeAt(0);
	 win.items.add("pan",tabs);
	 win.doLayout();
	} 

	win.show();
}

//长度量算 回调方法
function measureLengthsCallback(s){
}
//面积量算 回调方法
function measureAreasCallback(s){
}
//图斑查询 回调方法
function findExecuteCallback(s){
	parent.frames["east"].findExecuteCallback(s);
}
