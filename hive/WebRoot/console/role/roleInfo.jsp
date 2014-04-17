<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.klspta.console.role.Role"%>
<%@page import="com.klspta.console.ManagerFactory"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String extPath = basePath + "base/thirdres/ext/";
	String roleId = request.getParameter("roleId");
	String parentRoleId=request.getParameter("parentRoleId");
	Role roleBean=ManagerFactory.getRoleManager().getRoleWithId(roleId);
	if(roleBean==null)
		roleBean=new Role();
	String userInfo=ManagerFactory.getUserManager().getUserNameJsonByRoleId(roleId);
    String users=ManagerFactory.getUserManager().getSelectUserJsonByRoleId(roleId);
	String userInfoArray=ManagerFactory.getUserManager().getUserInfoArrayJsonByRoleId(roleId);
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
		<%@ include file="/base/include/restRequest.jspf" %>
		<script type="text/javascript"
			src="<%=basePath%>/web/cbd/yzt/RowEditor.js"></script>
	<script type="text/javascript" src="<%=extPath%>examples/ux/MultiSelect.js"></script>
<script type="text/javascript" src="<%=extPath%>examples/ux/ItemSelector.js"></script>
			 <link rel="stylesheet" type="text/css" href="<%=extPath%>examples/ux/css/MultiSelect.css"/>


		<script>
		var winForm;
 Ext.onReady(function() {
    Ext.QuickTips.init();
    var win;
    var form = new Ext.form.FormPanel({
        renderTo: 'roleInfo',
        title   : '<%=roleBean.getRolename()%>',
        autoHeight: true,
        width   : 500,
        url:'<%=basePath%>service/rest/userAction/saveRoleUser?roleId=<%=roleId%>&parentRoleId=<%=parentRoleId%>',
        bodyStyle: 'padding: 5px',
        defaults: {
            anchor: '0'
        },
        items: [
   			{
                xtype: 'textfield',
                id      : 'sort',
                value:'<%=roleBean.getSort()%>',
                fieldLabel: '序号',             
                anchor    : '-250'
            },
            {
                xtype     : 'textfield',
           		id:'roleName',
                value:'<%=roleBean.getRolename()%>',
                fieldLabel: '机构名称',
                anchor    : '-250'

               
            },
 			{
                    xtype: 'compositefield',
                    id:'compositefield',
                    items: [
	              	{  
	                xtype: 'textfield',
	                id      : 'personName',
	                fieldLabel: '包含人员',
	                value:'<%=userInfo%>',
	                 anchor    : '0'
	            	},
                       {
                       		xtype: 'button',
                       		value: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
                       		iconCls:'background-image:url(<%=basePath%>/console/images/users.png);background-repeat: no-repeat;CURSOR:hand;',
                       		listeners:{
                       			'click':function(t,e){
                       				//弹出选择人员列表窗体
				           if(!win){	           
				           		win = new Ext.Window({
				                layout: 'fit',
				                title: '请选择人员列表',
				                closeAction: 'hide',
				                width:600,
				                height:440,
				                x: 40,
				                y: 110,
				                items:winForm
				            });    
				        }
				        win.show();
                       }
                      }
                 }]
          }],
        buttons: [
            {
                text: '重置',
                handler: function() {
                    var Record = Ext.data.Record.create([
                       {name: 'roleName',type:'string'},
                       {name: 'sort',type:'string'},
                       {name: 'personName',type: 'string'}           
                    ]);
                    
                    form.form.loadRecord(new Record({
                        'roleName'  :'<%=roleBean.getRolename()%>',
                        'sort'	    :'<%=roleBean.getSort()%>',
                        'personName':'<%=userInfo%>'
                    }));
                }
            },
            {
                text   : '保存',
                handler: function() {  

					       form.getForm().submit({
			               waitMsg:'正在提交数据请稍后...',
				           success:function(form,action){ 
				           if(action.result.msg){
				               Ext.Msg.alert('提示','保存成功！'); 
		
				               parent.tree.document.location.reload();
				           }    
				       	   }, 
				       	  failure:function(form,action){//加载失败的处理函数 
				          Ext.Msg.alert('提示','保存失败!'); 
				       } 
					});
          	    }
            }
        ]
    });
     //用于多选的用户信息 
	  var leftDs = new Ext.data.ArrayStore({
	       data: <%=userInfoArray%>,
	       fields: ['value','text']
	   	}); 
	  var rightDs = new Ext.data.ArrayStore({ 
	       data:<%=users%>,
	       fields: ['value','text'],
	       sortInfo: {
	           field: 'value',
	           direction: 'ASC'
	       }
	   	}); 
    //window中的formPanel
			winForm = new Ext.form.FormPanel({
		        //title: 'ItemSelector Test',
		        width:600,
		        //url:"<%=basePath%>formOperationAC.do?method=",
		        bodyStyle: 'padding:10px;',
		        region: 'center',//定位
		        items:[
			        {
		                xtype: 'textfield',
		                id: 'findusers',
		                value:'',
		                emptyText:'利用username(用户名)首字母快速查询',
		                maxLength:1,
		                maxLengthText: '只能输入一个字符',
		                width:250,
		                fieldLabel: '快速查找',
		                enableKeyEvents : true,
		                listeners:{
		                		'keyup':findusers
                       	}             
	            	},
		       		 //多选
		        	{
			            xtype: 'itemselector',
			            name: 'itemselector',
				        imagePath: '<%=extPath%>examples/ux/images/',
				        fieldLabel: '人员列表',
			            multiselects: [{
		                  width: 220,
		                  height: 280,
		                  store: leftDs,
		                  displayField: 'text',
		                  valueField: 'value'
			           },{
			              width: 220,
			              height: 280,
			              store: rightDs,
			              displayField: 'text',
		                  valueField: 'value',
			              tbar:[{
			                 text: '清空已选人员',
			                 handler:function(){
				             winForm.getForm().findField('itemselector').reset();
				            }
			              }]       
			            }]		        
		        	 }],	
			          buttons: [{
			            text: '保存',
			            handler: function(){
			                if(winForm.getForm().isValid()){  
			                    Ext.getCmp('personName').setValue(winForm.form.findField('itemselector').getValue());  
			                 }		
						   win.hide();
			              }
			         }]
		       });
		       
        });
        
        function findusers(){
        	var keyWord = Ext.getCmp('findusers').getValue();
            putClientCommond("userAction","getUserInfoArrayJsonByRoleId");
            putRestParameter("roleId","<%=roleId%>");
            putRestParameter("keyWord",escape(escape(keyWord)));
            var myData = restRequest(); 
	   		
	   		winForm.getForm().findField('itemselector').restore(myData);
        }
   </script>
</head>
	<body>
		<div id="roleInfo" />
	</body>
</html>