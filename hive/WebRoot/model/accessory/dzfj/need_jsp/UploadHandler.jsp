<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="org.apache.commons.fileupload.servlet.*"%>
<%@ page import="com.scand.fileupload.*"%>
<%@ page import="com.klspta.model.accessory.dzfj.AccessoryOperation"%>
<%@ page import="com.klspta.base.util.bean.ftputil.AccessoryBean"%>
<%@ page import="com.klspta.model.accessory.dzfj.Globals"%>
<%@ page import="java.io.*"%>
<%
request.setCharacterEncoding("utf-8"); 
String para = request.getParameter("yw_guid");
	para = new String(request.getParameter("yw_guid").getBytes("ISO-8859-1"),"utf-8");

//System.out.println(para);
//String para=request.getParameter("yw_guid");
String yw_guid=para.substring(0,para.indexOf("?"));
String fileId=para.substring(para.indexOf("sessionId=")+10,para.length());
String parent_file_id=request.getParameter("parent_file_id");
    //取得临时文件夹
    String uploadFolder = new Globals().getTemp_File_path();
    //校验上传请求
    boolean isMultipart = FileUpload.isMultipartContent(request);
    if (!isMultipart) {
        out.println("Use multipart form to upload a file!");
    } else {
        //String fileId = request.getParameter("sessionId").toString().trim();
        //创建上传句柄
        FileItemFactory factory = new ProgressMonitorFileItemFactory(request, fileId);
        ServletFileUpload upload = new ServletFileUpload(factory);
        List items = upload.parseRequest(request);
        //处理上传文件
        Iterator iter = items.iterator();
        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();
            if (item.isFormField()) {
                //TODO processFormField
            } else {
                //processUploadedFile
                String fieldName = item.getFieldName();
                String fileName = item.getName();
                int i2 = fileName.lastIndexOf("\\");
                if (i2 > -1)
                    fileName = fileName.substring(i2 + 1);
                File dirs = new File(uploadFolder);
                if (!dirs.isFile()) {
                    dirs.mkdirs();
                }
                String file_id=Globals.getGuid();
                //处理没有后缀的文件，一步一个坑啊 add by 郭 2010-11-24
                int flag=fileName.lastIndexOf(".");
                String temp=fileName.replaceFirst(fileName,file_id);
                if(flag>=0){
                    temp=fileName.replaceFirst(fileName.substring(0,fileName.lastIndexOf(".")),file_id);
                }
                File uploadedFile = new File(dirs, temp);
                item.write(uploadedFile);
                //上传临时文件夹处理完毕，开始调用FTP接口
                AccessoryBean accessoryBean=new AccessoryBean();
                accessoryBean.setFile_id(file_id);
                accessoryBean.setFile_name(fileName);
                accessoryBean.setFile_path(uploadedFile.getAbsolutePath());
                accessoryBean.setFile_type("file");
                accessoryBean.setParent_file_id(parent_file_id);
                accessoryBean.setUser_id("");
                accessoryBean.setYw_guid(yw_guid);
                AccessoryOperation.getInstance().upload(accessoryBean);
                uploadedFile.delete();
                session.setAttribute("FileUpload.Progress." + fileId, "-1");
            }
        }

    }
%>