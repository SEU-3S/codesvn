package com.klspta.web.xiamen.wpzf.tdbgdc;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.base.wkt.Polygon;
import com.klspta.web.xiamen.wpzf.wpzf.WptbData;

/**
 * 
 * <br>Title:土地变更调查管理类
 * <br>Description:土地调查管理类
 * <br>Author:黎春行
 * <br>Date:2013-11-19
 */
public class TdbgdcManager extends AbstractBaseBean {
	//public static final String[][] showList_WHC = new String[][]{{"YW_GUID", "0.1","hiddlen"},{"JCBH", "0.1","监测编号"},{"JCMJ", "0.1","监测面积"},{"XZQMC", "0.1","行政区名称"},{"WPND","0.1","卫片年度"},{"SPMJ","0.1","审批面积"},{"GDMJ","0.1","供地面积"},{"HCMJ","0.1","巡查核查面积"},{"YGQK","0.1","压盖情况"},{"XF","0.1","下发"}};
	//public static final String[][] showList_QB = new String[][]{{"YW_GUID", "0.1","hiddlen"},{"JCBH", "0.1","监测编号"},{"JCMJ", "0.08","监测面积"},{"XMC", "0.1","行政区名称"},{"ND","0.1","卫片年度"},{"TBLX","0.1","图斑类型"},{"SPMJ","0.1","审批面积"},{"SPBL","0.1","审批比率"},{"GDMJ","0.1","供地面积"},{"GDBL","0.1","供地比率"},{"HCMJ","0.1","巡查核查情况"}};
	
	public void getqb(){
		//String userId = request.getParameter("userid");
		String keyword = request.getParameter("keyword");
		ItdbgdcData tdbgdc = new TdbgdcData();
		List<Map<String, Object>> queryList = tdbgdc.getList(keyword);
		response(queryList);
	}
	
	public void getyshf(){
		String userId = request.getParameter("userid");
		String keyword = request.getParameter("keyword");
		ItdbgdcData tdbgdc = new TdbgdcData();
		List<Map<String, Object>> queryList = tdbgdc.getDhcHF(userId, keyword);
		response(queryList);
	}
	
	public void getyswf(){
		String userId = request.getParameter("userid");
		String keyword = request.getParameter("keyword");
		ItdbgdcData tdbgdc = new TdbgdcData();
		List<Map<String, Object>> queryList = tdbgdc.getDhcWF(userId, keyword);
		response(queryList);
	}
		
	public void changefxqk(){
		String yw_guid = request.getParameter("yw_guid");
		String value = request.getParameter("value");
		ItdbgdcData tdbgdc = new TdbgdcData();
		String result = tdbgdc.changeFxqk(yw_guid, value);
		response(result);
	}
	
	public void getwf(){
        String keyword = request.getParameter("keyword");
        ItdbgdcData tdbgdc = new TdbgdcData();
        List<Map<String, Object>> queryList = tdbgdc.getWFList(keyword);
        response(queryList);	    	    
	}
	
	public void setWf(){
	    String yw_guid = request.getParameter("yw_guid");
	    String[] yw_guids = yw_guid.split(",");
	    String sql = "update tdbgdc t set t.iswf = '1' where t.yw_guid = ?";
	    for(int i=0;i<yw_guids.length;i++){
	        update(sql,YW,new Object[]{yw_guids[i]});
	    }
	    response("true");
	}

    public void setHf(){
        String yw_guid = request.getParameter("yw_guid");
        String[] yw_guids = yw_guid.split(",");
        String sql = "update tdbgdc t set t.iswf = '0' where t.yw_guid = ?";
        for(int i=0;i<yw_guids.length;i++){
            update(sql,YW,new Object[]{yw_guids[i]});
        }
        response("true");
    }	
    
    public void writeQd(){
        String yw_guid = request.getParameter("yw_guid");
        String selectSql = "select t.yw_guid from ajccqd t where t.yw_guid = ?";
        String insertSql = "insert into ajccqd(yw_guid) values (?)";
        List<Map<String,Object>> list = query(selectSql,YW,new Object[]{yw_guid});
        if(list.size()==0){
            update(insertSql,YW,new Object[]{yw_guid});
        }       
        try {
            String path = request.getContextPath();
            String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
            response.sendRedirect(basePath+"web/xiamen/wpzf/tdbgdc/ajccqd.jsp?jdbcname=YWTemplate&yw_guid="+yw_guid);
        } catch (IOException e) {
            e.printStackTrace();
        }       
    }
    public void getBGList(){
        String keyword =  request.getParameter("keyword");
        String where = null;
        if(keyword != null){
            keyword = UtilFactory.getStrUtil().unescape(keyword);
            where = " where (upper(t.tbbh)||upper(t.xmc) like '%" +keyword +"%')";
        }
        ItdbgdcData bgdcData = new TdbgdcData();
        List<Map<String, Object>> wptbList = bgdcData.getBGList(where);
        response(wptbList);
    }    
    
    public void getWkt(){
        String tbbh = request.getParameter("tbbh"); 
        String xzdm = request.getParameter("xzdm"); 
        String sql = "select sde.st_astext(t.shape) wkt from dlgzjctb_2013r t where t.jcbh = ? and t.xzqdm = ?";
        List<Map<String,Object>> list = query(sql,GIS,new Object[]{tbbh,xzdm});
        String wkt = (String)(list.get(0)).get("wkt");
        Polygon polygon = new Polygon(wkt);
        response(polygon.toJson());
    }
}
