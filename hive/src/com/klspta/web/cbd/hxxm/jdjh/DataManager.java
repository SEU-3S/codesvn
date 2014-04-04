package com.klspta.web.cbd.hxxm.jdjh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;

/**
 * 
 * <br>Title:季度计划数据管理类
 * <br>Description:TODO 类功能描述
 * <br>Author:黎春行
 * <br>Date:2013-10-14
 */
public class DataManager extends AbstractBaseBean {
	
	private static DataManager dataManager;
	
	public static DataManager getInstance(){
		if(dataManager == null){
			dataManager = new DataManager();
		}
		return dataManager;
	}
	
	
	/**
	 * 
	 * <br>Description:将开发体量数据根据季度分类
	 * <br>Author:黎春行
	 * <br>Date:2013-10-14
	 */
	public List<Map<String, Object>> getJDList(){
		String sqlString = "select * from hx_sx t order by t.nd, t.jd ";
		List<Map<String, Object>> sxList = query(sqlString, YW);
		return sxList;
	}
	
	/**
	 * 
	 * <br>Description:获取开发体量总和
	 * <br>Author:黎春行
	 * <br>Date:2013-10-14
	 * @return
	 */
	public List<Map<String, Object>> getKFTL_ZHList(){
		String totalSql = "select sum(t.zshs) as zshs, sum(t.wckfdl) as wckfdl, sum(t.wckfgm) as wckfgm, sum(t.cbhxtz) as cbhxtz from hx_sx t";
		List<Map<String, Object>> totalList = query(totalSql, YW);
		return totalList;
	}
	
	/**
	 * 
	 * <br>Description:获取按季度和项目名称汇总的信息
	 * <br>Author:黎春行
	 * <br>Date:2013-10-14
	 * @return
	 */
	public List<Map<String, Object>> getKFTL_ALLList(){
		String kftlSql = "select t.nd, t.jd ,t.xmmc, sum(t.hs) as hs,sum(t.dl) as dl, sum(t.gm) as gm, sum(t.tz) as tz, sum(t.zhu) as zhu, sum(t.qi) as qi, sum(hsz) as hsz, sum(dlz) as dlz, sum(gmz) as gmz, sum(tzz) as tzz, sum(zhuz) as zhuz, sum(qiz) as qiz, avg(t.lm) as lm, avg(t.qi) as qi from hx_kftl t  group by t.nd, t.jd, t.xmmc order by t.xmmc, t.nd, t.jd";
		return query(kftlSql, YW);
	}
	/**
	 * 
	 * <br>Description:获取按季度和项目名称汇总的供地体量信息
	 * <br>Author:黎春行
	 * <br>Date:2013-10-15
	 * @return
	 */
	public List<Map<String, Object>> getGDTL_ALLList(){
		String kftlSql = "select t.nd, t.jd ,t.xmmc, sum(t.dl) as dl,sum(t.gm) as gm, sum(t.cb) as cb, sum(t.sy) as sy, sum(t.zj) as zj, sum(t.zuj) as zuj,sum(t.dlz) as dlz, sum(t.gmz) as gmz, sum(cbz) as cbz, sum(syz) as syz, sum(zjz) as zjz, avg(t.zuj) as zuj from hx_gdtl t  group by t.nd, t.jd, t.xmmc order by t.xmmc, t.nd, t.jd";
		return query(kftlSql, YW);
	}
	/**
	 * 
	 * <br>Description:获取所有具有供地体量的项目
	 * <br>Author:黎春行
	 * <br>Date:2013-10-15
	 * @return
	 */
	public List<Map<String, Object>> getGDTL_XMList(){
		String projctsSql = "select distinct t.xmmc from hx_gdtl t order by t.xmmc";
		return query(projctsSql, YW);
	}
	/**
	 * 
	 * <br>Description:获取所有具有开发时序的项目
	 * <br>Author:黎春行
	 * <br>Date:2013-10-14
	 * @return
	 */
	public List<Map<String, Object>> getKFTL_XMList(){
		String projctsSql = "select distinct t.xmmc from hx_kftl t order by t.xmmc";
		return query(projctsSql, YW);
	}
	
	
	/**
	 * 
	 * <br>Description:获取具有实施时序计划的年度区间
	 * <br>Author:黎春行
	 * <br>Date:2013-10-11
	 * @return
	 */
	public Map<String, Object> getPlanYear(){
		String sql = "select min(t.nd) as minyear, max(t.nd) as maxyear from hx_sx t";
		Map<String, Object> planYearMap = new HashMap<String, Object>();
		List<Map<String, Object>> resultList = query(sql, YW);
		if(resultList.size() > 0){
			planYearMap = resultList.get(0);
		}
		return planYearMap;
	}
	/**
	 * 
	 * <br>Description:获取安置房建设总和
	 * <br>Author:黎春行
	 * <br>Date:2013-10-15
	 * @return
	 */
	public List<Map<String, Object>> getAZFJC_ZHList(){
		String totalSql = "select sum(t.kgjgfl) as kgjgfl, sum(t.azftz) as azftz, sum(t.azfsyl) as azfsyl, sum(t.azfcl) as azfcl from hx_sx t";
		List<Map<String, Object>> totalList = query(totalSql, YW);
		return totalList;
	}
	
	/**
	 * 
	 * <br>Description:获取安置房存量详细信息
	 * <br>Author:黎春行
	 * <br>Date:2013-10-15
	 * @return
	 */
	public List<Map<String, Object>> getAZFJCXXList(){
		String sql = "select * from hx_azf t order by t.yw_guid, t.nd, t.jd";
		List<Map<String, Object>> azfList = query(sql, YW);
		return azfList;
	}
	
	/**
	 * 
	 * <br>Description:获取所有安置房建设List
	 * <br>Author:黎春行
	 * <br>Date:2013-10-15
	 * @return
	 */
	public List<Map<String, Object>> getAzfProList(){
		String projctsSql = "select t.kg as kg, t.tz from hx_azf t group by t.kgmc, t.tzmc, t.yw_guid order by t.yw_guid";
		return query(projctsSql, YW);
	}
	
	public List<Map<String, Object>> getGDTL_ZHList(){
		String totalSql = "select sum(t.gytd) as gytd, sum(t.gygm) as gygm, sum(t.cbkkc) as cbkkc, sum(t.cbkrznl) as cbkrznl from hx_sx t";
		List<Map<String, Object>> totalList = query(totalSql, YW);
		return totalList;
	}
	
	public List<Map<String, Object>> getTrzqk_ZHList(){
		String totalSql = "select sum(t.bqtzxq) as bqtzxq, sum(t.bqhlcb) as bqhlcb, sum(t.zftdsy) as zftdsy, sum(t.bqrzxq) as bqrzxq, sum(t.bqhkxq) as bqhkxq, sum(t.qyxzjzr) as qyxzjzr, sum(t.fzye) as fzye, sum(t.cbkrzqk) cbkrzqk, sum(t.zjfx) as zjfx, sum(t.bqzmye) as bqzmye from hx_sx t";
		List<Map<String, Object>> totalList = query(totalSql, YW);
		return totalList;
	}
}
