package com.klspta.web.cbd.yzt.hxxm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;

/**
 * 
 * <br>
 * Title:基本斑详细列表 <br>
 * Description:生成基本详细列表 <br>
 * Author:黎春行 <br>
 * Date:2013-12-18
 */
public class HxxmReport extends AbstractBaseBean implements IDataClass {
	public static String[][] shows = new String[][] { { "rownum", "false" },
			{ "xmname", "false" }, { "zd", "false" }, { "jsyd", "false" },
			{ "rjl", "false" }, { "jzgm", "false" }, { "ghyt", "false" },
			{ "GJJZGM", "false" }, { "JZJZGM", "false" },
			{ "SZJZGM", "false" }, { "zzsgm", "false" }, { "zzzsgm", "false" },
			{ "zzzshs", "false" }, { "hjmj", "false" }, { "fzzzsgm", "false" },
			{ "fzzjs", "false" }, { "KFCB", "false" }, { "LMCB", "false" },
			{ "DMCB", "false" }, { "YJCJJ", "false" }, { "YJZFTDSY", "false" },
			{ "CXB", "false" }, { "CQQD", "false" }, { "CBFGL", "false" },
			{ "ZZCQFY", "false" }, { "QYCQFY", "false" }, { "QTFY", "false" },
			{ "AZFTZCB", "false" }, { "ZZHBTZCB", "false" },
			{ "CQHBTZ", "false" }, { "QTFYZB", "false" }, { "LMCJJ", "false" },
			{ "FWSJ", "false" }, { "ZJ", "false" }, { "DKMC", "false" } };
	private String form_name = "JC_XIANGMU";

	@Override
	public Map<String, TRBean> getTRBeans(Object[] obj, TRBean trBean) {
		Map<String, TRBean> trbeans = new TreeMap<String, TRBean>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if (obj.length > 0) {
			queryMap = (Map<String, Object>) obj[0];
		}
		trbeans.put("00", getSub(queryMap).get(0));
		List<TRBean> trbeanList = getBody(queryMap);
		for (int i = 0; i < trbeanList.size(); i++) {
			trbeans.put(i + "1", trbeanList.get(i));
		}
		return trbeans;
	}

	public List<TRBean> getBody(Map queryMap) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select rownum,");
		for (int i = 1; i < shows.length - 1; i++) {
			sqlBuffer.append("t.").append(shows[i][0]).append(",");
		}
		sqlBuffer.append("t.").append(shows[shows.length - 1][0]).append(
				" from ");
		sqlBuffer.append(form_name).append(" t ");
		if (queryMap != null && !queryMap.isEmpty()) {
			sqlBuffer.append(String.valueOf(queryMap.get("query")));
		}
		// sqlBuffer.append(" order by t.zrbbh,t.yw_guid");
		List<Map<String, Object>> queryList = query(sqlBuffer.toString(), YW);
		List<TRBean> list = new ArrayList<TRBean>();
		for (int num = 0; num < queryList.size(); num++) {
			TRBean trBean = new TRBean();
			TDBean tdBean;
			trBean.setCssStyle("trsingle");
			Map<String, Object> map = queryList.get(num);
			for (int i = 0; i < shows.length; i++) {
				String value = String.valueOf(map.get(shows[i][0]));
				if ("null".equals(value)) {
					value = "";
				}
				if (i == shows.length - 1) {
					tdBean = new TDBean(value, "200", "20", shows[i][1]);
				} else {
					tdBean = new TDBean(value, "80", "20", shows[i][1]);
				}

				trBean.addTDBean(tdBean);
			}
			list.add(trBean);
		}
		return list;
	}

	public List<TRBean> getSub(Map queryMap) {
		String sql = "select sum(t.zzsgm) as zzsgm, sum(t.zzzsgm) as zzzsgm, sum(t.zzzshs) as zzzshs,trunc(decode(sum(t.zzzshs),0,0,sum(t.zzzsgm)/sum(t.zzzshs)),2) as hjmj,sum(t.fzzzsgm) as fzzzsgm, "
				+ "sum(t.fzzjs) as fzzjs, sum(t.zd) as zd, sum(t.jsyd) as jsyd,sum(t.rjl) as rjl, sum(t.jzgm) as jzgm, ''as ghyt, sum(t.gjjzgm) as gjjzgm, sum(t.jzjzgm) as jzjzgm,sum(t.szjzgm) as szjzgm,"
				+ "sum(t.kfcb) as kfcb,sum(t.lmcb) as lmcb,sum(t.dmcb) as dmcb,sum(t.yjcjj) as yjcjj,sum(t.yjzftdsy) as yjzftdsy,sum(t.cxb) as cxb,sum(t.cqqd) as cqqd,sum(t.cbfgl) as cbfgl"
				+ ",sum(t.zzcqfy) as zzcqfy,sum(t.qycqfy) as qycqfy,sum(t.qtfy) as qtfy,sum(t.azftzcb) as azftzcb,sum(t.zzhbtzcb) as zzhbtzcb,sum(t.cqhbtz) as cqhbtz,'' as qtfyzb,sum(t.lmcjj) as lmcjj,sum(t.fwsj) as fwsj,sum(t.zj) as zj,''as dk from JC_XIANGMU t ";
		if (queryMap != null && !queryMap.isEmpty()) {
			sql += String.valueOf(queryMap.get("query"));
		}
		List<Map<String, Object>> queryList = query(sql.toString(), YW);
		List<TRBean> list = new ArrayList<TRBean>();
		TRBean trBean = new TRBean();
		trBean.setCssStyle("trtotal");
		Map<String, Object> map = queryList.get(0);
		TDBean tdname = new TDBean("合计", "180", "20");
		tdname.setColspan("2");
		trBean.addTDBean(tdname);
		for (int i = 2; i < shows.length; i++) {
			String value = String.valueOf(map.get(shows[i][0]));
			if ("null".equals(value)) {
				value = "";
			}
			TDBean tdBean = new TDBean(value, "80", "20", "false");
			trBean.addTDBean(tdBean);
		}
		list.add(trBean);
		return list;
	}
}
