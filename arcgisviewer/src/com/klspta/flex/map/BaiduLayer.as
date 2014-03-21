package com.klspta.flex.map{
	
	import com.esri.ags.SpatialReference;
	import com.esri.ags.geometry.Extent;
	import com.esri.ags.geometry.MapPoint;
	import com.esri.ags.layers.TiledMapServiceLayer;
	import com.esri.ags.layers.supportClasses.LOD;
	import com.esri.ags.layers.supportClasses.TileInfo;
	
	import flash.net.URLRequest;
	
	import flashx.textLayout.formats.Float; 
	
	public class BaiduLayer extends TiledMapServiceLayer{
		
		//成员变量 
		private var _tileInfo:TileInfo = new TileInfo(); 
		private var _wkid:int = 102100;
		private var cornerCoordinate:Number = 20037508.3427892; 
		private var _mapStyle:String = "Image"; 
		private var _initialExtent:Extent;
		
		public function BaiduLayer(mapStyle:String) { 
			this._mapStyle = mapStyle;
			super(); 
			buildTileInfo(); // to create our hardcoded tileInfo 
			setLoaded(true); // Map will only use loaded layers 
		}
		
		//  全屏范围 
		override public function get fullExtent():Extent { 
			return new Extent(-cornerCoordinate, -cornerCoordinate, cornerCoordinate, cornerCoordinate, new SpatialReference(_wkid)); 
		} 
		
		//  初始化范围 (左下角坐标，右上角坐标)
		override public function get initialExtent():Extent { 
			return new Extent(5916776.8, 1877209.3, 19242502.6, 7620381.8, new SpatialReference(102100));
		} 
		
		//  空间参考系 
		override public function get spatialReference():SpatialReference { 
			return new SpatialReference(_wkid); 
		} 
		
		override public function get tileInfo():TileInfo { 
			return _tileInfo; 
		} 
		
		override protected function getTileURL(level:Number, row:Number, col:Number):URLRequest { 
			var zoom:int = level - 1; 
			var offsetX:int = Math.pow(2, zoom) as int; 
			var offsetY:int = offsetX - 1; 
			var numX:int = col - offsetX; 
			var numY:int = (-row) + offsetY; 
			
			zoom = level + 1; 
			var num:int = (col + row) % 8 + 1; 
			var url:String = null; 
			if (_mapStyle == "Vector"){ //获取矢量地图  
				//http://online3.map.bdimg.com/tile/?qt=tile&x=3273&y=890&z=14&styles=pl&udt=20140314
				url = "http://online"+num+".map.bdimg.com/tile/?qt=tile&x="+numX+"&y="+ numY + "&z=" + zoom + "&styles=pl"; 
			} else if (_mapStyle == "Image"){  //影像地图
				//http://shangetu1.map.bdimg.com/it/u=x=3274;y=891;z=14;v=009;type=sate&fm=46&udt=20140117
				url = "http://shangetu"+num+".map.bdimg.com/it/u=x="+numX+";y="+numY+";z="+zoom+";v=009;type=sate&fm=46"; 
			} else if (_mapStyle == "POI"){  //获取道路等POI，和影像地图配合使用  
				//http://online0.map.bdimg.com/tile/?qt=tile&x=3272&y=892&z=14&styles=sl&v=021&udt=20140314
				url = "http://online"+num+".map.bdimg.com/tile/?qt=tile&x="+numX+"&y="+numY+"&z="+zoom+"&styles=sl&v=021&udt=20140314"; 
				
			} 
			trace(url);
			return new URLRequest(url); 
		} 
		
		//  自定义方法，定义地图缩放等级 
		private function buildTileInfo():void { 
		
			_tileInfo.height=256; 
			_tileInfo.width=256; 
			_tileInfo.origin=new MapPoint(-cornerCoordinate, cornerCoordinate); 
			_tileInfo.spatialReference=new SpatialReference(_wkid); 
			_tileInfo.lods = [ 
				new LOD(0, 156543.033928, 591657527.591555), 
				new LOD(1, 78271.5169639999, 295828763.795777), 
				new LOD(2, 39135.7584820001, 147914381.897889), 
				new LOD(3, 19567.8792409999, 73957190.948944), 
				new LOD(4, 9783.93962049996, 36978595.474472), 
				new LOD(5, 4891.96981024998, 18489297.737236), 
				new LOD(6, 2445.98490512499, 9244648.868618), 
				new LOD(7, 1222.99245256249, 4622324.434309), 
				new LOD(8, 611.49622628138, 2311162.217155), 
				new LOD(9, 305.748113140558, 1155581.108577), 
				new LOD(10, 152.874056570411, 577790.554289), 
				new LOD(11, 76.4370282850732, 288895.277144), 
				new LOD(12, 38.2185141425366, 144447.638572), 
				new LOD(13, 19.1092570712683, 72223.819286), 
				new LOD(14, 9.55462853563415, 36111.909643), 
				new LOD(15, 4.77731426794937, 18055.954822), 
				new LOD(16, 2.38865713397468, 9027.977411), 
				new LOD(17, 1.19432856685505, 4513.988705), 
				new LOD(18, 0.597164283559817, 2256.994353), 
				new LOD(19, 0.298582141647617, 1128.497176) 
			]; 
		} 
	} 
}