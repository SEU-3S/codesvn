package com.klspta.model.CBDReport;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.FourthBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.bean.ThirdBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;

public class FourthWorker extends AbstractBaseBean{
	private static Map<String, LinkedHashMap<String, FourthBean>> fourthBeansMap = new LinkedHashMap<String, LinkedHashMap<String, FourthBean>>();

	private Map<String, FourthBean> getFourthBeans(String id) {
        if(!CBDReportManager.USE_CACHE){
            createBeans(id);
        }
		if (fourthBeansMap.containsKey(id)) {
			return fourthBeansMap.get(id);
		} else {
			if (createBeans(id)) {
				return getFourthBeans(id);
			} else {
				return null;
			}
		}
	}

	private boolean createBeans(String id) {
		String sql = "select * from RP_M_FOURTH where third_key = ?";
		List<Map<String, Object>> res = query(sql, YW, new Object[] { id });
		Boolean isExits = false;
		LinkedHashMap<String, FourthBean> beansMap = new LinkedHashMap<String, FourthBean>();
		for (int i = 0; i < res.size(); i++) {
			isExits = true;
			beansMap.put("" + i, new FourthBean(res.get(i)));
		}
		if(isExits){
			fourthBeansMap.put(id, beansMap);
		}
		return isExits;
	}
	
	public Map<String, TRBean> build(ThirdBean thirdBean, TRBean trBean, Object[] where){
		Map<String, FourthBean> map = getFourthBeans(thirdBean.getThirdKey() + thirdBean.getYWGuid());
		FourthBean fourthBean = null;
		Map<String, TRBean> trBeans = new LinkedHashMap<String, TRBean>();
		for(int i = 0; i < map.size(); i++){
		    fourthBean = map.get("" + i);
		    Map<String, TRBean> c = null;
		    if(fourthBean.getDataFrom().equals("SQL")){
		        c = localQuery(i+fourthBean.getWyGuid(), fourthBean.getDataFrom(), fourthBean.getSql(),thirdBean.getWidth(), thirdBean.getHeight(), thirdBean.getEditable(), where, trBean);
		    }else if(fourthBean.getDataFrom().equals("CLASS")){
		        c = invokeClass(fourthBean, where, trBean);
		    }
		    trBeans.putAll(c);
		}
		return trBeans;
	}
	
	private Map<String, TRBean> localQuery(String ywGuid, String from, String sql, String width, String height, String editable, Object[] where, TRBean trBean){
	    List<Map<String, Object>> rets = null;
	    if(where != null){
	        rets = query(sql, YW, where);
	    }else{
	        rets = query(sql, YW);
	    }
	    Map<String, TRBean> trBeans = new LinkedHashMap<String, TRBean>();
	    for(int i = 0; i < rets.size(); i++){
	        TRBean trCopy = trBean.copyStatic();
	        trCopy.addTDBeans(rets.get(i), width, height, editable);
	        trBeans.put(ywGuid + i, trCopy);
	    }
	    return trBeans;
	}
	
	private Map<String, TRBean> invokeClass(FourthBean fourthBean, Object[] where,TRBean trbean){
	    try {
            Class<?> c = Class.forName(fourthBean.getClassName());
            Constructor<?>[] cons = c.getDeclaredConstructors();
            IDataClass idc = (IDataClass)cons[0].newInstance();
            return idc.getTRBeans(where,trbean);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
	}
}
