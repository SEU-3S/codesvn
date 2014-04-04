package com.klspta.web.cbd.dtjc.tjbh;

import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.CBDReportManager;
/**
 * 
 * <br>Title:年度、季度计划管理类
 * <br>Description:为了解决使用jsp加载报表速度较慢问题，采用异步加载方式加载
 * <br>Author:黎春行
 * <br>Date:2014-1-10
 */
public class JhManager extends AbstractBaseBean  {
	
	public void getJdjhReport(){
		response(String.valueOf(new CBDReportManager().getReport("JDJH")));
	}
	
	
	public void getNdjhReport(){
		response(String.valueOf(new CBDReportManager().getReport("NDJH")));
		
		
	}
	

}
