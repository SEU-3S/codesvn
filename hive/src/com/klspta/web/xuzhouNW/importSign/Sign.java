package com.klspta.web.xuzhouNW.importSign;

import java.io.File;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


import com.klspta.base.AbstractBaseBean;

public class Sign extends AbstractBaseBean {
	private static String temporaryFolder = temporaryFolder(); 
	
	public void setSign() throws Exception{
		request.setCharacterEncoding("utf-8");
		String userId = request.getParameter("userId");
		ISignManager signManager = new SignManager();
		if(ServletFileUpload.isMultipartContent(request)){
			DiskFileItemFactory dfif = new DiskFileItemFactory();
			ServletFileUpload fileUpload = new ServletFileUpload(dfif);
			List<FileItem> files = fileUpload.parseRequest(request);
			for(FileItem f:files){
				if(!f.isFormField()){ 
					String filePath = temporaryFolder  + userId;
					if(!new File(filePath).exists()){
						if(!new File(temporaryFolder).exists()){
							new File(temporaryFolder).mkdir();
						}
						new File(filePath).createNewFile();
					}
					f.write(new File(filePath));
					signManager.setSign(filePath, userId);
				}
			}
		}
		response("上传成功");
	}
	
	public void getSign(){
		
		
	}
	
	private static String temporaryFolder(){
		String classPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
		//String filePath = classPath.substring(0, classPath.lastIndexOf("WEB-INF/classes"));
		String filePath = "D:/sign/";
		return filePath;
	}
	
}
