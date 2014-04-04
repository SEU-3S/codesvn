package com.klspta.web.xuzhouNW.wpzf;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.web.xuzhouNW.wpzf.shape.Shpreader;

/**
 * 
 * <br>Title:导入卫片监测图斑类
 * <br>Description:导入卫片监测图斑
 * <br>Author:王雷
 * <br>Date:2013-8-1
 */
public class ImportWp extends AbstractBaseBean {
    //临时文件路径
    private String tempPath = getTempPath();
    
    /**
     * 
     * <br>Description:导入卫片监测图斑入口
     * <br>Author:王雷
     * <br>Date:2013-8-1
     */
    public void importWp(){
        String zipPath = getTempFile();
        if(zipPath!=null){
            File zipFile = new File(zipPath);
            String folderpath = tempPath + zipFile.getParentFile().getName();
            //解压缩
            UtilFactory.getZIPUtil().unZip(zipPath, folderpath);
            //实现上图
            upload(folderpath);
            response("{success:true}");
        }
        
    }
    
    /**
     * 
     * <br>Description:解析shape文件夹的内容，将shp上传
     * <br>Author:黎春行
     * <br>Date:2013-7-31
     * @param folderpath
     */
    private void upload(String folderpath){
    	File file = new File(folderpath);
    	File[] files = file.listFiles();
    	Calendar calender= Calendar.getInstance();
    	int year = calender.get(Calendar.YEAR);
    	String shpfilePath = "";
    	String dbffilePath = "";
    	for(int i = 0; i < files.length; i++){
    		File newFile = files[i];
    		if(newFile.getName().contains(".dbf")){
    			dbffilePath = newFile.getPath();
    		}else if (newFile.getName().contains(".shp")) {
				shpfilePath = newFile.getPath();
			}
    	}
    	//获取shape文件的所有属性信息和空间数据
    	List<Map<String, Object>> shapeList = new Shpreader(shpfilePath, dbffilePath).getShpMap();
    	String upLoadName = UtilFactory.getConfigUtil().getConfig("wpname");
    	//获取对应的srid
    	String querySrid = "select t.srid from sde.st_geometry_columns t where upper(t.table_name) = ?";
    	List<Map<String, Object>>  sridList = query(querySrid, GIS, new Object[]{upLoadName});
    	String srid = "";
    	if(sridList.size() > 0){
    		srid = String.valueOf(sridList.get(0).get("srid"));
    	}
    	//将shp文件上传到空间库当中
    	for(int i = 0; i < shapeList.size(); i++){
    		//判断对应的objectid是否存在
    		shapeList.get(i).get("OBJECTID");
    		String objectid = String.valueOf(shapeList.get(i).get("OBJECTID"));
    		String objectSql = "select t.objectid from " + upLoadName + " t where t.OBJECTID = ?"; 
    		int objectsize = query(objectSql, GIS, new Object[]{objectid}).size();
    		//删除重复的objectid
    		if(objectsize > 0){
    			String deleteSql = "delete from " + upLoadName + " t where t.OBJECTID = ?";
    			update(deleteSql, GIS, new Object[]{objectid});
    		}
    		//写入新的shape数据
    		String[] name = {"OBJECTID", "XZQDM", "XMC", "JCBH", "TBLX", "TZ", "QSX", "HSX", "XZB", "YZB", "JCMJ", "BGDL", "BGFW", "WBGLX", "SHAPE_Length"};
    		StringBuffer nameBuffer = new StringBuffer();
    		StringBuffer valueBuffer = new StringBuffer();
    		for(int j = 0; j < name.length; j++){
    			String value = String.valueOf(shapeList.get(i).get(name[j]));
    			if("".equals(value) || value == null || "null".equals(value)){
    				continue;
    			}
    			nameBuffer.append(name[j] + ",");
    			valueBuffer.append("'" + value + "',");
    		}
    		nameBuffer.append("YW_GUID,");
    		nameBuffer.append("YEAR,");
    		nameBuffer.append("SHAPE");
    		valueBuffer.append(String.valueOf(shapeList.get(i).get("JCBH")) + ",");
    		valueBuffer.append(year+",");
    		valueBuffer.append("sde.st_geometry ('" + String.valueOf(shapeList.get(i).get("geometry")) + "', '" + srid + "')");
    		String insertSql = "insert into " + upLoadName + "(" + nameBuffer.toString() + ") values ("+ valueBuffer.toString() +")"; 
    		//System.out.println(insertSql);
    		update(insertSql, GIS);
    	}
    	
    }
    
    /**
     * 
     * <br>Description:获取上传的压缩包
     * <br>Author:王雷
     * <br>Date:2013-8-1
     * @return
     */
    private String getTempFile(){
       try {
           List<String> list = UtilFactory.getFileUtil().upload(request, 0, 0);
        return list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }     
       return null;      
    }
    
    /**
     * 
     * <br>Description:获取文件临时路径
     * <br>Author:王雷
     * <br>Date:2013-8-1
     * @return
     */
    private String getTempPath(){
        String tempPath="";
        tempPath = UtilFactory.getConfigUtil().getShapefileTempPathFloder();
        return tempPath;
        
    }
    
}
