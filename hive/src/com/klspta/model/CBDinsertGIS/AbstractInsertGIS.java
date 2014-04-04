package com.klspta.model.CBDinsertGIS;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.wkt.Point;
import com.klspta.base.wkt.Polygon;
import com.klspta.base.wkt.Ring;


public abstract class AbstractInsertGIS extends AbstractBaseBean{
	
	public String buildWKT(InputStream inputStream) throws Exception{
		Polygon polygon = new Polygon();
		Ring ring = new Ring();
		String wkt = "";
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String readline = null;
		double beginX = 0;
		double beginY = 0;
		while ((readline = bufferedReader.readLine()) != null) {
			if(readline.contains("于端点")){
				double x = 0;
				double y = 0;
				String[] strings = readline.split(" ");
				for(int i = 0; i < strings.length; i++){
					String points = strings[i];
					if(points.contains("X=")){
						points = points.substring(2);
						x = Double.parseDouble(points);
						if(beginX == 0){
							beginX = x;
						}
						System.out.println(points);
					}else if (points.contains("Y=")) {
						points = points.substring(2);
						y = Double.parseDouble(points);
						if(beginY == 0){
							beginY = y;
						}
						System.out.println(points);
					}
				}
				Point point = new Point(x, y);
				ring.putPoint(point);
			}
		}
		Point point = new Point(beginX, beginY);
		ring.putPoint(point);
		polygon.addRing(ring);
		wkt = polygon.toWKT();
		return wkt;
	}
	public abstract boolean insertGIS(InputStream inputStream,String guid);
	
	public String getSrid(String tableName){
		String querySrid = "select t.srid from sde.st_geometry_columns t where upper(t.table_name) = ?";
		String srid = null;
		List<Map<String, Object>> rs = query(querySrid, GIS, new Object[]{tableName});
		if(rs.size() > 0){
			srid = rs.get(0).get("srid") + "";
		}
		return srid;
	}
	
	public boolean isExit(String formName, String primaryName, String primaryValue, String type){
		if("".equals(primaryName) || "".equals(primaryValue)){
			return false;
		}
		String sql = "select " + primaryName + " from " + formName + " where " + primaryName + "='" + primaryValue + "'";
		List<Map<String, Object>> list = query(sql, type);
		if(list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
}
