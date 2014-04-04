package com.klspta.web.xiamen.device;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;

/**
 * <br>
 * Title:DevicePlayBack <br>
 * Description:轨迹回放 <br>
 * Author:赵伟 <br>
 * Date:2014-1-13
 */
public class DevicePlayBack extends AbstractBaseBean {
	public void getHistoryById() {
		String deviceIds = request.getParameter("carId");
		String start = request.getParameter("startDate");
		String end = request.getParameter("endDate");
		if (deviceIds == null || start == null || end == null) {
			response("");
			return;
		}
		String Ids[] = deviceIds.split(",");
		deviceIds = "'" + Ids[0] + "'";
		for (int i = 1; i < Ids.length; i++) {
			deviceIds += ",'" + Ids[i] + "'";
		}
		String sql = "select t.gps_x \"X\",t.gps_y \"Y\",(t1.gps_unit||t1.gps_name) \"CARID\",to_char(t.send_time, 'YYYY/MM/DD HH24:MI:SS') || ' UTC' history_date from GPS_LOCATION_LOG t,GPS_INFO t1 where t.gps_id in("
				+ deviceIds
				+ ") and t.send_time>to_date(?,'YYYY-MM-DD HH24:MI:SS') and t.send_time<to_date(?,'YYYY-MM-DD HH24:MI:SS') and t1.gps_id=t.gps_id order by t.gps_id,t.send_time";
		List<Map<String, Object>> result = query(sql, YW, new Object[] { start, end });
		response(result);
	}
}
