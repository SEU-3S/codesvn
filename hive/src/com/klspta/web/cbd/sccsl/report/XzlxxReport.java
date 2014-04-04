package com.klspta.web.cbd.sccsl.report;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;
import com.klspta.web.cbd.sccsl.XzlzjjcManager;

public class XzlxxReport extends AbstractBaseBean implements IDataClass{
	
	private final static String[] TITLE= {"编号","写字楼名称","开发商","物业公司","投资方"};
	private final static String[] TITLE_XM ={"产品定位","产品类型","产业类型","入住企业","开盘时间","预售许可证","成本测算","楼层","标准层高(米)","外墙","采暖","供电","供水","电梯","固定车位(个)","停车位租价","使用率"};
	
	@Override
	public Map<String, TRBean> getTRBeans(Object[] obj, TRBean trBean) {
		Map<String, TRBean> trbeans = new LinkedHashMap<String, TRBean>();
		buildTitle(trbeans);
		buildXZL(trbeans);
		return trbeans;
	}

	private void buildXZL(Map<String, TRBean> trbeans) {
		List<Map<String, Object>> xzl = new XzlzjjcManager().getXZLData();
		TRBean trbean = null;
		TDBean tdbean = null;
		int i= 0;
		if(xzl!=null){
			for(Map<String,Object> map : xzl){
				trbean = new TRBean();
				Set<String> keyset = map.keySet();
				for(String key : keyset){
					if("QT".equals(key)){
						tdbean = new TDBean(map.get(key)==null?"":map.get(key).toString(),"300","");
						trbean.addTDBean(tdbean);
					}else{
						tdbean = new TDBean(map.get(key)==null?"":map.get(key).toString(),"120","");
						trbean.addTDBean(tdbean);
					}
				}
				trbeans.put("xzl"+i, trbean);
				i++;
			}
		}
	}

	private void buildTitle(Map<String, TRBean> trbeans) {
		TRBean trbean = new TRBean();
		trbean.setCssStyle("tr01");
		TDBean tdBean = null;
		for(String name : TITLE){
			tdBean = new TDBean(name, "120", "");
			tdBean.setRowspan("2");
			trbean.addTDBean(tdBean);
		}
		tdBean = new TDBean("产品", "360", "");
		tdBean.setColspan("3");
		trbean.addTDBean(tdBean);
		tdBean = new TDBean("项目", "1680", "");
		tdBean.setColspan("14");
		trbean.addTDBean(tdBean);
		tdBean = new TDBean("其它", "300", "");
		tdBean.setRowspan("2");
		trbean.addTDBean(tdBean);
		trbeans.put("title1", trbean);
		trbean = new TRBean();
		trbean.setCssStyle("tr01");
		for(String name:TITLE_XM){
			tdBean = new TDBean(name, "120", "");
			trbean.addTDBean(tdBean);
		}
		trbeans.put("title2", trbean);
	}
	
}
