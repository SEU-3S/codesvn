package com.klspta.web.cbd.yzt.jbb;

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

public class JbbData extends AbstractBaseBean   {
	private static final String formName = "JC_JIBEN";
	private static final String zrformName = "JC_ZIRAN";
	private static final String form_gis = "CBD_JBB";
	private static final JbdkValueChange linkChange = new JbdkValueChange();
	
	public static List<Map<String, Object>> jbbList;
	
	public List<Map<String, Object>> getAllList(HttpServletRequest request) {
		if(jbbList == null){
		    StringBuffer sql = new StringBuffer();
		    sql.append("select rownum xh,t.* from ").append(formName).append(" t") ;
	        List<Map<String, Object>> resultList = query(sql.toString(), YW);
	        jbbList = addZrb(resultList);
		}
		return jbbList;
	}

	public List<Map<String, Object>> getQuery(HttpServletRequest request) {
		String keyWord = request.getParameter("keyWord");
		StringBuffer querySql = new StringBuffer();
		querySql.append("select  rownum xh,t.* from ").append(formName).append(" t");
		if(keyWord != null){
			keyWord = UtilFactory.getStrUtil().unescape(keyWord);
			querySql.append(" where dkmc like '%");
			querySql.append(keyWord).append("%'");
		}
		List<Map<String, Object>> resultList = query(querySql.toString(), YW);
		return addZrb(resultList);
	}
	
	
	private List<Map<String, Object>> addZrb(List<Map<String, Object>> resultList){
		
		StringBuffer zrb = new StringBuffer("select t.zrbbh from " + zrformName + " t where t.jbguid = ");
		for(int i = 0; i < resultList.size(); i++){
			String zrbbh = "";
			Map<String, Object> resultMap = resultList.get(i);
			String jbGuid = String.valueOf(resultMap.get("YW_GUID"));
			List<Map<String, Object>> zrList = query(zrb.append("'"+jbGuid+"'").toString(), YW);
			if(zrList == null){
				System.out.println("是空的");
			}
			for(int j = 0; j < zrList.size(); j++){
				zrbbh += String.valueOf(zrList.get(j).get("zrbbh"));
			}
			resultList.get(i).put("zrbbh", zrbbh);
		}
		return resultList;
	}
	
	public boolean updateJbb(HttpServletRequest request){
	    String yw_guid=request.getParameter("tbbh");
        String dbChanges=request.getParameter("tbchanges");
        String zrbbh = "";
        JSONArray js=JSONArray.fromObject(UtilFactory.getStrUtil().unescape(dbChanges));
        System.out.println(js);
        Iterator<?> it = js.getJSONObject(0).keys();
        StringBuffer sb=new StringBuffer("update jc_jiben set ");
        List<Object> list=new ArrayList<Object>();
        while(it.hasNext()){        
           String key = (String) it.next().toString();
           String value= js.getJSONObject(0).getString(key);
           if("ZRBBH".equals(key.toUpperCase())){
        	   zrbbh = value;
           }
           sb.append(key).append("=?,");
           list.add(value);
        }
        list.add(yw_guid);
        sb.replace(sb.length()-1,sb.length()," where yw_guid=?");
        int result=update(sb.toString(),YW,list.toArray());
        
        //关联了自然斑后，修改基本斑数据
        refreshJBB();
        
        if(result==1){
            flush(js.getJSONObject(0),yw_guid);
        }
        return result == 1 ? true : false;
    }
	/**
	 * 
	 * <br>Description:基本斑缓存与数据库同步
	 * <br>Author:黎春行
	 * <br>Date:2013-12-11
	 */
    public void refreshJBB(){
	    StringBuffer sql = new StringBuffer();
	    sql.append("select rownum xh,t.* from ").append(formName).append(" t") ;
        List<Map<String, Object>> resultList = query(sql.toString(), YW);
        jbbList = addZrb(resultList);
    }
	
    private void flush(JSONObject jObject,String guid){
        int count=jbbList.size();
        int num=-1;
        Map<String,Object> map=null;
        for(int i=0;i<count;i++){
            map=jbbList.get(i);
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
            jbbList.set(num,map);
        }
    }
    
    public boolean modifyValue(String zrbbh, String field, String value){
    	StringBuffer sqlBuffer = new StringBuffer();
    	sqlBuffer.append(" update ").append(formName);
    	sqlBuffer.append(" t set t.").append(field).append("=? where t.dkmc=?");
    	int i = update(sqlBuffer.toString(), YW, new Object[]{value, zrbbh});
    	if(!"dkmc".equals(field)){
    		linkChange.add(zrbbh);
    	}else{
    		linkChange.add(value);
    	}
    	
    	//同步基本斑与空间库数据
    	StringBuffer synBuffer = new StringBuffer();
    	synBuffer.append("update giser.").append(form_gis);
    	synBuffer.append(" a set( a.zd, a.jsyd, a.rjl,a.jzgm, a.ghyt, a.gjjzgm, a.jzjzgm, a.szjzgm, a.zzsgm, a.zzzsgm, a.zzzshs, a.hjmj, a.fzzzsgm, a.fzzjs, a.kfcb, a.lmcb, a.dmcb, a.yjcjj , a.yjzftdsy, a.cxb, a.cqqd, a.cbfgl, a.xmguid)=(select b.zd, b.jsyd, b.rjl,b.jzgm, b.ghyt, b.gjjzgm, b.jzjzgm, b.szjzgm, b.zzsgm, b.zzzsgm, b.zzzshs, b.hjmj, b.fzzzsgm, b.fzzjs, b.kfcb, b.lmcb, b.dmcb, b.yjcjj, b.yjzftdsy, b.cxb, b.cqqd, b.cbfgl, b.xmguid from zfjc.");
    	synBuffer.append(formName).append(" b where a.tbbh = b.dkmc) where a.tbbh in (select dkmc from zfjc.").append(formName).append(")");
    	update(synBuffer.toString(), YW);
    	
    	return i == 1 ? true : false;
    }
    
    /**
     * 
     * <br>Description:上图，将基本斑保存到空间库中
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
            //判断对应tbbh是否存在,存在用update 否则 用 insert
            boolean isExit = isExit(form_gis, "TBBH", tbbh, GIS);
            String sql = "";
            if(isExit){
            	sql = "update " + form_gis + " t set t.SHAPE=sde.st_geometry ('" + wkt + "', " + srid + ") where t.TBBH='" + tbbh + "'";
            }else{
                sql = "INSERT INTO "+ form_gis+"(OBJECTID,TBBH,GISER_CBD_,SHAPE) VALUES ((select nvl(max(OBJECTID)+1,1) from "+form_gis+"),'"
                	+ tbbh + "','0',sde.st_geometry ('" + wkt + "', " + srid + "))";
            }
            update(sql, GIS);
            
            //更新自然斑面积
            String updatesql = "update zfjc." + formName + " a set(a.zd)=(select trunc(b.shape.area) from giser." + form_gis + " b where b.tbbh = a.dkmc) where a.dkmc in (select tbbh from giser." + form_gis + ")";
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
	
	public List<Map<String, Object>> getDkmcList(){
        String sql = "select t.dkmc from " + formName + " t order by to_number(t.cqqd)";
        return query(sql, YW);
    }

}
