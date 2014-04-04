package com.klspta.web.jizeNW.dtxc;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.console.ManagerFactory;
import com.klspta.model.analysis.Analysis;
import com.klspta.model.analysis.Djfx;

/**
 * 
 * <br>Title: 动态巡查管理类
 * <br>Description: 
 * <br>Author: 姚建林
 * <br>Date: 2013-6-18
 */
public class DtxcManager extends AbstractBaseBean {
    DecimalFormat df=new DecimalFormat("0.##"); 
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
		if (keyWord != null) {
			keyWord = UtilFactory.getStrUtil().unescape(keyWord);
			sql += " and XCBH||XCDW||XCRQ||XCQY||XCRY||XCLX||SFYWF||CLYJ||SPQK like '%"+keyWord+"%'";
		}
		List<Map<String, Object>> result = query(sql, YW);
		response((String)result.get(preNum).get("YW_GUID"));
	}
	
	/**
	 * 
	 * <br>Description:获取抄告单状态
	 * <br>Author:王雷
	 * <br>Date:2013-9-18
	 */
	public void getCgdState(){
	    String yw_guid = request.getParameter("yw_guid");
	    String sql = "select t.cgdqk from xcrz_cg t where t.yw_guid=? order by t.num asc";
	    List<Map<String,Object>> list = query(sql,YW,new Object[]{yw_guid});
	    response(list);
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
		if (keyWord != null) {
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
			String selectCgSQL="select t.yw_guid from dc_ydqkdcb t where to_char(to_date(t.hcrq,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd') = to_char(to_date(?,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd') and t.xian=?";
			List<Map<String,Object>> list2 = query(selectCgSQL,YW,new Object[]{xcrq,xcq});
			if(list2!=null && list2.size()>0){
				response("1");				
			}else{
				response("0");	
			}		
		}
	}
    public void staticWycg(){
        String xzq = UtilFactory.getStrUtil().unescape(request.getParameter("xzq"));
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        
        String sql = "select t.yw_guid, t.pmzb from dc_ydqkdcb t where t.impxzq = ? and to_date(t.hcrq,'YYYY-MM-DD HH24:MI:SS') between to_date(?,'YY/MM/DD') and to_date(?,'YY/MM/DD')";
        
        List<Map<String,Object>> list = query(sql,YW,new Object[]{xzq,start,end});
        Map<String,Object> map = null;
        String yw_guid = "";
        String points = "";
        Djfx djfx = new Djfx();
        Analysis analysis = new Analysis();
        String wkt = "";
        StringBuffer sb = new StringBuffer("<table id='title' border='0' cellpadding='0' cellspacing='0' width='800' height='60' style='text-align:center; vertical-align:middle;font-family: 宋体, Arial; font-size: 18px;'><tr><td>"+xzq+"&nbsp;&nbsp;&nbsp;"+start+"—"+end+"&nbsp;外业统计表</td></tr></table>"
        +"<table id='report' border='1' cellpadding='0' cellspacing='0' width='800'  style='text-align:center; vertical-align:middle;font-family: 宋体, Arial; font-size: 16px;border-collapse:collapse;border:1px #000 solid;' >");        
        sb.append("<tr><td>编号</td><td>总面积</td><td>不符合规划</td><td>符合规划</td><td>基本农田</td><td>权属单位</td><td>备注</td></tr>");
        double zongmianjihj =0;
        double fhghmjhj = 0;
        double bfhghmjhj = 0;
        double zyjbntmjhj = 0;        
        
        for(int i=0;i<list.size();i++){
            map = list.get(i);
            yw_guid = (String)map.get("yw_guid");
            points = (String)map.get("pmzb");
            wkt = djfx.getWkt(points, "0");
            
            //现状
            List<Map<String,Object>> xzList =analysis.analysis("XZ", "QSDWMC", wkt);
            StringBuffer xzSb = new StringBuffer("");
            for(int j=0;j<xzList.size();j++){
                Map<String,Object> xzMap = xzList.get(j);           
                xzSb.append(xzMap.get("qsdwmc")==null?"":xzMap.get("qsdwmc").toString()).append(" ");               
            }
            //规划
            List<Map<String,Object>> ghList = analysis.analysis("GH_TDYTQ", "TDYTQLXDM", wkt);
            double fhghmj = 0;
            double bfhghmj = 0;
            double zyjbntmj = 0;
            String fhghdm = "030,040,050";
            String zyjbntdm = "010";                    
            for(int j=0;j<ghList.size();j++){
                Map<String,Object> ghMap = ghList.get(j);
                String tdytqlxdm = (String)ghMap.get("tdytqlxdm");            
                double ghdlmj = 0;
                if(ghMap.get("area")!=null){
                    ghdlmj = Double.parseDouble(ghMap.get("area").toString());              
                }
                if (fhghdm.indexOf(tdytqlxdm) >= 0)
                {
                    fhghmj += ghdlmj;
                }
                else
                {
                    bfhghmj += ghdlmj;
                    if (zyjbntdm.equals(tdytqlxdm))
                    {
                        zyjbntmj += ghdlmj;
                    }
                }          
            }
            String fhghmianji = String.valueOf(df.format(fhghmj*0.0015))+"亩";
            String bfhghmianji = String.valueOf(df.format(bfhghmj*0.0015))+"亩";
            String zyjbntmianji = String.valueOf(df.format(zyjbntmj*0.0015))+"亩";            
            String zongmianji = String.valueOf(df.format(fhghmj*0.0015+bfhghmj*0.0015))+"亩";  
            sb.append("<tr><td>").append(yw_guid==null?"&nbap;":yw_guid).append("</td><td>")
            .append(zongmianji).append("</td><td>").append(bfhghmianji).append("</td><td>")
            .append(fhghmianji).append("</td><td>").append(zyjbntmianji).append("</td><td>")
            .append(xzSb).append("</td><td>").append("&nbsp;").append("</td></tr>");  
            
            zongmianjihj += (fhghmj*0.0015+bfhghmj*0.0015);
            fhghmjhj += fhghmj*0.0015;
            bfhghmjhj += bfhghmj*0.0015;
            zyjbntmjhj += zyjbntmj*0.0015;
  
        }
        
        sb.append("<tr><td>合计</td><td>").append(String.valueOf(df.format(zongmianjihj))+"亩").append("</td><td>")
        .append(String.valueOf(df.format(bfhghmjhj))+"亩").append("</td><td>").append(String.valueOf(df.format(fhghmjhj))+"亩").append("</td><td>")
        .append(String.valueOf(df.format(zyjbntmjhj))+"亩").append("</td><td>").append("&nbsp;").append("</td><td>").append("&nbsp;").append("</td></tr></table>");
        
        response(sb.toString());
    }	
}
