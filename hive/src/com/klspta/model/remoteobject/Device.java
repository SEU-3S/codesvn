package com.klspta.model.remoteobject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;

public class Device extends AbstractBaseBean {
	public List<Map<String, Object>> getAllDevices() {
		String sql = "select * from device_information";
		List<Map<String, Object>> result = query(sql, YW);
		return result;
	}
	
	public List<Map<String, Object>> getHistoryCoords(ArrayList<String> devices) {
		String sql = "select * from device_track";
		List<Map<String, Object>> result = query(sql, YW);
		return result;
	}
}
