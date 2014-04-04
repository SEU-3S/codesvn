package com.klspta.web.cbd.dtjc;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sun.tools.jar.resources.jar;

import net.sf.jasperreports.components.barbecue.BarcodeProviders.NW7Provider;

import com.klspta.base.AbstractBaseBean;

/**
 * 
 * <br>
 * Title:创建统计报表修改页面 <br>
 * Description:TODO 类功能描述 <br>
 * Author:黎春行 <br>
 * Date:2013-10-29
 */
public class TjbbBuild {
	public static final int MIN_YEAR = 2011;

	/**
	 * 
	 * <br>
	 * Description:生成前台展现页面table <br>
	 * Author:黎春行 <br>
	 * Date:2013-10-29
	 * 
	 * @return
	 */
	public static StringBuffer buildTable() {
		StringBuffer tableBuffer = new StringBuffer();
		tableBuffer.append(buildTitle());
		tableBuffer.append(buildKFTL());
		tableBuffer.append(buildGDTL());
		tableBuffer.append(buildRzxq());
		tableBuffer.append(buildQyzj());
		tableBuffer.append(buildAZF());
		tableBuffer.append("</table>");
		return tableBuffer;
	}

	public static StringBuffer buildTable(String userId) {
		TjbbData tjbbData = new TjbbData();
		List<Map<String, Object>> userInfo = tjbbData.getPlanByUserId(userId);
		if (userInfo.size() < 1) {
			return buildTable();
		} else {
			Map<String, Object> userMap = userInfo.get(0);
			int minyear = Integer.parseInt(String.valueOf(userMap
					.get("MINYEAR")));
			int maxyear = Integer.parseInt(String.valueOf(userMap
					.get("MAXYEAR")));
			Set<String> projects = new HashSet<String>();
			String[] pros = String.valueOf(userMap.get("PROJECTS")).split(",");
			for (int i = 0; i < pros.length; i++) {
				projects.add(pros[i]);
			}
			StringBuffer tableBuffer = new StringBuffer();
			tableBuffer.append(buildTitle(minyear, maxyear));
			tableBuffer.append(buildKFTL(minyear, maxyear, projects));
			tableBuffer.append(buildGDTL(minyear, maxyear, projects));
			tableBuffer.append(buildRzxq(minyear, maxyear));
			tableBuffer.append(buildQyzj(minyear, maxyear));
			tableBuffer.append(buildAZF(minyear, maxyear));
			tableBuffer.append("</table>");
			return tableBuffer;
		}
	}

	private static StringBuffer buildAZF() {
		StringBuffer azf = new StringBuffer();
		TjbbData tjbbData = new TjbbData();
		int max_year = Integer.parseInt(tjbbData.getMaxYear());
		List<Map<String, Object>> azflist = tjbbData.getAzfProject();
		if (azflist.size() > 0) {
			for (int i = 0; i < azflist.size(); i++) {
				azf = buildKG(MIN_YEAR, max_year, azf, azflist.get(i));
				azf = buildTZ(MIN_YEAR, max_year, azf, azflist.get(i));
			}
		}
		return azf;
	}

	private static StringBuffer buildAZF(int minyear, int maxyear) {
		TjbbData tjbbData = new TjbbData();
		List<Map<String, Object>> azflist = tjbbData.getAzfProject();
		StringBuffer azf = new StringBuffer();
		if (azflist.size() > 0) {
			for (int i = 0; i < azflist.size(); i++) {
				azf = buildKG(minyear, maxyear, azf, azflist.get(i));
				azf = buildTZ(minyear, maxyear, azf, azflist.get(i));
			}
		}
		return azf;
	}

	private static StringBuffer buildKG(int minyear, int maxyear,
			StringBuffer kgBody, Map<String, Object> map) {
		Map<String, String> kgMap = new TjbbData().getKG(minyear, maxyear,map);
		kgBody.append("<tbody id='kg'>");
		kgBody.append("<tr  style='background: #FFFFE5;'>");
		kgBody
				.append("<td style='background: #C0C0C0;border-bottom:1px #ffffff solid;' ><label>开工</label></td>");
		kgBody.append("<td style='background: #C0C0C0;border-bottom:1px #ffffff solid;' ><label>").append(map.get("XMMC")).append("</label></td>");
		for (int j = minyear; j <= maxyear; j++) {
			for (int quarter = 1; quarter <= 4; quarter++) {
				String key = j + "#" + quarter;
				String value = kgMap.get(key);
				value = (value == null) ? "" : value;
				kgBody.append("<td class='no' width='45px' >");
				kgBody
						.append("<input type='text' style='width:45px;' onchange='addkg(this); return false' value='");
				kgBody.append(value).append("'></td>");
			}
		}
		kgBody.append("</tr>");
		kgBody.append("</tbody>");
		return kgBody;
	}

	private static StringBuffer buildTZ(int minyear, int maxyear,
			StringBuffer kgBody, Map<String, Object> map) {
		Map<String, String> kgMap = new TjbbData().getTZ(minyear, maxyear,map);
		kgBody.append("<tbody id='tz'>");
		kgBody.append("<tr  style='background: #FFFFE5;'>");
		kgBody
				.append("<td style='background: #C0C0C0;border-bottom:1px #ffffff solid;' ><label>投资</label></td>");
		kgBody.append("<td style='background: #C0C0C0;border-bottom:1px #ffffff solid;' ><label>").append(map.get("TZMC")).append("</label></td>");
		for (int j = minyear; j <= maxyear; j++) {
			for (int quarter = 1; quarter <= 4; quarter++) {
				String key = j + "#" + quarter;
				String value = kgMap.get(key);
				value = (value == null) ? "" : value;
				kgBody.append("<td class='no' width='45px' >");
				kgBody
						.append("<input type='text' style='width:45px;' onchange='addtz(this); return false' value='");
				kgBody.append(value).append("'></td>");
			}
		}
		kgBody.append("</tr>");
		kgBody.append("</tbody>");
		return kgBody;
	}

	/**
	 * 
	 * <br>
	 * Description:创建开发时序表格抬头 <br>
	 * Author:黎春行 <br>
	 * Date:2013-10-29
	 * 
	 * @return
	 */
	private static StringBuffer buildTitle() {
		TjbbData tjbbData = new TjbbData();
		int max_year = Integer.parseInt(tjbbData.getMaxYear());
		return buildTitle(MIN_YEAR, max_year);
	}

	private static StringBuffer buildTitle(int min_year, int max_year) {
		StringBuffer title = new StringBuffer();
		int width = 400 + (max_year - min_year) * 200;
		title
				.append(
						"<table id='planTable'  style='text-align: center; font: normal 14px verdana; border:none; width:")
				.append(width).append(";' cellpadding='0' cellspacing='0'   >");
		title
				.append("<thead>")
				.append(
						"<tr  style='background: #C0C0C0; text-align: center; font: normal 18px verdana;'>");
		title
				.append("<td colspan='2' rowspan='2' style='border-bottom:1px #ffffff solid;' ><label>项目名称</label></td>");
		// 缓存季度行
		StringBuffer quarter = new StringBuffer();
		// 添加年度行
		for (int i = min_year; i <= max_year; i++) {
			title.append("<td colspan=4 width='200'><label>").append(i).append(
					"</label></td>");
			quarter.append("<td class='spring'><label>1</label></td>");
			quarter.append("<td class='summer'><label>2</label></td>");
			quarter.append("<td class='fall'><label>3</label></td>");
			quarter.append("<td class='winter'><label>4</label></td>");
		}
		title.append("</tr><tr>").append(quarter);
		title.append("</tr></thead>");
		return title;
	}

	/**
	 * 
	 * <br>
	 * Description:创建开发体量tbody <br>
	 * Author:黎春行 <br>
	 * Date:2013-10-29
	 * 
	 * @return
	 */
	private static StringBuffer buildKFTL() {
		TjbbData tjbbData = new TjbbData();
		int max_year = Integer.parseInt(tjbbData.getMaxYear());
		Set<String> kftlProjectSet = tjbbData.getKFTLProject();
		return buildKFTL(MIN_YEAR, max_year, kftlProjectSet);

	}

	private static StringBuffer buildKFTL(int min_year, int max_year,
			Set kftlProjectSet) {
		StringBuffer kftlBody = new StringBuffer();
		TjbbData tjbbData = new TjbbData();
		Map<String, Map<String, Object>> kftlPlanMap = tjbbData.getKFTLPlan();
		kftlBody
				.append("<tbody id='kftl' style='border-bottom:1px #000000 solid'>");
		Iterator<String> proIterator = kftlProjectSet.iterator();
		for (int i = 0; i < kftlProjectSet.size(); i++) {
			String projectName = proIterator.next();
			Map<String, Object> plan = kftlPlanMap.get(projectName);
			if (0 == i) {
				kftlBody
						.append(
								"<tr style='background: #E5FFE5;'><td rowspan='")
						.append(kftlProjectSet.size())
						.append(
								"' width='20px' align='center' style='background: #C0C0C0;border-bottom:1px #ffffff solid' ><label>开发体量</label></td>");
			} else {
				if (i / 2 == 0) {
					kftlBody.append("<tr  style='background: #FFFFE5;' >");
				} else {
					kftlBody.append("<tr  style='background: #E5FFE5;' >");
				}
			}
			kftlBody
					.append(
							"<td style='background: #C0C0C0;border-bottom:1px #ffffff solid;'><label>")
					.append(projectName).append("</label></td>");
			for (int j = min_year; j <= max_year; j++) {
				for (int t = 1; t <= 4; t++) {
					if (!(plan == null) && plan.containsKey(j + "##" + t)) {
						kftlBody
								.append("<td class='yes' onMouseOver='changePlan(this, 1); return false;'' ></td>");
					} else {
						kftlBody
								.append("<td class='no'  onMouseOver='hiddleDiv(); return false;' onDblClick='addDetail(this); return false;'></td>");
					}
				}
			}
			kftlBody.append("</tr>");
		}
		kftlBody.append("</tbody>");
		return kftlBody;

	}

	/**
	 * 
	 * <br>
	 * Description:创建供地体量tbody <br>
	 * Author:黎春行 <br>
	 * Date:2013-10-29
	 * 
	 * @return
	 */
	private static StringBuffer buildGDTL() {
		TjbbData tjbbData = new TjbbData();
		int max_year = Integer.parseInt(tjbbData.getMaxYear());
		Set<String> gdtlProjectSet = tjbbData.getGDTLProject();
		return buildGDTL(MIN_YEAR, max_year, gdtlProjectSet);
	}

	private static StringBuffer buildGDTL(int min_year, int max_year,
			Set gdtlProjectSet) {
		StringBuffer gdtlBody = new StringBuffer();
		TjbbData tjbbData = new TjbbData();
		Map<String, Map<String, Object>> gdtlPlanMap = tjbbData.getGDTLPlan();
		gdtlBody.append("<tbody id='gdtl'>");
		Iterator<String> proIterator = gdtlProjectSet.iterator();
		for (int i = 0; i < gdtlProjectSet.size(); i++) {
			String projectName = proIterator.next();
			Map<String, Object> plan = gdtlPlanMap.get(projectName);
			if (0 == i) {
				gdtlBody
						.append(
								"<tr style='background: #E5FFE5;'><td rowspan='")
						.append(gdtlProjectSet.size())
						.append(
								"' width='20px' align='center' style='background: #C0C0C0;' ><label>供地体量</label></td>");
			} else {
				if (i / 2 == 0) {
					gdtlBody.append("<tr  style='background: #FFFFE5;' >");
				} else {
					gdtlBody.append("<tr  style='background: #E5FFE5;' >");
				}
			}
			gdtlBody
					.append(
							"<td style='background: #C0C0C0;border-bottom:1px #ffffff solid;'><label>")
					.append(projectName).append("</label></td>");
			for (int j = min_year; j <= max_year; j++) {
				for (int t = 1; t <= 4; t++) {
					if (!(plan == null) && plan.containsKey(j + "##" + t)) {
						gdtlBody
								.append("<td class='yes' onMouseOver='changePlan(this, 1); return false;'' ></td>");
					} else {
						gdtlBody
								.append("<td class='no'  onMouseOver='hiddleDiv(); return false;' onDblClick='addDetail(this); return false;'></td>");
					}
				}
			}
			gdtlBody.append("</tr>");
		}
		gdtlBody.append("</tbody>");
		return gdtlBody;
	}

	/**
	 * 
	 * <br>
	 * Description:创建投融资情况的融资需求模块 <br>
	 * Author:黎春行 <br>
	 * Date:2013-10-29
	 * 
	 * @return
	 */
	private static StringBuffer buildRzxq() {
		TjbbData tjbbData = new TjbbData();
		int max_year = Integer.parseInt(tjbbData.getMaxYear());
		return buildRzxq(MIN_YEAR, max_year);
	}

	private static StringBuffer buildRzxq(int min_year, int max_year) {
		Map<String, String> rzxqMap = new TjbbData().getBqrzxq(min_year,
				max_year);
		StringBuffer rzxqBody = new StringBuffer();
		rzxqBody.append("<tbody id='gdtl'>");
		rzxqBody.append("<tr  style='background: #FFFFE5;'>");
		rzxqBody
				.append("<td style='background: #C0C0C0;border-bottom:1px #ffffff solid;' colspan='2' ><label>融资需求</label></td>");
		for (int j = min_year; j <= max_year; j++) {
			for (int quarter = 1; quarter <= 4; quarter++) {
				String key = j + "#" + quarter;
				String value = rzxqMap.get(key);
				value = (value == null) ? "" : value;
				rzxqBody.append("<td class='no' width='45px' >");
				rzxqBody
						.append("<input type='text' style='width:45px;' onchange='addrzxq(this); return false' value='");
				rzxqBody.append(value).append("'></td>");
			}
		}
		rzxqBody.append("</tr>");
		rzxqBody.append("</tbody>");
		return rzxqBody;
	}

	/**
	 * 
	 * <br>
	 * Description:创建投融资情况的权益性资金注入 <br>
	 * Author:黎春行 <br>
	 * Date:2013-10-29
	 * 
	 * @return
	 */
	private static StringBuffer buildQyzj() {
		TjbbData tjbbData = new TjbbData();
		int max_year = Integer.parseInt(tjbbData.getMaxYear());
		return buildQyzj(MIN_YEAR, max_year);
	}

	private static StringBuffer buildQyzj(int min_year, int max_year) {
		Map<String, String> qyzjMap = new TjbbData().getQyxzjzr(min_year,
				max_year);
		StringBuffer qyzjBody = new StringBuffer();
		qyzjBody.append("<tbody id='gdtl'>");
		qyzjBody.append("<tr  style='background: #FFFFE5;'>");
		qyzjBody
				.append("<td style='background: #C0C0C0;border-bottom:1px #ffffff solid;' colspan='2' ><label>权益性资金注入</label></td>");
		for (int j = min_year; j <= max_year; j++) {
			for (int quarter = 1; quarter <= 4; quarter++) {
				String key = j + "#" + quarter;
				String value = qyzjMap.get(key);
				value = (value == null) ? "" : value;
				qyzjBody.append("<td class='no' width='45px' >");
				qyzjBody
						.append("<input type='text' style='width:45px;' onchange='addzjzr(this); return false' value='");
				qyzjBody.append(value).append("'></td>");
			}
		}
		qyzjBody.append("</tr>");
		qyzjBody.append("</tbody>");
		return qyzjBody;
	}

	public static StringBuffer addTR(){
		StringBuffer string = new StringBuffer();
		string =  buildKG(string);
		return buildTZ(string);
	}

	private static StringBuffer buildKG(StringBuffer kgBody) {
		TjbbData tjbbData = new TjbbData();
		int max_year = Integer.parseInt(tjbbData.getMaxYear());
		kgBody.append("\"<tr  style='background: #FFFFE5;'>");
		kgBody
				.append("<td style='background: #C0C0C0;border-bottom:1px #ffffff solid;' ><label>开工</label></td>");
		kgBody.append("<td style='background: #C0C0C0;border-bottom:1px #ffffff solid;' ><label>").append("<input type='text' style='width:45px;' onchange='addkg(this); return false' value=''/>").append("</label></td>");
		for (int j = MIN_YEAR; j <= max_year; j++) {
			for (int quarter = 1; quarter <= 4; quarter++) {
				kgBody.append("<td class='no' width='45px' >");
				kgBody
						.append("<input type='text' style='width:45px;' onchange='addkg(this); return false' value=''></td>");
			}
		}
		kgBody.append("</tr>");
		return kgBody;
	}

	private static StringBuffer buildTZ(StringBuffer kgBody) {
		TjbbData tjbbData = new TjbbData();
		int max_year = Integer.parseInt(tjbbData.getMaxYear());
		kgBody.append("<tr  style='background: #FFFFE5;'>");
		kgBody
				.append("<td style='background: #C0C0C0;border-bottom:1px #ffffff solid;' ><label>投资</label></td>");
		kgBody.append("<td style='background: #C0C0C0;border-bottom:1px #ffffff solid;' ><label>").append("<input type='text' style='width:45px;' onchange='addkg(this); return false' value=''/>").append("</label></td>");
		for (int j = MIN_YEAR; j <= max_year; j++) {
			for (int quarter = 1; quarter <= 4; quarter++) {
				kgBody.append("<td class='no' width='45px' >");
				kgBody
						.append("<input type='text' style='width:45px;' onchange='addkg(this); return false' value=''></td>");
			}
		}
		kgBody.append("</tr>\"");
		return kgBody;
	}
}
