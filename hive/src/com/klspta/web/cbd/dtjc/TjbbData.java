package com.klspta.web.cbd.dtjc;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.klspta.base.AbstractBaseBean;
import com.klspta.web.cbd.dtjc.trzqk.ExtendData;

/**
 * 
 * <br>Title:统计报表数据处理类
 * <br>Description:接受管理类的请求，增、删、改、查统计报表类数据
 * <br>Author:黎春行
 * <br>Date:2013-10-29
 */
public class TjbbData extends AbstractBaseBean {
	/*
	 * 缓存，存储已制定计划的最大年份
	 */
	private static String max_year = "0";
	private static String formSX = "HX_SX" ;
	private  Map<String, Map<String, Object>> kftlPlan = new TreeMap<String, Map<String,Object>>();
	private  Map<String, Map<String, Object>> gdtlPlan = new TreeMap<String, Map<String,Object>>();

	/**
	 * 
	 * <br>Description:获取一定时间段内安置房建设
	 * <br>Author:李国明
	 * <br>Date:2013-11-21
	 * @param minYear
	 * @param maxYear
	 * @return
	 */
	public Map<String,String> getKG(int minYear,int maxYear,Map<String,Object> map){
		return new ExtendData().getKG(minYear, maxYear,map);
	}
	
	public Map<String,String> getTZ(int minYear,int maxYear,Map<String,Object> map){
		return new ExtendData().getTZ(minYear, maxYear,map);
	}
	
	
	/**
	 * 
	 * <br>Description:获取统计报表中的最大年度
	 * <br>Author:黎春行
	 * <br>Date:2013-10-29
	 */
	public String getMaxYear(){
		if("0" == max_year){
			StringBuffer sql = new StringBuffer();
			sql.append("select max(t.nd) as nd from ").append(formSX).append(" t");
			List<Map<String, Object>> resultList = query(sql.toString(), YW);
			max_year = String.valueOf(resultList.get(0).get("nd"));
		}
		return max_year;
	}
	
	/**
	 * 
	 * <br>Description:获取所有开发体量计划项目
	 * <br>Author:黎春行
	 * <br>Date:2013-10-29
	 * @return
	 */
	public Set<String> getKFTLProject(){
		Set<String> projectSet = new TreeSet<String>();
		String projctsSql = "select distinct t.xmname from jc_xiangmu t order by t.xmname";
		List<Map<String, Object>> resultList = query(projctsSql, YW);
		for(int i = 0; i < resultList.size(); i++){
			projectSet.add(String.valueOf(resultList.get(i).get("xmname")));
		}
		return projectSet;
	}
	
	/**
	 * 
	 * <br>Description:获取所有供地体量计划项目
	 * <br>Author:黎春行
	 * <br>Date:2013-10-29
	 * @return
	 */
	public Set<String> getGDTLProject(){
		Set<String> projectSet = new TreeSet<String>();
		String projctsSql = "select distinct t.xmname from jc_xiangmu t order by t.xmname";
		List<Map<String, Object>> resultList = query(projctsSql, YW);
		for(int i = 0; i < resultList.size(); i++){
			projectSet.add(String.valueOf(resultList.get(i).get("xmname")));
		}
		return projectSet;
	}
	
	public List<Map<String, Object>> getAzfProject(){
		String projctsSql = "select distinct( t.xmmc),t.tzmc from hx_azf t order by t.xmmc";
		List<Map<String, Object>> resultList = query(projctsSql, YW);
		return resultList;
	}
	
	public Map<String, Map<String, Object>> getKFTLPlan(){
		String sqlString = "select * from hx_kftl t order by t.nd, t.jd ";
		List<Map<String, Object>> sxList = query(sqlString, YW);
		for(Map<String, Object> kftl : sxList){
			String xmmc = String.valueOf(kftl.get("xmmc"));
			if(kftlPlan.containsKey(xmmc)){
				Map<String, Object> planMap = kftlPlan.get(xmmc);
				String year = String.valueOf(kftl.get("nd"));
				String quarter = String.valueOf(kftl.get("jd"));
				String key = year + "##" + quarter;
				planMap.put(key, kftl);
				kftlPlan.put(xmmc, planMap);
			}else{
				Map<String, Object> planMap = new TreeMap<String, Object>();
				String year = String.valueOf(kftl.get("nd"));
				String quarter = String.valueOf(kftl.get("jd"));
				String key = year + "##" + quarter;
				planMap.put(key, kftl);
				kftlPlan.put(xmmc, planMap);
			}
		} 
		return kftlPlan;
	}
	
	public Map<String, Map<String, Object>> getGDTLPlan(){
		String sqlString = "select * from hx_gdtl t order by t.nd, t.jd ";
		List<Map<String, Object>> sxList = query(sqlString, YW);
		for(Map<String, Object> gdtl : sxList){
			String xmmc = String.valueOf(gdtl.get("xmmc"));
			if(gdtlPlan.containsKey(xmmc)){
				Map<String, Object> planMap = gdtlPlan.get(xmmc);
				String year = String.valueOf(gdtl.get("nd"));
				String quarter = String.valueOf(gdtl.get("jd"));
				String key = year + "##" + quarter;
				planMap.put(key, gdtl);
				gdtlPlan.put(xmmc, planMap);
			}else{
				Map<String, Object> planMap = new TreeMap<String, Object>();
				String year = String.valueOf(gdtl.get("nd"));
				String quarter = String.valueOf(gdtl.get("jd"));
				String key = year + "##" + quarter;
				planMap.put(key, gdtl);
				gdtlPlan.put(xmmc, planMap);
			}
		} 
		return gdtlPlan;
	}
	
	/**
	 * 
	 * <br>Description:修改时序信息
	 * <br>Author:黎春行
	 * <br>Date:2013-10-30
	 * @param formName
	 * @param setValues
	 * @param conditions
	 * @return
	 */
	public int changeQuarter(String formName, Map<String, String> setValues, Map<String, String> conditions){
		StringBuffer sql = new StringBuffer();
		sql.append("update ").append(formName).append(" t set ");
		Set<String> nameSet = setValues.keySet();
		Set<String> conditonSet = conditions.keySet();
		for(String name : nameSet ){
			sql.append("t.").append(name).append("=").append("'").append(setValues.get(name)).append("' ,");
		}
		int length = sql.length();
		sql.deleteCharAt(length - 1);
		sql.append("where 1=1  ");
		for(String conditon : conditonSet){
			sql.append(" and t.").append(conditon).append("=").append("'").append(conditions.get(conditon)).append("'");
		}
		
		return update(sql.toString(), YW);
	}

	/**
	 * 
	 * <br>Description:根据用户userId确定用户上次处理的项目和年度
	 * <br>Author:黎春行
	 * <br>Date:2013-11-5
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getPlanByUserId(String userId){
		String sql = "select t.* from user_projects t where t.userid = ?";
		return query(sql, YW, new Object[]{userId});
	}
	
	public String saveProjectsByUserid(String userId, String minyear, String maxyear, String projects){
		String sql = "merge into user_projects t using (select id as userid  from core.core_users where id = ?)  s  on (t.userid = s.userid) when matched then update set t.minyear = ?, t.maxyear = ?, t.projects = ? when not matched then insert(t.userid, t.minyear, t.maxyear,t.projects) values (?,?,?,?)";
		update(sql, YW, new Object[]{userId, minyear, maxyear, projects, userId, minyear, maxyear, projects});
		return null;
	}
	
	/**
	 * 
	 * <br>Description:获取一定时间段内的本期融资需求
	 * <br>Author:黎春行
	 * <br>Date:2013-11-13
	 * @param minYear
	 * @param maxYear
	 * @return
	 */
	public Map<String, String> getBqrzxq(int minYear, int maxYear){
		return new ExtendData().getBqrzxq(minYear, maxYear);
	}
	/**
	 * 
	 * <br>Description:获取一定时间段内的权益性资金注入
	 * <br>Author:黎春行
	 * <br>Date:2013-11-13
	 * @param minYear
	 * @param maxYear
	 * @return
	 */
	public Map<String, String> getQyxzjzr(int minYear, int maxYear){
		return new ExtendData().getQyxzjzr(minYear, maxYear);
	}
	
	/**
	 * 
	 * <br>Description:添加融资需求
	 * <br>Author:黎春行
	 * <br>Date:2013-11-13
	 * @param year
	 * @param quarter
	 * @param value
	 */
	public void addRzxq(String year, String quarter, String value){
		new ExtendData().addRzxq(year, quarter, value);
	}
	
	public void addKG(String year, String quarter, String value,String xmmc,String tzmc){
		new ExtendData().addKG(year, quarter, value,xmmc,tzmc);
	}
	
	public void addTZ(String year, String quarter, String value,String xmmc,String tzmc){
		new ExtendData().addTZ(year, quarter, value,xmmc,tzmc);
	}
	
	/**
	 * 
	 * <br>Description:添加权益性资金注入
	 * <br>Author:黎春行
	 * <br>Date:2013-11-13
	 * @param year
	 * @param quarter
	 * @param value
	 */
	public void addQyxzjzr(String year, String quarter, String value){
		new ExtendData().addQyxzjzr(year, quarter, value);
	}

	public void savaAZFProject(String xmmc, String tzmc) {
		new ExtendData().addAZFProject(xmmc, tzmc);
	}
	
}
