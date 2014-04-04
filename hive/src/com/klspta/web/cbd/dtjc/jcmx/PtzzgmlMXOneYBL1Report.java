package com.klspta.web.cbd.dtjc.jcmx;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;

public class PtzzgmlMXOneYBL1Report extends AbstractBaseBean implements IDataClass{

	private static String[] bl1_name = new String[]{"购房总费用","房屋总价款","预留装修及置物款","购房相关税费","购房相关费用",
		"最低购房首付","最低自筹购房款额度","月缴存公积金额度","可承受购房首付额度","公积金贷款金额","公积金贷款占贷款比","商业贷款金额","商业贷款依赖度"};//,"公积金贷款金额2","商业贷款金额2"};
	private static String[] bl1_id = new String[]{"gfzfy","fwzjk","ylzxjzwk","gfxgsf","gfxgfy","zdgfsf","zdzcgfked","yjcgjjed",
		"kcsgfsfed","gjjdkje","gjjdkzdkb","sydkje","sydkyld"};//,"gjjdkje2","sydkje2"};
	
	@Override
	public Map<String, TRBean> getTRBeans(Object[] obj, TRBean trBean) {
		Map<String, TRBean> trbeans = new LinkedHashMap<String, TRBean>();
	    trbeans.put("ybl1", buildTitle());
	    buildYBL(trbeans);
	    buildYBL(trbeans);
		return trbeans;
	}

	private TRBean buildTitle(){
		TRBean trbean = new TRBean();
		trbean.setCssStyle("tr01");
		TDBean tdbean = new TDBean("购房资金情况模块(因变量1)","280","");
		tdbean.setColspan("2");
		trbean.addTDBean(tdbean);
		return trbean;
	}
		
	private void buildYBL(Map<String,TRBean> map){
		for(int i=0;i<bl1_name.length;i++){
			TRBean trbean = new TRBean();
			TDBean tdbean = new TDBean(bl1_name[i],"150","");
			tdbean.setStyle("tr03");
			trbean.addTDBean(tdbean);
			tdbean = new TDBean("","130","");
			tdbean.setStyle("tr06");
			trbean.addTDBean(tdbean);
			map.put(bl1_id[i], trbean);
		}
	}
}
