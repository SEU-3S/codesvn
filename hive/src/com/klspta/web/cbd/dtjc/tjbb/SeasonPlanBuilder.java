package com.klspta.web.cbd.dtjc.tjbb;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;

public class SeasonPlanBuilder extends AbstractBaseBean implements IDataClass {

    @Override
    public Map<String, TRBean> getTRBeans(Object[] obj, TRBean trBean) {
        Map<String, TRBean> trbeans = new LinkedHashMap<String, TRBean>();
        List<Map<String, Object>> ndList = getNd();
        List<Map<String, Object>> xmList = getXmmc();
        if (ndList.size() > 0) {
            trbeans.put("nd", buildTitle(ndList));
            buildKftlTotal(trbeans, ndList, xmList);
            buildKftl(trbeans, ndList, xmList);
            buildAzfTotal(trbeans, ndList, xmList);
            buildAzf(trbeans, ndList);
            buildGdtlTotal(trbeans, ndList, xmList);
            buildGdtl(trbeans, ndList, xmList);
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
            td = new TDBean(ndList.get(i).get("nd").toString(), "400", "");
            td.setColspan("8");
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
        tdBean = new TDBean("1", "45", "");
        trbhs.addTDBean(tdBean);
        tdBean = new TDBean("征收户数", "190", "");
        trbhs.addTDBean(tdBean);
        putTj(trbhs, "hx_sx", "zshs", ndList, true);

        tdBean = new TDBean("2", "45", "");
        trbdl.addTDBean(tdBean);
        tdBean = new TDBean("完成开发地量(公顷)", "190", "");
        trbdl.addTDBean(tdBean);
        putTj(trbdl, "hx_sx", "wckfdl", ndList, true);

        tdBean = new TDBean("3", "45", "");
        trbgm.addTDBean(tdBean);
        tdBean = new TDBean("完成开发规模（万㎡）", "190", "");
        trbgm.addTDBean(tdBean);
        putTj(trbgm, "hx_sx", "wckfgm", ndList, true);

        tdBean = new TDBean("4", "45", "");
        trbtz.addTDBean(tdBean);
        tdBean = new TDBean("储备红线投资（亿元）", "190", "");
        trbtz.addTDBean(tdBean);
        putTj(trbtz, "hx_sx", "cbhxtz", ndList, true);

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
        tdBean = new TDBean("9", "45", "");
        trbgytd.addTDBean(tdBean);
        tdBean = new TDBean("供应土地(公顷)", "190", "");
        trbgytd.addTDBean(tdBean);
        putTj(trbgytd, "hx_sx", "gytd", ndList, false);

        tdBean = new TDBean("10", "45", "");
        trbgygm.addTDBean(tdBean);
        tdBean = new TDBean("供应规模(公顷)", "190", "");
        trbgygm.addTDBean(tdBean);
        putTj(trbgygm, "hx_sx", "gygm", ndList, false);

        tdBean = new TDBean("11", "45", "");
        trbcbkkc.addTDBean(tdBean);
        tdBean = new TDBean("储备库库存（万㎡）", "190", "");
        trbcbkkc.addTDBean(tdBean);
        putTj(trbcbkkc, "hx_sx", "cbkkc", ndList, false);

        tdBean = new TDBean("12", "45", "");
        trbtz.addTDBean(tdBean);
        tdBean = new TDBean("储备库融资能力（亿元）", "190", "");
        trbtz.addTDBean(tdBean);
        putTj(trbtz, "hx_sx", "cbkrznl", ndList, false);

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
        tdBean = new TDBean("5", "45", "");
        trbeanKg.addTDBean(tdBean);
        tdBean = new TDBean("开工量（㎡）", "200", "");
        trbeanKg.addTDBean(tdBean);
        putAzfTotal(trbeanKg,  "开工及购房量", ndList);

        tdBean = new TDBean("6", "45", "");
        trbtz.addTDBean(tdBean);
        tdBean = new TDBean("投资（亿元）", "200", "");
        trbtz.addTDBean(tdBean);
        putAzfTotal(trbtz,  "投资", ndList);

        tdBean = new TDBean("7", "45", "");
        trbsyl.addTDBean(tdBean);
        tdBean = new TDBean("使用量（万㎡）", "190", "");
        trbsyl.addTDBean(tdBean);
        putAzfTotal(trbsyl, "使用量", ndList);

        tdBean = new TDBean("8", "45", "");
        trbcl.addTDBean(tdBean);
        tdBean = new TDBean("安置房存量（万㎡）", "190", "");
        trbcl.addTDBean(tdBean);
        putAzfTotal(trbcl, "剩余量", ndList);

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
        tdBean = new TDBean("13", "45", "");
        trbeanTzxq.addTDBean(tdBean);
        tdBean = new TDBean("年度投资需求(亿元)", "190", "");
        trbeanTzxq.addTDBean(tdBean);
        putTj(trbeanTzxq, "hx_sx", "bqtzxq", ndList, false);

        tdBean = new TDBean("14", "45", "");
        trbeanHlcb.addTDBean(tdBean);
        tdBean = new TDBean("年度回笼成本（亿元）", "200", "");
        trbeanHlcb.addTDBean(tdBean);
        putTj(trbeanHlcb, "hx_sx", "bqhlcb", ndList, false);

        tdBean = new TDBean("15", "45", "");
        trbeanTdsy.addTDBean(tdBean);
        tdBean = new TDBean("政府土地收益(亿元)", "190", "");
        trbeanTdsy.addTDBean(tdBean);
        putTj(trbeanTdsy, "hx_sx", "zftdsy", ndList, false);

        tdBean = new TDBean("16", "45", "");
        trbeanRzxq.addTDBean(tdBean);
        tdBean = new TDBean("年度融资需求(亿元)", "190", "");
        trbeanRzxq.addTDBean(tdBean);
        putTj(trbeanRzxq, "hx_sx", "bqrzxq", ndList, false);

        tdBean = new TDBean("17", "45", "");
        trbeanHkxq.addTDBean(tdBean);
        tdBean = new TDBean("年度还款需求(亿元)", "190", "");
        trbeanHkxq.addTDBean(tdBean);
        putTj(trbeanHkxq, "hx_sx", "bqhkxq", ndList, false);

        tdBean = new TDBean("18", "45", "");
        trbeanZjzr.addTDBean(tdBean);
        tdBean = new TDBean("权益性资金注入(亿元)", "190", "");
        trbeanZjzr.addTDBean(tdBean);
        putTj(trbeanZjzr, "hx_sx", "qyxzjzr", ndList, false);

        tdBean = new TDBean("19", "45", "");
        trbeanFzye.addTDBean(tdBean);
        tdBean = new TDBean("负债余额", "190", "");
        trbeanFzye.addTDBean(tdBean);
        putTj(trbeanFzye, "hx_sx", "fzye", ndList, false);

        tdBean = new TDBean("20", "45", "");
        trbeanZmye.addTDBean(tdBean);
        tdBean = new TDBean("年度账面余额", "190", "");
        trbeanZmye.addTDBean(tdBean);
        putTj(trbeanZmye, "hx_sx", "bqzmye", ndList, false);

        trbeans.put("trztzxq", trbeanTzxq);
        trbeans.put("trzhlcb", trbeanHlcb);
        trbeans.put("trztdsy", trbeanTdsy);
        trbeans.put("trzrzxq", trbeanRzxq);
        trbeans.put("trzhkxq", trbeanHkxq);
        trbeans.put("trzzjzr", trbeanZjzr);
        trbeans.put("trzfzye", trbeanFzye);
        trbeans.put("trzzmye", trbeanZmye);
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
    private void buildKftl(Map<String, TRBean> trbeans, List<Map<String, Object>> ndList,
            List<Map<String, Object>> xmList) {
        int count = xmList.size();
        String xmmc = "";
        for (int i = 0; i < count; i++) {
            xmmc = xmList.get(i).get("xmmc").toString();
            TRBean trbeanHs = new TRBean();
            TDBean tdBean = new TDBean(i + 1 + "", "", "");
            tdBean.setRowspan("8");
            trbeanHs.addTDBean(tdBean);
            tdBean = new TDBean("户数", "45", "");
            trbeanHs.addTDBean(tdBean);
            tdBean = new TDBean(xmmc, "200", "");
            tdBean.setRowspan("8");
            trbeanHs.addTDBean(tdBean);
            putSingle(xmmc, "hx_kftl", new String[] { "hs", "hsz" }, ndList, trbeanHs);
            trbeans.put("kftlhs" + i, trbeanHs);

            TRBean trbeanDl = new TRBean();
            tdBean = new TDBean("地量", "45", "");
            trbeanDl.addTDBean(tdBean);
            putSingle(xmmc, "hx_kftl", new String[] { "dl", "dlz" }, ndList, trbeanDl);
            trbeans.put("kftldl" + i, trbeanDl);

            TRBean trbeanGm = new TRBean();
            tdBean = new TDBean("规模", "45", "");
            trbeanGm.addTDBean(tdBean);
            putSingle(xmmc, "hx_kftl", new String[] { "gm", "gmz" }, ndList, trbeanGm);
            trbeans.put("kftlgm" + i, trbeanGm);

            TRBean trbeanTz = new TRBean();
            tdBean = new TDBean("投资", "45", "");
            trbeanTz.addTDBean(tdBean);
            putSingle(xmmc, "hx_kftl", new String[] { "tz", "tzz" }, ndList, trbeanTz);
            trbeans.put("kftltz" + i, trbeanTz);

            TRBean trbeanZ = new TRBean();
            tdBean = new TDBean("住", "45", "");
            trbeanZ.addTDBean(tdBean);
            putSingle(xmmc, "hx_kftl", new String[] { "zhu", "zhuz" }, ndList, trbeanZ);
            trbeans.put("kftlz" + i, trbeanZ);

            TRBean trbeanQ = new TRBean();
            tdBean = new TDBean("企", "45", "");
            trbeanQ.addTDBean(tdBean);
            putSingle(xmmc, "hx_kftl", new String[] { "qi", "qiz" }, ndList, trbeanQ);
            trbeans.put("kftlq" + i, trbeanQ);

            TRBean trbeanLm = new TRBean();
            tdBean = new TDBean("楼面", "45", "");
            trbeanLm.addTDBean(tdBean);
            putSingle(xmmc, "hx_kftl", new String[] { "lm", "'——'" }, ndList, trbeanLm);
            trbeans.put("kftllm" + i, trbeanLm);

            TRBean trbeanCj = new TRBean();
            tdBean = new TDBean("成交", "45", "");
            trbeanCj.addTDBean(tdBean);
            putSingle(xmmc, "hx_kftl", new String[] { "cj", "'——'" }, ndList, trbeanCj);
            trbeans.put("kftlcj" + i, trbeanCj);
        }
    }

    /**
     * 
     * <br>Description:构建供地体量投资
     * <br>Author:陈强峰
     * <br>Date:2013-11-4
     * @param ndList
     * @param xmList
     * @param trbeans
     */
    private void buildGdtl(Map<String, TRBean> trbeans, List<Map<String, Object>> ndList,
            List<Map<String, Object>> xmList) {
        int count = xmList.size();
        String xmmc = "";
        for (int i = 0; i < count; i++) {
            xmmc = xmList.get(i).get("xmmc").toString();
            TRBean trbeanDl = new TRBean();
            TDBean tdBean = new TDBean(i + 1 + "", "", "");
            tdBean.setRowspan("6");
            trbeanDl.addTDBean(tdBean);
            tdBean = new TDBean("地量", "45", "");
            trbeanDl.addTDBean(tdBean);
            tdBean = new TDBean(xmmc, "200", "");
            tdBean.setRowspan("6");
            trbeanDl.addTDBean(tdBean);
            putSingle(xmmc, "hx_gdtl", new String[] { "dl", "dlz" }, ndList, trbeanDl);
            trbeans.put("gdtlhs" + i, trbeanDl);

            TRBean trbeanGm = new TRBean();
            tdBean = new TDBean("规模", "45", "");
            trbeanGm.addTDBean(tdBean);
            putSingle(xmmc, "hx_gdtl", new String[] { "gm", "gmz" }, ndList, trbeanGm);
            trbeans.put("gdtlgm" + i, trbeanGm);

            TRBean trbeanCb = new TRBean();
            tdBean = new TDBean("成本", "45", "");
            trbeanCb.addTDBean(tdBean);
            putSingle(xmmc, "hx_gdtl", new String[] { "cb", "cbz" }, ndList, trbeanCb);
            trbeans.put("gdtlcb" + i, trbeanCb);

            TRBean trbeanSy = new TRBean();
            tdBean = new TDBean("收益", "45", "");
            trbeanSy.addTDBean(tdBean);
            putSingle(xmmc, "hx_gdtl", new String[] { "sy", "syz" }, ndList, trbeanSy);
            trbeans.put("gdtlsy" + i, trbeanSy);

            TRBean trbeanZj = new TRBean();
            tdBean = new TDBean("总价", "45", "");
            trbeanZj.addTDBean(tdBean);
            putSingle(xmmc, "hx_gdtl", new String[] { "zj", "zjz" }, ndList, trbeanZj);
            trbeans.put("gdtlzj" + i, trbeanZj);

            TRBean trbeanZuj = new TRBean();
            tdBean = new TDBean("租金", "45", "");
            trbeanZuj.addTDBean(tdBean);
            putSingle(xmmc, "hx_gdtl", new String[] { "zuj", "'——'" }, ndList, trbeanZuj);
            trbeans.put("gdtlzuj" + i, trbeanZuj);
        }
    }

    /**
     * 
     * <br>Description:安置房详细
     * <br>Author:陈强峰
     * <br>Date:2013-11-5
     * @param trbeans
     * @param ndList
     */
    private void buildAzf(Map<String, TRBean> trbeans, List<Map<String, Object>> ndList) {
        String sql = "select distinct(xmmc),tzmc from hx_azf";
        List<Map<String, Object>> kgxmList = query(sql, YW);
        if (kgxmList.size() > 0) {
            TRBean trBeanTz;
            TRBean trBeanKg = new TRBean();
            TDBean tdBean = new TDBean("", "", "");
            tdBean.setRowspan(kgxmList.size() * 2 + "");
            trBeanKg.addTDBean(tdBean);
            for (int i = 0; i < kgxmList.size(); i++) {
                Object kgmc = kgxmList.get(i).get("xmmc");
                Object tzmc = kgxmList.get(i).get("tzmc");
                if (i != 0) {
                    trBeanKg = new TRBean();
                }
                tdBean = new TDBean("开工", "45", "");
                trBeanKg.addTDBean(tdBean);
                tdBean = new TDBean(checkNull(kgmc), "200", "");
                trBeanKg.addTDBean(tdBean);
                putAzf(checkNull(kgmc), "hx_azf", new String[] { "kg", "kgbl" }, ndList, trBeanKg);

                trBeanTz = new TRBean();
                tdBean = new TDBean("投资", "45", "");
                trBeanTz.addTDBean(tdBean);
                tdBean = new TDBean(checkNull(tzmc), "200", "");
                trBeanTz.addTDBean(tdBean);
                putAzf(checkNull(kgmc), "hx_azf", new String[] { "kg", "kgbl" }, ndList, trBeanTz);
                trbeans.put("azfkg" + i, trBeanKg);
                trbeans.put("azftz" + i, trBeanTz);
            }
        }
    }

    private void putSingle(String xmmc, String tableName, String[] fields, List<Map<String, Object>> ndList,
            TRBean trBean) {
        StringBuffer sBuffer = new StringBuffer("select ");
        int ndCount = ndList.size();
        Object nd;
        Object[] objs = new Object[ndCount * 8 + 1];
        for (int t = 0; t < ndCount; t++) {
            sBuffer.append("(select ").append(fields[0]).append("||'#'||").append(fields[1]).append(" from ")
                    .append(tableName).append(" where xmmc=? and nd=? and jd='1')  as ").append("s")
                    .append(t).append("1,");
            sBuffer.append("(select ").append(fields[0]).append("||'#'||").append(fields[1]).append(" from ")
                    .append(tableName).append(" where xmmc=? and nd=? and jd='2')  as ").append("s")
                    .append(t).append("2,");
            sBuffer.append("(select ").append(fields[0]).append("||'#'||").append(fields[1]).append(" from ")
                    .append(tableName).append(" where xmmc=? and nd=? and jd='3')  as ").append("s")
                    .append(t).append("3,");
            sBuffer.append("(select ").append(fields[0]).append("||'#'||").append(fields[1]).append(" from ")
                    .append(tableName).append(" where xmmc=? and nd=? and jd='4')  as ").append("s")
                    .append(t).append("4,");
            nd = ndList.get(t).get("nd");
            objs[t * 8] = xmmc;
            objs[t * 8 + 1] = nd;
            objs[t * 8 + 2] = xmmc;
            objs[t * 8 + 3] = nd;
            objs[t * 8 + 4] = xmmc;
            objs[t * 8 + 5] = nd;
            objs[t * 8 + 6] = xmmc;
            objs[t * 8 + 7] = nd;
        }
        sBuffer.append("(select sum(").append(fields[0]).append(") from ").append(tableName).append(
                " t where xmmc=? ) as ").append("s").append(ndCount);
        sBuffer.append(" from dual");
        objs[ndCount * 8] = xmmc;
        List<Map<String, Object>> trList;
        trList = query(sBuffer.toString(), YW, objs);
        TDBean tb;
        if (trList.size() > 0) {
            Map<String, Object> mapKf = trList.get(0);
            for (int z = 0; z <= ndCount; z++) {
                if (z == ndCount) {
                    tb = new TDBean(checkNull(mapKf.get("s" + z)), "90", "");
                    trBean.addTDBean(tb);
                } else {
                    for (int i = 1; i <= 4; i++) {
                        String[] value = checkNull(mapKf.get("s" + z + i)).split("#");
                        if (value.length > 1) {
                            tb = new TDBean(value[0], "50", "");
                            tb.setStyle("tdfull");
                            trBean.addTDBean(tb);
                            tb = new TDBean(value[1], "50", "");
                            tb.setStyle("tdfull");
                            trBean.addTDBean(tb);
                        }else{
                            tb = new TDBean("", "50", "");
                            tb.setStyle("tdnull");
                            trBean.addTDBean(tb);
                            trBean.addTDBean(tb);
                        }
                    }
                }
            }
        } else {
            for (int z = 0; z <= ndCount; z++) {
                tb = new TDBean("", "100", "");
                tb.setStyle("tdnull");
                trBean.addTDBean(tb);
            }
        }
    }
    
    private void putAzf(String xmmc, String tableName, String[] fields, List<Map<String, Object>> ndList,
            TRBean trBean) {
        StringBuffer sBuffer = new StringBuffer("select ");
        int ndCount = ndList.size();
        Object nd;
        Object[] objs = new Object[ndCount * 8 + 1];
        for (int t = 0; t < ndCount; t++) {
            sBuffer.append("(select ").append(fields[0]).append("||'#'||").append(fields[1]).append(" from ")
                    .append(tableName).append(" where xmmc=? and nd=? and jd='1')  as ").append("s")
                    .append(t).append("1,");
            sBuffer.append("(select ").append(fields[0]).append("||'#'||").append(fields[1]).append(" from ")
                    .append(tableName).append(" where xmmc=? and nd=? and jd='2')  as ").append("s")
                    .append(t).append("2,");
            sBuffer.append("(select ").append(fields[0]).append("||'#'||").append(fields[1]).append(" from ")
                    .append(tableName).append(" where xmmc=? and nd=? and jd='3')  as ").append("s")
                    .append(t).append("3,");
            sBuffer.append("(select ").append(fields[0]).append("||'#'||").append(fields[1]).append(" from ")
                    .append(tableName).append(" where xmmc=? and nd=? and jd='4')  as ").append("s")
                    .append(t).append("4,");
            nd = ndList.get(t).get("nd");
            objs[t * 8] = xmmc;
            objs[t * 8 + 1] = nd;
            objs[t * 8 + 2] = xmmc;
            objs[t * 8 + 3] = nd;
            objs[t * 8 + 4] = xmmc;
            objs[t * 8 + 5] = nd;
            objs[t * 8 + 6] = xmmc;
            objs[t * 8 + 7] = nd;
        }
        sBuffer.append("(select sum(").append(fields[0]).append(") from ").append(tableName).append(
                " t where xmmc=? ) as ").append("s").append(ndCount);
        sBuffer.append(" from dual");
        objs[ndCount * 8] = xmmc;
        List<Map<String, Object>> trList;
        trList = query(sBuffer.toString(), YW, objs);
        TDBean tb;
        if (trList.size() > 0) {
            Map<String, Object> mapKf = trList.get(0);
            for (int z = 0; z <= ndCount; z++) {
                if (z == ndCount) {
                    tb = new TDBean(checkNull(mapKf.get("s" + z)), "90", "");
                    trBean.addTDBean(tb);
                } else {
                    for (int i = 1; i <= 4; i++) {
                        String[] value = checkNull(mapKf.get("s" + z + i)).split("#");
                        if (value.length > 1) {
                            tb = new TDBean(value[0], "50", "");
                            tb.setStyle("tdfull");
                            trBean.addTDBean(tb);
                            tb = new TDBean(value[1], "50", "");
                            tb.setStyle("tdfull");
                            trBean.addTDBean(tb);
                        } else if(value.length==1){
                        	if(value[0]==null || value[0]==""){
                        		tb=new TDBean("","50","");
                            	tb.setStyle("tdnull");
                            	trBean.addTDBean(tb);
                            	trBean.addTDBean(tb);
                        	}else{
                            tb = new TDBean(value[0], "50", "");
                            tb.setStyle("tdfull");
                            trBean.addTDBean(tb);
                            tb = new TDBean("","50","");
                            tb.setStyle("tdfull");
                            trBean.addTDBean(tb);
                        	}
                        }
                    }
                }
            }
        } else {
            for (int z = 0; z <= ndCount; z++) {
                tb = new TDBean("", "100", "");
                tb.setStyle("tdnull");
                trBean.addTDBean(tb);
            }
        }
    }

    /**
     * 
     * <br>Description:统计季度(开发)
     * <br>Author:陈强峰
     * <br>Date:2013-11-4
     * @param trbeans
     * @param trBean
     * @param field
     * @param ndList
     */
    private void putTj(TRBean trBean, String tableName, String field, List<Map<String, Object>> ndList,
            boolean isKf) {
        StringBuffer sBuffer = new StringBuffer("select ");
        int count = ndList.size();
        Object[] objs = new Object[count * 4];
        Object nd;
        for (int i = 0; i < count; i++) {
            sBuffer.append("(select ").append(field).append(" from ").append(tableName).append(
                    " where nd=? and jd='1')  as ").append("s").append(i).append("1,");
            sBuffer.append("(select ").append(field).append(" from ").append(tableName).append(
                    " where nd=? and jd='2')  as ").append("s").append(i).append("2,");
            sBuffer.append("(select ").append(field).append(" from ").append(tableName).append(
                    " where nd=? and jd='3')  as ").append("s").append(i).append("3,");
            sBuffer.append("(select ").append(field).append(" from ").append(tableName).append(
                    " where nd=? and jd='4')  as ").append("s").append(i).append("4,");
            nd = ndList.get(i).get("nd");
            objs[i * 4] = nd;
            objs[i * 4 + 1] = nd;
            objs[i * 4 + 2] = nd;
            objs[i * 4 + 3] = nd;
        }
        sBuffer.append("(select sum(").append(field).append(") from ").append(tableName).append(" t ) as ")
                .append("s").append(count);
        sBuffer.append(" from dual");
        List<Map<String, Object>> trList;
        trList = query(sBuffer.toString(), YW, objs);
        TDBean tb;
        if (isKf) {
            if (trList.size() > 0) {
                Map<String, Object> mapKf = trList.get(0);
                for (int z = 0; z <= count; z++) {
                    if (z == count) {
                        tb = new TDBean(checkNull(mapKf.get("s" + z)), "90", "");
                        trBean.addTDBean(tb);
                    } else {
                        for (int i = 1; i <= 4; i++) {
                            tb = new TDBean(checkNull(mapKf.get("s" + z + i)), "100", "");
                            tb.setColspan("2");
                            trBean.addTDBean(tb);
                        }
                    }
                }
            } else {
                int total = count * 4;
                for (int z = 0; z <= total; z++) {
                    tb = new TDBean("", "100", "");
                    if (z != total) {
                        tb.setColspan("2");
                    }
                    trBean.addTDBean(tb);
                }
            }
        } else {
            if (trList.size() > 0) {
                Map<String, Object> mapKf = trList.get(0);
                for (int z = 0; z <= count; z++) {
                    if (z == count) {
                        tb = new TDBean(checkNull(mapKf.get("s" + z)), "90", "");
                        trBean.addTDBean(tb);
                    } else {
                        for (int i = 1; i <= 4; i++) {
                            tb = new TDBean(checkNull(mapKf.get("s" + z + i)), "50", "");
                            trBean.addTDBean(tb);
                            tb = new TDBean("", "50", "");
                            trBean.addTDBean(tb);
                        }
                    }
                }
            } else {
                int total = count * 4;
                for (int z = 0; z < total; z++) {
                    tb = new TDBean("", "50", "");
                    trBean.addTDBean(tb);
                    tb = new TDBean("", "50", "");
                    trBean.addTDBean(tb);
                }
                tb = new TDBean("", "90", "");
                trBean.addTDBean(tb);
            }

        }
    }

    private void putAzfTotal(TRBean trBean,String field, List<Map<String, Object>> ndList){
    	 StringBuffer sBuffer = new StringBuffer();
    	 sBuffer.append("select ");
    	 int count = ndList.size();
         Object[] objs = new Object[count * 4];
         Object nd;
         for (int i = 0; i < count; i++) {
             sBuffer.append("(select ").append(field).append(" from zfjc.v_安置房 ").append(
                     " where 年度=? and 季度='1')  as ").append("s").append(i).append("1,");
             sBuffer.append("(select ").append(field).append(" from zfjc.v_安置房 ").append(
                     " where 年度=? and 季度='2')  as ").append("s").append(i).append("2,");
             sBuffer.append("(select ").append(field).append(" from zfjc.v_安置房 ").append(
                     " where 年度=? and 季度='3')  as ").append("s").append(i).append("3,");
             sBuffer.append("(select ").append(field).append(" from zfjc.v_安置房 ").append(
                     " where 年度=? and 季度='4')  as ").append("s").append(i).append("4,");
             nd = ndList.get(i).get("nd");
             objs[i * 4] = nd;
             objs[i * 4 + 1] = nd;
             objs[i * 4 + 2] = nd;
             objs[i * 4 + 3] = nd;
            
         }
         String str = sBuffer.substring(0, sBuffer.length()-1);
         str += " from dual";
         List<Map<String, Object>> trList;
         trList = query(str, YW, objs);
         TDBean tb;
         if (trList.size() > 0) {
             Map<String, Object> mapKf = trList.get(0);
             for (int z = 0; z <= count; z++) {
                 if (z == count) {
                     tb = new TDBean(checkNull(mapKf.get("s" + z)), "90", "");
                     trBean.addTDBean(tb);
                 } else {
                     for (int i = 1; i <= 4; i++) {
                         tb = new TDBean(checkNull(mapKf.get("s" + z + i)), "50", "");
                         trBean.addTDBean(tb);
                         tb = new TDBean("", "50", "");
                         trBean.addTDBean(tb);
                     }
                 }
             }
         } else {
             int total = count * 4;
             for (int z = 0; z < total; z++) {
                 tb = new TDBean("", "50", "");
                 trBean.addTDBean(tb);
                 tb = new TDBean("", "50", "");
                 trBean.addTDBean(tb);
             }
             tb = new TDBean("", "90", "");
             trBean.addTDBean(tb);
         }

    }
    
    private String checkNull(Object obj) {
        if (obj == null) {
            return "";
        }
        return obj.toString();
    }

}
