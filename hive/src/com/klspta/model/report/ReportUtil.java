package com.klspta.model.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
/**
 * <br>Title:报表操作工具
 * <br>Description:
 * <br>Author:李亚栓
 * <br>Date:2012-8-6
 */
public class ReportUtil extends AbstractBaseBean{
    private  ReportUtil reportUtil;

    public static  List<ReportBean> reportBeanList=new ArrayList<ReportBean>();
    
    public ReportUtil(){
    	flush();
    }
    public  ReportUtil getInstance() {
        if (reportUtil == null) {
            reportUtil = new ReportUtil();
            flush();
        }
        return reportUtil;
    }

    public void flush() {
        String sql = "select * from report_resource t where t.flag='1'";
        List<Map<String, Object>> list=query(sql,CORE);
        for(int i=0;i<list.size();i++){
        	Map<String, Object> map=list.get(i);
            ReportBean reportBean = new ReportBean();
            reportBean.setId((String)map.get("id"));
            reportBean.setReportId((String)map.get("reportid"));
            reportBean.setReportName((String)map.get("reportName"));
            reportBean.setJasperPath((String)map.get("jasperPath"));
            reportBean.setQueryPath((String)map.get("queryPath"));
            reportBean.setParentid((String)map.get("parentid"));
            boolean ishavechart=true;
            String s=(String)map.get("isHaveChart");
            if("0".equals(s)) ishavechart=false;
            reportBean.setHaveChart(ishavechart);
            reportBeanList.add(reportBean);
        }      
    }
    public ReportBean getReportBeanById(String id) {
        if (id == null || "".equals(id)) {
            return null;
        }
        System.out.println(reportBeanList.size());
        for (int i = 0; i < reportBeanList.size(); i++) {
            if (((ReportBean) reportBeanList.get(i)).getId().endsWith(id)) {
                return (ReportBean) reportBeanList.get(i);
            }
        }
        return null;
    }
}
