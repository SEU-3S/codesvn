<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="com.klspta.console.user.User"%>
<!-- 引入ext包 -->
<%@ include file="/base/include/ext.jspf"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	String fullName;//全名
	String userName = null;//登陆账号
	String emailAddress;//电子邮件地址
	String officePhone;//办公电话
	String mobilePhone;//移动电话
	String xzqh = null;//行政区划代码
	
	if (principal instanceof User) {
		fullName = ((User) principal).getFullName();
		userName = ((User) principal).getUsername();
		emailAddress = ((User) principal).getEmail();
		officePhone = ((User) principal).getOfficephone();
		mobilePhone = ((User) principal).getMobilephone();
		xzqh = ((User) principal).getXzqh();
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

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<!-- add by 姚建林 2012-5-23 -->
		
		<script type="text/javascript">
			Ext.onReady(function(){//ext的开始
			Ext.QuickTips.init();   
			    var tabs = new Ext.TabPanel({/*标签面板*/
			    	renderTo: document.body,/*将内容显示在页面上*/
			        activeTab: 0,
			        width:300,
			        height:240,    
			        plain:true,
			        defaults:{autoScroll: true},
			        items:[{
				                title: '基本信息修改',
				                layout:"form",
				                xtype:'form',
				                id:"changeBaseInfo",/*表单的id*/
				                url:'<%=basePath%>service/rest/personInfo/changeBaseInfo?userName=<%=userName%>',/*处理这个表单的服务路径和方法名和参数等*/         
			                defaults:{width:150}, 
			                labelWidth:70,
			               	labelAlign:"right",  
			                padding:"5 0 0 10px",                                    
			                items:[{
			                	fieldLabel:"姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名",
			                	id:"fullName", 
			                	name:"fullName", 
			                	value:'<%=fullName%>',        
			            			xtype:'textfield',
			            			disabled: true /*文本框中的内容不可编辑*/
			                },{
			                	fieldLabel:"登陆账号",
			                	id:"userName",
			                	name:"userName",
			                	value:"<%=userName%>",
			                		xtype:'textfield',
			            			disabled: true /*文本框中的内容不可编辑*/
			                },{
			                	fieldLabel:"电子邮件",  
			                	id:"emailAddress", 
			                	name:"emailAddress", 
			                	value:'<%=emailAddress==null?"":emailAddress%>',            			
			            		xtype:'textfield'      
			                },{
			                	fieldLabel:"办公电话", 
			                	id:"officePhone",  
			                	name:"officePhone",
			                	value:'<%=officePhone==null?"":officePhone%>',
			            		xtype:'textfield' 
			                },{
			                	fieldLabel:"手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机", 
			                	id:"mobilePhone", 
			                	name:"mobilePhone",
			                	value:'<%=mobilePhone==null?"":mobilePhone%>',
			            		xtype:'numberfield'
			                },{
			                	fieldLabel:"行政区划", 
			                	id:"xzqh", 
			                	name:"xzqh",
			                	value:'<%=xzqh%>',     
			            		xtype:'numberfield',
			            		disabled: true /*文本框中的内容不可编辑*/
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
									         	Ext.Msg.alert('提示','基本信息修改失败！')   
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
			               	url:'<%=basePath%>service/rest/personInfo/changePwdInfo?userName=<%=userName%>',/*处理这个表单的服务路径和方法名和参数等*/       
			                defaults:{width:150},  
			                labelWidth:70, 
			                labelAlign:"right", 
			                padding:"5 0 0 10px",  
			                items:[{
			                	fieldLabel:"旧&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;码" ,   
			                	xtype:'textfield',  
			                	name:"oldpass", 
			                	id:"oldpass",  
			                	inputType:'password',
			                	allowBlank:false,
			    					blankText :'请输入旧密码！'                
			                },{
			                	fieldLabel:"新&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;码" ,
			                	xtype:'textfield',  
			                	name:"newpass",
			                	id:"newpass",
			                	inputType:'password',
			                	allowBlank:false,
			    					blankText :'请输入新密码！' 	                	                 	
			                },{
			                	fieldLabel:"确认新密码",
			              	 	xtype:'textfield',   
			                	name:"passensure",
			                	id:"passensure", 
			                	inputType:'password',
			                	allowBlank:false,
			    					blankText :'请再次输入新密码！' 	                	                 	
			                }],
			     			 buttons:[{  
				               	text:"保存", 
								handler:function(){
									if(Ext.getCmp('oldpass').getValue() == ''){ 
										Ext.Msg.alert('提示','请输入原密码！'); 
										return; 
									}
									if(Ext.getCmp('newpass').getValue() == ''){ 
										Ext.Msg.alert('提示','请输入新密码！'); 
										return; 
									}
									if(Ext.getCmp('passensure').getValue() == ''){ 
										Ext.Msg.alert('提示','请再次确认新密码！'); 
										return; 
									}					
									if(Ext.getCmp('newpass').getValue() != Ext.getCmp('passensure').getValue()){
										Ext.Msg.alert('提示','新密码两次输入不一致！'); 
										return; 
									}
									Ext.getCmp("changePassword").getForm().submit({   	 		             	
						             		 waitMsg: '正在保存,请稍候... ',  
									         success:function(){  
											 	Ext.Msg.alert('提示','保存成功。')  
								             },  
									         failure:function(){  
									         	Ext.Msg.alert('提示','密码修改失败！')   
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
	</head>
	<body>
	</body>
</html>
	