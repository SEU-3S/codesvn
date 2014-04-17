<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.klspta.base.workflow.foundations.deploy.ProcessList"%>
<%@ taglib uri="/WEB-INF/taglib/label.tld" prefix="common"%>
<%
	String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
    + request.getServerPort() + path + "/";
    ProcessList list=new ProcessList();
    String rows = list.getProcessList();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>建设用地列表</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<%@ include file="/base/include/restRequest.jspf"%>
<%@ include file="/base/include/ext.jspf" %>
 <script type="text/javascript">
 var grid;
 Ext.onReady(function(){
	  var form = new Ext.form.FormPanel({
	  renderTo:'import', 
	  frame:true,
	  url: '<%=basePath%>/service/rest/importProcess/uploadFile',
	  width: 674, 
	  labelWidth: 60, 
	  autoHeight: true,
	  fileUpload: true,
	  items: [
	  {
       xtype: 'compositefield',
       items: [
		{ 
	  xtype: 'textfield',  
	  fieldLabel: '文件路径', 
	  width:350, 
	  name: 'file',  
	  inputType: 'file'//文件类型  
	  },{  
      xtype:'button',
	  text: '导入',
	  width: 80,
	  	 handler: function() {
	    var filePath=form.getForm().findField('file').getRawValue();
		var re = /(\\+)/g;  
		var filename=filePath.replace(re,"#"); //对路径字符串进行剪切截取
	    var one=filename.split("#"); //获取数组中最后一个，即文件名
		var two=one[one.length-1]; //再对文件名进行截取，以取得后缀名
		var three=two.split("."); //获取截取的最后一个字符串，即为后缀名
		var last=three[three.length-1];//添加需要判断的后缀名类型
		var tp="zip"; //返回符合条件的后缀名在字符串中的位置
		var rs=tp.indexOf(last.toLowerCase()); //如果返回的结果大于或等于0，说明包含允许上传的文件类型
		if(rs>=0&&filePath!=""){ 
	      form.getForm().submit({  
	          success: function(form, action){  
	          Ext.MessageBox.alert('提示：',action.result.info); 
	          document.location.reload();
	        },  
	        failure: function(form, action){  
	           Ext.Msg.alert('提示：',action.result.info);  
	        }  
	      });
	    }
	    else{
	        Ext.Msg.alert('错误', '文件上传失败');  
	    }  
	   } 
	  }]  
	 }] 
   });
 
	myData= <%=rows%>;//采用json格式存储的数组
    var store = new Ext.data.ArrayStore({
    proxy: new Ext.ux.data.PagingMemoryProxy(myData),
		remoteSort:true,
        fields: [
           {name: '发布号'},
           {name: '名称'},
           {name: '版本'},
           {name: '描述'},
           {name: '发布ID'},
           {name: '查看'},
           {name: '删除'}
        ]
    });   
    store.load({params:{start:0, limit:10}});   

     	grid = new Ext.grid.GridPanel({
        store: store,
        columns: [
            {header: '发布号', width: 120},
            {header: '名称', width: 120, sortable: false},
            {header: '版本', width: 50, sortable: false},
            {header: '描述', width: 300, sortable: false},
            {header: '发布ID', width: 10, sortable: false, hidden: true},
            {header: '查看', width: 40, sortable: false, renderer: view},
            {header: '删除', width: 40, sortable: false, renderer: del}
        ],
        stripeRows: true,
        width:674,
        height: 330,
        stateful: true,
        stateId: 'grid',
        bbar: new Ext.PagingToolbar({
        pageSize: 15,
        store: store,
        displayInfo: true,
            displayMsg: '共{2}条，当前为：{0} - {1}条',
            emptyMsg: "无记录",
        plugins: new Ext.ux.ProgressBarPager()
        })
    });
    
    grid.render('processList');           
}) 

function del(){
  return "<a href='#' onclick='delInfo();return false;'><img src='base/form/images/delete.png' alt='删除'></a>";
}

/*删除 add by 王峰 2011-4-20*/
function delInfo(){
rowIndex = grid.store.indexOf(grid.getSelectionModel().getSelected());
var deploymentId=grid.getStore().getAt(rowIndex).get('发布ID');
Ext.MessageBox.confirm('注意', '将删除工作流模板，并且所有运行中的工作流将全部删除，您确定吗？',function(btn){
    if(btn=='yes'){
    
    	/*
	    var path = "<%=basePath%>";
	    var actionName = "importProcess";
	    var actionMethod = "delProcess";
	    var parameter="deploymentId="+deploymentId;
		var result = ajaxRequest(path,actionName,actionMethod,parameter);
		*/
        putClientCommond("importProcess","delProcess");
        putRestParameter("deploymentId", deploymentId);
		var result=restRequest();
		if(result){ 
			//服务器端数据成功删除后，同步删除客户端列表中的数据
			 var ds =  grid.getStore(); 
			 var selectedRow = grid.getSelectionModel().getSelected(); 
             if (selectedRow){ 
                ds.remove(selectedRow);
             } 
	    }
	    else
	    {
	         Ext.MessageBox.alert(result); 
	    }
	}
	else{
	   return false;
	}
  });
 }
 
function view(id){
 return "<a href='#' onclick='viewDetail("+id+");return false;'><img src='base/form/images/view.png' alt='查看'></a>";
}
function viewDetail(id){
var wfID=myData[id][0]
var resourceName=myData[id][7]
var deploymentId=myData[id][4]
document.location.href='<%=basePath%>model/workflow/processAuthorityIndex.jsp?wfID='+wfID+'&resourceName='+resourceName+'&deploymentId='+deploymentId;
} 
</script>
</head>
  <body  bgcolor="#FFFFFF" topmargin="0" leftmargin="0">
    <div id="import"> <font size="2" color="red">文件类型必须为"ZIP"格式!</font></div>
	<div id="processList" style="width: 100%; height: 100%"></div>
  </body>
</html>