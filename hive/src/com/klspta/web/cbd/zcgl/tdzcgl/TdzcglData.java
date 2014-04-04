package com.klspta.web.cbd.zcgl.tdzcgl;

import com.klspta.base.AbstractBaseBean;

public class TdzcglData extends AbstractBaseBean {
	private String form_name = TdzcglReport.form_base;

	public boolean insertXMMC(String xmmc, String dkmc, String status){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("merge into ").append(form_name).append(" t using(select '");
		sqlBuffer.append(xmmc).append("' as xmmc, '").append(dkmc).append("' as dkmc from dual) s on (t.xmmc = s.xmmc and t.dkmc = s.dkmc) when matched then ");
		sqlBuffer.append(" update set t.thirsta = '").append(status).append("' where t.dkmc ='");
		sqlBuffer.append(dkmc).append("' when not matched then insert (xmmc, dkmc,thirsta").append(") values (?,?,?)");
		int i = update(sqlBuffer.toString(), YW, new Object[]{xmmc, dkmc, status});
		return i==1?true:false;
	}
	/**
	 * 
	 * <br>Description:更新表
	 * <br>Author:黎春行
	 * <br>Date:2014-1-13
	 * @return
	 */
	public boolean update(String dkmc, String field, String value){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("merge into ").append(form_name).append(" t using(select '");
		sqlBuffer.append(dkmc).append("' as xmmc from dual) s on (t.dkmc = s.xmmc) when matched then ");
		sqlBuffer.append(" update set t.").append(field).append(" = '").append(value).append("' where t.dkmc ='");
		sqlBuffer.append(dkmc).append("' when not matched then insert (dkmc,").append(field).append(") values (?,?)");
		int i = update(sqlBuffer.toString(), YW, new Object[]{dkmc, value});
		return i==1?true:false;
	}
}
