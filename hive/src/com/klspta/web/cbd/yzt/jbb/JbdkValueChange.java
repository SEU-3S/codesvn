package com.klspta.web.cbd.yzt.jbb;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;


public class JbdkValueChange extends AbstractBaseBean {
	private String source_name = "JC_JIBEN";
	private String impress_name = "JC_XIANGMU";
	private String fields = "jsyd,rjl,jzgm,gjjzgm,jzjzgm,szjzgm,zzsgm,zzzsgm,zzzshs,hjmj,fzzzsgm,fzzjs,kfcb,dmcb,yjcjj,yjzftdsy,cxb,cqqd,cbfgl";
	private String sql = "select sum(t.zzsgm) as zzsgm,  sum(t.zzzsgm) as zzzsgm, sum(t.zzzshs) as zzzshs, trunc(decode(sum(t.zzzshs),0,0,sum(t.zzzsgm)/sum(t.zzzshs)),2) as hjmj, sum(t.fzzzsgm) as fzzzsgm,"+
						" sum(t.fzzjs) as fzzjs, sum(t.kfcb) as kfcb, sum(t.dmcb) as dmcb,sum(t.yjcjj) as yjcjj, sum(t.yjzftdsy) as yjzfdsy, sum(t.cxb)"+
						" as cxb, sum(t.cqqd) as cqqd, sum(t.cbfgl) as cbfgl from jc_jiben t where t.dkmc in ";
	public boolean add(String ywGuid) {
		//根据地块编号找到对应项目编号
		String jbdkSql = "select t.dkmc,t.xmname from " + impress_name + " t where  t.dkmc like '%" + ywGuid + "%'";
		List<Map<String, Object>> jbdkList = query(jbdkSql, YW);
		if(jbdkList.size() < 1){
			return true;
		}else{
			Map<String, Object> jbdkMap = jbdkList.get(0);
			String xmmc = String.valueOf(jbdkMap.get("xmname"));
			String bhdk = String.valueOf(jbdkMap.get("dkmc"));
			String[] values = getValues(bhdk, xmmc);
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append("update ").append(impress_name).append(" t set ");
			String[] field = fields.split(",");
			for(int i = 0; i < field.length - 1; i++){
				sqlBuffer.append(" t.").append(field[i]).append("=?,");
			}
			sqlBuffer.append("t.").append(field[field.length - 1]).append("=? where t.xmname = ?");
			int i = update(sqlBuffer.toString(), YW, values);
			return i==1?true:false;
		}
	}

	public boolean delete(String ywGuid) {
		return false;
	}

	public boolean modify(String ywGuid) {
		return add(ywGuid);
	}

	public boolean modifyguid(String oldguid, String newguid) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private String[] getValues(String bhdk, String xmmc){
		String[] field = fields.split(",");  
		String[] values = new String[field.length + 1];
		StringBuffer bhdkBuffer = new StringBuffer();
		String[] bhdks = bhdk.split(",");
		bhdkBuffer.append("(");
		for(int i = 0; i < bhdks.length - 1; i++){
			bhdkBuffer.append("'").append(bhdks[i]).append("',");
		}
		bhdkBuffer.append("'").append(bhdks[bhdks.length - 1]).append("')");
		String querySql = sql + bhdkBuffer;
		List<Map<String, Object>> resultList = query(querySql, YW);
		Map<String, Object> resultMap = resultList.get(0);
		for(int i = 0; i < field.length; i++){
			String fieldValue = String.valueOf(resultMap.get(field[i]));
			fieldValue = fieldValue=="null"?"0":fieldValue;
			values[i] = fieldValue;
		}
		values[field.length] = xmmc;
		return values;
	}

}
