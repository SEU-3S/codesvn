package com.klspta.model.formulaEdit;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

public class FormulaEdit extends AbstractBaseBean{
    
    public FormulaEdit(){
    }

    public void getModelList(){
        try {
            String sql = "SELECT DISTINCT YW_GUID,FORMULA_NAME FROM RP_M_FORMULA";
            List<Map<String, Object>> list = query(sql, YW);
            response(UtilFactory.getJSONUtil().objectToJSON(list));
        } catch (Exception e) {
            response("获取失败！");
        }
    }
    
    public void getFormulaList(){
        String id = request.getParameter("id");
        try {
            String sql = "SELECT FORMULA_ID FROM RP_M_FORMULA T WHERE T.YW_GUID = ?";
            List<Map<String, Object>> list = query(sql, YW, new Object[] {id});
            response(UtilFactory.getJSONUtil().objectToJSON(list));
        } catch (Exception e) {
            response("获取失败！");
        }
    }
    
    public void getPropertyList(){
        String id = request.getParameter("id");
        List<Map<String, Object>> ret = getPropertyList(id);
        response(ret);
    }
    
    private List<Map<String, Object>> getPropertyList(String id){
        String sql = "SELECT　PROP_CN,PROP_ABBR FROM PR_M_FORMULA_PROPERTIES T WHERE T.FORMULA_ID = ?";
        List<Map<String, Object>> ret = query(sql, YW, new Object[] {id});
        return ret;
    }

    public void getFormula(){
        String id = request.getParameter("id");
        
        String sql = "SELECT * FROM RP_M_FORMULA T WHERE T.FORMULA_ID = ?";
        List<Map<String, Object>> ret = query(sql, YW, new Object[] { id});
        Map<String, Object> m = ret.get(0);
        String context = m.get("FORMULA_CONTEXT").toString();
        context = changeKeyWord(id, context, "PROP_ABBR", "PROP_CN");
        response(context);
    }
    
    private String changeKeyWord(String id, String context, String from, String to){
        List<Map<String, Object>> prop = getPropertyList(id);
        java.util.Iterator<Map<String, Object>> iter = prop.iterator();
        while(iter.hasNext()){
            Map<String, Object> a = iter.next();
            String fromString = a.get(from).toString();
            String toString = a.get(to).toString();
            context = context.replaceAll(fromString, toString);
        }
        return context;
    }
    
    public void updateFormula() throws Exception{
        String id = request.getParameter("id");
        String context = new String(request.getParameter("context").getBytes("iso-8859-1"), "UTF-8");
        context = context.replaceAll("#_#", "+");
        context = context.replaceAll("-_-gt;", ">");
        context = context.replaceAll("-_-lt;", "<");
        context = changeKeyWord(id, context, "PROP_CN", "PROP_ABBR");
        String sql = "UPDATE RP_M_FORMULA T SET T.FORMULA_CONTEXT = ? WHERE　T.FORMULA_ID = ?";
        update(sql, YW, new Object[]{context, id});
    }
}
