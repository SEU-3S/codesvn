package com.klspta.web.test;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;

public class Test1 extends AbstractBaseBean {
	public void getUserInfo() {
		String sql = "select * from core_users";
		List<Map<String,Object>> result=query(sql, CORE);
		response(result);
	}
}
