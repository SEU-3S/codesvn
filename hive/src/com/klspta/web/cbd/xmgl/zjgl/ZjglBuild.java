package com.klspta.web.cbd.xmgl.zjgl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;

import com.klspta.console.ManagerFactory;
import com.klspta.console.role.Role;
import com.klspta.console.user.User;

/*******************************************************************************
 * 
 * <br>
 * Title:资金管理前台展现处理类 <br>
 * Description: <br>
 * Author:朱波海 <br>
 * Date:2013-12-26
 */

public class ZjglBuild {

	static String[] items = { "YY", "EY", "SANY", "SIY", "WY", "LY", "QY",
			"BQY", "JY", "SIYUE", "SYY", "SEY" };

	/***************************************************************************
	 * 
	 * <br>
	 * Description:构建title <br>
	 * Author:朱波海 <br>
	 * Date:2013-12-19
	 * 
	 * @return
	 */
	public static StringBuffer buildTitle(String year) {
		StringBuffer Buffer = new StringBuffer();
		Buffer
				.append("<table  width='1900px' id='table'>"
						+ "<tr class='tr01' style='text-align:center' >"
						+ "<td rowspan='3' align='center' width='20%' class='tr01'><div  width='200px'>类别<div></td>"
						+ "<td rowspan='3'  align='center' width='80px' class='tr01'>预算费用</td>"
						+ "<td rowspan='3' colspan='2' align='center' width='260px' class='tr01'>累计已缴纳/已审批资金</td>"
						+ "<td rowspan='2' colspan='2' align='center' width='200' class='tr01'>累计发生(或返还)费用</td>"
						+ "<td rowspan='3'  align='center' width='80px' class='tr01'>期初余额</td>"
						+ "<td colspan='12'  align='center' width='1000px' class='tr01'>"
						+ year
						+ "年资金审批</td>"
						+ "<td  rowspan='3'  align='center' width='80px' class='tr01'>"
						+ year
						+ "年度流入/审批</td>"
						+ "</tr>"
						+

						"<tr class='tr01'>"
						+ "<td colspan='3' align='center' width='250px' class='tr01'>一季度</td>"
						+ "<td colspan='3' align='center' width='250px' class='tr01'>二季度</td>"
						+ "<td colspan='3' align='center' width='250px' class='tr01'>三季度</td>"
						+ "<td colspan='3' align='center' width='250px' class='tr01'>四季度</td>"
						+ "</tr>"
						+

						"<tr class='tr01'>"
						+ "<td align='center' width='130px' class='tr01'>已发生/到账</td>"
						+ "<td align='center' width='130px' class='tr01'>资金进度</td>"
						+ "<td align='center' width='83px' class='tr01'>一月</td>"
						+ "<td align='center' width='83px' class='tr01'>二月</td>"
						+ "<td align='center' width='83px' class='tr01'>三月</td>"
						+ "<td align='center' width='83px' class='tr01'>四月</td><"
						+ "td align='center' width='83px' class='tr01'>五月</td>"
						+ "<td align='center' width='83px' class='tr01'>六月</td> "
						+ "<td align='center' width='83px' class='tr01'>七月</td>"
						+ "<td align='center' width='83px' class='tr01'>八月</td>"
						+ "<td align='center' width='83px' class='tr01'>九月</td>"
						+ "<td align='center' width='83px' class='tr01'>十月</td>"
						+ "<td align='center' width='83px' class='tr01'>十一月</td>"
						+ "<td align='center' width='83px' class='tr01'>十二月</td> "
						+ "</tr>");
		return Buffer;
	}

	public static StringBuffer buildZjlr(List<Map<String, Object>> list) {
		StringBuffer stringBuffer = new StringBuffer();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				stringBuffer
						.append("<tr><td width='200px' class='tr04'>"
								+ delNull(String.valueOf(list.get(i).get("lb")))
								+ "</td><td class='tr04'><input class='tr04' type='text' style='width:70px;hight:15px;' onchange='addzjlr(this); return false' value='"
								+ delNull(String.valueOf(list.get(i)
										.get("ysfy")))
								+ "'  id='lr@"
								+ String.valueOf(list.get(i).get("status"))
								+ "@2'/></td><td colspan='2' class='tr04'><input class='tr04' type='text' style='width:180px;hight:15px;' onchange='addzjlr(this); return false' value='"
								+ delNull(String.valueOf(list.get(i).get("lj")))
								+ " ' id='lr@"
								+ String.valueOf(list.get(i).get("status"))
								+ "@3'/></td><td class='tr04'><input class='tr04' type='text' style='width:90px;hight:15px;' onchange='addzjlr(this); return false' value='"
								+ delNull(String.valueOf(list.get(i).get(
										"YFSDZ")))
								+ " ' id='lr@"
								+ String.valueOf(list.get(i).get("status"))
								+ "@4'/></td><td class='tr04'><input class='tr04' type='text' style='width:90px;hight:15px;' onchange='addzjlr(this); return false' value='"
								+ delNull(String.valueOf(list.get(i)
										.get("ZJJD")))
								+ " ' id='lr@"
								+ String.valueOf(list.get(i).get("status"))
								+ "@5'/></td><td class='tr04'><input class='tr04' type='text' style='width:90px;hight:15px;' onchange='addzjlr(this); return false' value='"
								+ delNull(String.valueOf(list.get(i)
										.get("CQYE"))) + " ' id='lr@"
								+ String.valueOf(list.get(i).get("status"))
								+ "@6'/></td>");

				for (int j = 0; j < items.length; j++) {
					stringBuffer
							.append("<td class='tr04'><input class='tr04' type='text' style='width:90px;hight:15px;' onchange='addzjlr(this); return false' value='"
									+ delNull(String.valueOf(list.get(i).get(
											items[j])))
									+ " ' id='lr@"
									+ String.valueOf(list.get(i).get("status"))
									+ "@" + i + 7 + "'/></td>");
				}

				stringBuffer
						.append("<td class='tr04'><input class='tr04' type='text' style='width:90px;hight:15px;' onchange='addzjlr(this); return false' value='"
								+ delNull(String.valueOf(list.get(i)
										.get("LRSP")))
								+ " ' id='lr@"
								+ String.valueOf(list.get(i).get("status"))
								+ "@19'/></td></tr>");
			}
		}
		return stringBuffer;

	}
	
	/**
	 * 
	 * <br>Description:根据动态树结构生成资金流入节点
	 * <br>Author:黎春行
	 * <br>Date:2014-2-21
	 * @param list 数据源
	 * @param key  根节点
	 * @param status 类型（只读（read） 、编辑（write））
	 * @return
	 */
	public static List<Object> buildZjlrTR(List<Map<String, Object>> list, String key, String status){
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> returnObject = new ArrayList<Object>();
		List<Map<String, Object>> getMapList = new ArrayList<Map<String,Object>>();
		for(int i = 0; i < list.size(); i++){
			Map<String, Object> resuMap = list.get(i);
			if((key.equals(getValue(resuMap, "parent_id")))){
				String childkey = getValue(resuMap, "tree_id");
				List<Object> childList = buildZjlrTR(list, childkey, status);
				StringBuffer childString = (StringBuffer)childList.get(0);
				if(childString.length() == 0){
					getMapList.add(resuMap);
					stringBuffer.append(buildTr(resuMap, status));
				}else{
					String[] names = {"ysfy", "lj","YFSDZ","ZJJD","CQYE","yy","ey","sany","siy","wy","ly","qy","bay","jy","siyue","syy","sey","lrsp"};
					Map<String, Object> sumMap = new HashMap<String, Object>();
					List<Map<String, Object>> child = (List)childList.get(1);
					sumMap.put("tree_name", getValue(resuMap, "tree_name"));
					for(int j = 0; j < child.size(); j++){
						Map<String, Object> childMap = child.get(j);
						for(int t = 0; t < names.length; t++){
							String beginValue = getValue(sumMap, names[t]);
							String childValue = getValue(childMap, names[t]);
							String value = String.valueOf(Long.parseLong(beginValue) + Long.parseLong(childValue));
							sumMap.put(names[t], value);
						}
					}
					getMapList.add(sumMap);
					stringBuffer.append(buildTr(sumMap, "read"));
					stringBuffer.append(childString);
				}
			}
		}
		returnObject.add(stringBuffer);
		returnObject.add(getMapList);
		return returnObject;
	}
	
	private static StringBuffer buildTr(Map<String, Object> trMap , String status){
		String[] names = {"tree_name", "ysfy", "lj","YFSDZ","ZJJD","CQYE","yy","ey","sany","siy","wy","ly","qy","bay","jy","siyue","syy","sey","lrsp"};
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<tr>");
		for(int i = 0; i < names.length; i++){
			if("read".equals(status)){
				if("lj".equals(names[i])){
					stringBuffer.append("<td class='tr02' colspan='2'>").append(getValue(trMap, names[i])).append("</td>");
				}else{
					stringBuffer.append("<td class='tr02' >").append(getValue(trMap, names[i])).append("</td>");
				}
			}else if(i == 0){
				if("lj".equals(names[i])){
					stringBuffer.append("<td class='tr04' colspan='2'>").append(getValue(trMap, names[i])).append("</td>");
				}else{
					stringBuffer.append("<td class='tr04' >").append(getValue(trMap, names[i])).append("</td>");
				}
			}else{
				if("lj".equals(names[i])){
					stringBuffer.append("<td class='tr04' colspan='2' >").append("<input class='tr04' width=1%  type='text' ");
					stringBuffer.append("onchange='addzjlr(this); return false;' value='").append(getValue(trMap, names[i])).append("' id='lr@");
					stringBuffer.append(getValue(trMap, "status")).append("@").append(i).append("'/></td>");
				}else{
					stringBuffer.append("<td class='tr04' >").append("<input class='tr04' type='text' width=100%  ");
					stringBuffer.append("onchange='addzjlr(this); return false;' value='").append(getValue(trMap, names[i])).append("' id='lr@");
					stringBuffer.append(getValue(trMap, "status")).append("@").append(i).append("'/></td>");
				}
			}
		}
		stringBuffer.append("</tr>");
		return stringBuffer;
	}
	
	private static String getValue(Map<String, Object> map, String key){
		String value = String.valueOf(map.get(key));
		value = ("null".equals(value))?"0":value;
		return value;
	}
	
	

	/***************************************************************************
	 * view
	 */
	public static StringBuffer buildZjlr_sum(List<Map<String, Object>> list) {
		StringBuffer stringBuffer = new StringBuffer();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				stringBuffer.append("<tr><td width='200px' class='tr02'>"
						+ delNull(String.valueOf(list.get(i).get("lb")))
						+ " </td><td class='tr02'>"
						+ delNull(String.valueOf(list.get(i).get("ysfy")))
						+ " </td><td colspan='2' class='tr02'>"
						+ delNull(String.valueOf(list.get(i).get("lj")))
						+ " </td><td class='tr02'>"
						+ delNull(String.valueOf(list.get(i).get("YFSDZ")))
						+ " </td><td class='tr02' >"
						+ delNull(String.valueOf(list.get(i).get("ZJJD")))
						+ " </td><td class='tr02'>"
						+ delNull(String.valueOf(list.get(i).get("CQYE")))
						+ " </td><td class='tr02'>");
				for (int j = 0; j < items.length; j++) {
					stringBuffer.append(delNull(String.valueOf(list.get(i).get(
							items[j])))
							+ " </td><td class='tr02'>");
				}
				stringBuffer.append(delNull(String.valueOf(list.get(i).get(
						"LRSP")))
						+ "</td></tr>");
			}
		}
		return stringBuffer;

	}

	public static StringBuffer buildZjzc_father_sum(
			List<Map<String, Object>> list) {
		
		StringBuffer stringBuffer = new StringBuffer();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (i == 0) {
					stringBuffer.append("<tr><td  rowspan='8' class='tr03'>"
							+ delNull(String.valueOf(list.get(i).get("LB")))
							+ "</td><td rowspan='8' class='tr03'>"
							+ delNull(String.valueOf(list.get(i).get("YSFY")))
							+ "</td><td width:'200px' class='tr03'>"
							+ delNull(String.valueOf(list.get(i).get("LJ")))
							+ "</td><td  class='tr03'>"
							+ delZer(String.valueOf(list.get(i).get("JL2")))
							+ "</td><td rowspan='8' class='tr03'>"
							+ delNull(String.valueOf(list.get(i).get("YFSDZ")))
							+ "</td><td rowspan='8' class='tr03'>"
							+ delNull(String.valueOf(list.get(i).get("ZJJD")))
							+ "</td><td class='tr03'>"
							+ delNull(String.valueOf(list.get(i).get("CQYE")))
							+ "</td><td class='tr03'>");
					for (int j = 0; j < items.length; j++) {
						stringBuffer.append(delNull(String.valueOf(list.get(i)
								.get(items[j])))
								+ "</td><td class='tr03'>");
					}
					stringBuffer.append(delNull(String.valueOf(list.get(i).get(
							"LRSP")))
							+ "</td></tr>");
				} else {
					stringBuffer.append("<tr><td class='tr03'>"
							+ delNull(String.valueOf(list.get(i).get("LJ")))
							+ "</td><td class='tr03'>"
							+ delZer(String.valueOf(list.get(i).get("JL2")))
							+ "</td> <td class='tr03'>"
							+ delNull(String.valueOf(list.get(i).get("CQYE")))
							+ " </td><td class='tr03'>");
					for (int j = 0; j < items.length; j++) {
						stringBuffer.append(delNull(String.valueOf(list.get(i)
								.get(items[j])))
								+ " </td><td class='tr03'>");
					}

					stringBuffer.append(delNull(String.valueOf(list.get(i).get(
							"LRSP")))
							+ "</td></tr>");
				}
			}
		}

		return stringBuffer;
	}

	public static StringBuffer buildZjzc_father(List<Map<String, Object>> list,String rolename) {
		int month = Calendar.getInstance().get(Calendar.MONTH)+1;
		StringBuffer stringBuffer = new StringBuffer();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (i == 0) {
					stringBuffer
							.append("<tr><td  rowspan='8' class='tr04'>"
									+ delNull(String.valueOf(list.get(i).get(
											"LB")))
									+ "</td><td rowspan='8' class='tr04'><input class='tr04' type='text' style='width:70px;' onchange='addrzxq(this); return false' value='"
									+ delNull(String.valueOf(list.get(i).get(
											"YSFY")))
									+ "'id='"
									+ String.valueOf(list.get(i).get("status"))
									+ "@"
									+ String.valueOf(list.get(i).get("lb"))
									+ "@"
									+ String.valueOf(list.get(i).get("sort"))
									+ "@2'/></td><td width:'200px' class='tr04'>"
									+ delNull(String.valueOf(list.get(i).get(
											"LJ")))
									+ "</td><td  class='tr04'>"
									+ delZer(String.valueOf(list.get(i).get(
											"JL2")))
									+ "</td><td rowspan='8' class='tr04'><input class='tr04' type='text' style='width:70px;' onchange='addrzxq(this); return false' value='"
									+ delNull(String.valueOf(list.get(i).get(
											"YFSDZ")))
									+ "'id='"
									+ String.valueOf(list.get(i).get("status"))
									+ "@"
									+ String.valueOf(list.get(i).get("lb"))
									+ "@"
									+ String.valueOf(list.get(i).get("sort"))
									+ "@5'/></td><td rowspan='8' class='tr04'><input class='tr04' type='text' style='width:70px;' onchange='addrzxq(this); return false' value='"
									+ delNull(String.valueOf(list.get(i).get(
											"ZJJD")))
									+ "'id='"
									+ String.valueOf(list.get(i).get("status"))
									+ "@"
									+ String.valueOf(list.get(i).get("lb"))
									+ "@"
									+ String.valueOf(list.get(i).get("sort"))
									+ "@6'/></td><td class='tr04'><input class='tr04' type='text' style='width:70px;' onchange='addrzxq(this); return false' value='"
									+ delNull(String.valueOf(list.get(i).get(
											"CQYE"))) + "'id='"
									+ String.valueOf(list.get(i).get("status"))
									+ "@"
									+ String.valueOf(list.get(i).get("lb"))
									+ "@"
									+ String.valueOf(list.get(i).get("sort"))
									+ "@7'/></td>");
					for (int j = 0; j < items.length; j++) {
						if(j+1==month  ||  "资金部".equals(rolename) || "资金部部长".equals(rolename)){
						stringBuffer
								.append("<td class='tr04'><input class='tr04' type='text' style='width:70px;' onchange='addrzxq(this); return false' value='"
										+ delNull(String.valueOf(list.get(i)
												.get(items[j])))
										+ "'id='"
										+ String.valueOf(list.get(i).get(
												"status"))
										+ "@"
										+ String.valueOf(list.get(i).get("lb"))
										+ "@"
										+ String.valueOf(list.get(i)
												.get("sort"))
										+ "@"
										+ i
										+ 8
										+ "'/></td>");
						}else{
							stringBuffer
							.append("<td class='tr04'>"
									+ delNull(String.valueOf(list.get(i)
											.get(items[j])))
									+ "</td>");
						}
					}
					stringBuffer
							.append("<td class='tr04'><input class='tr04' type='text' style='width:70px;' onchange='addrzxq(this); return false' value='"
									+ delNull(String.valueOf(list.get(i).get(
											"LRSP")))
									+ "'id='"
									+ String.valueOf(list.get(i).get("status"))
									+ "@"
									+ String.valueOf(list.get(i).get("lb"))
									+ "@"
									+ String.valueOf(list.get(i).get("sort"))
									+ "@20'/></td></tr>");
				} else {
					stringBuffer
							.append("<tr><td class='tr04' width='200px'>"
									+
									// delNull(String.valueOf(list.get(i).get("YSFY")))+
									// "</td><td class='tr04'>"+
									delNull(String.valueOf(list.get(i)
											.get("LJ")))
									+ "</td><td class='tr04'>"
									+ delZer(String.valueOf(list.get(i).get(
											"JL2")))
									+ "</td>"
									+ "  <td class='tr04'><input class='tr04' type='text' style='width:70px;' onchange='addrzxq(this); return false' value='"
									+ delNull(String.valueOf(list.get(i).get(
											"CQYE"))) + "'id='"
									+ String.valueOf(list.get(i).get("status"))
									+ "@"
									+ String.valueOf(list.get(i).get("lb"))
									+ "@"
									+ String.valueOf(list.get(i).get("sort"))
									+ "@7'/></td>");
					for (int j = 0; j < items.length; j++) {
						if(j+1==month  ||  "资金部".equals(rolename) || "资金部部长".equals(rolename)){
						stringBuffer
								.append("<td class='tr04'><input class='tr04' type='text' style='width:70px;' onchange='addrzxq(this); return false' value='"
										+ delNull(String.valueOf(list.get(i)
												.get(items[j])))
										+ "'id='"
										+ String.valueOf(list.get(i).get(
												"status"))
										+ "@"
										+ String.valueOf(list.get(i).get("lb"))
										+ "@"
										+ String.valueOf(list.get(i)
												.get("sort"))
										+ "@"
										+ i
										+ 8
										+ "'/></td>");
						}else{
							stringBuffer
							.append("<td class='tr04'>"
									+ delNull(String.valueOf(list.get(i)
											.get(items[j])))
									+ "</td>");
						}
					}
					stringBuffer
							.append("<td class='tr04'><input class='tr04' type='text' style='width:70px;' onchange='addrzxq(this); return false' value='"
									+ delNull(String.valueOf(list.get(i).get(
											"LRSP")))
									+ "'id='"
									+ String.valueOf(list.get(i).get("status"))
									+ "@"
									+ String.valueOf(list.get(i).get("lb"))
									+ "@"
									+ String.valueOf(list.get(i).get("sort"))
									+ "@20'/></td></tr>");
				}
			}
		}

		return stringBuffer;
	}

	public static StringBuffer buildZjzc_father_view(
			List<Map<String, Object>> list) {
		StringBuffer stringBuffer = new StringBuffer();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (i == 0) {
					stringBuffer.append("<tr><td  rowspan='8' class='tr04'>"
							+ delNull(String.valueOf(list.get(i).get("LB")))
							+ "</td><td rowspan='8' class='tr04'>"
							+ delNull(String.valueOf(list.get(i).get("YSFY")))
							+ "</td><td width:'200px' class='tr04'>"
							+ delNull(String.valueOf(list.get(i).get("LJ")))
							+ "</td><td  class='tr04'>"
							+ delZer(String.valueOf(list.get(i).get("JL2")))
							+ "</td><td rowspan='8' class='tr04'>"
							+ delNull(String.valueOf(list.get(i).get("YFSDZ")))
							+ "</td><td rowspan='8' class='tr04'>"
							+ delNull(String.valueOf(list.get(i).get("ZJJD")))
							+ "</td><td class='tr04'>"
							+ delNull(String.valueOf(list.get(i).get("CQYE")))
							+ "</td><td class='tr04'>");
					for (int j = 0; j < items.length; j++) {
						stringBuffer.append(delNull(String.valueOf(list.get(i)
								.get(items[j])))
								+ "</td><td class='tr04'>");
					}

					stringBuffer.append(delNull(String.valueOf(list.get(i).get(
							"LRSP")))
							+ " </td></tr>");
				} else {
					stringBuffer.append("<tr><td class='tr04'>"
							+
							// delNull(String.valueOf(list.get(i).get("YSFY")))+
							// "</td><td class='tr04'>"+
							delNull(String.valueOf(list.get(i).get("LJ")))
							+ "</td><td class='tr04'>"
							+ delZer(String.valueOf(list.get(i).get("JL2")))
							+ "</td>" + " <td class='tr04'> "
							+ delNull(String.valueOf(list.get(i).get("CQYE")))
							+ "</td><td class='tr04'>");
					for (int j = 0; j < items.length; j++) {
						stringBuffer.append(delNull(String.valueOf(list.get(i)
								.get(items[j])))
								+ "</td><td class='tr04'>");
					}

					stringBuffer.append(delNull(String.valueOf(list.get(i).get(
							"LRSP")))
							+ " </td></tr>");
				}
			}
		}

		return stringBuffer;
	}

	public static StringBuffer buildZjzc_child(List<Map<String, Object>> list,String rolename) {
		int month = Calendar.getInstance().get(Calendar.MONTH)+1;
		StringBuffer stringBuffer = new StringBuffer();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (i == 0) {
					stringBuffer
							.append("<tr><td  rowspan='8'>"
									+ delNull(String.valueOf(list.get(i).get(
											"LB")))
									+ " </td><td rowspan='8'><input type='text' style='width:70px;' onchange='addrzxq(this); return false' value='"
									+ delNull(String.valueOf(list.get(i).get(
											"YSFY")))
									+ "'id='"
									+ String.valueOf(list.get(i).get("status"))
									+ "@"
									+ String.valueOf(list.get(i).get("lb"))
									+ "@"
									+ String.valueOf(list.get(i).get("sort"))
									+ "@2'/></td><td width='200px'>"
									+ delNull(String.valueOf(list.get(i).get(
											"LJ")))
									+ "</td><td><input type='text' style='width:70px;' onchange='addrzxq(this); return false' value='"
									+ delNull(String.valueOf(list.get(i).get(
											"JL2")))
									+ "'id='"
									+ String.valueOf(list.get(i).get("status"))
									+ "@"
									+ String.valueOf(list.get(i).get("lb"))
									+ "@"
									+ String.valueOf(list.get(i).get("sort"))
									+ "@4'/></td><td  ><input type='text' style='width:70px;' onchange='addrzxq(this); return false' value='"
									+ delNull(String.valueOf(list.get(i).get(
											"YFSDZ")))
									+ "'id='"
									+ String.valueOf(list.get(i).get("status"))
									+ "@"
									+ String.valueOf(list.get(i).get("lb"))
									+ "@"
									+ String.valueOf(list.get(i).get("sort"))
									+ "@5'/></td><td ><input type='text' style='width:70px;' onchange='addrzxq(this); return false' value='"
									+ delNull(String.valueOf(list.get(i).get(
											"ZJJD")))
									+ "'id='"
									+ String.valueOf(list.get(i).get("status"))
									+ "@"
									+ String.valueOf(list.get(i).get("lb"))
									+ "@"
									+ String.valueOf(list.get(i).get("sort"))
									+ "@6'/></td><td><input type='text' style='width:70px;' onchange='addrzxq(this); return false' value='"
									+ delNull(String.valueOf(list.get(i).get(
											"CQYE"))) + "'id='"
									+ String.valueOf(list.get(i).get("status"))
									+ "@"
									+ String.valueOf(list.get(i).get("lb"))
									+ "@"
									+ String.valueOf(list.get(i).get("sort"))
									+ "@7'/></td>");
					for (int j = 0; j < items.length; j++) {
						if(j+1 == month ||  "资金部".equals(rolename) || "资金部部长".equals(rolename)){
						stringBuffer
								.append("<td><input type='text' style='width:70px;' onchange='addrzxq(this); return false' value='"
										+ delNull(String.valueOf(list.get(i)
												.get(items[j])))
										+ "'id='"
										+ String.valueOf(list.get(i).get(
												"status"))
										+ "@"
										+ String.valueOf(list.get(i).get("lb"))
										+ "@"
										+ String.valueOf(list.get(i)
												.get("sort"))
										+ "@"
										+ i
										+ 8
										+ "'/></td>");
						}else{
							stringBuffer
							.append("<td>"
									+ delNull(String.valueOf(list.get(i)
											.get(items[j])))
									+ "</td>");
						}
					}
					stringBuffer
							.append("<td><input type='text' style='width:70px;' onchange='addrzxq(this); return false' value='"
									+ delNull(String.valueOf(list.get(i).get(
											"LRSP")))
									+ "'id='"
									+ String.valueOf(list.get(i).get("status"))
									+ "@"
									+ String.valueOf(list.get(i).get("lb"))
									+ "@"
									+ String.valueOf(list.get(i).get("sort"))
									+ "@20'/></td></tr>");
				} else {
					stringBuffer
							.append("<tr><td width='200px'>"
									+ delNull(String.valueOf(list.get(i).get(
											"LJ")))
									+ "</td>"
									+ "<td><input type='text' style='width:70px;' onchange='addrzxq(this); return false' value='"
									+ delNull(String.valueOf(list.get(i).get(
											"LJ2")))
									+ "'id='"
									+ String.valueOf(list.get(i).get("status"))
									+ "@"
									+ String.valueOf(list.get(i).get("lb"))
									+ "@"
									+ String.valueOf(list.get(i).get("sort"))
									+ "@4'/></td><td ><input type='text' style='width:70px;' onchange='addrzxq(this); return false' value='"
									+ delNull(String.valueOf(list.get(i).get(
											"YFSDZ")))
									+ "'id='"
									+ String.valueOf(list.get(i).get("status"))
									+ "@"
									+ String.valueOf(list.get(i).get("lb"))
									+ "@"
									+ String.valueOf(list.get(i).get("sort"))
									+ "@5'/></td><td ><input type='text' style='width:70px;' onchange='addrzxq(this); return false' value='"
									+ delNull(String.valueOf(list.get(i).get(
											"ZJJD")))
									+ "'id='"
									+ String.valueOf(list.get(i).get("status"))
									+ "@"
									+ String.valueOf(list.get(i).get("lb"))
									+ "@"
									+ String.valueOf(list.get(i).get("sort"))
									+ "@6'/></td><td><input type='text' style='width:70px;' onchange='addrzxq(this); return false' value='"
									+ delNull(String.valueOf(list.get(i).get(
											"CQYE"))) + "'id='"
									+ String.valueOf(list.get(i).get("status"))
									+ "@"
									+ String.valueOf(list.get(i).get("lb"))
									+ "@"
									+ String.valueOf(list.get(i).get("sort"))
									+ "@7'/></td>");
					for (int j = 0; j < items.length; j++) {
						if(j+1==month || "资金部".equals(rolename) || "资金部部长".equals(rolename)){
						stringBuffer
								.append("<td><input type='text' style='width:70px;' onchange='addrzxq(this); return false' value='"
										+ delNull(String.valueOf(list.get(i)
												.get(items[j])))
										+ "'id='"
										+ String.valueOf(list.get(i).get(
												"status"))
										+ "@"
										+ String.valueOf(list.get(i).get("lb"))
										+ "@"
										+ String.valueOf(list.get(i)
												.get("sort"))
										+ "@"
										+ i
										+ 8
										+ "'/></td>");
						}else{
							stringBuffer
							.append("<td>"
									+ delNull(String.valueOf(list.get(i)
											.get(items[j])))
									+ "</td>");
						}
					}
					stringBuffer
							.append("<td><input type='text' style='width:70px;' onchange='addrzxq(this); return false' value='"
									+ delNull(String.valueOf(list.get(i).get(
											"LRSP")))
									+ "'id='"
									+ String.valueOf(list.get(i).get("status"))
									+ "@"
									+ String.valueOf(list.get(i).get("lb"))
									+ "@"
									+ String.valueOf(list.get(i).get("sort"))
									+ "@20'/></td></tr>");
				}
			}
		}
		return stringBuffer;
	}

	public static StringBuffer buildZjzc_child_view(
			List<Map<String, Object>> list) {
		StringBuffer stringBuffer = new StringBuffer();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				if (i == 0) {
					stringBuffer.append("<tr><td  rowspan='8'>"
							+ delNull(String.valueOf(list.get(i).get("LB")))
							+ " </td><td rowspan='8'> "
							+ delNull(String.valueOf(list.get(i).get("YSFY")))
							+ "</td><td width='200px'>"
							+ delNull(String.valueOf(list.get(i).get("LJ")))
							+ "</td><td> "
							+ delNull(String.valueOf(list.get(i).get("JL2")))
							+ "</td><td  > "
							+ delNull(String.valueOf(list.get(i).get("YFSDZ")))
							+ "</td><td > "
							+ delNull(String.valueOf(list.get(i).get("ZJJD")))
							+ "</td><td> "
							+ delNull(String.valueOf(list.get(i).get("CQYE")))
							+ " </td><td> ");
					for (int j = 0; j < items.length; j++) {
						stringBuffer.append(delNull(String.valueOf(list.get(i)
								.get(items[j])))
								+ " </td><td> ");
					}

					stringBuffer.append(delNull(String.valueOf(list.get(i).get(
							"LRSP")))
							+ " </td></tr>");
				} else {
					stringBuffer.append("<tr><td width='200px'>"
							+ delNull(String.valueOf(list.get(i).get("LJ")))
							+ "</td><td> "
							+ delNull(String.valueOf(list.get(i).get("LJ2")))
							+ " </td><td > "
							+ delNull(String.valueOf(list.get(i).get("YFSDZ")))
							+ " </td><td > "
							+ delNull(String.valueOf(list.get(i).get("ZJJD")))
							+ " </td><td> "
							+ delNull(String.valueOf(list.get(i).get("CQYE")))
							+ " </td><td> ");
					for (int j = 0; j < items.length; j++) {
						stringBuffer.append(delNull(String.valueOf(list.get(i)
								.get(items[j])))
								+ " </td><td> ");
					}
					stringBuffer.append(delNull(String.valueOf(list.get(i).get(
							"LRSP")))
							+ " </td></tr>");
				}
			}
		}
		return stringBuffer;
	}

	public static String delNull(String str) {
		if (str.equals("null") || str.equals("0")) {
			return "";
		} else {
			return str;
		}
	}

	public static String delZer(String str) {
		if (str.equals("null") || str.equals("")) {
			return "0";
		} else {
			return str;
		}
	}

}
