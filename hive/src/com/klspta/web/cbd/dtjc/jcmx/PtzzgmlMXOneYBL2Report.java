package com.klspta.web.cbd.dtjc.jcmx;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;

public class PtzzgmlMXOneYBL2Report extends AbstractBaseBean implements IDataClass{

	@Override
	public Map<String, TRBean> getTRBeans(Object[] obj, TRBean trBean) {
		Map<String, TRBean> trbeans = new LinkedHashMap<String, TRBean>();
	    trbeans.put("ybl1", buildTitle());
	    buildCSLJG(trbeans);
	    buildCSLFX(trbeans);
	    trbeans.put("ygfx", buildTitle1());
	    buildYGFX(trbeans);
	    buildHKFX(trbeans);
		return trbeans;
		
	}

	private void buildHKFX(Map<String, TRBean> trbeans) {
		TRBean trbean = new TRBean();
		trbean.setCssStyle("tr11");
		TDBean tdbean = new TDBean("每月还款资金合计","250","");
		TDBean tdbean1 = new TDBean("","250","");
		tdbean.setColspan("2");
		tdbean1.setColspan("2");
		trbean.addTDBean(tdbean);
		trbean.addTDBean(tdbean1);
		trbeans.put("hkfx1", trbean);
		
		
		trbean = new TRBean();
		trbean.setCssStyle("tr11");
		tdbean = new TDBean("每月偿还公积金本息","250","");
		tdbean1 = new TDBean("","250","");
		tdbean.setColspan("2");
		tdbean1.setColspan("2");
		trbean.addTDBean(tdbean);
		trbean.addTDBean(tdbean1);
		trbeans.put("hkfx2", trbean);
		
		trbean = new TRBean();
		trbean.setCssStyle("tr11");
		tdbean = new TDBean("每月偿还商业贷款本息","250","");
		tdbean1 = new TDBean("","250","");
		tdbean.setColspan("2");
		tdbean1.setColspan("2");
		trbean.addTDBean(tdbean);
		trbean.addTDBean(tdbean1);
		trbeans.put("hkfx3", trbean);
		
		trbean = new TRBean();
		trbean.setCssStyle("tr11");
		tdbean = new TDBean("每月预留偿还亲友借款","250","");
		tdbean1 = new TDBean("","250","");
		tdbean.setColspan("2");
		tdbean1.setColspan("2");
		trbean.addTDBean(tdbean);
		trbean.addTDBean(tdbean1);
		trbeans.put("hkfx4", trbean);
	}

	private void buildYGFX(Map<String, TRBean> trbeans) {
		TRBean trbean = new TRBean();
		trbean.setCssStyle("tr06");
		TDBean tdbean = new TDBean("首付金款","150","");
		TDBean tdbean1 = new TDBean("30","100","");
		TDBean tdbean2 = new TDBean("公积金贷款年限","150","");
		TDBean tdbean3 = new TDBean("30","100","");
		trbean.addTDBean(tdbean);
		trbean.addTDBean(tdbean1);
		trbean.addTDBean(tdbean2);
		trbean.addTDBean(tdbean3);
		trbeans.put("ygfx1", trbean);
		
		
		trbean = new TRBean();
		trbean.setCssStyle("tr06");
		tdbean = new TDBean("商业贷款年限","150","");
		tdbean1 = new TDBean("30","100","");
		tdbean2 = new TDBean("偿还贷款年限","150","");
		tdbean3 = new TDBean("5","100","");
		trbean.addTDBean(tdbean);
		trbean.addTDBean(tdbean1);
		trbean.addTDBean(tdbean2);
		trbean.addTDBean(tdbean3);
		trbeans.put("ygfx2", trbean);
	}

	private void buildCSLFX(Map<String, TRBean> trbeans) {
		TRBean trbean = new TRBean();
		trbean.setCssStyle("tr07");
		TDBean tdbean = new TDBean("可用于购房资金","150","");
		TDBean tdbean1 = new TDBean("","100","");
		TDBean tdbean2 = new TDBean("月还款资金合计","150","");
		TDBean tdbean3 = new TDBean("","100","");
		trbean.addTDBean(tdbean);
		trbean.addTDBean(tdbean1);
		trbean.addTDBean(tdbean2);
		trbean.addTDBean(tdbean3);
		trbeans.put("fx1", trbean);
		
		
		trbean = new TRBean();
		trbean.setCssStyle("tr07");
		tdbean = new TDBean("公积金贷款预计年限","150","");
		tdbean1 = new TDBean("","100","");
		tdbean2 = new TDBean("公积金最低月供","150","");
		tdbean3 = new TDBean("","100","");
		trbean.addTDBean(tdbean);
		trbean.addTDBean(tdbean1);
		trbean.addTDBean(tdbean2);
		trbean.addTDBean(tdbean3);
		trbeans.put("fx2", trbean);
		
		trbean = new TRBean();
		trbean.setCssStyle("tr07");
		tdbean = new TDBean("商业贷款预计年限","150","");
		tdbean1 = new TDBean("","100","");
		tdbean2 = new TDBean("商业贷款最低月供","150","");
		tdbean3 = new TDBean("","100","");
		trbean.addTDBean(tdbean);
		trbean.addTDBean(tdbean1);
		trbean.addTDBean(tdbean2);
		trbean.addTDBean(tdbean3);
		trbeans.put("fx3", trbean);
		
		trbean = new TRBean();
		trbean.setCssStyle("tr07");
		tdbean1 = new TDBean("—————","150","");
		tdbean1.setColspan("2");
		tdbean2 = new TDBean("每月预留偿借款","150","");
		tdbean3 = new TDBean("","100","");
		trbean.addTDBean(tdbean1);
		trbean.addTDBean(tdbean2);
		trbean.addTDBean(tdbean3);
		trbeans.put("fx4", trbean);
	}

	private void buildCSLJG(Map<String, TRBean> trbeans) {
		TRBean trbean = new TRBean();
		trbean.setCssStyle("tr07");
		TDBean tdbean = new TDBean("","500","");
		tdbean.setColspan("4");
		trbean.addTDBean(tdbean);
		trbeans.put("result1", trbean);
		trbean = new TRBean();
		trbean.setCssStyle("tr07");
		tdbean = new TDBean("","500","");
		tdbean.setColspan("4");
		trbean.addTDBean(tdbean);
		trbeans.put("result2", trbean);
	}

	private TRBean buildTitle(){
		TRBean trbean = new TRBean();
		trbean.setCssStyle("tr01");
		TDBean tdbean = new TDBean("经济承受能力分析模块（因变量2）","500","");
		tdbean.setColspan("4");
		trbean.addTDBean(tdbean);
		return trbean;
	}
	private TRBean buildTitle1(){
		TRBean trbean = new TRBean();
		trbean.setCssStyle("tr04");
		TDBean tdbean = new TDBean("资金结构、还款年限与月供分析","500","");
		tdbean.setColspan("4");
		trbean.addTDBean(tdbean);
		return trbean;
	}
}
