package com.klspta.web.sanya.xfjb;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;

/**
 * 
 * <br>Title:信访类型管理类
 * <br>Description:TODO 类功能描述
 * <br>Author:黎春行
 * <br>Date:2013-8-30
 */
public class XflxHandler extends AbstractBaseBean {

	/**
	 * 
	 * <br>Description:获取信访类型列表（一级）
	 * <br>Author:黎春行
	 * <br>Date:2013-8-30
	 */
	public void getfirstList(){
		String sql = "select t.* from xflx t where t.flxbm = '0'";
		List<Map<String, Object>> resultList = query(sql, YW);
		response(resultList);	
	}
	
	/**
	 * 
	 * <br>Description:根据一级信访类型获取二级信访类型
	 * <br>Author:黎春行
	 * <br>Date:2013-8-30
	 */
	public void getSecondList(){
		String xflx = request.getParameter("xflx");
		String sql = "select t.* from xflx t where t.flxbm = ?";
		List<Map<String, Object>> resultList = query(sql, YW, new Object[]{xflx});
		response(resultList);
	}
	
}
