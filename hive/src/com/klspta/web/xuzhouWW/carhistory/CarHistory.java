package com.klspta.web.xuzhouWW.carhistory;

import java.util.List;
import java.util.Map;
import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

/***
 * 
 * <br>Title:车辆轨迹
 * <br>Description:车辆历史轨迹处理类
 * <br>Author:朱波海
 * <br>Date:2013-7-8
 */
public class CarHistory extends AbstractBaseBean {

	public CarHistory() {
	}
/***
 * 
 * <br>Description:根据车辆id、时间等信息获取车辆历史坐标点
 * <br>Author:朱波海
 * <br>Date:2013-7-8
 */
	public void getHistoryById() {
		String carId = request.getParameter("carId");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		carId = UtilFactory.getStrUtil().unescape(carId);
		String carIds[] = carId.split(",");
		String cars = "'" + carIds[0] + "'";
		for (int i = 1; i < carIds.length; i++) {
			cars += ",'" + carIds[i] + "'";
		}
		String sql = "select t2.car_name carid,t1.x,t1.y,to_char(t1.history_date, 'YYYY/MM/DD HH24:MI:SS') || ' UTC' history_date from car_location_history t1,car_info t2 where t1.carid=t2.car_id and t2.car_name in("
				+ cars
				+ ") and t1.history_date >to_date(?,'YYYY-MM-DD HH24:MI:SS') and t1.history_date <to_date(?,'YYYY-MM-DD HH24:MI:SS') order by t1.carid,t1.history_date";
		List<Map<String, Object>> list = query(sql, YW, new Object[] {
				startDate, endDate });
		response(list);
	}
}
