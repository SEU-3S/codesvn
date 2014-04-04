package com.klspta.web.cbd.dtjc.zxzjgl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javassist.bytecode.annotation.ArrayMemberValue;

import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;
import com.sun.jndi.url.iiopname.iiopnameURLContextFactory;

public class Xmzxsyqk extends AbstractBaseBean implements IDataClass {
	private static Map<String, String[][]> titleMap = new TreeMap<String, String[][]>();
	
	@Override
	public Map<String, TRBean> getTRBeans(Object[] obj, TRBean trBean) {
		Map<String, TRBean> trbeans = new TreeMap<String, TRBean>();
		String condition = "";
		if(obj.length > 0){
			condition = String.valueOf(obj[0]);
		}
		List<TRBean> titleBeans = getTitle();
		List<TRBean> bodyBeans = getBody(condition);
		for(int i = 0; i < titleBeans.size(); i++){
			trbeans.put("00" + i , titleBeans.get(i));
		}
		for(int i = 0; i < bodyBeans.size(); i++){
			trbeans.put("01" + i, bodyBeans.get(i));
		}
		return trbeans;
	}
	
	private void setTitle(){
		String[][] firstTitle = {{"序号", "80", "1", "3"}, {"项目\\资金投向","150","1","3"},{"资金到位总额","100","1","3"},{"资金支出总额","820","10","1"},{"资金余额","80","1","1"}};
		String[][] secondTitle = {{"合计","100","1","2"},{"储备开发支出（亿元）","480","6","1"},{"储备开发成本外支出（亿元）","240","3","1"}};
		String[][] thirdTitle = {{"小计","80","1","1"},{"前期费用","80","1","1"},{"拆迁费用","80","1","1"},{"市政费用","80","1","1"},{"财务费用","80","1","1"},{"管理费","80","1","1"},
				{"小计","80","1","1"},{"筹融资金<br>返还","80","1","1"},{"其他支出","80","1","1"}};
		titleMap.put("1", firstTitle);
		titleMap.put("2", secondTitle);
		titleMap.put("3", thirdTitle);
	}
	
	private List<TRBean> getTitle(){
		if(titleMap.isEmpty()){
			setTitle();
		}
		List<TRBean> trBeans = new ArrayList<TRBean>();
		for(int i = 0; i < titleMap.size(); i++){
			String key = String.valueOf(i + 1);
			TRBean trBean = new TRBean();
			if(titleMap.containsKey(key)){
				String[][] titlefields = titleMap.get(key);
				for(int j = 0; j < titlefields.length; j++){
					TDBean tdBean = new TDBean(titlefields[j][0],titlefields[j][1],"20");
					tdBean.setColspan(titlefields[j][2]);
					tdBean.setRowspan(titlefields[j][3]);
					trBean.addTDBean(tdBean);
				}
			}
			trBeans.add(trBean);
		}
		return trBeans;
	}
	private List<TRBean> getBody(String condition){
		List<TRBean> bodyList = new ArrayList<TRBean>();
		Map<String, Map<String, String>> formatData = formatData(condition);
		int i = 0;
		for(String key : formatData.keySet()){
			i++;
			if("合 计".equals(key)){
				bodyList.add(getTrBean(key, String.valueOf(i), formatData.get(key)));
			}
		}
		TRBean trBean = getTrBean("合 计", String.valueOf(i), formatData.get("合 计"));
		trBean.setCssStyle("total");
		bodyList.add(trBean);
		
		return bodyList;
	}

	private Map<String, Map<String, String>> formatData(String condition){
		Map<String, Map<String, String>> formatData = new TreeMap<String, Map<String,String>>();
		//计算资金到位总额
		String sql = "select t.yw_guid, j.xmname, sum(t.yy + t.ey + t.sany + t.siy + t.wy + t.ly + t.qy + t.bay + t.jy + t.siyue + t.syy + t.sey) as he  from xmzjgl_lr t right join jc_xiangmu j on j.yw_guid = t.yw_guid group by t.yw_guid, j.xmname";
		List<Map<String, Object>> zjdw = query(sql, YW);
		for(int i = 0; i < zjdw.size(); i++){
			Map<String, Object> formatMap = zjdw.get(i);
			Map<String, String> tableMap = new HashMap<String, String>();
			String xmname = String.valueOf(formatMap.get("XMNAME"));
			String value = String.valueOf(formatMap.get("HE"));
			tableMap.put("zjdwze", value);
			formatData.put(xmname, tableMap);
		}
		
		//计算资金支出总额
		sql = "select t.status, t.lb, J.XMNAME, sum(t.yy) as he from XMZJGL_ZC t right join JC_XIANGMU J ON t.yw_guid = j.yw_guid  group by t.status, t.lb, t.yw_guid,j.xmname";
		List<Map<String, Object>> dwjeResult = query(sql, YW);
		for(int i = 0; i < dwjeResult.size(); i++){
			Map<String, Object> dwjeMap = dwjeResult.get(i);
			Map<String, String> xmMap;
			String key = String.valueOf(dwjeMap.get("status"));
			String value = String.valueOf(dwjeMap.get("he"));
			String xmname = String.valueOf(dwjeMap.get("xmname"));
			if(formatData.containsKey(xmname)){
				xmMap = formatData.get(xmname);
			}else{
				xmMap = new HashMap<String, String>();
			}
			xmMap.put(key, value);
			formatData.put(xmname, xmMap);
		}
		
		//添加合计总额
		for(String key : formatData.keySet()){
			Map<String, String> tableMap = formatData.get(key);
			String cbkfxjValue = sum(tableMap, new String[]{"QQFY","CQFY","SZFY","CWFY","GLFY"});
			String kfcbValue = sum(tableMap, new String[]{"CRZJFH","QTZC"});
			String zczeValue = String.valueOf(Long.parseLong(cbkfxjValue) + Long.parseLong(kfcbValue));
			String jdwze = tableMap.get("zjdwze");
			jdwze = ("null".equals(jdwze) || jdwze == null)?"0":jdwze;
			String zjye = String.valueOf(Long.parseLong(jdwze) - Long.parseLong(zczeValue));
			tableMap.put("kfxj", cbkfxjValue);
			tableMap.put("cbzcxj", kfcbValue);
			tableMap.put("hj", zczeValue);
			tableMap.put("zjye", zjye);
			formatData.put(key, tableMap);
		}
		
		//添加合计
		Map<String, String> countMap = new HashMap<String, String>();
		for(String key : formatData.keySet()){
			Map<String, String> leafMap = formatData.get(key);
			for(String leafkey : leafMap.keySet()){
				String value = "0";
				if(countMap.containsKey(leafkey)){
					long num = Long.parseLong(countMap.get(leafkey)==null||"null".equals(countMap.get(leafkey))?"0":countMap.get(leafkey)) + Long.parseLong(leafMap.get(leafkey)==null||"null".equals(leafMap.get(leafkey))?"0":leafMap.get(leafkey));
					value = String.valueOf(num);
				}else{
					value = leafMap.get(leafkey);
				}
				countMap.remove(leafkey);
				countMap.put(leafkey, value);
			}
		}
		formatData.put("合 计", countMap);
		return formatData;
	}
	
	private String sum(Map<String, String> calculate, String[] names){
		String sum = "0";
		for(int i = 0; i < names.length; i++){
			String key = names[i];
			String value = "0";
			if(calculate.containsKey(key)){
				value = calculate.get(key);
			}
			sum = String.valueOf(Long.parseLong(sum) + Long.parseLong(value));
		}
		return sum;
	}
	
	private TRBean getTrBean(String firstValue, String num, Map<String, String> values){
		String[] names = {"zjdwze","hj","kfxj","QQFY","CQFY","SZFY","CWFY","GLFY","cbzcxj","CRZJFH","QTZC","zjye"};
		TRBean trBean = new TRBean();
		trBean.setCssStyle("trSingle");
		trBean.addTDBean(new TDBean(num,"80","20"));
		trBean.addTDBean(new TDBean(firstValue,"200","20"));
		for(int i = 0; i < names.length; i++){
			trBean.addTDBean(new TDBean(values.get(names[i]), "100", "20"));
		}
		return trBean;
	}
}
