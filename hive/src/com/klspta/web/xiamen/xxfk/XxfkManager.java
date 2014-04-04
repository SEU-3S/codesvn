package com.klspta.web.xiamen.xxfk;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.console.ManagerFactory;
import com.klspta.console.user.User;

public class XxfkManager extends AbstractBaseBean {
	
	public void saveOneXxfk(){
		String enterFlag = request.getParameter("enterFlag");//用于区别新增和查看
		String yw_guid = request.getParameter("yw_guid");//yw_guid
		
		if("new".equals(enterFlag)){//新增
			String wtfkdw = request.getParameter("wtfkdw");//问题反馈单位
			String wtfkr = request.getParameter("wtfkr");//问题反馈人
			String wtfkr_id = request.getParameter("wtfkr_id");//问题反馈人id
			String wtfkms = request.getParameter("wtfkms");//问题反馈描述
			String wtfksj = request.getParameter("wtfksj");//问题反馈时间
			
			String insertSql = "insert into wtfkb(wtfkdw,wtfkr,wtfkr_id,wtfkms,wtfksj) values(?,?,?,?,?)";
			try {
				update(insertSql, YW, new Object[]{wtfkdw,wtfkr,wtfkr_id,wtfkms,wtfksj});
				response.getWriter().write("{success:true,msg:true}");
			} catch (Exception e) {
				try {
					response.getWriter().write("{failure:true,msg:true}");
				} catch (IOException e1) {
					
				}
			}
		}else{//查看
			String wtjdxq = request.getParameter("wtjdxq");//问题解答详情
			String wtjdry = request.getParameter("wtjdry");//问题解答人员
			String wtjdsj = request.getParameter("wtjdsj");//问题解答时间
			
			String updateSql = "update wtfkb t set t.wtjdxq = ?,t.wtjdry = ?,t.wtjdsj = ?,t.wtfkzt = '已解答' where t.yw_guid = ?";
			try {
				update(updateSql, YW, new Object[]{wtjdxq,wtjdry,wtjdsj,yw_guid});
				response.getWriter().write("{success:true,msg:true}");
			} catch (Exception e) {
				try {
					response.getWriter().write("{failure:true,msg:true}");
				} catch (IOException e1) {
					
				}
			}
		}
	}
	
	public void getAllXxfk(){
		String keyword = request.getParameter("keyword");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.yw_guid,t.wtfkdw,t.wtfkr,t.wtfkms,t.wtfksj,t.wtjdxq,t.wtjdry,t.wtjdsj,t.wtfkzt from wtfkb t where 1=1");
		//sqlBuffer.append("select t.* from wtfkb t where 1=1");
		if (keyword != null) {
             keyword = UtilFactory.getStrUtil().unescape(keyword);
             sqlBuffer.append(" and").append("(upper(wtfkdw)||upper(wtfkr)||upper(wtfkms)||upper(wtjdxq)||upper(wtjdry)||upper(wtfkzt)").append(" like '%");
             sqlBuffer.append(keyword);
             sqlBuffer.append("%')");
        }
		sqlBuffer.append(" order by t.wtfksj desc");
		List<Map<String, Object>> getList = query(sqlBuffer.toString(), YW);
        response(getList);
	}
	
	public void getOneXxfk(){
		String userId = request.getParameter("userId");
		String yw_guid = request.getParameter("yw_guid");
		//登录人员
		User user = null;
		try {
			user = ManagerFactory.getUserManager().getUserWithId(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//登录时间
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(date);
		//查询记录
		String selectSql = "select t.* from wtfkb t where t.yw_guid = ?";
		List<Map<String, Object>> getList = query(selectSql, YW, new Object[]{yw_guid});
		if(getList.size() == 0){//说明是新增记录
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("YW_GUID", "");
			map.put("WTFKDW", "");
			map.put("WTFKR", user.getFullName());
			map.put("WTFKR_ID", userId);
			map.put("WTFKMS", "");
			map.put("WTFKSJ", strDate);
			map.put("WTJDXQ", "");
			map.put("WTJDRY", "");
			map.put("WTJDSJ", "");
			getList.add(map);
		}else{
			getList.get(0).remove("WTJDRY");
			getList.get(0).remove("WTJDSJ");
			getList.get(0).put("WTJDRY", user.getFullName());
			getList.get(0).put("WTJDSJ", strDate);
		}
		response(getList);
	}
}
