package com.klspta.web.cbd.dtjc.zxzjgl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;

public class XmcbzjzcqkReport extends AbstractBaseBean implements IDataClass {
	private static Map<String, String[][]> titleMap = new TreeMap<String, String[][]>();
	
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
	
	private void setTitle(){
		String[][] firstTitle = {{"序号", "80", "1", "2"}, {"科目","200","1","2"},{"预算费用","100","1","2"},{"已审批资金","300","3","1"},{"审批比例","100","1","2"},{"未审批比例","100","1","2"}};
		String[][] secondTitle = {{"小计","100","1","1"},{"实际支出","100","1","1"},{"已批未付","100","1","1"}};
		titleMap.put("1", firstTitle);
		titleMap.put("2", secondTitle);
	}
	
	private List<TRBean> getTitle(){
		if(titleMap.isEmpty()){
			setTitle();
		}
		List<TRBean> trBeans = new ArrayList<TRBean>();
		for(int i = 0; i < titleMap.size(); i++){
			String key = String.valueOf(i + 1);
			TRBean trBean = new TRBean();
			if(titleMap.containsKey(key)){
				String[][] titlefields = titleMap.get(key);
				for(int j = 0; j < titlefields.length; j++){
					TDBean tdBean = new TDBean(titlefields[j][0],titlefields[j][1],"20");
					tdBean.setColspan(titlefields[j][2]);
					tdBean.setRowspan(titlefields[j][3]);
					trBean.addTDBean(tdBean);
				}
			}
			trBeans.add(trBean);
		}
		return trBeans;
	}

	private List<TRBean> getBody(String condition){
		List<TRBean> bodyList = new ArrayList<TRBean>();
		Map<String, Map<String, String>> formatData = formatData(condition);
		bodyList.add(getTrBean("一级开发费用", "1", formatData.get("subTotal")));
		bodyList.add(getTrBean("前期费用", "1.1", formatData.get("QQFY")));
		bodyList.add(getTrBean("拆迁费用", "1.2", formatData.get("CQFY")));
		bodyList.add(getTrBean("市政费用", "1.3", formatData.get("SZFY")));
		bodyList.add(getTrBean("财务费用", "1.4", formatData.get("CWFY")));
		bodyList.add(getTrBean("管理费", "1.5", formatData.get("GLFY")));
		bodyList.add(getTrBean("筹资本金返还", "2", formatData.get("CRZJFH")));
		bodyList.add(getTrBean("其他支出", "3", formatData.get("QTZC")));
		TRBean trBean = getTrBean("合计", "4", formatData.get("total"));
		trBean.setCssStyle("total");
		bodyList.add(trBean);
		return bodyList;
	}
	
	private Map<String, Map<String, String>> formatData(String condition){
		Map<String, Map<String, String>> formatdata = new TreeMap<String, Map<String,String>>();
		String sql = "select t.*, j.xmname,(t.yy + t.ey + t.sany + t.siy + t.wy + t.ly + t.qy + t.bay + t.jy + t.siyue + t.syy + t.sey) as he, t.rowid from XMZJGL_ZC t, jc_xiangmu j where t.yw_guid = j.yw_guid and j.xmname = ?";
		List<Map<String, Object>> resultList = query(sql, YW, new Object[]{condition});
		for(int i = 0; i < resultList.size(); i++){
			Map<String, Object> resultMap = resultList.get(i);
			Map<String, String> subTotalMap = new TreeMap<String, String>();
			Map<String, String> totalMap = new TreeMap<String, String>();
			Map<String, String> xmMap = new TreeMap<String, String>();
			String xj = "0";
			String sjzc = "0";
			String key = String.valueOf(resultMap.get("status"));
			String status = String.valueOf(resultMap.get("lj"));
			if(formatdata.containsKey(key)){
				xmMap = formatdata.get(key);
			}
			if(formatdata.containsKey("subTotal")){
				subTotalMap = formatdata.get("subTotal");
			}
			if(formatdata.containsKey("total")){
				totalMap = formatdata.get("total");
			}
			//计算预算费用
			String ysfy = String.valueOf(resultMap.get("ysfy"));
			String ys = "0";
			if(xmMap.containsKey("ysfy")){
				ys = xmMap.get("ysfy");
			}
			ys  = String.valueOf(Long.parseLong(ys) + Long.parseLong(ysfy));
			xmMap.put("ysfy", ys);
			
			//计算已审批资金小计
			if("总计划审批".equals(status)){
				xj = String.valueOf(resultMap.get("he"));
				xmMap.put("xj", xj);
			}
			//计算实际支出
			if("实际支出".equals(status)){
				sjzc = String.valueOf(resultMap.get("he"));
				xmMap.put("sjzc", sjzc);
			}
			
			if("QQFY".equals(key)||"CQFY".equals(key)||"SZFY".equals(key)||"CWFY".equals(key)||"GLFY".equals(key) ){
				String yjys = "0";
				String yjxj = "0";
				String yjsjzc = "0";
				if(subTotalMap.containsKey("ysfy")){
					yjys = subTotalMap.get("ysfy");
				}
				yjys = String.valueOf(Long.parseLong(yjys) + Long.parseLong(ysfy));
				subTotalMap.put("ysfy", yjys);
				
				if(subTotalMap.containsKey("xj")){
					yjxj = subTotalMap.get("xj");
				}
				yjxj = String.valueOf(Long.parseLong(yjxj) + Long.parseLong(xj));
				subTotalMap.put("xj", yjxj);
				
				if(subTotalMap.containsKey("sjzc")){
					yjsjzc = subTotalMap.get("sjzc");
				}
				yjsjzc = String.valueOf(Long.parseLong(yjsjzc) + Long.parseLong(sjzc));
				subTotalMap.put("sjzc", yjsjzc);
			}
			//添加合计
			if("QQFY".equals(key)||"CQFY".equals(key)||"SZFY".equals(key)||"CWFY".equals(key)||"GLFY".equals(key)||"CRZJFH".equals(key)||"QTZC".equals(key) ){
				String yjys = "0";
				String yjxj = "0";
				String yjsjzc = "0";
				if(totalMap.containsKey("ysfy")){
					yjys = totalMap.get("ysfy");
				}
				yjys = String.valueOf(Long.parseLong(yjys) + Long.parseLong(ysfy));
				totalMap.put("ysfy", yjys);
				
				if(totalMap.containsKey("xj")){
					yjxj = totalMap.get("xj");
				}
				yjxj = String.valueOf(Long.parseLong(yjxj) + Long.parseLong(xj));
				totalMap.put("xj", yjxj);
				
				if(totalMap.containsKey("sjzc")){
					yjsjzc = totalMap.get("sjzc");
				}
				yjsjzc = String.valueOf(Long.parseLong(yjsjzc) + Long.parseLong(sjzc));
				totalMap.put("sjzc", yjsjzc);
			}
			formatdata.put("subTotal", subTotalMap);
			formatdata.put("total", totalMap);
			formatdata.put(key, xmMap);
		}
		return formatdata;
	}
	
	private TRBean getTrBean(String firstValue, String num, Map<String, String> values){
		//String[] names = {"ysfy","xj","sjzc"};
		TRBean trBean = new TRBean();
		trBean.setCssStyle("trSingle");
		trBean.addTDBean(new TDBean(num,"80","20"));
		trBean.addTDBean(new TDBean(firstValue,"200","20"));
		String ysfy = values.get("ysfy");
		ysfy = (null == ysfy)?"0":ysfy;
		String xj = values.get("xj");
		xj = (null == xj)?"0":xj;
		String sjzc = values.get("sjzc");
		sjzc = (null == sjzc)?"0":xj;
		//计算已批未付
		String ypwf = String.valueOf(Long.parseLong(xj) - Long.parseLong(sjzc));
		//计算审批比例
		String fm = ysfy.equals("0")?"1":ysfy;
		String spbl = String.valueOf(Long.parseLong(xj)/Long.parseLong(fm));
		//计算未审批资金
		String wspzj = String.valueOf(Long.parseLong(ysfy) - Long.parseLong(xj));
		
		trBean.addTDBean(new TDBean(ysfy,"100","20"));
		trBean.addTDBean(new TDBean(xj, "100", "20"));
		trBean.addTDBean(new TDBean(sjzc, "100", "20"));
		trBean.addTDBean(new TDBean(ypwf, "100", "20"));
		trBean.addTDBean(new TDBean(spbl, "100", "20"));
		trBean.addTDBean(new TDBean(wspzj, "100", "20"));
		
		return trBean;
	}
	
 	
}
