package com.klspta.web.cbd.yzt.kgzb;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.base.wkt.Point;
import com.klspta.base.wkt.Polygon;
import com.klspta.base.wkt.Ring;
import com.klspta.web.cbd.yzt.zrb.ZrbData;
import com.klspta.web.cbd.yzt.zrb.ZrbValueChange;

public class KgzbData  extends AbstractBaseBean implements Runnable{
	
	private static final String formName = "DCSJK_KGZB";
    private static final String form_gis = "CBD_KGZB";
    
private static final ZrbValueChange linkChange = new ZrbValueChange();
    
    private String zrbbh = "";
    private String field = "";
    private String value = "";
    private static KgzbData kgzbData;
    public static KgzbData getInstance(){
    	if(kgzbData == null){
    		kgzbData = new KgzbData();
    	}
    	return kgzbData;
    }
	 /**
     * 
     * <br>Description:上图，将规划指标保存到空间库中
     * <br>Author:李国明
     * <br>Date:2013-12-10
     * @param tbbh
     * @param polygon
     * @return
     * @throws Exception 
     */
    public boolean recordGIS(String tbbh, String polygons) throws Exception{
    	JSONObject json = UtilFactory.getJSONUtil().jsonToObject(polygons);
    	String rings = json.getString("rings");
    	rings = rings.replace("]]]", "]");
    	rings = rings.replace("[[[", "[");
    	String wkt = "4326";
    	String[] allPoint = rings.split(",");
		Polygon polygon = new Polygon();
		Ring ring = new Ring();
    	if(allPoint.length > 2){
    		for(int i = 0; i < allPoint.length; i+=2){
    			allPoint[i] = allPoint[i].replace("[", "");
    			double x = Double.parseDouble(allPoint[i]);
    			allPoint[i+1] = allPoint[i+1].replace("]", "");
    			double y = Double.parseDouble(allPoint[i + 1]);
    			Point point = new Point(x, y);
    			ring.putPoint(point);
    		}
    		Point p2 = new Point(Double.parseDouble(allPoint[0]), Double.parseDouble(allPoint[1]));
            ring.putPoint(p2);
            polygon.addRing(ring);
            wkt = polygon.toWKT();
    	}
        String querySrid = "select t.srid from sde.st_geometry_columns t where upper(t.table_name) = ?";
        String srid = null;
        List<Map<String, Object>> rs = query(querySrid, GIS, new Object[]{form_gis});
        try {
            if (rs.size() > 0) {
                srid = rs.get(0).get("srid") + "";
            }
            //判断对应zrbbh是否存在,存在用update 否则 用 insert
            boolean isExit = isExit(form_gis, "DKMC", tbbh, GIS);
            String sql = "";
            if(isExit){
            	sql = "update " + form_gis + " t set t.SHAPE=sde.st_geometry ('" + wkt + "', " + srid + ") where t.DKMC='" + tbbh + "'";
            }else{
                sql = "INSERT INTO "+ form_gis+"(OBJECTID,DKMC,SHAPE) VALUES ((select nvl(max(OBJECTID)+1,1) from "+form_gis+"),'"
                	+ tbbh + "',sde.st_geometry ('" + wkt + "', " + srid + "))";
            }
            update(sql, GIS);
            
        } catch (Exception e) {
            System.out.println("采集坐标出错");
            return false;
        }
        return true;
    }

	private boolean isExit(String formName, String primaryName, String primaryValue, String type){
		if("".equals(primaryName) || "".equals(primaryValue)){
			return false;
		}
		String sql = "select " + primaryName + " from " + formName + " where " + primaryName + "='" + primaryValue + "'";
		List<Map<String, Object>> list = query(sql, type);
		if(list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
    
	@Override
	public void run() {
		modifyValue(zrbbh, field, value);
	}
	
	public void setChange(String zrbbh, String field, String value) {
		this.zrbbh = zrbbh;
		this.field = field;
		this.value = value;
	}
	
	public boolean modifyValue(String zrbbh, String field, String value){
    	StringBuffer sqlBuffer = new StringBuffer();
    	sqlBuffer.append(" update ").append(formName);
    	sqlBuffer.append(" t set t.").append(field).append("=? where t.zrbbh=?");
    	int i = update(sqlBuffer.toString(), YW, new Object[]{value, zrbbh});
    	if(!"zrbbh".equals(field)){
    		linkChange.add(zrbbh);
    	}else{
    		linkChange.modifyguid(zrbbh,value);
    	}
    	//同步自然斑与空间库数据
    	StringBuffer synBuffer = new StringBuffer();
    	synBuffer.append("update giser.").append(form_gis);
    	synBuffer.append(" a set(a.zdmj, a.lzmj, a.cqgm, a.zzlzmj, a.zzcqgm, a.yjhs, a.fzzlzmj, a.fzzcqgm, a.bz)=(select b.zdmj, b.lzmj, b.cqgm,b.zzlzmj, b.zzcqgm, b.yjhs, b.fzzlzmj, b.fzzcqgm, b.bz from zfjc.");
    	synBuffer.append(formName).append(" b where a.ZRBBH = b.zrbbh) where a.ZRBBH in (select zrbbh from zfjc.").append(formName).append(")");
    	update(synBuffer.toString(), YW);
    	
    	
    	return i == 1 ? true : false;
    }
}
