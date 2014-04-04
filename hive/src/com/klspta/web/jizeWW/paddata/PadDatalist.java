package com.klspta.web.jizeWW.paddata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.base.wkt.Polygon;

/**
 * 
 * <br>
 * Title:Pad 功能类 <br>
 * Description:对数据库中PAD表操作 <br>
 * Author:陈强峰 <br>
 * Date:2011-7-22
 */
public class PadDatalist extends AbstractBaseBean {
    public void getQueryData() {
        String keyword = request.getParameter("keyWord");
        if (keyword != null) {
            keyword = UtilFactory.getStrUtil().unescape(keyword);
            System.out.print(keyword);
            String sql = "select t.readflag,t.guid,t.xzqmc,t.xmmc,t.rwlx,t.sfwf,(select u.fullname from core.core_users u where u.username=t.xcr) xcr,t.xcrq,t.cjzb,t.jwzb,t.imgname from v_pad_data t where (upper(guid)||upper(xmmc)||upper(rwlx)||upper(sfwf)||upper(xcr)||upper(xcrq) like '%"
                    + keyword + "%')";
            List<Map<String, Object>> query = query(sql, YW);
            response(query);
        }
    }

    /**
     * 
     * <br>Description:根据传入的参数list和strColumnName返回where条件语句
     * <br>Author:王雷
     * <br>Date:2011-5-6
     * @param list
     * @param strColumnName
     * @return
     */
    public String queryForWhere(List<?> list,String strColumnName){
        String condition=null;
        String accord=null;
        String where="";
        if(list!=null&&list.size()>0){
            condition=(String)list.get(0);
            accord=(String)list.get(1);
        }
        if(condition!=null&&condition.length()>0){
            condition=condition.trim();
            while(condition.indexOf("  ")>0){//循环去掉多个空格，所有字符中间只用一个空格间隔
                condition=condition.replace("  ", " ");
            }
            condition=UtilFactory.getStrUtil().unescape(condition);
            condition=condition.toUpperCase();
            if (accord != null && "true".equals(accord)) {
                where += " and ("+strColumnName+" like '%"
                        + (condition.replaceAll(" ", "%' and "+strColumnName+"  like '%")) + "%')";//查询条件
            } else {
                where += " and ("+strColumnName+"  like '%"
                        + (condition.replaceAll(" ", "%' or "+strColumnName+"  like '%")) + "%')";//查询条件
            }
        }      
        return where;
    }
    /**
     * 
     * <br>
     * Description:查询数据库中PAD字段信息 <br>
     * Author:陈强峰 <br>
     * Date:2011-7-22
     * 
     * @param list
     *            筛选条件
     * @return
     */
    public String getPADDataList(List<?> list) {
        String strColumnName = "guid||xmmc||rwlx||sfwf||xcr||xcrq||xzqmc";
        String where = queryForWhere(list, strColumnName);
        String condition = null;
        if (list != null && list.size() > 0) {
            condition = (String) list.get(0);
        }
        String sql = "select t.readflag,t.guid,t.xzqmc,t.xmmc,t.rwlx,t.sfwf,(select u.fullname from core.core_users u where u.username=t.xcr) xcr,t.xcrq,t.cjzb,t.jwzb,t.imgname from v_pad_data t";
        sql += where;
        sql += " order by t.xcrq desc";
         List<List<Object>> allRows = new ArrayList<List<Object>>();
         List<Map<String, Object>> rows = query(sql, YW);
        for (int i = 0; i < rows.size(); i++) {
            List<Object> oneRow = new ArrayList<Object>();
            Map<String, Object> map = (Map<String, Object>) rows.get(i);
            oneRow.add(map.get("guid") == null ? " " : UtilFactory.getStrUtil().changeKeyWord(
                    (String) map.get("guid"), condition));
            oneRow.add(map.get("xzqmc") == null ? " " : UtilFactory.getStrUtil().changeKeyWord(
                    (String) map.get("xzqmc"), condition));
            oneRow.add(map.get("xmmc") == null ? " " : UtilFactory.getStrUtil().changeKeyWord(
                    (String) map.get("xmmc"), condition));
            oneRow.add(map.get("rwlx") == null ? " " : UtilFactory.getStrUtil().changeKeyWord(
                    (String) map.get("rwlx"), condition));
            oneRow.add(map.get("sfwf") == null ? " " : UtilFactory.getStrUtil().changeKeyWord(
                    (String) map.get("sfwf"), condition));
            oneRow.add(map.get("xcr") == null ? " " : UtilFactory.getStrUtil().changeKeyWord(
                    (String) map.get("xcr"), condition));
            oneRow.add(map.get("xcrq") == null ? " " : UtilFactory.getStrUtil().changeKeyWord(
                    (String) map.get("xcrq"), condition));
            oneRow.add(map.get("cjzb") == null ? " " : UtilFactory.getStrUtil().changeKeyWord(
                    (String) map.get("cjzb"), condition));
            oneRow.add(map.get("jwzb") == null ? " " : UtilFactory.getStrUtil().changeKeyWord(
                    (String) map.get("jwzb"), condition));
            oneRow.add(map.get("imgname") == null ? " " : UtilFactory.getStrUtil().changeKeyWord(
                    (String) map.get("imgname"), condition));
            oneRow.add(i);
            oneRow.add(i);
            oneRow.add(map.get("readflag") == null ? " " : UtilFactory.getStrUtil().changeKeyWord(
                    (String) map.get("readflag"), condition));
            allRows.add(oneRow);
        }
        return JSONArray.fromObject(allRows).toString();
    }

    /**
     * 
     * <br>
     * Description:删除指定采集信息 <br>
     * Author:陈强峰 <br>
     * Date:2011-7-22
     * 
     * @param mes
     *            删除条件参数数组
     * @return
     */
    boolean del(Object[] mes) {
        String sql = "delete from pad_xcxcqkb where yw_guid=?";
        int i = update(sql, YW, mes);
        return i == 1 ? true : false;
    }
/**
    private boolean delgis(Object[] mes) {
        String message = searchZb(mes);
        String sql = "delete from " + message.split("@")[1] + " where dataid=?";
        int i = update(sql, GIS, new Object[] { message.split("@")[0] });
        return i == 1 ? true : false;
    }

    private String searchZb(Object[] mes) {
        String zb = "";
        String guid = "";
        String sql = "select cjzb,guid from pad_data  where xh=? and tbsj=? and username=?";
      List<Map<String, Object>> list = query(sql, YW, mes);
        if (list.size() > 0) {
            Map<String, Object> map = (Map<String, Object>) list.get(0);
            zb = map.get("cjzb").toString();
            guid = map.get("guid").toString();
        }
        String[] allPoint = zb.split(",");
        String table = "";
        if (allPoint.length == 2) {
            table = "WYXCHC_POINT";
        } else if (allPoint.length == 4) {
            table = "WYXCHC_LINE";
        } else {
            table = "WYXCHC_POLYGON";
        }
        return guid + "@" + table;
    }
***/
    public byte[] decode(String str) {
        byte[] bt = null;
        try {
            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
            bt = decoder.decodeBuffer(str);
        } catch (IOException e) {
        	responseException(this,"decode", "500003", e);
        }

        return bt;
    }

    public String doGet(String url, String queryString, String charset, boolean pretty) {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        try {
            if (StringUtils.isNotBlank(queryString))
                // 对get请求参数做了http请求默认编码，好像没有任何问题，汉字编码后，就成为%式样的字符串
                method.setQueryString(URIUtil.encodeQuery(queryString));
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(method
                        .getResponseBodyAsStream(), charset));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (pretty)
                        response.append(line).append(System.getProperty("line.separator"));
                    else
                        response.append(line);
                }
                reader.close();
            }
        } catch (URIException e) {
        	responseException(this, "doGet", "500004", e);
            e.printStackTrace();
        } catch (IOException e) {
        	responseException(this, "doGet", "500001", e);
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return response.toString();
    }

    /**
     * <br>
     * Description: 根据yw_guid获取外业采集的数据 <br>
     * Author:李如意 <br>
     * DateTime:2012-9-12 下午12:59:08
     * 
     * @param yw_guid
     * @return
     */
    public List<Map<String, Object>>  getPADDataByYwguid(String yw_guid) {
        String sql = "select t.guid,t.xzqmc,t.xmmc,t.rwlx,t.sfwf,(select u.fullname from core.core_users u where u.username=t.xcr) xcr,t.xcrq,t.zmj,t.cjzb,t.jwzb,t.imgname,T.XCQKMC from v_PAD_DATA t where t.guid = ?";
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       // List allRows = new ArrayList();
        List<Map<String, Object>> rows = query(sql, YW, new Object[] { yw_guid });
        return rows;
    }

    /**
     * <br>
     * Description: 根据yw_guid获取系统现状、规划数据自动分析数据 <br>
     * Author:李如意 <br>
     * DateTime:2012-10-9 下午04:29:46
     * 
     * @param yw_guid
     * @return
     */
    public List<Map<String, Object>> getPADCompareAnalysisDataByYwguid(String yw_guid) { // 'XC320120910002'
        String sql = "select t.guid,to_number(t.zmj)*0.0015 zmj,t.nyd,t.gd,t.jsyd,t.wlyd,t.location,t.xzjsyd,t.xjsyd,t.ytjjsq,t.xzjsq,t.jzjsq,t.zyjbnt from V_PAD_DATA t where t.guid = ?";
       // List allRows = new ArrayList();
        List<Map<String, Object>> rows  = query(sql, YW, new Object[] { yw_guid });
        return rows;
    }

    /**
     * <br>
     * Description: 根据数据库中的标志位，显示未读的数量 <br>
     * Author:姚建林 <br>
     * DateTime:2012-11-14
     * 
     * @param
     * @return
     */
    public void getNotReadNumber() {
        String res = "";
        String sql = "select count(guid) cou from pad_data where readflag = '0'";
        List<Map<String, Object>> count = query(sql, YW);
        res = count.get(0).get("cou").toString();
        res = "(" + res + ")";
        clearParameter();
        putParameter(res);
        response();
    }

    public void getNewReadNumber() {
        String res = "false";
        String sql = "select * from pad_data t where to_char(ROUND(TO_NUMBER(sysdate - t.RECEIVETIME) * 24 * 60 *60)) <= 28";
        List<Map<String, Object>> list = query(sql, YW);
        if (list.size() > 0) {
            res = "true";
        }
        response(res);
    }

    public String getPolygon(String zb) {
        String[] zbs = zb.split(",");
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < zbs.length / 2; i++) {
            list.add(zbs[i * 2] + "," + zbs[i * 2 + 1]);
        }
        String sql = "select t.*, t.rowid from gis_extent t where t.flag = '1'";
        List<Map<String, Object>> mapConfigList = query(sql, CORE);
        int wkid = ((BigDecimal) (mapConfigList.get(0).get("wkid"))).intValue();
        Polygon polygon = new Polygon(list, wkid, true);
        String str = polygon.toJson();
        return str;
    }

    public void checkReadFlag() {
        String yw_guid = request.getParameter("carId");
        String sql = "update pad_data set readflag='1' where guid='" + yw_guid + "'";
        update(sql, YW);
        response();
    }

    /**
     * 
     * <br>Description: 删除指定的外业设备回传信息
     * <br>Author:姚建林
     * <br>Date:2012-11-19
     * @return
     */
    public String delPAD() {
        String guid = request.getParameter("yw_guid");
        if (guid != null) {
            String sql = "delete from pad_xcxcqkb where yw_guid='" + guid + "'";
            update(sql, YW);
        }
        return null;
    }
}
