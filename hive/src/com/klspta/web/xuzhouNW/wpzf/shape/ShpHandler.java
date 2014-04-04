package com.klspta.web.xuzhouNW.wpzf.shape;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

/**
 * 
 * <br>Title:shp文件处理类
 * <br>Description:shp文件上图功能 
 * <br>Author:黎春行
 * <br>Date:2013-7-30
 */
public class ShpHandler extends AbstractBaseBean {
	
	public void importShapefile(){
		String filePath = uploadFileToTempFloder();
		if(filePath.equals("")){
			putParameter("");
			response("error:文件为空");
			return;
		}
		String shapefileType = "";
		Map map = new HashedMap();
		
		
	}
	
	/**
	 * 
	 * <br>Description:将前台文件上传到临时文件夹
	 * <br>Author:黎春行
	 * <br>Date:2013-7-30
	 * @return
	 */
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
	
	
	
}
