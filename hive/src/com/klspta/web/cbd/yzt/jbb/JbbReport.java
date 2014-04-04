package com.klspta.web.cbd.yzt.jbb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;
import com.sun.mail.imap.protocol.Status;

/**
 * 
 * <br>Title:基本斑详细列表
 * <br>Description:生成基本详细列表
 * <br>Author:黎春行
 * <br>Date:2013-12-18
 */
public class JbbReport extends AbstractBaseBean implements IDataClass {
	public static String[][] shows = new String[][]{{"dkmc","false"},{"zzsgm","false"},{"zzzsgm","false"},{"zzzshs","false"},{"hjmj","false"},{"fzzzsgm","false"},{"fzzjs","false"},{"zd","false"},{"jsyd","false"},{"rjl","false"},{"jzgm","false"},{"kzgd","false"},{"ghyt","false"},{"gjjzgm","false"},{"jzjzgm","false"},{"szjzgm","false"},{"kfcb","false"},{"lmcb","false"},{"dmcb","false"},{"yjcjj","false"},{"yjzftdsy","false"},{"cxb","false"},{"cqqd","false"},{"cbfgl","false"},{"ssqy","false"}};
	private String form_name = "JC_JIBEN";
	private String isEdit = "true";
	
	@Override
	public Map<String, TRBean> getTRBeans(Object[] obj, TRBean trBean) {
		Map<String, TRBean> trbeans = new TreeMap<String, TRBean>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		trbeans = getBody(obj);
		return trbeans;
	}

	public Map<String, TRBean> getBody(Object[] obj){
		Map<String, TRBean> trbeans = new TreeMap<String, TRBean>();
		Map<String, List<TRBean>> buildMap = new TreeMap<String, List<TRBean>>();
		if(obj.length > 1){
			trbeans.put("00", getSub((Map<String, Object>)obj[1]).get(0));
		}else{
			trbeans.put("00", getSub(null).get(0));
		}
		//添加所有纳入规划储备库数据
		buildMap.putAll(getByStatus(obj,"是"));
		Set<String> keySet = buildMap.keySet();
		for(String key : keySet){
			List<TRBean> trBeans = buildMap.get(key);
			for(int i = 0; i < trBeans.size(); i++){
				trbeans.put("11" + key+i, trBeans.get(i));
			}
		}
		//添加所有未纳入规划储备库数据
		buildMap.clear();
		keySet.clear();
		buildMap.putAll(getByStatus(obj,"否"));
		keySet = buildMap.keySet();
		for(String key : keySet){
			List<TRBean> trBeans = buildMap.get(key);
			for(int i = 0; i < trBeans.size(); i++){
				trbeans.put(key+i, trBeans.get(i));
			}
		}
		return trbeans;
	}
	
	private Set<String> getSubTotalKey(Object[] obj){
		Set<String> keySet = new TreeSet<String>();
		String sqlKey = "select substr(t.dkmc, 0,instr(t.dkmc,'_') - 1) dk from "+ form_name +" t ";

		sqlKey += " order by t.dkmc";
		List<Map<String, Object>> queryList = query(sqlKey, YW);
		for(int i = 0; i < queryList.size(); i++){
			String value = String.valueOf(queryList.get(i).get("dk"));
			keySet.add(value);
		}
		return keySet;
	}
	
	
	private List<TRBean> getSubTotal(String key,Object[] obj,String status){
		Map<String, Object> queryMap = new TreeMap<String, Object>();
		if(obj.length > 1){
			queryMap = (Map<String, Object>)obj[1];
			isEdit = (String)obj[0];
		}else{
			isEdit = (String)obj[0];
		}
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select ");
		for(int i = 1; i < shows.length - 1; i++){
			sqlBuffer.append("sum(t.").append(shows[i][0]).append(") as ").append(shows[i][0]).append(",");
		}
		//sqlBuffer.append("sum(t.").append(shows[shows.length - 1][0]).append(") as ").append(shows[shows.length - 1][0]).append(" from ");
		sqlBuffer.append("' ' as ").append(shows[shows.length - 1][0]).append(" from ");
		sqlBuffer.append(form_name).append(" t ");
		//if(obj != null){
		//	sqlBuffer.append("  where t.dkmc like '").append(key).append("_%' and t.ssqy like ? ");
		//}else{
		//	sqlBuffer.append("  where t.dkmc like '").append(key).append("_%'");
		//}
		if(!queryMap.isEmpty()){
			String query = String.valueOf(queryMap.get("query"));
			sqlBuffer.append(query).append(" and t.nrghcbk='").append(status).append("' and t.dkmc like '").append(key).append("%'");
		}else{
			sqlBuffer.append("where t.nrghcbk='").append(status).append("' and t.dkmc like '").append(key).append("%'");
		}
		sqlBuffer.append(" order by t.dkmc");
		List<Map<String, Object>> queryList = null;
		queryList = query(sqlBuffer.toString(), YW);
		Map<String, Object> map = queryList.get(0);
		TRBean trBean = new TRBean();
		trBean.setCssStyle("trtotal");
		TDBean tdtitle = new TDBean(key + "街区小计","130","20","false");
		trBean.addTDBean(tdtitle);
		for(int j = 1; j < shows.length; j++){
			String value = String.valueOf(map.get(shows[j][0]));
			if("null".equals(value)){
				value = "";
			}
			TDBean tdBean = new TDBean(value,"100","20","false");
			trBean.addTDBean(tdBean);
		}
		List<TRBean> returnList = new ArrayList<TRBean>();
		returnList.add(trBean);
		return returnList;
	}
	
	private Map<String, List<TRBean>> getByStatus(Object[] obj,String status){
		Map<String, Object> queryMap = new TreeMap<String, Object>();
		if(obj.length > 1){
			queryMap = (Map<String, Object>)obj[1];
			isEdit = (String)obj[0];
		}else{
			isEdit = (String)obj[0];
		}
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select ");
		for(int i = 0; i < shows.length - 1; i++){
			sqlBuffer.append("t.").append(shows[i][0]).append(",");
		}
		sqlBuffer.append("t.").append(shows[shows.length - 1][0]).append(" from ");
		sqlBuffer.append(form_name).append(" t ");
		if(!queryMap.isEmpty()){
			String query = String.valueOf(queryMap.get("query"));
			sqlBuffer.append(query).append(" and t.nrghcbk='").append(status).append("'");
		}else{
			sqlBuffer.append("where t.nrghcbk='").append(status).append("'");
		}
		sqlBuffer.append(" order by t.dkmc");
		List<Map<String, Object>> queryList = query(sqlBuffer.toString(), YW);
		Map<String, List<TRBean>> buildMap = new TreeMap<String, List<TRBean>>();
		for(int i = 0; i < queryList.size(); i++){
			Map map = queryList.get(i);
			String key = String.valueOf(map.get("dkmc"));
			//key = key.split("-")[0];
			key = key.substring(0,1);
			if("".equals(key)){
				continue;
			}
			List<TRBean> subTotal = buildMap.get(key);
			if(subTotal == null){
				subTotal = getSubTotal(key, obj,status);
			}
			TRBean trBean = new TRBean();
			trBean.setCssStyle("trsingle");
			for(int j = 0; j < shows.length; j++){
				String value = String.valueOf(map.get(shows[j][0]));
				TDBean tdBean;
				if("null".equals(value)){
					value = "";
				}
				if("true".equals(isEdit)){
					tdBean = new TDBean(value,"100","20", shows[j][1]);
				}else{
					tdBean = new TDBean(value,"100","20", "false");
				}
				trBean.addTDBean(tdBean);
			}
			subTotal.add(trBean);
			buildMap.remove(key);
			buildMap.put(key, subTotal);
		}
		return buildMap;
	}
	

	public List<TRBean> getSub(Map queryMap){
		String sql = "select sum(t.zzsgm) as zzsgm, sum(t.zzzsgm) as zzzsgm, sum(t.zzzshs) as zzzshs, sum(t.hjmj) as hjmj, sum(t.fzzzsgm) as fzzzsgm, sum(t.fzzjs) as fzzjs,sum(t.zd), sum(t.jsyd), sum(t.rjl), sum(t.jzgm),'' as kzgd, sum(t.ghyt), sum(t.gjjzgm), sum(t.jzjzgm), sum(t.szjzgm),sum(t.kfcb), sum(t.lmcb), sum(t.dmcb),sum(t.yjcjj),sum(t.yjzftdsy),sum(t.cxb),sum(t.cqqd),sum(t.cbfgl),''as cbfgl from jc_jiben t  ";
		if(queryMap != null && !queryMap.isEmpty()){
			sql += String.valueOf(queryMap.get("query"));
		}
		List<Map<String, Object>> queryList = query(sql.toString(), YW);
		List<TRBean> list = new ArrayList<TRBean>();
		TRBean trBean = new TRBean();
		trBean.setCssStyle("trtotal");
		Map<String, Object> map = queryList.get(0);
		TDBean tdname=new TDBean("合计","180","20");
		tdname.setColspan("1");
		trBean.addTDBean(tdname);
		for(int i = 1; i < shows.length; i++){
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
