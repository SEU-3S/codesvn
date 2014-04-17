<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String yw_guid=request.getParameter("yw_guid");
String year = request.getParameter("year");
String dtbzflag=request.getParameter("dtbzflag");
String flag=request.getParameter("flag");
String preParameters=request.getQueryString();
String filter = request.getParameter("filter");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>地图查看 flex调用js中 locationChange(bbox);，js 调用flex中setMapAlpha(0.1);</title>
  </head>
  	<%@ include file="/base/include/ext.jspf" %>
  <script type="text/javascript">
Ext.onReady(function(){
	border =new Ext.Viewport( 
		{
		layout:"border",
		items:[
			    center = new Ext.Panel({ 
                region: 'center', // a center region is ALWAYS required for border layout
                contentEl: 'center',
                collapsible: false,
                margins:'0 0 0 0'
            }),
            { 
                region: 'east', // a center region is ALWAYS required for border layout
                contentEl: 'east',
                id:'east-panel',
                collapsible: true,
                margins:'0 0 0 0',
                width: 300,
                minSize: 0,
                maxSize: 300,
                collapsed: true,
                title:''
            },
			{
                    region:'west',
					id:'west-panel',
                    contentEl: 'mapTree',
                    split:true,
                    width: 200,
                    minSize: 0,
                    maxSize: 300,
                    collapsible: true,
                    title:'图层树',
                    collapsed: true,
                    margins:'0 0 0 0'
                }
			  ]
		}
	);
}
);
</script>
	<body>
		<iframe id="mapTree"  name="mapTree"  style="width: 100%; height: 100%;overflow: auto;" src="<%=basePath%>base/fxgis/framework/mapTree.jsp"></iframe>
		<iframe id="center" name="center"  style="width: 100%; height:<%=yw_guid==null?100:100%>%;overflow: auto;border: 0px" src="menu.jsp?yw_guid=<%=yw_guid%>&flag=<%=flag%>&dtbzflag=<%=dtbzflag%>&year=<%=year%>&<%=preParameters%>&filter=<%=filter%>"></iframe>
		<iframe id="east"  name="east"  style="width: 100%; height: 100%;overflow: auto;" src=""></iframe>
	</body>
</html>
