package com.klspta.base.wkt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.klspta.base.util.UtilFactory;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class Polygon {

    Vector<Ring> polygon = new Vector<Ring>();

    Geometry geo;

    int wkid;

    public Polygon() {
    }

    /**
     * 
     * <br>Description:多个ring构建Polygon
     * <br>Author:陈强峰
     * <br>Date:2012-8-23
     * @param rings
     * @param wkid
     */
    public Polygon(List<Ring> rings, int wkid) {
        this.wkid = wkid;
        for (int i = 0; i < rings.size(); i++) {
            polygon.add(rings.get(i));
        }
    }

    /**
     * 
     * <br>Description:对于list 格式坐标构建面
     * <br>Author:陈强峰
     * <br>Date:2012-8-23
     * @param list list中为string x,y格式 例如 200,300
     * @param wkid
     * @param flag
     */
    public Polygon(List<String> list, int wkid, boolean flag) {
        this.wkid = wkid;
        Ring ring = new Ring();
        for (int i = 0; i < list.size(); i++) {
            String[] xy = list.get(i).split(",");
            ring.putPoint(new Point(xy[0], xy[1]));
        }
        this.polygon.add(ring);
    }

    public Polygon(String wkt) {
        WKTReader reader = new WKTReader();
        try {
            geo = reader.read(wkt);
            int geocount = geo.getNumGeometries();
            for (int i = 0; i < geocount; i++) {
                polygon.add(new Ring(geo.getGeometryN(i).getCoordinates()));
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
    }

    public void addRing(Ring ring) {
        polygon.add(ring);
    }

    public void addRing(int index, Ring ring) {
        polygon.add(index, ring);
    }

    public void removeRing(int index) {
        polygon.remove(index);
    }

    public void removeRing(Ring ring) {
        polygon.remove(ring);
    }

    public Ring getRing(int index) {
        return polygon.get(index);
    }

    public int getRingCount() {
        return polygon.size();
    }
    
    public void setWktid(int wktid){
        this.wkid=wktid;
    }

    public String toWKT() {
        if (polygon.size() > 1) {
            StringBuffer sb = new StringBuffer("MULTIPOLYGON  ((");
            for (int i = 0; i < polygon.size(); i++) {
                sb.append(getRing(i).toWKT()).append(",");
            }
            return sb.substring(0, sb.length() - 1).concat("))").toString();
        } else {
            StringBuffer sb = new StringBuffer("POLYGON  (");
            for (int i = 0; i < polygon.size(); i++) {
                sb.append(getRing(i).toWKT()).append(",");
            }
            return sb.substring(0, sb.length() - 1).concat(")").toString();
        }
    }

    /**
     * 
     * <br>Description:针对定位的wkt
     * <br>Author:陈强峰
     * <br>Date:2012-8-23
     * @return
     */
    public String toJson() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<List<List<Object>>> allRings = new ArrayList<List<List<Object>>>();
        for (int i = 0; i < polygon.size(); i++) {
            List<List<Object>> points = new ArrayList<List<Object>>();
            Vector<Point> v = polygon.get(i).ring;
            for (int y = 0; y < v.size(); y++) {
                List<Object> point = new ArrayList<Object>();
                point.add(v.get(y).getX());
                point.add(v.get(y).getY());
                points.add(point);
                //对于单个ring首尾坐标是否一致的处理
                if (y == v.size() - 1 && (!v.get(y).getSplitePoint().equals(points.get(0).get(0)))) {
                    points.add(points.get(0));
                }
            }
            allRings.add(points);
        }
        Map<String, Object> wktMap = new HashMap<String, Object>();
        wktMap.put("wkid", this.wkid);
        map.put("rings", allRings);
        map.put("spatialReference", wktMap);
        try {
            return UtilFactory.getJSONUtil().objectToJSON(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void clear() {
        polygon.clear();
    }
}
