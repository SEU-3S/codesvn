<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.klspta.base.util.bean.xzqhutil.XzqhBean"%>
<%@page import="com.klspta.base.util.UtilFactory"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
    String xzqname= UtilFactory.getStrUtil().unescape(request.getParameter("xzqname"));
    String xzqh=request.getParameter("xzqh");   
    String level = request.getParameter("level");
    XzqhBean xzqhbean=UtilFactory.getXzqhUtil().getBeanById(xzqh);
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
	    <%@ include file="/base/include/restRequest.jspf" %>
		<%@ include file="/base/include/ext.jspf" %>
			
			<script>
 var level = "<%=level%>";
 Ext.onReady(function() {
 
 var store = new Ext.data.JsonStore({//是否重点城市的数据
					fields: ['value','display'],
					data:[{"value":"1","display":"是"},{"value":"0","display":"否"}]
					});
					
    Ext.QuickTips.init();
    var form = new Ext.Panel({
     	title:'修改:<%=xzqname%>',
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
                value:'<%=xzqhbean.getCatoncode()%>',
                fieldLabel: '行政区编号',
                disabled:true//设置为不可操作的
            },
           	{
                xtype: 'textfield',
                id      : 'xzqmc',
                value:'<%=xzqhbean.getCatonname()%>',
                fieldLabel: '行政区名称',
                	allowBlank:false,/*文本框不可为空*/
				    blankText :'行政区名称不能为空'
            },
            {
                xtype: 'textfield',
                id      : 'xzqjc',
                value:'<%=xzqhbean.getCatonsimpleName()=="null"?"":xzqhbean.getCatonsimpleName()%>',
                fieldLabel: '行政区简称'
            },
            {
                xtype: 'textfield',
                id      : 'zfmc',
                value:'<%=xzqhbean.getGovname()=="null"?"":xzqhbean.getGovname()%>',
                fieldLabel: '政府名称'
            },
            {
                xtype: 'textfield',
                id      : 'gtbmc',
                value:'<%=xzqhbean.getCatonsimpleName()=="null"?"":xzqhbean.getCatonsimpleName()%>',
                fieldLabel: '国土部门名称'
            },
            {
                xtype: 'combo',
                id : 'sfzdcs',
                value:'<%=xzqhbean.getStateflag()=="null"?"1":xzqhbean.getStateflag()%>',//最初的数据是没有重点城市的标志位的，当没有时默认为重点
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
                value:'<%=xzqhbean.getFullname()=="null"?"":xzqhbean.getFullname()%>',
                fieldLabel: '完整地址名称'
            },
            {
                xtype: 'textfield',
                id      : 'yzbm',
                value:'<%=xzqhbean.getPostalcode()==null?"":xzqhbean.getPostalcode()%>',
                fieldLabel: '邮政编码'
            }
          
        ],
        buttons: [
            {
                text   : '保存',
                handler: function() {
							if(Ext.getCmp("xzqmc").getValue() == ''){ 
								Ext.Msg.alert('提示','请输入行政区划名称！'); 
								return; 
							}
                			var path = "<%=basePath%>";
							var actionName = "xzqh";
							var actionMethod = "addXzqh";
							var parameter = "xzqh="+"<%=xzqhbean.getCatoncode()%>";//自己的行政区划代码
						     	parameter+="&xzqmc="+Ext.getCmp("xzqmc").getValue();
						     	parameter+="&xzqjc="+Ext.getCmp("xzqjc").getValue();
						     	parameter+="&zfmc="+Ext.getCmp("zfmc").getValue();
						     	parameter+="&gtbmc="+Ext.getCmp("gtbmc").getValue();
						     	parameter+="&sjxzq="+"<%=xzqhbean.getParentcode()%>";//上级的行政区划代码
						     	parameter+="&sfzdcs="+Ext.getCmp("sfzdcs").getValue();
						     	parameter+="&wzdzmc="+Ext.getCmp("wzdzmc").getValue();
						     	parameter+="&yzbm="+Ext.getCmp("yzbm").getValue();
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
                   document.location.reload()
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