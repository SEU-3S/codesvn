package com.klspta.base.util;

import com.klspta.base.util.api.ICoordinateChangeUtil;
import com.klspta.base.util.api.IShapeUtil;

/**
 * 
 * <br>
 * Title:工具工厂类 <br>
 * Description:GIS工具类实例均通过此工厂获取 <br>
 * Author:陈强峰 <br>
 * Date:2013-6-5
 */
public class GisUtilFactory {

	/**
	 * 
	 * <br>
	 * Description:私有化 <br>
	 * Author:陈强峰 <br>
	 * Date:2013-6-5
	 */
	private GisUtilFactory() {
	}

	/**
	 * <br>
	 * Description:获取坐标转换工具 <br>
	 * Author:陈强峰 <br>
	 * Date:2013-6-5
	 * 
	 * @return
	 */
	public static ICoordinateChangeUtil getCoordinateChangeUtil() {
		return UtilFactory.getCoordinateChangeUtil();
	}

	/**
	 * <br>
	 * Description:shp文件操作工具 <br>
	 * Author:陈强峰 <br>
	 * Date:2013-6-5
	 * 
	 * @return
	 */
	public static IShapeUtil getShapeUtil() {
		return UtilFactory.getShapeUtil();
	}
}
