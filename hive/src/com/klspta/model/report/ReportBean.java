package com.klspta.model.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class ReportBean {

    String reportId;

    String reportName;

    String jasperPath;

    String queryPath;
    
    boolean isHaveChart;
    
    private String parentid = "";
    //描述
    private String remark ;
    //数据类型
    private String data_type;
    //数据生成时间
    private String data_generation_time;
    //系统自动生成id
    private String id;
    public ReportBean(){}
    public ReportBean(Map<String, Object> map){
        this.reportId = (String)map.get("reportId");
        this.reportName = (String)map.get("reportName");
        this.jasperPath = (String)map.get("jasperPath");
        this.queryPath = (String)map.get("queryPath");
        this.parentid = (String)map.get("parentid");
        if(null == map.get("remark")){
        	this.remark =" ";
        }else{
        	this.remark = (String)map.get("remark");
        }
        this.data_type = (String)map.get("data_type");
        this.parentid = (String)map.get("parentid");
        String ihchart = (String)map.get("isHaveChart");
        this.isHaveChart = "1".equals(ihchart)?true:false;   
        Date s=(Date)map.get("data_generation_time");
        String t="";
        if(s != null){
        	if(!"0".equals((String)map.get("parentid"))){
        		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        		t=sdf.format(s);
        	}
        }
        this.data_generation_time =t;
        this.id = (String)map.get("id");
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getJasperPath() {
        return jasperPath;
    }

    public void setJasperPath(String jasperPath) {
        this.jasperPath = jasperPath;
    }

    public String getQueryPath() {
        if(queryPath==null){
            return "";
        }
        return queryPath;
    }

    public void setQueryPath(String queryPath) {
        this.queryPath = queryPath;
    }

    public boolean getIsHaveChart() {
        return isHaveChart;
    }

    public void setHaveChart(boolean isHaveChart) {
        this.isHaveChart = isHaveChart;
    }
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public String getData_generation_time() {
		return data_generation_time;
	}
	public void setData_generation_time(String data_generation_time) {
		this.data_generation_time = data_generation_time;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
