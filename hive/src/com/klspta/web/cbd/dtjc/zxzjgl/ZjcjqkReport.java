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

public class ZjcjqkReport extends AbstractBaseBean implements IDataClass {
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
		String[][] firstTitle = {{"资金来源","200","1","1"},{"总额（亿元）","150","1","1"},{"已到位（亿元）","150","1","1"},{"未到位（亿元）","150","1","1"}};
		titleMap.put("1", firstTitle);
	}
	
	private List<TRBean> getTitle(){
		if(titleMap.isEmpty()){
			setTitle();
		}
		List<TRBean> trBeans = new ArrayList<TRBean>();
		for(int i = 0; i < titleMap.size(); i++){
			String key = String.valueOf(i + 1);
			TRBean trBean = new TRBean();
			trBean.setCssStyle("title");
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
		bodyList.add(getTrBean("1 筹融资金", formatData.get("CRZJ")));
		bodyList.add(getTrBean("1.1 金融机构贷款", formatData.get("ZRJGDK")));
		bodyList.add(getTrBean("1.2 实施主体带资", formatData.get("SSZTDZ")));
		bodyList.add(getTrBean("1.3 国有土地收益基金", formatData.get("GYTDSYJJ")));
		bodyList.add(getTrBean("2 出让回笼资金", formatData.get("CRHLZJ")));
		bodyList.add(getTrBean("3 其他资金", formatData.get("QTZJ")));
		TRBean trBean = getTrBean("合计", formatData.get("sum"));
		trBean.setCssStyle("total");
		bodyList.add(trBean);
		return bodyList;
	}
	
	private Map<String, Map<String, String>> formatData(String condition){
		Map<String, Map<String, String>> formatData = new TreeMap<String, Map<String,String>>();
		String sql = "select sum(t.ysfy) as ysfy, sum((t.yy + t.ey + t.sany + t.siy + t.wy + t.ly + t.qy + t.bay + t.jy + t.siy + t.syy + t.sey )) as he, t.lb, t.status from XMZJGL_LR t group by t.status, t.lb";
		List<Map<String, Object>> dwjeResult = query(sql, YW);
		for(int i = 0; i < dwjeResult.size(); i++){
			Map<String, Object> dwjeMap = dwjeResult.get(i);
			Map<String, String> tableMap = new HashMap<String, String>();
			String key = String.valueOf(dwjeMap.get("status"));
			String ysfy = String.valueOf(dwjeMap.get("ysfy"));
			ysfy = ("null".equals(ysfy) || ysfy == null)?"0":ysfy;
			String he = String.valueOf(dwjeMap.get("he"));
			he = ("null".equals(he) || he == null)?"0":he;
			String wdw = String.valueOf(Long.parseLong(ysfy) - Long.parseLong(he));
			tableMap.put("ysfy", ysfy);
			tableMap.put("he", he);
			tableMap.put("wdw", wdw);
			if(key.equals("CRZJ") || key.equals("ZRJGDK") || key.equals("SSZTDZ") || key.equals("GYTDSYJJ") || key.equals("CRHLZJ") || key.equals("QTZJ")){
				formatData.put(key, tableMap);
			}
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
	
	private TRBean getTrBean(String firstValue, Map<String, String> values){
		String[] names = {"ysfy","he","wdw"};
		TRBean trBean = new TRBean();
		trBean.setCssStyle("trSingle");
		trBean.addTDBean(new TDBean(firstValue,"200","20"));
		for(int i = 0; i < names.length; i++){
			if(values != null && values.containsKey(names[i])){
				trBean.addTDBean(new TDBean(values.get(names[i]), "100", "20"));
			}else{
				trBean.addTDBean(new TDBean( " ", "100", "20"));
			}
			
		}
		return trBean;
	}

}
