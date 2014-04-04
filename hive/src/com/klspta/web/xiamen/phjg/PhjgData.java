package com.klspta.web.xiamen.phjg;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

public class PhjgData extends AbstractBaseBean implements IphjgData {
	private String FORM_NAME = "DC_YDQKDCB";
	
	public PhjgData(String fORMNAME) {
		super();
		this.FORM_NAME = fORMNAME;
	}

	public PhjgData() {
		super();
	}

	@Override
	public List<Map<String, Object>> getList(String where) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.* from ").append(FORM_NAME).append(" t where 1=1 ");
		sqlBuffer.append("and t.yw_guid like 'PHJG%' ");
		if(where != null){
		    where = UtilFactory.getStrUtil().unescape(where);
		    sqlBuffer.append("and t.phjgxmmc||t.phjgyddw||t.phjgdwlx||t.phjgpzwh||t.phjgpzrq||t.phjghdfs||t.phjgpzyt||t.phjgtdzl||t.phjgtdxz||t.phjgsjydbmbz||t.phjggtsbz like '%"+where+"%' ");
		}
		sqlBuffer.append("order by t.scsj");
		List<Map<String, Object>> getList = query(sqlBuffer.toString(), YW);
		return getList;
	}

}
