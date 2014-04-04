package com.klspta.web.cbd.dtjc.jcmx;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;

public class PtzzgmlMXOneData extends AbstractBaseBean {
	
	public void getCL(){
		String sql = "select * from GML_PARAMETER_CL";
		List<Map<String,Object>> list = query(sql, YW);
		response(list);
	}
	
	public void getBL(){
		String sql = "select * from GML_PARAMETER_BL";
		List<Map<String,Object>> list = query(sql, YW);
		response(list);
	}
	
	public void Sava_CL(){
		try{
	    	String ZXJZWBL=request.getParameter("ZXJZWBL");
	    	String QSYHS=request.getParameter("QSYHS");
	    	String YYS=request.getParameter("YYS");
	    	String SXF=request.getParameter("SXF");
	    	String ZJF=request.getParameter("ZJF");
	    	String JKHKQX=request.getParameter("JKHKQX");
	    	String GJJDKZGED=request.getParameter("GJJDKZGED");
	    	String DKNXNLYQSX=request.getParameter("DKNXNLYQSX");
	    	String YJCGJJBL=request.getParameter("YJCGJJBL");
	    	String DKZGNX=request.getParameter("DKZGNX");
	    	String GJJDKLL=request.getParameter("GJJDKLL");
	    	String SYDKJZLL=request.getParameter("SYDKJZLL");
	    	String SYDKLLFD=request.getParameter("SYDKLLFD");
	        String sql="update GML_PARAMETER_CL set ZXJZWBL=?,QSYHS=?,YYS=?,SXF=?,ZJF=?,JKHKQX=?,GJJDKZGED=?,DKNXNLYQSX=?,YJCGJJBL=?,DKZGNX=?,GJJDKLL=?,SYDKJZLL=?,SYDKLLFD=?";
	        update(sql,YW,new Object[]{ZXJZWBL,QSYHS,YYS,SXF,ZJF,JKHKQX,GJJDKZGED,DKNXNLYQSX,YJCGJJBL,DKZGNX,GJJDKLL,SYDKJZLL,SYDKLLFD});
	        response("true");
	    	}catch(Exception e){
	            response("false");
	    	}
	}
	
	public void Sava_BL(){
		try{
	    	String NGFWMJ=request.getParameter("NGFWMJ");
	    	String NGFWDJ=request.getParameter("NGFWDJ");
	    	String FWLX=request.getParameter("FWLX");
	    	FWLX = new String(FWLX.getBytes("iso-8859-1"),"utf-8");
	    	String ESFSYNS=request.getParameter("ESFSYNS");
	    	String FL=request.getParameter("FL");
	    	String GFLX=request.getParameter("GFLX");
	    	GFLX= new String(GFLX.getBytes("iso-8859-1"),"utf-8");
	    	String JTYKZPSR=request.getParameter("JTYKZPSR");
	    	String YYYHDZJBL=request.getParameter("YYYHDZJBL");
	    	String WXXQJK=request.getParameter("WXXQJK");
	    	String GJJLXJNNS=request.getParameter("GJJLXJNNS");
	    	String GFRNL=request.getParameter("GFRNL");
	    	
	        String sql="update GML_PARAMETER_BL set NGFWMJ=?,NGFWDJ=?,FWLX=?,ESFSYNS=?,FL=?," +
	        		"GFLX=?,JTYKZPSR=?,YYYHDZJBL=?,WXXQJK=?,GJJLXJNNS=?,GFRNL=?";
	        update(sql,YW,new Object[]{NGFWMJ,NGFWDJ,FWLX,ESFSYNS,FL,
	        		GFLX,JTYKZPSR,YYYHDZJBL,WXXQJK,GJJLXJNNS,GFRNL});
	        response("true");
	    	}catch(Exception e){
	            response("false");
	    	}
	}
}
