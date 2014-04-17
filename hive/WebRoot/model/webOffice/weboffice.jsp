<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 
< %@page import="com.lowagie.text.Document"%>
-->
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	request.setCharacterEncoding("UTF-8");	
	response.setCharacterEncoding("UTF-8");
	//List ps = WebOffice.getInstance().getJsonList2(); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    
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
	<script src="<%=basePath%>/base/form/DatePicker/WdatePicker.js"></script> 
	
  </head>
 <Script language="javascript">
var path = "<%=basePath%>";
var docName; 
 	
// 实现地区选择
function jbrdzChange(level){
	//获取select选择框的数值
	document.getElementById("hidden"+level.id).value = level.options[level.selectedIndex].text;     
	var path = "<%=basePath%>"; 
	var actionName = "xzqh";
	var actionMethod = "getNextPlace";
	var parameter = "code=" + level.value;
	var myData = ajaxRequest(path, actionName, actionMethod, parameter);
	var obj = eval('(' + myData + ')');
	if(obj == "") return ;
	var currentLevel = level.id;
	var currentNum = currentLevel.charAt(currentLevel.length - 1);
	var name = currentLevel.substring(0, currentLevel.length - 1);
	var nextNum = parseInt(currentNum) + 1;
	var nextLevel = name + nextNum;
	var selectPlace = document.getElementById(nextLevel);
	if(selectPlace == undefined){
		return; 
	}
	selectPlace.options.innerText = "";
	//重选时，去除选择过得选项  
	for(var i = nextNum; i < 6; i++){
		var deleteLevel = name + i;
		deleteOptions = document.getElementById(deleteLevel);
		if(deleteOptions != undefined){
			if(deleteOptions.options !== undefined){
				deleteOptions.options.innerText = "";
			}
		}
	}
	
	if(selectPlace == undefined) return ; 
	// 判断是否是直辖市
	var index = level.selectedIndex;
	currentName = level.options[index].text;
	if(currentName.charAt(currentName.length - 1) == "市" &&　nextNum == 2){
		selectPlace.options.innerText = "";
		selectPlace.style.display = "none";
		selectPlace.nextSibling.data =" ";
		nextNum = nextNum + 1;
		nextLevel = name + nextNum;
		selectPlace = document.getElementById(nextLevel);	
	}else{
		selectPlace.options.innerText = "";
		selectPlace.style.display = "";
		if(selectPlace.nextSibling.data == " "){
			selectPlace.nextSibling.data = "市";
		}
	}
	
	selectPlace.options.innerText = "";
	for(var i = 0; i < obj[0].length; i++){
		var opt = document.createElement('option');
		opt.text = obj[0][i].name;
		opt.value = obj[0][i].code;
		//opt.selected = "selected";
		selectPlace.options.add(opt, 0);
	}
}
	
function initSelect(){
	var path = "<%=basePath%>";
	var actionName = "xzqh";
	var actionMethod = "getAllPlace";
	var parameter = null;
	var myData = ajaxRequest(path, actionName, actionMethod, parameter);
	var obj = eval('(' + myData + ')'); 
	var select = document.getElementById("WTFSD1");
	if(obj != ""){ 
		for(var i = 0; i < obj[0].length; i++){
			var opt2 = document.createElement('option')
			opt2.text = obj[0][i].name;
			opt2.value = obj[0][i].code;
			select.options.add(opt2);
		}
	}
}
		
function getRadioValue(){
		var zhengqu	= document.getElementById("WTFSD3").value; 
		var type;
        var number = document.getElementsByName("reportType"); 
        for (var i = 0; i <number.length; ++ i)
           	if (number[i].checked)type = number[i].value
       		return type;   
}
	
function updatedata(){
	  var start = document.getElementById("startTime").value;
	  var end = document.getElementById("endTime").value;
	  var startYear=start.split('-')[0];
	  var startMonth=start.split('-')[1];
	  var startDate = start.split('-')[2].split(' ')[0];
	  var endYear=end.split('-')[0];
	  var endMonth=end.split('-')[1];
	  var endDate = end.split('-')[2].split(' ')[0];
	  var type = getRadioValue(); 
	  var province = document.getElementById("hiddenWTFSD1").value; 
	  var city = document.getElementById("hiddenWTFSD2").value; 
	  var county = document.getElementById("hiddenWTFSD3").value; 
		
	  document.all.WebOffice1.SetFieldValue("titlebu","", "");  
	  document.all.WebOffice1.SetFieldValue("titlesheng",province, "");   
	  document.all.WebOffice1.SetFieldValue("titleshi",city, "");   
	  document.all.WebOffice1.SetFieldValue("titlequxian",county, "");  
	  document.all.WebOffice1.SetFieldValue("titleType",type, "");     
	  document.all.WebOffice1.SetFieldValue("xsslstartTimeYear",startYear, "");     
	  document.all.WebOffice1.SetFieldValue("xsslstartTimeMonth",startMonth, "");    
	  document.all.WebOffice1.SetFieldValue("xsslstartTimeDay",startDate, "");   
	  document.all.WebOffice1.SetFieldValue("xsslendTimeMonth",endMonth, "");  
	  document.all.WebOffice1.SetFieldValue("xsslendTimeDay",endDate, "");   
	  document.all.WebOffice1.SetFieldValue("xsslbu","", "");  
	  document.all.WebOffice1.SetFieldValue("xsslsheng","", "");  
	  document.all.WebOffice1.SetFieldValue("xsslshi","", "");  
	  document.all.WebOffice1.SetFieldValue("xsslquxian","", "");  
	  document.all.WebOffice1.SetFieldValue("xsslglxs","4434", "");  
	  document.all.WebOffice1.SetFieldValue("xsslglxsjbdh","3814", "");  
	  document.all.WebOffice1.SetFieldValue("xsslglxsjbxj","576", "");  
	  document.all.WebOffice1.SetFieldValue("xsslglxsjbdzyj","44", "");  
	  document.all.WebOffice1.SetFieldValue("xsslglxsjbcz","29", "");  
	  document.all.WebOffice1.SetFieldValue("xsslglxsldpd","0", "");  
	  document.all.WebOffice1.SetFieldValue("xsslglxsxjsb","0", "");  
	  document.all.WebOffice1.SetFieldValue("xsslglxsmtfy","0", "");  
	  document.all.WebOffice1.SetFieldValue("xsslglxstb","上升141.6", "");  
	  document.all.WebOffice1.SetFieldValue("xsslglxshb","下降5.0", "");   
	  document.all.WebOffice1.SetFieldValue("xsslsyslfwxs","4409", "");   
	  document.all.WebOffice1.SetFieldValue("xsslsyslfwxstdlywfwgxs","3909", "");   
	  document.all.WebOffice1.SetFieldValue("xsslsyslfwxskczylywfwgxs","500", "");   
	  document.all.WebOffice1.SetFieldValue("xsslsyslfwxsyz","99.4", "");   
	  document.all.WebOffice1.SetFieldValue("xsslsyslfwxstb","上升85.8", "");   
	  document.all.WebOffice1.SetFieldValue("xsslsyslfwxshb","下降23.9", ""); 
	  document.all.WebOffice1.SetFieldValue("xsslcxjg","2134", ""); 
	  document.all.WebOffice1.SetFieldValue("xsslcxjgyz","87.2", ""); 
	  document.all.WebOffice1.SetFieldValue("xsslcxjgtb","上升69.6", ""); 
	  document.all.WebOffice1.SetFieldValue("xsslcxjghb","上升6.0", ""); 
	  document.all.WebOffice1.SetFieldValue("xsslzzzxbsyslfw","15", "");  
	  document.all.WebOffice1.SetFieldValue("xsslzzzxbsyslfwyz","0.3", "");  
	  document.all.WebOffice1.SetFieldValue("xsslzzzxbsyslfwyztb","上升532.9", "");  
	  document.all.WebOffice1.SetFieldValue("xsslzzzxbsyslfwyzhb","上升3.8", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkjbtdlywfwgxs","3409", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkjbtdlywfwgxstb","上升84.3", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkjbtdlywfwgxshb","下降22.6", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkfyffzd","160", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkfyffzdyz","4.7", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkfyffzrtd","353", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkfyffzrtdyz","5.7", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkfyffpd","489", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkfyffpdyz","8.9", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkfydjcrtd","324", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkfydjcrtdyz","4.2", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkfyphnyd","920", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkfyphnydyz","9.6", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkfyzdbcfd","435", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkfyzdbcfdyz","7.5", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkfytdlyqtwfwg","623", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkfytdlyqtwfwgyz","5.1", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkjbkczylywfwgxs","734", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkjbkczylywfwgxstb","上升125.0", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkjbkczylywfwgxshb","下降21.7", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkjbkczylywfwgxswfkc","213", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkjbkczylywfwgxswfkcyz","4.9", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkjbkczylywfwgxswfzr","198", "");   
	  document.all.WebOffice1.SetFieldValue("xsslflqkjbkczylywfwgxswfzryz","3.1", "");   
	  document.all.WebOffice1.SetFieldValue("xsslflqkjbkczylywfwgxsqtkcwf","90", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkjbkczylywfwgxsqtkcwfyz","2.1", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkjbkczylywfwgxsfywfkc","109", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkjbkczylywfwgxsfywfkcyz","1.7", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkjbkczylywfwgxsfyffpzckqtkq","67", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkjbkczylywfwgxsfyffpzckqtkqyz","1.3", "");  
	  document.all.WebOffice1.SetFieldValue("xsslflqkjbkczylywfwgxsfybagdjnkczybcf","487", "");  	   
	  document.all.WebOffice1.SetFieldValue("xsslflqkjbkczylywfwgxsfybagdjnkczybcfyz","4.7", ""); 
	  document.all.WebOffice1.SetFieldValue("xsslxsfb","", "");  
	  document.all.WebOffice1.SetFieldValue("xsslxsclqkyjbl","3364", "");  
	  document.all.WebOffice1.SetFieldValue("xsslxsclqkyjblzd","1109", "");  
	  document.all.WebOffice1.SetFieldValue("xsslxsclqkyjblzdyz","48.7", "");  
	  document.all.WebOffice1.SetFieldValue("xsslxsclqkyjblyb","2255", "");  
	  document.all.WebOffice1.SetFieldValue("xsslxsclqkyjblybyz","51.3", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkstartTimeYear",startYear, "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkstartTimeMonth",startMonth, "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkstartTimeDay",startDate, "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkendtTimeMonth",endMonth, "");   
	  document.all.WebOffice1.SetFieldValue("dhslqkendtTimeDay",endDate, "");   
	  document.all.WebOffice1.SetFieldValue("dhslqkzjbdh","12208", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkzjbdhtb","上升141.6", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkzjbdhhb","下降5.0", "");  	  
	  document.all.WebOffice1.SetFieldValue("dhslqkzjbdhsyslfwxs","12009", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkzjbdhsyslfwxstdlywfwgxs","4532", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkzjbdhsyslfwxskczylywfwgxs","3212", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkzjbdhsyslfwxsyz","98.4", "");  	    
	  document.all.WebOffice1.SetFieldValue("dhslqkzjbdhsyslfwxstb","上升85.5", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkzjbdhsyslfwxshb","下降23.9", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkzjbdhcxjg","8690", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkzjbdhcxjgyz","84.8", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkzjbdhcxjgtb","上升69.6", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkzjbdhcxjghb","上升6.0", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkzjbdhzczxbsyslfw","1209", "");   
	  document.all.WebOffice1.SetFieldValue("dhslqkzjbdhzczxbsyslfwyz","3.2", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkzjbdhzczxbsyslfwtb","上升532.9", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkzjbdhzczxbsyslfwhb","上升3.8", "");
	  document.all.WebOffice1.SetFieldValue("dhslqkflqktdlywfwgxs","9801", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqktdlywfwgxstb","上升84.3", "");  	  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqktdlywfwgxshb","下降22.6", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqktdlywfwgxsfyffzd","2301", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqktdlywfwgxsfyffzdyz","34.6", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqktdlywfwgxsfyffzrtd","1209", "");  	    
	  document.all.WebOffice1.SetFieldValue("dhslqkflqktdlywfwgxsfyffzrtdyz","4.3", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqktdlywfwgxsfyffpd","2103", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqktdlywfwgxsfyffpdyz","4.8", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqktdlywfwgxsfydjcrtd","1207", "");  	  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqktdlywfwgxsfydjcrtdyz","2.9", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqktdlywfwgxsfyphnyd","3211", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqktdlywfwgxsfyphnydyz","31.1", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqktdlywfwgxsfyzdbcfd","1231", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqktdlywfwgxsfyzdbcfdyz","21.3", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqktdlywfwgxsfytdlyqtwfwgxw","991", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqktdlywfwgxsfytdlyqtwfwgxwyz","12.6", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqkjbkczylywfwgxs","3210", "");  	   
	  document.all.WebOffice1.SetFieldValue("dhslqkflqkjbkczylywfwgxstb","上升125.0", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqkjbkczylywfwgxshb","下降21.7", "");   
	  document.all.WebOffice1.SetFieldValue("dhslqkflqkjbkczylywfwgxsfywfkaicai","761", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqkjbkczylywfwgxsfywfkaicaiyz","32.7", "");  	    
	  document.all.WebOffice1.SetFieldValue("dhslqkflqkjbkczylywfwgxsfywfzr","231", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqkjbkczylywfwgxsfywfyz","30.1", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqkjbkczylywfwgxsfywfkc","234", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqkjbkczylywfwgxsfywfkcyz","9.3", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqkjbkczylywfwgxsfyffpzckqtkq","214", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqkjbkczylywfwgxsfyffpzckqtkqyz","6.8", "");  		  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqkjbkczylywfwgxsfybagdjnzybcf","45", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkflqkjbkczylywfwgxsfybagdjnzybcfyz","2.1", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkxsfb","", "");   
	  document.all.WebOffice1.SetFieldValue("dhslqkxsclqkyjbl","3211", "");  		  
	  document.all.WebOffice1.SetFieldValue("dhslqkxsclqkyjblzd","1201", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkxsclqkyjblzdyz","37.4", "");  		  
	  document.all.WebOffice1.SetFieldValue("dhslqkxsclqkyjblyb","2010", "");  
	  document.all.WebOffice1.SetFieldValue("dhslqkxsclqkyjblybyz","62.6", "");  		  
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
	document.all.WebOffice1.SetCustomToolBtn(4,"自动生成");  
	document.all.WebOffice1.SetCustomToolBtn(0,"关闭文档");  
}
	
function MyFormatTime(str){
    var year=str.split('-')[0];
    var month=str.split('-')[1];
    var date = str.split('-')[2].split(' ')[0];
    var h = str.split('-')[2].split(' ')[1].split(':')[0];
    var m = str.split('-')[2].split(' ')[1].split(':')[1];
    var s = str.split('-')[2].split(' ')[1].split(':')[2];    
    return new Date(year,month,date,h,m,s);
}

function fontcolor(){ 
		var vBKCount;
		vBKCount = document.all.WebOffice1.GetBookMarkCount();
		var iIndex; 
		var vName;
		var vValue;
		var vShowInfo = "";
		var vObj= document.all.WebOffice1.GetDocumentObject();
		for(iIndex = 1; iIndex <= vBKCount; iIndex++){
			vName = document.all.WebOffice1.GetBookMarkInfo(iIndex,0 ); 
			vValue = document.all.WebOffice1.GetBookMarkInfo(iIndex,1 );
			vObj.Bookmarks(vName).Range.Font.Color =255;      
			vName = "";
			vValue = ""; 
		}
}
</script>

<!-- add by  李如意 Date:2012-06-06 12:55 文书在线编辑，动态数值保存能到word的实现  -->
<SCRIPT language=javascript event=NotifyToolBarClick(iIndex) for=WebOffice1> 
	if(iIndex == 32776){ 
		var webObj=document.getElementById("WebOffice1"); 
		webObj.Close(); 
	}
	if(iIndex == 32780){ 
		var start = document.getElementById("startTime").value;
		var end = document.getElementById("endTime").value;
	    if(start == "" || end == ""){ 
	        alert("请选择时间段！"); 
	        return;
	    }else{
			if(MyFormatTime(start) > MyFormatTime(end)){
				alert("开始时间应该早于结束时间，请重新选择！");
				return; 
			}else{ 
				var webObj=document.getElementById("WebOffice1"); 
				webObj.Close();  
				var docName = document.getElementById("text").value;  
				document.all.WebOffice1.LoadOriginalFile('<%=basePath%>model/webOffice/documentTemplate/'+encodeURI(unescape(docName))+'.doc', "doc"); 
				updatedata();  
				fontcolor();
			}
		}  
	}
</SCRIPT> 

  <body leftmargin="0" bottommargin="0" rightmargin="0" topmargin="0" onload="initSelect();webofficeInit();">
    	<div style="width:100%; height:25px;float:left;">
	    	<font size="2">政区：</font>
			<SELECT id="WTFSD1" onchange="jbrdzChange(this)" style="width:90px"><OPTION value="" selected></OPTION></SELECT><font size="2">省</font>
			<SELECT id="WTFSD2" onclick="jbrdzChange(this)" style="width:90px"><OPTION value="" selected></OPTION></SELECT><font size="2">市</font>
			<SELECT id="WTFSD3" onclick="jbrdzChange(this)" style="width:90px"><OPTION value="" selected></OPTION></SELECT><font size="2">县  </font>	
	    	<font size="2">开始日期：</font>	<input type="text" name="startTime" id="startTime" class="Wdate" onClick="WdatePicker()" />
	    	<font size="2">结束日期：</font>	<input type="text" name="endTime" id="endTime" class="Wdate" onClick="WdatePicker()" />
		    <input style="width:50" type="hidden" name="text" id="text" value="01统计分析周（月、季、年）报模板"> 
		    <font size="2">类型：</font>		<input type="radio" name="reportType" id="1" value="周报" checked="checked" /><font size="2">周报</font>
			<input type="radio" name="reportType" id="2" value="月报" /><font size="2">月报</font>
			<input type="radio" name="reportType" id="3" value="季报" /><font size="2">季报</font>
			<input type="radio" name="reportType" id="4" value="年报" /><font size="2">年报</font><br />
	    </div>
    	<div>
				<input type="hidden" id="hiddenWTFSD1" name="hiddenWTFSD1" >
				<input type="hidden" id="hiddenWTFSD2" name="hiddenWTFSD2" >
				<input type="hidden" id="hiddenWTFSD3" name="hiddenWTFSD3" >
    	</div>
		<div>
		<TABLE class=TableBlock width="100%">
		  <TBODY>
		  	<TR>  
			    <TD class=TableData vAlign=top width="100%"><SCRIPT src="LoadWebOffice.js"></SCRIPT></TD>
			</TR>
			</TBODY>
		</TABLE>
		</div>
 </body>
</html>
