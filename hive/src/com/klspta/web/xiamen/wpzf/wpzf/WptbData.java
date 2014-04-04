package com.klspta.web.xiamen.wpzf.wpzf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.web.xiamen.jcl.XzqHandle;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

public class WptbData extends AbstractBaseBean implements IwptbData {
	private WPbean wpBean = new WPbean();
	private static final String TDBGDC = "(upper(t.JCBH)||upper(t.JCMJ)||upper(t.XZQMC)||upper(t.SPMJ)||upper(t.GDMJ)||upper(t.WPND)";
	private static final String WPTB = "(upper(t.objectid)||upper(t.xmc)||upper(t.jcbh)||upper(t.tblx)||upper(t.jcmj)";
	public WptbData(WPbean wpBean) {
		super();
		this.wpBean = wpBean;
	}
	
	public WptbData() {
		super();
	}
	
	@Override
	public List<Map<String, Object>> getHFlist(String userId, String keyword) {
		String tdbg = getTDBG(userId, "合法", keyword);
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.objectid, t.xmc, t.jcbh, t.tblx, t.jcmj from ").append(wpBean.getTDBG_NAME());
		sqlBuffer.append(" t, ").append(wpBean.getWP_NAME()).append(" j where ");
		sqlBuffer.append("sde.st_intersects(t.shape, j.shape) = 1 and to_char(t.objectid) in ");
		sqlBuffer.append(tdbg);
		/**
		if (keyword != null) {
            keyword = UtilFactory.getStrUtil().unescape(keyword);
             sqlBuffer.append(" and").append(WPTB).append(" like '%");
             sqlBuffer.append(keyword);
             sqlBuffer.append("%')");
        }
        **/
		List<Map<String, Object>> queryList = query(sqlBuffer.toString(), GIS);
		List<Map<String, Object>> tdbgList = getTDBGList(userId, "合法", keyword);
		return linkMap(queryList, tdbgList);
	}

	@Override
	public List<Map<String, Object>> getWCLlist(String userId, String keyword) {
		StringBuffer sqlBuffer = new StringBuffer();
		String xzq = XzqHandle.editXzq(userId);
		sqlBuffer.append("select j.objectid, j.sm as xmc, j.bsm as jcbh, j.ysdm as tblx, j.gzqmj as jcmj from ").append(wpBean.getTDBG_NAME());
		sqlBuffer.append(" t, ").append(wpBean.getWP_NAME()).append(" j where ");
		sqlBuffer.append("sde.st_intersects(t.shape, j.shape) = 0 and j.xzqdm in ");
		sqlBuffer.append(xzq);
		if (keyword != null) {
            keyword = UtilFactory.getStrUtil().unescape(keyword);
             sqlBuffer.append(" and").append(WPTB).append(" like '%");
             sqlBuffer.append(keyword);
             sqlBuffer.append("%')");
        }
		List<Map<String, Object>> queryList = query(sqlBuffer.toString(), GIS);
		return queryList;
	}

	@Override
	public List<Map<String, Object>> getWFlist(String userId, String keyword) {
		String tdbg = getTDBG(userId, "违法", keyword);
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.objectid, t.xmc, t.jcbh, t.tblx, t.jcmj from ").append(wpBean.getTDBG_NAME());
		sqlBuffer.append(" t, ").append(wpBean.getWP_NAME()).append(" j where ");
		sqlBuffer.append("sde.st_intersects(t.shape, j.shape) = 1 and to_char(t.objectid) in ");
		sqlBuffer.append(tdbg);
		/**
		if (keyword != null) {
            keyword = UtilFactory.getStrUtil().unescape(keyword);
             sqlBuffer.append(" and").append(WPTB).append(" like '%");
             sqlBuffer.append(keyword);
             sqlBuffer.append("%')");
        }
        **/
		List<Map<String, Object>> queryList = query(sqlBuffer.toString(), GIS);
		List<Map<String, Object>> tdbgList = getTDBGList(userId, "违法", keyword);
		return linkMap(queryList, tdbgList);
	}
	
	public List<Map<String, Object>> getWPList(String where){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.objectid as yw_guid, t.tbbh, t.xjxzqhmc, sde.st_astext(t.shape) wkt from ").append(wpBean.getWP_NAME()).append(" t ");
		if(where != null){
			sqlBuffer.append(" ").append(where);
		}
		sqlBuffer.append(" order by to_number(t.tbbh) ");
		List<Map<String, Object>> queryList = query(sqlBuffer.toString(), GIS);
		return queryList;
	}
	
	private String getTDBG(String userId, String type, String keyword){
		List<Map<String, Object>> queryList = getTDBGList(userId, type, keyword);
		Set<String> tdbgSet = new HashSet<String>();
		for(Map<String, Object> queryMap : queryList){
			tdbgSet.add(String.valueOf(queryMap.get("YW_GUID")));
		}
		
		StringBuffer containBuffer = new StringBuffer();
		containBuffer.append("(");
		for(String tdbgname : tdbgSet){
			containBuffer.append("'").append(tdbgname).append("',");
		}
		containBuffer.append(" 'null' )");
		
		return containBuffer.toString();
	}
	
	private List<Map<String, Object>> getTDBGList(String userId, String type, String keyword){
		String xzq = XzqHandle.editXzq(userId);
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.* from ").append(wpBean.getTDBG_YW());
		if(! type.equals("all")){
			sqlBuffer.append(" t where t.xchcqk = '").append(type).append("' and t.xzqdm in ");
		}else{
			sqlBuffer.append(" t where t.xzqdm in ");
		}
		sqlBuffer.append(xzq);
		if (keyword != null) {
            keyword = UtilFactory.getStrUtil().unescape(keyword);
             sqlBuffer.append(" and").append(TDBGDC).append(" like '%");
             sqlBuffer.append(keyword);
             sqlBuffer.append("%')");
        }
		List<Map<String, Object>> queryList = query(sqlBuffer.toString(),YW);
		return queryList;
	}
	
	
	private List<Map<String, Object>> linkMap(List<Map<String, Object>> queryList, List<Map<String, Object>> tdbgList ){
		Map<String, Map<String, Object>> linkMap = new HashMap<String, Map<String,Object>>();
		for(Map<String, Object> queryMap : queryList){
			String key = String.valueOf(queryMap.get("objectid"));
			linkMap.put(key, queryMap);
		}
		for(Map<String, Object> tdbgMap : tdbgList){
			String key = String.valueOf(tdbgMap.get("yw_guid"));
			Map<String, Object> sonMap = new HashMap<String, Object>();
			if(linkMap.containsKey(key)){
				sonMap = linkMap.get(key);
				sonMap.putAll(tdbgMap);
			}
			linkMap.remove(key);
			linkMap.put(key, sonMap);
		}
		Set<String> linkeys = linkMap.keySet();
		List<Map<String, Object>> returnList =new  ArrayList<Map<String,Object>>();
		for(String key : linkeys){
			returnList.add(linkMap.get(key));
		}
		return returnList;
	}

    @Override
    public List<Map<String, Object>> getWpxf(String where) {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("select t.yw_guid, t.ygspmj,t.ygspbl,t.yggdmj,t.yggdbl,t.nydmj,t.gdmj,t.jsydmj,t.wlydmj,t.yxjsq,t.ytjjsq,t.xzjsq,t.jzjsq,t.fhghmj,t.bfhghmj,t.zyjbntmj,d.xjxzqhmc,d.xjxzqhdm,d.tbbh,d.dlbm,d.dlmc,d.qsxz,d.qsdwdm,d.qsdwmc,d.tbmj,d.tblx,d.tfh from wpzfjc t inner join zfjcgis.dlgzyswftb_zfjc_2012r d on t.tbbh = d.tbbh and t.xjxzqhdm = d.xjxzqhdm");
        if(where != null && !"null".equals(where)){         
            where = UtilFactory.getStrUtil().unescape(where);           
            String[] wheres = where.split("@");
            if(!"null".equals(wheres[0])){
                sqlBuffer.append(" and d.xjxzqhmc = '").append(wheres[0]+"'");
            }
            if(!"null".equals(wheres[1])){ 
                sqlBuffer.append(" and d.tbmj > ").append(wheres[1]);
            }
            if(!"null".equals(wheres[2])){
                sqlBuffer.append(" and d.tbmj < ").append(wheres[2]);
            }
            if(!"null".equals(wheres[3])){
                sqlBuffer.append(" and t.ygspmj > ").append(wheres[3]);
            }
            if(!"null".equals(wheres[4])){
                sqlBuffer.append(" and t.ygspmj < ").append(wheres[4]);
            }
            if(!"null".equals(wheres[5])){
                sqlBuffer.append(" and t.ygspbl > ").append(wheres[5]);
            }
            if(!"null".equals(wheres[6])){
                sqlBuffer.append(" and t.ygspbl < ").append(wheres[6]);
            }
            if(!"null".equals(wheres[7])){
                sqlBuffer.append(" and t.fhghmj > ").append(wheres[7]);
            }
            if(!"null".equals(wheres[8])){
                sqlBuffer.append(" and t.fhghmj < ").append(wheres[8]);
            }
            if(!"null".equals(wheres[9])){
                sqlBuffer.append(" and d.tblx = '").append(wheres[9]+"'");
            }
            if(!"null".equals(wheres[10])){
                sqlBuffer.append(" and t.nydmj > ").append(wheres[10]);
            }
            if(!"null".equals(wheres[11])){
                sqlBuffer.append(" and t.nydmj < ").append(wheres[11]);
            }
            if(!"null".equals(wheres[12])){
                sqlBuffer.append(" and t.yggdmj > ").append(wheres[12]);
            }
            if(!"null".equals(wheres[13])){
                sqlBuffer.append(" and t.yggdmj < ").append(wheres[13]);
            }
            if(!"null".equals(wheres[14])){
                sqlBuffer.append(" and t.yggdbl > ").append(wheres[14]);
            }
            if(!"null".equals(wheres[15])){
                sqlBuffer.append(" and t.yggdbl < ").append(wheres[15]);
            }
            if(!"null".equals(wheres[16])){
                sqlBuffer.append(" and t.bfhghmj > ").append(wheres[16]);
            }
            if(!"null".equals(wheres[17])){
                sqlBuffer.append(" and t.bfhghmj < ").append(wheres[17]);
            }
        }
        sqlBuffer.append(" order by to_number(d.tbbh)");
        List<Map<String, Object>> getList = query(sqlBuffer.toString(), YW);
        return getList;        
    }

    @Override
    public List<Map<String, Object>> getWpsb(String where) {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("select t.yw_guid, t.ygspmj,t.ygspbl,t.yggdmj,t.yggdbl,t.nydmj,t.gdmj,t.jsydmj,t.wlydmj,t.yxjsq,t.ytjjsq,t.xzjsq,t.jzjsq,t.fhghmj,t.bfhghmj,t.zyjbntmj,d.xjxzqhmc,d.xjxzqhdm,d.tbbh,d.dlbm,d.dlmc,d.qsxz,d.qsdwdm,d.qsdwmc,d.tbmj,d.tblx,d.tfh from wpzfjc t inner join zfjcgis.dlgzyswftb_zfjc_2012r d on t.tbbh = d.tbbh and t.xjxzqhdm = d.xjxzqhdm and t.iswf='1'");
        if(where != null && !"null".equals(where)){         
            where = UtilFactory.getStrUtil().unescape(where);           
            String[] wheres = where.split("@");
            if(!"null".equals(wheres[0])){
                sqlBuffer.append(" and d.xjxzqhmc = '").append(wheres[0]+"'");
            }
            if(!"null".equals(wheres[1])){ 
                sqlBuffer.append(" and d.tbmj > ").append(wheres[1]);
            }
            if(!"null".equals(wheres[2])){
                sqlBuffer.append(" and d.tbmj < ").append(wheres[2]);
            }
            if(!"null".equals(wheres[3])){
                sqlBuffer.append(" and t.ygspmj > ").append(wheres[3]);
            }
            if(!"null".equals(wheres[4])){
                sqlBuffer.append(" and t.ygspmj < ").append(wheres[4]);
            }
            if(!"null".equals(wheres[5])){
                sqlBuffer.append(" and t.ygspbl > ").append(wheres[5]);
            }
            if(!"null".equals(wheres[6])){
                sqlBuffer.append(" and t.ygspbl < ").append(wheres[6]);
            }
            if(!"null".equals(wheres[7])){
                sqlBuffer.append(" and t.fhghmj > ").append(wheres[7]);
            }
            if(!"null".equals(wheres[8])){
                sqlBuffer.append(" and t.fhghmj < ").append(wheres[8]);
            }
            if(!"null".equals(wheres[9])){
                sqlBuffer.append(" and d.tblx = '").append(wheres[9]+"'");
            }
            if(!"null".equals(wheres[10])){
                sqlBuffer.append(" and t.nydmj > ").append(wheres[10]);
            }
            if(!"null".equals(wheres[11])){
                sqlBuffer.append(" and t.nydmj < ").append(wheres[11]);
            }
            if(!"null".equals(wheres[12])){
                sqlBuffer.append(" and t.yggdmj > ").append(wheres[12]);
            }
            if(!"null".equals(wheres[13])){
                sqlBuffer.append(" and t.yggdmj < ").append(wheres[13]);
            }
            if(!"null".equals(wheres[14])){
                sqlBuffer.append(" and t.yggdbl > ").append(wheres[14]);
            }
            if(!"null".equals(wheres[15])){
                sqlBuffer.append(" and t.yggdbl < ").append(wheres[15]);
            }
            if(!"null".equals(wheres[16])){
                sqlBuffer.append(" and t.bfhghmj > ").append(wheres[16]);
            }
            if(!"null".equals(wheres[17])){
                sqlBuffer.append(" and t.bfhghmj < ").append(wheres[17]);
            }
        }
        sqlBuffer.append(" order by to_number(d.tbbh)");
        List<Map<String, Object>> getList = query(sqlBuffer.toString(), YW);
        return getList; 
    }
	
	
	

}
