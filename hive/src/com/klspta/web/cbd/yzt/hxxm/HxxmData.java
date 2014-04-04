package com.klspta.web.cbd.yzt.hxxm;

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
import com.klspta.web.cbd.yzt.jbb.JbdkValueChange;

public class HxxmData extends AbstractBaseBean {
	private static final String formName = "JC_XIANGMU";
	private static final String form_gis = "CBD_XM";
	private static final String jbformName = "JC_JIBEN";

	/**
	 * 项目数据列表
	 */
	public static List<Map<String, Object>> xmList;

	public List<Map<String, Object>> getAllList(HttpServletRequest request) {
		//if (xmList == null) {
			StringBuffer sql = new StringBuffer();
			sql.append("select rownum xh,t.* from ").append(formName).append(
					" t");
			List<Map<String, Object>> resultList = query(sql.toString(), YW);
			for (Map<String, Object> map : resultList) {
				if (map.containsKey("LMCB")) {
					if (map.get("LMCB") != "" && map.get("LMCB")!=null) {
						double lmcbtemp = Double.parseDouble(map.get("LMCB").toString());
						String lmcb = String.format("%.1f", lmcbtemp);
						map.remove("LMCB");
						map.put("LMCB", lmcb);
					}
				}
			}
			xmList = addJbb(resultList);
		//}
		return xmList;
	}

	public List<Map<String, Object>> getQuery(HttpServletRequest request) {
		String keyWord = request.getParameter("keyWord");
		StringBuffer querySql = new StringBuffer();
		querySql.append("select  rownum xh,t.* from ").append(formName).append(
				" t");
		if (keyWord != null) {
			keyWord = UtilFactory.getStrUtil().unescape(keyWord);
			querySql.append(" where xmname like '%");
			querySql.append(keyWord).append("%'");
		} else if (xmList != null) {
			return xmList;
		}
		List<Map<String, Object>> resultList = query(querySql.toString(), YW);
		return addJbb(resultList);
	}

	private List<Map<String, Object>> addJbb(
			List<Map<String, Object>> resultList) {
		String zrb = "select t.dkbh from " + jbformName
				+ " t where t.xmguid= ?";
		for (int i = 0; i < resultList.size(); i++) {
			String zrbbh = "";
			Map<String, Object> resultMap = resultList.get(i);
			String jbGuid = String.valueOf(resultMap.get("YW_GUID"));
			List<Map<String, Object>> zrList = query(zrb, YW,
					new Object[] { jbGuid });
			for (int j = 0; j < zrList.size(); j++) {
				zrbbh += String.valueOf(zrList.get(j).get("dkbh"));
			}
			resultList.get(i).put("dkbh", zrbbh);
		}
		return resultList;
	}
	
    /**
     * 
     * <br>Description:TODO 方法功能描述
     * <br>Author:黎春行
     * <br>Date:2013-12-25
     * @param zrb
     * @return
     */
    public boolean delete(String xmmc){
    	String sql = "delete from "+ form_gis + " t where t.xmmc=? ";
    	int result = 0;
    	try{
    		result = update(sql,GIS, new Object[]{xmmc});
    	}catch (Exception e) {
    		
		}finally{
			sql = "delete from " + formName + " t where t.xmname = ?";
        	result = update(sql, YW, new Object[]{xmmc});
			sql = "delete from zcgl_tdzcgl z where z.xmmc = ?";
        	update(sql,YW, new Object[]{xmmc});
		}
    	return result == 1 ? true : false;
    }

	public boolean updateHxxm(HttpServletRequest request) {
		String yw_guid = request.getParameter("tbbh");
		String dbChanges = request.getParameter("tbchanges");
		JSONArray js = JSONArray.fromObject(UtilFactory.getStrUtil().unescape(
				dbChanges));
		System.out.println(js);
		Iterator<?> it = js.getJSONObject(0).keys();
		StringBuffer sb = new StringBuffer("update jc_xiangmu set ");
		List<Object> list = new ArrayList<Object>();
		while (it.hasNext()) {
			String key = (String) it.next().toString();
			String value = js.getJSONObject(0).getString(key);
			sb.append(key).append("=?,");
			list.add(value);
		}
		list.add(yw_guid);
		sb.replace(sb.length() - 1, sb.length(), " where yw_guid=?");
		int result = update(sb.toString(), YW, list.toArray());
		if (result == 1) {
			flush(js.getJSONObject(0), yw_guid);
		}
		return result == 1 ? true : false;
	}

	private void flush(JSONObject jObject, String guid) {
		int count = xmList.size();
		int num = -1;
		Map<String, Object> map = null;
		for (int i = 0; i < count; i++) {
			map = xmList.get(i);
			if (map.get("yw_guid").equals(guid)) {
				num = i;
				break;
			}
		}
		if (num != -1) {
			Iterator<?> it = jObject.keys();
			while (it.hasNext()) {
				String key = (String) it.next().toString();
				String value = jObject.getString(key);
				map.put(key, value);
			}
			xmList.set(num, map);
		}
	}

	/**
	 * 
	 * <br>Description:修改红线项目的值
	 * <br>Author:黎春行
	 * <br>Date:2013-12-24
	 * @param xmmc
	 * @param field
	 * @param value
	 * @return
	 */
	public boolean modifyValue(String xmmc, String field, String value) {
    	StringBuffer sqlBuffer = new StringBuffer();
    	sqlBuffer.append(" update ").append(formName);
    	sqlBuffer.append(" t set t.").append(field).append("=? where t.xmname=?");
    	int i = update(sqlBuffer.toString(), YW, new Object[]{value, xmmc});
    	//如果修改的是基本斑编号
    	if("DKMC".equals(field)){
    		new JbdkValueChange().modify(value.split(",")[0]);
    	}
		return i == 1 ? true : false;
	}

	public boolean insertHxxm(String xmmc) {
		String sql = "insert into " + formName + "(xmname) values(?)";
		int result = update(sql, YW, new Object[]{xmmc});
		return result == 1 ? true : false;
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
            boolean isExit = isExit(form_gis, "xmmc", tbbh, GIS);
            String sql = "";
            if(isExit){
            	sql = "update " + form_gis + " t set t.SHAPE=sde.st_geometry ('" + wkt + "', " + srid + ") where t.xmmc='" + tbbh + "'";
            }else{
                sql = "INSERT INTO "+ form_gis+"(OBJECTID,xmmc,SHAPE) VALUES ((select nvl(max(OBJECTID)+1,1) from "+form_gis+"),'"
                	+ tbbh + "',sde.st_geometry ('" + wkt + "', " + srid + "))";
            }
            update(sql, GIS);
            
            String updatesql = "update zfjc." + formName + " a set(a.zd)=(select trunc(b.shape.area, 2)/10000 from giser." + form_gis + " b where b.xmmc = a.xmname) where a.xmname in (select xmmc from giser." + form_gis + ")";
            update(updatesql, YW);
        } catch (Exception e) {
        	e.printStackTrace();
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
}
