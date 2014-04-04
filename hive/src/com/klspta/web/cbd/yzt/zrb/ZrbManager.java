package com.klspta.web.cbd.yzt.zrb;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;


import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.model.CBDReport.CBDReportManager;
import com.klspta.model.CBDReport.tablestyle.ITableStyle;
import com.klspta.web.cbd.yzt.jc.report.TableStyleEditRow;

/**
 * 
 * <br>Title:自然斑管理类
 * <br>Description:管理自然斑数据
 * <br>Author:黎春行
 * <br>Date:2013-10-18
 */
public class ZrbManager extends AbstractBaseBean {
//	private String[][] fields = {{"dkmc","false","null"},{"jsydmj","false","sum"},{"rjl","false","avg"},{"ghjzgm","false","sum"},{"jzkzgd","false","avg"},{"DJZJ","true","sum"},{"DJYJLY","true","sum"},{"DJYJLB","true","null"},{"ZFSYZE","true","sum"},{"ZFSYYJLY","true","sum"}
//	,{"ZFSYYJLB","true","sum"},{"ZFSYHTYD","true","null"},{"ZFSYWYJ","true","sum"},{"BCFZE","true","sum"},{"BCFYJLY","true","sum"},{"BCFYJLB","true","null"},{"BCFHTYD","true","null"},{"BCFYCSWY","true","sum"},{"DJKJLSJ","true","null"},{"CBZH","true","null"}
//	,{"ZCMJ","true","sum"},{"CRSJ","true","null"},{"ZBR","true","null"},{"JDSJ","true","null"},{"YJSJ","true","null"},{"KGSJ","true","null"},{"TDXZSJ","true","null"},{"YT","true","null"},{"SFYL","true","null"},{"DGDW","true","null"}
//	,{"SX","true","null"},{"bz","true","null"}};
	//private String[][] fields = 
    /**
     * 
     * <br>Description:获取所有自然斑列表
     * <br>Author:黎春行
     * <br>Date:2013-10-18
     */
    public void getZrb() {
        HttpServletRequest request = this.request;
        response(ZrbData.getInstance().getAllList(request));
    }

    /**
     * 
     * <br>Description:根据关键字查询自然斑列表
     * <br>Author:黎春行
     * <br>Date:2013-10-21
     */
    public void getQuery() {
        HttpServletRequest request = this.request;
        response(ZrbData.getInstance().getQuery(request));
    }

    /**
     * 
     * <br>Description:更新自然斑
     * <br>Author:黎春行
     * <br>Date:2013-10-22
     * @throws Exception 
     */
    public void updateZrb() throws Exception {
        HttpServletRequest request = this.request;
        if (ZrbData.getInstance().updateZrb(request)) {
            response("{success:true}");
        } else {
            response("{success:false}");
        }
    }
    
    
    public void update() throws Exception{
    	String zrbbh =new String(request.getParameter("key").getBytes("iso-8859-1"), "UTF-8");
    	String index = request.getParameter("vindex");
    	String value = new String(request.getParameter("value").getBytes("iso-8859-1"), "UTF-8");
    	String field = ZrbReport.shows[Integer.parseInt(index)][0];
    	//response(String.valueOf(new ZrbData().modifyValue(zrbbh, field, value)));
    	//为添加响应速度，采用多线程
    	//Thread thread = new Thread(new ZrbData(zrbbh, field, value));
    	//thread.run();
    	ZrbData zrbData = ZrbData.getInstance();
    	zrbData.setChange(zrbbh, field, value);
    	ExecutorService exec = Executors.newCachedThreadPool();
    	exec.execute(zrbData);
    	exec.shutdown();
    	//zrbData.run();
    	
    }
    
    /**
     * 
     * <br>Description:添加一个新的自然斑
     * <br>Author:黎春行
     * <br>Date:2013-12-12
     */
    public void insertZrb(){
    	String zrbBH = request.getParameter("ZRBBH");
    	if (zrbBH != null) {
    		zrbBH = UtilFactory.getStrUtil().unescape(zrbBH);
	        if (ZrbData.getInstance().insertZrb(zrbBH)) {
	            response("{success:true}");
	        } else {
	            response("{success:false}");
	        }
    	}else{
    		response("{success:false}");
    	}
    }
    
    /**
     * 
     * <br>Description:自然斑上图
     * <br>Author:黎春行
     * <br>Date:2013-12-10
     * @throws Exception 
     */
    public void drawZrb() throws Exception{
    	String tbbh = request.getParameter("tbbh");
    	String polygon = request.getParameter("polygon");
    	if (tbbh != null) {
    		tbbh = UtilFactory.getStrUtil().unescape(tbbh);
    	}else{
    		response("{error:not primary}");
    	}
    	boolean draw = ZrbData.getInstance().recordGIS(tbbh, polygon);
    	response(String.valueOf(draw)); 
    }
    
    /**
     * 
     * <br>Description:自然斑列表过滤
     * <br>Author:黎春行
     * <br>Date:2013-12-25
     * @throws Exception
     */
	public void getReport() throws Exception{
		String keyword = request.getParameter("keyword");
		String type = request.getParameter("type");
		StringBuffer query = new StringBuffer();
		ITableStyle its = new TableStyleEditRow();
		if(keyword != null){
			query.append(" where ");
			keyword = UtilFactory.getStrUtil().unescape(keyword);
			StringBuffer querybuffer = new StringBuffer();
			String[][] nameStrings = ZrbReport.shows;
			for(int i = 0; i < nameStrings.length - 1; i++){
				querybuffer.append("upper(").append(nameStrings[i][0]).append(")||");
				
			}
			querybuffer.append("upper(").append(nameStrings[nameStrings.length - 1][0]).append(")) like '%").append(keyword).append("%'");
			query.append("(");
			query.append(querybuffer);
		}
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("query", query.toString());
		if(type != null){
			response(String.valueOf(new CBDReportManager().getReport("ZRB", new Object[]{"false",conditionMap},its)));
		}else{
			response(String.valueOf(new CBDReportManager().getReport("ZRB", new Object[]{"true",conditionMap},its)));
		}
	}
    public static Map<String, String> getZRBBHMap(){
    	ZrbData zrbData = ZrbData.getInstance();
    	Set<String> leftSet = new TreeSet<String>();
    	List<Map<String, Object>> zRBBHList = zrbData.getZRBNameList();
    	for(int i = 0; i < zRBBHList.size(); i++){
    		String name = String.valueOf(zRBBHList.get(i).get("ZRBBH"));
    		leftSet.add(name);
    	}
    	Map<String, String> proMap = new HashMap<String, String>();
    	proMap.put("left", toJson(leftSet));
    	return proMap;
    }
    
    /**
     * 
     * <br>Description:根据自然斑编号删除自然斑
     * <br>Author:黎春行
     * <br>Date:2013-12-25
     * @throws Exception
     */
    public void delete() throws Exception{
    	boolean result = true;
    	ZrbData zrbData = ZrbData.getInstance();
    	String zrbs =new String(request.getParameter("zrbbh").getBytes("iso-8859-1"),"utf-8");
    	String[] zrbArray = zrbs.split(",");
    	for(int i = 0; i < zrbArray.length; i++){
    		result = result && zrbData.delete(zrbArray[i]);
    	}
    	response(String.valueOf(result));
    }
    
	private static String toJson(Set<String> set){
		StringBuffer jsonBuffer = new StringBuffer();
		jsonBuffer.append("[");
		for(String project : set){
			jsonBuffer.append("['").append(project).append("','");
			jsonBuffer.append(project).append("'],");
		}
		if(!set.isEmpty())
			jsonBuffer = jsonBuffer.deleteCharAt(jsonBuffer.length() - 1);
		jsonBuffer.append("]");
		return jsonBuffer.toString();
	}
	/**
     * 
     * <br>Description:获取自然斑编号
     * <br>Author:侯文超
     * <br>Date:2014-01-10
     * @throws Exception
     */
	public void getZRBBH(){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.zrbbh from JC_ZIRAN t");
		List<Map<String, Object>> list = query(sqlBuffer.toString(), YW);
		response(list);
	} 
	
	public void setZrb() throws Exception{
		String zrbbh = request.getParameter("zrbbh");
		zrbbh = (zrbbh == null || zrbbh == "null")? "null" : zrbbh;
		zrbbh = new String(zrbbh.getBytes("iso-8859-1"), "utf-8");
		ZrbData zrbData = new ZrbData();
		boolean isExit = zrbData.isExit(ZrbData.formName, "zrbbh", zrbbh, YW);
		if(!isExit){
			zrbData.insertZrb(zrbbh);
		}
		String[][] fields = ZrbReport.shows;
		for(int i = 2; i < fields.length; i++){
			String value = request.getParameter(fields[i][0]);
			value = (value == null || value == "null")? "null" : value;
			value = new String(value.getBytes("iso-8859-1"), "utf-8");
			if(i < fields.length - 1){
				zrbData.modifyValue(zrbbh, fields[i][0], value, false);
			}else{
				zrbData.modifyValue(zrbbh, fields[i][0], value);
			}
		}
		response("{success:true}");
	}
	
	
}
