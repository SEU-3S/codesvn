package com.klspta.model.CBDReport.bean;

import java.util.Map;

public class SecondBean {
	private String YWGuid;
	private String FirstKey;
	private String PartType;
	
	public SecondBean(Map<String, Object> map){
		this.YWGuid = (String)map.get("YW_GUID");
		this.FirstKey = (String)map.get("FIRST_KEY");
		this.PartType = (String)map.get("PART_TYPE");
	}
	
	public String getYWGuid() {
		return YWGuid;
	}
	public void setYWGuid(String guid) {
		YWGuid = guid;
	}
	public String getFirstKey() {
		return FirstKey;
	}
	public void setFirstKey(String firstKey) {
		FirstKey = firstKey;
	}
	public String getPartType() {
		return PartType;
	}
	public void setPartType(String partType) {
		PartType = partType;
	}
}
