package com.klspta.web.cbd.xmgl.xmkgzbb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.model.CBDReport.CBDReportManager;
import com.klspta.web.cbd.yzt.zrb.ZrbData;

public class Xmkgzbbmanager extends AbstractBaseBean { 
	public static final String[][] showList = new String[][]{{"READFLAG", "0.1","hiddlen"},{"ROWNUM", "0.03","序号"},{"DKBH", "0.1","地块编号"},{"YDXZDH", "0.11","用地性质代号"},{"YDXZ", "0.11","用地性质"},{"YDMJ", "0.09","用地面积"},{"RJL","0.08","容积率"},{"JZMJ","0.08","建筑面积"},{"KZGD","0.08","控制高度"},{"BZ","0.1","备注"}};
	private String[] fields = new String[]{"yw_guid", "dzdlydmj","rjl","dzdljzmj","dzdlkzgd","dzdlbz"};
	
	public void getDKMC(){
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.qy,t.dkmc from DCSJK_KGZB t");
		List<Map<String, Object>> list = query(sqlBuffer.toString(), YW);
		response(list);
	}
	
	public void getReport(){
		String keyword = request.getParameter("keyword");
		String yw_guid = request.getParameter("yw_guid");
		StringBuffer query = new StringBuffer();
		if(keyword != null){
			keyword = UtilFactory.getStrUtil().unescape(keyword);
			StringBuffer querybuffer = new StringBuffer();
			querybuffer.append("upper(ROWNUM)||upper(T1.DKMC)||upper(T1.YDXZDH)||upper(T1.YDXZ)||upper(T1.JSYDMJ)||upper(T1.RJL)||upper(T1.GHJZGM)||upper(T1.JZKZGD)||upper(T1.BZ) like '%").append(keyword).append("%') ");
			query.append("(");
			query.append(querybuffer);
		}
		if(yw_guid != null){
			query.append(" and t2.yw_guid = '").append(yw_guid).append("'");
		}
		Map<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put("query", query.toString());
		response(String.valueOf(new CBDReportManager().getReport("XMKGZBBCX", new Object[]{conditionMap})));
	}
	 public void getQuery() {
	        HttpServletRequest request = this.request;
	        response(new XmkgzbbData().getQuery(request));
	    }
  
  public void saveDK(){
      String dkbh = request.getParameter("dkbh");
      String ydxzlx = request.getParameter("ydxzlx");
      String yw_guid = request.getParameter("yw_guid");
      String qy = request.getParameter("qy");
      String xqy = request.getParameter("xqy");
      
      dkbh=UtilFactory.getStrUtil().unescape(dkbh);
      ydxzlx=UtilFactory.getStrUtil().unescape(ydxzlx);
      yw_guid=UtilFactory.getStrUtil().unescape(yw_guid);
      qy=UtilFactory.getStrUtil().unescape(qy);
      xqy=UtilFactory.getStrUtil().unescape(xqy);
      

      String insertString="insert into xmkgzbb (dkbh,ydxzlx,yw_guid,qy,xqy )values(?,?,?,?,?)";
      int i = update(insertString, YW,new Object[]{dkbh,ydxzlx,yw_guid,qy,xqy});
      if(i>0){
         response("success");
      }else{
          response("failure");
      }
  }
  
  public void delete() throws Exception{
  	boolean result = true;
  	XmkgzbbData xmkgzbbData = XmkgzbbData.getInstance();
  	String dks =new String(request.getParameter("dkbh").getBytes("iso-8859-1"),"utf-8");
  	String yw_guid =new String(request.getParameter("yw_guid").getBytes("iso-8859-1"),"utf-8");
  	String[] dkArray = dks.split(",");
  	for(int i = 0; i < dkArray.length; i++){
  		result = result && xmkgzbbData.delete(dkArray[i],yw_guid);
  	}
  	response(String.valueOf(result));
  }
  public void update() throws Exception{
  	String yw_guid =new String(request.getParameter("key").getBytes("iso-8859-1"), "UTF-8");
  	String index = request.getParameter("vindex");
  	String value = new String(request.getParameter("value").getBytes("iso-8859-1"), "UTF-8");
  	String field = fields[Integer.parseInt(index)];
  	XmkgzbbData xmkgzbbData = XmkgzbbData.getInstance();
  	xmkgzbbData.modifyValue(yw_guid, field, value);
  	
  }
   public String  delNull(String str ){
	   if(str==null||str.equals("null")){
		   return "";
		   
	   }else{
		   return str;
		   
	   }
	   
   }
    
}
