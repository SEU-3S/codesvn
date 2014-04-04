package com.klspta.model.CBDReport.bean;

import java.util.Map;

public class FirstBean {
	private String YWGuid;
	private String ReportName;
	private String tableWidth;
	private String ReportDescrition;
	
	public FirstBean(Map<String, Object> map){
		this.YWGuid = (String)map.get("YW_GUID");
		this.ReportName = (String)map.get("RP_NAME");
		this.tableWidth = (String)map.get("TB_WIDTH");
		this.ReportDescrition = (String)map.get("RP_DESCRIBE");
	}

	public String getYWGuid() {
		return YWGuid;
	}

	public String getReportName() {
		return ReportName;
	}

	public String getTableWidth() {
		return tableWidth;
	}

	public String getReportDescrition() {
		return ReportDescrition;
	}
}
