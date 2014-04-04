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
 * <br>Title:供地体量
 * <br>Description:
 * <br>Author:陈强峰
 * <br>Date:2013-10-10
 */
@Component
public class Gdtl extends AbstractBaseBean {
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
     * <br>Description:新增供地体量
     * <br>Author:陈强峰
     * <br>Date:2013-10-10
     */
    public void add() {
        String xmmc = request.getParameter("gdxmmc");
        String year = request.getParameter("gdnd");
        String dl = request.getParameter("gddl")==""?"0":request.getParameter("gddl");
        String gm = request.getParameter("gdgm")==""?"0":request.getParameter("gdgm");
        String cb = request.getParameter("gdcb")==""?"0":request.getParameter("gdcb");
        String sy = request.getParameter("gdsy")==""?"0":request.getParameter("gdsy");
        String zj = request.getParameter("gdzj")==""?"0":request.getParameter("gdzj");
        String dlz = request.getParameter("gddlbl")==""?"0":request.getParameter("gddlbl");
        String gmz = request.getParameter("gdgmbl")==""?"0":request.getParameter("gdgmbl");
        String cbz = request.getParameter("gdcbbl")==""?"0":request.getParameter("gdcbbl");
        String syz = request.getParameter("gdsybl")==""?"0":request.getParameter("gdsybl");
        String zjz = request.getParameter("gdzjbl")==""?"0":request.getParameter("gdzjbl");
        String zujin = request.getParameter("zujin")==""?"0":request.getParameter("zujin");
        String season = request.getParameter("gdjd");
        String xmbh = getXmbh(xmmc);
        String sql = "insert into hx_gdtl(xmmc,nd,jd,dl,gm,cb,sy,zj,zuj,xmguid,dlz,gmz,cbz,syz,zjz) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int flag = update(sql, YW, new Object[] { xmmc, year, season, dl, gm, cb, sy, zj, zujin, xmbh, dlz,
                gmz, cbz, syz, zjz });
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
     * <br>Description:更新供地体量
     * <br>Author:陈强峰
     * <br>Date:2013-10-11
     */
    public void update() {
        String xmmc = request.getParameter("gdxmmc");
        String year = request.getParameter("year");
        String dl = request.getParameter("dl")==""?"0":request.getParameter("dl");
        String gm = request.getParameter("gm")==""?"0":request.getParameter("gm");
        String cb = request.getParameter("cb")==""?"0":request.getParameter("cb");
        String sy = request.getParameter("sy")==""?"0":request.getParameter("sy");
        String zj = request.getParameter("zj")==""?"0":request.getParameter("zj");
        String dlz = request.getParameter("dlbl")==""?"0":request.getParameter("dlbl");
        String gmz = request.getParameter("gmbl")==""?"0":request.getParameter("gmbl");
        String cbz = request.getParameter("cbbl")==""?"0":request.getParameter("cbbl");
        String syz = request.getParameter("sybl")==""?"0":request.getParameter("sybl");
        String zjz = request.getParameter("zjbl")==""?"0":request.getParameter("zjbl");
        String zujin = request.getParameter("zujin")==""?"0":request.getParameter("zujin");
        String season = request.getParameter("jd");
        String xmbh = getXmbh(xmmc);
        String sql = "update hx_gdtl set dl=?,gm=?,cb=?,sy=?,zj=?,zuj=?,dlz=?,gmz=?,cbz=?,syz=?,zjz=? where nd=? and jd=? and xmguid=?";
        int flag = update(sql, YW, new Object[] { dl, gm, cb, sy, zj, zujin, dlz, gmz, cbz, syz, zjz, year,
                season, xmbh });
        if (flag == 0) {
            response("{success:false}");
        } else {
            updateTj(year, season);
            response("{success:true}");
        }
    }

    /**
     * 
     * <br>Description:删除供地体量
     * <br>Author:陈强峰
     * <br>Date:2013-10-11
     */
    public void delete() {
        String xmmc = UtilFactory.getStrUtil().unescape(request.getParameter("gdxmmc"));
        String year = request.getParameter("gdnd");
        String season = request.getParameter("gdjd");
        String xmbh = getXmbh(xmmc);
        String sql = "select nd,jd,xmguid from hx_gdtl where nd=? and jd=? and xmguid=?";
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
     * <br>Description:供地体量查询
     * <br>Author:陈强峰
     * <br>Date:2013-10-11
     */
    public void query() {
        String xmmc = UtilFactory.getStrUtil().unescape(request.getParameter("xmmc"));
        String nd = request.getParameter("nd");
        String jd = request.getParameter("jd");
        String sql = "select xmmc,nd||'-'||yf as sx,dl,gm,cb,sy,zj,zuj as zujin,rownum-1 as mod,rownum-1 as del,yw_guid  as gdbh,dlz,gmz,cbz,syz,zjz,yf from hx_gdtl  where xmmc=? and nd=? and jd=?";

        List<Map<String, Object>> list = query(sql, YW, new Object[] { xmmc, nd, jd });
        response(list);
    }

    /**
     * 
     * <br>Description:获取季度
     * <br>Author:陈强峰
     * <br>Date:2013-10-14
     * @param month
     * @return
     */
    private String getSeason(String month) {
        int season = 1;
        int mon = Integer.parseInt(month);
        if (mon >= 4) {
            if (mon < 7) {
                season = 2;
            } else if (mon < 10) {
                season = 3;
            } else {
                season = 4;
            }
        }
        return String.valueOf(season);
    }

    /**
     * 
     * <br>Description:将项目供地体量关联时序
     * <br>Author:陈强峰
     * <br>Date:2013-10-14
     * @param nd
     * @param jd
     * @param xmbh
     */
    private void insertToSx(String nd, String jd, String xmbh) {
        String sql = "select gdtl from hx_sx where nd=? and jd=?";
        List<Map<String, Object>> list = query(sql, YW, new Object[] { nd, jd });
        if (list.size() > 0) {
            Object obj = list.get(0).get("gdtl");
            String gdtls = obj == null ? "" : obj.toString();
            if (gdtls.indexOf(xmbh) < 0) {
                StringBuffer sb = new StringBuffer(gdtls);
                sql = "update hx_sx set gdtl =? where nd=? and jd=?";
                if (sb.length() > 0) {
                    sb.append(",").append(xmbh);
                } else {
                    sb.append(xmbh);
                }
                update(sql, YW, new Object[] { sb.toString(), nd, jd });
            }
        } else {
            sql = "insert into hx_sx(nd,jd,gdtl) values(?,?,?)";
            update(sql, YW, new Object[] { nd, jd, xmbh });
        }
    }

    /**
     * 
     * <br>Description:将项目供地体量从时序移除
     * <br>Author:陈强峰
     * <br>Date:2013-10-14
     * @param nd
     * @param jd
     * @param xmbh
     */
    private void removeFromSx(String nd, String jd, String xmbh) {
        String sql = "select gdtl from hx_sx where nd=? and jd=?";
        List<Map<String, Object>> listGdtl = query(sql, YW, new Object[] { nd, jd });
        if (listGdtl.size() > 0) {
            Object obj = listGdtl.get(0).get("gdtl");
            String gdtls = obj == null ? "" : obj.toString();
            if (gdtls.indexOf(xmbh) >= 0) {
                gdtls = gdtls.replace(xmbh + ",", "").replace(xmbh, "");
                sql = "update hx_sx set gdtl =? where nd=? and jd=?";
                update(sql, YW, new Object[] { gdtls, nd, jd });
            }
        }
    }



    /**
     * 
     * <br>Description:根据年度季度更新相应的供地体量统计
     * <br>Author:陈强峰
     * <br>Date:2013-10-16
     * @param nd
     * @param jd
     */
    public void updateTj(String nd, String jd) {
        String sql = "select sum(dl) as gydl,sum(gm) as gygm  from hx_gdtl t where  nd=? and jd=? ";
        List<Map<String, Object>> list = query(sql, YW, new Object[] { nd, jd });
        if (list.size() > 0) {
            Map<String, Object> map = list.get(0);
            Object gytd = map.get("gydl");
            Object gygm = map.get("gygm");
            String lastNd = nd;
            String lastJd = jd;
            if (jd.indexOf("1") == 0) {
                lastNd = Integer.parseInt(nd) - 1 + "";
                lastJd = "4";
            } else {
                lastJd = Integer.parseInt(jd) - 1 + "";
            }
            //获取上季度的储备库库存
            sql = "select cbkkc from hx_sx where nd=? and jd=?";
            List<Map<String, Object>> lastSeasonList = query(sql, YW, new Object[] { lastNd, lastJd });
            double lastCbkkc = 0;
            if (lastSeasonList.size() > 0) {
                Object obj = lastSeasonList.get(0).get("cbkkc");
                lastCbkkc = dealDouble(obj);
            }
            //获取本季度的完成开发规模
            sql = "select wckfgm from hx_sx where nd=? and jd=?";
            List<Map<String, Object>> seasonList = query(sql, YW, new Object[] { lastNd, lastJd });
            double wckfgm = 0;
            if (seasonList.size() > 0) {
                Object obj = lastSeasonList.get(0).get("wckfgm");
                wckfgm = dealDouble(obj);
            }
            //更新储备库库存
            double cbkkc = formatDouble(lastCbkkc + wckfgm - dealDouble(gygm));
            double cbkrznl =formatDouble(cbkkc * pgtdjz * dyl * (1 - rzss));
            sql = "update hx_sx set gytd=?,gygm=?,cbkkc=?,cbkrznl=? where nd=? and jd=?";
            update(sql, YW,
                    new Object[] { gytd, gygm, String.valueOf(cbkkc), String.valueOf(cbkrznl), nd, jd });
            
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
