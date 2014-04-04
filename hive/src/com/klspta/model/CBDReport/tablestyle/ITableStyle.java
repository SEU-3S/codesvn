package com.klspta.model.CBDReport.tablestyle;

import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;

public abstract class ITableStyle {
    public abstract String getTable1();
    public abstract String getTable2();
    public abstract String getTR1(TRBean trBean);
    public abstract String getTR2(TRBean trBean);
    public abstract String getTD1(TDBean tdBean);
    public abstract String getTD2(TDBean tdBean);
    public StringBuffer getErrorMsg(Exception e){
        StringBuffer trace = new StringBuffer();
        StackTraceElement[] ele = e.getStackTrace();
        for(int i = 0; i < ele.length; i++){
            trace.append(ele[i].getClassName()).append(".").append(ele[i].getMethodName()).append("&nbsp&nbsp&nbsp").append(ele[i].getLineNumber()).append("<br>");
        }
        StringBuffer sb = new StringBuffer("<font size='5' color='#990900'>数据生成失败，请稍后重试或联系管理员！</font><br><br>")
        .append("<font size='3' color='#990900'>错误产生的原因是： ").append(e.getMessage()).append("</font><br><br>")
        .append("<table><tr><td id='showbfq' height='20' onclick=\"if(bfq.style.display=='none'){bfq.style.display='';showbfq.innerText='关闭详细信息';}else{bfq.style.display='none';showbfq.innerText='更多详细信息';}\">更多详细信息</td>")
        .append("</tr><tr><td height='80' id='bfq' style='display:none;'><font size='1' color='#000001'>").append(trace).append("</font></td></tr></table>");
        return sb;
    }
}
