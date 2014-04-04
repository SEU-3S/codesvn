package com.klspta.web.qingdaoNW.dtxc;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.klspta.base.util.UtilFactory;

/**
 * <br>
 * Title:OverLayIdentify <br>
 * Description:叠加分析属性查询 <br>
 * Author:赵伟 <br>
 * Date:2014-3-30
 */
public class OverLayIdentify {
	public final String DLTB = "http://190.111.131.31/ArcGIS/rest/services/QD_DLTB/MapServer/identify";
	public final String TDYTQ = "http://190.111.131.31/ArcGIS/rest/services/GH_TDYTFQ/MapServer/identify";
	public final String SP = "http://190.111.131.35:8399/arcgis/rest/services/370201tdgy/MapServer/identify";
	public final String TDGY = "http://190.111.131.35:8399/arcgis/rest/services/370201tdgy/MapServer/identify";

	public final String geometryType = "esriGeometryPolygon";
	public final String SF = "2364";
	public final String MapExtent = "40211397.515323415,3926523.066869679,40888203.035601124,4147715.1759205544";
	public final String ImageDisplay = "1024,768,96";
	public final String returnGeometry = "true";
	public final String tolerance = "5";
	public final String f = "json";

	public JSONObject identifyAllLayers(String coords) {
		coords = "{\"rings\":[[[40540308.32939503,4003715.4087543604],[40541498.95677628,4003516.9708574843],[40540903.64308566,4002789.3652356067],[40540308.32939503,4003715.4087543604]]]}";
		String DLTB_RESULT = identifyLayer(DLTB, coords, "all:28");
		String TDYTQ_RESULT = identifyLayer(TDYTQ, coords, "all:0");
		String SP_RESULT = identifyLayer(SP, coords, "all:3");
		String TDGY_RESULT = identifyLayer(TDGY, coords, "all:2");

		JSONObject json1 = null;
		JSONObject json2 = null;
		JSONObject json3 = null;
		JSONObject json4 = null;
		JSONObject json5 = null;
		JSONObject json6 = null;
		JSONObject json7 = null;
		JSONObject json8 = null;
		try {
			json1 = UtilFactory.getJSONUtil().jsonToObject(DLTB_RESULT);
			json2 = UtilFactory.getJSONUtil().jsonToObject(TDYTQ_RESULT);
			json3 = UtilFactory.getJSONUtil().jsonToObject(SP_RESULT);
			json4 = UtilFactory.getJSONUtil().jsonToObject(TDGY_RESULT);
			json5 = UtilFactory
					.getJSONUtil()
					.jsonToObject(
							"{\"paramName\" : \"OUT_TDGY\",\"dataType\" : \"GPFeatureRecordSetLayer\",\"value\" :{\"geometryType\" : \"esriGeometryPolygon\",\"spatialReference\" :{\"wkid\" : 2364}}}");
			json6 = UtilFactory
					.getJSONUtil()
					.jsonToObject(
							"{\"paramName\" : \"OUT_SP\",\"dataType\" : \"GPFeatureRecordSetLayer\",\"value\" :{\"geometryType\" : \"esriGeometryPolygon\",\"spatialReference\" :{\"wkid\" : 2364}}}");
			json7 = UtilFactory
					.getJSONUtil()
					.jsonToObject(
							"{\"paramName\" : \"OUT_TDYTQ\",\"dataType\" : \"GPFeatureRecordSetLayer\",\"value\" :{\"geometryType\" : \"esriGeometryPolygon\",\"spatialReference\" :{\"wkid\" : 2364}}}");
			json8 = UtilFactory
					.getJSONUtil()
					.jsonToObject(
							"{\"paramName\" : \"OUT_DLTB\",\"dataType\" : \"GPFeatureRecordSetLayer\",\"value\" :{\"geometryType\" : \"esriGeometryPolygon\",\"spatialReference\" :{\"wkid\" : 2364}}}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		json5.getJSONObject("value").put("features", json4.getJSONArray("results"));
		json6.getJSONObject("value").put("features", json3.getJSONArray("results"));
		json7.getJSONObject("value").put("features", json2.getJSONArray("results"));
		json8.getJSONObject("value").put("features", json1.getJSONArray("results"));
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(json5);
		jsonArray.add(json6);
		jsonArray.add(json7);
		jsonArray.add(json8);
		JSONObject json = new JSONObject();
		json.put("results", jsonArray);
		return json;
	}

	private String identifyLayer(String url, String geometry, String layers) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("f", f);
		param.put("geometryType", geometryType);
		param.put("imageDisplay", ImageDisplay);
		param.put("mapExtent", MapExtent);
		param.put("returnGeometry", returnGeometry);
		param.put("tolerance", tolerance);
		param.put("wkid", SF);
		param.put("geometry", geometry);
		param.put("layers", layers);
		String result = sendGetHttp(url, param);
		return result;
	}

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
}
