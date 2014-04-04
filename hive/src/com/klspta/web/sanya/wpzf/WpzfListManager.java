package com.klspta.web.sanya.wpzf;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.base.wkt.Polygon;

/**
 * <br>
 * Title:卫片执法检查列表 <br>
 * Description:呈现卫片执法检查信息
 */
public class WpzfListManager extends AbstractBaseBean {

	/**
	 * <br>
	 * Description:获取所有卫片执法图斑
	 */
	public void getAllList() {
		String keyWord = request.getParameter("keyWord");
		String hzqhs1 = request.getParameter("hzqhs1");
		String hzqhs2 = request.getParameter("hzqhs2");
		String hzqhs3 = request.getParameter("hzqhs3");
		String addsql = "";
		String hst = "";
		if (hzqhs2 == "") {
			if (hzqhs1.endsWith("00")) {
				hst = hzqhs1.substring(0, hzqhs1.lastIndexOf("00"));
				addsql = "  t.xzqhdm like '" + hst + "__'";
			} else
				addsql = "  t.xzqhdm='" + hzqhs1 + "'";
		} else {
			if (hzqhs3 == "" && hzqhs2 != "") {
				addsql = "  (t.xzqhdm='" + hzqhs1 + "' or t.xzqhdm='" + hzqhs2
						+ "')";
			} else {
				addsql = "  (t.xzqhdm='" + hzqhs1 + "' or t.xzqhdm='" + hzqhs2
						+ "' or t.xzqhdm='" + hzqhs3 + "')";
			}
		}
		String strColumnName = "t.TBBH || t.XZQHMC || to_char(t.TBMJ) || t.QSX || t.HSX || to_char(t.TIME,'yyyy-MM-dd') ";
		String where = "";
		if (keyWord != null && !"".equals(keyWord)) {
			keyWord = keyWord.trim();
			while (keyWord.indexOf("  ") > 0) {// 循环去掉多个空格，所有字符中间只用一个空格间隔
				keyWord = keyWord.replace("  ", " ");
			}
			keyWord = UtilFactory.getStrUtil().unescape(keyWord);
			where += " and ("
					+ strColumnName
					+ " like '%"
					+ (keyWord.replaceAll(" ", "%' and " + strColumnName
							+ "  like '%")) + "%')";// 查询条件
		}
		String sql = "select t.TBBH, t.YEAR,t.XZQHMC,to_char(t.TBMJ) tbmj, t.QSX, t.HSX,to_char(t.TIME,'yyyy-MM-dd') WPLD from WPZF_TB t where  "
				+ addsql;
		sql += where;
		sql += " order by t.time desc";

		List<Map<String, Object>> resultList = query(sql, YW);
		// 对获取的数据进行预处理
		int i = 0;
		for (Map<String, Object> resultMap : resultList) {
			resultMap.put("ID", i);
			i++;
		}
		response(resultList);

	}

	/**
	 * <br>
	 * Description:获取卫片不符合规划的数据
	 */
	public void getyswfList() {
		String keyWord = request.getParameter("keyWord");
		String hzqhs1 = request.getParameter("hzqhs1");
		String hzqhs2 = request.getParameter("hzqhs2");
		String hzqhs3 = request.getParameter("hzqhs3");
		String addsql = "";
		String hst = "";
		if (hzqhs2 == "") {
			if (hzqhs1.endsWith("00")) {
				hst = hzqhs1.substring(0, hzqhs1.lastIndexOf("00"));
				addsql = " t1.xzqhdm like '" + hst + "__'";
			} else
				addsql = " t1.xzqhdm='" + hzqhs1 + "'";
		} else {
			if (hzqhs3 == "" && hzqhs2 != "") {
				addsql = " (t1.xzqhdm='" + hzqhs1 + "' or t1.xzqhdm='" + hzqhs2
						+ "')";
			} else {
				addsql = " (t1.xzqhdm='" + hzqhs1 + "' or t1.xzqhdm='" + hzqhs2
						+ "' or t1.xzqhdm='" + hzqhs3 + "')";
			}
		}
		String strColumnName = "t1.TBBH || t1.XZQHMC || to_char(t1.TIME,'yyyy-MM-dd')|| to_char(t1.TBMJ)|| to_char(t2.YXJSQMJ) || to_char(t2.JZJSQMJ) || to_char(t2.ZYJBNT) ";
		String where = "";
		if (keyWord != null && !"".equals(keyWord)) {
			keyWord = keyWord.trim();
			while (keyWord.indexOf("  ") > 0) {// 循环去掉多个空格，所有字符中间只用一个空格间隔
				keyWord = keyWord.replace("  ", " ");
			}
			keyWord = UtilFactory.getStrUtil().unescape(keyWord);
			keyWord = keyWord.toUpperCase();
			where += " and ("
					+ strColumnName
					+ " like '%"
					+ (keyWord.replaceAll(" ", "%' and " + strColumnName
							+ "  like '%")) + "%')";// 查询条件
		}

		String sql = null;
		sql = "select t1.tbbh,t1.xzqhmc,to_char(t1.TIME,'yyyy-MM-dd') year,to_char(t1.tbmj) tbmj,to_char(t2.yxjsqmj) yxjsqmj,to_char(t2.jzjsqmj) jzjsqmj,to_char(t2.zyjbnt) zyjbnt,to_char(t2.xzjsqmj) xzjsqmj,decode(t1.ajstatus,'1','未下发','已下发') xiafa from WPZF_TB t1, wpzf_analyse_gh t2, wpzf_analyse_sp t3, wpzf_analyse_gd t4 where t1.tbbh = t2.tbbh and t1.tbbh = t3.tbbh and t1.tbbh = t4.tbbh and ( decode(t3.ygbl,null,'0.0',t3.ygbl) < 0.7  or decode(t4.ygbl,null,'0.0',t4.ygbl) < 0.7 ) and "
				+ addsql;
		sql += where;
		sql += " order by t1.time desc";
		List<Map<String, Object>> resultList = query(sql, YW);
		// 对获取的数据进行预处理
		int i = 0;
		for (Map<String, Object> resultMap : resultList) {
			resultMap.put("ID", i);
			i++;
		}
		response(resultList);

	}

	/**
	 * <br>
	 * Description:获取卫片不符合审批的数据
	 */
	public void getspyswfList() {
		String keyWord = request.getParameter("keyWord");
		String hzqhs1 = request.getParameter("hzqhs1");
		String hzqhs2 = request.getParameter("hzqhs2");
		String hzqhs3 = request.getParameter("hzqhs3");
		String addsql = "";
		String hst = "";
		if (hzqhs2 == "") {
			if (hzqhs1.endsWith("00")) {
				hst = hzqhs1.substring(0, hzqhs1.lastIndexOf("00"));
				addsql = " t1.xzqhdm like '" + hst + "__'";
			} else
				addsql = " t1.xzqhdm='" + hzqhs1 + "'";
		} else {
			if (hzqhs3 == "" && hzqhs2 != "") {
				addsql = " (t1.xzqhdm='" + hzqhs1 + "' or t1.xzqhdm='" + hzqhs2
						+ "')";
			} else {
				addsql = " (t1.xzqhdm='" + hzqhs1 + "' or t1.xzqhdm='" + hzqhs2
						+ "' or t1.xzqhdm='" + hzqhs3 + "')";
			}
		}
		String strColumnName = "t1.TBBH || t1.XZQHMC || to_char(t1.TBMJ )|| to_char(t1.TIME,'yyyy-MM-dd') || to_char(t2.YGMJ) || t2.YGBL || t2.XMMC || t2.PZWH ";
		String where = "";
		if (keyWord != null && !"".equals(keyWord)) {
			keyWord = keyWord.trim();
			while (keyWord.indexOf("  ") > 0) {// 循环去掉多个空格，所有字符中间只用一个空格间隔
				keyWord = keyWord.replace("  ", " ");
			}
			keyWord = UtilFactory.getStrUtil().unescape(keyWord);
			keyWord = keyWord.toUpperCase();
			where += " and ("
					+ strColumnName
					+ " like '%"
					+ (keyWord.replaceAll(" ", "%' and " + strColumnName
							+ "  like '%")) + "%')";// 查询条件
		}
		String sql = null;
		//String sqlsp = "select * from wpzf_analyse_sp t2,WPZF_TB t1 where t1.tbbh=t2.tbbh and "
		//		+ addsql;
		//List<Map<String, Object>> resultsp = query(sqlsp, YW);

		sql = "select t1.tbbh,t1.xzqhmc,to_char(t1.tbmj) tbmj,to_char(t1.TIME,'yyyy-MM-dd') year,to_char(t2.ygmj) ygmj,t2.ygbl,t2.xmmc,t2.pzwh,"
				+ "decode(t1.ajstatus,'1','未下发','已下发') xiafa from wpzf_tb t1,wpzf_analyse_sp t2 "
				+ "where t1.tbbh = t2.tbbh  and decode(t2.ygbl,null,'0.0',t2.ygbl) < 0.7 and "
				+ addsql;

		sql += where;
		sql += " order by t1.time desc";
		List<Map<String, Object>> resultList = query(sql, YW);
		// 对获取的数据进行预处理
		int i = 0;
		for (Map<String, Object> resultMap : resultList) {
			resultMap.put("ID", i);
			i++;
		}
		response(resultList);
	}

	/**
	 * <br>
	 * Description:获取卫片不符合供地的数据
	 */
	public void getgdyswfList() {
		String keyWord = request.getParameter("keyWord");
		String hzqhs1 = request.getParameter("hzqhs1");
		String hzqhs2 = request.getParameter("hzqhs2");
		String hzqhs3 = request.getParameter("hzqhs3");
		String addsql = "";
		String hst = "";
		if (hzqhs2 == "") {
			if (hzqhs1.endsWith("00")) {
				hst = hzqhs1.substring(0, hzqhs1.lastIndexOf("00"));
				addsql = " t1.xzqhdm like '" + hst + "__'";
			} else
				addsql = " t1.xzqhdm='" + hzqhs1 + "'";
		} else {
			if (hzqhs3 == "" && hzqhs2 != "") {
				addsql = " (t1.xzqhdm='" + hzqhs1 + "' or t1.xzqhdm='" + hzqhs2
						+ "')";
			} else {
				addsql = " (t1.xzqhdm='" + hzqhs1 + "' or t1.xzqhdm='" + hzqhs2
						+ "' or t1.xzqhdm='" + hzqhs3 + "')";
			}
		}
		String strColumnName = "t1.TBBH || t1.XZQHMC || to_char(t1.TIME,'yyyy-MM-dd') || t2.XMMC || t2.GDWH ||to_char(t2.YGMJ )|| t2.YGBL ";
		String where = "";
		if (keyWord != null && !"".equals(keyWord)) {
			keyWord = keyWord.trim();
			while (keyWord.indexOf("  ") > 0) {// 循环去掉多个空格，所有字符中间只用一个空格间隔
				keyWord = keyWord.replace("  ", " ");
			}
			keyWord = UtilFactory.getStrUtil().unescape(keyWord);
			keyWord = keyWord.toUpperCase();
			where += " and ("
					+ strColumnName
					+ " like '%"
					+ (keyWord.replaceAll(" ", "%' and " + strColumnName
							+ "  like '%")) + "%')";// 查询条件
		}
		String sql = null;

		sql = "select t1.tbbh,t1.xzqhmc,to_char(t1.tbmj) tbmj,to_char(t1.TIME,'yyyy-MM-dd') year,t2.xmmc,t2. gdwh,to_char(t2.ygmj) ygmj,t2.ygbl, "
				+ "decode(t1.ajstatus,'1','未下发','已下发') xiafa from wpzf_tb t1,wpzf_analyse_gd t2 where  t1.tbbh=t2.tbbh "
				+ "and decode(t2.ygbl,null,'0.0',t2.ygbl) < 0.7 and " + addsql;

		sql += where;
		sql += " order by t1.time desc";
		List<Map<String, Object>> resultList = query(sql, YW);
		int i = 0;
		for (Map<String, Object> resultMap : resultList) {
			resultMap.put("ID", i);
			i++;
		}
		response(resultList);
	}

	/**
	 * <br>
	 * Description:获取合法数据
	 */
	public void gethfList() {
		String keyWord = request.getParameter("keyWord");
		String hzqhs1 = request.getParameter("hzqhs1");
		String hzqhs2 = request.getParameter("hzqhs2");
		String hzqhs3 = request.getParameter("hzqhs3");
		String addsql = "";
		String hst = "";
		if (hzqhs2 == "") {
			if (hzqhs1.endsWith("00")) {
				hst = hzqhs1.substring(0, hzqhs1.lastIndexOf("00"));
				addsql = " t1.xzqhdm like '" + hst + "__'";
			} else
				addsql = "  t1.xzqhdm='" + hzqhs1 + "'";
		} else {
			if (hzqhs3 == "" && hzqhs2 != "") {
				addsql = " ( t1.xzqhdm='" + hzqhs1 + "' or t1.xzqhdm='"
						+ hzqhs2 + "')";
			} else {
				addsql = " ( t1.xzqhdm='" + hzqhs1 + "' or t1.xzqhdm='"
						+ hzqhs2 + "' or t1.xzqhdm='" + hzqhs3 + "')";
			}
		}
		String strColumnName = "t1.tbbh || t1.xzqhmc || to_char(t1.tbmj)|| t1.qsx || t1.hsx || t3.xmmc || t2.xmmc || to_char(t1.TIME,'yyyy-MM-dd')";
		String where = "";
		if (keyWord != null && !"".equals(keyWord)) {
			keyWord = keyWord.trim();
			while (keyWord.indexOf("  ") > 0) {// 循环去掉多个空格，所有字符中间只用一个空格间隔
				keyWord = keyWord.replace("  ", " ");
			}
			keyWord = UtilFactory.getStrUtil().unescape(keyWord);
			keyWord = keyWord.toUpperCase();
			where += " and ("
					+ strColumnName
					+ " like '%"
					+ (keyWord.replaceAll(" ", "%' and " + strColumnName
							+ "  like '%")) + "%')";// 查询条件
		}
		String sql = null;
		sql = "select t1.tbbh, t1.xzqhmc, to_char(t1.tbmj) tbmj,t2.xmmc spxmmc,t3.xmmc gdxmmc,t1.qsx, t1.hsx, to_char(t1.TIME,'yyyy-MM-dd') wpld from wpzf_tb t1, wpzf_analyse_sp t2, wpzf_analyse_gd t3 where t1.tbbh = t2.tbbh   and t1.tbbh = t3.tbbh and t2.ygbl > 0.7  and  t3.ygbl > 0.7 and"
				+ addsql;
		sql += where;
		sql += " order by t1.time desc";
		List<Map<String, Object>> resultList = query(sql, YW);
		// 对获取的数据进行预处理
		int i = 0;
		for (Map<String, Object> resultMap : resultList) {
			resultMap.put("ID", i);
			i++;
		}
		response(resultList);
	}

	/**
	 * <br>
	 * Description:获取卫片未批未供的数据
	 */
	public void getwpwgList() {
		String keyWord = request.getParameter("keyWord");
		String hzqhs1 = request.getParameter("hzqhs1");
		String hzqhs2 = request.getParameter("hzqhs2");
		String hzqhs3 = request.getParameter("hzqhs3");
		String addsql = "";
		String hst = "";
		if (hzqhs2 == "") {
			if (hzqhs1.endsWith("00")) {
				hst = hzqhs1.substring(0, hzqhs1.lastIndexOf("00"));
				addsql = " t.xzqhdm like '" + hst + "__'";
			} else
				addsql = " t.xzqhdm='" + hzqhs1 + "'";
		} else {
			if (hzqhs3 == "" && hzqhs2 != "") {
				addsql = " (t.xzqhdm='" + hzqhs1 + "' or t.xzqhdm='" + hzqhs2
						+ "')";
			} else {
				addsql = " (t.xzqhdm='" + hzqhs1 + "' or t.xzqhdm='" + hzqhs2
						+ "' or t.xzqhdm='" + hzqhs3 + "')";
			}
		}
		String strColumnName = "t.TBBH || t.XZQHMC || to_char(t.TBMJ)|| t.QSX || t.HSX || to_char(t.TIME,'yyyy-MM-dd') || t.ISHF ";
		String where = "";
		if (keyWord != null && !"".equals(keyWord)) {
			keyWord = keyWord.trim();
			while (keyWord.indexOf("  ") > 0) {// 循环去掉多个空格，所有字符中间只用一个空格间隔
				keyWord = keyWord.replace("  ", " ");
			}
			keyWord = UtilFactory.getStrUtil().unescape(keyWord);
			keyWord = keyWord.toUpperCase();
			where += " and ("
					+ strColumnName
					+ " like '%"
					+ (keyWord.replaceAll(" ", "%' and " + strColumnName
							+ "  like '%")) + "%')";// 查询条件
		}
		String sql = "select t.TBBH, t.XZQHMC, to_char(t.TBMJ) tbmj, t.QSX, t.HSX, to_char(t.TIME,'yyyy-MM-dd') WPLD,decode(t.ajstatus,'1','未下发','已下发') xiafa from WPZF_TB t where"
				+ " (t.tbbh not in (select t2.tbbh from wpzf_analyse_gd t2)) and (t.tbbh not in (select t3.tbbh from "
				+ "wpzf_analyse_sp t3)) and " + addsql;
		sql += where;
		sql += " order by t.time desc";
		List<Map<String, Object>> resultList = query(sql, YW);
		// 对获取的数据进行预处理
		int i = 0;
		for (Map<String, Object> resultMap : resultList) {
			resultMap.put("ID", i);
			i++;
		}
		response(resultList);

	}

	/**
	 * <br>
	 * Description:获取新增的疑似违法卫片数据
	 */
	public void getwpxzList() {
		String keyWord = request.getParameter("keyWord");
		String hzqhs1 = request.getParameter("hzqhs1");
		String hzqhs2 = request.getParameter("hzqhs2");
		String hzqhs3 = request.getParameter("hzqhs3");
		String addsql = "";
		String hst = "";
		if (hzqhs2 == "") {
			if (hzqhs1.endsWith("00")) {
				hst = hzqhs1.substring(0, hzqhs1.lastIndexOf("00"));
				addsql = " t.xzqhdm like '" + hst + "__'";
			} else
				addsql = " t.xzqhdm='" + hzqhs1 + "'";
		} else {
			if (hzqhs3 == "" && hzqhs2 != "") {
				addsql = " (t.xzqhdm='" + hzqhs1 + "' or t.xzqhdm='" + hzqhs2
						+ "')";
			} else {
				addsql = " ( t.xzqhdm='" + hzqhs1 + "' or t.xzqhdm='" + hzqhs2
						+ "' or t.xzqhdm='" + hzqhs3 + "')";
			}
		}
		String strColumnName = "t.TBBH || t.XZQHMC || to_char(t.TBMJ)|| t.QSX || t.HSX || to_char(t.TIME,'yyyy-MM-dd')|| t.ISHF ";
		String where = "";
		if (keyWord != null && !"".equals(keyWord)) {
			keyWord = keyWord.trim();
			while (keyWord.indexOf("  ") > 0) {// 循环去掉多个空格，所有字符中间只用一个空格间隔
				keyWord = keyWord.replace("  ", " ");
			}
			keyWord = UtilFactory.getStrUtil().unescape(keyWord);
			keyWord = keyWord.toUpperCase();
			where += " and ("
					+ strColumnName
					+ " like '%"
					+ (keyWord.replaceAll(" ", "%' and " + strColumnName
							+ "  like '%")) + "%')";// 查询条件
		}

		String sql = "select t.TBBH, t.XZQHMC, to_char(t.TBMJ) tbmj, t.QSX, t.HSX,to_char(t.TIME,'yyyy-MM-dd') WPLD,decode(t.ajstatus,'1','未下发','已下发') xiafa  from WPZF_TB t where ( t.ajstatus='1' or t.ajstatus='2')and "
				+ addsql;
		sql += where;
		sql += "  order by t.time desc";
		List<Map<String, Object>> resultList = query(sql, YW);
		// 对获取的数据进行预处理
		int i = 0;
		for (Map<String, Object> resultMap : resultList) {
			resultMap.put("ID", i);
			i++;
		}
		response(resultList);
	}

	/**
	 * <br>
	 * Description:获取卫片图斑基本属性数据
	 */
	@SuppressWarnings("unchecked")
	public void getjbsxList() {

		String yw_guid = request.getParameter("yw_guid");
		List allRows = new ArrayList();
		String sql = "select TBBH,XZQHMC,XZQHDM,TBMJ,X,Y,QSX,HSX,YEAR from WPZF_TB t where  t.TBBH='"
				+ yw_guid + "'";
		List listYw = query(sql, YW);
		int i = 0;
		Map map = (Map) listYw.get(0);
		List oneRow1 = new ArrayList();
		String s = "图斑编号";
		oneRow1.add(s);
		oneRow1.add((String) map.get("TBBH"));
		oneRow1.add(i++);
		allRows.add(oneRow1);

		List oneRow2 = new ArrayList();
		s = "政区名称";
		oneRow2.add(s);
		oneRow2.add((String) map.get("XZQHMC"));
		oneRow2.add(i++);
		allRows.add(oneRow2);

		List oneRow3 = new ArrayList();
		s = "行政代码";
		oneRow3.add(s);
		oneRow3.add((String) map.get("XZQHDM"));
		oneRow3.add(i++);
		allRows.add(oneRow3);

		List oneRow4 = new ArrayList();
		s = "图斑面积";
		oneRow4.add(s);
		oneRow4.add(map.get("TBMJ"));
		oneRow4.add(i++);
		allRows.add(oneRow4);

		List oneRow5 = new ArrayList();
		s = "中心点坐标X";
		oneRow5.add(s);
		oneRow5.add(map.get("X"));
		oneRow5.add(i++);
		allRows.add(oneRow5);

		List oneRow6 = new ArrayList();
		s = "中心点坐标Y";
		oneRow6.add(s);
		oneRow6.add(map.get("Y"));
		oneRow6.add(i++);
		allRows.add(oneRow6);

		List oneRow7 = new ArrayList();
		s = "前时相";
		oneRow7.add(s);
		oneRow7.add((String) map.get("QSX"));
		oneRow7.add(i++);
		allRows.add(oneRow7);

		List oneRow8 = new ArrayList();
		s = "后时相";
		oneRow8.add(s);
		oneRow8.add((String) map.get("HSX"));
		oneRow8.add(i++);
		allRows.add(oneRow8);

		List oneRow9 = new ArrayList();
		s = "年度";
		oneRow9.add(s);
		oneRow9.add((String) map.get("YEAR"));
		oneRow9.add(i++);
		allRows.add(oneRow9);
		response(allRows);

	}

	/**
	 * <br>
	 * Description:获取卫片图斑审批叠加数据
	 */
	@SuppressWarnings("unchecked")
	public void getspList() {
		String yw_guid = request.getParameter("yw_guid");
		List allRows = new ArrayList();
		String sql = "select PZWH,XMMC,YGMJ,YGBL,PZSJ from WPZF_ANALYSE_SP t where  t.TBBH='"
				+ yw_guid + "'";
		List listYw = query(sql, YW);
		int i = 0;
		if (listYw.size() > 0) {
			Map map = (Map) listYw.get(0);
			List oneRow1 = new ArrayList();
			String s = "审批批复文号";
			oneRow1.add(s);
			oneRow1.add((String) map.get("PZWH"));
			oneRow1.add(i++);
			allRows.add(oneRow1);

			List oneRow2 = new ArrayList();
			s = "审批项目名称";
			oneRow2.add(s);
			oneRow2.add((String) (map.get("XMMC")));
			oneRow2.add(i++);
			allRows.add(oneRow2);

			List oneRow3 = new ArrayList();
			s = "审批压盖面积";
			oneRow3.add(s);
			if (map.get("YGMJ") != null) {
				oneRow3.add(map.get("YGMJ"));
			} else {
				oneRow3.add("0");
			}
			oneRow3.add(i++);
			allRows.add(oneRow3);

			List oneRow4 = new ArrayList();
			s = "审批压盖比率";
			oneRow4.add(s);
			oneRow4.add((String) map.get("YGBL"));
			oneRow4.add(i++);
			allRows.add(oneRow4);

			List oneRow5 = new ArrayList();
			s = "审批时间";
			oneRow5.add(s);
			oneRow5.add((String) map.get("PZSJ"));
			oneRow5.add(i++);
			allRows.add(oneRow5);

			response(allRows);
		} else {
			response("false");
		}

	}

	/**
	 * <br>
	 * Description:获取卫片图斑供地叠加数据
	 */
	@SuppressWarnings("unchecked")
	public void getgdList() {
		String yw_guid = request.getParameter("yw_guid");
		List allRows = new ArrayList();
		String sql = "select GDWH,XMMC,YGMJ,YGBL,GDSJ from WPZF_ANALYSE_GD t where  t.TBBH='"
				+ yw_guid + "'";
		List listYw = query(sql, YW);
		int i = 0;
		List oneRow1 = new ArrayList();
		if (listYw.size() > 0) {
			Map map = (Map) listYw.get(0);
			String s = "供地批复文号";
			oneRow1.add(s);
			oneRow1.add((String) map.get("GDWH"));
			oneRow1.add(i++);
			allRows.add(oneRow1);

			List oneRow2 = new ArrayList();
			s = "供地项目名称";
			oneRow2.add(s);
			oneRow2.add((String) (map.get("XMMC")));
			oneRow2.add(i++);
			allRows.add(oneRow2);

			List oneRow3 = new ArrayList();
			s = "供地压盖面积";
			oneRow3.add(s);
			if (map.get("YGMJ") != null) {
				oneRow3.add(map.get("YGMJ"));
			} else {
				oneRow3.add("0");
			}
			oneRow3.add(i++);
			allRows.add(oneRow3);

			List oneRow4 = new ArrayList();
			s = "供地压盖比率";
			oneRow4.add(s);
			oneRow4.add((String) map.get("YGBL"));
			oneRow4.add(i++);
			allRows.add(oneRow4);

			List oneRow5 = new ArrayList();
			s = "供地时间";
			oneRow5.add(s);
			oneRow5.add((String) map.get("GDSJ"));
			oneRow5.add(i++);
			allRows.add(oneRow5);
			response(allRows);
		} else {
			response("false");
			return;
		}
	}

	/**
	 * <br>
	 * Description:获取卫片图斑现状叠加数据
	 */
	@SuppressWarnings("unchecked")
	public void getxzList() {
		String yw_guid = request.getParameter("yw_guid");
		List allRows = new ArrayList();
		String sql = "select b.XZQHMC,b.TBMJ,a.NYDMJ,a.GDMJ,a.JSYDMJ,a.WLYDMJ from WPZF_ANALYSE_XZ a,WPZF_TB b where  a.TBBH='"
				+ yw_guid + "' and a.TBBH = b.TBBH ";
		List listYw = query(sql, YW);
		int i = 0;
		if (listYw.size() > 0) {
			Map map = (Map) listYw.get(0);
			List oneRow1 = new ArrayList();
			String s = "土地坐落";
			oneRow1.add(s);
			oneRow1.add((String) map.get("b.XZQHMC"));
			oneRow1.add(i++);
			allRows.add(oneRow1);

			List oneRow2 = new ArrayList();
			s = "总面积";
			oneRow2.add(s);
			if (map.get("b.TBMJ") != null) {
				oneRow2.add(map.get("b.TBMJ"));
			} else {
				oneRow2.add("0");
			}
			oneRow2.add(i++);
			allRows.add(oneRow2);

			List oneRow3 = new ArrayList();
			s = "农用地面积";
			oneRow3.add(s);
			if (map.get("a.NYDMJ") != null) {
				oneRow3.add(map.get("a.NYDMJ"));
			} else {
				oneRow3.add("0");
			}
			oneRow3.add(i++);
			allRows.add(oneRow3);

			List oneRow4 = new ArrayList();
			s = "农用地其中耕地面积";
			oneRow4.add(s);
			if (map.get("a.GDMJ") != null) {
				oneRow4.add(map.get("a.GDMJ"));
			} else {
				oneRow4.add("0");
			}
			oneRow4.add(i++);
			allRows.add(oneRow4);

			List oneRow5 = new ArrayList();
			s = "建设用地面积";
			oneRow5.add(s);
			if (map.get("a.JSYDMJ") != null) {
				oneRow5.add(map.get("a.JSYDMJ"));
			} else {
				oneRow5.add("0");
			}
			oneRow5.add(i++);
			allRows.add(oneRow5);

			List oneRow6 = new ArrayList();
			s = "未利用地面积";
			oneRow6.add(s);
			if (map.get("a.WLYDMJ") != null) {
				oneRow5.add(map.get("a.WLYDMJ"));
			} else {
				oneRow5.add("0");
			}
			oneRow6.add(i++);
			allRows.add(oneRow6);

			response(allRows);
		} else {
			response("false");
			return;
		}

	}

	/**
	 * <br>
	 * Description:获取卫片图斑详细地类数据
	 */
	public void getxxdlList() {
		String yw_guid = request.getParameter("yw_guid");
		String sql = "select TBBH,DLMC,QSDWMC,YGMJ from WPZF_ANALYSE_XZ_DLTB t where t. TBBH='"
				+ yw_guid + "'";
		List<Map<String, Object>> listYw = query(sql, YW);
		response(listYw);
	}

	/**
	 * <br>
	 * Description:获取卫片图斑规划分析数据
	 */
	@SuppressWarnings("unchecked")
	public void getghList() {
		String yw_guid = request.getParameter("yw_guid");
		List allRows = new ArrayList();
		String sql = "select YXJSQMJ,YTJJSQMJ,XZJSQMJ,JZJSQMJ,ZYJBNT from WPZF_ANALYSE_GH t where t.TBBH='"
				+ yw_guid + "' ";
		List listYw = query(sql, YW);
		int i = 0;
		List oneRow1 = new ArrayList();
		if (listYw.size() > 0) {
			Map map = (Map) listYw.get(0);
			String s = "允许建设区面积";
			oneRow1.add(s);
			if (map.get("YXJSQMJ") != null) {
				oneRow1.add(map.get("YXJSQMJ"));
			} else {
				oneRow1.add("0");
			}
			oneRow1.add(i++);
			allRows.add(oneRow1);

			List oneRow2 = new ArrayList();
			s = "有条件建设区面积";
			oneRow2.add(s);
			if (map.get("YTJJSQMJ") != null) {
				oneRow2.add(map.get("YTJJSQMJ"));
			} else {
				oneRow2.add("0");
			}
			oneRow2.add(i++);
			allRows.add(oneRow2);

			List oneRow3 = new ArrayList();
			s = "限制建设区面积";
			oneRow3.add(s);
			if (map.get("XZJSQMJ") != null) {
				oneRow3.add(map.get("XZJSQMJ"));
			} else {
				oneRow3.add("0");
			}
			oneRow3.add(i++);
			allRows.add(oneRow3);

			List oneRow4 = new ArrayList();
			s = "禁止建设区面积";
			oneRow4.add(s);
			if (map.get("JZJSQMJ") != null) {
				oneRow4.add(map.get("JZJSQMJ"));
			} else {
				oneRow4.add("0");
			}
			oneRow4.add(i++);
			allRows.add(oneRow4);

			List oneRow5 = new ArrayList();
			s = "占用基本农田面积";
			oneRow5.add(s);
			if (map.get("ZYJBNT") != null) {
				oneRow5.add(map.get("ZYJBNT"));
			} else {
				oneRow5.add("0");
			}
			oneRow5.add(i++);
			allRows.add(oneRow5);
			response(allRows);
		} else {
			response("false");
			return;
		}
	}

	/**
	 * <br>
	 * Description:获取卫片图斑历史核查情况分析数据
	 */
	@SuppressWarnings("unchecked")
	public void getlsList() {
		String yw_guid = request.getParameter("yw_guid");
		List allRows = new ArrayList();
		String sql = "select YXJSQMJ,YTJJSQMJ,XZJSQMJ,JZJSQMJ,ZYJBNT from WPZF_ANALYSE_GH t where t.TBBH='"
				+ yw_guid + "' ";
		List listYw = query(sql, YW);
		int i = 0;
		List oneRow1 = new ArrayList();
		if (listYw.size() > 0) {
			Map map = (Map) listYw.get(0);
			String s = "允许建设区面积";
			oneRow1.add(s);
			if (map.get("YXJSQMJ") != null) {
				oneRow1.add(map.get("YXJSQMJ"));
			} else {
				oneRow1.add("0");
			}
			oneRow1.add(i++);
			allRows.add(oneRow1);

			List oneRow2 = new ArrayList();
			s = "有条件建设区面积";
			oneRow2.add(s);
			if (map.get("YTJJSQMJ") != null) {
				oneRow2.add(map.get("YTJJSQMJ"));
			} else {
				oneRow2.add("0");
			}
			oneRow2.add(i++);
			allRows.add(oneRow2);

			List oneRow3 = new ArrayList();
			s = "限制建设区面积";
			oneRow3.add(s);
			if (map.get("XZJSQMJ") != null) {
				oneRow3.add(map.get("XZJSQMJ"));
			} else {
				oneRow3.add("0");
			}
			oneRow3.add(i++);
			allRows.add(oneRow3);

			List oneRow4 = new ArrayList();
			s = "禁止建设区面积";
			oneRow4.add(s);
			if (map.get("JZJSQMJ") != null) {
				oneRow4.add(map.get("JZJSQMJ"));
			} else {
				oneRow4.add("0");
			}
			oneRow4.add(i++);
			allRows.add(oneRow4);

			List oneRow5 = new ArrayList();
			s = "占用基本农田面积";
			oneRow5.add(s);
			if (map.get("ZYJBNT") != null) {
				oneRow5.add(map.get("ZYJBNT"));
			} else {
				oneRow5.add("0");
			}
			oneRow5.add(i++);
			allRows.add(oneRow5);

			response(allRows);
		} else {
			response("false");
			return;
		}
	}
	
    /**
     * <br>Description:根据监测图斑编号，返回wkt格式坐标信息
     * <br>Author:郭润沛
     * <br>Date:2013-5-24
     * @param tbbh
     * @return
     */
    
    public String getXmzb(String rwbh) {
        String sql = "select jwzb from dtjg.gd_ba where yw_guid=?";
        List<Map<String, Object>> list = query(sql, DTJG, new Object[] { rwbh });
        if (list.size() > 0) {
            String zb = list.get(0).get("jwzb").toString();
            if (zb == null) {
                return null;
            } else {
                String[] zbs = zb.split(";");
                List<String> listzb = new ArrayList<String>();
                for(int i=0 ; i< zbs.length ; i++){
                	listzb.add(zbs[i]);                	
                }
                sql = "select t.*, t.rowid from gis_extent t where t.flag = '1'";
                List<Map<String, Object>> mapConfigList = query(sql, CORE);
                BigDecimal wkid = (BigDecimal) mapConfigList.get(0).get("wkid");
                Polygon polygon = new Polygon(listzb,wkid.intValue(),true);
                return polygon.toJson();
            }
        } else {
            return null;
        }
    }
    
   /**
    * 
    * <br>Description:获取卫片监测图斑
    * <br>Author:王雷
    * <br>Date:2013-7-31
    */
    public void getWpJctbList(){
        String keyWord = request.getParameter("keyWord");
        String strColumnName = "t.yw_guid || t.xmc || jcbh|| t.qsx || t.hsx || t.year || t.jcmj ||t.xzb || t.yzb";
        String where = "";
        if (keyWord != null && !"".equals(keyWord)) {
            keyWord = keyWord.trim();
            while (keyWord.indexOf("  ") > 0) {// 循环去掉多个空格，所有字符中间只用一个空格间隔
                keyWord = keyWord.replace("  ", " ");
            }
            keyWord = UtilFactory.getStrUtil().unescape(keyWord);
            keyWord = keyWord.toUpperCase();
            where += " and ("
                    + strColumnName
                    + " like '%"
                    + (keyWord.replaceAll(" ", "%' and " + strColumnName
                            + "  like '%")) + "%')";// 查询条件
        }

        String sql="select t.yw_guid,t.xmc,t.jcbh,t.year,t.jcmj,t.qsx,t.hsx,t.xzb,t.yzb from xz_wp t where 1=1 ";
        sql += where;
        List<Map<String,Object>> list = query(sql,AbstractBaseBean.GIS);
        response(list);      
    }
}
