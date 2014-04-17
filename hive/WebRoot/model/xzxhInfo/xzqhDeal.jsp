<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@page import="com.klspta.console.user.User"%>
<%@page import="com.klspta.base.util.UtilFactory" %>

<%@ taglib uri="/WEB-INF/taglib/label.tld" prefix="common"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
    + request.getServerPort() + path + "/";
   String extPath = basePath + "base/thirdres/ext/";
   
   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();//得到当前用户的信息
    
    String level = "";//行政区划的级别(部、省、市、县)
    String xzqname = "";//行政区划的名称
    String xzqh = "";//行政区划代码
    
    //得到这个用户的行政区划代码
    if (principal instanceof User) {
		xzqh = ((User) principal).getXzqh();
	} else {
		xzqh = principal.toString();
	}
	
	//判断行政区划的级别
	if(xzqh.equals("0")){//部级
		level = "buji";
	}else{
		if(xzqh.substring(2).equals("0000")){//省级
			level = "shengji";
		}else{
			if(xzqh.substring(4).equals("00")){//市级
				level = "shiji";
			}else{//县级
				level = "xianji";
			}
		}
	}
	
	//得到行政区划的名称
	if(xzqh.equals("0")){
		xzqname = "部级";
	}else{
		xzqname = UtilFactory.getXzqhUtil().getNameByCode(xzqh);
	}
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
	    <%@ include file="/base/include/restRequest.jspf" %>
		<%@ include file="/base/include/ext.jspf" %>
		<script type="text/javascript" src="<%=extPath%>/examples/ux/PagingMemoryProxy.js"></script>
		<script type="text/javascript" src="<%=extPath%>/examples/ux/ProgressBarPager.js"></script>
		<script type="text/javascript">
var myData=eval('[{"code":"<%=xzqh%>","name":"<%=xzqname%>"}]');//将当前用户的行政区划信息组合成特定的数据形式
var panel;//单独定义一个变量，方便局部刷新的时候使用
Ext.onReady(function(){
  Ext.QuickTips.init();
  var shenStore= new Ext.data.JsonStore({//省级数据
                fields: ['code','name'],
                data:[]
                });
  var shiStore= new Ext.data.JsonStore({//市级数据
                fields: ['code','name'],
                data:[]
                });
   var xianStore= new Ext.data.JsonStore({//县级数据
                fields: ['code','name'],
                data:[]
                });
   var xiangStore= new Ext.data.JsonStore({//乡级数据
                fields: ['code','name'],
                data:[]
                });
   var cunStore= new Ext.data.JsonStore({//村级数据
                fields: ['code','name'],
                data:[]
                });
                
    panel = new  Ext.Panel({
 		title:'行政区划:<%=xzqname%>',
        renderTo:'xzqh_view',
        autoHeight: true,
        layout:'form',
        frame:true,
        bodyStyle:'padding:0px 0px 0',
        width: 420,
        labelWidth :140,
        defaults: {
            anchor: '0'
        },
        items   : [           
   			 { xtype: 'compositefield', 
   			    id:'shengji',
   			    items:[{
                xtype: 'combo',
                id      : 'shen',
                fieldLabel: '省(直辖市、自治区)',
                emptyText : "请选择",
                editable:false ,
                mode:'local',        
                store:shenStore,
                valueField: 'code',
                displayField: 'name',
                triggerAction:'all',
                listeners:{
                "select":function(){
                			 Ext.getCmp("shi").getStore().clearData();//清空下面四级的数据
 							 Ext.getCmp("shi").clearValue();
 							 Ext.getCmp("xian").getStore().clearData();
                             Ext.getCmp("xian").clearValue();
							 Ext.getCmp("xiang").getStore().clearData();
                             Ext.getCmp("xiang").clearValue();
                             Ext.getCmp("cun").getStore().clearData();
                             Ext.getCmp("cun").clearValue();
                             var temp = Ext.getCmp("shen").getValue();//得到省一级被选中的数据
                             //四个直辖市要特殊处理
                             if(temp=="110000"||temp=="310000"||temp=="120000"||temp=="500000"){//如果是直辖市，级联的数据要添加在xian一级
                             	var xianData=getData(temp);
                             	Ext.getCmp("xian").getStore().loadData(xianData[0]);//刷新县级数据
                             	return;
                             }
                             //除去直辖市之后的处理
                             var shiData=getData(temp);//刷新市级数据
                             Ext.getCmp("shi").getStore().loadData(shiData[0]);                         
                         }
                }  
                },{
                xtype:'button',
                id : 'shenchange',
                width: 40,
                text:'修改',
                listeners:{
	              	  'click':function(){ 
	              	  	 if(Ext.getCmp('shen').lastSelectionText==""||Ext.getCmp('shen').getValue()==""){
	              	           Ext.Msg.alert("提示","请选择一个省(直辖市、自治区)")
	              	           return
	              	      } 
	                	  modifyInfo(Ext.getCmp('shen').lastSelectionText,Ext.getCmp('shen').getValue(),'shengji');
	             	   }
                }
                },{
                xtype:'button',
                id : 'shenadd',
                width: 40,
                text:'新增',
                listeners:{
	              	  'click':function(){ 
	                	addXzqh('部',0,'shengji');
	             	   }
                }
                }]
            },
            { xtype: 'compositefield', 
               id:'shiji',
   			   items:[{
                xtype: 'combo',
                id      : 'shi',
                fieldLabel: '市(地区、自治州、盟)',
                emptyText : "请选择",   
                mode:'local',        
                store:shiStore,
                valueField: 'code',
                displayField: 'name', 
                triggerAction:'all',
                listeners:{
                "select":function(){
                             Ext.getCmp("xian").getStore().clearData();
                             Ext.getCmp("xian").clearValue();
							 Ext.getCmp("xiang").getStore().clearData();
                             Ext.getCmp("xiang").clearValue();
                             Ext.getCmp("cun").getStore().clearData();
                             Ext.getCmp("cun").clearValue();
                             var xianData=getData(Ext.getCmp("shi").getValue());
                             Ext.getCmp("xian").getStore().loadData(xianData[0]);//刷新县级数据
                         }
                }               
                },{
                xtype:'button',
                id : 'shichange',
                width: 40,
                text:'修改',
                listeners:{
	              	  'click':function(){
	              	  		var temp = Ext.getCmp("shen").getValue();
	              	  	    //四个直辖市要特殊处理
                            if(temp=="110000"||temp=="310000"||temp=="120000"||temp=="500000"){//如果是直辖市，（市级）按钮不做任何反应
                           		return;
                            }
	              	  	  if(Ext.getCmp('shi').lastSelectionText==""||Ext.getCmp('shi').getValue()==""){
	              	           Ext.Msg.alert("提示","请选择一个市(地区、自治州、盟)")
	              	           return
	              	      }  
	                	  modifyInfo(Ext.getCmp('shi').lastSelectionText,Ext.getCmp('shi').getValue(),'shiji');
	             	   }
	                }
                },{
                xtype:'button',
                id : 'shiadd',
                width: 40,
                text:'新增',
                listeners:{
	              	  'click':function(){
	              	  		var temp = Ext.getCmp("shen").getValue();
	              	  	    //四个直辖市要特殊处理
                            if(temp=="110000"||temp=="310000"||temp=="120000"||temp=="500000"){//如果是直辖市，（市级）按钮不做任何反应
                           		return;
                            }
	              	  	  if(Ext.getCmp('shen').lastSelectionText==""||Ext.getCmp('shen').getValue()==""){
	              	           Ext.Msg.alert("提示","请选择上级省(直辖市、自治区)")
	              	           return
	              	      }  
	                	  addXzqh(Ext.getCmp('shen').lastSelectionText,Ext.getCmp('shen').getValue(),'shiji');
	             	   }
	                }
                }]
            },
            { xtype: 'compositefield', 
                id:'xianji',
   			   items:[{
                xtype: 'combo',
                id      : 'xian',
                fieldLabel: '县(市辖区、县级市、旗)',
                emptyText : "请选择",   
                mode:'local',        
                store:xianStore,
                valueField: 'code',
                displayField: 'name',
                triggerAction:'all',
                listeners:{
                "select":function(){                     
                             Ext.getCmp("xiang").getStore().clearData();
                             Ext.getCmp("xiang").clearValue();
                             Ext.getCmp("cun").getStore().clearData();
                             Ext.getCmp("cun").clearValue();
                             var xiangData=getData(Ext.getCmp("xian").getValue());
                             Ext.getCmp("xiang").getStore().loadData(xiangData[0]);                     
                         }
                }               
                },{
                xtype:'button',
                id : 'xianchange',
                width: 40,
                text:'修改',
                listeners:{
	              	  'click':function(){
	              	      if(Ext.getCmp('xian').lastSelectionText==""||Ext.getCmp('xian').getValue()==""){
	              	           Ext.Msg.alert("提示","请选择一个县(市辖区、县级市、旗)")
	              	           return
	              	      } 
	                	  modifyInfo(Ext.getCmp('xian').lastSelectionText,Ext.getCmp('xian').getValue(),'xianji');
	             	   }
                }
                },{
                xtype:'button',
                id : 'xianadd',
                width: 40,
                text:'新增',
                listeners:{
	              	  'click':function(){
	              	  		var temp = Ext.getCmp("shen").getValue();
	              	  	    //四个直辖市要特殊处理
                            if(temp=="110000"||temp=="310000"||temp=="120000"||temp=="500000"){//如果是直辖市，按钮不做任何反应
                            	addXzqh(Ext.getCmp('shen').lastSelectionText,temp);
                           		return;
                            }
	              	  	  if(Ext.getCmp('shi').lastSelectionText==""||Ext.getCmp('shi').getValue()==""){
	              	           Ext.Msg.alert("提示","请选择上级市(地区、自治州、盟)")
	              	           return
	              	      }  
	                	  addXzqh(Ext.getCmp('shi').lastSelectionText,Ext.getCmp('shi').getValue(),'xianji');
	             	   }
	                }
                }]
            }, 
            { xtype: 'compositefield', 
               id:'xiangji',
   			   items:[{
                xtype: 'combo',
                id      : 'xiang',
                fieldLabel: '乡(镇、街道、区公所)',
                emptyText : "请选择",   
                mode:'local',        
                store:xiangStore,
                valueField: 'code',
                displayField: 'name',
                triggerAction:'all',
                listeners:{
                "select":function(){
                             Ext.getCmp("cun").getStore().clearData();
                             Ext.getCmp("cun").clearValue();
                             var cunData=getData(Ext.getCmp("xiang").getValue());
                             Ext.getCmp("cun").getStore().loadData(cunData[0]);
                         }
                }  
                },{
                xtype:'button',
                id : 'xiangchange',
                width: 40,
                text:'修改',
                listeners:{
	              	  'click':function(){ 
	              	     if(Ext.getCmp('xiang').lastSelectionText==""||Ext.getCmp('xiang').getValue()==""){
	              	           Ext.Msg.alert("提示","请选择一个乡(镇、街道、区公所)")
	              	           return
	              	      } 
	                	  modifyInfo(Ext.getCmp('xiang').lastSelectionText,Ext.getCmp('xiang').getValue(),'xiangji');
	             	   }
                }
                },{
                xtype:'button',
                id : 'xiangadd',
                width: 40,
                text:'新增',
                listeners:{
	              	  'click':function(){
	              	  	  if(Ext.getCmp('xian').lastSelectionText==""||Ext.getCmp('xian').getValue()==""){
	              	           Ext.Msg.alert("提示","请选择上级县(市辖区、县级市、旗)")
	              	           return
	              	      }  
	                	  addXzqh(Ext.getCmp('xian').lastSelectionText,Ext.getCmp('xian').getValue(),'xiangji');
	             	   }
	                }
                }]
            },
             { xtype: 'compositefield', 
               id:'cunji',
   			   items:[{
                xtype: 'combo',
                id      : 'cun',
                fieldLabel: '村',
                emptyText : "请选择", 
                mode:'local',        
                store:cunStore,
                valueField: 'code',
                displayField: 'name',
                triggerAction:'all'
                },
                {
                xtype:'button',
                id : 'cunchange',
                width: 40,
                text:'修改',
	            listeners:{
	              	  'click':function(){ 
	              		  if(Ext.getCmp('cun').lastSelectionText==""||Ext.getCmp('cun').getValue()==""){
	              	           Ext.Msg.alert("提示","请选择一个村")
	              	           return
	              	      } 
	                	  modifyInfo(Ext.getCmp('cun').lastSelectionText,Ext.getCmp('cun').getValue(),'cunji');
	             	   }
	                }
                },{
                xtype:'button',
                id : 'cunadd',
                width: 40,
                text:'新增',
                listeners:{
	              	  'click':function(){
	              	  	  if(Ext.getCmp('xiang').lastSelectionText==""||Ext.getCmp('xiang').getValue()==""){
	              	           Ext.Msg.alert("提示","请选择上级乡(镇、街道、区公所)")
	              	           return
	              	      }  
	                	  addXzqh(Ext.getCmp('xiang').lastSelectionText,Ext.getCmp('xiang').getValue(),'cunji');
	             	   }
	                }
                }]
            }
                          
        ]
 });

for(var t=0;t<panel.items.items.length;t++){
   var leveBack='<%=level%>';//管理人员的行政区划级别
   if(leveBack=='buji'){//如果是部级管理人员，初始化省级数据
      var shenData=getData(<%=xzqh%>);
      Ext.getCmp("shen").getStore().loadData(shenData[0]);     
    break;
   }
   var obj=panel.items.items[t];
   var levelid=obj.id;
     panel.items.items[t].hide();
    if(levelid==leveBack){
      var st=panel.items.items[t].items.items[0];
      st.getStore().loadData(myData); 
      st.setValue(<%=xzqh%>);
      st.fireEvent('select',this);
     break;     
    }
}

}
)

//获取行政区
function  getData(xzqh){
			var path = "<%=basePath%>";
			var actionName = "xzqh";
			var actionMethod = "getNextPlace";
			var parameter = "code=" + xzqh;
			var myData = ajaxRequest(path, actionName, actionMethod, parameter);
			var obj = eval('(' + myData + ')');
     return obj;		
}

//修改
function modifyInfo(value,num,level){
parent.info.location.href="xzqhModify.jsp?xzqname="+escape(escape(value))+"&xzqh="+num+"&level="+level;
}

//添加
function addXzqh(value,num,level){
parent.info.location.href="xzqhAdd.jsp?xzqname="+escape(escape(value))+"&xzqh="+num+"&level="+level;
}

//局部刷新市级
function flush_shi(){
    panel.items.items[0].items.items[0].fireEvent('select',this);
}
//局部刷新县级
function flush_xian(){
    panel.items.items[1].items.items[0].fireEvent('select',this);
}
//局部刷新乡级
function flush_xiang(){
    panel.items.items[2].items.items[0].fireEvent('select',this);
}
//局部刷新村级
function flush_cun(){
    panel.items.items[3].items.items[0].fireEvent('select',this);
}
</script>
	</head>
	<body  bgcolor="#FFFFFF" topmargin="0" leftmargin="0">
		<div id="xzqh_view" style="width: 700; height: 100%;"></div>
	</body>
</html>