package com.klspta.web.xiamen.ajcc;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

import com.klspta.web.xiamen.jcl.XzqHandle;

public class AjccData extends AbstractBaseBean implements IajccData {
	
	private static final String queryString = "(upper(guid)||upper(rownum)||upper(ydxmmc)||upper(yddw)||upper(ydwz)||upper(mj)||upper(jzmj)||upper(jzqk)||upper(yt)||upper(sffhtdlyztgh)||upper(ydsj)||upper(zzqk)||upper(zztzsbh)||upper(wjzzhjxzz)||upper(yydspqcz)";
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
		return num;
	}
	
	@Override
	public List<Map<String, Object>> getDclList(String userId, String keyword) {
		String xzq = editXzq(userId);
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.* from v_pad_data_xml t where t.impxzqbm in");
		sqlBuffer.append(xzq);
		if (keyword != null) {
            keyword = UtilFactory.getStrUtil().unescape(keyword);
             sqlBuffer.append(" and").append(queryString).append(" like '%");
             sqlBuffer.append(keyword);
             sqlBuffer.append("%')");
        }
		sqlBuffer.append(" order by rownum");
		List<Map<String, Object>> getList = query(sqlBuffer.toString(), YW);
        for (int i = 0; i < getList.size(); i++) {
        	getList.get(i).put("XIANGXI", i);
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

}
