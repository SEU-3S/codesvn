package com.klspta.web.xiamen.wpzf.wpzf;

import java.util.List;
import java.util.Map;
import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.base.wkt.Polygon;


public class WptbManager extends AbstractBaseBean {
	
	public void getWFlist(){
		String userId = request.getParameter("userid");
		String keyword = request.getParameter("keyword");
		WptbData wptbData = new WptbData();
		List<Map<String, Object>> wptbList = wptbData.getWFlist(userId, keyword);
		response(wptbList);	
	}
	
	
	public void getHFlist(){
		String userId = request.getParameter("userid");
		String keyword = request.getParameter("keyword");
		WptbData wptbData = new WptbData();
		List<Map<String, Object>> wptbList = wptbData.getHFlist(userId, keyword);
		response(wptbList);
		
	}
	
	
	public void getWCLlist(){
		String userId = request.getParameter("userid");
		String keyword = request.getParameter("keyword");
		WptbData wptbData = new WptbData();
		List<Map<String, Object>> wptbList = wptbData.getWCLlist(userId, keyword);
		response(wptbList);
	}
	
	public void getWPList(){
		String keyword =  request.getParameter("keyword");
		String where = null;
		if(keyword != null){
            keyword = UtilFactory.getStrUtil().unescape(keyword);
			where = " where (upper(t.tbbh)||upper(t.xjxzqhmc) like '%" +keyword +"%')";
		}
		WptbData wptbData = new WptbData();
		List<Map<String, Object>> wptbList = wptbData.getWPList(where);
		response(wptbList);
	}
	
	public void getZXDList(){
		String objectId = request.getParameter("objectId");
		String where = null;
		if(objectId != null){
			where = "where t.objectid =" + objectId;
		}
		WptbData wptbData = new WptbData();
		List<Map<String, Object>> wptbList = wptbData.getWPList(where);
		response(wptbList);
	}
	
	public void getWkt(){
	    String objectId = request.getParameter("objectId"); 
	    String sql = "select sde.st_astext(t.shape) wkt from dlgzyswftb_zfjc_2012r t where t.objectid = ?";
	    List<Map<String,Object>> list = query(sql,GIS,new Object[]{objectId});
	    String wkt = (String)(list.get(0)).get("wkt");
	    Polygon polygon = new Polygon(wkt);
	    response(polygon.toJson());
	}
	
	
	public void getReport(){
        String xzq = UtilFactory.getStrUtil().unescape(request.getParameter("xzq"));
        String start = request.getParameter("start");
        String end = request.getParameter("end");  
        
        StringBuffer sb = new StringBuffer("<table id='title' border='0' cellpadding='0' cellspacing='0' width='800' height='60' style='text-align:center; vertical-align:middle;font-family: 宋体, Arial; font-size: 18px;'><tr><td>厦门市2012年度卫片执法检查土地违法案件查处整改情况统计表</td></tr></table>"
        +"<table id='report' border='1' cellpadding='0' cellspacing='0' width='800'  style='text-align:center; vertical-align:middle;font-family: 宋体, Arial; font-size: 16px;border-collapse:collapse;border:1px #000 solid;' >");        
        sb.append("<tr></tr>");        
	    
	}
	
	public void getWpxf(){
	    String keyword = request.getParameter("keyword");
	    WptbData wptbData = new WptbData();
        List<Map<String, Object>> wptbList = wptbData.getWpxf(keyword);	    
 	    response(wptbList);
	}
    public void getWpsb(){
        String keyword = request.getParameter("keyword");
        WptbData wptbData = new WptbData();
        List<Map<String, Object>> wptbList = wptbData.getWpsb(keyword);     
        response(wptbList);
    }	
    
    public void setWf(){
        String yw_guid = request.getParameter("yw_guid");
        String[] yw_guids = yw_guid.split(",");
        String sql = "update wpzfjc t set t.iswf = '1' where t.yw_guid = ?";
        for(int i=0;i<yw_guids.length;i++){
            update(sql,YW,new Object[]{yw_guids[i]});
        }
        response("true");
    }

    public void setHf(){
        String yw_guid = request.getParameter("yw_guid");
        String[] yw_guids = yw_guid.split(",");
        String sql = "update wpzfjc t set t.iswf = '0' where t.yw_guid = ?";
        for(int i=0;i<yw_guids.length;i++){
            update(sql,YW,new Object[]{yw_guids[i]});
        }
        response("true");
    }
	
}
