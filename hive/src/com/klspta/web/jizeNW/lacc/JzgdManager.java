package com.klspta.web.jizeNW.lacc;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

public class JzgdManager  extends AbstractBaseBean{
	
	public void getList(){
		   // 获取参数
        String keyWord = request.getParameter("keyword");
      //  String fullName = UtilFactory.getStrUtil().unescape(request.getParameter("fullName"));
        String sql = "select t.YW_GUID,t.BH ,t.AY,t.DWMC,t.FDDBR,t.DWDZ,t.AJLY ,to_char(t.SLRQ,'yyyy-MM-dd hh24:mi:ss') as SLRQ from jzgd_lacpb t ";
        if (keyWord != null) {
            keyWord = UtilFactory.getStrUtil().unescape(keyWord);
            sql += " where  (upper(t.BH)||upper(t.AY)||upper(t.DWMC)||upper(t.FDDBR)||upper(t.DWDZ)||upper(t.SLRQ)||upper(t.AJLY) like '%"
                    + keyWord + "%')";
        }
        List<Map<String, Object>> result = query(sql, YW);
        response(result);
	}
	//新增归档卷宗
  public void addGD(){
	  //调用方法生成YW_GUID//生成yw_guid
		String yw_guid= UtilFactory.getStrUtil().getGuid();
		String sql = "insert into JZGD_LACPB(yw_guid) values (?)";
		int i = update(sql, YW ,new Object[]{yw_guid});
		if(i==1){
			response(yw_guid);
		}else{
			response("false");
		}
		
	  
  }
	
}
