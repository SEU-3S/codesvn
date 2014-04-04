package com.klspta.web.xuzhouNW.dtxc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.console.ManagerFactory;

/**
 * 
 * <br>Title: 动态巡查管理类
 * <br>Description: 
 * <br>Author: 姚建林
 * <br>Date: 2013-6-18
 */
public class DtxcManager extends AbstractBaseBean {
	
	//静态变量，用于标志抄告单是否生成
	static String[] strSta = new String[]{"0","0","0","0","0"};
	
	/**
	 * 
	 * <br>Title: 生成巡查日志
	 * <br>Description: 
	 * <br>Author: 姚建林
	 * <br>Date: 2013-6-18
	 */
	public void buildXcrz() {
		//生成yw_guid
		String yw_guid= UtilFactory.getStrUtil().getGuid();
		//生成xcbh巡查编号
		String xcbh = buildXcbh();
		//生成巡查日期
		String xcrq = UtilFactory.getDateUtil().getSimpleDate(new Date());
		String sql = "insert into xcrz(yw_guid,xcbh,xcrq) values (?,?,?)";
		update(sql, YW ,new Object[]{yw_guid,xcbh,xcrq});
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath()
				+ "/";
		StringBuffer url = new StringBuffer();
		url.append(basePath);
		url.append("/web/xuzhouNW/dtxc/xcrz/xcrz.jsp?jdbcname=YWTemplate&yw_guid=");
		url.append(yw_guid);
		redirect(url.toString());
	}
	
	/**
	 * 
	 * <br>Title: 生成巡查编号
	 * <br>Description: 
	 * <br>Author: 姚建林
	 * <br>Date: 2013-6-18
	 */
	public String buildXcbh(){
		StringBuffer xcbh = new StringBuffer();
		String strCou = "";
		//添加编号头"XC"
		xcbh.append("XC");
		//添加日期
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String strDate = sdf.format(date);
		xcbh.append(strDate);
		//添加流水号
		String sql = "select xcbh from( select substr(t.xcbh,11,5) xcbh from xcrz t where userid <> '1' order by t.writedate desc) where rownum=1";
		List<Map<String, Object>> result = query(sql, YW);
		if(result != null && result.size() > 0){
			strCou = result.get(0).get("xcbh").toString();//数据库中日志条数
		}else{
			strCou = "0";
		}
		int intCou = Integer.parseInt(strCou) + 1;//新生成日志是数据库中日志条数+1
		String strtemp = intCou + "";
		int i = strtemp.length();
		int j = 5 - i;//5位流水号
		for (int n = 0; n < j; n++) {
			xcbh.append("0");
		}
		xcbh.append(strtemp);
		//返回生成好的巡查日志编号
		return xcbh.toString();
	}
	
	/**
	 * 
	 * <br>Title: 异步保存抄告单
	 * <br>Description: 
	 * <br>Author: 姚建林
	 * <br>Date: 2013-6-19
	 */
	public void saveCgd(){
		String yw_guid = request.getParameter("yw_guid");
		String strFlag = request.getParameter("strFlag");
		String jsxm = UtilFactory.getStrUtil().unescape(request.getParameter("jsxm"));
		String jsdw = UtilFactory.getStrUtil().unescape(request.getParameter("jsdw"));
		String dgsj = UtilFactory.getStrUtil().unescape(request.getParameter("dgsj"));
		String jsqk = UtilFactory.getStrUtil().unescape(request.getParameter("jsqk"));
		String zdmj = UtilFactory.getStrUtil().unescape(request.getParameter("zdmj"));
		String zdwz = UtilFactory.getStrUtil().unescape(request.getParameter("zdwz"));
		String townname = UtilFactory.getStrUtil().unescape(request.getParameter("townname"));
		String countyname = UtilFactory.getStrUtil().unescape(request.getParameter("countyname"));
		String sql = "";
		int temp = 0;
		for (int i = 1; i < 6; i++) {//五个抄告单
			if(strFlag.contains(i+"")){
				temp = i;
				sql = "update xcrz set jsxm"+i+" = ?,jsdw"+i+" = ?,dgsj"+i+" = ?,jsqk"+i+" = ?,zdmj"+i+" = ?,zdwz"+i+" = ?,townname"+i+" = ?,countyname"+i+" = ?,cgdqk"+i+" = ? where yw_guid = ?";
			}
		}
		update(sql, YW, new Object[]{jsxm,jsdw,dgsj,jsqk,zdmj,zdwz,townname,countyname,1,yw_guid});
		response(strSta[temp - 1]);
		stateCgd(yw_guid);
	}
	
	/**
	 * 
	 * <br>Title: 异步删除抄告单
	 * <br>Description: 
	 * <br>Author: 姚建林
	 * <br>Date: 2013-6-19
	 */
	public void deleteCgd(){
		String yw_guid = request.getParameter("yw_guid");
		String strFlag = request.getParameter("strFlag");
		String sql = "";
		String sqldel = "delete from xcrzcgd where yw_guid = ? and cgdid = ?";;
		for (int i = 1; i < 6; i++) {
			if(strFlag.contains(i+"")){
				sql = "update xcrz set jsxm"+i+" = ?,jsdw"+i+" = ?,dgsj"+i+" = ?,jsqk"+i+" = ?,zdmj"+i+" = ?,zdwz"+i+" = ?,townname"+i+" = ?,countyname"+i+" = ?,cgdqk"+i+" = ? where yw_guid = ?";
				strSta[i - 1] = "0";
			}
		}
		update(sql, YW, new Object[]{"","","","","","","","",0,yw_guid});
		update(sqldel, YW, new Object[]{yw_guid,"cgd"+strFlag});
	}
	
	/**
	 * 
	 * <br>Title: 向前台返回五个抄告单的状态
	 * <br>Description: 
	 * <br>Author: 姚建林
	 * <br>Date: 2013-6-19
	 */
	public String[] stateCgd(String yw_guid){
		String sql = "select cgdqk1,cgdqk2,cgdqk3,cgdqk4,cgdqk5 from xcrz where yw_guid = ?";
		List<Map<String, Object>> result = query(sql, YW, new Object[]{yw_guid});
		strSta[0] = result.get(0).get("cgdqk1").toString();
		strSta[1] = result.get(0).get("cgdqk2").toString();
		strSta[2] = result.get(0).get("cgdqk3").toString();
		strSta[3] = result.get(0).get("cgdqk4").toString();
		strSta[4] = result.get(0).get("cgdqk5").toString();
		return strSta;
	}
	
	/**
	 * 
	 * <br>Title: 根据userid获得巡查日志列表
	 * <br>Description: 
	 * <br>Author: 姚建林
	 * <br>Date: 2013-6-20
	 */
	public void getXcrzListByUserId(){
		String userId = request.getParameter("userId");
		String keyWord = request.getParameter("keyWord");
		String userXzqh = "";
		String sql = "";
		try {
			userXzqh = ManagerFactory.getUserManager().getUserWithId(userId).getXzqh();
		} catch (Exception e) {
			responseException(this, "getXcrzListByUserId", "400001", e);
			e.printStackTrace();
		}
		if(userXzqh.length() == 6){
			if(userXzqh.substring(4).equals("00")){//市级
				sql = "select (rownum-1) RUNNUM1,YW_GUID,XCBH,XCRQ,XCDW,XCQY,XCRY,SFYWF,SPQK,CLYJ from xcrz where writerxzqh like '"+userXzqh.substring(0,4)+"%'";
			}else{//县级
				sql = "select (rownum-1) RUNNUM1,YW_GUID,XCBH,XCRQ,XCDW,XCQY,XCRY,SFYWF,SPQK,CLYJ from xcrz where writerxzqh like '"+userXzqh+"%'";
			}
		}else{//乡镇级
			sql = "select (rownum-1) RUNNUM1,YW_GUID,XCBH,XCRQ,XCDW,XCQY,XCRY,SFYWF,SPQK,CLYJ from xcrz where writerxzqh = "+userXzqh;
		}
		if (keyWord != null) {
			keyWord = UtilFactory.getStrUtil().unescape(keyWord);
			sql += " and XCBH||XCDW||XCRQ||XCQY||XCRY||XCLX||SFYWF||CLYJ||SPQK like '%"+keyWord+"%'";
		}
		List<Map<String, Object>> result = query(sql, YW);
		response(result);
	}
	
	/**
	 * 
	 * <br>Title: 生成抄告单统一编号
	 * <br>Description: 
	 * <br>Author: 姚建林
	 * <br>Date: 2013-6-22
	 */
	public void buildCgdbh(){
		//获得前台参数
		String yw_guid = request.getParameter("yw_guid");
		String cgdid = request.getParameter("cgdid");
		String sql = "select cgdbh from xcrzcgd where yw_guid = ? and cgdid = ?";
		List<Map<String, Object>> result = query(sql, YW, new Object[]{yw_guid,cgdid});
		if(result.size() == 1){
			response(result.get(0).get("cgdbh").toString());
		}
		if(result.size() == 0){
			StringBuffer cgdbh = new StringBuffer();
			String strCou = "";
			//年份
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String strDate = sdf.format(date);
			cgdbh.append("〔");
			cgdbh.append(strDate.substring(0,4));
			cgdbh.append("〕");
			//添加流水号
			sql = "select cgdbh from( select substr(t.cgdbh,7,3) cgdbh from xcrzcgd t where userid <> '1' order by t.createdate desc) where rownum=1";
			result = query(sql, YW);
			if(result != null && result.size() > 0){
				strCou = result.get(0).get("cgdbh").toString();//数据库中日志条数
			}else{
				strCou = "0";
			}
			int intCou = Integer.parseInt(strCou) + 1;//新生成日志是数据库中日志条数+1
			String strtemp = intCou + "";
			int i = strtemp.length();
			int j = 3 - i;//3位流水号
			for (int n = 0; n < j; n++) {
				cgdbh.append("0");
			}
			cgdbh.append(strtemp);
			cgdbh.append("号");
			
			//返回生成好的巡查日志编号
			response(cgdbh.toString());
		}
	}
	
	/**
	 * 
	 * <br>Title: 保存抄告单编号
	 * <br>Description: 
	 * <br>Author: 姚建林
	 * <br>Date: 2013-6-22
	 */
	public void saveCgdbh(){
		//获得前台参数
		String yw_guid = request.getParameter("yw_guid");
		String cgdid = request.getParameter("cgdid");
		String sql = "select * from xcrzcgd where yw_guid = ? and cgdid = ?";
		List<Map<String, Object>> result = query(sql, YW, new Object[]{yw_guid,cgdid});
		//如果数据库中之前没有这条记录，执行插入
		if(result.size() == 0){
			String cgdbh = UtilFactory.getStrUtil().unescape(request.getParameter("cgdbh"));
			String userid = request.getParameter("userid");
			//向数据库中插入数据
			sql = "insert into xcrzcgd (yw_guid,cgdid,cgdbh,userid) values (?,?,?,?)";
			update(sql, YW, new Object[]{yw_guid,cgdid,cgdbh,userid});
		}
	}
	

	/**
	 * 
	 * <br>Title: 根据当前巡查日志获取上一笔巡查日志
	 * <br>Description: 
	 * <br>Author: 黎春行
	 * <br>Date: 2013-6-24
	 */	
	public void getPreXcrz(){
		String num = request.getParameter("num");
		String userId = request.getParameter("userId");
		String keyWord = request.getParameter("keyWord");
		
		int preNum = Integer.parseInt(num) - 1;
		if(preNum < 0){
			response("error");
			return ;
		}
		
		String userXzqh = "";
		String sql = "";
		try {
			userXzqh = ManagerFactory.getUserManager().getUserWithId(userId).getXzqh();
		} catch (Exception e) {
			responseException(this, "getPreXcrz", "400001", e);
			e.printStackTrace();
		}
		if(userXzqh.length() == 6){
			if(userXzqh.substring(4).equals("00")){//市级
				sql = "select (rownum-1) RUNNUM1,YW_GUID,XCBH,XCRQ,XCDW,XCQY,XCRY,SFYWF,SPQK,CLYJ from xcrz where writerxzqh like '"+userXzqh.substring(0,4)+"%'";
			}else{//县级
				sql = "select (rownum-1) RUNNUM1,YW_GUID,XCBH,XCRQ,XCDW,XCQY,XCRY,SFYWF,SPQK,CLYJ from xcrz where writerxzqh like '"+userXzqh+"%'";
			}
		}else{//乡镇级
			sql = "select (rownum-1) RUNNUM1,YW_GUID,XCBH,XCRQ,XCDW,XCQY,XCRY,SFYWF,SPQK,CLYJ from xcrz where writerxzqh = "+userXzqh;
		}
		if (keyWord != null && (!"".equals(keyWord)) && (!"null".equals(keyWord))) {
			keyWord = UtilFactory.getStrUtil().unescape(keyWord);
			sql += " and XCBH||XCDW||XCRQ||XCQY||XCRY||XCLX||SFYWF||CLYJ||SPQK like '%"+keyWord+"%'";
		}
		List<Map<String, Object>> result = query(sql, YW);
		response((String)result.get(preNum).get("YW_GUID"));
	}
	
	/**
	 * 
	 * <br>Title: 根据当前巡查日志获取下一笔巡查日志
	 * <br>Description: 
	 * <br>Author: 黎春行
	 * <br>Date: 2013-6-24
	 */	
	public void getNextXcrz(){
		
		String num = request.getParameter("num");
		String userId = request.getParameter("userId");
		String keyWord = request.getParameter("keyWord");
		String userXzqh = "";
		String sql = "";
		try {
			userXzqh = ManagerFactory.getUserManager().getUserWithId(userId).getXzqh();
		} catch (Exception e) {
			responseException(this, "getNextXcrz", "400001", e);
			e.printStackTrace();
		}
		if(userXzqh.length() == 6){
			if(userXzqh.substring(4).equals("00")){//市级
				sql = "select (rownum-1) RUNNUM1,YW_GUID,XCBH,XCRQ,XCDW,XCQY,XCRY,SFYWF,SPQK,CLYJ from xcrz where writerxzqh like '"+userXzqh.substring(0,4)+"%'";
			}else{//县级
				sql = "select (rownum-1) RUNNUM1,YW_GUID,XCBH,XCRQ,XCDW,XCQY,XCRY,SFYWF,SPQK,CLYJ from xcrz where writerxzqh like '"+userXzqh+"%'";
			}
		}else{//乡镇级
			sql = "select (rownum-1) RUNNUM1,YW_GUID,XCBH,XCRQ,XCDW,XCQY,XCRY,SFYWF,SPQK,CLYJ from xcrz where writerxzqh = "+userXzqh;
		}
		if (keyWord != null && (!"".equals(keyWord)) && (!"null".equals(keyWord))) {
			keyWord = UtilFactory.getStrUtil().unescape(keyWord);
			sql += " and XCBH||XCDW||XCRQ||XCQY||XCRY||XCLX||SFYWF||CLYJ||SPQK like '%"+keyWord+"%'";
		}
		List<Map<String, Object>> result = query(sql, YW);
		int nextNum = Integer.parseInt(num) + 1;
		if(nextNum >= result.size()){
			response("error");
		}else{
			response((String)result.get(nextNum).get("YW_GUID"));
		}
	}
	
	/**
	 * 
	 * <br>Description:判断巡查日志是否有巡查成果
	 * <br>Author:王雷
	 * <br>Date:2013-7-13
	 */
	public void isHaveCG(){
		String yw_guid = request.getParameter("yw_guid");
		String selectSQL="select substr(t.xcdw,0,3) xcq,t.xcrq from xcrz t where t.yw_guid=?";
		List<Map<String,Object>> list = query(selectSQL,YW,new Object[]{yw_guid});
		String xcq="";
		String xcrq="";
		if(list!=null && list.size()>0){
			Map<String,Object> map = list.get(0);
			xcq = (String)map.get("xcq");
			xcrq = (String)map.get("xcrq");
			String selectCgSQL="select t.guid from v_pad_data_xml t where to_char(to_date(t.xcrq,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd') = to_char(to_date(?,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd') and t.xzqmc=?";
			List<Map<String,Object>> list2 = query(selectCgSQL,YW,new Object[]{xcrq,xcq});
			if(list2!=null && list2.size()>0){
				response("1");				
			}else{
				response("0");	
			}		
		}
	}
	
}
