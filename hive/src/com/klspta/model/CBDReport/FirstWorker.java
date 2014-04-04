package com.klspta.model.CBDReport;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.FirstBean;
import com.klspta.model.CBDReport.bean.TRBean;

public class FirstWorker extends AbstractBaseBean{

	private static Map<String, FirstBean> firstBeansMap = new LinkedHashMap<String, FirstBean>();
	
	private FirstBean getFirstBean(String id) {
	    if(!CBDReportManager.USE_CACHE){
	        createBean(id);
	    }
		if(firstBeansMap.containsKey(id)){
			return firstBeansMap.get(id);
		}else{
			if(createBean(id)){
				return getFirstBean(id);
			}else{
				return null;
			}
		}
	}
	
	private boolean createBean(String id){
		String sql = "select * from RP_M_FIRST where yw_guid = ?";
		List<Map<String, Object>> res = query(sql, YW, new Object[]{id});
		if(res.size() > 0){
			firstBeansMap.put(id, new FirstBean(res.get(0)));
			return true;
		}else{
			return false;
		}
	}
	
    public Map<String, TRBean> build(String key, Object[] where){
        FirstBean firstBean = getFirstBean(key);
        SecondWorker secondWorker = new SecondWorker();
        return secondWorker.build(firstBean, where);
    }
    
    public FirstBean getBean(String key){
        return getFirstBean(key);
    }

}
