package com.klspta.web.cbd.xmgl.xmbg;


import org.apache.poi.hslf.usermodel.SlideShow;

import com.klspta.base.AbstractBaseBean;

public class XmbgManager extends AbstractBaseBean {
	
	public void getPPT() throws Exception{
		request.setCharacterEncoding("UTF-8");
		String yw_guid = new String(request.getParameter("yw_guid").getBytes("iso-8859-1"),"utf-8");
		String file_ids = request.getParameter("file_id"); 
		ReportPPT reportPPT = new ReportPPT();
		SlideShow ppt = reportPPT.buildPPT(yw_guid, file_ids);
		response.setContentType("application/x-msdownload");
		response.setHeader( "Content-Disposition", "attachment; filename=reportPPT.ppt");
		ppt.write(response.getOutputStream());
		response.getOutputStream().close();
	}

}
