<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.klspta.base.util.UtilFactory"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
    String xzqname= UtilFactory.getStrUtil().unescape(request.getParameter("xzqname")); //处理jsp传递参数的乱码问题
    String xzqh=request.getParameter("xzqh");   
    String level = request.getParameter("level");
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
		<!-- 将请求rest服务的ajax引进 -->
	    <%@ include file="/base/include/restRequest.jspf" %>
		<%@ include file="/base/include/ext.jspf" %>
			
			<script>
 var level = "<%=level%>";
 Ext.onReady(function() {
    Ext.QuickTips.init();
    
    var store = new Ext.data.JsonStore({//是否重点城市的数据
					fields: ['value','display'],
					data:[{"value":"1","display":"是"},{"value":"0","display":"否"}]
					});
					
    var form = new Ext.Panel({
     	title:'添加:&nbsp;&nbsp;<%=xzqname%>(<%=xzqh%>)&nbsp;&nbsp;下级区划',
        renderTo: 'userInfo',
        autoHeight: true,
        layout:'form',
        frame:true,
        bodyStyle:'padding:5px 0px 0',
        width: 300,
        defaults: {
            anchor: '0'
        },
        items   : [
           {
                xtype: 'numberfield',
                id      : 'xzqh',
                value:'',
                fieldLabel: '行政区编号',
                emptyText : "<%=xzqh%>",
	                allowBlank:false,/*文本框不可为空*/
				    blankText :'行政区编号不能为空'
            },
           	{
                xtype: 'textfield',
                id      : 'xzqmc',
                value:'',
                fieldLabel: '行政区名称',
                	allowBlank:false,/*文本框不可为空*/
				    blankText :'行政区名称不能为空'
            },
            {
                xtype: 'textfield',
                id      : 'xzqjc',
                value:'',
                fieldLabel: '行政区简称'
            },
            {
                xtype: 'textfield',
                id      : 'zfmc',
                value:'',
                fieldLabel: '政府名称'
            },
            {
                xtype: 'textfield',
                id      : 'gtbmc',
                value:'',
                fieldLabel: '国土部门名称'
            },
            {
                xtype: 'combo',
                id : 'sfzdcs',
                value:'1',/*默认是重点城市*/
                fieldLabel: '是否重点城市',
                mode: 'local',
                editable:false ,
                displayField: "display",
                valueField: 'value',
                triggerAction:'all',
                store: store/*加载数据*/
            },
            {
                xtype: 'textfield',
                id      : 'wzdzmc',
                value:'',
                fieldLabel: '完整地址名称'
            },
            {
                xtype: 'textfield',
                id      : 'yzbm',
                value:'',
                fieldLabel: '邮政编码'
            }
          
        ],
        buttons: [
            {
                text   : '添加',
                handler: function() {
                			var fatherCode = "<%=xzqh%>";//上级行政区划代码
                			var xzqh = Ext.getCmp("xzqh").getValue().toString();//新添行政区划代码
                			
              				if(Ext.getCmp("xzqh").getValue() == ''){ 
								Ext.Msg.alert('提示','请输入行政区划代码！'); 
								return; 
							}
							
							if(fatherCode=="0"){//上级行政区划代码为0时，即添加省级时，不做任何操作
							
							}else{
								if(level=="shiji"){//添加市级
									if(fatherCode.substr(0,2)!=xzqh.substr(0,2)){
										Ext.Msg.alert('提示','行政区划代码输入错误');
										return;
									}
								}
								if(level=="xianji"){//添加县级
									if(fatherCode.substr(0,4)!=xzqh.substr(0,4)){
										Ext.Msg.alert('提示','行政区划代码输入错误');
										return;
									}
								}
								if(level=="xiangji"){//添加乡级
									if(fatherCode.substr(0,6)!=xzqh.substr(0,6)){
										Ext.Msg.alert('提示','行政区划代码输入错误');
										return;
									}
								}
							}
							
							if(Ext.getCmp("xzqmc").getValue() == ''){ 
								Ext.Msg.alert('提示','请输入行政区划名称！'); 
								return; 
							}
                			var path = "<%=basePath%>";
							var actionName = "xzqh";
							var actionMethod = "addXzqh";
							var parameter = "xzqh="+xzqh;//行政区划代码
						     	parameter+="&xzqmc="+Ext.getCmp("xzqmc").getValue();//行政区划名称
						     	parameter+="&xzqjc="+Ext.getCmp("xzqjc").getValue();//行政区划简称
						     	parameter+="&zfmc="+Ext.getCmp("zfmc").getValue();//政府名称
						     	parameter+="&gtbmc="+Ext.getCmp("gtbmc").getValue();//国土部名称
						     	parameter+="&sjxzq="+"<%=xzqh%>";//上级行政区不需要在页面填写
						     	parameter+="&sfzdcs="+Ext.getCmp("sfzdcs").getValue();//是否重点城市
						     	parameter+="&wzdzmc="+Ext.getCmp("wzdzmc").getValue();//完整地址名称
						     	parameter+="&yzbm="+Ext.getCmp("yzbm").getValue();//邮政编码
							    var mes = ajaxRequest(path, actionName, actionMethod, parameter);
							    var mes = eval('(' + mes + ')');
							    if(mes=="success"){
							    	Ext.Msg.alert('提示','保存成功。');
							    	
							    	if(level=="shengji"){//刷新xzqhDeal页面的数据
							    		parent.grid.location.reload();
							    		return;
							    	}
							    	if(level=="shiji"){
							    		parent.grid.flush_shi();
							    		return;
							    	}
							    	if(level=="xianji"){
							    		parent.grid.flush_xian();
							    		return;
							    	}
							    	if(level=="xiangji"){
							    		parent.grid.flush_xiang();
							    		return;
							    	}
							    	if(level=="cunji"){
							    		parent.grid.flush_cun();
							    		return;
							    	}
							    	
							    }						
							    if(mes=="fail"){
							    	Ext.Msg.alert('提示','保存失败，请稍后重试或联系管理员。');
							    }						
                	}
            	},   
            {
                text   : '刷新',
                handler: function() {
                   document.location.reload();
                }
            }
        ]
  });
});
	</script>
	</head>
	<body bgcolor="#FFFFFF">
	  <div id="userInfo" />
	</div></body>
</html>