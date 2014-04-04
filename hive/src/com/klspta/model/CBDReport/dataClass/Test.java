package com.klspta.model.CBDReport.dataClass;

import java.util.LinkedHashMap;
import java.util.Map;

import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;

public class Test implements IDataClass {

    @Override
    public Map<String, TRBean> getTRBeans(Object[] obj,TRBean trBean) {
        Map<String, TRBean> trbeans = new LinkedHashMap<String, TRBean>();
        TRBean trb = new TRBean();
        TDBean tdb = new TDBean("显示的内容", "10", "10", "");
        TDBean tdb2 = new TDBean("显示的内容2", "10", "10", "");
        TDBean tdb3 = new TDBean("显示的内容3", "10", "10", "");
        TDBean tdb4 = new TDBean("显示的内容4", "10", "10", "");
        TDBean tdb5 = new TDBean("显示的内容5", "10", "10", "");
        TDBean tdb6 = new TDBean("显示的内容6", "10", "10", "");
        trb.addTDBean(tdb);
        trb.addTDBean(tdb2);
        trb.addTDBean(tdb3);
        trb.addTDBean(tdb4);
        trb.addTDBean(tdb5);
        trb.addTDBean(tdb6);
        trbeans.put("aa", trb);
        return trbeans;
    }
}
