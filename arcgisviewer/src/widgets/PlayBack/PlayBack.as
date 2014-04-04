package widgets.PlayBack{
	import com.esri.ags.Map;
	import com.esri.ags.components.TimeSlider;
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
	
	import flash.display.SimpleButton;

	public class PlayBack{
		
		var timeSlider:TimeSlider = null;
		var map:Map = null;
		var flayer:FeatureLayer = new FeatureLayer();
		
		
		public function PlayBack(timeSlider:TimeSlider,map:Map){
			this.timeSlider = timeSlider;
			this.map = map;
		}
		
		
		public function playBack():void{
			initFeatureLayer();
			featureAndMap();
			getData();
		}
		
		
		private function initFeatureLayer():void{
			var trackRenderer:SimpleRenderer = new SimpleRenderer();
			var latestObservationRenderer:SimpleRenderer = new SimpleRenderer();
			var observationRenderer:SimpleRenderer = new SimpleRenderer();
			var observationAger:TimeRampAger = new TimeRampAger();
			
			var temporalRenderer:TemporalRenderer = new TemporalRenderer();
			temporalRenderer.trackRenderer = trackRenderer;
			temporalRenderer.latestObservationRenderer = latestObservationRenderer;
			temporalRenderer.observationRenderer = observationRenderer;
			temporalRenderer.observationAger = observationAger;
			
			trackRenderer.symbol = new SimpleLineSymbol();
			
			var latestObservationSymbol:PictureMarkerSymbol = new PictureMarkerSymbol();
			latestObservationSymbol.height = 40;
			latestObservationSymbol.width = 40;
			latestObservationSymbol.source = "@Embed('widgets/PlayBack/greenCar.png')";
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
		
		
		private function featureAndMap():void{
			var timeInfo:TimeInfo = new TimeInfo();
			timeInfo.startTimeField = "time";
			
			var details:LayerDetails = new LayerDetails();
			details.timeInfo = timeInfo;
			
			flayer.featureCollection = new FeatureCollection(null,details);
			
			map.addLayer(flayer);
			map.timeSlider = timeSlider;
		}
		
		private function getData():void{
			var gpxFeed:GPXFeed = new GPXFeed();
			gpxFeed.featureLayer = flayer;
			gpxFeed.map = map;
			gpxFeed.timeSlider = timeSlider;
			gpxFeed.url = "widgets/PlayBack/activity.gpx";
			gpxFeed.fetch();
		}
	}
}