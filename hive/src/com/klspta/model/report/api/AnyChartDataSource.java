package com.klspta.model.report.api;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

/**
 * <br>
 * Title:Report <br>
 * Description:报表统一类 <br>
 * Author:赵伟 <br>
 * Date:2012-8-20
 */
public class AnyChartDataSource extends AbstractBaseBean {

	/**
	 * <br>
	 * Description:获取anychart所需资源 <br>
	 * Author:赵伟 <br>
	 * Date:2012-8-20
	 */
	public void getChartResource() {
		String id = request.getParameter("id");
		String[] ob = { id };
		String sql = "select t.sql,t.xmlpath from REPORT_RESOURCE t where t.id=?";
		List<Map<String, Object>> list = query(sql, CORE, ob);
		response(list);
	}

	/**
	 * <br>
	 * Description:获取并返回图表数据 <br>
	 * Author:赵伟 <br>
	 * Date:2012-8-20
	 */
	public void getChartData() {
		String sql = request.getParameter("sql");
		String condition = request.getParameter("condition");
		String fieldValue = request.getParameter("fieldValue");
		if (condition == null) {
			condition = "";
		}
		condition = UtilFactory.getStrUtil().unescape(condition);
		sql = UtilFactory.getStrUtil().unescape(sql);
		
		sql = getCustomSql(sql, condition);
		List<Map<String, Object>> list;
		if (fieldValue.equals("null") || fieldValue.equals("")
				|| fieldValue == null) {
			list = query(sql, YW);
		} else {
			fieldValue = UtilFactory.getStrUtil().unescape(fieldValue);
			String[] str = fieldValue.split(",");
			list = query(sql, YW, str);
		}
		response(list);
	}

	/**
	 * <br>
	 * Description:根据定制查询生成指定的sql语句 <br>
	 * Author:赵伟 <br>
	 * Date:2012-8-20
	 * 
	 * @param sql
	 * @param parameters
	 * @return
	 */
	private String getCustomSql(String sql, String conditions) {
		if (conditions == null || conditions.equals("null")
				|| conditions.equals("")) {
			return sql;
		}
		sql = "select * from (" + sql + ") where " + conditions;
		return sql;
	}

}
