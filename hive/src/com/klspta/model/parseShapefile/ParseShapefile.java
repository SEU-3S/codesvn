package com.klspta.model.parseShapefile;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.stereotype.Component;
import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

/**
 * 需要在conf下的applicationContext-bean.xml中，增加配置信息： <bean name="simpleExample"
 * class="com.klspta.model.SimpleExample" scope="prototype"/>
 * 
 * @author wang
 * 
 */
@Component
public class ParseShapefile extends AbstractBaseBean {
    
    DecimalFormat a = new DecimalFormat("#.00");
    
    public void parseShapefile() {
        String filepath = uploadFileToTempFloder();
        if(filepath.equals("")){
            putParameter("");
            response();
            return;
        }
//      Vector<String> filepaths = UtilFactory.getZIPUtil().unzip(filepath, UtilFactory.getConfigUtil().getShapefileTempPathFloder() + "$" + System.currentTimeMillis());
        String shapefileType="";
        Map map = new HashMap();
        Vector<Vector<Vector<String>>> geometry = new Vector<Vector<Vector<String>>>();
            if((filepath.toLowerCase()).endsWith(".shp")){
                ShpReader sr = new ShpReader(filepath);
                map = sr.parseShapefile();
                shapefileType=sr.getShapefileType();
            }/*else if(tempname.endsWith(".dbf")){
                DbfReader dr = new DbfReader(tempname);
                dr.parseDbfFile();
            }*/
        Map<String, Object> hashmap = new HashMap<String, Object>();
        hashmap.put("success", true);
        hashmap.put("geo", map.get("geo"));
        hashmap.put("pro", map.get("pro"));
        hashmap.put("shapefileType", shapefileType);
        putParameter(hashmap);
        response();
    }
    
    private String uploadFileToTempFloder(){
        String filepath = "";
        UtilFactory.getConfigUtil().getShapefileTempPathFloder();
        try {
            ServletRequestContext ctx = new ServletRequestContext(request);
            boolean isMultiPart = FileUpload.isMultipartContent(ctx);// 必须是multi的表单模式才行
            if (isMultiPart) {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setSizeThreshold(10000 * 1024); // 设置保存到内存中的大小：10M
                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setSizeMax(1024 * 1024 * 1024);// 设置最大上传文件的大小1GB
                List<?> items = upload.parseRequest(ctx);// 解析请求里的上传文件单元
                if (items != null && items.size() > 0) {
                    Iterator<?> it = items.iterator();
                    while (it.hasNext()) {
                        FileItem item = (FileItem) it.next();
                        boolean isForm = item.isFormField();// 是否是表单域
                        if (!isForm) {// 如果不适表单域，则是文件上传
                            String fileName = item.getName();// 获取上传的文件名
                            if(!fileName.equals("")){
                                if(!(fileName.toLowerCase()).endsWith("shp")){
                                    return "";
                                }
                                File shpfile = new File(UtilFactory.getConfigUtil().getShapefileTempPathFloder() + System.currentTimeMillis() + ".shp");
                                item.write(shpfile);// 上传文件
                                filepath = shpfile.getPath();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filepath;
    }
    
   
    
    private String getArea(String wkt){
        String sql = "select sde.st_area(sde.st_geometry(?, 11)) area from wpzfjc tt where rownum = 1";
        String area = "0";
        double aread = 0;
        try {
            List<Map<String,Object>> list = query(sql, GIS, new Object[]{wkt});
            if(list.size()>0){
            	Map<String,Object> map=list.get(0);
            	 area = (String)map.get("area");
                 aread = Double.parseDouble(area);
                 if(aread > 666.667){
                     return (a.format(aread / 666.667)) + "亩";
                 }else{
                     return (a.format(aread)) + "平方米";
                 }
            }
        } catch (Exception e) {
            return "-1";
        }
        return area;
    }
}
