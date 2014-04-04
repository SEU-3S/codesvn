package com.klspta.web.cbd.swkgl;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.model.CBDReport.CBDReportManager;
import com.klspta.model.CBDReport.tablestyle.ITableStyle;
import com.klspta.web.cbd.yzt.jc.report.TableStyleEditRow;

public class AzfzcManager extends AbstractBaseBean {

	private static AzfzcManager AzfzcManager;

	public static AzfzcManager getInstcne() {
		if (AzfzcManager == null) {
			AzfzcManager = new AzfzcManager();
		}
		return AzfzcManager;
	}

	public String getList() {
		String sql = "select t.*  from azfzc t ";
		List<Map<String, Object>> list = query(sql, YW);
		StringBuffer result = new StringBuffer(
				"<table id='azftable' name='esftable' ><tr id='-1'><td class='title'>序号</td><td class='title'>用地名称</td><td  class='title'>土地一级开发主体</td><td  class='title'>占地面积</td><td width=300 class='title'>建设用地</td><td  class='title'>规划容积率</td><td class='title'>规划建筑规模</td> ");
		result
				.append("<td class='title'>规划用途</td><td  class='title'>控高</td><td  class='title'>土地成本</td><td  class='title'>预计可形成安置房套数</td><td class='title'>供地方式</td><td class='title'>土地开发建设补偿协议</td><td class='title'>土地移交</td><td class='title'>安置房建设单位</td><td class='title'>土地出让合同</td><td class='title'>出让合同约定开工时间</td><td class='title'>土地证</td><td class='title'>备注</td></tr> ");
		String add = "<tr id='newRow' class='trsingle' style='display:none;'><td align='center' height='10' width='10'></td>"
				+ "<td align='center' height='10' width='100'><input id='ydmc' size=10 type='text'/></td>"
				+ "<td align='center' height='10' width='100'><input id='tdyjkfzt' size=10 type='text' /></td>"
				+ "<td align='center' height='10' width='100'><input id='zdmj' size=10 type='text' /></td>"
				+ "<td align='center' height='10' width='100'><input id='jsyd' size=10 type='text' /></td>"
				+ "<td align='center' height='10' width='100'><input id='ghrjl' size=10 type='text' /></td>"
				+ "<td align='center' height='10' width='100'><input id='ghjzgm' size=10 type='text' /></td>"
				+ "<td align='center' height='10' width='100'><input id='ghyt' size=10 type='text'/></td>"
				+ "<td align='center' height='10' width='100'><input id='kg' size=10 type='text' /></td>"
				+ "<td align='center' height='10' width='100'><input id='tdcb' size=10 type='text' /></td>"
				+ "<td align='center' height='10' width='100'><input id='yjkxcazfts' size=10 type='text' /></td>"
				+ "<td align='center' height='10' width='100'><input id='gdfs' size=10 type='text' /></td>"
				+ "<td align='center' height='10' width='100'><input id='tdkfjsbcxy' size=10 type='text' /></td>"
				+ "<td align='center' height='10' width='100'><input id='tdyj' size=10 type='text' /></td>"
				+ "<td align='center' height='10' width='100'><input id='azfjsdw' size=10 type='text' /></td>"
				+ "<td align='center' height='10' width='100'><input id='tdcrht' size=10 type='text' /></td>"
				+ "<td align='center' height='10' width='100'><input id='crhtydkgsj' size=10 type='text' /></td>"
				+ "<td align='center' height='10' width='100'><input id='tdz' size=10 type='text' /></td>"
				+ "<td align='center' height='10' width='100'><input id='bz' size=10 type='text' /></td>"
				+ "<td style='display:none;'><input  id='yw_guid'></td>"
				+ "</tr>";
		result.append(add);
		for (int i = 0; i < list.size(); i++) {
			String rownum = i + 1 + "";
			String yw_guid = (String) (list.get(i)).get("YW_GUID");
			result.append("<tr id=row" + i + "><td class='trsingle'>" + rownum
					+ "</td><td class='trsingle'>"
					+ (String) (list.get(i)).get("YDMC")
					+ "</td><td class='trsingle'>"
					+ (String) (list.get(i)).get("TDYJKFZT")
					+ "</td><td class='trsingle'>"
					+ (String) (list.get(i)).get("ZDMJ")
					+ "</td><td class='trsingle'>"
					+ (String) (list.get(i)).get("JSYD")
					+ "</td><td class='trsingle'>"
					+ (String) (list.get(i)).get("GHRJL")
					+ "</td><td class='trsingle'>"
					+ (String) (list.get(i)).get("GHJZGM")
					+ "</td><td class='trsingle'>"
					+ (String) (list.get(i)).get("GHYT")
					+ "</td><td class='trsingle'>"
					+ (String) (list.get(i)).get("KG")
					+ "</td><td class='trsingle'>"
					+ (String) (list.get(i)).get("TDCB")
					+ "</td><td class='trsingle'>"
					+ (String) (list.get(i)).get("YJKXCAZFTS")
					+ "</td><td class='trsingle'>"
					+ (String) (list.get(i)).get("GDFS")
					+ "</td><td class='trsingle'>"
					+ (String) (list.get(i)).get("TDKFJSBCXY")
					+ "</td><td class='trsingle'>"
					+ (String) (list.get(i)).get("TDYJ")
					+ "</td><td class='trsingle'>"
					+ (String) (list.get(i)).get("AZFJSDW")
					+ "</td><td class='trsingle'>"
					+ (String) (list.get(i)).get("TDCRHT")
					+ "</td><td class='trsingle'>"
					+ (String) (list.get(i)).get("CRHTYDKGSJ")
					+ "</td><td class='trsingle'>"
					+ (String) (list.get(i)).get("TDZ")
					+ "</td><td class='trsingle'>"
					+ (String) (list.get(i)).get("BZ")
					+ "</td><td style='display:none;'>" + yw_guid
					+ "</td></tr>");

		}

		result.append("</table>");
		return result.toString().replaceAll("null", "").replaceAll("\r\n",
				" ; ");
	}

	public void addAZFzc() {
		String ydmc = request.getParameter("ydmc");
		String tdyjkfzt = request.getParameter("tdyjkfzt");
		String zdmj = request.getParameter("zdmj");
		String jsyd = request.getParameter("jsyd");
		String ghrjl = request.getParameter("ghrjl");
		String ghjzgm = request.getParameter("ghjzgm");
		String ghyt = request.getParameter("ghyt");
		String kg = request.getParameter("kg");
		String tdcb = request.getParameter("tdcb");
		String yjkxcazfts = request.getParameter("yjkxcazfts");
		String gdfs = request.getParameter("gdfs");
		String tdkfjsbcxy = request.getParameter("tdkfjsbcxy");
		String tdyj = request.getParameter("tdyj");
		String azfjsdw = request.getParameter("azfjsdw");
		String tdcrht = request.getParameter("tdcrht");
		String crhtydkgsj = request.getParameter("crhtydkgsj");
		String tdz = request.getParameter("tdz");
		String bz = request.getParameter("bz");

		ydmc = UtilFactory.getStrUtil().unescape(ydmc);
		tdyjkfzt = UtilFactory.getStrUtil().unescape(tdyjkfzt);
		ghyt = UtilFactory.getStrUtil().unescape(ghyt);
		gdfs = UtilFactory.getStrUtil().unescape(gdfs);
		tdkfjsbcxy = UtilFactory.getStrUtil().unescape(tdkfjsbcxy);
		tdyj = UtilFactory.getStrUtil().unescape(tdyj);
		azfjsdw = UtilFactory.getStrUtil().unescape(azfjsdw);
		tdcrht = UtilFactory.getStrUtil().unescape(tdcrht);
		tdz = UtilFactory.getStrUtil().unescape(tdz);
		bz = UtilFactory.getStrUtil().unescape(bz);
		String sql = "select ydmc from zc_azfzc where ydmc =?";
		List<Map<String,Object>> list = query(sql, YW,new Object[]{ydmc});
		int i = 0;
		if(list.size()==0){
		String insertString = "insert into ZC_AZFZC  (ydmc,tdyjkfzt,zdmj,jsyd,ghrjl,ghjzgm,ghyt,kg,tdcb,yjkxcazfts,gdfs,tdkfjsbcxy,tdyj,azfjsdw,tdcrht,crhtydkgsj,tdz,bz)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		i = update(insertString, YW, new Object[] { ydmc, tdyjkfzt, zdmj,
				jsyd, ghrjl, ghjzgm, ghyt, kg, tdcb, yjkxcazfts, gdfs,
				tdkfjsbcxy, tdyj, azfjsdw, tdcrht, crhtydkgsj, tdz, bz });
		}else {
			String insertString = "update zc_azfzc  set tdyjkfzt=?,zdmj=?,jsyd=?,ghrjl=?,ghjzgm=?,ghyt=?,kg=?,tdcb=?,yjkxcazfts=?,gdfs=?,tdkfjsbcxy=?,tdyj=?,azfjsdw=?,tdcrht=?,crhtydkgsj=?,tdz=?,bz=? where ydmc=?";
			i = update(insertString, YW, new Object[] {  tdyjkfzt, zdmj,
					jsyd, ghrjl, ghjzgm, ghyt, kg, tdcb, yjkxcazfts, gdfs,
					tdkfjsbcxy, tdyj, azfjsdw, tdcrht, crhtydkgsj, tdz, bz ,ydmc});
		}
		if (i > 0) {
		    response("{success:true}");
		} else {
		    response("{success:failure}");
		}
	}

	public void updateAZFzc() {
		String yw_guid = request.getParameter("yw_guid");
		String ydmc = request.getParameter("ydmc");
		String tdyjkfzt = request.getParameter("tdyjkfzt");
		String zdmj = request.getParameter("zdmj");
		String jsyd = request.getParameter("jsyd");
		String ghrjl = request.getParameter("ghrjl");
		String ghjzgm = request.getParameter("ghjzgm");
		String ghyt = request.getParameter("ghyt");
		String kg = request.getParameter("kg");
		String tdcb = request.getParameter("tdcb");
		String yjkxcazfts = request.getParameter("yjkxcazfts");
		String gdfs = request.getParameter("gdfs");
		String tdkfjsbcxy = request.getParameter("tdkfjsbcxy");
		String tdyj = request.getParameter("tdyj");
		String azfjsdw = request.getParameter("azfjsdw");
		String tdcrht = request.getParameter("tdcrht");
		String crhtydkgsj = request.getParameter("crhtydkgsj");
		String tdz = request.getParameter("tdz");
		String bz = request.getParameter("bz");

		ydmc = UtilFactory.getStrUtil().unescape(ydmc);
		tdyjkfzt = UtilFactory.getStrUtil().unescape(tdyjkfzt);
		ghyt = UtilFactory.getStrUtil().unescape(ghyt);
		gdfs = UtilFactory.getStrUtil().unescape(gdfs);
		tdkfjsbcxy = UtilFactory.getStrUtil().unescape(tdkfjsbcxy);
		tdyj = UtilFactory.getStrUtil().unescape(tdyj);
		azfjsdw = UtilFactory.getStrUtil().unescape(azfjsdw);
		tdcrht = UtilFactory.getStrUtil().unescape(tdcrht);
		tdz = UtilFactory.getStrUtil().unescape(tdz);
		bz = UtilFactory.getStrUtil().unescape(bz);
		String insertString = "update azfzc  set ydmc=?,tdyjkfzt=?,zdmj=?,jsyd=?,ghrjl=?,ghjzgm=?,ghyt=?,kg=?,tdcb=?,yjkxcazfts=?,gdfs=?,tdkfjsbcxy=?,tdyj=?,azfjsdw=?,tdcrht=?,crhtydkgsj=?,tdz=?,bz=? where yw_guid=?";
		int i = update(insertString, YW, new Object[] { ydmc, tdyjkfzt, zdmj,
				jsyd, ghrjl, ghjzgm, ghyt, kg, tdcb, yjkxcazfts, gdfs,
				tdkfjsbcxy, tdyj, azfjsdw, tdcrht, crhtydkgsj, tdz, bz ,yw_guid});
		if (i > 0) {
			response("success");
		} else {
			response("failure");
		}
	}

	
	public void delByYwGuid() {
		try {
			String yw_guid = request.getParameter("yw_guid");
			String sql = "delete from azfzc where yw_guid=?";
			update(sql, YW, new Object[] { yw_guid });
			response("success");
		} catch (Exception e) {
			response("false");
			// TODO: handle exception
		}
	}
	public void getReport(){
		String keyword = request.getParameter("keyword");
		keyword=UtilFactory.getStrUtil().unescape(keyword);
		ITableStyle its = new TableStyleEditRow();
		 StringBuffer buffer = new CBDReportManager().getReport("AZFZC",new Object[]{"%"+keyword+"%"},its);
		 response(buffer.toString());
	}
}
