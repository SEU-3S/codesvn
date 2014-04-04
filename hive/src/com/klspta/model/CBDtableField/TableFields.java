package com.klspta.model.CBDtableField;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;

public class TableFields extends AbstractBaseBean implements ITableFields {

	protected String template  = YW;
	
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	@Override
	public boolean addField(String formName, String fieldName, String type, String annotation,
			String defaultValue) {
		StringBuffer alterSql = new StringBuffer();
		alterSql.append("alter table ").append(formName).append(" add ( ");
		alterSql.append(fieldName).append(" ").append(type);
		if(defaultValue != null){
			alterSql.append(" default ").append(defaultValue);
		}
		alterSql.append(")");
		boolean result = true;
		try {
			update(alterSql.toString(), template);
		} catch (Exception e) {
			result = false;
			System.out.println("字段已存在");
		}
		if(!"null".equals(annotation) && annotation != null){
			String sql = "comment on column " + formName +"." + fieldName + "is '" + annotation + "'";
			update(sql, template);
		}
		
		return result;
	}

	@Override
	public boolean addField(String formName, String fieldName, String type, String annotation) {
		return addField(formName, fieldName, type, annotation, null);
	}

	@Override
	public boolean dropField(String formName, String field) {
		return dropFields(formName, new String[]{field});
	}

	@Override
	public boolean dropFields(String formName, String[] fields) {
		StringBuffer alterSql = new StringBuffer();
		alterSql.append("alter table ").append(formName).append(" drop (");
		for(int i = 0; i < fields.length - 1; i++){
			alterSql.append(fields).append(",");
		}
		alterSql.append(fields[fields.length - 1]).append(") CASCADE CONSTRAINTS");
		boolean result = true;
		try {
			update(alterSql.toString(), template);
		} catch (Exception e) {
			result = false;
			System.out.println("字段不存在，无法删除");
		}
		return result;
	}

	@Override
	public boolean setFieldUnused(String formName, String field) {
		return setFieldsUnused(formName, new String[]{field});
	}

	@Override
	public boolean setFieldsUnused(String formName, String[] fields) {
		StringBuffer alterSql = new StringBuffer();
		alterSql.append("alter table ").append(formName).append(" set unused (");
		for(int i = 0; i < fields.length - 1; i++){
			alterSql.append(fields).append(",");
		}
		alterSql.append(fields[fields.length - 1]).append(") CASCADE CONSTRAINTS");
		int i = update(alterSql.toString(), template);
		return Boolean.parseBoolean(String.valueOf(i));
	}

	@Override
	public List<Map<String, Object>> getFormInf(String formName) {
		return null;
	}



}
