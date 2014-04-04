package com.klspta.web.sanya.zhbg;

import java.util.List;
import java.util.Map;


import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

/**
 * 
 * <br>Title:案件管理类
 * <br>Description:TODO 类功能描述
 * <br>Author:黎春行
 * <br>Date:2013-9-2
 */
public class WjspHandler extends AbstractBaseBean{
	/**
	 * 
	 * <br>Description:获取所有待处理案件
	 * <br>Author:黎春行
	 * <br>Date:2013-9-2
	 */
	public void getAllDCLList(){
		List<Map<String, Object>> resultList = getList("未处理", "");
		response(resultList);
	}
	
	/**
	 * 
	 * <br>Description:根据关键字获取待处理案件
	 * <br>Author:黎春行
	 * <br>Date:2013-9-2
	 * @throws Exception 
	 */
	public void getDCLListByKeyWords() throws Exception{
		String keywords = request.getParameter("keyword");
		keywords = UtilFactory.getStrUtil().unescape(keywords);
		List<Map<String, Object>> resultList = getList("未处理", keywords);
		response(resultList);
	}
	
	/**
	 * 
	 * <br>Description:获取所有已处理案件
	 * <br>Author:黎春行
	 * <br>Date:2013-9-2
	 */
	public void getAllYCLList(){
		List<Map<String, Object>> resultList = getList("已处理", "");
		response(resultList);
	}
	
	
	public void getSGTHJZYTYCLList(){
		List<Map<String, Object>> resultList = getList("已处理", "省国土环境资源厅");
		response(resultList);
	}
	public void getSGTHJZYTYCLListByKeyWords(){
		String keywords = request.getParameter("keyword");
		keywords = UtilFactory.getStrUtil().unescape(keywords);
		List<Map<String, Object>> resultList = getList("已处理", "省国土环境资源厅", keywords);
		response(resultList);
	}
	
	public void getSGTHJJCZDYCLList(){
		List<Map<String, Object>> resultList = getList("已处理", "省国土环境监察总队");
		response(resultList);
	}
	public void getSGTHJJCZDYCLListByKeyWords(){
		String keywords = request.getParameter("keyword");
		keywords = UtilFactory.getStrUtil().unescape(keywords);
		List<Map<String, Object>> resultList = getList("已处理", "省国土环境监察总队", keywords);
		response(resultList);
	}
	
	public void getSWSZFYCLList(){
		List<Map<String, Object>> resultList = getList("已处理", "市委市政府");
		response(resultList);
	}
	public void getSWSZFYCLListByKeyWords(){
		String keywords = request.getParameter("keyword");
		keywords = UtilFactory.getStrUtil().unescape(keywords);
		List<Map<String, Object>> resultList = getList("已处理", "市委市政府", keywords);
		response(resultList);
	}
	
	public void getSYHJZYJYCLList(){
		List<Map<String, Object>> resultList = getList("已处理", "三亚环境资源局");
		response(resultList);
	}
	public void getSYHJZYJYCLListByKeyWords(){
		String keywords = request.getParameter("keyword");
		keywords = UtilFactory.getStrUtil().unescape(keywords);
		List<Map<String, Object>> resultList = getList("已处理", "三亚环境资源局", keywords);
		response(resultList);
	}
	
	public void getQTYCLList(){
		List<Map<String, Object>> resultList = getList("已处理", "其他");
		response(resultList);
	}
	public void getQTYCLListByKeyWords(){
		String keywords = request.getParameter("keyword");
		keywords = UtilFactory.getStrUtil().unescape(keywords);
		List<Map<String, Object>> resultList = getList("已处理", "其他", keywords);
		response(resultList);
	}
	
	
	/**
	 * 
	 * <br>Description:根据关键字获取已处理案件
	 * <br>Author:黎春行
	 * <br>Date:2013-9-2
	 */
	public void getYCLListByKeyWords(){
		String keywords = request.getParameter("keyword");
		keywords = UtilFactory.getStrUtil().unescape(keywords);
		List<Map<String, Object>> resultList = getList("已处理", keywords);
		response(resultList);
	}
	
	private List<Map<String, Object>> getList(String status, String type){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.wjspsx,t.wjlx,t.blsx, t.wjsq, t.blqk, t.yw_guid, t.createdate,t.zhblr  from wjspdjb t where t.blqk ='").append(status).append("'");
		//添加关键字查询
		if((!"".equals(type)) || type != null){
			sqlBuffer.append(" and (t.wjspsx||t.wjlx||t.blsx||t.wjsq||t.zhblr||t.blqk||t.createdate like '%");
			sqlBuffer.append(type);
			sqlBuffer.append("%')");
		}
		sqlBuffer.append(" order by t.createdate DESC"); 
		List<Map<String, Object>> returnList = query(sqlBuffer.toString(), YW);
		return returnList;
	}
	private List<Map<String, Object>> getList(String status,String type,String keywords){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.wjspsx,t.wjlx,t.blsx, t.wjsq, t.blqk, t.yw_guid, t.createdate,t.zhblr  from wjspdjb t where t.blqk ='").append(status).append("'").append(" and wjlx ='").append(type).append("'");
		//添加关键字查询
		if((!"".equals(keywords)) || keywords != null){
			sqlBuffer.append(" and (t.wjspsx||t.wjlx||t.blsx||t.wjsq||t.zhblr||t.blqk||t.createdate like '%");
			sqlBuffer.append(keywords);
			sqlBuffer.append("%')");
		}
		sqlBuffer.append(" order by t.createdate DESC"); 
		List<Map<String, Object>> returnList = query(sqlBuffer.toString(), YW);
		return returnList;
	}
	
	public void delete(){
		String yw_guid=request.getParameter("yw_guid");
		String sql = " Delete from wjspdjb where yw_guid=?";
		int i = update(sql, YW ,new Object[]{yw_guid});
		if(i==1){response("success");}else{
			response("false");
		}
		
	}
}
