package com.klspta.model.accessory.dzfj;

import it.sauronsoftware.ftp4j.FTPClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.AbstractDataBaseSupport;
import com.klspta.base.util.bean.ftputil.AccessoryBean;
import com.klspta.base.util.bean.ftputil.FTPOperation;


public class AccessoryOperation extends AbstractBaseBean  {
    private static AccessoryOperation accessoryOperation;
    private AccessoryOperation() {
    }

    /**
     * 
     * <br>Description: 获取实例
     * <br>Author:朱波海
     * <br>Date:2012-8-7
     * @return
     */
    public static AccessoryOperation getInstance() {
        if (accessoryOperation == null) {
            accessoryOperation = new AccessoryOperation();
        }
        return accessoryOperation;
    }
    public List<AccessoryBean> getAccessorylistByYwGuid(String yw_guid) {
        String sql = "select * from atta_accessory t where t.yw_guid =? order by t.file_type desc";
        
     Object args[] = {yw_guid};
   	List <AccessoryBean> ls=new ArrayList<AccessoryBean>();
     List<Map<String, Object>> list = query(sql, CORE, args);
        for(int i=0;i<list.size();i++){
         AccessoryBean accessoryBean = new AccessoryBean();
          Map<String, Object>	map=list.get(i);
          accessoryBean.setFile_id((String)map.get("file_id"));
          accessoryBean.setFile_name((String)map.get("file_name"));
          accessoryBean.setFile_path((String)map.get("file_path"));
          accessoryBean.setFile_type((String)map.get("file_type"));
          accessoryBean.setParent_file_id((String)map.get("parent_file_id"));
          accessoryBean.setUser_id((String)map.get("user_id")); 
          accessoryBean.setYw_guid((String)map.get("yw_guid"));
          
        	ls.add(accessoryBean);
        }
        
        return ls;
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


    public boolean createFolder(AccessoryBean accessoryBean) {
        insertRecord(accessoryBean);
        return true;
    }

    /**
     * 
     * <br>Description:往Accessory表中插入记录。调用之前请确保所有必填项非空，若file_id未生成，请先生成再调用
     * <br>Author:朱波海
     * <br>Date:2012-8-7
     * @param accessoryBean
     */
    
    private void insertRecord(AccessoryBean accessoryBean) {
        String parent_file_id = accessoryBean.getParent_file_id();
        String file_type = accessoryBean.getFile_type();
        String file_name = accessoryBean.getFile_name();
        String file_path = accessoryBean.getFile_path();
        String yw_guid = accessoryBean.getYw_guid();
        String user_id = accessoryBean.getUser_id();
     //   String file_id = accessoryBean.getFile_id();
        String sql = "insert into atta_accessory( parent_file_id, file_type, file_name, file_path, yw_guid,user_id) values(?,?,?,?,?,?)";
        Object[] args = {  parent_file_id, file_type, file_name, file_path, yw_guid, user_id };
        update(sql,CORE, args);
    }

    /**
     * 
     * <br>Description:  根据AccessoryBean删除accessory表的记录
     * <br>Author:朱波海
     * <br>Date:2012-8-7
     * @param accessoryBean
     */
    public void deleteAccessory(AccessoryBean accessoryBean) {
        String file_id = accessoryBean.getFile_id();
        String file_type = accessoryBean.getFile_path();
        if (file_type != null && "file".equals(file_type)) {
            deleteFTPFile(accessoryBean.getFile_path());
        }
        String sql = "delete from  atta_accessory where file_id=?";
        Object[] args = { file_id };
        update(sql,AbstractDataBaseSupport.CORE, args);
    }

    /**
     * 
     * <br>Description:  根据file_id获取bean
     * <br>Author:朱波海
     * <br>Date:2012-8-7
     * @param file_id
     * @return
     */
    public AccessoryBean getAccessoryById(String file_id) {
        String sql = "select * from atta_accessory where file_id=?";
        Object args[]={file_id};
        
        List<Map<String, Object>> list = query(sql,CORE,args);
        
        List <AccessoryBean> ls=new ArrayList<AccessoryBean>();
        for(int i=0;i<list.size();i++){
          AccessoryBean accessoryBean = new AccessoryBean();
          Map<String, Object>	map=list.get(i);
          accessoryBean.setFile_id((String)map.get("file_id"));
          accessoryBean.setFile_name((String)map.get("file_name"));
          accessoryBean.setFile_path((String)map.get("file_path"));
          accessoryBean.setFile_type((String)map.get("file_type"));
          accessoryBean.setParent_file_id((String)map.get("parent_file_id"));
          accessoryBean.setUser_id((String)map.get("user_id")); 
          accessoryBean.setYw_guid((String)map.get("yw_guid"));
          
        	ls.add(accessoryBean);
        
        }if (ls.size() == 1) {
            return (AccessoryBean) ls.get(0);
        }else{ 
        	
        	return null;}
       
    }

    /**
     * 
     * <br>Description:  通过file_path获得AccessoryBean
     * <br>Author:朱波海
     * <br>Date:2012-8-7
     * @param file_path
     * @return
     */

   
    public AccessoryBean getAccessoryByFile_path(String file_path) {
        String sql = "select * from atta_accessory where file_path=?";
        Object args[]={file_path};
        List<Map<String, Object>> list = query(sql,CORE,args);
         List <AccessoryBean> ls=new ArrayList<AccessoryBean>();
         for(int i=0;i<list.size();i++){
           AccessoryBean accessoryBean = new AccessoryBean();
           Map<String, Object>	map=list.get(i);
           accessoryBean.setFile_id((String)map.get("file_id"));
           accessoryBean.setFile_name((String)map.get("file_name"));
           accessoryBean.setFile_path((String)map.get("file_path"));
           accessoryBean.setFile_type((String)map.get("file_type"));
           accessoryBean.setParent_file_id((String)map.get("parent_file_id"));
           accessoryBean.setUser_id((String)map.get("user_id")); 
           accessoryBean.setYw_guid((String)map.get("yw_guid"));
           
         	ls.add(accessoryBean);
       
            } if (ls.size() == 1) {
            return (AccessoryBean) ls.get(0);
            }else{ 
            	
            	return null ;}
       
    }

    /**
     * 
     * <br>Description:调用FTP进行附件的上传，上传成功后更改file_path为FTP保存绝对路径
     * <br>Author:朱波海
     * <br>Date:2012-8-7
     * @param accessoryBean
     * @return
     * @throws Exception
     */
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
            // 直接抛出，由调用者进行exception的处理 
            //e.printStackTrace();
            throw e;
        }
    }

    /**
     * 
     * <br>Description:  根据FTP文件路径，在服务器上进行删除
     * <br>Author:朱波海
     * <br>Date:2012-8-7
     * @param FTPFile_path
     */
 
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
     * 
     * <br>Description:下载附件 根据bean的file_id查询FTP保存路径，不读取bean中的file_path
     * <br>Author:朱波海
     * <br>Date:2012-8-7
     * @param accessoryBean
     * @param realPath
     * @return
     */
    public String download(AccessoryBean accessoryBean, String realPath) {
        String file_id = accessoryBean.getFile_id();
        if (file_id == null || "".equals(file_id)) {
            return null;
        }
        String file_type = accessoryBean.getFile_type();
        if (file_type == null || "folder".equals(file_type)) {
            return null;
        }
        /*根据file_id重置bean，屏蔽脏数据*/
        accessoryBean = this.getAccessoryById(file_id);
        String file_path = accessoryBean.getFile_path();
        FTPClient client = FTPOperation.getInstance().getFTPClient();
        File file = new File(realPath);
        //删除旧文件
        //deleteFiles(file);
        file.mkdirs();
        file = new File(realPath + accessoryBean.getFile_path());
        //当缓存存在时，且文件大于5M时，不再执行下载，不进行缓存清理
        if(!file.exists() || file.length() < 5 * 1024 * 1024){
	        try {
	            client.download(file_path, file);
	            client.disconnect(true);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
    	}
        
        return accessoryBean.getFile_path();

    }

    /**
     * 
     * <br>Description: 循环删除文件夹及下属子文件
     * <br>Author:朱波海
     * <br>Date:2012-8-7
     * @param file
     */
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
        File directory = new File(directoryPath);
        //文件夹不存在或非文件夹
        if ((!directory.exists()) || (!directory.isDirectory())) {
            System.out.println("文件夹路径不存在：" + directoryPath);
            return false;
        }
        File[] files = directory.listFiles();
        //循环处理
        AccessoryBean accessoryBean;
        if (isDirectoryUpload) {
            //创建文件夹
            accessoryBean = new AccessoryBean();
           // String fileId = Globals.getGuid();
            String fileId="sss";
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
             //   String fileId = Globals.getGuid();
                
                String fileId="sss";
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
                filePath = path + fileName.replaceFirst(fileName.substring(0, pro), "sss");
                File newFile = new File(filePath);
                files[i].renameTo(newFile);

                accessoryBean = new AccessoryBean();
                accessoryBean.setYw_guid(yw_guid);
                accessoryBean.setFile_name(fileName);
                accessoryBean.setFile_id("ssss");
                accessoryBean.setParent_file_id(parentId);
                accessoryBean.setFile_type("file");
                accessoryBean.setFile_path(filePath);
                    upload(accessoryBean);
            }
        }
        return true;
    }
    public void download(String yw_guid, String realPath,boolean isZip) throws Exception {
        FTPClient client = FTPOperation.getInstance().getFTPClient();
        File file_root = new File(realPath + "/" + yw_guid + "/");
        deleteFiles(file_root);
        file_root.mkdirs();
        String sql="select t.* from atta_accessory t where yw_guid ='"+yw_guid+"'";
        List<Map<String,Object> >list =query(sql, AbstractDataBaseSupport.CORE);
       List < AccessoryBean>ls = new ArrayList< AccessoryBean>();
        for(int i=0;i<list.size();i++){
            AccessoryBean accessoryBean = new AccessoryBean();
             Map<String, Object>	map=list.get(i);
             accessoryBean.setFile_id((String)map.get("file_id"));
             accessoryBean.setFile_name((String)map.get("file_name"));
             accessoryBean.setFile_path((String)map.get("file_path"));
             accessoryBean.setFile_type((String)map.get("file_type"));
             accessoryBean.setParent_file_id((String)map.get("parent_file_id"));
             accessoryBean.setUser_id((String)map.get("user_id")); 
             accessoryBean.setYw_guid((String)map.get("yw_guid"));
             
           	ls.add(accessoryBean);}
        
        for(int i=0;i<ls.size();i++){
            AccessoryBean aBean=( AccessoryBean )ls.get(i);
            String file_type=aBean.getFile_type();
            String file_id=aBean.getFile_id();
//            String parent_file_id=aBean.getParent_file_id();
            String file_name=aBean.getFile_name();
            String file_path=aBean.getFile_path();
            
            if("file".equals(file_type)){//如果是文件，调用FTP写磁盘
                String filePath=file_root.getAbsolutePath()+"/"+getFilePath(file_id,"",ls);
                String fileDirectory=filePath.substring(0, filePath.lastIndexOf(file_name)-1);
                File files = new File(fileDirectory);
                if(!files.exists()){
                    files.mkdir();
                }
                client.download(file_path, new File(filePath));
                
            }else if ("folder".equals(file_type)) {//如果是文件夹，创建文件夹
                String filePath=getFilePath(file_id,"",ls);
                File files = new File(file_root.getAbsolutePath()+"/"+filePath);
                if(!files.isDirectory()){
                    files.mkdirs();
                }
            }
            
        }
        if(isZip){
        //    UtilFactory.getZIPUtil().zip(file_root+".zip", realPath+yw_guid);
        }
    }
    /**
     * 
     * <br>Description:根据文件编号，采用递归调用，获取附件实际路径
     * <br>Author:朱波海
     * <br>Date:2012-8-7
     * @param file_id
     * @param file_path
     * @param list
     * @return
     */
    private String getFilePath(String file_id,String file_path,List<AccessoryBean> list){
        for(AccessoryBean aBean: list){
            //根据file_id获取parent_file_id和file_name
            if(file_id.equals(aBean.getFile_id())){
                if(file_path.length()>0){
                    file_path=aBean.getFile_name()+"/"+file_path;
                }else{
                    file_path=aBean.getFile_name();
                }
                //判断parent_file_id，如果为0，追加file_name后返回
                if(!"0".equals(aBean.getParent_file_id())){
                    file_path=getFilePath(aBean.getParent_file_id(),file_path,list);
                }
                break;
            }
        }
        
        
        return file_path;
    }

    public boolean isHaveAccessory(String yw_guid) {
        String sql = "select * from atta_accessory where yw_guid=? and file_type='file'";
        Object args[]={yw_guid};
        List<Map<String, Object>> list = query(sql,CORE,args);
        if (list.size() != 0) {
            return true;
        }
        return false;
    }
    
    public String  getFilePathByywguid(String yw_guid){
        String filepath = "";
        String sql = "select file_path from atta_accessory where yw_guid=? and file_type='file'";
        List<Map<String, Object>> list = query(sql, CORE, new Object[]{yw_guid});
        filepath = list.get(0).get("file_path").toString();
        return filepath;
    }
    
    public String  getFilePathByywguid1(String yw_guid){
        String filepath = "";
        String sql = "select file_path from atta_accessory where yw_guid=? and file_type='file'";
        List<Map<String, Object>> list = query(sql, CORE, new Object[]{yw_guid});
        filepath = list.get(1).get("file_path").toString();
        return filepath;
    }
    
    public String  getFilePathByywguid2(String yw_guid){
        String filepath = "";
        String sql = "select file_path from atta_accessory where yw_guid=? and file_type='file'";
        List<Map<String, Object>> list = query(sql, CORE, new Object[]{yw_guid});
        filepath = list.get(2).get("file_path").toString();
        return filepath;
    }
    
}
