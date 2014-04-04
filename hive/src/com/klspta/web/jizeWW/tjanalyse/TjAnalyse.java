package com.klspta.web.jizeWW.tjanalyse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import com.klspta.base.AbstractBaseBean;

/**
 * 
 * <br>Title:卫片执法检查列表
 * <br>Description:呈现卫片执法检查信息
 * <br>Author:王雷
 * <br>Date:2011-5-3
 */
public class TjAnalyse extends AbstractBaseBean{

    public String getWptbList(String xzqhdm) {
        
        String sql="select count(1) as sum,sum(zmj) as zmj,sum(xz_nyd) xz_nyd,sum(xz_gd) xz_gd,sum(xz_jsyd) xz_jsyd,sum(xz_wlyd) xz_wlyd,sum(gh_fhgh) gh_fhgh,sum(gh_xzhjsyd) gh_xzhjsyd,sum(gh_xzjsyd) gh_xzjsyd,sum(gh_bfhgh) gh_bfhgh,sum(gh_ytjjsq) gh_ytjjsq,sum(gh_xzjsq) gh_xzjsq,sum(gh_jzjsq) gh_jzjsq,sum(gh_jbnt) gh_jbnt from v_zfjc_wpzfjc_2011 t";
         List<Map<String, Object>> rows = query(sql,YW);
         List<List<?>> allRows = new ArrayList<List<?>>();
        if(rows.size()>0){
            List<String> oneRow = new ArrayList<String>();
            Map<String, Object> map = (Map<String, Object>) rows.get(0);
            oneRow.add("<b><font color=red>合计<font></b>");
            oneRow.add("<b>"+(map.get("sum")==null?0:map.get("sum")));
            oneRow.add("<b>"+(map.get("zmj")==null?0:map.get("zmj")));
            oneRow.add("<b>"+(map.get("xz_nyd")==null?0:map.get("xz_nyd")));
            oneRow.add("<b>"+"<font color=red>"+(map.get("xz_gd")==null?0:map.get("xz_gd"))+"</font>");
            oneRow.add("<b>"+(map.get("xz_jsyd")==null?0:map.get("xz_jsyd")));
            oneRow.add("<b>"+(map.get("xz_wlyd")==null?0:map.get("xz_wlyd")));
            oneRow.add("<b>"+(map.get("gh_fhgh")==null?0:map.get("gh_fhgh")));
            oneRow.add("<b>"+(map.get("gh_xzhjsyd")==null?0:map.get("gh_xzhjsyd")));
            oneRow.add("<b>"+(map.get("gh_xzjsyd")==null?0:map.get("gh_xzjsyd")));
            oneRow.add("<b>"+(map.get("gh_bfhgh")==null?0:map.get("gh_bfhgh")));
            oneRow.add("<b>"+(map.get("gh_ytjjsq")==null?0:map.get("gh_ytjjsq")));
            oneRow.add("<b>"+(map.get("gh_xzjsq")==null?0:map.get("gh_xzjsq")));
            oneRow.add("<b>"+(map.get("gh_jzjsq")==null?0:map.get("gh_jzjsq")));
            oneRow.add("<b>"+"<font color=red>"+(map.get("gh_jbnt")==null?0:map.get("gh_jbnt"))+"</font>");
            allRows.add(oneRow);
        }
        sql = "select * from v_zfjc_wpzfjc_2011 t";
        sql+="  order by t.xmc ,t.kc00 desc"; 
        rows = query(sql,YW);
        for (int i = 0; i < rows.size(); i++) {
            List<Object> oneRow = new ArrayList<Object>();
            Map<String, Object> map = (Map<String, Object>) rows.get(i);
            oneRow.add(map.get("kc00")==null?0:map.get("kc00"));
            oneRow.add(map.get("xmc")==null?0:map.get("xmc"));
            oneRow.add(map.get("zmj")==null?0:map.get("zmj"));
            oneRow.add(map.get("xz_nyd")==null?0:map.get("xz_nyd"));
            oneRow.add("<font color=red>"+(map.get("xz_gd")==null?0:map.get("xz_gd"))+"</font>");
            oneRow.add(map.get("xz_jsyd")==null?0:map.get("xz_jsyd"));
            oneRow.add(map.get("xz_wlyd")==null?0:map.get("xz_wlyd"));
            oneRow.add(map.get("gh_fhgh")==null?0:map.get("gh_fhgh"));
            oneRow.add(map.get("gh_xzhjsyd")==null?0:map.get("gh_xzhjsyd"));
            oneRow.add(map.get("gh_xzjsyd")==null?0:map.get("gh_xzjsyd"));
            oneRow.add(map.get("gh_bfhgh")==null?0:map.get("gh_bfhgh"));
            oneRow.add(map.get("gh_ytjjsq")==null?0:map.get("gh_ytjjsq"));
            oneRow.add(map.get("gh_xzjsq")==null?0:map.get("gh_xzjsq"));
            oneRow.add(map.get("gh_jzjsq")==null?0:map.get("gh_jzjsq"));
            oneRow.add("<font color=red>"+(map.get("gh_jbnt")==null?0:map.get("gh_jbnt"))+"</font>");
            allRows.add(oneRow);
        }
        return JSONArray.fromObject(allRows).toString();
    }
    /***
     * 
     * <br>Description:获取行政区划
     * <br>Author:朱波海
     * <br>Date:2013-7-4
     * @return
     */
    public String getXzqh() {
        String sql="select t.qt_ctn_code,t.na_ctn_name from public_xzqh t where t.na_gov_name is null or t.na_gov_name not like '%弃用%' order by rank";
         List<Map<String, Object>> rows = query(sql,YW);
          List<List<?>> allRows = new ArrayList<List<?>>();
        for (int i = 0; i < rows.size(); i++) {
            List<String> oneRow = new ArrayList<String>();
            Map<String, Object> map = (Map<String, Object>) rows.get(i);
            oneRow.add(map.get("qt_ctn_code").toString());
            oneRow.add(map.get("na_ctn_name").toString());
            allRows.add(oneRow);
        }
        return JSONArray.fromObject(allRows).toString();
    }
}
