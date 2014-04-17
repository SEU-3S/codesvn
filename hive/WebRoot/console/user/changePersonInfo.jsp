<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="com.klspta.console.ManagerFactory"%>
<%@page import="com.klspta.console.user.User"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String fullName;
    String userId=null;  
    String emailAddress; 
    String officePhone;
    String mobilePhone; 

    if (principal instanceof User) {
    	User user = (User) principal;
        fullName = user.getFullName();
        userId = ((User) principal).getUserID();
        emailAddress = user.getEmail();
        officePhone = user.getOfficephone();
        mobilePhone = user.getMobilephone();
    } else {
        fullName = principal.toString();
        emailAddress = principal.toString();        
        officePhone = principal.toString(); 
 		mobilePhone = principal.toString();
    }
%> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'password.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<%@ include file="/base/include/ext.jspf"%>	

<!-- add by 李如意 Description:用户基本信息修改 Date:2011-06-23 -->		
<script type="text/javascript">
var username='<%=fullName%>';
  

var officePhone='<%=officePhone%>';  

 
Ext.onReady(function(){
Ext.QuickTips.init();   
    var tabs = new Ext.TabPanel({
        renderTo: document.body,
        activeTab: 0,
        width:298,
        height:200,    
        plain:true,
        defaults:{autoScroll: true},
        items:[{
	                title: '基本信息设置',
	                layout:"form",
	                xtype:'form',
	                id:"changeBaseInfo",
	                url:'<%=basePath%>changeBaseInfo.do?method=changeBaseInfo&userId=<%=userId%>',         
	                defaults:{width:150}, 
	                labelWidth:70,
	               	labelAlign:"right",  
	                padding:"5 0 0 10px",                                    
	                items:[{
	                	fieldLabel:"姓 名",
	                	id:"userName", 
	                	name:"username", 
	                	value:username,        
             			xtype:'textfield',
             			hidden:true	 
	                },{
	                	fieldLabel:"Email ",  
	                	id:"emailAddress", 
	                	name:"emailAddress", 
	                	value:'<%=emailAddress%>',              			
             			xtype:'textfield',        
             			allowBlank:false,
     					blankText :'邮箱不能为空',  
             			regex:/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, 
             			regexText:"请输入正确邮箱格式，例如：123456@321.com" 
             				                	              	
	                },{
	                	fieldLabel:"办公电话", 
	                	id:"officePhone",  
	                	name:"officePhone",
	                	value:'<%=officePhone%>',    
             			xtype:'textfield', 
             			allowBlank:false,
     					blankText :'请输入办公电话！'                				                	                	
	                },{
	                	fieldLabel:"手 机", 
	                	id:"mobilePhone", 
	                	name:"mobilePhone",
	                	value:'<%=mobilePhone%>',     
             			xtype:'textfield',
             			allowBlank:false,
     					blankText :'手机号码不能为空',                			
             			regex:/^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\d{8}$/,  
             			regexText:"手机号码必须为13,15,187,188,189等开头的11位数字！"  	                 	                	
	                }],
	                buttons:[{
		                	text:"保存", 	 
		                 	handler: function (){
			                   	Ext.getCmp("changeBaseInfo").getForm().submit({   	 		             	
				             		 waitMsg: '正在保存,请稍候... ',  
							         success:function(){  
									 	Ext.Msg.alert('提示','保存成功。')  
						             },  
							         failure:function(){  
							         	Ext.Msg.alert('提示','请输入完整信息！')   
							         } 			             	
				             	});     	 		             	
	                    } 
	                },{
		                	text:"重置",   
	                 		handler: function (){
								Ext.getCmp("changeBaseInfo").getForm().reset();   
                    		} 
	                }]
                },{ 
                	title: '密码修改',
                	layout:"form", 
                	xtype:'form',   
                	id:"changePassword",  
                	url:'<%=basePath%>passChange.do?method=changePassword&userId=<%=userId%>',       
	                defaults:{width:160},  
	                labelWidth:110, 
	                labelAlign:"right", 
	                padding:"5 0 0 10px",  
	                items:[{
	                	 fieldLabel:"姓 名",
	                	 id:"username1", 
	                	 name:"username1",
	                     value:username,        
             			 xtype:'textfield',
 						 hidden:true						
	                },{ 
	                	fieldLabel:"请输入旧密码" ,   
	                	xtype:'textfield',  
	                	name:"oldpass", 
	                	id:"old",  
	                	inputType:'password',
	                	allowBlank:false,
     					blankText :'请输入旧密码！'                
	                },{
	                	fieldLabel:"请输入新密码" ,
	                	xtype:'textfield',  
	                	name:"newpass",
	                	id:"new",
	                	inputType:'password',
	                	allowBlank:false,
     					blankText :'请输入新密码！' 	                	                 	
	                },{
	                	fieldLabel:"请再次输入新密码",
	              	 	xtype:'textfield',   
	                	name:"passensure",
	                	id:"ensure", 
	                	inputType:'password',
	                	allowBlank:false,
     					blankText :'请再次输入新密码！' 	                	                 	
	                }],
	                	                
      			 buttons:[{  
                	text:"保存", 
                			                	 
					handler:function(){
						if(Ext.getCmp('old').getValue() == ''){ 
							Ext.Msg.alert('提示','请输入原密码！'); 
							return; 
						}
						if(Ext.getCmp('new').getValue() == ''){ 
							Ext.Msg.alert('提示','请输入新密码！'); 
							return; 
						}
						if(Ext.getCmp('ensure').getValue() == ''){ 
							Ext.Msg.alert('提示','请再次确认新密码！'); 
							return; 
						}					
						if(Ext.getCmp('new').getValue() != Ext.getCmp('ensure').getValue()){
							Ext.Msg.alert('提示','新密码两次输入不一致！'); 
							return; 
						}
						Ext.Ajax.request({
							url:'<%=basePath%>passChange.do'
							,method:'post'
							,params:{
									method:'changePassword' 
									,userId:'<%=userId%>'
									,oldpass:Ext.getCmp('old').getValue()
									,newpass:Ext.getCmp('new').getValue()
								}
									,success:function(response){
												Ext.Msg.alert('提示',response.responseText);
											}
									,failure:function(response){
												Ext.Msg.alert('提示',response.responseText);
											}
						});
					}

                 		   
               },{
		                	text:"重置",   
	                 		handler: function (){
						    Ext.getCmp("changePassword").getForm().reset();  
                    	} 
	                }]   
            }]
    });
});
</script>	

<body>
</body>
</html>