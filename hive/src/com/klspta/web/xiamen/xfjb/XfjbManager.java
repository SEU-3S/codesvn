package com.klspta.web.xiamen.xfjb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.console.ManagerFactory;
import com.klspta.model.CBDReport.CBDReportManager;
import com.klspta.web.xiamen.xchc.Cgreport;

public class XfjbManager extends AbstractBaseBean {
	
	//用于删除因为工作流创建而插入的空数据
	private static String delString = "delete from xfdjb t where t.xzq is null";
	/**
	 * 
	 * <br>Description:根据登陆人员获取对应的待处理信访案件
	 * <br>Author:朱波海
	 * <br>Date:2013-11-19
	 * @throws Exception
	 */
	public void getXfjbDcl() throws Exception {
		//用于删除因为工作流创建而插入的空数据
		update(delString, YW);
		
		String userId = request.getParameter("userId");
		String keyWord = request.getParameter("keyWord");
		String condition = request.getParameter("condition");
		String fullName = ManagerFactory.getUserManager().getUserWithId(userId)
				.getFullName();
		String sql = "select  t.yw_guid,t.bh,t.xzq,t.jbr,t.jbxs,t.lxdz,t.jbzywt,to_char(t.jbsj, 'yyyy-MM-dd')as jbsj ,t.lxdh ,t.jsr,t.jlr,j.activity_name_ ajblzt, j.wfinsid from xfdjb t join workflow.v_active_task j on t.yw_guid=j.yw_guid where j.assignee_=?";
		if(condition!=null && !"".equals(condition)){
	        condition = UtilFactory.getStrUtil().unescape(condition);
	        condition="(select * from xfdjb where "+condition+")";
	        sql = "select  t.yw_guid,t.bh,t.xzq,t.jbr,t.jbxs,t.lxdz,t.jbzywt,to_char(t.jbsj, 'yyyy-MM-dd')as jbsj ,t.lxdh ,t.jsr,t.jlr,j.activity_name_ ajblzt, j.wfinsid from "+condition+"  t join workflow.v_active_task j on t.yw_guid=j.yw_guid where j.assignee_=?";
		}
		if (keyWord != null && !"".equals(keyWord)) {
			keyWord = UtilFactory.getStrUtil().unescape(keyWord);
			sql += " and (t.xzq||t.bh||t.jbr||t.jbxs||t.jbsj||t.JSR||t.JLR||t.lxdz||t.jbzywt||t.lxdh like '%"
					+ keyWord + "%') ";
		}
		sql += " order by jbsj desc";
		List<Map<String, Object>> result = query(sql, YW,
				new String[] { fullName });
		// 调整数据格式
		int i = 0;
		for (Map<String, Object> map : result) {
			map.put("INDEX", i++);
		}
		response(result);
	}
	/**
	 * 
	 * <br>Description:获取登陆人员已处理的信访案件
	 * <br>Author:朱波海
	 * <br>Date:2013-11-19
	 * @throws Exception
	 */
	public void getXfjbYcl() throws Exception{
		//用于删除因为工作流创建而插入的空数据
		update(delString, YW);
		
		String userId = request.getParameter("userId");
		String keyWord = request.getParameter("keyWord");
		String condition = request.getParameter("condition");
		// 获取参数
		String fullName = ManagerFactory.getUserManager().getUserWithId(userId)
				.getFullName();
		// 获取数据
		String sql = "select distinct t.yw_guid,t.bh,t.xzq,t.jbr,t.jbxs,t.lxdz,t.jbzywt,to_char(t.jbsj, 'yyyy-MM-dd')as jbsj ,t.lxdh ,t.jsr,t.jlr,j.activityname as ajblzt, j.wfinsid from xfdjb t inner join workflow.v_hist_task j on t.yw_guid=j.yw_guid where j.assignee_=?";
		if(condition!=null && !"".equals(condition)){
	        condition = UtilFactory.getStrUtil().unescape(condition);
	        condition="(select * from xfdjb where "+condition+")";
	        sql = "select distinct t.yw_guid,t.bh,t.xzq,t.jbr,t.jbxs,t.lxdz,t.jbzywt,to_char(t.jbsj, 'yyyy-MM-dd')as jbsj ,t.lxdh ,t.jsr,t.jlr,j.activityname as ajblzt, j.wfinsid from "+condition+" t inner join workflow.v_hist_task j on t.yw_guid=j.yw_guid where j.assignee_=?";
	    }
		if (keyWord != null && !"".equals(keyWord)) {
			keyWord = UtilFactory.getStrUtil().unescape(keyWord);
			sql += "and (t.xzq||t.bh||t.jbr||t.jbxs||t.jbsj||t.JSR||t.JLR||t.lxdz||t.jbzywt||t.lxdh like '%"
					+ keyWord + "%')";
		}
		sql += " order by jbsj desc";
		List<Map<String, Object>> result = query(sql, YW,
				new String[] { fullName });
		int i = 0;
		for (Map<String, Object> map : result) {
			map.put("INDEX", i++);
		}
		response(result);
	}

	/**
	 * 
	 * <br>Description:获取所有信访办理中案件，案件查询时使用
	 * <br>Author:朱波海
	 * <br>Date:2013-11-20
	 */
	
	public void getXfjbBlz(){
		//用于删除因为工作流创建而插入的空数据
		update(delString, YW);
		
		String keyWord = request.getParameter("keyWord");
		//String userId = request.getParameter("userId");
		String condition = request.getParameter("condition");
		String sql = "select t.bh,t.xzq, t.yw_guid,t.jbr,t.jbxs,t.lxdz,t.jbzywt,to_char(t.jbsj, 'yyyy-MM-dd')as jbsj ,t.lxdh ,t.jsr,t.jlr, j.activity_name_ ajblzt, j.wfinsid from xfdjb t inner join workflow.v_active_task j on t.yw_guid=j.yw_guid ";
		if(condition!=null && !"".equals(condition)){
	        condition = UtilFactory.getStrUtil().unescape(condition);
	        condition="(select * from xfdjb where "+condition+")";
	        sql = "select t.bh,t.xzq, t.yw_guid,t.jbr,t.jbxs,t.lxdz,t.jbzywt,to_char(t.jbsj, 'yyyy-MM-dd')as jbsj ,t.lxdh ,t.jsr,t.jlr, j.activity_name_ ajblzt, j.wfinsid from "+condition+" t inner join workflow.v_active_task j on t.yw_guid=j.yw_guid ";
		}
		if (keyWord != null && !"".equals(keyWord)) {
			keyWord = UtilFactory.getStrUtil().unescape(keyWord);
			sql += " and (t.xzq||t.bh||t.jbr||t.jbxs||t.jbsj||t.JSR||t.JLR||t.lxdz||t.jbzywt||t.lxdh like '%"
					+ keyWord + "%')";
		}
		sql += " order by jbsj desc";
		List<Map<String, Object>> result = query(sql, YW);
		int i = 0;
		for (Map<String, Object> map : result) {
			map.put("INDEX", i++);
		}
		response(result);
	}
	/**
	 * 
	 * <br>Description:获取所有信访已办结案件，案件查询时使用
	 * <br>Author:朱波海
	 * <br>Date:2013-11-20
	 */
	public void getXFEndList(){
		//用于删除因为工作流创建而插入的空数据
		update(delString, YW);
		
		String keyWord = request.getParameter("keyWord");
		//String userId = request.getParameter("userId");
        String condition = request.getParameter("condition");
			// 获取数据
			String sql = "select t.bh,t.xzq,t.yw_guid,t.jbr,t.jbxs,t.lxdz,t.jbzywt,to_char(t.jbsj, 'yyyy-MM-dd')as jbsj ,t.lxdh ,t.jsr,t.jlr,j.endactivity_ ajblzt, j.wfinsid from xfdjb t inner join workflow.v_end_wfins j on t.yw_guid=j.yw_guid ";
			if(condition!=null && !"".equals(condition)){
	            condition = UtilFactory.getStrUtil().unescape(condition);
	            condition="(select * from xfdjb where "+condition+")";
	            sql = "select t.bh,t.xzq,t.yw_guid,t.jbr,t.jbxs,t.lxdz,t.jbzywt,to_char(t.jbsj, 'yyyy-MM-dd')as jbsj ,t.lxdh ,t.jsr,t.jlr,j.endactivity_ ajblzt, j.wfinsid from "+condition+" t inner join workflow.v_end_wfins j on t.yw_guid=j.yw_guid ";
	        }
			if (keyWord != null && !"".equals(keyWord)) {
				keyWord = UtilFactory.getStrUtil().unescape(keyWord);
				sql += " and (t.xzq||t.bh||t.jbr||t.jbxs||t.jbsj||t.JSR||t.JLR||t.lxdz||t.jbzywt||t.lxdh like '%"
						+ keyWord + "%')";
			}
			sql += " order by j.end_ desc";
			List<Map<String, Object>> result = query(sql, YW);
			// 调整数据格式
			int i = 0;
			for (Map<String, Object> map : result) {
				map.put("INDEX", i++);
			}
			response(result);
	}
	
public void getReport(){
        String userid = request.getParameter("userid");
        String yddw = request.getParameter("yddw");
        String keyword = request.getParameter("keyword");
        StringBuffer query = new StringBuffer();
        if (yddw != null && !("".equals(yddw))) {
            query.append(" where ");
            yddw = UtilFactory.getStrUtil().unescape(yddw);
            query.append(" xzq = '").append(yddw).append("'");
        }
        if(keyword != null&& !("".equals(keyword))){
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
        response(String.valueOf(new CBDReportManager().getReport("XFJBCX", new Object[]{conditionMap})));
    }
    
}
