package com.klspta.web.cbd.dtjc.jcmx;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;

public class PtzzgmlMXOneBlReport extends AbstractBaseBean implements IDataClass{

	@Override
	public Map<String, TRBean> getTRBeans(Object[] obj, TRBean trBean) {
		Map<String, TRBean> trbeans = new LinkedHashMap<String, TRBean>();
		List<Map<String,Object>> BLlist = getGMLBL();
		if(BLlist.size()>0){
			trbeans.put("bl1", buildTitle());
			buildGMLBL1(trbeans,BLlist);
			trbeans.put("bl2", buildTiele2());
			buildGMLBL2(trbeans,BLlist);
		}
		return trbeans;
	}

	private void buildGMLBL1(Map<String, TRBean> trbeans,
			List<Map<String, Object>> llist) {
		TRBean trbean = null;
		TDBean tdbean = null;
		int i = 0;
		String[] bl1name = new String[]{"拟购房屋面积","拟购房屋单价","房屋类型","二手房使用年数","房龄","购房类型"};
		
		for(String name : bl1name){
			trbean = new TRBean();
			trbean.setCssStyle("tr02");
			tdbean = new TDBean(name,"180","");
			trbean.addTDBean(tdbean);
			tdbean = new TDBean("","120","");
			trbean.addTDBean(tdbean);
			trbeans.put(name, trbean);
	        i++;
		}
	}
	
	private void buildGMLBL2(Map<String, TRBean> trbeans,List<Map<String,Object>> llist){
		TRBean trbean = null;
		TDBean tdbean = null;
		int i = 0;
		String[] bl2name = new String[]{"家庭月可支配收入","月用于还贷资金比例","当前存款","无息限期借款","公积金连续缴纳年数","购房人年龄"};
		
		for(String name : bl2name){
			trbean = new TRBean();
			trbean.setCssStyle("tr02");
			tdbean = new TDBean(name,"180","");
			trbean.addTDBean(tdbean);
			tdbean = new TDBean("","120","");
			trbean.addTDBean(tdbean);
			trbeans.put(name, trbean);
	        i++;
		}
	}

	private TRBean buildTitle(){
		TRBean trbean = new TRBean();
		trbean.setCssStyle("tr01");
		TDBean tdbean = new TDBean("拟购住宅房屋情况模块（自变量1）","300","");
		tdbean.setColspan("2");
		trbean.addTDBean(tdbean);
		return trbean;
	}
		
	private TRBean buildTiele2(){
		TRBean trbean = new TRBean();
		trbean.setCssStyle("tr01");
		TDBean tdbean = new TDBean("购房人基本情况模块（自变量2）","300","");
		tdbean.setColspan("2");
		trbean.addTDBean(tdbean);
		return trbean;
	}
	
	private List<Map<String,Object>> getGMLBL(){
		List<Map<String,Object>> result = null;
		String sql = "select * from zfjc.GML_PARAMETER_BL";
		result = query(sql,YW);
		return result;
	}
	
}
