package com.klspta.web.cbd.yzt.hxxm;

import java.io.InputStream;

import com.klspta.model.CBDinsertGIS.AbstractInsertGIS;


public class HxxmInsertGIS extends AbstractInsertGIS {
	
	 private static final String form_gis = "CBD_XM";
	 private static final String formName = "JC_XIANGMU";

	@Override
	public boolean insertGIS(InputStream inputStream, String guid) {
		boolean result = false;
		try {
			String wkt = buildWKT(inputStream);
			String srid = getSrid(form_gis);
            //判断对应zrbbh是否存在,存在用update 否则 用 insert
            boolean isExit = isExit(form_gis, "XMMC", guid, GIS);
            String sql = "";
            if(isExit){
            	sql = "update " + form_gis + " t set t.SHAPE=sde.st_geometry ('" + wkt + "', " + srid + ") where t.XMMC='" + guid + "'";
            }else{
                sql = "INSERT INTO "+ form_gis+"(OBJECTID,XMMC,SHAPE) VALUES ((select nvl(max(OBJECTID)+1,1) from "+form_gis+"),'"
                	+ guid + "',sde.st_geometry ('" + wkt + "', " + srid + "))";
            }
            update(sql, GIS);
			result = true;
            String updatesql = "update zfjc." + formName + " a set(a.zd)=(select trunc(b.shape.area, 2)/10000 from giser." + form_gis + " b where b.xmmc = a.xmname) where a.xmname in (select xmmc from giser." + form_gis + ")";
            update(updatesql, YW);
            
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	
	


}
