package com.klspta.web.cbd.dtjc.tjbb;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;

public class YearPlanBuilder extends AbstractBaseBean implements IDataClass {

    @Override
    public Map<String, TRBean> getTRBeans(Object[] obj, TRBean trBean) {
        Map<String, TRBean> trbeans = new LinkedHashMap<String, TRBean>();
        List<Map<String, Object>> ndList = getNd();
        List<Map<String, Object>> xmList = getXmmc();
        if (ndList.size() > 0) {
            trbeans.put("nd", buildTitle(ndList));
            buildKftlTotal(trbeans, ndList, xmList);
            buildKftz(trbeans, ndList, xmList);
            buildAzfTotal(trbeans, ndList, xmList);
            buildGdtlTotal(trbeans, ndList, xmList);
            buildGdgm(trbeans, ndList, xmList);
            buildRZTotal(trbeans, ndList, xmList);
        }
        return trbeans;
    }

    private List<Map<String, Object>> getNd() {
        String sql = "select distinct(nd) from hx_sx order by nd";
        List<Map<String, Object>> list = query(sql, YW);
        return list;
    }

    private List<Map<String, Object>> getXmmc() {
        String sql = "select distinct(xmname) as xmmc from jc_xiangmu t";
        List<Map<String, Object>> list = query(sql, YW);
        return list;
    }

    /**
     * 
     * <br>Description:构建列名
     * <br>Author:陈强峰
     * <br>Date:2013-11-4
     * @param nd
     * @return
     */
    private TRBean buildTitle(List<Map<String, Object>> ndList) {
        TRBean trb = new TRBean();
        trb.setCssStyle("tr01");
        TDBean td = new TDBean("序号", "90", "");
        td.setColspan("2");
        trb.addTDBean(td);
        td = new TDBean("时序(年)", "90", "");
        trb.addTDBean(td);
        for (int i = 0; i < ndList.size(); i++) {
            td = new TDBean(ndList.get(i).get("nd").toString(), "90", "");
            trb.addTDBean(td);
        }
        td = new TDBean("合计", "90", "");
        trb.addTDBean(td);
        return trb;
    }

    /**
     * 
     * <br>Description:构建开发体量总计
     * <br>Author:陈强峰
     * <br>Date:2013-11-4
     * @param trbeans
     * @param ndList
     */
    private void buildKftlTotal(Map<String, TRBean> trbeans, List<Map<String, Object>> ndList,
            List<Map<String, Object>> xmList) {
        TRBean trbhs = new TRBean();
        trbhs.setCssStyle("tr02");
        TRBean trbdl = new TRBean();
        trbdl.setCssStyle("tr02");
        TRBean trbgm = new TRBean();
        trbgm.setCssStyle("tr02");
        TRBean trbtz = new TRBean();
        trbtz.setCssStyle("tr02");

        TDBean tdBean = new TDBean("开发体量", "", "");
        tdBean.setRowspan("4");
        trbhs.addTDBean(tdBean);
        tdBean = new TDBean("1", "", "");
        trbhs.addTDBean(tdBean);
        tdBean = new TDBean("征收户数", "190", "");
        trbhs.addTDBean(tdBean);
        putTj(trbhs, "hx_sx", "zshs", ndList);

        tdBean = new TDBean("2", "", "");
        trbdl.addTDBean(tdBean);
        tdBean = new TDBean("完成开发地量(公顷)", "190", "");
        trbdl.addTDBean(tdBean);
        putTj(trbdl, "hx_sx", "wckfdl", ndList);

        tdBean = new TDBean("3", "", "");
        trbgm.addTDBean(tdBean);
        tdBean = new TDBean("完成开发规模（万㎡）", "190", "");
        trbgm.addTDBean(tdBean);
        putTj(trbgm, "hx_sx", "wckfgm", ndList);

        tdBean = new TDBean("4", "", "");
        trbtz.addTDBean(tdBean);
        tdBean = new TDBean("储备红线投资（亿元）", "190", "");
        trbtz.addTDBean(tdBean);
        putTj(trbtz, "hx_sx", "cbhxtz", ndList);

        trbeans.put("kfhs", trbhs);
        trbeans.put("kfdl", trbdl);
        trbeans.put("kfgm", trbgm);
        trbeans.put("kftz", trbtz);
    }

    /**
     * 
     * <br>Description:构建供地体量总计
     * <br>Author:陈强峰
     * <br>Date:2013-11-4
     * @param trbeans
     * @param ndList
     */
    private void buildGdtlTotal(Map<String, TRBean> trbeans, List<Map<String, Object>> ndList,
            List<Map<String, Object>> xmList) {
        TRBean trbgytd = new TRBean();
        trbgytd.setCssStyle("tr11");
        TRBean trbgygm = new TRBean();
        trbgygm.setCssStyle("tr11");
        TRBean trbcbkkc = new TRBean();
        trbcbkkc.setCssStyle("tr11");
        TRBean trbtz = new TRBean();
        trbtz.setCssStyle("tr11");

        TDBean tdBean = new TDBean("供地体量", "", "");
        tdBean.setRowspan("4");
        trbgytd.addTDBean(tdBean);
        tdBean = new TDBean("9", "", "");
        trbgytd.addTDBean(tdBean);
        tdBean = new TDBean("供应土地(公顷)", "190", "");
        trbgytd.addTDBean(tdBean);
        putTj(trbgytd, "hx_sx", "gytd", ndList);

        tdBean = new TDBean("10", "", "");
        trbgygm.addTDBean(tdBean);
        tdBean = new TDBean("供应规模(公顷)", "190", "");
        trbgygm.addTDBean(tdBean);
        putTj(trbgygm, "hx_sx", "gygm", ndList);

        tdBean = new TDBean("11", "", "");
        trbcbkkc.addTDBean(tdBean);
        tdBean = new TDBean("储备库库存（万㎡）", "190", "");
        trbcbkkc.addTDBean(tdBean);
        putTj(trbcbkkc, "hx_sx", "cbkkc", ndList);

        tdBean = new TDBean("12", "", "");
        trbtz.addTDBean(tdBean);
        tdBean = new TDBean("储备库融资能力（亿元）", "190", "");
        trbtz.addTDBean(tdBean);
        putTj(trbtz, "hx_sx", "cbkrznl", ndList);

        trbeans.put("gdhs", trbgytd);
        trbeans.put("gdgm", trbgygm);
        trbeans.put("gdkc", trbcbkkc);
        trbeans.put("gdrz", trbtz);
    }
    
    
    /**
     * 
     * <br>Description:构建安置房总计
     * <br>Author:陈强峰
     * <br>Date:2013-11-4
     * @param trbeans
     * @param ndList
     */
    private void buildAzfTotal(Map<String, TRBean> trbeans, List<Map<String, Object>> ndList,
            List<Map<String, Object>> xmList) {
        TRBean trbeanKg = new TRBean();
        trbeanKg.setCssStyle("tr07");
        TRBean trbtz = new TRBean();
        trbtz.setCssStyle("tr07");
        TRBean trbsyl = new TRBean();
        trbsyl.setCssStyle("tr07");
        TRBean trbcl = new TRBean();
        trbcl.setCssStyle("tr07");

        TDBean tdBean = new TDBean("安置房建设", "", "");
        tdBean.setRowspan("4");
        trbeanKg.addTDBean(tdBean);
        tdBean = new TDBean("5", "", "");
        trbeanKg.addTDBean(tdBean);
        tdBean = new TDBean("开工量（㎡）", "200", "");
        trbeanKg.addTDBean(tdBean);
        putAzf(trbeanKg,  "开工及购房量", ndList);

        tdBean = new TDBean("6", "", "");
        trbtz.addTDBean(tdBean);
        tdBean = new TDBean("投资（亿元）", "200", "");
        trbtz.addTDBean(tdBean);
        putAzf(trbtz, "投资", ndList);

        tdBean = new TDBean("7", "", "");
        trbsyl.addTDBean(tdBean);
        tdBean = new TDBean("使用量（万㎡）", "190", "");
        trbsyl.addTDBean(tdBean);
        putAzf(trbsyl,  "使用量", ndList);

        tdBean = new TDBean("8", "", "");
        trbcl.addTDBean(tdBean);
        tdBean = new TDBean("安置房存量（万㎡）", "190", "");
        trbcl.addTDBean(tdBean);
        putAzf(trbcl, "剩余量", ndList);

        trbeans.put("azfkgl", trbeanKg);
        trbeans.put("azftz", trbtz);
        trbeans.put("azfsyl", trbsyl);
        trbeans.put("azfcl", trbcl);
    }

    /**
     * 
     * <br>Description:构建融资
     * <br>Author:陈强峰
     * <br>Date:2013-11-4
     * @param trbeans
     * @param ndList
     */
    private void buildRZTotal(Map<String, TRBean> trbeans, List<Map<String, Object>> ndList,
            List<Map<String, Object>> xmList) {
        TRBean trbeanTzxq = new TRBean();
        trbeanTzxq.setCssStyle("tr16");
        TRBean trbeanHlcb = new TRBean();
        trbeanHlcb.setCssStyle("tr16");
        TRBean trbeanTdsy = new TRBean();
        trbeanTdsy.setCssStyle("tr16");
        TRBean trbeanRzxq = new TRBean();
        trbeanRzxq.setCssStyle("tr16");
        TRBean trbeanHkxq = new TRBean();
        trbeanHkxq.setCssStyle("tr16");
        TRBean trbeanZjzr = new TRBean();
        trbeanZjzr.setCssStyle("tr16");
        TRBean trbeanFzye = new TRBean();
        trbeanFzye.setCssStyle("tr16");
        TRBean trbeanZmye = new TRBean();
        trbeanZmye.setCssStyle("tr16");

        TDBean tdBean = new TDBean("投融资情况", "", "");
        tdBean.setRowspan("8");
        trbeanTzxq.addTDBean(tdBean);
        tdBean = new TDBean("13", "", "");
        trbeanTzxq.addTDBean(tdBean);
        tdBean = new TDBean("年度投资需求(亿元)", "190", "");
        trbeanTzxq.addTDBean(tdBean);
        putTj(trbeanTzxq, "hx_sx", "bqtzxq", ndList);

        tdBean = new TDBean("14", "", "");
        trbeanHlcb.addTDBean(tdBean);
        tdBean = new TDBean("年度回笼成本（亿元）", "200", "");
        trbeanHlcb.addTDBean(tdBean);
        putTj(trbeanHlcb,"hx_sx", "bqhlcb", ndList);

        tdBean = new TDBean("15", "", "");
        trbeanTdsy.addTDBean(tdBean);
        tdBean = new TDBean("政府土地收益(亿元)", "190", "");
        trbeanTdsy.addTDBean(tdBean);
        putTj(trbeanTdsy, "hx_sx", "zftdsy", ndList);

        tdBean = new TDBean("16", "", "");
        trbeanRzxq.addTDBean(tdBean);
        tdBean = new TDBean("年度融资需求(亿元)", "190", "");
        trbeanRzxq.addTDBean(tdBean);
        putTj(trbeanRzxq, "hx_sx", "bqrzxq", ndList);
        
        tdBean = new TDBean("17", "", "");
        trbeanHkxq.addTDBean(tdBean);
        tdBean = new TDBean("年度还款需求(亿元)", "190", "");
        trbeanHkxq.addTDBean(tdBean);
        putTj(trbeanHkxq, "hx_sx", "bqhkxq", ndList);
        
        tdBean = new TDBean("18", "", "");
        trbeanZjzr.addTDBean(tdBean);
        tdBean = new TDBean("权益性资金注入(亿元)", "190", "");
        trbeanZjzr.addTDBean(tdBean);
        putTj(trbeanZjzr, "hx_sx", "qyxzjzr", ndList);
        
        tdBean = new TDBean("19", "", "");
        trbeanFzye.addTDBean(tdBean);
        tdBean = new TDBean("负债余额", "190", "");
        trbeanFzye.addTDBean(tdBean);
        putTj(trbeanFzye, "hx_sx", "fzye", ndList);
        
        tdBean = new TDBean("20", "", "");
        trbeanZmye.addTDBean(tdBean);
        tdBean = new TDBean("年度账面余额", "190", "");
        trbeanZmye.addTDBean(tdBean);
        putTj(trbeanZmye, "hx_sx", "bqzmye", ndList);
        
        
        trbeans.put("trztzxq", trbeanTzxq);
        trbeans.put("trzhlcb", trbeanHlcb);
        trbeans.put("trztdsy", trbeanTdsy);
        trbeans.put("trzrzxq",trbeanRzxq);
        trbeans.put("trzhkxq",trbeanHkxq);
        trbeans.put("trzzjzr", trbeanZjzr);
        trbeans.put("trzfzye",trbeanFzye);
        trbeans.put("trzzmye",trbeanZmye);
    }    
    
    /**
     * 
     * <br>Description:构建开发体量投资
     * <br>Author:陈强峰
     * <br>Date:2013-11-4
     * @param ndList
     * @param xmList
     * @param trbeans
     */
    private void buildKftz(Map<String, TRBean> trbeans, List<Map<String, Object>> ndList,
            List<Map<String, Object>> xmList) {
        int count = xmList.size();
        String xmmc = "";
        List<Map<String, Object>> trList;
        for (int i = 0; i < count; i++) {
            xmmc = xmList.get(i).get("xmmc").toString();
            StringBuffer sb = new StringBuffer("select ");
            int ndCount = ndList.size();
            Object[] objs = new Object[ndCount * 2 + 1];
            for (int t = 0; t < ndCount; t++) {
                sb.append("(select  sum(t.tz)  from hx_kftl t  where xmmc = ? and nd =?) as ").append("s")
                        .append(i).append(",");
                objs[t * 2] = xmmc;
                objs[t * 2 + 1] = ndList.get(t).get("nd");
            }
            sb.append("(select  sum(t.tz)  from hx_kftl t  where xmmc = ?) as ").append("s").append(i)
                    .append(",");
            objs[ndCount * 2] = xmmc;
            sb.replace(sb.length() - 1, sb.length(), " from dual");
            trList = query(sb.toString(), YW, objs);
            TRBean trb = new TRBean();
            trb.setCssStyle("trsingle");

            TDBean tdbxh = new TDBean(i + 1 + "", "20", "");
            TDBean tdbtz = new TDBean("投资", "40", "");
            TDBean tdbmc = new TDBean(xmmc, "190", "");
            trb.addTDBean(tdbxh);
            trb.addTDBean(tdbtz);
            trb.addTDBean(tdbmc);
            if (trList.size() > 0) {
                Map<String, Object> mapKf = trList.get(0);
                for (int z = 0; z <= ndCount; z++) {
                    TDBean tb = new TDBean(checkNull(mapKf.get("s" + z)), "", "");
                    trb.addTDBean(tb);
                }
            } else {
                for (int z = 0; z <= ndCount; z++) {
                    TDBean tb = new TDBean("", "", "");
                    trb.addTDBean(tb);
                }
            }
            trbeans.put("kf" + tdbxh.getText(), trb);
        }
    }

    /**
     * 
     * <br>Description:构建供地规模
     * <br>Author:陈强峰
     * <br>Date:2013-11-4
     * @param ndList
     * @param xmList
     * @param trbeans
     */
    private void buildGdgm(Map<String, TRBean> trbeans, List<Map<String, Object>> ndList,
            List<Map<String, Object>> xmList) {
        int count = xmList.size();
        String xmmc = "";
        List<Map<String, Object>> trList;
        for (int i = 0; i < count; i++) {
            xmmc = xmList.get(i).get("xmmc").toString();
            StringBuffer sb = new StringBuffer("select ");
            int ndCount = ndList.size();
            Object[] objs = new Object[ndCount * 2 + 1];
            for (int t = 0; t < ndCount; t++) {
                sb.append("(select  sum(t.gm)  from hx_gdtl t  where xmmc = ? and nd =?) as ").append("s")
                        .append(i).append(",");
                objs[t * 2] = xmmc;
                objs[t * 2 + 1] = ndList.get(t).get("nd");
            }
            sb.append("(select  sum(t.gm)  from hx_gdtl t  where xmmc = ?) as ").append("s").append(i)
                    .append(",");
            objs[ndCount * 2] = xmmc;
            sb.replace(sb.length() - 1, sb.length(), " from dual");
            trList = query(sb.toString(), YW, objs);
            TRBean trb = new TRBean();
            trb.setCssStyle("trsingle");

            TDBean tdbxh = new TDBean(i + 1 + "", "20", "");
            TDBean tdbtz = new TDBean("规模", "40", "");
            TDBean tdbmc = new TDBean(xmmc, "190", "");
            trb.addTDBean(tdbxh);
            trb.addTDBean(tdbtz);
            trb.addTDBean(tdbmc);
            if (trList.size() > 0) {
                Map<String, Object> mapKf = trList.get(0);
                for (int z = 0; z <= ndCount; z++) {
                    TDBean tb = new TDBean(checkNull(mapKf.get("s" + z)), "", "");
                    trb.addTDBean(tb);
                }
            } else {
                for (int z = 0; z <= ndCount; z++) {
                    TDBean tb = new TDBean("", "", "");
                    trb.addTDBean(tb);
                }
            }
            trbeans.put("gd" + tdbxh.getText(), trb);
        }
    }    
    
    
    /**
     * 
     * <br>Description:统计(开发 安置 供地等)
     * <br>Author:陈强峰
     * <br>Date:2013-11-4
     * @param trbeans
     * @param trBean
     * @param field
     * @param ndList
     */
    private void putTj(TRBean trBean, String tableName, String field, List<Map<String, Object>> ndList) {
        StringBuffer sBuffer = new StringBuffer("select ");
        int count = ndList.size();
        Object[] objs = new Object[count];
        for (int i = 0; i < count; i++) {
            sBuffer.append("(select sum(").append(field).append(") from ").append(tableName).append(
                    " where nd=?)  as ").append("s").append(i).append(",");
            objs[i] = ndList.get(i).get("nd");
        }
        sBuffer.append("(select sum(").append(field).append(") from ").append(tableName).append(" t ) as ")
                .append("s").append(count);
        sBuffer.append(" from dual");
        List<Map<String, Object>> trList;
        trList = query(sBuffer.toString(), YW, objs);
        if (trList.size() > 0) {
            Map<String, Object> mapKf = trList.get(0);
            for (int z = 0; z <= count; z++) {
                TDBean tb = new TDBean(checkNull(mapKf.get("s" + z)), "", "");
                trBean.addTDBean(tb);
            }
        } else {
            for (int z = 0; z <= count; z++) {
                TDBean tb = new TDBean("", "", "");
                trBean.addTDBean(tb);
            }
        }
    }
    
    private void putAzf(TRBean trBean,String field, List<Map<String, Object>> ndList) {
        StringBuffer sBuffer = new StringBuffer("select ");
        int count = ndList.size();
        Object[] objs = new Object[count];
        for (int i = 0; i < count; i++) {
            sBuffer.append("(select sum(").append(field).append(") from zfjc.v_安置房").append(
                    " where 年度=?)  as ").append("s").append(i).append(",");
            objs[i] = ndList.get(i).get("nd");
        }
        /*sBuffer.append("(select sum(").append(field).append(") from zfjc.v_安置房").append(" t ) as ")
                .append("s").append(count);*/
        String str = sBuffer.substring(0, sBuffer.length()-1);
        str += " from dual";
        List<Map<String, Object>> trList;
        trList = query(str.toString(), YW, objs);
        if (trList.size() > 0) {
            Map<String, Object> mapKf = trList.get(0);
            for (int z = 0; z <= count; z++) {
                TDBean tb = new TDBean(checkNull(mapKf.get("s" + z)), "", "");
                trBean.addTDBean(tb);
            }
        } else {
            for (int z = 0; z <= count; z++) {
                TDBean tb = new TDBean("", "", "");
                trBean.addTDBean(tb);
            }
        }
    }

    private String checkNull(Object obj) {
        if (obj == null) {
            return "";
        }
        return obj.toString();
    }
}
