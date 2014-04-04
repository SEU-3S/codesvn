package com.klspta.model.accessory.dzfj;

import java.rmi.server.UID;
import java.util.List;
import java.util.Map;
import com.klspta.base.AbstractBaseBean;

public class Globals extends AbstractBaseBean{
	
     // <br>Description:取得临时路径 <br>
   
	public  String getTemp_File_path() {
		
			String sql = "select t.child_name from public_code t where t.id='TEMPFILEPATH' and t.in_flag='1'";
			List<Map<String, Object>> list = query(sql,YW);
		return (String)list.get(0).get("child_name");
	}
	public static String getGuid() {
		return new UID().toString().replaceAll(":", "-");

	}
}
