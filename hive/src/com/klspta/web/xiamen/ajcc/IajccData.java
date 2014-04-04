package com.klspta.web.xiamen.ajcc;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>Title:巡查核查数据管理类
 * <br>Description:用来实现外业成果的增、删、改、查
 * <br>Author:黎春行
 * <br>Date:2013-11-20
 */
public interface IajccData {

	/**
	 * 
	 * <br>Description:获取待处理列表
	 * <br>Author:黎春行
	 * <br>Date:2013-11-20
	 * @param userId
	 * @param keyword
	 * @return
	 */
	public List<Map<String, Object>>  getDclList(String userId,String keyword);
	
	/**
	 * 
	 * <br>Description:添加新的外业成果记录
	 * <br>Author:黎春行
	 * <br>Date:2013-11-21
	 * @param userid
	 * @return
	 */
	public String SetNewRecord(String userid);
}
