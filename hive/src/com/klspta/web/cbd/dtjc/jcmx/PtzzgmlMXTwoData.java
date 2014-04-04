package com.klspta.web.cbd.dtjc.jcmx;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;

public class PtzzgmlMXTwoData extends AbstractBaseBean{
	
	/*
	 * 
	 */
	public void getGMLData_CL(){
		String sql = "select * from zfjc.gmlmx1_parameter";
		List<Map<String,Object>> result = query(sql, YW);
		response(result);
	}
	
	public void Sava_GMLPA(){
		try{
	    	String SRKYYGFBL=request.getParameter("SRKYYGFBL");
	    	String FWLX=request.getParameter("FWLX");
	    	FWLX= new String(FWLX.getBytes("iso-8859-1"),"utf-8");
	    	String GFLX=request.getParameter("GFLX");
	    	GFLX = new String(GFLX.getBytes("iso-8859-1"),"utf-8");
	    	String SFGFK=request.getParameter("SFGFK");
	    	String ZXJZWZJBL=request.getParameter("ZXJZWZJBL");
	    	String QSYHS=request.getParameter("QSYHS");
	    	String GJJDKZGED=request.getParameter("GJJDKZGED");
	    	String DKZGNX=request.getParameter("DKZGNX");
	    	String GJJJNBL=request.getParameter("GJJJNBL");
	    	String GJJDKLL=request.getParameter("GJJDKLL");
	    	String SYDKJZLL=request.getParameter("SYDKJZLL");
	    	String SYDKLLFD=request.getParameter("SYDKLLFD");
	    	String YCKSJ=request.getParameter("YCKSJ");
	    	String ZDCK=request.getParameter("ZDCK");
	        String sql="update GMLMX1_PARAMETER set SRKYYGFBL=?,FWLX=?,GFLX=?,SFGFK=?,ZXJZWZJBL=?,QSYHS=?,GJJDKZGED=?,DKZGNX=?,GJJJNBL=?,GJJDKLL=?,SYDKJZLL=?,SYDKLLFD=?,YCKSJ=?,ZDCK=?";
	        update(sql,YW,new Object[]{SRKYYGFBL,FWLX,GFLX,SFGFK,ZXJZWZJBL,QSYHS,GJJDKZGED,DKZGNX,GJJJNBL,GJJDKLL,SYDKJZLL,SYDKLLFD,YCKSJ,ZDCK});
	        response("true");
	    	}catch(Exception e){
	            response("false");
	    	}
	}
}