package com.klspta.web.cbd.zcgl.tdzcgl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.model.CBDReport.CBDReportManager;
import com.klspta.model.CBDReport.tablestyle.ITableStyle;
import com.klspta.web.cbd.yzt.cbjhzhb.Cbjhzhb;
import com.klspta.web.cbd.yzt.jc.report.TableStyleEditRow;

/**
 * 
 * <br>
 * Title:TODO 类标题 <br>
 * Description:TODO 类功能描述 <br>
 * Author:黎春行 <br>
 * Date:2014-1-19
 */
public class TdzcglManager extends AbstractBaseBean {
	private String[][] fields = { { "dkmc", "false", "null" },
			{ "jsydmj", "false", "sum" }, { "rjl", "false", "avg" },
			{ "ghjzgm", "false", "sum" }, { "jzkzgd", "false", "avg" },
			{ "DJZJ", "false", "sum" }, { "DJYJLY", "false", "sum" },
			{ "DJYJLB", "false", "null" }, { "ZFSYZE", "false", "sum" },
			{ "ZFSYYJLY", "false", "sum" }, { "ZFSYYJLB", "false", "sum" },
			{ "ZFSYHTYD", "false", "null" }, { "ZFSYWYJ", "false", "sum" },
			{ "BCFZE", "false", "sum" }, { "BCFYJLY", "false", "sum" },
			{ "BCFYJLB", "false", "null" }, { "BCFHTYD", "false", "null" },
			{ "BCFYCSWY", "false", "sum" }, { "DJKJLSJ", "false", "null" },
			{ "CBZH", "false", "null" }, { "ZCMJ", "false", "sum" },
			{ "CRSJ", "false", "null" }, { "ZBR", "false", "null" },
			{ "JDSJ", "false", "null" }, { "YJSJ", "false", "null" },
			{ "KGSJ", "false", "null" }, { "TDXZSJ", "false", "null" },
			{ "YT", "false", "null" }, { "SFYL", "false", "null" },
			{ "DGDW", "false", "null" }, { "SX", "false", "null" },
			{ "bz", "false", "null" } };

	/**
	 * 
	 * <br>
	 * Description:TODO 方法功能描述 <br>
	 * Author:黎春行 <br>
	 * Date:2014-1-19
	 * 
	 * @throws Exception
	 */

	/**
	 * public void add() throws Exception{ String xmmc = new
	 * String(request.getParameter("xmmc").getBytes("iso-8859-1"),"utf-8");
	 * String dkmc = new
	 * String(request.getParameter("dkmc").getBytes("iso-8859-1"),"utf-8");
	 * String status = new
	 * String(request.getParameter("status").getBytes("iso-8859-1"),"utf-8");
	 * TdzcglData tdzcglData = new TdzcglData(); boolean result =
	 * tdzcglData.insertXMMC(xmmc, dkmc, status); response("{success:true}"); }
	 * 
	 * public void update() throws Exception{ String dkmc =new
	 * String(request.getParameter("key").getBytes("iso-8859-1"), "UTF-8");
	 * String index = request.getParameter("vindex"); String value = new
	 * String(request.getParameter("value").getBytes("iso-8859-1"), "UTF-8");
	 * String field = fields[Integer.parseInt(index)][0]; TdzcglData tdzcglData =
	 * new TdzcglData(); boolean result = tdzcglData.update(dkmc, field, value);
	 * response("{success:true}"); }
	 */

	public void add() throws Exception {
		String xmmc = request.getParameter("xmmc");
		String dkmc = request.getParameter("dkmc");
		String status = request.getParameter("status");
		String ydxz = request.getParameter("ydxz");
		String jsydmj = request.getParameter("jsydmj");
		String rjl = request.getParameter("rjl");
		String ghjzgm = request.getParameter("ghjzgm");
		String jzkzgd = request.getParameter("jzkzgd");
		String djkzj = request.getParameter("djkzj");
		String djkyjn = request.getParameter("djkyjn");
		String djkyjnbl = request.getParameter("djkyjnbl");
		String zfsyze = request.getParameter("zfsyze");
		String zfsyyjn = request.getParameter("zfsyyjn");
		String zfsyyjnbl = request.getParameter("zfsyyjnbl");
		String zfsyydjnsj = request.getParameter("zfsyydjnsj");
		String zfsywyj = request.getParameter("zfsywyj");
		String bcfze = request.getParameter("bcfze");
		String bcfyjn = request.getParameter("bcfyjn");
		String bcfyjnbl = request.getParameter("bcfyjnbl");
		String bcfydjnsj = request.getParameter("bcfydjnsj");
		String bcfwyj = request.getParameter("bcfwyj");
		String djkjnsj = request.getParameter("djkjnsj");
		String cbzh = request.getParameter("cbzh");
		String zzmj = request.getParameter("zzmj");
		String crsj = request.getParameter("crsj");
		String zbr = request.getParameter("zbr");
		String xyydjdsj = request.getParameter("xyydjdsj");
		String tdyjsj = request.getParameter("tdyjsj");
		String xmkgsj = request.getParameter("xmkgsj");
		String tdxzsj = request.getParameter("tdxzsj");
		String yt = request.getParameter("yt");
		String sfyl = request.getParameter("sfyl");
		String dgdw = request.getParameter("dgdw");
		String sx = request.getParameter("sx");
		String bz = request.getParameter("bz");
		String sql = "select t.xmmc as xmmc ,t.dkmc as dkmc from ZCGL_TDZCGL t where t.xmmc=? and t.dkmc=?";

		List<Map<String, Object>> list = query(sql, YW, new Object[] { xmmc,
				dkmc });
		if (list.size() == 0) {
			sql = "insert into ZCGL_TDZCGL(xmmc,thirsta,dkmc,djzj,"
					+ "djyjly,djyjlb,zfsyze,zfsyyjly,zfsyyjlb,zfsyhtyd,zfsywyj,bcfze,bcfyjly,bcfyjlb,"
					+ "bcfhtyd,bcfycswy,djkjlsj,cbzh,zcmj,crsj,zbr,jdsj,yjsj,kgsj,tdxzsj,yt,sfyl,dgdw,sx,bz)"
					+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			update(sql, YW, new Object[] { xmmc, status, dkmc, djkzj, djkyjn,
					djkyjnbl, zfsyze, zfsyyjn, zfsyyjnbl, zfsyydjnsj, zfsywyj,
					bcfze, bcfyjn, bcfyjnbl, bcfydjnsj, bcfwyj, djkjnsj, cbzh,
					zzmj, crsj, zbr, xyydjdsj, tdyjsj, xmkgsj, tdxzsj, yt,
					sfyl, dgdw, sx, bz });
		} else {
			sql = "update ZCGL_TDZCGL set thirsta=?,djzj=?,"
					+ "djyjly=?,djyjlb=?,zfsyze=?,zfsyyjly=?,zfsyyjlb=?,zfsyhtyd=?,zfsywyj=?,bcfze=?,bcfyjly=?,bcfyjlb=?,"
					+ "bcfhtyd=?,bcfycswy=?,djkjlsj=?,cbzh=?,zcmj=?,crsj=?,zbr=?,jdsj=?,yjsj=?,kgsj=?,tdxzsj=?"
					+ ",yt=?,sfyl=?,dgdw=?,sx=?,bz=? where xmmc=? and dkmc=?";
			update(sql, YW, new Object[] { status, djkzj, djkyjn, djkyjnbl,
					zfsyze, zfsyyjn, zfsyyjnbl, zfsyydjnsj, zfsywyj, bcfze,
					bcfyjn, bcfyjnbl, bcfydjnsj, bcfwyj, djkjnsj, cbzh, zzmj,
					crsj, zbr, xyydjdsj, tdyjsj, xmkgsj, tdxzsj, yt, sfyl,
					dgdw, sx, bz, xmmc, dkmc });
		}
		response("{success:true}");
	}

	public void getGHSJByDKMC() {
		String dkmc = request.getParameter("dkmc");
		String sql = "select * from dcsjk_kgzb where dkmc = ?";
		List<Map<String, Object>> list = query(sql, YW, new Object[] { dkmc });
		response(list);
	}

	public void deleteByDKMC() {
		try {
			String dkmc = request.getParameter("dkmc");
			dkmc = UtilFactory.getStrUtil().unescape(dkmc);
			String sql = "delete from ZCGL_TDZCGL where dkmc=?";
			update(sql, YW, new Object[] { dkmc });
			response("{success:true}");
		} catch (Exception e) {
			response("{success:false}");
		}

	}
	
	public void query() throws Exception{
		String xmmcs = UtilFactory.getStrUtil().unescape(request.getParameter("xmmc"));
		ITableStyle its = new TableStyleEditRow();
		response(new CBDReportManager().getReport("TDZCGL",new Object[]{xmmcs},its).toString());
	}

}
