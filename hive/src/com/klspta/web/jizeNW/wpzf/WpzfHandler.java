package com.klspta.web.jizeNW.wpzf;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

/**
 * 
 * <br>Title:卫片执法管理类
 * <br>Description:卫片模块中核查内容与空间库之间数据的联合查询
 * <br>Author:黎春行
 * <br>Date:2013-9-23
 */
public class WpzfHandler extends AbstractBaseBean {
	
	public static final String WP_NAME = "WP_";
	
	/**
	 * 
	 * <br>Description:获取所有未处理的卫片列表
	 * <br>Author:黎春行
	 * <br>Date:2013-9-23
	 */
	public void getWCLList(){
		String year = request.getParameter("year");
		String form_Name = WP_NAME + year;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select to_char(t.objectid) objectid, to_char(t.jcbh) jcbh, t.xmc, to_char(trunc(t.shape.area, 2)) as area, to_char(substr(t.hsx, 0, 4)) as year, to_char(t.tblx) tblx from ");
		sqlBuffer.append(form_Name).append(" t where t.jcbh not in (select substr(j.yw_guid, instr(j.yw_guid,'_') + 1) from zfjc.dc_ydqkdcb j )");
		String keyword = request.getParameter("keyword");
		if (keyword != null) {
            keyword = UtilFactory.getStrUtil().unescape(keyword);
             sqlBuffer.append(" and (upper(t.jcbh)||upper(t.xmc)||upper(t.shape.area)||upper(t.hsx)||upper(t.tblx) like '%");
             sqlBuffer.append(keyword);
             sqlBuffer.append("%')");
        }
		sqlBuffer.append(" order by cast(t.jcbh As int)");
		List<Map<String, Object>> wclList = query(sqlBuffer.toString(), GIS);
		response(wclList);
	}
	
	/**
	 * 
	 * <br>Description:获取所有已核查的卫片图斑
	 * <br>Author:黎春行
	 * <br>Date:2013-9-26
	 */
	public void getYCLList(){
		String year = request.getParameter("year");
		String form_Name = WP_NAME + year;
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select to_char(t.objectid) objectid, to_char(t.jcbh) jcbh, t.xmc, to_char(trunc(t.shape.area, 2)) as area, to_char(substr(t.hsx, 0, 4)) as year, to_char(t.tblx) tblx, j.ydsj, j.yddw, j.jsqk, j.xmmc,j.ydqk, j.dfccqk  from ");
		sqlBuffer.append(form_Name).append(" t, zfjc.dc_ydqkdcb j where  j.yw_guid like concat('%/_',t.jcbh) escape '/' ");
		String keyword = request.getParameter("keyword");
		if (keyword != null) {
            keyword = UtilFactory.getStrUtil().unescape(keyword);
             sqlBuffer.append(" and (upper(j.ydqk)||upper(j.dfccqk)||upper(t.jcbh)||upper(t.xmc)||upper(t.shape.area)||upper(t.hsx)||upper(t.tblx)||upper(j.ydsj)||upper(j.yddw)||upper(j.ydqk)||upper(j.xmmc) like '%");
             sqlBuffer.append(keyword);
             sqlBuffer.append("%')");
        }
		sqlBuffer.append(" order by cast(t.jcbh As int)");
		List<Map<String, Object>> yclList = query(sqlBuffer.toString(), GIS);
		response(yclList);
	}
	

}
