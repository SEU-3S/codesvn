package com.klspta.model.accessory.dzfj;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.base.util.bean.ftputil.AccessoryBean;

public class AccessoryAction extends AbstractBaseBean{
	//添加一个缓存，并判断缓存地址是否存在。
	private static String  cacheLocation = "C:\\cache";
	static{
		if(!(new File(cacheLocation).exists())){
			new File(cacheLocation).mkdirs();
		}
	};
	
    /**
     * 
     * <br>Description:创建文件夹
     * <br>Author:朱波海
     * <br>Date:2012-8-7
     */
    public void createFolder() {
        String file_name =UtilFactory.getStrUtil().unescape( request.getParameter("file_name"));
        file_name = file_name.trim();
        String file_type = request.getParameter("file_type");
        String parent_file_id = request.getParameter("parent_file_id");
        String yw_guid = request.getParameter("yw_guid");
        String user_id = request.getParameter("user_id");
        String file_id = request.getParameter("file_id ");
        AccessoryBean accessoryBean = new AccessoryBean();
        accessoryBean.setFile_id(file_id);
        accessoryBean.setFile_name(file_name);
        accessoryBean.setFile_type(file_type);
        accessoryBean.setParent_file_id(parent_file_id);
        accessoryBean.setYw_guid(yw_guid);
        accessoryBean.setUser_id(user_id);
       AccessoryOperation.getInstance().createFolder(accessoryBean);
    }

    /**
     * 
     * <br>Description:删除操作:先删除accessory表的记录，再删除FTP服务器
     * <br>Author:朱波海
     * <br>Date:2012-8-7
     */
    
    public void deleteFile(){
        String file_id = request.getParameter("file_id");
        AccessoryBean accessoryBean = AccessoryOperation.getInstance().getAccessoryById(file_id);
        AccessoryOperation.getInstance().deleteAccessory(accessoryBean);
        if (accessoryBean.getFile_type().equals("file")) {
            AccessoryOperation.getInstance().deleteFTPFile(accessoryBean.getFile_path());
        }
    }

    /**
     * 
     * <br>Description:树形节点重命名
     * <br>Author:朱波海
     * <br>Date:2012-8-7
     */
    public void nodeRename(){

        String file_id = request.getParameter("file_id");
        String newName = UtilFactory.getStrUtil().unescape(request.getParameter("newName"));
        String sql = "update atta_accessory set file_name = ? where FILE_ID = ?";
        Object[] args = { newName, file_id };
        update(sql,CORE, args);
    }

    /**
     * 
     * <br>Description:树形节点重命名，根据树形的file_id取得对应的FILE_NAME
     * <br>Author:朱波海
     * <br>Date:2012-8-7
     */

   
    public void getNodeName() {
        String file_id = request.getParameter("file_id");
        String sql = "select t.file_name from atta_accessory t where t.file_id = ?";
        Object args[]={file_id};
        List <Map<String ,Object>> list=query(sql,CORE ,args);
        String oldName=null;
             Map<String ,Object>map=list.get(0);
             oldName=  ((String)map.get("file_name"));
        response(oldName);
    }
    /**
     电子附件全部下载
     * @throws Exception 
     */
   // public void downloadAll()  {
      //  String realPath=request.getRealPath("/")+"common//pages//accessory//download//";
      //  String yw_guid = request.getParameter("yw_guid");
       
   // }
    /**
     * 
     * <br>Description:附件下载，根据附件的file_id获取下载附件(2013-11-22修改，因点击文件时，getAccessory.jsp已将文件下载到缓存，故不必要从新下载到缓存中)
     * <br>Author:黎春行
     * <br>Date:2012-6-9
     */
    public void downLoadfile() throws Exception{
    	String file_id = request.getParameter("file_id");
    	AccessoryBean bean = new AccessoryBean();
    	bean = AccessoryOperation.getInstance().getAccessoryById(file_id);
    	String fileName = bean.getFile_name();
    	fileName = new String(fileName.getBytes(), "ISO8859-1");
    	//String file_Path = cacheLocation + "\\" + AccessoryOperation.getInstance().download(bean, cacheLocation + "\\");
    	String classPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
    	String dirpath = classPath.substring(0, classPath.lastIndexOf("WEB-INF/classes"));
    	dirpath=dirpath+"model/accessory/dzfj/download/";
    	String file_Path = dirpath + bean.getFile_path();
    	
    	FileInputStream fis = new FileInputStream(new File(file_Path));
    	int nSize = (int)fis.available();
    	byte[] data = new byte[nSize];
    	fis.read(data);
    	
		response.setContentType("application/x-msdownload");
		response.setHeader( "Content-Disposition", "attachment; filename="+ fileName + "\"");
    	response.getOutputStream().write(data);
    	response.getOutputStream().close();
    	fis.close();
    }
    
    public void emptyFiles(){
        String classPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
        String dirpath = classPath.substring(0, classPath.lastIndexOf("WEB-INF/classes"));
        dirpath=dirpath+"model/accessory/dzfj/download/";
        File file = new File(dirpath);
        if (!file.exists()) {
          return ;
        }
        if (!file.isDirectory()) {
          return ;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
           if (dirpath.endsWith(File.separator)) {
              temp = new File(dirpath + tempList[i]);
           } else {
               temp = new File(dirpath + File.separator + tempList[i]);
           }
           if (temp.isFile()) {
              temp.delete();
           }

        }
    }
}
