<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.klspta.model.accessory.dzfj.*"%>
<%@page import="com.klspta.base.util.bean.ftputil.AccessoryBean"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
    String treePath = basePath + "thirdres/dhtmlx//dhtmlxTree//codebase";
    String yw_guid = request.getParameter("yw_guid");
    yw_guid="123";
    String tree = AccessoryBean.transfer(AccessoryOperation.getInstance().getAccessorylistByYwGuid(yw_guid));
    //AccessoryOperation.getInstance().upload("58409D3E2FBC4A3299E72AEEAD3B63BA","","建设用地文件.doc","","123");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>outlookBar</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>common/css/styles.css">
		<link rel="stylesheet" type="text/css"
			href="<%=treePath%>/dhtmlxtree.css">
		<script src="<%=treePath%>/dhtmlxcommon.js"></script>
		<script src="<%=treePath%>/dhtmlxtree.js"></script>
		<script src="<%=treePath%>/ext/dhtmlxtree_json.js"></script>
		<style>
html,body {
	width: 100%;
	height: 100%;
	margin: 0px;
	overflow: hidden;
}
</style>
	</head>

	<body>
		<div id="outlookbar"
			style="width: 100%; height: 100%; background-color: #f5f5f5; border: 1px solid Silver;; overflow: auto;" />
	</body>
</html>
<script>
	var treeArray = new Array(<%=tree%>);
	tree=new dhtmlXTreeObject(document.getElementById('outlookbar'),"100%","100%",0);
	
		tree.setImagePath("<%=treePath%>/imgs/csh_dhx_skyblue/");
		tree.enableCheckBoxes(false);
		tree.enableDragAndDrop(false);
			tree.loadJSArray(treeArray);//for loading from array object
			//更改子文件夹图标
			for(i=0;i<treeArray.length;i++){
			
			if(treeArray[i][4]=='forder'){
			//alert(treeArray[i][0])
			tree.setItemImage2(treeArray[i][0],'folderOpen.gif','folderOpen.gif','folderOpen.gif');
			}
			}

		tree.openAllItems(0);
		/*添加单击监听事件 */
		tree.attachEvent("onSelect",function(id){
		if((tree.hasChildren(id))==0){//只处理叶子节点
            parent.dhxLayout.cells("b").setText(tree.getSelectedItemText());
           // parent.dhxLayout.cells("b").attachURL("<%=basePath%>"+tree.getUserData(id,"href"));
            parent.dhxLayout.cells("b").progressOff();
            return true;
            }
        });
</script>
