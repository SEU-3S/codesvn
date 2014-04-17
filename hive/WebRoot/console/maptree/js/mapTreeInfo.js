
function stringToArray(str){
	//alert("stringToArray:"+str);    
		var str1 = ""+str;
	    var str2 = str1.split(","); 
	   	var array = new Array(); 
	   	for(var i=0;i<(str1.match(/,/g).length)/2;i++){	 
	   	   array[i]=new Array(); 
		   array[i][0]=str2[i*2]; 
		   array[i][1]=str2[i*2+1]; 
	   	 }
	   	 return array; 	 
}

function change(str,layerId){
		var result;
		if((str != "") && (layerId != "")){ 
			var json = eval('('+str+')');
			var layerJSON = json.layers
			for(var i in json.layers){
				if(layerJSON[i].id == layerId){
					result = layerJSON[i].name;
				}  
			}
		}else{
			result = "";
		}
		return result;
}






function layerIds(str){
	var result; 
		if(str != "" && str != null){
			var json = eval('('+str+')');
			var layerJSON = json.layers
			 result = layerJSON; 
		}else{ 
			result = ""; 
		}
	return result;
}