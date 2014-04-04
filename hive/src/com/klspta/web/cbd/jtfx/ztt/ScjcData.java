package com.klspta.web.cbd.jtfx.ztt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.web.cbd.yzt.utilList.IData;

public class ScjcData extends AbstractBaseBean implements IData{
    private static final String formName = "ZSQK";
    public static List<Map<String, Object>> List;

    @Override
    public List<Map<String, Object>> getAllList(HttpServletRequest request) {
        StringBuffer sql = new StringBuffer();
        sql.append("select * from ").append(formName);
        List<Map<String, Object>> resultList = query(sql.toString(), YW);
        List = resultList;
        return List;
    }

    @Override
    public List<Map<String, Object>> getQuery(HttpServletRequest request) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public boolean updateScjc(HttpServletRequest request) {
        String yw_guid = request.getParameter("tbbh");
        String dbChanges = request.getParameter("tbchanges");
        JSONArray js = JSONArray.fromObject(UtilFactory.getStrUtil().unescape(dbChanges));
        //System.out.println(js);
        Iterator<?> it = js.getJSONObject(0).keys();
        StringBuffer sb = new StringBuffer("update ZSQK set ");
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
        return result == 1 ? true : false;
    }
    
}
