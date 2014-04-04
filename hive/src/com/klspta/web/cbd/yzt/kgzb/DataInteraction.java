package com.klspta.web.cbd.yzt.kgzb;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
/****
 * 
 * <br>Title:数据库操作类
 * <br>Description:
 * <br>Author:朱波海
 * <br>Date:2014-1-15
 */
public class DataInteraction extends AbstractBaseBean {
	
	public StringBuffer getDate( String dqyName) {
		String sql = "Select distinct qy from dcsjk_kgzb where  dqy=?  order by  qy";
		List<Map<String, Object>> list = query(sql, YW, new Object[] {dqyName });
		BuilTable table = new BuilTable();
		StringBuffer buffer = new StringBuffer();
		for(int i=0;i<list.size();i++){
			String qy = String.valueOf(list.get(i).get("qy"));
			String sql1="select * from dcsjk_kgzb where qy=? and dqy=?";
			List<Map<String, Object>> list1 = query(sql1, YW,new Object[] { qy,dqyName });
			StringBuffer qyTr = table.getQyTr(list1);
			String sql2="select * from dcsjk_kgzb_view where qy=?";
			List<Map<String, Object>> list2 = query(sql2, YW,new Object[] { qy });
			StringBuffer qyTr_sum = table.getQyTr_sum(list2);
			buffer.append(qyTr);
			buffer.append(qyTr_sum);
		}
		
		String sqlAll_sum="select *  from  dcsjk_kgzb_view2 where qy=? ";
		List<Map<String, Object>> list3 = query(sqlAll_sum, YW,new Object[]{dqyName});
		StringBuffer qyAll_sum = table.getQyTr_sum(list3);
		buffer.append(qyAll_sum);
		return buffer;
	}
	
	
	

}
