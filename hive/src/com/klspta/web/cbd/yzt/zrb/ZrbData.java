package com.klspta.web.cbd.yzt.zrb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.base.wkt.Point;
import com.klspta.base.wkt.Polygon;
import com.klspta.base.wkt.Ring;

public class ZrbData extends AbstractBaseBean implements Runnable {
    public static final String formName = "JC_ZIRAN";
    private static final String form_gis = "CBD_ZRB";
    private static final ZrbValueChange linkChange = new ZrbValueChange();
    
    private String zrbbh = "";
    private String field = "";
    private String value = "";
    
    private static ZrbData zrbData;
    
    public static ZrbData getInstance(){
    	if(zrbData == null){
    		zrbData = new ZrbData();
    	}
    	return zrbData;
    }

    /**
     * 保存自然斑列表
     */
    public static List<Map<String, Object>> zrbList;

    /**
     * 
     * <br>Description:获取所有自然斑列表
     * <br>Author:黎春行
     * <br>Date:2013-10-18
     * @param request
     */
    public List<Map<String, Object>> getAllList(HttpServletRequest request) {
        if (zrbList == null) {
            String sql = "select * from " + formName + " t order by t.zrbbh";
            zrbList = query(sql, YW);
        }
        return zrbList;
    }
    
    /**
     * 
     * <br>Description:获取所有自然斑列表
     * <br>Author:黎春行
     * <br>Date:2013-12-9
     * @return
     */
    public List<Map<String, Object>> getZRBNameList(){
    	String sql = "select t.zrbbh from " + formName + " t order by t.zrbbh";
    	return query(sql, YW);
    }

    /**
     * 
     * <br>Description:查询自然斑
     * <br>Author:黎春行
     * <br>Date:2013-10-21
     * @param request
     * @return
     */
    public List<Map<String, Object>> getQuery(HttpServletRequest request) {
        String keyWord = request.getParameter("keyWord");
        StringBuffer querySql = new StringBuffer();
        querySql.append("select * from ").append(formName).append(" t ");
        if (keyWord != null) {
            keyWord = UtilFactory.getStrUtil().unescape(keyWord);
            querySql
                    .append("where t.yw_guid||t.zrbbh||t.zdmj||t.lzmj||t.cqgm||t.zzlzmj||t.zzcqgm||t.yjhs||t.fzzlzmj||t.fzzcqgm||t.bz like '%");
            querySql.append(keyWord).append("%'");
        } else if (zrbList != null) {
            return zrbList;
        }
        querySql.append(" order by to_number(t.yw_guid)");
        return query(querySql.toString(), YW);
    }

    /**
     * 
     * <br>Description:更新自然斑数据
     * <br>Author:黎春行
     * <br>Date:2013-10-21
     * @param request
     * @return
     * @throws Exception 
     */
    public boolean updateZrb(HttpServletRequest request) throws Exception {
        //String yw_guid = UtilFactory.getStrUtil().escape(request.getParameter("tbbh"));
        String yw_guid = new String(request.getParameter("tbbh").getBytes("iso-8859-1"), "UTF-8");
    	String dbChanges = request.getParameter("tbchanges");
        JSONArray js = JSONArray.fromObject(UtilFactory.getStrUtil().unescape(dbChanges));
        Iterator<?> it = js.getJSONObject(0).keys();
        StringBuffer sb = new StringBuffer("update jc_ziran set ");
        List<Object> list = new ArrayList<Object>();
        while (it.hasNext()) {
            String key = (String) it.next().toString();
            String value = js.getJSONObject(0).getString(key);
            sb.append(key).append("=?,");
            list.add(value);
        }
        list.add(yw_guid);
        sb.replace(sb.length() - 1, sb.length(), " where ZRBBH=?");
        int result = update(sb.toString(), YW, list.toArray());
        if(result==1){
            flush(js.getJSONObject(0),yw_guid);
        }
        linkChange.add(yw_guid);
        return result == 1 ? true : false;
    }
    
    /**
     * 
     * <br>Description:导入自然斑编号
     * <br>Author:黎春行
     * <br>Date:2013-12-12
     * @param zrb
     * @return
     */
    public boolean insertZrb(String zrb){
    	//生成序号
//    	String ywsql = "select max(to_number(t.yw_guid)) t from " + formName+ " t";
//    	String yw_guid = String.valueOf(query(ywsql, YW).get(0).get("t"));
//    	yw_guid = String.valueOf(Integer.parseInt(yw_guid) + 1);
    	String sql = "insert into " + formName + "(ZRBBH) VALUES(?)";
    	int result = update(sql, YW, new Object[]{zrb});
        sql = "select * from " + formName + " t order by t.zrbbh";
        zrbList = query(sql, YW);
    	return result == 1 ? true : false;
    }
    
    /**
     * 
     * <br>Description:TODO 方法功能描述
     * <br>Author:黎春行
     * <br>Date:2013-12-25
     * @param zrb
     * @return
     */
    public boolean delete(String zrb){
    	String sql = "delete from jc_jiben where dkmc=?";
    	update(sql, YW, new Object[]{zrb});
    	sql = "delete from " + formName + " t where t.zrbbh = ?";
    	String sqlgis = "delete from " + form_gis + " t where t.zrbbh = ?";
    	update(sqlgis, GIS, new Object[]{zrb});
    	int result = update(sql, YW, new Object[]{zrb});
    	
    	return result == 1 ? true : false;
    }
    
    public boolean modifyValue(String zrbbh, String field, String value, boolean calcu){
    	StringBuffer sqlBuffer = new StringBuffer();
    	sqlBuffer.append(" update ").append(formName);
    	sqlBuffer.append(" t set t.").append(field).append("=? where t.zrbbh=?");
    	int i = update(sqlBuffer.toString(), YW, new Object[]{value, zrbbh});
    	if(calcu){
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
    	}
    	return i == 1 ? true : false;
    }
    
    public boolean modifyValue(String zrbbh, String field, String value){
    	return modifyValue(zrbbh, field, value, true);
    }
    
    private void flush(JSONObject jObject,String guid){
        int count=zrbList.size();
        int num=-1;
        Map<String,Object> map=null;
        for(int i=0;i<count;i++){
            map=zrbList.get(i);
            if(map.get("yw_guid").equals(guid)){
                num=i;
                break;
            }
        }
        if(num!=-1){
            Iterator<?> it = jObject.keys();
            while (it.hasNext()) {
                String key = (String) it.next().toString();
                String value = jObject.getString(key);
                map.put(key, value);
            }
            zrbList.set(num,map);
        }
    }
    
    /**
     * 
     * <br>Description:上图，将自然斑保存到空间库中
     * <br>Author:黎春行
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
            boolean isExit = isExit(form_gis, "ZRBBH", tbbh, GIS);
            String sql = "";
            if(isExit){
            	sql = "update " + form_gis + " t set t.SHAPE=sde.st_geometry ('" + wkt + "', " + srid + ") where t.ZRBBH='" + tbbh + "'";
            }else{
                sql = "INSERT INTO "+ form_gis+"(OBJECTID,ZRBBH,SHAPE) VALUES ((select nvl(max(OBJECTID)+1,1) from "+form_gis+"),'"
                	+ tbbh + "',sde.st_geometry ('" + wkt + "', " + srid + "))";
            }
            update(sql, GIS);
            
            //更新自然斑面积
            String updatesql = "update zfjc." + formName + " a set(a.zdmj)=(select trunc(b.shape.area) from giser." + form_gis + " b where b.zrbbh = a.zrbbh) where a.zrbbh in (select zrbbh from giser." + form_gis + ")";
            update(updatesql, YW);
            
        } catch (Exception e) {
            System.out.println("采集坐标出错");
            return false;
        }
        return true;
    }
    
	public boolean isExit(String formName, String primaryName, String primaryValue, String type){
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
	
	ZrbData(){
		super();
	}
}
