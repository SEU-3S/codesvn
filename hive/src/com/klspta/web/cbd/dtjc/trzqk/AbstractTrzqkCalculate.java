package com.klspta.web.cbd.dtjc.trzqk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.klspta.base.AbstractBaseBean;
/**
 * 
 * <br>Title:投融资情况计算类
 * <br>Description:抽象类，用户计算投融资情况
 * <br>Author:黎春行
 * <br>Date:2013-11-8
 */
public abstract class AbstractTrzqkCalculate extends AbstractBaseBean {
	public AbstractTrzqkCalculate() {
		super();
		getRecord();
		spanYear = getYears();
	}

	private String[] types = new String[]{"bqtzxq","bqhlcb","zftdsy","bqhkxq","qyxzjzr","bqrzxq","fzye","cbkrzqk","zjfx","bqzmye","cbhxtz","azftz", "gygm"};  
	public Map<String, Map<String, String>> cache = new HashMap<String, Map<String,String>>();
	public Map<String, String> spanYear;
	
	/**
	 * 
	 * <br>Description:重新计算所有投融资情况数据
	 * <br>Author:黎春行
	 * <br>Date:2013-11-11
	 */
	public abstract void calCulateAll();
	
	/**
	 * 
	 * <br>Description:获取投融资情况记录
	 * <br>Author:黎春行
	 * <br>Date:2013-11-8
	 */
	public void getRecord(){
		String sql = "select * from hx_sx t order by t.nd, t.jd";
		List<Map<String, Object>> recordList = query(sql, YW);
		for(Map<String, Object> record : recordList){
			String nd = String.valueOf(record.get("nd"));
			String jd = String.valueOf(record.get("jd"));
			String key = nd + "_" + jd;
			for(int i = 0; i < types.length; i++){
				if(cache.get(types[i]) == null){
					Map<String, String> cacheMap = new HashMap<String, String>();
					cache.put(types[i], cacheMap);
				}
				Map<String, String> cacheMap = cache.get(types[i]);
				cacheMap.remove(key);
				cacheMap.put(key, String.valueOf(record.get(types[i]))== "null" ? "0" : String.valueOf(record.get(types[i])));
				cache.remove(types[i]);
				cache.put(types[i], cacheMap);
			}
		}
	}
	
	/**
	 * 
	 * <br>Description:将计算过后的缓存数据保存到数据库
	 * <br>Author:黎春行
	 * <br>Date:2013-11-8
	 */
	public void setRecord(){
			
		for(int i = 0; i < types.length - 3; i++){
			Map<String, String> cacheMap = cache.get(types[i]);
			if(cacheMap != null){
				Set<String> keySet = cacheMap.keySet();
				for(String time : keySet){
					String[] times = time.split("_");
					String nd = times[0];
					String jd = times[1];
					if(nd == "null" || nd == null || jd==null || jd=="null"){
						continue;
					}
					String value = cacheMap.get(time);
					StringBuffer sql = new StringBuffer();	
					sql.append("merge into hx_sx j using(select distinct '");
					sql.append(nd).append("' as nd,'").append(jd).append("' as jd from hx_sx t ) t2 on (j.nd=t2.nd and j.jd = t2.jd) when matched then ");		
					sql.append("update set j.").append(types[i]).append("=round(to_number(?),2) when not matched then insert(j.nd, j.jd, j.");
					sql.append(types[i]).append(") values(?, ?, round(to_number(?),2))");
					update(sql.toString(), YW, new Object[]{value, nd, jd, value});	
				}
			}else{
				System.out.println("投融资情况缓存异常，类TrzqkCalculate");	
			}
		}
		
	}

	/**
	 * 
	 * <br>Description:获取关联表所跨年度
	 * <br>Author:黎春行
	 * <br>Date:2013-11-8
	 * @return
	 */
	public Map<String, String> getYears(){
		String sql = "select min(t.nd) as minyear, max(t.nd) as maxyear from hx_sx t where t.jd != '0'";
		List<Map<String, Object>> resultList = query(sql, YW);
		Map<String, String> returnMap = new HashMap<String, String>();
		if(resultList.size() > 0){
			Map map = resultList.get(0);
			returnMap.put("minyear", String.valueOf(map.get("minyear")));
			returnMap.put("maxyear", String.valueOf(map.get("maxyear")));
		}
		return returnMap;
	}
}
