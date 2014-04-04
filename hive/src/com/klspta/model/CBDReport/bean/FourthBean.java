package com.klspta.model.CBDReport.bean;

import java.util.Map;

public class FourthBean {
	private String wyGuid = "";
	private String thirdKey = "";
	private String dataSource = "";
	private String dataFrom = "";
	private String sql = "";
	private String className = "";
	
	public FourthBean(Map<String, Object> map){
		this.wyGuid = (String)map.get("YW_GUID");
		this.thirdKey = (String)map.get("THIRD_KEY");
		this.dataSource = (String)map.get("DATA_SOURCE");
		this.dataFrom = (String)map.get("DATA_FROM");
		this.sql = (String)map.get("SQL");
		this.className = (String)map.get("CLASS_NAME");
	}
	
	public String getWyGuid() {
		return wyGuid;
	}
	public String getThirdKey() {
		return thirdKey;
	}
	public String getDataSource() {
		return dataSource;
	}
	public String getDataFrom() {
		return dataFrom;
	}
	public String getSql() {
		return sql;
	}
	public String getClassName() {
		return className;
	}

}
