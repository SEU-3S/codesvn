package com.klspta.web.sanya.noticeset;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

/**
 * <br>
 * Title:督办时间配置操作类 <br>
 * Description: <br>
 * Author:赵伟 <br>
 * Date:2013-9-7
 */
public class NoticeSet extends AbstractBaseBean {
	/**
	 * <br>
	 * Description:根据传过来的案件类型来获取在数据库中的配置 <br>
	 * Author:赵伟 <br>
	 * Date:2013-9-7
	 */
	public void getConfigByType() {
		String type = UtilFactory.getStrUtil().unescape(request.getParameter("type"));
		String sql = "select * from noticeset_ t where t.type=?";
		List<Map<String, Object>> result = query(sql, YW, new String[] { type });
		response(result);
	}

	public void setConfigByType() {
		try {
			String type = UtilFactory.getStrUtil().unescape(request.getParameter("type"));
			type = (type.equals("")) ? "信访" : type;
			String t = request.getParameter("limit");
			int limit=Integer.parseInt(t);
			String shi = request.getParameter("shi");
			String fen = request.getParameter("fen");
			String sql = "update noticeset_ t set t.limit=?, t.hour=?, t.minuts=? where t.type=?";
			update(sql, YW, new String[] { t,shi, fen, type });
			response("true");
		} catch (Exception e) {
			System.out.println(e);
			response("false");
		}
	}
}
