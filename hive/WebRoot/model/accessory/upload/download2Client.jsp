<%@page language="java" contentType="application/x-msdownload"    pageEncoding="gb2312"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String file_path = request.getParameter("file_path");
	//String fileName = new String(request.getParameter("fileName").getBytes("ISO8859_1"));	//jsp中文参数传递处理
	response.setContentType("application/x-download");      
	//String filedisplay=new String(fileName.getBytes(), "ISO8859-1");
	String filedisplay=new String("附件下载.zip".getBytes(), "ISO8859-1");  	
	response.addHeader("Content-Disposition","attachment;filename=" + filedisplay);     
   	OutputStream output = null;    
   	FileInputStream in = null;    
	try{
	   output = response.getOutputStream();  
	   in=new FileInputStream(file_path);       
	   byte[] b = new byte[1024];    
	   int i = 0;    
	   while((i = in.read(b)) > 0){    
	   		output.write(b, 0, i);    
	   }    
	   output.flush(); 
	   out.clear();
	   out = pageContext.pushBody();      
	  }catch(Exception e){
	   	e.printStackTrace();
	  }finally{  
		   if(in != null)    
		    {    
		    in.close();    
		    in = null;    
		  }
	 }
%>