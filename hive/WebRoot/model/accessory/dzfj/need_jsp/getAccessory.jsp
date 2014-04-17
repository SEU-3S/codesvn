<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.klspta.model.accessory.dzfj.AccessoryOperation" %>
<%@page import="com.klspta.base.util.bean.ftputil.AccessoryBean" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String file_id = request.getParameter("file_id");
String yw_guid = request.getParameter("yw_guid");
String type=request.getParameter("type");
String flag = request.getParameter("flag");	//标记是否是单机预览：0为单机预览，不下载
if(type==null){
    type = "null";
}
if(flag==null){
    flag = "null";
}
String realPath=request.getRealPath("/")+"model//accessory//dzfj//download//";
String paths = null;
String temp_file_path = null;
if(file_id!=null){
	AccessoryBean bean=new AccessoryBean();
	bean.setFile_id(file_id);
	bean.setFile_type("file");
	temp_file_path = basePath+"model//accessory//dzfj//download//"+AccessoryOperation.getInstance().download(bean,realPath);
	paths = "../../../accessory/dzfj/need_jsp/down.jsp?flag="+flag+"&file_path="+temp_file_path;
}
else{
	paths = basePath + "/model/accessory/dzfj/download/" +yw_guid+".zip";
}
%>
 <script type="text/javascript">
 if("<%=type%>"=="down"||"<%=type%>"=="null"||"<%=type%>"=="all"){
 document.location.href = "<%=paths%>";
 }else{
 document.location.href = "<%=temp_file_path%>";
 }
</script>
<html>
  <body bgcolor="#FFFFFF" leftmargin="0" bottommargin="0" rightmargin="0" topmargin="0">
  </body>
</html>
