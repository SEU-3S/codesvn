package com.klspta.model.mapconfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.base.wkt.Polygon;

/**
 * 
 * <br>Title:图斑查询
 * <br>Description:图斑查询
 * <br>Author:李亚栓
 * <br>Date:2012-8-16
 */
public class TBQuery extends AbstractBaseBean{
	private String YZ_JSYDSP ="YZ_JSYDSP";			  //建设用地审批
	private String YZ_GDGZ_POLYGON ="YZ_GDGZ_POLYGON";//供地跟踪
	private String YZ_WPZFJC_2011 ="YZ_WPZFJC_2011";  //卫片监测图斑
	private String JSYDGZQ ="JSYDGZQ";                //土地规划用途
	private String DJ_POLYGON_TB ="DJ_POLYGON_TB";    //土地利用现状
	/**
	 * 
	 * <br>Description:根据地图服务、输入关键字查询图斑
	 * <br>Author:李亚栓
	 * <br>Date:2012-8-16
	 */
	public void queryTB(){
		String keywork = request.getParameter("keywork");
		String dt_service = request.getParameter("dtService");
		String[] dtservice = dt_service.split("@");
		//地图服务id
		String dtService = dtservice[0];
		String sql = null;
		String where = "";
		String strColumnName = null;
		List<Map<String, Object>> listYw = new ArrayList<Map<String,Object>>();
		if (keywork != null && !"".equals(keywork)) {
			keywork = keywork.trim();
			while (keywork.indexOf("  ") > 0) {// 循环去掉多个空格，所有字符中间只用一个空格间隔
				keywork = keywork.replace("  ", " ");
			}
			keywork = UtilFactory.getStrUtil().unescape(keywork);
		}
		//查询
		if(YZ_JSYDSP.equals(dtService)){
			strColumnName ="t.objectid||t.zqbm||t.zqmc||t.xbh||t.dkmc";
			where += "where  ("+ strColumnName+ " like '%"+ (keywork.replaceAll(" ", "%' and " + strColumnName+ "  like '%")) + "%')";// 查询条件
			sql = "select (rownum-1) as rownum1 ,t.objectid,t.dkmc from "+dtService+" t ";
			sql +=where;
			listYw = query(sql, GIS);
			for(Map<String,Object> list : listYw){
				list.put("XMMC", list.get("dkmc"));
				list.put("tableName",dtService);
			}
		}else if(YZ_GDGZ_POLYGON.equals(dtService)){
			strColumnName ="t.objectid||t.xm_mc";
			where += "where  ("+ strColumnName+ " like '%"+ (keywork.replaceAll(" ", "%' and " + strColumnName+ "  like '%")) + "%')";// 查询条件
			sql = "select (rownum-1) as rownum1 ,t.objectid,t.xm_mc from "+dtService+" t ";
			sql +=where;
			listYw = query(sql, GIS);
			for(Map<String,Object> list : listYw){
				list.put("XMMC", list.get("xm_mc"));
				list.put("tableName",dtService);
			}
		}else if(YZ_WPZFJC_2011.equals(dtService)){
			//strColumnName ="t.jcbh||t.xzmc";
			//where += " and ("+ strColumnName+ " like '%"+ (keywork.replaceAll(" ", "%' and " + strColumnName+ "  like '%")) + "%')";// 查询条件
//			sql = "select (rownum-1) as rownum1 ,t.objectid,t.xmc,t.xzqdm||'-'||t.jcbh as tbbh from "+dtService+" t  where t.jcbh='"+keywork+"'";
			
			//wpzf_tb
			sql ="select t.TBBH,t.XZQHMC from wpzf_tb t where t.TBBH like '%-"+keywork+"' or t.XZQHMC like '%"+keywork+"%'";
			
			listYw = query(sql, YW);
			for(Map<String,Object> list : listYw){
				list.put("XMMC", list.get("XZQHMC"));
				list.put("tableName",dtService);
			}
		}else if(JSYDGZQ.equals(dtService)){
			strColumnName ="t.objectid||t.bsm||t.sm";
			where += "  ("+ strColumnName+ " like '%"+ (keywork.replaceAll(" ", "%' and " + strColumnName+ "  like '%")) + "%')";// 查询条件
			sql = "select (rownum-1) as rownum1 ,t.objectid,t.sm from "+dtService+" t where ";
			sql +=where;
			listYw = query(sql, GIS);
			for(Map<String,Object> list : listYw){
				list.put("XMMC", list.get("sm"));
				list.put("tableName",dtService);
			}
		}else if(DJ_POLYGON_TB.equals(dtService)){
			strColumnName ="t.objectid||t.dlmc||t.qsdwmc";
			where += "  ("+ strColumnName+ " like '%"+ (keywork.replaceAll(" ", "%' and " + strColumnName+ "  like '%")) + "%')";// 查询条件
			sql = "select (rownum-1) as rownum1 ,t.objectid,t.dlmc from "+dtService+" t where ";
			sql +=where;
			listYw = query(sql, GIS);
			for(Map<String,Object> list : listYw){
				list.put("XMMC", list.get("dlmc"));
				list.put("tableName",dtService);
			}
		}
		response(listYw);
	}
	/**
	 * 
	 * <br>Description:获取图斑坐标
	 * <br>Author:李亚栓
	 * <br>Date:2012-8-20
	 */
// 	public void getZbByoObjectid(){
// 		String dkid = request.getParameter("objectid");
// 		String tableName = request.getParameter("tableName");
// 		String sql="select sde.st_astext(d.shape) as wkt from "+tableName+" d where d.objectid='"+dkid+"'";
// 		List<Map<String, Object>> list=query(sql,GIS);
// 		Map<String, Object> map=list.get(0);
// 		String wkt=(String)map.get("wkt");
// 		Polygon p=new Polygon();
// 		wkt=p.toWKT(wkt, 2364);
// 		response(wkt);
// 	}
	/**
	 * 
	 * <br>Description:获取图斑的【基本属性】信息
	 * <br>Author:李亚栓
	 * <br>Date:2012-8-21
	 */
 	public void getJbsxData(){
 		String tbbh = request.getParameter("tbbh");
 		//String tableName = request.getParameter("tableName");
 		String sql = null;
 		sql = "select t.tbbh,t.tbmj,t.xzqhdm,t.x,t.y,t.qsx,t.hsx,t.YEAR from wpzf_tb t where t.tbbh=? ";
 		List<Map<String, Object>> list = query(sql, YW, new Object[]{tbbh});
 		//myData = [["图斑编号","420282-0745"],["图斑面积","134.6"],["行政代码","370783001"],["图斑类型","未批先建"],["中心点坐标X","40385728.9"],["中心点坐标Y","4126772.9"],["前时相","20101118"],["后时相","20111201"],["年度","2011"]];
 		//myData = [{mc:"图斑编号",sx:"420282-0745"},{mc:"图斑面积",sx:"134.6"}];
 		List<Map<String, Object>> data= new ArrayList<Map<String, Object>>();
 		if(list.size()>0){
		   Map<String, Object> map1 = new HashMap<String, Object>();
		   map1.put("mc","图斑编号");
		   map1.put("sx",list.get(0).get("TBBH"));
			data.add(map1);
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("mc","图斑面积");
			map2.put("sx",list.get(0).get("TBMJ"));
			data.add(map2);
			Map<String, Object> map3 = new HashMap<String, Object>();
			map3.put("mc","行政代码");
			map3.put("sx",list.get(0).get("XZQHDM"));
			data.add(map3);
			Map<String, Object> map4 = new HashMap<String, Object>();
			map4.put("mc","中心点坐标X");
			map4.put("sx",list.get(0).get("X"));
			data.add(map4);
			Map<String, Object> map5 = new HashMap<String, Object>();
			map5.put("mc","中心点坐标X");
			map5.put("sx",list.get(0).get("Y"));
			data.add(map5);
			Map<String, Object> map6 = new HashMap<String, Object>();
			map6.put("mc","前时相");
			map6.put("sx",list.get(0).get("QSX"));
			data.add(map6);
			Map<String, Object> map7 = new HashMap<String, Object>();
			map7.put("mc","后时相");
			map7.put("sx",list.get(0).get("HSX"));
			data.add(map7);
			Map<String, Object> map8 = new HashMap<String, Object>();
			map8.put("mc","年度");
			map8.put("sx",list.get(0).get("YEAR"));
			data.add(map8);
 		}
 		response(data);
 	}
 	/**
	 * 
	 * <br>Description:获取图斑的【现状分析】信息
	 * <br>Author:李亚栓
	 * <br>Date:2012-8-21
	 */
 	public void getXzfxData(){
 		String tbbh = request.getParameter("tbbh");
 		String sql = null;
 		sql ="select t.tbbh,t.dlmc,t.qsdwmc,t.ygmj from wpzf_analyse_xz_dltb t where  t.tbbh=? ";
 		List<Map<String, Object>> list = query(sql, YW, new Object[]{tbbh});
 		response(list);
 	}
	/**
	 * 
	 * <br>Description:获取图斑的【现状分析】用地的信息
	 * <br>Author:李亚栓
	 * <br>Date:2012-8-21
	 */
 	public List<Map<String, Object>> getXzfxYdData(Map<String,Object> map){
 		String tbbh = (String)map.get("tbbh");
 		String sql = null;
 		sql ="select nvl(x.nydmj,0.00) as nydmj,nvl(x.gdmj,0.00) as gdmj,nvl(x.jsydmj,0.00) as jsydmj,nvl(x.wlydmj ,0.00) as wlydmj from wpzf_analyse_xz x where x.tbbh=?";
 		List<Map<String, Object>> list = query(sql, YW, new Object[]{tbbh});
 		return list;
 	}
	/**
	 * 
	 * <br>Description:获取图斑的【规划分析】信息
	 * <br>Author:李亚栓
	 * <br>Date:2012-8-21
	 */
 	public String getGhfxData(Map<String,Object> map){
 		String tbbh = (String) map.get("tbbh");
 		String sql = "select nvl(t.yxjsqmj,0)+nvl(t.ytjjsqmj,0) as fhghmj,nvl(t.xzjsqmj,0)+nvl(t.jzjsqmj,0) as bfhghmj,nvl(t.zyjbnt,0) as zyjbnt from wpzf_analyse_gh t where t.tbbh=?";
 		List<Map<String, Object>> list = query(sql, YW, new Object[]{tbbh});
 		//var myData = [["符合规划面积","88.2"],["不符合规划面积","46.6"],["占用基本农田面积","0"]];
 		String str ="";
 		StringBuffer suff = new StringBuffer(str);
 		if(list.size()>0){
 			suff.append("[[\"符合规划面积\",\"");
 			suff.append(list.get(0).get("FHGHMJ")+"\"],[\"不符合规划面积\",\"");
 			suff.append(list.get(0).get("BFHGHMJ")+"\"],[\"占用基本农田面积\",\"");
 			suff.append(list.get(0).get("ZYJBNT")+"\"]]");
 			str = suff.toString();
 		}
 		return str;
 	} 
 	/**
 	  * <br>Description:各图层需要分析项
	 * <br>Author:李亚栓
	 * <br>Date:2012-9-27
 	 */
 	public void getLayername()
 	{
 		String serviceid =request.getParameter("serviceid");
 		String layerid =request.getParameter("layerid");
 		String layerName ="[{title:\"查询结果\",html: \"<iframe id='cxjg' name='cxjg' style=' width:350px;' src='queryData.jsp'/>\"},{title:\"基本属性\",html: \"<iframe id='jbsx' style='width:350px;' src='jbsxList.jsp' />\"}";
 		String sql ="select t.LAYERNAME ,t.TREENAME from GIS_MAPTREE t where serverid=? and layerid=? and  t.parenttreeid not like '0'";
 		List<Map<String, Object>> data = query(sql, CORE, new Object[]{serviceid,layerid});
 		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
 		if(data.get(0).get("LAYERNAME")!=null){
 		String ss=(String )	data.get(0).get("LAYERNAME");
 		String[] layersArray = ss.split(",");
 		for(int i=0;i<layersArray.length;i++){
            if(layersArray[i].equals("GD")){
            	layerName=layerName+",{title:\"供地分析\",html: \"<iframe id='gdfx' style='width:350px;' src='' />\"}";
             }else if(layersArray[i].equals("SP")){
             	layerName=layerName+",{title:\"审批分析\",html: \"<iframe id='spfx' style='width:350px;' src='' />\"}";
            }else if(layersArray[i].equals("GH")){
            	layerName=layerName+",{title:\"规划分析\",html: \"<iframe id='ghfx' style='width:350px;' src='' />\"}";
            }else if(layersArray[i].equals("XZ")){ 
            	layerName=layerName+",{title:\"现状分析\",html: \"<iframe id='xzfx' style='width:350px;' src='' />\"}";
            }
            
       }   
 		layerName=layerName+"]";
 		Map<String, Object> map = new HashMap<String, Object>();
 		map.put("list", layerName);
 		list.add(map);
 		response(list);
 		} else{
 	    layerName=layerName+"]";
 		Map<String, Object> map = new HashMap<String, Object>();
 		map.put("list", layerName);
 		list.add(map);
 		response(list);
 		}
 	}
 	
 	/**
 	 * <br>Description:获取卫片监测图斑与审批的分析数据
	 * <br>Author:李亚栓
	 * <br>Date:2012-9-28
 	 */
 	public void getWpjctbSPdata(){
 		String tbbh = request.getParameter("tbbh");
 		String sql="select t.* from wpzf_analyse_sp t where t.tbbh=?";
 		List<Map<String, Object>> list = query(sql, YW, new Object[]{tbbh});
 		response(list);
 	}
	/**
 	 * <br>Description:获取卫片监测图斑与供地的分析数据
	 * <br>Author:李亚栓
	 * <br>Date:2012-9-28
 	 */
 	public void getWpjctbGDdata(){
 		String tbbh = request.getParameter("tbbh");
 		String sql="select t.* from wpzf_analyse_gd t where t.tbbh=?";
 		List<Map<String, Object>> list = query(sql, YW, new Object[]{tbbh});
 		response(list);
 	}
 	public void getQueryData(){
 		String data = request.getParameter("data");
 		System.out.println("data-----:"+data);
 		
 	}
}
