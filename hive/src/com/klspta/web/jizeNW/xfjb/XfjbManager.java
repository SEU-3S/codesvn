package com.klspta.web.jizeNW.xfjb;

import java.io.UnsupportedEncodingException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

public class XfjbManager extends AbstractBaseBean{
	
	/**
	 * 
	 * <br>Description:生成线索号
	 * <br>Author:姚建林
	 * <br>Date:2013-9-10
	 * @return
	 */
	public String buildXSH(){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", new DateFormatSymbols());
		String dateString = df.format(Calendar.getInstance().getTime());
				
		String numsql = "select max(t.xsh) num from wgwfxsdjb t where t.xsh like '" + dateString + "%'";
		String num;
		List<Map<String, Object>> result = query(numsql, YW);
		String nestNum = String.valueOf(result.get(0).get("num"));
		if(nestNum.equals("null") || nestNum.equals("")){
			num = dateString + "001";
		}else{
			String temp = nestNum.substring(nestNum.length() - 3, nestNum.length());
            temp=String.valueOf(Integer.parseInt(temp)+1);   
            temp = "00" + temp;
            num = dateString + temp.substring(temp.length() - 3);
		}
		return num;
	}
	
	/**
	 * 
	 * <br>Description:判断此yw_guid是否在数据库中存在
	 * <br>Author:姚建林
	 * <br>Date:2013-9-10
	 * @param strKey时需要判断的yw_guid
	 * @return
	 */
	public String checkGuid(String strKey){
		String newForm = "true";
		String sql = "select t.yw_guid from wgwfxsdjb t where t.yw_guid = ?";
		List<Map<String, Object>> resultList = query(sql, YW ,new Object[]{strKey});
		if(resultList.size() == 1){
			newForm = "false";
		}
		return newForm;
	}
	
	/**
	 * 
	 * <br>Description:获取所有未反馈的信访
	 * <br>Author:姚建林
	 * <br>Date:2013-9-10
	 */
	public void getAllDCLList(){
		List<Map<String, Object>> resultList = getList("0", "");
		response(resultList);
	}
	
	/**
	 * 
	 * <br>Description:根据关键字获取未反馈的信访
	 * <br>Author:姚建林
	 * <br>Date:2013-9-10
	 */
	public void getDCLListByKeyWords() throws Exception{
		String keywords = request.getParameter("keyword");
		keywords = UtilFactory.getStrUtil().unescape(keywords);
		List<Map<String, Object>> resultList = getList("0", keywords);
		response(resultList);
	}
	
	/**
	 * 
	 * <br>Description:获取所有已处理信访案件
	 * <br>Author:黎春行
	 * <br>Date:2013-9-2
	 */
	public void getAllYCLList(){
		List<Map<String, Object>> resultList = getList("1", "");
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
		List<Map<String, Object>> resultList = getList("1", keywords);
		response(resultList);
	}
	
	private List<Map<String, Object>> getList(String status, String keywords){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.YW_GUID,t.XSH,t.XSLX, t.BLFS, t.JBR, t.BJBDW, t.DJSJ  from WGWFXSDJB t where t.STATUS ='").append(status).append("'");
		//添加关键字查询
		if((!"".equals(keywords)) || keywords != null){
			sqlBuffer.append(" and (t.XSH||t.XSLX||t.BLFS||t.JBR||t.BJBDW like '%");
			sqlBuffer.append(keywords);
			sqlBuffer.append("%')");
		}
		sqlBuffer.append(" order by t.BUILDYEAR DESC"); 
		List<Map<String, Object>> returnList = query(sqlBuffer.toString(), YW);
		return returnList;
	}
	
	/**
	 * 
	 * <br>Description:得到前一条或者后一条的YW_GUID
	 * <br>Author:姚建林
	 * <br>Date:2013-9-12
	 */
	public void getPreOrNext(){
		String preOrNext = request.getParameter("preOrNext");
		String yw_guid = request.getParameter("yw_guid");
		String keywords = request.getParameter("keyWord");
		String status = request.getParameter("status");
		String sql = "";
		if("pre".equals(preOrNext)){
			sql = "select yw_guid from wgwfxsdjb t where yw_guid = (select c.p from (select yw_guid,lag(yw_guid,1,0)"+
					" over (order by BUILDYEAR) as p from wgwfxsdjb) c where c.yw_guid = ?) and t.status = " + status;
		}else{
			sql = "select yw_guid from wgwfxsdjb t where yw_guid = (select c.p from (select yw_guid,lead(yw_guid,1,0)"+
					" over (order by BUILDYEAR) as p from wgwfxsdjb) c where c.yw_guid = ?) and t.status = " + status;
		}
		//添加关键字查询
		if((keywords != null && !"".equals(keywords))){
			try {
				keywords = new String(keywords.getBytes("ISO8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			sql = sql + " and (t.XSH||t.XSLX||t.BLFS||t.JBR||t.BJBDW like '%" + keywords + "%')";
		}
		List<Map<String, Object>> result = query(sql, YW, new Object[]{yw_guid});
		if(result.size() == 0){
			response("error");
		}else{
			response((String)result.get(0).get("yw_guid"));
		}
	}
}
