package com.klspta.web.qingdaoNW.lacc.workflow;



import java.util.Calendar;
import java.util.Date;
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
 * <br>Title:立案查处工作流类
 * <br>Description:立案查处工作流类
 * <br>Author:王雷
 * <br>Date:2013-6-21
 */
public class StartWorkflowLacc extends AbstractBaseBean {
    //业务编号
	private String yw_guid = "";

	/**
	 * 
	 * <br>Description:启动工作流并初始化相关业务
	 * <br>Author:王雷
	 * <br>Date:2013-6-17
	 * @throws Exception
	 */
	public void initWorkflow() throws Exception {
		//1、获取参数 启动流程
		yw_guid = UtilFactory.getStrUtil().getGuid();
		String userId = request.getParameter("userId");
		String zfjcType = request.getParameter("zfjcType");
		String returnPath = request.getParameter("returnPath");
		String wfinsId = WorkflowOp.getInstance().start(zfjcType,ManagerFactory.getUserManager().getUserWithId(userId).getFullName(), yw_guid);
				
		//2、处理业务相关初始化
	    //立案呈批表编号生成规则 执立徐国土资【2013】128号
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		String selectSql="select bh from( select substr(t.bh,13,3) bh from lacpb t order by t.dateflag desc) where rownum=1";
		List<Map<String,Object>> list=query(selectSql,YW);
		String bh = "";
		if(list!=null && list.size()>0){
			int count=Integer.parseInt((list.get(0)).get("bh").toString());
			count=count+1;
			int number=3;
	        StringBuffer aa=new StringBuffer(count+"");
	        StringBuffer bb=new StringBuffer("");
	        for(int i=aa.length();i<number;i++){
	            bb.append("0");
	        }
	        bb.append(aa);
			bh="执立徐国土资【"+year+"】"+bb.toString()+"号";
		}else{
			bh="执立徐国土资【"+year+"】001号";
		}
		Date date=cal.getTime();
		String insertSql="insert into lacpb(yw_guid,bh,slrq) values(?,?,?)";
		update(insertSql,YW,new Object[]{yw_guid,bh,date});
		//立案查处其他表初始化
		String []datasheets={"cljdcpb","cfjdzysx","cfjdlsqk","flwscpb","jacpb","ajjbxxdjb"};
		String otherSql="";
		for(int i=0;i<datasheets.length;i++){
		    otherSql="insert into "+datasheets[i]+"(yw_guid) values(?)";
		    update(otherSql,YW,new Object[]{yw_guid});
		}
		
		//3、response参数封装及跳转
		String urlPath = "";
		if(returnPath != null && (!"null".equals(returnPath))){
			urlPath = "/model/workflow/wf.jsp?yw_guid="
				+ yw_guid + "&zfjcType=" + zfjcType + "&wfInsId=" + wfinsId+ "&buttonHidden=la,back&zfjcName=立案查处&returnPath=" + returnPath;
		}else{
			urlPath = "/model/workflow/wf.jsp?yw_guid="
				+ yw_guid + "&zfjcType=" + zfjcType + "&wfInsId=" + wfinsId+ "&buttonHidden=la,back&zfjcName=立案查处&returnPath=web/xuzhouNW/lacc/dbaj/dbaj.jsp";
		}
		response(urlPath);
	}
	
	/**
	 * 
	 * <br>Description:工作流的中止方法
	 * <br>Author:王雷
	 * <br>Date:2013-6-17
	 */
	public void deleteWorkflow(){
	    String yw_guid = request.getParameter("yw_guid");
	    String wfInsId = request.getParameter("wfInsId");
	    //1.删除业务数据
	    String []datasheets={"lacpb","cljdcpb","cfjdzysx","cfjdlsqk","flwscpb","jacpb","ajjbxxdjb"};
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
