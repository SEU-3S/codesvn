<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.jasperreports.engine.JRExporterParameter"%>
<%@ page import="net.sf.jasperreports.engine.JasperFillManager"%>
<%@ page import="net.sf.jasperreports.engine.util.JRLoader"%>
<%@ page import="net.sf.jasperreports.engine.export.JRXlsExporter"%>
<%@ page import="net.sf.jasperreports.engine.export.JRHtmlExporter"%>
<%@ page import="net.sf.jasperreports.engine.export.JRPdfExporter"%>
<%@ page
	import="net.sf.jasperreports.engine.export.JRXlsExporterParameter"%>
<%@ page
	import="net.sf.jasperreports.engine.export.JRHtmlExporterParameter"%>
<%@ page import="net.sf.jasperreports.engine.JasperPrint"%>
<%@ page import="net.sf.jasperreports.engine.JasperReport"%>
<%@ page import="net.sf.jasperreports.engine.JRRuntimeException"%>
<%@ page import="net.sf.jasperreports.j2ee.servlets.*"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.OutputStream"%>
<%@page import="com.klspta.base.util.UtilFactory"%>
<%@page import="com.klspta.model.report.api.ReportDataSource"%>
<%@page import="net.sf.jasperreports.engine.export.JRPdfExporterParameter"%>
<%
   //使用方法
   //必须传在数据库里面配置的ID和导出类型type type可供选择格式：excel pdf html
   //如：exportFile.jsp?id=123321&type=excle
   //condition filedValue 也可以进行添加，添加方法和ireport一样
   //

	String sty = request.getParameter("type");//导出类型
	//获取必要参数
	String id = request.getParameter("id");//报表ID
	String condition = request.getParameter("condition");//定制查询条件
	if (condition != null) {
		condition = UtilFactory.getStrUtil().unescape(condition);
	}
	String fieldValue = request.getParameter("fieldValue");//sql语句中需要填充的字段值

	if (fieldValue != null) {
		fieldValue = UtilFactory.getStrUtil().unescape(fieldValue);
	}

	String pathIndex = request.getParameter("pathIndex");//配置路径中有多个配置文件的情况下，要显示指定配置文件的索引。索引从0开始。

	//初始化数据源类
	ReportDataSource report = new ReportDataSource();
	report.loadReportParameter(condition, fieldValue, pathIndex);
	report.getReportType(id);

	String jasperPath = report.configPath;
	String reportFileName = application.getRealPath(jasperPath);
	File reportFile = new File(reportFileName);
	if (!reportFile.exists())
		throw new JRRuntimeException(
				"File WebappReport.jasper not found. The report design must be compiled first.");

	JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
    
    //ireport加载数据
    Map parameters = new HashMap();
	parameters.put("ReportTitle", "Address Report");
	parameters.put("BaseDir", reportFile.getParentFile());
	
	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,report);


	if (sty.equals("excel")) {
		// 声明导出对象
		JRXlsExporter exporter = new JRXlsExporter();
		session.setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
		// 设置导出模板
		exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
		// 设置输出流
		exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		// 设置Xls属性
		exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		// 告诉浏览器执行导出Xls操作
		response.setHeader("Content-Disposition", "attachment;filename=export.xls");
		response.setContentType("application/vnd.ms-excel");
		// 下面2个out的方法是为了解决tomcat输入流和输出流冲突，若不设置会报异常
		out.clear();
		out = pageContext.pushBody();
		exporter.exportReport();
	} else if (sty.equals("pdf")) {

		OutputStream outputStream = response.getOutputStream();
		//发送文件类型及编码
		JRPdfExporter exporter = new JRPdfExporter();
		session.setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
		exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, outputStream);
		response.setContentType("application/pdf");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=export.pdf");
		out.clear();
		out = pageContext.pushBody();
		exporter.exportReport();
		outputStream.close();
	} else if (sty.equals("html")) {

		//声明导出类
		JRHtmlExporter exporter = new JRHtmlExporter();
		//设置导出jasper_print
		exporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT, jasperPrint);
		//设置导出流
		exporter.setParameter(JRHtmlExporterParameter.OUTPUT_WRITER, response.getWriter());
		//设置
		exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
		//导出编码：
		exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, "utf-8");
		response.setCharacterEncoding("utf-8");
		//导出：
		response.setContentType("application/html");
		response.setHeader("Content-Disposition", "attachment;filename=export.html");
		// 下面2个out的方法是为了解决tomcat输入流和输出流冲突，若不设置会报异常
		out.clear();
		out = pageContext.pushBody();
		exporter.exportReport();

	}
%>
