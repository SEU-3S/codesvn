<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@page import="com.klspta.console.ManagerFactory"%>
<%@ taglib uri="/WEB-INF/taglib/label.tld" prefix="common"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
    + request.getServerPort() + path + "/";
    String gridPath = basePath + "base/thirdres/dhtmlx/dhtmlxGrid";
    String extPath = basePath + "base/thirdres/ext/"; 
    String rows=ManagerFactory.getUserManager().getExtAllUserInfoJson();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>人员管理</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<%@ include file="/base/include/restRequest.jspf"%>
		<%@ include file="/base/include/ext.jspf" %>
		<script type="text/javascript" src="<%=extPath%>/examples/ux/PagingMemoryProxy.js"></script>
		<script type="text/javascript" src="<%=extPath%>/examples/ux/ProgressBarPager.js"></script>
		<script type="text/javascript">
		var myData;
		 var grid;
Ext.onReady(function(){
	myData= <%=rows%>;//采用json格式存储的数组
    // create the data store
    var store = new Ext.data.ArrayStore({
    proxy: new Ext.ux.data.PagingMemoryProxy(myData),
		remoteSort:true,
        fields: [
           {name: '序号'},
           {name: '姓名'},
           {name: '登录账号'},
           {name: '密码'},
           {name: 'eMail'},
           {name: '办公电话'},
           {name: '手机'},
           {name: '行政区划'},
           {name: '修改'},
           {name: '删除'}
        ]
    });
    
    store.load({params:{start:0, limit:15}});
    
     grid = new Ext.grid.GridPanel({
        store: store,
        columns: [
            {header: '序号', width: 40, sortable: true},
            {header: '姓名', width: 80, sortable: true},
            {header: '登录账号', width: 70, sortable: true},
            {header: '密码', width: 50, sortable: true},
            {header: 'eMail', width: 100, sortable: false},
            {header: '办公电话', width: 100, sortable: true},
            {header: '手机', width: 90, sortable: true},
            {header: '行政区划', width: 90, sortable: true},
            {header: '修改', width: 40, sortable: false, renderer: modify}, 
            {header: '删除', width: 50, sortable: false, renderer: del}          
        ],
         tbar:[
		        	{xtype:'label',text:'快速查找:',width:60},
		        	{xtype:'textfield',id:'keyword',width:450,emptyText:'请输入关键字进行查询'},
		        	{xtype: 'button',text:'查询',handler: query}
		        ],
        stripeRows: true,
        height: 480,
        title: '系统人员列表',
        // config options for stateful behavior
        stateful: true,
        stateId: 'grid',
        bbar: new Ext.PagingToolbar({
        pageSize: 15,
        store: store,
        displayInfo: true,
            displayMsg: '共{2}条，当前为：{0} - {1}条',
            emptyMsg: "无记录",
        plugins: new Ext.ux.ProgressBarPager()
        }),
                buttons: [{
            text: '新增',handler: createUser
        }]
    });
    
    grid.render('mygrid_container'); 
}
)
/*处理删除操作 add by 郭 2011-1-20*/
function del(id){
 return "<span style='cursor:pointer;' onclick='delteUser(\""+id+"\")'><img src='base/form/images/delete.png' alt='删除'/></span>";
}
/*处理查看操作 add by 郭 2011-1-20*/
function modify(id){
 return "<span style='cursor:pointer;' onclick='modifyUser(\""+id+"\")'><img src='base/form/images/conf.png' alt='修改'/></span>";
}
function createUser(){
parent.info.location.href="userInfo.jsp?userId="+newGuid();
parent.parent.Ext.getCmp('west').collapse();
}
function modifyUser(id){
var userId=id;
//myData[id][9]
parent.info.location.href="userInfo.jsp?userId="+userId;
parent.parent.Ext.getCmp('west').collapse();

}
/*查询功能*/
function query(){

        var keyWord=Ext.getCmp('keyword').getValue();
         putClientCommond("userAction", "getuser");
         putRestParameter("keyWord",escape(escape(keyWord)));
         myData = restRequest(); 
         var store = new Ext.data.JsonStore({
              proxy: new Ext.ux.data.PagingMemoryProxy(myData),
              remoteSort:true,
             fields: [
           {name: 'SORT'},
           {name: 'FULLNAME'},
           {name: 'USERNAME'},
           {name: 'PASSWORD'},
           {name: 'EMAILADDRESS'},
           {name: 'OFFICEPHONE'},
           {name: 'MOBILEPHONE'},
           {name: 'XZQH'},
           {name: 'ID'},
           {name: 'XZQH'}
              ]
        });
        grid.reconfigure(store, new Ext.grid.ColumnModel([
          //new Ext.grid.RowNumberer(),        
             {header: '序号', dataIndex:'SORT',width: 40, sortable: true},
            {header: '姓名',dataIndex:'FULLNAME', width: 80, sortable: true},
            {header: '登录账号',dataIndex:'USERNAME', width: 70, sortable: true},
            {header: '密码', dataIndex:'PASSWORD',width: 50, sortable: true},
            {header: 'eMail',dataIndex:'EMAILADDRESS', width: 100, sortable: false},
            {header: '办公电话',dataIndex:'OFFICEPHONE', width: 100, sortable: true},
            {header: '手机',dataIndex:'MOBILEPHONE', width: 90, sortable: true},
            {header: '行政区划',dataIndex:'XZQH', width: 90, sortable: true},
            {header: '修改', dataIndex:'ID',width: 40, sortable: false, renderer: modify}, 
            {header: '删除', dataIndex:'ID',width: 50, sortable: false, renderer: del}        
        ]));
        grid.getBottomToolbar().bind(store);
        store.load({params:{start:0,limit:15}});
}
/*删除 add by 郭 2011-1-20*/
function delteUser(id){
var userId=id;
//myData[id][9];
Ext.MessageBox.confirm('注意', '删除后不能恢复，您确定吗？',function(btn){
  if(btn=='yes'){
  	/*
	var path = "<%=basePath%>";
    var actionName = "userAction";
    var actionMethod = "deleteUser";
    var parameter="userId="+userId;
	var result = ajaxRequest(path,actionName,actionMethod,parameter);
	*/
	putClientCommond("userAction", "deleteUser");
	putRestParameter("userId", userId);
	var result = restRequest();
	
	document.location.reload();
	}
	else{
	return false;
	}
});
  }
  function newGuid(){ 
    var guid = ""; 
    for (var i = 1; i <= 32; i++){ 
        var n = Math.floor(Math.random()*16.0).toString(16); 
        guid += n; 
    } 
    return guid; 
}
</script>
	</head>
	<body  bgcolor="#FFFFFF" topmargin="0" leftmargin="0">
		<div id="mygrid_container" style="width: 700; height: 100%;"></div>
	</body>
</html>