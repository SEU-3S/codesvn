<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="java.sql.*,java.io.*"%>
<%@ page language="java" import="com.jspsmart.*"%>
<%@page import="com.klspta.base.util.UtilFactory"%>
<%@page import="com.klspta.base.util.bean.ftputil.AccessoryBean"%>
<%@page import="com.klspta.model.accessory.AccessoryOperation"%>
<%@page import="com.klspta.model.accessory.AccessoryUtil"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="com.klspta.console.user.User"%>
<jsp:useBean id="mySmartUpload" scope="page" class="com.jspsmart.upload.SmartUpload" />

<%
String path = request.getContextPath();
try{
	 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     String userID;
     if (principal instanceof User) {
         userID = ((User)principal).getUserID();
     } else {
         userID = principal.toString();
     }
	// 初始化上传组件
 	mySmartUpload.initialize(pageContext);
	mySmartUpload.upload();
	String DocID = "";
	String DocTitle = "";
	String DocType = "";
	String Docdata = "";
	String ID = "";
	String yw_guid = request.getParameter("yw_guid");
	Boolean flag = false;
	
	// 获取传到表单记录
	ID = mySmartUpload.getRequest().getParameter("id");
	DocID = mySmartUpload.getRequest().getParameter("DocID");
	DocTitle = mySmartUpload.getRequest().getParameter("DocTitle");
	DocType = mySmartUpload.getRequest().getParameter("DocType");
	String FilePath;
	com.jspsmart.upload.File myFile = null;
	myFile = mySmartUpload.getFiles().getFile(0);
	//FilePath = UtilFactory.getConfigUtil().getConfig("SHAPEFILE_PATH")+myFile.getFileName();
	String tem = new java.io.File(application.getRealPath(request.getRequestURI())).getParent();
	FilePath = (tem.substring(0,tem.lastIndexOf(path.substring(1))-1)+tem.substring(tem.lastIndexOf(path.substring(1))+6)+"/documents/documentTemporaryFolder/").replace("\\","/")+myFile.getFileName();;
	if (!myFile.isMissing()){
		myFile.saveAs(FilePath,mySmartUpload.SAVE_PHYSICAL);	
		File uploadedFile = new File(FilePath);
		if(uploadedFile.exists()) {
			  AccessoryBean accessoryBean=new AccessoryBean();
                accessoryBean.setFile_id(DocID);
                accessoryBean.setFile_name(DocTitle);
                accessoryBean.setFile_path(uploadedFile.getAbsolutePath());
                accessoryBean.setFile_type("file");
                accessoryBean.setParent_file_id("0");
                accessoryBean.setUser_id(userID);
                accessoryBean.setYw_guid(yw_guid); 
                flag = AccessoryUtil.getInstance().uploadAccessory(accessoryBean);
                if(flag){
                    uploadedFile.delete();   	//删除临时文件夹下的文件
                	out.write(flag.toString());     
                }
		}
	}	
	}catch(Exception e){
		out.clear();
		out.write("failed");
		out.flush();
	}
%>