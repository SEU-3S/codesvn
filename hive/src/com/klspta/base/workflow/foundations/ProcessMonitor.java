package com.klspta.base.workflow.foundations;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

public class ProcessMonitor extends AbstractBaseBean {
	public static boolean isFlush = true;
	public static Map<String, Map<String, Integer>> c_map;// 用来存放节点信息缓存的Map

	/**
	 * <br>
	 * Description:获得流程监控列表 <br>
	 * Author:赵伟 <br>
	 * Date:2012-9-14
	 * 
	 * @return
	 * @throws Exception
	 */
	public void getProcessMonitorList() throws Exception {
		String wfInsId = request.getParameter("wfInsId");
		String wfId = request.getParameter("wfId");
		String sql = "select t1.dbid_,t2.activity_name_,t1.assignee_,t2.start_,t2.end_,t3.x,t3.y,t3.height,t3.width from JBPM4_HIST_TASK t1,JBPM4_HIST_ACTINST t2,EXT_NODEDEFINE_INFO t3 "
				+ "where t3.wfid=? and t1.execution_=? and t2.activity_name_=t3.nodename and t1.dbid_=t2.htask_ order by t1.dbid_";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> list = query(sql, WORKFLOW, new String[] { wfId,wfInsId });
		for (Map<String, Object> map : list) {
			if (map.get("END_") == null) {
				map.remove("END_");
				map.put("END_", "----------------");
			} else {
				String end = df.format(map.get("END_"));
				map.remove("END_");
				map.put("END_", end);
			}
			String start = df.format(map.get("START_"));
			map.remove("START_");
			map.put("START_", start);
		}
		response(list);
	}

	/**
	 * <br>
	 * Description:获取历史节点信息，用于给流程图涂色 <br>
	 * Author:赵伟 <br>
	 * Date:2012-10-29
	 * 
	 * @param wfInsId
	 * @return 根据hist_activity表里面当前节点和transmission是否相同判断是否为回退节点
	 */
	public List<Map<String, Object>> getHistoryTaskInfo(String wfInsId) {
		// 回退的节点
		String sql_1 = "select distinct(t.activity_name_) from JBPM4_HIST_ACTINST t where t.execution_=? and t.activity_name_ =t.transition_";
		// 没有回退并且已经走过的节点
		String sql_2 = "select distinct(t.activity_name_) from JBPM4_HIST_ACTINST t where t.execution_=? and t.activity_name_ !=t.transition_ ";
		List<Map<String, Object>> result_1 = query(sql_1, WORKFLOW, new String[] { wfInsId });
		List<Map<String, Object>> result_2 = query(sql_2, WORKFLOW, new String[] { wfInsId });
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map_1 = new HashMap<String, Object>();
		Map<String, Object> map_2 = new HashMap<String, Object>();
		for (int i = 0; i < result_1.size(); i++) {
			Map<String, Object> map = result_1.get(i);
			map_1.put((String) map.get("activity_name_"), (String) map.get("activity_name_"));
		}
		for (int i = 0; i < result_2.size(); i++) {
			Map<String, Object> map = result_2.get(i);
			map_2.put((String) map.get("activity_name_"), (String) map.get("activity_name_"));
		}
		list.add(map_1);
		list.add(map_2);
		return list;
	}

	/**
	 * <br>
	 * Description:流程图加上节点提示信息 <br>
	 * Author:赵伟 <br>
	 * Date:2012-10-31
	 * 
	 * @param wfInsId
	 *            流程实例Id
	 * @param activityName
	 *            节点名称
	 */
	public void getImageTip() {
		String wfInsId = request.getParameter("wfInsId");
		String activityName = request.getParameter("activityName");
		activityName = UtilFactory.getStrUtil().unescape(activityName);

		String sql = "select t1.activity_name_,t2.assignee_,to_char(t1.start_,'YYYY-MM-DD HH24:MI:SS') start_,to_char(t2.end_,'YYYY-MM-DD HH24:MI:SS') end_ from JBPM4_HIST_ACTINST t1,jbpm4_hist_task t2 "
				+ "where t1.htask_=t2.dbid_ and t1.execution_=? and t1.activity_name_=? order by t1.start_";
		List<Map<String, Object>> result = query(sql, WORKFLOW, new String[] { wfInsId, activityName });
		for (Map<String, Object> map : result) {
			if (map.get("END_") == null) {
				map.put("END_", "----------------");
			}
		}
		response(result);
	}
}
