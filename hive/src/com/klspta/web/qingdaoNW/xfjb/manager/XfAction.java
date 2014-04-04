package com.klspta.web.qingdaoNW.xfjb.manager;



import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.api.ICoordinateChangeUtil;
import com.klspta.base.util.impl.CoordinateChangeUtil;
import com.klspta.base.wkt.Point;
/**
 * 
 * <br>Title:信访举报服务
 * <br>Description:提供信访举报各个状态实例
 * <br>Author:黎春行
 * <br>Date:2013-6-21
 */
public class XfAction extends AbstractBaseBean {
	
	/**
	 * 
	 * <br>Description:根据登陆人员获取对应的待处理信访案件
	 * <br>Author:黎春行
	 * <br>Date:2013-5-21
	 * @throws Exception
	 */
	public void getDBListByUserId() throws Exception{
		String userId = request.getParameter("userId");
		String keyWord = request.getParameter("keyWord");
		response(new XfManager().getDCLListByUserId(userId, keyWord));
	}
	
	/**
	 * 
	 * <br>Description:获取登陆人员已处理的信访案件
	 * <br>Author:黎春行
	 * <br>Date:2013-5-21
	 * @throws Exception
	 */
	public void getYBListByUserId() throws Exception{
		String userId = request.getParameter("userId");
		String keyWord = request.getParameter("keyWord");
		response(new XfManager().getYCLListByUserId(userId, keyWord));
	}
	
	/**
	 * 
	 * <br>Description:获取所有信访办理中案件，案件查询时使用
	 * <br>Author:王峰
	 * <br>Date:2013-6-20
	 */
	public void getXFblzList(){
		String keyWord = request.getParameter("keyWord");
		response(new XfManager().getXFblzList(keyWord));
	}
	
	/**
	 * 
	 * <br>Description:获取所有信访已办结案件，案件查询时使用
	 * <br>Author:王峰
	 * <br>Date:2013-6-20
	 */
	public void getXFEndList(){
		String keyWord = request.getParameter("keyWord");
		String xzqh=request.getParameter("xzqh");
		response(new XfManager().getXFEndList(keyWord,xzqh));
	}
	
	/**
	 * 
	 * <br>Title: 保存标注坐标
	 * <br>Description: 
	 * <br>Author: 姚建林
	 * <br>Date: 2013-7-11
	 */
	public void saveBiaozhu(){
		String strzb = request.getParameter("strzb");
		String yw_guid = request.getParameter("yw_guid");
		String sql = "update wfxsfkxx set bzzb = ? where yw_guid = ?";
		int i = update(sql, YW, new Object[]{strzb,yw_guid});
		response(i+"");
	}
	
	/**
	 * 
	 * <br>Title: 取出标注坐标
	 * <br>Description: 
	 * <br>Author: 姚建林
	 * <br>Date: 2013-7-11
	 */
	public String getBiaozhu(String yw_guid){
		String sql = "select t.bzzb from wfxsfkxx t where t.yw_guid = ?";
		List<Map<String, Object>> result = query(sql, YW, new Object[]{yw_guid});
		if(result.get(0).get("bzzb") == null){
			return "null";
		}
		return result.get(0).get("bzzb").toString();
	}
	
	/**
	 * 
	 * <br>Title: 删除标注信息
	 * <br>Description: 
	 * <br>Author: 姚建林
	 * <br>Date: 2013-7-12
	 */
	public void deleteBiaozhu(){
		String yw_guid = request.getParameter("yw_guid");
		String sql = "update wfxsfkxx set bzzb = '' where yw_guid = ?";
		int i = update(sql, YW, new Object[]{yw_guid});
		response(i+"");
	}
	
	public void showInfo(){
	    String zb = request.getParameter("point");
	    String x = zb.split(",")[0];
	    String y = zb.split(",")[1];
	    Point p = new Point(x,y);
	    //ICoordinateChangeUtil coor = new CoordinateChangeUtil();
	    //Point point = coor.changePoint(p, ICoordinateChangeUtil.BL80_TO_PLAIN80);	    
	    String wkt = p.toWKT();
        StringBuffer ms = new StringBuffer("<table cellpadding='0' cellspacing='0'>");        
        String sql = "select t.shape.srid srid from xz_sp t where rownum =1 ";
        List<Map<String, Object>> ls = query(sql,GIS);
        String srid = "";
        if(ls.size()>0){
            srid = (ls.get(0)).get("srid").toString();
        }                
        sql = "select * from xz_sp t where sde.st_contains (t.shape,sde.st_geometry(?,?))=1";       
        ls = query(sql,GIS,new Object[]{wkt,srid});  
        ms.append("<tr><td>审批情况：</td>");
        if (ls.size() > 0) {
            Map<String, Object> map = ls.get(0);
            ms.append("<td><SPAN style='COLOR:green'>已审批:").append(map.get("xmmc")).append("</SPAN></td></tr>");
        } else {
            ms.append("<td><SPAN style='COLOR:red'>未审批</SPAN>").append("</td></tr>");
        }       
        sql = "select * from xz_gd t where sde.st_contains (t.shape,sde.st_geometry(?,?))=1";       
        ls = query(sql,GIS,new Object[]{wkt,srid});
        ms.append("<tr><td>供地情况：</td>");
        if (ls.size() > 0) {
            Map<String, Object> map = ls.get(0);
            ms.append("<td><SPAN style='COLOR:green'>已供地:").append(map.get("xmmc")).append("</SPAN></td></tr>");
        } else {
            ms.append("<td><SPAN style='COLOR:red'>未供地</SPAN>").append("</td></tr>");
        }               
        sql = "select * from xz_gh t where sde.st_contains (t.shape,sde.st_geometry(?,?))=1 and t.tdytqlxdm ='010'";       
        ls = query(sql,GIS,new Object[]{wkt,srid});    
        ms.append("<tr><td>规划情况：</td>");
        if (ls.size() > 0) {
            ms.append("<td><SPAN style='COLOR:red'>占用基本农田</SPAN>").append("</td></tr>");
        } else {
            ms.append("<td><SPAN style='COLOR:green'>未占用基本农田</SPAN>").append("</td></tr>");
        }             
        sql = "select substr(t.hsx,0,4) year from xz_wp t where sde.st_contains (t.shape,sde.st_geometry(?,?))=1";
        ls = query(sql,GIS,new Object[]{wkt,srid});
        ms.append("<tr><td>卫片情况：</td>");
        if (ls.size() > 0) {
            Map<String, Object> map = ls.get(0);
            ms.append("<td><SPAN style='COLOR:green'>发现"+map.get("year")+"年度卫片执法检查").append(
                    "</SPAN></td></tr>");
        }else {
            ms.append("<td><SPAN style='COLOR:green'>年度卫片执法检查未发现</SPAN></td></tr>");
        }
        ms.append("</table>");
	    response(ms.toString());    
	}
	
}
