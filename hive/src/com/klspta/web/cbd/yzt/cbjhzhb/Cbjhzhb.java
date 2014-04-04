package com.klspta.web.cbd.yzt.cbjhzhb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import sun.print.SunMinMaxPage;

import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;

/**
 * 
 * <br>Title:储备计划整合表动态生成类
 * <br>Description:TODO 类功能描述
 * <br>Author:黎春行
 * <br>Date:2014-1-10
 */
public class Cbjhzhb extends AbstractBaseBean implements IDataClass {
	private static Map<String, String[][]> titleMap = new TreeMap<String, String[][]>();
	public static String[][] fields;
	public static String form_extend = "jc_cbjhzhb";
	private static String form_base = "jc_xiangmu";
	
	/**
	 * 
	 * <br>Description:设定表单抬头
	 * <br>Author:黎春行
	 * <br>Date:2014-1-10
	 */
	private void SetTitle(){
		//{name,width,cols,row}
		String[][] firstTitle = {{"序号","50","1","3"},{"项目名称","100","1","3"},{"项目类型","200","1","3"},{"区县","100","1","3"},{"地块名称","100","1","3"},{"项目位置","100","1","3"},{"项目区位","100","1","3"},{"项目类别","100","1","3"},
				{"项目主体","100","1","3"},{"实施主<br>体是否<br>带资","100","1","3"},{"项目单位带资实施比率","100","1","3"},{"项目状态","100","1","3"},{"土地面积<br>(公顷)","300","3","1"},{"代拆土<br>地面积<br>(公顷)","100","1","3"},{"规划用地面积","500","5","1"},{"规划建筑规模(万平方米)","400","4","1"},
				{"手续办理情况","700","7","1"},{"征地情况(公顷)","500","5","1"},{"拆迁情况(万平方米)","500","3","1"},{"投资情况(万元)","500","10","1"},{"预计资金回笼情况(万元)","500","4","1"},{"完成开发情况(公顷、万平方米)","500","15","1"},{"计划供应情况(公顷、万平方米)","500","16","1"}
				,{"当前项目推进中存在的主要问题","200","1","3"},{"是否位<br>于新城<br>范围","200","1","3"},{"轨道交<br>通线<br>（站点半<br>径1km范围）","200","1","3"},{"项目新增理由","200","1","3"},{"备注","200","1","3"}};
		String[][] secondTitle = {{"总面积<br>(不含代<br>拆土地)","100","1","2"},{"农用地<br>面积","100","1","2"},{"其中耕<br>地面积","100","1","2"},{"建设用地","400","4","1"},{"代征地","100","1","2"},{"小计","100","1","2"}
		,{"居住","100","1","2"},{"商服","100","1","2"},{"其它","100","1","2"},{"过会年份","100","1","2"},{"取得一<br>级开发<br>授权时间","100","1","2"},{"一级规<br>划条件","100","1","2"},{"用地预<br>审批复","100","1","2"},{"核准<br>批复","100","1","2"},{"征地<br>批复","100","1","2"}
		,{"拆迁<br>许可","100","1","2"},{"需征地<br>总面积","100","1","2"},{"已完成<br>征地总<br>面积","100","1","2"},{"2013年<br>前已征<br>地面积","100","1","2"},{"2013年<br>已征地<br>面积","100","1","2"},{"2014年<br>计划征<br>地面积","100","1","2"},{"需拆迁<br>建筑面积","100","1","2"},{"已完成<br>拆迁比例","100","1","2"},{"2014年<br>计划拆迁建筑面积","100","1","2"},
		{"计划<br>投资","100","1","2"},{"已完成<br>投资","100","1","2"},{"2013年已投资","400","4","1"},{"2014年计划投资","500","5","1"},{"2014年预计回笼资金","400","4","1"},{"累计完<br>成开发<br>面积","100","1","2"},{"2013年<br>现场验<br>收面积","100","1","2"},{"是否<br>计划<br>2014年<br>完成开发","100","1","2"},{"计划完成开发时间","100","1","2"},{"计划完成开发面积","100","1","2"},
		{"规划用地面积","400","4","1"},{"规划建筑规模","400","4","1"},{"完成开发确保程度","100","1","2"},{"完成开发可能性排序","100","1","2"},{"累计供应面积","100","1","2"},{"2013年供应面积","100","1","2"},{"是否计2014年供应","100","1","2"},{"计划供应时间","100","1","2"},{"计划供应面积","100","1","2"},{"规划用地面积","400","4","1"}
		,{"规划建筑规模","400","4","1"},{"实现供应确保程度","100","1","2"},{"实现供应可能性排序","100","1","2"}}; 
		String[][] thirdTitle = {{"小计","100","1","1"},{"居住","100","1","1"},{"商服","100","1","1"},{"其他","100","1","1"},{"小计","100","1","1"},{"市中心已投资","100","1","1"},{"分中心已投资","100","1","1"},{"企业已投资","100","1","1"},{"小计","100","1","1"},{"土地储<br>备开发<br>总成本","100","1","1"},{"市中心投资","100","1","1"},{"分中心投资","100","1","1"},{"企业投资","100","1","1"},
				{"小计","100","1","1"},{"市中心回笼","100","1","1"},{"分中心回笼","100","1","1"},{"企业回笼","100","1","1"},{"小计","100","1","1"},{"居住","100","1","1"},{"商服","100","1","1"},{"其他","100","1","1"},{"小计","100","1","1"},{"居住","100","1","1"},{"商服","100","1","1"},{"其他","100","1","1"},{"小计","100","1","1"},{"居住","100","1","1"},{"商服","100","1","1"},{"其他","100","1","1"}
				,{"小计","100","1","1"},{"居住","100","1","1"},{"商服","100","1","1"},{"其他","100","1","1"}};
		
		titleMap.put("1", firstTitle);
		titleMap.put("2", secondTitle);
		titleMap.put("3", thirdTitle);
	}
	
	/**
	 * 
	 * <br>Description:生成table的title
	 * <br>Author:黎春行
	 * <br>Date:2014-1-10
	 * @return
	 */
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
	
	/**
	 * 
	 * <br>Description:设定body展现字段
	 * <br>Author:黎春行
	 * <br>Date:2014-1-10
	 */
	public void Setfields(){
		//no:不参与统计，null：统计显示为空，sum：显示统计总和为（sum）
		String[][] fields = {{"xmname","false","no"},{"xmlx","false","null"},{"xmqx","false","null"},{"dkmc","false","null"},{"xmwz","false","null"},{"xmqw","false","null"},{"xmlb","false","null"},{"xmzt","false","null"},{"sfdz","false","null"},{"dzbv","false","null"},{"xmztai","false","null"},{"zd","false","sum"},{"nydmj","false","sum"},{"qzgdmj","false","sum"},{"dctdmj","false","sum"},{"jsydxj","false","sum"},{"jsydjz","false","sum"},{"jsydsf","false","sum"},{"jsydqt","false","sum"},{"ghdzd","false","sum"},
			{"ghjzxj","false","sum"},{"ghjzjz","false","sum"},{"ghjzsf","false","sum"},{"ghjzqt","false","sum"},{"sxghnf","false","null"},{"sxsqsj","false","null"},{"sxghtj","false","null"},{"sxyspf","false","null"},{"sxhzpf","false","null"},{"sxzdpf","false","null"},{"sxcqxk","false","null"},{"zdzmj","false","sum"},{"ywczdzmj","false","sum"},{"qyzdmj","false","sum"},{"yzdmj","false","sum"},{"jhzdmj","false","sum"},{"cqjzmj","false","sum"},{"cqcqbl","false","sum"},{"cqjhmj","false","sum"},{"tzjhztz","false","sum"},
			{"tzywc","false","sum"},{"ytzxj","false","sum"},{"ytzszx","false","sum"},{"ytzfzx","false","sum"},{"ytzqy","false","sum"},{"jhxj","false","sum"},{"jhzcb","false","sum"},{"jhszx","false","sum"},{"jhfzx","false","sum"},{"jhqy","false","sum"},{"zjhlxj","false","sum"},{"zjhlszx","false","sum"},{"zjhlfzx","false","sum"},{"zjhlqy","false","sum"},{"ljwckf","false","sum"},{"xcysmj","false","sum"},{"sfjhwc","false","null"},{"jhwcsj","false","null"},{"jhwcmj","false","sum"},{"ghydxj","false","sum"},{"ghydjz","false","sum"},{"ghydsf","false","sum"}
			,{"ghydqt","false","sum"},{"wcghjzxj","false","sum"},{"wcghjzjz","false","sum"},{"wcghjzsf","false","sum"},{"wcghjzqt","false","sum"},{"WCKFQBCD","false","null"},{"WCKFPX","false","null"},{"LJGYMJ","false","sum"},{"DNGYMJ","false","sum"},{"SFJMNGY","false","sum"},{"JHGYSJ","false","sum"},{"JHGYMJ","false","sum"},{"GHYDMJXJ","false","sum"},{"GHYDMJJZ","false","sum"},{"GHYDMJSF","false","sum"},{"GHYDMJQT","false","sum"},{"GHYDMXJ","false","sum"},{"GHJZGMJZ","false","sum"},{"GHJZGMSF","false","sum"},{"GHJZGMQT","false","sum"},{"SXGYQBCD","false","null"},
			{"SXGYPX","false","null"},{"ZYWT","false","null"},{"SFXCFW","false","null"},{"GDJTX","false","null"},{"XZLY","false","null"},{"BZ","false","null"}};
		this.fields = fields;
	}
	
	/**
	 * 
	 * <br>Description:生成table的body
	 * <br>Author:黎春行
	 * <br>Date:2014-1-10
	 * @return
	 */
	private Map<String, TRBean> buildBody(){
		Map<String, TRBean> trBeans = new LinkedHashMap<String, TRBean>();
		if(fields == null){
			Setfields();
		}
		String querySql = "select t.* , j.* from " + form_base + " t left join " + form_extend + " j on j.xmmc = t.xmname order by j.xmlx desc,t.xmname ";
		Map<String, Map<String, String>> subTotalMap = new HashMap<String, Map<String,String>>();
		Map<String, String> totalMap = new HashMap<String, String>();
		String key = "";
		List<Map<String, Object>> queryList = query(querySql, YW);
		int num = 1;
		for(int i = 0; i < queryList.size(); i++){
			TRBean trbean = new TRBean();
			trbean.setCssStyle("trsingle");
			Map<String, Object> queryMap = queryList.get(i);
			String prekey = key;
			key = String.valueOf(queryMap.get("xmlx"));
			if(null == key || "null".equals(key) || "".equals(key)){
				key = "未分类";
			}
			Map<String, String> subMap = subTotalMap.get(key);
			//添加小计行
			if(!"".equals(prekey) && !prekey.equals(key)){
				num = 1;
				trBeans.put("0" + (i+1), getTotal(subTotalMap, prekey));
			}
				//添加合计
				if(subMap == null){
					subMap = new HashMap<String, String>();
				}
				for(int t = 0; t < fields.length; t++){
					String[] field = fields[t];
					if(field[2].equals("no")){
						continue;
					}else if(field[2].equals("null")){
						if(!subMap.containsKey(field[0])){
							subMap.put(field[0], "---");
						}
						if(!totalMap.containsKey(field[0])){
							totalMap.put(field[0], "---");
						}
					}else{
						String keyname = field[0];
						String newvalue = String.valueOf(queryMap.get(keyname));
						float value = 0;
						try{
							value = Float.parseFloat(newvalue);
						}catch (Exception e) {
						}
						
						if(subMap.containsKey(keyname)){
							String oldvalue = subMap.get(keyname);
							String newValue = String.valueOf(Float.parseFloat(oldvalue) + value);
							subMap.put(keyname, newValue);
						}else{
							subMap.put(keyname, String.valueOf(value));
						}
						
						if(totalMap.containsKey(keyname)){
							String oldvalue = totalMap.get(keyname);
							String newValue = String.valueOf(Float.parseFloat(oldvalue) + value);
							totalMap.put(keyname, newValue);
						}else{
							totalMap.put(keyname, String.valueOf(value));
						}
						
						subTotalMap.put(key, subMap);
					}
				
			}
			subTotalMap.put("合计", totalMap);
			// 添加展现行
			TDBean tdBean = new TDBean(String.valueOf(num),"50","20");
			trbean.addTDBean(tdBean);
			for(int t = 0; t < fields.length; t++){
				String[] field = fields[t];
				String value = String.valueOf(queryMap.get(field[0]));
				value = (value == "null") ? "" : value;
				trbean.addTDBean(new TDBean(value, "100", "20", field[1]));
			}
			trBeans.put("0" + (i+1) + "00", trbean);
			num++;
		}
		
		//添加最后一个小计
		String prekey = "计划新增项目";
		trBeans.put("0" + (queryList.size() + 1) , getTotal(subTotalMap, prekey));
		//添加合计
		trBeans.put("0" + (queryList.size() + 2) , getTotal(subTotalMap, "合计"));
		
		return  trBeans;
	}

	
	/**
	 * 
	 * <br>Description:TODO 方法功能描述
	 * <br>Author:黎春行
	 * <br>Date:2014-1-10
	 * @param where
	 * @return
	 */
	private TRBean getTotal(Map<String,Map<String, String>> subTotalMap, String prekey){
		Map<String, String> subMap = subTotalMap.get(prekey);
		TDBean tdBean;
		TRBean subbean = new TRBean();
		if(prekey.contains("合计")){
			tdBean = new TDBean("合计","300","20");
		}else{
			tdBean = new TDBean(prekey + "小计","300","20");
		}
		tdBean.setColspan("2");
		subbean.addTDBean(tdBean);
		subbean.setCssStyle("trtotal");
		for(int t = 0; t < fields.length; t++){
			String[] field = fields[t];
			if("no".equals(field[2])){
				continue;
			}else{
				String value = "";
				if(subMap != null && subMap.containsKey(field[0])){
					value = subMap.get(field[0]);
				}
				TDBean td = new TDBean(value,"100","20");
				subbean.addTDBean(td);
			}
		}
		return subbean;
	} 
	
	
	@Override
	public Map<String, TRBean> getTRBeans(Object[] obj, TRBean trBean) {
		Map<String, TRBean>  bodyMap = new TreeMap<String, TRBean>();
		List<TRBean> titles = buildTitle();
		for(int i = 0; i < titles.size(); i++){
			bodyMap.put("000" + i, titles.get(i));
		}
		Map<String, TRBean> bodys = buildBody();
		bodyMap.putAll(bodys);
		return bodyMap;
	}

}
