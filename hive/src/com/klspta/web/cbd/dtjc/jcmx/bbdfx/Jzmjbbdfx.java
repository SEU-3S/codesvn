package com.klspta.web.cbd.dtjc.jcmx.bbdfx;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;

public class Jzmjbbdfx extends AbstractBaseBean implements IDataClass {

	@Override
	public Map<String, TRBean> getTRBeans(Object[] obj, TRBean trBean) {
		Map<String, TRBean> trbeans = new LinkedHashMap<String, TRBean>();
		List<Map<String, Object>> ndList = getNd();
		List<Map<String, Object>> xmList = getXmmc();
		if (ndList.size() > 0) {
			trbeans.put("bbdtitle", buildTitle(ndList));
			buildKftlTotal(trbeans, ndList, xmList);
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
	 * <br>
	 * Description:构建列名 <br>
	 * Author:陈强峰 <br>
	 * Date:2013-11-4
	 * 
	 * @param nd
	 * @return
	 */
	private TRBean buildTitle(List<Map<String, Object>> ndList) {
		TRBean trb = new TRBean();
		trb.setCssStyle("tr01");
		TDBean td = new TDBean("", "130", "");
		td.setStyle("td00");
		trb.addTDBean(td);
		for (int i = 10; i < 21; i++) {
			td = new TDBean(i + "", "70", "");
			trb.addTDBean(td);
		}
		td = new TDBean("", "130", "");
		td.setStyle("td01");
		trb.addTDBean(td);
		return trb;
	}

	/**
	 * 
	 * <br>
	 * Description:构建开发体量总计 <br>
	 * Author:陈强峰 <br>
	 * Date:2013-11-4
	 * 
	 * @param trbeans
	 * @param ndList
	 */
	private void buildKftlTotal(Map<String, TRBean> trbeans,
			List<Map<String, Object>> ndList, List<Map<String, Object>> xmList) {
		TRBean trbhs = new TRBean();
		trbhs.setCssStyle("tr02");
		int zdcbkfcb = getZdcbkfcb();
		for (int i = 0; i <= zdcbkfcb; i += 1000) {
			TRBean trs = new TRBean();
			trs.setCssStyle("tr02");
			TDBean tds = new TDBean(i + "", "110", "15");
			trs.addTDBean(tds);
			for (int j = 10; j < 21; j++) {
				TDBean tds1 = new TDBean("", "110", "15");
				trs.addTDBean(tds1);
			}
			TDBean tds1 = new TDBean(i + 3000 + "", "110", "15");
			trs.addTDBean(tds1);
			trbeans.put(i / 1000 + "", trs);
			if (i == 0)
				i = 6000;
		}
	}

	private int getZdcbkfcb() {
		String sql = "select zdcbkfcb from sys_parameter";
		List<Map<String, Object>> result = query(sql, YW);
		return Integer.parseInt(result.get(0).get("ZDCBKFCB").toString());
	}

	public void getBBDpara() {
		String sql = "select * from BBD_PARAMETER";
		List<Map<String, Object>> list = query(sql, YW);
		response(list);
	}

	public void saveBBDpara() {
		try {
			// String lmbbdstring = request.getParameter("LMBBDSTRING");
			// updateLMBBD();
			String JSQ = request.getParameter("JSQ");
			String JSQLL = request.getParameter("JSQLL");
			String JSQSFL = request.getParameter("JSQSFL");
			String JSCBD = request.getParameter("JSCBD");
			String MDJ = request.getParameter("MDJ");
			String YYQLL = request.getParameter("YYQLL");
			String ZHYYFL = request.getParameter("ZHYYFL");
			String ZYZJBL = request.getParameter("ZYZJBL");
			String GLFYBFB = request.getParameter("GLFYBFB");
			String XSFYBFB = request.getParameter("XSFYBFB");
			String QTFYBFB = request.getParameter("QTFYBFB");
			String DZXSJGXS = request.getParameter("DZXSJGXS");
			String ZYZJDYXBFB = request.getParameter("ZYZJDYXBFB");
			String ZSYSBL = request.getParameter("ZSYSBL");
			String CZL = request.getParameter("CZL");
			String sql = "update BBD_PARAMETER set JSQ=?,JSQLL=?,JSQSFL=?,JSCBD=?,MDJ=?,YYQLL=?,ZHYYFL=?,ZYZJBL=?,GLFYBFB=?,XSFYBFB=?,QTFYBFB=?,DZXSJGXS=?,ZYZJDYXBFB=?,ZSYSBL=?,CZL=?";
			update(sql, YW, new Object[] { JSQ, JSQLL, JSQSFL, JSCBD, MDJ,
					YYQLL, ZHYYFL, ZYZJBL, GLFYBFB, XSFYBFB, QTFYBFB, DZXSJGXS,
					ZYZJDYXBFB, ZSYSBL, CZL });
			response("true");
		} catch (Exception e) {
			response("false");
		}
	}


	/**
	 * 保存租金保本点结果
	 */
	public void setValue() {
		String json = request.getParameter("json");
		StringBuffer updatesql1 = new StringBuffer();
		StringBuffer updatesql2 = new StringBuffer();
		// 删除数据库数据
		String delSql = "delete BBDFXJG where lmcb is not null";
		update(delSql, YW);
		JSONArray jsonArray;
		StringBuffer insertSql = new StringBuffer();
		int hsqPrameter = Integer.parseInt(query("select hsq from sys_parameter", YW).get(0).get("HSQ").toString());
		insertSql.append("insert all ");
		try {
			jsonArray = UtilFactory.getJSONUtil().jsonToObjects(
					json.substring(json.indexOf('[')));
			for (int m = 0; m < jsonArray.size(); m++) {
				JSONObject obj = jsonArray.getJSONObject(m);
				String lmcb = obj.get("cbcb").toString();
				String bbd = "";
				
				for (int i = 10; i <= 20; i++) {
					bbd = obj.get(i + "").toString();
					insertSql.append(" into BBDFXJG (LMCB,TZHSQ,BBD) values('")
							.append(lmcb).append("','").append(i).append("','")
							.append(bbd).append("')");
					
				}
				
			}
			insertSql.append(" select 'a','b','c' from dual");
			update(insertSql.toString(), YW);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
