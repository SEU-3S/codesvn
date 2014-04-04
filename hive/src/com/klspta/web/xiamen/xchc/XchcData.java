package com.klspta.web.xiamen.xchc;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.console.ManagerFactory;
import com.klspta.console.user.User;
import com.klspta.web.xiamen.jcl.XzqHandle;

public class XchcData extends AbstractBaseBean implements IxchcData {
	
	private static final String queryString = "(upper(guid)||upper(xzqmc)||upper(yddw)||upper(ydsj)||upper(tdyt)||upper(jsqk)||upper(ydqk)||upper(dfccqk)||upper(wfwglx)" +
			                                  "||upper(ydxmmc)||upper(ydzt)||upper(ydwz)||upper(zdmj)||upper(gdmj)||upper(jzmj)||upper(jzxz)";
	
	@Override
	public String SetNewRecord(String userid){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", new DateFormatSymbols());
		String dateString = "XC" + df.format(Calendar.getInstance().getTime());
				
		String numsql = "select max(t.yw_guid) num from dc_ydqkdcb t where t.yw_guid like '" + dateString + "%'";
		String num;
		List<Map<String, Object>> result = query(numsql, YW);
		String nestNum = String.valueOf(result.get(0).get("num"));
		if(nestNum.equals("null") || nestNum.equals("")){
			num = dateString + "001";
		}else{
			String temp = nestNum.substring(nestNum.length() - 3, nestNum.length());
            temp=String.valueOf(Integer.parseInt(temp)+1);   
            temp = "00" + temp;
            num = dateString + temp.substring(temp.length() - 3);
		}
		User user = null;
		String xzqdm = null;
		String xzqmc = null;
		//生成线索号后初始化
		try {
			user = ManagerFactory.getUserManager().getUserWithId(userid);
			xzqdm = user.getXzqh();
			xzqmc = UtilFactory.getXzqhUtil().getNameByCode(xzqdm);
			String sql = "insert into dc_ydqkdcb(yw_guid,impuser, impxzq, impxzqbm) values(?,?,?,?)";
			update(sql, YW, new Object[]{num, user.getFullName(), xzqmc, xzqdm});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}
	
	@Override
	public List<Map<String, Object>> getDclList(String userId, String keyword) {
		//String xzq = editXzq(userId);
	    //String xzqs = XzqHandle.getXzqByUserxzq(userXzq)	    
		StringBuffer sqlBuffer = new StringBuffer();
		String sql ="select t.* from v_pad_data_xml t where 1=1";
		//sqlBuffer.append(xzq);
		String xzqSql = XzqHandle.getXzqSql(userId, sql, "impxzqbm");
		sqlBuffer.append(xzqSql);
		sqlBuffer.append(" and t.guid like 'XC%'");
		if (keyword != null) {
            keyword = UtilFactory.getStrUtil().unescape(keyword);
             sqlBuffer.append(" and").append(queryString).append(" like '%");
             sqlBuffer.append(keyword);
             sqlBuffer.append("%')");
        }
		sqlBuffer.append(" order by t.scsj desc");
		List<Map<String, Object>> getList = query(sqlBuffer.toString(), YW);
        for (int i = 0; i < getList.size(); i++) {
        	getList.get(i).put("XIANGXI", i);
        	getList.get(i).put("SEND", i);
        	getList.get(i).put("LIAN", i);
        	getList.get(i).put("DELETE", i);
        }
		return getList;
	}
	
    @Override
    public List<Map<String, Object>> getHccgList(String userId, String keyword) {
        //String xzq = editXzq(userId);
        StringBuffer sqlBuffer = new StringBuffer();
        //sqlBuffer.append("select t.* from v_pad_data_xml t where t.impxzqbm in");
        String sql ="select t.* from v_pad_data_xml t where 1=1";
        //sqlBuffer.append(xzq);
        String xzqSql = XzqHandle.getXzqSql(userId, sql, "impxzqbm");
        sqlBuffer.append(xzqSql);
        sqlBuffer.append(" and t.guid not like 'XC%' and t.guid not like 'PHJG%'");
        if (keyword != null) {
            keyword = UtilFactory.getStrUtil().unescape(keyword);
             sqlBuffer.append(" and").append(queryString).append(" like '%");
             sqlBuffer.append(keyword);
             sqlBuffer.append("%')");
        }
        sqlBuffer.append(" order by t.scsj desc");
        List<Map<String, Object>> getList = query(sqlBuffer.toString(), YW);
        for (int i = 0; i < getList.size(); i++) {
            getList.get(i).put("XIANGXI", i);
            getList.get(i).put("SEND", i);
            getList.get(i).put("DELETE", i);
        }
        return getList;
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
	public List<Map<String, Object>> getYlaList(String userId, String keyword) {
		String xzq = editXzq(userId);
		StringBuffer sqlBuffer = new StringBuffer();
		String sql ="select t.* from v_pad_data_xml t where 1=1";
		String xzqSql = XzqHandle.getXzqSql(userId, sql, "impxzqbm");
		sqlBuffer.append(xzqSql);
		sqlBuffer.append(" and t.state = '已立案' and t.guid like 'XC%'");
		if (keyword != null) {
            keyword = UtilFactory.getStrUtil().unescape(keyword);
             sqlBuffer.append(" and").append(queryString).append(" like '%");
             sqlBuffer.append(keyword);
             sqlBuffer.append("%')");
        }
		sqlBuffer.append(" order by t.ydsj");
		List<Map<String, Object>> getList = query(sqlBuffer.toString(), YW);
        for (int i = 0; i < getList.size(); i++) {
        	getList.get(i).put("XIANGXI", i);
        }
		return getList;
	}



}
