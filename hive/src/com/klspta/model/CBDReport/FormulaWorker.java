package com.klspta.model.CBDReport;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;

public class FormulaWorker extends AbstractBaseBean{
    public String getFormula(String report, String formulaID){
        String sql = "select * from rp_m_formula where yw_guid = ? and formula_id = ?";
        List<Map<String, Object>> res = query(sql, YW, new Object[]{report, formulaID});
        StringBuffer sb = null;
        if(res.size() > 0){
            sb = new StringBuffer(""+res.get(0).get("FORMULA_CONTEXT"));
            return sb.toString();
        }
        return "";
    }
}
