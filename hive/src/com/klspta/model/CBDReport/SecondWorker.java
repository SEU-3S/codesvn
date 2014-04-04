package com.klspta.model.CBDReport;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.FirstBean;
import com.klspta.model.CBDReport.bean.SecondBean;
import com.klspta.model.CBDReport.bean.TRBean;

public class SecondWorker extends AbstractBaseBean {

	private static Map<String, LinkedHashMap<String, SecondBean>> secondBeansMap = new LinkedHashMap<String, LinkedHashMap<String, SecondBean>>();

	private Map<String, SecondBean> getSecondBeans(String id) {
        if(!CBDReportManager.USE_CACHE){
            createBeans(id);
        }
		if (secondBeansMap.containsKey(id)) {
			return secondBeansMap.get(id);
		} else {
			if (createBeans(id)) {
				return getSecondBeans(id);
			} else {
				return null;
			}
		}
	}

	private boolean createBeans(String id) {
		String sql = "select * from RP_M_SECOND where first_key = ? order by yw_guid";
		List<Map<String, Object>> res = query(sql, YW, new Object[] { id });
		Boolean isExits = false;
		LinkedHashMap<String, SecondBean> beansMap = new LinkedHashMap<String, SecondBean>();
		for (int i = 0; i < res.size(); i++) {
			isExits = true;
			beansMap.put(i + "", new SecondBean(res.get(i)));
		}
		if (isExits) {
			secondBeansMap.put(id, beansMap);
		}
		return isExits;
	}

	public Map<String, TRBean> build(FirstBean firstBean, Object[] where) {
		Map<String, SecondBean> map = getSecondBeans(firstBean.getYWGuid());
		ThirdWorker thirdWorker = new ThirdWorker();
		Map<String, TRBean> trBeans = new LinkedHashMap<String, TRBean>();
		for (int i = 0; i < map.size(); i++) {
		    Map<String, TRBean> c = thirdWorker.build(map.get(i + ""), where);
			trBeans.putAll(c);
		}
		return trBeans;
	}
}
