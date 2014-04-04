package com.klspta.model.report.api;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

/**
 * <br>
 * Title:ReportDataSource <br>
 * Description:报表统一的数据源 <br>
 * Author:赵伟 <br>
 * Date:2012-8-20
 */
public class ReportDataSource extends AbstractBaseBean implements JRDataSource {
	public String condition;
	public String fieldValue;
	public String sql;
	public String configPath;
	private int pathIndex;
	private int cursor = -1;
	private List<Map<String, Object>> datas;
	private List<Object> Jsonlist;

	/**
	 * <br>
	 * Description:加载及初始化参数 <br>
	 * Author:赵伟 <br>
	 * Date:2012-8-23
	 * 
	 * @param condition
	 * @param fieldValue
	 * @param pathIndex
	 * @throws UnsupportedEncodingException
	 */
	public void loadReportParameter(String condition, String fieldValue, String pathIndex)
			throws UnsupportedEncodingException {
		if (condition == null) {
			condition = "";
		}
		//condition = new String(condition.getBytes("ISO-8859-1"), "utf-8");
		if (fieldValue == null) {
			fieldValue = "";
		}
		//fieldValue = new String(fieldValue.getBytes("ISO-8859-1"), "utf-8");
		if (pathIndex == null) {
			pathIndex = "0";
		}
		this.pathIndex = Integer.parseInt(pathIndex);
		this.condition = condition;
		this.fieldValue = fieldValue;
	}

	/**
	 * <br>
	 * Description:获取参数组的Json格式 <br>
	 * Author:赵伟 <br>
	 * Date:2012-9-3
	 * 
	 * @param map
	 * @return
	 */
	public String getCustomParameterJson(Map<String, String> map) {
		Jsonlist = new ArrayList<Object>();
		String str = "";
		try {
			Iterator<Entry<String, String>> its = map.entrySet().iterator();
			while (its.hasNext()) {
				Entry<String, String> entry = (Entry<String, String>) (its.next());
				String key = entry.getKey().toString().trim();
				String value = entry.getValue();
				splitParameter(key, value);
			}
			str = UtilFactory.getJSONUtil().objectToJSON(Jsonlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * <br>
	 * Description:拆分参数重组Json格式的方法 <br>
	 * Author:赵伟 <br>
	 * Date:2012-9-3
	 * 
	 * @param type
	 * @param parameter
	 */
	private void splitParameter(String type, String parameter) {
		if (parameter == null || parameter.endsWith("null"))
			return;
		try {
			parameter = new String(parameter.trim().getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 根据分号拆分成若干组参数组,并迭代出所有参数组。
		String parameters[] = parameter.split(";");
		for (int i = 0; i < parameters.length; i++) {
			int endIndex = parameters[i].indexOf("{");
			String[] position = parameters[i].substring(0, endIndex - 1).split(",");
			String[] value = parameters[i].substring(endIndex + 1, parameters[i].indexOf("}")).split(",");
			// 根据参数的格式迭代出所有参数
			for (int j = 0; j < value.length; j++) {
				Map<String, String> map = new HashMap<String, String>();
				// 迭代出位置参数
				for (int k = 0; k < position.length; k++) {
					map.put("key" + k, position[k]);
				}
				int index = value[j].indexOf(":");
				map.put("name", type + value[j].substring(0, index));
				map.put("value", value[j].substring(index + 1));
				Jsonlist.add(map);
			}
		}
	}

	/**
	 * <br>
	 * Description:获取报表类型，anychart？ireport？ <br>
	 * Author:赵伟 <br>
	 * Date:2012-8-20
	 * 
	 * @param id
	 * @return
	 */
	public String getReportType(String id) {
		String[] ob = { id };
		String sql1 = "select t.configpath,t.sql from REPORT_RESOURCE t where t.id=?";
		List<Map<String, Object>> list = query(sql1, CORE, ob);
		String type = "";
		try {
			Map<String, Object> map = list.get(0);
			configPath = map.get("configpath").toString();
			String[] str = configPath.split(",");
			configPath = str[pathIndex];
			if (configPath.indexOf(".") == configPath.lastIndexOf(".")) {
				if (configPath.indexOf(".xml") != -1) {
					type = "anychart";
				} else if (configPath.indexOf(".jasper") != -1) {
					type = "ireport";
				} else {
					System.out.println("配置文件后缀名要          为.xml或.jasper");
				}
			} else {
				System.out.println("文件格式不正确");
			}
			sql = map.get("sql").toString().replace("\n", " ");
		} catch (Exception e) {
			System.out.println("++++" + e.getMessage());
			HttpSession session1 = request.getSession();
			session1.setAttribute("errormessage", "无此ID信息或某些字段为空;");
			session1.setAttribute("e     rrordetail", e.getMessage().replace(";", ";\n"));
		}
		return type;
	}

	/**
	 * <br>
	 * Description:获取并返回anychart图表数据 <br>
	 * Author:赵伟 <br>
	 * Date:2012-8-20
	 */
	public void getChartData() {
		sql = UtilFactory.getStrUtil().unescape(request.getParameter("sql"));
		condition = UtilFactory.getStrUtil().unescape(request.getParameter("condition"));
		fieldValue = UtilFactory.getStrUtil().unescape(request.getParameter("fieldValue"));
		sql = getCustomSql(sql, condition);
		List<Map<String, Object>> list = null;
		try {
			if (fieldValue.equals("")) {
				list = query(sql, YW);
			} else {
				Object [] str = fieldValue.split(",");
				list = query(sql, YW, str);
			}
		} catch (Exception e) {
			HttpSession session = request.getSession();
			session.setAttribute("errormessage", "获取数据信息错误;");
			session.setAttribute("errordetail", e.getMessage().replace(";", ";\n"));
			e.printStackTrace();
			return;
		}
		response(list);
	}

	public void test() {
		System.out.println("fdsfsdfssdsd");
	}

	/**
	 * <br>
	 * Description:获取ireport数据 <br>
	 * Author:赵伟 <br>
	 * Date:2012-8-20
	 */
	public void fillData() {
		sql = getCustomSql(sql, condition);
		if (fieldValue.equals("")) {
			datas = query(sql, YW);
		} else {
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
		if (conditions.equals("")) {
			return sql;
		}
		sql = "select * from (" + sql + ") where " + conditions;
		return sql;
	}
}
