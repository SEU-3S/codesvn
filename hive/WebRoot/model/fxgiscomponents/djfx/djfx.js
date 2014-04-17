﻿﻿﻿
Ext.onReady(function(){
var polygonWkt ;
var _$ID = '';
var polygonJson = "";
	var fp = new Ext.form.FormPanel({
        renderTo: 'form-ct',
        fileUpload: true,
        autoWidth: true,
        autoHeight: true,
		bodyStyle: 'padding: 5px 0px 0 5px;',
        labelWidth: 20,
      items: [{
            xtype: 'fileuploadfield',
            id: 'form-file',
            emptyText: '请选择正确图斑文件',
            name: 'shape-path',
             buttonText: '浏览', 
			width:210
			
       // }//,{  xtype: 'button',
          //  id: 'form',
          //  text:'导入并分析', 
		//	width:60
			}],
        buttons: [{
            text: '导入并分析',
            handler: drbfx
        },{
            text: '从图选择并分析',
            id:'analysis', 
            disabled:false,
            handler:ctxzbfx
        }, {text:'绘制并分析',
        handler:hzbfx
        }]
    });
	
	
});

function strToJson(str){
     var json = eval('(' + str + ')');     
     return json;
}

//wkt格式转换成geometry格式json
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
function hzbfx(){alert("绘制并分析");
frames["lower"].swfobject.getObjectById("FxGIS").full();
}
function ctxzbfx(){alert("选择并分析");
frames["lower"].swfobject.getObjectById("FxGIS").identify();
}

function  drbfx(){
            var fileType = Ext.getCmp("form-file").getValue().substring(Ext.getCmp("form-file").getValue().lastIndexOf(".")+1);  
            	alert(fileType);
                if(fp.getForm().isValid()){
                	if(fileType == "shp"){
		                fp.getForm().submit({
							url:  "http://" + window.location.href.split("/")[2] + '/reduce/service/rest/parseShapefile/parseShapefile?objectid=0&bjectid2=' + _$ID,
							method:'POST', 
							waitTitle:'提示',
		                    waitMsg: '正在导入,请稍后...',
		                    success: function(form,action){
	  	
		                    },
	                        failure:function(form,action){
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
									Ext.getCmp("savebtn").disable().enable();
								}
								if(shapefileType == "polyline"){
									Ext.getCmp("analysis").disable(); 
								}
								if(shapefileType == "point"){
									Ext.getCmp("analysis").disable();
									Ext.getCmp("savebtn").disable().enable();
								}
								//var textSymbol = parent.application.getTextSymbol("Hello Word!");
								
								polygon.spatialReference = new esri.SpatialReference({ wkid: _$WKID});
								var highlightGraphic = new esri.Graphic(polygon,parent.center.commonsfs);
			                    parent.center.addHighlight(highlightGraphic);
			                    
			                    parent.center.setMapExtent(polygon.getExtent().expand(3));	
	                        }
		                });
                	}else{
                		Ext.Msg.alert('提示', '请选择shp文件！');	
                	}
                }
            }
            }