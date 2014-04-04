package com.klspta.web.qingdaoNW.dtxc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

/**
 * <br>
 * Title:OverLay <br>
 * Description:青岛内网压盖分析<br>
 * Author:赵伟 <br>
 * Date:2014-3-17
 */
public class OverLay extends AbstractBaseBean {

	private static List<Map<String, Object>> gp = new ArrayList<Map<String, Object>>();
	private static List<Map<String, Object>> geometry = new ArrayList<Map<String, Object>>();

	public JSONObject executeTask(String coords) throws Exception {
		coords = coordsToWKT(coords);
		String geourl = getGeoServices();
		// geourl="http://127.0.0.1:8399/arcgis/rest/services/SEU/GPServer/OVERLAY/execute";
		Map<String, String> params = new HashMap<String, String>();
		params.put("f", "pjson");
		params.put("INPUT_POLYGON", coords);
		String result = sendGetHttp(geourl, params);
		JSONObject json = UtilFactory.getJSONUtil().jsonToObject(result);
		return areaResults(json);
	}

	private JSONObject areaResults(JSONObject json) throws Exception {
		List<JSONObject> polygons = new ArrayList<JSONObject>();
		List<Integer> polygonsIndex = new ArrayList<Integer>();// 存储每个图层图斑的序列
		int index = 0;
		for (int i = 0; i < json.getJSONArray("results").size(); i++) {
			JSONObject value = (JSONObject) json.getJSONArray("results").get(i);
			if (value.getJSONObject("value").getJSONArray("features").size() == 0) {
				polygonsIndex.add(i);
				continue;
			}
			for (int j = 0; j < value.getJSONObject("value").getJSONArray("features").size(); j++) {
				JSONObject polygon = value.getJSONObject("value").getJSONArray("features").getJSONObject(j)
						.getJSONObject("geometry");
				polygons.add(polygon);
			}
			index = index + value.getJSONObject("value").getJSONArray("features").size();
			polygonsIndex.add(index);
		}
		String geometryurl = getGeometry();
		// geometryurl="http://127.0.0.1:8399/arcgis/rest/services/Geometry/GeometryServer";
		geometryurl += "/areasAndLengths";
		Map<String, String> params = new HashMap<String, String>();
		params.put("f", "json");
		params.put("sr", "2364");
		params.put("polygons", polygons.toString());
		String result = sendPostHttp(geometryurl, params);
		JSONArray areas = UtilFactory.getJSONUtil().jsonToObject(result).getJSONArray("areas");
		int index1 = 0;// 标记返回面积数组的游标
		for (int i = 0; i < polygonsIndex.size(); i++) {
			int index2 = polygonsIndex.get(i);
			if (index2 - index1 == 0) {
				json.getJSONArray("results").getJSONObject(i).put("F_AREA", 0);
				continue;
			}
			double area = 0;
			int index3 = 0;// 标记图斑在图层里面的游标
			for (int j = index1; j < index2; j++) {
				// 累加图层面积
				area = area + Double.parseDouble(areas.getString(j));
				// 图斑加入叠加面积字段
				json.getJSONArray("results").getJSONObject(i).getJSONObject("value").getJSONArray("features")
						.getJSONObject(index3).put("F_AREA", areas.getString(j));
				index1++;
				index3++;
			}
			json.getJSONArray("results").getJSONObject(i).put("F_AREA", area);
		}
		return json;
	}

	private String coordsToWKT(String coords) {
		//coords="40538031.74142029,3991906.8040332077,40538549.26745534,3991871.2439620877,40538341.83370714,3991481.776516486,40538031.74142029,3991906.8040332077";
		String[] cs = coords.split(",");
		if (cs.length == 0) {
			return null;
		}
		StringBuilder str = new StringBuilder();
		str.append("{\"features\":[{\"geometry\":{\"rings\":[[");
		for (int i = 0; i < cs.length; i = i + 2) {
			str.append("[");
			str.append(cs[i]);
			str.append(",");
			str.append(cs[i + 1]);
			str.append("],");
		}
		str.deleteCharAt(str.length() - 1);
		str.append("]],\"spatialReference\":{\"wkid\":2364}}}],\"geometryType\":\"esriGeometryPolygon\"}");
		return str.toString();
	}

	// /////////////////////////////////发GET POST请求//////////////////////
	private String sendGetHttp(String url, Map<String, String> param) {
		StringBuilder result = new StringBuilder();
		Iterator<Entry<String, String>> iter = param.entrySet().iterator();
		url += "?";
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			url += "&" + key + "=" + val;
		}
		try {
			URL geturl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) geturl.openConnection();
			connection.connect();
			BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(connection.getInputStream(),
					"UTF-8"));
			String lines;
			while ((lines = reader.readLine()) != null) {
				result.append(lines);
			}
			reader.close();
			connection.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	private String sendPostHttp(String url, Map<String, String> param) {
		StringBuilder result = new StringBuilder();
		Iterator<Entry<String, String>> iter = param.entrySet().iterator();
		url += "?";
		String parameters = "";
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			parameters += "&" + key + "=" + val;
		}
		try {
			URL posturl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) posturl.openConnection();
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			byte[] b = parameters.getBytes();
			connection.getOutputStream().write(b, 0, b.length);
			connection.getOutputStream().flush();
			connection.getOutputStream().close();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String lines;
			while ((lines = in.readLine()) != null) {
				result.append(lines);
			}
			in.close();
			connection.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	// //////////////////////获取服务地址/////////////////////////
	private String getGeoServices() {
		if (gp.size() == 0) {
			String sql = "select * from gis_gpservices t where t.flag=1 and t.id='OVERLAY'";
			List<Map<String, Object>> list = query(sql, CORE);
			gp = list;
		}
		return gp.get(0).get("URL").toString();
	}

	private String getGeometry() {
		if (geometry.size() == 0) {
			String sql = "select * from GIS_GEOMETRYSERVICE t where t.flag=1 ";
			List<Map<String, Object>> list = query(sql, CORE);
			geometry = list;
		}
		return geometry.get(0).get("URL").toString();
	}
}
