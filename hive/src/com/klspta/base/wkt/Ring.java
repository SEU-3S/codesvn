package com.klspta.base.wkt;

import java.util.Vector;

import com.klspta.base.util.UtilFactory;
import com.vividsolutions.jts.geom.Coordinate;

/**
 * point -> ring -> polygon -> multipolygon
 * @author wang
 *
 */
public class Ring {
	Vector<Point> ring = new Vector<Point>();
	
	public Ring(){
	}
	
	public Ring(Coordinate[] coords){
	    Coordinate coord = null;
        for(int j = 0, jc = coords.length; j < jc; j++){
            coord = coords[j];
            putPoint(coord.x, coord.y);
        }
	}
	
	
    public void putPoint(Point point){
    	ring.add(point);
    }
    
    public void putPoint(String x, String y){
    	ring.add(new Point(x, y));
    }
    
    public void putPoint(double x, double y){
    	ring.add(new Point(x, y));
    }
    
    public void removePoint(int index){
    	ring.remove(index);
    }
    
    public void removePoint(Point point){
    	ring.remove(point);
    }
    
    public Point getPoint(int index){
    	return ring.get(index);
    }
    
    public Point getPointFormat(int index, String type){
        return UtilFactory.getCoordinateChangeUtil().changePoint(ring.get(index), type);
    }
    
    public int getPointCount(){
        return ring.size();
    }
    
    public String toWKT(){
    	StringBuffer sb = new StringBuffer("( ");
    	for(int i = 0; i < ring.size(); i++){
    		sb.append(getPoint(i).getPoint()).append(", ");
    	}
    	return sb.substring(0, sb.length() - 2).concat(")").toString();
    }
}
