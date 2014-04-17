package com.klspta.model.remoteobject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;

public class Device extends AbstractBaseBean {
	public List<Map<String, Object>> getAllDevices() {
		String sql = "select * from device_information";
		List<Map<String, Object>> result = query(sql, YW);
		return result;
	}

	public List<Map<String, Object>> getHistoryCoords(String devices, Date start, Date end) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HH:mm");
		String sql = "select * from device_track where id=? and h_date between to_date(?, 'yyyymmdd hh24:mi') and to_date(?, 'yyyymmdd hh24:mi')";
		List<Map<String, Object>> result = query(sql, YW,new Object[]{devices,formatter.format(start),formatter.format(end)});
		return result;
	}
}
