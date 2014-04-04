package com.klspta.model.CBDReport.dataClass;

import java.util.Map;

import com.klspta.model.CBDReport.bean.TRBean;

public interface IDataClass {
    Map<String, TRBean> getTRBeans(Object[] obj,TRBean trBean);
}
