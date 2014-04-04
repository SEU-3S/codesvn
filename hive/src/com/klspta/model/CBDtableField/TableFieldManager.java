package com.klspta.model.CBDtableField;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

/**
 * 
 * <br>Title:
 * <br>Description:
 * <br>Author:黎春行
 * <br>Date:2013-12-4
 */
public class TableFieldManager extends AbstractBaseBean {
	/**
	 * 表字段管理类显示内容
	 */
	public static String[][] TABLE_INF = new String[][]{{"TABLENAME", "0.1","表名"},{"FIELD", "0.1","字段名"},{"DATATYPE", "0.1","数据类型"},{"ISSHOW", "0.1","是否展现"},{"SHOWNAME", "0.1","别名"},{"ANNOTATION", "0.1","注释"}}; 
	private CBDtableFields tableFields = new CBDtableFields();
	
	/**
	 * 
	 * <br>Description:根据前台传递的tablename,获取可编辑的字段
	 * <br>Author:黎春行
	 * <br>Date:2013-12-5
	 */
	public void getTableInf(){
		String userid = request.getParameter("userid");
		String tablename = request.getParameter("tablename");
		List<Map<String, Object>> returnList = tableFields.getFormInf(tablename);
		response(returnList);
	}
	
	public void addTableField(){
		String userid = request.getParameter("userid");
		String tablename = request.getParameter("tablename");
		String fieldname = request.getParameter("fieldname");
		String type = request.getParameter("type");
		String annotation = request.getParameter("annotation");
		String showname = request.getParameter("showname");
		String isshow = request.getParameter("isshow");
		if(showname !=null){
			showname = UtilFactory.getStrUtil().unescape(showname);
		}
		if(annotation !=null){
			annotation = UtilFactory.getStrUtil().unescape(annotation);
		}
		//type为VARCHAR时，设定默认大小为32字节
		type +="(32)";
		boolean cacluate = tableFields.addField(tablename, fieldname, type,annotation, showname, isshow);
		response(String.valueOf(cacluate));
	}
	
	public void deleteTableField(){
		String userid = request.getParameter("userid");
		String tablename = request.getParameter("tablename");
		String fieldname = request.getParameter("fieldname");
		boolean cacluate = tableFields.dropField(tablename, fieldname);
		response(String.valueOf(cacluate));
	}
	
	public void modifyTableField(){
		// String userid = request.getParameter("userid");
		String tablename = request.getParameter("tablename");
		String fieldname = request.getParameter("fieldname");
		String showname = request.getParameter("showname");
		String isshow = request.getParameter("isshow");
		if(showname !=null){
			showname = UtilFactory.getStrUtil().unescape(showname);
		}
		boolean cacluate = tableFields.modifyField(tablename, fieldname, showname, isshow);
		response(String.valueOf(cacluate));
	}
	
}
