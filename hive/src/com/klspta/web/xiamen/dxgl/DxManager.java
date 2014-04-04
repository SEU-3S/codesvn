package com.klspta.web.xiamen.dxgl;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;

public class DxManager extends AbstractBaseBean {

	public void getAllList(){
		String keyword = request.getParameter("keyword");
		IdxData dxData = new DxData();
		List<Map<String, Object>> queryList = dxData.getAllList(keyword);
		response(queryList);
	}
	
	public void getOwnerList(){
		String userId = request.getParameter("userid");
		String keyword = request.getParameter("keyword");
		IdxData dxData = new DxData();
		List<Map<String, Object>> queryList = dxData.getOwnerList(userId, keyword);
		response(queryList);
	}
}
