package com.klspta.web.cbd.dtjc.zxzjgl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;

public class ZxsyqkReport extends AbstractBaseBean implements IDataClass {
	
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
		String[][] firstTitle = {{"资金来源\\资金投向", "200", "1", "3"}, {"资金到位总额","150","1","3"},{"资金支出总额","1000","10","1"},{"资金余额","100","1","3"}};
		String[][] secondTitle = {{"合计","100","1","2"},{"储备开发支出（亿元）","600","6","1"},{"储备开发成本外支出（亿元）","300","3","1"}};
		String[][] thirdTitle = {{"小计","100","1","1"},{"前期费用","100","1","1"},{"拆迁费用","100","1","1"},{"市政费用","100","1","1"},{"财务费用","100","1","1"},{"管理费","100","1","1"},
				{"小计","100","1","1"},{"筹融资金<br>返还","100","1","1"},{"其他支出","100","1","1"}};
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
		bodyList.add(getTrBean("金融机构贷款", formatData.get("ZRJGDK")));
		bodyList.add(getTrBean("实施主体带资", formatData.get("SSZTDZ")));
		bodyList.add(getTrBean("国有土地收益基金", formatData.get("GYTDSYJJ")));
		bodyList.add(getTrBean("出让回笼资金", formatData.get("CRHLZJ")));
		bodyList.add(getTrBean("其他资金", formatData.get("QTZJ")));
		TRBean trBean = getTrBean("合计", formatData.get("sum"));
		trBean.setCssStyle("total");
		bodyList.add(trBean);
		
		return bodyList;
	}
	
	private Map<String, Map<String, String>> formatData(String condition){
		Map<String, Map<String, String>> formatData = new TreeMap<String, Map<String,String>>();
		
		//添加资金到位总额
		String dwjeSql = "select sum((t.yy + t.ey + t.sany + t.siy + t.wy + t.ly + t.qy + t.bay + t.jy + t.siy + t.syy + t.sey )) as he , t.status, t.lb from XMZJGL_LR t where t.rq=? group by t.status, t.lb";
		List<Map<String, Object>> dwjeResult = query(dwjeSql, YW,new Object[]{condition});
		for(int i = 0; i < dwjeResult.size(); i++){
			Map<String, Object> dwjeMap = dwjeResult.get(i);
			Map<String, String> tableMap = new HashMap<String, String>();
			String key = String.valueOf(dwjeMap.get("status"));
			String value = String.valueOf(dwjeMap.get("he"));
			tableMap.put("zjdwze", value);
			if(key.equals("CRHLZJ") || key.equals("ZRJGDK") || key.equals("GYTDSYJJ") || key.equals("QTZJ") || key.equals("SSZTDZ")){
				formatData.put(key, tableMap);
			}
		}
		
		//添加资金支出总额
		String zczeSql = "select t.status, t.lj , sum(t.yy) as he from XMZJGL_ZC t where t.rq=?  group by t.status, t.lj";
		List<Map<String, Object>> zczeResult = query(zczeSql, YW,new Object[]{condition});
		for(int i = 0; i < zczeResult.size(); i++){
			Map<String, Object> zczeMap = zczeResult.get(i);
			String zjlyType = String.valueOf(zczeMap.get("lj"));
			
			String mainKey = "";
			//确定类型
			if("出让回笼资金审批".equals(zjlyType)){
				mainKey = "CRHLZJ";
			}else if("贷款审批".equals(zjlyType)){
				mainKey = "ZRJGDK";
			}else if("国有土地收益基金审批".equals(zjlyType)){
				mainKey = "GYTDSYJJ";
			}else if("其他资金审批".equals(zjlyType)){
				mainKey = "QTZJ";
			}else if("实施主体带资审批".equals(zjlyType)){
				mainKey = "SSZTDZ";	
			}
			if(!"".equals(mainKey)){
				Map<String, String> tableMap;
				if(formatData.containsKey(mainKey)){
					tableMap = formatData.get(mainKey);
				}else{
					tableMap = new HashMap<String, String>();
				}
				tableMap.put(String.valueOf(zczeMap.get("status")), String.valueOf(zczeMap.get("he")));
				formatData.remove(mainKey);
				formatData.put(mainKey, tableMap);
			}
		}
		
		//添加合计总额
		for(String key : formatData.keySet()){
			Map<String, String> tableMap = formatData.get(key);
			//添加储备开发支出小计
			String cbkfxjValue = sum(tableMap, new String[]{"QQFY","CQFY","SZFY","CWFY","GLFY"});
			//添加开发成本外支出（亿元）
			String kfcbValue = sum(tableMap, new String[]{"CRZJFH","QTZC"});
			//添加资金支出总额
			String zczeValue = String.valueOf(Long.parseLong(cbkfxjValue) + Long.parseLong(kfcbValue));
			//计算资金余额
			String jdwze = tableMap.get("zjdwze");
			jdwze = ("null".equals(jdwze) || jdwze == null)?"0":jdwze;
			String zjye = String.valueOf(Long.parseLong(jdwze) - Long.parseLong(zczeValue));
			tableMap.put("cbkfzcxj", cbkfxjValue);
			tableMap.put("cbkfcbwxj", kfcbValue);
			tableMap.put("zjzcze", zczeValue);
			tableMap.put("zjye", zjye);
			//formatData.remove(key);
			formatData.put(key, tableMap);
		}
		
		//添加合计
		Map<String, String> countMap = new HashMap<String, String>();
		for(String key : formatData.keySet()){
			Map<String, String> leafMap = formatData.get(key);
			for(String leafkey : leafMap.keySet()){
				String value = "0";
				if(countMap.containsKey(leafkey)){
					value = String.valueOf(Long.parseLong(countMap.get(leafkey)) + Long.parseLong(leafMap.get(leafkey)));
				}
				countMap.remove(leafkey);
				countMap.put(leafkey, value);
			}
		}
		formatData.put("sum", countMap);
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
	
	private TRBean getTrBean(String firstValue, Map<String, String> values){
		String[] names = {"zjdwze","zjzcze","cbkfzcxj","QQFY","CQFY","SZFY","CWFY","GLFY","cbkfcbwxj","CRZJFH","QTZC","zjye"};
		TRBean trBean = new TRBean();
		trBean.setCssStyle("trSingle");
		trBean.addTDBean(new TDBean(firstValue,"200","20"));
		for(int i = 0; i < names.length; i++){
			if(values != null && values.containsKey(names[i])){
				trBean.addTDBean(new TDBean(values.get(names[i]), "100", "20"));
			}else{
				trBean.addTDBean(new TDBean( "0 ", "100", "20"));
			}
		}
		return trBean;
	}

}
