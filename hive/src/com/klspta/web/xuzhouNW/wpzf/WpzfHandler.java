package com.klspta.web.xuzhouNW.wpzf;


import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
/**
 * 
 * <br>Title:卫片执法管理类
 * <br>Description：对空间库中的卫片图斑实现查看处理
 * <br>Author:黎春行
 * <br>Date:2013-9-10
 */
public class WpzfHandler extends AbstractBaseBean {
	public static final String HCQK_WBH = "0";
	public static final String HCQK_HF = "1";
	public static final String HCQK_WF = "2";
	public static final String HCQK_SSNYD = "3";
	public static final String WP_FORM = "WP_XZ";
	
	/**
	 * 
	 * <br>Description:获取所有未处理的图斑
	 * <br>Author:黎春行
	 * <br>Date:2013-9-10
	 */
	public void getwclTb(){		
		String sql = "select distinct yw_guid from wphcqkdjk ";
		List<Map<String, Object>> hcList = query(sql, YW);
		StringBuffer yhc = new StringBuffer();
		yhc.append(" (");
		for(int i = 0; i < hcList.size(); i++){
			yhc.append("'").append(String.valueOf(hcList.get(i).get("yw_guid"))).append("',");
		}
		yhc.append("'0')");
		
		//获取所有未处理的卫片
		String keyword = request.getParameter("keyword");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select to_char(t.objectid) objectid, to_char(t.jcbh) jcbh, t.xmc, to_char(trunc(t.shape.area, 2)) as area, to_char(substr(t.hsx, 0, 4)) as year, to_char(t.tblx) tblx from ");
		sqlBuffer.append(WP_FORM).append(" t ");
		sqlBuffer.append("where (not ( t.objectid in ").append(yhc.toString()).append("))");
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
	 * <br>Description:获取所有处理中卫片
	 * <br>Author:黎春行
	 * <br>Date:2013-9-10
	 */
	public void getclzTab(){

		String keyword = request.getParameter("keyword");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select to_char(t.objectid) objectid, to_char(t.jcbh) jcbh, t.xmc, to_char(trunc(t.shape.area, 2)) as area, to_char(substr(t.hsx, 0, 4)) as year, to_char(t.tblx) tblx from ");
		sqlBuffer.append(WP_FORM).append(" t, zfjc.wphcqkdjk j ");
		sqlBuffer.append("where t.objectid = j.yw_guid and j.hcqk='2' and (j.status is null or j.status='0')");
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
	 * <br>Description:获取所有已处理卫片
	 * <br>Author:黎春行
	 * <br>Date:2013-9-10
	 * @throws Exception 
	 */
	public void getyclTab() throws Exception{
		String type = request.getParameter("type");
		String keyword = request.getParameter("keyword");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select to_char(t.objectid) objectid, to_char(t.jcbh) jcbh, t.xmc, to_char(trunc(t.shape.area, 2)) as area, to_char(substr(t.hsx, 0, 4)) as year, to_char(t.tblx) tblx from ");
		sqlBuffer.append(WP_FORM).append(" t , zfjc.wphcqkdjk j ");
		sqlBuffer.append("where t.objectid = j.yw_guid and j.hcqk=? ");
		if(type.equals(HCQK_WF)){
			sqlBuffer.append(" and j.status = '1'");
		}
		if (keyword != null) {
            keyword = UtilFactory.getStrUtil().unescape(keyword);
            sqlBuffer.append(" and (upper(t.jcbh)||upper(t.xmc)||upper(t.shape.area)||upper(t.hsx)||upper(t.tblx) like '%");
            sqlBuffer.append(keyword);
            sqlBuffer.append("%')");
        }
		sqlBuffer.append(" order by cast(t.jcbh As int)");
		List<Map<String, Object>> wclList = query(sqlBuffer.toString(), GIS, new Object[]{type});
		response(wclList);	
	}
	
	/**
	 * 
	 * <br>Description:修改处理中卫片的处理状态
	 * <br>Author:黎春行
	 * <br>Date:2013-9-10
	 */
	public void changeStatus(){
		String objectId = request.getParameter("objectid");
		String sql = "update wphcqkdjk t set t.status = '1' where t.yw_guid = ?";
		update(sql, YW, new Object[]{objectId});
	}
	
}
