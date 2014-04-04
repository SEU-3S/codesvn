package com.klspta.model.CBDReport;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.SecondBean;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.bean.ThirdBean;

public class ThirdWorker extends AbstractBaseBean {
    private static Map<String, LinkedHashMap<String, ThirdBean>> thirdBeansMap = new LinkedHashMap<String, LinkedHashMap<String, ThirdBean>>();

    private Map<String, ThirdBean> getThirdBeans(String id) {
        if(!CBDReportManager.USE_CACHE){
            createBeans(id);
        }
        if (thirdBeansMap.containsKey(id)) {
            return thirdBeansMap.get(id);
        } else {
            if (createBeans(id)) {
                return getThirdBeans(id);
            } else {
                return null;
            }
        }
    }

    private boolean createBeans(String id) {
        String sql = "select * from RP_M_THIRD where second_key = ? order by yw_guid";
        List<Map<String, Object>> res = query(sql, YW, new Object[] { id });
        Boolean isExits = false;
        LinkedHashMap<String, ThirdBean> beansMap = new LinkedHashMap<String, ThirdBean>();
        for (int i = 0; i < res.size(); i++) {
            isExits = true;
            beansMap.put(i + "", new ThirdBean(res.get(i)));
        }
        if (isExits) {
            thirdBeansMap.put(id, beansMap);
        }
        return isExits;
    }

    public Map<String, TRBean> build(SecondBean sb, Object[] where) {
        Map<String, ThirdBean> map = getThirdBeans(sb.getFirstKey() + sb.getYWGuid());
        Map<String, TRBean> trBeans = new LinkedHashMap<String, TRBean>();
        TRBean trBean = new TRBean();
        for (int i = 0; i < map.size(); i++) {
            trBean.setCssStyle(sb.getPartType());
            ThirdBean thirdBean = map.get(i + "");
            if (thirdBean.getDataType().equals(ThirdBean.DATA_TYPE_STATIC)) {
                trBean.addTDBean(new TDBean(thirdBean), ThirdBean.DATA_TYPE_STATIC);
                if(i == map.size() - 1){
                    trBeans.put(i+sb.getYWGuid(), trBean);  
                }
            } else if (thirdBean.getDataType().equals(ThirdBean.DATA_TYPE_DYNAMIC)) {
                FourthWorker fourthWorker = new FourthWorker();
                Map<String, TRBean> c = fourthWorker.build(thirdBean, trBean, where);
                trBeans.putAll(c);
            }
        }
        
        return trBeans;
    }
}
