package com.klspta.web.qingdaoNW.dtxc;

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
public class XcDataList extends AbstractBaseBean {
    public void getQueryData() {
        String keyword = request.getParameter("keyWord");
        if (keyword != null) {
            keyword = UtilFactory.getStrUtil().unescape(keyword);
            System.out.print(keyword);
            String sql = "select t.guid,t.xmmc,t.yddw,t.ydsj,t.ydwz,t.ydxz,t.jsqk,t.xcr,t.xcrq,t.pmzb,t.jwzb,t.imgname from xchcqkb t where (upper(guid)||upper(xmmc)||upper(yddw)||upper(ydsj)||upper(ydwz)||upper(ydxz)||upper(jsqk)||upper(xcr)||upper(xcrq) like '%"
                    + keyword + "%')";
            List<Map<String, Object>> query = query(sql, YW);
            response(query);
        }
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
    public String getList() {
        String sql = "select t.guid,t.xmmc,t.yddw,t.ydsj,t.ydwz,t.ydxz,t.jsqk,xcr,t.xcrq,t.pmzb,t.jwzb,t.imgname from xchcqkb t order by t.xcrq desc";
         List<List<Object>> allRows = new ArrayList<List<Object>>();
         List<Map<String, Object>> rows = query(sql, YW);
        for (int i = 0; i < rows.size(); i++) {
            List<Object> oneRow = new ArrayList<Object>();
            Map<String, Object> map = (Map<String, Object>) rows.get(i);
            oneRow.add(map.get("guid") == null ? " " :(String) map.get("guid"));
            oneRow.add(map.get("xmmc") == null ? " " :(String) map.get("xmmc"));
            oneRow.add(map.get("yddw") == null ? " " :(String) map.get("yddw"));
            oneRow.add(map.get("ydsj") == null ? " " :(String) map.get("ydsj"));
            oneRow.add(map.get("ydwz") == null ? " " :(String) map.get("ydwz"));
            oneRow.add(map.get("ydxz") == null ? " " :(String) map.get("ydxz"));
            oneRow.add(map.get("jsqk") == null ? " " :(String) map.get("jsqk"));
            oneRow.add(map.get("xcr") == null ? " " :(String) map.get("xcr"));
            oneRow.add(map.get("xcrq") == null ? " " :(String) map.get("xcrq"));
            oneRow.add(map.get("cjzb") == null ? " " :(String) map.get("cjzb"));
            oneRow.add(map.get("jwzb") == null ? " " :(String) map.get("jwzb"));
            oneRow.add(map.get("imgname") == null ? " " :(String) map.get("imgname"));
            oneRow.add(i);
            oneRow.add(i);
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
        String sql = "select t.guid,t.yddw,t.xmmc,t.ydsj,t.ydwz,t.xcr,t.xcrq,t.ydxz,t.jsqk,t.bz,t.pmzb,t.jwzb,t.imgname from xchcqkb t where t.guid = ?";
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
     * 
     * <br>Description: 删除指定的外业设备回传信息
     * <br>Author:姚建林
     * <br>Date:2012-11-19
     * @return
     */
    public String delPAD() {
        String guid = request.getParameter("guid");
        if (guid != null) {
            String sql = "delete from xchcqkb where guid='" + guid + "'";
            update(sql, YW);
        }
        return null;
    }
}
