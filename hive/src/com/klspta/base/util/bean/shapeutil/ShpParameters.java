package com.klspta.base.util.bean.shapeutil;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ShpParameters {

    private  String fields = "geom:Polygon";
    
    private  Vector<Map<String, String>> records = new Vector<Map<String, String>>();
    
    public static final String LINE_GEOMETRY_TYPE = "Poline";
    
    public static final String POINT_GEOMETRY_TYPE = "Point";
    
    private String filename = "";
    
    private String filepath = "";
    
    public void putField(String type, String name){
        fields = fields + "," + name + ":" + type;//"geom:Polygon,name:String,SS:String"
    }
    public String getFields(){
        return fields;
    }
    
    public void putRecord(String wkt, String[] attrs){
        Map<String, String> record = new HashMap<String, String>();
        record.put("geometry", wkt);
        int i = 0;
        for(i = 0; i < attrs.length; i++){
            record.put("" + i, attrs[i]);
        }
        String attrs2[]=attrs[0].split(",");
        record.put("size", "" + attrs2.length);
        records.add(record);
    }
    
    public Vector<Map<String, String>> getRecords(){
        return records;
    }
    
    public void setGeometryType(String type){
        fields = fields.replaceAll("Polygon", type);
        fields = fields.replaceAll("Poline", type);
        fields = fields.replaceAll("Point", type);
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
    public String getFilepath() {
        return filepath;
    }
}
