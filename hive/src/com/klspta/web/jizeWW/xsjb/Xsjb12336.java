package com.klspta.web.jizeWW.xsjb;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

public class Xsjb12336 extends AbstractBaseBean {
	public void getAllData() {
		String sql = "select jbrdw ,jbfs,xsh,jbrdz,jbrlxdh,bjbdw,djbm,to_char(djrq) djrq, wtfsd,yw_guid  from WFXSDJB ";
		List<Map<String, Object>> query = query(sql, YW);
		response(query);
	}

	// 新增线索
	public void getXzxsYW_GUID() {
		String yw_guid = null;
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = (Calendar.getInstance().get(Calendar.MONTH) + 1);
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		String first = String.valueOf(year)
				+ UtilFactory.getStrUtil().manageStr(month, 2)
				+ UtilFactory.getStrUtil().manageStr(day, 2);
		String sql = "Select count(*) num from wfxsdjb where yw_guid like '%"
				+ first + "%' ";
		List<Map<String, Object>> resuList = query(sql, AbstractBaseBean.YW);
		String count = String.valueOf(Integer.parseInt(String.valueOf(resuList
				.get(0).get("num"))) + 1);
		if (yw_guid == null) {
			yw_guid = "12336" + year
					+ UtilFactory.getStrUtil().manageStr(month, 2)
					+ UtilFactory.getStrUtil().manageStr(day, 2) + count;
		}
		// 数据库增加一笔数据
		String sql_insert = "insert into wfxsdjb(yw_guid,xsh) values(?,?)";
		int a = update(sql_insert, AbstractBaseBean.YW, new Object[] { yw_guid,
				yw_guid });
		try {
			if (a > 0) {
				response.getWriter().write(yw_guid);
			} else {
				response.getWriter().write("{yw_guid:null}");
			}
		} catch (IOException e) {
			responseException(this, "getXzxsYW_GUID", "500003", e);
			e.printStackTrace();
		}
	}

	/***************************************************************************
	 * 
	 * <br>
	 * Description: 获取12336标注坐标 <br>
	 * Author:朱波海 <br>
	 * Date:2012-12-4
	 */
	public String getPoint(String yw_guid) {
		String xy = "123";
		String sql = "select t.xy ,t.yw_guid from wfxsdjb t  where yw_guid="
				+ yw_guid;
		List<Map<String, Object>> query = query(sql, YW);
		if (query.get(0).get("xy") != null) {
			xy = query.get(0).get("xy").toString();
			return xy;
		} else {
			return xy;
		}
	}

	/***************************************************************************
	 * 
	 * <br>
	 * Description:新增信访时12336标注坐标 <br>
	 * Author:朱波海 <br>
	 * Date:2012-12-4
	 */
	public void setPoint() {
		String yw_guid = request.getParameter("yw_guid");
		String xy = request.getParameter("xy");
		String sql = "update  wfxsdjb xy=? where yw_guid=? ";
		update(sql, YW, new Object[] { xy, yw_guid });
	}

}
