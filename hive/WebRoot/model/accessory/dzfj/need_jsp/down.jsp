<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import= "java.io.* "%>
<%@page import="com.klspta.model.accessory.dzfj.AccessoryOperation" %>
<%@page import="com.klspta.base.util.bean.ftputil.AccessoryBean" %>
<%

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String filePath = request.getParameter("file_path");
String hzm=filePath.substring(filePath.lastIndexOf(".")+1,filePath.length());
String flag = request.getParameter("flag");
int a = filePath.lastIndexOf("/");
filePath = filePath.substring(a+1);
AccessoryBean bean = new AccessoryBean();
bean = AccessoryOperation.getInstance().getAccessoryByFile_path(filePath);
String fileName = bean.getFile_name();
fileName = new String(fileName.getBytes(), "ISO8859-1");
if(!flag.equals("0")){
response.setContentType("application/x-download");  
response.addHeader("Content-Type:text/html"," charset=utf-8");
response.addHeader("content-type","application/x-msdownload");
response.addHeader("Content-Disposition","attachment;filename="+fileName+"\"");
}
String classPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
String dirpath = classPath.substring(0, classPath.lastIndexOf("WEB-INF/classes"));
dirpath=dirpath+"model/accessory/dzfj/download/";
String mp4filePath = classPath.substring(0, classPath.lastIndexOf("WEB-INF/classes"))+"model/accessory/dzfj/need_jsp/WMP.exe";
if((dirpath+filePath).endsWith("mp4") || (dirpath+filePath).endsWith("mp3")|| (dirpath+filePath).endsWith("3gpp")){
	//处理视频、音频
	StringBuffer bodyBuffer = new StringBuffer();
	bodyBuffer.append("<object id=\"Player\" width=95% height=500 classid=\"CLSID:6BF52A52-394A-11D3-B153-00C04F79FAA6\">");
	bodyBuffer.append("<param name=\"URL\" value=\"").append(basePath).append("/model//accessory//dzfj//download//").append(filePath).append("\"");
	bodyBuffer.append("<param name=\"autoStart\" value=\"1\">");
	bodyBuffer.append("<param name=\"balance\" value=\"0\">");
	bodyBuffer.append("<param name=\"baseURL\" value>");
	bodyBuffer.append("<param name=\"captioningID\" value>");
	bodyBuffer.append("<param name=\"currentPosition\" value=\"0\">");
	bodyBuffer.append("<param name=\"currentMarker\" value=\"0\">");
	bodyBuffer.append(" <param name=\"defaultFrame\" value=\"0\">");
	bodyBuffer.append("<param name=\"enabled\" value=\"1\">");
	bodyBuffer.append("<param name=\"enableErrorDialogs\" value=\"0\">");
	bodyBuffer.append("<param name=\"enableContextMenu\" value=\"1\">");
	bodyBuffer.append("<param name=\"fullScreen\" value=\"0\"> ");
	bodyBuffer.append("<param name=\"invokeURLs\" value=\"1\">");
	bodyBuffer.append("<param name=\"mute\" value=\"0\">");
	bodyBuffer.append("<param name=\"playCount\" value=\"1\">");
	bodyBuffer.append("<param name=\"rate\" value=\"1\">");
	bodyBuffer.append("<param name=\"SAMIStyle\" value>");
	bodyBuffer.append("<param name=\"SAMILang\" value>");
	bodyBuffer.append("<param name=\"SAMIFilename\" value>");
	bodyBuffer.append("<param name=\"stretchToFit\" value=\"0\">");
	bodyBuffer.append("<param name=\"uiMode\" value=\"full\">");
	bodyBuffer.append("<param name=\"volume\" value=\"100\">");
	bodyBuffer.append("<param name=\"windowlessVideo\" value=\"0\">");
	bodyBuffer.append("</object>");
	out.write(bodyBuffer.toString());
	//out.write();bodyBuffer.append("<param name="autoStart" value="1">");
	out.println("<br><br><font size='5'>说明：如果音视频文件不能正常播放，请自行下载MP4解码器并安装！</font>");
	out.println("<br><font size='4'><a href='javascript:void(0)' onclick='downMP4()'>点击此处下载MP4解码器</a></font>");
}else if((dirpath+filePath).endsWith("JPG") || (dirpath+filePath).endsWith("jpg")){
//处理图片
	out.write("<img src='"+basePath +"/model//accessory//dzfj//download//"+ filePath+"' height='600'>");
}else if((dirpath+filePath).endsWith("pdf")){
//处理pdf
	out.write("当前格式不支持预览，请直接下载");
}else{
//通用处理方式

	if(hzm.toLowerCase().equals("txt")){
		FileInputStream fis = new FileInputStream(new File(dirpath+filePath));
		BufferedReader in = new BufferedReader(new InputStreamReader(fis,"GBK"));
		String inputLine;
		while((inputLine = in.readLine()) != null){
			out.println(inputLine+"<br>");
		}
		in.close();
	}else{
	
  		OutputStream output = null;  
   		FileInputStream in = null;    
 		try{
  			output = response.getOutputStream();  
  			in=new FileInputStream(new File(dirpath+filePath));  
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
  			if(in != null){    
    			in.close();    
    			in = null;    
 			}
 		}
	}
}

%> 
<script type="text/javascript">
function downMP4(){
	window.open("<%=basePath%>model/accessory/dzfj/need_jsp/dowmmp4.jsp?file_path=<%=mp4filePath%>");
}
</script>
