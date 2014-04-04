package com.klspta.web.cbd.dtjc.trzqk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.klspta.base.AbstractBaseBean;

/**
 * 
 * <br>
 * Title:投融资情况扩展类 <br>
 * Description:用于获取计算投融资情况的数据 <br>
 * Author:黎春行 <br>
 * Date:2013-11-8
 */
public class ExtendData extends AbstractBaseBean {

	public Map<String, String> getAzfjs(int minYear, int maxYear) {
		String sql = "select t.nd, t.jd, t.kgjgfl from hx_sx t where t.nd >=? and t.nd <= ? order by t.nd, t.jd";
		List<Map<String, Object>> recordList = query(sql, YW, new Object[] {
				minYear, maxYear });
		Map<String, String> rzxqMap = new HashMap<String, String>();
		for (Map<String, Object> recordMap : recordList) {
			String year = String.valueOf(recordMap.get("nd"));
			String quarter = String.valueOf(recordMap.get("jd"));
			String key = year + "#" + quarter;
			String value = String.valueOf(recordMap.get("kgjgfl"));
			value = value == "null" ? "" : value;
			rzxqMap.put(key, value);
		}
		return rzxqMap;
	}

	/**
	 * 
	 * <br>
	 * Description:获取特定年度和季度的供地体量成本总和 <br>
	 * Author:黎春行 <br>
	 * Date:2013-11-8
	 * 
	 * @param nd
	 * @param jd
	 * @return
	 */
	public String getgdcbTotal(String nd, String jd) {
		String sql = "select sum(t.cbz) as cbz from hx_gdtl t where t.nd = ? and t.jd= ?";
		List<Map<String, Object>> totalList = query(sql, YW, new Object[] { nd,
				jd });
		if (totalList.size() > 0) {
			return String.valueOf(totalList.get(0).get("cbz"));
		} else {
			return "0";
		}
	}

	/**
	 * 
	 * <br>
	 * Description:获取特定年度和季度的供地体量收益总和 <br>
	 * Author:黎春行 <br>
	 * Date:2013-11-8
	 * 
	 * @param nd
	 * @param jd
	 * @return
	 */
	public String getgdsyTotal(String nd, String jd) {
		String sql = "select sum(t.syz) as syz from hx_gdtl t where t.nd = ? and t.jd= ?";
		List<Map<String, Object>> totalList = query(sql, YW, new Object[] { nd,
				jd });
		if (totalList.size() > 0) {
			return String.valueOf(totalList.get(0).get("syz"));
		} else {
			return "0";
		}
	}

	/**
	 * 
	 * <br>
	 * Description:获取项目的评估土地价值、抵押率、融资损失参数 <br>
	 * Author:黎春行 <br>
	 * Date:2013-11-11
	 * 
	 * @return
	 */
	public Map<String, String> getParameters() {
		String sql = "select t.key, to_number(t.value) value from jc_canshu t";
		List<Map<String, Object>> parametersList = query(sql, YW);
		Map<String, String> parameters = new HashMap<String, String>();
		for (Map parameterMap : parametersList) {
			if ("xm_pgtdjz".equals(String.valueOf(parameterMap.get("key")))) {
				parameters.put("pgtdjz", String.valueOf(parameterMap
						.get("value")));
			} else if ("xm_dyl".equals(String.valueOf(parameterMap.get("key")))) {
				parameters
						.put("dyl", String.valueOf(parameterMap.get("value")));
			} else if ("xm_rzss"
					.equals(String.valueOf(parameterMap.get("key")))) {
				parameters.put("rzss", String
						.valueOf(parameterMap.get("value")));
			}
		}
		return parameters;
	}

	/**
	 * 
	 * <br>
	 * Description:获取特定时间段内的本期融资需求 <br>
	 * Author:黎春行 <br>
	 * Date:2013-11-13
	 * 
	 * @param minYear
	 * @param maxYear
	 * @return
	 */
	public Map<String, String> getBqrzxq(int minYear, int maxYear) {
		String sql = "select t.nd, t.jd, t.bqrzxq from hx_sx t where t.nd >=? and t.nd <= ? order by t.nd, t.jd";
		List<Map<String, Object>> recordList = query(sql, YW, new Object[] {
				minYear, maxYear });
		Map<String, String> rzxqMap = new HashMap<String, String>();
		for (Map<String, Object> recordMap : recordList) {
			String year = String.valueOf(recordMap.get("nd"));
			String quarter = String.valueOf(recordMap.get("jd"));
			String key = year + "#" + quarter;
			String value = String.valueOf(recordMap.get("bqrzxq"));
			value = value == "null" ? "" : value;
			rzxqMap.put(key, value);
		}
		return rzxqMap;
	}

	public Map<String, String> getKG(int minyear, int maxyaer,Map<String,Object> map) {
		String sql = "select t.nd, t.jd, t.kg from hx_azf t where t.nd >=? and t.nd <= ? and t.xmmc=? and t.tzmc=? order by t.nd, t.jd";
		List<Map<String, Object>> recordList = query(sql, YW, new Object[] {
				minyear, maxyaer,map.get("XMMC").toString(),map.get("TZMC").toString() });
		Map<String, String> rzxqMap = new HashMap<String, String>();
		for (Map<String, Object> recordMap : recordList) {
			String year = String.valueOf(recordMap.get("nd"));
			String quarter = String.valueOf(recordMap.get("jd"));
			String key = year + "#" + quarter;
			String value = String.valueOf(recordMap.get("kg"));
			value = value == "null" ? "" : value;
			rzxqMap.put(key, value);
		}
		return rzxqMap;
	}

	public Map<String, String> getTZ(int minyear, int maxyaer,Map<String,Object> map) {
		String sql = "select t.nd, t.jd, t.tz from hx_azf t where t.nd >=? and t.nd <= ? and t.xmmc=? and t.tzmc=? order by t.nd, t.jd";
		List<Map<String, Object>> recordList = query(sql, YW, new Object[] {
				minyear, maxyaer ,map.get("XMMC").toString(),map.get("TZMC").toString()});
		Map<String, String> rzxqMap = new HashMap<String, String>();
		for (Map<String, Object> recordMap : recordList) {
			String year = String.valueOf(recordMap.get("nd"));
			String quarter = String.valueOf(recordMap.get("jd"));
			String key = year + "#" + quarter;
			String value = String.valueOf(recordMap.get("tz"));
			value = value == "null" ? "" : value;
			rzxqMap.put(key, value);
		}
		return rzxqMap;
	}

	/**
	 * 
	 * <br>
	 * Description:添加融资需求 <br>
	 * Author:黎春行 <br>
	 * Date:2013-11-13
	 * 
	 * @param year
	 * @param quarter
	 * @param value
	 */
	public void addRzxq(String year, String quarter, String value) {
		StringBuffer sql = new StringBuffer();
		sql.append("merge into hx_sx j using(select distinct '");
		sql
				.append(year)
				.append("' as nd,'")
				.append(quarter)
				.append(
						"' as jd from hx_sx t ) t2 on (j.nd=t2.nd and j.jd = t2.jd) when matched then ");
		sql
				.append("update set j.bqrzxq=round(to_number(?),2) when not matched then insert(j.nd, j.jd, j.bqrzxq)");
		sql.append(" values(?, ?, round(to_number(?),2))");
		update(sql.toString(), YW, new Object[] { value, year, quarter, value });
	}

	public void addKG(String year, String quarter, String value,String xmmc,String tzmc) {
		String sql = "select * from hx_azf where nd=? and jd=? and xmmc=? and tzmc=?";
		List<Map<String,Object>> azflist = query(sql, YW, new Object[]{year,quarter,xmmc,tzmc});
		if(azflist.size()>0){
			sql="update hx_azf set kg=? where nd=? and jd=? and xmmc=? and tzmc=?";
		}else{
			sql = "insert into hx_azf (kg,nd,jd,xmmc,tzmc) values (?,?,?,?,?)";
		}
		update(sql,YW,new Object[]{value,year,quarter,xmmc,tzmc});
	}

	public void addTZ(String year, String quarter, String value,String xmmc,String tzmc) {
		String sql = "select * from hx_azf where nd=? and jd=? and xmmc=? and tzmc=?";
		List<Map<String,Object>> azflist = query(sql, YW, new Object[]{year,quarter,xmmc,tzmc});
		if(azflist.size()>0){
			sql="update hx_azf set tz=? where nd=? and jd=? and xmmc=? and tzmc=?";
		}else{
			sql = "insert into hx_azf (tz,nd,jd,xmmc,tzmc) values(?,?,?,?,?)";
		}
		update(sql,YW,new Object[]{value,year,quarter,xmmc,tzmc});
	}

	/**
	 * 
	 * <br>
	 * Description:获取特定时间段内的权益性资金注入 <br>
	 * Author:黎春行 <br>
	 * Date:2013-11-13
	 * 
	 * @param minYear
	 * @param maxYear
	 * @return
	 */
	public Map<String, String> getQyxzjzr(int minYear, int maxYear) {
		String sql = "select t.nd, t.jd, t.qyxzjzr from hx_sx t where t.nd >=? and t.nd <= ? order by t.nd, t.jd";
		List<Map<String, Object>> recordList = query(sql, YW, new Object[] {
				minYear, maxYear });
		Map<String, String> zjzrMap = new HashMap<String, String>();
		for (Map<String, Object> recordMap : recordList) {
			String year = String.valueOf(recordMap.get("nd"));
			String quarter = String.valueOf(recordMap.get("jd"));
			String key = year + "#" + quarter;
			String value = String.valueOf(recordMap.get("qyxzjzr"));
			value = value == "null" ? "" : value;
			zjzrMap.put(key, value);
		}
		return zjzrMap;
	}

	/**
	 * 
	 * <br>
	 * Description:添加权益性资金注入 <br>
	 * Author:黎春行 <br>
	 * Date:2013-11-13
	 * 
	 * @param year
	 * @param quarter
	 * @param value
	 */
	public void addQyxzjzr(String year, String quarter, String value) {
		StringBuffer sql = new StringBuffer();
		sql.append("merge into hx_sx j using(select distinct '");
		sql
				.append(year)
				.append("' as nd,'")
				.append(quarter)
				.append(
						"' as jd from hx_sx t ) t2 on (j.nd=t2.nd and j.jd = t2.jd) when matched then ");
		sql
				.append("update set j.qyxzjzr=round(to_number(?),2) when not matched then insert(j.nd, j.jd, j.qyxzjzr)");
		sql.append(" values(?, ?, round(to_number(?),2))");
		update(sql.toString(), YW, new Object[] { value, year, quarter, value });
	}

	public void addAZFProject(String xmmc, String tzmc) {
		
	}

}
