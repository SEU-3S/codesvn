package com.klspta.web.cbd.yzt.cbjhzhb;

import com.klspta.base.AbstractBaseBean;

/**
 * 
 * <br>Title:储备计划管理表扩展类
 * <br>Description:TODO 类功能描述
 * <br>Author:黎春行
 * <br>Date:2014-1-13
 */
public class CbjhData extends AbstractBaseBean implements Runnable{
	
    private String xmmc = "";
    private String field = "";
    private String value = "";
	
	
	
	/**
	 * 
	 * <br>Description:更新储备计划管理类扩展表
	 * <br>Author:黎春行
	 * <br>Date:2014-1-13
	 * @return
	 */
	public boolean update(String xmmc, String field, String value){
		String form_name = Cbjhzhb.form_extend;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("merge into ").append(form_name).append(" t using(select '");
		sqlBuffer.append(xmmc).append("' as xmmc from dual) s on (t.xmmc = s.xmmc) when matched then ");
		sqlBuffer.append(" update set t.").append(field).append(" = '").append(value).append("' where t.xmmc ='");
		sqlBuffer.append(xmmc).append("' when not matched then insert ( xmmc,").append(field).append(") values (?,?)");
		int i = update(sqlBuffer.toString(), YW, new Object[]{xmmc, value});
		return i==1?true:false;
	}



	@Override
	public void run() {
		update(xmmc, field, value);
	}
	
	public void setChange(String xmmc, String field, String value) {
		this.xmmc = xmmc;
		this.field = field;
		this.value = value;
	}
	
}
