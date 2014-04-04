package com.klspta.web.cbd.swkgl;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

public class Fyzcmanager extends AbstractBaseBean {
    private static Fyzcmanager Fyzcmanager;

    public static Fyzcmanager getInstcne() {
        if (Fyzcmanager == null) {
            Fyzcmanager = new Fyzcmanager();
        }
        return Fyzcmanager;
    }

    public void saveFyzc() {
        String sj = request.getParameter("sj");
        String jzrq = request.getParameter("jzrq");
        jzrq = UtilFactory.getStrUtil().unescape(jzrq);
        sj = UtilFactory.getStrUtil().unescape(sj);
        if (sj != null && !sj.equals("")) {
            String[] date = sj.split("@");
            for (int i = 0; i < date.length; i++) {
                String[] split = date[i].split("_");
                String yw_guid = split[0];
                String xh = split[1];
                String value = split[2];
                String update = "update fyzc set " + xh + "='" + value + "' where yw_guid=?";
                this.update(update, YW, new Object[] { yw_guid });
            }
        }
        String sql = "update fyzc set jzrq='" + jzrq + "'";
        this.update(sql, YW);
        response("success");
    }

    public void addFyzc() {
        String mc = request.getParameter("mc");
        String gzfyts = request.getParameter("gzfyts");
        String gzjzgm = request.getParameter("gzjzgm");
        String dycbzj = request.getParameter("dycbzj");
        String gzdj = request.getParameter("gzdj");
        String lyfyts = request.getParameter("lyfyts");
        String lyjzgm = request.getParameter("lyjzgm");
        String fyclts = request.getParameter("fyclts");
        String jzmjcl = request.getParameter("jzmjcl");
        String zyzjcl = request.getParameter("zyzjcl");
        String ftlx = request.getParameter("ftlx");
        String fymc = request.getParameter("fymc");
        String zje = request.getParameter("zje");
        String mpfmft = request.getParameter("mpfmft");
        String ylyfyft = request.getParameter("ylyfyft");
        String zjfyft = request.getParameter("zjfyft");
        String fwjkzj = request.getParameter("fwjkzj");
        String dqdj = request.getParameter("dqdj");
        String jzrq = request.getParameter("jzrq");
        String bz = request.getParameter("bz");

        mc = UtilFactory.getStrUtil().unescape(mc);
        jzrq = UtilFactory.getStrUtil().unescape(jzrq);
        bz = UtilFactory.getStrUtil().unescape(bz);
        int i = 0;
        String sql = "select mc from fyzc where mc=?";
        List<Map<String,Object>> list = query(sql, YW,new Object[]{mc});
        if(list.size()>0){
        	String updateString = "update fyzc set gzfy=?, gzgm=?, cbzj=?, gzdj=?, lyfy=?, lygm=?, qmfy=?" +
        			", jzmj=?,zyzj=?,ftlx=?,fymc=?,ze=?,pmft=?, lyft=?, jzft=?, jkzj=?, dj=?, bz=?,jzrq=? "+
        			"where mc=?" ;
           i = update(updateString, YW, new Object[] {  gzfyts, gzjzgm, dycbzj, gzdj, lyfyts, lyjzgm, fyclts, jzmjcl,
	        		zyzjcl, ftlx, fymc, zje, mpfmft, ylyfyft, zjfyft, fwjkzj, dqdj, bz, jzrq ,mc});
        }else{
	        String insertString = "insert into fyzc  (mc, gzfy, gzgm, cbzj, gzdj, lyfy, lygm, qmfy, jzmj,zyzj,ftlx,fymc,ze,pmft, lyft, jzft, jkzj, dj, bz,jzrq)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	        i = update(insertString, YW, new Object[] { mc, gzfyts, gzjzgm, dycbzj, gzdj, lyfyts, lyjzgm, fyclts, jzmjcl,
	        		zyzjcl, ftlx, fymc, zje, mpfmft, ylyfyft, zjfyft, fwjkzj, dqdj, bz, jzrq });
        }
        if (i > 0) {
            response("{success:true}");
        } else {
            response("{success:false}");
        }
    }

    public String getList() {
        String sql = "select distinct jzrq from fyzc";
        List<Map<String, Object>> list2 = query(sql, YW);
        String jzrq = (String) (list2.get(0)).get("jzrq");
        StringBuffer result = new StringBuffer(
                "<table id='FYZC' width='2000' border='1' cellpadding='1' cellspacing='0'>" +
                "<tr class='title' onclick='showMap(this); return false;' ondblclick='editMap(this); return false;'><td rowspan='2' colspan='1' class='tr01'>名称</td><td colspan='4' rowspan='1' class='tr01'>购置情况</td><td colspan='2' rowspan='1' class='tr01'>利用情况</td><td colspan='3' rowspan='1' class='tr01'>期末存量情况</td><td  class='tr01'><input id='jzrq' style='border:0;background:transparent;' value="
                        + jzrq
                        + "></td><td rowspan='1' colspan='5' class='tr01'>其他费用</td><td rowspan='2' colspan='1' class='tr01'>房屋价款总计</td><td rowspan='2' colspan='1' class='tr01'>当前单价</td><td rowspan='2' colspan='1' class='tr01'>备注</td></tr><tr><td colspan='1' rowspan='1' class='tr01'>房源套数</td><td colspan='1' rowspan='1' class='tr01'>建筑规模</td><td colspan='1' rowspan='1' class='tr01'>动用储备资金</td><td colspan='1' rowspan='1' class='tr01'>购置单价</td><td colspan='1' rowspan='1' class='tr01'>房源套数</td><td colspan='1' rowspan='1' class='tr01'>建筑规模</td><td colspan='1' rowspan='1' class='tr01'>房源套数</td><td colspan='1' rowspan='1' class='tr01'>建筑面积</td><td colspan='1' rowspan='1' class='tr01'>占压资金</td><td colspan='1' rowspan='1' class='tr01'>分摊利息</td><td colspan='1' rowspan='1' class='tr01'>费用名称</td><td colspan='1' rowspan='1' class='tr01'>总金额</td><td colspan='1' rowspan='1' class='tr01'>每平米分摊</td><td colspan='1' rowspan='1' class='tr01'>已利用房源分摊</td><td colspan='1' rowspan='1' class='tr01'>结转房源分摊</td></tr>");
        String allsql = "select t.mc,t.gzfy,t.gzgm,t.cbzj,t.gzdj,t.jzmj,t.zyzj,t.pmft,t.lyft,t.ze,t.lyfy,t.lygm,t.dj,t.qmfy,t.ftlx,t.fymc,t.jzft,t.jkzj,t.bz,t.yw_guid from fyzc t";
        List<Map<String, Object>> alllist = query(allsql, YW);
        for (int i = 0; i < alllist.size(); i++) {
            String mc = (String) (alllist.get(i)).get("mc");
            String gzfy = (String) (alllist.get(i)).get("gzfy");
            String gzgm = (String) (alllist.get(i)).get("gzgm");
            String cbzj = (String) (alllist.get(i)).get("cbzj");
            String gzdj = (String) (alllist.get(i)).get("gzdj");
            String jzmj = (String) (alllist.get(i)).get("jzmj");
            String zyzj = (String) (alllist.get(i)).get("zyzj");
            String pmft = (String) (alllist.get(i)).get("pmft");
            String lyft = (String) (alllist.get(i)).get("lyft");
            String ze = (String) (alllist.get(i)).get("ze");
            String lyfy = (String) (alllist.get(i)).get("lyfy");
            String lygm = (String) (alllist.get(i)).get("lygm");
            String dj = (String) (alllist.get(i)).get("dj");
            String qmfy = (String) (alllist.get(i)).get("qmfy");
            String ftlx = (String) (alllist.get(i)).get("ftlx");
            String fymc = (String) (alllist.get(i)).get("fymc");
            String jzft = (String) (alllist.get(i)).get("jzft");
            String jkzj = (String) (alllist.get(i)).get("jkzj");
            String bz = (String) (alllist.get(i)).get("bz");
            String yw_guid = (String) (alllist.get(i)).get("yw_guid");
            result.append("<tr class='trsingle' onclick='showMap(this); return false;' ondblclick='editMap(this); return false;'><td>" + mc
                    + "</td><td>" + gzfy
                    + "</td><td>" + gzgm
                    + "</td><td>" + cbzj
                    + "</td><td>" + gzdj
                    + "</td><td>" + lyfy
                    + "</td><td>" + lygm
                    + "</td><td>" + qmfy
                    + "</td><td>"+ jzmj
                    + "</td><td>" + zyzj
                    + "</td><td>" + ftlx
                    + "</td><td>" + fymc
                    + "</td><td>" + ze
                    + "</td><td>" + pmft
                    + "</td><td>" + lyft
                    + "</td><td>" + jzft
                    + "</td><td>" + jkzj 
                    + "</td><td>" + dj
                    + "</td><td>" + bz
                    + "</td></tr>");
        }
        String sumsql = "select sum(gzfy)as gzfy,sum(gzgm)as gzgm,sum(cbzj)as cbzj,sum(gzdj)as gzdj,sum(lyfy)as lyfy,sum(lygm)as lygm,sum(qmfy)as qmfy,sum(jzmj)as jzmj,sum(zyzj)as zyzj,sum(ftlx)as ftlx,sum(fymc)as fymc,sum(ze)as ze,sum(pmft)as pmft,sum(lyft)as lyft,sum(jzft)as jzft,sum(jkzj)as jkzj,sum(dj)as dj from fyzc t";
        List<Map<String, Object>> sumlist = query(sumsql, YW);
        for (int i = 0; i < sumlist.size(); i++) {
            String sumgzfy = (String) (sumlist.get(i)).get("gzfy")==null?"":(sumlist.get(i)).get("gzfy").toString();
            String sumgzgm = (String) (sumlist.get(i)).get("gzgm")==null?"":(sumlist.get(i)).get("gzgm").toString();
            String sumcbzj = (String) (sumlist.get(i)).get("cbzj")==null?"":(sumlist.get(i)).get("cbzj").toString();
            String sumgzdj = (String) (sumlist.get(i)).get("gzdj")==null?"":(sumlist.get(i)).get("gzdj").toString();
            String sumlyfy = (String) (sumlist.get(i)).get("lyfy")==null?"":(sumlist.get(i)).get("lyfy").toString();
            String sumlygm = (String) (sumlist.get(i)).get("lygm")==null?"":(sumlist.get(i)).get("lygm").toString();
            String sumqmfy = (String) (sumlist.get(i)).get("qmfy")==null?"":(sumlist.get(i)).get("qmfy").toString();
            String sumjzmj = (String) (sumlist.get(i)).get("jzmj")==null?"":(sumlist.get(i)).get("jzmj").toString();
            String sumzyzj = (String) (sumlist.get(i)).get("zyzj")==null?"":(sumlist.get(i)).get("zyzj").toString();
            String sumftlx = (String) (sumlist.get(i)).get("ftlx")==null?"":(sumlist.get(i)).get("ftlx").toString();
            String sumfymc = (String) (sumlist.get(i)).get("fymc")==null?"":(sumlist.get(i)).get("fymc").toString();
            String sumze = (String) (sumlist.get(i)).get("ze")==null?"":(sumlist.get(i)).get("ze").toString();
            String sumpmft = (String) (sumlist.get(i)).get("pmft")==null?"":(sumlist.get(i)).get("pmft").toString();
            String sumlyft = (String) (sumlist.get(i)).get("lyft")==null?"":(sumlist.get(i)).get("lyft").toString();
            String sumjzft = (String) (sumlist.get(i)).get("jzft")==null?"":(sumlist.get(i)).get("jzft").toString();
            String jkzj = (String) (sumlist.get(i)).get("jkzj")==null?"":(sumlist.get(i)).get("jkzj").toString();
            String sumdj = (String) (sumlist.get(i)).get("dj")==null?"":(sumlist.get(i)).get("dj").toString();
            result.append("<tr><td class='tr01'>总计</td><td class='tr01'>" + sumgzfy
                    + "</td><td class='tr01'>" + sumgzgm + "</td><td class='tr01'>" + sumcbzj
                    + "</td><td class='tr01'>" + sumgzdj + "</td><td class='tr01'>" + sumlyfy
                    + "</td><td class='tr01'>" + sumlygm + "</td><td class='tr01'>" + sumqmfy
                    + "</td><td class='tr01'>" + sumjzmj + "</td><td class='tr01'>" + sumzyzj
                    + "</td><td class='tr01'>" + sumftlx + "</td><td class='tr01'>" + sumfymc
                    + "</td><td class='tr01'>" + sumze + "</td><td class='tr01'>" + sumpmft
                    + "</td><td class='tr01'>" + sumlyft + "</td><td class='tr01'>" + sumjzft
                    + "</td><td class='tr01'>" + jkzj + "</td><td class='tr01'>" + sumdj + "</td><td class='tr01'></td><td class='tr01'></td></tr>");
        }
        result.append("</table>");
        return result.toString().replaceAll("null", "");
    }

    public void delByYwGuid() {
        String mc = request.getParameter("mc");
        mc = UtilFactory.getStrUtil().unescape(mc);
        String[] mcs = mc.substring(0, mc.length()-1).split(",");
        for(int i=0;i<mcs.length;i++){
        	String sql = "delete from fyzc t where t.mc='" + mcs[i] + "'";
        	this.update(sql, YW);
        }
        response("success");
    }
    
    
    public void quryKeyWord(){
        String keyword = request.getParameter("keyword");
        keyword = UtilFactory.getStrUtil().unescape(keyword);
        String sql = "select distinct jzrq from fyzc";
        List<Map<String, Object>> list2 = query(sql, YW);
        String jzrq = (String) (list2.get(0)).get("jzrq");
        StringBuffer result = new StringBuffer(
                "<table id='FYZC' width='2000' border='1' cellpadding='1' cellspacing='0'>" +
                "<tr class='title' onclick='showMap(this); return false;' ondblclick='editMap(this); return false;'><td rowspan='2' colspan='1' class='tr01'>名称</td><td colspan='4' rowspan='1' class='tr01'>购置情况</td><td colspan='2' rowspan='1' class='tr01'>利用情况</td><td colspan='3' rowspan='1' class='tr01'>期末存量情况</td><td  class='tr01'><input id='jzrq' style='border:0;background:transparent;' value="
                        + jzrq
                        + "></td><td rowspan='1' colspan='5' class='tr01'>其他费用</td><td rowspan='2' colspan='1' class='tr01'>房屋价款总计</td><td rowspan='2' colspan='1' class='tr01'>当前单价</td><td rowspan='2' colspan='1' class='tr01'>备注</td></tr><tr><td colspan='1' rowspan='1' class='tr01'>房源套数</td><td colspan='1' rowspan='1' class='tr01'>建筑规模</td><td colspan='1' rowspan='1' class='tr01'>动用储备资金</td><td colspan='1' rowspan='1' class='tr01'>购置单价</td><td colspan='1' rowspan='1' class='tr01'>房源套数</td><td colspan='1' rowspan='1' class='tr01'>建筑规模</td><td colspan='1' rowspan='1' class='tr01'>房源套数</td><td colspan='1' rowspan='1' class='tr01'>建筑面积</td><td colspan='1' rowspan='1' class='tr01'>占压资金</td><td colspan='1' rowspan='1' class='tr01'>分摊利息</td><td colspan='1' rowspan='1' class='tr01'>费用名称</td><td colspan='1' rowspan='1' class='tr01'>总金额</td><td colspan='1' rowspan='1' class='tr01'>每平米分摊</td><td colspan='1' rowspan='1' class='tr01'>已利用房源分摊</td><td colspan='1' rowspan='1' class='tr01'>结转房源分摊</td></tr>");
        String qurysql = "select t.mc,t.gzfy,t.gzgm,t.cbzj,t.gzdj,t.jzmj,t.zyzj,t.pmft,t.lyft,t.ze,t.lyfy,t.lygm,t.dj,t.qmfy,t.ftlx,t.fymc,t.jzft,t.jkzj,t.bz,t.yw_guid from fyzc t where t.mc like '%"+keyword+"%'";
        List<Map<String, Object>> qurylist = query(qurysql, YW);
        for (int i = 0; i < qurylist.size(); i++) {
            String mc = (String) (qurylist.get(i)).get("mc");
            String gzfy = (String) (qurylist.get(i)).get("gzfy");
            String gzgm = (String) (qurylist.get(i)).get("gzgm");
            String cbzj = (String) (qurylist.get(i)).get("cbzj");
            String gzdj = (String) (qurylist.get(i)).get("gzdj");
            String jzmj = (String) (qurylist.get(i)).get("jzmj");
            String zyzj = (String) (qurylist.get(i)).get("zyzj");
            String pmft = (String) (qurylist.get(i)).get("pmft");
            String lyft = (String) (qurylist.get(i)).get("lyft");
            String ze = (String) (qurylist.get(i)).get("ze");
            String lyfy = (String) (qurylist.get(i)).get("lyfy");
            String lygm = (String) (qurylist.get(i)).get("lygm");
            String dj = (String) (qurylist.get(i)).get("dj");
            String qmfy = (String) (qurylist.get(i)).get("qmfy");
            String ftlx = (String) (qurylist.get(i)).get("ftlx");
            String fymc = (String) (qurylist.get(i)).get("fymc");
            String jzft = (String) (qurylist.get(i)).get("jzft");
            String jkzj = (String) (qurylist.get(i)).get("jkzj");
            String bz = (String) (qurylist.get(i)).get("bz");
            result.append("<tr class='trsingle' onclick='showMap(this); return false;' ondblclick='editMap(this); return false;'><td>" + mc
                    + "</td><td>" + gzfy
                    + "</td><td>" + gzgm
                    + "</td><td>" + cbzj
                    + "</td><td>" + gzdj
                    + "</td><td>" + lyfy
                    + "</td><td>" + lygm
                    + "</td><td>" + qmfy
                    + "</td><td>"+ jzmj
                    + "</td><td>" + zyzj
                    + "</td><td>" + ftlx
                    + "</td><td>" + fymc
                    + "</td><td>" + ze
                    + "</td><td>" + pmft
                    + "</td><td>" + lyft
                    + "</td><td>" + jzft
                    + "</td><td>" + jkzj 
                    + "</td><td>" + dj
                    + "</td><td>" + bz
                    + "</td></tr>");
        }
        result.append("<tr><td class='tr01'>总计</td><td class='tr01'></td><td class='tr01'></td><td class='tr01'></td><td class='tr01'></td><td class='tr01'></td><td class='tr01'></td><td class='tr01'></td><td class='tr01'></td><td class='tr01'></td><td class='tr01'></td><td class='tr01'></td><td class='tr01'></td><td class='tr01'></td><td class='tr01'></td><td class='tr01'></td><td class='tr01'></td><td class='tr01'></td><td class='tr01'></td><td class='tr01'></td></tr></table>");
        response(result.toString().replaceAll("null", ""));
    }

}
