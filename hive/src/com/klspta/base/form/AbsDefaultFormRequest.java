package com.klspta.base.form;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

/**
 * 
 * <br>
 * Title:表单请求抽象类 <br>
 * Description:用于前台表单请求时的处理类，此为抽象类，所有用于表单的bean均需继承该类 <br>
 * Author:王瑛博 <br>
 * Date:2011-7-19
 */
abstract public class AbsDefaultFormRequest extends AbstractBaseBean {
	/**
	 * 默认保存标识
	 */
	public static final String USE_DEFAULT_SAVE_IMPL = "usedefaultsaveimpl";

	/**
	 * 默认查询标识
	 */
	public static final String USE_DEFAULT_QUERY_IMPL = "usedefaultqueryimpl";

	/**
	 * 时间格式化
	 */
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 
	 * <br>
	 * Description:装载数据 <br>
	 * Author:王瑛博 <br>
	 * Date:2011-7-19
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void loadData() throws Exception {
		try {
			String resu = query();
			if (resu.equals(AbsDefaultFormRequest.USE_DEFAULT_QUERY_IMPL)) {
				resu = queryDefault();
			}
			request.setAttribute("flag", "0");
			response.getWriter().write(resu);
		} catch (Exception ex) {
			response.getWriter().write("error");
		}
	}

	/**
	 * 
	 * <br>
	 * Description:保存表单数据 <br>
	 * Author:王瑛博 <br>
	 * Date:2011-7-19
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void saveData() throws Exception {
		request.setCharacterEncoding("UTF-8");
		String url = request.getHeader("referer");
		try {
			String resu = save();
			response.setCharacterEncoding("utf-8");
			if (resu.equals(AbsDefaultFormRequest.USE_DEFAULT_SAVE_IMPL)) {
				resu = "success";
				saveDefault();
			}
			request.setAttribute("flag", "1");
			response.getWriter().write(resu);
		} catch (Exception ex) {
			response.getWriter().write("error");
		}
		response.sendRedirect(url + "&msg=success");
	}

	// ////////////////////////共外部实现的保存方法/////////////////////////////////////////////////////////
	abstract public String save() throws Exception;

	abstract public String query() throws Exception;

	// /////////////////////////////////////公共方法
	// 保存、取数据都用////////////////////////////////////////
	private List<Map<String, Object>> getAllFields(String tableName) {
		tableName = tableName.toUpperCase();
		String sql = "select distinct(t.COLUMN_NAME),t.DATA_TYPE from dba_tab_columns t where t.TABLE_NAME = ?";// 去掉t.OWNER
		// =
		// 'GTZF'扩大数据库搜索范围
		if (tableName.equalsIgnoreCase("xfgl")) {
			sql = "select distinct(t.COLUMN_NAME),t.DATA_TYPE from dba_tab_columns t where t.TABLE_NAME = ? and t.OWNER = 'ZFJC'";
		}
		Object[] args = { tableName };
		String jdbcName = "CORETemplate";
		List<Map<String, Object>> list = query(sql, jdbcName, args);
		return list;
	}

	// /////////////////////////////////////存数据用的方法/////////////////////////////////////////////////
	public void saveDefault() throws Exception {
		String keyValue = request.getParameter("_key");
		String primaryKey = request.getParameter("_keyfield");
		String jdbcName = request.getParameter("_jdbcname");
		String sql = buildSQL(request, primaryKey);
		Object[] args = { keyValue };
		update(sql, jdbcName, args);
	}

	/**
	 * 
	 * <br>
	 * Description:构建SQL <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param request
	 * @return
	 */
	private String buildSQL(HttpServletRequest request, String primaryKey) {
		String formName = request.getParameter("_formName");
		List<Map<String, Object>> listFields = getAllFields(formName);
		StringBuffer sqlsb = new StringBuffer("update ").append(formName).append(" set ");
		String sql = "";
		String fieldString = "";
		String dataTypeString = "";
		String valueString = "";
		String[] valueStrings = null;
		for (Map<String, Object> field : listFields) {
			fieldString = (String) field.get("COLUMN_NAME");
			dataTypeString = (String) field.get("DATA_TYPE");
			try {
				valueString = request.getParameter(fieldString.toLowerCase());
				if (request.getParameter(fieldString.toLowerCase()) != null)
					valueString = new String(request.getParameter(fieldString.toLowerCase()).getBytes("iso-8859-1"),
							"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valueStrings = request.getParameterValues(fieldString.toLowerCase());

			if (valueString == null || valueString.equals("null")) {
				valueString = "";
			} else {
				if (valueStrings.length > 1) {
					valueString = "";
					for (String s : valueStrings) {
						valueString = valueString + "!" + s;
					}
				}
				sqlsb.append(fieldString);
				if (dataTypeString.equals("VARCHAR2") || dataTypeString.equals("NUMBER")
						|| dataTypeString.equals("CHAR")) {
					sqlsb.append(" = '").append(valueString).append("',");
				} else if (dataTypeString.equals("DATE")) {
					sqlsb.append(" = TO_DATE('").append(valueString).append("','yyyy-mm-dd hh24:mi:ss'),");
				} else {
					sqlsb.append(" = ").append(valueString).append(",");
				}
			}
		}
		sql = sqlsb.toString();
		sql = sql.substring(0, sql.length() - 1) + " where " + primaryKey + " = ?";
		return sql;
	}

	// /////////////////////////////////////取数据用的方法//////////////////////////////////////////////////
	public String queryDefault() throws Exception {
		String activityName = request.getParameter("activityName");
		if (activityName != null) {
			activityName = URLDecoder.decode(activityName, "utf-8");
		} else {
			activityName = "null";
		}
		String zfjcType = request.getParameter("zfjcType");
		String formName = request.getParameter("_formName");
		String keyValue = request.getParameter("_key");
		String primaryKey = request.getParameter("_keyfield");
		String editFields = getEditFields(activityName, zfjcType, formName, keyValue);
		List<Map<String, Object>> listFields = getAllFields(formName);
		String ret = queryData(formName, primaryKey, keyValue, listFields, editFields);
		return ret;
	}

	/**
	 * 
	 * <br>
	 * Description:保存表单权限 <br>
	 * Author:尹宇星 <br>
	 * Date:2011-11-07
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void setEditFields() throws Exception {
		request.setCharacterEncoding("utf-8");
		String formName = request.getParameter("formName");
		String activityName = request.getParameter("activityName");
		activityName = URLDecoder.decode(request.getParameter("activityName"), "utf-8");
		String zfjcType = request.getParameter("zfjcType");
		String editFiles = request.getParameter("editFiles");
		if(!"".equals(editFiles)){
		    editFiles = editFiles.substring(0, editFiles.length() - 1);
		}else{
		    editFiles = "no";
		}
		Object[] args = { editFiles, activityName, zfjcType, formName };
		int i = 0;
		if (queryData(activityName, zfjcType, formName)) {
			i = update("update FORM_AUTHORITY set editFiles=? where activityName=? and zfjcType=? and formName=?", CORE,
					args);
		} else {
			i = update("insert into FORM_AUTHORITY(editFiles,activityName,zfjcType,formName) values(?,?,?,?)", CORE, args);
		}
		if(i ==1){
		   response("success"); 
		}else{
		   response("failure"); 
		}
		
	}
	
	/**
	 * 
	 * <br>Description:工作节点资源树授权
	 * <br>Author:王雷
	 * <br>Date:2013-6-20
	 */
   public void saveWorkflowTree() {
        try {
            String treeIdList = request.getParameter("treeIdList");
            String wfID = request.getParameter("wfID");
            String treeName = request.getParameter("treeName");
            String nodeName = URLDecoder.decode(request.getParameter("nodeName"), "utf-8");
            String sql = "select nodeName from map_workflow_tree where wfid='" + wfID + "' and nodeName='" + nodeName
                    + "'";
            List<Map<String, Object>> list = query(sql, CORE);
            if (list.size() == 0) {
                sql = "insert into map_workflow_tree(nodeids,wfid,nodename,treename) values(?,?,?,?)";
            } else {
                sql = "update map_workflow_tree set nodeids=? where wfid=? and nodename=? and treename=?";
            }
            Object[] args = { treeIdList, wfID, nodeName, treeName };
            update(sql, CORE, args);
            response.getWriter().write("{success:true}");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	/**
	 * 
	 * <br>
	 * Description:根据表名和节点返回表单权限 <br>
	 * Author:尹宇星 <br>
	 * Date:2011-11-07
	 * 
	 * @param formName
	 * @param nodeName
	 * @return
	 * @throws Exception
	 */
	private String getEditFields(String activityName, String zfjcType, String formName, String yw_guid)
			throws Exception {
		// 案件调查处理审批表在听证后修改处理意见的，需要修改权限
		if (yw_guid.contains("v02") && formName.equals("ajsccpb")) {
			if ("大队审查".equals(activityName)) {
				activityName = "大队审理";
			} else if ("执行室审查".equals(activityName)) {
				activityName = "案件执行室审查";
			} else if ("支队审核".equals(activityName)) {
				activityName = "支队长审核";
			} else if ("政策法规处意见".equals(activityName)) {
				activityName = "政策法规处审批";
			} else if ("分管局长签批".equals(activityName)) {
				activityName = "局长签批";
			} else if ("更改意见".equals(activityName)) {
				activityName = "审查决定";
			}
		}
		String sql = "select editFiles from FORM_AUTHORITY where zfjcType=? and activityName=? and formName=?";
		Object[] args = { zfjcType, activityName, formName };
		List<Map<String, Object>> queryList = query(sql, CORE, args);
		String ret = "";
		if (queryList.size() > 0) {
			ret = queryList.get(0).toString();
			ret = ret.substring(11, ret.length() - 1);
		}
		return ret;
	}

	/**
	 * 
	 * <br>
	 * Description:查询对应表中数据 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param tableName
	 *            表名
	 * @param keyValue
	 *            主键值
	 * @param primaykey
	 *            表的主键
	 * @param listFields
	 *            所有字段
	 * @param editFields
	 *            可编辑字段
	 * @return
	 */
	private String queryData(String tableName, String primaryKey, String keyValue,
			List<Map<String, Object>> listFields, String editFields) {
		String jdbcName = request.getParameter("jdbcname");
		String sql = "";
		List<Map<String, Object>> queryList = null;
		if (keyValue.indexOf("$") < 0) {
			sql = "select * from " + tableName + " where " + primaryKey + "= ?";
			Object[] args = { keyValue };
			queryList = query(sql, jdbcName, args);
		} else {
			sql = "select * from " + tableName + " where " + keyValue.split("\\$")[0] + " = ?";
			Object[] args = { keyValue.split("\\$")[1] };
			queryList = query(sql, jdbcName, args);
		}
		Iterator<Map<String, Object>> it = queryList.iterator();
		String ret = "";

		while (it.hasNext()) {
			Map<String, Object> rece = (Map<String, Object>) it.next();
			Map<String, Object> map = new HashMap<String, Object>();
			String colunmName = "";
			String dataType = "";
			for (Map<String, Object> field : listFields) {
				colunmName = (String) field.get("COLUMN_NAME");
				dataType = (String) field.get("DATA_TYPE");
				if (dataType.equals("DATE")) {
					Timestamp stamp = (Timestamp) rece.get(colunmName);
					if (stamp != null) {
						map.put(colunmName, sdf.format(stamp));
					}
				} else {
					map.put(colunmName, (Object) rece.get(colunmName));
				}
				if (editFields.toUpperCase().indexOf(colunmName) >= 0 || editFields.equals("")) {
					map.put(colunmName + "Edit", "true");
				} else {
					map.put(colunmName + "Edit", "false");
				}
			}
			try {
				ret = UtilFactory.getJSONUtil().objectToJSON(map);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ret = UtilFactory.getStrUtil().escape(ret);
		return ret;
	}

	/**
	 * 
	 * <br>
	 * Description:查找数据库中是否有该条记录 <br>
	 * Author：尹宇星 <br>
	 * Date:2011-11-07
	 * 
	 * @param tableName
	 *            表名
	 * @param formName
	 *            字段名
	 * @param nodeName
	 *            字段名
	 * @return 若有该记录返回true，否则返回false
	 */
	private boolean queryData(String activityName, String zfjcType, String formName) {
		boolean b = false;
		List<Map<String, Object>> queryList = null;
		String sql = "select * from FORM_AUTHORITY where zfjcType=? and activityName=? and formName=?";
		Object[] args = { zfjcType, activityName, formName };
		queryList = query(sql, CORE, args);
		if (queryList.size() > 0) {
			b = true;
		}
		return b;
	}
}