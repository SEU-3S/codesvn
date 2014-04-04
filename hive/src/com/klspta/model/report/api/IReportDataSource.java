package com.klspta.model.report.api;

import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

public class IReportDataSource extends AbstractBaseBean implements JRDataSource {
	private int cursor = -1;
	private List<Map<String, Object>> datas;
	private String condition;
	private String fieldValue;
	private String sql;

	/**
	 * <br>
	 * Description:加载条件 <br>
	 * Author:赵伟 <br>
	 * Date:2012-8-20
	 * 
	 * @param sql
	 * @param condition
	 * @param fieldValue
	 */
	public void loadResource(String sql, String condition, String fieldValue) {
		this.sql = sql;
		this.condition = condition;
		this.fieldValue = fieldValue;
	}

	/**
	 * <br>
	 * Description:获取Ireport报表资源 <br>
	 * Author:赵伟 <br>
	 * Date:2012-8-20
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getIReportResource(String id) {
		String[] ob = { id };
		String sql = "select t.jasperpath,t.sql from REPORT_RESOURCE t where t.id=?";
		List<Map<String, Object>> list = query(sql, CORE, ob);
		return list;
	}

	/**
	 * <br>
	 * Description:获取数据 <br>
	 * Author:赵伟 <br>
	 * Date:2012-8-20
	 */
	public void fillData() {
		condition = UtilFactory.getStrUtil().unescape(condition);
		sql = UtilFactory.getStrUtil().unescape(sql);
		sql = getCustomSql(sql, condition);

		if (fieldValue == null || fieldValue.equals("null")
				|| fieldValue.equals("")) {
			datas = query(sql, YW);
		} else {
			fieldValue = UtilFactory.getStrUtil().unescape(fieldValue);
			String[] str = fieldValue.split(",");
			datas = query(sql, YW, str);
		}
	}

	@Override
	public Object getFieldValue(JRField arg0) throws JRException {
		Map<String, Object> temp = datas.get(cursor);
		return temp.get(arg0.getName());
	}

	@Override
	public boolean next() throws JRException {
		if (datas == null) {
			fillData();
		}
		cursor++;
		if (cursor >= datas.size()) {
			return false;
		} else {
			return true;
		}
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
