package com.klspta.web.cbd.dtjc.jcmx;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;

public class PtzzgmlMXOneClReport extends AbstractBaseBean implements IDataClass{

	@Override
	public Map<String, TRBean> getTRBeans(Object[] obj, TRBean trBean) {
		Map<String, TRBean> trbeans = new LinkedHashMap<String, TRBean>();
		List<Map<String,Object>> CLlist = getGMLCL();
		if(CLlist.size()>0){
			trbeans.put("cl", buildTitle());
			buildGMLCL(trbeans,CLlist);
		}
		return trbeans;
	}

	private void buildGMLCL(Map<String, TRBean> trbeans,
			List<Map<String, Object>> llist) {
		TRBean trbean = null;
		TDBean tdbean = null;
		int i = 0;
		String[] clname = new String[]{"装修及置物比例","契税、印花税","营业税","手续费","中介费","借款还款期限","公积金贷款最高额度",
			"贷款年限年龄要求上限","月缴存公积金比例","贷款最高年限","公积金贷款利率","商业贷款基准利率","商业贷款利率浮点"};
		
		Map<String,Object> map = llist.get(0);
		for(String key : map.keySet()){
			trbean = new TRBean();
			trbean.setCssStyle("tr02");
			tdbean = new TDBean(clname[i],"180","");
			trbean.addTDBean(tdbean);
			tdbean = new TDBean(map.get(key).toString(),"120","");
			trbean.addTDBean(tdbean);
			trbeans.put(key, trbean);
			i++;
		}
		
		
		
	}

	private TRBean buildTitle(){
		TRBean trbean = new TRBean();
		trbean.setCssStyle("tr01");
		TDBean tdbean = new TDBean("购房常规涉及参数（常量）","300","");
		tdbean.setColspan("2");
		trbean.addTDBean(tdbean);
		return trbean;
	}
	
	private List<Map<String,Object>> getGMLCL(){
		List<Map<String,Object>> result = null;
		String sql = "select * from zfjc.GML_PARAMETER_CL";
		result = query(sql,YW);
		return result;
	}
	
}
