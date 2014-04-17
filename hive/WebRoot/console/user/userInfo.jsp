<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.klspta.console.user.User"%>
<%@page import="com.klspta.console.ManagerFactory"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
	String userId=request.getParameter("userId");
    User userBean=ManagerFactory.getUserManager().getUserWithId(userId);
    String extPath = basePath + "base/thirdres/ext/";
    if(userBean==null)
     {
     	userBean=new User();
     	userBean.setUserID(userId);
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
		<style type=text/css>
	        .upload-icon {
	            background: url('<%=extPath%>examples/shared/icons/fam/image_add.png') no-repeat 0 0 !important;
	        }
    	</style>
			
			<script>
 Ext.onReady(function() {
    Ext.QuickTips.init();
    var form = new Ext.form.FormPanel({
        renderTo: 'userInfo',
        autoHeight: true,
        frame:true,
        bodyStyle:'padding:5px 0px 0',
        width: 300,
        url:"<%=basePath%>service/rest/userAction/saveUser?userId=<%=userId%>",
        defaults: {
            anchor: '95%',
            allowBlank: false,
            msgTarget: 'side'
        },
        items   : [
   			{
                xtype: 'textfield',
                id      : 'fullName',
                value:'<%=userBean.getFullName()%>',
                fieldLabel: '姓名'
            },
            {
                xtype     : 'textfield',
           		id:'userName',
                value:'<%=userBean.getUsername()%>',
                fieldLabel: '登录账号'            
            },
             {
                xtype: 'textfield',
                id      : 'password',
                value:'<%=userBean.getPassword()%>',
                fieldLabel: '密码'
            },
            {
                xtype: 'textfield',
                id      : 'emailAddress',
                value:'<%=userBean.getEmail()%>',
                fieldLabel: 'email'
            },
            {
                xtype: 'textfield',
                id      : 'officePhone',
                value:'<%=userBean.getOfficephone()%>',
                fieldLabel: '办公电话'
            },
            {
                xtype: 'textfield',
                id      : 'mobilePhone',
                value:'<%=userBean.getMobilephone()%>',
                fieldLabel: '手机'
            },
            {
                xtype: 'numberfield',
                id      : 'sort',
                value:'<%=userBean.getSort()%>',
                fieldLabel: '排序号'
            },           
            {
                xtype: 'textfield',
                id      : 'xzqh',
                value:'<%=userBean.getXzqh()%>',
                fieldLabel: '行政区划代码'
            }           
        ],
        buttons: [
            {
                text   : '保存',
                handler: function() {
						form.form.submit({ 
							waitMsg: '正在保存,请稍候... ', 		
							success:function(){ 
								
								Ext.Msg.alert('提示','保存成功。');
								parent.grid.location.reload();
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

	function change(){
		   var imagepath = document.getElementById("file").value;
	       if(imagepath!=''||imagepath!=null){
	    	   var length=imagepath.length;
	    	   if(length>4){
	    	   var suffix=imagepath.substr(length-4,length);
	    	       if(suffix!='.jpg'){
	    	       alert("请选择jpg图片文件！否则，上传失败！");
	    	       document.getElementById("submit").disabled = false;  
	    	          }
	    	   }else{
	    		   alert("请选择jpg图片文件！否则，上传失败！");
	    		   document.getElementById("submit").disabled = false;  
	    		   
	    	   }
	       }
	       document.getElementById("form1").submit();
	}
	</script>
	</head>
	<body bgcolor="#FFFFFF">
	  <div id="userInfo" />
	  <div >
	         <form action="<%=basePath%>service/rest/sign/setSign?userId=<%=userId%>" id="form1" name="form1" encType="multipart/form-data"  method="post" target="hidden_frame" >
    	    <font style="margin-left:70px; vertical-align: middle;width: 64px" >图片上传</font> 
    	     <input type="file" id="file" name="file" style="width:240" onchange="change();" /><br>
    	     <img  width="60" height="25" src="<%=basePath%>signservice?userId=<%=userId%>"/>	
             </form> 
      		
      </div>
	</body>
</html>