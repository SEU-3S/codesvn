package com.klspta.web.cbd.xmgl.xmkgzbb;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

public class XmkgzbbData extends AbstractBaseBean {
	
	private static final String formName = "XMKGZBB";
	private static final String queryString = "(upper(ROWNUM)||upper(T1.DKMC)||upper(T1.YDXZDH)||upper(T1.YDXZ)||upper(T1.JSYDMJ)||upper(T1.RJL)||upper(T1.GHJZGM)||upper(T1.JZKZGD)||upper(T1.BZ))";
	public static List<Map<String, Object>> xmkgzbbList;
	
	private static XmkgzbbData xmkgzbbData;
	
	private String dkbh = "";
    private String field = "";
    private String value = "";
	
	public static XmkgzbbData getInstance(){
    	if(xmkgzbbData == null){
    		xmkgzbbData = new XmkgzbbData();
    	}
    	return xmkgzbbData;
    }
	
	
	public List<Map<String, Object>> getDclList(String userId, String keyword) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.* from v_pad_data_xml t ");
		if (keyword != null) {
            keyword = UtilFactory.getStrUtil().unescape(keyword);
             sqlBuffer.append(" and").append(queryString).append(" like '%");
             sqlBuffer.append(keyword);
             sqlBuffer.append("%')");
        }
		List<Map<String, Object>> getList = query(sqlBuffer.toString(), YW);
        for (int i = 0; i < getList.size(); i++) {
        	getList.get(i).put("XIANGXI", i);
        	getList.get(i).put("DELETE", i);
        }
		return getList;
	}
	
	
	public List<Map<String, Object>> getQuery(HttpServletRequest request) {
        String keyWord = request.getParameter("keyWord");
        String yw_guid = request.getParameter("yw_guid");
        StringBuffer querySql = new StringBuffer();
        querySql.append("select rownum,t1.dkmc,t1.ydxzdh,t1.ydxz,t1.jsydmj,t1.rjl,t1.ghjzgm,t1.jzkzgd,t1.bz,t2.ydxzlx from  DCSJK_KGZB t1,XMKGZBB t2 where t1.dkmc=t2.dkbh and yw_guid = '").append(yw_guid).append("' and t1.dqy=t2.qy and t1.qy=t2.xqy ");
        if (keyWord != null&&!"".equals(keyWord)) {
            keyWord = UtilFactory.getStrUtil().unescape(keyWord);
            querySql.append(" and t1.dkmc||t1.ydxz||t1.ydxzdh||t1.jsydmj||t1.rjl||t1.ghjzgm||t1.jzkzgd||t1.bz like '%");
            querySql.append(keyWord).append("'");
        } else if (xmkgzbbList != null) {
            return xmkgzbbList;
        }
        return query(querySql.toString(), YW);
    }
	public boolean delete(String dk,String yw_guid){
    	String sql = "delete from " + formName + " t where t.dkbh = ? and t.yw_guid = '"+yw_guid+"'";
    	int result = update(sql, YW, new Object[]{dk});
    	return result == 1 ? true : false;
    }
	public boolean modifyValue(String yw_guid, String field, String value){
    	StringBuffer sqlBuffer = new StringBuffer();
    	sqlBuffer.append(" update ").append(formName);
    	sqlBuffer.append(" t set t.").append(field);
    	sqlBuffer.append("='"+value);
    	sqlBuffer.append("' where t.ydxzlx='4' and t.yw_guid=?");
    	int i = update(sqlBuffer.toString(), YW, new Object[]{ yw_guid});
     	return i == 1 ? true : false;
    }
	
}
