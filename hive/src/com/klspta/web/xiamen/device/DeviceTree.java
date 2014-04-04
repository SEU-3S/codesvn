package com.klspta.web.xiamen.device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

/**
 * <br>
 * Title:DeviceTree <br>
 * Description:生成设备树相关操作 <br>
 * Author:赵伟 <br>
 * Date:2014-1-8
 */
public class DeviceTree extends AbstractBaseBean {
	private static DeviceTree dt;
	private List<Map<String, Object>> treeResults;
	private List<Map<String, Object>> treeJson;

	private DeviceTree() {
		treeJson = new ArrayList<Map<String, Object>>();
		init();
	}

	public static DeviceTree getInstance() {
		if (dt == null) {
			dt = new DeviceTree();
		}
		return dt;
	}

	private void init() {
		Map<String, List<Map<String, Object>>> devices = new HashMap<String, List<Map<String, Object>>>();
		String sql = "select * from GPS_INFO t order by t.gps_cantoncode,t.sort";
		treeResults = query(sql, YW);
		for (Map<String, Object> one : treeResults) {
			String unit = one.get("GPS_UNIT").toString();
			one.put("leaf", true);
			one.put("text", one.get("GPS_NAME"));
			List<Map<String, Object>> kinds = devices.get(unit);
			if (kinds == null) {
				ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				list.add(one);
				devices.put(unit, list);
				continue;
			}
			kinds.add(one);
		}

		Iterator<String> iterator = devices.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			List<Map<String, Object>> lt = devices.get(key);
			Map<String, Object> map = new HashMap<String, Object>();
			map.clear();
			map.put("text", key);
			map.put("expanded", true);
			map.put("children", lt);
			treeJson.add(map);
		}
	}
	/**
	 * <br>Description:获取轨迹回放的资源树
	 * <br>Author:赵伟
	 * <br>Date:2014-1-13
	 * @throws Exception
	 */
	public void getDeviceTrackTree() throws Exception{
		response(UtilFactory.getJSONUtil().objectToJSON(treeJson));
	}
	
	/**
	 * <br>
	 * Description:获取所有设备在线状态 <br>
	 * Author:赵伟 <br>
	 * Date:2014-1-9
	 */
	private Map<String, Map<String, Object>> getDevicesOnline() {
		String sql = "select t.*, case when floor(to_number(sysdate - t.send_time) * 24 * 60) < 5 then '1' else '0' end \"online\" from gps_current_location t";
		List<Map<String, Object>> result = query(sql, YW);
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
		for (Map<String, Object> one : result) {
			map.put(one.get("GPS_ID").toString(), one);
		}
		return map;
	}

	/**
	 * <br>
	 * Description:获取监控设备的树形结构 <br>
	 * Author:赵伟 <br>
	 * Date:2014-1-9
	 * 
	 * @throws Exception
	 */
	public void getDeviceMonitorTree() throws Exception {
		Map<String, Map<String, Object>> onlineDevices = getDevicesOnline();
		for (int i = 0; i < treeResults.size(); i++) {
			String gps_id = treeResults.get(i).get("GPS_ID").toString();
			String text = treeResults.get(i).get("text").toString();
			if (onlineDevices.get(gps_id) == null) {
				text = "<font color='red'>" + text + "(离线)</font>";
				treeResults.get(i).put("text", text);
				treeResults.get(i).put("online", "0");
				continue;
			}
			String online = onlineDevices.get(gps_id).get("ONLINE").toString();
			if (online.equals("1")) {
				text = "<font color='green'>" + text + "(在线)</font>";
				treeResults.get(i).put("online", "1");
			} else {
				text = "<font color='red'>" + text + "(离线)</font>";
				treeResults.get(i).put("online", "0");
			}
			treeResults.get(i).put("text", text);
		}
		response(UtilFactory.getJSONUtil().objectToJSON(treeJson));
	}
}
