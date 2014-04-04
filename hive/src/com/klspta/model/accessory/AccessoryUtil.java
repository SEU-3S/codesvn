package com.klspta.model.accessory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.base.util.bean.ftputil.AccessoryBean;
public class AccessoryUtil extends AbstractBaseBean {
    private static AccessoryUtil accessoryUtil;

    public AccessoryUtil() {
    
    }

    public static AccessoryUtil getInstance() {
        if (accessoryUtil == null) {
        	accessoryUtil = new AccessoryUtil();
        }
        return accessoryUtil;
    }
    
    /**
     * <br>Description: 附件上传
     * <br>Author:李如意
     * <br>Date:2012-8-16
     * @param accessoryBean
     * @return
     */
    public boolean uploadAccessory(AccessoryBean accessoryBean){
    	Boolean flag = UtilFactory.getFtpUtil().uploadFile(accessoryBean.getFile_path());
    	if(flag){
    		AccessoryOperation.getInstance().insertRecord(accessoryBean);
    	}
		return flag;
    }
    
    
    /**
     * <br>Description: 文件下载
     * <br>Author:李如意
     * <br>DateTime:2012-8-24 下午09:41:12
     */
    public void downloadAccessory(){
    	String yw_guid = request.getParameter("yw_guid");
    	String selectFiles = UtilFactory.getStrUtil().unescape(request.getParameter("ids"));
    	String[] fileNames = selectFiles.split("//");
    	String uploadTempFolder = request.getParameter("uploadTempFolder");
    	Boolean flag = false;
    	
    	String zipPath = null;
    	String tempPath =  uploadTempFolder+yw_guid+"/";
        File dirs = new File(uploadTempFolder+yw_guid);
        if (!dirs.isFile()) {
            dirs.mkdirs();
        }
    	for(int i=0;i<fileNames.length;i++){
    		String fileName  = fileNames[i].split("/")[1];	
    		String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
    		String ftpFileName = fileNames[i].split("/")[0]+fileSuffix;
    		flag = UtilFactory.getFtpUtil().downloadFile(ftpFileName, tempPath+fileName);
    	}
    	//将要下载的文件名称修改成原来的名称放到指定文件夹下进行压缩下载
    	UtilFactory.getZIPUtil().zip(uploadTempFolder+yw_guid+".zip",tempPath);
    	//返回压缩文件的路径
    	response(uploadTempFolder+yw_guid+".zip"); 
    }
    
    /**
     * <br>Description: 文件删除
     * <br>Author:李如意
     * <br>DateTime:2012-8-24 下午09:40:41
     * @return
     */
    public boolean deleteAccessory(){
    	String yw_guid = request.getParameter("yw_guid");
    	String ids = UtilFactory.getStrUtil().unescape(request.getParameter("ids"));
    	String[] fileNames = ids.split("//");
    	Boolean flag = false;
    	for(int i=0;i<fileNames.length;i++){
    		String fileid = fileNames[i].split("/")[0];
    		String fileName = fileNames[i].split("/")[1];
        	String ftpFileName = fileid + fileName.substring(fileName.lastIndexOf("."));	//获取文件id
        	flag = UtilFactory.getFtpUtil().deleteFile(ftpFileName); 
        	if(flag){
        		AccessoryOperation.getInstance().deleteFileRecord(fileid,yw_guid);
        	}
    	}
    	return flag;
    }
    
    /**
     * <br>Description: 根据yw_guid获取附件列表，Extjs展现
     * <br>Author:李如意
     * <br>DateTime:2012-8-24 下午07:41:19
     * @param yw_guid
     * @return
     */
    public String getAccessorylistByYwGuid(String yw_guid){
        List<List<Object>> allRows = new ArrayList<List<Object>>();
        List<Map<String,Object>> rows = AccessoryOperation.getInstance().getAccessorylistByYwGuid(yw_guid);
        for (int i = 0; i < rows.size(); i++) {
            List<Object> oneRow = new ArrayList<Object>();
            Map<String, Object> map = (Map<String, Object>) rows.get(i);
            oneRow.add(map.get("FILE_ID"));
            oneRow.add(map.get("FILE_NAME"));
            oneRow.add(i);
            allRows.add(oneRow);
        }
        return JSONArray.fromObject(allRows).toString();
    }
    
    /**
     * <br>Description:删除临时文件夹下的文件
     * <br>Author:李如意
     * <br>Date:2012-7-2
     * @param fileTempPath
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public boolean deleteTempFile(String fileTempPath){
    	File file = new File(fileTempPath); 
    	if(file.exists()) {
    		file.delete();
    		return true;
    	}
    	return false;
    }

}
 