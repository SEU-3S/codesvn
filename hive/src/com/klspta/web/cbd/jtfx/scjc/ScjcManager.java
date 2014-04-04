package com.klspta.web.cbd.jtfx.scjc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.model.CBDReport.CBDReportManager;
import com.klspta.model.CBDReport.tablestyle.ITableStyle;
import com.klspta.web.cbd.yzt.jc.report.TableStyleEditRow;

/**
 * <br>
 * Title:市场监测信息维护 <br>
 * Description:二手房基础信息维护，及月度价格维护 <br>
 * Author:邹勇 <br>
 * Date:2013-12-30
 */
public class ScjcManager extends AbstractBaseBean {
    /**
     * <br>
     * Description:获取每一列的信息 <br>
     * Author:邹勇 <br>
     * Date:2013-12-30
     * 
     * @return
     */
    private static ScjcManager scjcManager;

    public static ScjcManager getInstcne() {
        if (scjcManager == null) {
            scjcManager = new ScjcManager();
        }
        return scjcManager;
    }

    public void getReport() {
        String keyword = request.getParameter("keyword");
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        keyword = UtilFactory.getStrUtil().unescape(keyword);
		ITableStyle its = new TableStyleEditRow();
		response(String.valueOf(new CBDReportManager().getReport("ESFQK", new Object[]{year,month,"false",keyword},its,"1000px")));
//        if (keyword != null && !keyword.equals("")) {
//            keyword = UtilFactory.getStrUtil().unescape(keyword);
//        }
//        response(getList(keyword));
    }

    public String getList(String keyord) {
        String sql = "";
        List<Map<String, Object>> list = null;
        if (keyord == null) {
            sql = "select t.*  from esf_jbxx t where 1=1 order by t.dateflag desc";
            list = query(sql, YW);
        } else {
            sql = "select t.*  from esf_jbxx t where 1=1 and t.ssqy||t.xqmc||t.xqlb like ? order by t.dateflag desc ";
            list = query(sql, YW, new Object[] { "%" + keyord + "%" });
        }
        StringBuffer result = new StringBuffer(
                "<table id='ESFXX' width='800' border='1' cellpadding='1' cellspacing='0'><tr id='-1'"
                        + " class='title' onclick='showMap(this); return false;' ondblclick='editMap(this);"
                        + " return false;'><td width='50' hight='10'  class=''>序号</td><td width='100'"
                        + " hight='10' class=''>所属区域</td><td width='150' hight='10' class=''>小区名称</td>"
                        + "<td width='100' hight='10'  class=''>小区类别</td><td width=450 class=''>备注</td><td style='display:none;'>备注</td></tr>  ");
        for (int i = 0; i < list.size(); i++) {
            String rownum = i + 1 + "";
            String ssqy = (String) (list.get(i)).get("ssqy");
            String xqmc = (String) (list.get(i)).get("xqmc");
            String xqlb = (String) (list.get(i)).get("xqlb");
            String bz = (String) (list.get(i)).get("bz");
            String yw_guid = (String) (list.get(i)).get("yw_guid");
            if (xqlb.equals("老旧房")) {
                result.append("<tr tr class='trsingle' onclick='showMap(this); return false;' ondblclick='editMap(this); return false;' id=row"
                        + i
                        + "><td class='tr02'>"
                        + rownum
                        + "</td><td class='tr02'>"
                        + ssqy
                        + "</td><td class='tr02'>"
                        + xqmc
                        + "</td><td class='tr02'>"
                        + xqlb
                        + "</td><td class='tr02'>"
                        + bz
                        + "</td><td style='display:none;'>"
                        + yw_guid
                        + "</td></tr>");
            } else if (xqlb.equals("新居房")) {
                result.append("<tr  tr class='trsingle' onclick='showMap(this); return false;' ondblclick='editMap(this); return false;' id=row"
                        + i
                        + "><td class='tr03'>"
                        + rownum
                        + "</td><td class='tr03'>"
                        + ssqy
                        + "</td><td class='tr03'>"
                        + xqmc
                        + "</td><td class='tr03'>"
                        + xqlb
                        + "</td><td class='tr03'>"
                        + bz
                        + "</td><td style='display:none;'>"
                        + yw_guid
                        + "</td></tr>");
            }
        }

        result.append("</table>");
        return result.toString().replaceAll("null", "").replaceAll("\r\n", " ; ");
    }

    public String getList() {
        return getList(null);
    }

    public void delByYwGuid() {
        try {
            String yw_guid = request.getParameter("xqmc");
            String[] xqmcs = yw_guid.split(",");
            List<Map<String,Object>> list = null;
	            for(int i = 0; i < xqmcs.length;i++){
	            	String sql = "select yw_guid from esf_jbxx where xqmc = ?";
	            	list = query(sql, YW,new Object[]{xqmcs[i]});
		            sql = "delete from esf_jbxx t where yw_guid='" + list.get(0).get("yw_guid") + "'";
		            this.update(sql, YW);
		            sql = "delete from esf_zsxx t where yw_guid='" + list.get(0).get("yw_guid") + "'";
		            this.update(sql, YW);
            }
            response("{success:true}");
        } catch (Exception e) {
            response("{success:false}");
        }
    }

    public void save() {
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String esfzl = request.getParameter("esfzl");
        String esfjj = request.getParameter("esfjj");
        String czl = request.getParameter("czl");
        String czfjj = request.getParameter("czfjj");
        String ssqy = UtilFactory.getStrUtil().unescape(request.getParameter("ssqy"));
        String xqmc = UtilFactory.getStrUtil().unescape(request.getParameter("xqmc"));
        String xqlb = UtilFactory.getStrUtil().unescape(request.getParameter("xqlb"));
        String bz = UtilFactory.getStrUtil().unescape(request.getParameter("bz"));
        String sql = "select yw_guid from esf_jbxx where xqmc=?";
        List<Map<String,Object>> list = query(sql, YW,new Object[]{xqmc});
        sql = "select t.yw_guid from esf_zsxx t,esf_jbxx j where t.yw_guid = j.yw_guid and j.xqmc=? and t.year=? and t.month=?";
        List<Map<String ,Object>> list1 = query(sql, YW,new Object[]{xqmc,year,month});
        if (list.size()!=0) {
            String update = "update esf_jbxx set ssqy='" + ssqy + "',xqmc='" + xqmc + "',xqlb='" + xqlb
                    + "',bz='" + bz + "'where yw_guid=?";
            this.update(update, YW, new Object[] { list.get(0).get("yw_guid") });
            if(list1.size()!=0){
            	update = "update esf_zsxx set zl=?,esfjj=?,czl=?,czfjj=? where yw_guid=? and year=? and month=?";
            	update(update, YW,new Object[]{esfzl,esfjj,czl,czfjj,list.get(0).get("yw_guid"),year,month});
            }else {
            	update = "insert into esf_zsxx(yw_guid,year,month,zl,esfjj,czl,czfjj)values(?,?,?,?,?,?,?)";
                this.update(update, YW, new Object[] { list.get(0).get("yw_guid"), year, month ,esfzl,esfjj,czl,czfjj});
            }
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss");
            String format = dateFormat.format(new Date());
            String insertSql = "insert into esf_jbxx(ssqy,xqmc,xqlb,bz,yw_guid) values(?,?,?,?,?)";
            this.update(insertSql, YW, new Object[] { ssqy, xqmc, xqlb, bz, format });
            String intserSql2 = "insert into esf_zsxx(yw_guid,year,month,zl,esfjj,czl,czfjj)values(?,?,?,?,?,?,?)";
            this.update(intserSql2, YW, new Object[] { format, year, month ,esfzl,esfjj,czl,czfjj});
        }
        
        
        
        response("{success:true}");

    }

    /**
     * <br>
     * Description:生成录入租售情况表 <br>
     * Author:邹勇 <br>
     * Date:2013-12-30
     * 
     * @return
     */
    public String getList2() {
    	String year = Calendar.getInstance().get(Calendar.YEAR)+"";		
    	String month = Calendar.getInstance().get(Calendar.MONTH)+1+"";
        String sql = "select t.xqmc,t2.zl,t2.esfjj,t2.czl,t2.czfjj,t.yw_guid  from esf_jbxx t ,esf_zsxx t2 where t.yw_guid=t2.yw_guid and month='"+month+"' and year='"+year+"' order by t.dateflag desc";
        List<Map<String, Object>> list = query(sql, YW);
        String newTable = "";
        if (list.size() > 0) {
            newTable = getNewTable(list);
        } else {
            String selSql = "select * from  esf_jbxx";
            List<Map<String, Object>> query = query(selSql, YW);
            for (int j = 0; j < query.size(); j++) {
                String insert = "insert into esf_zsxx (yw_guid,month,year) values(?,?,?) ";
                update(insert, YW, new Object[] { query.get(j).get("yw_guid"), month,year });
            }

            // 插入完成之后，
            String sql2 = "select t.xqmc,t2.zl,t2.esfjj,t2.czl,t2.czfjj,t.yw_guid  from esf_jbxx t ,esf_zsxx t2 where t.yw_guid=t2.yw_guid  and t2.year=? and t2.month=? order by t.dateflag desc";
            List<Map<String, Object>> list2 = query(sql2, YW);
            newTable = getNewTable(list2);
        }

        return newTable;
    }

    public void insert() {
        String sj = request.getParameter("sj");
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        if (sj != null && !sj.equals("")) {
            String[] date = sj.split("@");
            for (int i = 0; i < date.length; i++) {
                String[] split = date[i].split("_");
                String yw_guid = split[0] + "_" + split[1];
                String xh = split[2];
                String value = split[3];
                String type = "";
                String esfjjzf = "";
                String czfjjzf = "";
                if (xh.equals("1")) {
                    type = "zl";
                    String upda = "update esf_zsxx set " + type + "='" + value
                            + "' where yw_guid=? and month=?  and year=?";
                    this.update(upda, YW, new Object[] { yw_guid, month, year });
                }
                if (xh.equals("2")) {
                    type = "esfjj";
                    if (month.equals("1")) {
                        String sql = "select esfjj from esf_zsxx where yw_guid=? and month='12' and year=?";
                        List<Map<String, Object>> list = this.query(sql, YW,
                                new Object[] { yw_guid, (Integer.parseInt(year) - 1) });
                        if(list.isEmpty()){
                            esfjjzf="0";
                        }else{
                        String old = (String) (list.get(0)).get("esfjj");
                        int old2 = Integer.parseInt(old);
                        int now = Integer.parseInt(value);
                        String result = String.valueOf((now - old2) / old2);
                        esfjjzf = result;}
                    } else {
                        String sql2 = "select esfjj from esf_zsxx where yw_guid=? and month=? and year=?";
                        List<Map<String, Object>> list2 = this.query(sql2, YW, new Object[] { yw_guid,
                                (Integer.parseInt(month) - 1), year });
                        if(list2.isEmpty()){
                            esfjjzf="0";
                        }else{
                        String old = (String) (list2.get(0)).get("esfjj");
                        int old2 = Integer.parseInt(old);
                        int now = Integer.parseInt(value);
                        String result = String.valueOf((now - old2) / old2);
                        esfjjzf = result;}

                    }
                    String upda = "update esf_zsxx set " + type + "='" + value +"',esfjjzf='"+esfjjzf+"'" 
                            + " where yw_guid=? and month=?  and year=?";
                    this.update(upda, YW, new Object[] { yw_guid, month, year });
                }
                if (xh.equals("3")) {
                    type = "czl";
                    String upda = "update esf_zsxx set " + type + "='" + value
                            + "' where yw_guid=? and month=?  and year=?";
                    this.update(upda, YW, new Object[] { yw_guid, month, year });
                }
                if (xh.equals("4")) {
                    type = "czfjj";
                    if (month.equals("1")) {
                        String sql = "select czfjj from esf_zsxx where yw_guid=? and month='12' and year=?";
                        List<Map<String, Object>> list = this.query(sql, YW,
                                new Object[] { yw_guid, (Integer.parseInt(year) - 1) });
                        if(list.isEmpty()){
                            czfjjzf = "0";
                        }else{
                        String old = (String) (list.get(0)).get("czfjj");
                        int old2 = Integer.parseInt(old);
                        int now = Integer.parseInt(value);
                        String result = String.valueOf((now - old2) / old2);
                        czfjjzf = result;}
                    }
                     else {
                        String sql2 = "select czfjj from esf_zsxx where yw_guid=? and month=? and year=?";
                        List<Map<String, Object>> list2 = this.query(sql2, YW, new Object[] { yw_guid,
                                (Integer.parseInt(month) - 1), year });
                        if(list2.isEmpty()){
                            czfjjzf = "0";
                        }else{
                        String old = (String) (list2.get(0)).get("czfjj");
                        int old2 = Integer.parseInt(old);
                        int now = Integer.parseInt(value);
                        String result = String.valueOf((now - old2) / old2);
                        czfjjzf = result;}

                    }
                    String upda = "update esf_zsxx set " + type + "='" + value + "',czfjjzf ='"+czfjjzf+"'"
                            + " where yw_guid=? and month=?  and year=?";
                    this.update(upda, YW, new Object[] { yw_guid, month, year });
                    
                }

            }
        }
        response("success");
    }

    public void months_mm() {
        String month = request.getParameter("month");
        String year = request.getParameter("year");
        String sql = "select t.xqmc,t2.zl,t2.esfjj,t2.czl,t2.czfjj,t.yw_guid  from esf_jbxx t ,esf_zsxx t2 where t.yw_guid=t2.yw_guid  and t2.year=? and t2.month=? order by t.dateflag desc";
        List<Map<String, Object>> list = query(sql, YW, new Object[] { year, month });
        String newTable = "";
        if (list.size() > 0) {
            newTable = getNewTable(list);
        } else {
            String selSql = "select * from  esf_jbxx";
            List<Map<String, Object>> query = query(selSql, YW);
            for (int j = 0; j < query.size(); j++) {
                String insert = "insert into esf_zsxx (yw_guid,month,year) values(?,?,?) ";
                update(insert, YW, new Object[] { query.get(j).get("yw_guid"), month, year });
            }

            // 插入完成之后，
            String sql2 = "select t.xqmc,t2.zl,t2.esfjj,t2.czl,t2.czfjj,t.yw_guid  from esf_jbxx t ,esf_zsxx t2 where t.yw_guid=t2.yw_guid  and t2.year=? and t2.month=? order by t.dateflag desc";
            List<Map<String, Object>> list2 = query(sql2, YW, new Object[] { year, month });
            newTable = getNewTable(list2);

        }

        response(newTable);
    }

    public String replace(String str) {
        if (str.equals("null")) {
            return "";
        } else {
            return str;
        }

    }

    public String getNewTable(List<Map<String, Object>> list) {
        StringBuffer result = new StringBuffer(
                "<table id='zsqktable' name='zsqktable'><tr id='-1'><td class='tr01'>序号</td><td class='tr01'>小区名称</td><td class='tr01'>二手房总量</td><td class='tr01'>二手房均价</td><td class='tr01'>出租量</td><td class='tr01'>出租房均价</td><td style='display:none;'>主键(hiden)</td></tr>");
        for (int i = 0; i < list.size(); i++) {
            String rownum = i + 1 + "";
            String xqmc = (String) (list.get(i)).get("xqmc");
            String yw_guid = (String) (list.get(i)).get("yw_guid");
            result.append("<tr id=row"
                    + i
                    + "><td>"
                    + rownum
                    + "</td><td>"
                    + xqmc
                    + "</td><td><input id='"
                    + (String) (list.get(i)).get("yw_guid")
                    + "_1'value='"
                    + replace(String.valueOf(list.get(i).get("zl")))
                    + "' onchange='chang(this)'  style='border-style:none'/></td><td><input id='"
                    + (String) (list.get(i)).get("yw_guid")
                    + "_2' value='"
                    + replace(String.valueOf(list.get(i).get("esfjj")))
                    + "'  onchange='chang(this)' style='border-style:none'/></td><td><input id='"
                    + (String) (list.get(i)).get("yw_guid")
                    + "_3' value='"
                    + replace(String.valueOf(list.get(i).get("czl")))
                    + "'  onchange='chang(this)' style='border-style:none'/></td><td><input id='"
                    + (String) (list.get(i)).get("yw_guid")
                    + "_4' value='"
                    + replace(String.valueOf(list.get(i).get("czfjj")))
                    + "'  onchange='chang(this)' style='border-style:none'/></td><td id='yw_guid' style='display:none;'>"
                    + yw_guid + "</td></tr>");
        }
        result.append("</table>");
        return result.toString();

    }

    public void query_year_month() {
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        ITableStyle its = new TableStyleEditRow();
        StringBuffer buffer = new CBDReportManager().getReport("ESFQK", new Object[] { year, month, "false" },its,
                "1000px");
        response(buffer.toString());
    }
//
    
    
}
