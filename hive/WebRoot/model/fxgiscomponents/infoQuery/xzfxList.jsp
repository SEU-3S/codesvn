<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.klspta.model.mapconfig.TBQuery"%>
<%@ taglib uri="/WEB-INF/taglib/queryLabel.tld" prefix="common"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
    //卫片检测图斑地图服务层 图斑编号
    String tbbh = request.getParameter("tbbh");        
    String type = request.getParameter("type");
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("type",type);
    map.put("tbbh",tbbh);
    List<Map<String,Object>> ydList = new TBQuery().getXzfxYdData(map);
 	String nydmj="";//农用地面积
    String gdmj="";//耕地面积
    String jsydmj="";//建设用地面积
    String wlydmj=""; //未利用地面积
    String zmj="";//总面积
    if(ydList.size()>0){
    nydmj = (String)ydList.get(0).get("NYDMJ"); 
    gdmj = (String)ydList.get(0).get("GDMJ"); 
    jsydmj = (String)ydList.get(0).get("JSYDMJ"); 
    wlydmj = (String)ydList.get(0).get("WLYDMJ"); 
    zmj = Float.parseFloat(nydmj)+Float.parseFloat(gdmj)+Float.parseFloat(jsydmj)+Float.parseFloat(wlydmj)+"";
    } 

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>PDA列表</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	    <%@ include file="/base/include/ext.jspf" %>
	    <%@ include file="/base/include/restRequest.jspf" %>
	</head>
	<script>
    var myData;
    Ext.onReady(function(){
    var d = eval(parent.data); 
    
    
	//var myData = [["0189","有林地","温泉村农民集体","84.5亩"],["0028","沟渠","温泉村农民集体","50.1亩"]];
    putClientCommond("tBQuery","getXzfxData");
    putRestParameter("tbbh",'<%=tbbh%>');
	myData = restRequest();
    //myData=[{TBBH:'321003-351', DLMC:'农村道路', QSDWMC:'村集体', YGMJ:'5.9', NYDMJ:'8.09'}];
    var store = new Ext.data.JsonStore({
    proxy: new Ext.ux.data.PagingMemoryProxy(myData),
        fields: [
           {name: 'TBBH'},
           {name: 'DLMC'},
           {name: 'QSDWMC'},
           {name: 'YGMJ'}
        ]
    });  
       var grid = new Ext.grid.GridPanel({
        store: store, 
        height:300,
        width:340,
        columns: [
            {header: '图斑编号',dataIndex:'TBBH',width: 65},
            {header: '地类名称',dataIndex:'DLMC',width: 65},
            {header: '权属单位名称',dataIndex:'QSDWMC',width: 100},
            {header: '压盖面积',dataIndex:'YGMJ',width:70}
        ],
         listeners:{
		       rowclick:function(grid,row){	
		       }
         }      
    });  
    store.load(); 
    grid.render('status_grid');
 })
	</script>
	<body>
	    <div style="font-size:12px">总面积：<%=zmj%>亩<br>农用地：<%=nydmj%>亩（耕地：<%=gdmj%>亩）<br>建设用地：<%=jsydmj%>亩<br>未利用地:<%=wlydmj%>亩</div>
		<div id="status_grid"></div>
	</body>
</html>