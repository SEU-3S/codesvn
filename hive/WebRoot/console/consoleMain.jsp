<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="com.klspta.console.user.User"%>
<%@page import="com.klspta.base.util.UtilFactory"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
     Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userId=null;
		String fullName="";
		if (principal instanceof User) {
		   userId = ((User)principal).getUserID();
		   fullName = ((User)principal).getFullName();
		} else {
		    userId =null;
		    fullName = principal.toString();
		}
   String date=UtilFactory.getDateUtil().getChineseDate(new Date());
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>执法监察系统</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<%@ include file="/base/include/ext.jspf" %>>
	<style type="text/css">
	html, body {
        font:normal 12px verdana;
        margin:0;
        padding:0;
        border:0 none;
        overflow:hidden;
        height:100%;
    }
	.x-panel-body p {
	    margin:5px;
	}
    .x-column-layout-ct .x-panel {
        margin-bottom:5px;
    }
    .x-column-layout-ct .x-panel-dd-spacer {
        margin-bottom:5px;
    }
    .settings {
        background-image:url(../shared/icons/fam/folder_wrench.png) !important;
    }
    .nav {
        background-image:url(../shared/icons/fam/folder_go.png) !important;
    }
    </style>
	<script type="text/javascript">	
	
	Ext.ux.TabCloseMenu = function(){
    var tabs, menu, ctxItem;
    this.init = function(tp){
        tabs = tp;
        tabs.on('contextmenu', onContextMenu);
    }

    function onContextMenu(ts, item, e){
        if(!menu){ // create context menu on first right click
            menu = new Ext.menu.Menu([{
                id: tabs.id + '-close',
                text: '关闭标签',
                handler : function(){
                    tabs.remove(ctxItem);
                }
            },{
                id: tabs.id + '-close-others',
                text: '关闭其他标签',
                handler : function(){
                    tabs.items.each(function(item){
                        if(item.closable && item != ctxItem){
                            tabs.remove(item);
                        }
                    });
                }
            },{
                id: tabs.id + '-close-all',
                text: '关闭全部标签',
                handler : function(){
                   document.location.reload();
                    tabs.items.each(function(item){
                        if(item.closable){
                            tabs.remove(item);
                        }
                    });
                }
            }]);
        }
        ctxItem = item;
        var items = menu.items;
        items.get(tabs.id + '-close').setDisabled(!item.closable);
        var disableOthers = true;
        tabs.items.each(function(){
            if(this != item && this.closable){
                disableOthers = false;
                return false;
            }
        });
        items.get(tabs.id + '-close-others').setDisabled(disableOthers);
        var disableAll = true;
        tabs.items.each(function(){
            if(this.closable){
                disableAll = false;
                return false;
            }
        });
        items.get(tabs.id + '-close-all').setDisabled(disableAll);
        menu.showAt(e.getPoint());
      }
    };
  var left;
  Ext.onReady(function(){
	left = new Ext.tree.TreePanel({
		region:"west",
		id:'west',
		title:"系统管理",
		collapsible:true,
		split:true,
		containerScroll:true,
		autoScroll:true,
		width:200,
		listeners:{
			click:function(n){
				var url=n.attributes.url;
				var id =n.attributes.id;
				if(url){
					if(center.getItem(id)){
						//表示标签已打开，则激活
						center.setActiveTab(id);
					}else{
					var ifr = document.createElement("IFRAME"); 
					document.body.appendChild(ifr);  
					 ifr.height='100%';
					  ifr.width='100%';
					 ifr.src = url; 
					ifr.id=id+"_ifr";
						var p =new Ext.Panel({
							title:n.attributes.text,
							id:id,
							contentEl: id+'_ifr',
							closable:true
						});
						center.add(p);
						center.setActiveTab(p);
					}
					
				}
			}
		}
	});
	
	
	
	
	var root = new Ext.tree.TreeNode({id:"1",text:"系统管理",leaf:"false"});
	var role=new Ext.tree.TreeNode({d:"2",text:"机构管理",url:"<%=basePath%>/console/role/roleMain.jsp"});
	var person= new Ext.tree.TreeNode({id:"3",text:"人员管理",url:"<%=basePath%>/console/user/userMain.jsp"});
	
	var map_authority = new Ext.tree.TreeNode({id:"4",text:"地图数据授权",leaf:"false",url:"<%=basePath%>/console/maptree/mapAuthorization/mapAuthorMain.jsp"});
	var menu_authority = new Ext.tree.TreeNode({id:"5",text:"功能菜单授权",leaf:"false",url:"<%=basePath%>/console/menu/menuAuthorization/menuAuthorMain.jsp"});
	var map_manage = new Ext.tree.TreeNode({id:"6",text:"图层数据管理",leaf:"false",url:"<%=basePath%>/console/maptree/mapManage/mapTreeManageMain.jsp"});
	var menu_manage = new Ext.tree.TreeNode({id:"7",text:"功能菜单管理",leaf:"false",url:"<%=basePath%>/console/menu/menuManage/menuManageMain.jsp"});
	var process_manage = new Ext.tree.TreeNode({id:"8",text:"流程模板管理",leaf:"false",url:"<%=basePath%>/console/menu/menuManage/process.jsp"});
	root.appendChild([role,person,map_authority,menu_authority,map_manage,menu_manage,process_manage]);
	left.setRootNode(root);
	
	var center = new Ext.TabPanel({
		region:"center",
		enableTabScroll:true, 
		defaults:{autoScroll:true},
		plugins: new Ext.ux.TabCloseMenu(),		
		items:[{
			title:"欢迎",
			html:"<br>   &nbsp;&nbsp;&nbsp;欢迎使用执法监察系统配置平台，本平台仅限管理员使用，操作请谨慎。",
			id:"index"
		}
		],
		enableTabScroll:true
	});
	center.setActiveTab("index");
	
	
	var vp = new Ext.Viewport({
		layout:"border",
		items:[left,center]
	})
	left.expandAll();
	
})
	</script>
</head>
<body>
  <div style=" position:absolute; width:200px; top:5px; left:830px; z-index:10"><a href="<%=basePath %>j_spring_security_logout" target="_top" style="text-decoration:none; font-size:10; color:#333"><img src="console/images/auth.png" width="16" height="16" /><%=fullName%>,<%=date%></a></div>
</body>
</html>
