package com.klspta.web.cbd.yzt.zrb;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.web.cbd.yzt.jbb.JbbData;
import com.klspta.web.cbd.yzt.jbb.JbdkValueChange;


public class ZrbValueChange extends AbstractBaseBean  {
	private String source_name = "JC_ZIRAN";
	private String impress_name = "JC_JIBEN";
	//private String fields = "dkmc,zzsgm";
	private String fields = "dkmc,zzsgm,zzzsgm,zzzshs,hjmj,fzzzsgm";
	private String sql = "select sum(t.lzmj) as zzsgm, sum(t.zzcqgm) as zzzsgm, sum(t.yjhs) as zzzshs ,  CEIL(decode(sum(t.yjhs),0,0,(sum(t.zzcqgm)/sum(t.yjhs)))) as hjmj, sum(t.fzzcqgm) as fzzzsgm    from jc_ziran t where t.zrbbh like ?";
	private  JbdkValueChange jbdkValueChange = new JbdkValueChange();
	
	public boolean add(String dkmc) {
		//约定自然斑的名称构成为基本地块+"-"+编号
		String jbdkmc = dkmc.substring(0,dkmc.lastIndexOf("-"));
		String[] cacluteValue = getValues(jbdkmc);
		StringBuffer sqlBuffer = new StringBuffer();
		String[] field = fields.split(",");
		String insertValue = "'"+ jbdkmc +"','";
		sqlBuffer.append("merge into ").append(impress_name).append(" t using(select '");
		sqlBuffer.append(jbdkmc).append("' as jbdkmc from dual) s on (t.dkmc = s.jbdkmc)　when matched then ");
		sqlBuffer.append(" update set ");
		for(int i = 1; i < field.length - 1; i++){
			sqlBuffer.append("t.").append(field[i]).append("='").append(cacluteValue[i-1]).append("',");
			insertValue += cacluteValue[i - 1] + "','";
		}
		sqlBuffer.append("t.").append(field[field.length - 1]).append("='").append(cacluteValue[field.length - 2]).append("'");
		insertValue += cacluteValue[field.length - 2] + "'";
		sqlBuffer.append("when not matched then insert(").append(fields).append(") values (");
		sqlBuffer.append(insertValue).append(")");
		int i = update(sqlBuffer.toString(), YW);
		jbdkValueChange.add(jbdkmc);
		//刷新缓存
		JbbData jbbData = new JbbData();
		jbbData.refreshJBB();
		
		return i==1?true:false;
	}

	public boolean delete(String dkmc) {
		String[] strings = getValues(dkmc);
		String jbdkmc = dkmc.split("-")[0];
		boolean delete = true;
		if(strings[0] == "null" || "0".equals(strings[0])){
			String sql = "delete from " + impress_name + " t where t.dkmc = ?";
			delete = update(sql, YW, new Object[]{jbdkmc}) == 1?true:false;
			jbdkValueChange.delete(jbdkmc);
		}else{
			delete = add(dkmc);
		}
		return delete;
	}

	public boolean modify(String dkmc) {
		return add(dkmc);
	}
	
	private String[] getValues(String dkmc){
		List<Map<String, Object>> resultList = query(sql, YW, new Object[]{dkmc+"%"});
		String[] field = fields.split(",");
		StringBuffer value = new StringBuffer();
		for(int i = 1; i < field.length; i++){
			String fieldValue = String.valueOf(resultList.get(0).get(field[i]));
			fieldValue = fieldValue=="null"?"0":fieldValue;
			value.append(fieldValue).append(",");
		}
		String returnValue = value.substring(0, value.length() - 1);
		return returnValue.split(",");
	}

	public boolean modifyguid(String oldguid, String newguid) {
		return delete(oldguid)&&add(newguid);
	}

}
