package com.klspta.web.cbd.yzt.kgzb;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.model.CBDReport.CBDReportManager;
import com.klspta.model.CBDReport.tablestyle.ITableStyle;
import com.klspta.web.cbd.yzt.jc.report.TableStyleEditRow;
import com.klspta.web.cbd.yzt.zrb.ZrbData;
/*****
 * 
 * <br>Title:底层数据库——控规指标处理类
 * <br>Description:
 * <br>Author:朱波海
 * <br>Date:2014-1-15
 */
public class KgzbManager extends AbstractBaseBean {
/**
 * 
 * <br>Description:删除操作
 * <br>Author:朱波海
 * <br>Date:2014-1-15
 */
	public void delet() {
		String dkmc = request.getParameter("dkmc");
		dkmc = UtilFactory.getStrUtil().unescape(dkmc);
		String delet = "delete dcsjk_kgzb where dkmc=?  ";
		int i = update(delet, YW, new Object[] { dkmc });
		if (i == 1) {
			response("true");
		}

	}
/*****
 * 
 * <br>Description:查询功能
 * <br>Author:朱波海
 * <br>Date:2014-1-15
 */
	public void quey() {
		String yw_guid = request.getParameter("yw_guid");
		String type = request.getParameter("type");
		type = UtilFactory.getStrUtil().unescape(type);
		String quey = "select * from dcsjk_kgzb where yw_guid=? and dqy=? ";
		List<Map<String, Object>> query = query(quey, YW, new Object[] {
				yw_guid, type });
		response(query);

	}
/**
 * 
 * <br>Description:保存功能
 * <br>Author:朱波海
 * <br>Date:2014-1-15
 */
	public void save() {
		String DKMC = request.getParameter("dkmc");
		String YDXZDH = request.getParameter("ydxzdh");
		String YDXZ = request.getParameter("ydxz");
		String JSYDMJ = request.getParameter("jsydmj");
		String RJL = request.getParameter("rjl");
		String GHJZGM = request.getParameter("ghjzgm");

		String JZKZGD = request.getParameter("jzkzgd");
		String JZMD = request.getParameter("jzmd");
		String LHL = request.getParameter("lhl");
		String DBZS = request.getParameter("nbzs");
		String DXMK = request.getParameter("dxmk");
		String GHSJLY = request.getParameter("ghsjly");
		String BZ = request.getParameter("bz");
		String QY = request.getParameter("qy");

		DKMC = UtilFactory.getStrUtil().unescape(DKMC);
		BZ = UtilFactory.getStrUtil().unescape(BZ);
		YDXZ = UtilFactory.getStrUtil().unescape(YDXZ);
		YDXZDH = UtilFactory.getStrUtil().unescape(YDXZDH);
		GHSJLY = UtilFactory.getStrUtil().unescape(GHSJLY);
		int i = 0;

		if (JSYDMJ == null || JSYDMJ.equals("")) {
			JSYDMJ = "0";
		}
		if (GHJZGM == null || GHJZGM.equals("")) {
			GHJZGM = "0";
		}
		String sql = "select * from dcsjk_kgzb where dkmc=?";
		List<Map<String,Object>> list = query(sql, YW,new Object[]{DKMC});
		if (list.size()>0) {
			sql = " update  dcsjk_kgzb set DKMC='" + DKMC + "',YDXZDH='"
					+ YDXZDH + "' ,YDXZ='" + YDXZ + "' ,JSYDMJ='" + JSYDMJ
					+ "',RJL='" + RJL + "',GHJZGM='" + GHJZGM + "',JZKZGD='"
					+ JZKZGD + "',JZMD='" + JZMD + "',LHL='" + LHL + "',DBZS='"
					+ DBZS + "',DXMK='" + DXMK + "',GHSJLY='" + GHSJLY
					+ "',BZ='" + BZ + "' where dkmc=?";
			i = update(sql, YW, new Object[] { DKMC });
		} else {
			sql = " insert into  dcsjk_kgzb ( DKMC,YDXZDH,YDXZ ,JSYDMJ,RJL,GHJZGM,JZKZGD,JZMD,LHL,DBZS,DXMK,GHSJLY,BZ,QY) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			i = update(
					sql,
					YW,
					new Object[] { DKMC, YDXZDH, YDXZ, JSYDMJ, RJL, GHJZGM,
							JZKZGD, JZMD, LHL, DBZS, DXMK, GHSJLY, BZ, QY});
		}
		if (i == 1) {
			response("{success:true}");
		}
	}

	/**
	 * 
	 * <br>
	 * Description:控规指标列表过滤 <br>
	 * Author:李国明 <br>
	 * Date:2014-1-15
	 * 
	 * @throws Exception
	 */
	public void getReport() throws Exception {
		String keyword = request.getParameter("keyword");
		String type = request.getParameter("type");
		ITableStyle its = new TableStyleEditRow();
		if (!"reader".equals(type)) {
			response(String.valueOf(new CBDReportManager().getReport("SWCBR",
					new Object[] { "%" + keyword + "%" }, its)));
		} else {
			response(String.valueOf(new CBDReportManager().getReport("SWCBR",
					new Object[] { "%" + keyword + "%" }, its)));
		}
	}

	
	/**
     * 
     * <br>Description:控规指标上图
     * <br>Author:李国明
     * <br>Date:2014-01-19
     * @throws Exception 
     */
    public void drawZrb() throws Exception{
    	String dkmc = request.getParameter("dkmc");
    	String polygon = request.getParameter("polygon");
    	if (dkmc != null) {
    		dkmc = UtilFactory.getStrUtil().unescape(dkmc);
    	}else{
    		response("{error:not primary}");
    	}
    	boolean draw = KgzbData.getInstance().recordGIS(dkmc, polygon);
    	response(String.valueOf(draw)); 
    }
    /**
	 * 
	 * <br>
	 * Description:获取地块名称 <br>
	 * Author:侯文超 <br>
	 * Date:2014-02-20
	 * 
	 */
	public void getDKBH() {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.dkmc as dkmc from dcsjk_kgzb t");
		List<Map<String, Object>> list = query(sqlBuffer.toString(), YW);
		response(list);
	}
}
