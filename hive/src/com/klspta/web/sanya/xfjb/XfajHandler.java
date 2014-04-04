package com.klspta.web.sanya.xfjb;

import java.util.List;
import java.util.Map;


import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

/**
 * 
 * <br>Title:信访案件管理类
 * <br>Description:TODO 类功能描述
 * <br>Author:黎春行
 * <br>Date:2013-9-2
 */
public class XfajHandler extends AbstractBaseBean{
	/**
	 * 
	 * <br>Description:获取所有待处理信访案件
	 * <br>Author:黎春行
	 * <br>Date:2013-9-2
	 */
	public void getAllDCLList(){
		List<Map<String, Object>> resultList = getList("未处理", "");
		response(resultList);
	}
	
	/**
	 * 
	 * <br>Description:根据关键字获取待处理信访案件
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
	 * <br>Description:获取所有已处理信访案件
	 * <br>Author:黎春行
	 * <br>Date:2013-9-2
	 */
	public void getAllYCLList(){
		List<Map<String, Object>> resultList = getList("已处理", "");
		response(resultList);
	}
	
	/**
	 * 
	 * <br>Description:根据关键字获取已处理信访案件
	 * <br>Author:黎春行
	 * <br>Date:2013-9-2
	 */
	public void getYCLListByKeyWords(){
		String keywords = request.getParameter("keyword");
		keywords = UtilFactory.getStrUtil().unescape(keywords);
		List<Map<String, Object>> resultList = getList("已处理", keywords);
		response(resultList);
	}
	
	private List<Map<String, Object>> getList(String status, String keywords){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.xfsx,t.xflx,t.blsx, t.blks, t.blzt, t.yw_guid, t.createdate,t.zhblr  from xfajdjb t where t.blzt ='").append(status).append("'");
		//添加关键字查询
		if((!"".equals(keywords)) || keywords != null){
			sqlBuffer.append(" and (t.zhblr||t.xfsx||t.xflx||t.blsx||t.blks||t.blzt like '%");
			sqlBuffer.append(keywords);
			sqlBuffer.append("%')");
		}
		sqlBuffer.append(" order by t.createdate DESC"); 
		List<Map<String, Object>> returnList = query(sqlBuffer.toString(), YW);
		return returnList;
	}
	/**
     * 
     * <br>Description: 删除指定的编号的信访案件
     * <br>Author:侯文超
     * <br>Date:2013-09-27
     * @return
     */
    public void delete() {
    	String yw_guid=request.getParameter("yw_guid");
		String sql = "Delete from xfajdjb where yw_guid=?";
		int i = update(sql, YW ,new Object[]{yw_guid});
		if(i==1){response("success");}else{
			response("false");
		}
    }
	
	
}
