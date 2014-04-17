<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="com.klspta.console.user.User"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String yw_guid=request.getParameter("yw_guid");
String flag=request.getParameter("flag");
Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
String userId = ((User) principal).getUserID(); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>地图查看 flex调用js中 locationChange(bbox);，js 调用flex中setMapAlpha(0.1);</title>
    <%@ include file="/base/include/ext.jspf" %>
 	<%@ include file="/base/include/restRequest.jspf" %>
<style >
    #tip
    {
        position: absolute;
        right: 0px;
        bottom: 0px;
        height: 0px;
        width: 180px;
        border: 1px solid #CCCCCC;
        background-color: #eeeeee;
        padding: 1px;
        overflow: hidden;
        display: none;
        font-size: 12px;
        z-index: 1001;
    }
    #tip p
    {
        padding: 6px;
    }
    #tip h1, #detail h1
    {
        font-size: 14px;
        height: 25px;
        line-height: 25px;
        background-color: #0066CC;
        color: #FFFFFF;
        padding: 0px 3px 0px 3px;
        filter: Alpha(Opacity=100);
    }
    #tip h1 a, #detail h1 a
    {
        float: right;
        text-decoration: none;
        color: #FFFFFF;
    }

.winTitle{background:#9DACBF; height:20px; line-height:20px} 
.winTitle .title_left{font-weight:bold; color:#FFF; padding-left:5px; float:left} 
.winTitle .title_right{float:right} 
.winTitle .title_right a{color:#000; text-decoration:none} 
.winTitle .title_right a:hover{text-decoration:underline; color:#FF0000} 

 
</style>
  </head>
  

  <script type="text/javascript">
  window.onload=function()
{
	
	 divTip = document.createElement("div");
	 divTip.id="tip"; 
	 divTip.innerHTML="<div id='tixing' class='winTitle' style='width: 100%; height: 10%;'><span class='title_left'>事件提醒</span><span class='title_right'><a href='javascript:closeWindow()' id='closeButton' >关闭</a></span></div><div id='jicui' style='width: 100%; height: 90%;'></div>";; 
	 divTip.style.height='0px';
	 divTip.style.bottom='0px';
	 divTip.style.width = '300px'; 
	 divTip.style.position='absolute';
	   
	 document.body.appendChild(divTip);
	 //showWindow = document.getElementById("lower");
	 //showWindow.appendChild(divTip);
	 show();
	
	 
}
var t;
function chooseMenu2(menu23)
{	
	var childMenus= top.content.left. document.getElementById("menuLeftDiv").children;
	
	if(childMenus.length==0)
	{	//直到右边页面加载完成	
		t=setTimeout("chooseMenu2('"+menu23+"')",200);
	}
	else
	{
		//alert(top.content.left.document.getElementById("menuLeftDiv").children[0].children[1].children[0].id);
		//alert(top.content.left.document.getElementById("menuLeftDiv").children[0].innerHTML);
		//alert(top.content.left.document.getElementById("img_"+menu23).parentNode.outerHTML);
		var obj=top.content.left.document.getElementById("img_"+menu23).parentNode;
		if(obj.parentNode.parentNode.id!="menuLeftDiv")
		{			
			var menuParent=obj.parentNode;		
			menuParent.style.display="block";			
		}
		if( document.createEvent )
		{
			var evObj = document.createEvent('MouseEvents');
			evObj.initEvent( 'onclick', true, true );
			obj.dispatchEvent(evObj);
		}
		else if( document.createEventObject )
		{
			
			obj.fireEvent('onclick');
		}	
	
		clearTimeout(t);
	}
	
	
}
function chooseMenu(menu1,menu23)
{
	var menus= top.menu.document.getElementById("menu0_cm").children;

	for(var i=0;i<menus.length;i++)
	{
		if(menus[i].innerHTML==menu1)
		{
			var obj=menus[i];
			if( document.createEvent )
			{
				var evObj = document.createEvent('MouseEvents');
				evObj.initEvent( 'onclick', true, true );
				obj.dispatchEvent(evObj);
			}
			else if( document.createEventObject )
			{
				
				obj.fireEvent('onclick');
			}			
			//top.content.left.isload();
			chooseMenu2(menu23);
			
			
			
		}
	}
}
	
function show(){
		//var obj = document.getElementById("tip"); 
		if (parseInt(divTip.style.height)==0) 
		{   
		    divTip.style.display="block"; 
			handle = setInterval("changeH('up')",1); 
		}else 
		{ 
			handle = setInterval("changeH('down')",1) 
		} 
	}


function changeH(str){
		//var obj=this.document.all?this.document.all["tip"] : this.document.getElementById("tip"); 
		if(str=="up") 
		{ 
			if (parseInt(divTip.style.height)>200) 
				clearInterval(handle); 
			else 
			divTip.style.height=(parseInt(divTip.style.height)+8).toString()+"px"; 
		} 
		if(str=="down") 
		{ 
			if (parseInt(divTip.style.height)<8) 
			{ 
				clearInterval(handle); 
				divTip.style.height="18px"; 
			} else {
				divTip.style.height=(parseInt(divTip.style.height)-8).toString()+"px"; 
			}
		} 
}
//关闭、展开窗口
function closeWindow(){ 
  var tt=document.getElementById("closeButton").innerText;
  if(divTip.style.height=="18px"){
  	 document.getElementById("closeButton").innerText="关闭";
  	 handle = setInterval("changeH('up')",5); 
  }else{
  	 document.getElementById("closeButton").innerText="展开";
 	 handle = setInterval("changeH('down')",5) ;
  }
  
} 
  
  
  
  
  
  
Ext.onReady(function(){
	border =new Ext.Viewport( 
		{
		layout:"border",
		items:[
			    center = new Ext.Panel({ 
                region: 'center', // a center region is ALWAYS required for border layout
                contentEl: 'center',
                collapsible: false,
                margins:'0 0 0 0'
            }),
            { 
                region: 'east', // a center region is ALWAYS required for border layout
                contentEl: 'east',
                id:'east-panel',
                collapsible: true,
                margins:'0 0 0 0',
                width: 300,
                minSize: 0,
                maxSize: 300,
                collapsed: true,
                title:''
            },
			{
                    region:'west',
					id:'west-panel',
                    contentEl: 'mapTree',
                    split:true,
                    width: 200,
                    minSize: 0,
                    maxSize: 300,
                    collapsible: true,
                    title:'图层树',
                    collapsed: true,
                    margins:'0 0 0 0'
                }
			  ]
		}
	);
	
	// 加载弹出框内容
	putClientCommond("remind", "getUserRemind");
	putRestParameter("userId", "<%=userId%>");
	myData = restRequest();
	store = new Ext.data.JsonStore({
		proxy: new Ext.ux.data.PagingMemoryProxy(myData),
		remoteSort: true,
		fields:[
			{name:'SXM'},
			{name:'SXZ'},
			{name:'MENUID'}
		]
	});
	
	store.load({params:{start:0, limit:5}});
	
	var width = 280;
	var height = 184;
	grid = new Ext.grid.GridPanel({
		store: store,
		columns: [
			new Ext.grid.RowNumberer(),
				{header: '待办类型',dataIndex:'SXM',width: width*0.3, sortable: true},
			 	{header: '待办数量',dataIndex:'SXZ', width: width*0.65, sortable: true},
			 	{header: 'MENUID',dataIndex:'MENUID', hidden:true,width: width*0.65, sortable: true}
			],
			stripeRows: true,
			height: height,
			stateful: true,
			buttonAlign:'center',
			bbar: new Ext.PagingToolbar({
				pageSize: 5,
				store: store
				//displayInfo: true,
				//displayMsg: '共{2}条，当前为：{0} - {1}条',
				//emptyMsg: "无记录",
				//	plugins: new Ext.ux.ProgressBarPager()
			}),
			listeners:{
		       'rowdblclick' : function(grid, rowIndex, e){ 
		          var record = grid.store.getAt(rowIndex);
		          var menuName = record.get('SXM'); 
		          var menuid = record.get('MENUID');
		          var menu1,menu23;
		          chooseMenu(menuName,menuid);
		       	  //window.location.href = "<%=basePath%>" + location;
		       }
         	}
			
		});
		grid.render('jicui');
	
}
);
</script>
	<body>
		<iframe id="mapTree"  name="mapTree"  style="width: 100%; height: 100%;overflow: auto;" src="<%=basePath%>base/fxgis/framework/mapTree.jsp?expanded=false"></iframe>
		<iframe id="center" name="center"  style="width: 100%; height:<%=yw_guid==null?100:100%>%;overflow: auto;border: 0px" src="menu.jsp?yw_guid=<%=yw_guid%>&flag=<%=flag%>"></iframe>
		<iframe id="east"  name="east"  style="width: 100%; height: 100%;overflow: auto;" src=""></iframe>
	</body>
</html>
