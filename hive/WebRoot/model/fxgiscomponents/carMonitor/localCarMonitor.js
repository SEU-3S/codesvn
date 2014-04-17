//打开端口并发送命令程序 
var inpotBuffer;   
var isAutoCenter=true;
var hasInfoWindow = true; 
var start_x='';
var start_y='';
var end_x='';
var end_y='';
var mdd_x=''
var mdd_y='';
var pointGraphic;//车辆当前位置
var polylineGraphic;//目的地导航直线
var xy;//坐标转换
var store;
var myData;
var len=0.02;//缓冲区分析大小
var locationRate=5;//GPS定位间隔(秒)
var locationLogTime=5;//轨迹保存次数  保存时间为：locationRate*locationLogTime
var locationLog=0;
var isdh=true;//是否导航
var bufferAnalyseRate=3000;//缓冲区分析间隔(秒)

/*
Ext.onReady(function(){
    store = new Ext.data.ArrayStore({
        fields: [
           {name: 'objectid'},
           {name: '卫片编号'},
           {name: '图幅号'},
           {name: '面积'}
        ]
    });  
       var grid = new Ext.grid.GridPanel({
        store: store, 
        height:400,
        width:width,
        columns: [
         	{header: 'objectid', width: 0},
            {header: '卫片编号', width: width/4-1},
            {header: '图幅号', width: width/3-1},
            {header: '面积', width: width/3-1}
        ]    
    });  
    	    grid.on('cellclick',function(e){
        var row = grid.getSelectionModel().getSelected();
		objectid = row.data.objectid;
		//alert(row.data.objectid);
		parent.parent.center.queryAndLocation("DC_YW",5,"objectid="+objectid,50,true);
		document.getElementById("autoCenter").value = "继续跟踪";
		isAutoCenter=false;
		});
    grid.render('status_grid');
 })
*/
function OpenPort()    
{
	document.getElementById("end").disabled=false;
	document.getElementById("start").disabled=true;  
	if(!MSComm1.PortOpen)    
	{    
		MSComm1.PortOpen=true;    
		MSComm1.Output="R";//发送命令    
		//window.alert("成功发出命令！"); 
	}    
	else    
	{    
	    window.alert ("已经开始接收数据!");    
	}    
}   
function closePort(){
	mdd_x='';
	mdd_y='';
	if(MSComm1.PortOpen){
		document.getElementById("start").disabled=false;
		document.getElementById("end").disabled=true; 
		MSComm1.PortOpen=false; 
	}else{
		alert('端口已关闭');
	}
} 

function change(){
	if(document.getElementById("autoCenter").value == "继续跟踪"){
		document.getElementById("autoCenter").value = "暂停跟踪";
		isAutoCenter=false;
	}else{	 
		document.getElementById("autoCenter").value = "继续跟踪";		
		isAutoCenter=true;	
	}
}

//重写 mscomm 控件的唯一事件处理代码    
function MSComm1_OnComm(){      
if(MSComm1.CommEvent==2)//如果是接收事件    
{    
        //document.form1.txtReceive.value=document.form1.txtReceive.value + filter(MSComm1.Input); 
        var gpgga=filter(MSComm1.Input).split(','); 
        if(gpgga[0]=='$GPGGA'){
        if((gpgga[1]%locationRate)==0){
        var x=gpgga[2];
        var y=gpgga[4];
        if(x!='NaN' && y!='NaN'&&x>=400 && x<5300 && y<=13500 && y>=5300){
        	var status=gpgga[6];
        	if(status==0) status='未定位';
        	if(status==1) status='一般定位';
        	if(status==2) status='精确定位';
        	if(status==3) status='无效定位';
        	if(status==6) status='正在估算';
         //document.getElementById("dwjd").value=status;
         document.getElementById("wxgs").value=gpgga[7];
         //document.getElementById("jd").value=gpgga[8];
	        //开始纠偏
				parent.parent.center.putClientCommond("location","changeMe");
		        parent.parent.center.putRestParameter("_$sid","aaa");
		        parent.parent.center.putRestParameter("x",y);
		        parent.parent.center.putRestParameter("y",x);
		        parent.parent.center.putRestParameter("from","BL_84");
		        parent.parent.center.putRestParameter("to","BL_80");
			    xy = parent.parent.center.restRequest();		    	
				//document.form1.txtReceive.value='搜到卫星数:'+gpgga[7]+'\nGPS状态:'+status;
		
		//alert(y+","+x+"   "+xy[0][0]+","+xy[0][1]);
		
		if(start_x=='' || start_y==''){
		start_x=xy[0][0];
		start_y=xy[0][1];
		}else{
		end_x=xy[0][0];
		end_y=xy[0][1];
		locationLog++;
		//判断是否清除历史轨迹
		if(locationLogTime==locationLog){
			parent.parent.center.map.graphics.clear();
		locationLog=0;
		
				//目的地导航
		if(isdh && mdd_x!='' && mdd_y!=''){//目的地导航
		var end=parent.parent.center.getPoint(mdd_x,mdd_y);	
	    polyline=parent.parent.center.getPolyline(parent.parent.center.getPoint(xy[0][0], xy[0][1]),end);
		polylineGraphic=parent.parent.center.addToMap(polyline);
			}
			
			
		}
		//轨迹跟踪
		if(pointGraphic!=null){
		parent.parent.center.map.graphics.remove(pointGraphic);
		}
		pointGraphic=parent.parent.center.doLocationItWithPoint('', xy[0][0], xy[0][1],isAutoCenter,false);
		var pl = parent.parent.center._$getPolyline(parent.parent.center._$getPoint(start_x, start_y),parent.parent.center._$getPoint(end_x, end_y));
		var highlightGraphic = new parent.parent.center.esri.Graphic(pl,parent.parent.center.commonbluelight);
		parent.parent.center.addHighlight(highlightGraphic);
		start_x=end_x;
		start_y=end_y;

		//缓冲区分析
		//if((gpgga[1]%bufferAnalyseRate)==0){
		//	buffer();
		//}
		}
		
        }else{
		//document.form1.txtReceive.value='获取GPS信号中，请确保GPS设备处于开阔地带。';
		}
        }
} 
} 		
}	


function filter(v){
inpotBuffer+=v;

var i=inpotBuffer.indexOf('$GPGGA');
var j;
if(i>=0){
v=inpotBuffer.substr(i);
var j=inpotBuffer.indexOf('\r\n');
j=inpotBuffer.substring(0,j+1);
if(j.substring(0,1)==','){
v='$GPGGA'+j;
}else{
v='$GPGGA,'+j;
}
inpotBuffer='';
return v;
}else{
return '';
}
}
//定义ajax方法
function ajaxRequest(path) {
	var objXMLReq = new ActiveXObject("Microsoft.XMLHTTP");
		objXMLReq.open("get", path, false);
		objXMLReq.send();
		var result = objXMLReq.responseText;            
		return result;
}
 function buffer(){
 if(start_x!='' && start_y!=''){
 				parent.parent.center.putClientCommond("location","doit");
		        parent.parent.center.putRestParameter("_$sid","aaa");
		        parent.parent.center.putRestParameter("x",start_x);
		        parent.parent.center.putRestParameter("y",start_y);
		        parent.parent.center.putRestParameter("len",len);
			    var result = parent.parent.center.restRequest();
				store.loadData(result);
 }
 }