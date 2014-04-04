package com.klspta.web.cbd.dtjc.tjbb;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.web.cbd.dtjc.trzqk.TrzqkThread;

/**
 * 
 * <br>Title:开发体量
 * <br>Description:
 * <br>Author:陈强峰
 * <br>Date:2013-10-10
 */
@Component
public class Kftl extends AbstractBaseBean {
    DecimalFormat df = new DecimalFormat("#.00");

    /**
     * 评估土地价值
     */
    private double pgtdjz = 2;

    /**
     * 抵押率
     */
    private double dyl = 0.2;

    /**
     * 融资损失
     */
    private double rzss = 0;

    /**
     * 
     * <br>Description:新增开发体量
     * <br>Author:陈强峰
     * <br>Date:2013-10-10
     */
    public void add() {
        String xmmc = request.getParameter("xmmc");
        String year = request.getParameter("nd");
        String hs = request.getParameter("hs")==""?"0":request.getParameter("hs");
        String dl = request.getParameter("dl")==""?"0":request.getParameter("dl");
        String gm = request.getParameter("gm")==""?"0":request.getParameter("gm");
        String tz = request.getParameter("tz")==""?"0":request.getParameter("tz");
        String z = request.getParameter("z")==""?"0":request.getParameter("z");
        String q = request.getParameter("q")==""?"0":request.getParameter("q");
        String hsz = request.getParameter("hsbl")==""?"0":request.getParameter("hsbl");
        String dlz = request.getParameter("dlbl")==""?"0":request.getParameter("dlbl");
        String gmz = request.getParameter("gmbl")==""?"0":request.getParameter("gmbl");
        String tzz = request.getParameter("tzbl")==""?"0":request.getParameter("tzbl");
        String zhuz = request.getParameter("zbl")==""?"0":request.getParameter("zbl");
        String qiz = request.getParameter("qbl")==""?"0":request.getParameter("qbl");
        String lm = request.getParameter("lm")==""?"0":request.getParameter("lm");
        String cj = request.getParameter("cj")==""?"0":request.getParameter("cj");
        String season = request.getParameter("jd");
        String xmbh = getXmbh(xmmc);
        String sql = "insert into hx_kftl(xmmc,nd,jd,hs,dl,gm,tz,zhu,qi,lm,cj,hsz,dlz,gmz,tzz,zhuz,qiz,xmguid) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int flag = update(sql, YW, new Object[] { xmmc, year, season, hs, dl, gm, tz, z, q, lm, cj, hsz, dlz,
                gmz, tzz, zhuz, qiz, xmbh });
        if (flag == 1) {
            insertToSx(year, season, xmbh);
            updateTj(year, season);
        }
        if (flag == 0) {
            response("{success:false}");
        } else {
            response("{success:true}");
        }
    }

    /**
     * 
     * <br>Description:更新开发体量
     * <br>Author:陈强峰
     * <br>Date:2013-10-11
     */
    public void update() {
        String xmmc = request.getParameter("xmmc");
        String year = request.getParameter("nd");
        System.out.println(request.getParameter("hs"));
        String hs = request.getParameter("hs")==""?"0":request.getParameter("hs");
        String dl = request.getParameter("dl")==""?"0":request.getParameter("dl");
        String gm = request.getParameter("gm")==""?"0":request.getParameter("gm");
        String tz = request.getParameter("tz")==""?"0":request.getParameter("tz");
        String z = request.getParameter("z")==""?"0":request.getParameter("z");
        String q = request.getParameter("q")==""?"0":request.getParameter("q");
        String hsz = request.getParameter("hsbl")==""?"0":request.getParameter("hsbl");
        String dlz = request.getParameter("dlbl")==""?"0":request.getParameter("dlbl");
        String gmz = request.getParameter("gmbl")==""?"0":request.getParameter("gmbl");
        String tzz = request.getParameter("tzbl")==""?"0":request.getParameter("tzbl");
        String zhuz = request.getParameter("zbl")==""?"0":request.getParameter("zbl");
        String qiz = request.getParameter("qbl")==""?"0":request.getParameter("qbl");
        String lm = request.getParameter("lm")==""?"0":request.getParameter("lm");
        String cj = request.getParameter("cj")==""?"0":request.getParameter("cj");
        String season = request.getParameter("jd");
        String xmbh = getXmbh(xmmc);
        String sql = "update hx_kftl set hs=?,dl=?,gm=?,tz=?,zhu=?,qi=?,hsz=?,dlz=?,gmz=?,tzz=?,zhuz=?,qiz=?,lm=?,cj=?  where nd=? and jd=? and xmguid=?";
        int flag = update(sql, YW, new Object[] { hs, dl, gm, tz, z, q, hsz, dlz, gmz, tzz, zhuz, qiz, lm,
                cj, year, season, xmbh });
        if (flag == 0) {
            response("{success:false}");
        } else {
            updateTj(year, season);
            response("{success:true}");
        }
    }

    /**
     * 
     * <br>Description:删除开发体量
     * <br>Author:陈强峰
     * <br>Date:2013-10-11
     */
    public void delete() {
        String xmmc = UtilFactory.getStrUtil().unescape(request.getParameter("xmmc"));
        String year = request.getParameter("nd");
        String season = request.getParameter("jd");
        String xmbh = getXmbh(xmmc);
        String sql = "delete from hx_kftl where nd=? and jd=? and xmguid=?";
        int flag = update(sql, YW, new Object[] { year, season, xmbh });
        if (flag == 0) {
            response("{success:false}");
        } else {
            removeFromSx(year, season, xmbh);
            updateTj(year, season);
            response("{success:true}");
        }
        response("{success:false}");
    }

    /**
     * 
     * <br>Description:开发体量查询
     * <br>Author:陈强峰
     * <br>Date:2013-10-11
     */
    public void query() {
        String xmmc = UtilFactory.getStrUtil().unescape(request.getParameter("xmmc"));
        String nd = request.getParameter("nd");
        String jd = request.getParameter("jd");
        String sql = "select dl,hs,gm,tz,zhu as z,qi as q,lm,cj,rownum-1 as mod,rownum-1 as del,yw_guid  as kfbh,hsz,dlz,gmz,tzz,zhuz,qiz,yf from hx_kftl where xmmc=? and nd=? and jd=?";
        List<Map<String, Object>> list = query(sql, YW, new Object[] { xmmc, nd, jd });
        response(list);
    }

    /**
     * 
     * <br>Description:将项目开发体量关联时序
     * <br>Author:陈强峰
     * <br>Date:2013-10-14
     * @param nd
     * @param jd
     * @param xmbh
     */
    private void insertToSx(String nd, String jd, String xmbh) {
        String sql = "select kftl from hx_sx where nd=? and jd=?";
        List<Map<String, Object>> list = query(sql, YW, new Object[] { nd, jd });
        if (list.size() > 0) {
            Object obj = list.get(0).get("kftl");
            String kftls = obj == null ? "" : obj.toString();
            if (kftls.indexOf(xmbh) < 0) {
                StringBuffer sb = new StringBuffer(kftls);
                sql = "update hx_sx set kftl =? where nd=? and jd=?";
                if (sb.length() > 0) {
                    sb.append(",").append(xmbh);
                } else {
                    sb.append(xmbh);
                }
                update(sql, YW, new Object[] { sb.toString(), nd, jd });
            }
        } else {
            sql = "insert into hx_sx(nd,jd,kftl) values(?,?,?)";
            update(sql, YW, new Object[] { nd, jd, xmbh });
        }
    }

    /**
     * 
     * <br>Description:将项目开发体量从时序移除
     * <br>Author:陈强峰
     * <br>Date:2013-10-14
     * @param nd
     * @param jd
     * @param xmbh
     */
    private void removeFromSx(String nd, String jd, String xmbh) {
        String sql = "select kftl from hx_sx where nd=? and jd=?";
        List<Map<String, Object>> listKftl = query(sql, YW, new Object[] { nd, jd });
        if (listKftl.size() > 0) {
            Object obj = listKftl.get(0).get("kftl");
            String kftls = obj == null ? "" : obj.toString();
            if (kftls.indexOf(xmbh) >= 0) {
                kftls = kftls.replace(xmbh + ",", "").replace(xmbh, "");
                sql = "update hx_sx set kftl =? where nd=? and jd=?";
                update(sql, YW, new Object[] { kftls, nd, jd });
            }
        }
    }

    /**
     * 
     * <br>Description:判断在同一季度项目开发体量的条数是否唯一
     * <br>Author:陈强峰
     * <br>Date:2013-10-14
     * @param nd
     * @param jd
     * @param xmbh
     * @return
     */
    private boolean judgeCount(String nd, String jd, String xmbh) {
        String sql = "select yw_guid from hx_kftl where nd=? and jd=? and xmguid=?";
        List<Map<String, Object>> listCount = query(sql, YW, new Object[] { nd, jd, xmbh });
        if (listCount.size() == 1) {
            return true;
        }
        return false;
    }

    /**
     * 
     * <br>Description:根据年度季度更新相应的开发体量统计
     * <br>Author:陈强峰
     * <br>Date:2013-10-16
     * @param nd
     * @param jd
     */
    public void updateTj(String nd, String jd) {
        String sql = "select sum(t.hs) as zshs,sum(dl) as kfdl,sum(gm) as kfgm,sum(tz) as hxtz from hx_kftl t where  nd=? and jd=? ";
        List<Map<String, Object>> list = query(sql, YW, new Object[] { nd, jd });
        if (list.size() > 0) {
            Map<String, Object> map = list.get(0);
            sql = "update hx_sx set zshs=?,wckfdl=?,wckfgm=?,cbhxtz=?  where nd=? and jd=?";
            update(sql, YW, new Object[] { map.get("zshs"), map.get("kfdl"), map.get("kfgm"),
                    map.get("hxtz"), nd, jd });
            //供地相关若存在
            //获取上季度的储备库库存
            String lastNd = nd;
            String lastJd = jd;
            if (jd.indexOf("1") == 0) {
                lastNd = String.valueOf(Integer.parseInt(nd) - 1);
                lastJd = "4";
            } else {
                lastJd = String.valueOf(Integer.parseInt(jd) - 1);
            }
            sql = "select cbkkc from hx_sx where nd=? and jd=?";
            List<Map<String, Object>> lastSeasonList = query(sql, YW, new Object[] { lastNd, lastJd });
            double lastCbkkc = 0;
            if (lastSeasonList.size() > 0) {
                Object obj = lastSeasonList.get(0).get("cbkkc");
                lastCbkkc = dealDouble(obj);
            }
            //获取当前季度的供应规模
            sql = "select gygm from hx_sx where  nd=? and jd=?";
            List<Map<String, Object>> seasonList = query(sql, YW, new Object[] { lastNd, lastJd });
            double gygm = 0;
            if (seasonList.size() > 0) {
                Object obj = seasonList.get(0).get("gygm");
                gygm = dealDouble(obj);
            }
            //更新储备库库存
            double cbkkc = formatDouble(lastCbkkc + dealDouble(map.get("kfgm")) - gygm);
            double cbkrznl = formatDouble(cbkkc * pgtdjz * dyl * (1 - rzss));
            sql = "update hx_sx set cbkkc=?,cbkrznl=? where nd=? and jd=?";
            update(sql, YW, new Object[] { String.valueOf(cbkkc), String.valueOf(cbkrznl), nd, jd });
            
    		//通过线程实现投融资情况更新
    		Thread trzqkThread = new Thread(new TrzqkThread());
    		trzqkThread.setDaemon(true);
    		trzqkThread.start();
        }
    }

    private String getXmbh(String xmmc) {
        String sql = "select yw_guid from jc_xiangmu where xmname=?";
        List<Map<String, Object>> list = query(sql, YW, new Object[] { xmmc });
        if (list.size() == 1) {
            return list.get(0).get("yw_guid").toString();
        }
        return "";
    }

    /**
     * 
     * <br>Description:转double
     * <br>Author:陈强峰
     * <br>Date:2013-11-7
     * @param obj
     * @return
     */
    private double dealDouble(Object obj) {
        if (obj == null) {
            return 0;
        }
        String str = obj.toString();
        if (str.length() == 0) {
            return 0;
        } else {
            return Double.parseDouble(str);
        }
    }

    /**
     * 
     * <br>Description:保留两位小数
     * <br>Author:陈强峰
     * <br>Date:2013-11-7
     * @param value
     * @return
     */
    private double formatDouble(double value) {
        return Double.parseDouble(df.format(value));
    }
}
