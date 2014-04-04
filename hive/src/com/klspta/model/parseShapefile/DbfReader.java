package com.klspta.model.parseShapefile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.geotools.data.shapefile.dbf.DbaseFileReader;

public class DbfReader {
	
	private String dbfpath = "";
	private Charset charset = Charset.forName("GBK"); 
	
	public DbfReader(String dbfpath){
		this.dbfpath = dbfpath;
	}
	
	public DbfReader(String dbfpath, Charset charset){
		this.dbfpath = dbfpath;
		this.charset = charset;
	}
	
    public Vector<Map<String, Object>> parseDbfFile(){
    	Vector<Map<String, Object>> v = new Vector<Map<String, Object>>();
    	FileChannel in = null;
    	DbaseFileReader r = null;
		try {
			in = new FileInputStream(dbfpath).getChannel();
	    	r = new DbaseFileReader(in, true, charset);
	    	int fields = r.getHeader().getNumFields(); 
	    	while (r.hasNext()) { 
	    		Map<String, Object> hashMap = null;
	    		for (int i = 0; i < fields; i++) {
	    			hashMap = new HashMap<String, Object>();
	    			hashMap.put(r.getHeader().getFieldName(i), r.readField(i));
	    	    }
	    		v.add(hashMap);
	        }
	    	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(r != null){
				    r.close();
				}
				if(in != null){
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	return v;
    }
}
