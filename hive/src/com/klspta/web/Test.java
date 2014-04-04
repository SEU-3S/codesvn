package com.klspta.web;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.klspta.base.AbstractBaseBean;

public class Test extends AbstractBaseBean {
	public void test() {
		String sql = "select * from test";
		List<Map<String, Object>> result = query(sql, GIS);
		String str="";
		for(int i=0;i<result.size();i++){
			str+="<point name=\""+result.get(i).get("NAME99")+"\" y=\""+(new Random().nextInt(200))+"\"/>";
		}
		System.out.println(str);
	}
}
