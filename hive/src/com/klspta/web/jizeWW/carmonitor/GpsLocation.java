package com.klspta.web.jizeWW.carmonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.client.ResponseExtractor;

import com.klspta.base.AbstractBaseBean;

public class GpsLocation extends AbstractBaseBean {
    private Map<String, GpsDeviceBean> GPSBean = new HashMap<String, GpsDeviceBean>();
  
    /***
     * 
     * <br>Description:根据gps_id实例化GpsDeviceBean
     * <br>Author:朱波海
     * <br>Date:2013-7-4
     */
    @SuppressWarnings("unchecked") 
    public GpsLocation() {
        String sql = "select t.*, c.gps_x x, c.gps_y y from wy_device_info t, WY_GPS_CURRENT_LOCATION c where t.gps_id = c.gps_id";
        List<?> list = query(sql, YW);
        Iterator<?> it = list.iterator();
        while (it.hasNext()) {
            Map<String, String> map = (HashMap<String, String>) it.next();
            GPSBean.put(map.get("GPS_ID"), new GpsDeviceBean());
        }
    }

    /**
     * 
     * <br>Description:GPS回传入口
     * <br>Author:陈强峰
     * <br>Date:2012-8-23
     */
    public void gpsBack() {
        try {
            String owner = request.getParameter("owner");
            String pwd = request.getParameter("pwd");
            String info = request.getParameter("info");
            String id = request.getParameter("id");
            System.out.println("输出结果:" + owner + ":" + pwd + "," + info + "," + id);
            if (owner == null || pwd == null || info == null || id == null
                    || (info.indexOf(",") < 0 || id.equals(""))) {
                response("false");
                return;
            }
            owner = new String(owner.getBytes("8859_1"), "UTF-8");
            info = new String(info.getBytes("8859_1"), "UTF-8");
            id = new String(id.getBytes("8859_1"), "UTF-8");
            String[] xy = info.split(",");
            float x = Float.parseFloat(xy[1]);
            float y = Float.parseFloat(xy[0]);
            if (x >= 37 || x < 31 || y >= 121 || y <= 116) {
                return;
            }
            //临时方法，用于手持机发回坐标纠偏到百度地图
            String sql = "select gps_type from WY_GPS_INFO where gps_id='" + id + "'";
            List<Map<String, Object>> rs = query(sql, YW);
            String type = "";
            if (rs != null && rs.size() > 0) {
                Map<String, Object> map = (Map<String, Object>) rs.get(0);
                type = (String) map.get("gps_type");
            }
            if ("3".equals(type)) {
                String url = "http://api.map.baidu.com/ag/coord/convert?from=0&to=4&x=" + xy[0] + "&y="
                        + xy[1];
                String result = doGet(url, null, "UTF-8", false);
                System.out.println("百度纠偏结果" + result);
                if (result.indexOf("\"error\":0") >= 0) {
                    xy[0] = result.split(",")[1].split(":")[1].replaceAll("\"", "");
                    xy[1] = result.split(",")[2].split(":")[1].replaceAll("\"", "").replaceAll("}", "");
                }
                xy[0] = new String(decode(xy[0]));
                xy[1] = new String(decode(xy[1]));
            }
            System.out.println(xy[0] + " " + xy[1]);
            if (!checkUser(owner, pwd)) {
                response("用户名密码验证失败！");
                return;
            }
            if (xy[0].equals("0") || xy[0].equals("1")) {
                response("非有效坐标！");
                return;
            }
            if (updateMemoLocation(id, xy[0], xy[1])) {
                sql = "insert into WY_GPS_LOCATION_LOG(gps_id,gps_x,gps_y) values(?,?,?)";
                update(sql, YW, new Object[] { id, xy[0], xy[1] });
            }
            sql = "update WY_GPS_CURRENT_LOCATION t set t.gps_x = ?, t.gps_y = ?,t.timestamp = sysdate where t.gps_id = ?";
           update(sql, YW, new Object[] { xy[0], xy[1], id });
            response("true");
            System.out.println("获取外业设备位置:" + id + ":" + xy[0] + "," + xy[1]);
        } catch (UnsupportedEncodingException e2) {
        	responseException(this,"gpsBack", "500001", e2);
            e2.printStackTrace();
        } catch (SQLException e1) {
        	responseException(this,"gpsBack", "500002", e1);
            e1.printStackTrace();
        }

    }
/***
 * 
 * <br>Description:更新
 * <br>Author:朱波海
 * <br>Date:2013-7-4
 * @param GPSID
 * @param x
 * @param y
 * @return
 */
    private boolean updateMemoLocation(String GPSID, String x, String y) {
        GpsDeviceBean gdb = GPSBean.get(GPSID);
        if (gdb == null) {
            System.out.println(GPSID + ":未在数据库中进行配置。");
            return false;
        }
        if (!(gdb.getX().equals(x) && gdb.getY().equals(y))) {
            gdb.setXY(x, y);
            GPSBean.put(GPSID, gdb);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * <br>Description:验证用户名密码是否存在
     * <br>Author:陈强峰
     * <br>Date:2011-11-22
     * @param name
     * @param pwd
     * @return
     * @throws SQLException 
     */
    private boolean checkUser(String owner, String pwd) throws SQLException {
        String findSql = "select username from core.CORE_USERS where username=? and password=?";
        List<Map<String, Object>> reExite = query(findSql, YW, new Object[] { owner, pwd });
        if (reExite != null) {
            return true;
        }
        return false;
    }

    /** 
     * 执行一个HTTP GET请求，返回请求响应的HTML 
     * 
     * @param url                 请求的URL地址 
     * @param queryString 请求的查询参数,可以为null 
     * @param charset         字符集 
     * @param pretty            是否美化 
     * @return 返回请求响应的HTML 
     */
    private static String doGet(String url, String queryString, String charset, boolean pretty) {
        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        try {
            if (StringUtils.isNotBlank(queryString))
                //对get请求参数做了http请求默认编码，好像没有任何问题，汉字编码后，就成为%式样的字符串 
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return response.toString();
    }

    /**
     * 解码
     * @param str
     * @return string
     */
    private byte[] decode(String str) {
        byte[] bt = null;
        try {
            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
            bt = decoder.decodeBuffer(str);
        } catch (IOException e) {
        	responseException(this,"decode", "500003", e);
            e.printStackTrace();
        }

        return bt;
    }

}
