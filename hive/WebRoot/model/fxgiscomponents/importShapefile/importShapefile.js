﻿﻿﻿
Ext.onReady(function(){
var polygonWkt ;
var polygonJson = "";
	var fp = new Ext.form.FormPanel({
        renderTo: 'form-ct',
        fileUpload: true,
        autoWidth: true,
        autoHeight: true,
		bodyStyle: 'padding: 5px 0px 0 5px;',
        labelWidth: 10,
        items: [{
            xtype: 'fileuploadfield',
            id: 'form-file',
            emptyText: '请选择图斑文件(*.shp)',
            name: 'shape-path',
            buttonText: '浏览',            
			width:270
        }],
        buttons: [{
            text: '预览',
            handler: function(){
            var fileType = Ext.getCmp("form-file").getValue().substring(Ext.getCmp("form-file").getValue().lastIndexOf(".")+1);  
                if(fp.getForm().isValid()){
                	if(fileType == "shp"){
		                fp.getForm().submit({
							url:  basePath + "/service/rest/parseShapefile/parseShapefile",
							method:'POST', 
							waitTitle:'提示',
		                    waitMsg: '正在导入,请稍后...',
		                    success: function(form,action){
	  							
		                    },
	                        failure:function(form,action){
	                        	var json=strToJson(action.response.responseText);	                        	
								polygonJson = json[0].geo;//geometry
                                myData=json[0].pro;
  								var polygon =Ext.util.JSON.encode(polygonJson);
		                     	//定位测试
		                     	//var rings="{\"rings\":[[[40449368,3620962],[40449375,3620791],[40449552,3620788],[40449538,3620899],[40449368,3620962]]],\"spatialReference\":{\"wkid\":2364}}";
  								parent.center.frames["lower"].swfobject.getObjectById("FxGIS").doLocation(polygon);
  								Ext.getCmp("analysis").disable().enable();
	                        	/*
								var json=strToJson(action.response.responseText);
								polygonJson = json[0].geo;
                                myData=json[0].pro;
								store.loadData(myData);
								var polygon = new esri.geometry.Polygon();
								var shapefileType=json[0].shapefileType;
								for(var i = 0; i < json[0].geo.length; i++){
									var geo = json[0].geo;
									polygon.addRing(geo[i]);
								}	
								if(shapefileType == "polygon"){
									Ext.getCmp("analysis").disable().enable();
									//Ext.getCmp("savebtn").disable().enable();
								}
								if(shapefileType == "polyline"){
									Ext.getCmp("analysis").disable(); 
								}
								if(shapefileType == "point"){
									Ext.getCmp("analysis").disable();
									//Ext.getCmp("savebtn").disable().enable();
								}
								//var textSymbol = parent.application.getTextSymbol("Hello Word!");
								
								polygon.spatialReference = new esri.SpatialReference({ wkid: _$WKID});
								var highlightGraphic = new esri.Graphic(polygon,parent.center.commonsfs);
			                    parent.center.addHighlight(highlightGraphic);
			                    
			                    parent.center.setMapExtent(polygon.getExtent().expand(3));	*/
	                        }
		                });
		                Ext.getCmp("form-file").getValue()
		                
                	}else{
                		Ext.Msg.alert('提示', '请选择shp文件！');	
                	}
                }
            }
        },{
            text: '分析',
            id:'analysis', 
            disabled:true,
            handler: function(){				
                store.loadData(myData);
            	store.load({params:{start:0,limit:15}}); 
          	  //var polygon = new esri.geometry.Polygon(parent.center.getMapSpatialReference());
			  //polygon.addRing(stringToGeometry(polygonJson)); 
			 /*
          	  var progressBar=Ext.Msg.show({
	              title:"提示",
	              msg:"shape文件分析中...",
	              width:200,
	              wait:true,
	              waitConfig:{
	              	interval:1,
	              	duration:1600,
	              	increment:100,
	              	fn:function () {  
	               
		            // var result  = 	parent.center.showPropertiesAndAnalyseResult(polygon,'2,3');
		            // if(!result){
		            // 	 Ext.Msg.hide();
		            // }
		             	
	              }},
	              closable:true
          	  });	
          	    */
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
