package com.klspta.web.cbd.yzt.hxxm;




import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.model.CBDReport.CBDReportManager;
import com.klspta.model.CBDReport.tablestyle.ITableStyle;
import com.klspta.web.cbd.yzt.jbb.JbbReport;
import com.klspta.web.cbd.yzt.jc.report.TableStyleEditRow;

public class HxxmManager extends AbstractBaseBean {
	
	public void getHxxm() {
		HttpServletRequest request = this.request;
		response(new HxxmData().getAllList(request));
	}
	
	public void getHxxmmc(){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.xmname from JC_XIANGMU t");
		List<Map<String, Object>> list = query(sqlBuffer.toString(), YW);
		response(list);
	} 
	
	public void getQuery(){
		HttpServletRequest request = this.request;
		response(new HxxmData().getQuery(request));
	}
	
	public void updateHxxm() {
        HttpServletRequest request = this.request;
        if (new HxxmData().updateHxxm(request)) {
            response("{success:true}");
        } else {
            response("{success:false}");
        }
    }
	
	/**
	 * 
	 * <br>Description:TODO 方法功能描述
	 * <br>Author:黎春行
	 * <br>Date:2013-12-24
	 * @throws Exception 
	 */
	public void update() throws Exception{
    	String xmmc =new String(request.getParameter("key").getBytes("iso-8859-1"), "UTF-8");
    	String index = request.getParameter("vindex");
    	String value = new String(request.getParameter("value").getBytes("iso-8859-1"), "UTF-8");
    	String field = HxxmReport.shows[Integer.parseInt(index)][0];
    	response(String.valueOf(new HxxmData().modifyValue(xmmc, field, value)));
	}
	/**
	 * 
	 * <br>Description:添加一个新的红线项目
	 * <br>Author:黎春行
	 * <br>Date:2013-12-24
	 * @throws Exception 
	 */
	public void insert() throws Exception{
		String xmmc = new String(request.getParameter("xmmc").getBytes("iso-8859-1"), "UTF-8");
    	if (xmmc != null) {
    		xmmc = UtilFactory.getStrUtil().unescape(xmmc);
	        if (new HxxmData().insertHxxm(xmmc)) {
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
	 * <br>Description:删除红线项目
	 * <br>Author:黎春行
	 * <br>Date:2013-12-25
	 * @throws Exception
	 */
    public void delete() throws Exception{
    	boolean result = false;
    	HxxmData hxxmData = new HxxmData();
    	String hxxms =new String(request.getParameter("xmmc").getBytes("iso-8859-1"),"utf-8");
    	String[] hxxmArray = hxxms.split(",");
    	for(int i = 0; i < hxxmArray.length; i++){
    		result = result || hxxmData.delete(hxxmArray[i]);
    	}
    	response(String.valueOf(result));
    }
	
	
	public void draw() throws Exception{
    	//String guid = new String(request.getParameter("guid").getBytes("ISO-8859-1"),"UTF-8");
    	String guid = request.getParameter("guid");
		String polygon = request.getParameter("polygon");
    	if (guid != null) {
    		guid = UtilFactory.getStrUtil().unescape(guid);
    	}else{
    		response("{error:not primary}");
    	}
    	boolean draw = new HxxmData().recordGIS(guid, polygon);
    	response(String.valueOf(draw)); 
	}
	
    /**
     * 
     * <br>Description：红线项目列表过滤
     * <br>Author:黎春行
     * <br>Date:2013-12-25
     * @throws Exception
     */
	public void getReport() throws Exception{
		String keyword = request.getParameter("keyword");
		StringBuffer query = new StringBuffer();
		ITableStyle its = new TableStyleEditRow();
		if(keyword != null){
			query.append(" where ");
			keyword = UtilFactory.getStrUtil().unescape(keyword);
			StringBuffer querybuffer = new StringBuffer();
			String[][] nameStrings = HxxmReport.shows;
			for(int i = 1; i < nameStrings.length - 1; i++){
				querybuffer.append("upper(t.").append(nameStrings[i][0]).append(")||");
			}
			querybuffer.append("upper(t.").append(nameStrings[nameStrings.length - 1][0]).append(")) like '%").append(keyword).append("%'");
			query.append("(");
			query.append(querybuffer);
		}
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("query", query.toString());
		response(String.valueOf(new CBDReportManager().getReport("HXXM", new Object[]{conditionMap},its)));
	}
	
	
	public void getdkmc(){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.dkmc from dcsjk_kgzb t");
		List<Map<String, Object>> list = query(sqlBuffer.toString(), YW);
		response(list);
	} 

	public void getCQSJ(){
		String value = request.getParameter("value");
		String[] dkmcs = value.split(",");
		String sql = "select sum(zzsgm) as zzsgm,sum(zzzsgm) as zzzsgm ,sum(zzzshs) as zzzshs,sum(hjmj)" +
				" as hjmj,sum(fzzzsgm) as fzzzsgm,sum(fzzjs) as fzzjs from jc_jiben where dkmc in (";
		for(int i=0;i<dkmcs.length ;i++){
			if(i==dkmcs.length-1){
				sql += " ?)";
			}else {
				sql += " ?,";
			}
		}
		List<Map<String,Object>> list = query(sql, YW,dkmcs);
		response(list);
	}
	String[] items = {"xmmc","zd","jsyd","rjl", "jzgm","ghyt", "gjjzgm",
	     "jzjzgm", "szjzgm", "zzsgm", "zzzsgm", "zzzshs", "hjmj", "fzzzsgm", 
	 "fzzjs", "kfcb", "lmcb", "dmcb","yjcjj","yjzftdsy","cxb",  "cqqd", "cbfgl", 
	 "zzcqfy", "qycqfy", "qtfy", "azftzcb", "zzhbtzcb", "cqhbtz","qtfyzb","lmcjj",
	 "fwsj", "zj","jbdk"};
	public void modify(){
		String[] values = new String[items.length];
		String[] values1 = new String[items.length];
		String insertsql = "insert into jc_xiangmu (xmname,zd,jsyd,rjl,jzgm,ghyt,gjjzgm,jzjzgm,szjzgm,zzsgm,zzzsgm,zzzshs," +
				"hjmj,fzzzsgm,fzzjs,kfcb,lmcb,dmcb,yjcjj,yjzftdsy,cxb,cqqd,cbfgl,zzcqfy,qycqfy,qtfy,azftzcb,zzhbtzcb," +
				"cqhbtz,qtfyzb,lmcjj,fwsj,zj,dkmc) values (";
		String updatesql = "update jc_xiangmu set zd=?,jsyd=?,rjl=?,jzgm=?,ghyt=?,gjjzgm=?,jzjzgm=?,szjzgm=?,zzsgm=?,zzzsgm=?,zzzshs=?," +
		"hjmj=?,fzzzsgm=?,fzzjs=?,kfcb=?,lmcb=?,dmcb=?,yjcjj=?,yjzftdsy=?,cxb=?,cqqd=?,cbfgl=?,zzcqfy=?,qycqfy=?,qtfy=?,azftzcb=?,zzhbtzcb=?," +
		"cqhbtz=?,qtfyzb=?,lmcjj=?,fwsj=?,zj=?,dkmc=? where xmname=?";
		for( int i=0;i<items.length;i++){
			values[i] = request.getParameter(items[i]);
			if(i==items.length-1){
				values1[i] = request.getParameter(items[0]);
			}else {
				values1[i] = request.getParameter(items[i+1]);
			}
			insertsql +="?,";
		}
		insertsql = insertsql.substring(0,insertsql.length()-1);
		insertsql += ")";
		String sql = "select xmname from jc_xiangmu where xmname = ?";
		List<Map<String,Object>> list = query(sql, YW,new Object[]{request.getParameter("xmmc")});
		int i = 0; 
		if(list.size()>0){
			i = update(updatesql, YW,values1 );
		}else{
			i = update(insertsql,YW,values);
		}
		if(i==1){
			response("{success:true}");
		}else {
			response("{success:false}");
		}
	}
}
