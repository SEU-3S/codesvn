<%@ page language="java" pageEncoding="utf-8"%>

<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="com.klspta.console.user.User"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

	String  expanded = "";
	expanded = request.getParameter("expanded");
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId=null;
		if (principal instanceof User) {
		   userId = ((User)principal).getUserID();
		} else {
		    userId =null;
		}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>flex左</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<%@ include file="/base/include/ext.jspf" %>
		<%@ include file="/base/include/restRequest.jspf" %>
		<script src="<%=basePath%>/base/fxgis/framework/js/toJson.js"></script>
<style>
body {
	font-family: helvetica, tahoma, verdana, sans-serif;
	padding: 0px;
	scrollbar-3dlight-color:#D4D0C8; 
  	scrollbar-highlight-color:#fff; 
  	scrollbar-face-color:#E4E4E4; 
  	scrollbar-arrow-color:#666; 
 	scrollbar-shadow-color:#808080; 
  	scrollbar-darkshadow-color:#D7DCE0; 
  	scrollbar-base-color:#D7DCE0; 
 	scrollbar-track-color:#;	
}
</STYLE>
	</head>
	<script>

var path = "<%=basePath%>";
var actionName = "mapAuthorOperation";
var actionMethod = "getExtTreeByUserid";
var parameter = "userid=<%=userId%>";
putClientCommond(actionName, actionMethod);
putRestParameter("userid", "<%=userId%>");
var treeData = restRequest();
var treeData;
var loadFlag=true;
/*********树形菜单展开收缩功能**add by 李如意 2011-07-13****/	  
	function closeOrOpenNode(){
		var str = document.getElementById("closeOrOpenNode").value; 
		if(str == "op"){ 
			tree.getRootNode().expand(true); 
			document.getElementById("closeOrOpenNode").src = "<%=basePath%>/base/thirdres/ext/examples/docs/resources/collapse-all.gif";
			document.getElementById("closeOrOpenNode").alt = "收起";   
			document.getElementById("closeOrOpenNode").value = "cl";
			return;
		}else{ 
			tree.getRootNode().collapse(true);  
			document.getElementById("closeOrOpenNode").src = "<%=basePath%>/base/thirdres/ext/examples/docs/resources/expand-all.gif";
			document.getElementById("closeOrOpenNode").alt = "展开";    	  
			document.getElementById("closeOrOpenNode").value = "op";
			return;		 
		}
	}
	
Ext.onReady(function(){
	
    tree = new Ext.tree.TreePanel({ 
        el:'mapTree',  
        title:"<div align='left'><img id=\"closeOrOpenNode\" value=\"op\" src='<%=basePath%>/base/thirdres/ext/examples/docs/resources/expand-all.gif' alt='展开' class=x-btn-text onclick=\"closeOrOpenNode();\" /></div>", 
        useArrows:true,  
        autoScroll:true, 
		frame: true, 			//显示树形列表样式   
        animate:true,
        enableDD:true,
        margins: '2 2 0 2',
        autoScroll: true,
        border: false,
        containerScroll: true,
        rootVisible: false,
        checkModel: 'cascade',
        onlyLeafCheckable: true,
        loader: new Ext.tree.TreeLoader({
        	baseAttrs: { uiProvider: Ext.ux.TreeCheckNodeUI }
        }),
        root: new Ext.tree.AsyncTreeNode({
        <%if("ture".equals(expanded)){%>
        	expanded: true,
        <%}else{%>
        	expanded: false,
        <%}%>
            children: treeData
        }),
        /* 添加图层树控制 add by 郭润沛 2011-1-30*/
        listeners: {
            'checkchange': function(node, checked){
				changeLayer(node,checked);
            },
              'beforecollapsenode': function(node,deep,anim){
if(deep && loadFlag){
loadFlag=false;
try{
 //changeMap();
 //setTimeout("changeMap()",3000);
 }catch(ex){
 document.location.reload();
 }
 }
            }
        }
    });

 tree.render();
  //先展开用于初始化ext的checked选项,否则无法获取mapService的可见图层
 tree.getRootNode().expand(true); 

//再合并
 tree.getRootNode().collapse(true); 
 tree.getRootNode().expand(true); 
});
/*当某个mapserice异常时，将下属图层设为不可用 add by guorp 2011-2-22*/
function unChecked(serviceid_2){

                    var checked= tree.getChecked();
                    for(var j=0;j<checked.length;j++){
		                    var node=checked[j]
		                    var id=node.id
		                    var mapServices=id.split("@");
		                    var serverid=mapServices[1]
		                    if(serverid!=null &&serverid!='null' && serviceid_2==serverid){
checked[j].disable();
                    	}
                    }
//alert(serviceid+"取消")
}

function changeLayer(node,checked){
  var attr=node.attributes;
  parent.frames["center"].frames["lower"].swfobject.getObjectById("FxGIS").setLayerVisiableById(attr.serverid,checked+'',attr.layerid);
}

/*根据图层树的选中情况，控制图层的显示*/
function changeMap(){
//1、获取所有选中的叶子
var parentnodes= tree.root.childNodes;
var jsonData=new Dictionary();
for(var j=0;j<parentnodes.length;j++){
 var chilenodes=parentnodes[j].childNodes;
  for(var t=0;t<chilenodes.length;t++){
     //第三层遍历
     if(chilenodes[t].attributes.leaf==0){
      var chilenodesinner=chilenodes[t].childNodes;
      for(var z=0;z<chilenodesinner.length;z++){
        jsonData.put(chilenodesinner[t].attributes.serverid,chilenodesinner[t].attributes.layerid,chilenodesinner[t].attributes.type,chilenodesinner[t].attributes.checked);
      }
     }jsonData.put("test",1);
     //alert(jsonData.toStr());
     jsonData.put(chilenodes[t].attributes.serverid,chilenodes[t].attributes.layerid,chilenodes[t].attributes.type,chilenodes[t].attributes.checked);
     }
}
var argStr=jsonData.toStr();
parent.frames["center"].frames["lower"].swfobject.getObjectById("FxGIS").setLayerVisiable(argStr);
//alert(getTopLayer());
//parent.frames["center"].frames["lower"].swfobject.getObjectById("FxGIS").setLayerVisiable("[{\"servicename\":\"DC_YXT_2011\",\"visiableids\":[]},{\"servicename\":\"DC_YXT_2010\",\"visiableids\":true}]");
}

function getTopLayer(){
var parentnodes= tree.root.childNodes;
for(var j=0;j<parentnodes.length;j++){
 var chilenodes=parentnodes[j].childNodes;
  for(var t=0;t<chilenodes.length;t++){
     if(chilenodes[t].attributes.checked&&chilenodes[t].attributes.type!='tiled'){
        var result='{\"servicename\":\"'+chilenodes[t].attributes.serverid+'\",\"visiableids\":['+chilenodes[t].attributes.layerid+']}';
         return result;
      }
     }
}
return null;
}

function  AscSort(x, y)    
{   
   return  x==y?0:(x>y?1:-1);   
}    
    
 function  DescSort(x, y)    
  {   
   return  x==y?0:(x>y?-1:1);   
}  

function cctt(){
parent.frames["center"].frames["lower"].swfobject.getObjectById("FxGIS").setMapAlpha(0.1);
}

</script>
	<body bgcolor="#FFFFFF">
		<div id="mapTree"  style="margin-Left:0px;margin-Right:-14px;margin-Top:0px"/>
	</body>
</html>
