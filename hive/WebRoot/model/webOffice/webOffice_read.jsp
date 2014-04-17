<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="com.klspta.console.user.User"%>
<%@page import="com.klspta.base.util.bean.ftputil.AccessoryBean"%>
<%@page import="com.klspta.model.webOffice.EmptyDocuments"%>
<%@page import="com.klspta.base.util.UtilFactory"%>
<%
	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName;
	if (principal instanceof User) {
	   userName = ((User)principal).getUsername();
	} else {
	   userName = principal.toString();
	}
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	request.setCharacterEncoding("UTF-8");	
	response.setCharacterEncoding("UTF-8");
	List ps = EmptyDocuments.getInstance().getJsonList2();
	String moreFunction = request.getParameter("moreFeatures");
	String yw_guid = request.getParameter("yw_guid");
	String docName = (String)request.getAttribute("docName");
	String file_id=request.getParameter("file_id");
	AccessoryBean bean=new AccessoryBean();
	bean.setFile_id(file_id);
	bean.setFile_type("file");
	String file_type=request.getParameter("file_type");
	String ftpFileName = file_id+file_type;
	String docNodeName = request.getParameter("docNodeName");	
	//String tempFolder = UtilFactory.getConfigUtil().getConfig("SHAPEFILE_PATH");
	String tem = new java.io.File(application.getRealPath(request.getRequestURI())).getParent();
	String temp = (tem.substring(0,tem.lastIndexOf(path.substring(1))-1)+tem.substring(tem.lastIndexOf(path.substring(1))+6)+"/documents/documentTemporaryFolder/").replace("\\","/");
	UtilFactory.getFtpUtil().downloadFile(ftpFileName,temp+ftpFileName); //将ftp服务器中指定的文档下载到服务器临时文件夹下(weOffice模块下的documentTemporaryFolder)
	String base = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + request.getRequestURI();
	String tempFolder = base.substring(0,base.lastIndexOf("/")) + "/documents/documentTemporaryFolder/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>DocumentPreview</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
		<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%@ include file="/base/include/restRequest.jspf" %>
    <%@ include file="/base/include/ext.jspf" %>
  </head>

 <Script language="javascript">
 	var path = "<%=basePath%>";
 	var yw_guid= "<%=yw_guid%>";
 	var docName; 
	var returnValue;
	
	function uploadDoc(){   
		var actionName = "weboffice";
	    var actionMethod = "saveDoc";  
	    var parameter="docName="+encodeURI(unescape("<%=docNodeName%>"))+"&yw_guid=<%=yw_guid%>&tempFilePath=<%=temp+ftpFileName%>&username=<%=userName%>&webofficeRead=read";           
		var webObj=document.getElementById("WebOffice1"); 
		var returnValue;
		var flag = false;
		webObj.HttpInit();						
		webObj.HttpAddPostString("id", yw_guid);
		if("<%=docNodeName%>"!=undefined){		
			webObj.HttpAddPostString("DocTitle","<%=docNodeName%>");  
			webObj.HttpAddPostString("DocID", "<%=file_id%>");   
			webObj.HttpAddPostString("DocType","doc");  
			webObj.HttpAddPostString("yw_guid",yw_guid);  
			webObj.HttpAddPostString("docName","<%=docNodeName%>");  
			webObj.HttpAddPostString("file_id","<%=file_id%>");    
			webObj.HttpAddPostCurrFile("1","<%=file_id%>"); 	
			returnValue = webObj.HttpPost(path+"/model/webOffice/webOffice_save.jsp?yw_guid=<%=yw_guid%>"); 	   	    
			returnValue = ajaxRequest(path,actionName,actionMethod,parameter);	
			flag = returnValue.split(",")[1];
			if(flag){
				alert("保存成功！");
			}    
		}
	}

	function deleteDoc(){  
		if(confirm("文件删除后不可恢复，确定删除吗？") == true) {
			var actionName = "weboffice";
		    var actionMethod = "deleteDoc";   
		    var parameter="deletedoc=true&yw_guid=<%=yw_guid%>&file_id=<%=file_id%>&ftpFileTempPath="+encodeURI(unescape("<%=temp%><%=ftpFileName%>"));   //对中文字符进行转码             
			var returnValue = ajaxRequest(path,actionName,actionMethod,parameter);
			if("true" == returnValue){      
				alert("删除成功！"); 
			}	
			parent.Ext.getCmp("west_tree").getNodeById("<%=file_id%>").remove() ; 
			var webObj=document.getElementById("WebOffice1"); 
			webObj.Close(); 
	  	}else{
	  		return false;
	  	}
	} 
	
	function webofficeInit(){
		var webObj=document.getElementById("WebOffice1");
		var vCurItem = document.all.WebOffice1.HideMenuItem(0);
		if(vCurItem & 0x01){
			webObj.HideMenuItem(0x01);
		}else{
			webObj.HideMenuItem(0x01 + 0x8000); 
		}
		if(vCurItem & 0x02){
			webObj.HideMenuItem(0x02);
		}else{
			webObj.HideMenuItem(0x02 + 0x8000); 
		}
		if(vCurItem & 0x04){
			webObj.HideMenuItem(0x04);  
		}else{
			webObj.HideMenuItem(0x04 + 0x8000); 
		} 
	 	if("<%=moreFunction%>" == "true"){ 
			document.all.WebOffice1.SetCustomToolBtn(0,"保存文档");  
			document.all.WebOffice1.SetCustomToolBtn(1,"关闭文档"); 
			document.all.WebOffice1.SetCustomToolBtn(2,"删除文档"); 
		}
		document.all.WebOffice1.LoadOriginalFile("<%=tempFolder%><%=ftpFileName%>", "doc");
		document.all.WebOffice1.HideMenuArea("hideall","","","");  
	}
	
	//关闭文书
	function closeDoc(){
		var actionName = "weboffice";
	    var actionMethod = "deleteDoc";   
	    var parameter="ftpFileTempPath="+encodeURI(unescape("<%=temp%><%=ftpFileName%>"));   //对中文字符进行转码              
		var returnValue = ajaxRequest(path,actionName,actionMethod,parameter);
	}

</script>
<!-- add by  李如意 Date:2012-08-23  -->
<SCRIPT language=javascript event=NotifyToolBarClick(iIndex) for=WebOffice1> 
	if(iIndex == 32776){ 	
		var webObj=document.getElementById("WebOffice1");  
		if(webObj.IsOpened){	
			uploadDoc();
		}
	}
	if(iIndex == 32777){ 	
		var webObj=document.getElementById("WebOffice1"); 
		webObj.Close();
				var actionName = "weboffice";
	    var actionMethod = "deleteDoc";   
	    var parameter="ftpFileTempPath="+encodeURI(unescape("<%=temp%><%=ftpFileName%>"));   //对中文字符进行转码              
		var returnValue = ajaxRequest(path,actionName,actionMethod,parameter); 
	} 
	if(iIndex == 32778){ 	
		var webObj=document.getElementById("WebOffice1");  
		if(webObj.IsOpened){ 
			deleteDoc(); 
			webObj.Close();  
		}
	} 
</SCRIPT> 
<body leftmargin="0" bottommargin="0" rightmargin="0" topmargin="0"  onload="webofficeInit();" onunload="closeDoc();" >  
<div>
	<TABLE class=TableBlock width="100%">
	  <TBODY><TR><TD class=TableData vAlign=top width="100%"><SCRIPT src="LoadWebOffice.js"></SCRIPT></TD></TR></TBODY>
	</TABLE>
</div>
 </body>
</html>
