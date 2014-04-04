package com.klspta.base.wkt;

import java.util.Vector;

public class Linestring {
    Vector<Point> line = new Vector<Point>();
    
    public void putPoint(Point point){
        line.add(point);
    }
    
    public void putPoint(String x, String y){
        line.add(new Point(x, y));
    }
    
    public void putPoint(double x, double y){
        line.add(new Point(x, y));
    }
    
    public void removePoint(int index){
        line.remove(index);
    }
    
    public void removePoint(Point point){
        line.remove(point);
    }
    
    public Point getPoint(int index){
        return line.get(index);
    }
    
    public String toWKT(){
        StringBuffer sb = new StringBuffer("LINESTRING(");
        for(int i = 0; i < line.size(); i++){
            sb.append(getPoint(i).getPoint()).append(",");
        }
        return sb.substring(0, sb.length() - 1).concat(")").toString();
    }
}
