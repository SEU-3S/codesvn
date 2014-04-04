package com.klspta.model.CBDtableField;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>Title:数据表字段管理类
 * <br>Description:管理和控制数据库中数据表字段的增、删、改
 * <br>Author:黎春行
 * <br>Date:2013-12-4
 */
public interface ITableFields {

	/**
	 * 
	 * <br>Description：在指定表中增加一列
	 * <br>Author:黎春行
	 * <br>Date:2013-12-4
	 * @param formName
	 * @param fieldName
	 * @param type
	 * @param defalultValue
	 * @return
	 */
	public boolean addField(String formName, String fieldName, String type, String annotation, String defalultValue);
	
	/**
	 * 
	 * <br>Description:在指定表中增加一列
	 * <br>Author:黎春行
	 * <br>Date:2013-12-4
	 * @param formName
	 * @param fieldName
	 * @param type
	 * @return
	 */
	public boolean addField(String formName, String fieldName, String type,String annotation);
	
	/**
	 * 
	 * <br>Description:设定字段属性为Unused
	 * <br>Author:黎春行
	 * <br>Date:2013-12-4
	 * @param formName
	 * @param fields
	 * @return
	 */
	public boolean setFieldsUnused(String formName, String[] fields);
	
	/**
	 * 
	 * <br>Description:设定字段属性为Unused
	 * <br>Author:黎春行
	 * <br>Date:2013-12-4
	 * @param formName
	 * @param field
	 * @return
	 */
	public boolean setFieldUnused(String formName,String field);
	
	/**
	 * 
	 * <br>Description:删除表中的对应列
	 * <br>Author:黎春行
	 * <br>Date:2013-12-4
	 * @param formName
	 * @param fields
	 * @return
	 */
	public boolean dropFields(String formName, String[] fields);
	
	/**
	 * 
	 * <br>Description:删除表中的对应列
	 * <br>Author:黎春行
	 * <br>Date:2013-12-4
	 * @param formName
	 * @param field
	 * @return
	 */
	public boolean dropField(String formName, String field);
	
	/**
	 * 
	 * <br>Description:获取表字段的详细信息
	 * <br>Author:黎春行
	 * <br>Date:2013-12-4
	 * @param formName
	 * @return
	 */
	public List<Map<String, Object>> getFormInf(String formName);
	
}
