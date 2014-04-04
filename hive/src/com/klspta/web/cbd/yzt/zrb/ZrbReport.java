package com.klspta.web.cbd.yzt.zrb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;

public class ZrbReport extends AbstractBaseBean implements IDataClass {
	public static String[][] shows = new String[][]{{"yw_guid","false"},{"zrbbh","true"},{"zdmj","true"},{"lzmj","true"},{"cqgm","true"},{"zzlzmj","true"},{"zzcqgm","true"},{"yjhs","true"},{"fzzlzmj","true"},{"fzzcqgm","true"},{"bz","true"}};
	private String form_name = "JC_ZIRAN";
	private String isEdit = "true";
	
	@Override
	public Map<String, TRBean> getTRBeans(Object[] obj, TRBean trBean) {
		Map<String, TRBean> trbeans = new TreeMap<String, TRBean>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		if(obj.length > 1){
			queryMap = (Map<String, Object>)obj[1];
			isEdit = (String)obj[0];
		}else{
			isEdit = (String)obj[0];
		}
		List<TRBean> trbeanList = getBody(queryMap);
		for(int i = 0; i < trbeanList.size(); i++){
			String key = String.valueOf(i);
			key = "000" + i;
			key = key.substring(key.length() - 3, key.length());
			trbeans.put(key, trbeanList.get(i));
		}
		trbeans.put("999", getSub(queryMap).get(0));
		return trbeans;
	}
	
	public List<TRBean> getBody(Map queryMap){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select ");
		for(int i = 0; i < shows.length - 1; i++){
			sqlBuffer.append("t.").append(shows[i][0]).append(",");
		}
		sqlBuffer.append("t.").append(shows[shows.length - 1][0]).append(" from ");
		sqlBuffer.append(form_name).append(" t ");
		if(queryMap != null && !queryMap.isEmpty()){
			sqlBuffer.append(String.valueOf(queryMap.get("query")));
		}
		sqlBuffer.append(" order by t.zrbbh");
		List<Map<String, Object>> queryList = query(sqlBuffer.toString(), YW);
		List<TRBean> list = new ArrayList<TRBean>();
		for(int num = 0; num < queryList.size(); num++){
			TRBean trBean = new TRBean();
			trBean.setCssStyle("trsingle");
			Map<String, Object> map = queryList.get(num);
			trBean.addTDBean(new TDBean(String.valueOf(num + 1),"80", "20"));
			for(int i = 1; i < shows.length; i++){
				String value = String.valueOf(map.get(shows[i][0]));
				TDBean tdBean;
				if("null".equals(value)){
					value = "";
				}
				if("true".equals(isEdit)){
					tdBean = new TDBean(value, "80", "20",shows[i][1]);
				}else{
					tdBean = new TDBean(value, "80", "20","false");
				}
				trBean.addTDBean(tdBean);
			}
			list.add(trBean);
		}
		return list;
	}
	
	public List<TRBean> getSub(Map queryMap){
		String sql = "select sum(t.zdmj) as zdmj, sum(t.lzmj) as lzmj, sum(t.cqgm) as cqgm, sum(t.zzlzmj) as zzlzmj, sum(t.zzcqgm) as zzcqgm, sum(t.yjhs) as yjhs, sum(t.fzzlzmj) as fzzlzmj, sum(t.fzzcqgm) as fzzcqgm, '0' as bz from jc_ziran t ";
		if(queryMap != null && !queryMap.isEmpty()){
			sql += String.valueOf(queryMap.get("query"));
		}
		List<Map<String, Object>> queryList = query(sql.toString(), YW);
		List<TRBean> list = new ArrayList<TRBean>();
		TRBean trBean = new TRBean();
		trBean.setCssStyle("trtotal");
		Map<String, Object> map = queryList.get(0);
		TDBean tdname=new TDBean("合计","180","20");
		tdname.setColspan("2");
		trBean.addTDBean(tdname);
		for(int i = 2; i < shows.length; i++){
			String value = String.valueOf(map.get(shows[i][0]));
			if("null".equals(value)){
				value = "";
			}
			TDBean tdBean = new TDBean(value, "80", "20","false");
			trBean.addTDBean(tdBean);
		}
		list.add(trBean);
		return list;
	}
}
