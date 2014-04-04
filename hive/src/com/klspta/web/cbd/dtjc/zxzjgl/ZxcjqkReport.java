package com.klspta.web.cbd.dtjc.zxzjgl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;
/**
 * 
 * <br>Title:中心资金筹集情况表
 * <br>Description:生成中心资金筹集情况
 * <br>Author:黎春行
 * <br>Date:2014-2-12
 */
public class ZxcjqkReport extends AbstractBaseBean implements IDataClass {
	
	@Override
	public Map<String, TRBean> getTRBeans(Object[] obj, TRBean trBean) {
		Map<String, TRBean> trbeans = new TreeMap<String, TRBean>();
		String condition = "";
		if(obj.length > 0){
			condition = String.valueOf(obj[0]);
		}
		List<TRBean> titleBeans = getTitle();
		List<TRBean> bodyBeans = getBody(condition);
		for(int i = 0; i < titleBeans.size(); i++){
			trbeans.put("00" + i , titleBeans.get(i));
		}
		for(int i = 0; i < bodyBeans.size(); i++){
			trbeans.put("01" + i, bodyBeans.get(i));
		}
		return trbeans;
	}
	
	/**
	 * 
	 * <br>Description:创建标题行
	 * <br>Author:黎春行
	 * <br>Date:2014-2-12
	 * @return
	 */
	private List<TRBean> getTitle(){
		List<TRBean> trBeans = new ArrayList<TRBean>();
		TRBean trBean = new TRBean();
		trBean.setCssStyle("title");
		trBean.addTDBean(new TDBean("序号", "80", "20"));
		trBean.addTDBean(new TDBean("项目", "150", "20"));
		trBean.addTDBean(new TDBean("筹集总额", "150", "20"));
		trBean.addTDBean(new TDBean("金融机构贷款", "200", "20"));
		trBean.addTDBean(new TDBean("实施主体带资", "200", "20"));
		trBean.addTDBean(new TDBean("国有土地收益基金", "200", "20"));
		trBean.addTDBean(new TDBean("出让回笼资金", "150", "20"));
		trBean.addTDBean(new TDBean("其他资金", "150", "20"));
		trBeans.add(trBean);
		return trBeans;
	}
	
	/**
	 * 
	 * <br>Description:创建内容
	 * <br>Author:黎春行
	 * <br>Date:2014-2-12
	 * @return
	 */
	private List<TRBean> getBody(String year){
		Map<String, Map<String, String>> formatMap = formatData(year);
		List<TRBean> trBeans = new ArrayList<TRBean>();
		int i = 0;
		for (String key : formatMap.keySet()) {
			i++;
			TRBean trBean = new TRBean();
			Map<String, String> xmData = formatMap.get(key);
			if("合计".equals(key)){
				continue;
				//trBean.setCssStyle("trtotal");
			}else{
				trBean.setCssStyle("trSingle");
			}
			trBean.addTDBean(new TDBean(getValue(String.valueOf(i)), "80", "20"));
			trBean.addTDBean(new TDBean(key,"150","20"));
			trBean.addTDBean(new TDBean(getValue(String.valueOf(xmData.get("CRZJ"))), "150","20"));
			trBean.addTDBean(new TDBean(getValue(String.valueOf(xmData.get("ZRJGDK"))), "200","20"));
			trBean.addTDBean(new TDBean(getValue(String.valueOf(xmData.get("SSZTDZ"))), "200","20"));
			trBean.addTDBean(new TDBean(getValue(String.valueOf(xmData.get("GYTDSYJJ"))), "200","20"));
			trBean.addTDBean(new TDBean(getValue(String.valueOf(xmData.get("CRHLZJ"))), "150","20"));
			trBean.addTDBean(new TDBean(getValue(String.valueOf(xmData.get("QTZJ"))), "150","20"));
			trBeans.add(trBean);
		}
		TRBean trBean = new TRBean();
		Map<String, String> xmData = formatMap.get("合计");
		trBean.addTDBean(new TDBean(getValue(String.valueOf(i)), "80", "20"));
		trBean.addTDBean(new TDBean("合计","150","20"));
		trBean.addTDBean(new TDBean(getValue(String.valueOf(xmData.get("CRZJ"))), "150","20"));
		trBean.addTDBean(new TDBean(getValue(String.valueOf(xmData.get("ZRJGDK"))), "200","20"));
		trBean.addTDBean(new TDBean(getValue(String.valueOf(xmData.get("SSZTDZ"))), "200","20"));
		trBean.addTDBean(new TDBean(getValue(String.valueOf(xmData.get("GYTDSYJJ"))), "200","20"));
		trBean.addTDBean(new TDBean(getValue(String.valueOf(xmData.get("CRHLZJ"))), "150","20"));
		trBean.addTDBean(new TDBean(getValue(String.valueOf(xmData.get("QTZJ"))), "150","20"));
		trBeans.add(trBean);
		
		
		return trBeans;
	}
	
	/**
	 * 
	 * <br>Description:格式化数据
	 * <br>Author:黎春行
	 * <br>Date:2014-2-12
	 * @return
	 */
	private Map<String, Map<String, String>> formatData(String year){
		String sql = "select t.yw_guid, t.lb, t.status, j.xmname, (t.yy + t.ey + t.sany + t.siy + t.wy + t.ly + t.qy + t.bay + t.jy + t.siyue + t.syy + t.sey) as he  from xmzjgl_lr t right join  jc_xiangmu j on j.yw_guid = t.yw_guid order by t.yw_guid,t.lb";
		List<Map<String , Object>> dataList = query(sql, YW);
		Map<String, Map<String, String>> formatData = new TreeMap<String, Map<String,String>>();
		Map<String, String> sumData = new TreeMap<String, String>();
		for(int i = 0; i < dataList.size();){
			Map<String, Object> dataMap = dataList.get(i);
			String preXmmc = String.valueOf(dataMap.get("xmname"));
			Map<String, String> tableData = new TreeMap<String, String>();
			while(true){
				if(i == dataList.size()){
					break;
				}
				Map<String, Object> data = dataList.get(i);
				String xmmc = String.valueOf(data.get("xmname"));
				if(!preXmmc.equals(xmmc)){
					break;
				}else{
					String type = String.valueOf(data.get("status"));
					String value = String.valueOf(data.get("he"));
					tableData.put(type, value);
					if (sumData.containsKey(type)) {
						String sumValue = sumData.get(type);
						sumValue = ("null".equals(sumValue)) ? "0" : sumValue;
						value = ("null".equals(value)) ? "0" : value;
						sumValue = String.valueOf(Long.parseLong(sumValue) + Long.parseLong(value));
						sumData.remove(type);
						sumData.put(type, sumValue);
					}else{
						sumData.put(type, value);
					}
					i++;
				}
			}
			formatData.put(preXmmc, tableData);
		}
		formatData.put("合计", sumData);
		return formatData;
	}
	
	private String getValue(String value){
		return ("null".equals(value))?"0":value;
	}

}
