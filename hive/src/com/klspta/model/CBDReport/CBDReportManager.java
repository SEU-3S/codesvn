package com.klspta.model.CBDReport;

import java.util.Map;

import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.tablestyle.ITableStyle;
import com.klspta.model.CBDReport.tablestyle.TableStyleDefault;
public class CBDReportManager {
    
    public static final boolean USE_CACHE = true;
	
	private FirstWorker firstWorker = new FirstWorker();
	
	private static TableStyleDefault tableStyleDefault = new TableStyleDefault();
    
    public StringBuffer getReport(String reportId){
        return getReport(reportId, null, tableStyleDefault, null);
    }
    
    public StringBuffer getReport(String reportId, String tableWidth){
        return getReport(reportId, null, tableStyleDefault, tableWidth);
    }
    
    public StringBuffer getReport(String reportId, Object[] where){
        return getReport(reportId, where, tableStyleDefault, null);
    }
    
    public StringBuffer getReport(String reportId, ITableStyle its){
        return getReport(reportId, null,its, null);
    }
    
    public StringBuffer getReport(String reportId, Object[] where, String tableWidth){
        return getReport(reportId, where, tableStyleDefault, tableWidth);
    }
    
    public StringBuffer getReport(String reportId, Object[] where, ITableStyle its){
        return getReport(reportId, where, its, null);
    }
    
    public StringBuffer getReport(String reportId, ITableStyle its, String tableWidth){
        return getReport(reportId, null, its, tableWidth);
    }
    
    public StringBuffer getReport(String reportId, Object[] where, ITableStyle its, String tableWidth){
        TableBuilder tableBuilder = new TableBuilder();
        try{
            Map<String, TRBean> c = firstWorker.build(reportId, where);
            if(tableWidth == null){
                tableWidth = firstWorker.getBean(reportId).getTableWidth();
            }
            return tableBuilder.prase(reportId, tableWidth, c, its);
        }catch(Exception e){
            return tableBuilder.getErrorMsg(e, its);
        }
    }
    
    public StringBuffer getFormula(String reportId, String id){
        FormulaWorker fw = new FormulaWorker();
        return new StringBuffer(fw.getFormula(reportId, id));
    }
}
