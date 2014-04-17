<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="net.sf.jasperreports.engine.*"%>
<%@ page import="net.sf.jasperreports.engine.util.*"%>
<%@ page import="net.sf.jasperreports.engine.export.*"%>
<%@ page import="net.sf.jasperreports.j2ee.servlets.*"%>
<%@ page import="java.io.*"%>
<%@page import="com.klspta.base.util.UtilFactory"%>
<%@page import="com.klspta.model.report.api.IReportDataSource"%>


<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	String condition = request.getParameter("condition");
	String fieldValue = request.getParameter("fieldValue");
	if (condition == null) {
		condition = "";
	}
	if (fieldValue == null) {
		fieldValue = "";
	}
	String id = request.getParameter("id");

	//根据ID获取报表的资源
	IReportDataSource ireport = new IReportDataSource();
	List<Map<String, Object>> list = ireport.getIReportResource(id);

	//获取jasper路径
	String jasperPath = list.get(0).get("jasperpath").toString();
	String reportFileName = application.getRealPath(jasperPath);
	//获取数据的sql
	String sql = list.get(0).get("sql").toString();
	//加载资源及各种条件参数
	condition = 
			UtilFactory.getStrUtil().escape(condition);
	ireport.loadResource(sql, condition, fieldValue);

	//ireport加载数据
	File reportFile = new File(reportFileName);
	if (!reportFile.exists())
		throw new JRRuntimeException(
				"File WebappReport.jasper not found. The report design must be compiled first.");

	JasperReport jasperReport = (JasperReport) JRLoader
			.loadObject(reportFile.getPath());
	JasperPrint jasperPrint = JasperFillManager.fillReport(
			jasperReport, null, ireport);

	JRHtmlExporter exporter = new JRHtmlExporter();
	session.setAttribute(
			ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,
			jasperPrint);

	exporter
			.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response
			.getWriter());

	exporter.setParameter(
			JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
			Boolean.FALSE);
	exporter.setParameter(
			JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
			Boolean.TRUE);
	exporter.setParameter(JRHtmlExporterParameter.ZOOM_RATIO, 1.3f);

	exporter.exportReport();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<base href="<%=basePath%>">

		<title>report result</title>


	</head>

</html>
