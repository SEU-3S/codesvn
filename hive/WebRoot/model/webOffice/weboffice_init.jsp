<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="com.klspta.console.user.User"%>
<%@page import="com.klspta.model.webOffice.EmptyDocuments"%>
<%@page import="java.rmi.server.UID"%>
<%
     Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     String userName;
     String userID;
     if (principal instanceof User) {
         userName = ((User)principal).getUsername();
         userID = ((User)principal).getUserID();
     } else {
         userName = principal.toString();
         userID = principal.toString();
     }
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	request.setCharacterEncoding("UTF-8");	
	response.setCharacterEncoding("UTF-8");
	List ps = EmptyDocuments.getInstance().getJsonList2();
	String yw_guid = request.getParameter("yw_guid");
	String file_id = new UID().toString().replaceAll(":", "-");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>文书加载</title>
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
 	var dateFormat;
 	
function downloadDoc(){  
	var webObj=document.getElementById("WebOffice1"); 
	webObj.Close();  
	docName = document.getElementById("text").value; 
	document.all.WebOffice1.LoadOriginalFile('<%=basePath%>model/webOffice/documents/emptyDocuments/'+encodeURI(unescape(docName))+'.doc', "doc");
	document.getElementById("save").disabled = false; 
	document.getElementById("clo").disabled = false;
	document.all.WebOffice1.HideMenuArea("hideall","","","");  
}
	
function uploadDoc(){ 
		var file_id = "<%=file_id%>"; 
		docName = document.getElementById("text").value; 
		var webObj=document.getElementById("WebOffice1");
		var returnValue;
		var num =0;
		var flag = false;  
		try{
		    var actionName = "weboffice";
		    var actionMethod = "saveDoc";
		    var parameter="docName="+escape(escape(docName))+"&yw_guid=<%=yw_guid%>&dateFormat="+dateFormat+"&file_id=<%=file_id%>&username=<%=userName%>";   //对中文字符进行转码       
			var result = ajaxRequest(path,actionName,actionMethod,parameter); 
			flag = result.split(",")[1];
			num = result.split(",")[0]; 
			var myDate = new Date();
			//alert(num );
			//alert(myDate.getFullYear());
			webObj.HttpInit();			
			webObj.HttpAddPostString("id", yw_guid);
			webObj.HttpAddPostString("DocTitle",docName+"【第"+num+"次】.doc");      
			webObj.HttpAddPostString("DocID", file_id);    
			webObj.HttpAddPostString("DocType","doc");  
			webObj.HttpAddPostCurrFile("1",file_id);		 
			returnValue = webObj.HttpPost(path+"/model/webOffice/webOffice_save.jsp?yw_guid=<%=yw_guid%>"); 	
			returnValue = returnValue.replace(/(^\s*)|(\s*$)/g, "") 
			if(returnValue){	//returnValue 文书是否上传成功
				if(flag){     
					parent.Ext.getCmp("west_tree").getNodeById("4").appendChild([{ text: docName+"【第"+num+"次】.doc",leaf:1,id:file_id,filter:false,src:'model/webOffice/webOffice_read.jsp?moreFeatures=true&file_id='+file_id+'&file_type=.doc'}]);                     
				} 
				var actionName = "weboffice";
		        var actionMethod = "saveBH";
		        var parameter="year="+myDate.getFullYear()+"&file_id=<%=file_id%>&num="+num;   //对中文字符进行转码       
			    var result = ajaxRequest(path,actionName,actionMethod,parameter); 
				alert("保存成功！");
			}else{
				alert("保存失败！");
			} 
			window.location.reload();  
		}catch(e){
			alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description); 
		}
}
	
function closeDoc(){
		document.getElementById("save").disabled = true; 
		document.getElementById("clo").disabled = true;   
		var webObj=document.getElementById("WebOffice1");  
		webObj.Close();  
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
}

function TextValue(documentName){
	if(documentName == ""){
		closeDoc();
	}else{
		document.getElementById("text").value = documentName;
		downloadDoc(); 
	}
	var actionName = "weboffice";
    var actionMethod = "saveDoc";
    var parameter="docName="+escape(escape(docName))+"&yw_guid=<%=yw_guid%>&dateFormat="+dateFormat+"&file_id=<%=file_id%>&username=<%=userName%>";   //对中文字符进行转码       
	var result = ajaxRequest(path,actionName,actionMethod,parameter); 
	num = result.split(",")[0]; 
	var myDate = new Date();
	document.all.WebOffice1.SetFieldValue("year",myDate.getFullYear(), "");  
	document.all.WebOffice1.SetFieldValue("mark",num, ""); 
	//alert(document.all.WebOffice1.SetFieldValue("year",myDate.getFullYear(), "") + ':' +document.all.WebOffice1.SetFieldValue("mark",num, ""));
}
</script>
  <body leftmargin="0" bottommargin="0" rightmargin="0" topmargin="0"  onload="webofficeInit();"> 
    <div style="width:100%; height:25px;float:left;background-color: gray">
    	<select id="blankDocuments" name="blankDocuments" onChange="TextValue(this.value)">
	    	<option value="">==========请 选 择==========</option>
	    	<%
	    		for(int i=0;i<ps.size();i++){
	    	%>	
		    	<option value="<%=ps.get(i) %>">
					<%=ps.get(i) %>
		    	</option>	
	    	<%	
	    		}    	
	    	%>
    	</select>
    	<input id="save" style="width:50" type="button" value="保存" onclick="uploadDoc()" disabled="disabled"/>
    	<input id="clo" style="width:50" type="button" value="关闭" onclick="closeDoc()" disabled="disabled"/>
	    <input style="width:50" type="hidden" name="text" id="text" value="01责令停止违法行为通知书"> 
	    
    </div>
<div>
	<TABLE class=TableBlock width="100%">
	  <TBODY><TR><TD class=TableData vAlign=top width="100%"><SCRIPT src="LoadWebOffice.js"></SCRIPT></TD></TR></TBODY>
	</TABLE>
</div>
 </body>
</html>
