package com.klspta.web.xiamen.wpzf.tdbgdc;

import java.util.List;
import java.util.Map;
import java.util.Set;
import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.web.xiamen.jcl.XzqHandle;

public class TdbgdcData extends AbstractBaseBean implements ItdbgdcData {
	public static final int XF_YXF = 1;
	public static final int YGFX_YSWF = 0;
	public static final int YGFX_HF = 1;
	public static final String XCHC_YSWF = "违法";
	public static final String XCHC_HF = "合法";
	private static final String queryString = "(upper(t.JCBH)||upper(t.JCMJ)||upper(t.XZQDM)||upper(t.SPMJ)||upper(t.GDMJ)";
	private String formName = "TDBGDC";

	public TdbgdcData(String formName) {
		super();
		this.formName = formName;
	}
	
	public TdbgdcData() {
		super();
	}

	@Override
	public List<Map<String, Object>> getDhcHF(String userId, String keyword) {
		String xzq = editXzq(userId);
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select replace(replace(t.isxf, '0', '未下发'), '1', '已下发') as xf, t.*, '合法' as ygqk from "+formName+" t where t.ygfxqk = ? and t.xchcqk is null and t.xzqdm in ");
		sqlBuffer.append(xzq);
		
		if (keyword != null) {
            keyword = UtilFactory.getStrUtil().unescape(keyword);
             sqlBuffer.append(" and").append(queryString).append(" like '%");
             sqlBuffer.append(keyword);
             sqlBuffer.append("%')");
        }
        
		List<Map<String, Object>> getList = query(sqlBuffer.toString(), YW, new Object[]{String.valueOf(YGFX_HF)});
		return getList;
	}

	@Override
	public List<Map<String, Object>> getDhcWF(String userId, String keyword) {
		String xzq = editXzq(userId);
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select replace(replace(t.isxf, '0', '未下发'), '1', '已下发') as xf, t.*, '违法' as ygqk  from "+formName+" t where t.ygfxqk = ? and t.xchcqk is null and t.xzqdm in ");
		sqlBuffer.append(xzq);
		if (keyword != null) {
            keyword = UtilFactory.getStrUtil().unescape(keyword);
             sqlBuffer.append(" and").append(queryString).append(" like '%");
             sqlBuffer.append(keyword);
             sqlBuffer.append("%')");
        }
		List<Map<String, Object>> getList = query(sqlBuffer.toString(), YW, new Object[]{String.valueOf(YGFX_YSWF)});
		return getList;
	}


	@Override
	public String setClqk(String yw_guid, String value) {
		String sql = "update "+formName+" t set t.clqk = ? where t.yw_guid = ?";
		int result = update(sql, YW, new Object[]{value, yw_guid});
		return String.valueOf(result);
	}

	@Override
	public String setxchcqk(String yw_guid, String value) {
		String sql = "update "+formName+" t set t.xchcqk = ? where t.yw_guid = ?";
		int result = update(sql, YW, new Object[]{value, yw_guid});
		return String.valueOf(result);
	}

	private String editXzq(String userId){
		Set<String> xzqSet = XzqHandle.getChildSetByUserId(userId);
		StringBuffer containBuffer = new StringBuffer();
		containBuffer.append("(");
		for(String xzqname : xzqSet){
			containBuffer.append("'").append(xzqname).append("',");
		}
		containBuffer.append(" 'null' )");
		return containBuffer.toString();
	}

	@Override
	public String changeFxqk(String yw_guid, String value) {
		String sql = "update "+formName+" t set t.ygfxqk = ? where t.yw_guid = ?";
		int result = update(sql, YW, new Object[]{value, yw_guid});
		return String.valueOf(result);
	}

	@Override
	public List<Map<String, Object>> getList(String where) {
	    StringBuffer sqlBuffer = new StringBuffer();
	    sqlBuffer.append("select t.* from "+formName+" t where 1=1");
	    if(where != null && !"null".equals(where)){	        
	        where = UtilFactory.getStrUtil().unescape(where);	        
	        String[] wheres = where.split("@");
	        if(!"null".equals(wheres[0])){
	            sqlBuffer.append(" and t.xzdm = ").append(wheres[0]);
	        }
	        if(!"null".equals(wheres[1])){ 
	            sqlBuffer.append(" and t.jcmj > ").append(wheres[1]);
	        }
	        if(!"null".equals(wheres[2])){
	            sqlBuffer.append(" and t.jcmj < ").append(wheres[2]);
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
                sqlBuffer.append(" and t.tblx like '%").append(wheres[9]).append("%'");
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
		sqlBuffer.append(" order by to_number(t.tbbh)");
		List<Map<String, Object>> getList = query(sqlBuffer.toString(), YW);
		return getList;
	}

    @Override
    public List<Map<String, Object>> getWFList(String where) {
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("select t.* from "+formName+" t where t.iswf = '1'");
        if(where != null && !where.equals("null")){
            where = UtilFactory.getStrUtil().unescape(where);           
            String[] wheres = where.split("@");
            if(!"null".equals(wheres[0])){
                sqlBuffer.append(" and t.xzdm = ").append(wheres[0]);
            }
            if(!"null".equals(wheres[1])){ 
                sqlBuffer.append(" and t.jcmj > ").append(wheres[1]);
            }
            if(!"null".equals(wheres[2])){
                sqlBuffer.append(" and t.jcmj < ").append(wheres[2]);
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
                sqlBuffer.append(" and t.tblx like '%").append(wheres[9]).append("%'");
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
        sqlBuffer.append(" order by to_number(t.tbbh)");
        List<Map<String, Object>> getWFList = query(sqlBuffer.toString(), YW);
        return getWFList;
    }
	
    public List<Map<String, Object>> getBGList(String where){
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("select t.yw_guid, t.tbbh, t.xmc,t.xzdm from ").append(formName).append(" t ");
        if(where != null){
            sqlBuffer.append(" ").append(where);
        }
        sqlBuffer.append(" order by to_number(t.tbbh) ");
        List<Map<String, Object>> queryList = query(sqlBuffer.toString(), YW);
        return queryList;
    }
	
}
