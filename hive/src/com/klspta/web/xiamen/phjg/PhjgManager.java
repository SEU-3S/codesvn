package com.klspta.web.xiamen.phjg;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.wkt.Polygon;

public class PhjgManager extends AbstractBaseBean {

	public static final String[][] showList = new String[][]{{"YW_GUID","0.12","任务编号"},{"PHJGXMMC", "0.1","项目名称"},{"PHJGYDDW", "0.1","用地单位"},{"PHJGDWLX", "0.1","单位类型"},{"PHJGPZWH", "0.1","批准文号"},{"PHJGPZRQ", "0.1","批准日期"},{"PHJGHDFS","0.1","取得方式"},{"PHJGPZYT","0.1","批准用途"},{"PHJGTDZL","0.1","土地坐落"},{"PHJGTDXZ","0.1","土地现状"}};
	
	public void getList(){
		String userId = request.getParameter("userid");
		String keyword = request.getParameter("keyword");
		IphjgData phjgData = new PhjgData();
		List<Map<String, Object>> phjgList = phjgData.getList(keyword);
		response(phjgList);
	}
	
	
    public void getWkt(){
        String objectId = request.getParameter("objectId"); 
        String sql = "select sde.st_astext(t.shape) wkt from dlgzgdr t where t.objectid = ?";
        List<Map<String,Object>> list = query(sql,GIS,new Object[]{objectId});
        String wkt = (String)(list.get(0)).get("wkt");
        Polygon polygon = new Polygon(wkt);
        response(polygon.toJson());
    }
}
