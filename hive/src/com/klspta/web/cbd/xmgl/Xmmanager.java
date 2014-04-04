package com.klspta.web.cbd.xmgl;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.web.cbd.xmgl.zjgl.TreeManager;
import com.klspta.web.cbd.xmgl.zjgl.ZjglData;

public class Xmmanager extends AbstractBaseBean {
	public static String ZJGL_LR[] = { "LB", "YSFY", "LJ", "YFSDZ", "ZJJD",
			"CQYE", "YY", "EY", "SANY", "SIY", "WY", "LY", "QY", "BAY", "JY",
			"SIYUE", "SYY", "SEY", "LRSP" };

	public static String ZJGL_ZC[] = { "LB", "YSFY", "LJ", "JL2", "YFSDZ",
			"ZJJD", "CQYE", "YY", "EY", "SANY", "SIY", "WY", "LY", "QY", "BAY",
			"JY", "SIYUE", "SYY", "SEY", "LRSP" };

	public static String ZJGL_ZJZC;

	public static Xmmanager instens;

	private Xmmanager() {
	}

	public static Xmmanager getXmmanager() {
		if (instens == null) {
			return new Xmmanager();
		} else {
			return instens;
		}
	}

	public void init() {
		// ArrayList<?> arrayList = new ArrayList<?>();
		// 获取全部支出yw_guid
		String sql = "select distinct yw_guid  from  xmzjgl where status='zc'";
		List<Map<String, Object>> list = query(sql, YW);
		// t.zcstatus
		for (int i = 0; i < list.size(); i++) {
			String yw_guid = list.get(i).get("yw_guid").toString();
			// 一个项目的所有lb
			String lxsql = "select distinct lb  from  xmzjgl where status='zc' and yw_guid=?";
			List<Map<String, Object>> lblist = query(lxsql, YW,
					new Object[] { yw_guid });
			for (int j = 0; j < lblist.size(); j++) {

			}

		}

	}

	/***************************************************************************
	 * 
	 * <br>
	 * Description:项目管理中——办理过程 <br>
	 * Author:朱波海 <br>
	 * Date:2013-12-16
	 */
	public List<Map<String, Object>> getBLGC(String yw_guid) {
		String sql = "select * from xmblgc where xmid=?  order  by  blsj ";
		List<Map<String, Object>> list = query(sql, YW,
				new Object[] { yw_guid });
		return list;
	}
	
	/***************************************************************************
	 * 
	 * <br>
	 * Description:项目管理中——办理过程 <br>
	 * Author:李国明 <br>
	 * Date:2014-2-16
	 */
	public void getBLGCByKey() {
		String key = request.getParameter("key");
		String xmid = request.getParameter("xmid");
		key = UtilFactory.getStrUtil().unescape(key);
		String sql  = "";
		List<Map<String,Object>> list = null;
		if(key==""){
			sql = "select * from xmblgc where xmid=? order by blsj";
			list = query(sql, YW,new Object[]{xmid});
		}else{
			sql = "select * from xmblgc where xmid=? and blsj||sjbl||bmjbr||bz like ?  order  by  blsj ";
			list = query(sql, YW,
					new Object[] { xmid,"%"+key+"%" });
		}
		 
		StringBuffer sb = new StringBuffer();
		sb.append("<table width='800' cellpadding='1' cellspacing='0' id='esftable' border='1'>"+
				"<tr class='title' onclick='showMap(this); return false;' ondblclick='editMap(this); return false;'>"+
					"<td align='center' width='80px' height='50px' ><h3>序号</h3></td>"+
					"<td align='center' width='90px'><h3>时间</h3></td>"+
					"<td align='center' width='500px><h3>事件</h3></td>"+
					"<td align='center' width='120px'><h3>部门/经办人</h3></td>"+
					"<td align='center' width='200px'><h3>备注</h3></td>"+
					"<td align='center' width='200px' style='display: none;><h3>yw_guid</h3></td>"+
				"</tr>");
		if (list != null) {
	        for (int i = 0; i < list.size(); i++) {
	        	sb.append("<tr align='center' class='trsingle' id='row").append(i).append("' onclick='showMap(this); return false;' ondblclick='editMap(this); return false;'>")
					.append("<td align='center' width='80px' >").append(i+1).append("</td>")
					.append("<td align='center' width='80px>").append(list.get(i).get("blsj")).append("</td>")
					.append("<td align='center' width='500px'>").append(list.get(i).get("sjbl")).append("</td>")
					.append("<td align='center' width='80px'>").append(list.get(i).get("bmjbr")).append("</td>")
					.append("<td align='center' width='200px'>").append(list.get(i).get("bz")).append("</td>")
					.append("<td align='center' width='200px' style='display: none;'>").append(list.get(i).get("yw_guid")).append("</td>")
				.append("</tr>");
	        }
		}
		sb.append("</table>");
		response(sb.toString());
		
	}

	/***************************************************************************
	 * 
	 * <br>
	 * Description:项目管理——保存办理过程 <br>
	 * Author:朱波海 <br>
	 * Date:2013-12-16
	 */
	public void saveBLGC() {
		String rq = request.getParameter("rq");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(rq);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String sj = request.getParameter("sj");
		String jbr = request.getParameter("jbr");
		String bz = request.getParameter("bz");
		String xmid = request.getParameter("xmid").trim();
		String yw_guid = request.getParameter("yw_guid").trim();
		sj = UtilFactory.getStrUtil().unescape(sj);
		jbr = UtilFactory.getStrUtil().unescape(jbr);
		bz = UtilFactory.getStrUtil().unescape(bz);
		int i = 0;
		if (yw_guid != null && !"".equals(yw_guid)) {
			String sql = "update  xmblgc set blsj='" + rq + "',sjbl='" + sj
					+ "',bmjbr='" + jbr + "',bz='" + bz
					+ "' where yw_guid=? and  xmid=?";
			i = update(sql, YW, new Object[] {  yw_guid,xmid });
		} else {
			String sql = "insert into xmblgc (blsj,sjbl,bmjbr,bz,xmid )values(?,?,?,?,?)";
			i = update(sql, YW, new Object[] { rq, sj, jbr, bz, xmid });
		}
		if (i > 0) {
			response("{success:true}");
		} else {
			response("{success:false}");
		}
	}

	/***************************************************************************
	 * 
	 * <br>
	 * Description:删除 <br>
	 * Author:朱波海 <br>
	 * Date:2013-12-30
	 */
	public void delBLGC() {
		String yw_guid = request.getParameter("yw_guid").trim();
		String sql = "delete xmblgc where YW_GUID=? ";
		int i = update(sql, YW, new Object[] { yw_guid });
		if (i > 0) {
			response("{success:true}");
		} else {
			response("{success:false");
		}

	}

	/***************************************************************************
	 * 
	 * <br>
	 * Description:获取红线项目 <br>
	 * Author:朱波海 <br>
	 * Date:2013-12-16
	 */
	public List<Map<String, Object>> getHXXM() {
		String sql = "select xmname,yw_guid,rownum from jc_xiangmu ";
		List<Map<String, Object>> list = query(sql, YW);
		return list;

	}

	/***************************************************************************
	 * 
	 * <br>
	 * Description:项目管理——资金管理——资金流入 <br>
	 * Author:朱波海 <br>
	 * Date:2013-12-17
	 */
	public List<Map<String, Object>> getZJGL_ZJLR(String yw_guid) {
		String sql = "select * from  xmzjgl where status='lr' and yw_guid=?";
		List<Map<String, Object>> list = query(sql, YW,
				new Object[] { yw_guid });
		return list;
	}

	/***************************************************************************
	 * 
	 * <br>
	 * Description:保存资金管理——资金流入 <br>
	 * Author:朱波海 <br>
	 * Date:2013-12-17
	 * @throws Exception 
	 */
	public void saveZJGL_ZJLR() throws Exception {

		String yw_guid = request.getParameter("yw_guid");
		String val = request.getParameter("val");
		String cols = request.getParameter("cols");
		int i = Integer.parseInt(cols);
		String stye = request.getParameter("stye");
		stye = new String(stye.getBytes("iso-8859-1"), "UTF-8");
		String sql = "update xmzjgl_lr set " + ZJGL_LR[i - 1] + "= " + val
				+ "  where yw_guid=? and status=?";
		update(sql, YW, new Object[] { yw_guid, stye });
	}

	/***************************************************************************
	 * 
	 * <br>
	 * Description:项目管理——资金管理——资金支出 <br>
	 * Author:朱波海 <br>
	 * Date:2013-12-17
	 */
	public List<Map<String, Object>> getZJGL_ZJZC(String yw_guid) {
		String sql = "select distinct lb,yw_guid  from  xmzjgl where status='zc' and yw_guid=?";
		List<Map<String, Object>> list = query(sql, YW,
				new Object[] { yw_guid });
		// t.zcstatus
		for (int i = 0; i < list.size(); i++) {
			list.get(i).get("lb").toString();

		}
		return list;
	}

	/***************************************************************************
	 * 
	 * <br>
	 * Description:保存资金管理——资金支出 <br>
	 * Author:朱波海 <br>
	 * Date:2013-12-17
	 */
	public void saveZJGL_ZJZC() {
		String yw_guid = request.getParameter("yw_guid");
		String val = request.getParameter("val");
		String status = request.getParameter("status");
		String lb = request.getParameter("lb");
		lb = UtilFactory.getStrUtil().unescape(lb);
		String sort = request.getParameter("sort");
		String cols = request.getParameter("cols");
		int i = Integer.parseInt(cols);
		String sql = "update xmzjgl_zc set " + ZJGL_ZC[i - 1] + "= " + val
				+ "  where yw_guid=? and status=? and lb=? and sort=?";
		update(sql, YW, new Object[] { yw_guid, status, lb, sort });

	}

	/***************************************************************************
	 * 
	 * <br>
	 * Description:保存树 <br>
	 * Author:朱波海 <br>
	 * Date:2013-12-25
	 */
	public void saveZjglTree() {
		String st[] = {"ZJLR", "QQFY", "CQFY", "SZFY", "CWFY", "GLFY", "CRZJFH",
				"QTZC" };
		String yw_guid = request.getParameter("yw_guid").trim();
		String parent_id = request.getParameter("parent_id");
		String selct_id = request.getParameter("id");
		String selet_year = request.getParameter("selet_year");
		String tree_name = request.getParameter("tree_name");
		tree_name = UtilFactory.getStrUtil().unescape(tree_name).trim();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String date = dateFormat.format(new Date());
		//for (int i = 0; i < st.length; i++) {
		//	if (selct_id.equals(st[i])) {
				String sql = " insert into zjgl_tree (yw_guid,parent_id,tree_name,tree_id,rq)values (?,?,?,?,?)";
				update(sql, YW, new Object[] { yw_guid, selct_id, tree_name,
						date, selet_year });
				new ZjglData().saveNode(tree_name, selct_id, yw_guid, selet_year);
		//	}
		//}
	}

	public void delt_tree() {
		String st[] = {"ZJLR", "QQFY", "CQFY", "SZFY", "CWFY", "GLFY", "CRZJFH",
				"QTZC" };
		String yw_guid = request.getParameter("yw_guid").trim();
		String parent_id = request.getParameter("parent_id");
		String id = request.getParameter("id");
		String selet_year = request.getParameter("selet_year");
		String tree_text = request.getParameter("tree_text");
		String roorId = request.getParameter("rootId");
		tree_text = UtilFactory.getStrUtil().unescape(tree_text).trim();
		for (int i = 0; i < st.length; i++) {
			String sql = " delete zjgl_tree where  yw_guid=? and parent_id=? and tree_name=? and tree_id=? and rq=?";
			update(sql, YW, new Object[] { yw_guid, parent_id, tree_text,
					id, selet_year });
				if(roorId.equals("ZJLR")){
					String delet = "delete XMZJGL_LR where yw_guid=? and lb=? and rq=?";
					update(delet, YW, new Object[] { yw_guid, tree_text, selet_year });
				}else{
					String delet = "delete XMZJGL_ZC where yw_guid=? and lb=? and zcstatus=? and rq=?";
					update(delet, YW, new Object[] { yw_guid, tree_text, parent_id,
							selet_year });
				}
			
		}
	}

	public void modify_tree() {
		String st[] = {"ZJLR", "QQFY", "CQFY", "SZFY", "CWFY", "GLFY", "CRZJFH",
				"QTZC" };
		String yw_guid = request.getParameter("yw_guid").trim();
		String parent_id = request.getParameter("parent_id");
		String id = request.getParameter("id");
		String selet_year = request.getParameter("selet_year");
		String tree_text = request.getParameter("tree_text");
		tree_text = UtilFactory.getStrUtil().unescape(tree_text).trim();
		for (int i = 0; i < st.length; i++) {
			if (!id.equals(st[i]) && parent_id.equals(st[i])) {
				String sql = " update zjgl_tree set tree_name='"
						+ tree_text
						+ "'  where  yw_guid=? and parent_id=? and tree_id=? and rq=?";
				update(sql, YW, new Object[] { yw_guid, parent_id, id,
						selet_year });
				String delet = "update XMZJGL_ZC set lb='" + tree_text
						+ "' where yw_guid=? and  zcstatus=? and rq=?";
				update(delet, YW,
						new Object[] { yw_guid, parent_id, selet_year });

			}
		}

	}

	public void getTree() {
		String yw_guid = request.getParameter("yw_guid");
		String rq = request.getParameter("rq");
		String tree = new TreeManager().getTree(yw_guid, rq);
		response(tree);
	}

	public String dellNull(String str) {
		if (str.equals("null")) {
			return "";
		} else {
			return str;
		}

	}
}
