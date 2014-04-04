package com.klspta.web.cbd.cbxmjbsj;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
/**
 * 
 * <br>Title:表单管理类
 * <br>Description：管理前台表单的增加、删除、修改、查询等功能
 * <br>Author:黎春行
 * <br>Date:2013-7-26
 */
public class ProjectInfoSave extends AbstractBaseBean {
	private String keyField = "";
	private String keyValue = "";
	private String formName = "";
	private String url = "";
	
	/**
	 * 
	 * <br>Description:初始化表单页面
	 * <br>Author:黎春行
	 * <br>Date:2013-7-26
	 */
	public void loadData(){
		getJsonbyGuid();
	}
	
	/**
	 * 
	 * <br>Description:根据guid获取表单内容json串
	 * <br>Author:黎春行
	 * <br>Date:2013-7-26
	 */
	public void getJsonbyGuid(){
		formName = request.getParameter("_formName");
		keyField = request.getParameter("_keyfield");
		keyValue = request.getParameter("_key");
		String retJson = query(formName, keyField, keyValue);
		response(retJson);
	}
	
	/**
	 * 
	 * <br>Description:查询数据
	 * <br>Author:黎春行
	 * <br>Date:2013-7-26
	 * @return
	 */
	private String  query(String formName, String keyField, String keyValue){
		String sql = "select * from form_sql t where t.formName = ?";
		List<Map<String, Object>> getSqlList = query(sql, CORE, new Object[]{formName});
		
		//对查询到的处理结果做处理
		Map<String, Object> responseMap = new TreeMap<String, Object>();
		for(int i = 0; i < getSqlList.size(); i++){
			if("s".equals(String.valueOf(getSqlList.get(i).get("mapping")))){
				//查询结果关系是一对一时
				String oracleName = String.valueOf(getSqlList.get(i).get("ORACLENAME"));
				String selectSql = "select * from " + oracleName + " t where t." + keyField + "='" + keyValue + "'";
				List<Map<String, Object>> signleList = query(selectSql, CORE);
				if(signleList.size() > 0){
					responseMap.putAll(signleList.get(0));
				}
			}else if ("m".equals(String.valueOf(getSqlList.get(i).get("mapping")))) {
				//查询结果关系是一对多时
				String oracleName = String.valueOf(getSqlList.get(i).get("ORACLENAME"));
				String selectSql = "select * from " + oracleName + " t where t." + keyField + "='" + keyValue + "' order by t.num";
				List<Map<String, Object>> multiList = query(selectSql, CORE);
				//获取表单中字段名称
				List<Map<String, Object>> formFields = getFormFields(oracleName);
				//修改字段名称
				for(int j = 0; j < multiList.size(); j++){
					for(int t = 0; t < formFields.size(); t++){
						String fieldName = String.valueOf(formFields.get(t).get("column_name"));
						String fieldValue = String.valueOf(multiList.get(j).get(fieldName));
						if("null".equals(fieldValue)){
							fieldValue = "";
						}
						fieldName = fieldName + "_" + (multiList.get(j).get("num"));
						responseMap.put(fieldName, fieldValue);
					}
				}
			}	
		}
		String ret = "";
		try {
			ret = UtilFactory.getJSONUtil().objectToJSON(responseMap);
		} catch (Exception e) {
			responseException(this, "query", "100050", e);
		}
		ret = UtilFactory.getStrUtil().escape(ret);
		return ret;
	}
	
	/**
	 * 
	 * <br>Description:获取数据库中表单所含有的所有字段
	 * <br>Author:黎春行
	 * <br>Date:2013-7-26
	 * @param formName
	 * @return
	 */
	private List<Map<String, Object>> getFormFields(String tableName){
		tableName = tableName.substring(5);
		tableName = tableName.toUpperCase();
		//formName = formName.split(".")[1];
		String sql = "select distinct(t.column_name), t.data_type from dba_tab_columns t where t.table_name=?";
		Object[] args = {tableName};
		String jdbcName = "CORETemplate";
		List<Map<String, Object>> list = query(sql, jdbcName, args);
		return list; 
	}
	
	/**
	 * 
	 * <br>Description:表单数据保存
	 * <br>Author:黎春行
	 * <br>Date:2013-7-30
	 * @throws Exception 
	 */
	public void saveData() throws Exception{
		request.setCharacterEncoding("UTF-8");
		formName = request.getParameter("_formName");
		keyField = request.getParameter("_keyfield");
		keyValue = request.getParameter("_key");
		url = request.getHeader("referer");
		setToData(formName, keyField, keyValue);
	}
	
	/**
	 * 
	 * <br>Description:表单数据保存
	 * <br>Author:黎春行
	 * <br>Date:2013-8-12
	 * @param formName
	 * @param keyField
	 * @param keyValue
	 * @throws Exception
	 */
	private void setToData(String formName, String keyField, String keyValue) throws Exception{
		boolean isExist = true;
		String sql = "select * from form_sql t where t.formName = ?";
		List<Map<String, Object>> getSqlList = query(sql, CORE, new Object[]{formName});
		//主键不存在时，生成主键
		if("0".equals(keyValue)){
			isExist = false;
			keyValue = UtilFactory.getStrUtil().getGuid();
			this.keyValue = keyValue;
		}else{
			//判断数据库中是否确定存在主键的值
			String oracleName = String.valueOf(getSqlList.get(0).get("ORACLENAME"));
			String selectSql = "select * from " + oracleName + " t where t." + keyField + "='" + keyValue + "'";
			List<Map<String, Object>> signleList = query(selectSql, CORE);
			if(signleList.size() < 1){
				isExist = false;
			}
		}
		for(int i = 0; i < getSqlList.size(); i++){
			if("s".equals(String.valueOf(getSqlList.get(i).get("mapping")))){
				//查询结果关系是一对一时
				String oracleName = String.valueOf(getSqlList.get(i).get("ORACLENAME"));
				//获取表单中字段名称
				List<Map<String, Object>> formFields = getFormFields(oracleName);
				Map<String, Object> values = new HashMap<String, Object>();
				//实现对应的表单内容保存
				//获取保存的数据
				for(Map<String, Object> field : formFields){
					String fieldString = (String)field.get("COLUMN_NAME");
					String valueString = "";
					if(keyField.equals(fieldString.toLowerCase())){
						valueString = keyValue;
					}else{
						valueString = request.getParameter(fieldString.toLowerCase());
						
						if(valueString != null){
							try {
								valueString = new String(valueString.getBytes("iso-8859-1"), "UTF-8");
							} catch (UnsupportedEncodingException e) {
								responseException(this, "setToData", "100050", e);
							}
						}
						String[] valueStrings = request.getParameterValues(fieldString.toLowerCase());
						if(valueString == null || "null".equals(valueString)){
							valueString = "";
						}else{
							if(valueStrings.length > 1){
								valueString = "";
								for(String s : valueStrings){
									valueString = valueString + "!" + s;
								}
							}
						}
					}
					values.put(fieldString, valueString);
				}
				values = calculateData(values);
				String updatesql = buildSQL(oracleName,formFields, values, isExist);
				update(updatesql, CORE);
				
			}else if ("m".equals(String.valueOf(getSqlList.get(i).get("mapping")))) {
				//查询结果关系是一对多时
				String oracleName = String.valueOf(getSqlList.get(i).get("ORACLENAME"));
				//获取表单中字段名称
				List<Map<String, Object>> formFields = getFormFields(oracleName);
				//获取所有前台传过来的字段名称
				String fieldName = "";
				for(Map<String, Object> field : formFields){
					String fieldString = (String)field.get("COLUMN_NAME");
					if(keyField.equals(fieldString.toLowerCase()) || "num".equals(fieldString.toLowerCase())){
						continue;
					}else{
						fieldName = fieldString;
						break;
					}
				}
				Enumeration<String> requestNames = request.getParameterNames();
				while(true){
					String requestName = "";
					if(requestNames.hasMoreElements()){
						requestName = String.valueOf(requestNames.nextElement());
					}else{
						break;
					}
					if(requestName == null || "".equals(requestName)){
						break;
					}else if(keyField.equals(requestName)){
						continue;   
					}
					if(requestName.contains(fieldName.toLowerCase() + "_")){
						//向数据库录入或更新一笔数据
						Map<String, Object> values = new HashMap<String, Object>();
						String num = requestName.split("_")[1];
						for(Map<String, Object> field : formFields){
							String fieldString = (String)field.get("COLUMN_NAME");
							String valueString = "";
							if(keyField.equals(fieldString.toLowerCase())){
								valueString = keyValue;
							}else if ("num".equals(fieldString.toLowerCase())) {
								continue;
							}else{
								valueString = request.getParameter(fieldString.toLowerCase() + "_" + num);
								if(valueString != null){
									try {
										valueString = new String(valueString.getBytes("iso-8859-1"), "UTF-8");
									} catch (UnsupportedEncodingException e) {
										responseException(this, "setToData", "100050", e);
									}
								}
								String[] valueStrings = request.getParameterValues(fieldString.toLowerCase() + "_" + num);
								if(valueString == null || "null".equals(valueString)){
									valueString = "";
								}else{
									if(valueStrings.length > 1){
										valueString = "";
										for(String s : valueStrings){
											valueString = valueString + "!" + s;
										}
									}
								}
							}
							values.put(fieldString, valueString);
						}
						values.put("NUM", num);
						String updateSql = buildSQL(oracleName, formFields, values, isExist);
						update(updateSql, CORE);
					}else{
						continue;
					}
				}
				
			}
		}
		response.sendRedirect(url + "&msg=success");
	}
	
	/**
	 * 
	 * <br>Description:将表单数据保存到数据库
	 * <br>Author:黎春行
	 * <br>Date:2013-8-1
	 * @param fieldnames
	 * @param fieldvalues
	 * @param isExist
	 * @return
	 */
	private String buildSQL(String formname, List<Map<String, Object>> fieldnames, Map<String, Object> fieldvalues, boolean isExist){
		StringBuffer sqlsb = new StringBuffer();
		String sql = "";
		if(isExist){
			//一对多关系时，存在的yw_guid添加数据时
			String num = (String)fieldvalues.get("NUM");
			boolean isExistMuti = false;
			if(!(num == null)){
				String isExistsql = "select * from " + formname + " where " + keyField + "='" + keyValue + "' and num = '" + num + "'";
				List<Map<String, Object>> isExistList = query(isExistsql, CORE);
				if(isExistList.size() < 1){
					isExistMuti = true;
				}
			}
			if(isExistMuti){
				sql = buildSQL(formname, fieldnames, fieldvalues, false);
			}else{
				//主键存在时，更新(update)数据
				sqlsb.append("update ").append(formname).append(" set ");
				for(Map<String, Object> fieldname : fieldnames){
					String fieldString = (String)fieldname.get("COLUMN_NAME");
					String dataTypeString = (String)fieldname.get("DATA_TYPE");
					//如果名称是主键，跳出本次循环
					if(keyField.equals(fieldString.toLowerCase()) || "num".equals(fieldString.toLowerCase()) ){
						continue;
					}
					String fieldvalue = (String)fieldvalues.get(fieldString);
					sqlsb.append(fieldString).append(" = ");
					if("VARCHAR2".equals(dataTypeString) || "NUMBER".equals(dataTypeString) || "CHAR".endsWith(dataTypeString)){
						sqlsb.append("'").append(fieldvalue).append("',");
					}else if ("DATE".equals(dataTypeString)) {
						sqlsb.append("TO_DATE('").append(fieldvalue).append("','yyyy-mm-dd hh24:mi:ss'),");
					}else{
						sqlsb.append(fieldvalue).append(",");
					}
				}
				
				sql = sqlsb.toString();
				sql = sql.substring(0, sql.length() - 1) + " where " + keyField + " = '" + keyValue + "'";
				if(!(num == null)){
					sql = sql + "and num = '" + num + "'"; 
				}
			}
			
		}else{
			//主键不存在时，添加(insert)数据
			sqlsb.append("insert into  ").append(formname);
			StringBuffer names = new StringBuffer();
			StringBuffer values = new StringBuffer();
			for(Map<String, Object> fieldname : fieldnames){
				String fieldString = (String)fieldname.get("COLUMN_NAME");
				String dataTypeString = (String)fieldname.get("DATA_TYPE");
				names.append(fieldString).append(",");
				String fieldvalue = (String)fieldvalues.get(fieldString);
				if("VARCHAR2".equals(dataTypeString) || "NUMBER".equals(dataTypeString) || "CHAR".endsWith(dataTypeString)){
					values.append("'").append(fieldvalue).append("',");
				}else if ("DATE".equals(dataTypeString)) {
					values.append("TO_DATE('").append(fieldvalue).append("','yyyy-mm-dd hh24:mi:ss'),");
				}else{
					values.append(fieldvalue).append(",");
				}
			}
			String namevalues = names.toString();
			namevalues = namevalues.substring(0, namevalues.length() - 1);
			String valueValues = values.toString();
			valueValues = valueValues.substring(0, valueValues.length() - 1);
			sqlsb.append("(").append(namevalues).append(") values (").append(valueValues).append(")");
			
			sql = sqlsb.toString();
			if(url.contains("?")){
				if(url.contains(keyField)){
					
				}else{
					url = url + "&" + keyField + "=" + keyValue;
				}	
			}else{
				url = url + "?" + keyField + "=" + keyValue;
			}
		}
		return sql;
	}
	
	private Map<String, Object> calculateData(Map<String, Object> values){
		//规模（万㎡）
		double GM = Double.parseDouble(values.get("GM").toString());
		//成本（亿元）
		double CB = Double.parseDouble(values.get("CB").toString());
		//住宅拆迁费用（亿元）
		double ZZCQFY = Double.parseDouble(values.get("ZZCQFY").toString());
		//安置房投资成本（亿元）
		double AZFTZCB = Double.parseDouble(values.get("AZFTZCB").toString());
		//企业拆迁费用（亿元）
		double QYCQFY = Double.parseDouble(values.get("QYCQFY").toString());
		//其他费用（亿元）
		double QTFY = Double.parseDouble(values.get("QTFY").toString());
		
		//住宅货币投资成本（亿元）
		double ZZHBTZCB = ZZCQFY - AZFTZCB;
		//拆迁货币投资（亿元）
		double CQHBTZ = QYCQFY + ZZHBTZCB;
		//其他费用占比
		double QTFYZB = QTFY/CB;
		//楼面成本（万元/㎡）
		double LMCB = CB/GM;
		
		DecimalFormat df = new DecimalFormat("##.00");//小数点后剩两位
		
		values.put("ZZHBTZCB", df.format(ZZHBTZCB));
		values.put("CQHBTZ", df.format(CQHBTZ));
		values.put("QTFYZB", df.format(QTFYZB * 100) + "%");
		values.put("LMCB", df.format(LMCB));
		
		return values;
	}
	
	/**
	 * 
	 * <br>Description:根据项目名称获取项目的yw_guid
	 * <br>Author:黎春行
	 * <br>Date:2013-8-26
	 * @throws UnsupportedEncodingException 
	 */
	public void getYw_guidbymc() throws UnsupportedEncodingException{
		String xmmc = new String(request.getParameter("xmmc").getBytes("iso-8859-1"), "utf-8");
		String sql = "select t.yw_guid from xminfo t where t.xmname=?";
		List<Map<String, Object>> returnList = query(sql, YW, new Object[]{xmmc});
		response(returnList);
	}
}