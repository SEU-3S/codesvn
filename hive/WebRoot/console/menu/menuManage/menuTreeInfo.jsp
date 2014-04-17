<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.klspta.console.menu.MenuBean"%>
<%@page import="com.klspta.console.ManagerFactory"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
    String extPath = basePath + "base/thirdres/ext/";

	String treeId=request.getParameter("treeId");
	
	MenuBean menuBean=ManagerFactory.getMenuManager().getMenuBeanByMenuId(treeId);
	if(menuBean==null)
	{	menuBean=new MenuBean();
		menuBean.setMenuId(treeId);
		menuBean.setMenuType(request.getParameter("menuType"));
		menuBean.setParentId(request.getParameter("parentTreeId"));
	}
 %>
 
 	

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Insert title here</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
			<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<%@ include file="/base/include/ext.jspf" %>
			 <link rel="stylesheet" type="text/css" href="<%=extPath%>examples/ux/fileuploadfield/css/fileuploadfield.css"/>
			    <script type="text/javascript" src="<%=extPath%>examples/ux/fileuploadfield/FileUploadField.js"></script>
	<style type=text/css>
        .upload-icon {
            background: url('<%=extPath%>examples/shared/icons/fam/image_add.png') no-repeat 0 0 !important;
        }
    </style>
			
			<script>
 Ext.onReady(function() {
    Ext.QuickTips.init();
    
    var form = new Ext.form.FormPanel({
        renderTo: 'mapTreeInfo',
        //fileUpload: true,
        autoHeight: true,
        title   : '<%=menuBean.getMenuName() %>信息',
        frame:true,
        bodyStyle:'padding:5px 0px 0',
        width: 500,
        url:"<%=basePath%>service/rest/menuAction/save?id=<%=treeId%>&parentId=<%=menuBean.getParentId()%>",
        defaults: {
           anchor: '95%',
            allowBlank: false,
            msgTarget: 'side'
        },
        items   : [
   			{   xtype: 'textfield',
                id      : 'menuName',
                value:'<%=menuBean.getMenuName()%>',
                fieldLabel: '菜单名称'
            },
            {   xtype: 'numberfield',
                id      : 'sort',
                value:'<%=menuBean.getSort() %>',
                fieldLabel: '排序号'
            },
            {
            	xtype: 'fieldset',
                title: '详细',
                collapsible: true,
                items: [
                	{
			            xtype: 'fileuploadfield',
			            id: 'icon',
			            emptyText: '请选择图标...',
			            fieldLabel: '图标',
			            value:'<%=menuBean.getIcon() %>',
			            name: 'icon',
			            buttonText: '',
			            buttonCfg: {
			                iconCls: 'upload-icon'
			            }
			        },
			        {
			        	xtype:'compositefield',
			        	msgTarget: 'under',
			        	items:[
                	{
		                xtype: 'textfield',
		                id      : 'url_center',
		                value:'<%=menuBean.getUrl_center() %>',
		                fieldLabel: '中部URL'
            		},{
            			xtype:'checkbox',
            			boxLabel:'是否选中',
            			checked:<%=menuBean.getCenter().equals("1")?true:false%>,
            			id:'center'
            		}]
            		},
            		{
			        	xtype:'compositefield',
			        	msgTarget: 'under',
			        	items:[
                	{
		                xtype: 'textfield',
		                id      : 'url_east',
		               	value:'<%=menuBean.getUrl_east() %>',
		                fieldLabel: '右侧URL'
            		},{
            			xtype:'checkbox',
            			boxLabel:'是否选中',
            			checked:<%=menuBean.getEast().equals("1")?true:false%>,
            			id:'east'
            		}]
            		},
            		{
			        	xtype:'compositefield',
			        	msgTarget: 'under',
			        	items:[
                	{
		                xtype: 'textfield',
		                id      : 'url_local',
		               	value:'<%=menuBean.getUrl_local() %>',
		                fieldLabel: '本地URL'
            		},{
            			xtype:'checkbox',
            			boxLabel:'是否选中',
            			checked:<%=menuBean.getLocal().equals("1")?true:false%>,
            			id:'local'
            		}]
            		},
            		{
		                xtype: 'textfield',
		                id      : 'handler',
		                value:'<%=menuBean.getHandler() %>',
		                fieldLabel: '点击事件'
            		}
                ]
            }
        ],
        buttons: [
            {
                text   : '保存',
                handler: function() {
					form.form.submit(
					{ 
						waitMsg: '正在保存,请稍候... ', 
						params: form.getForm().getFieldValues(),
						success:function(){ 
						Ext.Msg.alert('提示','保存成功。');
						parent.menuTree.location.reload()
						}, 
						failure:function(){ 
						Ext.Msg.alert('提示','保存失败，请稍后重试或联系管理员。');
						} 
					});
                }
            },
            
            {
                text   : '刷新',
                handler: function() {
                   document.location.reload()
                }
            }
        ]
    });
    
});

	</script>
	</head>
	<body bgcolor="#FFFFFF"">
	  <div id="mapTreeInfo" />
	</body>
</html>