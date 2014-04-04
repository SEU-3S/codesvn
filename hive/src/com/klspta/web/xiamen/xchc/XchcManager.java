package com.klspta.web.xiamen.xchc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.console.ManagerFactory;
import com.klspta.model.CBDReport.CBDReportManager;

public class XchcManager extends AbstractBaseBean {
	//public static final String[][] showList = new String[][]{{"READFLAG", "0.1","hiddlen"},{"GUID", "0.15","任务编号"},{"XZQMC", "0.1","所在政区"},{"XMMC", "0.1","项目名称"},{"YDDW", "0.1","用地单位"},{"RWLX","0.1","任务类型"},{"SFWF","0.1","是否违法"},{"XCR","0.05","巡查人"},{"XCRQ","0.1","巡查日期"},{"IMGNAME","0.1","hiddlen"},{"XIANGXI","0.1","详细信息"},{"DELETE","0.1","删除"}};
    public static final String[][] showXCList = new String[][]{{"GUID", "0.10","任务编号"},{"YDXMMC", "0.13","用地项目名称"},{"YDZT", "0.10","用地主体"},{"YDWZ", "0.15","用地位置"},{"ZDMJ","0.08","占地面积(亩)"},{"GDMJ","0.08","耕地面积(亩)"},{"JZMJ","0.08","建筑面积(亩)"},{"STATE","0.05","立案状态"},{"XIANGXI","0.05","详细信息"},{"SEND","0.05","发送短信"},{"LIAN","0.05","立案"},{"DELETE","0.05","删除"}};//
    public static final String[][] showLAList = new String[][]{{"GUID", "0.10","任务编号"},{"YDXMMC", "0.18","用地项目名称"},{"YDZT", "0.14","用地主体"},{"YDWZ", "0.22","用地位置"},{"ZDMJ","0.08","占地面积(亩)"},{"GDMJ","0.08","耕地面积(亩)"},{"JZMJ","0.08","建筑面积(亩)"},{"STATE","0.05","立案状态"},{"XIANGXI","0.05","详细信息"},{"SEND","0.05","发送短信"},{"LIAN","0.05","立案"},{"DELETE","0.05","删除"}};//
    public static final String[][] showHCList = new String[][]{{"GUID", "0.10","任务编号"},{"XZQMC", "0.1","所在政区"},{"YDDW", "0.11","用地单位"},{"YDSJ", "0.09","用地时间"},{"TDYT","0.08","土地用途"},{"JSQK","0.08","建设情况"},{"YDQK","0.08","用地情况"},{"DFCCQK","0.08","地方查处情况"},{"WFWGLX","0.1","违法违规类型"},{"XIANGXI","0.05","详细信息"},{"SEND","0.05","发送短信"},{"DELETE","0.05","删除"}};//
	
	public void getDclList(){
		String userId = request.getParameter("userid");
		String keyword = request.getParameter("keyword");
		IxchcData xchc = new XchcData();
		List<Map<String, Object>> queryList = xchc.getDclList(userId, keyword);
		response(queryList);
	}
	
	public void getHccgList(){
        String userId = request.getParameter("userid");
        String keyword = request.getParameter("keyword");
        IxchcData xchc = new XchcData();
        List<Map<String, Object>> queryList = xchc.getHccgList(userId, keyword);
        response(queryList);	        
	}
	/*
	public void getReport() throws Exception{
		String userid = request.getParameter("userid");
		String yddw = request.getParameter("yddw");
		String keyword = request.getParameter("keyword");
		StringBuffer query = new StringBuffer();
		if (yddw != null && !("".equals(yddw))) {
			query.append(" where ");
            yddw = UtilFactory.getStrUtil().unescape(yddw);
            query.append(" t.yddw = '").append(yddw).append("'");
        }else{
			query.append(" where ");
            yddw = UtilFactory.getXzqhUtil().getNameByCode(ManagerFactory.getUserManager().getUserWithId(userid).getXzqh());
            query.append(" t.yddw = '").append(yddw).append("'");
        }
		if(keyword != null){
			if(!(yddw != null && !("".equals(yddw)))){
				query.append(" where ");
			}else {
				query.append(" and ");
			}
			keyword = UtilFactory.getStrUtil().unescape(keyword);
			StringBuffer querybuffer = new StringBuffer();
			String[][] nameStrings = Cgreport.showHCList;
			for(int i = 0; i < nameStrings.length - 1; i++){
				querybuffer.append("upper(").append(nameStrings[i][0]).append(")||");
				
			}
			querybuffer.append("upper(").append(nameStrings[nameStrings.length - 1][0]).append(") like '%").append(keyword).append("%')");
			query.append("(");
			query.append(querybuffer);
		}
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("query", query.toString());
		response(String.valueOf(new CBDReportManager().getReport("XCHCCG", new Object[]{conditionMap})));
	}
	*/
	public void getReport(){
	     String xzq = request.getParameter("xzq");
	     String begindate = request.getParameter("begindate");
	     String enddate = request.getParameter("enddate");
	     //String keyword = request.getParameter("keyword");
	     StringBuffer query = new StringBuffer();
	     if (xzq != null && !("".equals(xzq))) {
	         query.append(" where ");
	         query.append(" t.impxzqbm = '").append(xzq).append("'");
	     }	     
	     if(begindate !=null && !("".equals(begindate))){
	         if(!(xzq != null && !("".equals(xzq)))){
	             query.append(" where ");             
	         }else{
	             query.append(" and ");
	             
	         }
	         query.append(" t.begindate > '").append(begindate).append("'");
	     }
	     if(enddate !=null && !("".equals(enddate))){
	         if(!(xzq != null && !("".equals(xzq))&& begindate !=null && !("".equals(begindate)))){
                 query.append(" where ");             
             }else{
                 query.append(" and ");              
             }
	         query.append(" t.enddate < '").append(enddate).append("'");
	     }
	     Map<String,Object> conditionMap = new HashMap<String,Object>();
	     
	     conditionMap.put("query", query.toString());
	     response(String.valueOf(new CBDReportManager().getReport("XCHCCG", new Object[]{conditionMap})));
	    
	}
	
	
    public String delData() {
        String guid = request.getParameter("yw_guid");
        if (guid != null) {
            String sql = "delete from dc_ydqkdcb where yw_guid='" + guid + "'";
            update(sql, YW);
            sql = "select file_id,file_path from atta_accessory where yw_guid=? ";
            List<Map<String, Object>> list = query(sql, CORE, new Object[] { guid });
            for (int i = 0; i < list.size(); i++) {
                String ftpFileName = list.get(i).get("file_path").toString();
                String fileId = list.get(i).get("file_id").toString();
                UtilFactory.getFtpUtil().deleteFile(ftpFileName);
                sql = "delete from atta_accessory where file_id=?";
                update(sql, CORE, new Object[] { fileId });
            }
        }
        return null;
    }
	
    public void seeLocation(){
        String xzq = request.getParameter("xzq");
        String begindate = request.getParameter("begindate");
        String enddate = request.getParameter("enddate");
        String sql = "select t.yw_guid from dc_ydqkdcb t where 1=1 ";//t.impxzq = ? and to_date(t.begindate,'YYYY-MM-DD') between to_date(?,'YY/MM/DD') and to_date(?,'YY/MM/DD') 
        if(xzq!=null && !"".equals(xzq)){
            sql+=" and t.impxzqbm ="+xzq;
        }
        Date begintime= null;
        Date endtime= null;     
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'UTC 0800' yyyy",Locale.ENGLISH);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        if(begindate!=null && !"".equals(begindate)){
            try {
                begintime = sdf.parse(begindate);
                begindate = sdf1.format(begintime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            sql+=" and to_date(t.begindate,'YYYY-MM-DD')>to_date('"+begindate+"','YY/MM/DD')";
        }
        if(enddate!=null && !"".equals(enddate)){
            try {         
                endtime = sdf.parse(enddate);
                enddate = sdf1.format(endtime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            sql+=" and to_date(t.begindate,'YYYY-MM-DD')<to_date('"+enddate+"','YY/MM/DD')";
        }
        List<Map<String,Object>> list = query(sql,YW);     
         
        String sql2 = " update dlgzwyr t set t.location='0'";
        update(sql2,GIS);
        String gisSql = "update dlgzwyr t set t.location='1' where t.yw_guid=?";
        for(int i=0;i<list.size();i++){
            Map<String,Object> map = list.get(i);
            update(gisSql,GIS,new Object[]{map.get("yw_guid")});
        }

        response("true");
        
    }
    
    public void updateState(){
        String guid = request.getParameter("id");
        String sql = "update dc_ydqkdcb t set t.state='已立案' where t.yw_guid=?";
        update(sql,YW,new Object[]{guid});
        response("success");
    }
    
    
    public void receiveCoordinate(){
        String x = request.getParameter("X");
        String y = request.getParameter("Y");
        String id = request.getParameter("imsi"); 
        System.out.println("X:"+x+"========Y:"+y+"==========ID:"+id);
        String insertSql = "insert into gps_location_log(gps_id,gps_x,gps_y) values(?,?,?)";
        int i = 0;
        i = update(insertSql,YW,new Object[]{id,x,y});      
        String sql = "update gps_current_location t set t.gps_x=?,t.gps_y=?,t.send_time=sysdate where t.gps_id=?";
        i = update(sql,YW,new Object[]{x,y,id});
        if(i == 1){
            response("success"); 
        }else{
            response("failure"); 
        }
              
    }
    
    public void getYlaList(){
		String userId = request.getParameter("userid");
		String keyword = request.getParameter("keyword");
		IxchcData xchc = new XchcData();
		List<Map<String, Object>> queryList = xchc.getYlaList(userId, keyword);
		response(queryList);
	}
    
}