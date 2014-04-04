package com.klspta.web.xiamen.ajcc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.model.CBDReport.CBDReportManager;

public class AjccManager extends AbstractBaseBean {
	public static final String[][] showList = new String[][]{{"READFLAG", "0.1","hiddlen"},{"ROWNUM", "0.03","序号"},{"YDXMMC", "0.1","用地项目名称"},{"YDDW", "0.11","用地主体"},{"YDWZ", "0.11","用地位置"},{"MJ", "0.09","占地面积"},{"JZMJ","0.08","建筑面积"},{"JZQK","0.08","建筑现状"},{"YT","0.08","用途"},{"SFFHTDLYZTGH","0.1","是否符合土地利用总体规划"},{"YDSJ","0.1","发现时间"},{"ZZQK","0.05","制止情况"},{"ZZTZSBH","0.05","制止通知书编号"},{"WJZZHJXZZ","0.05","违建制止后继续制止"},{"YYDSPQCZ","0.05","有用地审批且超占"}};
	
	
	public void getDclList(){
		String userId = request.getParameter("userid");
		String keyword = request.getParameter("keyword");
		IajccData ajcc = new AjccData();
		List<Map<String, Object>> queryList = ajcc.getDclList(userId, keyword);
		response(queryList);
	}
	
	public void getReport(){
		String keyword = request.getParameter("keyword");
		StringBuffer query = new StringBuffer();
		if(keyword != null){
			query.append(" where ");
			keyword = UtilFactory.getStrUtil().unescape(keyword);
			StringBuffer querybuffer = new StringBuffer();
			querybuffer.append("upper(YDDW)||upper(MJ)||upper(JSQK)||upper(YDSJ) like '%").append(keyword).append("%') order by ROWNUM");
			query.append("(");
			query.append(querybuffer);
		}
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("query", query.toString());
		response(String.valueOf(new CBDReportManager().getReport("AJCCCX", new Object[]{conditionMap})));
	}
	
}
