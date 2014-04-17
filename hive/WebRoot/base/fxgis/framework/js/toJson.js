function Dictionary() {
	this.data = new Array();
	
	this.put = function(key, value,type,flag) {
		if(type=='tiled'){
			this.data[key] =flag;
		 return
		}
		if(type=='wmts'){
			this.data[key] =flag;
		 return
		}
		var t = this.data[key]
		 if(t==null){
		   this.data[key]=null;
		 }
		if(flag){
			if (t != null) {
				this.data[key] = t + ',' + value;
			} else {
				this.data[key] = value;
			}
		}
	};
	
	this.get = function(key) {
		return this.data[key];
	};
	
	this.remove = function(key) {
		this.data[key] = null;
	};
	
	this.size = function() {
		return this.data.length;
	};
	
	this.toStr = function() {
		var jsonStr = "[";
		for (var key in this.data) {
			if (this.data.hasOwnProperty(key)) {
				if( this.data[key]==null){
			       jsonStr += '{\"servicename\":\"' + key + '\",\"visiableids\":[]},'
				}else{
					if(typeof(this.data[key]) =="boolean"){
						jsonStr += '{\"servicename\":\"' + key + '\",\"visiableids\":'
							+ this.data[key] + '},'
					}else{
					jsonStr += '{\"servicename\":\"' + key + '\",\"visiableids\":['
							+ this.data[key] + ']},'
					}
				}
			}
		}
		jsonStr = jsonStr.substring(0, jsonStr.lastIndexOf(",")) + "]";
		return jsonStr;
	}
}
