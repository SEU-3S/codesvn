package com.klspta.model.CBDReport;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.klspta.base.AbstractBaseBean;

public abstract class ReportExcel extends AbstractBaseBean {
	/**
	 * 
	 * <br>Description:根据reportId动态生成excel展现用table
	 * <br>Author:黎春行
	 * <br>Date:2013-11-6
	 * @return
	 */
	protected  abstract  String buildTable(String reportId);
	
	
	/**
	 * 
	 * <br>Description:将创建的table放入到Excel中,传递给前台
	 * <br>Author:黎春行
	 * <br>Date:2013-11-6
	 * @param request
	 */
	public void getExcel(HttpServletRequest request, HttpServletResponse response){
		//获取table
		String reportId = request.getParameter("reprotId");
		String table = buildTable(reportId);
		String fileName = "C:\\excel.xls";
		File file = new File(fileName);
		file.deleteOnExit();
		try {
			file.createNewFile();
			BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			bWriter.write(table);
			FileInputStream fis = new FileInputStream(file);
	    	int nSize = (int)fis.available();
	    	byte[] data = new byte[nSize];
	    	fis.read(data);  
			//将Excel文件传递给前台
			response.setContentType("application/x-msdownload");
			response.setHeader( "Content-Disposition", "attachment; filename=reportExcel.xls");
	    	response.getOutputStream().write(data);
	    	response.getOutputStream().close();
	    	fis.close();
	    	bWriter.close();
	    	file.deleteOnExit();
		} catch (FileNotFoundException e) {
			System.out.println("缓存文件创建失败");
		} catch (IOException e) {
			System.out.println("缓存文件写入失败");
		} 
	}
}
