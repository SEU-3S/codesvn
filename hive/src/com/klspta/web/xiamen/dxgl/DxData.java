package com.klspta.web.xiamen.dxgl;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

public class DxData extends AbstractBaseBean implements IdxData {
	
	private static final String queryString = "(upper(t.DXBH)||upper(t.DXNR)||upper(t.JSRY)||upper(t.FSR_NAME)";
	private String formName = "DXXXB";

	@Override
	public List<Map<String, Object>> getAllList(String where) {
		StringBuffer sqlBuffer = new StringBuffer();
	    sqlBuffer.append("select t.* from ");
	    sqlBuffer.append(formName);
	    sqlBuffer.append(" t where 1=1 ");
	    
	    if (where != null && !"".equals(where)) {
	    	where = UtilFactory.getStrUtil().unescape(where);
        	sqlBuffer.append("and ").append(queryString).append(" like '%");
        	sqlBuffer.append(where);
        	sqlBuffer.append("%') ");
        }
	    
		sqlBuffer.append("order by to_date(t.FSSJ,'yyyy-mm-dd hh24:mi:ss') desc");
		List<Map<String, Object>> getList = query(sqlBuffer.toString(), YW);
		return getList;
	}

	@Override
	public List<Map<String, Object>> getOwnerList(String userId, String where) {
		StringBuffer sqlBuffer = new StringBuffer();
	    sqlBuffer.append("select t.* from ");
	    sqlBuffer.append(formName);
	    sqlBuffer.append(" t where 1=1 ");
	    sqlBuffer.append("and t.FSR_ID = '");
	    sqlBuffer.append(userId);
	    sqlBuffer.append("' ");
	    
	    if (where != null && !"".equals(where)) {
	    	where = UtilFactory.getStrUtil().unescape(where);
        	sqlBuffer.append("and ").append(queryString).append(" like '%");
        	sqlBuffer.append(where);
        	sqlBuffer.append("%') ");
        }
	    
		sqlBuffer.append("order by to_date(t.FSSJ,'yyyy-mm-dd hh24:mi:ss') desc");
		List<Map<String, Object>> getList = query(sqlBuffer.toString(), YW);
		return getList;
	}

}
