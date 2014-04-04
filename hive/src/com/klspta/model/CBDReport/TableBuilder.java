package com.klspta.model.CBDReport;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.tablestyle.ITableStyle;
import com.klspta.model.CBDReport.tablestyle.TableStyleDefault;

public class TableBuilder {
    private static String wr = "";
    public StringBuffer prase(String reportID, String tableWidth, Map<String, TRBean> c, ITableStyle its) {
        if(CBDReportManager.USE_CACHE){
            wr = "";
        }else{
            wr = "非缓存模式，测试时使用，正式部署请修改CBDreportManager 类的 USE_CACHE 参数为 true！";
        }
        StringBuffer html = new StringBuffer(wr).append(its.getTable1().replace("#TABLEWIDTH", tableWidth).replace("#REPORTNAME", reportID));
        Iterator<Map.Entry<String, TRBean>> iter = c.entrySet().iterator();
        int trIndex = 0;
        while (iter.hasNext()) {
            Map.Entry<String, TRBean> entry = (Map.Entry<String, TRBean>) iter.next();
            TRBean val = (TRBean) entry.getValue();
            html.append(buildTR(trIndex++, val, its));
        }
        return html.append(its.getTable2());
    }

    public StringBuffer prase(String reportID, String tableWidth, Map<String, TRBean> c) {
        return prase(reportID, tableWidth, c, new TableStyleDefault());
    }

    private StringBuffer buildTR(int trIndex, TRBean trBean, ITableStyle its) {
        if (trBean != null) {
            Vector<TDBean> tdBeans = trBean.getTDBeans();
            StringBuffer tds = new StringBuffer(its.getTR1(trBean).replace("#TRCSS", trBean.getCssStyle()));
            for (int j = 0; j < tdBeans.size(); j++) {
                tds.append(buildTD(trIndex, j, tdBeans.get(j), its));
            }
            return tds.append(its.getTR2(trBean));
        }
        return null;
    }

    private String buildTD(int trIndex, int tdIndex, TDBean tdBean, ITableStyle its) {
        String html = its.getTD1(tdBean);
        html = html.replace("#TDINDEX", trIndex + "_" + tdIndex);
        html = html.replace("#HEIGHT", null2String(tdBean.getHeight()));
        html = html.replace("#WIDTH", null2String(tdBean.getWidth()));
        html = html.replace("#COLSPAN", null2String(tdBean.getColspan()));
        html = html.replace("#ROWPAN", null2String(tdBean.getRowspan()));
        html = html.replace("#TEXT", null2String(tdBean.getText()));
        html = html.replace("#STYLE", null2String(tdBean.getStyle()));
        html = html.replace("#TDCSS", null2String(tdBean.getHeight()));
        return html;
    }
    
    private String null2String(String input){
        if(input == null){
            return "";
        }else{
            return input;
        }
    }
    
    public StringBuffer getErrorMsg(Exception e, ITableStyle its){
        return its.getErrorMsg(e);
    }
}
