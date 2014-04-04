package com.klspta.model.parseShapefile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.geotools.data.shapefile.ShpFiles;
import org.geotools.data.shapefile.shp.ShapeType;
import org.geotools.data.shapefile.shp.ShapefileException;
import org.geotools.data.shapefile.shp.ShapefileReader;
import com.klspta.base.wkt.Point;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

/**
 * <br>Title:读取shape文件
 * <br>Description:
 * <br>Author:李如意
 * <br>Date:2011-8-3
 */
public class ShpReader {
	
	private String filepath = "";
	private ShpFiles shp;
	public ShpReader(String filepath){
		this.filepath = filepath;
		try {
			shp = new ShpFiles(filepath);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <br>Description:解析shape文件
	 * <br>Author:王雷
	 * <br>Date:2012-9-27
	 * @return 
	 */
	public Map<String, Object> parseShapefile(){
		ShapefileReader r = null;
		int count=1;
		List list=new ArrayList();
		Vector<Vector<Vector<String>>> polygon = new Vector<Vector<Vector<String>>>();
		Vector<Vector<Vector<String>>> polygon2=null;
		 Vector<Vector<String>> geometry =null;
		 Map<String,Object> wkid=new HashMap<String ,Object>();
		try {
			r = new ShapefileReader(shp, false, false);
			Point tp = new Point(0, 0);
	    	while (r.hasNext()){
	    		 polygon2=new Vector<Vector<Vector<String>>>();
	    		 Geometry g = (Geometry)r.nextRecord().shape();
	    		 list.add(count);
	    		 list.add(g.getArea());
	    		 list.add(g.getLength());
	    		 Coordinate[] cc = g.getCoordinates();
	    		 geometry= new Vector<Vector<String>>();
	    		 for(int i = 0; i < cc.length; i++){
		    		 Vector<String> ring = new Vector<String>();
		    		 tp.setPointXY(cc[i].x, cc[i].y);
		    		// tp = UtilFactory.getChangeCoordsSysUtil().changeMe(tp, IChangeCoordsSysUtil.BL80_TO_PLAIN80);
		    		 ring.add(tp.getX4Str());
		    		 ring.add(tp.getY4Str());
		    		 geometry.add(ring);
	    		 } 
	    		 polygon2.add(geometry);
	    		 polygon.add(geometry); 
	    		 count++;
	    	     wkid.put("wkid", 2364);
	    	     Map<String,Object> map2=new HashMap<String ,Object>();
	    	     map2.put("spatialReference", wkid);
	    	     map2.put("rings", polygon2);
	    		 list.add(map2);
	    	 }
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ShapefileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(r != null){
				try {
					r.close();
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
		}
		
		Map<String, Object> ret = new HashMap<String, Object>();
	    Map<String,Object> map=new HashMap<String ,Object>();
	    map.put("rings", polygon);
	    map.put("spatialReference", wkid);
		ret.put("geo",map);
	    DecimalFormat a = new DecimalFormat("0.00");
		String[][]  responsetext=new String[list.size()/4][4];
		int flag=0;
		for(int i=0;i<list.size()/4;i++){
			if(flag<list.size()){
				responsetext[i][0]=String.valueOf(list.get(flag++));
				responsetext[i][1]=String.valueOf(a.format((Double)list.get(flag++) * 0.0015))+"亩";
				responsetext[i][2]=String.valueOf(a.format(list.get(flag++)))+"米";	
				responsetext[i][3]=String.valueOf(list.get(flag++));	
			}	
		}
		ret.put("pro", responsetext);
		return  ret;
	}


	
	/**
	 * <br>Description:获取shape文件类型
	 * <br>Author:李如意
	 * <br>Date:2011-8-3
	 * @return
	 */
	public String getShapefileType() {
	        ShapefileReader r;
	        try {
	            r = new ShapefileReader(shp, false, false);
	            ShapeType type = r.getHeader().getShapeType();
	            if (type.isLineType()) {
	                return "polyline";
	            } else if ( type.isMultiPointType() || type.isPointType()) {
	                return "point";
	            } else if (type.isPolygonType()) {
	                return "polygon";
	            }
	        } catch (ShapefileException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;

	}
}
