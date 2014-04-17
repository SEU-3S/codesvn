<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
    String name = request.getParameter("name");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title></title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<%@ include file="/base/include/ext.jspf"%>
		<style>
html,body {
	width: 100%;
	height: 100%;
	margin: 0px;
	overflow: hidden;
}
</style>
<script type="text/javascript">
Ext.onReady(function(){
	var form = new Ext.FormPanel({
        width: 500,
        frame: true,
        applyTo: 'newText',
        autoHeight: true,
        labelWidth: 60,
        items: [{
        	layout: 'column',
			items: [{
				columnWidth: .4,
				layout: 'form',
				items:[{
					xtype: 'textfield',
					id:'textName',
					allowBlank: false, 
					blankText: '请输入新文件夹名称',
					value: '新建文件夹',
					fieldLabel: '文件名称'
				}]				
        	},{
        		columnWidth: .1,
        		layout: 'form',
        		items:[{
        			xtype:'button',
					text: '确定',
					handler: function(){  
						var name = Ext.getCmp("textName").getValue();
						if(name==null || name=='') 
								return false;
						window.returnValue=escape(name)
						window.close()             
					 }
        		}]
        		
       		},{
       			columnWidth: .1,
       			layout: 'form',
       			items:[{
       				xtype: 'button',
	       			text: '取消',
	       			handler: function(){
	       				window.close();
	       			}
       			}]
       		}]
       }]	
    });
})

/*function confirm(){
	var name=document.getElementById("name").value;
	if(name==null || name=='') 
			return false;
	 window.returnValue=escape(name)
	 window.close()
	}

function cancel(){
 	window.close()
}*/

function onload(){
	var name='<%=name%>'
	name=unescape(name)
	Ext.getCmp("textName").value=name
	//document.getElementById("name").selected
}
</script>
</head>

	<body topmargin="0" bottommargin="0" leftmargin="0" marginheight="0" marginwidth="0" rightmargin="0">
		<!--  <font size=2>文件名称：</font>
		<input id="name" maxlength="40" name="name" size="40">
		<input onclick="confirm()" type="button" value="确定">
		<input onclick="cancel()" type="button" value="取消"> -->
		<div id="newText"></div>
	</body>
</html>
