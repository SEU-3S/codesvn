<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String gisapiPath = basePath + "gisapp/arcgis/";
String url=basePath+"base/fxgis/fx/print.html?debug=true";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>打印预览页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>

  <script>    
function init(){
 	  /// bdhtml=window.opener.document.body.innerHTML;
 	  // prnhtml=bdhtml.substring(bdhtml.indexOf("打印")+6); 
      // window.document.getElementById('printing').innerHTML=prnhtml;
	}
function printPage(){
  if(document.all.centerTopic.offsetHeight>54){
     alert("请减少备注行数(建议3行内)，以便更好打印！");
  return false;
  }
   if(document.getElementById("topicCenter").innerText==""){
    document.getElementById("topic").style.display="none";
    document.getElementById("topicCenter").style.display="none";
   }
	document.getElementById("btp").style.display="none";
	
	pagesetup_null();  	//打印之前通过注册表删除页眉页脚！  
	window.print();
	pagesetup_default();//打印之后通过注册表增加页眉页脚！   
	window.close();
}
function checkRow(e){
 if(document.all.centerTopic.offsetHeight>=55){
    alert("请减少备注行数(建议3行内)，以便更好打印！");
     e.keyCode=8;  
 }
}

/**
*<br>add by 李如意 
*<br>Description:打印之前通过注册表删除页眉页脚！   
*<br>Date:2011-06-27
*/   	          
var hkey_root,hkey_path,hkey_key;  
	hkey_root="HKEY_CURRENT_USER"; 
	hkey_path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\"; 
	function pagesetup_null(){ 			 
		try{ 
			// 去除打印时的页面页脚					 
			var RegWsh = new ActiveXObject("WScript.Shell"); 
			hkey_key="header" ;
			RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,""); 
			hkey_key="footer"; 
			RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");  				
		}catch(e){
			alert(e.message);  			
		}				
	}
/**
*<br>add by 李如意 
*<br>Description:打印之前通过注册表恢复页眉页脚！     
*<br>Date:2011-06-28 
*/
function pagesetup_default() 
{ 
	try{ 
		var RegWsh = new ActiveXObject("WScript.Shell") 
		hkey_key="header" 
		RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"&w&b页码，&p/&P") 
		hkey_key="footer" 
		RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"&u&b&d")  
	}catch(e){
		alert(e.message);  
	} 
} 
 

</script>

  <body  class="tundra" onload="init()"> 
  <center><font id="topic" style="font-weight: bold;"  size="3">备注</font></center>
  <center id="centerTopic"><textarea id="topicCenter" style="overflow:visible; border: 1px #ccc solid;line-height:16px" cols="80" onkeydown="checkRow(event)"></textarea></center><br>
  <div id="printing"><iframe id="lower" name="lower"  style="width: 100%;height:100%; overflow: auto;" src=<%=url%>></iframe></div>
  <div id="btP"style='width: 36px; font-stretch:expanded; height:20px;;border:1px #ccc solid;cursor: pointer; position:absolute;right:50px;top:30px;' onclick="printPage()">打印</div> 
  </body>
</html>
