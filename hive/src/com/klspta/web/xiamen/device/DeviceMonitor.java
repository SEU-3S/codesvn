package com.klspta.web.xiamen.device;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

/**
 * <br>
 * Title:DeviceMonitor <br>
 * Description:设备监控、轨迹回放 <br>
 * Author:赵伟 <br>
 * Date:2014-1-8
 */
public class DeviceMonitor extends AbstractBaseBean {

	/**
	 * <br>
	 * Description:获取坐标信息 <br>
	 * Author:赵伟 <br>
	 * Date:2014-1-8
	 * 
	 * @throws Exception
	 */
	public void getDeviceCoordinate() throws Exception {
		String sql = "select * from gps_current_location";
		List<Map<String, Object>> result = query(sql, YW);
		Map<String, Map<String, Object>> devicesCoor = new HashMap<String, Map<String, Object>>();
		for (Map<String, Object> map : result) {
			devicesCoor.put(map.get("GPS_ID").toString(), map);
		}
		response(UtilFactory.getJSONUtil().objectToJSON(devicesCoor));
	}
}
