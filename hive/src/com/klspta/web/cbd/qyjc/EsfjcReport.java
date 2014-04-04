package com.klspta.web.cbd.qyjc;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.model.CBDReport.CBDReportManager;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;
import com.klspta.model.CBDReport.tablestyle.ITableStyle;
import com.klspta.web.cbd.yzt.jc.report.TableStyleEditRow;
import com.klspta.web.cbd.yzt.zrb.ZrbReport;

public class EsfjcReport extends AbstractBaseBean implements IDataClass {
	private String[][] title = {{"所属<br>区域","40"},{"序号","50"},{"小区名称","180"},{"二手房总量<br>(户)","150"},{"二手房均价<br>(元/㎡)","200"},{"二手房均价涨幅(%)","200"},{"出租量(户)","100"},{"出租房均价(元/月)","200"},{"出租房均价涨幅(%)","150"},{"备注","500"}};
	private Map<String, Map<String, Map<String, Object>>> showMap = new TreeMap<String, Map<String,Map<String, Object>>>();
	private String[][] total ={{"zl","total"},{"esfjj","avg"},{"esfjjzf","avg"},{"czl","total"},{"czfjj","avg"},{"czfjjzf","avg"}};
	private DecimalFormat df = new DecimalFormat("0.00");
	@Override
	public Map<String, TRBean> getTRBeans(Object[] obj, TRBean trBean) {
		Map<String, TRBean> trbeans = new TreeMap<String, TRBean>();
		//添加标题
		trbeans.put("00", getTitle());
		//添加总计
		trbeans.putAll(getQuhj( obj));
		
		return trbeans;
	}
	
	public TRBean getTitle(){
		TRBean trBean = new TRBean();
		trBean.setCssStyle("title");
		for(int i = 0; i < title.length; i++){
			TDBean tdBean = new TDBean(title[i][0],title[i][1],"20");
			trBean.addTDBean(tdBean);
		}
		return trBean;
	}
	
	public void getBody(Object[] obj){
	    String year=obj[0].toString();
	    String month=obj[1].toString();
	    String sql = "";
	    List<Map<String, Object>> resultList = null;
	    if(obj.length>=4){
	    	sql = "select t.* from (select to_char(t.zl,'fm99999999.00')  as zl ,to_char(t.esfjj,'fm99999999.00') as esfjj ,"+
    		"to_char(t.esfjjzf,'fm99999999.00') as esfjjzf,to_char(t.czl,'fm99999999.00') as czl ,"+
    		"to_char(t.czfjj,'fm99999999.00') as czfjj,to_char(t.czfjjzf,'fm99999999.00') as czfjjzf,"+
    		"j.ssqy as ssqy , j.xqmc as xqmc, j.xqlb as xqlb,j.bz as bz from esf_zsxx t right join esf_jbxx j on t.yw_guid = j.yw_guid "+
    		"and t.year = ? and t.month=? ) t  where (t.zl||t.esfjj||t.czl||t.czfjj||t.ssqy||t.xqmc||t.xqlb||t.bz) like ?";
	    	resultList = query(sql, YW,new Object []{year,month,"%"+obj[3].toString()+"%"});
	    }else{
	    	sql = "select to_char(t.zl,'fm99999999.00')  as zl ,to_char(t.esfjj,'fm99999999.00') as esfjj ,to_char(t.esfjjzf,'fm99999999.00') as esfjjzf,to_char(t.czl,'fm99999999.00') as czl ,to_char(t.czfjj,'fm99999999.00') as czfjj,to_char(t.czfjjzf,'fm99999999.00') as czfjjzf,j.ssqy, j.xqmc, j.xqlb,j.bz, t.rowid from esf_zsxx t right join esf_jbxx j on t.yw_guid = j.yw_guid and t.year = ? and t.month=?";
	    	resultList = query(sql, YW,new Object []{year,month});
	    }
	    
		for(int i = 0; i < resultList.size(); i++){
			Map<String, Object> resultMap = resultList.get(i);
			String xqmc = String.valueOf(resultMap.get("xqmc"));
			String ssqy = String.valueOf(resultMap.get("ssqy"));
			String xqlb = String.valueOf(resultMap.get("xqlb"));
			String keytype = ssqy + "-" + xqlb;
			Map<String, Map<String, Object>> subTotalMap;
			Map<String, Object> totalMap = new TreeMap<String, Object>();
			if(showMap.containsKey(keytype)){
				subTotalMap = showMap.get(keytype);
				totalMap = subTotalMap.get(keytype);
				showMap.remove(keytype);
				subTotalMap.remove(keytype);
			}else{
				subTotalMap = new TreeMap<String, Map<String, Object>>();
				totalMap = new TreeMap<String, Object>();
			}
			for(int j = 0; j < total.length; j++){
				String value;
				if(totalMap.containsKey(total[j][0])){
					value = String.valueOf(totalMap.get(total[j][0]));
					value = ("null".equals(value)) ? "" : value;
				}else{
					value = "";
				}
				value += "," + (String)resultMap.get(total[j][0]);
				totalMap.put(total[j][0], value);
			}
			subTotalMap.put(xqmc, resultMap);
			subTotalMap.put(keytype, totalMap);
			showMap.put(keytype, subTotalMap);
		}
		
		
	}
	
	private Map<String, TRBean> getQuhj( Object[] obj){
		Map<String, TRBean> trbeans = new TreeMap<String, TRBean>();
		Map<String, Map<String, Object>> quTotalMap = new TreeMap<String, Map<String,Object>>();
		String sql = "select t.ssqy, t.xqlb from esf_jbxx t group by t.ssqy, t.xqlb order by t.ssqy";
		List<Map<String, Object>> typeList = query(sql, YW);
		Map<String, TRBean> list = new LinkedHashMap<String, TRBean>();
		String pressqy = "";
		getBody(obj);
		//生成对应数据
		for(int i = 0; i < typeList.size(); i++){
			Map<String, Object> type = typeList.get(i);
			String ssqy = String.valueOf(String.valueOf(type.get("ssqy")));
			String xqlb = String.valueOf(String.valueOf(type.get("xqlb")));
			String key = ssqy + "-" + xqlb;
			Map<String, Object> quMap;
			if(quTotalMap.containsKey(ssqy)){
				quMap = quTotalMap.get(ssqy);
				quTotalMap.remove(ssqy);
			}else{
				quMap = new TreeMap<String, Object>();
			}
			if(showMap.containsKey(key)){
				Map<String, Object> qutypeMap = showMap.get(key).get(key);
				for(int j = 0; j < total.length; j++){
					String value;
					if(quMap.containsKey(total[j][0])){
						value = String.valueOf(quMap.get(total[j][0]));
						quMap.remove(total[j][0]);
					}else{
						value = "";	
					}
					value += "," + qutypeMap.get(total[j][0]);
					quMap.put(total[j][0], value);
				}
			}else{
				continue;
			}
			quTotalMap.put(ssqy, quMap);
		}
		
		for(int i = 0; i < typeList.size(); i++){
			Map<String, Object> type = typeList.get(i);
			String ssqy = String.valueOf(String.valueOf(type.get("ssqy")));
			String xqlb = String.valueOf(String.valueOf(type.get("xqlb")));
			String key = ssqy + "-" + xqlb;
			if(!pressqy.equals(ssqy)){
				//添加区合计
				TRBean trBean = new TRBean();
				trBean.setCssStyle("trtotal");
				Map<String, Object> map = quTotalMap.get(ssqy);
				TDBean tdBean = new TDBean(ssqy + "合计", "500", "20",obj[2].toString());
				tdBean.setColspan("3");
				Map<String, Object> quMap = quTotalMap.get(ssqy);
				trBean.addTDBean(tdBean);
				for(int j = 0; j < total.length; j++){
					String name = total[j][0];
					String values = "";
					if(quMap!=null && quMap.containsKey(name)){
						values = String.valueOf(quMap.get(name));
					}
					values = values=="null"?"0":values;
					String calcutype = total[j][1];
					TDBean tdbean;
					if("total".equals(calcutype)){
						String[] value = values.split(",");
						float truevalue = 0;
						for(int t = 0; t < value.length; t++){
							value[t] = ("".equals(value[t]) || null == value[t] || "null".equals(value[t])) ?"0":value[t];
							truevalue += Float.parseFloat(value[t]);
						}
						tdbean = new TDBean(String.valueOf(truevalue), "100", "20",obj[2].toString());
					}else{
						String[] value = values.split(",");
						float truevalue = 0;
						for(int t = 0; t < value.length; t++){
							value[t] = ("".equals(value[t]) || null == value[t] || "null".equals(value[t])) ?"0":value[t];
							truevalue += Float.parseFloat(value[t]);
						}
						truevalue = truevalue/value.length;
						
						tdbean = new TDBean(df.format(truevalue).toString(), "100", "20",obj[2].toString());
					}
					trBean.addTDBean(tdbean);
					
				}
				//trBean.addTDBean(new TDBean("", "100", "20"));
				trBean.addTDBean(new TDBean("", "100", "20",obj[2].toString()));
				trbeans.put(ssqy, trBean);
				//添加详细数据
				trbeans.putAll(getsubTotalBeans(key,obj));
			}else{
				//添加详细数据
				trbeans.putAll(getsubTotalBeans(key,obj));
			}
			pressqy = ssqy;
		}
		
		//添加合计数据
		trbeans.put("01", getTotal(quTotalMap,obj));
		return trbeans;
	}
	
	private Map<String, TRBean> getsubTotalBeans(String key,Object[] obj){
		Map<String, TRBean> trbeans = new TreeMap<String, TRBean>();
		Map<String, Map<String, Object>> subMap = showMap.get(key);
		TRBean trBean = new TRBean();
		TDBean tdtotal = new TDBean(key.substring(0, key.indexOf("区")+1),"50","100",obj[2].toString());
		tdtotal.setColspan("1");
		if(subMap != null){
			tdtotal.setRowspan(String.valueOf(subMap.size()));
		}else{
			tdtotal.setRowspan("1");
			subMap = new HashMap<String, Map<String,Object>>();
		}
		trBean.addTDBean(tdtotal);
		TDBean td2 = new TDBean(key + "小计","200","20",obj[2].toString());
		td2.setColspan("2");
		trBean.addTDBean(td2);
		//处理小计
		for(int j = 0; j < total.length; j++){
			String name = total[j][0];
			Map<String, Object> quMap = subMap.get(key);
			String values = "0";
			if(quMap != null && quMap.containsKey(name)){
				values = String.valueOf(quMap.get(name));
			}
			String calcutype = total[j][1];
			TDBean tdbean;
			if("total".equals(calcutype)){
				String[] value = values.split(",");
				float truevalue = 0;
				for(int t = 0; t < value.length; t++){
					value[t] = ("".equals(value[t]) || null == value[t] || "null".equals(value[t])) ?"0":value[t];
					truevalue += Float.parseFloat(value[t]);
				}
				tdbean = new TDBean(String.valueOf(truevalue), "100", "20",obj[2].toString());
			}else{
				String[] value = values.split(",");
				float truevalue = 0;
				for(int t = 0; t < value.length; t++){
					value[t] = ("".equals(value[t]) || null == value[t] || "null".equals(value[t])) ?"0":value[t];
					truevalue += Float.parseFloat(value[t]);
				}
				truevalue = truevalue/value.length;
				tdbean = new TDBean(df.format(truevalue).toString(), "100", "20",obj[2].toString());
			}
			trBean.addTDBean(tdbean);
		}
		//trBean.addTDBean(new TDBean("", "100", "20"));
		trBean.addTDBean(new TDBean("", "100", "20",obj[2].toString()));
		trbeans.put(key, trBean);
		//处理其他数据
		int num = 1;
		Set<String> keySet = subMap.keySet();
		for(String name : keySet){
			if(!name.equals(key)){
				Map<String, Object> sonMap = subMap.get(name);
				TRBean tr = new TRBean();
				tr.setCssStyle("trsingle");
				tr.addTDBean(new TDBean(String.valueOf(num), "50","20",obj[2].toString()));
				tr.addTDBean(new TDBean(name,"180","20"));
				for(int i = 0; i < total.length; i++){
					String value = String.valueOf(sonMap.get(total[i][0]));
					value = ("null".equals(value))?"":value;
					tr.addTDBean(new TDBean(value, "100", "20"));
				}
				//tr.addTDBean(new TDBean("", "100", "20"));
				tr.addTDBean(new TDBean(String.valueOf(sonMap.get("bz")), "1000", "20",obj[2].toString()));
				trbeans.put(key + name, tr);
				num++;
			}
		}
		return trbeans;
	}
	
	public TRBean getTotal(Map<String, Map<String, Object>> quTotalMap,Object[] obj){
		TRBean trBean = new TRBean();
		trBean.setCssStyle("trtotal");
		TDBean tdBean = new TDBean("CBD合计","270","20",obj[2].toString());
		tdBean.setColspan("3");
		trBean.addTDBean(tdBean);
		Set<String> set = quTotalMap.keySet();
		for(int i = 0; i < total.length; i++){
			float value = 0;
			for(String name : set){
				float subvalue = 0;
				String truevalue = String.valueOf(quTotalMap.get(name).get(total[i][0]));
				truevalue = ("".equals(truevalue) || null == truevalue || "null".equals(truevalue)) ?"0":truevalue;
				String[] values = truevalue.split(",");
				for(int t = 0; t < values.length; t++){
					values[t] = ("".equals(values[t]) || null == values[t] || "null".equals(values[t])) ?"0":values[t];
					subvalue += Float.parseFloat(values[t]);
				}
				if(!"total".equals(total[i][1])){
					subvalue = subvalue/values.length;
				}
				value += subvalue;
			}
			if(!"total".equals(total[i][1])){
				value = value/quTotalMap.size();
			}
			trBean.addTDBean(new TDBean(df.format(value).toString(),"100","20",obj[2].toString()));
		}
		trBean.addTDBean(new TDBean("", "100", "20",obj[2].toString()));
		//trBean.addTDBean(new TDBean("", "100", "20"));
		return trBean;
	}
	

}
