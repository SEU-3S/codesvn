﻿﻿﻿
Ext.onReady(function(){
var polygonWkt ;
var _$ID = '';
var polygonJson = "";
var array = new Array(); 
	var fp = new Ext.form.FormPanel({
        renderTo: 'form-ct',
        fileUpload: true,
        autoWidth: true,
        autoHeight: true,
        url: 'http://127.0.0.1:8080/reduce/service/rest/parseText/parseText?objectid=0&bjectid2=' + _$ID,
		bodyStyle: 'padding: 5px 0px 0 5px;',
        labelWidth: 10,
        items: [{
            xtype: 'fileuploadfield',
            id: 'form-file',
            emptyText: '请选择图斑文件(*.txt)',
            name: 'shape-path',
            buttonText: '浏览',            
			width:270
        }],
        buttons: [{
            text: '预览',
            handler: function(){
	    var filePath=fp.getForm().findField('shape-path').getRawValue();
		var re = /(\\+)/g;  
		var filename=filePath.replace(re,"#"); 
		//对路径字符串进行剪切截取
	    var one=filename.split("#"); 
		//获取数组中最后一个，即文件名
		var two=one[one.length-1]; 
	    //再对文件名进行截取，以取得后缀名
		var three=two.split("."); 
		//获取截取的最后一个字符串，即为后缀名
		var last=three[three.length-1];
		//添加需要判断的后缀名类型
		var tp ="txt";
		//返回符合条件的后缀名在字符串中的位置
		var rs=tp.indexOf(last); 
		//如果返回的结果大于或等于0，说明包含允许上传的文件类型
		if(rs>=0&&filePath!=""){ 
	      fp.getForm().submit({  
	        success: function(form, action){
	           var rings=action.result.msg;
	           alert(rings);
	           parent.center.frames["lower"].swfobject.getObjectById("FxGIS").doLocation(rings);
	        },  
	        failure: function(){  
	           Ext.Msg.alert('错误', '文件导入失败');  
	        }  
	      });
	    }else if(filePath==""){
	    	Ext.Msg.alert('错误', '请选择需要导入的文件！');
	    }
	    else{
	        Ext.Msg.alert('错误', '导入文件类型错误！请选择txt文件');  
	    }  
            }
        },{
            text: '保存', 
            id:'savebtn',
            disabled:true,
            handler: function(){ 
              parent.center.map.graphics.clear();
          	  var polygon = new esri.geometry.Polygon(parent.center.getMapSpatialReference());
			  polygon.addRing(array);
			  parent.center.saveMark(polygon);
            }
        }]
    });
	
	
});

function strToJson(str){
     var json = eval('(' + str + ')');     
     return json;
}

//wkt格式转换成geometry格式json
//"POLYGON((40446762.9465000000 3591634.0715999994,40446835.7766000000 3591622.4068,40446909.5786000000 3591607.9586999994,40446969.7170000000 3591596.546,40446981.6202000000 3591594.6547999997,40447014.9606000000 3591589.3575,40447016.0948000000 3591587.701300001,40447013.3025000000 3591571.8082,40447012.1508000000 3591537.5703999996,40447002.1697000000 3591470.4637,40446998.4646000000 3591452.533500001,40446995.8432000000 3591436.139799999,40446994.1353000000 3591434.3160999995,40446991.6903000000 3591434.6993000004,40446976.6037000000 3591402.8836000003,40446942.1220000000 3591340.436899999,40446924.9065000000 3591349.319,40446869.8082000000 3591376.1602,40446811.6546999960 3591402.8817,40446786.0630000000 3591412.8461000007,40446787.4178000000 3591425.2993,40446803.1688000000 3591452.1334000006,40446804.0997000040 3591456.531300001,40446806.0472999960 3591476.615700001,40446804.2704000000 3591507.261499999,40446804.6093000000 3591518.7957000006,40446806.9810000000 3591546.909499999,40446809.0987000000 3591563.9306000005,40446812.5682000000 3591578.5624,40446812.9090000000 3591592.0512000006,40446789.6974000000 3591597.8958,40446761.6870000000 3591604.6619000006,40446761.5065000000 3591616.3684,40446762.9465000000 3591634.071599999,40446762.9465000000 3591634.0715999994))";
function wktToRing(wkt){
           	var ring1 = wkt.substring((wkt.indexOf("(("))+2,wkt.length-2);
           	var regExp = new RegExp(" ","g");
			var ring2 = ring1.replace(regExp , ","); 
           	var ring3 = ring2.split(",");
           	var array = new Array(); 
           	for(var i=0;i<(ring1.match(/,/g).length)+1;i++){	
           	   array[i]=new Array(); 
			   array[i][0]=Number(ring3[i*2]); 
			   array[i][1]=Number(ring3[i*2+1]); 
           	 }
           	return array;        	 
}

//字符串转换成geometry格式json
function stringToGeometry(str){
			var str1 = ""+str;
		    var str2 = str1.split(","); 
		   	var array = new Array(); 
		   	for(var i=0;i<(str1.match(/,/g).length)/2;i++){	
		   	   array[i]=new Array(); 
			   array[i][0]=Number(str2[i*2]); 
			   array[i][1]=Number(str2[i*2+1]); 
		   	 }
		   	 return array; 	
}
