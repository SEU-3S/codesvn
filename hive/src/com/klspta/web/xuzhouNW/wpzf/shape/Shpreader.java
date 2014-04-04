package com.klspta.web.xuzhouNW.wpzf.shape;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.geotools.data.shapefile.ShpFiles;
import org.geotools.data.shapefile.dbf.DbaseFileHeader;
import org.geotools.data.shapefile.dbf.DbaseFileReader;
import org.geotools.data.shapefile.shp.ShapefileHeader;
import org.geotools.data.shapefile.shp.ShapefileReader;


public class Shpreader {
	private String shpfilePath = "";
	private String dbffilePath = "";
	private ShpFiles shp;
	public Shpreader(String shpfilePath, String dbffilePath){
		this.shpfilePath = shpfilePath;
		this.dbffilePath = dbffilePath;
		try {
			shp = new ShpFiles(shpfilePath);
		} catch (MalformedURLException e) {
			// error(this, "geotools类型转换失败，请确定shp文件正确上传");
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * <br>Description:读取dbf和shp文件，获取shape属性
	 * <br>Author:黎春行
	 * <br>Date:2013-7-31
	 * @return
	 */
	public List<Map<String, Object>> getShpMap(){
		List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
		try {
			FileChannel in = new FileInputStream(dbffilePath).getChannel();
			ShapefileReader shapefileReader= new ShapefileReader(shp, false, false);
			
			ShapefileHeader sHeader = shapefileReader.getHeader();
			DbaseFileReader dReader = new DbaseFileReader(in, true, Charset.defaultCharset());
			DbaseFileHeader dHeader = dReader.getHeader();
			int fields = dHeader.getNumFields();
			while (dReader.hasNext() && shapefileReader.hasNext() ) {
				DbaseFileReader.Row row = dReader.readRow();
				Map<String, Object> returnMap = new TreeMap<String, Object>();
				for(int i = 0; i < fields; i++){
					Object data = row.read(i);
					String name = dHeader.getFieldName(i);
					returnMap.put(name, data);
				}
				returnMap.put("geometry", shapefileReader.nextRecord().shape());
				returnList.add(returnMap);
			}
			dReader.close();
			shapefileReader.close();
			return returnList;
		} catch (FileNotFoundException e) {
			//error(this, "数据流转换失败");
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//error(this, "文件读取失败");
			e.printStackTrace();
		}
		return null;
	}
}
