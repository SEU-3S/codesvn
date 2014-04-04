package com.klspta.web.cbd.yzt.jbb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.model.CBDReport.CBDReportManager;
import com.klspta.model.CBDReport.tablestyle.ITableStyle;
import com.klspta.web.cbd.yzt.jc.report.TableStyleEditRow;
import com.klspta.web.cbd.yzt.zrb.ZrbData;
import com.klspta.web.cbd.yzt.zrb.ZrbReport;

public class JbbManager extends AbstractBaseBean {
	public void getJbb() {
		HttpServletRequest request = this.request;
		response(new JbbData().getAllList(request));
	}

	public void getQuery() {
		HttpServletRequest request = this.request;
		response(new JbbData().getQuery(request));
	}

	public void updateJbb() {
		HttpServletRequest request = this.request;
		if (new JbbData().updateJbb(request)) {
			response("{success:true}");
		} else {
			response("{success:false}");
		}
	}

	public void update() throws Exception {
		String dkmc = new String(request.getParameter("key").getBytes(
				"iso-8859-1"), "UTF-8");
		String index = request.getParameter("vindex");
		String value = new String(request.getParameter("value").getBytes(
				"iso-8859-1"), "UTF-8");
		String field = JbbReport.shows[Integer.parseInt(index)][0];
		response(String.valueOf(new JbbData().modifyValue(dkmc, field, value)));
	}

	public void modify(){
		String dkbh = request.getParameter("jbdkbh");
		String zd = request.getParameter("zd");
		String jsyd = request.getParameter("jsyd");
		String jzgm = request.getParameter("jzgm");
		String rjl = request.getParameter("rjl");
		String kzgd = request.getParameter("kzgd");
		String ghyt = request.getParameter("ghyt");
		String gjjzgm = request.getParameter("gjjzgm");
		String jzjzgm = request.getParameter("jzjzgm");
		String szjzgm = request.getParameter("szjzgm");
		String kfcb = request.getParameter("kfcb");
		String lmcb = request.getParameter("lmcb");
		String dmcb = request.getParameter("dmcb");
		String yjcjj = request.getParameter("yjcjj");
		String yjzftdsy = request.getParameter("yjzftdsy");
		String cxb = request.getParameter("cxb");
		String cqqd = request.getParameter("cqqd");
		String cbfgl = request.getParameter("cbfgl");
		String ssqy = request.getParameter("ssqy");
		
		String sql = "update jc_jiben set zd=?,jsyd=?,rjl=?,jzgm=?,kzgd=?,ghyt=?,gjjzgm=?,jzjzgm=?,szjzgm=?,kfcb=?,"+
  		                 "lmcb=?,dmcb=?,yjcjj=?,yjzftdsy=?,cxb=?,cqqd=?,cbfgl=?,ssqy=? where dkmc=?" ;
  		    int i = update(sql,YW,new Object[]{zd, jsyd,rjl,jzgm,kzgd,ghyt,gjjzgm,jzjzgm,szjzgm,kfcb,
                        lmcb,dmcb,yjcjj,yjzftdsy,cxb,cqqd,cbfgl,ssqy,dkbh}); 
  		    if("民生改善区".equals(ssqy)){
  		    	sql = "update cbd_jbb set gnfq=2 where tbbh=?";
  		    }else if("城市形象提升区".equals(ssqy)){
  		    	sql = "update cbd_jbb set gnfq=3 where tbbh=?";
  		    }else if("产业功能改造区".equals(ssqy)){
  		    	sql = "update cbd_jbb set gnfq=1 where tbbh=?";
  		    }else if("保留微调区".equals(ssqy)){
  		    	sql = "update cbd_jbb set gnfq=4 where tbbh=?";
  		    }
  		    update(sql,GIS,new Object[]{dkbh});
  		    if(i==1){
  		    	response("{success:true}");
  		    }else {
  		    	response("{success:false}");
			}
	}

	/**
	 * 
	 * <br>
	 * Description:基本斑列表过滤 <br>
	 * Author:黎春行 <br>
	 * Date:2013-12-25
	 * 
	 * @throws Exception
	 */
	public void getReport() throws Exception {
		String keyword = request.getParameter("keyword");
		String ssqy = request.getParameter("ssqy");
		String type = request.getParameter("type");
		StringBuffer query = new StringBuffer();
		ITableStyle its = new TableStyleEditRow();
		if (keyword != null) {
			query.append(" where ");
			keyword = UtilFactory.getStrUtil().unescape(keyword);
			StringBuffer querybuffer = new StringBuffer();
			String[][] nameStrings = JbbReport.shows;
			for (int i = 0; i < nameStrings.length - 1; i++) {
				querybuffer.append("upper(t.").append(nameStrings[i][0])
						.append(")||");
			}
			querybuffer.append("upper(t.").append(
					nameStrings[nameStrings.length - 1][0])
					.append(")) like '%").append(keyword).append("%'");
			query.append("(");
			query.append(querybuffer);
		}
		if (ssqy != null) {
			ssqy = UtilFactory.getStrUtil().unescape(ssqy);
		} else {
			ssqy = "%%";
		}
		query.append(" and t.ssqy like '").append(ssqy).append("'");
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("query", query.toString());
		if (!"reader".equals(type)) {
			response(String.valueOf(new CBDReportManager().getReport("JBB",
					new Object[] { "true", conditionMap }, its)));
		} else {
			response(String.valueOf(new CBDReportManager().getReport("JBB",
					new Object[] { "false", conditionMap }, its)));
		}
	}

	/**
	 * 
	 * <br>
	 * Description:基本斑上图 <br>
	 * Author:黎春行 <br>
	 * Date:2013-12-10
	 * 
	 * @throws Exception
	 */
	public void drawZrb() throws Exception {
		String tbbh = request.getParameter("tbbh");
		String polygon = request.getParameter("polygon");
		if (tbbh != null) {
			tbbh = UtilFactory.getStrUtil().unescape(tbbh);
		} else {
			response("{error:not primary}");
		}
		boolean draw = new JbbData().recordGIS(tbbh, polygon);
		response(String.valueOf(draw));
	}

	public static Map<String, String> getDKMCMap() {
		JbbData jbbData = new JbbData();
		Set<String> leftSet = new TreeSet<String>();
		List<Map<String, Object>> dkmcList = jbbData.getDkmcList();
		for (int i = 0; i < dkmcList.size(); i++) {
			String name = String.valueOf(dkmcList.get(i).get("DKMC"));
			leftSet.add(name);
		}
		Map<String, String> proMap = new HashMap<String, String>();
		proMap.put("left", toJson(leftSet));
		return proMap;
	}

	private static String toJson(Set<String> set) {
		StringBuffer jsonBuffer = new StringBuffer();
		jsonBuffer.append("[");
		for (String project : set) {
			jsonBuffer.append("['").append(project).append("','");
			jsonBuffer.append(project).append("'],");
		}
		if (!set.isEmpty())
			jsonBuffer = jsonBuffer.deleteCharAt(jsonBuffer.length() - 1);
		jsonBuffer.append("]");
		return jsonBuffer.toString();

	}

	/*
	 * public void getExcel(){ HttpServletRequest request = this.request;
	 * HttpServletResponse response = this.response; JbbReport jbbReport = new
	 * JbbReport(); jbbReport.getExcel(request, response); }
	 */

	public Map<String, Object> getJHCB() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> result = null;
		String sql = "select sum(j.kfcb) as kfcb ,round(sum(j.kfcb)/sum(j.jzgm),1)*10000 as lmcb ,round(sum(j.kfcb)/sum(j.jsyd),1)*10000 as dmcb from jc_jiben j where j.ssqy in(?,?,?)";
		result = query(sql, YW, new Object[] { "产业功能改造区", "民生改善区", "城市形象提升区" });

		map.put("KFCB", result.get(0).get("KFCB") == null ? "0" : result.get(0)
				.get("KFCB").toString());
		map.put("LMCB", result.get(0).get("LMCB") == null ? "0" : result.get(0)
				.get("LMCB").toString());
		map.put("DMCB", result.get(0).get("DMCB") == null ? "0" : result.get(0)
				.get("DMCB").toString());
		sql = "select b.bbd as bbd from sys_parameter s,bbdfxjg b where b.lmcb=? and s.hsq = b.tzhsq";
		result = query(sql, YW, new Object[] { map.get("LMCB").toString() });
		if (result.size() > 0) {
			map.putAll(result.get(0));
		} else {
			sql = "select b.bbd as bbd from bbdfxjg b ,sys_parameter s where b.tzhsq=s.hsq order by to_number(lmcb) desc";
			result = query(sql, YW);
			map.put("BBD", ">" + result.get(0).get("bbd"));
		}
		return map;
	}

	/**
	 * 
	 * <br>
	 * Description:获取基本斑编号 <br>
	 * Author:侯文超 <br>
	 * Date:2014-01-10
	 * 
	 * @throws Exception
	 */
	public void getJBBBH() {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.dkmc from JC_JIBEN t");
		List<Map<String, Object>> list = query(sqlBuffer.toString(), YW);
		response(list);
	}
}
