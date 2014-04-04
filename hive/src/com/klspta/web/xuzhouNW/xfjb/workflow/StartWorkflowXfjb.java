package com.klspta.web.xuzhouNW.xfjb.workflow;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.base.workflow.foundations.IWorkflowInsOp;
import com.klspta.base.workflow.foundations.WorkflowInsOp;
import com.klspta.base.workflow.foundations.WorkflowOp;
import com.klspta.console.ManagerFactory;

/**
 * 
 * <br>Title:信访举报处理类
 * <br>Description:信访举报工作流处理类
 * <br>Author:黎春行
 * <br>Date:2013-5-21
 */
public class StartWorkflowXfjb extends AbstractBaseBean {
	/**
	 * 
	 * <br>
	 * Description:创建工作流实例 <br>
	 * Author:黎春行 <br>
	 * Date:2013-4-2
	 * 
	 * @throws Exception
	 */
	public void initWorkflow() throws Exception {
		//1、获取参数 启动流程
		String yw_guid = UtilFactory.getStrUtil().getGuid();
		String userId = request.getParameter("userId");
		String zfjcType = request.getParameter("zfjcType");
		String wfinsId = WorkflowOp.getInstance().start(
				zfjcType,
				ManagerFactory.getUserManager().getUserWithId(userId)
						.getFullName(), yw_guid);
		    String xzqh = ManagerFactory.getUserManager().getUserWithId(userId).getXzqh();
		//2、处理业务相关初始化
		String bh = buildID();
		String sql = "insert into wfxsfkxx(yw_guid, bh,txryxzqh) values(? , ?,?)";
		update(sql, YW, new Object[] { yw_guid, bh,xzqh});
		//3、response参数封装及跳转
		String urlPath = "model/workflow/wf.jsp?yw_guid="
				+ yw_guid + "&zfjcType=" + zfjcType + "&wfInsId=" + wfinsId
				+ "&buttonHidden=la,return,back&zfjcName=信访举报&returnPath=web/xuzhouNW/xfaj/xfybaj.jsp";
		response(urlPath);
	}
	
	/**
	 * 
	 * <br>Description:创建表单流水号
	 * <br>Author:黎春行
	 * <br>Date:2013-6-18
	 */
	private String buildID(){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", new DateFormatSymbols());
		String dateString = df.format(Calendar.getInstance().getTime());
				
		//目前所有人共用测试数据库，不能用内存记录流水号
		String numsql = "select max(t.bh) num from wfxsfkxx t where t.bh like '" + dateString + "%'";
		String num;
		List<Map<String, Object>> result = query(numsql, YW);
		String nestNum = String.valueOf(result.get(0).get("num"));
		if(nestNum.equals("null") || nestNum.equals("")){
			num = dateString + "001";
		}else{
			//String nestNum = (String)result.get(0).get("num");
			String temp = nestNum.substring(nestNum.length() - 3, nestNum.length());
            temp=String.valueOf(Integer.parseInt(temp)+1);   
            temp = "00" + temp;
            num = dateString + temp.substring(temp.length() - 3);
		}
		return num;
		
	}
	
	
	/**
	 * 
	 * <br>Description:工作流的中止方法
	 * <br>Author:黎春行
	 * <br>Date:2013-6-21
	 */
	public void deleteWorkflow(){
	    String yw_guid = request.getParameter("yw_guid");
	    String wfInsId = request.getParameter("wfInsId");
	    //1.删除业务数据
	    String []datasheets={"wfxsfkxx"};
        String sql="";
	    for(int i=0;i<datasheets.length;i++){
	        sql="delete from "+datasheets[i]+" where yw_guid=?";
	        update(sql,YW,new Object[]{yw_guid});   
	    }
	    //2.删除工作流实例
	    IWorkflowInsOp workflowIns = WorkflowInsOp.getInstance();
	    workflowIns.deleteWfIns(wfInsId);
	    response("true");
	}
}
