package widgets.PlayBack{
	import com.esri.ags.Graphic;
	import com.esri.ags.Map;
	import com.esri.ags.TimeExtent;
	import com.esri.ags.components.TimeSlider;
	import com.esri.ags.events.ExtentEvent;
	import com.esri.ags.geometry.MapPoint;
	import com.esri.ags.layers.FeatureLayer;
	import com.esri.ags.layers.supportClasses.FeatureCollection;
	import com.esri.ags.layers.supportClasses.LayerDetails;
	import com.esri.ags.layers.supportClasses.TimeInfo;
	import com.esri.ags.renderers.SimpleRenderer;
	import com.esri.ags.renderers.TemporalRenderer;
	import com.esri.ags.renderers.supportClasses.AlphaRange;
	import com.esri.ags.renderers.supportClasses.SizeRange;
	import com.esri.ags.renderers.supportClasses.TimeRampAger;
	import com.esri.ags.symbols.PictureMarkerSymbol;
	import com.esri.ags.symbols.SimpleLineSymbol;
	import com.esri.ags.symbols.SimpleMarkerSymbol;
	import com.esri.ags.utils.GraphicUtil;
	
	import mx.collections.ArrayCollection;

	public class PlayBack{
		
		private var timeSlider:TimeSlider = null;
		private var map:Map = null;
		public var flayer:FeatureLayer = null;
		private var temporalRenderer:TemporalRenderer =null;
		public var features:Array=[];
		
		
		public function PlayBack(timeSlider:TimeSlider,map:Map){
			this.timeSlider = timeSlider;
			this.map = map;
			
			flayer=new FeatureLayer();
			initRender();
			
			var timeInfo:TimeInfo = new TimeInfo();
			timeInfo.startTimeField = "date";
			var details:LayerDetails = new LayerDetails();
			details.timeInfo = timeInfo;
			flayer.featureCollection = new FeatureCollection(null,details);
			
			map.addLayer(flayer);
			map.timeSlider = timeSlider;
		}
		
		
		public function playBack(result:ArrayCollection):void{
			if(features.length!=0){
				flayer.applyEdits(null,null,features);
				features=[];
			}
			addFeatures(result);
		}
		
		private function initRender():void{
			var trackRenderer:SimpleRenderer = new SimpleRenderer();
			var latestObservationRenderer:SimpleRenderer = new SimpleRenderer();
			var observationRenderer:SimpleRenderer = new SimpleRenderer();
			var observationAger:TimeRampAger = new TimeRampAger();
			
			temporalRenderer = new TemporalRenderer();
			temporalRenderer.trackRenderer = trackRenderer;
			temporalRenderer.latestObservationRenderer = latestObservationRenderer;
			temporalRenderer.observationRenderer = observationRenderer;
			temporalRenderer.observationAger = observationAger;
			
			trackRenderer.symbol = new SimpleLineSymbol();
			
			var latestObservationSymbol:PictureMarkerSymbol = new PictureMarkerSymbol();
			latestObservationSymbol.height = 40;
			latestObservationSymbol.width = 40;
			latestObservationSymbol.source = "widgets/PlayBack/greenCar.png";
			latestObservationRenderer.symbol = latestObservationSymbol;
			
			var observationSymbol:SimpleMarkerSymbol = new SimpleMarkerSymbol();
			observationSymbol.color = 0xFF0000;
			observationRenderer.symbol = observationSymbol;
			
			var alphaRange:AlphaRange = new AlphaRange();
			alphaRange.fromAlpha = 0.1;
			alphaRange.toAlpha = 0.8;
			var sizeRange:SizeRange = new SizeRange();
			sizeRange.fromSize = 2;
			sizeRange.toSize = 10;
			observationAger.alphaRange = alphaRange;
			observationAger.sizeRange = sizeRange;
			
			flayer.renderer = temporalRenderer;
		}
		
		private function addFeatures(result:ArrayCollection):void{
			var timeExtent:TimeExtent=new TimeExtent();
			for(var i:Number=0;i<result.length;i++){
				var obj:Object=result.getItemAt(i);
				var id:String=obj.ID;
				var x:Number=obj.X;
				var y:Number=obj.Y;
				var date:Date=obj.H_DATE;
				var graphic:Graphic=new Graphic(new MapPoint(x,y),null,{"date":date});
				features.push(graphic);
				//start and end date
				if (!timeExtent.startTime || timeExtent.startTime.time > date.time)
				{
					timeExtent.startTime = date;
				}
				if (!timeExtent.endTime || timeExtent.endTime.time < date.time)
				{
					timeExtent.endTime = date;
				}
			}
			if(features.length>0){
				flayer.applyEdits(features,null,null);
				map.extent=GraphicUtil.getGraphicsExtent(features);
				map.addEventListener(ExtentEvent.EXTENT_CHANGE, function map_extentChangeHandler(event:ExtentEvent):void
				{
					map.removeEventListener(ExtentEvent.EXTENT_CHANGE, map_extentChangeHandler);
					if (timeExtent.startTime !== timeExtent.endTime)
					{
						if (timeSlider)
						{
							timeSlider.createTimeStopsByCount(timeExtent, features.length);
							//timeSlider.createTimeStopsByTimeInterval(timeExtent,36,TimeInfo.UNIT_SECONDS);
						}
						else
						{
							map.timeExtent = timeExtent;
						}
					}
				});
			}
		}
	}
}