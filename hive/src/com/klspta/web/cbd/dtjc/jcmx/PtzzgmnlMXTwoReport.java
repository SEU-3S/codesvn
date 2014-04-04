package com.klspta.web.cbd.dtjc.jcmx;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;

public class PtzzgmnlMXTwoReport extends AbstractBaseBean implements IDataClass{

	private static int MJ_MIN = 30;
	private static int MJ_MIDDLE = 10;
	private static int MJ_MAX = 150;
	
	private static double SR_MIN = 0.8;
	private static double SR_MIDDLE = 0.2;
	private static double SR_MAX = 5.0;
	
	@Override
	public Map<String, TRBean> getTRBeans(Object[] obj, TRBean trBean) {
		 Map<String, TRBean> trbeans = new LinkedHashMap<String, TRBean>();
	        List<Map<String, Object>> mjList = getMJ(MJ_MIN,MJ_MIDDLE,MJ_MAX);
	        List<Map<String, Object>> xmList = getKzpsr(SR_MIN,SR_MIDDLE,SR_MAX);
	        if (mjList.size() > 0) {
	            trbeans.put("mj", buildTitle(mjList));
	            build(trbeans, xmList,mjList);
	        }
	        return trbeans;
	}
	
	private List<Map<String,Object>> getMJ(int min,int middle,int max){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map= null;
		for(int i = min ;i<=max;i+=middle){
			map = new HashMap<String, Object>();
			map.put("MJ", i);
			list.add(map);
		}
		return list;
	}

	private List<Map<String,Object>> getKzpsr(double min,double middle,double max){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map= null;
		DecimalFormat df=new DecimalFormat(".#");
		while(true){
			map = new HashMap<String, Object>();
			String str = df.format(min);
			if(str.startsWith(".")){
			str = "0"+str;
			}
			map.put("SR", str);
			list.add(map);
			min += middle;
			if(min - max > 0.000001){
				break;
				
			}
		}
		return list;
	}
	
	private TRBean buildTitle(List<Map<String,Object>> list){
		TRBean trb = new TRBean();
        trb.setCssStyle("tr01");
        TDBean td = new TDBean("", "170", "");
        td.setStyle("td00");
        trb.addTDBean(td);
        for (int i = 0; i < list.size(); i++) {
            td = new TDBean(list.get(i).get("MJ").toString(), "90", "");
            trb.addTDBean(td);
        }
        return trb;
	}
	
	private void build(Map<String,TRBean> beans ,List<Map<String,Object>> list,List<Map<String,Object>> mjList){
		TRBean trb= null;
		TDBean td = null;
		for(int i =0 ; i<list.size();i++){
			trb = new TRBean();
			trb.setCssStyle("tr02");
			td = new TDBean(list.get(i).get("SR").toString(), "90", "");
            trb.addTDBean(td);
            for(int j=0;j<mjList.size();j++){
            	td = new TDBean("","90","");
            	trb.addTDBean(td);
            }
            beans.put("va"+i, trb);
		}	
	}
}
