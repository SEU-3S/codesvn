package com.klspta.web.jizeWW.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;

import com.klspta.base.AbstractBaseBean;


/*******************************************************************************
 * 
 * <br>
 * Title:外网工具类
 * Description: 在工程util包中未涉及到的工具，暂时写在此类里，根据需要在进行迁移到工程util
 * Author:朱波海 <br>
 * Date:2012-11-10
 */
public class UtilTool extends AbstractBaseBean  {

	
	public static Map<String, Object> cacheMap = new HashMap<String, Object>();
	/*****
	 * 
	 * <br>Description:根据两个坐标点判断方向
	 * <br>Author:朱波海
	 * <br>Date:2013-7-4
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 * @return
	 */
	public double OffsetAngle(double x0, double y0, double x1, double y1) {
		double Angle = 0.001;
		double molecular = x1 - x0;// 分子
		double denominator = y1 - y0;
		if (molecular > 0 & denominator > 0) {
			Angle = Math.atan(molecular / denominator) / 3.1415926 * 180;
		} else if (molecular > 0 & denominator < 0) {
			Angle = Math.atan(molecular / denominator) / 3.1415926 * 180 + 90;

		} else if (molecular < 0 & denominator < 0) {
			Angle = Math.atan(molecular / denominator) / 3.1415926 * 180 + 180;
		} else if (molecular < 0 & denominator > 0) {
			Angle = Math.atan(molecular / denominator) / 3.1415926 * 180 + 270;
		} else {
			Angle = 0.0000;
		}
		return Angle;
	}

	/***************************************************************************
	 * 
	 * <br>
	 * Description:后台发送请求，获取天气预报实施接口情况 <br>
	 * Author:朱波海 <br>
	 * Date:2012-11-14
	 * 
	 * @return
	 */
	public String getWeather() {
		String url = "http://www.weather.com.cn/data/cityinfo/101091011.html";
		String result = doGet(url, null, "UTF-8", false);
		return result;

	}

	/**
	 * 执行一个HTTP GET请求，返回请求响应的HTML
	 * 
	 * @param url
	 *            请求的URL地址
	 * @param queryString
	 *            请求的查询参数,可以为null
	 * @param charset
	 *            字符集
	 * @param pretty
	 *            是否美化
	 * @return 返回请求响应的HTML
	 */
	private  String doGet(String url, String queryString, String charset,
			boolean pretty) {
		StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);
		try {
			if (StringUtils.isNotBlank(queryString))
				// 对get请求参数做了http请求默认编码，好像没有任何问题，汉字编码后，就成为%式样的字符串
				method.setQueryString(URIUtil.encodeQuery(queryString));
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(method.getResponseBodyAsStream(),
								charset));
				String line;
				while ((line = reader.readLine()) != null) {
					if (pretty)
						response.append(line).append(
								System.getProperty("line.separator"));
					else
						response.append(line);
				}
				reader.close();
			}
		} catch (URIException e) {
			responseException(this, "doGet", "500003", e);
			e.printStackTrace();
		} catch (IOException e) {
			responseException(this, "doGet", "500003", e);
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return response.toString();
	}
	
	
}
