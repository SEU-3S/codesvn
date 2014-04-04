package com.klspta.model.accessory;

import it.sauronsoftware.ftp4j.FTPClient;
import java.io.File;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.klspta.base.AbstractBaseBean;
import com.klspta.base.AbstractDataBaseSupport;
import com.klspta.base.util.bean.ftputil.AccessoryBean;
import com.klspta.base.util.bean.ftputil.FTPOperation;



public class AccessoryOperation extends AbstractBaseBean {
    private static AccessoryOperation accessoryOperation;
  
    private AccessoryOperation() {}

    public static AccessoryOperation getInstance() {
        if (accessoryOperation == null) {
            accessoryOperation = new AccessoryOperation();
        }
        return accessoryOperation;
    }
    
    public List<AccessoryBean> getAccessorylist(String yw_guid) {
    	List<AccessoryBean> accList=new ArrayList<AccessoryBean>();
    	AccessoryBean acc=null;
        List<Map<String,Object>> list=getAccessorylistByYwGuid(yw_guid);
        if(list!=null&&list.size()>0){
        	for(int i=0;i<list.size();i++){
        		acc=new AccessoryBean();
        		Map<String,Object> map=list.get(i);
        		acc.setFile_id((String)map.get("FILE_ID"));
        		acc.setFile_name((String)map.get("FILE_NAME"));
        		acc.setParent_file_id((String)map.get("PARENT_FILE_ID"));
        		accList.add(acc);
        	}
        }
        return accList;
    }
    
    public String download(AccessoryBean accessoryBean, String realPath) {
        String file_id = accessoryBean.getFile_id();
        if (file_id == null || "".equals(file_id)) {
            return null;
        }
        String file_type = accessoryBean.getFile_type();
        if (file_type == null || "folder".equals(file_type)) {
            return null;
        }
        /*根据file_id重置bean，屏蔽脏数据 add by 郭 2010-11-24*/
        accessoryBean = this.getAccessoryById(file_id);
        String file_path = accessoryBean.getFile_path();
        FTPClient client = FTPOperation.getInstance().getFTPClient();
        File file = new File(realPath);
        //删除旧文件
        deleteFiles(file);
        file.mkdirs();
        file = new File(realPath + accessoryBean.getFile_path());
        try {
            client.download(file_path, file);
            client.disconnect(true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //TODO
        }
        return accessoryBean.getFile_path();

    }
    
    public AccessoryBean getAccessoryById(String file_id) {
    	AccessoryBean acc=new AccessoryBean();
        String sql = "select * from atta_accessory where file_id=?";
        Object[] args = { file_id };
        List<Map<String,Object>> mapList=query(sql,CORE,args);
        if(mapList!=null&&mapList.size()>0){
        	Map<String,Object> map=mapList.get(0);
        	acc.setFile_id((String)map.get("FILE_ID"));
        	acc.setFile_name((String)map.get("FILE_NAME"));
        	acc.setFile_path((String)map.get("FILE_PATH"));
        	acc.setFile_type((String)map.get("FILE_TYPE"));
        	acc.setParent_file_id((String)map.get("PARENT_FILE_ID"));
        	acc.setUser_id((String)map.get("USER_ID"));
        	acc.setYw_guid((String)map.get("YW_GUID"));
        }
        return acc;
    }
    
    public AccessoryBean getAccessoryByFile_path(String file_path) {
    	AccessoryBean acc=new AccessoryBean();
        String sql = "select * from atta_accessory where file_path=?";
        Object[] args = { file_path };
        List<Map<String,Object>> mapList=query(sql,CORE,args);
        if(mapList!=null&&mapList.size()>0){
        	Map<String,Object> map=mapList.get(0);
        	acc.setFile_id((String)map.get("FILE_ID"));
        	acc.setFile_name((String)map.get("FILE_NAME"));
        	acc.setFile_path((String)map.get("FILE_PATH"));
        	acc.setFile_type((String)map.get("FILE_TYPE"));
        	acc.setParent_file_id((String)map.get("PARENT_FILE_ID"));
        	acc.setUser_id((String)map.get("USER_ID"));
        	acc.setYw_guid((String)map.get("YW_GUID"));
        }
        return acc;
    }
    
    private void deleteFiles(File file) {
        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            file.delete();
        }else{
        for (File fil : files) {
            deleteFiles(fil);
        }
        file.delete();
        }
    }
    
    
    public boolean uploadDirectory(String directoryPath, String yw_guid, String parentId,
            boolean isDirectoryUpload) throws Exception {
        // TODO Auto-generated method stub
        
        File directory = new File(directoryPath);
        //文件夹不存在或非文件夹
        if ((!directory.exists()) || (!directory.isDirectory())) {
            System.out.println("文件夹路径不存在：" + directoryPath);
            return false;
        }
        File[] files = directory.listFiles();
        //循环处理
        File file;
        AccessoryBean accessoryBean;
        if (isDirectoryUpload) {
            //创建文件夹
            accessoryBean = new AccessoryBean();
            String fileId = new UID().toString().replaceAll(":", "-");
            accessoryBean.setFile_id(fileId);
            accessoryBean.setYw_guid(yw_guid);
            accessoryBean.setParent_file_id(parentId);
            accessoryBean.setFile_type("folder");
            accessoryBean.setFile_name(directoryPath.substring(directoryPath.lastIndexOf("\\") + 1,
                    directoryPath.length()));
            createFolder(accessoryBean);
            //更改parentId
            parentId = fileId;

        }
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                //创建文件夹
                accessoryBean = new AccessoryBean();
                String fileId = new UID().toString().replaceAll(":", "-");
                accessoryBean.setFile_id(fileId);
                accessoryBean.setYw_guid(yw_guid);
                accessoryBean.setParent_file_id(parentId);
                accessoryBean.setFile_type("folder");
                String absolutePath = files[i].getAbsolutePath();
                accessoryBean.setFile_name(absolutePath.substring(absolutePath.lastIndexOf("\\") + 1,
                        absolutePath.length()));
                createFolder(accessoryBean);
                //递归
                uploadDirectory(files[i].getAbsolutePath(), yw_guid, fileId, false);
            } else if (files[i].isFile()) {
                String filePath = files[i].getAbsolutePath();
                String path = filePath.substring(0, filePath.lastIndexOf("\\") + 1);
                String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.length());
                fileName = fileName.replaceAll("\\(", "-");
                fileName = fileName.replaceAll("\\)", "-");
                int pro = fileName.lastIndexOf(".");
                if (pro < 0) {
                    pro = fileName.length();
                }
                filePath = path + fileName.replaceFirst(fileName.substring(0, pro), new UID().toString().replaceAll(":", "-"));
                File newFile = new File(filePath);
                files[i].renameTo(newFile);

                accessoryBean = new AccessoryBean();
                accessoryBean.setYw_guid(yw_guid);
                accessoryBean.setFile_name(fileName);
                accessoryBean.setFile_id(new UID().toString().replaceAll(":", "-"));
                accessoryBean.setParent_file_id(parentId);
                accessoryBean.setFile_type("file");
                accessoryBean.setFile_path(filePath);
                upload(accessoryBean);
            }
        }
        return true;
    }
    
    public boolean createFolder(AccessoryBean accessoryBean) {
        insertRecord(accessoryBean);
        return true;
    }
    
    public boolean upload(AccessoryBean accessoryBean) {
        try {
            accessoryBean = uploadAccessory(accessoryBean);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        insertRecord(accessoryBean);
        return true;
    }
    
    private AccessoryBean uploadAccessory(AccessoryBean accessoryBean) throws Exception {
        FTPClient client = FTPOperation.getInstance().getFTPClient();
        String file_path = accessoryBean.getFile_path();
        try {
            File file = new File(file_path);
            //上传
            client.upload(file);
            client.disconnect(false);
            //回写FTP保存的路径
            accessoryBean.setFile_path(file_path.substring(file_path.lastIndexOf("\\") + 1));
            return accessoryBean;
        } catch (Exception e) {
            // 直接抛出，由调用者进行exception的处理 add by 郭 2010-11-23
            //e.printStackTrace();
            throw e;
        }
    }
    
    public void deleteAccessory(AccessoryBean accessoryBean) {
        String file_id = accessoryBean.getFile_id();
        String file_type = accessoryBean.getFile_path();
        if (file_type != null && "file".equals(file_type)) {
            deleteFTPFile(accessoryBean.getFile_path());
        }
        String sql = "delete from  atta_accessory where file_id=?";
        Object[] args = { file_id };
        update(sql,CORE, args);
    }
    
    public void deleteFTPFile(String FTPFile_path) {
        FTPClient client = FTPOperation.getInstance().getFTPClient();
        try {
            client.deleteFile(FTPFile_path);
            client.disconnect(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * <br>Description: 根据yw_guid获取附件
     * <br>Author:李如意
     * <br>Date:2012-6-30
     * @param accessoryBean
     * @return
     */
    public List<Map<String,Object>> getAccessorylistByYwGuid(String yw_guid) {
        String sql = "select t.FILE_ID,t.PARENT_FILE_ID,t.FILE_NAME from ATTA_ACCESSORY t where yw_guid=?  order by t.file_id asc ";
        Object[] args = { yw_guid };
        List<Map<String,Object>> listmap = query(sql,AbstractDataBaseSupport.CORE,args);
        return listmap;
    }

    /**
     * <br>Description: 将附件信息保存到数据库
     * <br>Author:李如意
     * <br>Date:2012-7-1
     * @param accessoryBean
     * @return
     */
    public boolean insertRecord(AccessoryBean accessoryBean) {
    	boolean flag = false;
        String parent_file_id = accessoryBean.getParent_file_id();
        String file_type = accessoryBean.getFile_type();
        String file_name = accessoryBean.getFile_name();
        String file_path = accessoryBean.getFile_path();
        String yw_guid = accessoryBean.getYw_guid();
        String user_id = accessoryBean.getUser_id();
        String file_id = accessoryBean.getFile_id();
        
        String sql0 = "select * from ATTA_ACCESSORY t where t.file_id = ? and t.yw_guid = ? ";
        Object[] args0 = { file_id,yw_guid};
        List<?> list = query(sql0, AbstractDataBaseSupport.CORE, args0);
        if(list.size() < 1){
            String sql = "insert into ATTA_ACCESSORY(file_id, parent_file_id, file_type, file_name, file_path, yw_guid,user_id) values(?,?,?,?,?,?,?)";
            Object[] args = { file_id, parent_file_id, file_type, file_name, file_path, yw_guid, user_id };
            int i = update(sql,AbstractDataBaseSupport.CORE, args);
            if(i>0){
            	flag = true;
            }	
        }
        return flag;
    }
    
    /**
     * <br>Description: 删除附件时，一并删除数据库中相应的信息
     * <br>Author:李如意
     * <br>Date:2012-7-1
     * @param fileid
     * @return
     */
    public boolean deleteFileRecord(String fileid,String yw_guid){
    	boolean flag = false;
    	String sql = "delete from ATTA_ACCESSORY t where t.file_id = ? and t.yw_guid=?";
    	Object[] args = { fileid,yw_guid};
    	int i = update(sql,AbstractDataBaseSupport.CORE, args);
    	if(i>0){
    		flag = true;
    	}
    	return flag;
    }
    
}
