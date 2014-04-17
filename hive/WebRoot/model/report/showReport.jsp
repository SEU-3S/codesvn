<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@page import="com.klspta.base.util.UtilFactory"%>
<%
	Object errorMessage = session.getAttribute("errormessage");
	if (errorMessage != null) {
%>
<%@ include file="reportError.jsp"%>
<%
	} else {
%>

<%@ page import="net.sf.jasperreports.engine.*"%>
<%@ page import="net.sf.jasperreports.engine.util.*"%>
<%@ page import="net.sf.jasperreports.engine.export.*"%>
<%@ page import="net.sf.jasperreports.j2ee.servlets.*"%>
<%@ page import="java.io.*"%>
<%@ page import="com.klspta.model.report.api.ReportDataSource"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>


<%
	String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ path + "/";

		//获取必要参数
		String id = request.getParameter("id");//报表ID
		String condition = request.getParameter("condition");//定制查询条件
		if(condition!=null){
			condition = UtilFactory.getStrUtil().unescape(condition);
		}
		String fieldValue  = request.getParameter("fieldValue");//sql语句中需要填充的字段值
		
		//new String(request.getParameter("fieldValue").getBytes("ISO-8859-1"), "utf-8");
		if(fieldValue!=null){
			fieldValue = UtilFactory.getStrUtil().unescape(fieldValue);
		}

		String pathIndex = request.getParameter("pathIndex");//配置路径中有多个配置文件的情况下，要显示指定配置文件的索引。索引从0开始。
	
		//anychart可调参数
		String chartTitle = request.getParameter("chartTitle");//图表的标题。           可调参数：size,family,title 例:chartTitle=v1,{size:10,title:test};v2,{size:10}

		String chartLegendTitle = request.getParameter("chartLegendTitle");//图注标题.可调参数：size,family,title.参数格式：viewid,{key1:value1,key2,value2}
		String chartTip = request.getParameter("chartTip");//提示信息。                  可调参数：size,family.      参数格式：viewid,seriesid,{key1:value1,key2,value2}
		String chartLegend = request.getParameter("chartLegend");//图注信息。           可调参数：size,family.      参数格式：viewid,seriesid,{key1:value1,key2,value2}
		String chartLable = request.getParameter("chartLable");//显示信息。             可调参数：size,family.      参数格式：viewid,seriesid,{key1:value1,key2,value2}
		String chartSeries = request.getParameter("chartSeries");//一组数据的显示格式。  可调参数：type,color.       参数格式：viewid,seriesid,{key1:value1,key2,value2}
		//例：chartSeriesType=v1,s1,bar. 常用显示格式bar(条状),line(直线),spine(曲线),area(矩形围成的面积),splinearea（曲线围成的面积）

		//ireport可调参数
		String iZoom = request.getParameter("iZoom");//ireport的视图大小
		float iZoom_ = 1.3f;
		if (iZoom != null) {
			iZoom_ = Float.parseFloat(iZoom);
		}

		//初始化数据源类
		ReportDataSource report = new ReportDataSource();
		report.loadReportParameter(condition, fieldValue, pathIndex);
		String type = report.getReportType(id);
		//如果报表类型为ireport
		if (type.equals("ireport")) {
			//获取资源路径
			String jasperPath = report.configPath;
			String reportFileName = application.getRealPath(jasperPath);
			File reportFile = new File(reportFileName);
			if (!reportFile.exists())
				throw new JRRuntimeException(
						"File WebappReport.jasper not found. The report design must be compiled first.");

			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
			//ireport加载数据
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, report);

			JRHtmlExporter exporter = new JRHtmlExporter();
			session.setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());

			exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
			exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporter.setParameter(JRHtmlExporterParameter.ZOOM_RATIO, iZoom_);

			exporter.exportReport();
		}
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>图表展示页</title>
		<META HTTP-EQUIV="pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
		<%
			if (type.equals("anychart")) {
		%>
		<script
			src="<%=basePath%>/base/thirdres/anyChart/binaries/js/AnyChart.js"></script>
		<%@ include file="/base/include/ext.jspf"%>
		<%@ include file="/base/include/restRequest.jspf"%>
		<style type="text/css">
         html,body,#chartContainer {
	     width: 100%;
	     height: 100%;
	     padding: 0;
	     margin: 0;
         }
        </style>
		<%
			}
		%>
	</head>

	<body>
		<!--执行数据加载 add by 赵伟 2012-8-7-->
		<%
			if (type.equals("anychart")) {
		%>
		<script type="text/javascript" language="javascript">
		
        <!--获取数据 add by 赵伟 2012-8-8-->
        putClientCommond("reportDataSource", "getChartData");
        putRestParameter("sql", URLencode(escape("<%=report.sql%>")));
        putRestParameter("condition", escape(escape("<%=report.condition%>")));
        putRestParameter("fieldValue", escape(escape("<%=report.fieldValue%>")));
        var result = eval(restRequest());
        if(result==undefined){
        document.location.reload();
        }
		
        var chart = new AnyChart('<%=basePath%>/base/thirdres/anyChart/binaries/swf/AnyChart.swf');
        chart.setXMLFile("<%=basePath%><%=report.configPath%>");
        chart.width = '100%';
        chart.height = '100%';
        chart.write();
		
       
         <!--数据加载 add by 赵伟 2012-8-8-->
        var view_list=new Array();
        var series_list=new Array();

	    for ( var i = 0; i < result.length; i++) {
		  view_list.push(result[i].VIEWID);
		  series_list.push([[result[i].VIEWID],[result[i].SERIESID]]);
	    }
	   view_list.sort();
	   series_list.sort();
       for(var i=result.length-1;i>=1;i--) {
         if((""+view_list[i-1])==(""+view_list[i])) {
          view_list.splice(i,1); 
         }
         if((""+series_list[i-1])==(""+series_list[i])) {
          series_list.splice(i,1); 
         }
       }
     <!--增加节点并给节点添加属性 add by 赵伟 2012-8-8-->
	   var isSeriesIdReady=false
       setTimeout(function () {
    	   try{
	       <%
	       Map<String,String> map=new HashMap<String,String>();
	       map.put(" ",chartSeries);
	       %>
	       var series = eval(<%=report.getCustomParameterJson(map)%>);
		   for ( var i = 0; i < series_list.length; i++) {
		     var temp="";
		     for(var j=0;j<series.length;j++){
		       if(series[j].key0==series_list[i][0]&&series[j].key1==series_list[i][1]){
		        temp+=""+series[j].name+"='"+series[j].value+"'";
		        series.splice(j,1);
		        j=j-1;
		       }
		     }
		    chart.view_addSeries(""+series_list[i][0],"<series id='"+series_list[i][1]+"' name='"+series_list[i][1]+"' "+temp+"></series>");
		  }
		   isSeriesIdReady=true;
    	   }
   		catch(err)
   		{
   			errorMsg();
   		}
		   //setData();
		  setTimeout("setData()",100);
      }, 2000);
      <!--给节点增加数据并添加point级别参数 add by 赵伟 2012-8-8-->
       <%
       map.clear();
       map.put("tip",chartTip);
       map.put("legend",chartLegend);
       map.put("lable",chartLable);
       %>
       var pointAttr=eval(<%=report.getCustomParameterJson(map)%>);
       var isDataReady=false;
     //  setTimeout("setData()",500);
       function setData() {
    		//alert("data");
    		try{
    		  
	    	   for ( var i = 0; i < result.length; i++) {
	    	     var attr ="<attributes>";
	        	    for(var j=0;j<pointAttr.length;j++){
	    	         if(pointAttr[j].key0==result[i].VIEWID&&pointAttr[j].key1==result[i].SERIESID){
	    	          attr+="<attribute name='"+pointAttr[j].name+"'><![CDATA["+pointAttr[j].value+"]]></attribute>";
	    	         }
	    	        }
	    	     attr+="</attributes>";
	    		 chart.view_addPoint("" + result[i].VIEWID, "" + result[i].SERIESID,
	    				"<point id='" + result[i].POINTNAME + "' name='"+ result[i].POINTNAME
	    				+ "' y='" + result[i].POINTVALUE+ "' >"+attr+"</point>");
	    	   }
	    	   isDataReady=true;
	    	   chart_updateCustomAttr();
    		}
    		catch(err)
    		{
    			errorMsg();
    		}
	    	  setTimeout("chart_updateCustomAttr()",100);
    	   
     }

     <!--增加data级别参数 add by 赵伟 2012-8-23-->
     // setTimeout(chart_updateCustomAttr,800);
      <%
      map.clear();
     
      map.put("title",chartTitle);
      map.put("legendtitle",chartLegendTitle);
      %>
     function chart_updateCustomAttr() {
    	 
    		  //alert("flush");
    	 
    		try{
		      var dataAttr = eval(<%=report.getCustomParameterJson(map)%>);
		     
		      for ( var i = 0; i < dataAttr.length; i++) {
		       chart.view_setPlotCustomAttribute(""+dataAttr[i].key0,""+dataAttr[i].name, ""+dataAttr[i].value);
		      
		      }
		      for ( var i = 0; i < view_list.length; i++) {
		       chart.view_refresh(view_list[i]);
		      }
		      chart.refresh();
    		}
    		catch(err)
    		{
    			errorMsg();
    		}
    	  
     }
     function URLencode(sStr) 
	 {
    	return escape(sStr).replace(/\+/g, '%2B').replace(/\"/g,'%22').replace(/\'/g, '%27').replace(/\//g,'%2F');
	 }
     function errorMsg()
     {
    	document.location="<%=basePath%>/model/report/error.html";
    	//document.write("统计数据失败，请重新点击查询!");
     }
   
     
</script>
		<%
			}
		%>
		<%
		
			if (type.equals("")) {
					out.println("错误：无此ID信息或配置信息不全");
				}
			}
		
		
		%>
	</body>
</html>
