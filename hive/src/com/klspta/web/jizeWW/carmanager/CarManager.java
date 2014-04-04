package com.klspta.web.jizeWW.carmanager;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
/**
 * 
 * <br>Title:车辆管理
 * <br>Description:关于车辆增删改查等操作处理
 * <br>Author:朱波海
 * <br>Date:2013-7-8
 */
public class CarManager extends AbstractBaseBean {
	/***************************************************************************
	 * 
	 * <br>
	 * Description:车辆管理 <br>
	 * Author:朱波海 <br>
	 * Date:2012-11-12
	 */
	public void getAllCarData() {
		String sql = "select CAR_ID,CAR_NAME,CAR_UNIT,CAR_PERSON,CAR_PERSON_PHONE,CAR_CANTONCODE,CAR_NUMBER,CAR_INFO_XZQH_NAME ,CAR_STYLE, to_char(CAR_GMRQ,'yyyy-MM-dd') as CAR_GMRQ from car_info";
		List<Map<String, Object>> query = query(sql, YW);
		response(query);
	}

	/***************************************************************************
	 * 
	 * <br>
	 * Description:增加/修改操作处理 <br>
	 * Author:朱波海 <br>
	 * Date:2012-11-12
	 */
	public void addCarData() {
		String car_id = request.getParameter("car_id");
		String car_name = request.getParameter("car_name");
		String car_unit = request.getParameter("car_unit");
		String car_person = request.getParameter("car_person");
		String car_person_phone = request.getParameter("car_person_phone");
		String car_number = request.getParameter("car_number");
		String car_info_xzqh_name = request.getParameter("car_info_xzqh_name");
		String car_style = request.getParameter("car_style");
		String car_gmrq = request.getParameter("car_gmrq");
		String sql_xzqh = "select qt_ctn_code from code_xzqh where na_ctn_name='"
				+ car_info_xzqh_name + "'and qt_parent_code='320300'";
		List<Map<String, Object>> query = query(sql_xzqh, CORE);
		String car_cantoncode = query.get(0).get("qt_ctn_code").toString();
		// 增加
		if (car_id.equals("") | car_id == null) {
			String sql = " insert into car_info (car_name,car_number,car_unit,car_person,car_person_phone,car_cantoncode,car_info_xzqh_name,car_style,car_gmrq) values ('"
					+ car_name
					+ "','"
					+ car_number
					+ "','"
					+ car_unit
					+ "','"
					+ car_person
					+ "','"
					+ car_person_phone
					+ "','"
					+ car_cantoncode
					+ "','"
					+ car_info_xzqh_name
					+ "','"
					+ car_style + "',to_date('" + car_gmrq + "','yyyy-mm-dd'))";
			update(sql, YW);
			response("{success:true}");

		} else {
			//String sql3 = "select car_number from car_info where car_id='"
			//		+ car_id + "'";
		//	List<Map<String, Object>> query3 = query(sql3, YW);
			String sql = "UPDATE car_info SET car_name='" + car_name
					+ "',car_unit ='" + car_unit + "' ,car_person='"
					+ car_person + "', " + "car_person_phone='"
					+ car_person_phone + "',car_cantoncode='" + car_cantoncode
					+ "',car_number='" + car_number + "',"
					+ "car_info_xzqh_name='" + car_info_xzqh_name
					+ "',car_style='" + car_style + "',car_gmrq=to_date('"
					+ car_gmrq + "','yyyy-mm-dd')" + " where car_id='" + car_id
					+ "'";
			update(sql, YW);
			response("{success:true}");
		}
	}

	public void getxzqhData() {
		String sql = "select QT_CTN_CODE,NA_CTN_NAME from CODE_XZQH where QT_PARENT_CODE='320300'";
		List<Map<String, Object>> query = query(sql, CORE);
		response(query);

	}

	/***************************************************************************
	 * 
	 * <br>
	 * Description:删除 <br>
	 * Author:朱波海 <br>
	 * Date:2012-11-12
	 */
	public void deleteCarData() {
		String car_id = request.getParameter("car_id");
		car_id = UtilFactory.getStrUtil().unescape(car_id);
		String sql = " Delete from car_info where car_id='" + car_id + "'";
		update(sql, YW);
		response("success");
	}

	/***************************************************************************
	 * 
	 * <br>
	 * Description:查找 <br>
	 * Author:朱波海 <br>
	 * Date:2012-11-12
	 */
	public void queryCarData() {
		String keyworld = request.getParameter("keyWord");
		String xzqh = request.getParameter("xzqh");
		if (keyworld != "" & xzqh != "") {
			keyworld = UtilFactory.getStrUtil().unescape(keyworld);
			String sql = "select CAR_NAME,CAR_UNIT,CAR_PERSON,CAR_PERSON_PHONE,CAR_CANTONCODE,CAR_NUMBER,CAR_INFO_XZQH_NAME ,CAR_STYLE,to_char(CAR_GMRQ,'yyyy-MM-dd') as CAR_GMRQ from car_info t where (t.CAR_NUMBER||t.CAR_NAME||t.CAR_STYLE ||t.CAR_PERSON like '%"
					+ keyworld + "%') and CAR_CANTONCODE='" + xzqh + "'";
			List<Map<String, Object>> query = query(sql, YW);
			response(query);
		} else if (xzqh != "") {
			String sql = "select CAR_NAME,CAR_UNIT,CAR_PERSON,CAR_PERSON_PHONE,CAR_CANTONCODE,CAR_NUMBER,CAR_INFO_XZQH_NAME ,CAR_STYLE,to_char(CAR_GMRQ,'yyyy-MM-dd') as CAR_GMRQ from car_info t where  CAR_CANTONCODE='"
					+ xzqh + "'";
			List<Map<String, Object>> query = query(sql, YW);
			response(query);

		} else if (xzqh == "" & keyworld != "") {
			keyworld = UtilFactory.getStrUtil().unescape(keyworld);
			String sql = "select CAR_NAME,CAR_UNIT,CAR_PERSON,CAR_PERSON_PHONE,CAR_CANTONCODE,CAR_NUMBER,CAR_INFO_XZQH_NAME ,CAR_STYLE,to_char(CAR_GMRQ,'yyyy-MM-dd') as CAR_GMRQ from car_info t where (t.CAR_NUMBER||t.CAR_NAME||t.CAR_STYLE||t.CAR_PERSON  like '%"
					+ keyworld + "%')";
			List<Map<String, Object>> query = query(sql, YW);
			response(query);
		}

	}

}
