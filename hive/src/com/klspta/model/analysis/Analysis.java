package com.klspta.model.analysis;

import java.util.List;
import java.util.Map;
import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.GisUtilFactory;
import com.klspta.base.wkt.Point;
import com.klspta.base.wkt.Polygon;
import com.klspta.base.wkt.Ring;

public class Analysis extends AbstractBaseBean {
      
    /**
     * 
     * <br>Description:获取压盖制定图层的属性和面积等信息
     * <br>Author:王雷
     * <br>Date:2013-11-7
     * @param layerName 图层名字
     * @param properties 图层的属性信息，多个以逗号分隔
     * @param wkt
     * @return
     */
    public List<Map<String,Object>> analysis(String layerName,String properties,String wkt){ 
 
        String querySrid = "select t.shape.srid srid from "+layerName+" t where rownum =1 ";
        List<Map<String,Object>> sridList = query(querySrid,GIS);
        String srid = "";
        if(sridList.size()>0){
            srid = (sridList.get(0)).get("srid").toString();
        }       
        String ayalySql = "select "+properties+",sde.st_area(sde.st_intersection(t.shape,sde.st_geometry(?,?))) area from "+layerName+" t where sde.st_intersects(t.shape,sde.st_geometry(?,?))=1";
        List<Map<String,Object>> list = query(ayalySql,GIS,new Object[]{wkt,srid,wkt,srid});
        return list;
    }
     

}
