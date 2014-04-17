<%@page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="org.apache.commons.fileupload.servlet.*"%>
<%@page import="com.scand.fileupload.*"%>
<%@page import="java.io.*"%>
<%@page import="java.rmi.server.UID"%>
<%@page import="com.klspta.base.util.UtilFactory"%>
<%@page import="com.klspta.base.util.bean.ftputil.AccessoryBean"%>
<%@page import="com.klspta.model.accessory.AccessoryOperation"%>
<%@page import="com.klspta.model.accessory.AccessoryUtil"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setCharacterEncoding("utf-8"); 
String yw_guid = request.getParameter("yw_guid");
    //从配置文件中获取临时文件夹
    String uploadTempFolder = UtilFactory.getConfigUtil().getConfig("SHAPEFILE_PATH");
    String file_id= new UID().toString().replaceAll(":", "-");
    String fileSuffix;
    //校验上传请求
    boolean isMultipart = FileUpload.isMultipartContent(request);
    if (!isMultipart) {
        //out.println("Use multipart form to upload a file!"); 
    } else {
        //String fileId = request.getParameter("sessionId").toString().trim();
        //创建上传句柄
        FileItemFactory factory = new ProgressMonitorFileItemFactory(request, "");
        ServletFileUpload upload = new ServletFileUpload(factory);
        List items = upload.parseRequest(request);
        //处理上传文件
        Iterator iter = items.iterator();
        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();
            if (item.isFormField()) {

            } else {
                //String fieldName = item.getFieldName();
                String fileName = item.getName();							
                fileSuffix = fileName.substring(fileName.lastIndexOf("."));	
                int i2 = fileName.lastIndexOf("\\");
                if (i2 > -1)
                    fileName = fileName.substring(i2 + 1);
                File dirs = new File(uploadTempFolder);
                if (!dirs.isFile()) {
                    dirs.mkdirs();
                }
                String tempFileName=fileName.replaceFirst(fileName,file_id);	
                tempFileName = file_id+fileSuffix;	
                File uploadedFile = new File(dirs, tempFileName);	
                String fullPath = uploadedFile.toString();
				AccessoryBean accessoryBean = new AccessoryBean();
				accessoryBean.setFile_id(file_id);		
				accessoryBean.setParent_file_id("0");	
				accessoryBean.setFile_type("file");		
				accessoryBean.setFile_name(fileName);	
				accessoryBean.setFile_path(tempFileName); 
				accessoryBean.setYw_guid(yw_guid);		
                UtilFactory.getFtpUtil().uploadFile(item.getInputStream(),tempFileName);
                AccessoryOperation.getInstance().insertRecord(accessoryBean);
            }
        }
    }
    String rows = AccessoryUtil.getInstance().getAccessorylistByYwGuid(yw_guid);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>附件</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
      <%@ include file="/base/include/restRequest.jspf" %>
    
	<%@ include file="/base/include/ext.jspf" %>
	<script src="<%=basePath%>base/thirdres/ext/examples/ux/fileuploadfield/FileUploadField.js" type="text/javascript"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>base/thirdres/ext/examples/ux/fileuploadfield/css/fileuploadfield.css" />


<script type="text/javascript">
var grid;
var store;

Ext.onReady(function(){
	 	myData= <%=rows%>;//采用json格式存储的数组 
	    store = new Ext.data.ArrayStore({ 
	    proxy: new Ext.ux.data.PagingMemoryProxy(myData),
			remoteSort:true,
	        fields: [
	           {name: '序号'},
	           {name: '名称'},
	           {name: '下载'}
	        ]
	    });
	    store.load({params:{start:0, limit:7}});
	    
	    var sm = new Ext.grid.CheckboxSelectionModel({
	    	handleMouseDown:Ext.emptyFn,
	        listeners : {  
	            "rowdeselect" : {  
	                fn : function(e, rowIndex, record) {  
	                	var records=grid.getSelectionModel().getSelections();
						if(records.length<1){ 						
							Ext.getCmp("multipleDown").disable(); 
							Ext.getCmp("multipleDel").disable(); 
						}
	                }  
	            },  
	            "rowselect" : {  
	                fn : function(e, rowIndex, record) {  
						var records=grid.getSelectionModel().getSelections();
						if(records.length>0){
							Ext.getCmp("multipleDel").disable().enable() ;
							Ext.getCmp("multipleDown").disable().enable() ;   
	                	}
	            	}
	            } 
	        } 
	    });  
	    
	    grid = new Ext.grid.GridPanel({
	    sm:sm,
	    store: store,
	    columns: [
	        sm,
	        {header: '序号',dataIndex:'序号', width: 170, hidden:true},  
	        {header: '名称',dataIndex:'名称', width: 510, sortable: true}, 
	        {header: '下载', dataIndex:'下载',width: 40,renderer:down}
	    ],
	    stripeRows: true,
	    height: 275, 
	    stateful: true,
	    stateId: 'grid',
	    buttonAlign:'center', 
	    bbar: new Ext.PagingToolbar({
	        pageSize: 7,
	        buttons: [{ 
	     		text:'批量下载',		
	     		id:'multipleDown',
	     		disabled:true,   
	     		handler: downloadSelectedFile
	     	},{
	     		text:'批量删除',		
	     		id:'multipleDel', 
	     		disabled:true, 
	     		handler: deleteSelectedFile
	     	}],
	        store: store,
	        displayInfo: true,
	        displayMsg: '共{2}条，当前为：{0} - {1}条',
	        emptyMsg: "无记录",
	     plugins: new Ext.ux.ProgressBarPager()
	     })
	     });
	  grid.render('mygrid_container'); 
     		
	
});


function getSelectedFile(){
  var ids="";
  if (grid.getSelectionModel().hasSelection()){
     var records=grid.getSelectionModel().getSelections();
	 for(var i=0;i<records.length;i++){
	   if(i==records.length){
	      ids=ids+records[i].get('序号')+"/"+records[i].get('名称');  
	   }else{
	      ids=ids+records[i].get('序号')+"/"+records[i].get('名称')+"//";   
	   }
     }
  }
  return ids; 
}


function down(id){ 
 	return "<a href='#' onclick='handle("+id+",\"down\");return false;'><img src='model/accessory/images/download.png' width='20' height='20' alt='下载'></a>";  
}


function del(id){
 	return "<a href='#' onclick='handle("+id+",\"del\");return false;'><img src='model/accessory/images/delete.png' width='18' height='18' alt='删除'></a>"; 
}


function handle(id,name){
	if(name == "down"){  
		if (confirm("确定下载该文件吗?") == true){
		  	var ids = store.getAt(id).get('序号')+"/"+store.getAt(id).get('名称')+"//";  
			var path = "<%=basePath%>";   
			var actionName = "accessory";        
			var actionMethod = "downloadAccessory";          
			var parameter = "yw_guid="+escape(escape("<%=yw_guid%>"))+"&ids="+escape(escape(ids))+"&uploadTempFolder=<%=uploadTempFolder%>";               
			var myData = ajaxRequest(path, actionName, actionMethod, parameter); 
			window.open("download2Client.jsp?file_path="+myData);   
		} 
	}else if(name == "del"){ 
		if (confirm("确定删除该文件吗?") == true){ 
		  	var ids = store.getAt(id).get('序号')+"/"+store.getAt(id).get('名称')+"//";   
			var path = "<%=basePath%>";   
			var actionName = "accessory";        
			var actionMethod = "deleteAccessory";             
			var parameter = "yw_guid="+escape(escape("<%=yw_guid%>"))+"&ids="+escape(escape(ids));                  
			var mapList = ajaxRequest(path, actionName, actionMethod, parameter);
		}
	}
}


function downloadSelectedFile(){
  var ids = getSelectedFile();
  if(ids != ""){
	if (confirm("确定要下载选择的文件吗?") == true){   
		var path = "<%=basePath%>";    
		var actionName = "accessory";          
		var actionMethod = "downloadAccessory";          
		var parameter = "yw_guid="+escape(escape("<%=yw_guid%>"))+"&ids="+escape(escape(ids))+"&uploadTempFolder=<%=uploadTempFolder%>";               
		var myData = ajaxRequest(path, actionName, actionMethod, parameter); 
		window.open("download2Client.jsp?file_path="+myData);   
	}
  }
}

function deleteSelectedFile(){ 
  var ids = getSelectedFile();	
  if(ids != ""){ 
	if (confirm("确定删除该文件吗?") == true){  
		var path = "<%=basePath%>";   
		var actionName = "accessory";        
		var actionMethod = "deleteAccessory";         
		var parameter = "yw_guid="+escape(escape("<%=yw_guid%>"))+"&ids="+escape(escape(ids));                  
		var mapList = ajaxRequest(path, actionName, actionMethod, parameter); 
	     var records=grid.getSelectionModel().getSelections();
		 for(var i=0;i<records.length;i++){
		 	var selectedRow = grid.getSelectionModel().getSelected();
		   	if(selectedRow){
		      grid.getStore().remove(selectedRow); 
		    }
		 }
	}
  }
}
</script> 
<body >
  <div id="mygrid_container" style="width:100%;margin-left:2px; "></div> 
</body>
</html>
