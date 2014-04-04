package com.klspta.web.cbd.zcgl.tdzcgl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;


import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;

public class TdzcglReport extends AbstractBaseBean implements IDataClass{
	private static Map<String, String[][]> titleMap = new TreeMap<String, String[][]>();
	public static String[][] fields;
	public static String form_extend = "dcsjk_kgzb";
	public static String form_base = "ZCGL_TDZCGL";
	
	private void SetTitle(){
		//{name,width,cols,row}
		//String[][] firstTitle = {{"项目名称","100","1","3"},{"状态","300","3","3"},{"地块名称","300","1","3"},{"规划情况","400","4","1"}};
		//String[][] secondTitle = {{"建筑用地面积(公顷)","100","1","2"},{"容积率","100","1","2"},{"规划建筑规模(万㎡)","100","1","2"},{"建筑控制高度(米)","100","1","2"},};
		//String[][] thirdTitle = {};
		String[][] firstTitle = {{"项目名称","100","1","3"},{"状态","150","3","3"},{"地块名称","100","1","3"},{"用地性质","100","1","3"},{"规划情况","400","4","1"},{"地价款情况","1400","14","1"},{"基本情况","800","8","1"},{"期间管理","400","4","1"},{"备注","100","1","3"}};
		String[][] secondTitle = {{"建筑用地面积(公顷)","100","1","2"},{"容积率","100","1","2"},{"规划建筑规模(万㎡)","100","1","2"},{"建筑控制高度(米)","100","1","2"},{"地价款","300","3","1"},{"政府收益","500","5","1"},{"补偿费","500","5","1"},
				{"地价款<br>缴纳时间","100","1","2"},{"储备证号","100","1","2"},{"证载面积<br>（公顷）","100","1","2"},{"出让时间","100","1","2"},{"中标人","100","1","2"},{"协议约定<br>交地时间","100","1","2"},{"土地<br>移交时间","100","1","2"},{"项目<br>开工时间","100","1","2"},
				{"土地<br>闲置时间","100","1","2"},{"用途","100","1","2"},{"是否盈利","100","1","2"},{"代管单位","100","1","2"},{"时限","100","1","2"}}; 
		String[][] thirdTitle = {{"总价","100","1","1"},{"已缴纳<br>万元","100","1","1"},{"已缴纳<br>比例","100","1","1"},{"总额<br>万元","100","1","1"},{"已缴纳<br>万元","100","1","1"},{"已缴纳<br>比例","100","1","1"},{"合同约定<br>缴纳时间","100","1","1"},
				{"已产生的<br>违约金<br>（万元）","100","1","1"},{"总额<br>（万元）","100","1","1"},{"已缴纳<br>万元","100","1","1"},{"已缴纳<br>比例","100","1","1"},{"合同约定<br>缴纳时间","100","1","1"},{"已产生的<br>违约费用<br>（万元）","100","1","1"}};
		
		titleMap.put("1", firstTitle);
		titleMap.put("2", secondTitle);
		titleMap.put("3", thirdTitle);
	}
	
	public void setFields(){
		String[][] fields = {{"dkmc","false","null"},{"ydxz","false","null"},{"jsydmj","false","sum"},{"rjl","false","avg"},{"ghjzgm","false","sum"},{"jzkzgd","false","avg"},{"DJZJ","false","sum"},{"DJYJLY","false","sum"},{"DJYJLB","false","null"},{"ZFSYZE","false","sum"},{"ZFSYYJLY","false","sum"}
		,{"ZFSYYJLB","false","sum"},{"ZFSYHTYD","false","null"},{"ZFSYWYJ","false","sum"},{"BCFZE","false","sum"},{"BCFYJLY","false","sum"},{"BCFYJLB","false","null"},{"BCFHTYD","false","null"},{"BCFYCSWY","false","sum"},{"DJKJLSJ","false","null"},{"CBZH","false","null"}
		,{"ZCMJ","false","sum"},{"CRSJ","false","null"},{"ZBR","false","null"},{"JDSJ","false","null"},{"YJSJ","false","null"},{"KGSJ","false","null"},{"TDXZSJ","false","null"},{"YT","false","null"},{"SFYL","false","null"},{"DGDW","false","null"}
		,{"SX","false","null"},{"bz","false","null"}};
		this.fields = fields;
	}
	
	private List<TRBean> buildTitle(){
		if(titleMap.isEmpty()){
			SetTitle();
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
	
	@Override
	public Map<String, TRBean> getTRBeans(Object[] obj, TRBean trBean) {
		String xmmc = ""; 
		Map<String, TRBean>  bodyMap = new TreeMap<String, TRBean>();
		List<TRBean> titles = buildTitle();
		//判断是否定义项目名称
		if(obj != null && obj.length > 0){
			xmmc = String.valueOf(obj[0]);
		}
		for(int i = 0; i < titles.size(); i++){
			bodyMap.put("000" + i, titles.get(i));
		}
		
		bodyMap.putAll(getBody(xmmc));
		
		return bodyMap;
	}
	
	public Map<String, TRBean> getBody(String xmmcs){
		if(fields == null){
			setFields();
		}
		//获取项目名称
		Map<String, TRBean>  bodyMap = new TreeMap<String, TRBean>();
		if("".equals(xmmcs)){
			String sql = "select distinct xmmc from " + form_base + " t ,jc_xiangmu j where t.xmmc = j.xmname";
			List<Map<String, Object>> xmmcList = query(sql, YW);
			for(int i = 0; i < xmmcList.size(); i++){
				//根据项目名称生成trs
				String xmmc = String.valueOf(xmmcList.get(i).get("xmmc"));
				bodyMap.putAll(getTrBeanMapByXMMC(xmmc, String.valueOf(i)));
				bodyMap.putAll(getTotal(xmmc));
			}
		}else{
			String[] name = xmmcs.split(",");
			for(int i = 0; i < name.length; i++){
				String xmmc = name[i];
				bodyMap.putAll(getTrBeanMapByXMMC(xmmc, String.valueOf(i)));
				bodyMap.putAll(getTotal(xmmc));
			}
		}
		return bodyMap;
	}
	
	private Map<String, TRBean> getTrBeanMapByXMMC(String xmmc, String preKey){
		Map<String, TRBean> trBeans = new LinkedHashMap<String, TRBean>();
		String sql = "select t.*, j.* from jc_xiangmu x ," + form_base + " t, " + form_extend + " j where t.dkmc = j.dkmc and x.xmname = t.xmmc and t.xmmc = ? ";
		List<Map<String, Object>> queryList = query(sql, YW, new Object[]{xmmc});
		if(queryList.size() == 0){
			return trBeans;
		}
		/*
		TRBean trBean = new TRBean();
		trBean.addTDBean(new TDBean("11","2","3"));
		trBean.addTDBean(new TDBean("11","2","3"));
		trBean.addTDBean(new TDBean("11","2","3"));
		trBean.addTDBean(new TDBean("11","2","3"));
		trBean.addTDBean(new TDBean("11","2","3"));
		trBean.addTDBean(new TDBean("11","2","3"));
		trBeans.put("01", trBean);
		*/
		//生成已出库模块
		trBeans.putAll(getYckByXMMC(xmmc, preKey, queryList));
		//生成已入库未出库模块
		trBeans.putAll(getYrkByXMMC(xmmc,preKey,queryList));
		//生成已出让但未入库项目模块
		trBeans.putAll(getYcrByXMMC(xmmc,preKey,queryList));
		//生成公共空间项目模块
		//trBeans.putAll(getGGKJByXMMC(xmmc,preKey,queryList));
		
		return trBeans;
	}
	
	/**
	 * 
	 * <br>Description:生成项目总计
	 * <br>Author:李国明
	 * <br>Date:2014-2-10
	 * @return
	 */
	private Map<String,TRBean> getTotal(String xmmc) {
		String sql = "select sum(t.djzj) as djzj ,sum(t.djyjly) as djyjly,sum(t.zfsyze) as zfsyze," +
				"sum(t.zfsyyjly) as zfsyyjly,sum(t.zfsywyj) as zfsywyj,sum(t.bcfze) as bcfze,sum(t.bcfyjly) as bcfyjly," +
				"sum(t.bcfycswy) as bcfycswyj,sum(t.zcmj) as zcmj,sum(j.jsydmj) as jsydmj,sum(j.rjl) as rjl," +
				"sum(j.ghjzgm) as ghjzgm,sum(j.jzkzgd) as jzkzgd from jc_xiangmu x ," +
			form_base + " t, " + form_extend + " j where t.dkmc = j.dkmc and x.xmname = t.xmmc and t.xmmc = ?";
		List<Map<String,Object>> list = query(sql, YW,new Object[]{xmmc});
		Map<String,Object> map = list.get(0);
		Map<String, TRBean>  bodyMap = new TreeMap<String, TRBean>();
		TRBean trBean = new TRBean();
		TDBean tdBean = new TDBean("总计","400","20");
		tdBean.setColspan("4");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean("--","100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean("--","100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean(map.get("jsydmj")==null?"0":map.get("jsydmj").toString(),"100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean(map.get("rjl")==null?"0":map.get("rjl").toString(),"100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean(map.get("ghjzgm")==null?"":map.get("ghjzgm").toString(),"100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean(map.get("jzkzgd")==null?"0":map.get("jzkzgd").toString(),"100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean(map.get("djzj")==null?"0":map.get("djzj").toString(),"100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean(map.get("djyjly")==null?"0":map.get("djyjly").toString(),"100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean("--","100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean(map.get("zfsyze")==null?"0":map.get("zfsyze").toString(),"100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean(map.get("zfsyyjly")==null?"0":map.get("zfsyyjly").toString(),"100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean("--","100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean("--","100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean(map.get("zfsywyj")==null?"0":map.get("zfsywyj").toString(),"100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean(map.get("bcfze")==null?"0":map.get("bcfze").toString(),"100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean(map.get("bcfyjly")==null?"0":map.get("bcfyjly").toString(),"100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean("--","100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean("--","100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean(map.get("bcfycswy")==null?"0":map.get("bcfycswy").toString(),"100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean("--","100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean("--","100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean(map.get("zcmj")==null?"0":map.get("zcmj").toString(),"100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean("--","100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean("--","100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean("--","100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean("--","100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean("--","100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean("--","100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean("--","100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean("--","100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean("--","100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean("--","100","20");
		trBean.addTDBean(tdBean);
		tdBean = new TDBean("--","100","20");
		trBean.addTDBean(tdBean);
		bodyMap.put("xiangmutotal", trBean);
		return bodyMap;
	}

	/**
	 * 
	 * <br>Description:生成已出库模块
	 * <br>Author:黎春行
	 * <br>Date:2014-1-16
	 * @param xmmc
	 * @param prekey
	 * @param queryList
	 * @return
	 */
	private Map<String, TRBean> getYckByXMMC(String xmmc, String prekey, List<Map<String, Object>> queryList){
		Map<String, TRBean>  bodyMap = new TreeMap<String, TRBean>();
		String size = String.valueOf(queryList.size() + 6);
		TRBean trBean = new TRBean();
		//trBean.setCssStyle("trSingle");
		
		TDBean tdBean = new TDBean(xmmc, "100","20");
		tdBean.setRowspan(size);
		trBean.addTDBean(tdBean);
		
		TDBean statuBean = new TDBean("已出库","300","20");
		statuBean.setColspan("3");
		int num = 1;
		Vector<TRBean> trbeans = new Vector<TRBean>();
		Vector<String> subTotal = new Vector<String>();
		for(int i = 0; i < queryList.size(); i++){
			Map<String, Object> queryMap = queryList.get(i);
			if("已出库".equals(String.valueOf(queryMap.get("THIRSTA")))){
				TRBean tryck = new TRBean();
				tryck.setCssStyle("trSingle");
				for(int j = 0; j < fields.length; j++){
					String[] field = fields[j];
					String value = String.valueOf(queryMap.get(field[0]));
					value = (value == "null")?"":value;
					tryck.addTDBean(new TDBean(value,"100","20",field[1]));
					if(j == 0){
						if(subTotal.size() > j){
							subTotal.remove(j);
						}
						subTotal.add(j,"小计");
					}else{
						String totalValue;
						if(j >= subTotal.size()){
							totalValue = "0";
						}else{
							totalValue = subTotal.elementAt(j);
						}
						if("null".equals(field[2])){
							totalValue = "--";
						}else if("sum".equals(field[2]) || "avg".equals(field[2])){
							try{
								Float sumValue = Float.parseFloat(value);
								totalValue = String.valueOf(Float.parseFloat(totalValue) + sumValue);
							}catch (Exception e) {
							}
						}
						if(subTotal.size() > j){
							subTotal.remove(j);
						}
						subTotal.add(j, totalValue);
					}
				}
				trbeans.add(tryck);
				num++;
			}
		}
		
		statuBean.setRowspan(String.valueOf(num));
		trBean.addTDBean(statuBean);
		for(int i = 0; i < subTotal.size(); i++){
			trBean.addTDBean(new TDBean(subTotal.get(i),"100","20"));
		}
		
		bodyMap.put(prekey + "01", trBean);
		for(int i = 0; i < trbeans.size(); i++){
			bodyMap.put(prekey + "01" + (i+1), trbeans.get(i));
		}
		return bodyMap;
	}
	/**
	 * 
	 * <br>Description:生成已入库未出库模块
	 * <br>Author:黎春行
	 * <br>Date:2014-1-16
	 * @param xmmc
	 * @param prekey
	 * @return
	 */
	private Map<String, TRBean> getYrkByXMMC(String xmmc, String prekey, List<Map<String, Object>> queryList){
		Map<String, TRBean>  bodyMap = new TreeMap<String, TRBean>();
		int firstTotal = 4;
		int dckTotal = 2;
		int swgyTotal = 2;
		int dqlTotal = 1;
		int wscTotal = 1;
		int wgdTotal = 1;
		int cqkcTotal = 1;
		
		Vector<TRBean> dqlBeans  = new Vector<TRBean>();
		Vector<TRBean> wscBeans  = new Vector<TRBean>();
		Vector<TRBean> wgdBeans  = new Vector<TRBean>();
		Vector<TRBean> cqkcBeans  = new Vector<TRBean>();
		
		Vector<String> dqlTo = new Vector<String>();
		Vector<String> wscTo = new Vector<String>();
		Vector<String> wgdTo = new Vector<String>();
		Vector<String> cqkcTo = new Vector<String>();
		
		for(int i = 0; i < queryList.size(); i++){
			Map<String, Object> queryMap = queryList.get(i);
			String status = String.valueOf(queryMap.get("THIRSTA"));
			if("待清理".equals(status)){
				TRBean tryck = new TRBean();
				tryck.setCssStyle("trSingle");
				for(int j = 0; j < fields.length; j++){
					String[] field = fields[j];
					String value = String.valueOf(queryMap.get(field[0]));
					value = (value == "null")?"":value;
					tryck.addTDBean(new TDBean(value,"100","20",field[1]));
					if(j == 0){
						if(dqlTo.size() > j){
							dqlTo.remove(j);
						}
						dqlTo.add(j,"小计");
					}else{
						String totalValue;
						if(j >= dqlTo.size()){
							totalValue = "0";
						}else{
							totalValue = dqlTo.elementAt(j);
						}
						if("null".equals(field[2])){
							totalValue = "--";
						}else if("sum".equals(field[2]) || "avg".equals(field[2])){
							try{
								Float sumValue = Float.parseFloat(value);
								totalValue = String.valueOf(Float.parseFloat(totalValue) + sumValue);
							}catch (Exception e) {
							}
						}
						if(dqlTo.size() > j){
							dqlTo.remove(j);
						}
						dqlTo.add(j, totalValue);
					}
				}
				dqlBeans.add(tryck);
				dqlTotal++;
				dckTotal++;
				firstTotal++;
			}else if("未受偿".equals(status)){
				TRBean tryck = new TRBean();
				tryck.setCssStyle("trSingle");
				for(int j = 0; j < fields.length; j++){
					String[] field = fields[j];
					String value = String.valueOf(queryMap.get(field[0]));
					value = (value == "null")?"":value;
					tryck.addTDBean(new TDBean(value,"100","20",field[1]));
					if(j == 0){
						if(wscTo.size() > j){
							wscTo.remove(j);
						}
						wscTo.add(j,"小计");
					}else{
						String totalValue;
						if(j >= wscTo.size()){
							totalValue = "0";
						}else{
							totalValue = wscTo.elementAt(j);
						}
						if("null".equals(field[2])){
							totalValue = "--";
						}else if("sum".equals(field[2]) || "avg".equals(field[2])){
							try{
								Float sumValue = Float.parseFloat(value);
								totalValue = String.valueOf(Float.parseFloat(totalValue) + sumValue);
							}catch (Exception e) {
							}
						}
						if(wscTo.size() > j){
							wscTo.remove(j);
						}
						wscTo.add(j, totalValue);
					}
				}
				wscBeans.add(tryck);
				wscTotal++;
				dckTotal++;
				firstTotal++;
			}else if("未供地".equals(status)){
				TRBean tryck = new TRBean();
				tryck.setCssStyle("trSingle");
				for(int j = 0; j < fields.length; j++){
					String[] field = fields[j];
					String value = String.valueOf(queryMap.get(field[0]));
					value = (value == "null")?"":value;
					tryck.addTDBean(new TDBean(value,"100","20",field[1]));
					if(j == 0){
						if(wgdTo.size() > j){
							wgdTo.remove(j);
						}
						wgdTo.add(j,"小计");
					}else{
						String totalValue;
						if(j >= wgdTo.size()){
							totalValue = "0";
						}else{
							totalValue = wgdTo.elementAt(j);
						}
						if("null".equals(field[2])){
							totalValue = "--";
						}else if("sum".equals(field[2]) || "avg".equals(field[2])){
							try{
								Float sumValue = Float.parseFloat(value);
								totalValue = String.valueOf(Float.parseFloat(totalValue) + sumValue);
							}catch (Exception e) {
							}
						}
						if(wgdTo.size() > j){
							wgdTo.remove(j);
						}
						wgdTo.add(j, totalValue);
					}
				}
				wgdBeans.add(tryck);
				wgdTotal++;
				swgyTotal++;
				firstTotal++;
			}else if("长期库存".equals(status)){
				TRBean tryck = new TRBean();
				tryck.setCssStyle("trSingle");
				for(int j = 0; j < fields.length; j++){
					String[] field = fields[j];
					String value = String.valueOf(queryMap.get(field[0]));
					value = (value == "null")?"":value;
					tryck.addTDBean(new TDBean(value,"100","20",field[1]));
					if(j == 0){
						if(cqkcTo.size() > j){
							cqkcTo.remove(j);
						}
						cqkcTo.add(j,"小计");
					}else{
						String totalValue;
						if(j >= cqkcTo.size()){
							totalValue = "0";
						}else{
							totalValue = cqkcTo.elementAt(j);
						}
						if("null".equals(field[2])){
							totalValue = "--";
						}else if("sum".equals(field[2]) || "avg".equals(field[2])){
							try{
								Float sumValue = Float.parseFloat(value);
								totalValue = String.valueOf(Float.parseFloat(totalValue) + sumValue);
							}catch (Exception e) {
							}
						}
						if(cqkcTo.size() > j){
							cqkcTo.remove(j);
						}
						cqkcTo.add(j, totalValue);
					}
				}
				cqkcBeans.add(tryck);
				cqkcTotal++;
				firstTotal++;
				swgyTotal++;
			}
		}
		
		//待清理
		TRBean trBean = new TRBean();
		TDBean tdBean = new TDBean("已入库<br>未出库","100","20");
		tdBean.setRowspan(String.valueOf(firstTotal));
		trBean.addTDBean(tdBean);
		tdBean = new TDBean("待出库<br>项目","100","20");
		tdBean.setRowspan(String.valueOf(dckTotal));
		trBean.addTDBean(tdBean);
		tdBean = new TDBean("待清理","100","20");
		tdBean.setRowspan(String.valueOf(dqlTotal));
		trBean.addTDBean(tdBean);
		if(dqlTo.isEmpty()){
			trBean.addTDBean(new TDBean("小计","100","20"));
			for(int i = 0; i < 31; i++){
				trBean.addTDBean(new TDBean(" ","100","20"));
			}
		}else{
			for(int i = 0; i < dqlTo.size(); i++){
				trBean.addTDBean(new TDBean(dqlTo.get(i),"100","20"));
			}
		}
		bodyMap.put(prekey + "02", trBean);
		for(int i = 0; i < dqlBeans.size(); i++){
			bodyMap.put(prekey + "02" + (i + 1), dqlBeans.get(i));
		}
		
		//未受偿
		trBean = new TRBean();
		tdBean = new TDBean("未受偿","100","20");
		tdBean.setRowspan(String.valueOf(wscTotal));
		trBean.addTDBean(tdBean);
		if(wscTo.isEmpty()){
			trBean.addTDBean(new TDBean("小计","100","20"));
			for(int i = 0; i < 31; i++){
				trBean.addTDBean(new TDBean("","100","20"));
			}
		}else{
			for(int i = 0; i < wscTo.size(); i++){
				trBean.addTDBean(new TDBean(wscTo.get(i),"100","20"));
			}
		}
		bodyMap.put(prekey + "03", trBean);
		for(int i = 0; i < wscBeans.size(); i++){
			bodyMap.put(prekey + "03" + (i + 1), wscBeans.get(i));
		}
		
		//未供地
		trBean = new TRBean();
		tdBean = new TDBean("尚未供应<br>项目","100","20");
		tdBean.setRowspan(String.valueOf(swgyTotal));
		trBean.addTDBean(tdBean);
		tdBean = new TDBean("未供地","100","20");
		tdBean.setRowspan(String.valueOf(wgdTotal));
		trBean.addTDBean(tdBean);
		if(wgdTo.isEmpty()){
			trBean.addTDBean(new TDBean("小计","100","20"));
			for(int i = 0; i < 31; i++){
				trBean.addTDBean(new TDBean(" ","100","20"));
			}
		}else{
			for(int i = 0; i < wgdTo.size(); i++){
				trBean.addTDBean(new TDBean(wgdTo.get(i),"100","20"));
			}
		}
		bodyMap.put(prekey + "04", trBean);
		for(int i = 0; i < wgdBeans.size(); i++){
			bodyMap.put(prekey + "04" + (i + 1), wgdBeans.get(i));
		}
		
		//长期库存
		trBean = new TRBean();
		tdBean = new TDBean("长期库存","100","20");
		tdBean.setRowspan(String.valueOf(cqkcTotal));
		trBean.addTDBean(tdBean);
		if(cqkcTo.isEmpty()){
			trBean.addTDBean(new TDBean("小计","100","20"));
			for(int i = 0; i < 31; i++){
				trBean.addTDBean(new TDBean("","100","20"));
			}
		}else{
			for(int i = 0; i < wscTo.size(); i++){
				trBean.addTDBean(new TDBean(cqkcTo.get(i),"100","20"));
			}
		}
		bodyMap.put(prekey + "05", trBean);
		for(int i = 0; i < cqkcBeans.size(); i++){
			bodyMap.put(prekey + "05" + (i + 1), cqkcBeans.get(i));
		}
		return bodyMap;
	}
	/**
	 * 
	 * <br>Description:生成已出让但未入库项目模块
	 * <br>Author:黎春行
	 * <br>Date:2014-1-16
	 * @param xmmc
	 * @param prekey
	 * @return
	 */
	private Map<String, TRBean> getYcrByXMMC(String xmmc, String prekey, List<Map<String, Object>> queryList){
		Map<String, TRBean>  bodyMap = new TreeMap<String, TRBean>();
		String size = String.valueOf(queryList.size() + 6);
		TRBean trBean = new TRBean();
		//trBean.setCssStyle("trSingle");
				
		TDBean statuBean = new TDBean("已出让但未入库项目","300","20");
		statuBean.setColspan("3");
		int num = 1;
		Vector<TRBean> trbeans = new Vector<TRBean>();
		Vector<String> subTotal = new Vector<String>();
		for(int i = 0; i < queryList.size(); i++){
			Map<String, Object> queryMap = queryList.get(i);
			if("已出让但未入库项目".equals(String.valueOf(queryMap.get("THIRSTA")))){
				TRBean tryck = new TRBean();
				tryck.setCssStyle("trSingle");
				for(int j = 0; j < fields.length; j++){
					String[] field = fields[j];
					String value = String.valueOf(queryMap.get(field[0]));
					value = (value == "null")?"":value;
					tryck.addTDBean(new TDBean(value,"100","20",field[1]));
					if(j == 0){
						if(subTotal.size() > j){
							subTotal.remove(j);
						}
						subTotal.add("小计");
					}else{
						String totalValue;
						if(j >= subTotal.size()){
							totalValue = "0";
						}else{
							totalValue = subTotal.elementAt(j);
						}
						if("null".equals(field[2])){
							totalValue = "--";
						}else if("sum".equals(field[2]) || "avg".equals(field[2])){
							try{
								Float sumValue = Float.parseFloat(value);
								totalValue = String.valueOf(Float.parseFloat(totalValue) + sumValue);
							}catch (Exception e) {
							}
						}
						if(subTotal.size() > j){
							subTotal.remove(j);
						}
						subTotal.add(j, totalValue);
					}
				}
				trbeans.add(tryck);
				num++;
			}
		}
		
		statuBean.setRowspan(String.valueOf(num));
		trBean.addTDBean(statuBean);
		for(int i = 0; i < subTotal.size(); i++){
			trBean.addTDBean(new TDBean(subTotal.get(i),"100","20"));
		}
		
		bodyMap.put(prekey + "06", trBean);
		for(int i = 0; i < trbeans.size(); i++){
			bodyMap.put(prekey + "06" + (i+1), trbeans.get(i));
		}
		return bodyMap;
	}
	/**
	 * 
	 * <br>Description:生成公共空间项目模块
	 * <br>Author:黎春行
	 * <br>Date:2014-1-16
	 * @param xmmc
	 * @param prekey
	 * @return
	 */
	private Map<String, TRBean> getGGKJByXMMC(String xmmc, String prekey, List<Map<String, Object>> queryList){
		Map<String, TRBean>  bodyMap = new TreeMap<String, TRBean>();
		String size = String.valueOf(queryList.size() + 6);
		TRBean trBean = new TRBean();
		//trBean.setCssStyle("trSingle");
		TDBean statuBean = new TDBean("公共空间","100","20");
		statuBean.setColspan("3");
		int num = 1;
		Vector<TRBean> trbeans = new Vector<TRBean>();
		Vector<String> subTotal = new Vector<String>();
		for(int i = 0; i < queryList.size(); i++){
			Map<String, Object> queryMap = queryList.get(i);
			if("代征绿地 ".equals(String.valueOf(queryMap.get("THIRSTA"))) || "代征道路 ".equals(String.valueOf(queryMap.get("THIRSTA")))){
				TRBean tryck = new TRBean();
				tryck.setCssStyle("trSingle");
				for(int j = 0; j < fields.length; j++){
					String[] field = fields[j];
					String value = String.valueOf(queryMap.get(field[0]));
					value = (value == "null")?"":value;
					tryck.addTDBean(new TDBean(value,"100","20",field[1]));
					if(j == 0){
						if(subTotal.size() > j){
							subTotal.remove(j);
						}
						subTotal.add("小计");
					}else{
						String totalValue;
						if(j >= subTotal.size()){
							totalValue = "0";
						}else{
							totalValue = subTotal.elementAt(j);
						}
						if("null".equals(field[2])){
							totalValue = "--";
						}else if("sum".equals(field[2]) || "avg".equals(field[2])){
							try{
								Float sumValue = Float.parseFloat(value);
								totalValue = String.valueOf(Float.parseFloat(totalValue) + sumValue);
							}catch (Exception e) {
							}
						}
						if(subTotal.size() > j){
							subTotal.remove(j);
						}
						subTotal.add(j, totalValue);
					}
				}
				trbeans.add(tryck);
				num++;
			}
		}
		
		statuBean.setRowspan(String.valueOf(num));
		trBean.addTDBean(statuBean);
		for(int i = 0; i < subTotal.size(); i++){
			trBean.addTDBean(new TDBean(subTotal.get(i),"100","20"));
		}
		
		bodyMap.put(prekey + "07", trBean);
		for(int i = 0; i < trbeans.size(); i++){
			bodyMap.put(prekey + "07" + (i+1), trbeans.get(i));
		}
		return bodyMap;
	}
	
	/**
	 * 
	 * <br>Description:获取项目合计内容
	 * <br>Author:黎春行
	 * <br>Date:2014-1-16
	 * @param xmmc
	 * @return
	 */
	private TRBean getTotalByXMMC(String xmmc){
		
		return null;
	}
	


}
