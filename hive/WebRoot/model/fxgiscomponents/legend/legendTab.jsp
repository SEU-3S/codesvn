<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.klspta.model.legend.LegendFiles"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + path + "/";
    String extPath = basePath + "thirdres/ext/";
    List lf = LegendFiles.getInstance().getAllFoldersName();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>analysis</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<%@ include file="/base/include/ext.jspf" %>
		<script type="text/javascript"
			src="<%=extPath%>/examples/docs/source/PagingMemoryProxy.js"></script>
		<script type="text/javascript"
			src="<%=extPath%>/examples/docs/source/ProgressBarPager.js"></script>
   <style type="text/css">
    html,body {
	    font: normal 12px verdana;
	    margin: 0;
	    padding: 0;
	    border: 0 none;
	    overflow: hidden;
	    height: 100%;
    }
   </style>
   <script>
Ext.onReady(function(){
   Ext.QuickTips.init();
   var scrHeight= document.body.offsetHeight; //(包括边线的高)457
    var viewport = new Ext.Viewport({
        layout:'border',
        items:[{
            region: 'west',
            width: 300,   
            layout: 'accordion',
            layoutConfig: {
                titleCollapse: true,
                animate: true,
                activeOnTop: false
            },
            items: [
            <%
            	for(int i=0;i<lf.size();i++){
          
            %>
               		{
		                title: '<%=lf.get(i) %>',    
		                html: "<iframe style='height:"+(scrHeight-95)+"PX;' src='legend.jsp?type=<%=lf.get(i) %>' />"            
		       		}
            <%
	            	if(i != lf.size()-1){
	        %>
	         		,
	        <%   		
	            	}
            	}
            %>
            ]
        },{
            region: 'center',
            split: true,
            border: true
        }]
    });
 
});
  
   </script>
  </head>
	<body >
		<div id="legendTab" style='height:100%;width:100%; '></div>	
	</body>
</html>
